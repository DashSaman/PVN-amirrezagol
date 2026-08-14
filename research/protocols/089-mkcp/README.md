# 089 — mKCP

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: **engine-specific transport capability**, not a standalone VPN protocol.

Shared Xray evidence: `research/upstreams/xray-family/`.

Current Xray-core research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`.

Pinned Xray source still recognizes mKCP/KCP configuration.

Research decision:

`LEGACY / COMPATIBILITY TRANSPORT TARGET`.

PVNetwork should expose mKCP only where real user/server compatibility demand justifies its UI/test/maintenance cost. It should normally remain an Advanced-mode transport capability.

Required evidence before support claim:

- selected core/version source support;
- exact parameter/default validation;
- client/server interoperability;
- resource/performance behavior;
- import/export round trip;
- failure/error mapping;
- platform TUN/DNS/routing interaction.

Later `COMPLETE-REFERENCE-v2` must add technical data/framing behavior, server/client install/deployment references, configuration/menu fields and any applicable cryptographic/security-layer relationship.
