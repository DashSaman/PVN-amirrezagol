# 040 — Shadowsocks classic AEAD — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference only; no runtime/Store/device/interoperability certification.

## Exact 16 gates

1. **Server ecosystem — PASS.** Dedicated MIT shadowsocks-rust primary candidate plus multi-core alternatives bounded.
2. **Installer/deployment projects — PASS.** crates/Homebrew/Snap/static/GHCR/Kubernetes/Helm plus panel reference reviewed.
3. **Server install matrix — PASS.** Linux/macOS/Windows/OCI/Kubernetes and router/mobile bounds explicit.
4. **Server UI/menu maps — PASS.** Dedicated CLI/config/manager surface and separate 3X-UI panel map documented.
5. **Client install matrix — PASS.** Desktop rust/Windows, official Android/TV and bounded Apple ecosystem covered.
6. **Major client UI/menu maps — PASS.** Android/TV, Windows and dedicated CLI behaviors mapped separately.
7. **Cryptographic design — PASS.** Canonical classic AEAD spec records exact standard methods, key/salt/nonce/tag sizes, EVP_BytesToKey/HKDF-SHA1 subkey derivation and unsafe stream-cipher boundary.
8. **Data path/wire flow — PASS.** TCP salt/chunk AEAD and independent UDP packet AEAD flows mapped.
9. **Ports/transports/handshake — PASS.** No fixed port invented; password/method/shared-key model, TCP/UDP and separate plugin transport are explicit.
10. **Deployment topologies — PASS.** Local modes, TCP/UDP, plugin, container/Kubernetes and manager topology mapped.
11. **Source/license/activity pins — PASS.** Active rust source, stable release, canonical spec and Android/Windows client pins/licenses recorded.
12. **Security/supply-chain risks — PASS.** Unsafe stream ciphers, password/config secrets, plugins, moving container tags/manifests, package digest/SBOM/licensing risks explicit.
13. **Upgrade/uninstall/rollback — PASS.** Dedicated binaries/services/config/plugins/container/chart and client profiles have separate lifecycle; stable vs master source boundary explicit.
14. **Differences/uncertainties — PASS.** Classic-vs-2022 split, extra/nonstandard methods, plugin compatibility, alternate cores, Apple/Store/device/digests/runtime remain bounded.
15. **REFERENCE_INDEX — PASS.** Complete dossier and reuse decision linked.
16. **Continuation — PASS with promotion batch.** Tracker/state/handoff/foreground advance to **041 — Shadowsocks 2022**.

Decision: **COMPLETE-REFERENCE-v2**.
