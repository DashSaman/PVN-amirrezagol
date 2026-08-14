# PVNetwork Research Campaign Status — 2026-08-14 — Remaining Proxy / Transport / Security v1 Closure

Repository phase: research / requirements / architecture.

This work unit closes original-v1 research decisions for:

- 041 Shadowsocks 2022
- 077 TLS
- 078 uTLS / TLS fingerprinting
- 079 Cloak
- 080 TLS Fragmentation
- 081 TCP
- 082 UDP
- 083 QUIC
- 085 HTTP/1.1
- 087 HTTP/3
- 090 KCP
- 093 DTLS

Related entries 084 WebSocket, 086 HTTP/2, 088 gRPC, 089 mKCP, 091 XHTTP and 092 RAW were already synchronized under the Xray v1 closure.

All listed entries are **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

## Shared evidence

`research/upstreams/transport-security-family/SUPPORT_REUSE_DECISIONS.md`

## Main rule

These entries are not separate VPN engines. They are security layers, OS transports, application transports or core-specific transport capabilities.

Engine minimization:

- TCP/UDP -> OS stack;
- TLS/HTTP/QUIC/HTTP3 -> mature libraries/selected engine;
- uTLS/Cloak/fragmentation -> advanced engine/library capability;
- WebSocket/HTTP2/gRPC/mKCP/XHTTP/RAW -> selected core capability;
- KCP -> mature library only if directly required;
- DTLS -> selected mature enterprise/TLS engine such as OpenConnect where applicable.

## Shadowsocks 2022

Entry 041 now has a separate v1 decision using `shadowsocks/shadowsocks-rust` as a primary open-source reference/candidate and requiring exact generation/method semantics rather than silent downgrade to older Shadowsocks.

## Residual gaps

Exact source pins/license/SBOM for several transport libraries, selected QUIC/HTTP3 stacks, current advisories/performance and full advanced settings/menu evidence remain explicit. Mandatory v2 adds standards, wire/handshake/crypto, server/client implementations and full install/menu matrices.

## Next exact action

Reconcile all 93 numbered entries against the actual repository tree and shared family handoffs. If every entry has a v1 research decision or an explicit shared-family handoff, mark the original 93-entry campaign **V1-COVERAGE-HANDOFF-READY / NOT IMPLEMENTED**, checkpoint Agent state, then begin the mandatory `COMPLETE-REFERENCE-v2` phase in priority order starting with the highest-value protocol family rather than waiting for owner confirmation.
