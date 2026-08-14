# 041 Shadowsocks 2022 — cryptographic design

Canonical source: `Shadowsocks-NET/shadowsocks-specs@20b4952e8a54e696ebcabc5f91b5dad7f322f2da`.

SS2022 uses pre-shared symmetric keys and AEAD and explicitly does **not** provide forward secrecy.

Required methods:
- `2022-blake3-aes-128-gcm`: 16-byte PSK / 16-byte salt;
- `2022-blake3-aes-256-gcm`: 32-byte PSK / 32-byte salt.

The PSK must be cryptographically random, fixed length and supplied directly, conventionally base64 encoded. Implementations MUST NOT use old `EVP_BytesToKey` or derive it from arbitrary passwords.

Session subkey derivation uses BLAKE3 derive-key context `shadowsocks 2022 session subkey` over PSK + salt. TCP uses AEAD with a 12-byte little-endian counter nonce. Optional ChaCha methods are separate capability gates and are not required baseline methods.

Full replay protection is mandatory. TCP timestamps beyond 30 seconds are replay; servers retain incoming salts for 60 seconds and must not use false-positive Bloom filters for that set. UDP uses session IDs/packet IDs plus sliding-window replay rejection and time validation.

EIH is a separate optional identity layer. For AES methods, TCP identity subkeys use BLAKE3 context `shadowsocks 2022 identity subkey`; identity headers carry the next PSK hash encrypted under the current identity key. Current shadowsocks-rust implements EIH and multi-user key lookup for both TCP and UDP.
