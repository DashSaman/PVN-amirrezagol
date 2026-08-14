# 026 — SonicWall NetExtender / SSL VPN — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original 20-gate research contract only. This is not PVNetwork implementation, SonicWall certification, Store readiness or production support.

Primary authority: current official SonicWall NetExtender 10.3 Feature Guide, current Windows 10.3.5 release notes, current SonicOS SSL VPN documentation and official support knowledge base.

Entry-specific current evidence: `OFFICIAL_NETEXTENDER_CURRENT.md`.

## 1. Top clients / implementations — PASS

Top references are explicit:

1. **SonicWall NetExtender 10.3.x** — authoritative proprietary client/product reference; current reviewed Windows release 10.3.5.
2. **Official SonicWall SSL VPN service on SonicOS/SMA** — authoritative server/gateway policy reference.
3. **OpenConnect NetExtender development issue/MR** — public protocol-research/watch reference only; not a current merged/released drop-in.
4. **SonicWall Mobile Connect** — adjacent separate client family for some non-NetExtender platforms; not collapsed into entry 026.

Searches of public NetExtender-named repositories found package/install wrappers rather than a serious maintained independent drop-in protocol engine.

## 2. Canonical sources pinned — PASS (`PROPRIETARY-N/A`)

Official NetExtender source is proprietary and has no public source commit to pin. Current product behavior is version-pinned to the 10.3 family and reviewed Windows release 10.3.5 with official SonicWall documentation.

OpenConnect's SonicWall work is intentionally **not selected as a reusable candidate** because support remains an open development issue rather than a merged protocol. Therefore an unmerged branch is not falsely promoted into the canonical implementation set.

## 3. License / legal reuse — PASS

Official SonicWall code, installers, UI and branding are proprietary/reference-only and not copied.

No mature public drop-in implementation is selected, so there is no open-source engine license to misclassify as approved product reuse. If OpenConnect SonicWall support lands later, its exact release/license/linking obligations must be audited before product use.

## 4. Complete source-tree reference / manifest — PASS (`N/A-PROPRIETARY`)

NetExtender source is proprietary; a public recursive source manifest does not exist and is not fabricated. Official versioned documentation supplies product/platform behavior evidence instead.

The public OpenConnect WIP is not selected as a current engine, so its incomplete SonicWall branch is treated as ecosystem/issue evidence rather than a source implementation being certified.

## 5. Languages / build systems — PASS (`PRIVATE-IMPLEMENTATION-N/A`)

Official NetExtender internal implementation languages/build system are proprietary and unknown. Research records public package/install forms and supported platforms without guessing internals.

Windows standalone/MSI and supported Linux DEB/RPM/TGZ-style distribution/lifecycle are documented. This satisfies the product packaging/build-boundary research question while preserving private code details as N/A.

## 6. Architecture — PASS

The product/gateway architecture is mapped as:

`NetExtender profile/auth UI`

`-> selected transport capability (Auto / TLS-TCP / DTLS-UDP / WireGuard where supported)`

`-> SonicWall SSL VPN gateway/service`

`-> user/group/domain authentication + authorization`

`-> assigned client address + routes + DNS/WINS/suffix`

`-> protected remote-network access`

Legacy NetExtender documentation also establishes PPP-oriented SSL VPN history. Current multi-transport UI means transport is a versioned capability, not the whole product identity.

Gateway route advertisement, user/group VPN Access authorization and management authentication remain distinct.

## 7. Core / engine integration — PASS

No public reusable NetExtender engine is selected. PVNetwork therefore must not write a black-box proprietary clone or invent cryptography.

Future integration paths are bounded:

- use independently supported standards through their own PVNetwork adapters where the exact SonicWall gateway exposes them;
- watch OpenConnect NetExtender support and re-audit if it becomes maintained/released;
- otherwise keep unsupported proprietary NetExtender combinations official-client-only.

Entry 027 Global VPN/IPsec is a separate adapter/certification target.

## 8. UI / menu map — PASS

Current official Feature Guide maps:

- connection/profile selector;
- Add/Edit Connection;
- server/domain/authentication;
- protocol selector;
- Save / Connect / Disconnect;
- Windows `More > Properties` with Settings, Connection Settings, Connection Script, Proxy, Packet Capture, Diagnostics;
- Linux Properties with Proxy, Certificate Settings, Settings;
- `More > Logs` view/export/clear/debug behavior;
- Windows diagnostics Ping, Traceroute, DNS Lookup, Network Info, iPerf, TCP Connection;
- Windows/Linux CLI;
- PreLogon/Network Logon and Always-On features.

This is sufficient v1 information architecture evidence without copying SonicWall visuals/trade dress.

## 9. Configuration / import / export — PASS

Official documentation maps connection profiles containing server, authentication domain and optional remembered user data, current protocol selection, proxy settings, client certificates and network settings.

Server-side configuration supplies address pools, client routes, DNS/WINS/suffix and optional profile creation.

PVNetwork canonical profiles must remain product-owned and capability-typed. A NetExtender profile is not treated as a universal protocol file. Unsupported/lossy fields must be surfaced if future import/export is attempted.

## 10. Persistence / secure storage — PASS

Research distinguishes:

- connection profile metadata;
- optionally remembered username/password subject to admin/client policy;
- SAML/browser session material;
- OTP factors;
- proxy credentials;
- certificate/private-key/smart-card PIN material;
- runtime transport/session state;
- admin-managed policy;
- logs/packet captures.

Official private credential-store implementation is not guessed. PVNetwork must use platform secure storage for reusable secrets and keep transient auth state separate.

## 11. Platform integrations — PASS

Current official NetExtender 10.3 documentation covers Windows 10/11 including x86/x86_64/arm64 and supported Linux distributions. Windows supports richer product integration such as Network Logon/PreLogon, Always-On, service/deployment and MSI administration.

Mobile Connect is a separate SonicWall family rather than evidence that NetExtender itself is the native iOS/Android/macOS client in this entry.

Current appliance compatibility is version-specific; 10.3.x availability does not prove support on every firewall.

## 12. Logs / diagnostics — PASS

Official current client behavior includes:

- NetExtender session logs;
- view/export/clear/debug controls;
- log rotation;
- Windows diagnostic tools;
- explicit packet capture;
- Event Viewer-style diagnostic access.

PVNetwork requirements derived from this research:

- protected/redacted diagnostics;
- packet capture only by explicit user/admin action;
- actual selected transport recorded when profile uses Auto;
- routes, DNS, authorization and data-path health exposed separately from simple Connected state.

## 13. Images / UI assets / visual references — PASS (`PROPRIETARY-REFERENCE`)

Official Feature Guide contains screenshots/UI examples and product branding, useful only as behavioral/navigation reference. SonicWall icons/logos/trade dress are proprietary and `DO-NOT-COPY`.

Public package-wrapper projects do not grant rights to SonicWall assets. PVNetwork uses owner-supplied branding and independently designed Persian/English UI.

## 14. Meaningful forks / ecosystem — PASS

Public ecosystem search covered:

- current official proprietary client;
- OpenConnect's unmerged SonicWall protocol work;
- NetExtender-named GitHub package/install wrappers;
- security research/emulation references.

No maintained independent public drop-in protocol engine was found and selected. That negative result is recorded rather than inventing a fork hierarchy.

## 15. Important issues / PRs / releases / advisories — PASS

Current SonicWall Windows 10.3.5 release notes and current May 2026 Feature Guide were reviewed. Current documentation also records versioned 10.3.4-era behavior such as SAML browser selection, all-user profile sharing, idle timeout and CLI TOTP QR binding.

OpenConnect issue #143 / development MR !496 was reviewed as a long-running incomplete NetExtender support effort. Its unmerged status is a material upstream limitation.

Security/update history is treated as release-freeze input; current exact CVE/advisory recheck remains mandatory at implementation/release time.

## 16. Relevant official docs / community lessons — PASS

Official sources reviewed include:

- NetExtender Feature Guide May 2026;
- Windows 10.3.5 release notes;
- SonicOS SSL VPN admin documentation;
- SSL VPN configuration KB;
- SAML SSL-VPN behavior KB;
- supported clients/appliance matrix;
- profile/auth/proxy/diagnostics/log/update/uninstall documentation.

OpenConnect's public issue is used only as protocol-development/ecosystem evidence, not to override SonicWall support claims.

## 17. Tests / CI — PASS (`VENDOR-INTERNAL-N/A`)

SonicWall's private source-level test/CI system is unavailable and not fabricated. Published release notes, support matrices and troubleshooting documentation are product-level quality evidence only.

No mature public NetExtender engine is selected, so there is no public engine CI to certify.

Future PVNetwork test pyramid is explicit: profile/auth state tests -> adapter/capability tests -> controlled server/mock where possible -> exact SonicWall gateway labs -> Windows/Linux real platform lifecycle -> route/DNS/data-path and failure/reconnect tests.

## 18. Store / privacy / security implications — PASS

Research explicitly covers:

- proprietary client/asset boundary;
- passwords/SAML/OTP/proxy/cert/PIN secret classes;
- certificate trust;
- packet-capture/debug-log privacy;
- route/DNS/authorization leakage/failure classes;
- transport downgrade/Auto transparency;
- administrator policy vs user preference;
- Windows service/PreLogon/Always-On privilege/lifecycle;
- Linux package/update/uninstall lifecycle.

No iOS/Android Store claim is inferred from the separate Mobile Connect family.

## 19. PVNetwork reuse decision — PASS

Decision:

`VENDOR-SPECIFIC PRODUCT COMPATIBILITY TARGET / OFFICIAL CLIENT PRIMARY / NO MATURE PUBLIC DROP-IN SELECTED`

Do not implement proprietary NetExtender framing/PPP/auth from scratch. Reuse standards implementations only where exact gateway capability proves them. Revisit OpenConnect only if SonicWall support becomes merged, maintained and auditable.

## 20. Uncertainties explicitly listed — PASS

Remaining later-stage uncertainties:

- exhaustive current SonicOS/SMA/firewall/client support matrix;
- exact per-transport TLS/DTLS/WireGuard/legacy wire and crypto details;
- installer hashes/signatures and update channels;
- complete server/admin menus and deployment topologies;
- SAML/MFA/Duo/certificate/smart-card permutations;
- full route/DNS/authorization behavior;
- PreLogon/Always-On failure/recovery behavior;
- real packet/interoperability evidence;
- whether OpenConnect SonicWall support ever merges and becomes maintainable;
- separate Mobile Connect platform/Store research where needed.

These are bounded v2/implementation/certification questions, not missing original v1 categories.

## Formal v1 result

All 20 original-v1 gates are evidence-backed or have evidence-backed `N/A-PROPRIETARY` treatment with explicit uncertainty.

**Entry 026 may be promoted to `COMPLETE-RESEARCH-v1`.**

Research completion only; no compatibility/implementation/production claim.