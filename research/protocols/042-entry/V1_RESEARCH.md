# 042 — Hysteria (legacy v1) — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: legacy Hysteria protocol generation; do not infer compatibility from current Hysteria2 source.

Research decision:

**`LEGACY COMPATIBILITY TARGET / DO NOT INFER FROM HYSTERIA2`**

Current upstream `apernet/hysteria` is Hysteria2-focused. If PVNetwork later supports legacy Hysteria1, it must pin and audit an actual v1-compatible client/server source/version separately.

Required future evidence:

- legacy source/tag;
- config/URI schema;
- security/dependency review;
- exact server/client interoperability;
- migration guidance;
- legacy warning/status in UI.

Do not silently convert Hysteria1 profiles to Hysteria2.

Shared v1 evidence: `research/upstreams/hysteria-family/`.

Full server installers, cryptography, wire flow and install/menu matrices belong to mandatory `COMPLETE-REFERENCE-v2`.
