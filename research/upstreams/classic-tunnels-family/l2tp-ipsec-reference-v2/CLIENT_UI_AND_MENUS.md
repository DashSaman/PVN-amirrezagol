# L2TP/IPsec — Client UI, Provisioning and Lifecycle

Review date: 2026-08-14

Entry: 008 L2TP/IPsec.

Status: reference/source mapping. Device execution remains required for strict completion.

## UX rule

Present L2TP/IPsec as **Legacy compatibility**. Never silently downgrade from IKEv2, WireGuard/AWG or another selected modern protocol.

The UI must keep three layers conceptually separate:

1. IPsec machine protection/authentication;
2. L2TP tunnel/session;
3. PPP user authentication/addressing.

A single generic `VPN failed` message is insufficient for diagnostics.

## Windows 10/11 native client

Primary UI ownership: Windows Settings / native RAS profile.

PVNetwork-facing normalized fields should include:

- profile/display name;
- server address/FQDN;
- VPN type = L2TP/IPsec;
- machine authentication mode: PSK or certificate where selected profile supports it;
- user authentication/sign-in method;
- username/account identity where applicable;
- split/full-tunnel intent;
- DNS/routes where managed through supported Windows provisioning interfaces.

Do not display the IPsec PSK after storage. Do not place it in ordinary logs, telemetry, profile export or command history.

### Lifecycle states

- NotProvisioned
- Provisioned
- ConnectingIPsec
- ConnectingL2TP
- AuthenticatingPPP
- Connected
- FailedIPsec
- FailedL2TP
- FailedPPPAuth
- Disconnected
- ProfileChangedExternally
- ProfileRemovedExternally

The actual Windows APIs may expose coarser state; these are product diagnostic states and must only be asserted when evidence permits classification.

## Apple native client

Current Apple documentation (reviewed 2026-08-14) explicitly lists L2TP over IPsec as a built-in supported protocol for iOS/iPadOS/macOS/tvOS/visionOS, with MS-CHAPv2 user password plus shared-secret machine authentication in the documented L2TP model.

Current macOS User Guide path:

`System Settings -> VPN -> Add VPN Configuration -> L2TP over IPSec`

Current macOS documentation describes configuration inputs including server address, account name, password/authentication and L2TP-specific options. L2TP options include disconnect behavior, sending all traffic over the VPN and verbose logging.

### Product mapping

PVNetwork should prefer native/managed profile provisioning rather than embedding an L2TP engine. Keep the OS profile as system-owned state and reconcile external edits/removal.

Do not copy IKEv2-only capability badges such as MOBIKE or ML-KEM additional exchanges onto L2TP/IPsec.

## Android

UI availability is version/OEM dependent and remains an open gate. Do not render a universal `Android native L2TP` setup wizard unless the selected OS/device capability matrix has been verified.

If capability is absent:

- show `Not available on this Android build`;
- offer an approved modern protocol instead;
- do not instruct users to install an unreviewed legacy VPN APK.

## Linux / NetworkManager-l2tp

A desktop UI is a composition, not one protocol engine:

`NetworkManager editor -> NetworkManager-l2tp -> IPsec backend -> L2TP daemon -> pppd`

Normalized UI domains:

- gateway/server;
- username/domain where applicable;
- PPP authentication policy;
- IPsec enablement;
- PSK or certificate identity;
- advanced IPsec proposal fields only when deliberately supported;
- MTU/MRU and PPP options;
- routes/DNS;
- secret flags/keyring ownership.

PVNetwork must record exact distro/plugin/backend versions before claiming UI parity.

## Diagnostics UI

Expose layered, redacted diagnostics:

### Protection
- IPsec negotiation started/established/failed;
- NAT-T detected where observable;
- peer identity/certificate summary without private material.

### L2TP
- control tunnel established/failed;
- session established/failed;
- compatibility/source-port issue hint only when evidence supports it.

### PPP
- authentication started/failed;
- assigned client address;
- DNS/routes received/applied;
- MTU/MRU summary.

Never expose PSKs, private keys, PPP passwords or RADIUS shared secrets.

## Migration UX

When both endpoints support a preferred modern target, offer an explicit migration action rather than automatic fallback:

`Legacy L2TP/IPsec profile detected -> Test modern replacement -> confirm connectivity -> switch default -> retain/remove legacy profile by policy`.

The old profile must not be deleted before a successful replacement receipt or explicit operator choice.

## Current authoritative references reviewed

- Microsoft Learn: current Windows Server RRAS protocol configuration and Windows Server 2025 hardening guidance.
- Microsoft Learn: Windows L2TP/IPsec client default encryption behavior.
- Apple Platform Deployment: current VPN overview and L2TP-over-IPsec authentication matrix.
- Apple macOS User Guide: current L2TP-over-IPsec creation and options UI.
- Pinned NetworkManager-l2tp evidence already recorded in `CLIENT_INSTALL_MATRIX.md`.

## Remaining strict gates

- Windows 10/11 exact UI/API/profile screenshots/receipts;
- current Apple iOS/iPadOS/macOS managed-profile and real-device receipts;
- Android exact release/OEM UI capability evidence;
- Linux exact distro NetworkManager-l2tp UI screenshots and backend receipts;
- accessibility/localization checks for any PVNetwork-owned wizard;
- cross-platform error classification against real servers.
