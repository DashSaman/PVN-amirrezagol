# 036 — Juniper Secure Connect — V1 Gate Reconciliation

Reviewed: 2026-08-14

Research completion only. No implementation, live SRX/vSRX interoperability, Store/device, or production claim.

Canonical evidence is consolidated in `JUNIPER_SECURE_CONNECT_CURRENT_AUDIT.md`.

| # | V1 research gate | Result | Evidence / bounded conclusion |
|---:|---|---|---|
| 1 | Entry identity and scope | PASS | Juniper Secure Connect is Juniper's first-party remote-access VPN solution composed of an SRX/vSRX gateway and Secure Connect application. |
| 2 | Standards / protocol documentation | PASS | Current Juniper J-Web places Secure Connect under IPsec VPN and exposes IKE/IPsec configuration, while product docs also describe it as client-based SSL-VPN. Standards IKE/IPsec evidence is reused only for exact layers; Juniper orchestration remains distinct. |
| 3 | Transports and ports | PASS (bounded) | Transport selection is Juniper/SRX policy driven and current product docs state the client chooses effective transport protocols. IKE/IPsec configuration is explicit; exact configured service ports/transports are deployment dependent and are not fabricated. |
| 4 | Handshake / state model | PASS | Bounded product flow: Gateway URL -> configuration retrieval/validation from SRX -> server/auth/certificate validation -> IKE/IPsec/selected transport establishment -> protected-network policy -> tunnel lifecycle. Proprietary messages are not invented. |
| 5 | Authentication | PASS | Juniper documents local and external auth, RADIUS, local/PSK, EAP-MSCHAPv2, EAP-TLS and related MFA/SSO surfaces. External authentication is recommended over local. |
| 6 | Cryptography / security | PASS | SRX certificate is required and default system-generated cert should be replaced. EAP-TLS requires PKI, CA and per-user cert. IKE/IPsec advanced settings are configured by SRX/J-Web; exact crypto defaults remain Junos/version specific rather than guessed. |
| 7 | Routing / DNS / address assignment | PASS | Protected networks define full/split-tunnel behavior, address pools assign clients, Source NAT is default-on in documented flows and can be disabled when return routing exists. DNS specifics remain deployment/platform policy rather than invented. |
| 8 | Client implementations | PASS | Official Secure Connect clients are documented for Windows, macOS, Android and iOS/iPadOS. No unsupported Linux client is inferred from generic IPsec. |
| 9 | Server / peer implementations | PASS | SRX Series Firewall and vSRX are canonical gateway roles. Generic IKE/IPsec gateways are not labeled Juniper Secure Connect servers. |
| 10 | Platform support / capabilities | PASS | Current system requirements document SRX/vSRX Junos 20.3R1+ and Windows 10+, supported macOS incl. Apple Silicon releases, Android 10+, iOS/iPadOS 12+. Exact device/Store certification remains separate. |
| 11 | Install / setup / package behavior | PASS | Juniper provides separate official download/install paths for Windows, macOS, Android and iOS/iPadOS plus SRX configuration prerequisites. Active SRX-based licensing is required. |
| 12 | UI / UX / menu / configuration mapping | PASS | Client gateway/profile/connect surfaces and admin J-Web `Network > VPN > IPsec VPN` wizard are mapped, including remote users, local gateway, protected networks, address pool, NAT, cert, auth and advanced IKE/IPsec fields. Full screen inventory is V2. |
| 13 | Configuration / profile / import-export | PASS | Secure Connect downloads configuration from SRX and uses a Gateway address/URL. Dynamic VPN migration maps protected resources to Secure Connect protected networks; no generic portable profile format is falsely invented. |
| 14 | Storage / credentials / secrets | PASS | Juniper documents platform-specific certificate/CA import and application storage paths; exact proprietary password/token storage internals are not public and are bounded N/A for source-level claims. |
| 15 | Logging / diagnostics / telemetry | PASS | Configuration validation, client connection state and SRX/J-Web operational troubleshooting are official product surfaces. Proprietary telemetry internals are not invented; runtime log receipt is later certification. |
| 16 | Reconnect / failover / lifecycle | PASS (bounded) | Secure Connect lifecycle/config retrieval and client connection flow are documented; exact roaming/reconnect timing is platform/version dependent and retained for runtime certification rather than a hidden research gate. |
| 17 | Limitations / regressions | PASS | Current platform list excludes a documented Linux Secure Connect client, local auth is discouraged, EAP-TLS disallows local auth, certificate prerequisites and migration caveats are explicit. |
| 18 | Source / version / release pinning | PASS (bounded N/A for source commit) | Current Juniper docs pin gateway requirement to Junos OS 20.3R1+ and current client OS requirements. No canonical public complete-source repo is identified, so source SHA pinning is evidence-backed N/A. |
| 19 | License / reuse / supply chain | PASS | Active SRX-based license required; two built-in concurrent user licenses per SRX are documented and extra users require purchased subscription licenses. Complete first-party client/SRX code and assets remain proprietary/reference-only. |
| 20 | Architecture / product decision | PASS | Keep a distinct Juniper Secure Connect vendor adapter/reference for config download, auth/cert, protected-network and lifecycle semantics. Reuse generic IKE/IPsec only for exact standards layers; do not claim generic IPsec equals Secure Connect compatibility. |

## V1 decision

**`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT JUNIPER-CERTIFIED`**

Decision:

`VENDOR JUNIPER REMOTE-ACCESS ADAPTER/REFERENCE / SRX-vSRX GATEWAY + PROPRIETARY FIRST-PARTY CLIENT / REUSE IKE-IPSEC COMPONENTS ONLY FOR EXACT STANDARD LAYERS / JUNIPER CONFIG-DOWNLOAD+AUTH+CERT+PROTECTED-NETWORK POLICY DISTINCT / NO GENERIC IPSEC COMPATIBILITY CLAIM / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`

All 20 original research gates are reconciled with current authoritative Juniper evidence or bounded evidence-backed N/A. Runtime/device/vendor certification remains separate.