# 033 — HPE Aruba Networking VIA — V1 Gate Reconciliation

Reviewed: 2026-08-14

Research completion only. No implementation, vendor certification, live interoperability, Store, or production claim is made.

Primary evidence is consolidated in `ARUBA_VIA_CURRENT_AUDIT.md` and the canonical HPE Aruba Networking documentation linked there. Shared entries 004–007 are reused only for standards-level IKE/IPsec semantics that exactly apply.

| # | V1 research gate | Result | Evidence / bounded conclusion |
|---:|---|---|---|
| 1 | Entry identity and scope | PASS | VIA is HPE Aruba Networking Virtual Intranet Access, a vendor remote-access VPN solution for remote/mobile users; distinct vendor provisioning and policy semantics are preserved. |
| 2 | Standards / protocol documentation | PASS | HPE current docs identify IPsec/IKEv1/IKEv2 and hybrid IPsec/SSL behavior. Standards mechanics may reuse entries 004–007; VIA-specific behavior remains HPE-defined. |
| 3 | Transports and ports | PASS | Current HPE prerequisites document UDP 4500 for VPN, ESP/IP protocol 50 under normal IPsec operation, TCP 443 for HTTPS functions and supported SSL fallback carrying ESP over TCP 443; profile/certificate download may use a configured HTTPS port. |
| 4 | Handshake / state model | PASS | Product flow is bounded as server/profile discovery and authentication -> role/profile download -> trusted/untrusted decision -> IKE/IPsec establishment (or supported fallback) -> routing/policy application -> lifecycle/reconnect. Generic IPsec details are not used to invent Aruba messages. |
| 5 | Authentication | PASS | Current HPE connection-profile docs expose IKEv2 `user-cert`, `EAP-TLS`, `EAP-MSCHAPv2`; IKEv2 PSK is explicitly unsupported for VIA. IKEv1 can use PSK/cert policy. Authentication server groups may include RADIUS/LDAP. |
| 6 | Cryptography / security | PASS | Security boundary is IKE/IPsec plus TLS/HTTPS provisioning/fallback surfaces. Server certificates are required for HTTPS/VPN; CA trust is required for certificate client auth; server-cert validation is default-on; OCSP controls and Suite B/FIPS options are documented. Exact algorithm policy remains version/configuration dependent. |
| 7 | Routing / DNS / address assignment | PASS | HPE docs define VPN IP pools, full/split tunnel, tunneled-network selectors, DNS suffixes, destination blocking, block-until-tunnel-up, allow-list rules, netmask and MTU. Platform-specific DNS semantics are retained as a later certification concern. |
| 8 | Client implementations | PASS | Official VIA clients are documented for Windows, macOS, Linux, Android and iOS. The official feature matrix records capability differences across all five. No unsupported public third-party VIA client is invented. |
| 9 | Server / peer implementations | PASS | AOS10/Central documentation identifies VPNC/gateway configuration; ArubaOS 8.x documentation identifies Mobility Conductor/managed device or standalone controller. Generic IPsec servers are not called VIA servers. |
| 10 | Platform support / capabilities | PASS | Current feature matrix covers Windows/macOS/Linux/Android/iOS and records per-platform differences for split tunnel, failover, auto-upgrade, logs, IPv6, lockdown, CLI and more. Exact release/CPU/store certification remains separate. |
| 11 | Install / setup / package behavior | PASS | HPE documents support-site/store distribution, VPNC/external installer hosting, OS-sensitive installer presentation, profile-controlled auto-upgrade, platform prerequisites, install/uninstall and profile acquisition. |
| 12 | UI / UX / menu / configuration mapping | PASS | v1 surfaces include VPN server list/add/edit/delete, profile download, connect/disconnect, authentication/profile choice where provisioned, logs/diagnostics, support export, auto-login, lockdown and admin-side Security > L3 Authentication > VIA Connection / role attachment. Full screen inventory is v2. |
| 13 | Configuration / profile / import-export | PASS | VIA connection profile is the authoritative policy bundle downloaded after authentication and attached to a user role. It carries servers, auth, tunnel selectors, certificates, DNS, MTU, logging, upgrade and lifecycle settings. No generic IPsec import format is falsely equated with VIA provisioning. |
| 14 | Storage / credentials / secrets | PASS | HPE docs expose policy-controlled password saving and state that successful credentials are stored securely until uninstall or stored IKE auth failure; certificate trust/store behavior is documented. Exact OS key-store implementation is proprietary/platform-specific and not invented. |
| 15 | Logging / diagnostics / telemetry | PASS | Client logging can be enabled; logs can be sent to support email; lockdown retains ping/traceroute diagnostics. Central remote-client views provide status/traffic/event observability. Exact telemetry internals are proprietary/N-A for source audit. |
| 16 | Reconnect / failover / lifecycle | PASS | Auto-login, trusted-network detection, maximum reconnect attempts, max-session timeout, idle timeout, multiple VIA servers, controller selection/load-balance, auto-upgrade and profile update behavior are documented. Live failover performance is not a hidden research gate. |
| 17 | Limitations / regressions | PASS | Platform feature differences, legacy OS support caveats, conflict risk with other VPN products, Windows driver downgrade constraint in VIA 4.6 and product/version-specific behavior are explicit. No universal capability claim is made. |
| 18 | Source / version / release pinning | PASS (bounded N/A for source commit) | Current HPE Help Center is canonical and lists release-note families through VIA 4.7.6; detailed vendor release notes are retained. Complete VIA source is not published as a canonical reusable repository, so an invented source SHA is prohibited and source-commit pin is evidence-backed N/A. |
| 19 | License / reuse / supply chain | PASS | Complete first-party VIA client/gateway is treated proprietary/reference-only. HPE third-party notices do not grant reuse of the whole product. Store/support binaries, exact signatures/hashes and vendor EULA review remain packaging/legal certification tasks, not hidden V1 gates. |
| 20 | Architecture / product decision | PASS | PVNetwork should keep a distinct `ArubaVIA` vendor adapter/reference boundary around provisioning, auth, policy and lifecycle; standards-level IKE/IPsec components may be reused only when compatible. Do not claim generic IPsec equals VIA compatibility. |

## V1 decision

**`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT VENDOR-CERTIFIED`**

Architecture decision:

`VENDOR REMOTE-ACCESS REFERENCE / HPE FIRST-PARTY CLIENT REFERENCE-ONLY / STANDARD IKE-IPSEC REUSE ONLY FOR EXACT LAYERS / ARUBA PROFILE+ROLE+AUTH+ROUTING+LIFECYCLE REMAIN DISTINCT / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`

All 20 original research gates are reconciled with canonical vendor evidence or an evidence-backed bounded N/A. Runtime device tests, packet captures, vendor certification, current Store receipts and binary signatures remain later implementation/certification evidence and are not prerequisites for research completion.