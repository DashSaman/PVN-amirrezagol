# 040 Shadowsocks classic AEAD — cryptographic design

Reviewed against canonical `shadowsocks/shadowsocks-org` AEAD specification commit `34598d65054dad975d330ff9d7317b0d41cf1efd` and `shadowsocks-rust@9214fdaf...`.

Standard classic AEAD methods:

- `chacha20-ietf-poly1305`: 32-byte key/salt, 12-byte nonce, 16-byte tag;
- `aes-256-gcm`: 32-byte key/salt, 12-byte nonce, 16-byte tag;
- `aes-128-gcm`: 16-byte key/salt, 12-byte nonce, 16-byte tag.

Compliant implementations must support ChaCha20-Poly1305; AES-GCM methods are recommended where hardware acceleration exists.

Master key may be supplied directly or derived from password using the historical OpenSSL EVP_BytesToKey-compatible derivation. Per-session subkey derives using HKDF-SHA1 with unique random salt and info `ss-subkey`.

TCP: random salt -> encrypted/authenticated two-byte chunk length -> encrypted/authenticated payload, counting nonce incremented for each AEAD operation, max payload 0x3FFF.

UDP: each datagram independently carries salt + AEAD ciphertext/tag and starts with zero nonce for that independently derived subkey; unique salts are required.

`shadowsocks-rust` separately gates deprecated stream ciphers and warns they are unsafe. They are legacy compatibility only, disabled by default. Entry 041 AEAD-2022 is not equivalent and is excluded here.
