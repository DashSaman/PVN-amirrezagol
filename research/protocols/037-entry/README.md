# 037 — VLESS

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: application proxy protocol in the Xray/V2Ray ecosystem; not a transport or security layer.

## Current primary Xray evidence

Shared family:

`research/upstreams/xray-family/`

Read at minimum:

- `SOURCE_ARCHITECTURE.md`
- `CONFIG_CAPABILITY_MODEL.md`
- `DEPENDENCIES_TESTS_RELEASES.md`
- `SUPPORT_REUSE_DECISIONS.md`
- `ISSUE_RELEASE_LESSONS.md`
- `SECURITY_AND_DEPENDENCY_ADVISORIES.md`
- `LIBXRAY_WRAPPER.md`
- `LIBXRAY_API_LIFECYCLE.md`
- `XRAY_API_CONTROL.md`

Current Xray-core research pin:

`XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`

Root license: MPL-2.0.

## Research decision

`HIGH-PRIORITY XRAY-NATIVE TARGET / EXACT COMBINATION CERTIFICATION REQUIRED`

VLESS support must be versioned and certified as combinations of:

- identity/auth fields;
- flow;
- TLS/REALITY or other effective security setting;
- transport such as RAW/XHTTP/WebSocket/gRPC where supported by the selected core version;
- IPv4/IPv6 and UDP behavior where applicable;
- routing/DNS behavior;
- exact client/server core versions.

Do not expose an unrestricted set of transport/security/flow combinations. Use core-version-aware capability validation.

## Client references

Primary UX/source references currently include v2rayN, v2rayNG, Hiddify, Karing, NekoBox, Throne and selected adjacent multi-core clients. Their application licenses are separate from Xray-core and are mostly reference-only for a closed PVNetwork product.

## Later mandatory v2 expansion

After original v1 gates, this folder must receive the applicable files defined by `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`, including server projects/installers/panels, server/client install matrices, exhaustive menus, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, deployment topologies and reference index.
