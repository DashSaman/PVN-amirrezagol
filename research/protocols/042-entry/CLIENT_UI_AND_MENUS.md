# 042 Hysteria v1 — client UI/menu maps

Canonical v1 client is CLI/JSON/JSON5 configuration driven; no first-party GUI menu exists at the frozen pin.

Client config surface includes server/protocol, mandatory upload/download estimates, retry/handshake/idle/hop controls, SOCKS5, HTTP, TUN/TAP, TCP/UDP relays, Linux TProxy/redirect, ACL/MMDB, `obfs`, `auth`/`auth_str`, ALPN, server name, insecure flag, custom CA, receive windows, PMTUD, Fast Open, lazy start and resolver controls.

Default ALPN=`hysteria`, idle timeout=20s, hop interval=10s. Validator requires at least one local operating mode and validates bandwidth and timeout ranges.

A PVNetwork importer must retain original v1 fields/source, mark legacy generation, redact auth/CA secrets, and never silently map to Hysteria2.
