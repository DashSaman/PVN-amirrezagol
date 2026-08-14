# 091 — XHTTP

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: **current Xray-family transport capability**, not a standalone VPN product.

Shared evidence: `research/upstreams/xray-family/`.

Current Xray-core research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`.

Research decision:

`HIGH-PRIORITY CURRENT XRAY TRANSPORT TARGET / CORE-VERSION-AWARE`.

Pinned current source has dedicated XHTTP/split-HTTP handling and current migration guidance often points newer H2/H3-style use toward XHTTP. Public issue history also shows that omitted/defaulted XHTTP/XMUX-related fields can change effective behavior across core versions.

PVNetwork requirements:

- preserve explicit values separately from “unset/use-core-default” state;
- record source/core version for imported profiles;
- validate protocol + security + flow + XHTTP mode as a combination;
- never silently migrate WebSocket/gRPC/old HTTP transport into XHTTP;
- expose only simple safe choices in Simple Mode;
- include effective generated settings in sanitized diagnostics;
- regression-test defaults when upgrading Xray-core.

Later `COMPLETE-REFERENCE-v2` must document current server-side Xray deployment/projects/panels, exhaustive XHTTP client/server fields, technical data/framing flow, HTTP-version behavior, ports/handshake, CDN/reverse-proxy topologies and installation matrices.
