# SSTP / MS-SSTP — Server UI and Control-Plane Menus

Review date: 2026-08-14

Entry: 011 SSTP / MS-SSTP.

There is no single SSTP server UI. Windows RRAS and SoftEther own different management models and must be documented separately.

## 1. Windows Server RRAS — management surface

Current Windows Server SSTP administration is spread across supported Windows management components rather than one SSTP-only page.

### Server Manager / role installation

Typical administrative path:

- Server Manager
- Add Roles and Features
- Remote Access
- VPN/RAS role service
- post-deployment RRAS configuration.

### Routing and Remote Access console

Major domains relevant to SSTP include:

- RRAS server enable/configuration;
- Ports / WAN Miniports;
- SSTP port availability/count and status;
- IPv4/IPv6/address assignment;
- authentication/accounting integration;
- NPS/RADIUS or Windows policy ownership;
- routing/NAT/remote-access behavior;
- active remote-access clients/connections.

Exact property names/screens vary by Windows Server release and must be captured for each certified build.

### Certificate binding

SSTP requires the RRAS/HTTP/TLS endpoint to use an appropriate server certificate. Certificate administration may involve:

- Local Computer certificate store;
- certificate subject/SAN and trust chain;
- private-key permissions;
- RRAS/SSTP binding selection or supported Windows configuration command/API;
- certificate renewal/rebinding.

A PVNetwork server UI should show a safe certificate reference/thumbprint and validation status, never private-key material.

### NPS / RADIUS / access policy

User authorization/authentication policy is separate from the SSTP transport. If NPS or RADIUS is used, expose it as an independent AAA configuration/status area.

### Windows Firewall

TCP443 listener access and post-VPN traffic policy are different firewall concerns. Do not treat opening TCP443 as permission for all VPN client traffic.

### Monitoring

A normalized adapter should collect supported RRAS/Windows status/event data for:

- listener/service state;
- connected users/sessions;
- assigned addresses;
- authentication failures;
- TLS/certificate problems;
- SSTP/PPP failures;
- routing/firewall issues.

Do not scrape localized MMC text if a supported command/API/event source exists.

## 2. SoftEther VPN Server Manager

SoftEther is a multiprotocol product with GUI and `vpncmd`-style administration. Exact current release UI must be captured during certification, but the server architecture normally separates:

### Server/Listener

- listener ports;
- server certificate;
- management settings;
- protocol compatibility features including SSTP.

### Virtual Hub

- hub creation/status;
- users/groups;
- authentication methods;
- RADIUS/external auth where configured;
- sessions/MAC/IP tables;
- security policy.

### Network integration

- local bridge;
- SecureNAT/NAT/DHCP where enabled;
- routing/virtual networking;
- cascade/other multiprotocol features.

### Logs/status

- server/session/security logs;
- active sessions;
- listener state;
- user authentication events.

PVNetwork must not copy SoftEther branding/UI assets. Use the workflow as an operator reference and preserve SoftEther's separate license/config ownership.

## 3. Recommended PVNetwork SSTP server UI

### Overview

- Server Backend: Windows RRAS / SoftEther / other approved
- Version/build
- SSTP Listener
- Certificate Status
- Active Sessions
- Authentication Backend
- Address Pool
- Health/Warnings

### TLS / Certificate

- Hostname
- Certificate reference/thumbprint
- Subject/SAN
- Issuer
- Expiry
- Trust/revocation status
- TLS policy summary
- Rotate/Rebind action

No private-key export in ordinary UI.

### SSTP

- Enabled
- Listener/port
- Session limits
- Crypto-binding/security capability/status
- Echo/keepalive settings only if the selected backend exposes supported controls
- Compatibility mode only when exact backend evidence exists.

### PPP / User Authentication

- Allowed authentication methods
- NPS/RADIUS/local/SoftEther user source
- RADIUS server references
- Accounting
- User/group authorization policy

Keep PPP credentials distinct from TLS certificate settings.

### Addressing / Network

- address pool/native assignment method
- DNS
- split/full-tunnel policy where server-controlled
- routes
- client-to-client policy
- Internet egress/NAT
- firewall/ACL policy.

### Status

Per session:

- username/account identity
- endpoint IP (privacy controlled)
- TLS/SSTP state
- PPP/auth state
- assigned client address
- connected time
- bytes/packets where backend exposes them
- disconnect action for authorized admins.

### Logs / Diagnostics

Separate views:

- TLS/certificate
- SSTP control
- PPP/auth
- RADIUS/NPS
- routes/firewall/DNS
- service/restart/update.

## 4. Secret handling

Separate secret classes:

- server TLS private key;
- RADIUS shared secret;
- local user password/verifier;
- proxy credentials only on client side;
- certificate trust metadata.

UI rules:

- write/replace-only for reusable secrets where possible;
- secure-store/provider references;
- no plaintext return through normal API;
- audit who changed a secret/policy, not the secret value;
- backups explicitly encrypted/access-controlled.

## 5. Listener sharing warning

If TCP443 is shared with an ordinary HTTPS website/service, the UI must show the exact supported demultiplexing architecture.

Do not offer a generic `Use existing reverse proxy` checkbox. SSTP requires protocol-specific long-lived duplex behavior and TLS/channel-binding correctness.

## 6. RBAC

Privileged actions include:

- enable/disable SSTP;
- change listener/certificate;
- change TLS/auth policy;
- add/remove users or RADIUS settings;
- change address pools/routes/NAT/firewall;
- disconnect sessions;
- rotate secrets/certificates;
- restart/update server.

Read-only monitoring must not inherit these actions.

## 7. Remaining server UI evidence

- exact Windows Server 2025 RRAS/MMC/PowerShell field correspondence and screenshots;
- selected older Windows Server differences;
- exact current SoftEther release GUI/vpncmd menu/source correspondence;
- certificate rotation UI/command behavior;
- NPS/RADIUS mapping;
- accessible remote-management API/RBAC design after implementation.
