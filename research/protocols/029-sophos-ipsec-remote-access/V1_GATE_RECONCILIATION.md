# 029 — Sophos IPsec Remote Access — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original 20-gate research contract only. This is not PVNetwork implementation, Sophos interoperability certification, or production support.

Primary entry audit: `SOPHOS_IPSEC_CURRENT_AUDIT.md`.

Shared public engine evidence: `research/upstreams/strongswan-family/` and the completed IKEv1/IPsec entry 005.

## 1. Top clients / implementations — PASS

Roles are explicitly separated:

1. **Sophos Connect current Windows/macOS client** — authoritative proprietary first-party product/UX behavior.
2. **Sophos Firewall current remote-access IPsec** — authoritative server/policy/configuration behavior.
3. **strongSwan 6.0.7** — primary maintained public IKE/IPsec engine/reference for standard semantics.
4. **third-party IPsec clients** — officially possible for `.tgb`/unsupported Sophos Connect endpoint cases, but exact capability must be certified and no generic client is blanket-approved.

The retired `IPsec (legacy)` feature is a historical/migration object, not a current implementation candidate.

## 2. Canonical sources pinned — PASS (`SOPHOS-PROPRIETARY-N/A`)

Sophos Connect/SFOS source is proprietary; current release/documentation is the product reference. Current reviewed client lines are Sophos Connect 2.5 MR1 Windows and 2.0 MR1 macOS.

Public standards engine is pinned:

- `strongswan/strongswan`
- release 6.0.7
- exact commit `5973ff8e41deef4e015e1138a2de688acedf6f75`

No private Sophos source revision is fabricated.

## 3. License / legal reuse — PASS

Sophos client/server code, assets, branding and private implementation are proprietary/reference-only.

strongSwan's GPLv2-family engineering boundary and exact build/distribution obligations are already documented. It remains a standards-engine candidate subject to deliberate architecture/legal review.

No abandoned third-party client or retired Sophos legacy mode is selected as reusable product code.

## 4. Complete source-tree reference / manifest — PASS (`PUBLIC-ENGINE`; `SOPHOS-N/A`)

The pinned strongSwan source tree/architecture/build/test evidence is captured in the shared family dossier.

Sophos private source is evidence-backed N/A. Sophos's public documentation and release notes expose product behavior and selected bundled-component information but not private source internals.

## 5. Languages / build systems — PASS

strongSwan native daemon/library/plugin/build architecture is mapped in the shared dossier.

Sophos Connect private languages/build system are not guessed. Current Windows/macOS package and deployment lifecycle are documented at the product level. Current troubleshooting evidence identifies strongSwan/`charon` as the IPsec engine domain.

## 6. Architecture — PASS

Current path is documented as:

`Sophos .scx/.tgb/.pro input`

`-> Sophos/vendor parsing or provisioning`

`-> IKE identity/auth policy`

`-> IKEv1 Phase 1`

`-> VPN user/group/MFA/SSO policy where applicable`

`-> IKEv1 Phase 2 / IPsec SA`

`-> ESP/NAT-T data plane`

`-> assigned client IP + DNS/routes`

`-> firewall authorization`

`-> application traffic`

The current mode is distinct from the separately retired `IPsec (legacy)` feature.

## 7. Core / engine integration — PASS

PVNetwork direction is a product-owned IPsec Adapter around pinned strongSwan or another approved platform backend for **standard IKEv1/IPsec semantics**.

Sophos-specific `.scx/.pro`, group auth, Entra SSO, Security Heartbeat, client network policy and profile translation stay outside the cryptographic engine.

No home-grown IKE/IPsec and no silent migration into the retired legacy mode.

## 8. UI / menu map — PASS

The current Sophos Connect shell is already mapped from entry 028 and applies to IPsec connections:

- Connections;
- Import connection/provisioning;
- Connect/Disconnect;
- sign-in;
- connection settings;
- Auto-connect where policy permits;
- Delete/Rename;
- Clear credentials;
- Update policy;
- Events;
- VPN log;
- technical support report;
- SSO re-login where supported.

IPsec-specific server UI is mapped under `Remote access VPN > IPsec`, including Enable, profile, authentication type, IDs, allowed users/groups, client network data, advanced settings, Export connection, Logs and Reset.

Sophos visual assets/trade dress are reference-only.

## 9. Configuration / import / export — PASS

Current formats are distinguished:

- `.scx` — recommended Sophos Connect IPsec config, includes general + advanced settings;
- `.tgb` — reduced general-settings artifact for third-party clients;
- `.pro` — Sophos provisioning/update artifact that can fetch/import `.scx` changes.

The retired legacy IPsec configuration is a separate firewall feature and is not conflated with current `.tgb` export.

PVNetwork canonical storage remains product-owned; unsupported/security-relevant vendor fields must be preserved/reported.

## 10. Persistence / secure storage — PASS

Separate state/secret classes are explicit:

- profile/config source metadata;
- PSK;
- RSA certificate/private-key reference;
- reusable username/password subject to policy;
- transient OTP;
- Entra/browser SSO session/token;
- provisioning state;
- transient IKE/IPsec keys/SAs;
- assigned IP/DNS/routes;
- diagnostics.

Current Sophos advanced policy can govern saved credentials; PVNetwork must respect it and use platform secure stores.

## 11. Platform integrations — PASS

Current first-party Sophos Connect remote-access IPsec support is documented for Windows 10/11 and macOS 13+.

Linux/mobile use third-party clients where their IPsec/profile capabilities match. Sophos Connect source portability or strongSwan portability is not treated as automatic certification.

Current package/deployment/GPO/provisioning lifecycle is documented without making unsupported mobile Store claims.

## 12. Logs / diagnostics — PASS

Current Sophos support data clearly separates:

- `charon.log` — strongSwan/IKE/ESP/IPsec/security/packet flow;
- `openvpn.log` — SSL/OpenVPN, separate entry;
- `scvpn.log` — shared VPN event lifecycle;
- `scgui.log` — UI/SSO/client details;
- imported-config summary;
- routes/IP/system state.

PVNetwork future diagnostics must expose profile/provisioning, IKE identity/proposal, user/MFA/SSO, ESP, IP assignment, DNS/routes, firewall authorization and data-path health as distinct failure domains.

## 13. Images / assets / visual references — PASS

Current Sophos Firewall/Sophos Connect documentation contains admin/client screenshots and navigation reference.

Sophos icons/logos/trade dress are proprietary/do-not-copy. Public strongSwan assets are governed by their own license and are not PVNetwork branding.

## 14. Meaningful forks / ecosystem — PASS

The meaningful public ecosystem is the maintained IPsec implementation family already audited: strongSwan primary, other maintained IPsec stacks secondary/operator references.

Sophos-specific value is in profile/provisioning/policy interoperability, not a separate public cryptographic core. No incidental GitHub config repository is elevated into a canonical engine.

## 15. Important issues / PRs / releases / advisories — PASS

Current/high-value release/policy evidence includes:

- `IPsec (legacy)` retirement in SFOS 22.0 MR1+ and upgrade block until legacy config is removed;
- current Sophos Connect Windows/macOS release lines;
- current SSO/MFA/client/profile behavior from Sophos release notes;
- current group-auth policy precedence and disconnect behavior;
- `.scx` update/reimport requirement unless provisioning is used;
- current RSA certificate limitation for remote-access IPsec;
- strongSwan 6.0.7 current security/release floor and recent CVE fixes in the shared dossier.

These are converted into migration/auth/profile/crypto/platform regression tests.

## 16. Relevant official docs / community lessons — PASS

Primary sources reviewed are current Sophos Firewall 21.5/22.0 IPsec overview/settings/example/group-auth/provisioning/legacy-retirement docs, current Sophos Connect troubleshooting/release documentation, and maintained strongSwan source/release material.

Vendor claims remain anchored to Sophos; standards-engine claims remain anchored to strongSwan.

## 17. Tests / CI — PASS

Sophos private CI/source tests are proprietary/N-A and are not fabricated.

strongSwan public source/security/test evidence is already captured. Future PVNetwork certification test layers are explicit:

- config parser/capability/security validation;
- vendor provisioning parser/update;
- strongSwan/native adapter tests;
- IKEv1/ESP/NAT-T controlled tests;
- exact Sophos Firewall lab;
- PSK/cert/user/MFA/SSO;
- client IP/DNS/routes/full-vs-split/firewall rules;
- reconnect/network change;
- install/update/GPO/coexistence;
- legacy-config migration prevention;
- leak/performance/security tests.

## 18. Store / privacy / security implications — PASS

Research explicitly covers:

- IKEv1 compatibility security floor and no weak global fallback;
- PSK/cert/private-key/password/OTP/SSO/provisioning secret separation;
- server/local/remote ID validation;
- current RSA-only vendor certificate capability;
- route/DNS/default-gateway/firewall authorization as security state;
- trusted `.pro` update channel;
- diagnostics privacy;
- GPL/dependency/SBOM consequences for strongSwan;
- first-party Windows/macOS package lifecycle;
- retired legacy feature must not be automatically resurrected.

## 19. PVNetwork reuse decision — PASS

Decision:

`CURRENT SOPHOS IPSEC COMPATIBILITY TARGET / STRONGSWAN-FIRST FOR STANDARD IKEV1-IPSEC SEMANTICS / SCX-PRO-POLICY-SSO SEPARATE / RETIRED LEGACY MODE MIGRATION-ONLY`

Use a product-owned adapter and canonical profile. Treat vendor provisioning/auth/network-policy semantics as separate capabilities and certify them against exact Sophos Firewall versions.

## 20. Uncertainties explicitly listed — PASS

Bounded later-stage uncertainties:

- exact SFOS/model/firmware compatibility matrix;
- exact Phase 1/2 proposal/security floor for supported product policy;
- exact user-auth/vendor-payload mapping;
- `.scx` schema/protection/translation details;
- `.tgb` third-party compatibility matrix;
- `.pro` network/trust/update semantics;
- Entra/MFA/cert/PSK permutations;
- exact strongSwan plugin/config mapping;
- third-party Linux/mobile matrix;
- routes/DNS/Heartbeat/default-route/firewall behavior;
- installers/signatures/update/coexistence;
- real packet/interoperability/reconnect/leak/performance testing;
- post-review advisories/releases.

These are v2/implementation/certification concerns, not missing original-v1 categories.

# Formal result

All 20 original-v1 gates are evidence-backed, evidence-backed proprietary N/A, or explicitly bounded with traceable later-stage uncertainty.

**Entry 029 may be promoted to `COMPLETE-RESEARCH-v1`.**

Research completion only; no implementation/vendor/production claim.