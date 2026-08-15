# 092 — RAW — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / XRAY RAW-TCP TRANSPORT NAMING+FRAMING / NOT OS RAW SOCKETS / NOT CRYPTO / NOT A VPN`**

## Authority and pins
- Xray `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0.
- Current implementation area: `transport/internet/tcp/`.
- Lower transport authority: Entry 081 TCP.

This entry refers to Xray's modern RAW naming around its TCP byte-stream/framing transport. It does **not** mean OS raw IP sockets, packet injection, privileged raw-socket APIs, or a separate cryptographic protocol.

## Exact 16-gate reconciliation
|#|Gate|Result|Evidence / boundary|
|---:|---|---|---|
|1|Server ecosystem|PASS|Pinned Xray is the implementation authority for this RAW/TCP transport family on both peers; no standalone RAW VPN server or raw-IP daemon is applicable.|
|2|Installers/deployment projects|PASS / N-A|No standalone RAW installer. Deployment follows parent Xray server/client packaging.|
|3|Server install matrix|PASS|Capability follows Xray/Go and ordinary TCP socket support. No privileged raw-socket/kernel-module deployment is required by this entry.|
|4|Server UI/menu map|PASS / N-A|Parent Xray panels may expose RAW/TCP framing/header settings. No generic RAW server panel exists.|
|5|Client install matrix|PASS|Capability follows Xray-family client packaging and ordinary TCP networking.|
|6|Client UI/menu map|PASS|Expose RAW as a parent transport with legacy `tcp` provenance/migration handling and source-backed framing/header fields. Avoid “raw packet” wording.|
|7|Cryptography|PASS / N-A|RAW/TCP is not encryption/authentication. TLS/REALITY or parent security layers remain separate.|
|8|Data path/wire flow|PASS|Parent protocol bytes -> Xray RAW/TCP transport/framing -> OS TCP socket -> peer Xray transport -> parent protocol. Optional HTTP-like header camouflage is framing, not standardized HTTP security.|
|9|Ports/transports/handshake|PASS|Uses TCP and parent-selected port. No raw-IP protocol number or independent handshake exists. Parent TLS/REALITY handshakes remain separate.|
|10|Deployment topologies|PASS|Direct client/server and any gateway/proxy topology is parent Xray deployment; RAW itself defines no management/control plane.|
|11|Source/license/activity pins|PASS|Exact Xray commit/tree/path and MPL-2.0 boundary.|
|12|Supply-chain/security risks|PASS|No dedicated installer. Pin Xray core; do not request privileged raw-socket permissions or rely on third-party framing scripts. Sensitive custom headers are redacted.|
|13|Upgrade/uninstall/rollback|PASS|Lifecycle follows Xray core/config. Upgrade must preserve legacy `tcp` import compatibility and exact field semantics; rollback restores prior core/config.|
|14|Differences/uncertainties|PASS|Modern `raw` vs legacy `tcp` naming, optional framing/header behavior, TCP lower-layer tuning and parent security compatibility are explicit and version-sensitive.|
|15|Reference index|PASS|Companion `REFERENCE_INDEX.md`.|
|16|Handoff continuation|PASS|Batch handoff advances to Entry 093 DTLS.|

## Final decision
All exact 16 V2 gates are evidence-backed or correctly Xray-transport N/A bounded. Entry 092 qualifies for **`COMPLETE-REFERENCE-v2`** without implementation/device/Store certification claims.
