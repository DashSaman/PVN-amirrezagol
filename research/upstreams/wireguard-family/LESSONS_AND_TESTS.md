# WireGuard Family — Upstream Lessons and PVNetwork Regression Requirements

Research date: 2026-08-14

Status: `IN-RESEARCH`. This file records failure classes from official source/mailing-list evidence and converts them into PVNetwork quality requirements. It is not an operational networking guide.

## Evidence sources reviewed

Primary upstream evidence included the official WireGuard source mirrors and the WireGuard mailing-list archive at `lists.zx2c4.com`.

Relevant historical/current discussion themes reviewed include:

- Android VPN authorization conflicts when another application is configured as Always-On.
- Android tunnel recovery/state problems after reboot in an Always-On scenario.
- Android Quick Settings tile occasionally opening UI instead of performing the expected state transition.
- Android/application state becoming stale when tunnel state is changed outside the normal UI path.
- endpoint/name-resolution behavior across changing networks and address families.
- Windows tunnel startup/service behavior when name resolution or network readiness is delayed.
- Windows roaming/sleep/network transitions where previously resolved endpoint/network state can become stale.
- Apple NetworkExtension lifecycle quirks, platform workarounds, release regressions, and App Store review latency making emergency fixes harder to distribute quickly.

These reports span different OS/app versions and should not be treated as proof that every old bug is still present. Their value is as recurring **failure classes** for PVNetwork's acceptance suite.

## 1. Android VPN ownership / Always-On conflict

### Lesson
Android can have OS-level ownership/authorization interactions between VPN applications. A connection failure caused by another Always-On VPN is not equivalent to an invalid PVNetwork profile.

### PVNetwork requirements

- Detect and classify OS authorization/ownership failure separately from engine failure.
- Show a clear user-facing explanation without blaming the profile.
- Do not enter a reconnect loop when OS permission/ownership makes success impossible.
- Retest after permission/Always-On state changes without requiring destructive profile recreation.

## 2. Reboot and restore-state reliability

### Lesson
Historical Android reports show that “configured as Always-On” and “actually functional after reboot” are separate states.

### PVNetwork tests

- reboot with an active/remembered profile;
- OS starts tunnel before UI process is launched;
- profile store available but network/DNS not yet ready;
- UI launched after OS-created tunnel;
- repeated reboot cycles without stale state or duplicate session objects.

## 3. UI / Quick Tile / external-state synchronization

### Lesson
A mature networking client can still show stale UI when the tunnel is changed through a different control surface.

### PVNetwork architecture rule

Tray, Quick Settings tile, main UI, background service and any future automation interface must subscribe to the same authoritative session state. None should maintain an independent “connected” boolean.

### PVNetwork tests

- toggle from Quick Settings while main UI is closed;
- open UI immediately during a transition;
- change state from service/system surface and verify UI convergence;
- kill/recreate UI process while session remains active;
- rapid repeated user actions do not produce contradictory state.

## 4. Network readiness and name-resolution timing

### Lesson
Historical Windows and Unix reports show startup may occur before DNS/network readiness, especially around boot. A fixed short retry policy may be wrong for some environments.

### PVNetwork requirements

- distinguish “network unavailable/not ready” from permanent profile failure;
- make retry/backoff policy state-aware rather than a blind infinite loop;
- preserve cancellation and explicit user disconnect;
- expose enough diagnostics to understand whether failure was resolution, reachability, permission or engine startup;
- verify recovery when the network becomes available later.

## 5. Roaming, sleep/resume and endpoint/address-family changes

### Lesson
Moving between networks, sleeping/resuming and DNS/address changes can leave previously valid runtime state stale even while the saved profile remains correct.

### PVNetwork tests

- Wi-Fi -> cellular -> Wi-Fi;
- IPv4-only -> dual-stack -> IPv6-only where platform permits;
- sleep on one network and resume on another;
- endpoint DNS answer changes while a profile remains selected;
- interface loss/recreation;
- repeated network transitions without profile corruption or UI desynchronization.

## 6. Route/helper assumptions

### Lesson
Convenience UI for route exclusion/inclusion can encode policy assumptions. A built-in convenience option can become incorrect or incomplete as platform expectations change.

### PVNetwork requirement

Routing helpers must be treated as versioned policy generators with tests, not as magic hard-coded checkboxes. The generated effective policy should be inspectable before activation.

## 7. Apple NetworkExtension and Store-release risk

### Lesson
WireGuard maintainers have documented that Apple networking-framework quirks can require platform-specific workarounds, and that Store review latency can delay a regression fix even when code is corrected quickly.

### PVNetwork requirements

- platform workarounds must be isolated, commented and covered by regression tests;
- never delete an apparently strange workaround without tracing its historical reason;
- maintain staged release/rollback plans for Apple platforms;
- keep server/control-plane changes backward compatible enough that an App Store delay does not instantly break the installed client population;
- test app upgrades while an existing NetworkExtension configuration is installed;
- separate “code fixed” from “Store version available to users” in operational status.

## 8. Minimum WireGuard-family regression matrix for PVNetwork

Before marking WireGuard support production-ready on any platform, test at least:

1. clean import/create/connect/disconnect/delete lifecycle;
2. malformed and partially unsupported profile handling;
3. protected persistence and explicit export;
4. UI/service/extension state synchronization;
5. process death and restart;
6. OS reboot/restore behavior;
7. permission revocation/authorization conflict;
8. network unavailable -> network available recovery;
9. Wi-Fi/cellular/interface transitions;
10. DNS result changes and address-family changes;
11. sleep/resume;
12. duplicate/rapid commands during transition;
13. log/error redaction;
14. upgrade/migration with saved profiles;
15. Persian RTL and technical LTR token rendering;
16. platform-specific Store/package update path;
17. exact engine-version interoperability;
18. separate AmneziaWG compatibility tests rather than assuming WireGuard test success covers it.

## Remaining research gaps

- review newer mailing-list threads and exact upstream fixes/commits for each historical report;
- map official Windows release history and current supported OS matrix;
- map Android release/change history and current platform/API minimums;
- review Apple issue/workaround history against current OS releases;
- AmneziaWG issue/release regression review;
- convert the final approved list into executable PVNetwork tests after implementation exists.