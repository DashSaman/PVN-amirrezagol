# SSTP / MS-SSTP — Client UI and Menu Maps

Review date: 2026-08-14

Entry: 011 SSTP / MS-SSTP.

## 1. Windows native SSTP client

Windows is the primary native SSTP client platform.

### Product-facing profile concepts

Current Windows native VPN profile management exposes concepts such as:

- Connection Name;
- Server name/address;
- VPN type / SSTP;
- Sign-in/authentication method;
- username/password or certificate/EAP-related credential source depending profile;
- Remember credentials;
- proxy behavior where system/network configuration applies;
- split tunneling/routes through supported profile/PowerShell/enterprise settings;
- connection status/connect/disconnect.

Exact Windows Settings labels vary by Windows release and should be captured per certified version rather than hard-coded from one screenshot.

### Recommended PVNetwork Windows UI

- Profile Name
- Server
- Protocol: SSTP
- User Authentication
- Credential/Certificate Reference
- Network
  - Full / Split tunnel
  - Routes
  - DNS
- Proxy
  - System / None / Explicit only when backend supports it
- Advanced
  - auth method policy
  - certificate/trust diagnostics
- Native Profile Status
- Connect / Disconnect
- Diagnostics
- Remove Native Profile.

PVNetwork should provision/observe Windows native profiles instead of duplicating the SSTP engine.

## 2. Windows system-owned states

The user can also modify/disconnect/delete the VPN from Windows Settings/Quick Settings/native UI.

PVNetwork must reconcile:

- profile removed externally;
- connection started/stopped externally;
- credential prompt/system UI;
- Windows network change;
- certificate/store changes.

Do not assume app UI is sole owner.

## 3. Linux sstp-client operator/client surface

The canonical `sstp-client` project is primarily a command-line/backend component rather than a complete polished consumer GUI.

Typical configuration concepts to map after exact source pin:

- SSTP server hostname;
- TLS trust/CA/certificate options;
- proxy settings;
- PPP/pppd options/profile;
- username/password/auth options passed through PPP integration;
- logging/debug;
- route/DNS integration.

### PVNetwork Linux UI

Do not expose raw command line as the normal UX. Use a typed adapter:

- Server
- TLS/Certificate Trust
- User Authentication
- Proxy
- Network/Routes/DNS
- Advanced PPP/SSTP compatibility
- Status/Logs.

The selected source release must be audited for which fields actually exist and how secrets are passed.

## 4. NetworkManager SSTP frontend

If a maintained NetworkManager SSTP plugin is selected, its editor UI becomes an important Linux desktop reference.

Before certification pin and map separately:

- plugin repository/license/version;
- gateway/server;
- user credentials;
- TLS certificate/trust options;
- PPP authentication toggles;
- proxy/advanced options;
- NetworkManager secret/keyring behavior;
- generated backend configuration.

Do not infer the plugin's fields from sstp-client CLI flags.

## 5. macOS / iOS / Android third-party clients

No native SSTP UI is claimed. Any selected client/engine requires its own UI/source/security/store review.

PVNetwork should not copy a proprietary third-party client's menu as a product specification. Instead normalize the protocol concepts proven by the selected engine.

## 6. Connection state UX

Recommended layered state labels:

- `ResolvingServer`
- `ConnectingTcp443`
- `ConnectingProxy` if applicable
- `ValidatingTls`
- `StartingSstpTransport`
- `NegotiatingSstpCall`
- `NegotiatingPpp`
- `AuthenticatingUser`
- `ValidatingCryptoBinding`
- `ConfiguringNetwork`
- `Connected`
- `Reconnecting`
- `Disconnecting`.

## 7. Error UX

Keep errors attributable:

### Network/Proxy

- Server unreachable
- TCP443 blocked/reset
- Proxy authentication failed
- Proxy does not support tunnel/connection.

### TLS

- Certificate untrusted
- Name mismatch
- Certificate expired/revoked
- TLS version/cipher policy mismatch.

### SSTP

- SSTP transport rejected
- SSTP control/call rejected
- Crypto binding failed
- SSTP peer incompatible.

### PPP/Auth

- PPP link negotiation failed
- User authentication failed
- EAP/certificate authentication failed
- Address/network configuration failed.

### Network install

- route/DNS install failed
- MTU/connectivity degraded.

Do not show one vague “Authentication failed” for TLS and PPP errors.

## 8. Certificate/trust UI

Normal users should see:

- server name;
- trusted/untrusted status;
- issuer;
- expiry;
- optional fingerprint in technical details.

Do not offer `Ignore certificate errors` as a normal toggle.

Enterprise/admin profile may select a managed CA/trust policy through approved OS mechanisms.

## 9. Proxy UI

Separate proxy credentials/settings from SSTP server/PPP credentials.

Show:

- Direct / System Proxy / Explicit Proxy where selected backend supports it;
- proxy hostname/port;
- proxy credential reference;
- whether TLS remains end-to-end to SSTP server.

A TLS-intercepting proxy should generate an explicit certificate/channel security warning or fail according to policy; do not silently trust it.

## 10. Diagnostics

Safe details:

- endpoint/port;
- proxy used;
- Windows/native/backend version;
- TLS version/cipher;
- server cert thumbprint/issuer/expiry;
- SSTP control state;
- crypto-binding validated;
- PPP auth method;
- assigned IPs;
- DNS/routes;
- session uptime/bytes;
- last error category.

Never show passwords, private keys, RADIUS/proxy secrets or TLS/session keys.

## 11. Persian/RTL

Keep technical tokens LTR:

- hostnames/IPs;
- TCP 443;
- certificate fingerprints;
- TLS cipher/version;
- error codes;
- PPP/EAP identifiers;
- logs/routes.

Persian labels/prose remain RTL.

## 12. Remaining client UI evidence

- exact Windows 11/10 current SSTP Settings/Quick Settings/profile screens;
- PowerShell/profile field correspondence;
- exact immutable sstp-client CLI/config source pin;
- selected NetworkManager SSTP plugin UI/source pin;
- any selected macOS/iOS/Android engine UI after product decision;
- accessibility/TV/mobile-specific UX only when a supported engine exists.
