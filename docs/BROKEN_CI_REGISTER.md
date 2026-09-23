# Broken / Red CI Register

Last updated: 2026-09-23 (by the continuation agent during phase-A/state
recovery + public-app Tasks 1–3 execution).

Purpose: track **pre-existing** failing CI evidence states discovered on
`main`, so no agent mistakes them for new regressions and no completion
claim rides on top of them.

## 1. m0-foundation-ci.yml — `actions/setup-java` pin typo — FIXED 2026-09-23

- Symptom: both jobs failed at `Set up job` with
  `Unable to resolve action actions/setup-java@b6effb05e454b25005698d916606bdc6ffcbf96a`.
- Root cause: the workflow pinned `...6ffcbf96a` while every other workflow
  pins `...6ffcbf961` (last character typo, pre-existing). The file had not
  been triggered since the typo was introduced, so it stayed hidden until
  the 2026-09-23 commit touched it.
- Fix: commit `e74a87f` corrects the SHA.

## 2. Xray REALITY interop test — RED since 2026-08-26 — OPEN

- Workflow: `m2-xray-adapter-ci.yml`, job
  `Real Xray modern-protocol JVM data paths`.
- Failing test:
  `RealityJvmHostXrayRealBinaryInteropTest.realVlessVisionRealityDataPath`
  (assertion at line ~174: `REALITY target connection was not established`).
- History: last workflow success = run 24 (2026-08-17, commit `adc00ca`).
  The REALITY test was added by commit `3db3951` (2026-08-26) and every run
  since (27–30) failed. The RAW VLESS interop test in the same job passes.
- Implication: **VLESS + REALITY + Vision is NOT interoperability-verified**
  despite the test existing. `docs/PROJECT_STATE.md` only ever claimed the
  VLESS RAW/no-TLS path, which remains green.
- Suspected area (unproven): REALITY handshake/decoy behavior between the
  generated server config and the pinned Xray v26.7.28 fixture, or a
  timing/await issue in the harness. Needs a dedicated debugging session
  with the fixture reproduced locally on Linux.

## 3. Mihomo TUIC v5 interop test — RED since 2026-08-26 — OPEN

- Workflow: `m3-mihomo-adapter-ci.yml`, job
  `Exact Mihomo v1.19.30 / sing-box v1.13.19 real data path`.
- Failing test: `JvmHostMihomoRealBinaryInteropTest.realTuicV5TlsDataPath`
  (assertion at line ~197). Hysteria2 and AnyTLS data-path tests in the
  same suite pass.
- History: last success = run 3 (2026-08-26 16:23, commit `9a6a0de`);
  runs 4–12 failed. Contract jobs remain green.
- Implication: TUIC v5 TLS real-data-path is not verified; Hysteria2 and
  AnyTLS interop states stand on their own evidence.

## Rules

- Do not mark Xray REALITY or Mihomo TUIC as supported/verified until the
  above are green with a documented fix.
- Do not delete or weaken the failing tests to fake green.
- When fixing, capture the root cause in this register.
