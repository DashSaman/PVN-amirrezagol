# EtherIP — Server / Peer Implementations

Review date: 2026-08-14 UTC

Entry 014 is infrastructure peer/bridge functionality, so “server” means an endpoint that terminates EtherIP and attaches recovered Ethernet frames to a bridge/Virtual Hub.

## Canonical / reviewed set

1. **RFC 3378** — wire authority. EtherIP carries Ethernet/IEEE 802.3 frames directly in IP, IPv4 protocol number 97, version 3 header. It is informational and does not provide integrated confidentiality/integrity.
2. **SoftEtherVPN/SoftEtherVPN** — primary reusable source candidate. Direct EtherIP source review is pinned at `b1f7ef00040786d00bfa06c27fa463d106851e0c`, especially `src/Cedar/Proto_EtherIP.c`. Existing family research also tracks the later shared SoftEther source baseline `49eb2f08641709d1af57a0d04971973ff94461db`. Root project license is Apache-2.0; third-party/submodule obligations remain separate.
3. **OpenBSD `etherip(4)`** — canonical OS-native behavior/reference implementation over IPv4/IPv6, bridge attachment and optional IPsec protection.
4. **FreeBSD `gif(4)` + bridge** — canonical OS-native behavior/reference implementation for Ethernet over IPv4/IPv6 using EtherIP.

SoftEther's official Stable sibling is tracked separately in the existing family research (`SoftEtherVPN/SoftEtherVPN_Stable`, observed stable commit `ed17437af9719ac66acab30faa29e375d613c35f`, `v4.44-9807-rtm`). Stable/Developer identity is not collapsed.

## Selection / reuse

- SoftEther: reusable server/runtime candidate behind a PVNetwork infrastructure adapter.
- OpenBSD/FreeBSD: OS-native peer/integration and interoperability references; this review does not approve copying their kernel source.
- No generic consumer EtherIP client is invented.
