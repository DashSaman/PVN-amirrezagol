package com.pvnetwork.core

import com.pvnetwork.core.adapter.AdapterDescriptor
import com.pvnetwork.core.adapter.AdapterId
import com.pvnetwork.core.adapter.CapabilityId
import com.pvnetwork.core.adapter.CapabilityRegistry
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.connection.ConnectionStateMachine
import com.pvnetwork.core.connection.InvalidConnectionTransition
import com.pvnetwork.core.diagnostics.DiagnosticSanitizer
import com.pvnetwork.core.network.DnsMode
import com.pvnetwork.core.network.DnsPolicy
import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.SecretRef
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FoundationContractTest {
    @Test
    fun profileStoresSecretReferencesAndRequiresNamespacedExtensions() {
        val profile = PVProfile(
            id = ProfileId("profile-001"),
            displayName = "Test profile",
            protocolId = "wireguard",
            endpoint = Endpoint("vpn.example.invalid", 51820),
            secretRefs = mapOf("private-key" to SecretRef("secret://profile-001/private-key")),
            extensions = mapOf("wireguard.keepalive" to "25"),
        )

        assertEquals("secret://profile-001/private-key", profile.secretRefs.getValue("private-key").value)
        assertFailsWith<IllegalArgumentException> {
            profile.copy(extensions = mapOf("unnamespaced" to "bad"))
        }
    }

    @Test
    fun stateMachineAllowsEvidenceBackedLifecycleAndRejectsFakeConnectedState() {
        val machine = ConnectionStateMachine()
        machine.transition(ConnectionState.PREPARING)
        machine.transition(ConnectionState.CONNECTING)
        machine.transition(ConnectionState.ESTABLISHING_TUNNEL)
        machine.transition(ConnectionState.CONNECTED)
        assertEquals(ConnectionState.CONNECTED, machine.state)

        val fresh = ConnectionStateMachine()
        assertFailsWith<InvalidConnectionTransition> {
            fresh.transition(ConnectionState.CONNECTED)
        }
    }

    @Test
    fun diagnosticSanitizerRedactsSensitiveMetadata() {
        val sanitized = DiagnosticSanitizer.sanitize(
            mapOf(
                "endpoint" to "vpn.example.invalid",
                "Authorization" to "Bearer should-not-leak",
                "private_key" to "should-not-leak",
                "sessionToken" to "should-not-leak",
            )
        )

        assertEquals("vpn.example.invalid", sanitized["endpoint"])
        assertEquals(DiagnosticSanitizer.REDACTED, sanitized["Authorization"])
        assertEquals(DiagnosticSanitizer.REDACTED, sanitized["private_key"])
        assertEquals(DiagnosticSanitizer.REDACTED, sanitized["sessionToken"])
    }

    @Test
    fun capabilityRegistryDoesNotInferSupportFromResearch() {
        val wireguard = CapabilityId("wireguard")
        val registry = CapabilityRegistry(
            listOf(
                AdapterDescriptor(
                    id = AdapterId("test-adapter"),
                    version = "0-test",
                    capabilities = setOf(wireguard),
                )
            )
        )

        assertTrue(wireguard in registry.supportedCapabilities())
        assertFalse(CapabilityId("openvpn") in registry.supportedCapabilities())
    }

    @Test
    fun customDnsRequiresAtLeastOneResolver() {
        assertFailsWith<IllegalArgumentException> {
            DnsPolicy(DnsMode.CUSTOM)
        }
        assertEquals(listOf("1.1.1.1"), DnsPolicy(DnsMode.CUSTOM, listOf("1.1.1.1")).resolvers)
    }
}
