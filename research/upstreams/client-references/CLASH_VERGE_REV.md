# Clash Verge Rev — Developer Research Dossier

Pinned repository: `clash-verge-rev/clash-verge-rev@6960daf05e5208ef9a8af482c9dcb791086503fe` from the current `dev` branch.

Research role: high-value **desktop UI / Tauri architecture / profile-routing UX reference**. Repository license metadata is GPL-3.0, so direct source reuse in a closed commercial PVNetwork product requires a GPL-compatible distribution strategy or separate legal basis.

## Complete source reference
Recursive source-tree manifest:
`https://api.github.com/repos/clash-verge-rev/clash-verge-rev/git/trees/6960daf05e5208ef9a8af482c9dcb791086503fe?recursive=1`

Important root areas at the pinned revision include:
- `src/` — frontend application.
- `src-tauri/` — native Tauri/Rust application layer and packaging.
- `crates/` — Rust workspace/components.
- `tests/` — tests.
- `docs/`, `template/`, `scripts/`, `.github/` — documentation/templates/build/release/automation.
- `Cargo.toml` / `Cargo.lock` — Rust workspace/dependencies.
- `package.json`, `pnpm-lock.yaml`, `pnpm-workspace.yaml` — JS/TS dependency/workspace layer.
- `vite.config.mts`, `vitest.config.mts`, `tsconfig.json` — frontend/test tooling.

## Frontend architecture
`src/` contains:
- `assets/`
- `components/`
- `hooks/`
- `locales/`
- `pages/`
- `providers/`
- `services/`
- `types/`
- `utils/`
- `main.tsx`

This is a useful separation model for PVNetwork: presentation/components, state/hooks/providers, service/API layer, localization and domain types are not mixed into the native backend.

## Navigation/menu map from source
Pinned `src/pages/_navigation-meta.ts` defines the main application routes:
- Home `/`
- Proxies `/proxies`
- Profiles `/profile`
- Connections `/connections`
- Rules `/rules`
- Logs `/logs`
- Unlock `/unlock`
- Settings `/settings`

The `src/pages/` tree also contains page-level files such as `home.tsx`, `profiles.tsx`, `connections.tsx`, `logs.tsx`, routing/navigation/layout/theme files, and additional pages in the same source tree.

PVNetwork lesson: maintain a single declarative navigation model and keep major operational areas separated. Do not copy the exact information architecture blindly; PVNetwork needs a universal protocol/profile model rather than a Clash-only mental model.

## Native/Tauri layer
`src-tauri/` contains:
- `Cargo.toml` / Rust build configuration;
- `capabilities/` — Tauri capability declarations;
- `assets/`, `icons/`, `images/` — desktop/package resources;
- `packages/` — packaging resources;
- `src/` — Rust application/backend code;
- `tauri.conf.json` plus OS-specific Tauri configuration files.

`src-tauri/src/` includes major areas such as:
- `cmd/`
- `config/`
- `core/`
- `enhance/`
- `feat/`
- `module/`
- `process/`
- `utils/`
- `lib.rs`
- `main.rs`

This strongly indicates a frontend/native-process split where Rust owns native application functions, configuration/core/process orchestration and Tauri commands while TypeScript/React owns most presentation and interaction.

## Localization/assets
The frontend contains `src/locales/`. Native packaging has separate icon/image/resource directories. PVNetwork should study localization structure and language switching but must not copy Clash Verge branding, icons, screenshots or layout identity.

## Testing/tooling
The root has a dedicated `tests/` directory, Vitest config, Rust/Cargo tooling, lint/format/static-analysis configuration, Renovate dependency automation and GitHub workflow infrastructure. A deeper pass should map which frontend/native modules are covered and identify missing reliability tests.

## PVNetwork lessons
- Tauri can provide a small native desktop shell with web frontend and Rust system layer.
- Keep frontend services/types separate from native command implementation.
- Keep navigation metadata centralized.
- Treat profiles, live connections, rules and logs as separate concepts.
- Keep platform capabilities explicit in application packaging.
- Dependency/update automation is part of maintainability, not an afterthought.
- A successful reference architecture can still be legally unsuitable for direct code reuse because of GPL.

## Required next research
- enumerate every page and settings subsection from `src/pages/` and components;
- map frontend state/storage and backend config files precisely;
- map IPC/Tauri command surface from source;
- map profile/subscription/config persistence paths;
- map core binary acquisition/version/update behavior;
- map system tray/menu behavior;
- review issue/PR/release history for crashes, route/DNS cleanup, update problems, large profile sets, sleep/resume and OS-specific bugs;
- inspect tests and CI coverage;
- inventory official screenshots/assets as links with rights status;
- audit all third-party dependencies/licenses before any reuse.

Status: `IN-RESEARCH`. No PVNetwork implementation is implied.