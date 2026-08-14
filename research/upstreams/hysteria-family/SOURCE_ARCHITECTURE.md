# Hysteria / Hysteria2 — Source Architecture

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

## Upstream

Repository: `apernet/hysteria`

Root license reviewed: **MIT**.

Current upstream source line is the Hysteria 2 generation. Hysteria 2 is not merely a UI rename of Hysteria 1; compatibility/version semantics must remain explicit.

Before implementation, pin the exact selected current tag/commit and record it in this file instead of using a moving branch.

## Product roles in upstream

The repository contains both client and server functionality and shared protocol/config/runtime packages.

Conceptually separate:

- client connection/runtime;
- server/listener/runtime;
- configuration parsing/validation;
- authentication;
- TLS/certificate handling;
- QUIC transport/session behavior;
- TCP/UDP proxy/data forwarding;
- masquerade/HTTP-style server behavior where configured;
- command-line/product entry points;
- tests/build/release assets.

PVNetwork should not embed server administration into the normal client UI simply because both live in one repository.

## Core transport architecture

Hysteria2 is QUIC-based. Product architecture should model:

- Hysteria2 application/session semantics;
- QUIC as underlying transport technology;
- TLS/certificate validation as security layer;
- authentication as separate profile capability;
- bandwidth/congestion-control settings;
- UDP and TCP forwarding behavior;
- product-owned TUN/routing/DNS lifecycle.

Do not flatten these into one opaque URL string in canonical storage.

## Client/server configuration

Upstream supports structured client/server configuration rather than one universal client-only profile.

PVNetwork should maintain a typed client profile containing only the client-relevant subset, while later server-management/reference work keeps server/listener/auth/masquerade/ACME configuration in a separate model.

## Authentication separation

Authentication is a server/client capability dimension. PVNetwork canonical profile should distinguish:

- authentication type;
- reusable credential/secret reference;
- server endpoint;
- TLS/server-name/certificate policy;
- transport/session options.

Do not persist authentication secrets as ordinary unprotected profile text.

## TLS / certificate boundary

QUIC does not eliminate TLS certificate validation requirements.

PVNetwork must separately model and test:

- server name/SNI behavior;
- certificate trust;
- optional pinning where supported;
- unsafe verification overrides;
- imported profile security semantics;
- platform trust-store behavior.

Simple Mode should not default to certificate-verification bypasses.

## Bandwidth / congestion behavior

Hysteria's performance-oriented design exposes bandwidth/congestion-related concepts that differ from ordinary TCP-based proxy profiles.

PVNetwork should keep these as advanced, typed fields and must not invent values automatically without evidence. Product diagnostics should distinguish configured limits/estimates from measured throughput.

## TUN/product boundary

Even if Hysteria client code exposes TCP/UDP proxy functionality, a full-device PVNetwork VPN experience still needs product/platform ownership of:

- Android VpnService;
- Apple NetworkExtension;
- Windows/Linux/macOS TUN/route/DNS lifecycle;
- per-app routing where supported;
- kill switch/leak behavior;
- reconnect/network handover;
- background/service process lifecycle.

Do not treat a working local proxy as proof that full-tunnel system routing works.

## Import/canonical model rule

Use:

`external URI/config`

`-> importer`

`-> canonical Hysteria profile`

`-> selected engine adapter config`

`-> transient runtime state`

Preserve original/unknown fields and report lossy conversions.

## Hysteria v1 vs Hysteria2

Entry 042 Hysteria and entry 043 Hysteria2 must remain separate compatibility states.

Current Hysteria2-focused source/release evidence must not be used to claim Hysteria v1 compatibility automatically.

For legacy Hysteria v1 support, later research must pin an actual v1-compatible source/tag/client and test it independently.

## Reuse direction

MIT root licensing makes the upstream code attractive for commercial engineering evaluation.

Preferred architecture direction:

- reuse upstream Hysteria2 client/core code behind a PVNetwork Hysteria Adapter where a clean API/process boundary is feasible;
- keep product UI/canonical storage/platform VPN lifecycle independent;
- use server code as later interoperability/server-reference evidence;
- do not fork the command-line UX as the PVNetwork product UI.

## Remaining v1 gaps

- exact selected current Hysteria2 release/commit pin;
- exact source package/API boundary;
- dependency/SBOM/advisory map;
- configuration field matrix;
- client UI references beyond CLI/core projects;
- issue/release regression sampling;
- legacy Hysteria1 source/support decision;
- platform/mobile wrapper feasibility.

Server installation/panels, full cryptography/wire flow and exhaustive menus belong to mandatory later v2.
