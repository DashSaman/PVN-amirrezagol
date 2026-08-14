# OpenConnect Family — Shared Enterprise Client Research

Related matrix entries: 016 Cisco AnyConnect-compatible, 017 OpenConnect/ocserv-compatible, 018 GlobalProtect, 020 Pulse Secure, 021 Ivanti Connect Secure, 022 Juniper Network Connect, 023 F5 BIG-IP, 024 Array Networks, and 019 Fortinet where current OpenConnect support applies.

Research state: `IN-RESEARCH`.

## Canonical source provenance
The GitHub repository `openconnect/openconnect` is archived and describes itself as a mirror. Its project homepage points to the active canonical project at:
`https://gitlab.com/openconnect/openconnect`

Current GitLab master observed during this research is active in 2026. The GitHub mirror remains useful as a historical machine-readable snapshot, not as the source of current release state.

## Current release evidence
Official GitLab releases show OpenConnect v9.21 released in 2026 after v9.20. The v9.21 notes include a fix for a CPU/infinite-loop text-buffer regression and compiler-warning fixes including Fortinet/Windows-related source. v9.20 restored/updated compatibility in several areas including current Cisco server behavior.

Release reference: `https://gitlab.com/openconnect/openconnect/-/releases`

## License
GitHub metadata and current upstream source headers identify OpenConnect as LGPL-2.1. Treat integration details separately from license compatibility of GUIs/front-ends and bundled dependencies.

## Language/source architecture
The project is primarily C. Current canonical tree contains platform/client source, an `android/` area, Java wrapper material, tests, translations (`po/`), documentation (`www/`), packaging/build integrations and protocol-specific source files.

Canonical tree reference:
`https://gitlab.com/openconnect/openconnect/-/tree/master`

Public API header reference:
`https://gitlab.com/openconnect/openconnect/-/blob/master/openconnect.h`

The public API is a strong reason to evaluate OpenConnect as a library/core integration candidate rather than duplicating protocol implementations in PVNetwork.

## Supported compatibility families — current official documentation
Official OpenConnect documentation currently describes support for:
- Cisco AnyConnect (original/default family)
- Juniper Network Connect
- Pulse/Ivanti Connect Secure
- Palo Alto GlobalProtect
- F5 BIG-IP
- Fortinet FortiGate
- Array Networks SSL VPN

Official manual/protocol reference:
`https://www.infradead.org/openconnect/manual.html`
`https://www.infradead.org/openconnect/protocols.html`

Support depth and authentication/posture capabilities differ by family. PVNetwork must therefore keep a per-vendor/version compatibility matrix rather than one global “OpenConnect supported” checkmark.

## Front-end/UI research principle
OpenConnect itself is primarily a library/CLI. For developer-level UX research, separately audit mature front-ends such as NetworkManager OpenConnect integration and platform-specific GUI clients. Do not treat command-line flags as a finished PVNetwork UX design.

## Important current failure/compatibility lessons
Current release history and official protocol pages already reveal several lessons:
- long-lived low-level bugs can surface after refactoring even in mature code, so PVNetwork needs regression tests around library upgrades;
- server compatibility can change when vendor behavior/user-agent expectations change;
- enterprise authentication flows differ widely and some modes remain experimental/partial;
- Fortinet reconnect behavior can depend heavily on server version/configuration;
- posture/host-check functionality may be absent or incomplete for some vendor families.

These lessons must become explicit PVNetwork capability states and future compatibility tests, not marketing assumptions.

## PVNetwork reuse direction
OpenConnect is a strong `REUSE-CANDIDATE` for compatible enterprise families because it exposes a library API and consolidates multiple vendor protocols. However:
- pin a tested release;
- audit LGPL linking/distribution obligations;
- build per-protocol compatibility tests;
- isolate browser/SSO/auth UI from the transport library;
- never claim full compatibility for a vendor family solely because OpenConnect has a protocol mode.

## Remaining research
- pin current canonical GitLab commit/tag and generate complete source-tree manifest;
- map `openconnect.h` API relevant to an adapter;
- map protocol source modules and front-end callbacks;
- audit GUI/front-end projects, menus and secure credential storage;
- map platform-specific source and packaging;
- review current issues/MRs by each protocol family;
- review mailing-list/forum guidance;
- inventory tests/fake servers/CI;
- audit assets/screenshots of selected front-ends;
- create separate vendor-specific conclusions in each numbered protocol folder.

Status: `IN-RESEARCH`; no PVNetwork implementation claim.