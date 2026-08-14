# OpenVPN — Data Path / Wire Flow

Reference layer: `COMPLETE-REFERENCE-v2`

Research date: 2026-08-14

State: `IN-RESEARCH` — architecture-level packet flow is documented. Exact packet formats/state-machine details remain version-pinned protocol/source work.

## High-level components

A typical routed OpenVPN session contains:

```text
Application traffic
    ↓
OS routing decision
    ↓
TUN virtual interface
    ↓
OpenVPN client data path
    ↓
Data-channel encryption/authentication
    ↓
OpenVPN framing
    ↓
UDP or TCP socket
    ↓
Internet / underlay network
    ↓
OpenVPN server socket
    ↓
Data-channel decrypt/authenticate
    ↓
Server TUN / routing / forwarding
    ↓
Destination network / Internet / peer
```

In TAP/Layer-2 deployments the virtual-interface payload and server bridging behavior differ. PVNetwork must record TUN vs TAP/topology as separate capabilities.

---

# 1. Connection preparation

Before wire traffic can carry the VPN session, the client resolves/selects:

- remote host/IP;
- remote port;
- UDP/TCP transport;
- local bind/interface policy if configured;
- proxy path if supported/configured;
- certificate/credential/key material;
- control-channel protection material;
- platform virtual-interface permission/driver/service.

Failures at this stage are not TLS/authentication failures and should have separate error categories.

---

# 2. Control-channel establishment

Conceptual flow:

```text
Client
  → transport reachability
  → optional pre-TLS control-channel protection/check
  → OpenVPN/TLS control-channel negotiation
  ↔ server certificate / client identity / control messages
  ↔ optional user auth / challenge
  ↔ configuration/options exchange
  ↔ keying material / data-channel session establishment
Server
```

Exact messages/order depend on OpenVPN protocol/version/features and are not represented here as a packet-level normative spec.

PVNetwork status progression should reflect real stages:

- Resolving
- ConnectingTransport
- EstablishingControlChannel
- Authenticating
- NegotiatingOptions
- EstablishingDataChannel
- ConfiguringTunnel
- Connected

---

# 3. Option/configuration exchange

A server may push client configuration such as:

- VPN address/topology data;
- routes;
- default-route policy;
- DNS-related options;
- keepalive/session-related options;
- data-channel compatibility/options according to protocol version.

Product rule:

**server-pushed options are untrusted remote input.**

PVNetwork/OpenVPN Adapter must:

- validate what the core/platform accepts;
- enforce product security/routing policy;
- show effective routes/DNS in diagnostics;
- reject/ignore only according to explicit policy;
- prevent unsupported remote options from invoking arbitrary local scripts/code.

---

# 4. Virtual interface configuration

After the control session supplies/derives tunnel settings, the client configures the platform VPN interface.

Typical routed/TUN state includes:

- tunnel address/prefix;
- peer/gateway/topology semantics;
- routes;
- DNS configuration;
- MTU;
- IPv6 state;
- full/split tunnel policy.

Platform implementation differs:

### Windows

Driver/virtual adapter/service and optional DCO path.

### Linux

TUN device, routing, resolver integration and optional DCO/kernel module path.

### Android

`VpnService` constructs an OS-managed virtual interface and supplies the file descriptor/path to the native/core integration.

### Apple

NetworkExtension/packet tunnel architecture owns the virtual network interface and route/DNS configuration.

Do not assume the OpenVPN core alone performs every platform configuration step.

---

# 5. Userspace data path

Traditional userspace OpenVPN path:

```text
Plain IP packet from TUN
    ↓
OpenVPN userspace reads packet
    ↓
optional packet/data-channel processing
    ↓
symmetric encryption + integrity/AEAD
    ↓
OpenVPN data-channel framing
    ↓
send over UDP/TCP transport socket
```

Receive path reverses the process:

```text
UDP/TCP OpenVPN packet
    ↓
OpenVPN framing/session lookup
    ↓
authenticate/decrypt
    ↓
plain tunnel packet
    ↓
write to TUN
    ↓
OS/application routing
```

PVNetwork metrics must distinguish:

- application/tunnel bytes;
- encrypted transport bytes;
- core/process statistics;
- OS interface statistics.

Do not show one number without knowing what it measures.

---

# 6. DCO data path

With supported Data Channel Offload:

```text
Control channel / authentication / key management
        handled by OpenVPN userspace
                  ↓
       negotiated data-channel state
                  ↓
      installed into DCO backend
                  ↓
Plain tunnel traffic ↔ kernel/driver DCO ↔ encrypted OpenVPN data packets
```

Benefits can include lower userspace copy/context-switch overhead and higher throughput/lower CPU depending on platform/workload.

But DCO has a narrower feature set than unrestricted userspace OpenVPN.

PVNetwork must record actual runtime path:

- `USERSPACE`
- `DCO_ACTIVE`
- `DCO_REQUESTED_FALLBACK`
- `DCO_UNAVAILABLE`
- `DCO_PROFILE_INCOMPATIBLE`

Never infer DCO from a settings checkbox.

---

# 7. UDP transport path

For UDP OpenVPN:

- each OpenVPN transport datagram is carried over UDP;
- loss/reordering is handled by the relevant OpenVPN control/data behaviors rather than an outer reliable TCP stream;
- generally avoids TCP-over-TCP retransmission interaction for tunneled TCP flows;
- NAT mapping/liveness/keepalive behavior matters.

PVNetwork tests:

- NAT rebinding/network change;
- packet loss/reordering;
- high latency;
- IPv4/IPv6 underlay;
- UDP-blocked network fallback policy;
- sleep/resume.

---

# 8. TCP transport path

OpenVPN can operate over a TCP transport for networks where UDP is blocked or where deployment requires TCP.

Flow:

```text
OpenVPN framed stream
    ↓
TCP connection
    ↓
underlay network
```

Potential operational concern:

Tunneling TCP application traffic inside TCP can create coupled retransmission/congestion effects under loss. Therefore PVNetwork should not assume TCP is always faster/more reliable merely because it is connection-oriented.

Use transport choice based on server/profile/network constraints.

---

# 9. Full-tunnel routing

Conceptual:

```text
Application destination
    ↓
default/full-tunnel route points to VPN
    ↓
TUN
    ↓
OpenVPN
    ↓
server
    ↓
server routing/NAT
    ↓
Internet/private network
```

Critical loop-avoidance rule:

The route to the OpenVPN server's own underlay endpoint must remain reachable outside/through the correct physical path so the tunnel does not route its transport back into itself.

Product route manager must test this explicitly during network changes.

---

# 10. Split tunnel

Only selected prefixes/domains/apps should use the VPN.

Routing can come from:

- server-pushed routes;
- local profile policy;
- per-app OS policy;
- domain-aware routing layer in PVNetwork.

PVNetwork must maintain one effective policy and show conflicts clearly.

Do not blindly accept a server-pushed default route if the user/admin policy forbids full tunnel.

---

# 11. DNS path

DNS behavior can vary:

```text
App DNS query
    ↓
OS/PVNetwork resolver policy
    ↓
server-pushed or local DNS
    ↓
route through VPN or direct according to policy
```

Leak testing must verify actual packet path, not just displayed resolver address.

Platform differences are substantial, especially Windows NRPT/DNS, Android VpnService DNS, Apple NetworkExtension DNS and Linux resolver managers.

---

# 12. Server routed data path

Common routed server path:

```text
Encrypted client packet
  → OpenVPN server/DCO decrypt
  → server TUN
  → kernel routing/firewall
  → private LAN OR NAT/Internet OR another route
```

Return traffic must have a valid route back to the VPN client pool, directly or through NAT as designed.

Installer scripts often hide this routing decision with masquerade rules; PVNetwork server management must make it explicit.

---

# 13. Site-to-site / routed subnet path

Example:

```text
Site A LAN
  ↔ gateway/client OpenVPN
  ↔ encrypted tunnel
  ↔ server/gateway
  ↔ Site B LAN
```

Requirements:

- route advertisement/static routes;
- IP forwarding;
- firewall policy;
- no overlapping subnets or explicit translation design;
- return routes;
- client-specific route ownership where needed.

Do not solve every site-to-site deployment with NAT by default.

---

# 14. TAP / Layer-2 bridging path

TAP carries Ethernet frames instead of only routed IP packets.

Potential use cases:

- legacy Layer-2 bridging;
- broadcast/multicast-dependent applications;
- special Ethernet-level interoperability.

Costs/constraints:

- more broadcast traffic;
- bridge complexity;
- mobile/platform support limitations;
- larger attack surface;
- generally not the default modern remote-access design.

PVNetwork should classify TAP as Advanced/Legacy-compatible and never assume availability on every platform.

---

# 15. Rekey data path

During rekey/session renegotiation:

- control channel negotiates new data-channel keying state;
- old/new key slots may overlap according to protocol behavior;
- traffic should continue without plaintext fallback;
- DCO/userspace backend must update state consistently.

Tests must inject traffic continuously while rekeying and verify no leaks/drop spikes beyond defined tolerance.

---

# 16. Reconnect/network migration

On underlay change:

```text
old network lost
    ↓
transport socket unusable
    ↓
connection state -> Reconnecting
    ↓
resolve/select endpoint on new network
    ↓
control/auth/session resume/new session as supported
    ↓
rebuild routes/DNS/interface state
    ↓
Connected
```

PVNetwork must clean old physical-network routes/DNS exceptions and avoid duplicate tunnel instances.

---

# 17. Kill switch / failure data path

A real kill switch protects against data escaping directly when the tunnel is required but unavailable.

Failure scenarios:

- OpenVPN process crash;
- DCO/driver failure;
- server timeout;
- underlay change;
- route deletion;
- sleep/resume;
- application UI crash.

The kill-switch enforcement should live in an OS/platform policy layer independent from the GUI process where possible.

---

# 18. Logging / privacy

Data-path diagnostics may include:

- endpoint IP/port;
- tunnel IP;
- routes;
- DNS;
- bytes;
- cipher;
- DCO state;
- packet/drop counters.

Must not include:

- data-channel keys;
- private keys;
- tls-auth/tls-crypt key contents;
- passwords;
- raw browsing payloads;
- unredacted tokens.

## Remaining v2 gaps

- exact OpenVPN packet/opcode/state-machine diagrams by selected protocol version;
- DCO architecture per Windows/Linux selected driver/module;
- precise control/data packet framing from source/protocol documentation;
- actual packet captures for lab fixtures with secrets protected;
- per-platform DNS/routing diagrams;
- TAP platform support matrix.
