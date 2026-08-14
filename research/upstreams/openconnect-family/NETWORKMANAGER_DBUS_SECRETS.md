# NetworkManager-openconnect — D-Bus, Service, Secret and Ownership Map

Research date: 2026-08-14

State: `IN-RESEARCH`; architectural evidence only, not PVNetwork implementation proof.

## Source baseline

Canonical project: `https://gitlab.gnome.org/GNOME/NetworkManager-openconnect`

Read-only GitHub mirror pinned for source-level inspection:

`GNOME/NetworkManager-openconnect@ea97564887f897a3a9bb8edf49d4a70bebae5a4a`

Primary files reviewed:

- `src/nm-openconnect-service.c`
- `auth-dialog/main.c`
- `shared/nm-service-defines.h`
- `nm-openconnect-service.conf`

## Ownership layers observed

The current codebase separates responsibilities into four practical ownership domains:

1. **NetworkManager connection/profile ownership** — connection metadata and VPN data items live in NetworkManager's connection model.
2. **NetworkManager VPN service/plugin ownership** — `nm-openconnect-service` owns the VPN plugin lifecycle, process supervision, tunnel-service integration and D-Bus service identity.
3. **Authentication frontend ownership** — `auth-dialog` renders server-provided authentication forms, browser/SSO flows and certificate prompts, and obtains authenticated session material for the service layer.
4. **Desktop secret-store ownership** — the authentication frontend uses `libsecret` for remembered passwords that the user elects to retain.

This separation is a strong architectural reference for PVNetwork, but PVNetwork should use its own explicit canonical profile, protected-secret and runtime-session models instead of inheriting NetworkManager's terminology directly.

## D-Bus service identity

The pinned source defines:

- service type: `org.freedesktop.NetworkManager.openconnect`
- D-Bus service name: `org.freedesktop.NetworkManager.openconnect`
- D-Bus interface constant: `org.freedesktop.NetworkManager.openconnect`
- object path: `/org/freedesktop/NetworkManager/openconnect`

`nm-openconnect-service.conf` permits ownership/sending for the root and `nm-openconnect` service contexts and denies arbitrary default-context ownership of the service name.

The service is instantiated through NetworkManager's `NMVpnServicePlugin` framework rather than exposing a standalone application-specific RPC design.

## NetworkManager VPN plugin lifecycle

The pinned service class wires the NetworkManager VPN plugin virtual methods to:

- `connect`
- `need_secrets`
- `disconnect`

The service validates declared profile properties and a separate set of runtime secret values before connection startup.

The source also contains explicit process supervision and disconnect cleanup behavior for the spawned OpenConnect process. PVNetwork should learn from the ownership boundary, but its own Core Adapter should hide engine/process details from UI models.

## Profile data vs secrets

The pinned service source keeps separate validation tables for ordinary VPN data items and runtime secrets.

Examples of profile/data-item classes include:

- gateway and protocol selection;
- CA/client-certificate/private-key references;
- MTU and UDP-disable preference;
- proxy and reported-OS selection;
- token and CSD-related settings;
- additional certificate-related settings.

The runtime secret set includes authenticated/session values such as the resulting connection cookie and final gateway/certificate-resolution state needed after authentication.

### PVNetwork rule

Do not map all of these directly into one flat "VPN config" object. Preserve at least:

- canonical non-secret profile data;
- protected reusable credentials/private material;
- transient authenticated session material;
- runtime engine/platform state.

## `need_secrets` ownership boundary

The service's `need_secrets` implementation is intentionally narrow: if the authenticated gateway/cookie/certificate-result values are missing, NetworkManager requests a secret/authentication phase rather than making the privileged service reproduce all interactive authentication itself.

The source comment explicitly places certificate/SecurID and related interactive work in the user's authentication-dialog context.

This is important for PVNetwork: privileged network/tunnel lifecycle should not own product authentication UI. Authentication UI/service and engine transport state should communicate through a typed product-owned boundary.

## Authentication frontend and libsecret

`auth-dialog/main.c` defines a dedicated `SecretSchema` named:

`org.freedesktop.NetworkManager.Connection.Openconnect`

with attributes including:

- VPN UUID;
- authentication-form ID;
- field label.

The auth frontend creates explicit in-memory records for passwords selected for persistence and writes them using libsecret. It also keeps distinct hash tables for input options, supplied secrets, successful secrets and successful passwords.

### PVNetwork lesson

Use a strongly typed credential policy instead of one vague "remember" flag:

- reusable password/secret;
- username/account identifier;
- remembered non-secret group/realm/form choice;
- certificate/key reference;
- short-lived cookie/token/session result.

Each class needs its own retention, redaction and deletion policy.

## Authentication UI model

The GNOME auth frontend dynamically renders server-provided form prompts and selections. The source shows separate handling for text/password fields, choice fields, group changes, notices/errors, certificate decisions and browser/webview-assisted SSO.

That reinforces the existing PVNetwork decision to use a generic **Auth Challenge Model** rather than hard-coding one username/password screen for enterprise VPN families.

## Browser / SSO boundary

The pinned auth source integrates WebKit-based web authentication and references the OpenConnect webview callback. Optional Entra Conditional Access support is also separated behind an additional integration dependency.

PVNetwork should define a platform Browser/SSO service with a narrow callback/result contract instead of embedding browser behavior inside the networking Core Adapter.

## Logging and secret exposure

The service exposes a debug option whose own help text warns that verbose debug logging may expose passwords. This is a direct reminder that PVNetwork production diagnostics must have explicit secret-redaction rules and cannot assume upstream debug output is safe for support bundles.

## Privilege and process boundary

The NetworkManager implementation demonstrates a split between privileged/system VPN service responsibilities and user-context authentication. It also has service-specific D-Bus policy and a dedicated helper path.

PVNetwork should preserve the principle:

`Product UI/Auth -> Enterprise Adapter -> Platform Network Service -> libopenconnect/runtime`

without copying the exact GNOME service topology onto Windows, Apple or Android.

## Regression requirements derived from this map

Future PVNetwork Linux/enterprise tests should verify:

- UI cancellation cannot leave privileged session/service state active;
- reusable secrets and transient cookies are never stored in the same persistence class;
- desktop keyring failures degrade safely and visibly;
- reconnect does not accidentally reuse expired session material as long-term credentials;
- service restart does not duplicate or corrupt profile state;
- D-Bus/service lifecycle and product-visible connection state remain synchronized;
- debug/support export redacts all secret classes;
- browser/SSO results are scoped to the correct session/profile;
- a profile edit does not mutate the active runtime representation behind the UI's back.

## Remaining gaps

- inspect current canonical GNOME GitLab changes beyond the pinned mirror where they materially differ;
- map exact NetworkManager SecretAgent interaction outside this plugin repository;
- map GTK3/GTK4 widgets to source/resources screen by screen;
- packaging behavior across target Linux distributions;
- current issue/MR review for secret storage, SSO and service lifecycle;
- real platform behavior once PVNetwork has an implementation to test.
