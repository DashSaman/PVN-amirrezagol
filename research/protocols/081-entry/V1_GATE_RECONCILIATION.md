# 081 — TCP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **081 — TCP**

Decision: **`COMPLETE-RESEARCH-v1 / FOUNDATIONAL OS TRANSPORT / NO DEDICATED VPN ENGINE / NOT ENCRYPTED / NOT IMPLEMENTED / NOT CERTIFIED`**

## Current standards baseline

- RFC 9293 — *Transmission Control Protocol (TCP)*, STD 7, Internet Standard, August 2022:
  `https://www.rfc-editor.org/info/rfc9293/`
- RFC 9293 is the modern base TCP specification and obsoletes RFC 793 plus several piecemeal updates. Companion RFCs continue to define congestion control, loss recovery, ECN, authentication and optional extensions; PVNetwork does not treat RFC 9293 alone as a complete deployment-tuning manual.

TCP provides reliable ordered byte-stream transport. It does **not** provide confidentiality or application/server authentication and must not be represented as a VPN or security protocol.

## Selected implementation references

### Linux kernel

- `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`
- tree `cdfb6ad04701df82290575494f40fbb00efe0512`
- recursive manifest already pinned by `research/upstreams/linux-tunnels-family/V1_SOURCE_TREE_AUDIT.md`
- root license `GPL-2.0 WITH Linux-syscall-note`, file-level SPDX authoritative
- relevant source includes `net/ipv4/tcp.c`, TCP input/output/timers/congestion-control paths, IPv6 hooks and networking selftests.

Current canonical path history was reviewed. The pinned source is newer than the 2026 fix `1bbf0ced1d9db73ac7893c2187f3459288603e0d`, which fixed a stale per-CPU TIME_WAIT-derived ISN value that could make a subsequent connection's initial sequence number more predictable. This is deployment-version evidence, not a claim that every downstream kernel is patched.

### Go standard networking runtime

- `golang/go@c19862e5f8415b4f24b189d065ed739517c548ba` (go1.26.5)
- tree `0bb2fb1cc06c334c36a2a92d2f0b07fea7236d74`
- BSD-3-Clause
- `src/net/`, `src/net/http/` and related tests provide application-level TCP/socket integration for Go engines.

### Xray product integration

- `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree `46ee908a9a67513d3c85bbf998be5d553a078109`
- MPL-2.0
- Xray delegates actual TCP transport to the host/runtime networking stack; PVNetwork should preserve that boundary rather than implement a second TCP stack.

## 20-gate reconciliation

| # | Gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top implementations | PASS | Linux kernel is the primary OS implementation reference; Go `net` is the primary application/runtime API reference for Go engines; Xray is the current product transport-integration reference. Other OS TCP stacks remain platform implementations to pin if directly depended on. |
| 2 | Sources pinned | PASS | RFC 9293 + exact Linux/Go/Xray commits and trees are pinned. No stale RFC 793-only baseline is used. |
| 3 | Licenses | PASS | Linux syscall/API use is separated from GPL-covered source copying; Go is BSD-3-Clause; Xray is MPL-2.0. PVNetwork uses OS/runtime APIs and does not copy a TCP stack into the closed core. Exact shipped dependencies retain their own notice/source obligations. |
| 4 | Source tree | PASS | Complete Linux, Go and Xray recursive tree references are pinned; TCP/kernel, Go socket/runtime, build/test and product-adapter paths are traceable. |
| 5 | Languages / build | PASS | Linux TCP implementation is C/Kbuild; Go runtime networking is Go; Xray adapter is Go. Kernel/distribution and application packaging remain separate deployment boundaries. |
| 6 | Architecture | PASS | Application/engine byte stream -> OS/runtime TCP socket -> TCP state machine/reliability/congestion/loss handling -> IP underlay. DNS, proxy framing, TLS/security and application protocol are independent layers. |
| 7 | Engine integration | PASS | Use native OS/runtime TCP sockets through the selected engine. Engine-specific keepalive, TFO, socket options and connection policy are capability-gated rather than reimplemented in a portable custom TCP core. |
| 8 | UI / menus | PASS/N-A | TCP is a foundational transport, not a consumer protocol card. Only expose transport selection and justified advanced socket/timeout options inside a parent profile. Low-level kernel tuning is not a normal profile field. |
| 9 | Config / import / export / URI / QR | PASS | Parent profiles may select network=`tcp` and carry engine-specific framing/socket fields. No standalone `tcp://` VPN subscription/QR format is invented. OS ephemeral state and congestion-control internals are not portable profile data. |
| 10 | Persistence / secrets | PASS | TCP itself has no cryptographic credential. Destinations, ports and socket-policy metadata may be sensitive topology data but are not secrets equivalent to private keys. TLS/proxy credentials stay owned by their parent layers. |
| 11 | Platforms | PASS for research | Linux and Go implementation paths are pinned; Windows/macOS/iOS/Android native stacks differ internally. Platform-specific socket/TFO/keepalive/background/VPN behavior is implementation evidence, not a hidden V1 blocker. |
| 12 | Logs / diagnostics | PASS | Distinguish DNS, connect/refused/unreachable, SYN timeout, reset, idle/read/write timeout, EOF/half-close, MTU/path, proxy/TLS/application failure and local resource exhaustion. Do not expose parent-layer secrets. |
| 13 | Assets / localization | PASS/N-A | TCP has no canonical application assets, screenshots or Store listing. Parent client assets remain separately licensed/reference-only. |
| 14 | Forks / alternatives | PASS | UDP, QUIC and application-level reliable transports are separate entries. MPTCP/TFO/congestion-control algorithms are TCP extensions/capabilities, not separate VPN protocols and are not silently enabled universally. |
| 15 | Issues / releases / advisories | PASS | Current Linux TCP path maintenance was reviewed, including the 2026 predictable-ISN hardening fix contained by the pinned source. Go/Xray exact releases remain pinned. Downstream distro backports must be checked at deployment rather than inferred from upstream master. |
| 16 | Official docs / support authority | PASS | RFC 9293/IETF transport documents, pinned kernel source/docs and official Go/Xray source are primary. Tuning blogs are not promoted to universal protocol requirements. |
| 17 | Tests / CI | PASS | Linux networking selftests and Go network tests are source-visible; Xray shared CI/tests are mapped. Product connection/performance/middlebox/device tests remain later acceptance/certification evidence. |
| 18 | Store / privacy / security | PASS | TCP alone is plaintext and unauthenticated. Security/privacy must come from TLS/IPsec/other parent security layers. Endpoint metadata/logs are minimized; Store policy belongs to the parent network/VPN app. |
| 19 | PVNetwork reuse decision | PASS | **OS TRANSPORT / NO DEDICATED ENGINE.** Reuse the host/runtime TCP stack, preserve engine-specific adapters, and do not implement or market TCP as a VPN protocol. |
| 20 | Open uncertainties / blockers | PASS | Exact congestion algorithm, TFO/MPTCP, keepalive defaults, buffer sizing, mobile background semantics, middlebox behavior, downstream kernel patches, performance and wire/device interoperability remain V2/deployment/certification work. They do not block the V1 architecture/reuse decision. |

## Final V1 decision

All 20 V1 gates are evidence-backed or correctly foundational-transport N/A bounded. Entry 081 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
