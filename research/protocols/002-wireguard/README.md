# 002 — WireGuard Research Dossier

Status: `IN-RESEARCH` / not implemented.

## Shared evidence

Use `research/upstreams/wireguard-family/` as the primary shared evidence base. Current committed files include:

- `SOURCE_REVISIONS.md`
- `CORE_ARCHITECTURE.md`
- `ANDROID_CLIENT.md`
- `APPLE_CLIENT.md`
- `LESSONS_AND_TESTS.md`

Related AmneziaWG research is intentionally separate under the same shared family because entry 003 is a distinct compatibility capability.

## Current upstream set

Primary reusable/reference sources currently pinned and reviewed:

- official `wireguard-go` portable userspace implementation;
- official WireGuard Windows source;
- official WireGuard Android source;
- official WireGuard Apple source.

Mesh products such as Tailscale/NetBird are not substitutes for the WireGuard protocol/core research. They belong to higher-level mesh/control-plane dossiers.

## Current architecture conclusion

PVNetwork should keep WireGuard behind a stable Core Adapter and select the appropriate official/native/platform implementation per OS. Do not bind UI directly to one engine and do not reimplement protocol cryptography.

Import/export representation, canonical PVNetwork profile storage, protected secrets and engine runtime configuration must remain separate layers.

## Evidence already captured

- canonical/mirror provenance and pinned revisions;
- core source-tree boundaries;
- Android backend/UI/settings/profile-store architecture;
- Apple app/NetworkExtension/Keychain/adapter architecture;
- Windows source-level service/UI/config-storage evidence recorded in Project State/Research Log because dedicated Windows dossier writes were connector-blocked;
- official mailing-list failure classes converted into PVNetwork regression requirements.

## Remaining completion gates

- dependency/SBOM/path-level license review;
- full Windows dossier persistence through a safe documentation path;
- current release/fix mapping;
- complete UI/assets/accessibility reference catalog;
- Store/package implications;
- final per-platform reuse decision;
- real interoperability/performance/power tests after implementation exists.

Do not mark this entry `COMPLETE-RESEARCH-v1` or implemented until the full research template and later PVNetwork test evidence are satisfied.