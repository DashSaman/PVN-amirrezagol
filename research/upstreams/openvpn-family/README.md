# OpenVPN Family — Shared Upstream Research Dossier

Related matrix entry: **001 — OpenVPN**.

Research state: `IN-RESEARCH`. This directory is the shared evidence base for OpenVPN clients/cores. Protocol-specific conclusions belong in `research/protocols/001-openvpn/README.md`.

## Why a shared dossier is necessary
“OpenVPN client” does not refer to one codebase. Different top clients expose very different engineering patterns:

- **OpenVPN Connect** — official cross-platform product/UX and behavior reference; OpenVPN 3 is the public core, but the complete Connect application UI/source is not treated as a public reusable codebase.
- **OpenVPN 3** — primary public C++ client-core candidate for PVNetwork.
- **OpenVPN GUI** — official Windows tray GUI; useful reference for Windows integration, config discovery, registry preferences and lifecycle around the OpenVPN engine.
- **OpenVPN for Android / ics-openvpn** — mature Android reference using Android VPN APIs; source is GPL and explicitly intended as an application rather than a drop-in library.
- **Tunnelblick** — major open-source macOS OpenVPN client; highly useful for macOS behavior/UX/privileged-helper research; GPL family obligations apply.
- **Pritunl Client** — useful multi-platform architecture/UX reference, but its current custom license explicitly restricts commercial use and redistribution, so it is `REFERENCE-ONLY` unless separately licensed.
- **Amnezia VPN** — useful multi-protocol product/reference to study how OpenVPN coexists with other engines; analyzed in its own shared dossier when created.

## Current PVNetwork direction
The working direction is to evaluate **OpenVPN 3** as the reusable OpenVPN protocol core while learning platform UX/integration lessons from official OpenVPN Connect, OpenVPN GUI, ics-openvpn and Tunnelblick. This is a research conclusion, not an implementation decision yet.

## Files in this dossier
- `SOURCE_REVISIONS.md` — pinned upstream revisions and complete-tree references.
- `OPENVPN3_CORE.md` — public core architecture/license/source notes.
- `OPENVPN_CONNECT.md` — official product UX/settings/import/release behavior reference.
- `OPENVPN_GUI_WINDOWS.md` — Windows GUI source/UI/storage architecture.
- `ICS_OPENVPN_ANDROID.md` — Android client source/architecture/license notes.
- `TUNNELBLICK_MACOS.md` — macOS client source/architecture/license notes.
- `PRITUNL_CLIENT.md` — source-visible but commercially restricted reference.
- `LESSONS_AND_TESTS.md` — upstream issues/regressions and PVNetwork test lessons.

## Completion gate
This shared dossier remains incomplete until each selected client has source-tree evidence, UI/menu map, config/storage map, platform model, issue/release/forum review, asset references, tests/CI review, license classification, and a final reuse decision.

Follow `research/PROTOCOL_RESEARCH_TEMPLATE.md` and `research/SOURCE_MIRROR_POLICY.md`.