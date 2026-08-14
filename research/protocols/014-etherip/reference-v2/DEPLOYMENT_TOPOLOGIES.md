# EtherIP — Deployment Topologies

Review date: 2026-08-14 UTC

## Covered patterns

1. **Bridge-to-bridge / LAN extension** — EtherIP peers bridge remote Ethernet segments over routed IP.
2. **SoftEther Virtual-Hub attachment** — EtherIP endpoint maps into a Virtual Hub through the reviewed IPC Layer-2 path.
3. **OS-native gateway pair** — OpenBSD/FreeBSD peer interfaces attach to bridges and outer IPv4/IPv6 paths according to OS support.
4. **Single-host/end-station tunnel** — permitted by RFC 3378 model, although PVNetwork priority is infrastructure/gateway use.

## Non-goals / risks

- not a normal remote-access consumer VPN;
- no integrated encryption;
- L2 broadcast/loop/VLAN/security-domain extension must be intentional;
- raw public-Internet deployment is not the preferred protected topology;
- IPsec-protected composition is entry 015 and must not be represented as raw EtherIP.
