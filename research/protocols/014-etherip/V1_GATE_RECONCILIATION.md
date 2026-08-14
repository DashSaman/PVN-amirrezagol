# 014 — EtherIP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research gate only. EtherIP is an infrastructure Layer-2 encapsulation protocol, not a consumer VPN application. This reconciliation therefore uses evidence-backed `N/A-CONSUMER / PEER-MAPPED` treatment where the v1 template asks about app UI/Store concepts that do not apply to the protocol itself.

## Canonical protocol identity

Authoritative specification:

- RFC 3378, *EtherIP: Tunneling Ethernet Frames in IP Datagrams*: `https://www.rfc-editor.org/rfc/rfc3378.html`

RFC 3378 is Informational. It documents EtherIP as Ethernet/IEEE 802.3 MAC-frame tunneling over IP, using IP protocol number 97 and EtherIP header version 3. The RFC explicitly documents protocol limitations and recommends standards-track alternatives in general. Its security section does not provide EtherIP confidentiality/integrity; it discusses policy exposure and IPsec protection as one approach.

Primary reviewed implementation/reference set:

1. SoftEther Developer Edition source:
   - repository: `SoftEtherVPN/SoftEtherVPN`
   - reviewed architecture pin: `b1f7ef00040786d00bfa06c27fa463d106851e0c`
   - source: `src/Cedar/Proto_EtherIP.c`
   - complete tree: `https://api.github.com/repos/SoftEtherVPN/SoftEtherVPN/git/trees/b1f7ef00040786d00bfa06c27fa463d106851e0c?recursive=1`
2. OpenBSD `etherip(4)` kernel interface:
   - current manual: `https://man.openbsd.org/etherip.4`
   - manual states RFC 3378 EtherIP over IPv4/IPv6, bridge membership, and optional IPsec protection.
3. FreeBSD `gif(4)` / bridge EtherIP implementation reference:
   - current manual: `https://man.freebsd.org/if_gif(4)`
   - documents Ethernet-over-IPv4/IPv6 using EtherIP in conjunction with bridge interfaces.
4. Official SoftEther Stable sibling is a release/maintenance comparison source, not a second protocol specification:
   - `SoftEtherVPN/SoftEtherVPN_Stable`
   - latest observed commit during this campaign: `ed17437af9719ac66acab30faa29e375d613c35f` (`v4.44-9807-rtm`).

Shared repository evidence:

- `research/upstreams/softether-family/SOURCE_CLIENT_ETHERIP_IPSEC_EVIDENCE.md`
- `research/upstreams/softether-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/softether-family/PROTOCOL_CAPABILITIES.md`
- `research/upstreams/softether-family/DEPENDENCIES_TESTS_SECURITY.md`
- `research/upstreams/softether-family/RELEASE_SECURITY_ISSUE_REVIEW.md`
- `research/upstreams/softether-family/SUPPORT_REUSE_DECISIONS.md`

## Gate-by-gate reconciliation

### 1. Top clients / implementations identified and justified — PASS

EtherIP has no meaningful normal-user “top client app” set. The correct implementation set is peer/bridge/server oriented: SoftEther `Proto_EtherIP`, OpenBSD `etherip(4)`, and FreeBSD `gif(4)` + bridge support. SoftEther is the strongest reusable source candidate inside the existing PVNetwork family research; BSD kernel implementations are interoperability/behavior references.

### 2. Canonical sources pinned — PASS

RFC 3378 is the wire-level authority. SoftEther source is pinned to `b1f7ef...` for reproducible source analysis. BSD manuals are authoritative OS implementation documentation. Final deployment version selection remains a later certification decision.

### 3. License / legal reuse reviewed — PASS

RFC/specification use is reference-only. SoftEther root license is Apache-2.0 at the reviewed pin, with `src/THIRD_PARTY.TXT` and submodule obligations already recorded. BSD implementations are studied as platform/reference implementations; this dossier does not approve copying kernel code. PVNetwork reuse direction is conditional SoftEther-backed capability or OS-native integration where appropriate, not source copying by assumption.

### 4. Complete source-tree reference / manifest captured — PASS

The complete recursive SoftEther tree at `b1f7ef...` is preserved. The exact EtherIP implementation file is source-pinned. OS-native implementations are referenced through canonical platform manuals rather than mirrored source trees because they are behavioral/platform references, not selected vendored components.

### 5. Languages / build systems mapped — PASS

SoftEther is a native C/CMake systems codebase with Cedar/Mayaqua dependencies and separate server/client/bridge/admin products. OpenBSD/FreeBSD EtherIP capability is kernel/network-stack functionality managed through native system interfaces. No fictitious cross-platform consumer SDK is asserted.

### 6. Architecture mapped — PASS

SoftEther source evidence shows EtherIP as a dedicated Cedar protocol stack connected through Layer-2 IPC to a Virtual Hub. `EtherIPIpcConnectThread()` resolves an EtherIP client ID to Hub/User/Password mapping, creates an `IPC_LAYER_2` session, and maintains reconnect behavior when effective mappings change. The protocol path is explicitly distinct from L2TPv3 mode.

### 7. Core / engine integration mapped — PASS

PVNetwork decision: entry 014 is a `SERVER-CAPABILITY / INFRASTRUCTURE-PEER` rather than a generic embedded consumer client engine. When SoftEther runtime is controlled, reuse the upstream protocol/server implementation behind the server/infra adapter. BSD hosts may use their native tunnel/bridge facilities as platform-specific peers. Do not force EtherIP into the ordinary VPN-client core interface.

### 8. UI / menu map — PASS (`N/A-CONSUMER / PEER-MAPPED`)

EtherIP itself has no protocol-defined consumer UI. Applicable management concepts are:

- local/remote tunnel peer addresses;
- bridge/Virtual-Hub attachment;
- SoftEther EtherIP client-ID mapping to Hub/User/Password;
- enable/disable/service state where a server owns the capability;
- explicit “unencrypted by itself” security classification;
- optional separate IPsec-protection configuration, which belongs to entry 015 / common IPsec models.

SoftEther Server Manager/`vpncmd` evidence and BSD native `ifconfig`/bridge management are sufficient to establish management ownership. A pixel-complete consumer app menu is `N/A` rather than missing evidence.

### 9. Configuration / import / export mapped — PASS

RFC 3378 defines wire behavior, not a portable profile file. SoftEther uses EtherIP ID mappings and server/Virtual-Hub configuration; BSD implementations use OS network-interface configuration. There is no standard EtherIP subscription/QR/consumer profile format to preserve. PVNetwork must model an infrastructure peer configuration with implementation-specific extension data, not invent a universal file format.

### 10. Persistence / secure storage mapped — PASS

Raw EtherIP has no protocol credential primitive. In SoftEther, the reviewed mapping supplies `HubName`, `UserName`, and `Password` to the internal Layer-2 IPC path, so those server-side mapping secrets belong to protected server configuration. For BSD raw EtherIP peers, peer/tunnel addresses are non-secret network configuration. If IPsec is added, IPsec keys/credentials are owned by the separate IPsec layer, not EtherIP. No plaintext secret-in-product-JSON policy is inferred.

### 11. Platform integration mapped — PASS

OpenBSD and FreeBSD provide native infrastructure paths. SoftEther provides a cross-platform server/runtime implementation. Android/iOS/TV consumer-client integration is `N/A` for this infrastructure capability unless a future product requirement explicitly adds a Layer-2 peer role that those platforms can support. Presence in the 93-entry matrix does not imply a mobile app implementation.

### 12. Logs / diagnostics mapped — PASS

Useful observability is implementation/peer oriented: tunnel/service state, peer addresses, mapping lookup success/failure, Virtual Hub/IPC state, reconnects, frame/byte counters where implementation exposes them, bridge state, malformed-header rejection, MTU/path behavior and optional IPsec-layer status. SoftEther `Proto_EtherIP.c` has explicit EtherIP logging around mapping/IPC connection lifecycle. Sensitive mapping credentials must never be emitted.

### 13. Images / UI assets / visual references — PASS (`N/A-PROTOCOL-ASSETS`)

RFC 3378 provides protocol-format diagrams; canonical OS manuals document interface topology/usage; SoftEther manager assets are product-level references already inventoried in the shared family dossier. EtherIP has no protocol logo or consumer visual asset that PVNetwork needs to copy. No third-party visual asset is approved for redistribution by this result.

### 14. Meaningful forks / implementation ecosystem reviewed — PASS

The review covers three materially distinct maintained implementation families: SoftEther source, OpenBSD native `etherip(4)`, and FreeBSD native `gif(4)`/bridge EtherIP support. Within SoftEther, the official Stable sibling is tracked separately from Developer Edition and canonical upstream is preferred over unrelated forks. That is sufficient ecosystem coverage for this narrow infrastructure protocol; popularity alone is not a selection criterion.

### 15. Issues / PRs / releases / advisories reviewed — PASS

SoftEther release/security/issue evidence is shared because entry 014 runs inside the same Cedar/Hub/server runtime. Current Developer-release/advisory concerns are already recorded, including shared-runtime crash/security lessons and the rule not to equate newest Developer release with a safe production pin. BSD manuals additionally document interoperability/MTU caveats around tunnel implementations. These are future acceptance-test inputs, not hidden research blockers.

### 16. Relevant official docs / community lessons reviewed — PASS

RFC 3378 plus canonical OpenBSD/FreeBSD manuals provide protocol and OS-implementation guidance. SoftEther canonical source/release/issues provide the selected reusable server implementation lessons. No forum-only assertion is needed to complete this gate.

### 17. Tests / CI reviewed — PASS

SoftEther CI/build/test surface is already mapped in the family dossier, including platform and sanitizer/build workflows. For EtherIP specifically, future PVNetwork tests are defined around RFC header acceptance/rejection, Layer-2 frame forwarding, mapping changes/reconnect, bridge cleanup, IPv4/IPv6 outer paths where implementation supports them, MTU, and raw-vs-IPsec security classification. No runtime PASS is fabricated in this research state.

### 18. Store / privacy / security implications reviewed — PASS (`INFRASTRUCTURE-NOT-CONSUMER-STORE`)

RFC 3378 security limitations are explicit: raw EtherIP does not supply confidentiality/integrity and can widen network-policy exposure. Therefore PVNetwork must never display a generic “secure/encrypted VPN” state for entry 014. Consumer mobile Store packaging is not applicable to the current server/peer role. If entry 014 is exposed through server administration, access control, logs/redaction and bridge/firewall policy remain implementation requirements.

### 19. PVNetwork reuse decision documented — PASS

Decision remains:

`ADVANCED L2 ENCAPSULATION / SERVER-CAPABILITY / LOW CONSUMER PRIORITY / NOT ENCRYPTED BY ITSELF`

Use SoftEther's implementation when PVNetwork intentionally controls a SoftEther server/runtime, or use OS-native peer facilities where selected. Keep raw EtherIP separate from EtherIP/IPsec. Do not prioritize a normal-user client UI.

### 20. Uncertainties explicitly listed — PASS

Remaining bounded uncertainties after research completion:

- exact product deployment/runtime implementation(s) are not selected;
- no production-safe SoftEther release is blessed by this research result;
- live SoftEther↔BSD and BSD↔BSD interoperability has not been certified;
- exact performance/resource/MTU behavior depends on selected OS/runtime/topology;
- firewall/bridge policy is deployment-specific;
- mobile consumer support is intentionally not claimed;
- IPsec protection is a separate entry/layer and has separate credential/algorithm/certification evidence.

These are implementation/certification choices or explicit unknowns, not unexamined v1 research categories.

## Formal v1 result

All 20 applicable original-v1 research gates are evidence-backed, evidence-backed `N/A`, or explicitly bounded with traceable uncertainty.

**Entry 014 may be promoted to `COMPLETE-RESEARCH-v1`.**

This means research completion only. It does not mean runtime interoperability, implementation, Store readiness, or production support.
