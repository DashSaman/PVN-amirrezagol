# PVNetwork — Third-Party Source / Asset Research and Mirroring Policy

This repository is a research-first project. Public availability on GitHub does **not** automatically grant permission to copy, relicense, redistribute, vendor, or commercially ship third-party code or assets.

## Required rule for every upstream project
Before copying any source file, binary, image, icon, translation, screenshot, configuration example, or documentation into this repository, record:

1. Upstream repository and canonical project site.
2. Exact pinned commit/tag used for research.
3. License and copyright notices.
4. Whether the license permits commercial use.
5. Whether redistribution is permitted.
6. Whether modifications may be distributed.
7. Copyleft/source-disclosure obligations.
8. Attribution/NOTICE requirements.
9. Trademark/branding restrictions.
10. Store-distribution concerns.

## Default research method
Prefer storing:
- immutable upstream URLs;
- pinned commit SHA;
- repository tree/API reference;
- file-path manifest;
- architecture notes;
- UI/menu/config/storage analysis;
- issue/PR/forum references;
- screenshots as links/metadata rather than copied image files;
- a reuse decision.

Do **not** mirror entire repositories or image collections by default.

## When vendoring may be considered
Only after license review. If a component is approved for vendoring, preserve its license/copyright notices, pin the exact version, document modifications, and keep the third-party component clearly separated from PVNetwork-owned code.

## Copyleft / restricted examples
GPL/AGPL/LGPL/MPL and custom source-available licenses have different obligations and must be evaluated individually. A public repository can still prohibit commercial redistribution. For example, the current Pritunl Client repository exposes source publicly but its custom license states non-commercial use and restricts redistribution; it is therefore a behavioral/architecture reference, not a code donor for a commercial PVNetwork release unless separately licensed.

## Images and screenshots
Do not copy third-party logos, icons, screenshots, promotional art, or UI assets merely because they are visible online. Store source links and descriptive analysis unless the asset license explicitly permits reuse. PVNetwork must use its own supplied logo and original visual identity.

## Goal
The research archive should let a developer understand each upstream project in depth without accidentally contaminating PVNetwork with code/assets that cannot legally be shipped.