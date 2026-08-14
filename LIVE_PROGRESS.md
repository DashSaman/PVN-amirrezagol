# PVNetwork — LIVE PROGRESS

> **این صفحه برای این است که مالک پروژه با یک نگاه بفهمد کار واقعاً در حال حرکت است یا نه.**  
> اعداد «پوشش تحقیق» با «تکمیل واقعی» عمداً جدا هستند تا درصد ساختگی یا گمراه‌کننده نمایش داده نشود.

## 🔴 وضعیت فعلی

- **Run status:** `IN_PROGRESS`
- **Active phase:** `COMPLETE-RESEARCH-v1`
- **Active work unit:** `XRAY-MODERN-PROXY-V1-CLOSURE`
- **Work-unit state:** `IN_PROGRESS`
- **خانواده فعال:** Xray / modern proxy ecosystem
- **آخرین handoff ثبت‌شده:** `AGENTS_HANDOFF_2026-08-14_XRAY_V1_1.md`

### آخرین کار واقعی که دیده شده

**`docs(research): map libXray API lifecycle and platform ownership`**

Commit: [`0a991e1548`](https://github.com/DashSaman/PVN-amirrezagol/commit/0a991e1548dfa322a247caa46a51bd6b0fdd39f2)  
Time: `2026-08-14T01:31:18Z`

> commitهای خود داشبورد با prefix `chore(progress):` در «آخرین کار واقعی» حساب نمی‌شوند.

---

## 📊 پیشرفت ۹۳ ورودی — قابل راستی‌آزمایی

| معیار | تعداد | درصد | نوار | معنی دقیق |
|---|---:|---:|---|---|
| **تکمیل سخت‌گیرانه V1** | **0 / 93** | **0.0%** | `░░░░░░░░░░░░░░░░░░░░` | فقط `COMPLETE-RESEARCH-v1`؛ تمام gateها باید evidence داشته باشند |
| **تحقیق عمیق شروع شده** | **36 / 93** | **38.7%** | `████████░░░░░░░░░░░░` | `IN-RESEARCH` + `EVIDENCE-GAPS` + complete |
| **Dossier/Skeleton یا بهتر ساخته شده** | **64 / 93** | **68.8%** | `██████████████░░░░░░` | `SKELETON` یا مرحله بالاتر؛ `PENDING/RESERVED` حساب نشده |
| **تکمیل سخت‌گیرانه V2** | **0 / 93** | **0.0%** | `░░░░░░░░░░░░░░░░░░░░` | فقط `COMPLETE-REFERENCE-v2` |

### توزیع فعلی V1

- `COMPLETE-RESEARCH-v1`: **0**
- `IN-RESEARCH`: **23**
- `EVIDENCE-GAPS`: **13**
- `SKELETON`: **28**
- `RESERVED`: **1**
- `PENDING`: **28**

> **نکته:** 68.8% به معنی 68.8% تکمیل پروژه نیست. این فقط می‌گوید برای 64 ورودی حداقل dossier/skeleton یا تحقیق جدی ایجاد شده است. معیار نهایی تکمیل همان `COMPLETE-*` است.

---

## 🚀 کارهای واقعی اخیر

1. [`0a991e1548`](https://github.com/DashSaman/PVN-amirrezagol/commit/0a991e1548dfa322a247caa46a51bd6b0fdd39f2) — `map libXray API lifecycle and platform ownership`
2. [`4019084691`](https://github.com/DashSaman/PVN-amirrezagol/commit/40190846916c89ad59d13194821a24fd8b32cdf2) — `map Xray runtime API and control ownership`
3. [`5890dcfe82`](https://github.com/DashSaman/PVN-amirrezagol/commit/5890dcfe8266bec99ae53ad56d3eebab8e6554c1) — `record Xray per-entry support and reuse decisions`
4. [`9cd861ce44`](https://github.com/DashSaman/PVN-amirrezagol/commit/9cd861ce44e659a912f1a40d8a9b737046fb927e) — `audit libXray cross-platform wrapper candidate`
5. [`23e8f43c3a`](https://github.com/DashSaman/PVN-amirrezagol/commit/23e8f43c3a9b16900c83d7eaea33c6e04a6bb5da) — `capture Xray and client issue regression lessons`
6. [`4c08b98287`](https://github.com/DashSaman/PVN-amirrezagol/commit/4c08b98287a95b3e8a42b7c9138a7575129564b1) — `map major Xray client ecosystem and reuse roles`
7. [`ad5135feed`](https://github.com/DashSaman/PVN-amirrezagol/commit/ad5135feed8c9cf5aeab3eb2e24ee84451850443) — `map Xray config and capability axes`
8. [`1083c4e9eb`](https://github.com/DashSaman/PVN-amirrezagol/commit/1083c4e9ebec66b8b2734da2ba7af00bd041f3b2) — `map Xray dependencies tests and release surface`
9. [`e200ad6c01`](https://github.com/DashSaman/PVN-amirrezagol/commit/e200ad6c01c190ff267ea7ef3182fecda60e11bc) — `map Xray core source architecture`

---

## 🎯 Exact next action

ادامه `XRAY-MODERN-PROXY-V1-CLOSURE` از handoff فعلی:

- deep-audit بیشتر libXray API/lifecycle/build/dependency/issues؛
- تکمیل capability/support-reuse برای entryهای Xray؛
- گسترش evidence مربوط به v2rayNG source/storage/VpnService/import؛
- security/dependency-advisory review؛
- commander/API/stats/runtime-control mapping؛
- sync کردن Xray INDEX و entryهای شماره‌دار؛
- checkpoint؛
- سپس بدون انتظار برای کاربر رفتن سراغ خانواده ناقص بعدی در V1؛
- WireGuard/AmneziaWG residual V1 قبل از هر ادعای تکمیل کلی باید دوباره بسته شود.

---

## 👀 چطور بفهمم واقعاً دارد کار می‌کند؟

اگر صفحه را refresh کردی، این موارد را نگاه کن:

1. **آخرین کار واقعی** و SHA آن عوض شده باشد؛
2. لیست **کارهای واقعی اخیر** commit جدید داشته باشد؛
3. `Active work unit` یا `Exact next action` جلو رفته باشد؛
4. تعداد `IN-RESEARCH` / `EVIDENCE-GAPS` تغییر کند و در نهایت `COMPLETE-RESEARCH-v1` بالا برود؛
5. پس از بسته شدن V1، عدد `COMPLETE-REFERENCE-v2` شروع به افزایش کند.

اگر فقط commitی با نام `chore(progress): ...` اضافه شود، آن **تحقیق واقعی محسوب نمی‌شود** و داشبورد آن را از latest meaningful work حذف می‌کند.

---

## 📂 لینک‌های سریع

- [Xray research folder](research/upstreams/xray-family/)
- [Current agent run state](docs/AGENT_RUN_STATE.json)
- [Agent checkpoint log](docs/AGENT_CHECKPOINT_LOG.md)
- [93-entry V1 tracker](research/RESEARCH_COMPLETENESS.md)
- [93-entry V2 tracker](research/REFERENCE_V2_COMPLETENESS.md)
- [Current Xray handoff](AGENTS_HANDOFF_2026-08-14_XRAY_V1_1.md)
- [Continuous execution contract](AGENT_EXECUTION_CONTRACT.md)

---

## 🤖 Auto-update

`scripts/update_live_progress.py` این صفحه را از state/tracker/Git history تولید می‌کند. GitHub Actions نیز برای refresh خودکار داشبورد تنظیم می‌شود؛ بنابراین نیازی نیست این درصدها دستی و از روی حدس تغییر کنند.
