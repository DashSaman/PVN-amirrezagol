# PVNetwork Automation Run Log

Durable ledger for **actual ChatGPT scheduled continuation runs**. This is separate from GitHub Actions watchdog/dashboard runs.

## Contract

- Every scheduled ChatGPT continuation writes a `RUN_START` record **before** long research.
- A normal slice writes `RUN_END` with `status=COMPLETED_SLICE` after persisting real work.
- If a later scheduled run sees a prior `RUN_START` without a matching `RUN_END`, it records that prior slice as `INTERRUPTED_INFERRED` and resumes from repository state.
- Manual resume requests are linked by request id when consumed.
- Dashboard code may count records in this file; therefore keep the record syntax stable.

## Records

<!-- Append machine-readable single-line records below. Example:
RUN_START | ts=2026-08-14T02:00:00Z | trigger=scheduled | work_unit=XRAY-MODERN-PROXY-V1-CLOSURE | resume_from=<sha> | manual_request=none
RUN_END | ts=2026-08-14T02:47:00Z | status=COMPLETED_SLICE | last_research_commit=<sha>
INTERRUPTION | ts=2026-08-14T03:00:00Z | status=INTERRUPTED_INFERRED | prior_start=2026-08-14T02:00:00Z
-->
RUN_START | ts=2026-08-14T02:00:57Z | trigger=scheduled | work_unit=WIREGUARD-AMNEZIAWG-V1-CLOSURE | handoff=AGENTS_HANDOFF_2026-08-14_XRAY_V1_2.md | resume_from=252ff882b62caa9bc0ad923f67953316d40c9e1e | manual_request=none
