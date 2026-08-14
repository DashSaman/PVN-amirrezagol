# 034 — Citrix Secure Access / NetScaler Gateway VPN — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT NETSCALER-CERTIFIED`**.

Decision: **`VENDOR SSL/TLS-DTLS REMOTE-ACCESS REFERENCE / NETSCALER-GATEWAY POLICY+AUTH+EPA+ROUTING SEMANTICS DISTINCT / FIRST-PARTY CLIENT REFERENCE-ONLY / PLATFORM-SPECIFIC ADAPTER REQUIRED / NO GENERIC TLS COMPATIBILITY CLAIM / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`**.

All 20 original V1 research gates are reconciled in `V1_GATE_RECONCILIATION.md`, with current Citrix and NetScaler evidence consolidated in `CITRIX_SECURE_ACCESS_CURRENT_AUDIT.md`.

Key boundaries:

- Current first-party clients cover Windows, macOS, iOS, Android and Linux with platform-specific feature parity.
- NetScaler Gateway is the canonical classic VPN termination/control product; SSL/TLS full VPN can use DTLS with documented TLS fallback and version/platform limits.
- nFactor, EPA, session policy, intranet applications, full/split/reverse tunnel and split DNS are vendor policy surfaces, not generic TLS semantics.
- Current 2026 vendor release history is pinned in the dossier; no canonical public complete-source repository was identified, so a source SHA must not be invented.
- First-party code/binaries/UI/branding remain proprietary/reference-only absent component-specific licensing.

Do not infer Citrix/NetScaler VPN compatibility solely from TLS/DTLS support. A distinct vendor adapter/reference boundary is required for authentication, posture, policy, routing/DNS and lifecycle.