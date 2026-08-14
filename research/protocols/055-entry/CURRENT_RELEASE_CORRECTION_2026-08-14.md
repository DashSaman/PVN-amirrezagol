# 055 Tor SOCKS — Current Release Correction — 2026-08-14

This correction supersedes the stale C Tor release references inside `V1_GATE_RECONCILIATION.md` wherever they conflict.

## Current C Tor release

Tor Project official download page reviewed 2026-08-14 lists:

- **Tor 0.4.9.11** as current source release;
- source archive: `tor-0.4.9.11.tar.gz`;
- release announced by Tor Project as **Security Release 0.4.9.11** on 2026-06-30;
- source SHA-256: `2e6c1720118c812acf0079fd47cf91b6bfaba5d766c321c4d3d2a28d6a11a8ed`;
- Tor Project publishes a checksum file and detached signature for the source release and documents signature verification.

Therefore all statements in the reconciliation that call `0.4.9.8` the current C Tor security release are stale. The authoritative current pin for this review is **0.4.9.11**.

## License/build-mode correction

C Tor's normal code licensing is 3-clause BSD-style. Current source lineage also contains an optional `--enable-gpl` build mode; when GPL-covered code is enabled, the resulting Tor/libtor distribution obligations change accordingly. Production provenance must therefore record exact build/configure options and component licenses, not only the default core license.

This correction does not alter the 20-gate conclusion; it strengthens gates 2, 3, 4 and 15 with current release evidence.

## Current Tor SOCKS authority reaffirmed

Canonical Tor SOCKS specification currently states:

- SOCKS4/4a BIND unsupported;
- SOCKS5 UDP ASSOCIATE unsupported;
- SOCKS5 BIND unsupported;
- SOCKS5 GSSAPI unsupported;
- Tor supports remote `RESOLVE` and SOCKS5 `RESOLVE_PTR` extensions;
- remote hostname resolution is privacy-critical;
- SOCKS username/password fields may carry stream-isolation/RPC metadata rather than ordinary proxy account credentials.

No ordinary SOCKS5 capability from entry 051 may be assumed for Tor unless the Tor specification explicitly supports it.

## V1 conclusion

With this correction, entry 055 remains eligible for `COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT CERTIFIED`.
