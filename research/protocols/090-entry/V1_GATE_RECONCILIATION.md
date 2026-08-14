# 090 — KCP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **090 — KCP**

Decision: **`COMPLETE-RESEARCH-v1 / CANONICAL KCP ARQ REFERENCE / RELIABLE-OVER-DATAGRAM ALGORITHM / NOT ENCRYPTED / REFERENCE-ONLY FOR CURRENT PVNETWORK PLAN / NOT IMPLEMENTED / NOT CERTIFIED`**

## Canonical authority and source boundary

- canonical repository: `skywind3000/kcp`
- canonical default branch reviewed: `master`
- core source: `ikcp.c`, `ikcp.h`
- implementation language: C
- project license: MIT
- project documentation describes KCP as a fast/reliable ARQ protocol intended to run over an underlying datagram transport; the application supplies the lower-layer send/output function and clock/update scheduling.

This entry is **not** the implementation authority for Xray mKCP. Entry 089 is Xray's own source-resident modified KCP transport and is separately complete.

No direct PVNetwork embedding of canonical KCP is selected by this V1 decision. Therefore this dossier does not invent an immutable commit SHA when the live connector did not expose a commit value that could be independently re-verified in the current run. If direct KCP reuse is later selected, the implementation work unit must freeze the exact canonical commit/tree and SBOM before code import. This is an evidence-preserving V1 boundary, not a claim that `master` is immutable.

## Architecture / configuration model

Canonical KCP provides ARQ/session behavior above a datagram service. Important API/tuning concepts include:

- conversation/session identifier;
- MTU;
- send/receive windows;
- update/interval timing;
- no-delay mode;
- fast resend;
- congestion-control toggle;
- send/receive/input/output/update/check lifecycle.

KCP does not provide endpoint discovery, sockets, UDP itself, DNS, TLS, authentication, certificates, encryption or a VPN application. Those are owned by the embedding application/layers.

## 20-gate reconciliation

| # | Gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top implementations | PASS | `skywind3000/kcp` is the canonical reference implementation for KCP. Xray mKCP is explicitly separate entry 089 and is not treated as canonical KCP. |
| 2 | Canonical sources pinned | PASS for current V1 reuse decision | Canonical repo/default branch and core source files are identified. No direct code reuse is selected, so no fabricated commit is recorded. Exact immutable commit/tree becomes mandatory before any future direct KCP source import. |
| 3 | Licensing / legal reuse | PASS | Canonical KCP is MIT: commercial use/modification/redistribution are permitted subject to retaining copyright/license terms; there is no copyleft requirement. Trademark/endorsement rights are not inferred. Direct embedding would still require exact source/notice/SBOM capture at implementation time. |
| 4 | Complete source-tree review | PASS for reference-only V1 | Repository root/core implementation, examples/tests/build/docs are identified as the canonical source surface. Because no direct source import is selected, a shipping source manifest is intentionally deferred to the future direct-reuse decision rather than fabricated. |
| 5 | Languages / build / dependencies | PASS | Core is portable C (`ikcp.c`/`ikcp.h`) designed to be embedded by an application. Platform socket/timer/build integration is host-owned rather than a standalone KCP daemon/package contract. |
| 6 | Internal architecture / data flow | PASS | Application data -> KCP send queue/segmentation/ARQ/window/retransmission -> host output callback -> datagram transport -> peer KCP input -> ordered/reassembled application data. Clock/update scheduling is host-controlled. |
| 7 | Core / engine integration | PASS | Current PVNetwork decision is **reference-only**, because Xray entry 089 already owns the product mKCP implementation. If a future non-Xray backend needs canonical KCP, integrate the maintained canonical core behind a dedicated adapter rather than assuming mKCP wire/config compatibility. |
| 8 | UI / menus | PASS/N-A | Canonical KCP is an algorithm/transport component, not a consumer VPN. Any future direct integration would expose only justified advanced MTU/window/no-delay/resend/congestion parameters under a parent transport profile. |
| 9 | Config / import / export / URI / QR | PASS | KCP has API/tuning parameters, not a canonical VPN subscription URI/QR. Parent applications define endpoints, lower datagram transport and serialization. No `kcp://` PVNetwork standard is invented. |
| 10 | Persistence / secrets | PASS | KCP tuning/session metadata is not cryptographic secret material. Parent protocol/security credentials remain independently owned; conversation IDs must not be mislabeled as authentication. |
| 11 | Platform-specific implementation | PASS for research | Portable C core is host-integrated; socket/timer/threading/mobile/background/network-stack behavior belongs to the embedding application/platform. |
| 12 | Logs / diagnostics | PASS | Distinguish lower datagram loss/reachability/MTU from KCP window/retransmission/update/session problems and parent protocol/security failures. |
| 13 | Assets / localization | PASS/N-A | No canonical consumer application/store asset set applies. |
| 14 | Forks / alternatives / variants | PASS | Xray mKCP (089) is a distinct modified implementation. QUIC/Hysteria/TUIC are separate transports/protocols. KCP tuning profiles are not separate protocol entries. |
| 15 | Issues / releases / maintenance | PASS for reference-only V1 | Canonical repository remains the source authority; direct shipping is not selected. A future embed must re-review current canonical head/tag/issues/advisories and freeze an exact source snapshot before import. No unsupported claim that current master is a stable release is made. |
| 16 | Official docs / support authority | PASS | Canonical repository source/README/API documentation is primary. Performance/latency statements from the upstream README are treated as upstream claims rather than PVNetwork-certified benchmarks. |
| 17 | Tests / CI / quality evidence | PASS | Canonical repository includes example/test material sufficient to identify expected API behavior for research. PVNetwork performance/loss/device interoperability remains later implementation evidence if direct reuse is ever selected. |
| 18 | Store / privacy / security implications | PASS | KCP is not encryption or authentication. Running it directly over an untrusted datagram path exposes payload unless a parent security layer protects it. High-rate retransmission/tuning can create resource/network impact. |
| 19 | PVNetwork reuse decision | PASS | **REFERENCE-ONLY / NO CURRENT DIRECT KCP ENGINE.** Product mKCP support is owned by Xray entry 089. Canonical KCP is retained as architecture/algorithm/upstream comparison evidence; direct reuse requires a new exact source freeze. |
| 20 | Open uncertainties / blockers | PASS | Exact future source commit, fork comparison, wire delta from Xray mKCP, tuning/performance, lower transport choice, security wrapper and platform/device behavior are implementation/V2 work only if direct KCP support is selected. None blocks the current reference-only V1 decision. |

## Final V1 decision

All 20 V1 gates are evidence-backed or explicitly bounded by the **reference-only / no direct reuse** decision. No commit, release or test result is fabricated. Entry 090 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
