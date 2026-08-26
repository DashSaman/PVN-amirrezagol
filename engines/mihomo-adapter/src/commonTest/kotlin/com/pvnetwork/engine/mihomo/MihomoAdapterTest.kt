package com.pvnetwork.engine.mihomo

import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretStore
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MihomoAdapterTest {
    private val adapter = MihomoAdapter(object : MihomoRuntimeFactory {
        override val runtimeDescriptor = MihomoRuntimeDescriptor(
            implementationId = "test-mihomo",
            upstreamVersion = "Mihomo Meta v1.19.30 linux amd64",
            availableCapabilities = MihomoAdapter.MIHOMO_PROTOCOLS,
        )
        override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection = error("not used")
    })

    @Test
    fun acceptsSelectedHysteria2Shape() {
        assertTrue(adapter.validate(profile(
            protocol = MihomoAdapter.HYSTERIA2_CAPABILITY,
            secretRole = MihomoAdapter.HYSTERIA2_PASSWORD_SECRET_ROLE,
        )).isValid)
    }

    @Test
    fun acceptsSelectedTuicV5Shape() {
        assertTrue(adapter.validate(profile(
            protocol = MihomoAdapter.TUIC_CAPABILITY,
            secretRole = MihomoAdapter.TUIC_PASSWORD_SECRET_ROLE,
            extra = mapOf("mihomo.tuic.uuid" to "550e8400-e29b-41d4-a716-446655440000"),
        )).isValid)
    }

    @Test
    fun acceptsSelectedAnyTlsShape() {
        assertTrue(adapter.validate(profile(
            protocol = MihomoAdapter.ANYTLS_CAPABILITY,
            secretRole = MihomoAdapter.ANYTLS_PASSWORD_SECRET_ROLE,
        )).isValid)
    }

    @Test
    fun rejectsUnsafeTlsAndTuicV4Token() {
        val validation = adapter.validate(profile(
            protocol = MihomoAdapter.TUIC_CAPABILITY,
            secretRole = MihomoAdapter.TUIC_PASSWORD_SECRET_ROLE,
            extra = mapOf(
                "mihomo.tuic.uuid" to "550e8400-e29b-41d4-a716-446655440000",
                "mihomo.tuic.token" to "legacy",
                "mihomo.skip-cert-verify" to "true",
            ),
        ))
        assertFalse(validation.isValid)
        val codes = validation.issues.map { it.code }.toSet()
        assertTrue("MIHOMO_UNSAFE_TLS_REJECTED" in codes)
        assertTrue("MIHOMO_TUIC_V4_TOKEN_UNSUPPORTED" in codes)
    }

    @Test
    fun rejectsAnyTlsSecurityWrapperUntilSeparatelyValidated() {
        val validation = adapter.validate(profile(
            protocol = MihomoAdapter.ANYTLS_CAPABILITY,
            secretRole = MihomoAdapter.ANYTLS_PASSWORD_SECRET_ROLE,
            extra = mapOf("mihomo.anytls.restls" to "true"),
        ))
        assertFalse(validation.isValid)
        assertTrue(validation.issues.any { it.code == "MIHOMO_ANYTLS_ADVANCED_SECURITY_NOT_EXPOSED" })
    }

    private fun profile(protocol: String, secretRole: String, extra: Map<String, String> = emptyMap()) = PVProfile(
        id = ProfileId("mihomo-$protocol-test"),
        displayName = "Mihomo $protocol",
        protocolId = protocol,
        endpoint = Endpoint("proxy.example", 443),
        secretRefs = mapOf(secretRole to SecretRef("secret://mihomo/$protocol")),
        extensions = mapOf("mihomo.application-protocol" to protocol) + extra,
    )
}
