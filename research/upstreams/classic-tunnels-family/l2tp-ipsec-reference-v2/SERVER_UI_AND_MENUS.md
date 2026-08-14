# L2TP/IPsec — Server UI and Control-Plane Menus

Review date: 2026-08-14

Entry: 008 L2TP/IPsec.

L2TP/IPsec has no canonical web admin UI. Server UI research must show which management product owns IPsec, L2TP, PPP users/AAA, addressing and firewall policy.

## 1. pfSense — current official UI map

Current Netgate documentation exposes a dedicated L2TP server at:

`VPN > L2TP`

### L2TP server surface

Current official docs show fields/domains including:

- Enable L2TP server;
- Interface/bind selection;
- Server Address;
- Remote Address Range;
- client subnet/netmask/concurrency planning;
- user authentication source;
- local Users tab;
- optional RADIUS server and RADIUS shared secret;
- L2TP-related server settings such as DNS/MTU/secret depending current UI version.

### User management

For local authentication, users are added under the L2TP Users tab. External RADIUS can replace local user records.

### Firewall

After enabling the server, client traffic is controlled separately at:

`Firewall > Rules > L2TP VPN`

Current Netgate docs state that without explicit L2TP VPN firewall rules, traffic initiated by connected clients is blocked.

The WAN/IPsec protection path is separate from the post-PPP client firewall rules.

### IPsec composition

The official L2TP/IPsec recipe first configures the L2TP server, users and L2TP firewall rules, then configures a separate mobile IPsec layer.

This is exactly the UI separation PVNetwork must preserve:

- IPsec machine protection;
- L2TP server/session;
- PPP users/AAA;
- assigned IP addresses;
- post-tunnel firewall rules.

### Logs/status

Current docs expose:

- `Status > System Logs > VPN > L2TP Logins` for login/logout timestamps, username and assigned address;
- `L2TP Raw` log for full L2TP service logging;
- separate IPsec logs/status for the protection layer.

A good diagnostics UI must correlate those layers without merging the logs.

### Vendor warning

Netgate's current docs explicitly advise avoiding L2TP/IPsec unless necessary and prefer IKEv2/OpenVPN/WireGuard for current remote-access deployments. This warning belongs in PVNetwork's compatibility/admin surface.

## 2. Windows Server RRAS UI

Current Microsoft server path uses Server Manager plus Routing and Remote Access MMC.

### Installation/admin surface

- Server Manager > Add Roles and Features;
- Remote Access role;
- DirectAccess and VPN (RAS) role service;
- Routing and Remote Access management console.

### Protocol/port surface

RRAS exposes Ports and WAN Miniports for supported VPN types. Current Microsoft documentation shows L2TP as a configurable WAN Miniport alongside IKEv2/SSTP/PPTP.

For Windows Server 2025, L2TP/PPTP are disabled for new setups by default and can be re-enabled explicitly. PVNetwork admin UX should mirror that concept with a **legacy compatibility enablement** rather than silently turning L2TP on.

### Addressing/auth/policy surfaces

RRAS configuration includes domains such as:

- IPv4 address assignment/static pool;
- authentication/NPS/RADIUS policy;
- certificates/PSK/IPsec machine-auth configuration;
- connection limits/ports;
- Windows Firewall;
- routing/remote-access policy.

### Monitoring

Operational status and event/logging belong to Windows RRAS/Event Viewer/native diagnostics. A PVNetwork adapter should collect safe normalized status rather than scrape UI text.

## 3. xl2tpd + pppd server — CLI/config surface

No built-in web UI.

### xl2tpd configuration domains

- global/listen settings;
- LNS/LAC sections;
- local/remote IP ranges;
- PPP options file;
- tunnel authentication/secret if used;
- length/flow/sequence/compatibility settings;
- kernel/userspace L2TP behavior;
- logs/control socket.

### pppd configuration domains

- LCP/MTU/MRU;
- authentication method policy;
- user credential/AAA plugin;
- IP address negotiation;
- DNS/options;
- routing hooks/scripts;
- logs.

### IPsec configuration domains

strongSwan/Libreswan configuration remains separate and is mapped in the completed IPsec v2 dossier.

### PVNetwork management direction

If PVNetwork later manages this stack, use a narrow typed server adapter that validates and owns generated files/services. Do not expose unrestricted shell/file editing from a remote web UI.

## 4. Accel-PPP admin/config surface

Accel-PPP is configuration/CLI/service oriented rather than a consumer web UI.

The pinned sample configuration exposes distinct module groups:

- `[modules]`
- `[core]`
- `[common]`
- `[ppp]`
- `[auth]`
- `[l2tp]`
- `[ip-pool]`/RADIUS and other access-server modules.

Relevant L2TP/PPP fields visible in source include:

- L2TP hello/timeout/retransmit/window/host name;
- L2TP secret;
- sequencing/reorder settings;
- per-protocol IP pools/interface naming;
- PPP MTU/MRU/LCP echo;
- PAP/CHAP/MS-CHAP auth modules;
- RADIUS/local credential backend.

PVNetwork should normalize these into protocol/admin concepts rather than expose the raw INI file as the primary UI.

## 5. SoftEther VPN Server Manager / vpncmd

SoftEther is a multiprotocol server product with its own management interfaces.

Existing PVNetwork classic-tunnels research records the relevant source families. Before v2 strict closure, current exact-release UI/menu screenshots/source should be refreshed.

Expected product domains to verify at selected release:

- IPsec/L2TP server enablement;
- IPsec pre-shared key;
- default virtual hub;
- user/group authentication;
- SecureNAT/routing;
- listener/management ports;
- logs/status;
- service configuration/backup.

Do not copy SoftEther branding/UI assets; use it as an operator-flow reference only.

## 6. Recommended PVNetwork server UI information architecture

If server management becomes a product feature, use a layered structure:

### L2TP/IPsec Service

- Overview / Legacy Warning
- IPsec Protection
  - IKE version/capability
  - Machine Authentication
  - Current Proposal Policy
  - NAT-T
- L2TP
  - Bind/Endpoint
  - Tunnel/Session Compatibility
  - Source-Port Policy
  - MTU/MRU
- PPP / Users
  - Authentication Methods
  - Local Users
  - RADIUS/AAA
  - Address Pool
  - DNS
- Routing / Firewall
  - Client routes
  - Egress NAT
  - L2TP-client firewall policy
- Status
  - IKE/IPsec SAs
  - L2TP Tunnels
  - L2TP Sessions
  - PPP Sessions
  - Assigned Addresses
- Logs
  - IPsec
  - L2TP
  - PPP/Auth
- Compatibility
  - Windows NAT caveats
  - Ephemeral L2TP source port
  - Legacy IKE/algorithm exceptions

## 7. Secret handling in server UI

Separate secret classes:

- IPsec PSK/private key;
- L2TP tunnel secret;
- PPP user password;
- RADIUS shared secret.

Rules:

- write-only or replace-only after creation where possible;
- never return existing values through ordinary API responses;
- use secure file/store permissions;
- redact logs/audit events;
- do not clone secrets into browser/local storage;
- backups must have explicit encryption/access/retention policy.

## 8. Action authorization

State-changing actions require explicit authorization and audit:

- enable legacy L2TP service;
- change machine PSK/cert;
- add/delete PPP user;
- change auth methods;
- expose UDP/1701 policy;
- restart IPsec/L2TP/PPP services;
- disconnect sessions;
- change routes/NAT/firewall.

A read-only status role must not inherit these controls.

## 9. Remaining UI evidence

- exact current SoftEther L2TP/IPsec screen/menu map;
- exact current RRAS L2TP property screenshots/PowerShell/API field correspondence;
- pfSense source-level form/action/CSRF/secret-at-rest audit;
- selected Linux admin product if a web panel is adopted;
- accessibility/mobile responsiveness for any future PVNetwork panel;
- runtime version correspondence to screenshots.
