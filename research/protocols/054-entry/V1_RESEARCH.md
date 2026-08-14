# 054 — SSH Tunnel — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: mature SSH tunneling/port-forwarding capability.

Decision: **`MATURE STANDARD TUNNEL TARGET / OPENSSH-NATIVE FIRST`**.

Primary source/reference: `openssh/openssh-portable` plus platform-native OpenSSH packages.

PVNetwork must not implement SSH cryptography/protocol from scratch. Use a mature SSH implementation behind a typed adapter.

Keep forwarding modes separate:

- dynamic forwarding / local SOCKS;
- local forwarding;
- remote forwarding as an advanced/admin feature;
- SSH-based transport exposed by selected cores where semantics match.

Host-key verification is mandatory product state, not an optional hidden detail. Private keys/passwords/known-host data require platform secure storage and explicit trust UX.

Later v2 adds exact source/license/version pins, supported cipher/KEX/host-key policy, wire/handshake references, platform install matrix, menus and deployment topologies.
