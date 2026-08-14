# WireGuard / AmneziaWG — Ports, Transports, Handshake, and Roaming

Review date: 2026-08-14

Status: reference research only; not implementation/certification.

## WireGuard transport

WireGuard's official protocol description states that all protocol packets are sent over **UDP**. There is no TCP transport mode in the WireGuard protocol itself.

Operationally:

- a peer may listen on a configurable UDP port;
- the remote endpoint may be configured as host/IP plus UDP port;
- NAT/firewall rules are host/network deployment concerns, not alternate WireGuard transports;
- overlay/control products that tunnel or coordinate WireGuard peers must not be described as new WireGuard wire transports unless they actually alter the data plane.

Primary source:

- https://www.wireguard.com/protocol/

## Handshake and data packets

WireGuard uses a Noise_IK-derived authenticated handshake. The official protocol page defines the construction identifier as `Noise_IKpsk2_25519_ChaChaPoly_BLAKE2s` and documents separate handshake-initiation, handshake-response, cookie-reply, and transport-data packet roles.

The protocol intentionally refreshes handshake/key state periodically rather than treating a tunnel as a reliable stream connection. This design matters for loss/reordering and roaming tests.

## Endpoint roaming

WireGuard peers are identified cryptographically, not by a permanently fixed source IP/port tuple. A valid authenticated packet can update where a peer is currently reachable. This is the basis of endpoint roaming and must be distinguished from control-plane discovery.

PVNetwork implication: UI/state should separate:

- configured endpoint;
- last observed runtime endpoint;
- peer public-key identity;
- routing selectors (`AllowedIPs`).

Do not overwrite remembered configuration with transient runtime endpoint state without an explicit product rule.

## Cryptokey routing / AllowedIPs

`AllowedIPs` is both a routing selector and a peer-selection/security boundary in WireGuard-style configuration. It is not equivalent to a conventional “server route list” alone.

Required future tests:

- overlapping selectors;
- default-route/full-tunnel selectors;
- IPv4/IPv6 split routes;
- multiple peers with disjoint selectors;
- route removal on stop/crash;
- endpoint address excluded from recursive tunnel routing where required by platform implementation.

## Persistent keepalive

Persistent keepalive is an operational peer setting commonly used where NAT/firewall mappings must remain usable. It does not convert WireGuard into a session-oriented transport. Product UI should describe it as a NAT/reachability aid rather than a generic performance knob.

## AmneziaWG packet-shaping delta

Current reviewed `amneziawg-go` documentation exposes additional packet-related configuration, including:

- junk packet count/ranges before handshakes;
- S1-S4 message/content padding values;
- configurable H1-H4 message header/type ranges;
- custom signature packets I1-I5;
- AWG3+ header protection;
- AWG3+ content-padding ranges;
- configurable handshake/rekey/keepalive timing ranges.

Primary source:

- https://github.com/amnezia-vpn/amneziawg-go

These settings change observable packet layout/timing/headers around a WireGuard-derived core. They must be versioned and validated as AWG-generation-specific capabilities.

## Current interoperability/regression evidence

Open project issue evidence reviewed in 2026 includes:

- reports of S4/streaming-obfuscation configurations producing handshake failures on some Linux/Raspberry Pi environments despite working on another client platform;
- current discussion around AWG2/AWG3 source and parameter compatibility;
- current source/documentation clarification questions around signature/padding semantics;
- a current userspace race report involving package-global protocol type state when multiple devices are configured/closed concurrently.

These reports are not universal-failure proof. They are mandatory regression-test inputs.

Primary project issue sources:

- https://github.com/amnezia-vpn/amneziawg-go/issues
- https://github.com/amnezia-vpn/amneziawg-linux-kernel-module/issues

## Required PVNetwork packet/handshake matrix

Before support certification, test at least:

1. WireGuard kernel ↔ WireGuard kernel/userspace;
2. WireGuard userspace ↔ official Android/Windows/Apple implementations;
3. NATed peer with and without persistent keepalive;
4. endpoint roaming after IP/network change;
5. IPv4, IPv6 and dual-stack AllowedIPs;
6. AWG generation-specific client/server combinations;
7. AWG S1-S4/header/signature fields at valid boundaries;
8. MTU-sensitive junk/padding cases;
9. invalid/mismatched AWG fields and header-protection key;
10. repeated rekey under packet loss/reordering;
11. multiple concurrent AWG devices to detect global-state/race regressions;
12. crash/restart recovery without stale routes or leaked secrets.

## Product architecture consequence

The canonical profile should model WireGuard base fields separately from an explicit versioned AWG extension object. Runtime adapters may derive engine-specific config, but the UI/storage layer must not flatten AWG generation-specific packet controls into generic WireGuard fields.
