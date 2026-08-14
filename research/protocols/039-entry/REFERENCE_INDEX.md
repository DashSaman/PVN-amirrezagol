# 039 Trojan — COMPLETE-REFERENCE-v2 index

Reviewed: 2026-08-15

Entry files: `README.md`, `V1_GATE_RECONCILIATION.md`, `SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_V2_AUDIT.md`.

Shared evidence: `research/upstreams/xray-family/` and the pinned Xray deployment/client references from entries 037/038 where genuinely shared.

Pins:
- Xray-core MPL-2.0 `v26.7.28` -> `5ca6f4b7d4dc20a881d4330e498892697627ec0c`; advisory floor v26.7.11.
- Xray-install GPL-3.0 `e741a4f56d368afbb9e5be3361b40c4552d3710d`.
- 3X-UI GPL-3.0 `ad32144c42455696ea9f14e12168beac3e25f5d2`.
- Remnawave AGPL-3.0 `4c222c2db180fd472d9f79a9ddf132e455fe788d`.
- v2rayN GPL-3.0 `230a2f6773d09a12ce4130404aa5571b20de63a2`.
- v2rayNG GPL-3.0 `b348ca792bd26b207c4969fb97c8c384e98f2628`, v2.3.4.
- original Trojan GPLv3 `3e7bb9aecdc694f9bcae8d646fae395f773d60f8`, historical/reference-only.
- Trojan-Go GPLv3 `2dc60f52e79ff8b910e78e444f1e80678e936450`, historical/reference-only.

Reuse decision: `TROJAN SUPPORTED-CANDIDATE / Xray-first / keep password secret and TLS identity/security explicit / original GPL standalone projects reference-only / no silent Trojan->VLESS migration / no crypto reimplementation.`
