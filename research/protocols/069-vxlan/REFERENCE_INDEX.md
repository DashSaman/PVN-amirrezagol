# VXLAN — Reference Index

Reviewed: 2026-08-15

Files: `SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_V2_AUDIT.md`, existing `V1_RESEARCH.md`.

Canonical evidence:
- RFC 7348: https://www.rfc-editor.org/rfc/rfc7348.html
- Linux VXLAN docs: https://docs.kernel.org/networking/vxlan.html
- Linux source pin: `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `drivers/net/vxlan/vxlan_core.c`, SPDX GPL-2.0-only.
- iproute2 pin: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`.

Boundaries: bare VXLAN has no intrinsic crypto; VNI is not a secret; IANA destination is UDP 4789 while Linux historical default may be 8472; consumer UI/server concepts are N/A; VXLAN-over-IPsec is entry 070.

Next: entry 070 VXLAN over IPsec.