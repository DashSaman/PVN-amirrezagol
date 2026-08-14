# OpenVPN — Server UI / Administration Menus / Management Surfaces

Reference layer: `COMPLETE-REFERENCE-v2`

Research date: 2026-08-14

State: `IN-RESEARCH` — source-backed architecture and primary menu domains are recorded; exact labels/screenshots must remain pinned to product version.

## Important distinction

OpenVPN Community Server does **not** define one universal graphical admin menu.

Server management can be through:

- configuration files;
- system service manager;
- command line;
- management interface/plugins/scripts;
- third-party panels;
- OpenVPN Access Server Admin Web UI.

Therefore this document has separate menu inventories per product/control plane.

---

# 1. OpenVPN Community Server — configuration/admin surface

Primary source: `OpenVPN/openvpn` + current Community manual.

## Administration domains

### Process / service

- start/stop/restart daemon or profile instance;
- enable/disable at boot;
- inspect service status;
- inspect journal/logs;
- instance/config selection.

Exact service names vary by OS/package and must be recorded in `SERVER_INSTALL_MATRIX.md` receipts.

### Listener / transport

Configuration domains include:

- server/listener address;
- port;
- UDP/TCP transport;
- IPv4/IPv6 behavior;
- tun/tap device;
- server mode/topology.

### PKI / TLS

- CA certificate;
- server certificate/private key;
- DH/EC/TLS options depending on version/config;
- tls-auth / tls-crypt-style control-channel protection;
- CRL/revocation;
- remote/client certificate requirements;
- TLS version/cipher policy.

### Data channel

- data ciphers/cipher negotiation;
- DCO capability where supported;
- MTU/mss/fragment-related advanced controls;
- compression legacy policy;
- replay/rekey/session behavior.

### Authentication / authorization

- certificate identity;
- username/password plugin/script/module integrations;
- per-client configuration directory;
- client-specific routes/options;
- external auth/MFA integrations depending on deployment.

### Addressing / routing

- VPN address pools;
- topology;
- route statements;
- pushed routes;
- redirect/default-route behavior;
- client-to-client behavior;
- IPv6 routes/pools.

### DNS / pushed client options

- pushed DNS servers/options according to client/platform support;
- domain/search/split-DNS-related options;
- Windows-specific DHCP options where applicable.

### Security / privileges

- user/group privilege drop;
- chroot/sandbox-like controls where supported;
- script security;
- management interface controls;
- duplicate-client policy;
- connection limits/timeouts/keepalive.

### Logging / status

- verbosity;
- log/status files;
- management/status output;
- client connection state;
- statistics.

### Scripts/plugins/hooks

- client connect/disconnect hooks;
- auth scripts/plugins;
- route-up/down hooks;
- management/control integrations.

PVNetwork server automation must never enable arbitrary hook execution from untrusted imported config without explicit admin policy.

---

# 2. OpenVPN Access Server — Admin Web UI

Official source: current OpenVPN Access Server documentation (`openvpn.net/as-docs/`).

The exact visible labels/order can change by Access Server release. Pin the product version when taking screenshots/menu inventories.

## Primary admin domains to inventory/version-pin

### Status / overview

Expected product functions include:

- server status/health;
- connected users/sessions;
- license/subscription state;
- service/listener status;
- high-level diagnostics.

### Configuration — Network / VPN settings

Administration can include product controls for:

- VPN networks/address pools;
- routing/private subnet access;
- NAT/routed access;
- DNS settings;
- client Internet/default-route behavior;
- IPv6 where supported;
- protocol/listener/port behavior;
- server hostname.

### Configuration — Authentication

Product domains can include:

- local authentication;
- PAM/system authentication;
- LDAP/Active Directory integrations;
- RADIUS;
- SAML/SSO or external authentication according to product version/licensing;
- MFA/TOTP-related options;
- certificate/client-profile authentication.

Every visible option must later be copied into a version-pinned menu snapshot from official docs/product lab, not inferred generically.

### User / group permissions

- users;
- groups;
- per-user/per-group access;
- routing/subnet permissions;
- profile/access policy;
- admin privilege controls.

### TLS / certificates / web services

- Access Server web certificate;
- VPN server certificate/PKI behavior;
- Admin Web UI listener;
- Client Web UI listener;
- hostname/certificate settings.

### Logging / reports / diagnostics

- server/service logs;
- authentication/session logs;
- connected clients;
- diagnostic/support information;
- configuration backup/restore where exposed.

### Advanced / command-line-only configuration

Some Access Server controls are managed through command-line/configuration APIs rather than the visible Admin UI. Keep a separate versioned `CLI_ONLY_SETTINGS` appendix later rather than inventing UI fields.

## Admin Web UI security checklist

- bind addresses and ports recorded;
- TLS certificate/hostname valid;
- admin account creation/password policy;
- MFA/SSO if enabled;
- no default/shared credentials left active;
- firewall restricts Admin UI where appropriate;
- session timeout/cookies/browser security reviewed;
- exact Access Server version patched;
- backup contains secrets and is protected;
- API/CLI credentials redacted.

---

# 3. Access Server Client Web UI

This is a **user provisioning portal**, not the Admin UI.

Inventory domains include:

- user sign-in;
- client/profile download;
- OpenVPN Connect download/deep-link/provisioning where offered;
- connection profile/user-locked profile options according to product/version;
- password/MFA/SSO interactions;
- user-visible support/instructions.

PVNetwork integration should consume compatible profiles/account provisioning through documented interfaces, not scrape the web UI.

---

# 4. Pritunl web administration

Reference: current pinned/versioned `pritunl/pritunl` server product after license/source review.

High-level menu/domain inventory to version-pin from source/product lab:

- Organizations
- Users
- Servers
- Routes
- Hosts / server nodes where applicable
- Links / site-to-site features where applicable
- Authentication / SSO / MFA settings
- Settings / system configuration
- Logs / events / status
- Profiles/key downloads
- certificates/PKI/service state

Exact names/order/features vary by edition/version and must not be copied from an old screenshot into the canonical reference.

### PVNetwork lesson

A future PVNetwork Server Manager can learn from Pritunl's separation of users/organizations/servers but should not copy its brand or UI.

---

# 5. PiVPN interactive administration

PiVPN is primarily installer/CLI-management UX rather than a large always-on web panel.

Menu/action categories to pin from exact script version include:

- install/configure;
- add client;
- revoke/remove client;
- list clients;
- generate/export profile/QR where applicable;
- debug/status;
- update;
- uninstall/reconfigure.

Exact command/menu names differ by version and VPN backend; record only source-backed labels in the final menu snapshot.

---

# 6. Angristan / Nyr installer prompts

These projects are installer/admin scripts rather than permanent server UIs.

Version-pinned prompt inventory should capture:

- detected interface/public address;
- IPv6 choice;
- listener port;
- transport UDP/TCP;
- DNS resolver selection;
- compression/customization choices if present in that revision;
- first client name;
- later add/revoke/remove/uninstall actions.

Do not treat their defaults as OpenVPN protocol defaults.

---

# 7. PVNetwork future server UI — recommended separation

Based on the ecosystem, the future product should have an independent server-management design:

## Overview

- server health/version/security status;
- connected clients;
- listener status;
- update status;
- certificate expiry.

## VPN Service

- protocol/listener;
- server networks;
- IPv4/IPv6;
- topology;
- DCO/data path;
- secure cipher/TLS policy.

## Users & Devices

- users/groups;
- client certificates/devices;
- revoke/disable;
- MFA/SSO identity provider.

## Routing & DNS

- advertised/pushed routes;
- Internet/default route;
- private subnets;
- NAT vs routed mode;
- DNS/split DNS.

## Certificates & PKI

- server cert;
- CA/external CA;
- client certs;
- revocation;
- expiry/rotation.

## Security

- TLS policy;
- data cipher policy;
- legacy compatibility warnings;
- admin access controls;
- script/plugin policy.

## Logs & Diagnostics

- auth/session logs;
- server log;
- route/firewall state;
- sanitized support bundle.

## Backup / Upgrade / Maintenance

- encrypted backup;
- restore;
- package/core update;
- rollback;
- uninstall/decommission.

Never expose raw arbitrary shell commands as normal admin controls.

---

# Version-pinned exhaustive-menu requirement

Before OpenVPN v2 can be called exhaustive for UI, add a lab/source snapshot for each selected product version:

```text
Product:
Version:
Source/docs revision:
Platform/browser:
Top-level menu:
Submenu:
Control/field:
Type:
Default:
Validation:
Secret?:
Restart/reconnect required?:
Underlying config/API mapping:
Screenshot/reference:
```

This file provides the hierarchy; final exhaustive rows still require actual pinned product versions/screenshots/source resources.
