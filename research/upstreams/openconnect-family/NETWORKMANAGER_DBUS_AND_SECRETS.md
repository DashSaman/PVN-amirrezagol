# NetworkManager-openconnect — D-Bus / Service / Secret Ownership Map

Review date: 2026-08-14

Pinned source mirror: `GNOME/NetworkManager-openconnect@ea97564887f897a3a9bb8edf49d4a70bebae5a4a`

Canonical upstream: GNOME GitLab. GitHub is a read-only source-inspection mirror.

Status: `IN-RESEARCH`; Linux architecture reference only, not PVNetwork implementation evidence.

## 1. Process/component map

The current source and `.name` metadata split the integration into distinct components:

- NetworkManager VPN service name: `org.freedesktop.NetworkManager.openconnect`;
- service executable: `nm-openconnect-service`;
- libnm plugin: `libnm-vpn-plugin-openconnect.so`;
- GNOME authentication frontend: `nm-openconnect-auth-dialog`;
- connection-properties/editor plugin: `libnm-openconnect-properties`;
- helper used by the OpenConnect child process for negotiated network configuration: `nm-openconnect-service-openconnect-helper`.

The `.name` metadata also declares support for multiple connections.

### PVNetwork lesson

Do not collapse profile editor, user-context authentication UI, privileged/system VPN service and tunnel engine into one object/process model. They have different ownership and secret requirements.

## 2. D-Bus policy boundary

`nm-openconnect-service.conf` defines a system-bus policy for the `org.freedesktop.NetworkManager.openconnect` name.

Source evidence shows:

- root can own/send to the OpenConnect NetworkManager service name;
- the dedicated `nm-openconnect` user can own/send to that name and send the `org.freedesktop.NetworkManager.VPN.Plugin` interface;
- default users are denied ownership/sending directly to the service name.

### PVNetwork lesson

A Linux implementation should use an explicit privileged/system service boundary rather than allowing ordinary UI processes to own arbitrary privileged network lifecycle directly. IPC authority must be narrow and auditable.

## 3. Configuration data vs connection secrets

`nm-openconnect-service.c` has separate validation tables for normal VPN data properties and session secrets.

Normal data properties include examples such as:

- configured gateway/profile host;
- CA certificate;
- auth type;
- user certificate/private key;
- MTU;
- certificate policy;
- UDP/DTLS disable option;
- protocol selector;
- proxy;
- CSD/posture-related configuration;
- token mode/secret configuration;
- reported OS;
- machine-certificate material.

The runtime `valid_secrets` set is much smaller and includes:

- authenticated cookie;
- final gateway after redirects/authentication;
- gateway certificate hash/trust result;
- resolved-address state.

This separation matches the architecture already selected for PVNetwork: durable profile/configuration != short-lived authenticated session material.

## 4. Authentication runs in the user's context; connection service consumes the result

`real_need_secrets()` documents the intended ownership clearly: the service mainly needs the authenticated cookie, final gateway and gateway certificate state, while certificate/token/auth complexity is handled by the authentication dialog in the user's context.

The service later retrieves those values from `NMSettingVpn` secrets and starts the OpenConnect tunnel process.

### PVNetwork architecture consequence

A future Linux Enterprise flow can cleanly separate:

`User Auth UI / Browser / user credential access`

from

`System VPN service / TUN ownership / engine launch / network configuration`.

Only the minimum short-lived authenticated session object should cross that boundary.

## 5. Tunnel/process privilege model

Current service source:

- opens `/dev/net/tun`;
- creates a persistent TUN device;
- assigns its owner/group to a dedicated `nm-openconnect` user;
- launches OpenConnect and drops the child process to that lower-privilege user when the persistent TUN path is available;
- retains service-side lifecycle monitoring and cleanup;
- destroys the persistent TUN on child exit.

This is valuable privilege-separation reference evidence.

### Security lesson from CSD wrapper handling

The current source contains an explicit warning that a user-provided CSD wrapper script remains unsafe to execute even after privilege dropping and therefore avoids passing it through in the shown path.

PVNetwork rule:

- never treat user-supplied/local posture scripts as ordinary profile fields that a privileged service blindly executes;
- posture/helper execution requires a separate threat model, allow-list/signing/sandbox decision and explicit product policy;
- do not execute arbitrary scripts from imported profiles.

## 6. OpenConnect child handoff

The service constructs the OpenConnect argument list from validated profile data, adds the NetworkManager helper script, selects the final authenticated gateway and writes the authenticated cookie to the child through stdin using the OpenConnect cookie-on-stdin mode.

This demonstrates a useful secret-handling pattern: short-lived authentication material need not be exposed as a command-line argument.

PVNetwork should preserve the principle but choose the narrowest platform-appropriate IPC/secret handoff mechanism for its own implementation.

## 7. Child lifecycle and failure mapping

The service watches the OpenConnect child process and maps exit behavior into NetworkManager VPN plugin states such as login failure, connection failure or disconnect. It cleans up the persistent TUN after exit.

Disconnect first requests an interrupt/graceful stop and escalates to a forced kill after a timeout if required.

### PVNetwork tests derived

- normal disconnect cleans the interface/routes/DNS;
- child crash cleans the interface and service state;
- authentication failure is distinct from generic connection failure;
- forced termination after graceful-timeout still cleans platform network artifacts;
- UI/service state converges after child exit.

## 8. NetworkManager editor secret flags

`properties/nm-openconnect-editor.c` explicitly marks per-login/session values as **NOT_SAVED**:

- gateway certificate hash;
- authenticated cookie;
- final authenticated gateway;
- resolved-address value.

The source comment says these values differ for every login session and should not be stored.

By contrast, auth-dialog internal remembered state such as XML configuration, last host, autoconnect choice and certificate-signature state is configured to be stored. The source also notes that arbitrary form fields supplied by a server can introduce additional remembered values.

### PVNetwork requirement

Do not inherit NetworkManager's generic “secret” naming literally. Classify by sensitivity and lifetime:

- reusable protected credential;
- short-lived session secret;
- remembered non-secret auth choice;
- server-provided configuration metadata;
- trust/pinning state.

Each class needs an explicit persistence policy.

## 9. Token secret nuance

The editor can place token-secret material into the NetworkManager VPN secret namespace. The exact persistence flag and whether a given token mode should be durably stored depends on the higher-level connection/secret-agent behavior and selected mode.

PVNetwork must define this explicitly rather than relying on implicit secret-agent defaults. Token seeds can be more sensitive than ordinary passwords because long-term compromise may enable repeated OTP generation.

## 10. Profile editor data flow

The connection editor:

- validates required gateway and proxy syntax;
- dynamically populates supported protocol choices from `libopenconnect`;
- dynamically exposes token modes based on OpenConnect feature support;
- writes selected profile data into `NMSettingVpn` data items;
- writes sensitive token material into the secret namespace;
- sets explicit secret-save flags for known session/internal fields.

This is strong evidence for capability-driven UI rather than hardcoding every option regardless of the active core build.

## 11. D-Bus/secret architecture proposed for PVNetwork Linux

If NetworkManager-backed integration is selected, keep conceptual boundaries like:

`PVNetwork UI/profile editor`
→ `PVNetwork Auth Challenge + Browser/SSO service in user context`
→ `protected desktop secret service / user keyring`
→ `narrow authenticated-session handoff`
→ `NetworkManager/system VPN adapter`
→ `libopenconnect/OpenConnect engine`
→ `system network state`.

If standalone Linux mode is also offered, preserve the same product-facing contract even if the implementation uses a PVNetwork system service instead of NetworkManager.

## 12. Required PVNetwork Linux regression tests

1. normal user cannot directly impersonate/own privileged VPN service endpoint;
2. service only accepts validated profile fields and expected short-lived secrets;
3. auth dialog/browser can restart/cancel without leaking session secrets;
4. per-login cookie/final gateway/resolution state is not durably persisted;
5. durable remembered choices are distinguishable and user-clearable;
6. TUN ownership/cleanup survives child crash;
7. graceful disconnect escalation still performs cleanup;
8. imported profile cannot cause arbitrary privileged script execution;
9. multiple simultaneous enterprise sessions remain isolated if product supports them;
10. logs and D-Bus diagnostics redact cookie/token/credential values.

## Remaining gaps

- exact current NetworkManager Secret Agent implementation and keyring persistence behavior outside this plugin repository;
- exact D-Bus method/signal surface inherited from the NetworkManager VPN plugin API;
- current SELinux/AppArmor/systemd sandbox/package policies by distribution;
- whether PVNetwork will choose NetworkManager, standalone service, or both;
- real distro/package tests after implementation exists.