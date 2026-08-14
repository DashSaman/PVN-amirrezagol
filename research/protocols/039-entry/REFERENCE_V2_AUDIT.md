# 039 — Trojan — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference only; no runtime/device/Store/interoperability certification is claimed.

## Exact 16 gates

1. **Server ecosystem — PASS.** Maintained Xray primary candidate and historical original/Trojan-Go references are independently pinned/licensed.
2. **Installer/deployment projects — PASS.** Xray-install/GHCR/3X-UI/Remnawave plus historical standalone build path are reviewed with supply-chain boundaries.
3. **Server install matrix — PASS.** Linux/OpenRC/Windows/macOS/BSD/OCI and evidence-backed Kubernetes/mobile N/A/bounds recorded.
4. **Server UI/menu maps — PASS.** Bare Xray config plus two panel families mapped; Trojan flow removal/deprecation is current-version explicit.
5. **Client install matrix — PASS.** v2rayN/v2rayNG/bare Xray/Apple bounds cover relevant platforms.
6. **Major client UI/menu maps — PASS.** Profile/import/password/TLS/transport/routing/runtime/logging flows mapped separately.
7. **Cryptographic design — PASS.** Genuine TLS security boundary and exact `hex(SHA224(password))` protocol token are source/spec backed; token is not misrepresented as secure password storage.
8. **Data path/wire flow — PASS.** TLS -> token -> SOCKS5-like request -> TCP/UDP framing -> server validation/dispatch is documented from original spec and current Xray source.
9. **Ports/transports/handshake — PASS.** No fixed port invented; canonical TLS handshake and Xray-specific composition/fallback boundaries are explicit.
10. **Deployment topologies — PASS.** Canonical direct TLS, local capture, fallback, panel-managed and chaining topologies are bounded.
11. **Source/license/activity pins — PASS.** Current Xray and inactive historical projects have immutable pins and exact MPL/GPL boundaries.
12. **Security/supply-chain risks — PASS.** TLS identity, password storage/redaction, insecure-cert risk, installer/panel/package pinning and SBOM/dependency work explicit.
13. **Upgrade/uninstall/rollback — PASS.** Shared Xray lifecycle plus independent config/panel/cert/password backup boundaries documented; downgrade compatibility not assumed.
14. **Differences/uncertainties — PASS.** Canonical real-TLS semantics vs Xray stream composition, old project inactivity, alternate-core interop, Store/device/package digest/live matrix explicitly bounded.
15. **REFERENCE_INDEX — PASS.** `REFERENCE_INDEX.md` links full dossier and reuse decision.
16. **Latest continuation state — PASS with promotion batch.** Tracker/state/handoff/foreground advance to **040 — Shadowsocks**.

Decision: **COMPLETE-REFERENCE-v2**.
