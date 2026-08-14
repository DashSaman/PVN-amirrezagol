# OpenVPN — Cryptography / Authentication / Security Model

Reference layer: `COMPLETE-REFERENCE-v2`

Research date: 2026-08-14

State: `IN-RESEARCH` — architecture is documented; exact algorithm defaults/support must always be pinned to server/client/core/TLS-backend versions.

## Security architecture summary

OpenVPN separates two major cryptographic planes:

1. **Control channel** — TLS-based authenticated key-management/control exchange.
2. **Data channel** — symmetric authenticated/encrypted packet protection using keys negotiated/derived through the control-channel session.

Do not describe OpenVPN as “just TLS” or “just AES”. The effective security is a combination of:

- TLS protocol/version and certificate/authentication configuration;
- optional pre-TLS control-channel protection;
- data-channel cipher/AEAD negotiation;
- key generation/rekeying;
- authentication plugins/credentials;
- TLS/crypto library implementation;
- client/server versions and policy;
- optional DCO/kernel data path.

---

# 1. Control channel

## TLS role

The TLS control channel establishes an authenticated secure control session and provides the context for negotiating/deriving keying material used by OpenVPN sessions.

A typical certificate-based deployment contains:

- CA trust anchor;
- server certificate/private key;
- client certificate/private key where mutual certificate authentication is used;
- optional additional username/password/MFA authentication;
- TLS protocol/cipher/signature/key-exchange policy;
- peer identity/certificate validation.

PVNetwork must record the actual TLS backend/version used by each client/server build because OpenSSL/mbedTLS/platform differences can affect available algorithms and behavior.

## Server identity validation

Client security requires more than trusting any certificate signed by a CA.

PVNetwork import/canonical model should preserve relevant server identity constraints and certificate policy from the profile.

Tests must include:

- trusted correct server identity;
- wrong hostname/name constraint;
- untrusted CA;
- expired/not-yet-valid certificate;
- revoked certificate when CRL/verification policy applies;
- wrong client cert/key;
- certificate rotation.

Never add a silent “accept invalid certificate” fallback.

---

# 2. `tls-auth`

OpenVPN configurations may use a static shared key to authenticate/control pre-TLS packets using `tls-auth`-style protection.

Security purpose:

- reject unauthenticated control-channel packets before expensive TLS processing;
- add an additional shared secret/authentication barrier;
- reduce exposure to some scanning/DoS/control-channel attacks.

Important modeling rules:

- the static key is a reusable secret and must be protected;
- client/server key direction semantics may matter for profile compatibility;
- it is additional control-channel protection, not a replacement for TLS certificates/authentication;
- do not log/export it accidentally.

PVNetwork should store imported static-key material in secure secret storage and generate runtime config/reference paths as needed.

---

# 3. `tls-crypt`

`tls-crypt`-style protection protects the TLS/control-channel packet layer using an additional shared key and provides both confidentiality/obfuscation and authentication for protected control-channel traffic according to the selected OpenVPN feature/version.

Product rules:

- treat `tls-auth` and `tls-crypt` as distinct capabilities;
- preserve the exact imported key/mode;
- never silently convert one into the other;
- store the key as protected secret material;
- test version interoperability.

There are newer/extended control-channel key-management modes in modern OpenVPN releases; they must be documented/version-pinned separately rather than inferred from generic `tls-crypt` support.

---

# 4. Data channel encryption

After the control channel establishes session keying state, OpenVPN protects tunnel data with symmetric data-channel keys.

Modern configurations commonly use **AEAD** ciphers such as AES-GCM and, where supported by the build/backend, ChaCha20-Poly1305. Exact supported/default/negotiated ciphers are version and build dependent.

PVNetwork must not hardcode one assumed cipher as “OpenVPN encryption”.

Record at runtime:

- client data-cipher capability list;
- server data-cipher policy;
- negotiated data cipher;
- whether legacy cipher negotiation/fallback was required;
- DCO compatibility with the negotiated cipher;
- crypto backend/version.

## AEAD

AEAD combines encryption and integrity/authentication in one construction.

Product diagnostics can safely show the negotiated cipher name, but must never expose key material/nonces/state.

## Legacy data ciphers

Older OpenVPN profiles/servers may specify legacy non-AEAD ciphers/auth combinations.

PVNetwork policy:

- do not silently enable obsolete ciphers;
- distinguish `Unsupported` from `Legacy security required`;
- require an explicit compatibility policy for weak/obsolete settings;
- surface a warning;
- preserve original profile semantics.

---

# 5. HMAC / packet authentication in legacy modes

Older non-AEAD data-channel configurations can use separate integrity/HMAC algorithms.

Treat this as a separate legacy capability. Do not assume the presence of an `auth` directive means modern AEAD data packets use a separate HMAC in the same way.

Canonical model should preserve imported directives and let the engine/version capability validator determine the effective cryptographic behavior.

---

# 6. Key derivation and rekeying

OpenVPN sessions derive/generate data-channel keying material from the authenticated control-channel session and can periodically renegotiate/rekey according to protocol/configuration behavior.

PVNetwork quality requirements:

- long-duration connection survives scheduled rekey;
- traffic does not leak during rekey;
- UI remains Connected/Rekeying rather than falsely Disconnected;
- server/client clock/time issues are diagnosed separately;
- network handover near rekey does not deadlock session state;
- data-channel key material is never persisted in normal application storage/logs.

Do not expose raw session keys to user-visible diagnostics.

---

# 7. Username/password / MFA / external authentication

OpenVPN can combine certificate/TLS security with additional user authentication through server/product/plugin mechanisms.

These credentials are **not the same thing as TLS private keys**.

PVNetwork canonical model should distinguish:

- username;
- password secret reference;
- OTP/MFA challenge (usually transient, not saved);
- SSO/browser token state;
- client certificate identity;
- private key reference;
- PKCS#11/hardware token identity where supported.

Never persist OTP codes or temporary SSO tokens unless the external protocol explicitly requires a protected refresh token and the product policy permits it.

---

# 8. PKI

Common Community deployments use an X.509 PKI:

- CA certificate/key;
- server certificate/key;
- per-client certificates/keys;
- certificate serial/validity;
- CRL/revocation.

Server automation requirements:

- CA private key should not be casually stored on every runtime VPN server when an offline/external CA model is selected;
- strict file permissions;
- explicit certificate expiration monitoring;
- revoke compromised client identities;
- backup/restore protection;
- external enterprise CA support.

PVNetwork client requirements:

- certificate/private key stored in platform keystore/keychain or protected vault where possible;
- support external/hardware identity only through approved platform/core capability;
- profile export must clearly state whether private key is included.

---

# 9. TLS backend / crypto library

OpenVPN/OpenVPN3 builds can use different crypto/TLS backends depending on project/platform/build configuration.

For each shipped PVNetwork/OpenVPN component record:

- TLS/crypto backend name;
- backend version;
- build options;
- enabled protocol versions;
- enabled algorithms/providers;
- FIPS/compliance mode if actually claimed;
- relevant CVE/advisory status.

A server using OpenSSL and a client using another supported backend can interoperate at protocol level while still having different local security/feature surfaces.

---

# 10. DCO (Data Channel Offload)

DCO moves supported OpenVPN data-channel processing from the traditional userspace path into an OS/kernel/driver implementation.

Security/architecture implications:

- control channel remains managed by user-space OpenVPN core/process;
- negotiated data-channel state is installed into the DCO/kernel backend;
- encrypted tunnel packets/data-channel processing follow the supported kernel/driver path;
- only supported cipher/features can use DCO;
- unsupported directives/features can force userspace fallback or make a profile incompatible with DCO.

PVNetwork must report:

- DCO available?
- DCO selected?
- DCO active?
- fallback reason?
- DCO module/driver version?
- negotiated cipher?

Never show “DCO enabled” just because the setting is on if the runtime path fell back.

---

# 11. Compression security

Compression in encrypted VPN tunnels has a security history because compression can interact with attacker-controlled plaintext and encrypted size observations.

PVNetwork policy:

- treat compression as legacy/compatibility-sensitive;
- do not enable compression automatically to make old profiles work;
- distinguish old compression directives from modern no-compression/stub compatibility behavior;
- warn when a server genuinely requires insecure legacy behavior;
- test exact client/server version compatibility.

---

# 12. Randomness / secret generation

Private keys, static control-channel keys, session keys and certificate keys require cryptographically secure random sources from approved libraries/OS facilities.

PVNetwork must never implement its own RNG or deterministic key generation outside a carefully defined test environment.

---

# 13. Secrets classification

## Long-lived secrets

- CA private key (server administration, if locally held);
- server private key;
- client private key;
- tls-auth/tls-crypt static key;
- saved user password;
- PKCS#11 PIN where policy permits saving;
- Access Server/API refresh credentials where applicable.

## Transient secrets

- OTP/MFA code;
- active TLS/session keys;
- data-channel session keys;
- short-lived SSO token/session cookie.

PVNetwork logs/support bundles must exclude both categories unless a deliberately redacted diagnostic representation exists.

---

# 14. Secure-default policy for PVNetwork

The future OpenVPN Adapter should expose three policy states:

### Modern default

Use current supported TLS/data cipher policy and reject obsolete profile directives unless compatibility is explicitly allowed.

### Compatible

Allow selected older but still accepted settings with visible diagnostics/warnings.

### Legacy / blocked

Unsupported or insecure settings requiring an explicit administrator/user override, or completely blocked when unacceptable on the platform/Store build.

Never silently lower security to connect.

---

# 15. Cryptographic test matrix

For each server/client/backend combination test:

- valid certificate baseline;
- bad CA;
- bad/expired cert;
- wrong identity;
- wrong user password;
- MFA challenge;
- tls-auth correct/wrong/missing key;
- tls-crypt correct/wrong/missing key;
- modern AEAD cipher negotiation;
- no-overlap data-cipher failure;
- legacy cipher policy behavior;
- rekey under traffic;
- reconnect after rekey/network change;
- DCO vs userspace negotiated-cipher path;
- compression rejection/warning;
- secret redaction.

---

# 16. Do not claim one fixed OpenVPN cipher suite

A marketing line such as “OpenVPN uses AES-256” is technically incomplete and can be false for a particular session.

PVNetwork should report/certify **effective negotiated cryptography**, not a hardcoded brand-level cipher claim.

## Remaining v2 gaps

- exact algorithm/default tables for selected Community Server and OpenVPN3 versions;
- exact TLS backend algorithm availability per platform;
- tls-crypt version extensions/current support table;
- DCO cipher/feature support per selected OS/driver;
- legacy algorithm blocklist policy;
- authoritative security advisory/CVE map.
