package com.pvnetwork.core.connection

enum class ConnectionState {
    DISCONNECTED,
    PREPARING,
    REQUESTING_PERMISSION,
    CONNECTING,
    AUTHENTICATING,
    ESTABLISHING_TUNNEL,
    CONNECTED,
    RECONNECTING,
    DISCONNECTING,
    ERROR,
}

data class ConnectionSnapshot(
    val state: ConnectionState,
    val adapterId: String? = null,
    val reasonCode: String? = null,
)

class InvalidConnectionTransition(
    from: ConnectionState,
    to: ConnectionState,
) : IllegalStateException("invalid connection transition: $from -> $to")

/**
 * Enforces the product-owned lifecycle without inventing protocol-specific
 * success. Adapters may skip non-applicable intermediate stages only through
 * explicitly allowed edges.
 */
class ConnectionStateMachine(
    initial: ConnectionState = ConnectionState.DISCONNECTED,
) {
    var state: ConnectionState = initial
        private set

    fun transition(to: ConnectionState): ConnectionState {
        if (to == state) return state
        if (to !in allowedTransitions.getValue(state)) {
            throw InvalidConnectionTransition(state, to)
        }
        state = to
        return state
    }

    companion object {
        private val allowedTransitions: Map<ConnectionState, Set<ConnectionState>> = mapOf(
            ConnectionState.DISCONNECTED to setOf(ConnectionState.PREPARING),
            ConnectionState.PREPARING to setOf(
                ConnectionState.REQUESTING_PERMISSION,
                ConnectionState.CONNECTING,
                ConnectionState.ERROR,
                ConnectionState.DISCONNECTING,
            ),
            ConnectionState.REQUESTING_PERMISSION to setOf(
                ConnectionState.CONNECTING,
                ConnectionState.ERROR,
                ConnectionState.DISCONNECTING,
            ),
            ConnectionState.CONNECTING to setOf(
                ConnectionState.AUTHENTICATING,
                ConnectionState.ESTABLISHING_TUNNEL,
                ConnectionState.ERROR,
                ConnectionState.DISCONNECTING,
            ),
            ConnectionState.AUTHENTICATING to setOf(
                ConnectionState.ESTABLISHING_TUNNEL,
                ConnectionState.ERROR,
                ConnectionState.DISCONNECTING,
            ),
            ConnectionState.ESTABLISHING_TUNNEL to setOf(
                ConnectionState.CONNECTED,
                ConnectionState.ERROR,
                ConnectionState.DISCONNECTING,
            ),
            ConnectionState.CONNECTED to setOf(
                ConnectionState.RECONNECTING,
                ConnectionState.DISCONNECTING,
                ConnectionState.ERROR,
            ),
            ConnectionState.RECONNECTING to setOf(
                ConnectionState.CONNECTING,
                ConnectionState.AUTHENTICATING,
                ConnectionState.ESTABLISHING_TUNNEL,
                ConnectionState.CONNECTED,
                ConnectionState.ERROR,
                ConnectionState.DISCONNECTING,
            ),
            ConnectionState.DISCONNECTING to setOf(
                ConnectionState.DISCONNECTED,
                ConnectionState.ERROR,
            ),
            ConnectionState.ERROR to setOf(
                ConnectionState.PREPARING,
                ConnectionState.DISCONNECTING,
                ConnectionState.DISCONNECTED,
            ),
        )
    }
}
