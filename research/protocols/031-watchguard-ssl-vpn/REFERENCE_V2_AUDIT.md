# 031 — WatchGuard SSL VPN — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: reference/research completion only. This does not claim live Firebox/OpenVPN interoperability, production support, Store/device certification, or rights to redistribute proprietary WatchGuard code/assets.

## Current product boundary

WatchGuard **Mobile VPN with SSL** is a Fireware remote-access service with two client paths:

- proprietary WatchGuard Mobile VPN with SSL client for Windows/macOS;
- standards-compatible `client.ovpn` for OpenVPN clients, including WatchGuard-documented OpenVPN Connect use on Android/iOS.

Current Fireware Help applies through the maintained v2026.3/v2026.2.1/v12.12.1 lines. Fireware and WatchGuard first-party client remain proprietary/reference-only.

Important lifecycle/capability boundaries:

- Fireware 12.11+ removed client-app download from the Firebox; client software comes from WatchGuard Software Downloads/WatchGuard Cloud, while `.ovpn` profile remains downloadable from Fireware UI;
- Fireware 12.11+ Windows first-party client supports SAML; macOS first-party client supports SAML from 12.11.2+;
- third-party OpenVPN clients are **not supported for WatchGuard SAML integration**;
- current SSL VPN clients require TLS 1.2 or higher;
- current Software Downloads portal is authoritative for exact first-party package identity; no fake latest installer/hash is generated.

Canonical vendor evidence:

- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/ssl/mvpn_ssl_about_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/content/en-US/Fireware/mvpn/ssl/mvpn_ssl_ovpn_profile_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/ssl/mvpn_ssl_client-install_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/content/en-us/Fireware/authentication/sso_saml_config.html
- https://www.watchguard.com/help/docs/help-center/en-us/content/en-us/Fireware/support/release_news_c.html

## OpenVPN source boundary

OpenVPN-compatible wire/profile behavior reuses completed entry 001 and `research/upstreams/openvpn-family/`.

Current repo OpenVPN 3 pin:

- upstream: `OpenVPN/openvpn3`
- release tag: **`release/3.11.7`**
- exact release commit: **`18edfae7e7fd8051c93bd4746ec69be91eb02dbb`**
- detailed source-analysis SHA: `1fd271caefc9a71406afdc2ff2460999dcfdb234`
- license: upstream dual choice **AGPL-3.0-only or MPL-2.0**, with documented OpenSSL permission for the AGPL path.

This open-source core does not imply first-party SAML/browser integration or WatchGuard client UI parity.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server is proprietary Firebox/Fireware Mobile VPN with SSL. Client ecosystem is proprietary WatchGuard Windows/macOS plus standards OpenVPN-compatible clients through `client.ovpn`. OpenVPN 3 is the reusable standards/source reference; no separate WatchGuard cryptographic engine is invented.

2. **Official/community installers/deployment projects — PASS.** Server deployment reuses the current physical Firebox, FireboxV VMware/Hyper-V/KVM/Proxmox and FireboxCloud ecosystem from entry 030. First-party clients are delivered as `WG-MVPN-SSL.exe` and `WG-MVPN-SSL.dmg` from authenticated WatchGuard channels. `client.ovpn` allows documented OpenVPN client deployment. Generic Docker/Helm/Kubernetes Fireware server deployment is not claimed.

3. **Server OS/container/orchestration install matrix — PASS.** Fireware is the proprietary server OS boundary. FireboxV/server deployment matrix remains vendor-controlled and platform-specific; generic Linux packages/containers are N/A without evidence. OpenVPN server software on arbitrary Linux is not relabeled as WatchGuard Fireware.

4. **Server panel/UI/menu maps — PASS.** Current Fireware Web UI/Policy Manager maps to `VPN > Mobile VPN` -> SSL section, wizard/manual configuration, server/port/protocol, auth servers/domains/users/groups, routed vs bridged traffic, client pool, DNS/name resolution, full/split Internet access, reconnect/data-channel/tunnel-security settings, Download Profile and client/log controls. `SSLVPN-Users` group/policies remain separate authorization. WatchGuard Cloud has the corresponding Mobile VPN device configuration surface.

5. **Client install matrix — PASS.** Proprietary first-party client is current on supported Windows and macOS versions per Fireware Release Notes. Android/iOS have official WatchGuard instructions for OpenVPN Connect via `client.ovpn`. Other OpenVPN-capable platforms require exact profile/backend compatibility and are not promoted to official WatchGuard-supported OS rows without evidence. Current Windows/macOS exact package hashes remain release-freeze data from Software Downloads.

6. **Major client UI/menu maps — PASS.** First-party client exposes Connect/Disconnect, Status, View Logs, Properties, About, Exit/Quit, and platform-specific connection/status options; server/auth domain, username/password, optional SAML, Remember Password and Auto reconnect are capability-controlled. Generic OpenVPN clients keep their own UI and do not inherit WatchGuard branding or SAML controls.

7. **Cryptographic design/security boundary — PASS.** Mobile VPN with SSL uses TLS/OpenVPN-compatible transport. Current Fireware minimum accepted TLS is TLS 1.2. Current configuration supports authentication hash choices and AES/AES-GCM cipher families under vendor controls; exact generated `client.ovpn` remains version-specific. First-party SAML/browser flow is authentication-plane functionality outside OpenVPN cryptographic semantics. No obsolete TLS downgrade or undocumented cipher is invented.

8. **Data path/wire flow — PASS.** Reference flow: Fireware SSL VPN config + auth/user policy -> first-party config or `client.ovpn` distribution -> TLS/OpenVPN negotiation -> local/RADIUS/AuthPoint/SAML authentication as applicable -> assigned virtual IP/routes/DNS -> Firebox `SSLVPN-Users`/firewall policy -> protected resources/optional Internet tunnel. SAML is first-party-client-only; OpenVPN-profile interoperability is a separate path.

9. **Ports/transports/handshake — PASS.** Default Mobile VPN with SSL service/data path is **TCP 443**, configurable. Fireware can use TCP or UDP data channel; current documented examples include UDP 53 as a performance-oriented option. If UDP is selected, the configuration channel can use TCP or UDP and may use a distinct port. TLS minimum is 1.2. Portal/configuration channel and VPN data-channel roles remain separate when configured separately.

10. **Deployment topologies — PASS.** Physical/virtual/cloud Firebox remote-access termination is supported. Routed VPN traffic is required for generic OpenVPN client usage; Fireware also exposes routed vs bridged modes for first-party architecture. Full tunnel/default-route and direct-Internet split behaviors are explicit policies. HA/FireCluster applicability remains platform-specific. This entry is remote access and is not generic OpenVPN server deployment.

11. **Source/release/license/activity pins — PASS.** Fireware and first-party WatchGuard client are proprietary. Current Fireware release activity is pinned to v2026.3/v2026.2.1/v12.12.1 maintained lines; earlier release history explicitly identifies Windows client 12.11.5 and macOS 12.11.2 milestones, while current exact Software Downloads package identity remains a package-freeze requirement rather than a fabricated 'latest'. OpenVPN 3 is separately pinned to `release/3.11.7` commit `18edfae7e7fd8051c93bd4746ec69be91eb02dbb`, dual AGPL-3.0-only/MPL-2.0.

12. **Security/supply-chain risks — PASS.** Use authenticated WatchGuard Software Downloads/Cloud channels and current advisories. Validate server certificate; current macOS 13+ rejects untrusted self-signed SSL endpoints. Protect `.ovpn`, `.wgssl`, passwords, OTP/MFA state and SAML cookies/tokens separately. Third-party OpenVPN clients must not be advertised as SAML-capable. Exact OpenVPN dependency/SBOM review and exact WatchGuard installer signature/hash belong to build/release freeze.

13. **Upgrade/uninstall/rollback — PASS.** Fireware 12.11+ changed client distribution/update behavior; first-party clients no longer use the Firebox download page/update prompt. Windows upgrades require admin rights and major version mismatch can block connection; v12.11.4+ installer can close a running client during update. macOS install/update has system-extension/certificate considerations. Profile/config download/update is separate from app package lifecycle, and stale-config fallback must be visible. OpenVPN third-party lifecycle remains independent.

14. **Differences/uncertainties — PASS.** `client.ovpn` compatibility does not equal first-party WatchGuard feature parity. SAML is first-party Windows/macOS only under documented versions; AuthPoint reconnect behavior can differ; routed/bridged modes and exact generated directives vary with Fireware. Current exact first-party installer version/hash requires Software Downloads freeze. Live OpenVPN3 directive compatibility, session reconnect and HA behavior remain later certification tasks, not hidden V2 gates.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `README.md`, `WATCHGUARD_SSL_CURRENT_AUDIT.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, this audit, entry 001 and `research/upstreams/openvpn-family/`. Reuse decision: `OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST FOR STANDARD PROFILE+WIRE SEMANTICS / WATCHGUARD FIRST-PARTY SAML+VENDOR FEATURES SEPARATE`. Proprietary WatchGuard code/assets remain reference-only.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 031 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 31/93 and continue at **032 — WatchGuard L2TP VPN**. No runtime/device/Store/live-interoperability receipt is added as an unstated gate.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by proprietary/package/platform uncertainty. OpenVPN compatibility, first-party SAML boundaries, current Fireware lifecycle and transport/security behavior are explicit.

Decision: **COMPLETE-REFERENCE-v2**.
