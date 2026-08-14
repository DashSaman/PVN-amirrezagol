# 011 — SSTP / MS-SSTP — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: TLS-based remote-access VPN tunneling protocol carrying PPP.

Research decision:

**`COMPATIBILITY REMOTE-ACCESS TARGET / WINDOWS-NATIVE-FIRST / LINUX SSTP-CLIENT CANDIDATE`**

Preferred direction:

- Windows: built-in SSTP/RAS VPN stack first;
- Linux: evaluate `sstp-client/sstp-client` and NetworkManager integration;
- Android/iOS/macOS/TV: no support claim until a maintained, legally compatible, Store-compatible engine is proven.

Open-source `sstp-client` root license reviewed as GPLv2 family, so direct embedding in a closed PVNetwork app requires deliberate distribution/legal architecture.

PVNetwork must distinguish TLS/certificate failure, SSTP negotiation, PPP negotiation/authentication, address/DNS assignment and route/platform errors.

Shared evidence:

- `research/upstreams/classic-tunnels-family/SSTP_CLIENT.md`
- `DEPENDENCIES_SECURITY_TESTS.md`
- `SUPPORT_REUSE_DECISIONS.md`

Later v2 must add full cryptography/TLS/PPP/wire flow, ports/handshake, server implementations/installers, OS install matrices and complete client/server menus.
