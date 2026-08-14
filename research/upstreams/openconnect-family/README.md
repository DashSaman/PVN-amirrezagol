# OpenConnect Family — Shared Enterprise Client Research

Related matrix entries: **016 Cisco AnyConnect-compatible**, **017 OpenConnect/ocserv-compatible**, **018 GlobalProtect**, **019 Fortinet FortiGate SSL VPN**, **020 Pulse Secure**, **021 Ivanti Connect Secure**, **022 Juniper Network Connect**, **023 F5 BIG-IP**, and **024 Array Networks**.

Research state: `IN-RESEARCH`; no PVNetwork implementation claim.

## Current shared evidence

- `SOURCE_PIN.md` — canonical GitLab source, stable v9.21 research baseline, current public API version evidence and license/source provenance.
- `VENDOR_COMPATIBILITY_MATRIX.md` — separate capability/limitation conclusions for entries 016–024.
- `LESSONS_AND_TESTS.md` — release/MR failure classes converted into PVNetwork regression requirements.

## Canonical source provenance

The old GitHub repository is archived and a mirror. Current release/source authority is the canonical OpenConnect GitLab project. Stable research baseline: **v9.21**. Current public API research shows API version **5.10**. License: LGPL-2.1.

## Architecture direction

OpenConnect is primarily a reusable C library/core plus CLI, not a finished PVNetwork product UI. Evaluate it through its public API behind a PVNetwork Enterprise/Core Adapter.

Keep these concerns outside private library internals:

- product authentication/challenge UI;
- browser/SSO handoff;
- protected credential/certificate storage;
- platform-specific service/extension lifecycle;
- product diagnostics and redaction;
- vendor/version compatibility certification.

## Compatibility rule

Do not translate “OpenConnect implements this protocol mode” into “PVNetwork fully supports this vendor”. Cisco, GlobalProtect, Fortinet, Pulse/Ivanti, Juniper, F5 and Array all require separate capability/version evidence.

The vendor matrix records distinctions such as basic auth, browser/SSO, posture/host-check, reconnect, IPv6 and known upstream limitations.

## Current protocol-folder synchronization

Individual protocol dossiers 017, 018, 019, 020, 021, 022, 023 and 024 have been linked to the current shared evidence. Entry 016 Cisco remains a connector-write documentation gap: repeated materially different README updates were rejected, so do not retry the same path blindly. The Cisco-specific conclusions remain preserved in `VENDOR_COMPATIBILITY_MATRIX.md`, Project State, Research Log and AGENTS handoff.

## Current release/quality lessons

Current upstream release/MR evidence demonstrates that:

- core upgrades can expose old bugs through newly used code paths;
- mature vendor compatibility still changes as servers evolve;
- SSO needs explicit progress/loop detection;
- a session may require multiple browser/auth phases;
- some vendor behavior depends on client/platform identity;
- protocol and platform regression dimensions must be tested together.

See `LESSONS_AND_TESTS.md`.

## Remaining research

- full machine-readable source manifest for the stable release;
- dependency/SBOM and LGPL distribution architecture;
- public API ownership/threading/callback mapping through a safe documentation path;
- selected GUI/front-end research, menus and secure credential storage;
- platform packaging/integration review;
- current issues/MRs mapped to merged fixes/releases per vendor;
- complete test/CI inventory and performance evidence;
- final numbered-entry support/reuse decisions.

Nothing in this dossier means PVNetwork currently implements or production-supports any enterprise protocol.