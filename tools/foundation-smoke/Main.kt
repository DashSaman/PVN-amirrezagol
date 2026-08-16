import com.pvnetwork.core.adapter.AdapterDescriptor
import com.pvnetwork.core.adapter.AdapterId
import com.pvnetwork.core.adapter.CapabilityId
import com.pvnetwork.core.adapter.CapabilityRegistry
import com.pvnetwork.core.branding.PVNetworkBrand
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.connection.ConnectionStateMachine
import com.pvnetwork.core.connection.InvalidConnectionTransition
import com.pvnetwork.core.diagnostics.DiagnosticSanitizer
import com.pvnetwork.core.i18n.PVLocales
import com.pvnetwork.core.i18n.TextDirection
import com.pvnetwork.core.i18n.TextKind
import com.pvnetwork.core.network.DnsMode
import com.pvnetwork.core.network.DnsPolicy
import com.pvnetwork.core.network.RoutingMode
import com.pvnetwork.core.network.RoutingPolicy
import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.SecretRef

fun main() {
    val profile = PVProfile(
        id = ProfileId("profile-001"), displayName = "Smoke profile", protocolId = "wireguard",
        endpoint = Endpoint("vpn.example.invalid", 51820),
        secretRefs = mapOf("private-key" to SecretRef("secret://profile-001/private-key")),
        extensions = mapOf("wireguard.keepalive" to "25"),
    )
    check(profile.secretRefs.values.single().value.startsWith("secret://"))

    val machine = ConnectionStateMachine()
    machine.transition(ConnectionState.PREPARING)
    machine.transition(ConnectionState.CONNECTING)
    machine.transition(ConnectionState.ESTABLISHING_TUNNEL)
    machine.transition(ConnectionState.CONNECTED)
    machine.transition(ConnectionState.DISCONNECTING)
    machine.transition(ConnectionState.DISCONNECTED)
    val invalidWasRejected = try { machine.transition(ConnectionState.CONNECTED); false } catch (_: InvalidConnectionTransition) { true }
    check(invalidWasRejected)

    val sanitized = DiagnosticSanitizer.sanitize(mapOf(
        "endpoint" to "vpn.example.invalid", "Authorization" to "Bearer should-not-leak",
        "private_key" to "should-not-leak", "sessionToken" to "should-not-leak",
    ))
    check(sanitized["endpoint"] == "vpn.example.invalid")
    check(sanitized["Authorization"] == DiagnosticSanitizer.REDACTED)
    check(sanitized["private_key"] == DiagnosticSanitizer.REDACTED)
    check(sanitized["sessionToken"] == DiagnosticSanitizer.REDACTED)

    val wireguard = CapabilityId("wireguard")
    val registry = CapabilityRegistry(listOf(AdapterDescriptor(AdapterId("fake-smoke-adapter"), "0-test", setOf(wireguard))))
    check(registry.adaptersFor(wireguard).size == 1)
    check(CapabilityId("openvpn") !in registry.supportedCapabilities())
    check(RoutingPolicy(RoutingMode.GLOBAL).rules.isEmpty())
    check(DnsPolicy(DnsMode.CUSTOM, listOf("1.1.1.1")).resolvers.size == 1)
    check(PVNetworkBrand.identity.productName == "PVNetwork")
    check(PVLocales.resolve("fa-IR") == PVLocales.PERSIAN)
    check(PVLocales.PERSIAN.direction == TextDirection.RTL)
    check(PVLocales.effectiveDirection(PVLocales.PERSIAN, TextKind.TECHNICAL_TOKEN) == TextDirection.LTR)
    println("PVNetwork foundation smoke: PASS")
}
