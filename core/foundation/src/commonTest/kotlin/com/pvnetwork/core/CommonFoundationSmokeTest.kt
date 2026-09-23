package com.pvnetwork.core

import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.connection.ConnectionStateMachine
import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.PVProfile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Compilation smoke guarantee for mobile target expansion: the shared
 * foundation must construct its core models without JVM-only types so the
 * same commonMain code compiles for Android/iOS/Kotlin-Native targets.
 */
class CommonFoundationSmokeTest {

    @Test
    fun commonFoundationCreatesCoreModelsWithoutPlatformTypes() {
        val state: ConnectionState = ConnectionState.DISCONNECTED
        assertEquals(ConnectionState.DISCONNECTED, state)

        val snapshot = ConnectionSnapshot(state = state)
        assertEquals(state, snapshot.state)
    }

    @Test
    fun commonStateMachineDrivesFullLifecycleOnCommonCodeOnly() {
        val machine = ConnectionStateMachine()
        machine.transition(ConnectionState.PREPARING)
        machine.transition(ConnectionState.CONNECTING)
        machine.transition(ConnectionState.ESTABLISHING_TUNNEL)
        machine.transition(ConnectionState.CONNECTED)
        machine.transition(ConnectionState.DISCONNECTING)
        machine.transition(ConnectionState.DISCONNECTED)
        assertEquals(ConnectionState.DISCONNECTED, machine.state)
    }

    @Test
    fun canonicalProfileConstructsInCommonCode() {
        val profile = PVProfile(
            id = ProfileId("smoke-profile"),
            displayName = "Smoke Profile",
            protocolId = "smoke.protocol",
            endpoint = Endpoint(host = "192.0.2.1", port = 443),
        )
        assertEquals("smoke.protocol", profile.protocolId)
        assertTrue(profile.secretRefs.isEmpty())
    }
}
