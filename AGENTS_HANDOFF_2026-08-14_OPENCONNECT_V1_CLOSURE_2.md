# AGENTS Handoff — 2026-08-14 — OpenConnect v1 Closure 2

This is a mandatory continuation checkpoint for `DashSaman/PVN-amirrezagol`.

## Current phase and priority

Repository phase: research / requirements / architecture.

Priority remains:

1. finish original `COMPLETE-RESEARCH-v1` campaign across the scope;
2. only then make the exhaustive `COMPLETE-REFERENCE-v2` server/client/crypto/wire-flow campaign the main campaign.

Full v2 contract already exists at:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

## OpenConnect family status

OpenConnect/Enterprise shared-family original research is now considered:

**`V1-HANDOFF-READY / NOT IMPLEMENTED`**

This is a research handoff milestone, not protocol support/certification.

Shared index:

`research/upstreams/openconnect-family/README.md`

Latest family status:

`docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_OPENCONNECT_V1_CLOSURE_2.md`

## New work completed after previous handoff

### 1. NetworkManager D-Bus / secrets / ownership

File:

`research/upstreams/openconnect-family/NETWORKMANAGER_DBUS_SECRETS.md`

Commit:

`c2b79cc9260938ce8657eafafd460565dcd19032`

Key evidence:

- `org.freedesktop.NetworkManager.openconnect` service family and D-Bus policy;
- NetworkManager VPN plugin lifecycle around connect/need-secrets/disconnect;
- ordinary profile properties separated from runtime secret/session values;
- user-context auth-dialog owns interactive authentication work;
- libsecret schema/persistence for remembered passwords;
- browser/SSO is a frontend/platform responsibility;
- verbose upstream debug output can expose passwords.

PVNetwork consequence: canonical profile, reusable protected secrets, non-secret remembered choices, temporary session values and runtime engine state must remain separate.

### 2. OpenConnect GUI screen/storage map

File:

`research/upstreams/openconnect-family/OPENCONNECT_GUI_SCREEN_STORAGE_MAP.md`

Commit:

`9a77acb23221fcce5751b4265a16f9502c1d4050`

Key evidence:

- current canonical GitLab/stable v1.6.2 behavior distinguished from archived historical GitHub source;
- historical source maps main window, quick/new profile, advanced editor, logs and prompt/trust dialogs;
- profile/storage areas identified (`server_storage`, `cryptdata`, certificate/key helpers);
- GUI profile storage is not the same as OpenConnect CLI config;
- confusing upstream “Batch Mode” password-remembering behavior converted into a PVNetwork explicit credential-policy requirement;
- SSO frontend callback gap remains an important product architecture lesson.

### 3. Security / advisories

File:

`research/upstreams/openconnect-family/SECURITY_AND_ADVISORIES.md`

Commit:

`4166c433fb5d18d5818e78bab76b4ec2133d9a86`

Recorded current/historical upstream security classes including v9.21 high-CPU and TPM2 auth-dialog fixes, certificate validation/metadata parsing, HTTP/framing parsing, reconnect/MTU state, route/leakage controls, legacy crypto and redacted diagnostics.

### 4. Packaging / distribution

File:

`research/upstreams/openconnect-family/PACKAGING_AND_DISTRIBUTION.md`

Commit:

`e457198fdf9490b9e3a3d9b8a37f8155c7b6762f`

Key rule: core, standalone GUI, NetworkManager frontend and PVNetwork's eventual Windows/Android/Apple/Linux product packaging are separate distribution surfaces. Final SBOM and LGPL obligations are per exact shipped build.

### 5. Assets / screenshot references

File:

`research/upstreams/openconnect-family/ASSETS_AND_SCREENSHOT_CATALOG.md`

Commit:

`6e8365e479d8e95c384ca9a1859cefddae030258`

Historical asset/source-resource paths are cataloged as references only. Do not copy project branding/assets without exact rights review. PVNetwork must use owner-supplied branding.

### 6. Performance/resource evidence

File:

`research/upstreams/openconnect-family/PERFORMANCE_AND_RESOURCE_EVIDENCE.md`

Commit:

`898d6fe0b57df133dd7d34814783e795213ba36d`

No invented throughput claim. Performance records must include pinned core/build, crypto backend, server/vendor/version, negotiated transport, MTU, network conditions and platform.

### 7. Support / reuse decision

File:

`research/upstreams/openconnect-family/SUPPORT_REUSE_DECISIONS.md`

Commit:

`07c82764a369a309990832ec85ba8497bbfd2d81`

Research priority:

1. 017 OpenConnect/ocserv controlled integration baseline;
2. 016 Cisco AnyConnect-compatible highest-value proprietary enterprise target;
3. 018 GlobalProtect;
4. 019 Fortinet conditional exact-version/mode;
5. 020/021 Pulse/Ivanti;
6. 022 Juniper Network Connect legacy;
7. 023 F5 partial/experimental;
8. 024 Array limited/experimental.

Shared `libopenconnect` classification remains `REUSE-CANDIDATE / LGPL-DISTRIBUTION-REVIEW-REQUIRED`.

### 8. Shared index/state synchronized

- Shared README commit: `856f16685b9967169acc8e865a0b5283033aa1ab`
- Dated status commit: `3d814136c3f3abe2b837acd62b564f1fe41a17ce`
- Project State commit: `340bfa9ef02f69050fc5f82802a77de12faf32e5`

## OpenConnect residual gaps — preserve, do not loop

- authoritative full v9.21 source-archive materialization/hash manifest remains tool-blocked;
- stronger machine-readable current canonical OpenConnect GUI main/v1.6.2 source materialization;
- running-client current screenshot set beyond source/resource references;
- final dependency-advisory/SBOM review for exact future shipped build;
- exact reproducible performance benchmarks where authoritative evidence exists;
- vendor certification needs implementation plus real appliance/server version labs;
- entry 016 Cisco README remains a connector-write blocker; Cisco evidence is preserved in shared files.

These gaps do not justify keeping OpenConnect as the only active original-research family.

## Owner's mandatory later full-reference expansion

After original v1 research gates, every applicable entry must execute `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`, producing as applicable:

- `SERVER_IMPLEMENTATIONS.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `CRYPTOGRAPHY.md`
- `DATA_PATH_AND_WIRE_FLOW.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `DEPLOYMENT_TOPOLOGIES.md`
- `REFERENCE_INDEX.md`

and split `server-ui/` / `client-ui/` files when multiple projects exist.

Server installer/panel research must include source pins, root/privilege requirements, packages/services, firewall/routing/DNS changes, exposed management interfaces, credential defaults, update/uninstall/rollback, container privileges and supply-chain risk. Do not recommend blind remote scripts without source review.

## Exact next action

1. Update `AGENTS.md` latest handoff pointer to this file.
2. Move to the next high-value unfinished **original `COMPLETE-RESEARCH-v1` family** based on actual tree/state.
3. Current recommended next family: **Xray / modern proxy ecosystem**, because shared work exists but substantial source/core/client/license/storage/issues/platform closure is incomplete and it covers many numbered entries.
4. Keep WireGuard/AmneziaWG remaining v1 gaps queued and return before declaring overall original campaign complete.
5. Do not start mass `COMPLETE-REFERENCE-v2` work yet.
6. At the end of every meaningful unit, create a newer AGENTS handoff and update the pointer.
