# WireGuard Family — Upstream Lessons and PVNetwork Regression Requirements

Research date: 2026-08-14

Status: `IN-RESEARCH`. This file records failure classes from official source/mailing-list/current source evidence and converts them into PVNetwork quality requirements. It is not an operational networking guide.

## Evidence sources reviewed

Primary upstream evidence includes official WireGuard source mirrors, WireGuard mailing-list archive, and current AmneziaWG platform/core source/commit history.

Relevant themes include:

- Android VPN authorization conflicts when another application is configured as Always-On;
- Android tunnel recovery/state problems after reboot;
- Quick Settings/UI/service state synchronization;
- endpoint/name-resolution behavior across changing networks/address families;
- Windows service/startup behavior when network/DNS readiness is delayed;
- Windows roaming/sleep/network transitions;
- Apple NetworkExtension lifecycle/workaround/release risks;
- current AmneziaWG route and AWG3.1 packet-layout regressions.

Historical reports are failure classes, not claims that every old bug remains present.

## 1. Android VPN ownership / Always-On conflict

### Lesson
Android OS-level VPN ownership/authorization can block an otherwise valid profile.

### PVNetwork requirements

- classify OS permission/ownership separately from engine failure;
- explain clearly without blaming the profile;
- do not enter blind reconnect loops;
- recover after permission/Always-On state changes without destructive profile recreation.

## 2. Reboot and restore-state reliability

### Lesson
“Configured as Always-On” and “functional after reboot” are distinct states.

### PVNetwork tests

- reboot with active/remembered profile;
- OS starts tunnel before UI process;
- storage available while network/DNS is not yet ready;
- UI launches after OS-created tunnel;
- repeated reboot cycles without stale/duplicate session state.

## 3. UI / Quick Tile / external-state synchronization

### Lesson
Tunnel state changed from another control surface can leave UI stale.

### Architecture rule

Tray, Quick Settings, main UI, background service and automation interfaces must subscribe to one authoritative session state.

### Tests

- toggle from Quick Settings while UI closed;
- open UI during transition;
- service/system state change -> UI convergence;
- UI process kill/recreate while session remains active;
- rapid repeated commands remain deterministic.

## 4. Network readiness and name-resolution timing

### Lesson
Startup can occur before DNS/network readiness, particularly at boot/resume.

### Requirements

- distinguish transient network-not-ready from permanent profile error;
- state-aware retry/backoff;
- preserve cancellation/user disconnect;
- diagnose resolution vs reachability vs permission vs engine startup;
- verify delayed network recovery.

## 5. Roaming, sleep/resume and endpoint/address-family changes

### Tests

- Wi-Fi -> cellular -> Wi-Fi;
- IPv4-only -> dual-stack -> IPv6-only where available;
- sleep on one network/resume on another;
- endpoint DNS answer changes;
- interface loss/recreation;
- repeated transitions without profile corruption/UI drift.

## 6. Route/helper assumptions

### Lesson
Convenience route inclusion/exclusion helpers can encode assumptions that later become wrong.

### Requirement

Routing helpers are versioned policy generators with tests, not magic checkboxes. Effective generated routing policy should be inspectable in diagnostics.

## 7. Apple NetworkExtension and Store-release risk

### Lesson
Platform-specific route/NetworkExtension workarounds and Store release delays can make regressions operationally expensive.

### Requirements

- isolate/comment/test platform workarounds;
- trace historical reason before deleting a strange workaround;
- staged release/rollback;
- backward-compatible server/control-plane changes where practical;
- test upgrades with existing NetworkExtension configuration;
- distinguish “code fixed” from “Store build available”.

## 8. Current AmneziaWG Apple route regression class

Current `amnezia-vpn/amneziawg-apple` head `e5410a539f28b8ce5dd1d060c45e4fa555e9a210` fixes a route behavior where applying an excluded route for the tunnel network could cause a Linux server peer to reset connections.

### PVNetwork tests

- Apple included/excluded route generation against a real peer;
- peer reachability before/after route application;
- reconnect after route changes;
- network handover while excluded routes exist;
- server implementation variation;
- route cleanup on extension stop/crash.

### Product lesson

A route optimization that looks locally correct can break actual peer connectivity. Platform route tests need real traffic/peer evidence, not only route-table assertions.

## 9. AWG3.1 `RandomTrailers` HandshakeCookie panic class

Current `amneziawg-go` head `1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1` fixes a runtime panic introduced by AWG3.1-related random trailer handling for `HandshakeCookie` messages. The source fix corrects allocation length when the trailer is appended.

### PVNetwork implications

This is a direct example that a new obfuscation/config feature can parse correctly yet fail on a less-common handshake message path.

### AWG regression tests

- `RandomTrailers=off` baseline;
- `RandomTrailers=on` normal handshake/data path;
- cookie-reply path;
- repeated handshakes/rekeys;
- packet-loss/retry paths that exercise cookie behavior;
- minimum/maximum/invalid trailer-related settings;
- no panic/process crash for malformed/edge config;
- exact AWG3.1 server/client version matrix;
- downgrade/old-client handling of new fields.

### Release rule

Do not approve a brand-new AWG generation solely because the current client UI exposes its fields. Require current core fix level plus cross-platform tests.

## 10. AWG configuration-version drift

Current Windows client/tunnel modules explicitly moved to AWG3.1 and added `RandomTrailers` and `DisableCookies`, while older research covered AWG1/AWG2 fields.

### Requirements

- store AWG generation/version in canonical extension metadata;
- preserve unknown future fields;
- reject/mark unsupported rather than silently drop AWG3.1 fields on an older core;
- profile round-trip fixtures for AWG1/AWG2/AWG3.1;
- diagnostics report client/wrapper/core/server versions separately.

## 11. Minimum WireGuard-family regression matrix for PVNetwork

Before production support on any platform, test at least:

1. clean import/create/connect/disconnect/delete lifecycle;
2. malformed/partially unsupported profile handling;
3. protected persistence and explicit export;
4. UI/service/extension state synchronization;
5. process death/restart;
6. OS reboot/restore behavior;
7. permission revocation/authorization conflict;
8. network unavailable -> available recovery;
9. Wi-Fi/cellular/interface transitions;
10. DNS result/address-family changes;
11. sleep/resume;
12. duplicate/rapid commands during transition;
13. log/error redaction;
14. upgrade/migration with saved profiles;
15. Persian RTL and technical LTR token rendering;
16. platform-specific Store/package update path;
17. exact engine-version interoperability;
18. separate AWG compatibility tests rather than assuming WireGuard success covers it;
19. AWG generation upgrade/downgrade fixtures;
20. AWG3.1 random-trailer/cookie/rekey coverage;
21. Apple route include/exclude real-peer validation;
22. repeated start/stop with no leaked service/interface/driver state;
23. platform secure-storage import/export round trip.

## Remaining v1 research gaps

- broader current official WireGuard release/advisory mapping;
- exact Android/Apple current platform/API/package support matrix;
- exact AmneziaWG Android/Apple embedded-core version graphs;
- more current AmneziaWG issue/release sampling;
- final support/reuse decision and numbered-entry sync.

The later `COMPLETE-REFERENCE-v2` phase will add cryptography/wire/data flow, server implementations/installers, installation matrices and full UI/menu inventories.
