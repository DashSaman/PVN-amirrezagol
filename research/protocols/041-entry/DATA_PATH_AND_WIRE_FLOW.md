# 041 Shadowsocks 2022 — data path and wire flow

## TCP

`local proxy request -> SS2022 client -> random salt -> optional EIH -> encrypted fixed request header -> encrypted variable destination/padding/initial-payload header -> encrypted length/payload chunks -> server replay/time/key validation -> destination`

Request fixed header contains type 0, u64be Unix timestamp and next-header length. Variable header contains SOCKS5 address/port, padding and optional initial payload. Response has a new random salt, type 1, timestamp, original request salt and payload length, binding response to request.

Payload chunk limit is 0xFFFF, unlike classic Shadowsocks AEAD's 0x3FFF limit. Salt + initial header must be buffered/read in one operation as specified to reduce probe-length leakage.

## UDP

SS2022 UDP is session-based, not classic independent-salt-per-datagram framing. Client generates 8-byte session ID plus monotonically advancing u64 packet ID. AES methods encrypt the 16-byte separate header with PSK and AEAD-protect the body using a session key derived from session ID. Main header carries type, timestamp, padding, destination and payload. Server response has its own server session ID/packet IDs and binds to client session ID.

Optional EIH sits between separate header and ciphertext for requests and is absent from responses.
