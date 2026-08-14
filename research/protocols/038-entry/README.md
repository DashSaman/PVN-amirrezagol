# 038 — VMess

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: application proxy protocol in the V2Ray/Xray ecosystem.

Primary shared evidence: `research/upstreams/xray-family/`.

Current Xray-core research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5` (MPL-2.0).

Research decision from `SUPPORT_REUSE_DECISIONS.md`:

`COMPATIBILITY TARGET / MATURE ECOSYSTEM / LOWER STRATEGIC PRIORITY THAN VLESS`.

PVNetwork requirements:

- preserve exact imported VMess semantics and current/legacy fields;
- test share-link and full-config round trips;
- keep transport/security separate from application protocol;
- maintain server/core-version interoperability evidence;
- do not silently convert VMess profiles to VLESS.

Client references include v2rayN/v2rayNG and other Xray-capable clients; GUI licenses are independent from Xray-core and are reference-only by default for a closed PVNetwork product.

Later `COMPLETE-REFERENCE-v2` must add server implementations/installers/panels, client/server install matrices, exhaustive menus, cryptography, wire/data flow, handshake/ports and deployment topology files required by `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`.
