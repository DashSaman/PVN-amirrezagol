package com.pvnetwork.core.network

enum class RoutingMode {
    GLOBAL,
    DIRECT,
    RULE,
    SPLIT,
}

enum class RouteAction {
    TUNNEL,
    DIRECT,
    BLOCK,
}

data class RouteRule(
    val matcher: String,
    val action: RouteAction,
) {
    init {
        require(matcher.isNotBlank()) { "route matcher must not be blank" }
    }
}

data class RoutingPolicy(
    val mode: RoutingMode,
    val rules: List<RouteRule> = emptyList(),
)

enum class DnsMode {
    SYSTEM,
    CUSTOM,
    ENCRYPTED,
}

data class DnsPolicy(
    val mode: DnsMode,
    val resolvers: List<String> = emptyList(),
) {
    init {
        if (mode != DnsMode.SYSTEM) {
            require(resolvers.isNotEmpty()) { "non-system DNS policy requires at least one resolver" }
        }
        require(resolvers.none(String::isBlank)) { "DNS resolver must not be blank" }
    }
}
