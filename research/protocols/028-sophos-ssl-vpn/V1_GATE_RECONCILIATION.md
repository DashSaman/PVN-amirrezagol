# 028 — Sophos SSL VPN — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original 20-gate research contract only. This is not PVNetwork implementation, Sophos interoperability certification or production support.

Primary entry audit: `SOPHOS_SSL_VPN_CURRENT_AUDIT.md`.

Shared reusable-engine evidence: `research/upstreams/openvpn-family/`.

## 1. Top clients / implementations — PASS

Top references are separated by role:

1. Sophos Connect current Windows/macOS product — authoritative proprietary client behavior reference.
2. Sophos Firewall remote-access SSL VPN — authoritative server/policy/provisioning reference.
3. OpenVPN-family clients — officially supported interoperability path for `.ovpn`, especially Linux/mobile and other supported endpoint cases.
4. OpenVPN 3 Core — PVNetwork's primary existing public reusable client-core candidate where generated profile/auth semantics are supported.

A separate Sophos-specific tunnel core is unnecessary and not selected.

## 2. Canonical sources pinned — PASS (`SOPHOS-PROPRIETARY-N/A`)

Sophos Connect and Sophos Firewall source are proprietary. Current product/release references are versioned to:

- Sophos Connect 2.5 MR1 Windows, released 2026-06-18;
- Sophos Connect 2.0 MR1 macOS, released 2026-05-21;
- current Sophos Firewall 22.0 release family, with MR2 current in July 2026.

The reusable OpenVPN3 source is already pinned and audited in `research/upstreams/openvpn-family/`.

## 3. License / legal reuse — PASS

Sophos application/server code, UI and assets are proprietary/reference-only.

OpenVPN3 Core's dual-license model and dependency/platform obligations are already documented in the shared OpenVPN family. PVNetwork's preferred direction is the legally reviewed reusable-core path rather than copying a GUI/product client.

Third-party OpenVPN clients remain implementation/UX references unless separately approved.

## 4. Complete source-tree reference / manifest — PASS (`PUBLIC-CORE`; `SOPHOS-N/A`)

The public reusable OpenVPN3/OpenVPN family source tree/revisions are captured in the shared dossier.

Sophos private source trees are N/A and are not fabricated. Official release notes disclose some bundled components in Sophos Connect, including OpenVPN/OpenSSL on current macOS releases, but that does not expose Sophos application source.

## 5. Languages / build systems — PASS

OpenVPN3 public core language/build/dependency evidence is mapped in the OpenVPN dossier.

Sophos private implementation languages/build internals are unavailable; current Windows MSI/macOS package lifecycle and exposed third-party component versions are recorded at the product level without guessing private internals.

## 6. Architecture — PASS

The architecture is mapped as:

`Sophos Firewall SSL VPN policy / VPN portal`

`-> generated .ovpn OR Sophos .pro provisioning`

`-> Sophos Connect or OpenVPN-compatible client`

`-> OpenVPN SSL VPN engine/service`

`-> TLS/authentication`

`-> virtual interface`

`-> pushed routes/DNS/network policy`

`-> authorized remote resources`

Sophos Connect's IPsec path/`charon.log` is separate and belongs primarily to entry 029.

## 7. Core / engine integration — PASS

PVNetwork decision: use a product-owned OpenVPN Adapter around the already-selected OpenVPN core where the Sophos-generated profile validates against exact core/platform capabilities.

Sophos `.pro` policy provisioning, Sophos-specific SSO metadata and policy update belong outside the OpenVPN wire engine.

Unsupported Sophos/OpenVPN directives are reported, never silently discarded. No new SSL VPN cryptography is implemented.

## 8. UI / menu map — PASS

Current Sophos Connect user guide maps:

- Connections page;
- Import connection;
- Connect / sign-in;
- settings icon per connection;
- Auto-connect;
- Delete;
- Rename;
- Clear credentials;
- Update policy where provisioning supports it;
- Events tab;
- menu -> Open VPN log;
- About -> Generate technical support report;
- Force SSO re-login on supported SSO workflows.

Official screenshots are behavioral/navigation reference only and are not copied.

## 9. Configuration / import / export — PASS

Sophos SSL VPN uses `.ovpn` as the portable client configuration. Current official docs explicitly allow it for Sophos Connect and OpenVPN clients.

Sophos `.pro` is a separate provisioning artifact that can fetch/update current policy on supported Sophos Connect/Windows combinations.

PVNetwork retains original imported source, normalizes into a product-owned canonical OpenVPN profile, preserves unsupported directives and keeps provisioning metadata separate.

## 10. Persistence / secure storage — PASS

The research separates:

- `.ovpn` source/profile metadata;
- `.pro` provisioning metadata;
- reusable credentials;
- transient OTP;
- SSO/browser tokens;
- certificate/private-key references;
- transient OpenVPN session state;
- routes/DNS/runtime network state;
- diagnostics.

Sophos Connect exposes Clear credentials and current release notes include SSL-VPN credential-persistence fixes on macOS. PVNetwork must use platform secure storage and independent secret classes.

## 11. Platform integrations — PASS

Current platform evidence is explicit:

- Windows 10/11, including current Windows ARM support in 2.5+;
- macOS 13+ current Sophos Connect SSL VPN support beginning with 2.0 in 2026;
- Linux uses OpenVPN-compatible clients rather than Sophos Connect SSL VPN;
- mobile platforms use OpenVPN-compatible clients, not Sophos Connect.

Platform support remains exact-version dependent; older 21.0 documentation that predated macOS SSL support is not treated as current truth.

## 12. Logs / diagnostics — PASS

Current Sophos Connect support bundle/log model is mapped:

- `openvpn.log` for SSL/OpenVPN connection/interface/packet/error detail;
- `scvpn.log` for VPN event lifecycle;
- `scgui.log` for client/OpenVPN/WebView2 and SSO detail;
- `configs.txt` for imported configuration summaries;
- route/IP/system information;
- Events page;
- technical support ZIP.

`charon.log` is separately identified as the IPsec engine domain, preventing SSL/IPsec diagnostic conflation.

PVNetwork requires sanitization/redaction and typed failure stages.

## 13. Images / assets / visual references — PASS

Current Sophos documentation contains client tray/dashboard/import/sign-in/screenshots and portal/admin screenshots.

Sophos branding/icons/screenshots are proprietary/reference-only. PVNetwork uses owner-supplied branding and an independent bilingual/RTL-capable UI.

OpenVPN reference-client assets are governed by their own licenses and are not copied blindly.

## 14. Meaningful forks / ecosystem — PASS

The meaningful implementation ecosystem is the OpenVPN family, which is already audited across OpenVPN3 Core, OpenVPN Connect behavior, OpenVPN GUI, Android references and Tunnelblick.

No Sophos-only public tunnel core needs to be selected because Sophos officially provides OpenVPN-compatible `.ovpn` profiles.

Sophos Connect itself is the proprietary first-party behavioral reference.

## 15. Important issues / PRs / releases / advisories — PASS

Current Sophos Connect release notes and known-issues list were reviewed. High-value regression/security cases include:

- 2.5 MR1 SSO + provisioning + special-character certificate fix;
- reconnect OTP behavior consistency fix;
- macOS 2.0 MR1 SSL VPN DNS fix;
- macOS credential-save restoration after profile re-import;
- current non-ASCII username limitation;
- older BF-CBC configurations rejected by newer client versions;
- historical Sophos Connect macOS local-log exposure vulnerability in older release history.

These become future profile/auth/DNS/Unicode/crypto/diagnostic regression tests.

## 16. Relevant official docs / community lessons — PASS

Primary research uses current Sophos Firewall 21.5/22.0 help, Sophos Connect release/user/troubleshooting documentation, VPN portal docs, MFA docs and official known-issues data.

Generic OpenVPN behavior/source evidence comes from the already-audited upstream family rather than third-party guesses.

## 17. Tests / CI — PASS

Sophos internal source CI/tests are proprietary/N-A and are not invented.

OpenVPN3/OpenVPN family public test/dependency/security evidence is captured in the shared dossier.

A future Sophos SSL certification test pyramid is explicitly defined: parser/capability -> OpenVPN adapter -> auth/MFA/SSO -> exact Sophos Firewall lab -> routes/DNS/full-vs-split -> reconnect/policy-update -> platform/install/coexistence -> leak/performance/security.

## 18. Store / privacy / security implications — PASS

Research covers:

- OpenVPN profile sensitivity and inline secrets;
- password/OTP/SSO/private-key/provisioning-secret separation;
- certificate validation;
- legacy/obsolete cipher rejection rather than silent downgrade;
- `.pro` remote policy update/trust boundary;
- full/split route and DNS leakage;
- diagnostic ZIP/log privacy;
- Windows/macOS package lifecycle and VPN-client coexistence warning;
- mobile/Linux third-party OpenVPN client choice and Store/platform differences;
- OpenVPN3 license/dependency obligations.

## 19. PVNetwork reuse decision — PASS

Decision:

`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST WHEN PROFILE+AUTH CAPABILITIES MATCH / SOPHOS PROVISIONING+SSO SEPARATE`

No dedicated Sophos SSL cryptographic engine is required.

Sophos `.ovpn` enters the existing OpenVPN adapter after validation. Optional `.pro` support is a separate vendor provisioning service. Exact Sophos Firewall compatibility is certified per version/profile/auth combination.

## 20. Uncertainties explicitly listed — PASS

Bounded later-stage uncertainties:

- exact SFOS/UTM SSL VPN server versions/deployments;
- all generated `.ovpn` directive variants;
- selected OpenVPN3 compatibility with those profiles per platform;
- exact TLS/data-channel cipher/security floor;
- `.pro` schema/network/trust/update semantics if implemented;
- Entra SSO and MFA combinations;
- current client installer hashes/signatures;
- server/admin full menu and deployment topology;
- platform route/DNS/full-vs-split behavior;
- real packet/interoperability/reconnect/leak tests;
- advisories/releases after this research date.

These are v2/implementation/certification questions rather than missing original v1 research categories.

# Formal result

All 20 original-v1 research gates are evidence-backed, evidence-backed proprietary N/A, or bounded with explicit later-stage uncertainty.

**Entry 028 may be promoted to `COMPLETE-RESEARCH-v1`.**

Research completion only; no implementation/vendor/production claim.