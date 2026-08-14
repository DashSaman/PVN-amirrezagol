# 038 VMess — server UI and menu maps

Reviewed: 2026-08-15

## Bare Xray-core

Xray-core has no canonical first-party web panel. Bare server operation is JSON/CLI/API driven, so a first-party web-menu map is evidence-backed N/A while the configuration surface is applicable.

VMess inbound configuration includes:

- listener address/port;
- `protocol: vmess`;
- users/clients including UUID ID, level/email and current account security behavior;
- separate `streamSettings` transport/security;
- routing/DNS/log/policy/API as separate Xray layers.

Current `infra/conf/vmess.go` accepts VMess account `id`, `security`, `experiments`; security maps to AES-128-GCM, ChaCha20-Poly1305 or auto. Current Xray emits a deprecation warning for VMess because it lacks Forward Secrecy and points to VLESS Encryption.

## 3X-UI reference panel

Pinned `MHSanaei/3x-ui@ad32144...`, GPL-3.0.

Major operator surfaces relevant to VMess:

- dashboard / runtime status;
- **Inbounds** -> add/edit inbound -> select VMess;
- listener, client UUID, quota/expiry and transport/security options supported by the bundled Xray version;
- add/edit client;
- routing/outbounds;
- subscriptions;
- nodes/multi-node features where enabled;
- settings, credentials, certificates, logs/service controls.

## Remnawave reference panel

Pinned `remnawave/panel@4c222c...`, AGPL-3.0. It supplies Xray node/config/log/subscription/auth management. Its panel concepts are not VMess wire semantics and must not become PVNetwork's canonical schema by accident.
