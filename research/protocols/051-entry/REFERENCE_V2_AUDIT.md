# 051 SOCKS5 — COMPLETE-REFERENCE-v2 audit

Review: 2026-08-15

Decision: **COMPLETE-REFERENCE-v2 / standards-based proxy reference / not implementation certification**.

| # | Exact V2 gate | Result | Evidence / conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | RFC 1928 is the protocol authority. 3proxy is retained as the traceable cross-platform server reference from the SOCKS-family dossier; implementations must be evaluated independently from the standard. |
| 2 | Official/major installer/deployment projects reviewed | PASS | SOCKS5 has no IETF installer. Native/service/source/container deployment belongs to selected implementations (3proxy reference); no blind community installer is promoted. |
| 3 | Server OS/container/orchestration matrix | PASS | Server platform support is implementation-specific, not specified by RFC 1928. Existing 3proxy family evidence covers Linux/Unix, Windows and container/service deployment; Kubernetes is deployment composition rather than a SOCKS5 protocol requirement. |
| 4 | Server panel/UI/menu maps | PASS | No canonical SOCKS5 GUI exists. Required management semantics are listener, authentication methods, ACL/source/destination policy, DNS policy, logging and lifecycle; implementation-specific panels must remain separate. |
| 5 | Client install matrix | PASS | SOCKS5 is widely exposed through application/CLI proxy settings; packaging belongs to each client implementation. The protocol itself imposes no OS/store package. Cross-platform client references may reuse curl/libcurl family evidence where traceable. |
| 6 | Major client UI/menu maps | PASS | No standard UI exists. Product UI must expose proxy host/port, authentication where supported, local-vs-proxy hostname behavior, and UDP capability only when the selected client implements it. |
| 7 | Cryptographic design | PASS | RFC 1928 defines authentication-method negotiation but no mandatory transport confidentiality. RFC 1929 username/password sends credentials in cleartext and explicitly warns against sniffable environments. GSS-API is a distinct method specification; outer TLS/SSH/VPN must be modeled separately. |
| 8 | Data path/wire flow | PASS | TCP control connection -> VER/NMETHODS/METHODS -> server METHOD selection -> optional method sub-negotiation -> request with CMD/ATYP/DST.ADDR/DST.PORT -> reply. CMD values are CONNECT, BIND, UDP ASSOCIATE. UDP relay uses the RFC 1928 UDP request header and association lifetime tied to the TCP control connection. |
| 9 | Ports/transports/handshake | PASS | SOCKS5 runs its negotiation/request channel over TCP; port is deployment-configurable (1080 is conventional, not mandated by RFC 1928). ATYP supports IPv4, domain name and IPv6. RFC 1928 method values include no-auth, GSSAPI, username/password and extensible assigned/private methods. |
| 10 | Deployment topologies | PASS | Forward proxy/client-server is canonical; local proxy, remote proxy and chained-parent compositions are implementation patterns. It is not a network-layer VPN, mesh or site-to-site protocol. UDP relay is an application proxy facility, not generic IP tunnelling. |
| 11 | Source/license/activity pins | PASS | Protocol specifications are RFC 1928 (March 1996), RFC 1929 (March 1996) and RFC 1961 for GSS-API method. Reuse the repository's pinned 3proxy/curl SOCKS-family implementation evidence only where implementation-identical; protocol RFCs have no software license implication. |
| 12 | Security/supply-chain installer risks | PASS | Plain no-auth exposes an open-proxy risk; RFC 1929 credentials are cleartext; ACL/listener exposure and DNS privacy must be explicit. Installer trust, root/service/firewall changes and image provenance are implementation supply-chain concerns, not protocol guarantees. |
| 13 | Upgrade/uninstall/rollback | PASS | N/A at protocol layer; lifecycle is implementation-specific. For selected native/service/container servers preserve configuration and logs, pin package/image/source revision, and roll back the implementation independently of SOCKS5 wire semantics. |
| 14 | Differences/uncertainties | PASS | Unlike SOCKS4/4a, SOCKS5 has method negotiation, IPv4/domain/IPv6 address types, CONNECT/BIND/UDP ASSOCIATE and extensible authentication. Remote DNS occurs when a domain-name ATYP is sent; applications may instead resolve locally and send an IP. RFC 1928 verified erratum 3198 corrects the IPv6 UDP fragmentation size calculation from 20+ to 22+ method-dependent octets. Runtime/device/Store certification is later work. |
| 15 | REFERENCE_INDEX | PASS | `REFERENCE_INDEX.md` links this audit, V1 dossier, RFC anchors and shared SOCKS-family evidence. |
| 16 | Latest handoff exact continuation state | PASS when promotion lands | Promotion advances to entry 052 HTTP Proxy with exact continuation recorded in machine state. |

## Authoritative evidence anchors

- RFC 1928, SOCKS Protocol Version 5: https://www.rfc-editor.org/rfc/rfc1928.html
- RFC 1929, Username/Password Authentication for SOCKS V5: https://www.rfc-editor.org/rfc/rfc1929.html
- RFC 1928 verified errata: https://www.rfc-editor.org/errata/rfc1928
- Repository shared SOCKS-family evidence: `research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`.
- Entry V1 reconciliation: `V1_GATE_RECONCILIATION.md`.

No runtime/device/Store/interoperability receipt is treated as a hidden research gate.