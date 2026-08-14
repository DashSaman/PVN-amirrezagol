# 041 Shadowsocks 2022 — COMPLETE-REFERENCE-v2 index

Reviewed: 2026-08-15

Entry dossier: `README.md`, `V1_GATE_RECONCILIATION.md`, `SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_V2_AUDIT.md`.

Pins:
- SS2022 spec `Shadowsocks-NET/shadowsocks-specs@20b4952e8a54e696ebcabc5f91b5dad7f322f2da`, tree `b98edead15d26bc345f20cf5f776dfdbcf893fc0`.
- `shadowsocks/shadowsocks-rust@9214fdaf1f8938a20f6c295b1260c69a625d1f4f`, MIT, source 1.25.0; stable `v1.24.0` -> `7ee1aa9223ed8f4d34734aac919036c8ad4502c2`.
- Android `shadowsocks/shadowsocks-android@ae28fd91931fe4d2d5aab044de9ceaf9ed07ad56`, GPL-3.0-or-later text, SS2022 feature enabled in embedded rust core.

Reuse decision: `MODERN SHADOWSOCKS TARGET / dedicated MIT shadowsocks-rust strong engine candidate / fixed-length base64 PSK + EIH typed semantics / no forward secrecy claim / no classic password conversion / no crypto reimplementation.`
