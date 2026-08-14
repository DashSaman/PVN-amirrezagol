# AGENTS Handoff — 2026-08-14 — WireGuard / AmneziaWG v2 slice 8

Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`  
Entries: 002 WireGuard, 003 AmneziaWG  
State: **IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED**

## Durable evidence added

### Exact wg-easy framework dependency and request semantics

New file: `research/upstreams/wireguard-family/reference-v2/WGEASY_V15_3_FRAMEWORK_REQUEST_SEMANTICS.md`.

The `v15.3.0` package/lock pair now pins Nuxt `3.21.5`, Nitro `2.13.4`, h3 `1.15.11`, plus a separate h3-next `2.0.1-rc.20` dependency. The stable h3 `v1.15.11` request source was inspected directly: Host is taken from the Host header unless `xForwardedHost:true`; X-Forwarded-For is opt-in via `xForwardedFor:true`; and this version treats `x-forwarded-proto: https` as HTTPS unless `xForwardedProto:false`. No trusted-proxy allow-list exists inside those extraction helpers. This is version-specific and avoids importing newer h3 defaults into the pinned wg-easy audit.

Research commit: `ca4f57372327a3f4ddf4f1c40bd0ed0d15ec7698`.

### Apple entitlement / Network Extension boundary

New file: `research/upstreams/wireguard-family/reference-v2/APPLE_ENTITLEMENTS_AND_EXTENSION_BOUNDARY.md`.

Pinned WireGuard Apple source declares packet-tunnel-provider capability on both the iOS containing app and Network Extension target, with a shared `group.$(APP_ID_IOS)` App Group; the app target also declares Wi-Fi information capability. The pinned standalone AmneziaWG Apple fork preserves the same entitlement structure. This establishes the source-level app↔extension signing boundary and a concrete PVNetwork integration requirement, while explicitly not claiming App Store binary provenance or device execution.

Research commit: `6ec255958e1d2210e81a297534bb2a9ca031ce4a`.

## Strict checks

- entries 002/003 COMPLETE-REFERENCE-v2: **NO**.
- exact wg-easy Nuxt/Nitro/h3 dependency versions: **YES**.
- pinned h3 Host/X-Forwarded-* helper semantics: **YES**.
- effective built-image forwarded-header behavior: **NO — execution required**.
- Apple packet-tunnel entitlement architecture: **YES, source-level**.
- Apple App Group sharing boundary: **YES, source-level**.
- shipped Store binary entitlement/source correspondence: **NO**.
- Apple-device / AWG generation interop receipts: **NO**.

## External blockers retained

No representative container host, Apple signing/device/TestFlight environment, or AWG multi-generation peer matrix is available. Execution-only receipts remain blocked and must not be fabricated.

## Exact next action

Continue the same work unit. Inventory the pinned WireGuard/AWG Apple Xcode configuration templates and target bundle/product identifiers, then seek authoritative release/App Store metadata that can narrow shipped-build provenance without guessing. Reconcile entries 002/003 line-by-line against `FULL_PROTOCOL_REFERENCE_CONTRACT.md`, explicitly marking source-complete versus execution-blocked gates. If source-only gaps remain in wg-easy, inspect concrete Nitro adapter/server entry points for where h3 request helpers are invoked. Keep entries PENDING until all strict gates genuinely pass.
