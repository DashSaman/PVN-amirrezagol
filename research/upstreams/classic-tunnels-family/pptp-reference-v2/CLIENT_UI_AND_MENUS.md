# PPTP — Client UI and Menu Maps

Review date: 2026-08-14

Entry: 012 PPTP.

PPTP must appear only in explicit legacy/compatibility UI. It should not be promoted beside modern protocols without a security warning and migration path.

## 1. Windows native PPTP client

Current Windows VPN profile concepts include:

- Connection Name;
- Server name/address;
- VPN type / PPTP;
- authentication/sign-in method;
- username/password/domain where applicable;
- Remember credentials;
- split tunnel/routes through supported native profile tooling;
- connection state/connect/disconnect.

Exact Settings labels vary by Windows release; capture per certified build.

### Recommended PVNetwork Windows UX

Header:

`PPTP — Obsolete Legacy Compatibility`

Fields:

- Name
- Server
- User Credential Reference
- Authentication Method — backend-approved choices only
- MPPE Requirement/Effective State — advanced/read-only where native backend owns it
- Full/Split routes
- DNS
- Native Profile Status
- Connect / Disconnect
- Migration Target
- Diagnostics.

No generic `Disable encryption` convenience toggle.

## 2. Windows system-owned state

Windows Settings/native networking can connect/disconnect/delete the profile independently.

PVNetwork must reconcile:

- profile removed externally;
- native credential prompt;
- connection changed externally;
- route/DNS changes;
- OS update changes PPTP availability/policy.

## 3. Android native legacy UI

Do not hard-code one historical Android screen. Actual current device/OEM/version behavior must be discovered at runtime/lab.

If a supported device exposes PPTP:

- place under Legacy/Compatibility;
- warn that security is obsolete;
- use system-owned profile flow where possible;
- show migration option;
- do not add custom weak-auth defaults.

## 4. Apple UI

macOS Sierra+ and iOS 10+ native PPTP support was removed. PVNetwork should not show a native PPTP setup flow on current Apple platforms.

If a third-party legacy implementation is ever selected, it needs a fully separate source/security/store/UI review.

## 5. Linux legacy client UI

A typed UI above a selected pptp-client/pppd/NetworkManager stack should expose:

- Legacy warning;
- server/gateway;
- username/domain;
- credential reference;
- allowed PPP auth methods;
- MPPE required/effective status;
- routes/DNS;
- advanced GRE/NAT/helper diagnostics;
- backend/version;
- logs.

Do not use raw pppd command lines as normal UI.

## 6. RouterOS client UI

Current RouterOS client configuration should be mapped on the exact selected release through WinBox/WebFig/CLI, including:

- connect-to server;
- user/password;
- profile/auth/encryption-related settings;
- add-default-route/routing parameters;
- status/counters;
- security warning.

Do not copy RouterOS UI branding/assets.

## 7. Layered connection state

Recommended labels:

- `ConnectingControlTcp1723`
- `ControlEstablished`
- `EstablishingPptpCall`
- `GreDataReady`
- `NegotiatingPpp`
- `AuthenticatingUser`
- `NegotiatingMppe`
- `ConfiguringNetwork`
- `Connected`
- `LegacySecurityWarning`
- `MigrationAvailable`.

## 8. Error categories

### Control

- TCP1723 blocked/reset
- PPTP control rejected.

### Data

- GRE protocol47 blocked
- PPTP ALG/NAT mapping failed
- multi-client NAT collision.

### PPP/security

- PPP link failed
- user auth failed
- MPPE required but not negotiated
- weak auth rejected by policy.

### Network

- address assignment failed
- route/DNS failed
- MTU/fragmentation.

Never show all as generic `VPN failed`.

## 9. Security warning UX

Suggested concise message:

`PPTP is an obsolete VPN protocol with known security limitations. Use it only for a legacy system that cannot be migrated yet.`

Then show the preferred replacement profile if configured.

## 10. Migration UX

For each PPTP profile:

- replacement protocol/profile;
- `Test replacement` action;
- last PPTP use;
- migration status;
- `Retire legacy profile` action after successful replacement;
- admin-defined retirement date.

No automatic silent protocol change because route/access semantics can differ; migration is explicit and validated.

## 11. Diagnostics

Safe technical details:

- backend/version;
- server IP/name;
- TCP1723 state;
- GRE47 packet counters;
- Call IDs;
- NAT/helper suspected state;
- PPP auth method;
- MPPE active/status identifiers;
- assigned IP/DNS/routes;
- last error.

Passwords/MPPE keys/RADIUS secrets are never shown.

## 12. Persian/RTL

Keep technical values LTR:

- IP/hostname;
- TCP 1723;
- GRE protocol 47;
- Call IDs;
- PPP/MS-CHAP/MPPE names;
- routes/logs/error codes.

Persian prose and warnings remain RTL.

## 13. Remaining UI evidence

- exact Windows 11/selected Windows 10 current profile screens;
- actual supported Android OEM/version UI if retained;
- exact Linux NetworkManager/plugin/source UI pin;
- selected RouterOS release UI/CLI mapping;
- migration dashboard after implementation.
