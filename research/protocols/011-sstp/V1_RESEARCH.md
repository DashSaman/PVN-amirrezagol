# 011 — SSTP / MS-SSTP — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Classification: TLS-based remote-access VPN tunneling protocol carrying PPP.

Formal original-v1 gate reconciliation:

- `research/protocols/011-sstp/V1_GATE_RECONCILIATION.md`

Canonical/current Linux client source correction:

- `research/upstreams/classic-tunnels-family/SSTP_CLIENT_SOURCE_PIN_2026-08-14.md`
- canonical project: `sstp-project/sstp-client` on GitLab
- research tag pin: `1.0.20` (`dd243124` short identifier shown by canonical GitLab)

Research decision:

**`COMPATIBILITY REMOTE-ACCESS TARGET / WINDOWS-NATIVE-FIRST / LINUX SSTP-CLIENT CANDIDATE`**

Preferred direction:

- Windows: built-in SSTP/RAS VPN stack first;
- Linux: tagged `sstp-client` + PPP/NetworkManager integration candidate with explicit GPL distribution architecture;
- Android/iOS/macOS/TV: no support claim until a maintained, legally compatible, Store-compatible engine is proven.

PVNetwork must distinguish TCP/reachability, TLS/certificate failure, SSTP negotiation, PPP negotiation/authentication, address/DNS assignment and route/platform errors.

Shared evidence:

- `research/upstreams/classic-tunnels-family/SSTP_CLIENT.md`
- `research/upstreams/classic-tunnels-family/DEPENDENCIES_SECURITY_TESTS.md`
- `research/upstreams/classic-tunnels-family/SUPPORT_REUSE_DECISIONS.md`
- `research/upstreams/classic-tunnels-family/sstp-reference-v2/`

Research-complete uncertainties retained for later source-freeze/implementation/certification:

- full long object SHA/archive digest for the 1.0.20 Linux-client tag;
- exact selected distro/package dependency set;
- live Windows/Linux/RRAS/SoftEther interoperability;
- proxy/certificate/crypto-binding/PPP method runtime matrix;
- mobile/macOS engine selection;
- performance/MTU/TCP-over-TCP behavior;
- Store/release verification.

These are not hidden original-v1 research gates. The complete 20-item reconciliation is in `V1_GATE_RECONCILIATION.md`.
