# 080 — TLS Fragmentation — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **080 — TLS Fragmentation**

Decision: **`COMPLETE-RESEARCH-v1 / XRAY FREEDOM-OUTBOUND FRAGMENTATION TECHNIQUE / NOT AN IETF TLS EXTENSION / NOT A STANDALONE PROTOCOL / NOT IMPLEMENTED / NOT CERTIFIED`**

## Exact implementation evidence

Primary source is the already pinned Xray-core implementation:

- `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree `46ee908a9a67513d3c85bbf998be5d553a078109`
- MPL-2.0
- Go 1.26 module
- complete recursive source manifest already recorded in the Xray-family dossier.

Relevant exact source paths:

- `infra/conf/freedom.go` — typed `fragment` configuration and validation;
- `proxy/freedom/freedom.go` — runtime Freedom outbound fragmentation writer path.

At the selected pin, `fragment` fields are:

- `packets`
- `length`
- `interval`
- optional `maxSplit`

Source semantics distinguish:

- `packets: "tlshello"` — **TLS Hello fragmentation into multiple handshake messages** (`PacketsFrom=0`, `PacketsTo=1` in the Xray internal model);
- empty `packets` — TCP segmentation for all packets;
- numeric/range `packets` — TCP segmentation over a selected packet range.

`length` is mandatory and may not start at zero; `interval` is mandatory; optional `maxSplit` constrains splitting. The runtime path applies fragmentation only to TCP Freedom outbound writes when a fragment config exists.

This is therefore an **Xray implementation/camouflage technique**, not an IETF-defined TLS protocol variant. Completed TLS entry 077 remains the authority for TLS security semantics.

## Product / safety boundary

Fragmentation changes write/record/segment presentation and timing. It does not add encryption, server authentication, certificate verification, key exchange, anonymity or a new VPN protocol. It may improve or worsen connectivity under a particular middlebox/censor and may impose latency/CPU/compatibility cost. Such effectiveness is environment-specific and must not be advertised as universal without later measured evidence.

PVNetwork must also distinguish the Xray `tlshello` mode from generic TCP segmentation. A UI label such as “TLS Fragmentation” must not silently apply arbitrary all-packet TCP splitting.

## 20-gate reconciliation

| # | Gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top implementations | PASS | The selected/current matrix implementation is Xray-core Freedom outbound. No independent IETF TLS-fragmentation protocol/client is invented. Client GUIs that expose Xray Freedom fragment fields are configuration references only. |
| 2 | Sources pinned | PASS | Exact Xray commit/tree/source paths are pinned; completed TLS entry 077 supplies the current TLS standards baseline. No separate fake RFC/tag/repository is assigned to this technique. |
| 3 | Licenses | PASS | Xray-core is MPL-2.0 with the already recorded covered-file/source/notice obligations. Fragmentation code is part of that source boundary; no permissive standalone fragmentation library is assumed. |
| 4 | Source tree | PASS | Complete Xray recursive manifest is already pinned. Config parser, Freedom runtime, protobuf/model, tests/build/release areas are traceable inside that complete tree. |
| 5 | Languages / build | PASS | Go/Xray build and dependency/test/release surfaces are already mapped. Fragmentation is built into Xray Freedom; it has no separate package/install system. |
| 6 | Architecture | PASS | Parent inbound/profile -> routing -> Freedom outbound -> TCP connection -> optional `FragmentWriter` -> remote destination. `tlshello` chooses the initial TLS-handshake-oriented range; generic modes perform TCP segmentation. TLS security itself remains above/inside the carried application flow. |
| 7 | Engine integration | PASS | Generate validated Xray Freedom `fragment` config through the version-aware adapter. Do not implement a second custom fragmentation engine unless a non-Xray backend later requires it and is researched separately. |
| 8 | UI / menus | PASS for v1 | Advanced outbound/network-evasion control only. Expose mode (`tlshello` vs packet-range segmentation), length/interval/maxSplit with safe validation and clear warnings. Hide in Simple mode by default; no standalone protocol card. |
| 9 | Config / import / export / URI / QR | PASS | `fragment` is nested Xray Freedom configuration, not a subscription protocol/URI/QR. Import/export preserves exact mode/ranges and distinguishes absent/default from user-set values. Invalid zero/missing length/interval states are rejected like upstream. |
| 10 | Persistence / secrets | PASS | Fragment settings are non-secret. They must remain separate from TLS/REALITY keys and credentials. Generated runtime config is subject to shared Xray redaction/persistence rules. |
| 11 | Platforms | PASS for research | Capability follows the selected Xray core/Freedom outbound on platforms where that engine runs. Exact OS socket behavior, VPN wrapper and mobile/background execution are implementation/certification concerns. |
| 12 | Logs / diagnostics | PASS | Distinguish config-validation error, non-TCP path, fragmentation write error, remote timeout/reset, TLS handshake failure, middlebox behavior and ordinary routing/connectivity failure. Avoid logging sensitive parent-profile secrets. |
| 13 | Assets / localization | PASS/N-A | No canonical independent app icon/store asset/localization set exists for TLS fragmentation. UI assets belong to parent Xray clients and are reference-only per their licenses. |
| 14 | Forks / alternatives | PASS | `tlshello`, all-packet TCP segmentation and packet-range segmentation are modes. uTLS fingerprinting (078), REALITY (074), Cloak (079) and transport choices are distinct capabilities, not aliases or required companions. |
| 15 | Issues / releases / maintenance | PASS | Exact current Xray implementation/source history was reviewed rather than assuming an eternal schema. Current Freedom path continues receiving 2026 changes; Xray also changed security/default behavior in current releases. Mitigation is exact core pin + config-version validation + later measured regression testing, not copying folklore fragment values. |
| 16 | Docs / discussions | PASS | Pinned Xray source/config semantics are primary authority. Community “best fragmentation values” or anti-censorship recipes are not evidence-backed defaults and are not promoted into PVNetwork research facts. |
| 17 | Tests / CI | PASS | Xray's shared Go CI/test surface is already mapped. Source-level config validation is explicit. Environment-specific fragmentation effectiveness requires later network/server/device testing but is not a hidden V1 completion gate. |
| 18 | Store / privacy / security | PASS | Fragmentation is not cryptographic protection and must not weaken certificate verification or be marketed as security. Timing/segmentation can itself become fingerprinting metadata; Store/privacy effects belong to the parent app/network behavior. |
| 19 | PVNetwork reuse decision | PASS | **XRAY-ADAPTER ADVANCED CAPABILITY.** Reuse the maintained Xray implementation with version-aware validation. No standalone “TLS Fragmentation VPN protocol”, no custom crypto, and no universal hard-coded bypass preset. |
| 20 | Open uncertainties / blockers | PASS | Exact wire segmentation, TLS-record versus TCP-write effects by OS/core version, useful parameter ranges, middlebox/censorship effectiveness, latency/throughput cost, transport compatibility and device/Store behavior remain V2/implementation/certification evidence. None blocks the V1 classification/integration decision. |

## Final V1 decision

All 20 V1 gates are evidence-backed or correctly bounded. Entry 080 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented and not effectiveness/runtime certified.
