# 044 TUIC v5 — COMPLETE-REFERENCE-v2 index

Reviewed: 2026-08-15

Entry dossier: `README.md`, `V1_GATE_RECONCILIATION.md`, `SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_V2_AUDIT.md`.

Pins:
- spec/governance `tuic-protocol/tuic@8e118f242f24a17a9f487dc344cc50d7e63e557e`, tree `3dab59619e77fe44d4f97b534e7b8ea9a0e96475`, GPL-3.0 repository; protocol concept separately declared license-free by upstream README; `SPEC.md` blob `fe246d88e57e306e767265230fa178640950060a`, v0x05.
- ClashRS `b0538e86aedcbe7f000bb9f00889175ffb85176c`, Apache-2.0.
- shoes `7a5a8ee3bd1c52bc15ec57e074e95e374d41f275`, MIT.
- Itsusinn/tuic `0eef0b1d62758bb63f954a81f7ac74b94ed9da29`, tree `cfd1d3bf38c5eeb3ba72de5f65fb737e5ef7c8a7`, GPL/copyleft component review required.

Reuse decision: `MODERN QUIC PROXY TARGET / protocol concept open but implementations separately licensed / evaluate Apache ClashRS and MIT shoes first / raw password protected / TLS verify on / version=0x05 explicit / 0-RTT replay-aware / no home-grown QUIC-TLS.`
