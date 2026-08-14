# SoftEther VPN Protocol — Client UI and Menu Map

Review date: 2026-08-14

Entry: 013 SoftEther VPN Protocol.

## 1. Windows SoftEther VPN Client / Client Manager

Primary native-client UX reference. Exact menu names/screens must be captured on the selected release, but the product model includes:

- virtual network adapter creation/status;
- connection/account/profile creation;
- server hostname/IP and listener port;
- Virtual Hub name;
- user authentication method/credential reference;
- certificate/trust settings;
- transport/connection-count options exposed by the selected release;
- connect/disconnect/status;
- session/network statistics;
- proxy/network options where supported;
- import/export/backup behavior.

## 2. Recommended PVNetwork native profile UI

### General

- Name
- Server
- Listener Port
- Virtual Hub
- Native SoftEther Protocol badge

### Authentication

- method
- username/certificate identity
- secure credential reference
- server certificate/trust policy.

### Virtual Adapter

- selected adapter
- adapter state
- MAC/network configuration where relevant
- create/remove adapter as a privileged lifecycle operation.

### Transport

- backend-supported connection count/mode
- proxy only when proven supported
- reconnect policy
- MTU advanced option only with evidence.

### Network

- effective IP/DHCP state
- routes
- DNS
- IPv4/IPv6 status.

### Diagnostics

- TLS state/version
- native session state
- Virtual Hub
- connection count
- bytes/packets/errors
- virtual adapter state
- reconnect count.

## 3. Layered client states

Normalize:

- `AdapterUnavailable`
- `ResolvingServer`
- `ConnectingTcp`
- `ValidatingTls`
- `NegotiatingNativeProtocol`
- `AuthenticatingUser`
- `JoiningVirtualHub`
- `ConfiguringAdapter`
- `Connected`
- `DegradedTransport`
- `Reconnecting`
- `Disconnecting`.

## 4. Error categories

Separate:

- TCP/listener unreachable;
- TLS certificate/trust failure;
- native protocol/version incompatibility;
- user authentication failure;
- Virtual Hub not found/unauthorized;
- virtual adapter/driver failure;
- route/DNS failure;
- partial parallel-connection failure;
- server-side bridge/SecureNAT reachability issue.

## 5. Native vs compatibility UX

Do not present a SoftEther Server SSTP/L2TP/OpenVPN profile as `SoftEther VPN Protocol` merely because the server brand is SoftEther.

Profile identity must include the actual wire protocol.

## 6. Windows system/service ownership

Client service and virtual adapter can outlive the foreground UI. PVNetwork must reconcile service/adapter/profile state and external changes rather than assume UI process ownership.

## 7. Linux/other native UI

If a selected current native client build exists, use a typed UI above its service/CLI instead of exposing raw arbitrary commands. Exact fields must come from the selected source/build and can differ from Windows Client Manager.

## 8. Secret handling

- password/private key -> secure write/replace-only reference;
- server certificate fingerprint/trust metadata -> safe technical display;
- admin/server-management credentials are not client session credentials;
- exported profiles/backups require explicit secret-inclusion policy.

## 9. Persian/RTL

Keep hostnames/IPs, TCP ports, Virtual Hub names, certificate fingerprints, adapter names, counters and logs LTR; Persian labels/help remain RTL.

## 10. Remaining UI evidence

- exact current Windows Client Manager full menu tree/screenshots;
- selected native client source-to-field mapping;
- Linux/macOS UI only if a supported native build is selected;
- accessibility/keyboard behavior;
- import/export secret semantics;
- current release update UI.
