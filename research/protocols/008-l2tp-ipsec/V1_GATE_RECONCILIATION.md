# Entry 008 — L2TP/IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Decision: **`COMPLETE-RESEARCH-v1 / LEGACY-COMPOSED-COMPATIBILITY / NOT IMPLEMENTED`**

This audit reconciles entry 008 against the exact original 20 research gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md`. L2TP/IPsec is treated as a composition of separate IPsec/IKE, L2TPv2 and PPP layers. Runtime/device/interoperability receipts are intentionally not used as hidden research-completion gates.

## Traceable baseline

Standards:

- RFC 2661 — L2TPv2: https://www.rfc-editor.org/rfc/rfc2661.html
- RFC 3193 — Securing L2TP using IPsec: https://www.rfc-editor.org/rfc/rfc3193.html
- RFC 1661 — PPP: https://www.rfc-editor.org/rfc/rfc1661.html

Current platform references:

- Microsoft RRAS VPN server guidance: https://learn.microsoft.com/windows-server/remote/remote-access/get-started-install-ras-as-vpn
- Microsoft RRAS VPN protocol configuration: https://learn.microsoft.com/windows-server/remote/remote-access/configure-vpn-protocols
- Android VPN developer documentation, which labels built-in PPTP/L2TP-IPsec as legacy VPN: https://developer.android.com/develop/connectivity/vpn
- Apple current macOS VPN configuration: https://support.apple.com/guide/mac-help/mchlp2963/mac
- Apple current network-security platform overview: https://support.apple.com/guide/deployment/depb59c050ef/web

Pinned public implementation references already recorded in `research/upstreams/classic-tunnels-family/l2tp-ipsec-reference-v2/REFERENCE_INDEX.md`:

- `xelerance/xl2tpd` release `v1.3.20`, commit `07b3063e2b6870fad16366bc8d7c52a6f2a4292f`
- `accel-ppp/accel-ppp-ng` commit `9654bb66fa129fc3c20b24612ea91fb43dd14f38`
- `ppp-project/ppp` commit `86c240ea75d48205310a4d0761784cb11f0b086e`
- NetworkManager-l2tp release `1.52.4`, commit `ef970e2f3bf3e219d99c949b7a91a6bb55ab6ef7`
- `katalix/go-l2tp` release `v0.1.8`, commit `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`

Relevant repository evidence:

- `research/protocols/008-l2tp-ipsec/V1_RESEARCH.md`
- the complete `research/upstreams/classic-tunnels-family/l2tp-ipsec-reference-v2/` dossier
- `research/upstreams/classic-tunnels-family/REFERENCE_PINS_2026-08-14.md`
- `research/upstreams/classic-tunnels-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/classic-tunnels-family/DEPENDENCIES_SECURITY_TESTS.md`
- the shared strongSwan/IPsec family evidence used by entries 004–007

## 20 original-v1 gates

### 1. Top clients/implementations — PASS

Serious reference set is evidence-backed and role-separated:

- Windows native VPN client/RRAS for proprietary native interoperability;
- Apple native L2TP over IPsec configuration where currently exposed by the platform;
- NetworkManager-l2tp for Linux desktop composition;
- xl2tpd + external IPsec + pppd for classic Linux composition;
- Accel-PPP NG for server/access concentration reference;
- go-l2tp/kl2tpd for a maintained L2TP component reference.

No client is treated as a complete L2TP/IPsec solution merely because it implements only L2TP or only IPsec.

### 2. Canonical sources pinned — PASS

The public implementation candidates above have immutable reviewed commits/releases in the reference index. Standards and proprietary/native platforms use canonical specification/vendor documentation URLs instead of fabricated source SHAs.

### 3. Licenses reviewed — PASS

The reference dossier records component-level license boundaries: xl2tpd GPL family, Accel-PPP NG GPLv2, pppd per-file licensing, NetworkManager-l2tp GPLv2 and go-l2tp MIT. Proprietary Windows/Apple stacks are interoperability/native-API references, not source-reuse candidates. Exact redistribution/linking review remains component-specific before implementation.

### 4. Complete source-tree reference/manifest — PASS

Pinned repositories/releases and their complete trees are referenceable from the recorded immutable revisions. The dossier maps relevant source/build/config subtrees instead of mirroring full third-party repositories into PVNetwork.

### 5. Languages/build systems — PASS

The dossier separates native OS stacks from Linux C/daemon components, pppd, NetworkManager plugin integration and Go L2TP components. Build/package ownership is documented in the server/client install matrices; unknown proprietary implementation languages are not invented.

### 6. Architecture — PASS

Architecture explicitly separates:

1. IKE/IPsec SA establishment and ESP/NAT-T protection;
2. L2TP tunnel/session control and data;
3. PPP link/auth/network configuration;
4. OS/product address/DNS/route/firewall lifecycle.

`DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md` and the shared IPsec dossier provide the evidence.

### 7. Engine/core integration — PASS

PVNetwork must use native OS stacks or typed adapters around selected maintained components. A strongSwan adapter alone is not a complete L2TP/IPsec backend, and an L2TP daemon alone does not provide IPsec protection. Cryptography is delegated to maintained/native IPsec implementations rather than reimplemented.

### 8. UI/menu map — PASS

`CLIENT_UI_AND_MENUS.md` and `SERVER_UI_AND_MENUS.md` map native Windows/Apple flows, Linux NetworkManager composition, server/peer controls and layered error states. UI must distinguish machine/IPsec authentication, L2TP session state, PPP user authentication and final routing/DNS state.

### 9. Config/import/export — PASS

The dossier maps native profile fields, L2TP tunnel/session configuration, IPsec/IKE credentials/policy, PPP credentials/options, server address and routing/DNS settings. Product-level QR/subscription formats are not protocol requirements and are treated as N/A unless a future PVNetwork canonical profile adds them.

### 10. Persistence/secrets — PASS

Machine PSK/private key/certificate, PPP user credential and server/RADIUS secret are separate secret classes. Native OS keychain/credential facilities or explicit secure references are preferred. Ordinary logs/exports must not contain plaintext reusable secrets.

### 11. Platform integrations — PASS

- Windows: current native client/RRAS reference; Windows Server 2025 new RRAS setups do not accept L2TP/PPTP by default but can explicitly enable them.
- Apple: current macOS/Apple platform documentation exposes L2TP over IPsec as a built-in configuration path.
- Android: Android developer documentation calls built-in L2TP/IPsec a legacy VPN; exact device/OEM availability remains an implementation-time capability check.
- Linux: NetworkManager-l2tp, xl2tpd/pppd and external IPsec composition are mapped.
- Android TV/Google TV: support is not assumed; use evidence-backed unsupported/unverified treatment.

### 12. Logs/diagnostics — PASS

Layered observability is documented for IKE/IPsec negotiation, L2TP tunnel/session, PPP authentication/addressing, route/DNS setup, reconnect and cleanup. Sensitive credentials and key material are redacted.

### 13. Asset/screenshot references — PASS

Native/vendor UI documentation and source UI resources are behavioral references. No third-party screenshots, icons or branding are approved for copying into PVNetwork by this research gate. Linux plugin/UI resources remain source references subject to license review.

### 14. Meaningful forks — PASS

The reference set already spans distinct maintained implementation families rather than relying on one fork: xl2tpd, Accel-PPP NG, pppd, NetworkManager-l2tp and go-l2tp. No additional fork is promoted without a demonstrated maintenance/security advantage. This review is sufficient for the legacy-composed product decision.

### 15. Issues/PRs/releases/advisories — PASS

The later-layer dossier and dependency/security file capture maintenance/security risks and selected release/source pins. Current Microsoft guidance hardens new Windows Server 2025 RRAS setups by not accepting L2TP/PPTP by default. Legacy algorithm assumptions from RFC 3193 are explicitly not copied into current IPsec policy.

### 16. Relevant forums/docs — PASS

Authoritative RFCs, Microsoft/Apple/Android platform documentation and upstream project documentation are the primary sources. Community deployment recipes do not override platform/source evidence and blind remote-install scripts are not trusted.

### 17. Tests/CI — PASS

The source/reference review records upstream build/test/CI evidence where available and a required PVNetwork acceptance matrix covering layered negative cases, NAT, multiple clients, rekey/network change, MTU, route/DNS cleanup and migration. No runtime PASS is invented; research completion only requires that the quality evidence and missing coverage are identified.

### 18. Store/privacy/security implications — PASS

L2TP/IPsec is classified as legacy compatibility rather than a modern default. No silent fallback is allowed. Credentials remain separated by layer, platform support is exact-version/capability based, and current Store/platform policy must be refreshed before implementation/release.

### 19. PVNetwork reuse decision — PASS

Decision: **native-first / composed-adapter compatibility only / no custom cryptography**.

- use native Windows/Apple stack where available and product requirements justify it;
- on Linux, compose explicitly versioned L2TP + IPsec + PPP components behind typed adapters;
- do not present raw L2TP as protected L2TP/IPsec;
- do not silently downgrade current IPsec policy to historical IKE/algorithm defaults;
- prefer modern protocols for new deployments.

### 20. Uncertainties — PASS

Explicit unresolved implementation/certification items remain:

- exact Windows/Apple/Android device/runtime behavior by selected OS build;
- exact Linux distro package/backend/version combinations;
- representative clean install/update/rollback/uninstall;
- NAT/multi-client/rekey/network-change/MTU interoperability;
- synchronized IKE/IPsec + L2TP + PPP packet/log traces;
- selected appliance/HA behavior if retained in scope;
- current Store policy at release time.

These are not hidden V1 research gates and no success is claimed for them.

## V1 completion decision

All 20 original research categories are evidence-backed or evidence-backed N/A. Entry **008 — L2TP/IPsec** may therefore be promoted to **`COMPLETE-RESEARCH-v1`**.

This does not imply `COMPLETE-REFERENCE-v2`, implementation, successful connection, runtime/device certification, Store readiness or production support.
