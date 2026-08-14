# Tunnelblick macOS Client — Developer Research

Pinned source: `Tunnelblick/Tunnelblick@46db6d5dd490379f3da6acc1253ed8d182614f96`.

## Research role
Tunnelblick is a major open-source macOS OpenVPN client and is valuable for studying native macOS application structure, menu-bar UX, configuration management, localization, privileged/helper separation, packaging and long-term compatibility decisions.

## Complete source reference
Recursive tree:
`https://api.github.com/repos/Tunnelblick/Tunnelblick/git/trees/46db6d5dd490379f3da6acc1253ed8d182614f96?recursive=1`

Root contents:
`https://api.github.com/repos/Tunnelblick/Tunnelblick/contents?ref=46db6d5dd490379f3da6acc1253ed8d182614f96`

## Languages
GitHub language statistics at review time are dominated by Objective-C, with substantial Shell plus supporting C, HTML and AppleScript. This makes Tunnelblick a strong reference for a mature native macOS desktop client rather than a cross-platform UI shell.

## Repository shape observed
The pinned repository contains large application/source areas such as:
- `tunnelblick/` — main application implementation;
- `third_party/` and `vendor/` — bundled/external code requiring path-level license auditing;
- OpenVPN-related source/reference material;
- build/tool scripts;
- acknowledgements/build documentation;
- release notes/history;
- macOS resources and application assets.

The repository is large; the recursive tree URL above is the complete pinned file-list reference and should be used instead of duplicating the full upstream tree in PVNetwork.

## Engineering lessons to extract
The deeper pass must map:
- status/menu-bar application architecture;
- profile/configuration discovery and organization;
- settings/preferences windows and per-profile options;
- logs/status windows;
- helper/privilege boundaries;
- macOS keychain/credential behavior;
- file associations/import behavior;
- update/signing/notarization approach;
- OpenVPN engine packaging/version selection;
- localization/resources;
- launch/login/background behavior;
- migration across macOS/OpenVPN versions.

## License/reuse
Treat Tunnelblick as a GPL-family open-source **reference implementation** until path-level licensing has been completed. Its large `third_party`/`vendor` footprint means a repository-level license label is not enough for copying individual files.

PVNetwork classification: **REFERENCE-ONLY by default for UI/application code; inspect component licenses individually before considering reuse.**

## Assets/screenshots
Tunnelblick contains product resources and public documentation imagery. Keep canonical links/path references rather than copying logos/screenshots/icons into PVNetwork. PVNetwork must use its own brand.

## PVNetwork lessons
- macOS deserves a platform-native integration dossier rather than assuming Windows/Android behavior maps directly;
- helper/privilege boundaries should be explicit and auditable;
- profile-level settings and global app settings should remain conceptually separate;
- engine/version management needs migration strategy;
- update/signing/notarization must be considered alongside core integration;
- path-level third-party licensing is mandatory for large mature repositories.

## Remaining research gaps
- enumerate the full UI/menu/preference controller map;
- map persistence and Keychain use from source;
- map helper/IPC architecture;
- audit update/signing/notarization code and docs;
- identify significant forks;
- review high-impact issues, release notes and official forums/docs;
- map tests and CI;
- inventory screenshot/resource directories and licenses;
- assess current Apple Store/direct-distribution implications.

Status: `IN-RESEARCH`.