# Juniper Network Connect — Cryptography / Security Boundary

Reviewed: 2026-08-14 UTC

Publicly reviewable OpenConnect NC behavior provides the security boundary:

- authentication/control and TCP data path use HTTPS/TLS;
- server certificate validation and user/certificate/token authentication are separate;
- UDP accelerated transport uses ESP;
- Host Checker/TNCC is endpoint-posture logic, not tunnel cryptography;
- current OpenConnect disables obsolete 3DES/RC4 compatibility ciphers by default unless explicitly re-enabled for a legacy server.

Legacy gateway compatibility must not justify silently weakening TLS/server identity verification. If an old gateway requires obsolete cryptography, PVNetwork must surface that as an explicit insecure-compatibility exception rather than a normal default.

Vendor Juniper/Pulse client cryptographic source is proprietary/unavailable and no implementation claim is fabricated.
