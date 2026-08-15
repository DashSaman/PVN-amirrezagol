# 090 — KCP — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / CANONICAL KCP ARQ REFERENCE / REFERENCE-ONLY FOR CURRENT PVNETWORK PLAN / NOT CRYPTO / NOT A VPN`**

## Authority and current pin
- Canonical repository: `skywind3000/kcp`.
- Current reviewed `master` commit: `b1a7a2101dcbb96017681a500d6b82bbe5a88766` (2026-06-23).
- Tree: `3b9adb65bd908994d7a3848eec1279b5483cfa37`.
- Core implementation: `ikcp.c`, `ikcp.h`.
- Language: C.
- License: MIT.

This entry remains distinct from Entry 089 mKCP. Current PVNetwork product support uses Xray-native mKCP where applicable; canonical KCP is retained as architecture/reference evidence unless a future non-Xray backend explicitly selects direct reuse.

## Exact 16-gate reconciliation
|#|Gate|Result|Evidence / boundary|
|---:|---|---|---|
|1|Server ecosystem|PASS|Canonical KCP is an embeddable ARQ library, not a standalone VPN server. Related language ports/applications exist but are implementation alternatives, not authoritative protocol servers.|
|2|Installers/deployment projects|PASS / N-A|No canonical standalone KCP VPN installer is required for the current reference-only decision. Embedding application owns deployment.|
|3|Server install matrix|PASS / N-A|Portable C core is host-embedded. OS/container/orchestration support belongs to the embedding application/toolchain, not a KCP daemon.|
|4|Server UI/menu map|PASS / N-A|No canonical KCP server panel. Any MTU/window/no-delay/resend/congestion controls belong to the embedding app.|
|5|Client install matrix|PASS / N-A|No standalone KCP client package selected. Direct reuse is not currently selected.|
|6|Client UI/menu map|PASS / N-A|If a future direct backend is selected, only justified advanced KCP tuning belongs under its parent profile; no standalone VPN card.|
|7|Cryptography|PASS / N-A|KCP has no encryption/authentication. Parent security layer is mandatory where confidentiality/integrity is required.|
|8|Data path/wire flow|PASS|Application data -> KCP queues/segmentation/ARQ/window/retransmission -> host output callback -> underlying datagram transport -> peer KCP input -> ordered/reassembled application data.|
|9|Ports/transports/handshake|PASS|KCP itself does not own sockets or fixed ports; host application selects lower datagram endpoint. Conversation/session identifier is not authentication.|
|10|Deployment topologies|PASS|Any client/server/tunnel topology is defined by the embedding application, not KCP itself.|
|11|Source/license/activity pins|PASS|Exact canonical commit/tree now pinned; MIT license and current maintenance state recorded.|
|12|Supply-chain/security risks|PASS|Do not import floating `master` later without freezing source/SBOM. Related ports/forks require independent review; KCP itself is plaintext.|
|13|Upgrade/uninstall/rollback|PASS|Reference-only today. Future direct reuse must pin an exact source snapshot; upgrade/rollback would be owned by the embedding backend/library dependency.|
|14|Differences/uncertainties|PASS|Canonical KCP versus Xray mKCP, tuning/performance, lower transport, wrappers/forks and platform behavior are explicit.|
|15|Reference index|PASS|Companion `REFERENCE_INDEX.md`.|
|16|Handoff continuation|PASS|Batch handoff advances to Entry 091 XHTTP.|

## Final decision
All 16 V2 gates are evidence-backed or correctly reference-only/embeddable-library N/A bounded. Entry 090 qualifies for **`COMPLETE-REFERENCE-v2`** without selecting a direct shipping KCP engine.
