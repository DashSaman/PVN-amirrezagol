# PPTP — Cryptography, Authentication and Security Limitations

Review date: 2026-08-14

Entry: 012 PPTP.

PPTP is retained only as an obsolete compatibility reference. Its common security design is not acceptable as a modern VPN default.

## 1. PPTP itself does not provide modern cryptographic transport security

RFC2637 defines PPTP control and GRE tunneling for PPP. Cryptographic protection historically comes from PPP-layer authentication/encryption mechanisms, especially Microsoft MPPE in common Windows deployments.

Do not describe the PPTP GRE tunnel itself as encrypted.

## 2. PPP authentication

PPP may negotiate PAP, CHAP or Microsoft CHAP variants. Legacy Windows PPTP deployments commonly use MS-CHAPv2.

RFC2759 documents MS-CHAPv2. Its presence is protocol history, not a current security recommendation.

Product rule:

- authentication method must be visible in technical status;
- weak modes are never auto-enabled;
- wrong-user-password errors are separate from PPTP control/GRE failures.

## 3. MPPE

RFC3078 defines Microsoft Point-to-Point Encryption (MPPE) for PPP, historically using RC4-based encryption with negotiated key lengths and stateful/stateless modes.

RFC3079 defines key derivation from MS-CHAP/MS-CHAPv2-related material.

Security conclusion:

- MPPE is legacy PPP encryption;
- it does not upgrade PPTP into an acceptable modern default;
- legacy RC4/MS-CHAP-derived designs should not be marketed as modern cryptographic security;
- product policy should prefer migration rather than inventing stronger proprietary crypto inside PPTP.

## 4. No custom cryptographic upgrade

Do not add a proprietary encryption layer to PPTP. If modern confidentiality/authentication is required, migrate the profile to a modern protocol.

The only reason to retain PPTP/MPPE is exact legacy interoperability.

## 5. Authentication vs encryption

Keep these states separate:

- PPTP control connection established;
- GRE call established;
- PPP link established;
- PPP user authentication succeeded;
- MPPE negotiated and active/inactive;
- network configuration complete.

A PPTP connection can technically carry PPP with different authentication/encryption choices. UI/security policy must not infer one from another.

## 6. Server/client policy

Recommended PVNetwork legacy policy:

- require an explicit admin opt-in to enable PPTP;
- show a prominent obsolete-security warning;
- scope access to the minimum required networks;
- prefer the strongest mutually supported legacy PPP auth/encryption only when unavoidable;
- set a migration target/profile;
- no silent fallback from a modern protocol.

## 7. Credential classes

### PPP user credential

- username/password or RADIUS-backed identity;
- secure credential reference;
- never ordinary plaintext JSON/logging.

### RADIUS shared secret

Server-side separate secure secret.

### MPPE runtime key state

Derived ephemeral PPP runtime material; backend only, never persisted/logged.

### PPTP Call IDs

Non-secret protocol identifiers, not keys.

## 8. Password handling

Do not put legacy PPP passwords in:

- command-line arguments visible to other users;
- world-readable `chap-secrets`/config files;
- analytics;
- debug bundles without redaction;
- QR/profile exports unless explicitly encrypted/export-approved.

Linux legacy stacks require exact audit of pppd secret files/plugins/process invocation.

## 9. Server security hardening

If PPTP cannot yet be retired:

- restrict exposure to known source networks/VPN gateways when possible;
- rate-limit/auth-monitor;
- segment the reachable internal network;
- use per-user credentials, not shared credentials;
- disable weak auth modes not required by the estate;
- monitor connection inventory and retirement progress;
- keep OS/router firmware patched even though the protocol itself remains obsolete.

## 10. NAT/PPTP ALG is not security

PPTP ALG/NAT helpers inspect/control PPTP TCP1723 and GRE Call IDs to create NAT state. This is an interoperability mechanism, not cryptographic protection.

Do not label `PPTP helper enabled` as a security feature.

## 11. Platform direction

Current platform/vendor behavior reinforces obsolete status:

- Apple removed native PPTP years ago;
- Android documents PPTP as legacy;
- Windows Server 2025 new RRAS setups do not accept PPTP by default;
- MikroTik current docs warn PPTP has known security issues and should not be used where security matters.

## 12. Logging/redaction

Safe diagnostics:

- auth method name;
- MPPE negotiated yes/no and key length/mode identifiers where backend exposes them safely;
- Call IDs;
- GRE sequence/ack state;
- assigned IP/routes;
- failure category.

Never log passwords, MPPE keys or RADIUS secrets.

## 13. Required security tests

- wrong user credential;
- weak auth disabled;
- MPPE required vs absent negotiation;
- no silent plaintext PPP fallback when policy requires MPPE;
- no silent downgrade from modern protocol to PPTP;
- secret-file/process/log redaction;
- rate-limit/brute-force behavior where server supports it;
- migration path success before legacy listener retirement.

Strict tests do not convert PPTP into a recommended secure protocol; they only bound a legacy compatibility deployment.
