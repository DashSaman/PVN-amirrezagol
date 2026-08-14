# SSTP / MS-SSTP — Cryptography and Authentication Boundaries

Review date: 2026-08-14

Entry: 011 SSTP / MS-SSTP.

SSTP's security model is layered. TLS protects the SSTP transport; SSTP includes protocol binding/security checks; PPP/EAP authenticates the user/session. Do not collapse these into one password or one `secure=true` flag.

## 1. TLS is the transport security boundary

MS-SSTP runs over HTTPS/TLS. The client establishes a TLS-protected connection to the SSTP server before SSTP control/data exchange.

TLS provides the primary confidentiality and integrity protection for SSTP frames over the network path.

PVNetwork rules:

- use the platform/native validated TLS stack where possible;
- validate server certificate chain, name and validity according to current platform policy;
- do not enable certificate-ignore/insecure modes as a silent compatibility fallback;
- use current TLS versions/cipher policy, not obsolete SSL/TLS examples from legacy deployments;
- record the effective TLS version/cipher/certificate chain in diagnostics where safe.

## 2. Server certificate

For Windows RRAS SSTP, current Microsoft administration guidance requires a suitable server-authentication certificate/private key available to the server and matching the hostname clients use.

Operational validation includes:

- trusted chain;
- server-authentication use/purpose as required by Windows policy;
- subject/SAN name match;
- valid dates;
- accessible private key;
- revocation/CRL/OCSP behavior according to Windows/platform configuration.

Certificate validation failures must not be relabeled as PPP authentication failures.

## 3. PPP/user authentication is separate

After SSTP tunnel/control setup, PPP negotiation/authentication occurs inside the SSTP tunnel.

Depending Windows/server/client policy, authentication may involve methods such as EAP or MS-CHAPv2 and corresponding enterprise credential/certificate infrastructure.

The exact approved method is a profile/backend capability and security-policy decision.

Do not claim the PPP password itself encrypts SSTP traffic; TLS protects the transport.

## 4. SSTP Crypto Binding / channel binding

The MS-SSTP protocol includes a crypto-binding mechanism designed to bind the SSTP/TLS channel and PPP authentication/session material so that independent tunnel/authentication layers cannot be trivially spliced by an attacker.

PVNetwork must treat crypto binding as part of the SSTP protocol-security state, not an optional UI decoration.

Record/validate according to the current Microsoft Open Specification and selected implementation:

- whether Crypto Binding Request/response is expected/supported;
- certificate hash protocol/algorithm information carried by the SSTP negotiation;
- compound-MAC/binding verification outcome;
- compatibility behavior for older peers.

Do not implement the binding calculation from memory; reuse native/maintained protocol code and test against Microsoft peers.

## 5. TLS certificate hash and protocol binding

SSTP control messages can carry attributes that identify server-certificate hash algorithms and participate in the crypto-binding flow. The exact wire fields and algorithm rules come from the current MS-SSTP specification.

Product code should expose high-level status such as:

- `ServerCertificateValidated`
- `CryptoBindingValidated`

while keeping low-level hash/MAC calculations inside the backend.

## 6. TLS termination boundary

Security depends on where TLS actually terminates.

### Direct SSTP termination

`client TLS -> RRAS/SoftEther SSTP server`

This is the simplest binding/trust model.

### Reverse proxy / TLS offload

Do **not** assume an ordinary HTTPS reverse proxy can terminate TLS and forward SSTP safely. SSTP uses a long-lived duplex HTTP/TLS tunnel and protocol binding to the TLS channel/server certificate.

Any TLS-offload topology requires implementation/vendor-specific evidence that SSTP semantics and crypto binding remain correct. Generic L7 proxying is not certified by HTTP 200 alone.

## 7. HTTP proxy traversal

An SSTP client may need to traverse an HTTP proxy or enterprise network to reach TCP443. Proxy authentication and CONNECT behavior are separate from SSTP/PPP authentication.

Credential classes:

- proxy credential;
- TLS server identity/trust;
- PPP/EAP user credential;
- server private key.

Never reuse one secret across these domains by default.

## 8. Windows credential/certificate storage

Native Windows profiles should reference/use Windows credential/certificate stores and supported profile mechanisms.

PVNetwork should persist:

- profile metadata;
- opaque credential/certificate references/provenance;
- non-secret routing/auth method choices.

Do not persist plaintext PPP passwords or server private keys in ordinary product JSON/logs/backups.

## 9. SoftEther credential boundary

SoftEther can own server certificate/private key plus its user/Virtual Hub/RADIUS authentication database. Treat these as SoftEther product/server secrets with its own storage/backup lifecycle, separate from PVNetwork product-profile secrets.

Refresh exact selected-release storage/encryption/backup behavior before deployment.

## 10. sstp-client credential boundary

A Linux client typically composes SSTP transport with PPP/pppd and system TLS libraries. Audit the selected release for:

- TLS library/provider;
- certificate validation defaults;
- CA/cert options;
- proxy credentials;
- PPP secret passing;
- command-line/process-list exposure;
- generated PPP options/temp files;
- secret-service integration if a desktop frontend is used.

The exact immutable source pin remains a source-freeze residual until materialized.

## 11. Algorithm policy

At implementation/release time record:

- TLS versions enabled;
- cipher suites/security level from OS/provider;
- certificate signature/key requirements;
- SSTP crypto-binding hash support;
- PPP/EAP authentication method;
- legacy compatibility exceptions.

Do not add TLS 1.0/obsolete ciphers or weak PPP auth automatically for an old server. Exceptions must be explicit, scoped and tested.

## 12. Secret classes

- SSTP server private key — secure certificate/private-key store;
- PPP/EAP user credential — OS/enterprise secure credential reference;
- proxy credential — separate secret reference;
- RADIUS shared secret — server-side secure config;
- TLS session keys — ephemeral TLS backend state;
- SSTP crypto-binding derived material — ephemeral protocol state;
- session identifiers/counters — non-secret operational metadata.

## 13. Logging/redaction

Never log:

- passwords;
- private keys;
- RADIUS secrets;
- proxy passwords;
- full Authorization/credential headers;
- TLS/session keys;
- crypto-binding secret material.

Safe diagnostics may include certificate thumbprint/fingerprint, issuer, TLS version/cipher, auth-method name, SSTP control state and high-level binding verification result.

## 14. Required security tests

- invalid/untrusted certificate;
- wrong hostname/SAN;
- expired/revoked certificate behavior;
- wrong PPP/EAP credential;
- SSTP crypto-binding success and tamper/negative case;
- TLS downgrade/obsolete policy rejection;
- proxy MITM/certificate substitution behavior;
- no clear SSTP/PPP fallback outside TLS;
- server certificate rotation;
- TLS/session reconnect;
- log/export redaction.
