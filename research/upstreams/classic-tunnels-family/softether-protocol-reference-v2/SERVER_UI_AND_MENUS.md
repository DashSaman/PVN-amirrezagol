# SoftEther VPN Protocol — Server UI and Control-Plane Menus

Review date: 2026-08-14

Entry: 013 SoftEther VPN Protocol.

SoftEther Server Manager and `vpncmd` are major operator references, but the management plane is separate from native VPN client data sessions.

## 1. Server-level administration

Map/verify on the selected release:

- server status/version;
- TCP listeners;
- server certificate;
- administrator password/security;
- logging/status;
- Virtual Hub creation/removal;
- cluster/cascade/server features where used;
- compatibility protocol enablement;
- configuration backup/restore.

## 2. Listener menu

For each listener show:

- TCP port;
- enabled/listening state;
- bind/interface if backend exposes it;
- role/usage;
- whether it is intended for native SoftEther clients;
- firewall exposure.

Common listener values such as 443/992/5555 are configuration, not fixed protocol constants.

## 3. Virtual Hub administration

Per hub:

- hub name/status;
- users/groups;
- authentication type;
- security policy;
- active sessions;
- MAC/IP tables where available;
- local bridge;
- SecureNAT;
- cascade connections;
- logs.

Virtual Hub authorization must remain separate from server TLS certificate configuration.

## 4. User/authentication menu

Map supported selected-release methods such as local credential/certificate and external AAA/domain options only when exact backend evidence exists.

For every method record:

- credential owner;
- secure storage/reference;
- group/policy mapping;
- expiry/disable state;
- external AAA dependency.

## 5. Native protocol vs compatibility menus

Admin UI must visually separate:

- Native SoftEther VPN Protocol
- SSTP
- L2TP/IPsec
- OpenVPN compatibility
- EtherIP
- other compatibility features.

Do not label `SoftEther Server enabled` as evidence that every protocol is enabled.

## 6. Network integration

### Local Bridge

- hub to physical/TAP interface mapping;
- VLAN/loop/STP implications;
- interface privilege/state.

### SecureNAT

- virtual NAT/DHCP enablement;
- address/DHCP policy;
- route/forwarding implications;
- performance warning if applicable at selected release.

### Cascade

- destination server/hub;
- authentication/credential reference;
- session status;
- topology/loop risks.

## 7. Recommended PVNetwork server UI

### Overview

- Backend/version
- Native listeners
- Native sessions
- Certificate health
- Virtual Hubs
- Compatibility-listener exposure
- Management exposure

### Native Sessions

- user/device identity
- Virtual Hub
- client endpoint
- connection count
- virtual adapter/session status
- bytes/packets
- uptime
- disconnect action.

### Security

- certificate/trust
- user auth backend
- management credential policy
- unused protocol/listener warning
- external AAA status.

### Network

- bridge/SecureNAT/cascade
- VLAN/Layer-2 policy
- routes/NAT/DHCP
- effective MTU.

## 8. vpncmd / API boundary

If PVNetwork automates `vpncmd` or a supported management API:

- use typed command wrappers;
- validate exact server version/capability;
- redact credentials/output;
- apply least privilege/RBAC;
- never pass arbitrary user-supplied commands from a browser.

## 9. Secret handling

Write/replace-only where possible:

- administrator password;
- user passwords/private keys;
- external AAA secrets;
- cascade credentials;
- server private key.

Ordinary monitoring can show certificate fingerprints, auth-method names and session IDs, not secrets.

## 10. Remaining UI evidence

- exact current Server Manager screenshots/menu tree;
- exact `vpncmd` command mapping for native listeners/hubs/users/sessions/networking;
- current release management authentication/listener behavior;
- backup/restore secret handling;
- clustering/HA UI if selected.
