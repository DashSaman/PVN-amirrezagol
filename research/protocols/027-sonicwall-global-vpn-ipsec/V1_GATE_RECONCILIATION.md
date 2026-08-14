# 027 — SonicWall Global VPN / IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original 20-gate research contract only. This is not PVNetwork implementation, SonicWall interoperability certification or production support.

Primary entry audit: `GVC_IPSEC_CURRENT_AUDIT.md`.

Shared standards-engine evidence: `research/upstreams/strongswan-family/`, especially current strongSwan 6.0.7 release/security/source evidence.

## 1. Top clients / implementations identified and justified — PASS

Research references are explicitly separated by role:

1. **SonicWall Global VPN Client (GVC)** — authoritative proprietary Windows client/product-behavior reference.
2. **SonicOS GroupVPN / IPsec VPN service** — authoritative gateway/policy/provisioning reference.
3. **strongSwan 6.0.7** — primary maintained public standards IKE/IPsec/XAUTH engine/reference where standard semantics match.
4. **Libreswan / other maintained IPsec stacks** — secondary standards/operator references from the broader IPsec dossier, not presumed GVC replacements.

A search did not identify a serious maintained SonicWall-specific public GVC drop-in engine. Random configuration repositories/snippets are not elevated to protocol implementations.

## 2. Canonical sources pinned — PASS (`GVC-PROPRIETARY-N/A`)

Official GVC is proprietary, so a source SHA is not applicable. Current public SonicWall product/support documentation is the authoritative product reference. The public product page continues to expose GVC for Windows; public 4.10 documentation remains available, while the exact latest downloadable package requires MySonicWall freeze and is explicitly retained as a later package-level residual rather than guessed.

The reusable standards candidate is pinned:

- `strongswan/strongswan`
- release 6.0.7
- exact commit `5973ff8e41deef4e015e1138a2de688acedf6f75`

## 3. License / legal reuse reviewed — PASS

- SonicWall GVC/SonicOS client/server code, installer/UI/branding: proprietary, reference-only, do-not-copy.
- strongSwan: GPLv2-family engineering boundary; exact build/distribution/legal architecture requires deliberate review.
- no public SonicWall-specific drop-in was selected, so no unreviewed config project is misclassified as reusable code.

No proprietary GVC policy/protocol implementation is recreated by copying vendor code/assets.

## 4. Complete source-tree reference / manifest captured — PASS (`PUBLIC-CANDIDATE`; `GVC-N/A`)

The exact public strongSwan release/source architecture and source-tree evidence are already captured in the strongSwan family dossier. It includes core daemon/library/plugin/platform/test boundaries.

SonicWall GVC source is private, so a recursive public source manifest is evidence-backed N/A. The research does not invent one.

## 5. Languages / build systems mapped — PASS

strongSwan's native daemon/library/plugin/build architecture is documented in the shared dossier.

Official GVC implementation languages/private build system are not public. Instead, product-level Windows installer/driver packaging and cleanup lifecycle are documented from SonicWall sources. Private implementation internals remain explicit N/A.

## 6. Architecture mapped — PASS

The current architecture is separated into:

`GVC connection/profile UI`

`-> IKEv1 / GroupVPN bootstrap`

`-> gateway auth / PSK or cert`

`-> XAUTH when configured`

`-> client policy provisioning / RCF-related vendor semantics`

`-> IPsec SA / ESP or NAT-T`

`-> virtual adapter / DHCP-over-VPN`

`-> effective routes / default-route or split policy`

`-> user/group VPN Access authorization`

`-> application data path`

Gateway management authentication is separate from VPN user access.

Generic IKE/IPsec is explicitly not treated as equivalent to complete GroupVPN provisioning.

## 7. Core / engine integration mapped — PASS

PVNetwork decision is to reuse an approved standards IKE/IPsec backend such as strongSwan where exact standard semantics match, behind a product-owned adapter.

Vendor-specific GroupVPN provisioning, RCF, virtual-adapter/DHCP behavior and identity/policy semantics are separate capability/certification gates.

No home-grown IKE/IPsec cryptography and no black-box clone of GVC.

## 8. UI / menu map completed — PASS

Official SonicWall support material maps research-level GVC UI/navigation:

- `File > New Connection`;
- remote-access gateway/IP/DNS flow;
- connection Enable/Disable;
- PSK and username/password prompts;
- Connection Properties via File/context-menu/toolbar;
- General / User Authentication / Peers / Status tabs;
- reconnect-after-sleep option;
- `View > Options` launch/warning/window-state behavior;
- `Help > Generate Report` diagnostics.

Official UI screenshots are reference-only; PVNetwork does not copy SonicWall trade dress.

## 9. Configuration / import / export mapped — PASS

SonicOS GroupVPN can export a password-protectable **RCF** client policy and GVC imports it via `File > Import`.

GVC local connection profile persistence includes `Connections.rcf` and `Backup.rcf` in the user's roaming profile per official SonicWall support documentation.

PVNetwork canonical storage must remain product-owned. RCF import, if implemented later, becomes a typed vendor import with unsupported/lossy fields surfaced rather than a universal product format.

## 10. Persistence / secure storage mapped — PASS

Secret/state classes are separated:

- GroupVPN PSK/default provisioning key;
- XAUTH username/password;
- certificate/private-key material;
- optional cached XAUTH credentials governed by gateway Never/Single Session/Always policy;
- optional RCF protection password;
- transient IKE/IPsec SA keys;
- ordinary profile metadata;
- runtime adapter/route/DHCP state.

PVNetwork must use platform secure stores for reusable secrets and respect server policy prohibiting credential caching. Vendor private credential-store internals are not guessed.

## 11. Platform integrations mapped — PASS

Current SonicWall product page exposes GVC for **Windows**. Windows 10/11 support behavior is part of current SonicWall troubleshooting/documentation.

The product installs a virtual/network adapter/driver and has explicit install/uninstall/cleanup requirements. SonicWall documents GVC Cleaner/DNE-related cleanup and virtual-adapter repair.

Non-Windows SonicWall client families are not incorrectly labeled as GVC. strongSwan portability also does not imply certified GVC compatibility on other PVNetwork platforms.

## 12. Logs / diagnostics mapped — PASS

Official GVC diagnostics include `Help > Generate Report` with version, drivers, system information, IP addresses, route table and current logs.

Gateway/GVC logs expose XAUTH, policy-download and IPsec failure states. A documented policy-download error can disable the connection when user/group VPN Access permissions are missing.

PVNetwork diagnostics must distinguish IKE/XAUTH/policy/IPsec/adapter/DHCP/routes/authorization/data-path stages rather than report a single generic VPN error.

## 13. Images / UI assets / visual references mapped — PASS

Official SonicWall KB/admin documentation provides GVC and GroupVPN screenshots/navigation examples. They are behavior/reference evidence only.

SonicWall branding, icons, installer art and screenshots are proprietary/do-not-copy. PVNetwork uses owner-supplied branding and independently designed Persian/English UI.

## 14. Meaningful forks / implementation ecosystem reviewed — PASS

Public ecosystem research did not find a maintained dedicated SonicWall GVC replacement worth selecting.

The meaningful public implementation ecosystem is the standards IPsec layer: strongSwan primary, Libreswan/other maintained stacks secondary. Historical/general IKEv1/XAUTH clients may be comparison references but are not automatically GroupVPN-compatible.

This negative SonicWall-specific result is recorded instead of manufacturing a fork hierarchy.

## 15. Important issues / PRs / releases / advisories reviewed — PASS

Current/relevant vendor operational/security evidence includes:

- current May 2026 GVC split-tunnel/Windows 10/11 throughput guidance;
- GroupVPN policy/authorization failure diagnostics;
- installation/virtual-adapter/coexistence failure classes;
- historical SonicWall GVC installer/application DLL search-order security notice affecting older 4.10.7-and-earlier contexts and remediation guidance;
- current strongSwan 6.0.7 release/security floor, including recent security fixes from the shared dossier.

Exact vendor installer/advisory recheck remains mandatory at release freeze.

## 16. Relevant official docs / community lessons reviewed — PASS

Primary claims are grounded in current SonicWall product/SonicOS/support documentation plus maintained strongSwan source/docs.

Reviewed official categories include:

- VPN clients product page;
- SonicOS 7/8 GroupVPN/IPsec admin guidance;
- client provisioning/RCF export/import;
- XAUTH/DHCP-over-VPN/virtual adapter;
- install/uninstall/driver cleanup;
- report/log troubleshooting;
- route/full-vs-split/access behavior;
- security notices.

Upstream strongSwan discussion/source evidence is used only for standards-engine behavior, not to override SonicWall vendor claims.

## 17. Tests / CI reviewed — PASS

GVC internal source tests/CI are proprietary and explicitly N/A.

The selected public standards candidate strongSwan has source test/security/CI evidence documented in the shared family dossier.

Future PVNetwork test pyramid is defined from profile/capability tests through strongSwan/native adapter tests and finally exact SonicOS GroupVPN interoperability, virtual-adapter/DHCP/routes/data-path and Windows package/driver lifecycle tests.

## 18. Store / privacy / security implications reviewed — PASS

Research explicitly covers:

- PSK/XAUTH/cert/private-key/RCF-password secret separation;
- admin credential-caching policy;
- weak IKEv1/aggressive-mode/default-key risk and no silent downgrade;
- NAT-T/ESP/firewall state;
- Windows driver/installer privilege and cleanup risk;
- GVC coexistence conflicts with third-party IPsec clients;
- route/DHCP/authorization/data-path leakage/failure classes;
- vendor installer security history;
- GPL implications if strongSwan is integrated/distributed.

GVC is a Windows desktop product in current public evidence; no mobile Store claim is invented.

## 19. PVNetwork reuse decision documented — PASS

Decision:

`VENDOR IPSEC INTEROPERABILITY TARGET / REUSE APPROVED IKE-IPSEC BACKEND WHERE STANDARD SEMANTICS MATCH / SONICWALL GROUPVPN CERTIFICATION REQUIRED`

StrongSwan is the primary maintained source reference/candidate for generic IKEv1/XAUTH/IPsec behavior. GroupVPN provisioning/policy/RCF/adapter semantics remain exact-vendor capability work.

Unsupported proprietary combinations remain official-GVC-only rather than triggering a custom cryptographic clone.

## 20. Uncertainties explicitly listed — PASS

Bounded later-stage uncertainties include:

- exact current downloadable GVC installer version/hash/signature in MySonicWall;
- exhaustive SonicOS/model/firmware compatibility;
- exact SonicWall vendor payload/provisioning wire semantics;
- exact supported IKEv1 proposals/security floor;
- certificate/XAUTH/RADIUS/LDAP combinations;
- RCF binary/crypto/provisioning semantics if future import is required;
- strongSwan/native vs official-GVC interoperability;
- Windows virtual-adapter/driver/coexistence behavior;
- NAT-T/full-vs-split/routes/DHCP/access policy combinations;
- sleep/hibernate/reconnect/performance/failover;
- post-2026-08-14 advisories/releases.

These are v2/implementation/certification questions, not missing original v1 research categories.

# Formal result

All 20 original-v1 research gates are evidence-backed, evidence-backed proprietary N/A, or explicitly bounded with traceable later-stage uncertainty.

**Entry 027 may be promoted to `COMPLETE-RESEARCH-v1`.**

This means research closure only. It does not mean PVNetwork implements GVC/GroupVPN, passes SonicWall interoperability, or is production certified.