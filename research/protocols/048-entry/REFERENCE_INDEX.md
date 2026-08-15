# 048 Snell — COMPLETE-REFERENCE-v2 index

Reviewed: 2026-08-15

Entry dossier: `README.md`, `V1_GATE_RECONCILIATION.md`, `SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_V2_AUDIT.md`.

Authoritative vendor references:
- Surge Knowledge Base `Snell` page — current stable v5.0.1 downloads/release notes, v4 compatibility, v5 QUIC Proxy Mode, egress/systemd, proprietary/anti-reverse-engineering notice.
- Surge Manual `Proxy Policy` — current Snell v4/v5/v6 client configuration, UDP support and v6 beta semantics.

Pins/boundaries:
- official stable server package version: v5.0.1; **binary-only/proprietary, no public source commit/license grant**.
- current v6: beta; compatible client/server beta pair required; not promoted to stable baseline.
- `missuo/opensnell@3100984fd7c3a2bd7b41e292ad41f10d928bfb2d`, GPL-3.0 repo metadata, release v1.0.4, v4/v5 interoperability reference only.
- `icpz/open-snell` historical community reference only.

Reuse decision: `PROPRIETARY SURGE COMPATIBILITY / OFFICIAL DOCS+BINARY REFERENCE / NO OFFICIAL SOURCE REUSE / NO REVERSE-ENGINEERING OR COMMUNITY CODE BUNDLING WITHOUT LEGAL-RIGHTS REVIEW / PSK SECURE / VERSION EXPLICIT / V6 BETA`.
