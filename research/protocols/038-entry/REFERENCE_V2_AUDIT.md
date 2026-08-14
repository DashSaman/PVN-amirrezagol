# 038 — VMess — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only. No runtime, real-device, Store, performance or interoperability certification is claimed.

## Exact 16 gates

1. **Server implementation/project ecosystem — PASS.** Xray-core current tagged source is pinned; VMess is kept distinct from VLESS and alternative cores are interoperability candidates only.
2. **Installer/deployment projects — PASS.** Official Xray-install/GHCR plus major 3X-UI/Remnawave management references are mapped with independent licenses and supply-chain boundaries.
3. **Server OS/container/orchestration matrix — PASS.** Systemd/OpenRC Linux, Windows/macOS/BSD, OCI and evidence-backed Kubernetes/mobile N/A/bounds are recorded.
4. **Server panel/UI/menu maps — PASS.** Bare Xray config surface plus 3X-UI/Remnawave management surfaces are separately documented.
5. **Client install matrix — PASS.** v2rayN/v2rayNG and bounded Apple/TV treatment cover relevant targets without Store/device inference.
6. **Major client UI/menu maps — PASS.** v2rayN/v2rayNG VMess import/editor/routing/runtime/logging flows are mapped; explicit-vs-default and no-auto-migration requirements are preserved.
7. **Cryptographic design — PASS.** Current Xray `v26.7.28` source backs AuthID, ±120s clock requirement, anti-replay, AEAD header, per-session body key/IV, AES-128-GCM/ChaCha20-Poly1305 payload modes and the no-Forward-Secrecy warning.
8. **Data path/wire flow — PASS.** AuthID -> user/time/replay validation -> AEAD request header -> command/destination -> protected chunks -> response flow is mapped from source.
9. **Ports/transports/handshake — PASS.** No fixed VMess port is invented; VMess command semantics are separated from outer Xray stream transport/security; current handshake ordering/time dependency is explicit.
10. **Deployment topologies — PASS.** Direct, outer-security, HTTP-family transport, TUN/system-proxy, managed-node, routing/chaining and Mux reference topologies are bounded by selected-core capability.
11. **Source/license/activity pins — PASS.** Core/installer/panel/client immutable pins and separate MPL/GPL/AGPL licenses are recorded; moving main is activity evidence only.
12. **Security/supply-chain risks — PASS.** Advisory-aware core selection, remote installer risk, panel privilege/DB/subscription secrets, package digest freeze, UUID/profile/log redaction and dependency/license boundaries are explicit.
13. **Upgrade/uninstall/rollback — PASS.** Core/geodata/config/canonical profile/panel DB/certificates have separate lifecycle and rollback requirements; downgrade compatibility is not assumed.
14. **Differences/uncertainties — PASS.** Historical VMess modes vs current source, exact other-core interoperability, outer transport combinations, Apple/TV/Store, package digest and live version matrix are preserved as uncertainties/certification work rather than guessed.
15. **REFERENCE_INDEX — PASS.** `REFERENCE_INDEX.md` links the full granular dossier and reuse decision.
16. **Latest continuation state — PASS with promotion batch.** Tracker/state/handoff/foreground advance to **039 — Trojan**; no hidden runtime/device/Store requirement is introduced.

## Current security conclusion

VMess remains important for installed-base compatibility, but current Xray itself warns that it has no Forward Secrecy. PVNetwork should support it only through a pinned reviewed core and explicit capability/version handling, not by reimplementing or silently translating it.

Decision: **COMPLETE-REFERENCE-v2**.
