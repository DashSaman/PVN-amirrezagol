# 035 — Barracuda TINA VPN — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT BARRACUDA-CERTIFIED`**.

Decision: **`PROPRIETARY BARRACUDA TINA VENDOR ADAPTER/REFERENCE / OFFICIAL CLIENT REQUIRED FOR TINA INTEROP UNTIL A LEGITIMATE REUSABLE IMPLEMENTATION IS PROVEN / DO NOT SUBSTITUTE GENERIC IPSEC / PRESERVE TRANSPORT+AUTH+PROFILE+FAILOVER SEMANTICS / FIRST-PARTY CODE REFERENCE-ONLY / MODERN CRYPTO POLICY REQUIRED`**.

All 20 original V1 research gates are reconciled in `V1_GATE_RECONCILIATION.md`, with current Barracuda evidence consolidated in `BARRACUDA_TINA_CURRENT_AUDIT.md`.

Key boundaries:

- TINA is explicitly proprietary and distinct from standard IPsec interoperability.
- Current Barracuda docs expose UDP, TCP, UDP+TCP hybrid, ESP and routing transports, with TCP 691/443 proxy behavior documented.
- Current client-to-site authentication includes user/password, X.509, X.509+password, `.lic` files, SAML and TOTP surfaces depending on deployment.
- Current CloudGen documentation exposes modern AES/GCM/SHA2-family choices alongside legacy algorithms that PVNetwork must not recommend as modern defaults.
- Barracuda VPN Client is the canonical TINA client; Android CudaLaunch includes TINA, while iOS CudaLaunch uses native IPsec and must not be mislabeled as TINA.
- No canonical public complete-source repository was identified; first-party code, binaries and protocol implementation remain proprietary/reference-only.

Do not infer TINA support from generic IPsec support.