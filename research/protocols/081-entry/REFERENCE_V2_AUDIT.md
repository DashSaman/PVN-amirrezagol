# 081 — TCP — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / FOUNDATIONAL OS TRANSPORT / NO STANDALONE PVNETWORK SERVER OR CLIENT / NO CRYPTO`**

## Authority and pins

- RFC 9293 — current base TCP Internet Standard.
- Linux reference: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, tree `cdfb6ad04701df82290575494f40fbb00efe0512`, GPL-2.0 WITH Linux-syscall-note at the root with file-level SPDX authoritative.
- Go reference: `golang/go@c19862e5f8415b4f24b189d065ed739517c548ba` (go1.26.5), tree `0bb2fb1cc06c334c36a2a92d2f0b07fea7236d74`, BSD-3-Clause.
- Xray integration: `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0.

TCP is a reliable ordered byte-stream transport. It is not a VPN/security protocol and supplies no confidentiality or peer authentication.

## Exact 16-gate reconciliation

|#|Gate|Result|Evidence / boundary|
|---:|---|---|---|
|1|Server ecosystem mapped|PASS / N-A|The meaningful “servers” are ordinary OS TCP stacks and applications listening on TCP sockets. No dedicated TCP VPN server product exists. Linux kernel is the selected open-source stack reference.|
|2|Installers/deployment projects|PASS / N-A|TCP ships as part of host OS networking. No separate TCP installer should be invented. Kernel/runtime/application deployment is owned by the parent OS/app.|
|3|Server install matrix|PASS|Linux is explicitly pinned; other Windows/Apple/BSD stacks are platform implementations, not separate protocol products. Container/orchestration use host/container networking and does not install a special TCP server.|
|4|Server UI/menu map|PASS / N-A|No canonical TCP server panel. Listener/bind/backlog/socket options belong to parent application/admin UI.|
|5|Client install matrix|PASS|No separate TCP client package; support follows the host OS/runtime and selected engine. Exact mobile/background/TFO behavior is platform implementation/certification evidence.|
|6|Client UI/menu map|PASS / N-A|No standalone TCP client UI. Parent profiles may select TCP and expose justified timeout/keepalive/socket controls only when the selected engine supports them.|
|7|Cryptography|PASS / N-A|TCP has no cryptography. TLS/IPsec/other layers own encryption and authentication.|
|8|Data path/wire flow|PASS|Application bytes -> socket API -> TCP state machine/reliability/congestion/loss recovery -> IP -> peer TCP -> application. DNS/TLS/proxy framing are separate layers.|
|9|Ports/transports/handshake|PASS|Port is selected by the parent application; TCP uses the TCP three-way connection establishment and reliable byte stream semantics from RFC 9293. No protocol-specific fixed VPN port exists.|
|10|Deployment topologies|PASS|Applies wherever parent applications use client/server, site-to-site, proxy, control-plane or data-plane TCP. TCP itself does not define those topologies.|
|11|Source/license/activity pins|PASS|Exact Linux/Go/Xray pins and licenses above; standards authority RFC 9293.|
|12|Supply-chain/security risks|PASS|No dedicated installer. Risks move to kernel/runtime/application provenance and patch level; do not ship a custom TCP stack or unpinned tuning script.|
|13|Upgrade/uninstall/rollback|PASS|Owned by OS/runtime/parent application. Socket configuration can be reverted; there is no independent TCP package lifecycle.|
|14|Differences/uncertainties|PASS|OS stacks differ in defaults, congestion algorithms, TFO/MPTCP, keepalive, buffer policy and downstream patches. Those differences are explicit and capability-gated.|
|15|Reference index|PASS|Companion `REFERENCE_INDEX.md` records this boundary and exact pins.|
|16|Handoff continuation|PASS|Batch handoff advances to Entry 082 UDP after promotion.|

## Security / diagnostics boundary

Diagnostics distinguish DNS/connect/refused/unreachable/SYN timeout/reset/idle/read/write timeout/EOF/MTU/resource errors from TLS/proxy/application failures. TCP must never be marketed as encrypted. Endpoint and flow metadata remain privacy-sensitive.

## Final decision

All 16 V2 research/reference gates are evidence-backed or correctly foundational-transport N/A bounded. Entry 081 qualifies for **`COMPLETE-REFERENCE-v2`** without claiming implementation/device/Store certification.
