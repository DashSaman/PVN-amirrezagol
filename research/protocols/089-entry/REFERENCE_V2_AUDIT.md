# 089 — mKCP — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / XRAY-NATIVE MODIFIED KCP OVER UDP / NOT CANONICAL KCP / NOT CRYPTO / NOT A VPN`**

## Authority and pins
- Xray `7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0.
- Implementation path `transport/internet/kcp/`.
- Entry 090 is canonical KCP reference and must remain separate.

## Exact 16-gate reconciliation
|#|Gate|Result|Evidence / boundary|
|---:|---|---|---|
|1|Server ecosystem|PASS|The canonical implementation for this matrix entry is Xray's source-resident mKCP transport on both peer sides; no independent mKCP server project/RFC is invented.|
|2|Installers/deployment projects|PASS / N-A|No standalone mKCP installer. Deployment follows the Xray parent server/client package.|
|3|Server install matrix|PASS|Capability follows Xray/Go and UDP-capable platforms; exact service/container packaging is parent-engine deployment.|
|4|Server UI/menu map|PASS / N-A|No generic mKCP server panel; parent Xray panels may expose source-backed transport settings.|
|5|Client install matrix|PASS|Capability follows Xray client packaging. UDP/MTU/mobile behavior remains platform implementation evidence.|
|6|Client UI/menu map|PASS|Advanced parent-transport UI may expose MTU/TTI/uplink/downlink/congestion/buffers/header/seed with exact-version validation; no standalone VPN card.|
|7|Cryptography|PASS / N-A|mKCP provides no authenticated encryption. Parent protocol/TLS/REALITY owns security; header/seed camouflage is not cryptographic security.|
|8|Data path/wire flow|PASS|Parent bytes -> Xray mKCP segmentation/session/ARQ/retransmission/congestion/header handling -> UDP -> peer mKCP -> parent bytes.|
|9|Ports/transports/handshake|PASS|Uses UDP and parent-selected endpoints. Session/setup is Xray mKCP-specific; no IETF KCP handshake or fixed VPN port is invented.|
|10|Deployment topologies|PASS|Direct parent client/server transport; any proxy/gateway topology is parent Xray deployment, not mKCP-specific control plane.|
|11|Source/license/activity pins|PASS|Exact Xray commit/tree/path and MPL-2.0 boundary.|
|12|Supply-chain/security risks|PASS|No standalone installer. Pin Xray core; avoid untrusted tuning presets/scripts and excessive resource defaults.|
|13|Upgrade/uninstall/rollback|PASS|Lifecycle follows Xray core/config. Upgrade requires config/wire regression review; rollback restores prior core/config.|
|14|Differences/uncertainties|PASS|mKCP versus canonical KCP, header variants, tuning ranges, MTU/NAT/firewall and cross-version behavior are explicit and not assumed equivalent.|
|15|Reference index|PASS|Companion `REFERENCE_INDEX.md`.|
|16|Handoff continuation|PASS|Batch handoff advances to Entry 090 KCP.|

## Final decision
All exact 16 V2 gates are evidence-backed or correctly engine-transport N/A bounded. Entry 089 qualifies for **`COMPLETE-REFERENCE-v2`** without runtime/device/Store certification claims.
