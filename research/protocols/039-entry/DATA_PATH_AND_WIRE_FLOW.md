# 039 Trojan — data path / wire flow

Reviewed against original Trojan spec and Xray `v26.7.28`.

Canonical flow:

1. client establishes real TLS to server and validates the configured identity policy;
2. inside TLS, client sends `hex(SHA224(password))` (56 bytes) + CRLF;
3. client sends SOCKS5-like Trojan request: command + address type/address + destination port + CRLF;
4. TCP CONNECT payload then streams directly;
5. UDP ASSOCIATE uses repeated framed datagrams: destination address/port + two-byte length + CRLF + payload;
6. server validates password token/request then dispatches to destination;
7. invalid user/protocol may be sent to configured fallback behavior in compatible servers.

Xray source defines commands TCP=1 and UDP=3 and IPv4/domain/IPv6 destination address types. Its inbound network listener is a stream (TCP/UNIX); UDP semantics are framed inside the stream. Local TUN/SOCKS/system-proxy capture and Xray routing are outside Trojan wire framing.
