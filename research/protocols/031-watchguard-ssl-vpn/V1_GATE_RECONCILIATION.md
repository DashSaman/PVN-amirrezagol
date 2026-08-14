# 031 — WatchGuard Mobile VPN with SSL — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original 20-gate research contract only. This is not implementation or WatchGuard certification.

Primary entry audit: `WATCHGUARD_SSL_CURRENT_AUDIT.md`.

Shared reusable engine evidence: `research/upstreams/openvpn-family/` and completed entry 001.

## 1. Top clients / implementations — PASS

The meaningful client/server set is explicit:

1. WatchGuard Mobile VPN with SSL first-party client for Windows;
2. WatchGuard Mobile VPN with SSL first-party client for macOS;
3. OpenVPN Connect / compatible OpenVPN clients using WatchGuard `client.ovpn`, including officially documented Android/iOS paths;
4. Firebox Mobile VPN with SSL as authoritative gateway/server policy implementation;
5. OpenVPN3 Core as PVNetwork's public reusable core candidate for standard profile/wire semantics.

## 2. Canonical sources pinned — PASS (`WATCHGUARD-PROPRIETARY-N/A`)

WatchGuard client/Fireware source is proprietary, so public source SHA is N/A. Current Fireware/client behavior is anchored to current WatchGuard Help Center/release guidance, including current 12.11+/2026 lifecycle changes.

Public OpenVPN source/release evidence is already pinned in the OpenVPN family dossier.

## 3. License / legal reuse — PASS

WatchGuard client/server code and assets are proprietary/reference-only. OpenVPN3's license/dependency/distribution obligations are already documented in the shared dossier and remain the reusable-engine legal path.

Third-party OpenVPN clients are reference/interoperability targets unless separately approved.

## 4. Complete source-tree reference / manifest — PASS (`PUBLIC-CORE`; `WATCHGUARD-N/A`)

OpenVPN family source-tree evidence is captured. WatchGuard private source tree/internal tests are evidence-backed N/A and not fabricated.

WatchGuard publishing `client.ovpn` does not imply rights to proprietary client/server source.

## 5. Languages / build systems — PASS

OpenVPN3 public language/build/dependency evidence is mapped. WatchGuard private implementation languages/build internals are unavailable and remain N/A.

First-party Windows/macOS installer/package, config distribution and update lifecycle are documented at product level.

## 6. Architecture — PASS

Connection path is mapped:

`Firebox SSL VPN policy`

`-> client.ovpn OR WatchGuard first-party config`

`-> OpenVPN-compatible TLS tunnel`

`-> user/domain auth`

`-> optional MFA/SAML depending on client/path`

`-> OpenVPN data channel`

`-> virtual IP/routes/DNS`

`-> Firebox resource policy`

`-> application data`.

Routed vs Bridged traffic remains a server networking capability.

## 7. Core / engine integration — PASS

PVNetwork uses the existing OpenVPN Adapter/OpenVPN3 candidate for standard WatchGuard `client.ovpn` semantics.

WatchGuard first-party SAML/session/proprietary config features remain separate capabilities and are not forced into the generic OpenVPN engine.

No new SSL VPN cryptographic engine is built.

## 8. UI / menu map — PASS

Current first-party client controls are mapped:

- Connect/Disconnect;
- Status;
- View Logs;
- Properties;
- About;
- Exit/Quit;
- macOS elapsed time/status options.

Server UI/wizard/client-profile download paths are mapped. Third-party OpenVPN UI is separately understood through the existing OpenVPN family references.

## 9. Configuration / import / export — PASS

Two distinct client inputs are documented:

- `client.ovpn` — standards/OpenVPN interoperability artifact;
- `sslvpn-client.wgssl` — WatchGuard first-party client configuration artifact.

PVNetwork canonical storage remains product-owned and preserves unsupported/vendor fields. WatchGuard config is not a universal database format.

## 10. Persistence / secure storage — PASS

Separate classes:

- OpenVPN profile/config metadata;
- WatchGuard vendor configuration source;
- passwords/domain selection;
- RADIUS/AuthPoint/OTP state;
- SAML/browser cookies/tokens;
- certificates/private keys;
- transient tunnel keys/session state;
- virtual IP/routes/DNS;
- diagnostics.

PVNetwork uses secure stores and independent transient-session ownership.

## 11. Platform integrations — PASS

Current official paths include:

- first-party Windows client;
- first-party macOS client;
- Android/iOS OpenVPN Connect with `client.ovpn`;
- other OpenVPN-capable endpoints subject to exact compatibility evidence.

macOS OS/client extension and certificate behavior is version-specific. Exact support follows current Fireware release-note OS compatibility.

## 12. Logs / diagnostics — PASS

Current first-party UI exposes View Logs and log level. WatchGuard server troubleshooting and PSInfo/support collection are additional operational references.

PVNetwork diagnostic stages are defined separately for profile/config, TLS/certificate, user auth/MFA/SAML, OpenVPN negotiation/data channel, virtual IP, routes/DNS, Firebox policy, reconnect/config update and application data.

## 13. Images / assets / visual references — PASS

Official WatchGuard documentation has client/admin screenshots used only as navigation reference. WatchGuard branding/assets/trade dress are proprietary/do-not-copy.

OpenVPN client assets follow their own licenses and are not automatically PVNetwork assets.

## 14. Meaningful forks / ecosystem — PASS

The meaningful public ecosystem is OpenVPN, already audited across core/frontends/platforms. There is no need to select a WatchGuard-specific public cryptographic fork because WatchGuard officially supports `.ovpn` client profiles.

First-party client remains the vendor behavioral reference for proprietary features.

## 15. Important issues / releases / advisories — PASS

Current compatibility/lifecycle evidence converted to regression tests includes:

- Fireware 12.11 client-download/update behavior changes;
- Windows/macOS SAML version floors;
- third-party OpenVPN clients not supported for WatchGuard SAML;
- AuthPoint MFA and auto-reconnect incompatibility in documented flow;
- macOS 13+ self-signed certificate trust problem;
- macOS client/system-extension version mismatch;
- major client version mismatch can block connection;
- stale configuration fallback/update behavior;
- TLS 1.2+ current minimum.

OpenVPN engine security/release evidence is already tracked separately.

## 16. Relevant official docs / community lessons — PASS

Primary claims use current WatchGuard Help Center, Fireware release-news, AuthPoint/SAML integration guides and official client/profile documentation.

Generic OpenVPN source/behavior uses audited upstream OpenVPN evidence rather than anecdotal assumptions.

## 17. Tests / CI — PASS

WatchGuard internal CI/tests are proprietary/N-A. OpenVPN3/OpenVPN public tests/security/dependency evidence is captured in the shared dossier.

Future test pyramid covers profile parsing, OpenVPN adapter, TLS/cert/auth/MFA/SAML, exact Firebox lab, routes/DNS/routed/bridged policy, first-party and third-party clients, config update/stale config, installer/OS extension lifecycle, reconnect/leak/performance/security.

## 18. Store / privacy / security implications — PASS

Research covers:

- TLS 1.2+ floor;
- server certificate trust;
- profile/config sensitivity;
- password/RADIUS/MFA/SAML token separation;
- first-party-only SAML boundary;
- route/DNS/routed/bridged security state;
- verbose log/support bundle privacy;
- Windows/macOS installer privilege/update/extension lifecycle;
- Android/iOS Store/client choice;
- OpenVPN3 license/SBOM/security review.

## 19. PVNetwork reuse decision — PASS

Decision:

`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST FOR STANDARD PROFILE+WIRE SEMANTICS / WATCHGUARD FIRST-PARTY SAML+VENDOR FEATURES SEPARATE`

Use the shared OpenVPN Adapter for WatchGuard `client.ovpn` after exact validation. Do not advertise first-party SAML or proprietary WatchGuard client behavior on a generic OpenVPN backend without proof.

## 20. Uncertainties explicitly listed — PASS

Bounded later-stage uncertainties:

- exact current Fireware/client compatibility matrix;
- exact generated `client.ovpn` directives/ciphers/protocol defaults;
- exact OpenVPN3 compatibility by Fireware profile;
- `.wgssl` semantics if import is required;
- first-party SAML/session behavior;
- RADIUS/AuthPoint/MFA combinations;
- Windows/macOS installer/hash/signature/extension lifecycle;
- mobile/third-party client versions;
- routed/bridged/full/split/routes/DNS/firewall policy;
- packet/interoperability/reconnect/stale-config tests;
- post-review advisories/releases.

These are v2/implementation/certification concerns, not missing original-v1 categories.

# Formal result

All 20 original-v1 gates are evidence-backed, evidence-backed proprietary N/A, or explicitly bounded with traceable later-stage uncertainty.

**Entry 031 may be promoted to `COMPLETE-RESEARCH-v1`.**

Research completion only; no implementation/vendor/production claim.