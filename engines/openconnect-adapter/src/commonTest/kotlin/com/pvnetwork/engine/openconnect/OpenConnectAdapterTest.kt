package com.pvnetwork.engine.openconnect

import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretStore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OpenConnectAdapterTest {
    @Test
    fun acceptsEvidenceBackedAnyConnectShape() {
        val validation = adapter().validate(profile())

        assertTrue(validation.isValid)
        assertEquals(setOf("openconnect"), adapter().descriptor.capabilities.map { it.value }.toSet())
    }

    @Test
    fun advertisesOnlyCapabilitiesConfirmedByTheRuntime() {
        val selected = adapter(protocols = setOf(OpenConnectAdapter.ANYCONNECT_PROTOCOL, "pulse"))

        assertEquals(setOf("openconnect"), selected.descriptor.capabilities.map { it.value }.toSet())
        assertEquals("OpenConnect version v9.12", selected.descriptor.upstreamVersion)
    }

    @Test
    fun rejectsKnownButUnimplementedUpstreamProtocols() {
        val validation = adapter().validate(profile(
            extensions = baseExtensions() + (OpenConnectAdapter.PROTOCOL_EXTENSION to "pulse"),
        ))

        assertFalse(validation.isValid)
        assertTrue(validation.issues.any { it.code == "OPENCONNECT_PROTOCOL_NOT_IMPLEMENTED" })
    }

    @Test
    fun failsClosedWhenHostRuntimeIsUnavailable() {
        val unavailable = adapter(available = false, protocols = emptySet())
        val validation = unavailable.validate(profile())

        assertFalse(validation.isValid)
        assertTrue(validation.issues.any { it.code == "OPENCONNECT_RUNTIME_UNAVAILABLE" })
        assertTrue(unavailable.descriptor.capabilities.isEmpty())
    }

    @Test
    fun rejectsUnsafeTrustCipherAndExternalExecutionOptions() {
        val validation = adapter().validate(profile(
            extensions = baseExtensions() + mapOf(
                OpenConnectAdapter.NO_SYSTEM_TRUST_EXTENSION to "true",
                OpenConnectAdapter.INSECURE_CIPHERS_EXTENSION to "true",
                OpenConnectAdapter.EXTERNAL_BROWSER_EXTENSION to "enabled",
                OpenConnectAdapter.CSD_WRAPPER_EXTENSION to "/tmp/wrapper",
            ),
        ))

        assertFalse(validation.isValid)
        val codes = validation.issues.map { it.code }.toSet()
        assertTrue("OPENCONNECT_SYSTEM_TRUST_DISABLE_FORBIDDEN" in codes)
        assertTrue("OPENCONNECT_INSECURE_CIPHERS_FORBIDDEN" in codes)
        assertTrue("OPENCONNECT_EXTERNAL_EXECUTION_UNSUPPORTED" in codes)
    }

    @Test
    fun rejectsMissingCredentialsAndPrepareCannotBypassValidation() {
        val invalid = profile(
            secretRefs = emptyMap(),
            extensions = mapOf(OpenConnectAdapter.PROTOCOL_EXTENSION to OpenConnectAdapter.ANYCONNECT_PROTOCOL),
        )
        val selected = adapter()
        val validation = selected.validate(invalid)

        assertFalse(validation.isValid)
        val codes = validation.issues.map { it.code }.toSet()
        assertTrue("OPENCONNECT_USERNAME_INVALID" in codes)
        assertTrue("OPENCONNECT_PASSWORD_SECRET_MISSING" in codes)
        assertFailsWith<IllegalStateException> { selected.prepare(invalid, NoopSecretStore) }
    }

    private fun adapter(
        available: Boolean = true,
        protocols: Set<String> = setOf(OpenConnectAdapter.ANYCONNECT_PROTOCOL),
    ) = OpenConnectAdapter(object : OpenConnectRuntimeFactory {
        override val runtimeDescriptor = OpenConnectRuntimeDescriptor(
            implementationId = "test-host-openconnect",
            upstreamVersion = "OpenConnect version v9.12",
            available = available,
            supportedProtocols = protocols,
        )

        override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection =
            error("not used by contract tests")
    })

    private fun profile(
        secretRefs: Map<String, SecretRef> = mapOf(
            OpenConnectAdapter.PASSWORD_SECRET_ROLE to SecretRef("secret://openconnect/password"),
        ),
        extensions: Map<String, String> = baseExtensions(),
    ) = PVProfile(
        id = ProfileId("openconnect-test"),
        displayName = "OpenConnect test",
        protocolId = OpenConnectAdapter.PROTOCOL_ID,
        endpoint = Endpoint("vpn.example", 443),
        secretRefs = secretRefs,
        extensions = extensions,
    )

    private fun baseExtensions() = mapOf(
        OpenConnectAdapter.PROTOCOL_EXTENSION to OpenConnectAdapter.ANYCONNECT_PROTOCOL,
        OpenConnectAdapter.USERNAME_EXTENSION to "pvnetwork-user",
    )

    private object NoopSecretStore : SecretStore {
        override fun put(
            purpose: com.pvnetwork.core.security.SecretPurpose,
            secret: CharArray,
        ): SecretRef = error("not used")

        override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? = null
        override fun delete(ref: SecretRef): Boolean = false
    }
}
