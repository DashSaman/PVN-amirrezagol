# SoftEther VPN Protocol — Cryptography and Authentication Boundaries

Review date: 2026-08-14

Entry: 013 SoftEther VPN Protocol.

## 1. Transport security

The native SoftEther client/server protocol uses TLS-protected TCP transport in the canonical implementation. TLS provides the network confidentiality/integrity boundary; native session/user authentication and Virtual Hub authorization remain separate layers.

PVNetwork must use the maintained SoftEther/platform TLS implementation and must not recreate protocol cryptography.

## 2. Server identity

Record and validate for the selected build/profile:

- certificate subject/SAN and endpoint name;
- chain/trust provenance;
- validity/revocation policy;
- private-key ownership/permissions;
- effective TLS version/cipher/provider.

Do not silently disable certificate validation to gain compatibility.

## 3. User/session authentication

SoftEther's Virtual Hub/user model can use multiple authentication methods/backends according to product configuration. Treat each as a capability with exact selected-release evidence.

Separate:

- TLS server certificate/private key;
- native client/session authentication credential;
- user/group authorization inside the Virtual Hub;
- RADIUS/domain/external-auth shared secrets/credentials where used;
- management administrator credentials.

## 4. Management plane is separate

Server Manager/vpncmd administration credentials/channels are not end-user VPN credentials. Never reuse management secrets as client session credentials by default.

## 5. Multiprotocol compatibility boundary

SSTP, L2TP/IPsec, OpenVPN-compatible and EtherIP modes may have different cryptographic stacks. Their crypto rules belong to their own entries. Do not infer native SoftEther TLS/session security from a compatibility listener and vice versa.

## 6. Secure storage

- server private key -> restricted certificate/key store;
- local user password/verifier -> SoftEther-owned protected config/database semantics at selected release;
- external AAA secrets -> server-side secure secret reference;
- client password/private key/certificate -> OS/product secure store reference;
- TLS/session keys -> ephemeral backend-only state;
- admin password -> separate privileged secret.

Backups containing server configuration/user material require explicit encryption/access/retention policy.

## 7. Algorithm policy

At source freeze and each release certification record:

- bundled/system TLS library/provider versions;
- enabled TLS protocol versions;
- cipher/security level;
- certificate key/signature requirements;
- any compatibility downgrade flags;
- selected user-auth mechanism.

Do not freeze historical TLS/cipher defaults as current policy.

## 8. Virtual Ethernet security

TLS protects packets between native VPN endpoints, but after decapsulation the Virtual Hub carries Ethernet traffic. Continue to enforce:

- hub/user/group security policy;
- bridge/VLAN segmentation;
- DHCP/ARP/ND controls where relevant;
- SecureNAT/firewall/routing policy;
- broadcast/multicast/MAC scale controls.

## 9. Logging/redaction

Safe diagnostics may include TLS version/cipher, server certificate fingerprint/expiry, auth method name, hub/session ID, connection count and counters.

Never log passwords, private keys, external AAA secrets, TLS/session keys or full sensitive server config.

## 10. Required security tests

- wrong/untrusted/name-mismatch/expired certificate;
- certificate rotation;
- wrong user credential/certificate;
- external AAA failure;
- management credential isolation;
- obsolete TLS/cipher rejection;
- no clear/native-protocol fallback after TLS failure;
- secret/log/export redaction;
- Virtual Hub authorization/segmentation after successful tunnel authentication.
