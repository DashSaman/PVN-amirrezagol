# 084 — WebSocket

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: **application transport**, not a standalone VPN protocol.

## Current Xray evidence

Shared family: `research/upstreams/xray-family/`.

Current Xray-core research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5` (MPL-2.0).

Pinned Xray config still recognizes WebSocket transport, while current source emits migration/deprecation guidance toward newer XHTTP H2/H3-style use in some contexts.

Research decision:

`COMPATIBILITY TRANSPORT / DEPRECATION-AWARE`.

PVNetwork must:

- preserve/import existing WebSocket profiles while selected core support exists;
- keep transport independent from VLESS/VMess/Trojan/etc.;
- never silently convert WebSocket to XHTTP;
- record core version and source semantics;
- validate host/path/header/security combinations through the adapter;
- test real interoperability before a support badge.

## Multi-core rule

WebSocket is also implemented by other candidate engines/runtimes. PVNetwork should normally inherit it from the selected core instead of maintaining a separate WebSocket tunnel stack. Canonical transport data should remain as core-neutral as practical.

## Later mandatory v2 expansion

`COMPLETE-REFERENCE-v2` must add exact engine/server implementations, server/client install matrices, menu/config field inventories, wire/framing/handshake relationships, proxy/CDN deployment topologies and reference evidence defined by `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`.
