# 040 Shadowsocks classic AEAD — data path/wire flow

Reviewed: 2026-08-15

Client-side local modes (SOCKS/HTTP/TUN/redir/DNS) are separate from Shadowsocks remote wire protocol.

## TCP

`local request -> destination address header -> per-connection random salt -> HKDF-derived subkey -> AEAD-encrypted length/tag + encrypted payload/tag chunks -> server decrypts/validates -> outbound destination`.

The first decrypted TCP payload carries the destination address/port before application data. Canonical AEAD chunks use a two-byte length capped at 0x3FFF, with independent authentication of length and data and monotonically incremented nonce operations.

## UDP

Each client UDP datagram is independently encoded/encrypted with destination address/port plus payload under a random salt-derived subkey and AEAD tag. Server validates/decrypts and dispatches the datagram; response follows corresponding Shadowsocks UDP framing.

TCP and UDP are separate capabilities and must be certified separately. Plugin/local-TUN framing is not part of classic Shadowsocks AEAD.
