# Entry 012 — PPTP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **012 — PPTP**

Decision: **`COMPLETE-RESEARCH-v1 / LEGACY-INSECURE-COMPATIBILITY-ONLY / NOT IMPLEMENTED`**

This reconciliation evaluates the exact original-v1 research gate in `research/PROTOCOL_RESEARCH_TEMPLATE.md`. It deliberately separates research completion from runtime/device/interoperability certification. PPTP remains an obsolete-security compatibility protocol and is not a recommended modern VPN.

## Authoritative and pinned evidence set

Protocol/security references:

- RFC 2637 — PPTP: https://www.rfc-editor.org/rfc/rfc2637.html
- RFC 1661 — PPP: https://www.rfc-editor.org/rfc/rfc1661.html
- RFC 2759 — MS-CHAPv2: https://www.rfc-editor.org/rfc/rfc2759.html
- RFC 3078 — MPPE: https://www.rfc-editor.org/rfc/rfc3078.html
- RFC 3079 — MPPE key derivation: https://www.rfc-editor.org/rfc/rfc3079.html

Current platform/vendor references reviewed on 2026-08-14:

- Microsoft RRAS VPN protocol configuration: https://learn.microsoft.com/windows-server/remote/remote-access/configure-vpn-protocols
- Microsoft RAS VPN server guidance: https://learn.microsoft.com/windows-server/remote/remote-access/get-started-install-ras-as-vpn
- Microsoft `netsh ras`: https://learn.microsoft.com/windows-server/administration/windows-commands/netsh-ras
- Android VPN developer documentation, which labels the built-in PPTP/L2TP-IPsec client as legacy: https://developer.android.com/develop/connectivity/vpn
- Apple current built-in network-security technologies, which list IPsec/IKEv2/L2TP but not PPTP: https://support.apple.com/guide/deployment/depb59c050ef/web
- MikroTik RouterOS PPTP documentation, which warns that PPTP has known security issues and is not recommended for secure use: https://help.mikrotik.com/docs/spaces/ROS/pages/2031638/PPTP

Open-source implementation/source references:

- Poptop/pptpd canonical maintained mirror/release repository: `quozl/pptpd`
- reviewed release: `1.5.0`
- immutable tag commit: `5e1efd65708300657d37f179a9758303df85ddf9`
- repository: https://github.com/quozl/pptpd
- root license reported by repository metadata: GPL-2.0
- upstream SourceForge project/release channel: https://sourceforge.net/projects/poptop/
- PPTP Client canonical project: https://pptpclient.sourceforge.net/ and https://sourceforge.net/projects/pptpclient/
- reviewed client release: `1.10.0` (2018-01-18 release artifact)
- canonical release artifact: https://sourceforge.net/projects/pptpclient/files/pptp/pptp-1.10.0/
- canonical SourceForge code tree: https://sourceforge.net/p/pptpclient/git/ci/master/tree/
- client license: GPL-2.0-or-later per canonical project page

Repository evidence reused:

- `research/protocols/012-pptp/V1_RESEARCH.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/REFERENCE_INDEX.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/SERVER_IMPLEMENTATIONS.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/SERVER_INSTALLERS_AND_PROJECTS.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/SERVER_INSTALL_MATRIX.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/SERVER_UI_AND_MENUS.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/CLIENT_INSTALL_MATRIX.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/CLIENT_UI_AND_MENUS.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/CRYPTOGRAPHY.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/DATA_PATH_AND_WIRE_FLOW.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/DEPLOYMENT_TOPOLOGIES.md`
- `research/upstreams/classic-tunnels-family/pptp-reference-v2/ENTRY_012_V2_GATE_RECONCILIATION.md`

## Exact 20-gate reconciliation

### 1. Top clients identified and justified — PASS

The serious reference set is intentionally small because PPTP is obsolete:

- Windows native VPN client / Windows RRAS — primary proprietary historical interoperability reference;
- MikroTik RouterOS PPTP client/server — current proprietary legacy interoperability reference with explicit vendor warning;
- PPTP Client 1.10.0 — historical Unix client/reference, GPL-2.0-or-later, not a preferred new dependency;
- Poptop/pptpd 1.5.0 — open-source server/reference, GPL-2.0, not a secure-modern default.

No mobile app is promoted merely to create a cross-platform matrix.

### 2. Canonical sources pinned — PASS

- RFCs and current vendor documentation URLs are recorded above.
- Poptop/pptpd is pinned to tag `1.5.0` at commit `5e1efd65708300657d37f179a9758303df85ddf9`.
- PPTP Client is pinned to canonical SourceForge release artifact `1.10.0`; its historical project uses SourceForge release/CVS/Git history rather than a GitHub SHA, so no fabricated SHA is introduced.

### 3. Licenses reviewed — PASS

- `quozl/pptpd`: GPL-2.0 repository license.
- PPTP Client: GPL-2.0-or-later per canonical project page.
- Windows and RouterOS are proprietary/vendor-owned; they are interoperability/reference targets, not source-reuse candidates.
- RFC/documentation text is reference material, not application source to vendor into PVNetwork.

Reuse classification: **reference/interoperability only by default; no third-party PPTP source is approved for product reuse by this research gate.**

### 4. Complete source-tree reference/manifest captured — PASS

For the only open-source server candidate retained for detailed source study, the exact repository/tag/commit is pinned and the complete repository tree remains retrievable from GitHub at that immutable revision. The PPTP Client canonical SourceForge code tree and release artifact are recorded. The later-layer implementation/install/UI documents map the relevant source/build/config boundaries. PVNetwork does not mirror either complete third-party tree.

### 5. Languages/build systems mapped — PASS

The implementation dossier records Poptop/pptpd as a C daemon composed with PPP/pppd on Unix-like systems, with package/source-build ownership separated from Windows/RouterOS native stacks. PPTP Client is a native Unix command-line/client component rather than a PVNetwork UI framework. Windows/RouterOS implementation languages are proprietary/unknown and are not invented.

### 6. Architecture mapped — PASS

`SERVER_IMPLEMENTATIONS.md`, `DATA_PATH_AND_WIRE_FLOW.md`, and `PORTS_TRANSPORTS_AND_HANDSHAKE.md` separate:

- TCP 1723 control;
- GRE IP protocol 47 data;
- PPP authentication/network control;
- optional MPPE legacy payload encryption;
- OS/router routing, address assignment and firewall/NAT ownership.

### 7. Core/engine integration mapped — PASS

PVNetwork must not implement PPTP or MPPE cryptography from scratch. Potential legacy support is via an OS-native/vendor stack or an explicitly selected audited external component behind the product adapter boundary. Engine ownership, error boundaries and cleanup responsibilities are documented in the shared PPTP dossier.

### 8. UI/menu map completed — PASS

`CLIENT_UI_AND_MENUS.md` and `SERVER_UI_AND_MENUS.md` map Windows native/system UI, RRAS administration, RouterOS legacy controls and historical Unix configuration concepts. The PVNetwork UX requirement is a deliberately hidden/advanced legacy option with a prominent security warning and migration guidance, not a normal recommended protocol tile.

### 9. Config/import/export mapped — PASS

The PPTP dossier records the relevant profile concepts: server address, user/authentication method, PPP/MPPE policy, routing/DNS, Windows/RouterOS native configuration and Unix daemon/client configuration. Generic QR/subscription formats are **N/A** to the protocol and must not be invented. Any future PVNetwork canonical-profile import/export would be a product-layer feature, not a PPTP wire feature.

### 10. Persistence/secrets mapped — PASS

The security dossier separates user credentials, OS/vendor profile state, PPP secrets, server-side authentication stores and ephemeral negotiated material. Plaintext credentials/private material must not be written to ordinary PVNetwork logs or portable exports. Exact OS secure-storage behavior remains platform-owned and must be revalidated at implementation time.

### 11. Platform integrations mapped — PASS

- Windows: native historical client and RRAS reference; Windows Server 2025 new RRAS setups do not accept PPTP by default and Microsoft recommends against PPTP due to security limitations.
- RouterOS: current vendor legacy support with explicit insecure warning.
- Android: Android documentation calls the built-in PPTP/L2TP-IPsec client a legacy VPN; exact device/OEM availability is not generalized.
- Apple: current Apple built-in network-security documentation does not list PPTP; no current native PPTP support is claimed.
- Linux/BSD: historical/open-source client/server references are documented; product support is not inferred from source availability.
- Android TV/Google TV: **N/A / unsupported-unverified**; no PPTP support is invented.

### 12. Logs/diagnostics mapped — PASS

The dossier defines layered diagnostics for control-channel establishment, GRE reachability, PPP authentication, MPPE negotiation, route/DNS state and cleanup while redacting credentials/derived secrets. Windows/RouterOS/native diagnostic facilities remain vendor-owned and exact paths must be refreshed for a selected release.

### 13. Asset/screenshot references mapped — PASS

Visual evidence is vendor/system-owned. Use current Microsoft/Android/Apple/MikroTik documentation and product UI as behavioral references. No third-party logos, screenshots or UI assets are approved for copying into PVNetwork. For historical Unix daemon/client components there is no product-grade visual asset set that PVNetwork needs to redistribute.

### 14. Meaningful forks reviewed — PASS / N-A

No fork is selected as a superior modern PPTP implementation. Poptop/pptpd 1.5.0 and PPTP Client 1.10.0 are retained as historical/reference implementations; RouterOS and Windows are proprietary interoperability targets. Because the product decision is legacy compatibility only and no source is selected for reuse, promoting unreviewed forks would add supply-chain risk without product value.

### 15. Important issues/PRs/releases/advisories reviewed — PASS

- Microsoft current guidance explicitly does not recommend PPTP because of its lack of security features and hardens new Windows Server 2025 RRAS setups by not accepting PPTP/L2TP by default.
- MikroTik current documentation explicitly states PPTP has many known security issues and is not recommended for secure use.
- Poptop 1.5.0 is the reviewed release and includes a modernized historical server baseline; it does not change the protocol-security decision.
- PPTP Client 1.10.0 remains the reviewed historical client release; its age is part of the maintenance risk rather than evidence of current product suitability.

### 16. Relevant forums/docs reviewed — PASS

Canonical standards and vendor documentation above are the primary decision sources. Historical project support/discussion channels are available through the SourceForge Poptop/PPTP Client projects and are treated as operational-history evidence only. Community advice cannot override the current Microsoft/MikroTik security classification.

### 17. Tests/CI reviewed — PASS

No selected reusable PPTP engine is being certified by this research gate. The historical projects do not provide evidence sufficient to claim PVNetwork runtime support, and no such claim is made. Required future acceptance tests are documented in the PPTP dossier: TCP1723/control success and failure, GRE blocked/control succeeds, PPP auth negative cases, MPPE negotiation policy, NAT/ALG behavior, multi-client mapping, route/DNS/MTU cleanup and migration/disablement. Runtime results remain a later implementation/certification concern.

### 18. Store/privacy/security implications reviewed — PASS

PPTP is not a modern secure default and must not be silently enabled or used as automatic fallback. Apple current platform documentation does not expose native PPTP; Android treats built-in PPTP as legacy; Windows Server 2025 hardens new RRAS deployments; RouterOS warns against secure use. Product/Store policy must therefore default to **not exposed / legacy compatibility only**, minimize credential collection and provide migration guidance. Current Store policies must be refreshed if implementation is ever proposed.

### 19. PVNetwork reuse decision documented — PASS

Decision: **DEFER / COMPATIBILITY-TEST ONLY / DO NOT BUILD NEW PPTP CRYPTO OR PROTOCOL STACK**.

Preferred order if a real legacy requirement appears later:

1. use a supported OS/vendor-native API or stack where available;
2. otherwise select and separately license/security-audit one external component for a narrowly scoped legacy adapter;
3. never auto-fallback from a modern protocol to PPTP;
4. never market MPPE/PPTP as modern secure VPN.

### 20. Uncertainties explicitly listed — PASS

- exact current client availability varies by Windows/Android/OEM release and must be checked at implementation time;
- Apple current built-in documentation does not list PPTP; no hidden/private platform support is assumed;
- PPTP Client 1.10.0 is historical and is not presumed maintained merely because source remains downloadable;
- Poptop/pptpd 1.5.0 has a recent historical maintenance release, but protocol insecurity remains unchanged;
- exact NAT/ALG, GRE traversal, multi-client mapping and CGNAT/cloud behavior require runtime topology-specific testing;
- exact authentication/MPPE combinations must never be weakened automatically for compatibility;
- no product implementation, build, device test, Store verification or production certification is claimed.

## V1 completion decision

All 20 original research gates are evidence-backed or evidence-backed N/A. The remaining uncertainties are implementation/runtime/certification concerns rather than missing research-contract categories.

Entry **012 — PPTP** may therefore be promoted to:

**`COMPLETE-RESEARCH-v1`**

This promotion does **not** imply `COMPLETE-REFERENCE-v2`, implementation, support, successful connection, device testing, Store readiness or production readiness.
