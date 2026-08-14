# OpenVPN — Reference Index / Evidence Status

Reference layer: `COMPLETE-REFERENCE-v2`

Research snapshot date: 2026-08-14

State: **`REFERENCE-v2 IN-RESEARCH`**.

This file is the index of evidence used by the OpenVPN v2 dossier. Exact release pins must be updated before any production implementation/certification.

---

# A. Primary upstream code

## OpenVPN Community 2.x client/server

Repository:

`https://github.com/OpenVPN/openvpn`

Role:

- primary Community server/client source;
- configuration/directive semantics;
- TLS control channel and data channel implementation;
- TUN/TAP/platform integration;
- DCO integration paths;
- interoperability baseline.

Pin status:

**MOVING/current repository inspected; exact selected release/tag still required for the certification lab.**

Action:

Before implementation, choose the current stable Community release rather than relying on `master` and record its immutable tag/commit here.

## OpenVPN 3 Core

Repository:

`https://github.com/OpenVPN/openvpn3`

Pinned v1 source:

`1fd271caefc9a71406afdc2ff2460999dcfdb234`

Role:

- primary PVNetwork OpenVPN client-core candidate;
- C++ client library;
- source for OpenVPN Connect family core behavior.

License at pin:

`AGPL-3.0-only OR MPL-2.0` according to reviewed upstream license file.

Action:

Select a current stable implementation pin for the actual PVNetwork build and re-run dependency/security/license review.

---

# B. Official OpenVPN documentation/products

## Community documentation/manual

Primary domain:

`https://openvpn.net/community-docs/`

Use for:

- current directive behavior;
- server/client options;
- installation/configuration guidance;
- version-specific deprecations and security behavior.

Do not treat an unversioned documentation page as a permanent spec snapshot; record selected OpenVPN release/manual version in certification receipts.

## OpenVPN Access Server

Official docs:

`https://openvpn.net/as-docs/`

Role:

- official commercial server product;
- Admin Web UI;
- Client Web UI;
- enterprise provisioning/authentication;
- product installation/upgrade/backup reference.

Source/reuse status:

Product reference/interoperability target. Do not assume full proprietary product source is reusable under the Community OpenVPN license.

## OpenVPN Connect

Official docs:

`https://openvpn.net/connect-docs/`

Role:

- official client UX/settings/import reference;
- Windows/macOS/Android/iOS product behavior;
- Access Server provisioning reference.

Source/reuse status:

Use official product behavior/reference; OpenVPN3 is the open client-core candidate. Do not copy Connect proprietary UI/assets.

---

# C. Open-source client references

## OpenVPN GUI Windows

Repository:

`https://github.com/OpenVPN/openvpn-gui`

Pinned source:

`7295bdc155e0d8d66dd53ab9bc4eb462e77bfa7f`

Role:

- Windows tray/menu/config/Registry/core integration;
- translation terminology/reference.

Existing dossier:

`research/upstreams/openvpn-family/OPENVPN_GUI_WINDOWS.md`

## ics-openvpn / OpenVPN for Android

Repository:

`https://github.com/schwabe/ics-openvpn`

Pinned source:

`ede0aa0b334b47941407599fef3d76da8b933edf`

Role:

- Android VpnService/profile/import/UI/source;
- encrypted profile storage;
- TV/system integration.

License/reuse status:

GPL application source; reference-only by default for a closed PVNetwork UI/application.

Existing dossier:

`research/upstreams/openvpn-family/ICS_OPENVPN_ANDROID.md`

## Tunnelblick

Repository:

`https://github.com/Tunnelblick/Tunnelblick`

Pinned source:

`cc3cefa77912fc103831ef8517962be438a983d2`

Role:

- macOS menu bar/profile/config/helper/lifecycle reference.

Existing dossier:

`research/upstreams/openvpn-family/TUNNELBLICK_MACOS.md`

## Pritunl Client

Repository/reference pinned in v1:

`pritunl/pritunl-client-electron@69508329df8a55070d9a1758765064516bb42a3a`

Role:

- desktop UX/auth/tray/profile reference.

Reuse status:

Current public license reviewed in v1 restricts commercial use/redistribution; reference-only unless separate rights are obtained.

---

# D. Server installer/control-plane references

## Angristan openvpn-install

Repository:

`https://github.com/angristan/openvpn-install`

Role:

- shell installer/operator-workflow reference;
- PKI/firewall/DNS/client generation behavior.

Status:

Exact current commit/license/OS support and source-side effects must be pinned before any recommendation or automation.

## Nyr openvpn-install

Repository:

`https://github.com/Nyr/openvpn-install`

Role:

- minimalist/commonly referenced shell installer behavior.

Status:

Reference only until exact source/license/current OS support/supply-chain review is recorded.

## PiVPN

Repository:

`https://github.com/pivpn/pivpn`

Role:

- interactive server installer/management UX;
- OpenVPN/WireGuard operator workflows depending on project version.

Status:

Reference/control-flow source. Pin exact revision/license/backend behavior before reuse/recommendation.

## Docker-OpenVPN

Repository:

`https://github.com/kylemanna/docker-openvpn`

Role:

- container packaging/PKI/network privilege reference.

Status:

Maintenance/current-image/security state must be checked before recommendation. Never deploy floating image tags blindly.

## Pritunl server

Repository:

`https://github.com/pritunl/pritunl`

Role:

- third-party web admin/control-plane/server reference;
- user/organization/server/profile lifecycle.

Status:

Pin exact server edition/version/license/current dependencies before reuse.

---

# E. V2 dossier files and status

| File | Status | Notes |
|---|---|---|
| `SERVER_IMPLEMENTATIONS.md` | PRESENT / IN-RESEARCH | Community, Access Server, Pritunl, appliance/container classes |
| `SERVER_INSTALLERS_AND_PROJECTS.md` | PRESENT / IN-RESEARCH | Angristan, Nyr, PiVPN, Docker, Access Server, Pritunl, package paths |
| `SERVER_INSTALL_MATRIX.md` | PRESENT / IN-RESEARCH | OS/container/deployment classification; exact version receipts pending |
| `SERVER_UI_AND_MENUS.md` | PRESENT / IN-RESEARCH | Community config/admin, Access Server, Pritunl, installer menu hierarchy |
| `CLIENT_INSTALL_MATRIX.md` | PRESENT / IN-RESEARCH | Connect, GUI, Android, Tunnelblick, Linux paths |
| `CLIENT_UI_AND_MENUS.md` | PRESENT / IN-RESEARCH | major reference client menu/function maps |
| `CRYPTOGRAPHY.md` | PRESENT / IN-RESEARCH | control/data channel, TLS, tls-auth/tls-crypt, AEAD, PKI, DCO |
| `DATA_PATH_AND_WIRE_FLOW.md` | PRESENT / IN-RESEARCH | userspace/DCO/TUN/routing/DNS flows |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | PRESENT / IN-RESEARCH | UDP/TCP/configurable ports and product handshake states |
| `DEPLOYMENT_TOPOLOGIES.md` | PRESENT / IN-RESEARCH | remote access/site-to-site/HA/cloud/container/PKI patterns |
| `REFERENCE_INDEX.md` | PRESENT | this file |

---

# F. Existing v1 shared evidence

Use in addition to v2 files:

`research/upstreams/openvpn-family/`

- `SOURCE_REVISIONS.md`
- `OPENVPN3_CORE.md`
- `OPENVPN_CONNECT.md`
- `OPENVPN_GUI_WINDOWS.md`
- `ICS_OPENVPN_ANDROID.md`
- `TUNNELBLICK_MACOS.md`
- `PRITUNL_CLIENT.md`
- `DEPENDENCIES_TESTS_SECURITY.md`
- `LESSONS_AND_TESTS.md`
- `SUPPORT_REUSE_DECISIONS.md`

Numbered v1:

`research/protocols/001-openvpn/README.md`

---

# G. Supply-chain evidence still required

Before any project becomes `PVNETWORK-RECOMMENDED` or is bundled/automated:

- immutable source tag/commit;
- repository owner/canonical upstream;
- license/SPDX/file-level exceptions;
- release signature/hash/provenance;
- dependencies/SBOM;
- current maintenance/release date;
- vulnerability/advisory status;
- installer remote downloads/package repositories;
- generated services/files/network rules;
- uninstall/rollback;
- secret handling;
- Store/commercial redistribution implications.

---

# H. Missing exact evidence / next actions

OpenVPN v2 is **not yet complete** because the following evidence still needs version-pinned expansion/lab verification:

1. exact current stable Community Server release/tag + source commit;
2. exact current stable OpenVPN3 build pin selected for PVNetwork research;
3. Access Server exact current version, supported OS matrix and exhaustive Admin Web UI menu snapshot;
4. OpenVPN Connect exact current version on each platform and exhaustive screen/control snapshot;
5. exact current commits/licenses/OS matrices for Angristan/Nyr/PiVPN/Docker/Pritunl server;
6. source-derived before/after package/files/firewall/sysctl/DNS maps for installers;
7. exact Community/OpenVPN3 algorithm/default/DCO matrices;
8. packet/state-machine/source references for selected protocol version;
9. real install/uninstall receipts;
10. real packet captures/interoperability/performance/Store/device evidence.

## Next exact action

Create an OpenVPN v2 status checkpoint summarizing the 11 required files now present and the remaining exact/lab gaps. Then continue immediately to **WireGuard / AmneziaWG `COMPLETE-REFERENCE-v2`** without waiting for owner confirmation.
