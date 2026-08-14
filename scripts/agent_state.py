#!/usr/bin/env python3
"""PVNetwork deterministic AI-agent backlog and completion validator.

This script deliberately derives work from repository contracts instead of chat
memory. It does not perform research itself; it makes the full 93-entry work
queue explicit and prevents false overall-completion claims.
"""

from __future__ import annotations

import argparse
import json
import re
import sys
from datetime import datetime, timezone
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
MATRIX = ROOT / "docs" / "PROTOCOL_MATRIX.md"
V1_TRACKER = ROOT / "research" / "RESEARCH_COMPLETENESS.md"
V2_TRACKER = ROOT / "research" / "REFERENCE_V2_COMPLETENESS.md"
RUN_STATE = ROOT / "docs" / "AGENT_RUN_STATE.json"
BACKLOG_OUT = ROOT / "docs" / "AGENT_BACKLOG.generated.json"
EXPECTED_ENTRIES = 93

V1_GATES = [
    ("top-clients", "Top clients identified and justified"),
    ("canonical-sources", "Canonical sources pinned"),
    ("licenses", "Licenses reviewed"),
    ("source-tree", "Complete source-tree reference/manifest captured"),
    ("languages-build", "Languages/build systems mapped"),
    ("architecture", "Architecture mapped"),
    ("core-engine", "Core/engine integration mapped"),
    ("ui-menu", "UI/menu map completed"),
    ("config-import-export", "Config/import/export mapped"),
    ("persistence-secrets", "Persistence/secrets mapped"),
    ("platform-integration", "Platform integrations mapped"),
    ("logs-diagnostics", "Logs/diagnostics mapped"),
    ("assets", "Asset/screenshot references mapped"),
    ("forks", "Meaningful forks reviewed"),
    ("issues-releases-advisories", "Important issues/PRs/releases/advisories reviewed"),
    ("forums-docs", "Relevant forums/docs reviewed"),
    ("tests-ci", "Tests/CI reviewed"),
    ("store-privacy-security", "Store/privacy/security implications reviewed"),
    ("reuse-decision", "PVNetwork reuse decision documented"),
    ("uncertainties", "Uncertainties explicitly listed"),
]

V2_GATES = [
    ("server-ecosystem", "Server implementation/project ecosystem mapped"),
    ("server-installers", "Official and major community installer/deployment projects reviewed"),
    ("server-install-matrix", "Server OS/container/orchestration install matrix completed"),
    ("server-ui-menus", "Server panel/UI/menu maps completed"),
    ("client-install-matrix", "Client install matrix completed across relevant OS targets"),
    ("client-ui-menus", "Major client UI/menu maps completed separately"),
    ("cryptography", "Cryptographic design documented from authoritative specifications/source"),
    ("data-path-wire-flow", "Data path/wire flow documented"),
    ("ports-transports-handshake", "Ports/transports/handshake documented"),
    ("deployment-topologies", "Deployment topologies documented"),
    ("source-license-activity", "Source/license/activity pins recorded for server and client projects"),
    ("installer-supply-chain", "Security/supply-chain risks of installer projects recorded"),
    ("upgrade-rollback", "Upgrade/uninstall/rollback behavior researched"),
    ("differences-uncertainties", "Protocol/server/client differences and uncertainties explicitly listed"),
    ("reference-index", "REFERENCE_INDEX.md links the complete dossier"),
    ("handoff", "Latest AGENTS handoff contains the exact continuation state"),
]

MATRIX_RE = re.compile(r"^\s*(\d+)\.\s+(.+?)\s+—\s+(.+?)\s+—\s+(RESEARCH|LEGACY)\s*$")
TRACKER_RE = re.compile(r"^\|\s*(\d{3})\s*\|\s*(.*?)\s*\|\s*(.*?)\s*\|\s*$")


def read_text(path: Path) -> str:
    if not path.exists():
        raise FileNotFoundError(path)
    return path.read_text(encoding="utf-8")


def parse_matrix() -> list[dict]:
    entries = []
    for line in read_text(MATRIX).splitlines():
        m = MATRIX_RE.match(line)
        if not m:
            continue
        entries.append(
            {
                "number": int(m.group(1)),
                "name": m.group(2).strip(),
                "classification": m.group(3).strip(),
                "matrix_state": m.group(4),
            }
        )
    return entries


def parse_tracker(path: Path) -> dict[int, str]:
    states: dict[int, str] = {}
    if not path.exists():
        return states
    for line in read_text(path).splitlines():
        m = TRACKER_RE.match(line)
        if not m:
            continue
        n = int(m.group(1))
        raw = m.group(3).strip()
        # Keep the formal state token only; explanatory text may follow.
        state = raw.split(" — ", 1)[0].split(" (`", 1)[0].strip()
        states[n] = state
    return states


def load_run_state() -> dict:
    return json.loads(read_text(RUN_STATE))


def build_backlog() -> dict:
    matrix = parse_matrix()
    v1 = parse_tracker(V1_TRACKER)
    v2 = parse_tracker(V2_TRACKER)
    run_state = load_run_state()

    if len(matrix) != EXPECTED_ENTRIES:
        raise RuntimeError(f"Expected {EXPECTED_ENTRIES} matrix entries, found {len(matrix)}")

    items = []
    for entry in matrix:
        n = entry["number"]
        v1_state = v1.get(n, "MISSING")
        v2_state = v2.get(n, "PENDING")
        v1_complete = v1_state == "COMPLETE-RESEARCH-v1"
        v2_complete = v2_state == "COMPLETE-REFERENCE-v2"
        items.append(
            {
                **entry,
                "v1_state": v1_state,
                "v1_gates": [
                    {
                        "id": f"{n:03d}:v1:{slug}",
                        "title": title,
                        "status": "PASS" if v1_complete else "REQUIRES_EVIDENCE_CHECK",
                    }
                    for slug, title in V1_GATES
                ],
                "v2_state": v2_state,
                "v2_gates": [
                    {
                        "id": f"{n:03d}:v2:{slug}",
                        "title": title,
                        "status": "PASS" if v2_complete else "PENDING_AFTER_V1" if not v1_complete else "REQUIRES_EVIDENCE_CHECK",
                    }
                    for slug, title in V2_GATES
                ],
            }
        )

    return {
        "generated_at_utc": datetime.now(timezone.utc).isoformat(),
        "repository": run_state.get("repository"),
        "execution_mode": run_state.get("execution_mode"),
        "entry_count": len(items),
        "v1_gate_count_per_entry": len(V1_GATES),
        "v2_gate_count_per_entry": len(V2_GATES),
        "minimum_gate_slots": len(items) * (len(V1_GATES) + len(V2_GATES)),
        "priority_entries": run_state.get("priority_entries", []),
        "active_work_unit": run_state.get("active_work_unit"),
        "entries": items,
    }


def choose_next(backlog: dict) -> dict | None:
    by_num = {e["number"]: e for e in backlog["entries"]}
    priority = backlog.get("priority_entries", [])
    ordered = []
    seen = set()
    for n in priority + sorted(by_num):
        if n in by_num and n not in seen:
            ordered.append(by_num[n])
            seen.add(n)

    # The active handoff/work unit has priority over generated generic gates.
    active = backlog.get("active_work_unit") or {}
    if active.get("status") == "IN_PROGRESS":
        return {
            "kind": "active-work-unit",
            "id": active.get("id"),
            "source_handoff": active.get("source_handoff"),
            "next_action": active.get("exact_next_action"),
        }

    # Finish all original v1 research before mass v2 expansion.
    for e in ordered:
        if e["v1_state"] != "COMPLETE-RESEARCH-v1":
            return {
                "kind": "v1-entry",
                "entry": e["number"],
                "name": e["name"],
                "tracker_state": e["v1_state"],
                "contract": "research/PROTOCOL_RESEARCH_TEMPLATE.md",
                "next_action": "Inspect dossier/shared evidence, close the highest-value unsatisfied v1 gate, persist evidence, checkpoint, and continue.",
            }

    for e in ordered:
        if e["v2_state"] != "COMPLETE-REFERENCE-v2":
            return {
                "kind": "v2-entry",
                "entry": e["number"],
                "name": e["name"],
                "tracker_state": e["v2_state"],
                "contract": "research/FULL_PROTOCOL_REFERENCE_CONTRACT.md",
                "next_action": "Close the next applicable v2 reference gate with evidence, persist it, checkpoint, and continue.",
            }
    return None


def verify(require_complete: bool) -> tuple[bool, list[str]]:
    problems: list[str] = []
    matrix = parse_matrix()
    v1 = parse_tracker(V1_TRACKER)
    v2 = parse_tracker(V2_TRACKER)
    state = load_run_state()

    if len(matrix) != EXPECTED_ENTRIES:
        problems.append(f"matrix count is {len(matrix)}, expected {EXPECTED_ENTRIES}")
    expected_numbers = set(range(1, EXPECTED_ENTRIES + 1))
    if {e["number"] for e in matrix} != expected_numbers:
        problems.append("matrix numbering is not exactly 1..93")
    if set(v1) != expected_numbers:
        missing = sorted(expected_numbers - set(v1))
        extra = sorted(set(v1) - expected_numbers)
        problems.append(f"v1 tracker coverage mismatch; missing={missing}, extra={extra}")
    if set(v2) != expected_numbers:
        missing = sorted(expected_numbers - set(v2))
        extra = sorted(set(v2) - expected_numbers)
        problems.append(f"v2 tracker coverage mismatch; missing={missing}, extra={extra}")

    if require_complete:
        not_v1 = [n for n in sorted(expected_numbers) if v1.get(n) != "COMPLETE-RESEARCH-v1"]
        not_v2 = [n for n in sorted(expected_numbers) if v2.get(n) != "COMPLETE-REFERENCE-v2"]
        if not_v1:
            problems.append(f"v1 incomplete entries: {not_v1}")
        if not_v2:
            problems.append(f"v2 incomplete entries: {not_v2}")
        if state.get("run_status") != "COMPLETE":
            problems.append(f"run_status is {state.get('run_status')!r}, expected 'COMPLETE'")
        active = state.get("active_work_unit") or {}
        if active.get("status") in {"IN_PROGRESS", "PENDING", "FAILED_RETRYABLE"}:
            problems.append(f"active_work_unit remains nonterminal: {active.get('id')} / {active.get('status')}")

    return (not problems), problems


def ensure_v2_tracker() -> None:
    if V2_TRACKER.exists():
        return
    matrix = parse_matrix()
    lines = [
        "# PVNetwork — COMPLETE-REFERENCE-v2 Tracker",
        "",
        "This tracker is for the second exhaustive reference layer defined by `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`.",
        "It does not imply implementation or production support.",
        "",
        "| # | Entry | Current reference state |",
        "|---:|---|---|",
    ]
    for e in matrix:
        lines.append(f"| {e['number']:03d} | {e['name']} | PENDING |")
    lines += [
        "",
        "An entry may change to `COMPLETE-REFERENCE-v2` only after every applicable v2 gate has evidence and the original `COMPLETE-RESEARCH-v1` gate has already passed.",
    ]
    V2_TRACKER.write_text("\n".join(lines) + "\n", encoding="utf-8")


def cmd_build(_: argparse.Namespace) -> int:
    ensure_v2_tracker()
    data = build_backlog()
    BACKLOG_OUT.write_text(json.dumps(data, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    print(f"PASS: wrote {BACKLOG_OUT.relative_to(ROOT)} with {data['entry_count']} entries and at least {data['minimum_gate_slots']} v1+v2 gate slots")
    return 0


def cmd_next(_: argparse.Namespace) -> int:
    ensure_v2_tracker()
    nxt = choose_next(build_backlog())
    if nxt is None:
        print("NO-NEXT-TASK: all tracked v1 and v2 entry states are complete; run strict verification before claiming completion")
    else:
        print(json.dumps(nxt, indent=2, ensure_ascii=False))
    return 0


def cmd_verify(args: argparse.Namespace) -> int:
    ensure_v2_tracker()
    ok, problems = verify(args.require_complete)
    if ok:
        print("PASS: agent state/tracker structure is consistent" + (" and strict completion gates pass" if args.require_complete else ""))
        return 0
    print("FAIL: agent state verification failed", file=sys.stderr)
    for p in problems:
        print(f"- {p}", file=sys.stderr)
    return 2


def main() -> int:
    parser = argparse.ArgumentParser()
    sub = parser.add_subparsers(dest="cmd", required=True)
    p_build = sub.add_parser("build", help="Generate the deterministic full campaign backlog")
    p_build.set_defaults(func=cmd_build)
    p_next = sub.add_parser("next", help="Print the next required work item")
    p_next.set_defaults(func=cmd_next)
    p_verify = sub.add_parser("verify", help="Validate campaign state and optionally forbid premature completion")
    p_verify.add_argument("--require-complete", action="store_true")
    p_verify.set_defaults(func=cmd_verify)
    args = parser.parse_args()
    return args.func(args)


if __name__ == "__main__":
    raise SystemExit(main())
