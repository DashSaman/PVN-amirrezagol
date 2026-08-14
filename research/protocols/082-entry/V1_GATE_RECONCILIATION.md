# 082 — UDP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **082 — UDP**

Decision: **`COMPLETE-RESEARCH-v1 / FOUNDATIONAL OS DATAGRAM TRANSPORT / NO DEDICATED VPN ENGINE / NOT RELIABLE / NOT ENCRYPTED / NOT IMPLEMENTED / NOT CERTIFIED`**

## Current standards baseline

- RFC 768 — *User Datagram Protocol*, STD 6, Internet Standard:
  `https://www.rfc-editor.org/info/rfc768/`
- RFC 9868 — *Transport Options for UDP* (2026), which **updates RFC 768** by defining an extension space for UDP transport options:
  `https://www.rfc-editor.org/info/rfc9868/`
- RFC 8085 — *UDP Usage Guidelines*, BCP 145:
  `https://www.rfc-editor.org/info/rfc8085/`

UDP is a minimal, connectionless, unreliable datagram service. It has no inherent congestion control, reliability, confidentiality or peer authentication. Applications/tunnels using UDP are responsible for appropriate congestion behavior, message sizing/reliability/security as applicable. RFC 9868 support must be capability-gated; its existence does not prove universal host/network deployment.

## Selected implementation references

- Linux: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, tree `cdfb6ad04701df82290575494f40fbb00efe0512`, relevant `net/ipv4/udp.c`, IPv6/common socket paths; root `GPL-2.0 WITH Linux-syscall-note`.
- Go: `golang/go@c19862e5f8415b4f24b189d065ed739517c548ba` (go1.26.5), tree `0bb2fb1cc06c334c36a2a92d2f0b07fea7236d74`, BSD-3-Clause, `src/net/` UDP APIs/tests.
- Xray: `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0, host/runtime UDP integration.

Current Linux UDP path maintenance was reviewed. The pinned source is newer than `3c94f241f776562c489876ff506f366224565c21` (2026-06), which clears repurposed `skb->dev` state before a sockmap verdict to prevent a kernel fault in a UDP+BPF lookup path and is marked for stable. Upstream release history also removed UDP-Lite support in the Linux 7.1 development cycle; UDP and UDP-Lite must not be conflated.

## 20-gate reconciliation

|#|Gate|Result|Evidence / decision|
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel is the primary OS datagram implementation reference; Go `net` is the primary Go application API; Xray is a product integration reference. Other OS stacks are separately pinned if directly relied upon.|
|2|Sources pinned|PASS|RFC768 + current RFC9868 update + RFC8085 guidance + exact Linux/Go/Xray pins.|
|3|Licenses|PASS|Linux syscall/API versus GPL source-copy boundary, Go BSD-3-Clause and Xray MPL-2.0 are explicit. PVNetwork uses host/runtime APIs, not a copied UDP stack.|
|4|Source tree|PASS|Complete Linux/Go/Xray recursive trees are pinned; UDP/socket/build/test paths are traceable.|
|5|Languages/build|PASS|Linux C/Kbuild; Go runtime/network library; Xray Go adapter. Kernel/distro packaging and product packaging remain separate.|
|6|Architecture|PASS|Application datagram -> OS UDP socket -> UDP/IP -> peer; no connection/retransmission/congestion/security state is supplied by base UDP. Parent QUIC/KCP/tunnel/security protocols own their added semantics.|
|7|Engine integration|PASS|Use native OS/runtime datagram APIs. Do not implement a custom UDP stack. UDP options/offloads/socket controls are exact-platform capabilities.|
|8|UI/menu|PASS/N-A|Foundational transport only; parent profiles may select UDP or expose advanced MTU/buffer/socket policy when justified. No standalone consumer VPN card.|
|9|Config/import/export/URI/QR|PASS|Parent profiles carry destination/port/network=udp and engine-specific options. No standalone `udp://` VPN subscription/QR is invented. Ephemeral socket state is not profile data.|
|10|Persistence/secrets|PASS|UDP itself has no credential. Endpoint/port metadata can be sensitive topology data; parent protocol keys remain separately owned.|
|11|Platforms|PASS for research|Linux/Go paths are pinned; Windows/Apple/Android stacks and offload/background behavior differ. RFC9868 transport options require explicit implementation support before use.|
|12|Logs/diagnostics|PASS|Differentiate DNS, unreachable/ICMP, send/receive timeout, truncation/message-size/MTU, packet loss/reordering, NAT/firewall, socket resource and parent-protocol failures.|
|13|Assets/localization|PASS/N-A|No canonical UDP application assets or Store listing.|
|14|Forks/alternatives|PASS|TCP, QUIC, DTLS, KCP/mKCP are separate transports/layers. UDP-Lite is not UDP and current Linux upstream removed its support; no silent aliasing.|
|15|Issues/releases/advisories|PASS|2026 Linux UDP fault-hardening fix and current networking release changes were reviewed. Exact downstream kernel/runtime patch level remains deployment evidence.|
|16|Official docs|PASS|RFC768/9868/8085 plus pinned Linux/Go source/docs are primary; folklore tuning is not product policy.|
|17|Tests/CI|PASS|Linux networking selftests and Go net tests are source-visible; Xray shared tests are mapped. Loss/NAT/device/performance testing remains later acceptance evidence.|
|18|Store/privacy/security|PASS|UDP does not encrypt/authenticate and can amplify spoofing/resource risks depending on the application. Parent protocols must implement security and congestion behavior; logs/topology are minimized.|
|19|Reuse decision|PASS|**OS DATAGRAM TRANSPORT / NO DEDICATED ENGINE.** Reuse the host/runtime UDP stack and parent-protocol reliability/security logic.|
|20|Open uncertainties|PASS|RFC9868 deployment support, GSO/GRO/offload, buffer defaults, NAT rebinding, path MTU, multicast, platform APIs, performance and device/network behavior remain V2/deployment/certification work.|

## Final V1 decision

All 20 gates are evidence-backed or correctly foundational-transport N/A bounded. Entry 082 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
