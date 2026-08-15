# 044 TUIC v5 — cryptographic/security design

Canonical source: `tuic-protocol/tuic SPEC.md@fe246d88e57e306e767265230fa178640950060a`.

TUIC relies on a multiplexable **TLS-encrypted stream**, mainly QUIC. TUIC itself does not define a new bulk cipher suite; security inherits the selected TLS/QUIC implementation and certificate/trust policy.

Authentication command contains:
- UUID: 16 bytes;
- TOKEN: 32 bytes.

The TOKEN is derived from the **raw password** with the TLS Keying Material Exporter on the current TLS session: exporter label = client UUID, context = raw password. Therefore the token is session-derived material, not the canonical password to persist.

Security boundaries:
- keep TLS verification enabled by default;
- UUID is identity, raw password is protected reusable secret;
- TLS private keys/certs and management credentials are separate secrets;
- 0-RTT has replay semantics and is capability-gated;
- pin exact QUIC/TLS dependencies and never implement TLS/QUIC/exporter cryptography from scratch.
