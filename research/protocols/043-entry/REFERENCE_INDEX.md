# 043 Hysteria2 — COMPLETE-REFERENCE-v2 index

Reviewed: 2026-08-15

Entry files: `README.md`, `V1_GATE_RECONCILIATION.md`, `SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_V2_AUDIT.md`.

Pins:
- `apernet/hysteria@14e9fff1d972ab0187ac7fcf75b9514dc8664065`, tree `39ad00e06933ebcc3077e825cc0ac969875a03cd`, MIT.
- stable release `app/v2.12.1`, 2026-08-09; official `hashes.txt` asset plus GitHub asset SHA-256 digests.
- protocol spec `PROTOCOL.md` at same commit.
- reviewed advisories `GHSA-vgrc-hq28-p3xp` and `GHSA-qh5x-rfwf-rvfv`, both establishing patched floor 2.9.2 for their issues.

Reuse decision: `HIGH-PRIORITY MODERN QUIC PROXY / official MIT engine strong candidate / pin app+core+extras+QUIC+SBOM / QUIC-TLS and HTTP3 auth distinct from Salamander obfs / unsafe TLS override off by default / no v1 semantic inheritance.`
