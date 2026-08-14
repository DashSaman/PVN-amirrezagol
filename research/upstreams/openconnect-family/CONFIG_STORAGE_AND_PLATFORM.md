# OpenConnect — Configuration, Persistence, Secrets and Platform Boundaries

Review date: 2026-08-14

Canonical core source/documentation: `openconnect/openconnect` GitLab / `infradead.org/openconnect`.

Status: `IN-RESEARCH`; no PVNetwork implementation claim.

## 1. Core configuration-file format

The official OpenConnect manual documents `--config=CONFIGFILE`.

The config file is a simple line-oriented option file:

- long-form command-line option names are written without the leading `--`;
- empty lines are ignored;
- lines whose first non-space character is `#` are comments;
- options from the config file are processed before later command-line options;
- the config option itself cannot recursively appear in the file.

This is an engine/CLI configuration format. It is **not** the proposed PVNetwork canonical profile-storage format.

PVNetwork rule:
- parse/import supported OpenConnect-style configuration into a canonical product model;
- validate and expose unsupported/lossy fields;
- generate runtime engine configuration only at the adapter boundary;
- do not store arbitrary engine CLI text as the sole product profile representation.

## 2. Authentication and connection can be two separate phases

Official documentation explicitly describes OpenConnect as having an authentication phase followed by a tunnel-connection phase.

The `--authenticate` mode can complete authentication without creating the tunnel and returns connection material such as:

- authentication cookie/session material;
- the exact server/connection target;
- certificate fingerprint/trust state;
- DNS-name/IP resolution information needed to reconnect consistently.

`--cookieonly` is a narrower authentication-only path.

This is a major architecture clue for PVNetwork:

`Auth UI/Browser/Certificate access -> authenticated session result -> privileged/network connection phase`

The two stages can run in different privilege/security contexts. Product code should model the authenticated-session result as short-lived secret material, not as a durable plaintext profile.

## 3. Secret classification

PVNetwork must distinguish at least:

### Durable profile metadata
- profile display name;
- server URL/host;
- selected protocol/vendor mode;
- group/realm/gateway preferences;
- non-secret routing/UI preferences;
- feature/capability settings.

### Durable protected credentials, only when the user explicitly chooses persistence
- passwords where policy permits;
- token seeds only where absolutely required and supported;
- private-key passphrases where appropriate;
- client certificate/key references;
- other reusable enterprise credentials.

### Short-lived session secrets
- authenticated VPN cookies;
- SSO/SAML result state;
- challenge/session identifiers;
- transient browser callback values;
- temporary certificate/auth continuation state.

### Trust decisions
- certificate fingerprint/pinning state;
- accepted server identity plus the hostname/port/context for which that trust decision applies.

Session secrets must not be written into ordinary logs, analytics or durable profile storage unless the specific upstream protocol and product feature require a protected persistence model and that model has been reviewed.

## 4. Certificate-trust handling

The public OpenConnect API/documentation includes peer-certificate hash/checking functions and trust controls. Current header documentation warns frontend developers not to cache a certificate hash without also binding it to the correct server context and not to compare fingerprints naively when hash formats can evolve.

PVNetwork requirements:

- normal system PKI validation first;
- user exception/pinning only through explicit trust UI;
- store hostname/port/context with any remembered exception;
- use adapter/library validation helpers instead of hand-written string comparison;
- distinguish CA trust, explicit pin and user-accepted exception;
- provide deletion/reset of remembered trust decisions.

## 5. OpenConnect GUI profile storage is not CLI config

OpenConnect GUI issue history confirms that its GUI profile storage uses its own application format/state and is not simply an OpenConnect CLI `--config` file.

This validates PVNetwork's separation rule:

- product profile database/model;
- engine CLI/library runtime configuration;
- export/import representation;
- protected secrets;
- diagnostic bundle.

Do not promise that a GUI profile file can be directly consumed by the OpenConnect CLI unless the product explicitly exports a compatible representation.

## 6. Credential UX lesson from OpenConnect GUI

OpenConnect GUI issue history shows that the term **Batch Mode** has been used for behavior that users interpret as remembering passwords and other login state. Maintainers/users have discussed that this is confusing and may need to be split into separate controls.

PVNetwork should expose explicit choices such as:

- remember username;
- remember non-secret group/realm selection;
- remember password securely, if allowed;
- use system/browser SSO;
- clear saved credentials;
- clear remembered trust decision.

Do not expose engine/internal terminology when the user-facing behavior is credential persistence.

## 7. NetworkManager-openconnect storage model

The current NetworkManager-openconnect build links against `libsecret` for the GNOME/GTK path and uses NetworkManager's connection/secret architecture.

Its authentication-dialog design distinguishes:

- tunnel/authentication secrets;
- remembered frontend choices/state;
- profile/connection settings managed by NetworkManager;
- browser/WebKit auth handling when required.

This is a useful Linux reference: product profile state and credential storage can be owned by higher-level platform services while libopenconnect remains the protocol/auth engine.

PVNetwork must still define its own canonical cross-platform storage contract rather than inheriting NetworkManager's schema.

## 8. Route/DNS ownership boundary

Traditional OpenConnect CLI packaging uses a `vpnc-script`-compatible helper to apply routing and DNS/network configuration after the core negotiates parameters.

That helper model demonstrates a clean boundary:

- core negotiates tunnel/network data;
- platform network integration applies routes/DNS/interface state;
- cleanup must run on disconnect/failure.

PVNetwork should strongly prefer a product-owned platform networking abstraction on platforms where native APIs/services are more appropriate, while preserving the same conceptual separation.

## 9. Platform-specific persistence direction

### Windows

Candidate design to evaluate:
- canonical PVNetwork profile store in application data;
- credentials/secrets protected using Windows-supported credential/DPAPI mechanisms;
- privileged networking service owns only the minimum connection/runtime state;
- no plaintext session cookie persistence;
- explicit export creates a separate user-requested portable file.

### Android

Candidate design to evaluate:
- app profile database/state separate from Android Preferences DataStore-style ordinary settings;
- secrets through Android Keystore-backed encryption or appropriate credential APIs;
- VpnService receives only active-session material;
- process-death recovery must not require storing raw transient cookies in logs/preferences.

### Apple platforms

Candidate design to evaluate:
- Keychain for reusable secrets/certificate references;
- app/NetworkExtension shared access only through approved entitlement/access-group architecture;
- short-lived session material passed through the narrowest extension-safe channel;
- no assumption that desktop CLI config is an appropriate NetworkExtension persistence format.

### Linux

Candidate design to compare:
- Secret Service/libsecret and NetworkManager integration where selected;
- application-managed protected storage for standalone mode;
- canonical profile format independent of desktop environment;
- clear migration between standalone and NetworkManager-backed modes if both are offered.

## 10. Import/export rules for PVNetwork

For OpenConnect-family profiles:

1. detect source format;
2. parse to canonical model;
3. classify secret vs non-secret fields;
4. validate vendor/protocol-specific fields;
5. report fields that cannot round-trip safely;
6. store credentials only according to explicit product policy;
7. export only on explicit user action;
8. redact secrets from support/diagnostic exports by default.

## 11. Logs and debug data

OpenConnect supports very verbose HTTP/auth debug output. Such diagnostics can include highly sensitive protocol/auth context if exposed carelessly.

PVNetwork requirements:

- ordinary logs never enable raw HTTP/body dumps;
- diagnostic mode is explicit and time-bounded;
- cookies, Authorization headers, SSO tokens, passwords, private keys and reusable challenge secrets are redacted before persistence/export;
- support bundles label what may contain server/user identifiers;
- diagnostic export must not silently include protected profile credentials.

## 12. Current architecture conclusion

The evidence supports a four-way separation:

1. **PVNetwork canonical profile and product settings**
2. **protected credential/trust store**
3. **short-lived authenticated session material**
4. **OpenConnect runtime/adapter configuration and platform networking state**

This separation is mandatory for the future Enterprise Adapter design.

## Remaining gaps

- exact current OpenConnect GUI storage classes/files on Windows and macOS;
- exact NetworkManager secret flags/schema and D-Bus ownership;
- final cross-platform PVProfile field schema;
- final credential-store abstraction;
- backup/restore and migration policy;
- privacy/threat model for support bundles;
- platform-specific implementation proof after PVNetwork code exists.