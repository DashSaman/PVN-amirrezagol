# 042 Hysteria v1 — COMPLETE-REFERENCE-v2 index

Reviewed: 2026-08-15

Entry dossier: `README.md`, `V1_GATE_RECONCILIATION.md`, `SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_V2_AUDIT.md`.

Pins:
- `apernet/hysteria` legacy tag `v1.3.5` -> `57c5164854d6cfe00bead730cce731da2babe406`, tree `f337850416be8834f2276118e0ce8a2630bd67ee`, release 2023-06-12.
- license: MIT source; executable built with `-tags gpl` must be GPLv3.
- pinned v1 dependency identity includes `apernet/quic-go v0.34.1-0.20230507231629-ec008b7e8473`.
- current upstream README explicitly labels Hysteria 1.x as legacy.

Reuse decision: `LEGACY IMPORT/INTEROP TARGET / exact v1 runtime only if demanded / no v2 parser substitution / TLS certificate validation mandatory by default / record build-tag license branch + full dependency SBOM.`
