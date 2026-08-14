# Xray-core — Configuration / Capability Model

Research date: 2026-08-14

State: `IN-RESEARCH`; configuration architecture evidence only.

Pinned source:

`XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`

## Why this file exists

Xray configurations combine multiple independent concepts. PVNetwork must not model every Xray-compatible connection as one flat URI/string or one untyped JSON document.

The pinned source shows clear separation between:

- application/proxy protocol;
- inbound/outbound role;
- outer transport;
- security layer;
- socket/platform settings;
- routing/DNS/policy/observability;
- protocol-specific authentication/identity fields.

## Protocol modules

Pinned source/config areas provide dedicated handling for protocols including:

- VLESS;
- VMess;
- Trojan;
- Shadowsocks;
- SOCKS;
- HTTP proxy;
- WireGuard;
- other core routing/outbound types.

Each protocol has its own validation/runtime structure. A PVNetwork profile should therefore use typed per-protocol extension data rather than an undifferentiated options dictionary.

## Transport axis

Pinned `infra/conf/transport_internet.go` currently maps transport names/configurations independently from application protocols.

Current source recognizes active transport families such as:

- RAW/TCP;
- XHTTP / split-HTTP implementation;
- mKCP;
- gRPC;
- WebSocket;
- HTTPUpgrade;
- Hysteria transport.

The same source marks some older transport names as deprecated or removed in favor of newer XHTTP-based equivalents. This is important migration evidence: PVNetwork cannot freeze a transport dropdown forever based on an old client UI.

### PVNetwork rule

Represent transport as a versioned capability object owned by the selected core adapter. When a core removes/deprecates a transport:

- preserve imported original data;
- surface migration/unsupported state;
- never silently rewrite to a semantically different transport;
- allow adapter-version-aware validation.

## Security axis

Pinned stream configuration treats security separately from transport.

Current source recognizes:

- none;
- TLS;
- REALITY.

The source explicitly treats legacy `xtls` security mode as removed and points toward current Vision flow usage with TLS/REALITY rather than legacy XTLS-as-security configuration.

### PVNetwork consequence

Entries such as REALITY, XTLS and XTLS Vision must not all be presented as equivalent standalone VPN protocols. Their classification and compatibility must reflect whether they are:

- security layer;
- flow/mode;
- application-protocol feature;
- transport combination.

## Transport/security compatibility

Pinned source enforces compatibility constraints between security layer and selected transport. This is critical product behavior: a UI that lets every protocol/security/transport checkbox combine freely will generate invalid or misleading configurations.

PVNetwork requires adapter-provided validation such as:

`supportsCombination(protocol, transport, security, flow, coreVersion)`

The exact API is not yet approved, but capability validation must be data/model driven.

## Config parsing vs runtime model

The `infra/conf/` layer builds runtime structures from human-facing configuration. Protocol-specific files such as `vless.go`, `vmess.go` and `trojan.go` coexist with transport and application configuration builders.

PVNetwork should use:

1. import parser;
2. canonical normalized `PVProfile`;
3. validation/capability layer;
4. Xray runtime-config generator;
5. transient runtime/session state.

Do not persist generated Xray runtime JSON as the authoritative product database.

## Import/link problem

Popular clients import multiple forms:

- protocol share links;
- JSON-like full configs;
- subscription content;
- QR codes;
- clipboard text;
- client-specific backups/config databases.

These formats are not semantically identical. Some carry only one endpoint, while full engine configs can contain routing, DNS, inbound and policy behavior.

PVNetwork importer requirements:

- detect format before parsing;
- preserve the original imported source for audit/re-export where safe;
- distinguish endpoint/profile import from full-engine-config import;
- report fields not representable in canonical schema;
- mark lossy conversion explicitly;
- never silently drop routing/DNS/policy content and call the import complete.

## Core version awareness

The current source already demonstrates removed/deprecated transport behavior and evolving protocol modules. Therefore canonical profiles need:

- schema version;
- source format/version metadata;
- intended engine/core family;
- adapter validation version;
- unsupported/legacy field preservation.

This reduces data loss when Xray-core changes.

## Routing/DNS/application separation

Pinned tree has dedicated app modules for routing, DNS, dispatcher, policy, stats and observability.

PVNetwork should not bake global routing/DNS behavior into each individual server/profile record. Use separate product routing/DNS policy models that can compile into Xray/Mihomo/sing-box/native equivalents where semantics exist.

## API/control boundary

Xray includes commander/API-style app modules. Before using them for PVNetwork control, a separate dossier must map:

- authentication/exposure model;
- lifecycle ownership;
- supported runtime mutations;
- stats/logging contracts;
- whether subprocess IPC or wrapper APIs are safer per platform.

Do not expose engine management APIs directly to untrusted local/network callers.

## Numbered-entry mapping to preserve

Xray-related numbered entries include at least:

- 037 VLESS;
- 038 VMess;
- 039 Trojan;
- 040 Shadowsocks;
- 074 REALITY;
- 075 XTLS;
- 076 XTLS Vision;
- 084 WebSocket;
- 086 HTTP/2-related transport classification;
- 088 gRPC;
- 089 mKCP;
- 091 XHTTP;
- 092 RAW.

Some of these are protocols, some security/flow concepts and some transports. The numbered matrix classification must remain explicit so PVNetwork does not market them all as separate VPN protocols.

## Remaining gaps

- full per-protocol field/capability matrix;
- current URI/share-link parser semantics from major clients;
- Xray JSON config schema/import edge cases;
- API/commander runtime-control map;
- migration table for removed/deprecated transport/security options;
- route/DNS semantic mapping against other candidate cores;
- full lossless/lossy import rules.
