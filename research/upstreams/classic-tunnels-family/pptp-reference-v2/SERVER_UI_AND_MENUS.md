# PPTP — Server UI and Control-Plane Menus

Review date: 2026-08-14

Entry: 012 PPTP.

Every PPTP server UI must present the protocol as obsolete/legacy and keep control/GRE, PPP auth, MPPE and network policy separate.

## 1. Windows Server RRAS

Relevant management surfaces:

### Server Manager

- Remote Access role installation;
- VPN/RAS role service;
- post-deployment RRAS configuration.

### Routing and Remote Access console

Legacy PPTP-relevant domains include:

- Ports / WAN Miniports;
- PPTP port acceptance/count;
- remote-access enablement;
- IPv4 address assignment/pools;
- authentication/accounting policy;
- NPS/RADIUS integration;
- connected clients;
- routing/NAT.

For Windows Server 2025, a PVNetwork adapter/UI must show that PPTP is **disabled by default for new RRAS setups** and requires explicit legacy enablement.

### Firewall

Show separately:

- TCP1723 control rule;
- GRE IP protocol47 allowance/state;
- post-PPP client access rules.

Do not label GRE as port 47.

## 2. MikroTik RouterOS

Current RouterOS PPTP configuration is built into PPP/interface administration.

Conceptual domains to map on exact release:

- PPTP Server enable/disable;
- Default Profile;
- Authentication methods;
- MPPE/encryption requirement/profile behavior;
- PPP Secrets / RADIUS;
- IP pools;
- active PPP sessions;
- PPTP client interfaces;
- firewall/NAT/PPTP helper state;
- logs.

The UI/help must retain MikroTik's current warning that PPTP has known security issues and is not suitable where security matters.

## 3. Linux pptpd/pppd

No canonical modern web UI.

Configuration domains:

### pptpd

- local/control bind/listen;
- local/remote IP pools;
- ppp options file;
- connections/debug/logging.

### pppd

- auth methods;
- `chap-secrets`/RADIUS or plugin;
- MPPE requirements;
- LCP/NCP;
- DNS/routes/scripts.

### OS networking

- TCP1723 firewall;
- GRE protocol47;
- conntrack/PPTP helper;
- forwarding/NAT;
- service/init.

A future PVNetwork legacy adapter should generate/validate narrowly scoped config rather than expose unrestricted root file/shell editing.

## 4. Recommended PVNetwork legacy PPTP server UI

### Header

`PPTP — Obsolete Legacy Compatibility`

Include:

- security warning;
- replacement protocol/profile;
- planned retirement date/status;
- active legacy user/device count.

### Service

- Enabled
- Backend/version
- TCP1723 control state
- GRE47 capability/firewall state
- Max sessions

### Authentication / PPP

- Allowed auth methods
- MPPE required/effective policy
- User source: local / RADIUS / Windows/NPS
- Address pool
- DNS

### Network

- routes
- NAT/Internet egress
- client isolation
- source-network restrictions
- GRE/PPTP helper status.

### Sessions

- account/user
- remote address
- Call IDs
- PPP/auth method
- MPPE active yes/no
- assigned IP
- uptime/counters
- disconnect.

### Migration

- replacement protocol
- replacement profile provisioned yes/no
- last PPTP use
- migration owner
- retirement target.

## 5. Secret handling

Separate:

- PPP user password/verifier;
- RADIUS shared secret;
- MPPE runtime keys;
- Call IDs.

Passwords/RADIUS secrets are secure write-only references; MPPE keys never exposed; Call IDs are non-secret technical values.

## 6. Safety controls

Require explicit privileged confirmation for:

- enabling PPTP;
- enabling weaker auth methods;
- relaxing MPPE requirement;
- opening GRE/TCP1723 Internet access;
- expanding reachable networks;
- disabling the migration warning/deadline.

Do not include an `Enable all legacy compatibility` bulk switch.

## 7. Monitoring/error views

Separate:

- TCP control failures;
- GRE/ALG/NAT failures;
- PPP LCP;
- auth failures;
- MPPE negotiation;
- address/NCP;
- routes/DNS;
- stale sessions/helpers.

## 8. Remaining UI evidence

- exact Windows Server 2025 RRAS PPTP enable/Ports UI and supported command/API mapping;
- selected RouterOS release WebFig/WinBox/CLI field map;
- actual historical pptpd/pppd config from isolated lab if retained;
- session/error screenshots/version correspondence;
- migration dashboard after implementation.
