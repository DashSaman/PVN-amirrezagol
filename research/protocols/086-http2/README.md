# 086 — HTTP/2

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: **application transport/protocol building block**, not a standalone VPN.

## Xray-specific semantics

Shared evidence: `research/upstreams/xray-family/`.

Current Xray-core research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`.

Pinned current source distinguishes historical Xray HTTP/H2 transport naming from newer XHTTP modes that can use HTTP/2-style behavior. The old generic transport must not be merged blindly with current XHTTP semantics.

Research decision:

`MIGRATION / SEMANTIC CLASSIFICATION REQUIRED`.

PVNetwork must separately represent:

1. generic HTTP/2 as an underlying standards technology;
2. historical Xray HTTP/H2 transport configuration;
3. current XHTTP modes using HTTP/2 behavior.

Do not store all three as one `http2=true` field and do not silently rewrite old profiles.

## Multi-engine rule

HTTP/2 is implemented by language runtimes and multiple candidate cores. PVNetwork should use the selected engine/runtime implementation rather than create a separate HTTP/2 stack.

## Later v2 work

The full reference phase must document standards references, framing/data path relationship, exact engine/server uses, install/deployment examples, configuration/menu fields and migration/version behavior required by `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`.
