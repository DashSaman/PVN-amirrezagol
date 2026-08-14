# 034 — Citrix Secure Access / NetScaler Gateway VPN — V1 Gate Reconciliation

Reviewed: 2026-08-14

Research completion only. No implementation, device, Store, live NetScaler interoperability, or production claim.

Canonical evidence is consolidated in `CITRIX_SECURE_ACCESS_CURRENT_AUDIT.md`.

| # | V1 research gate | Result | Evidence / bounded conclusion |
|---:|---|---|---|
| 1 | Entry identity and scope | PASS | Current Citrix Secure Access client family provides remote access for NetScaler Gateway and Citrix Secure Private Access; this entry keeps Gateway VPN vendor semantics distinct. |
| 2 | Standards / protocol documentation | PASS | NetScaler current docs define SSL/TLS full VPN and optional DTLS VPN transport. Generic TLS/DTLS standards do not substitute for Citrix Gateway policy/auth/client semantics. |
| 3 | Transports and ports | PASS | SSL VPN virtual server transport is TLS; optional DTLS VPN can share the SSL VPN virtual-server IP/port. If DTLS handshake fails, current NetScaler docs state fallback to TLS. Exact listening port is admin-configurable rather than hard-coded as a PVNetwork assumption. |
| 4 | Handshake / state model | PASS | Bounded flow: reach Gateway VPN vserver -> TLS/DTLS transport establishment -> configured authentication/nFactor/EPA -> session policy/routing acquisition -> tunnel operation -> reconnect/timeout lifecycle. Proprietary framing is not invented. |
| 5 | Authentication | PASS | Current NetScaler nFactor supports policy-composed authentication including LDAP, RADIUS, SAML, certificate, OAuth/OIDC, Kerberos and related mechanisms. Platform feature parity records nFactor/EPA differences. |
| 6 | Cryptography / security | PASS | Server certificate/key binds to VPN vserver; TLS/DTLS are transport security boundaries. DTLS 1.2 is documented with client/platform/build limitations. Exact cipher suites follow NetScaler SSL/DTLS profile and release policy; no client-default substitution. |
| 7 | Routing / DNS / address assignment | PASS | Current Gateway docs cover full tunnel, split tunnel ON, reverse split tunnel, intranet applications, FQDN rules and split DNS. Feature parity and Apple/Windows docs retain platform-specific DNS behavior and limitations. |
| 8 | Client implementations | PASS | Official Citrix Secure Access clients are documented for Windows, macOS, iOS, Android and Linux. Legacy Apple VPN client path is deprecated; current Apple path uses public Network Extension. |
| 9 | Server / peer implementations | PASS | NetScaler Gateway is the canonical classic VPN termination/control product; Citrix Secure Private Access is a separately documented supported deployment for applicable clients. No generic TLS server is called a Citrix Gateway. |
| 10 | Platform support / capabilities | PASS | Current requirements document Windows 10/11, selected Windows Server, macOS, Ubuntu 22.04/24.04, iOS and Android plus deployment-specific restrictions; feature parity records per-platform differences. |
| 11 | Install / setup / package behavior | PASS | Official install docs define first-party desktop/mobile installation and platform-specific setup. Mobile apps use platform stores; desktop lifecycle differs by OS. Exact package signatures remain later packaging certification. |
| 12 | UI / UX / menu / configuration mapping | PASS | V1 surfaces include Gateway/profile connection, auth/browser/nFactor, connect/disconnect, Always On where supported, routing/DNS modes, proxy/PAC/local-LAN where supported, EPA and diagnostics. Full screen inventory is v2. |
| 13 | Configuration / profile / import-export | PASS | Gateway session/profile policy and MDM-managed mobile/Apple profiles drive behavior; clients obtain protected-network/intranet application rules from Gateway. No unsupported portable generic profile format is invented. |
| 14 | Storage / credentials / secrets | PASS | Credentials/certificates/tokens are governed by platform client, browser/auth flow, OS/MDM and Gateway policy. Exact proprietary local storage internals are not public and are evidence-backed N/A for source-level claims. |
| 15 | Logging / diagnostics / telemetry | PASS | Current release notes explicitly discuss troubleshooting/log improvements and client defects; EPA and Gateway/client diagnostics are part of official product operation. Proprietary telemetry internals are not invented. |
| 16 | Reconnect / failover / lifecycle | PASS | Always On, idle/forced timeouts and reconnect/resume behaviors are documented with platform/version boundaries; current Windows notes include reconnect-after-sleep/session-timeout fixes. Live failover timing is later certification, not a hidden V1 gate. |
| 17 | Limitations / regressions | PASS | Current docs record platform feature gaps, EPA differences, Apple Network Extension constraints, DTLS limitations, DNS caveats and active release regressions/fixes. |
| 18 | Source / version / release pinning | PASS (bounded N/A for source commit) | Citrix document history provides current release pins, including Windows 26.6.1.20 (2026-07-14), iOS 26.05.2 (2026-07-01), Android 26.06.1 (2026-06-23), macOS 26.04.1.1 (2026-04-28). No canonical public complete-source repo is identified, so an invented source SHA is prohibited. |
| 19 | License / reuse / supply chain | PASS | Complete first-party client/Gateway code, binaries, UI assets and branding are proprietary/reference-only absent a component-specific public license. Download signatures/EULA/Store receipts belong to later packaging/legal certification. |
| 20 | Architecture / product decision | PASS | Keep a distinct Citrix/NetScaler vendor adapter/reference for Gateway auth, EPA, policy, routing/DNS and lifecycle. Reuse generic TLS/DTLS components only for standards-level layers, never as a compatibility claim. |

## V1 decision

**`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT NETSCALER-CERTIFIED`**

Decision:

`VENDOR SSL/TLS-DTLS REMOTE-ACCESS REFERENCE / NETSCALER-GATEWAY POLICY+AUTH+EPA+ROUTING SEMANTICS DISTINCT / FIRST-PARTY CLIENT REFERENCE-ONLY / PLATFORM-SPECIFIC ADAPTER REQUIRED / NO GENERIC TLS COMPATIBILITY CLAIM / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`

All 20 original research gates are reconciled with current authoritative Citrix/NetScaler documentation or bounded evidence-backed N/A. Runtime tests and vendor certification remain separate.