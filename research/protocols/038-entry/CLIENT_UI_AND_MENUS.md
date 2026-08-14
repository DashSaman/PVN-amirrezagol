# 038 VMess — client UI/menu maps

Reviewed: 2026-08-15

## v2rayN

Pinned source: `2dust/v2rayN@230a2f6773d09a12ce4130404aa5571b20de63a2`, GPL-3.0.

Current architecture exposes profile/server editing, subscriptions, routing, settings, logs and runtime/core control. A VMess user flow is modeled as:

1. add/import a VMess profile or subscription;
2. edit endpoint, port, UUID, effective security and separately selected transport/security fields;
3. preserve source/default/legacy metadata instead of silently converting to VLESS;
4. select profile;
5. configure client-layer routing/system proxy/TUN separately;
6. connect/disconnect and inspect logs/traffic;
7. update subscription/app/core independently.

## v2rayNG

Pinned source: `2dust/v2rayNG@b348ca792bd26b207c4969fb97c8c384e98f2628`, v2.3.4, GPL-3.0.

Major Android surfaces include profile list, manual/import/QR/subscription paths, VMess editor, transport/security settings, Android VPN lifecycle, routing/DNS/per-app behavior, logs and subscription/app/core maintenance.

## UI safety requirements derived

- show VMess as its own protocol, never as an alias for VLESS;
- distinguish explicit imported values from core defaults;
- surface clock-skew/auth failures distinctly because current VMess AEAD authentication is time-sensitive;
- redact UUID/profile secrets from support export where not required;
- keep TUN/system proxy and outer TLS/transport choices separate from VMess account/security semantics.

GPL client UIs are research/UX references by default, not proprietary-product drop-in code.
