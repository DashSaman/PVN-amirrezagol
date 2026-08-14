# PVNetwork Research Campaign Status — 2026-08-14 — OpenConnect Work Unit

Repository: `DashSaman/PVN-amirrezagol`

Phase: research / requirements / architecture. Nothing in this status file claims protocol implementation or production support.

## What was completed in this work unit

The shared OpenConnect/enterprise family was refreshed against current canonical upstream evidence and expanded from a general README into reusable research artifacts.

Committed shared files:

- `research/upstreams/openconnect-family/SOURCE_PIN.md`
- `research/upstreams/openconnect-family/VENDOR_COMPATIBILITY_MATRIX.md`
- `research/upstreams/openconnect-family/LESSONS_AND_TESTS.md`
- updated `research/upstreams/openconnect-family/README.md`

Stable research baseline recorded in the shared dossier: OpenConnect v9.21 from the canonical GitLab project. Current public API research records API version 5.10 and LGPL-2.1.

## Numbered dossiers synchronized

The following numbered entries were upgraded from minimal placeholders and linked to current shared evidence:

- 017 — OpenConnect / ocserv-compatible
- 018 — GlobalProtect
- 019 — Fortinet FortiGate SSL VPN
- 020 — Pulse Secure
- 021 — Ivanti Connect Secure
- 022 — Juniper Network Connect
- 023 — F5 BIG-IP SSL VPN
- 024 — Array Networks SSL VPN

Entry 016 — Cisco AnyConnect-compatible remains a connector-write documentation gap. A detailed update and then a materially shorter update were both rejected. Do not repeat the same README update again. Cisco-specific conclusions remain preserved in the shared vendor matrix.

## Main architectural conclusion

OpenConnect should be evaluated through its versioned public library API behind a PVNetwork Enterprise/Core Adapter. Product authentication UI, browser/SSO handoff, protected credentials, diagnostics, vendor-version certification and platform lifecycle should remain outside private OpenConnect internals.

## Vendor compatibility rule

Do not use one global “OpenConnect supported” or “vendor supported” boolean. The shared matrix keeps each enterprise family separate and requires evidence for capability dimensions such as authentication, browser/SSO, posture/host-check behavior, reconnect, platform behavior and exact tested server/software versions.

## Quality lessons captured

`LESSONS_AND_TESTS.md` converts current upstream release/MR evidence into PVNetwork regression categories, including:

- library upgrades exposing old bugs through newly used code paths;
- compatibility changing as vendor servers evolve;
- SSO non-progress/retry loops;
- invalid/empty SSO state;
- multiple browser/auth phases in one session;
- vendor behavior depending on client/platform identity;
- need for vendor-by-platform regression matrices.

## Connector blockers in this unit

- large `SOURCE_AND_API.md` write rejected;
- detailed `ADAPTER_API_MAP.md` write rejected;
- 016 Cisco README updates rejected twice with materially different sizes;
- a large rewrite of `docs/PROJECT_STATE.md` containing this work-unit detail was rejected.

Accepted evidence must be used instead of repeating blocked writes.

## Next exact action

1. Review current OpenConnect issues/MRs by vendor family and map high-impact items to fixed/current status.
2. Audit selected OpenConnect front ends for UI, browser/SSO integration, credential storage, logs and platform packaging.
3. Perform dependency/SBOM and LGPL distribution-architecture research for the exact selected OpenConnect integration.
4. Add protocol-specific conclusions only where connector writes are accepted; keep 016 as an explicit blocker otherwise.
5. At the end of the next work unit, update `docs/RESEARCH_LOG.md`, `docs/PROJECT_STATE.md` or a dated status snapshot, and `AGENTS.md`.

No numbered entry is promoted to `COMPLETE-RESEARCH-v1` by this work unit.