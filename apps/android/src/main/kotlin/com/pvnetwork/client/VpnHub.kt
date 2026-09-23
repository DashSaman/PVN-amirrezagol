package com.pvnetwork.client

import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Process-wide connection truth shared between the UI and the VPN service.
 * Only the service writes; the UI observes.
 */
object VpnHub {
    val state: StateFlow<ConnectionSnapshot> get() = _state
    private val _state = MutableStateFlow(ConnectionSnapshot(ConnectionState.DISCONNECTED))

    val lastReason: StateFlow<String?> get() = _lastReason
    private val _lastReason = MutableStateFlow<String?>(null)

    fun publish(snapshot: ConnectionSnapshot) {
        _state.value = snapshot
        _lastReason.value = snapshot.reasonCode ?: _lastReason.value
    }
}
