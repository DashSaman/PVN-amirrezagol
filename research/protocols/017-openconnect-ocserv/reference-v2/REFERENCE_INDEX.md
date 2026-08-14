# OpenConnect / ocserv-compatible — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14 UTC

Entry: **017 — OpenConnect / ocserv-compatible**.

## Pinned source / release / license

- OpenConnect **v9.21** — canonical GitLab tag, commit `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1, released 2026-06-16 in existing repository evidence; upstream signed tarball path recorded in `research/upstreams/openconnect-family/SOURCE_PIN.md`.
- ocserv **1.5.0** — canonical signed GitLab tag, commit `49f9956eeeffd613e4bcac3f6450c682ec21e75a`, GPLv2+, released 2026-06-07. Release includes material security fixes; exact baseline recorded here rather than using an unpinned distro name.

## Shared deep evidence

`research/upstreams/openconnect-family/`:

- `SOURCE_PIN.md`
- `DEPENDENCIES_AND_LGPL.md`
- `API_LIFETIME_AND_CALLBACKS.md`
- `CONFIG_STORAGE_AND_PLATFORM.md`
- `FRONTEND_OPENCONNECT_GUI.md`
- `OPENCONNECT_GUI_SCREEN_STORAGE_MAP.md`
- `FRONTEND_NETWORKMANAGER.md`
- `NETWORKMANAGER_DBUS_SECRETS.md`
- `ISSUE_MR_FIX_MATRIX.md`
- `SECURITY_AND_ADVISORIES.md`
- `TEST_AND_CI_INVENTORY.md`
- `PACKAGING_AND_DISTRIBUTION.md`
- `SUPPORT_REUSE_DECISIONS.md`

Canonical server docs additionally include `openconnect/ocserv` project README and current `doc/sample.config` for auth/listener/TUN/route/DNS/DTLS/vhost controls.

## Mandatory V2 files

`SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_INDEX.md`.

Reuse decision: **preferred controlled open AnyConnect-compatible client/server reference pair; libopenconnect through public API, ocserv as separately licensed server component.**
