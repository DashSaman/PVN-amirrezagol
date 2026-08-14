# 035 — Barracuda TINA VPN — V1 Gate Reconciliation

Reviewed: 2026-08-14

Research completion only. No implementation, live CloudGen interoperability, Store/device, or production claim.

Canonical evidence is consolidated in `BARRACUDA_TINA_CURRENT_AUDIT.md`.

| # | V1 research gate | Result | Evidence / bounded conclusion |
|---:|---|---|---|
| 1 | Entry identity and scope | PASS | TINA is Barracuda's proprietary VPN technology used by CloudGen Firewall and Barracuda VPN clients; standard IPsec is a separate interoperability path. |
| 2 | Standards / protocol documentation | PASS (vendor-proprietary boundary) | Barracuda's current CloudGen documentation is authoritative for TINA. It describes TINA as a proprietary extension of IPsec; generic IPsec RFC behavior is not substituted for unpublished TINA-specific semantics. |
| 3 | Transports and ports | PASS | Current Barracuda docs expose UDP, TCP, UDP+TCP hybrid, ESP and routing transports; current tunnel settings document TCP 691 by default and TCP 443 for HTTP-proxy traversal, with ESP as IP protocol 50. |
| 4 | Handshake / state model | PASS (bounded proprietary detail) | Barracuda documents modified initial handshake, X.509-based authentication capability, heartbeat monitoring/fast failover and tunnel establishment. Unpublished packet framing/state internals are not invented. |
| 5 | Authentication | PASS | Current Barracuda docs cover user/password, X.509, X.509+password, `.lic` personal-license files, SAML and TOTP MFA depending on deployment; certificate examples require appropriate cert/user credentials. |
| 6 | Cryptography / security | PASS | Current CloudGen docs expose AES/AES256/AES-CTR/AES256-CTR and GCM/SHA256/SHA512-class options plus legacy algorithms explicitly marked not recommended. Product decision requires modern strong algorithms and does not normalize legacy choices. |
| 7 | Routing / DNS / address assignment | PASS | Client-to-site CloudGen policy, selective routing, published VPN networks, gateway selection and VPN service routing are documented. IPv6 can be used for tunnel envelope while cited client-to-site guidance limits payload traffic to IPv4. DNS specifics remain policy/platform dependent rather than invented. |
| 8 | Client implementations | PASS | Barracuda VPN Client is the canonical TINA client; Windows/macOS/Linux and BSD-family paths are documented, while Android CudaLaunch includes TINA. iOS CudaLaunch uses native IPsec and is not falsely counted as TINA. |
| 9 | Server / peer implementations | PASS | Barracuda CloudGen Firewall VPN service is the canonical server/peer. Generic IPsec peers are separate and not represented as TINA servers. |
| 10 | Platform support / capabilities | PASS | Current CloudGen 9.0 documentation maps standalone VPN clients on Windows, macOS, Linux/FreeBSD and client/mobile distinctions; platform-specific capabilities are retained. Exact current OS/CPU certification remains later testing. |
| 11 | Install / setup / package behavior | PASS | Official docs cover Windows full/VPN-only and unattended setup, Linux/OpenBSD installation, macOS GUI, mobile/CudaLaunch paths and CloudGen client-to-site server prerequisites. |
| 12 | UI / UX / menu / configuration mapping | PASS | V1 surfaces include named VPN profiles, server/transport/proxy/auth/cert fields, connect/disconnect, profile import, Always On/reconnect/gateway selection and server policy. Full menu reconstruction is deferred to V2. |
| 13 | Configuration / profile / import-export | PASS | Windows can import Firewall Admin profiles; macOS self-service docs use `.vpn` group-policy files; multiple named client profiles and server-side group policies are documented. Exact private schema is not reverse-invented. |
| 14 | Storage / credentials / secrets | PASS | `.lic` files and X.509 certs are explicit credential artifacts; user/password and certificate handling are documented, including macOS Keychain-related support in release history. Proprietary secret-store internals are evidence-backed N/A for source-level claims. |
| 15 | Logging / diagnostics / telemetry | PASS | CloudGen VPN service logs client-to-site TINA login/logout/accounting; client status/logging and server-side VPN diagnostics are documented product surfaces. Proprietary telemetry internals are not invented. |
| 16 | Reconnect / failover / lifecycle | PASS | Barracuda documents quick tunnel restoration, heartbeat monitoring, redundant VPN gateways, optimal gateway selection and Always On where supported. Runtime failover timing remains later certification. |
| 17 | Limitations / regressions | PASS | Proprietary-only interoperability, platform differences, IPv4 payload limitation in cited client-to-site flows, proxy/transport tradeoffs and legacy crypto warnings are explicit. |
| 18 | Source / version / release pinning | PASS (bounded N/A for source commit) | Current 2026 Barracuda Campus documentation covers active Network Access/VPN Client and CloudGen 9.0/10.5 families. No canonical public complete-source repo exists in the reviewed authoritative materials, so source SHA pinning is N/A rather than fabricated. |
| 19 | License / reuse / supply chain | PASS | TINA and complete first-party clients/CloudGen implementation are proprietary/reference-only. No redistribution/source reuse right is inferred. Exact binary hash/signature/EULA review belongs to packaging/legal certification. |
| 20 | Architecture / product decision | PASS | PVNetwork must use a distinct Barracuda TINA adapter/reference boundary; do not substitute generic IPsec. Preserve vendor transport/auth/profile/failover semantics and require legitimate implementation/reuse evidence before coding TINA support. |

## V1 decision

**`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT BARRACUDA-CERTIFIED`**

Decision:

`PROPRIETARY BARRACUDA TINA VENDOR ADAPTER/REFERENCE / OFFICIAL CLIENT REQUIRED FOR TINA INTEROP UNTIL A LEGITIMATE REUSABLE IMPLEMENTATION IS PROVEN / DO NOT SUBSTITUTE GENERIC IPSEC / PRESERVE TRANSPORT+AUTH+PROFILE+FAILOVER SEMANTICS / FIRST-PARTY CODE REFERENCE-ONLY / MODERN CRYPTO POLICY REQUIRED`

All 20 original research gates are reconciled with current authoritative Barracuda evidence or bounded evidence-backed N/A. Runtime testing remains separate.