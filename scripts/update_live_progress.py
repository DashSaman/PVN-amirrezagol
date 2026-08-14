#!/usr/bin/env python3
"""Generate bilingual graphical PVNetwork live-progress dashboards from repository evidence."""

from __future__ import annotations

import html
import json
import re
import subprocess
from collections import Counter
from datetime import datetime, timezone
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
V1 = ROOT / "research" / "RESEARCH_COMPLETENESS.md"
V2 = ROOT / "research" / "REFERENCE_V2_COMPLETENESS.md"
STATE = ROOT / "docs" / "AGENT_RUN_STATE.json"
OUT_FA = ROOT / "LIVE_PROGRESS.md"
OUT_EN = ROOT / "LIVE_PROGRESS_EN.md"
ASSETS = ROOT / "assets"
SVG_FA = ASSETS / "live-progress-fa.svg"
SVG_EN = ASSETS / "live-progress-en.svg"
TOTAL = 93

ROW_RE = re.compile(r"^\|\s*(\d{3})\s*\|\s*(.*?)\s*\|\s*(.*?)\s*\|\s*$")
PROGRESS_ONLY_RE = re.compile(r"^(chore|ci|docs|fix)\(progress\):", re.IGNORECASE)
RESEARCH_RE = re.compile(r"^(docs|feat|fix)\((research|protocols?)\):", re.IGNORECASE)

FA_FOCUS = {
    "XRAY-MODERN-PROXY-V1-CLOSURE": "تکمیل پژوهش V1 اکوسیستم Xray / Modern Proxy",
    "OPENCONNECT-V1-CLOSURE": "تکمیل پژوهش V1 خانواده OpenConnect / Enterprise",
    "WIREGUARD-V1-CLOSURE": "تکمیل پژوهش V1 خانواده WireGuard / AmneziaWG",
}


def tracker(path: Path) -> dict[int, tuple[str, str]]:
    rows = {}
    for line in path.read_text(encoding="utf-8").splitlines():
        m = ROW_RE.match(line)
        if not m:
            continue
        raw = m.group(3).strip()
        state = raw.split(" — ", 1)[0].split(" (`", 1)[0].strip()
        rows[int(m.group(1))] = (m.group(2).strip(), state)
    return rows


def pct(n: int) -> float:
    return round(100.0 * n / TOTAL, 1)


def git_rows(limit: int = 10, research_only: bool = False):
    fmt = "%H%x1f%aI%x1f%s"
    proc = subprocess.run(
        ["git", "log", "-120", f"--pretty=format:{fmt}"],
        cwd=ROOT, check=False, capture_output=True, text=True
    )
    rows = []
    if proc.returncode != 0:
        return rows
    for line in proc.stdout.splitlines():
        parts = line.split("\x1f", 2)
        if len(parts) != 3:
            continue
        sha, iso, msg = parts
        if PROGRESS_ONLY_RE.match(msg):
            continue
        if research_only and not RESEARCH_RE.match(msg):
            continue
        rows.append((sha, iso, msg))
        if len(rows) >= limit:
            break
    return rows


def minutes_since(iso: str):
    try:
        dt = datetime.fromisoformat(iso.replace("Z", "+00:00")).astimezone(timezone.utc)
        return max(0, int((datetime.now(timezone.utc) - dt).total_seconds() // 60))
    except Exception:
        return None


def heartbeat(minutes):
    if minutes is None:
        return ("UNKNOWN", "نامشخص", "Unknown")
    if minutes <= 15:
        return ("RECENT", "فعالیت بسیار اخیر", "Very recent activity")
    if minutes <= 60:
        return ("QUIET", "آخرین فعالیت کمتر از یک ساعت پیش", "Last activity under an hour ago")
    return ("STALE", "مدتی commit تحقیقاتی جدید ثبت نشده", "No recent research commit observed")


def esc(s) -> str:
    return html.escape(str(s), quote=True)


def trunc(s: str, n: int = 92) -> str:
    s = " ".join(str(s).split())
    return s if len(s) <= n else s[: n - 1] + "…"


def progress_bar(x, y, w, value, accent):
    fill = round(w * value / 100.0, 1)
    return (
        f'<rect x="{x}" y="{y}" width="{w}" height="10" rx="5" fill="#213047"/>'
        f'<rect x="{x}" y="{y}" width="{fill}" height="10" rx="5" fill="{accent}"/>'
    )


def svg_dashboard(lang, state, counts, latest_research, recent_research):
    fa = lang == "fa"
    active = state.get("active_work_unit") or {}
    focus_id = active.get("id", "UNKNOWN")
    focus = FA_FOCUS.get(focus_id, focus_id) if fa else focus_id.replace("-", " ").title()
    phase = state.get("active_phase", "UNKNOWN")
    run_status = state.get("run_status", "UNKNOWN")
    work_state = active.get("status", "UNKNOWN")

    latest_sha, latest_iso, latest_msg = latest_research
    mins = minutes_since(latest_iso)
    hb_code, hb_fa, hb_en = heartbeat(mins)
    hb_text = hb_fa if fa else hb_en
    mins_text = ("—" if mins is None else (f"{mins} دقیقه پیش" if fa else f"{mins} min ago"))

    labels = {
        "title": "داشبورد زنده پژوهش PVNetwork" if fa else "PVNetwork Live Research Dashboard",
        "subtitle": "نمایش مبتنی بر شواهد ریپو — پوشش با تکمیل واقعی یکی نیست" if fa else "Repository-evidence view — coverage is not completion",
        "now": "در حال انجام" if fa else "NOW WORKING",
        "phase": "فاز فعال" if fa else "Active phase",
        "state": "وضعیت" if fa else "State",
        "heartbeat": "آخرین فعالیت واقعی" if fa else "RESEARCH HEARTBEAT",
        "last": "آخرین کار مشاهده‌شده" if fa else "Latest observed research action",
        "strict_v1": "تکمیل سخت‌گیرانه V1" if fa else "Strict V1 complete",
        "deep": "تحقیق عمیق شروع‌شده" if fa else "Deep research started",
        "materialized": "پرونده ساخته‌شده یا عمیق‌تر" if fa else "Dossier materialized+",
        "strict_v2": "تکمیل سخت‌گیرانه V2" if fa else "Strict V2 complete",
        "recent": "آخرین commitهای تحقیقاتی" if fa else "Recent research commits",
        "updated": "تولید خودکار هر ۵ دقیقه و پس از push" if fa else "Auto-generated every 5 minutes and after pushes",
    }

    metrics = [
        (labels["strict_v1"], counts["strict_v1"], "#8b5cf6"),
        (labels["deep"], counts["deep"], "#22c55e"),
        (labels["materialized"], counts["materialized"], "#38bdf8"),
        (labels["strict_v2"], counts["strict_v2"], "#f59e0b"),
    ]
    metric_svg = []
    for i, (label, value, accent) in enumerate(metrics):
        x = 58 + i * 280
        p = pct(value)
        metric_svg.append(
            f'<g><rect x="{x}" y="398" width="252" height="142" rx="18" fill="#111d2e" stroke="#23334b"/>'
            f'<text x="{x+18}" y="430" class="small muted">{esc(label)}</text>'
            f'<text x="{x+18}" y="475" class="metric">{p:.1f}%</text>'
            f'<text x="{x+205}" y="473" text-anchor="end" class="small">{value}/93</text>'
            f'{progress_bar(x+18, 496, 216, p, accent)}</g>'
        )

    recent_svg = []
    for i, (sha, iso, msg) in enumerate(recent_research[:3]):
        y = 606 + i * 38
        recent_svg.append(
            f'<circle cx="76" cy="{y-5}" r="5" fill="#22c55e"/>'
            f'<text x="94" y="{y}" class="small">{esc(trunc(msg, 88))}</text>'
            f'<text x="1084" y="{y}" text-anchor="end" class="tiny muted">{esc(sha[:8])}</text>'
        )

    rtl = ' direction="rtl"' if fa else ""
    latest_line = trunc(latest_msg, 100)

    return f'''<svg xmlns="http://www.w3.org/2000/svg" width="1200" height="760" viewBox="0 0 1200 760" role="img" aria-label="{esc(labels['title'])}">
<defs>
  <linearGradient id="bg" x1="0" y1="0" x2="1" y2="1"><stop stop-color="#07111f"/><stop offset="1" stop-color="#0c1728"/></linearGradient>
  <linearGradient id="hero" x1="0" y1="0" x2="1" y2="1"><stop stop-color="#13243b"/><stop offset="1" stop-color="#102033"/></linearGradient>
  <filter id="shadow"><feDropShadow dx="0" dy="8" stdDeviation="12" flood-color="#000" flood-opacity=".28"/></filter>
  <style>
    text {{ font-family: Inter, "Segoe UI", Tahoma, Arial, sans-serif; fill:#eef6ff; }}
    .title {{ font-size:30px; font-weight:700; }}
    .subtitle {{ font-size:14px; fill:#91a4bd; }}
    .eyebrow {{ font-size:13px; font-weight:700; letter-spacing:1.3px; fill:#5eead4; }}
    .focus {{ font-size:25px; font-weight:700; }}
    .metric {{ font-size:34px; font-weight:800; }}
    .small {{ font-size:14px; }}
    .tiny {{ font-size:12px; }}
    .muted {{ fill:#91a4bd; }}
    .mono {{ font-family: "SFMono-Regular", Consolas, monospace; }}
  </style>
</defs>
<rect width="1200" height="760" rx="28" fill="url(#bg)"/>
<circle cx="1040" cy="70" r="110" fill="#0ea5e9" opacity=".06"/>
<circle cx="1120" cy="165" r="70" fill="#22c55e" opacity=".05"/>

<text x="58" y="64" class="title"{rtl}>{esc(labels['title'])}</text>
<text x="58" y="91" class="subtitle"{rtl}>{esc(labels['subtitle'])}</text>
<rect x="975" y="42" width="167" height="40" rx="20" fill="#0f2b24" stroke="#1f6b55"/>
<circle cx="997" cy="62" r="6" fill="#22c55e"/>
<text x="1012" y="67" class="small" fill="#86efac">{esc(run_status)}</text>

<g filter="url(#shadow)">
  <rect x="58" y="126" width="720" height="220" rx="22" fill="url(#hero)" stroke="#263954"/>
  <text x="84" y="160" class="eyebrow"{rtl}>{esc(labels['now'])}</text>
  <text x="84" y="205" class="focus"{rtl}>{esc(trunc(focus, 64))}</text>
  <rect x="84" y="232" width="250" height="36" rx="18" fill="#0c2b3b"/>
  <text x="104" y="255" class="small"><tspan class="muted">{esc(labels['phase'])}: </tspan>{esc(phase)}</text>
  <rect x="348" y="232" width="210" height="36" rx="18" fill="#1e243a"/>
  <text x="368" y="255" class="small"><tspan class="muted">{esc(labels['state'])}: </tspan>{esc(work_state)}</text>
  <text x="84" y="300" class="small muted"{rtl}>{esc(labels['last'])}</text>
  <text x="84" y="326" class="small mono">{esc(latest_line)}</text>
</g>

<g filter="url(#shadow)">
  <rect x="804" y="126" width="338" height="220" rx="22" fill="#111d2e" stroke="#263954"/>
  <text x="830" y="160" class="eyebrow">{esc(labels['heartbeat'])}</text>
  <text x="830" y="207" class="metric">{esc(mins_text)}</text>
  <text x="830" y="236" class="small muted"{rtl}>{esc(hb_text)}</text>
  <rect x="830" y="262" width="120" height="34" rx="17" fill="#102b24"/>
  <text x="890" y="284" text-anchor="middle" class="small">{esc(hb_code)}</text>
  <text x="830" y="323" class="tiny muted">{esc(latest_iso)}</text>
</g>

{''.join(metric_svg)}

<text x="58" y="578" class="eyebrow"{rtl}>{esc(labels['recent'])}</text>
{''.join(recent_svg)}
<text x="1142" y="734" text-anchor="end" class="tiny muted"{rtl}>{esc(labels['updated'])}</text>
</svg>
'''


def language_switch(fa: bool):
    if fa:
        return '<p align="center"><strong>🇮🇷 فارسی</strong> &nbsp;•&nbsp; <a href="LIVE_PROGRESS_EN.md">🇬🇧 English</a></p>'
    return '<p align="center"><a href="LIVE_PROGRESS.md">🇮🇷 فارسی</a> &nbsp;•&nbsp; <strong>🇬🇧 English</strong></p>'


def md_page(lang, state, counts, latest_research, recent_research):
    fa = lang == "fa"
    active = state.get("active_work_unit") or {}
    latest_sha, latest_iso, latest_msg = latest_research
    mins = minutes_since(latest_iso)
    _, hb_fa, hb_en = heartbeat(mins)
    focus_id = active.get("id", "UNKNOWN")
    focus = FA_FOCUS.get(focus_id, focus_id) if fa else focus_id.replace("-", " ").title()
    next_action = active.get("exact_next_action", "No next action recorded.")
    svg = "assets/live-progress-fa.svg" if fa else "assets/live-progress-en.svg"

    if fa:
        lines = [
            "# PVNetwork — وضعیت زنده پروژه", "", language_switch(True), "",
            f'<p align="center"><img src="{svg}" width="100%" alt="PVNetwork Live Progress"></p>', "",
            "> **نکته:** «پوشش تحقیق» با «تکمیل سخت‌گیرانه» فرق دارد. فقط `COMPLETE-*` به معنی عبور از همه gateهای الزامی است.", "",
            "## 🔴 در این لحظه چه کاری در صف فعال است؟", "",
            f"- **فوکوس فعال:** `{focus}`", f"- **Work unit:** `{focus_id}`",
            f"- **فاز:** `{state.get('active_phase', 'UNKNOWN')}`", f"- **وضعیت:** `{active.get('status', 'UNKNOWN')}`", "",
            "### آخرین کار واقعی مشاهده‌شده", "",
            f"- [`{latest_sha[:10]}`](https://github.com/DashSaman/PVN-amirrezagol/commit/{latest_sha}) — **{latest_msg}**",
            f"- زمان commit: `{latest_iso}`",
            f"- Heartbeat: **{hb_fa}**" + (f" — حدود **{mins} دقیقه** قبل" if mins is not None else ""), "",
            "### اقدام بعدی ثبت‌شده در state", "", f"> {next_action}", "",
            "## 📊 درصدهای قابل اتکا", "", "| شاخص | مقدار | درصد |", "|---|---:|---:|",
            f"| تکمیل سخت‌گیرانه V1 | {counts['strict_v1']}/93 | **{pct(counts['strict_v1']):.1f}%** |",
            f"| تحقیق عمیق شروع‌شده | {counts['deep']}/93 | **{pct(counts['deep']):.1f}%** |",
            f"| dossier/skeleton یا بهتر | {counts['materialized']}/93 | **{pct(counts['materialized']):.1f}%** |",
            f"| تکمیل سخت‌گیرانه V2 | {counts['strict_v2']}/93 | **{pct(counts['strict_v2']):.1f}%** |", "",
            "## 🕒 آخرین commitهای تحقیقاتی", "",
        ]
    else:
        lines = [
            "# PVNetwork — Live Project Status", "", language_switch(False), "",
            f'<p align="center"><img src="{svg}" width="100%" alt="PVNetwork Live Progress"></p>', "",
            "> **Note:** research coverage is not strict completion. Only `COMPLETE-*` means all required gates have passed.", "",
            "## 🔴 What is active right now?", "",
            f"- **Current focus:** `{focus}`", f"- **Work unit:** `{focus_id}`",
            f"- **Phase:** `{state.get('active_phase', 'UNKNOWN')}`", f"- **State:** `{active.get('status', 'UNKNOWN')}`", "",
            "### Latest observed real research action", "",
            f"- [`{latest_sha[:10]}`](https://github.com/DashSaman/PVN-amirrezagol/commit/{latest_sha}) — **{latest_msg}**",
            f"- Commit time: `{latest_iso}`",
            f"- Heartbeat: **{hb_en}**" + (f" — about **{mins} min** ago" if mins is not None else ""), "",
            "### Exact next action recorded in state", "", f"> {next_action}", "",
            "## 📊 Evidence-based progress", "", "| Metric | Count | Percent |", "|---|---:|---:|",
            f"| Strict V1 complete | {counts['strict_v1']}/93 | **{pct(counts['strict_v1']):.1f}%** |",
            f"| Deep research started | {counts['deep']}/93 | **{pct(counts['deep']):.1f}%** |",
            f"| Dossier/skeleton or deeper | {counts['materialized']}/93 | **{pct(counts['materialized']):.1f}%** |",
            f"| Strict V2 complete | {counts['strict_v2']}/93 | **{pct(counts['strict_v2']):.1f}%** |", "",
            "## 🕒 Recent research commits", "",
        ]

    for sha, iso, msg in recent_research[:8]:
        lines.append(f"- `{iso}` — [`{sha[:10]}`](https://github.com/DashSaman/PVN-amirrezagol/commit/{sha}) — {msg}")

    if fa:
        lines += ["", "## ✅ از کجا بفهمم واقعاً دارد کار می‌کند؟", "",
                  "با Refresh صفحه این سه مورد را نگاه کن: **آخرین commit تحقیقاتی، زمان Heartbeat، و Work unit فعال**. Commitهای صرفاً مربوط به خود داشبورد از این لیست حذف می‌شوند تا پیشرفت مصنوعی نشان داده نشود.", "",
                  "منابع: [`AGENT_RUN_STATE`](docs/AGENT_RUN_STATE.json) · [`V1 tracker`](research/RESEARCH_COMPLETENESS.md) · [`V2 tracker`](research/REFERENCE_V2_COMPLETENESS.md) · [`Checkpoint log`](docs/AGENT_CHECKPOINT_LOG.md)", ""]
    else:
        lines += ["", "## ✅ How to verify work is really moving", "",
                  "Refresh this page and watch three things: **latest research commit, heartbeat time, and active work unit**. Dashboard-only commits are filtered out so they cannot fake progress.", "",
                  "Sources: [`AGENT_RUN_STATE`](docs/AGENT_RUN_STATE.json) · [`V1 tracker`](research/RESEARCH_COMPLETENESS.md) · [`V2 tracker`](research/REFERENCE_V2_COMPLETENESS.md) · [`Checkpoint log`](docs/AGENT_CHECKPOINT_LOG.md)", ""]
    return "\n".join(lines)


def main() -> int:
    v1 = tracker(V1)
    v2 = tracker(V2)
    state = json.loads(STATE.read_text(encoding="utf-8"))
    if len(v1) != TOTAL or len(v2) != TOTAL:
        raise SystemExit(f"tracker coverage error: v1={len(v1)}, v2={len(v2)}, expected={TOTAL}")

    c1 = Counter(s for _, s in v1.values())
    c2 = Counter(s for _, s in v2.values())
    counts = {
        "strict_v1": c1["COMPLETE-RESEARCH-v1"],
        "deep": c1["IN-RESEARCH"] + c1["EVIDENCE-GAPS"] + c1["COMPLETE-RESEARCH-v1"],
        "materialized": c1["IN-RESEARCH"] + c1["EVIDENCE-GAPS"] + c1["COMPLETE-RESEARCH-v1"] + c1["SKELETON"],
        "strict_v2": c2["COMPLETE-REFERENCE-v2"],
    }

    recent_research = git_rows(10, research_only=True)
    if recent_research:
        latest_research = recent_research[0]
    else:
        recent_any = git_rows(1, research_only=False)
        latest_research = recent_any[0] if recent_any else ("unknown", "unknown", "No git activity available")

    ASSETS.mkdir(parents=True, exist_ok=True)
    SVG_FA.write_text(svg_dashboard("fa", state, counts, latest_research, recent_research), encoding="utf-8")
    SVG_EN.write_text(svg_dashboard("en", state, counts, latest_research, recent_research), encoding="utf-8")
    OUT_FA.write_text(md_page("fa", state, counts, latest_research, recent_research), encoding="utf-8")
    OUT_EN.write_text(md_page("en", state, counts, latest_research, recent_research), encoding="utf-8")
    print("PASS: generated bilingual graphical live progress dashboard")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
