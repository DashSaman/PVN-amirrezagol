#!/usr/bin/env python3
"""Generate root LIVE_PROGRESS.md from repository state.

The output is deterministic for a given repository state. It intentionally
separates research coverage from strict completion so percentages cannot imply
false completion.
"""

from __future__ import annotations

import json
import re
import subprocess
from collections import Counter
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
V1 = ROOT / "research" / "RESEARCH_COMPLETENESS.md"
V2 = ROOT / "research" / "REFERENCE_V2_COMPLETENESS.md"
STATE = ROOT / "docs" / "AGENT_RUN_STATE.json"
OUT = ROOT / "LIVE_PROGRESS.md"
TOTAL = 93

ROW_RE = re.compile(r"^\|\s*(\d{3})\s*\|\s*(.*?)\s*\|\s*(.*?)\s*\|\s*$")


def tracker(path: Path) -> dict[int, tuple[str, str]]:
    rows: dict[int, tuple[str, str]] = {}
    for line in path.read_text(encoding="utf-8").splitlines():
        m = ROW_RE.match(line)
        if not m:
            continue
        n = int(m.group(1))
        name = m.group(2).strip()
        raw = m.group(3).strip()
        state = raw.split(" — ", 1)[0].split(" (`", 1)[0].strip()
        rows[n] = (name, state)
    return rows


def pct(n: int, d: int = TOTAL) -> str:
    return f"{(100.0 * n / d):.1f}%"


def bar(n: int, d: int = TOTAL, width: int = 20) -> str:
    filled = round(width * n / d) if d else 0
    return "█" * filled + "░" * (width - filled)


def git_lines(limit: int = 10) -> list[tuple[str, str, str]]:
    fmt = "%H%x1f%aI%x1f%s"
    proc = subprocess.run(
        ["git", "log", f"-{max(limit * 4, 40)}", f"--pretty=format:{fmt}"],
        cwd=ROOT,
        check=False,
        capture_output=True,
        text=True,
    )
    rows = []
    if proc.returncode != 0:
        return rows
    for line in proc.stdout.splitlines():
        parts = line.split("\x1f", 2)
        if len(parts) != 3:
            continue
        sha, iso, msg = parts
        if msg.startswith("chore(progress):"):
            continue
        rows.append((sha, iso, msg))
        if len(rows) >= limit:
            break
    return rows


def main() -> int:
    v1 = tracker(V1)
    v2 = tracker(V2)
    state = json.loads(STATE.read_text(encoding="utf-8"))

    if len(v1) != TOTAL or len(v2) != TOTAL:
        raise SystemExit(f"tracker coverage error: v1={len(v1)}, v2={len(v2)}, expected={TOTAL}")

    c1 = Counter(s for _, s in v1.values())
    c2 = Counter(s for _, s in v2.values())

    strict_v1 = c1["COMPLETE-RESEARCH-v1"]
    deep = c1["IN-RESEARCH"] + c1["EVIDENCE-GAPS"] + strict_v1
    materialized = deep + c1["SKELETON"]
    strict_v2 = c2["COMPLETE-REFERENCE-v2"]

    active = state.get("active_work_unit") or {}
    recent = git_lines(10)
    latest = recent[0] if recent else ("unknown", "unknown", "git history unavailable")

    lines = [
        "# PVNetwork — LIVE PROGRESS",
        "",
        "> This page is generated from repository evidence. **Coverage is not completion.**",
        "> `COMPLETE-*` percentages are strict gates; the other percentages only show how much of the 93-entry research scope has been opened/materialized.",
        "",
        "## 🔴 Current execution status",
        "",
        f"- **Run status:** `{state.get('run_status', 'UNKNOWN')}`",
        f"- **Active phase:** `{state.get('active_phase', 'UNKNOWN')}`",
        f"- **Active work unit:** `{active.get('id', 'UNKNOWN')}`",
        f"- **Work-unit state:** `{active.get('status', 'UNKNOWN')}`",
        f"- **Latest meaningful commit:** [`{latest[0][:10]}`](https://github.com/DashSaman/PVN-amirrezagol/commit/{latest[0]}) — {latest[2]}",
        f"- **Latest meaningful commit time:** `{latest[1]}`",
        "",
        "## 📊 93-entry progress",
        "",
        "| Metric | Count | Percent | Visual | Meaning |",
        "|---|---:|---:|---|---|",
        f"| **Strict V1 complete** | {strict_v1}/93 | **{pct(strict_v1)}** | `{bar(strict_v1)}` | All V1 completion gates passed with evidence |",
        f"| **Deep research started** | {deep}/93 | **{pct(deep)}** | `{bar(deep)}` | `IN-RESEARCH` + `EVIDENCE-GAPS` + completed |",
        f"| **Dossier materialized or deeper** | {materialized}/93 | **{pct(materialized)}** | `{bar(materialized)}` | `SKELETON` or stronger; excludes pending/reserved |",
        f"| **Strict V2 complete** | {strict_v2}/93 | **{pct(strict_v2)}** | `{bar(strict_v2)}` | Full second-layer reference gate passed |",
        "",
        "### V1 state distribution",
        "",
        f"- `COMPLETE-RESEARCH-v1`: **{c1['COMPLETE-RESEARCH-v1']}**",
        f"- `IN-RESEARCH`: **{c1['IN-RESEARCH']}**",
        f"- `EVIDENCE-GAPS`: **{c1['EVIDENCE-GAPS']}**",
        f"- `SKELETON`: **{c1['SKELETON']}**",
        f"- `RESERVED`: **{c1['RESERVED']}**",
        f"- `PENDING`: **{c1['PENDING']}**",
        "",
        "## 🎯 Exact next action",
        "",
        active.get("exact_next_action", "No active next action recorded."),
        "",
        "## 🧾 Latest checkpoint",
        "",
        f"- **Date:** `{(state.get('last_checkpoint') or {}).get('date', 'unknown')}`",
        f"- **Handoff:** `{(state.get('last_checkpoint') or {}).get('last_known_repo_handoff', 'unknown')}`",
        f"- **Summary:** {(state.get('last_checkpoint') or {}).get('summary', 'No checkpoint summary.')}",
        "",
        "## 🕒 Recent meaningful commits",
        "",
    ]

    for sha, iso, msg in recent:
        lines.append(f"- `{iso}` — [`{sha[:10]}`](https://github.com/DashSaman/PVN-amirrezagol/commit/{sha}) — {msg}")

    lines += [
        "",
        "## ✅ How to tell whether work is actually moving",
        "",
        "Work is moving when at least one of these changes over time:",
        "",
        "1. the **Latest meaningful commit** changes;",
        "2. new commits appear in **Recent meaningful commits**;",
        "3. the **Active work unit / Exact next action** advances;",
        "4. `IN-RESEARCH` / `EVIDENCE-GAPS` entries advance and eventually become `COMPLETE-RESEARCH-v1`;",
        "5. after V1 is complete, `COMPLETE-REFERENCE-v2` starts increasing.",
        "",
        "If only this dashboard's own `chore(progress):` commit changes, that does **not** count as research progress; those self-update commits are deliberately excluded above.",
        "",
        "## Source-of-truth files",
        "",
        "- [`docs/AGENT_RUN_STATE.json`](docs/AGENT_RUN_STATE.json)",
        "- [`docs/AGENT_CHECKPOINT_LOG.md`](docs/AGENT_CHECKPOINT_LOG.md)",
        "- [`research/RESEARCH_COMPLETENESS.md`](research/RESEARCH_COMPLETENESS.md)",
        "- [`research/REFERENCE_V2_COMPLETENESS.md`](research/REFERENCE_V2_COMPLETENESS.md)",
        "- [`AGENTS.md`](AGENTS.md)",
        "",
    ]

    OUT.write_text("\n".join(lines), encoding="utf-8")
    print(f"PASS: generated {OUT}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
