# PVNetwork Public App — Master Product, Engineering, Store and Quality Requirements

Research/spec snapshot: **2026-08-29**

Repository: `DashSaman/PVN-amirrezagol`

Audience: **all future AI agents, engineers, reviewers, QA, release engineers and product owners working on the public PVNetwork client.**

Status: **authoritative product/engineering specification for the public consumer app direction.** This document does not claim implementation or Store approval.

Read together with:

1. `AGENTS.md`
2. `docs/PROJECT_STATE.md`
3. `app/PRODUCTION_READINESS_GAP_ANALYSIS.md`
4. `app/KARING_DEEP_SOURCE_ANALYSIS.md`
5. `app/KARING_PLATFORM_IMPLEMENTATION_ANALYSIS.md`
6. `app/CROSS_PLATFORM_ARCHITECTURE_RECOMMENDATION.md`
7. `app/AGENT_HANDOFF.md`
8. `docs/superpowers/plans/2026-08-29-public-mobile-client.md`

---

# 1. Product mission

PVNetwork is to become a **professional public consumer VPN/proxy application**, suitable for distribution through Google Play and Apple App Store, with quality appropriate for non-technical users.

The target is **not**:

- a developer demo;
- a protocol showcase;
- a re-skinned competitor fork;
- a raw sing-box/Xray/Mihomo configuration editor;
- a UI that reports success when only a process has started;
- an application that works only on a developer's test device;
- an application that depends on users understanding transport/security terminology.

The target is a product that ordinary users can install, understand, connect, recover from failure, update, purchase/use a plan, obtain support for, and trust with network traffic.

## 1.1 Quality principle

**Reliability and correctness outrank protocol count.**

A smaller set of fully validated protocol/engine paths is better than dozens of partially working options.

Do not add a capability to the public UI merely because a parser or upstream core technically supports it. A capability may be marketed as supported only after the exact platform + engine + transport/security combination has appropriate interoperability/device evidence.

## 1.2 Meaning of “without defects”

No software can truthfully guarantee zero defects forever. For this project, release quality means:

- no known **Blocker** issue;
- no known **Critical** security/privacy/data-loss/billing/connection-integrity issue;
- no known reproducible crash/ANR that affects normal supported flows;
- no known false `Connected` state;
- no known secret leakage in logs/diagnostics;
- no known entitlement/billing bug that gives or removes paid access incorrectly;
- no known lifecycle bug that leaves a tunnel/service stuck after app/process/network transitions;
- no known DNS/route behavior contradicting the UI's claim;
- Store disclosure/privacy metadata matches actual behavior;
- release artifacts are signed, reproducible enough to trace, and tied to exact source/dependency versions;
- remaining accepted defects are explicitly classified, documented and approved as non-release-blocking.

Any agent that calls a release `READY` must present evidence against the gates in this document.

---

# 2. Launch platform scope

## 2.1 Primary public launch targets

The primary launch product is:

1. **Android phone/tablet** — Google Play.
2. **iPhone/iPad** — Apple App Store.

These are P0/P1 for public launch.

## 2.2 Secondary product targets

After the primary mobile product is stable:

3. Android TV / Google TV.
4. tvOS / Apple TV.
5. Windows.
6. macOS.
7. Linux.

Desktop research/implementation already exists in the repository and must be preserved. Do not delay mobile public-launch readiness merely to make all seven platforms feature-identical.

## 2.3 Platform parity rule

Feature parity is **capability-based, not cosmetic**.

If an OS cannot safely support a feature, the product must:

- hide it; or
- mark it unavailable with a truthful explanation; or
- offer the OS-appropriate equivalent.

Never emulate a setting in UI when the underlying OS cannot guarantee it.

Examples:

- Android per-app VPN routing may exist while iOS equivalent support is more constrained.
- Always-On / lockdown semantics differ by OS.
- Apple Network Extension limitations may force a smaller core/feature set.

---

# 3. Non-negotiable architecture

The long-term architecture is:

```text
PVNetwork UI
    -> PVNetwork application/use-case layer
    -> PVNetwork-owned canonical models
        -> account / entitlement
        -> subscription / profile
        -> routing
        -> DNS
        -> connection state
        -> diagnostics
    -> PlatformAdapter contracts
    -> EngineAdapter contracts
    -> approved OS APIs / protocol cores
```

## 3.1 Rules

- UI must never call Xray, sing-box, Mihomo, OpenVPN, WireGuard or another engine directly.
- Engine-native JSON/YAML must not become the product database schema.
- Generated engine config is transient output, not source of truth.
- Platform-specific VPN lifecycle must stay behind platform contracts.
- Protocol cryptography must not be reimplemented in the product layer.
- A new engine must be replaceable without redesigning consumer UI.
- All translations from canonical model to engine config must detect/report unsupported or lossy fields.
- Secrets must be passed by protected references or transient protected material where feasible.
- Backend entitlement state and local tunnel state are separate domains.

## 3.2 Required shared domains

The shared product layer must eventually contain versioned types equivalent to:

```text
AccountSession
UserIdentity
Entitlement
Plan
DeviceRegistration
SubscriptionSource
CanonicalProfile
ProtocolOptions
TransportOptions
SecurityOptions
RoutePolicy
RouteMatch
RouteAction
DnsPolicy
ServerDescriptor
ServerHealth
ConnectionIntent
ConnectionState
ConnectionFailure
TrafficStats
DiagnosticSnapshot
FeatureCapability
RemoteFeatureFlag
```

Exact names can evolve only through an explicit architecture decision. Do not create parallel duplicate concepts per platform.

---

# 4. Connection truth model

The application must have one canonical state machine.

Minimum states:

```text
DISCONNECTED
PREPARING
REQUESTING_PERMISSION
STARTING_SERVICE
STARTING_ENGINE
ESTABLISHING_TUNNEL
VERIFYING_DATA_PATH
CONNECTED
RECONNECTING
DISCONNECTING
ERROR
```

## 4.1 Truth requirement

`CONNECTED` must not mean “process launched”.

At minimum it must represent that:

- OS VPN/tunnel permission is active where required;
- the platform service/extension is running;
- the selected engine reports readiness;
- the expected interface/tunnel path exists where observable;
- the app has not received a fatal route/DNS/core error.

For selected release-critical protocol paths, add data-path verification in test/QA. Production UI should not continuously generate invasive traffic solely to prove connectivity unless product design requires it.

## 4.2 State ownership

- UI observes state; it does not invent state.
- Platform adapter owns OS tunnel lifecycle state.
- Engine adapter owns engine lifecycle state.
- Coordinator combines them into canonical state.
- Backend connectivity does not define tunnel connectivity.

## 4.3 Reconnect rules

Reconnect behavior must distinguish:

- temporary network loss;
- Wi-Fi <-> cellular transition;
- IP change;
- DNS failure;
- server failure;
- user disconnect;
- expired entitlement;
- revoked device;
- engine crash;
- OS process/extension termination.

User-requested disconnect must never be auto-reversed by reconnect logic.

---

# 5. Android production requirements

Create a real Android application module. `core:foundation` must become genuinely multi-target rather than JVM-only for the shared pieces selected for Android.

## 5.1 Android platform adapter responsibilities

Must include:

- `VpnService` permission flow;
- foreground-service lifecycle;
- persistent notification while required by Android policy;
- start/stop/restart idempotency;
- service binding/IPC or equivalent clean boundary;
- process-death recovery;
- network-change monitoring;
- boot/reconnect behavior only when user enabled and policy allows;
- battery optimization behavior without deceptive prompts;
- app background/foreground transitions;
- Quick Settings Tile after base lifecycle is proven;
- deep links/share intents/QR/clipboard import where appropriate;
- Android Keystore-backed secret storage;
- package/app routing capability where supported;
- Android TV feature detection separated from phone UX.

## 5.2 Android build requirements

Production planning must use current Play requirements. At this snapshot:

- phone/tablet Play releases beginning 2026-08-31 require target Android 16 / API 36+;
- bundled native code requires 64-bit support;
- bundled native code must satisfy current 16 KB page-size compatibility requirements;
- exact NDK/toolchain versions must be pinned in release CI.

Re-check official Google requirements before each major release.

## 5.3 Android lifecycle acceptance cases

Each release-critical engine path must survive:

1. install -> first permission -> connect;
2. connect -> disconnect -> connect repeatedly;
3. home/background while connected;
4. screen off/on;
5. app UI process killed while service remains alive, if architecture permits;
6. service killed/restarted by OS;
7. Wi-Fi -> cellular;
8. cellular -> Wi-Fi;
9. airplane mode on/off;
10. temporary no-route/no-DNS condition;
11. server goes offline;
12. selected server disappears after subscription refresh;
13. device reboot with auto-connect disabled;
14. device reboot with user-enabled supported reconnect behavior;
15. permission revoked;
16. notification permission behavior on versions where applicable;
17. low-memory/background restrictions;
18. battery saver/doze;
19. IPv4-only network;
20. IPv6-capable network.

A single Pixel test is insufficient.

---

# 6. Apple production requirements

Create a real iOS/iPadOS host app plus Packet Tunnel Network Extension.

## 6.1 Apple platform adapter responsibilities

Must cover:

- `NEVPNManager` / `NETunnelProviderManager` family as applicable;
- packet tunnel extension lifecycle;
- host app <-> extension communication;
- App Group shared files/preferences where required;
- Keychain-backed secrets;
- signing/provisioning for both app and extension;
- extension-safe dependency set;
- on-demand/reconnect logic where selected;
- extension crash/termination recovery;
- IPv4/IPv6/NAT64 behavior;
- sleep/wake/network transition handling;
- memory/CPU discipline within extension constraints;
- no assumptions that simulator behavior certifies real VPN behavior.

## 6.2 Apple distribution requirements

At this snapshot:

- VPN apps must be submitted by an Apple Developer Program **organization**;
- Network Extension entitlement/capability must be valid;
- App Store submissions require the current Apple toolchain (Xcode 26+ / current iOS SDK requirements as of this spec snapshot);
- VPN data handling must comply with Apple Guideline 5.4;
- app privacy details, privacy policy and privacy manifests must reflect reality.

Re-check official Apple rules before release.

## 6.3 Apple device acceptance cases

Release-critical paths must be tested on real devices across supported OS range with:

- first permission/install flow;
- repeated connect/disconnect;
- background/foreground;
- screen lock/unlock;
- Wi-Fi/cellular transition;
- cellular/Wi-Fi transition;
- airplane mode;
- extension termination/restart;
- app host killed while tunnel active;
- subscription refresh while connected;
- invalid/new config rollback;
- IPv6/NAT64 environment;
- server failure;
- entitlement expiry/revocation;
- device reboot and subsequent state correctness.

---

# 7. Public consumer UX requirements

The consumer UI must hide unnecessary engine complexity.

## 7.1 First launch

Required flow:

1. language follows system, with user override;
2. Persian and English are first-class;
3. correct RTL/LTR behavior;
4. concise privacy/VPN disclosure;
5. sign-in or import path;
6. plan/subscription discovery;
7. VPN permission only when user initiates connection or an immediately relevant step;
8. first successful connection achievable with minimal steps.

Do not request unrelated permissions at startup.

## 7.2 Home screen

Must show:

- one obvious Connect/Disconnect control;
- truthful canonical state;
- selected location/server or Auto;
- plan/quota/expiry when account entitlement provides it;
- session traffic where available;
- meaningful error and recovery action;
- access to server selection and settings without exposing raw core config.

## 7.3 Server selection

Must include:

- Auto/Smart option;
- manual location/server selection;
- favorites;
- recent selections;
- unavailable/maintenance state;
- bounded latency probing;
- no fake ping values;
- deterministic fallback when a server is removed/unavailable.

Latency should be labeled as a measurement, not presented as guaranteed application performance.

## 7.4 Subscription/profile management

Must support selected product needs:

- account-backed configuration;
- subscription URL import if supported;
- QR import;
- clipboard/share import where platform allows;
- manual refresh;
- background/periodic refresh where appropriate;
- last successful refresh timestamp;
- clear refresh error state;
- last-known-good cache;
- atomic config update;
- rollback if new configuration cannot validate;
- schema versioning;
- signature/authentication for PVNetwork-controlled remote config where selected.

Never overwrite a known-good working profile with an invalid partial download.

## 7.5 Settings — consumer mode

V1 consumer settings should be concise:

- language/theme;
- auto reconnect;
- connect on launch if supported;
- server selection mode;
- routing mode: Global / Smart Rules / Direct or equivalent selected product terms;
- DNS mode with safe defaults;
- diagnostics/support;
- privacy/account/device management.

Raw JSON and dozens of core flags belong in expert/developer tooling, not default consumer UX.

## 7.6 Accessibility

Before release:

- text scaling must not break major screens;
- controls need accessible labels;
- contrast must be acceptable in light/dark mode;
- touch targets must be reasonable;
- status must not be communicated by color alone;
- keyboard/focus behavior must work where applicable;
- TV builds require D-pad/focus-first UI rather than stretched phone screens.

---

# 8. Account, entitlement and device model

A public service requires a canonical entitlement system independent from Store UI.

## 8.1 Required conceptual model

```text
User
  -> Account
      -> Entitlement(s)
          -> plan_id
          -> status
          -> starts_at
          -> expires_at
          -> traffic_quota
          -> traffic_used / authoritative server view
          -> device_limit
          -> feature set
          -> purchase source
      -> RegisteredDevice(s)
      -> Subscription/config access
```

## 8.2 Rules

- Backend is authoritative for paid entitlement.
- Client must not be able to extend expiry or quota by modifying local storage.
- Device registration must be revocable.
- Device limit enforcement must be deterministic and support customer recovery.
- App must handle clock skew; do not trust local wall clock alone for paid access decisions.
- Offline grace behavior, if any, must be explicitly specified and bounded.
- Entitlement refresh must be idempotent.

## 8.3 Login

Select a low-friction method suitable for consumer use, e.g. email OTP/passkey or equivalent approved identity design.

Requirements:

- token rotation/expiration;
- secure storage;
- logout/revoke;
- multi-device behavior;
- no password/token in diagnostics;
- rate limiting and abuse protection on backend.

---

# 9. Billing / purchase requirements

If purchase/unlock occurs inside Store builds, follow current Store billing rules.

## 9.1 Internal abstraction

Store purchases must normalize into a PVNetwork backend entitlement. UI must not treat a local Store callback as final authoritative subscription state.

## 9.2 Required billing backend behavior

When Store billing is used:

- verify transactions/receipts server-side using current official APIs;
- process renewal;
- cancellation;
- refund;
- grace period;
- billing retry;
- upgrade/downgrade where offered;
- restore purchases;
- duplicate notification idempotency;
- delayed/out-of-order event handling;
- transaction audit trail;
- map Store account transaction to PVNetwork account safely.

## 9.3 Failure integrity

Never:

- grant permanent access from an unverified client callback;
- immediately revoke legitimate service because one webhook is late;
- double-consume/double-apply the same transaction event;
- expose purchase receipt tokens in logs/support exports.

---

# 10. Backend/control-plane requirements

A professional public app should have versioned APIs rather than rely exclusively on raw subscription links.

Recommended services/modules:

1. identity/session service;
2. entitlement service;
3. device registry;
4. versioned subscription/config API;
5. server catalog;
6. server health/maintenance service;
7. remote config/feature flags;
8. minimum-supported-version/forced-upgrade policy;
9. Apple/Google billing event processors if used;
10. account deletion/privacy service;
11. support/diagnostic upload service;
12. admin/audit tooling separate from consumer credentials.

## 10.1 API properties

- TLS only;
- authentication and authorization separated;
- short-lived access credentials where practical;
- idempotent mutation keys where needed;
- request correlation IDs that do not contain secrets;
- rate limiting;
- abuse protection;
- schema/version negotiation;
- backwards compatibility window;
- signed or integrity-protected high-risk config where appropriate;
- ETag/version for config refresh;
- explicit maintenance/error codes suitable for user messaging.

## 10.2 Remote config safety

Remote feature flags may disable a broken capability but must not silently alter privacy behavior or weaken security controls.

Critical config changes must be auditable.

---

# 11. Routing and DNS product requirements

Routing and DNS are first-class product subsystems.

## 11.1 Routing

Canonical rules should be capable of representing, as platform/core capability allows:

- domain exact/suffix/keyword/regex;
- IP/CIDR;
- GeoIP/GeoSite/ruleset;
- source/destination port;
- network type;
- app/package on Android;
- process where desktop supports it;
- logical combinations;
- actions: VPN outbound, direct/bypass, block/reject, selected group/outbound.

The consumer UI should provide presets first. Advanced custom rules can come later.

## 11.2 DNS

Canonical DNS policy should cover selected supported capabilities:

- system/default resolver;
- UDP/TCP where appropriate;
- DoT;
- DoH;
- DoQ/HTTP3 only when chosen engine/platform proves it;
- split DNS;
- route-aware DNS;
- IPv4/IPv6 strategy;
- cache behavior;
- leak prevention.

## 11.3 Truthful capability flags

If an engine/platform cannot honor a rule/action, translation must return a structured unsupported/lossy result. Never silently drop a route or DNS rule.

---

# 12. Protocol / engine release gate

Every public engine capability must pass this sequence:

```text
SOURCE IDENTIFIED
-> LICENSE REVIEWED
-> EXACT VERSION PINNED
-> BUILD PROVENANCE RECORDED
-> ADAPTER IMPLEMENTED
-> CONFIG TRANSLATION TESTED
-> PLATFORM RUNTIME IMPLEMENTED
-> REAL DATA PATH TESTED
-> DEVICE LIFECYCLE TESTED
-> SECURITY/SBOM CHECKED
-> STORE BINARY COMPATIBILITY CHECKED
-> RELEASE APPROVED
```

Parser success is not runtime support.

One VLESS+RAW test does not certify REALITY, Vision, WebSocket, gRPC, XHTTP, mKCP, etc.

Each materially different transport/security combination needs its own support/evidence status.

## 12.1 First launch core strategy

Do not block public launch on maximum protocol breadth.

Choose the smallest engine set that provides the required real customer profiles and can be maintained safely on Android and iOS.

Prioritize paths with:

- clean source/provenance;
- acceptable license/business compatibility;
- stable mobile embedding/runtime;
- tested TUN integration;
- manageable binary size;
- active upstream maintenance;
- security update path.

---

# 13. Security and privacy requirements

## 13.1 Secrets

Never persist in plaintext ordinary app storage/logs:

- passwords;
- private keys;
- reusable subscription URLs containing credentials;
- bearer tokens;
- Store receipts/tokens;
- VPN UUID/password credentials;
- backend admin credentials.

Use:

- Android Keystore-backed protection;
- Apple Keychain;
- restrictive transient files where engines require file config;
- cleanup on stop/failure;
- redaction before diagnostics.

## 13.2 Logging

Logs must be structured by severity/category and have redaction at the source.

Diagnostic export must remove or hash sensitive fields.

Do not rely on “support staff will be careful”. The app must prevent secret leakage programmatically.

## 13.3 Third-party SDK policy

Every SDK added must be justified.

Before adoption document:

- purpose;
- data collected;
- network destinations;
- privacy impact;
- Store disclosure impact;
- license;
- size/performance;
- update/security history;
- removal/replacement path.

Avoid advertising or traffic-derived analytics in the VPN path.

## 13.4 Supply chain

For every release:

- exact dependency lock/pins;
- SBOM;
- license inventory;
- vulnerability scan;
- native binary hashes;
- source/release provenance;
- signing identity traceability;
- retained debug symbols where appropriate;
- emergency update process for compromised/vulnerable core.

---

# 14. Privacy / Store disclosure requirements

Maintain one canonical **data inventory** as source of truth.

For each data element record:

- collected?;
- purpose;
- user-linked?;
- shared?;
- processor/recipient;
- retention;
- encryption;
- deletion behavior;
- region/storage if relevant.

Generate/keep consistent:

- privacy policy;
- in-app VPN disclosure/consent;
- Google Play Data Safety;
- Apple App Privacy;
- Apple privacy manifest (`PrivacyInfo.xcprivacy`) and required-reason API declarations where applicable;
- support documentation;
- account deletion behavior.

Any mismatch between declared and actual data behavior is a release blocker.

## 14.1 VPN-specific data rule

Do not monetize or profile user traffic. Do not design packet inspection for advertising/behavioral analytics.

Collect the minimum operational data needed for service/security/support, and document it.

---

# 15. Account deletion / privacy rights

If account creation exists:

- provide in-app account deletion initiation;
- provide the required external web deletion resource for Google Play;
- delete/revoke active sessions/devices;
- define immediate vs legally retained records;
- make deletion idempotent;
- prevent deleted credentials from remaining usable;
- provide understandable user confirmation/status.

If data export/DSAR is offered or required by applicable law/product policy, make it authenticated and auditable.

---

# 16. Diagnostics and support

A professional app needs support tooling that a normal user can use without exposing secrets.

## 16.1 Diagnostic snapshot

May include:

- app version/build;
- OS/device class;
- engine versions;
- canonical connection event timeline;
- VPN permission state;
- network type;
- selected server ID/location (not secret credential);
- DNS/routing status summaries;
- last config refresh result;
- crash correlation ID;
- sanitized errors.

Must not include reusable credentials.

## 16.2 Support workflow

Required before public launch:

- in-app Help/Support entry;
- FAQ/troubleshooting content;
- diagnostic export or user-approved upload;
- server-side support correlation without requiring the user to expose tokens;
- support email/web path;
- incident/maintenance messaging.

---

# 17. QA strategy

Testing layers must remain distinct:

1. unit tests;
2. serialization/model tests;
3. adapter translation tests;
4. engine lifecycle tests;
5. integration tests;
6. isolated network data-path tests;
7. Android instrumentation/device tests;
8. iOS real-device tests;
9. backend contract tests;
10. billing sandbox tests;
11. security/privacy tests;
12. Store review dry run;
13. staged production canary.

Passing one layer never substitutes for another.

## 17.1 Device matrix — minimum categories

Android must include at least representative:

- Google/Pixel-style Android;
- Samsung One UI;
- Xiaomi/HyperOS/MIUI class due aggressive background behavior;
- one lower-resource supported device;
- one current flagship/current Android release.

iOS must include at least:

- oldest supported iOS generation/device class feasible;
- current iOS generation;
- different hardware generation/device sizes.

Exact device list must be recorded before RC.

## 17.2 Network matrix

Test:

- Wi-Fi;
- cellular;
- Wi-Fi <-> cellular handoff;
- IPv4;
- IPv6/NAT64 where applicable;
- high latency;
- packet loss;
- temporary DNS failure;
- captive portal;
- server outage;
- network offline/online;
- MTU-sensitive paths where relevant.

---

# 18. Performance and stability gates

Before RC, measure and record:

- cold start;
- connect latency distribution for selected paths;
- reconnect latency;
- memory usage while disconnected/connected;
- extension/service memory behavior;
- CPU idle/connected;
- battery/wakelock behavior;
- sustained traffic stability;
- repeated connect/disconnect stability;
- crash-free sessions;
- Android ANR/Vitals;
- binary size and native library contribution.

Do not publish invented target numbers. Establish baselines on real builds, then set release budgets and monitor regressions.

---

# 19. CI/CD and release engineering

## 19.1 CI must eventually include

- shared unit tests;
- Android compile/lint/unit/instrumentation where infrastructure allows;
- iOS compile/test on supported macOS runner;
- engine adapter tests;
- selected real/isolated data-path tests;
- dependency/license/SBOM checks;
- secret scanning;
- static analysis;
- signed release artifact generation under protected release workflow;
- symbol/archive retention;
- version/build metadata generation.

## 19.2 Release tracks

Use staged progression:

```text
local/dev
-> CI integration
-> internal QA
-> Android internal/closed testing + TestFlight
-> release candidate
-> staged Store rollout
-> full rollout
```

Never move directly from developer build to 100% public release.

## 19.3 Rollback

Need both:

- Store version rollback/stop-rollout procedure where available;
- server-side feature/config kill switches for broken optional capabilities.

Remote config must not become an excuse to ship untested code.

---

# 20. Store submission package

Before submission prepare:

- final legal publisher identity;
- package ID / bundle ID;
- app name/branding rights;
- icon/splash assets;
- screenshots for required device sizes;
- accurate description;
- support URL;
- privacy policy URL;
- terms where applicable;
- age/content declarations;
- VPN/VpnService declarations;
- Data Safety/App Privacy answers;
- reviewer demo account or valid test provisioning path;
- reviewer instructions describing VPN permission and connection steps;
- billing test path if monetized;
- account deletion path;
- third-party license notices;
- export/encryption/compliance declarations as applicable;
- country/storefront availability review.

Reviewer credentials/config must remain valid throughout review.

---

# 21. Store/legal organization prerequisite

Publishing is not a last-week administrative task.

Current direction requires a real organization publisher setup for VPN distribution on Apple and Google Play under current rules.

Project owner/admin track must resolve:

- legal entity name;
- D-U-N-S where needed;
- Apple Developer organization enrollment;
- Google Play organization developer account;
- organization website/domain;
- support/privacy contact;
- banking/tax/payment setup if monetized;
- trademark/brand ownership/permissions.

Engineering may continue while these are processing, but public launch is blocked until they are complete.

---

# 22. Definition of Done by milestone

## M-P0 — Product decisions locked

Done only when committed decisions exist for:

- legal publisher path;
- package/bundle IDs;
- supported Android/iOS minimum versions;
- KMP/mobile architecture;
- first launch engine/protocol set;
- account model;
- purchase/billing model;
- privacy/telemetry policy;
- backend API ownership.

## M-P1 — Shared mobile foundation

Done only when:

- shared KMP targets needed by Android/iOS compile;
- canonical account/entitlement/subscription/connection models exist;
- `PlatformVpnService`-equivalent contract exists;
- secure-store contract exists;
- no mobile UI directly calls an engine.

## M-P2 — Android vertical slice

Done only when a real Android device proves:

```text
install
-> login/import
-> select entitlement/config
-> request VPN permission
-> connect
-> real data path
-> background
-> network transition
-> reconnect as designed
-> disconnect
-> cleanup
```

with captured evidence and no false state.

## M-P3 — iOS vertical slice

Done only when a real iPhone/iPad proves the analogous flow through Packet Tunnel Network Extension.

## M-P4 — Consumer product features

Done only when:

- onboarding;
- Home;
- account/plan/quota/expiry;
- server selection;
- subscription refresh/rollback;
- basic routing/DNS;
- diagnostics;
- Persian/English/RTL;
- accessibility baseline

are implemented and tested.

## M-P5 — Backend + billing + privacy

Done only when selected account/billing path is end-to-end tested, privacy inventory and deletion flow exist, and Store disclosures can be generated from actual behavior.

## M-P6 — Release candidate

Done only when:

- device/network acceptance matrix passes;
- no Blocker/Critical known issues;
- security/SBOM/license gates pass;
- signed Android/iOS RC artifacts produced;
- TestFlight/Play internal or closed testing completes;
- Store metadata/reviewer package prepared;
- release rollback/support runbooks exist.

## M-P7 — Store verified

Done only after actual Store review/approval for the specific submitted builds. Internal confidence is not `STORE VERIFIED`.

## M-P8 — Production verified

Done only after staged rollout demonstrates acceptable stability/operations and no release-blocking regression.

---

# 23. Bug severity and release blocking

## Blocker

Examples:

- cannot connect for supported primary flow;
- app/extension fails to launch for broad users;
- Store submission impossible;
- data corruption/destructive migration;
- signing/build pipeline unusable.

**Release prohibited.**

## Critical

Examples:

- credential/private-key/token leakage;
- traffic routed contrary to product security claim;
- false Connected state with no tunnel;
- billing grants/revokes paid access incorrectly at scale;
- reproducible severe crash/ANR in normal flow;
- privacy behavior contradicts disclosure.

**Release prohibited.**

## Major

Material user-visible failure with workaround or limited scope.

Normally blocks RC unless explicitly risk-accepted with owner/release approval.

## Minor

Cosmetic/non-critical issue with no security/data/billing/connectivity impact.

May be accepted only if tracked.

---

# 24. Migration / data integrity requirements

Once public users exist, configuration/account migrations become critical.

Every persistent schema change must have:

- schema version;
- forward migration;
- tests using old saved fixtures;
- rollback/failure behavior;
- no silent deletion of working profiles;
- backup/restore strategy where selected.

Never use generated engine config as the only persisted copy of user configuration.

---

# 25. Observability without violating VPN privacy

Operational telemetry should answer:

- app build/version distribution;
- crash/ANR rates;
- connection failure category counts;
- backend API availability;
- billing webhook health;
- server health;
- config rollout errors.

It should **not** require logging browsing destinations, packet contents or sensitive subscription secrets.

Prefer coarse, privacy-safe operational metrics.

---

# 26. Incident response

Before public launch define procedures for:

- broken app release;
- broken config/ruleset rollout;
- compromised server;
- leaked signing/backend credential;
- vulnerable upstream core;
- billing outage;
- authentication outage;
- Store rejection;
- privacy/security report;
- regional blocking/outage.

Runbooks must identify who/what can:

- disable a feature;
- revoke keys/devices;
- stop rollout;
- rotate credentials;
- force minimum version;
- publish maintenance notice.

---

# 27. Rules for future agents

All future agents working on the public app must follow these rules:

1. Read this spec before implementation.
2. Read repository state; do not rely on old chat claims.
3. Do not replace existing architecture without evidence and an explicit committed design decision.
4. Do not fork/copy Karing/Hiddify/GPL app code casually.
5. Do not add a third-party SDK/core without license/provenance/privacy/security review.
6. Use TDD/appropriate tests for each bounded implementation task.
7. Separate parser support, adapter support, runtime support, device verification, Store verification and production verification.
8. Never claim completion from compilation alone.
9. Never claim mobile support from Linux/JVM CI alone.
10. Never claim iOS VPN support from simulator-only evidence.
11. Never mark `CONNECTED` based only on process start.
12. Never log secrets.
13. Never silently drop unsupported routing/DNS/security fields.
14. Preserve last-known-good config before applying remote updates.
15. Update checkpoint/handoff documents after meaningful milestones.
16. Keep `docs/FOREGROUND_ACTIVITY.json` synchronized for interactive repository work.
17. Re-check current Store policies before release-affecting implementation decisions.
18. Prefer a complete vertical slice over broad unfinished scaffolding.
19. Keep consumer UX simple; expert/core settings are secondary.
20. Stop release on Blocker/Critical issues.

---

# 28. Immediate next engineering target

The next high-value implementation target is **not another protocol-count campaign**.

It is:

```text
Android public-app vertical slice
```

with this exact outcome:

```text
PVNetwork app launch
-> account/import config
-> canonical entitlement/profile
-> server selection
-> Android VPN permission
-> PlatformVpnService
-> one approved EngineAdapter path
-> actual tunneled data
-> truthful state
-> network transition/reconnect
-> disconnect/cleanup
-> sanitized diagnostics
```

Then reproduce the same product contract on iOS through Packet Tunnel Network Extension.

The execution plan is maintained in:

`docs/superpowers/plans/2026-08-29-public-mobile-client.md`

---

# 29. Release declaration template

An agent/release owner may only declare a milestone complete using an evidence summary similar to:

```text
Milestone: M-P2 Android vertical slice
Build: <version/commit>
Device(s): <exact models/OS>
Engine/core: <exact version/hash>
Protocol/transport/security: <exact scope>
Tests: <unit/integration/device/data-path receipts>
Known Blocker: 0
Known Critical: 0
Known Major: <count/list/risk decision>
Secrets/log redaction: PASS
Lifecycle matrix: PASS for stated scope
Result: PASS for stated scope only
Not claimed: iOS / other protocols / Store approval / production readiness
```

No broader claim is permitted without corresponding evidence.
