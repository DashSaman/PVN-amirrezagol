# PVNetwork Research Campaign Status — 2026-08-14 — OpenConnect v1 Closure 2

Repository: `DashSaman/PVN-amirrezagol`

Phase: research / requirements / architecture. No implementation or production support is claimed.

## Priority rule

The original `COMPLETE-RESEARCH-v1` campaign remains the active priority. The later `COMPLETE-REFERENCE-v2` server/client/crypto/wire-flow expansion is mandatory, but mass v2 work must not displace unfinished original research.

## New OpenConnect closure evidence committed in this work unit

### NetworkManager D-Bus / secret ownership

File:

`research/upstreams/openconnect-family/NETWORKMANAGER_DBUS_SECRETS.md`

Commit:

`c2b79cc9260938ce8657eafafd460565dcd19032`

Recorded from pinned NetworkManager-openconnect source:

- D-Bus/service identity and policy;
- `connect / need_secrets / disconnect` VPN plugin lifecycle;
- separation of ordinary profile data and runtime secrets;
- authentication dialog/user-context ownership;
- `libsecret` password persistence schema and field ownership;
- browser/SSO separation;
- debug-log secret exposure warning;
- PVNetwork product model rules for canonical profiles, reusable secrets and temporary session material.

### OpenConnect GUI screen/storage map

File:

`research/upstreams/openconnect-family/OPENCONNECT_GUI_SCREEN_STORAGE_MAP.md`

Commit:

`9a77acb23221fcce5751b4265a16f9502c1d4050`

Recorded:

- current canonical/stable v1.6.2 distinction from archived historical GitHub tree;
- main window, quick/new profile, advanced editor, log window and prompt-dialog source boundaries;
- historical `server_storage` / `cryptdata` / certificate/key helper areas;
- product profile storage vs CLI configuration distinction;
- password/group/banner persistence UX lesson;
- browser/SSO frontend capability requirement;
- installer/resource/source asset references.

### Security / advisory review

File:

`research/upstreams/openconnect-family/SECURITY_AND_ADVISORIES.md`

Commit:

`4166c433fb5d18d5818e78bab76b4ec2133d9a86`

Recorded current/historical upstream security lessons including:

- v9.21 high-CPU/infinite-loop and TPM2 auth-dialog crash fixes;
- CVE-2020-12105 certificate-validation class;
- CVE-2020-12823 local certificate metadata buffer-overflow class;
- CVE-2019-16239 HTTP chunked/framing parsing class;
- historical reconnect/MTU overflow class;
- route-control/TunnelVision-style leakage class;
- legacy crypto policy;
- secret-redaction and exact-build SBOM requirements.

### Packaging / distribution review

File:

`research/upstreams/openconnect-family/PACKAGING_AND_DISTRIBUTION.md`

Commit:

`e457198fdf9490b9e3a3d9b8a37f8155c7b6762f`

Recorded separate packaging surfaces for core, standalone GUI, NetworkManager frontend and platform product integrations. Windows, Android, Apple and Linux release concerns are now explicitly distinct. LGPL shared-library/relinking considerations remain a legal/platform review gate.

### Assets / screenshot catalog

File:

`research/upstreams/openconnect-family/ASSETS_AND_SCREENSHOT_CATALOG.md`

Commit:

`6e8365e479d8e95c384ca9a1859cefddae030258`

Recorded source-backed historical GUI application/status/profile/action/installer assets, `.ui` resource references, GNOME visual/localization evidence and a strict reference-only policy unless exact file-level reuse rights are approved.

### Performance/resource evidence

File:

`research/upstreams/openconnect-family/PERFORMANCE_AND_RESOURCE_EVIDENCE.md`

Commit:

`898d6fe0b57df133dd7d34814783e795213ba36d`

Recorded performance dimensions and reproducibility fields rather than inventing throughput claims. Includes v9.21 high-CPU regression lesson, preferred-vs-fallback transport distinction, framing/reassembly cost classes, UI/statistics isolation and debug-log overhead.

### Support / reuse decisions

File:

`research/upstreams/openconnect-family/SUPPORT_REUSE_DECISIONS.md`

Commit:

`07c82764a369a309990832ec85ba8497bbfd2d81`

Research-stage direction:

1. 017 OpenConnect/ocserv — first controlled enterprise integration target.
2. 016 Cisco AnyConnect-compatible — highest-priority proprietary certification target.
3. 018 GlobalProtect — high-value but capability/version matrix required.
4. 019 Fortinet — conditional exact-version/mode certification.
5. 020/021 Pulse/Ivanti — appliance/auth/posture matrix.
6. 022 Juniper Network Connect — legacy compatibility.
7. 023 F5 — experimental/partial vendor-specific certification.
8. 024 Array — limited/experimental, demand-driven.

No product support claim is made.

### Shared family index synchronized

`research/upstreams/openconnect-family/README.md`

Commit:

`856f16685b9967169acc8e865a0b5283033aa1ab`

Shared family state is now:

`V1-HANDOFF-READY / NOT IMPLEMENTED`

## Explicit remaining OpenConnect gaps

These remain known and must not be forgotten:

- official v9.21 source archive materialization/complete authoritative hash manifest remains tool-blocked;
- stronger machine-readable current OpenConnect GUI main/v1.6.2 source tree materialization;
- current running-client screenshots beyond source/resource references;
- exact dependency-advisory/SBOM review for the future selected shipped build;
- more current frontend issue-level detail can still be added;
- reproducible pinned performance benchmarks where authoritative evidence is available;
- actual vendor/version certification requires implementation and real lab servers.

These gaps are preserved, but they no longer justify holding the entire original campaign on this family.

## Second-layer expansion already recorded

The owner additionally requires a complete server+client technical reference for all 93 entries. This is already codified in:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

After v1 research gates across the original scope, every applicable entry must receive server implementation/installer research, server/client OS install matrices, full UI/menu maps, cryptography, data-path/wire-flow, ports/transports/handshake and deployment topology documentation.

## Next exact action

Move to the next highest-value incomplete **original `COMPLETE-RESEARCH-v1` family** based on actual repository evidence. Do not begin mass v2 expansion yet.

At the end of each new work unit, update status/state and the newest AGENTS handoff.
