# Multi-Protocol GUI Clients — Current License / Reuse Findings

Research baseline: 2026-08-14. These are client-application findings; underlying cores must be reviewed separately.

## Clash Verge Rev
- Repository: `clash-verge-rev/clash-verge-rev`
- Active in 2026; GitHub reports TypeScript as main language and describes it as Tauri-based for Windows/macOS/Linux.
- GitHub license metadata: GPL-3.0.
- PVNetwork use: high-value desktop UX/architecture/issue reference; direct code reuse requires GPL-compatible distribution strategy.

## FlClash
- Repository: `chen08209/FlClash`
- Active in 2026; main language Dart; multi-platform Flutter client.
- GitHub license metadata: GPL-3.0.
- PVNetwork use: high-value cross-platform UI/Flutter/behavior reference; direct code reuse requires GPL-compatible strategy.

## Karing
- Repository: `KaringX/karing`
- Active in 2026; main language Dart.
- GitHub metadata cannot classify the custom license automatically, but repository `LICENSE.md` states GPL version 3 or later and adds a restriction against derivative works implying association/name use without consent.
- Repository includes many localized README files including Persian (`README_fa.md`), useful as terminology/documentation reference.
- PVNetwork use: architecture/localization/UX reference by default; do not clone branding or UI.

## v2rayN
- Pinned earlier in this research at `e01717d8326a4f5060b335523590c5fda943fe03`.
- LICENSE: GPLv3.
- PVNetwork use: important desktop multi-core/profile UX reference; source reuse only under compatible GPL distribution.

## v2rayNG
- Pinned earlier at `e8a82d9810ca1cf97a3cc8a9b9525a9f21955807`.
- LICENSE: GPLv3.
- PVNetwork use: major Android client reference; source reuse only under compatible GPL distribution.

## Hiddify
- Pinned earlier at `276a7effb0046a039220a745022563740968c0b8`.
- Current `LICENSE.md` uses an Extended GPLv3 arrangement with additional conditions, including non-commercial restriction without written permission and fork/interface/branding conditions.
- PVNetwork use: UX/architecture/reference only for a commercial product unless separate permission/license is obtained.

## Happ Desktop
- Repository reviewed: `Happ-proxy/happ-desktop`.
- Current repository root only contains `README.md` and a `release` file; it is a release/product repository rather than confirmed complete desktop source.
- GitHub reports no repository license.
- README states the product is powered by Xray core, but that does not make the Happ application source open/reusable.
- PVNetwork use: product/UX/release reference until canonical licensed source is found.

## Core/client separation rule
Never infer a GUI application's license from its engine, and never infer an engine's license from the GUI. Examples already found:
- Xray-core MPL-2.0 vs multiple surrounding clients GPL/custom.
- AmneziaWG Go MIT vs Amnezia Client GPL-3.0.
- OpenVPN 3 dual AGPL/MPL vs GPL GUI references.

Every future client review must record exact pinned license evidence before any source is copied.