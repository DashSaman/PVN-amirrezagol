# AGENTS Handoff — 2026-08-14 — OpenConnect / Enterprise

This file is mandatory continuation context for the next AI/developer working on `DashSaman/PVN-amirrezagol`.

Phase remains research / requirements / architecture. No implementation or production-support claim is allowed from this research alone.

## Work completed

### Shared OpenConnect research

Committed:

- `research/upstreams/openconnect-family/SOURCE_PIN.md`
  - commit `b08043facdba9a962f0ea039457cab7666fde36b`
- `research/upstreams/openconnect-family/VENDOR_COMPATIBILITY_MATRIX.md`
  - commit `92295e89a535b9f9c28b8bd1695b77b5a13e5c43`
- `research/upstreams/openconnect-family/LESSONS_AND_TESTS.md`
  - commit `146dda12dbdf9df4be411565baf005f825908a4a`
- synchronized `research/upstreams/openconnect-family/README.md`
  - commit `0c67bf8f15bad6b9ef64a40802f9a7f735ff8c3b`

Stable research baseline recorded: OpenConnect v9.21 from canonical GitLab. Current public API research records API version 5.10 and LGPL-2.1.

### Numbered enterprise dossiers synchronized

Successfully upgraded from minimal placeholders and linked to shared evidence:

- 017 OpenConnect / ocserv — `328ce547f4c55cc7792390c2744f4ec5e6c465df`
- 018 GlobalProtect — `aa2eae3061bf0a1b1aaf456e695abb34f90aa029`
- 019 Fortinet — `2c58d91323e1af51d9104f4714529a2bcec8c49d`
- 020 Pulse — `00cdd89c8005b71458636bdc28b39c84b2884327`
- 021 Ivanti — `722928c4cd46a2df3e1cd4d25ceeedc58cb6270a`
- 022 Juniper Network Connect — `2d782a7ea550994e9bd25581f772d7e71000042b`
- 023 F5 BIG-IP — `69561ef3e8d8a94ea98a152eed94269acb233ba0`
- 024 Array Networks — `dd4ca59a4729ef1d34e4dcfced71c7631eee32d0`

### Entry 016 blocker

`016-cisco-anyconnect` remains a connector-write documentation blocker. A detailed README update was rejected, then a materially shorter update was also rejected. Do not retry the same README strategy again.

Cisco-specific conclusions remain preserved in:
- `research/upstreams/openconnect-family/VENDOR_COMPATIBILITY_MATRIX.md`
- `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_OPENCONNECT.md`
- current Project State / this handoff.

### Quality / regression research

Current official OpenConnect release and merge-request evidence was converted into PVNetwork regression categories in `LESSONS_AND_TESTS.md`.

Main lessons:
- core upgrades can expose old latent bugs through newly used code paths;
- vendor/server compatibility changes over time;
- browser/SSO flows need non-progress detection and bounded state handling;
- one enterprise session can require more than one browser/auth phase;
- client/platform identity can affect vendor behavior;
- test matrices must cross vendor family with platform, not only protocol name.

### Architecture conclusion

Use the versioned public OpenConnect library API behind a PVNetwork Enterprise/Core Adapter.

Keep these outside private OpenConnect internals:
- product authentication/challenge UI;
- browser/SSO handoff;
- protected credentials/certificates;
- product diagnostics/redaction;
- platform lifecycle/network integration;
- vendor/version compatibility certification.

### State files

- `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_OPENCONNECT.md`
  - commit `3f8e28c535c7e24625956369d019f9c09c64eed0`
- compact synchronized `docs/PROJECT_STATE.md`
  - commit `40e743de2e3ccf9f1f95038e87e2b9c8462f2e2d`

A larger Project State rewrite was rejected before the smaller snapshot strategy succeeded.

## Other connector blockers discovered in this work unit

- large `SOURCE_AND_API.md` write rejected; split strategy created `SOURCE_PIN.md` successfully;
- detailed `ADAPTER_API_MAP.md` write rejected;
- large direct `AGENTS.md` handoff update rejected; this dedicated AGENTS handoff file is the replacement strategy;
- full 93-entry tracker rewrite remains a known blocker from the previous work unit.

Do not repeat blocked writes unchanged.

## Next exact action

1. Review current OpenConnect issues/MRs by vendor and map high-impact items to current/fixed releases/commits.
2. Audit selected mature OpenConnect front ends for UI/menu structure, browser/SSO integration, credential storage, logs, packaging and platform behavior.
3. Audit dependencies/SBOM and the practical LGPL distribution architecture for the candidate OpenConnect library integration.
4. Keep entry 016 as a tracked write blocker unless a genuinely different safe documentation path appears.
5. Then choose the next highest-value incomplete family from actual repository tree + Project State + tracker.
6. Persist the next work unit in a new `AGENTS_HANDOFF_*.md`, update Project State/status, and update Research Log where connector permits.

## No false completion

No enterprise entry is implemented, production-tested, Store-approved or `COMPLETE-RESEARCH-v1` because of this work unit.