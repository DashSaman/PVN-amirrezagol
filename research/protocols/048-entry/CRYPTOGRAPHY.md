# 048 Snell — cryptographic/security design

Reviewed: 2026-08-15

Snell is proprietary. Surge publicly describes it as an encrypted proxy protocol and exposes a reusable PSK as required client/server authentication/configuration material. The authoritative vendor documentation does **not** publish the complete official cipher suite, key schedule, record format or source implementation for v4/v5/v6.

Therefore the exact proprietary cryptographic internals are **evidence-backed N/A/unpublished for authoritative-source reproduction**. No reverse-engineered third-party algorithm is promoted to official protocol truth and no cryptographic primitive is fabricated.

Authoritative v5 security behavior that is public:
- ordinary Snell proxy traffic is encrypted/authenticated by the proprietary protocol;
- v5 QUIC Proxy Mode specially handles detected QUIC traffic over UDP-over-UDP;
- in that mode the QUIC handshake packets are strongly encrypted/authenticated by Snell to protect SNI/target hostnames;
- subsequent QUIC packets are forwarded raw because those packets are already protected by QUIC itself.

v6 derives a deployment-specific protocol profile from PSK, but vendor docs do not publish the derivation internals. That profile is beta/version-specific.

Security boundary: PSK is a reusable secret requiring secure storage/redaction. ShadowTLS, when composed, is an independent layer and must not be counted as Snell's own cryptography.
