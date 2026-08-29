# PVNetwork Public Mobile App — Production / Store Readiness Gap Analysis

Research snapshot: **2026-08-29**

Repository: `DashSaman/PVN-amirrezagol`

Status: **gap analysis / requirements baseline. This document does not claim mobile implementation, Store approval, device validation or production readiness.**

## 1. Executive conclusion

PVNetwork already has valuable protocol research, canonical domain work and several engine adapter/runtime slices. The current blocker to a professional public client is **not protocol count**.

The missing work is primarily:

1. actual Android and iOS application targets;
2. production OS VPN integration (`VpnService` on Android, Network Extension / `NEVPNManager` family on Apple);
3. mobile lifecycle, networking and device validation;
4. subscription/account/backend product integration;
5. Store compliance, legal organization identity, privacy, billing and review artifacts;
6. polished consumer UX, accessibility, localization and diagnostics;
7. signed release pipeline, supply-chain controls and mobile QA;
8. operational support after launch.

The repository should be treated as a **strong engineering foundation, not a release candidate**.

Current repository evidence:

- `settings.gradle.kts` includes only `apps:desktop`; no Android or iOS app module exists.
- `core:foundation` uses Kotlin Multiplatform but currently configures only a JVM target.
- `docs/PROJECT_STATE.md` states `DEVICE VERIFIED: none` and `PRODUCTION READY: no`.
- current verified runtime evidence is scoped Linux/JVM interoperability, not Android/iOS device evidence.

---

# 2. Store / legal blockers before we can publish

## 2.1 Publisher must be a real organization

### Apple

Apple App Review Guideline 5.4 currently requires apps offering VPN services to be submitted by developers enrolled **as an organization**.

Primary policy:

- https://developer.apple.com/app-store/review/guidelines/ — section 5.4 VPN Apps

Required business preparation:

- legal entity;
- Apple Developer Program organization enrollment;
- D-U-N-S / organization verification as required by Apple enrollment;
- stable company website;
- public support email/contact;
- privacy policy and terms under the actual legal entity.

### Google Play

Google's Play Console requirements state that apps approved to use `VpnService` must use an **Organization** developer account. Organization accounts require a D-U-N-S number and verified organization information.

Primary policies:

- https://support.google.com/googleplay/android-developer/answer/10788890
- https://support.google.com/googleplay/android-developer/answer/13634885

This becomes especially important under the Play Console requirements effective 2026-09-30.

**Release blocker:** do not design the publishing process around a personal developer account.

---

## 2.2 Android `VpnService` policy declaration

A public Android VPN app must have VPN as its core functionality (or fit a permitted exception), and must complete the Play Console `VpnService` declaration.

Google currently requires, among other items:

- clear declaration of `VpnService` use in the Play listing;
- encrypted traffic from device to VPN tunnel endpoint;
- no collection of sensitive data through VPN APIs without prominent disclosure and affirmative consent;
- no manipulation/redirection of other-app traffic for monetization;
- a short review video demonstrating the app opening and VPN usage;
- accurate declaration of data collected/shared through the VPN service.

Primary policy:

- https://support.google.com/googleplay/android-developer/answer/12564964

### Product requirement

Create a dedicated first-run VPN disclosure/consent flow. It must be separate from a generic Terms checkbox if sensitive data is accessed/collected.

---

## 2.3 Apple VPN-specific privacy restriction

Apple Guideline 5.4 is stricter than a normal utility app:

- use the `NEVPNManager` / Network Extension architecture;
- clearly declare what data is collected and how it is used **before the user purchases or uses the VPN service**;
- VPN apps may not sell, use, or disclose VPN user data to third parties for any purpose;
- the privacy policy must commit to this;
- local VPN licensing requirements must be respected per storefront/territory.

This strongly favors a privacy-minimized telemetry design. Do not build advertising or traffic-derived analytics into the VPN path.

---

# 3. Build / platform blockers

## 3.1 Android application target does not exist yet

Create a real Android app module and extend KMP beyond JVM.

Minimum architecture:

```text
apps/android
    -> shared KMP product/domain code
    -> Android PlatformAdapter
    -> VpnService / foreground-service lifecycle
    -> EngineAdapter(s)
    -> secure storage / notifications / network monitoring
```

Required Android boundaries:

- VPN permission request;
- foreground service;
- notification channel;
- service start/stop/restart state machine;
- process death recovery;
- boot/reconnect policy if enabled;
- network change handling (Wi-Fi <-> mobile, roaming, captive portal);
- Android Always-On / lockdown compatibility where feasible;
- per-app routing only behind an Android capability check;
- Quick Settings tile can be added after the base lifecycle is proven;
- URI/deep-link/share/QR import entry points;
- secure secrets via Android Keystore-backed storage.

## 3.2 Android target SDK deadline

Starting **2026-08-31**, new apps and app updates submitted to Google Play must target Android 16 / API 36 or higher, with separate lower exceptions for Android TV/Automotive/Wear.

Primary source:

- https://developer.android.com/google/play/requirements/target-sdk

For a phone/tablet PVNetwork app beginning now, set the production plan around **targetSdk 36+**, not 35.

## 3.3 Android native-code requirements

Because VPN/proxy engines commonly contain native code, every bundled native artifact must be audited for:

- supported 64-bit ABI;
- 16 KB memory page-size compatibility;
- current NDK/toolchain compatibility;
- stripped symbols + separately retained debug symbols;
- reproducible provenance/hash;
- license/SBOM entry.

Google Play's current technical quality requirements include 64-bit and 16 KB page-size support for apps containing native code. TV has explicit 64-bit/16 KB requirements effective 2026-08-01.

Primary source:

- https://support.google.com/googleplay/android-developer/answer/17492799

A core that works on a Linux CI VM is not automatically Store-compatible Android native code.

---

## 3.4 iOS application target does not exist yet

Create the actual Apple target rather than treating iOS as a later packaging step.

Minimum structure:

```text
apps/ios
    -> shared PVNetwork domain/application logic where practical
    -> native Apple PlatformAdapter
    -> NETunnelProviderManager / NEVPNManager family
    -> Packet Tunnel Network Extension
    -> EngineAdapter / Apple-compatible core
    -> Keychain + App Group storage boundary
```

Required Apple work:

- Network Extensions capability;
- `packet-tunnel-provider` entitlement;
- App Group for app <-> extension configuration/state exchange;
- provisioning profiles / signing for host app and extension;
- Keychain storage;
- extension memory/CPU constraints;
- extension-safe dependencies;
- background/foreground lifecycle;
- on-demand / reconnect policy where appropriate;
- IPv4, IPv6 and NAT64 testing;
- real-device testing (simulator is insufficient for tunnel certification).

Primary entitlement source:

- https://developer.apple.com/documentation/bundleresources/entitlements/com.apple.developer.networking.networkextension

## 3.5 Apple submission toolchain

Since **2026-04-28**, App Store Connect submissions must be built with Xcode 26 or later using the iOS/iPadOS/tvOS 26 SDK or later.

Primary source:

- https://developer.apple.com/news/upcoming-requirements/

CI and developer Macs must therefore support the Xcode 26 release toolchain.

---

# 4. Privacy / data architecture required before UI polish

## 4.1 Privacy policy is a product dependency, not final paperwork

We need one canonical data inventory describing every field collected by:

- app;
- VPN engine;
- backend;
- analytics/crash SDK;
- payment provider / Store;
- support system.

For each field record:

- purpose;
- source;
- retention;
- storage region;
- encryption;
- third-party recipient;
- deletion behavior;
- whether it is linked to identity.

Then generate/maintain:

- Google Play Data Safety answers;
- Apple App Privacy answers;
- privacy policy;
- in-app privacy disclosures;
- account deletion workflow.

Google requires a privacy policy for all apps and the Data Safety form even when no data is collected.

Sources:

- https://support.google.com/googleplay/android-developer/answer/10144311
- https://support.google.com/googleplay/android-developer/answer/10787469

Apple requires a privacy policy URL and App Privacy disclosure of app + third-party partner data handling.

Source:

- https://developer.apple.com/help/app-store-connect/manage-app-information/manage-app-privacy

## 4.2 Apple privacy manifests

The Apple app and relevant third-party SDKs must have valid privacy manifests / required-reason API declarations where applicable.

Required artifact:

- `PrivacyInfo.xcprivacy`

Sources:

- https://developer.apple.com/documentation/bundleresources/adding-a-privacy-manifest-to-your-app-or-third-party-sdk
- https://developer.apple.com/documentation/bundleresources/describing-use-of-required-reason-api
- https://developer.apple.com/support/third-party-SDK-requirements/

Do not add analytics SDKs casually. Every SDK becomes a privacy, signing and review dependency.

---

# 5. Account / subscription / payment product design

A consumer VPN needs a single canonical entitlement model independent of Google/Apple billing.

Suggested internal model:

```text
Account
  -> Entitlement / Plan
      -> expiry
      -> quota
      -> device limit
      -> allowed products/features
      -> Store/Web purchase source
      -> server/subscription assignment
```

## 5.1 Decide account model before implementing UI

Recommended options:

### Option A — PVNetwork account + Store-native purchase

Best for broad public consumer distribution.

- email / OTP / passkey or similarly low-friction login;
- Apple IAP for in-app digital subscriptions;
- Google Play Billing for Play-distributed in-app digital subscriptions;
- backend normalizes Store transactions into PVNetwork entitlements.

### Option B — existing-customer companion app

Users log in/import an entitlement purchased elsewhere and no purchase CTA is offered inside the app.

This can reduce billing surface, but the exact Store business-model treatment must be validated against current regional rules before submission. Do not assume a website purchase button is universally allowed inside either Store build.

## 5.2 Billing requirements

If users purchase/unlock VPN service inside the Play-distributed app, Google's Payments policy generally requires Google Play Billing for digital services.

Source:

- https://support.google.com/googleplay/android-developer/answer/10281818

If users purchase/unlock digital VPN functionality inside the iOS app, Apple In-App Purchase rules apply unless a specific current exception/entitlement is valid for the storefront/business model.

Source:

- https://developer.apple.com/app-store/review/guidelines/ — section 3.1

Required backend work if Store subscriptions are used:

- transaction/receipt validation;
- App Store Server Notifications;
- Google Play Developer API / real-time developer notifications as selected;
- renewal/cancel/refund/grace handling;
- idempotency;
- entitlement reconciliation;
- restore purchases;
- account/device linking;
- customer-support audit trail that does not expose tunnel secrets.

## 5.3 Account deletion

If account creation exists:

- Apple requires account deletion available in-app;
- Google requires a discoverable in-app deletion path **and an external web deletion resource**.

Sources:

- https://developer.apple.com/app-store/review/guidelines/ — 5.1.1(v)
- https://support.google.com/googleplay/android-developer/answer/13327111

Deletion must define what is deleted immediately and what must legally be retained (for example payment/tax records).

---

# 6. Consumer feature set missing for a professional V1

The first Store release should not expose every engine/core knob. It should turn the existing protocol work into a reliable consumer product.

## 6.1 First-launch / onboarding

Required:

- Persian + English from day one;
- RTL/LTR correct layout;
- privacy/VPN disclosure;
- short explanation of what VPN permission does;
- account login or subscription/QR import;
- permission request only at the moment it is needed;
- first successful connection path in a few steps.

## 6.2 Home / connect experience

Required:

- one primary connect/disconnect control;
- honest state: preparing / connecting / connected / reconnecting / error;
- selected location/profile;
- current latency/quality signal where meaningful;
- session traffic;
- account quota and expiry when provided by backend;
- clear failure reason and recovery action.

Do not display `Connected` until the OS tunnel + engine state is actually established.

## 6.3 Subscription/profile management

Required:

- login-backed subscription or URL import;
- QR import;
- clipboard/share import where platform allows;
- periodic refresh;
- pull-to-refresh/manual refresh;
- last refresh + error state;
- multiple subscription sources if product requires it;
- cached last-known-good configuration;
- atomic update/rollback when a new subscription is invalid;
- signed/authenticated remote configuration where PVNetwork controls the feed.

## 6.4 Server selection

Required:

- Auto / Smart choice;
- manual location/server selection;
- latency probe with bounded concurrency;
- favorites / recent;
- unavailable node state;
- no fake ping / fake speed indicator;
- deterministic fallback if selected node disappears.

## 6.5 Reliability settings

Required or high-value:

- auto reconnect;
- reconnect after network change;
- optional connect-on-launch;
- OS-supported always-on / on-demand behavior;
- kill-switch/lockdown semantics only where platform capabilities make the claim true;
- IPv6 policy;
- DNS leak prevention;
- captive-portal-safe behavior;
- sleep/wake recovery.

## 6.6 Routing / DNS

V1 should provide simple product-level modes rather than core-specific JSON:

- Global VPN;
- Rules / Smart;
- Direct / bypass mode where relevant;
- custom DNS / secure DNS where supported;
- per-app routing on Android only behind a capability flag;
- clear indication when a feature is unavailable on iOS.

Advanced core configuration can be an expert feature later.

## 6.7 Diagnostics / support

Required:

- connection event timeline;
- engine version;
- app version/build;
- platform/OS info;
- permission state;
- network type;
- DNS/route test;
- exportable diagnostic bundle;
- mandatory redaction of tokens, passwords, UUID credentials, private keys and reusable subscription URLs;
- user-selectable support upload rather than silent upload of tunnel diagnostics.

---

# 7. Backend/control-plane requirements

A polished public client should not depend only on raw subscription links.

Recommended backend services:

1. **Identity** — account/login/session management.
2. **Entitlements** — plan, quota, expiry, devices, Store/web purchase normalization.
3. **Device registry** — device ID, revoke/logout, device limit enforcement.
4. **Subscription/config API** — versioned canonical config payload, ETag/version, signed response.
5. **Server catalog** — availability, location, capability metadata.
6. **Health service** — server health and maintenance state; never rely solely on client ping.
7. **Remote configuration / feature flags** — staged rollout, kill switch for broken engine/config features, minimum supported app version.
8. **Billing webhook processors** — Apple/Google events if monetized in Store.
9. **Account deletion/privacy API** — deletion request/status/export where offered.
10. **Support API** — ticket/report attachment with redaction.

### Security properties

- TLS validation everywhere;
- short-lived authenticated API sessions;
- rotation strategy for keys/secrets;
- rate limiting and abuse controls;
- signed server-driven configuration for high-risk fields;
- do not place reusable admin credentials in the app;
- do not trust client-reported quota/entitlement state;
- backend remains authoritative for account entitlement;
- app remains authoritative for local tunnel state.

---

# 8. Security / supply-chain gate

Before any native core is embedded in a Store binary:

- pin exact source revision/release;
- verify license and commercial redistribution;
- verify release signatures/checksums where upstream provides them;
- produce SBOM;
- vulnerability scan;
- record build provenance;
- verify Android 64-bit / 16 KB page-size support;
- verify Apple architecture + extension safety;
- isolate core behind `EngineAdapter`;
- keep secrets outside generated logs/config when feasible;
- use restrictive filesystem permissions for transient config;
- delete transient secrets after stop/failure;
- build dependency-update and emergency-revocation process.

Do not bundle a protocol core merely because a desktop executable passed CI.

## Security design deliverables still needed

- mobile threat model;
- privacy/data-flow diagram;
- secure-storage specification;
- remote-config signing specification;
- diagnostic redaction test suite;
- dependency/SBOM policy;
- incident-response procedure;
- compromised-server/key revocation flow.

---

# 9. QA / device certification gate

Current repo has no device-verified path. Public release requires real hardware evidence.

## 9.1 Android device matrix

At minimum test representative devices across:

- Android 8/9 legacy floor if retained;
- Android 12/13;
- Android 14/15;
- Android 16;
- Samsung One UI;
- Xiaomi/HyperOS;
- Pixel/AOSP-like device;
- aggressive background-kill vendor;
- Android TV/Google TV separately if shipped.

## 9.2 Apple matrix

At minimum:

- supported minimum iOS version;
- current iOS 26;
- small and large iPhones;
- iPad if listed as supported;
- real cellular + Wi-Fi tests;
- tvOS separately if shipped.

## 9.3 Network scenarios

Every promoted engine/protocol slice should survive:

- Wi-Fi connect/disconnect;
- Wi-Fi -> cellular handover;
- cellular -> Wi-Fi handover;
- airplane mode;
- device sleep/wake;
- app foreground/background;
- app process kill/relaunch;
- VPN service/extension restart;
- server timeout/refusal;
- DNS failure;
- IPv4-only;
- IPv6 / dual-stack;
- NAT64 where available;
- captive portal;
- MTU-sensitive path;
- rapid repeated connect/disconnect;
- subscription update while disconnected and while connected according to defined behavior.

## 9.4 Performance / reliability targets

Define hard release SLOs, for example:

- crash-free sessions;
- ANR threshold;
- connection success rate;
- median/p95 connection time;
- reconnect success rate;
- battery impact;
- memory footprint of host app + VPN service/extension;
- DNS leak test pass rate;
- clean resource teardown after disconnect.

Google Play monitors crash, ANR and wake-lock quality metrics, so these are Store health requirements, not only internal polish.

---

# 10. Release engineering / CI-CD missing

Required pipelines:

## Android

- deterministic Gradle build;
- API 36 compile/target gate;
- AAB generation;
- signing outside source control;
- Play App Signing strategy;
- native ABI + 16 KB validation;
- unit/instrumentation tests;
- real-device or device-farm smoke tests;
- internal/closed/production track promotion;
- mapping/native-symbol retention for crashes.

## Apple

- Xcode 26 CI runner;
- host app + Network Extension signing;
- App Group and entitlement verification;
- archive/export validation;
- privacy manifest validation;
- TestFlight pipeline;
- symbol upload/retention;
- App Store Connect metadata/release procedure.

## Shared

- versioning/build number policy;
- release notes;
- dependency/SBOM artifact;
- reproducible engine version manifest;
- staged rollout;
- rollback / kill switch;
- emergency hotfix process.

---

# 11. Store listing / review package

Before submission we need a real publication package, not just an installable binary.

Required or expected:

- final app name and bundle/package IDs;
- final icon and adaptive icon;
- screenshots for required device classes;
- short description / subtitle;
- full description;
- category;
- support URL;
- privacy policy URL;
- terms URL;
- support email/phone as required by account type;
- age/target audience declarations;
- accurate VPN/VpnService description;
- Data Safety / App Privacy answers;
- content rating / Apple age-rating answers;
- review notes;
- active demo account or static review QR/profile;
- backend kept live during review;
- purchase/subscription reviewer access;
- Google `VpnService` review video.

Google requires reviewer access details for login/paywall-restricted apps. Apple also requires a demo account or fully-featured demo mode when account-based functionality prevents review.

Sources:

- https://support.google.com/googleplay/android-developer/answer/15748846
- https://developer.apple.com/app-store/review/guidelines/ — section 2.1

---

# 12. Accessibility / professional UX quality

Professional means the client cannot only work for the developer's phone.

Required design-system work:

- reusable typography/spacing/components;
- Persian RTL + English LTR;
- mixed-direction IP/URL/hash rendering;
- dark/light/system themes;
- dynamic type / large text;
- screen reader labels;
- sufficient contrast;
- do not convey VPN state only by color;
- reduced-motion support;
- keyboard/focus support where relevant;
- D-pad/focus model for TV builds;
- touch targets and safe-area handling;
- clear offline/loading/error/empty states.

Apple now exposes Accessibility Nutrition Labels in App Store product pages. Even while voluntary at this snapshot, building accessibility correctly from V1 avoids a later UI rewrite.

Source:

- https://developer.apple.com/help/app-store-connect/manage-app-accessibility/overview-of-accessibility-nutrition-labels

---

# 13. Recommended V1 scope

To reach a high-quality public release sooner, do **not** make V1 equal to all researched protocols.

Recommended V1 product surface:

1. Android phone/tablet.
2. iPhone/iPad.
3. account or subscription-link/QR onboarding.
4. one-tap connect.
5. auto + manual server selection.
6. quota/expiry display.
7. subscription refresh.
8. Persian + English.
9. secure DNS/routing presets.
10. reconnect/network-change handling.
11. safe diagnostics/export.
12. a deliberately selected small engine set with real device E2E evidence.
13. Store-compliant billing/account model.
14. privacy/delete-account/support surfaces.

Android TV/tvOS can follow after phone lifecycle is stable unless TV is a launch requirement.

### Engine principle

Ship only capabilities that have passed:

```text
SOURCE/LICENSE
    -> BUILD
    -> ADAPTER TEST
    -> REAL MOBILE DEVICE TUNNEL
    -> REAL DATA PATH
    -> NETWORK/LIFECYCLE MATRIX
    -> STORE-SAFE BINARY AUDIT
```

Protocol count belongs after these gates, not before them.

---

# 14. Prioritized execution plan

## P0 — Legal / Store architecture gate — BLOCKING

- [ ] establish Organization publisher entity/account path;
- [ ] D-U-N-S / legal verification;
- [ ] reserve Android package + Apple bundle IDs;
- [ ] choose account model;
- [ ] choose Store billing model;
- [ ] freeze privacy posture: no traffic monetization and minimum VPN telemetry;
- [ ] write privacy/data inventory baseline;
- [ ] choose launch platforms: recommended Android+iOS first.

**Exit criterion:** we know who publishes the app, how users authenticate/pay, and what data is legally/technically allowed to exist.

## P1 — KMP mobile foundation — BLOCKING

- [ ] add Android target/app;
- [ ] add Apple-compatible KMP targets/framework boundary where useful;
- [ ] formalize `PlatformAdapter` / `PlatformVpnService` contracts;
- [ ] secure storage abstraction;
- [ ] shared app state/navigation/view-model contracts;
- [ ] shared localization resources/RTL rules;
- [ ] canonical subscription/account/entitlement models.

**Exit criterion:** same canonical profile/account state can drive Android and iOS without an engine directly touching UI.

## P2 — Android production vertical slice — BLOCKING

- [ ] API 36 app build;
- [ ] VpnService + foreground service;
- [ ] one engine real tunnel;
- [ ] real data path;
- [ ] connect/disconnect/cleanup;
- [ ] network change/process death;
- [ ] notifications;
- [ ] secure secrets;
- [ ] disclosure/consent UX;
- [ ] 64-bit/16 KB verification;
- [ ] physical-device matrix.

**Exit criterion:** one supported protocol path is Store-architecture-correct and device verified.

## P3 — iOS production vertical slice — BLOCKING

- [ ] Xcode 26 project;
- [ ] organization signing;
- [ ] Network Extension entitlement;
- [ ] packet tunnel extension;
- [ ] App Group/Keychain;
- [ ] one Apple-compatible engine real tunnel;
- [ ] real data path;
- [ ] background/network change/reconnect;
- [ ] real iPhone device matrix;
- [ ] privacy manifest.

**Exit criterion:** one supported tunnel path is real-device verified under Apple extension constraints.

## P4 — Consumer product / backend

- [ ] auth/login;
- [ ] entitlement/quota/expiry;
- [ ] subscription/config API;
- [ ] server catalog/health;
- [ ] smart selection;
- [ ] account deletion;
- [ ] privacy choices/support;
- [ ] Store purchase integration if selected;
- [ ] billing event reconciliation;
- [ ] feature flags/min-version control.

## P5 — Professional UX / reliability

- [ ] final design system;
- [ ] onboarding;
- [ ] home/server/profile/settings/diagnostics screens;
- [ ] accessibility;
- [ ] Persian/English QA;
- [ ] leak/IPv6/captive portal/lifecycle test suite;
- [ ] crash/ANR/battery SLOs;
- [ ] privacy-safe observability.

## P6 — Store release gate

- [ ] privacy policy + terms published;
- [ ] Google Data Safety complete;
- [ ] Google VpnService declaration + video;
- [ ] Apple App Privacy complete;
- [ ] review account/QR ready;
- [ ] screenshots/listing/localization;
- [ ] subscription metadata/reviewer access if monetized;
- [ ] TestFlight / Play closed track beta;
- [ ] staged release + rollback plan;
- [ ] support workflow staffed.

---

# 15. What should happen next in this repository

The next coding task should **not** be another broad protocol-import wave.

The next product milestone should be a written mobile design + implementation plan that starts with:

1. exact Android/iOS module layout;
2. `PlatformVpnService` contract;
3. secure storage contract;
4. app/account/subscription state model;
5. first engine selected for Android real-device tunnel;
6. first engine selected for iOS real-device tunnel;
7. UI state machine and required screens;
8. device/network acceptance test matrix;
9. Store/privacy/billing acceptance checklist.

Until those exist, adding more protocol adapters increases technical inventory but does not materially reduce the distance to a public Store release.
