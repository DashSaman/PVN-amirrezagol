# Fortinet FortiGate SSL VPN — Cryptography / Security Boundary

Review date: 2026-08-14 UTC

Legacy FortiGate SSL VPN tunnel mode is protected by TLS over the configured TCP listener. Fortinet documentation also supports optional DTLS/UDP tunnel transport to avoid TCP-over-TCP performance problems where gateway/client policy and network permit it.

Security boundaries:

- FortiGate server certificate/TLS policy establishes gateway identity and protects the tunnel/control path;
- user authentication, MFA, client certificate and machine/prelogon certificate policy are separate authentication layers;
- DTLS is an optional transport path, not a different user-auth protocol;
- FortiClient EMS/posture/security tags are product policy layers, not tunnel cryptography;
- OpenConnect Fortinet mode delegates TLS/DTLS crypto to maintained OpenConnect crypto backends and remains a separate implementation;
- legacy algorithms or certificate-bypass settings must not become default merely for compatibility.

Fortinet has an extensive SSL-VPN security history. Current deployment/research must check FortiGuard PSIRT and use a fixed/supported branch. Examples current at review time include FortiGuard PSIRT's SSL-VPN advisories and upgrade-path guidance; exact vulnerability applicability depends on branch/mode and must not be generalized.

Primary PSIRT index: https://www.fortiguard.com/psirt

No proprietary Fortinet cryptographic source code or test result is invented.
