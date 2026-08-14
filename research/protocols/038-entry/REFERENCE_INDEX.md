# 038 VMess — COMPLETE-REFERENCE-v2 index

Reviewed: 2026-08-15

## Entry-local dossier

- `README.md`
- `V1_GATE_RECONCILIATION.md`
- `SERVER_IMPLEMENTATIONS.md` — gate 1
- `SERVER_INSTALLERS_AND_PROJECTS.md` — gates 2, 12, 13
- `SERVER_INSTALL_MATRIX.md` — gate 3
- `SERVER_UI_AND_MENUS.md` — gate 4
- `CLIENT_INSTALL_MATRIX.md` — gates 5, 11
- `CLIENT_UI_AND_MENUS.md` — gate 6
- `CRYPTOGRAPHY.md` — gate 7
- `DATA_PATH_AND_WIRE_FLOW.md` — gate 8
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md` — gate 9
- `DEPLOYMENT_TOPOLOGIES.md` — gate 10
- `REFERENCE_V2_AUDIT.md` — gates 14-16 and exact decision

## Shared evidence reused without changing protocol boundaries

- `research/upstreams/xray-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/xray-family/CONFIG_CAPABILITY_MODEL.md`
- `research/upstreams/xray-family/CLIENT_ECOSYSTEM.md`
- entry 037 deployment/client/panel source pins where the Xray runtime is genuinely shared

## Current source/activity pins

- `XTLS/Xray-core` — MPL-2.0 — `v26.7.28` -> `5ca6f4b7d4dc20a881d4330e498892697627ec0c`; moving main observed `7d214f8b094f75322fa3990f8aadad1c912f24f5`.
- Xray advisory GHSA-5wf9-h793-w73c — fixed from `v26.7.11`; fix commit `a75a0184d60a2fb6dbc76edaa0c6b48f77e939e2`.
- `XTLS/Xray-install` — GPL-3.0 — `e741a4f56d368afbb9e5be3361b40c4552d3710d`.
- `MHSanaei/3x-ui` — GPL-3.0 — `ad32144c42455696ea9f14e12168beac3e25f5d2`.
- `remnawave/panel` — AGPL-3.0 — `4c222c2db180fd472d9f79a9ddf132e455fe788d`.
- `2dust/v2rayN` — GPL-3.0 — `230a2f6773d09a12ce4130404aa5571b20de63a2`.
- `2dust/v2rayNG` — GPL-3.0 — `b348ca792bd26b207c4969fb97c8c384e98f2628`, v2.3.4.

## Reuse decision

`VMESS COMPATIBILITY TARGET / Xray-core strong engine candidate under MPL-2.0 subject to final dependency/package review / do not implement crypto from scratch / keep VMess AEAD and its no-forward-secrecy limitation explicit / keep outer transport and TLS-REALITY layers separate / GPL-AGPL panels and GPL GUI clients are reference-only by default / preserve legacy imports but never silently migrate VMess to VLESS.`
