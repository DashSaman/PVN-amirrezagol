# Entry 076 — XTLS Vision — COMPLETE-REFERENCE-v2 audit

Research date: 2026-08-15

Result: **all 16 V2 research/reference gates PASS**.

Research completion only; no implementation/interoperability/performance/censorship-resistance certification is implied.

Canonical pin: `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, MPL-2.0. Latest observed GitHub release: `v26.3.27` (2026-03-27).

## 16-gate reconciliation

1. **Server ecosystem — PASS.** Vision is implemented inside current Xray-core VLESS handling. `proxy/vless/vless.go` defines `xtls-rprx-vision`; VLESS server/client processing carries the flow through addons and current proxy data-path code. It is not a standalone server daemon.
2. **Installer/deployment projects — PASS.** Deployment unit is Xray-core or an independently reviewed compatible client/server product embedding/hosting a matching core. No independent Vision installer exists; evidence-backed N/A for a separate package.
3. **Server install matrix — PASS.** Applicable matrix is Xray-core's platform/deployment matrix. Vision support must be version-aware; it cannot be inferred from an OS alone. No separate Vision install target exists.
4. **Server UI/menu map — PASS.** Canonical Xray-core has no built-in graphical Vision menu. Canonical configuration semantics are the flow string `xtls-rprx-vision` attached to the applicable VLESS account/request/config context plus a supported security stack. Third-party panel labels are non-normative and separately licensed.
5. **Client install matrix — PASS.** Client availability belongs to Xray-core-compatible apps/wrappers documented in the shared Xray client ecosystem. Support must be claimed per selected client/core/version, not merely because a GUI has a “Vision” label.
6. **Client UI/menu map — PASS.** Required semantic UI field is the flow choice `xtls-rprx-vision`, exposed only when the adapter says the exact application protocol/security/transport/core combination is valid. Legacy generic XTLS security toggle is not equivalent.
7. **Cryptography/security boundary — PASS.** Vision is not itself a replacement cipher suite or PKI. Confidentiality/authentication is supplied by the selected supported security layer, notably TLS or REALITY. Current source explicitly separates stream security from VLESS flow. Therefore cryptographic algorithms for TLS and REALITY remain in Entries 077 and 074; Vision's security-sensitive contribution is flow/data-path behavior rather than a standalone encryption algorithm.
8. **Data path/wire flow — PASS.** `proxy/vless/encoding/addons.go` serializes the Vision flow in VLESS addons and, for non-UDP Vision body handling, selects `proxy.NewVisionWriter`. The current implementation therefore changes VLESS body/data-path treatment after the enclosing secure connection is established. UDP follows the VLESS packet-length path rather than the Vision writer branch shown in the pinned file. Exact writer behavior is pinned to `proxy/proxy.go` at the same commit.
9. **Ports/transports/handshake — PASS.** Vision has no assigned port and no independent handshake. Listener port and outer transport belong to the enclosing Xray profile; TLS/REALITY provides the security handshake. Combination support is core-version-sensitive and must be validated rather than inferred from the string `xtls-rprx-vision` alone.
10. **Deployment topologies — PASS.** Valid topology is an Xray VLESS client/server path with Vision selected as flow and a supported security/transport combination. It can appear behind a client GUI/subscription/control plane, but those are separate components. No standalone Vision tunnel topology is claimed.
11. **Source/release/license/activity pins — PASS.** Current source pin, MPL-2.0 license and latest observed release are recorded. The repository remained active in August 2026; current main and latest release are distinct pins.
12. **Supply-chain/security risks — PASS.** Pin the Xray core actually used; independently review GUI/wrapper/panel licenses and distribution channels; do not trust a profile label without adapter validation; preserve flow/security/transport fields separately; avoid untrusted scripts/binaries; treat version changes as potentially altering compatibility/data-path semantics.
13. **Upgrade/uninstall/rollback — PASS.** Vision has no independent package lifecycle. Preserve canonical profile fields and source version separately from generated Xray JSON; validate the exact flow/security/transport combination before core upgrades; retain a pinned known-good core for rollback when required; never silently rewrite Vision to another flow.
14. **Differences/uncertainties — PASS.** Entry 075 legacy XTLS, Entry 076 Vision, Entry 077 TLS and Entry 074 REALITY are distinct. Current source rejects generic `security: "xtls"` while retaining `xtls-rprx-vision` as VLESS flow. GUI/client support and compatible transports remain version-specific; this research does not certify every third-party pair.
15. **REFERENCE_INDEX — PASS.** `REFERENCE_INDEX.md` records current source paths, pins and the flow/security boundary.
16. **Exact continuation — PASS.** Continue Entry 077 TLS. Audit current Xray TLS configuration/source plus standards/library boundaries, server/client install/UI fields, certificate/verification behavior, handshake/ports/topologies, source/license/supply-chain and rollback, while keeping TLS independent from uTLS fingerprinting Entry 078 and REALITY Entry 074.

## Completion decision

Current canonical source provides direct evidence for the Vision identifier, VLESS-addon placement and Vision-writer data path, while current stream configuration separately establishes TLS/REALITY security and removal of legacy XTLS security. All applicable 16 gates are evidence-backed or explicit N/A for nonexistent standalone installer/UI/package concepts. Entry 076 is eligible for `COMPLETE-REFERENCE-v2`.
