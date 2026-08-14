# Transport / Security Building Blocks — PVNetwork v1 Decisions

Decision date: 2026-08-14

State: research only; not implemented.

Entries 077–093 mix **security layers, transports, application transports and core-specific transports**. They must not be advertised as 17 separate VPN protocols.

## 077 — TLS

`FOUNDATIONAL SECURITY LAYER / MATURE PLATFORM OR ENGINE TLS STACK`

Never implement TLS cryptography/protocol from scratch. Record exact TLS backend/version/certificate policy per engine/platform.

## 078 — uTLS / TLS fingerprinting

Primary reference: `refraction-networking/utls`.

`ADVANCED TLS-CLIENT-HELLO COMPATIBILITY CAPABILITY / LIBRARY OR ENGINE FEATURE`

This is not a VPN protocol. Treat fingerprint/profile selection as an advanced capability and keep security/certificate verification separate.

## 079 — Cloak

Primary reference: `cbeuw/Cloak`.

`OPTIONAL OBFUSCATION/TRANSPORT WRAPPER / LICENSE+COMPOSITION REVIEW REQUIRED`

Cloak can wrap another service. Model outer/inner components separately and do not copy incompatible-license code blindly.

## 080 — TLS Fragmentation

`ENGINE/TRANSPORT FEATURE / NO DEDICATED ENGINE`

This is a connection-behavior technique/capability, not an independent VPN protocol. Keep as versioned advanced feature only where the selected engine implements it safely.

## 081 — TCP

`OS TRANSPORT / NO DEDICATED ENGINE`

TCP is a base transport. Engine-specific TCP behavior belongs to that engine adapter.

## 082 — UDP

`OS TRANSPORT / NO DEDICATED ENGINE`

UDP is a base transport. Product capability must still test NAT, IPv4/IPv6, MTU and platform policy.

## 083 — QUIC

`FOUNDATIONAL MODERN TRANSPORT / MATURE QUIC LIBRARY OR ENGINE`

Do not implement QUIC from scratch. Hysteria2/TUIC/HTTP3 and other protocols may use distinct QUIC libraries/configuration semantics.

## 084 — WebSocket

Already covered in Xray v1 evidence.

Classification: application transport; no standalone VPN claim.

## 085 — HTTP/1.1

`FOUNDATIONAL APPLICATION TRANSPORT / MATURE HTTP STACK`

No dedicated VPN engine.

## 086 — HTTP/2

Already covered in Xray v1 evidence.

Classification: application transport/protocol layer; no standalone VPN claim.

## 087 — HTTP/3

`HTTP OVER QUIC TRANSPORT / MATURE HTTP3+QUIC STACK`

No custom implementation. Keep HTTP semantics and QUIC/TLS backend version explicit.

## 088 — gRPC

Already covered in Xray v1 evidence.

Classification: RPC/application transport used by selected proxy protocols, not a VPN protocol by itself.

## 089 — mKCP

Already covered in Xray v1 evidence.

Classification: core-specific/reliable-UDP transport capability.

## 090 — KCP

Primary reusable library reference: `xtaci/kcp-go`.

`TRANSPORT LIBRARY CAPABILITY / USE MATURE IMPLEMENTATION`

Do not confuse generic KCP with Xray's specific mKCP framing/options.

## 091 — XHTTP

Already covered in Xray v1 evidence.

Classification: Xray transport; engine/version capability.

## 092 — RAW

Already covered in Xray v1 evidence.

Classification: Xray raw/TCP-style transport capability; engine/version specific.

## 093 — DTLS

`DATAGRAM TLS SECURITY/TRANSPORT LAYER / USE MATURE ENGINE OR TLS LIBRARY`

For enterprise VPN use cases, OpenConnect uses DTLS as a data transport in supported protocols. Do not add a standalone DTLS VPN engine merely because DTLS exists.

## Common architecture rule

Canonical profiles should represent:

`application protocol`

`+ transport`

`+ security layer`

`+ engine/version capabilities`

rather than turning every possible combination into a new first-class protocol.

## Security rule

Certificate validation, SNI/server-name, ALPN, fingerprinting and insecure overrides are separate typed fields/capabilities. Obfuscation/fingerprinting does not replace real certificate/authentication security.

## Engine minimization

Use:

- OS TCP/UDP;
- mature TLS/HTTP/QUIC stacks;
- approved core implementations for WebSocket/gRPC/mKCP/XHTTP/RAW;
- mature KCP library only if a direct need remains;
- OpenConnect/core-specific DTLS where already required.

Do not create 17 extra engines.

## Residual v1 gaps

- exact uTLS/Cloak/KCP source/license pins;
- exact QUIC/HTTP3 library choice by engine/platform;
- full capability/version matrix across Xray/Hysteria/TUIC/Naive/OpenConnect;
- current security/advisory/performance evidence;
- complete user-visible advanced settings map.

Mandatory v2 later adds exact standards/specs, handshake/wire flow, library/server/client implementations, installation matrices and exhaustive menus where applicable.