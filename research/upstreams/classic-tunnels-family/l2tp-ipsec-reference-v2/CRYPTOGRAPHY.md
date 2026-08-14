# L2TP/IPsec — Cryptography and Authentication Boundaries

Review date: 2026-08-14

Entry: 008 L2TP/IPsec.

L2TP/IPsec has multiple authentication/security layers. A secure product must model them independently rather than hiding everything behind one `password` field.

## 1. L2TP itself is not the VPN confidentiality layer

RFC 2661 defines L2TP as a tunneling protocol for PPP over packet networks. L2TP control/data framing and tunnel authentication do not, by themselves, provide the security services expected from a modern remote-access VPN.

RFC 3193 explicitly states that L2TP tunnel authentication and PPP authentication do not provide the required per-packet authentication/integrity/replay protection for L2TP traffic. The specification selects IPsec ESP as the required protection mechanism for compliant L2TP security.

Product rule:

**Never describe plain L2TP as encrypted simply because the client also has a PPP password or L2TP tunnel secret.**

## 2. IPsec protection layer

RFC 3193 requires IPsec ESP to protect L2TP control and data traffic and requires transport-mode support. Its original 2001 algorithm language references the IPsec standards/algorithm requirements of that era.

For PVNetwork in 2026:

- reuse the current IKE/IPsec v2 reference under `strongswan-family/reference-v2/`;
- do not copy DES/3DES/SHA1-era examples/requirements into product defaults;
- define a current policy based on selected platform/server capabilities and current IKE/IPsec guidance;
- legacy L2TP/IPsec interop may require compatibility algorithms on some old servers, but those must be explicit per-profile exceptions with warnings and lab proof.

## 3. IKE version boundary

The standardized RFC 3193 composition predates IKEv2 and describes IKEv1 Phase 1/Phase 2/Quick Mode behavior.

Therefore:

- an implementation that supports IKEv2 does not automatically provide standards-compatible L2TP/IPsec to legacy clients;
- exact L2TP/IPsec client/server stacks must be tested for the IKE version they actually use;
- do not silently downgrade a modern IKEv2 profile to legacy L2TP/IPsec/IKEv1;
- classify entry 008 as a legacy composed target.

## 4. Machine/peer authentication at IPsec layer

Common L2TP/IPsec client stacks support an IPsec machine/peer authentication method such as:

- pre-shared secret;
- certificate-based machine authentication on stacks that support it.

Current platform examples:

- Apple deployment documentation exposes a shared secret for L2TP VPN management profiles and additional user-auth options;
- Windows `Add-VpnConnection` supports `L2tpPsk`; if no L2TP PSK is specified, Microsoft documents certificate use for L2TP IPsec authentication;
- NetworkManager-l2tp supports machine PSK or TLS certificate through its selected strongSwan/Libreswan backend.

### Storage rule

Machine PSK/private key/certificate identity must be a separate secure credential object from the PPP username/password.

Never serialize a reusable machine PSK into logs, analytics or ordinary unencrypted profile exports.

## 5. L2TP tunnel authentication

RFC 2661 includes an optional tunnel authentication mechanism based on L2TP AVPs/challenge-response semantics.

RFC 3193 makes clear that L2TP tunnel authentication alone is insufficient for packet security. In many common L2TP/IPsec remote-access deployments, the major security boundaries users encounter are IPsec machine authentication plus PPP user authentication.

PVNetwork rule:

- model an L2TP tunnel secret only if the selected implementation/server actually requires/exposes it;
- do not confuse it with the IPsec PSK;
- do not label it as encryption.

Accel-PPP's source-visible L2TP configuration contains a `secret` option; this remains a separate L2TP control-layer value from IPsec machine credentials.

## 6. PPP link/authentication layer

RFC 1661 defines PPP as:

1. multiprotocol encapsulation;
2. LCP link establishment/configuration;
3. optional authentication;
4. NCP configuration for network-layer protocols.

PPP authentication is negotiated through the PPP link; it is not the IKE/IPsec peer authentication.

Common implementation options include PAP, CHAP and MS-CHAP variants. Accel-PPP source explicitly contains PAP, CHAP-MD5, MS-CHAPv1 and MS-CHAPv2 modules. NetworkManager-l2tp supports user credentials/TLS certificate options through its chosen stack.

## 7. MS-CHAPv2

RFC 2759 defines MS-CHAPv2 as a PPP CHAP dialect and notes its challenge/response and mutual-authentication behavior.

The existence of an RFC does not make MS-CHAPv2 a modern standalone cryptographic protection layer. In L2TP/IPsec, IPsec is still responsible for protecting L2TP/PPP packets on the network path.

Product policy:

- expose PPP auth only when required by the server/profile;
- prefer the strongest server-supported user authentication compatible with the selected native backend;
- do not use PPP authentication as a substitute for IPsec protection;
- do not claim that MS-CHAPv2 itself encrypts the VPN payload.

## 8. PPP encryption/compression boundary

RFC 3193 explicitly discusses possible PPP encryption/compression in addition to IPsec and notes that L2TP security compliant implementations rely on IPsec for the required security services.

PVNetwork should avoid automatically layering duplicate/legacy PPP encryption on top of IPsec unless a specific interoperable server profile requires it.

Record separately:

- IPsec ESP transform/effective confidentiality;
- PPP ECP/MPPE state if used;
- PPP compression state;
- server/client policy reason.

## 9. Credential-class model

Recommended canonical credential classes:

### IPsec machine/peer credential

- PSK secret reference;
- certificate/private-key identity reference;
- trust anchors and certificate-validation policy.

### L2TP tunnel-auth credential

- optional tunnel secret/reference if the implementation uses it.

### PPP/user credential

- username/account;
- password/OTP/token/reference;
- client certificate where supported by the selected stack;
- AAA/RADIUS backend provenance on the server.

### Ephemeral session state

- IKE-derived keys;
- ESP traffic keys;
- L2TP tunnel/session IDs;
- PPP challenge/session state.

Ephemeral cryptographic/session secrets belong only in the executing backend memory/state, not canonical product persistence.

## 10. Server secret storage

### xl2tpd + pppd stack

A production audit must inspect:

- IPsec PSK/private-key files and permissions;
- pppd `chap-secrets`/`pap-secrets` or RADIUS integration;
- xl2tpd tunnel secret if configured;
- generated configs/temp files;
- service user privileges.

### Accel-PPP

Source config shows local auth modules and optional RADIUS. A production deployment should prefer a controlled AAA/secret backend and strict config permissions rather than leaving reusable secrets in broadly readable files.

### NetworkManager-l2tp

Its current README documents generated runtime `ipsec.conf`, PPP options and an IPsec secrets include file, plus a Libreswan NSS certificate database. This is a concrete reason to audit filesystem permissions, cleanup and secret-at-rest behavior separately from the GUI password field.

## 11. Logging/redaction

Never log:

- IPsec PSK;
- L2TP tunnel secret;
- PPP password;
- private key;
- RADIUS shared secret;
- derived IKE/ESP keys;
- full authentication challenge/response material unless a narrowly scoped secure diagnostic explicitly requires safe fragments.

Safe diagnostics can include:

- auth class/method;
- certificate fingerprint/issuer metadata;
- IKE version;
- ESP algorithm identifiers;
- PPP auth method name;
- L2TP tunnel/session IDs where privacy policy permits;
- failure category.

## 12. Legacy policy

Current platform evidence reinforces legacy status:

- Android's current developer guide calls its built-in PPTP/L2TP-IPsec stack “legacy VPN”;
- new Windows Server 2025 RRAS deployments do not accept L2TP/PPTP by default unless explicitly enabled;
- modern Apple platforms still support managed L2TP over IPsec for compatibility.

Therefore PVNetwork policy should be:

`EXPLICIT LEGACY COMPATIBILITY / NOT NEW-DEPLOYMENT DEFAULT`

## 13. Required security tests

Before any strict support claim:

- prove that L2TP UDP traffic never proceeds in clear when the profile requires L2TP/IPsec;
- verify IPsec policy/SA binding to the correct L2TP endpoints/ports;
- negative test wrong machine PSK/certificate;
- negative test wrong PPP credentials;
- prove no silent fallback to plain L2TP;
- prove no silent IKE version downgrade;
- prove secret redaction and temp-file cleanup;
- verify reconnect/rekey keeps L2TP/PPP session ownership consistent;
- inspect obsolete algorithm negotiation and enforce per-profile policy.
