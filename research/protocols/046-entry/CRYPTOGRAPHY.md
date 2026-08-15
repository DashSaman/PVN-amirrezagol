# 046 ShadowTLS v3 — cryptographic/security design

Canonical doc: `protocol-v3-en.md` blob `1766a21576ddc81958276fb1f2f82bbc0b9665c2`.

ShadowTLS v3 deliberately reuses a real TLS handshake server but is itself a camouflage TCP-flow proxy. It does **not** replace the confidentiality/authentication of the inner proxy.

V3 client constructs a TLS ClientHello with a 32-byte SessionID; 28 bytes random plus 4-byte HMAC signature derived using the password over the ClientHello. Server validates this while still forwarding handshake traffic to the real TLS endpoint.

Server observes TLS ServerRandom. During handshake-side ApplicationData it XORs content with SHA256(PreSharedKey + ServerRandom) and adds stateful 4-byte HMAC prefixes. After switching to data-server path, both directions are wrapped as TLS ApplicationData-shaped frames with stateful HMAC chains initialized with ServerRandom+direction. HMAC state includes prior data/HMAC to detect tamper, disorder, replay/cut-splice classes; validation failure generates TLS Alert/bad-record behavior.

Strict v3 supports TLS1.3 handshake servers only. Non-strict can allow TLS1.2 as an explicit weaker compatibility mode. Password remains reusable secret and must be protected/redacted.
