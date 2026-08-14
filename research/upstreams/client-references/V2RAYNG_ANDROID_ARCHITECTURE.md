# v2rayNG — Android Source / Service / Platform Architecture

Research date: 2026-08-14

State: `IN-RESEARCH / REFERENCE-ONLY`. This is a GPLv3 client-reference dossier, not a PVNetwork code-reuse approval.

## Current pin

Repository: `2dust/v2rayNG`

Pinned current head:

`e8a82d9810ca1cf97a3cc8a9b9525a9f21955807`

Pinned tree used for recursive source inventory:

`1132f098b850c27426f43a7f8cb86e62c39b65a7`

Application license: GPLv3.

## Current Android build baseline

Pinned `V2rayNG/app/build.gradle.kts` currently declares:

- namespace/application ID `com.v2ray.ang`;
- compile SDK 37;
- target SDK 37;
- minimum SDK 24;
- Java/Kotlin JVM target 17;
- Compose enabled;
- `fdroid` and `playstore` product flavors;
- ABI split support for arm64-v8a, armeabi-v7a, x86_64 and x86 plus universal packaging;
- MMKV, Gson, OkHttp, CameraX, ZXing, coroutines, WorkManager and testing dependencies;
- locale filters including Persian and Arabic.

These are v2rayNG current choices, not PVNetwork target/minimum SDK decisions.

## Source structure

Current upstream's own `docs/AGENTS.md` and source tree identify major domains:

### `core/`

- `CoreServiceManager.kt` — active core lifecycle/state, generated config launch, network handover/reload, stats and callbacks;
- `CoreConfigManager.kt` — engine config generation;
- `CoreNativeManager.kt` — native AAR integration;
- outbound/context builder components.

### `service/`

Current Android services include:

- `CoreVpnService` — Android VPN mode;
- `CoreProxyOnlyService` — local proxy-only mode;
- `CoreRootService` / root-related mode;
- `CoreTestService` — test operations;
- `TProxyService` — tun2socks integration path;
- browser/native dialer services;
- background real-ping/update services;
- quick settings tile service;
- subscription update service.

### `handler/`

Business/data helpers include:

- `MmkvManager`;
- `SettingsManager`;
- `AngConfigManager`;
- subscription updater;
- backup/WebDAV/update/certificate-fingerprint/notification/speed-test managers.

### `ui/`

Current source has:

- main Compose-based product screen;
- dedicated protocol profile editors;
- subscriptions;
- routing;
- per-app proxy/app picker;
- settings;
- logs;
- backup/restore;
- update/about;
- scanner and shortcut/Tasker surfaces.

### `fmt/`

Protocol URL/share-link parsing/formatting.

### `dto/entities/`

Product-owned profile/subscription/routing/asset models.

## Native core boundary

The repository currently references a native AAR through its `AndroidLibXrayLite`/related submodule ecosystem and `app/libs/` integration.

This is architecturally important: v2rayNG does not implement the Xray engine directly in the activity layer. Android product code owns profile/settings/service lifecycle and bridges to a native core controller.

PVNetwork lesson:

**keep Android product lifecycle above a narrow native engine adapter.**

## Dedicated daemon process

The pinned manifest places the VPN/core-related service set in a dedicated process named `:RunSoLibV2RayDaemon`.

Benefits/lessons to evaluate for PVNetwork:

- isolates long-running core/VPN service from main UI process;
- can reduce UI-process lifecycle coupling;
- requires explicit multi-process persistence/state synchronization;
- service/UI state can become stale after process death/restart if not modeled carefully.

Do not copy the exact process strategy without measuring Android memory/process limits and Store policy requirements.

## VPN vs proxy-only modes

Pinned architecture supports at least:

### VPN mode

`CoreVpnService : VpnService`

The service owns Android VPN permission/interface creation and platform route/DNS/per-app configuration.

### Proxy-only mode

A separate foreground service path runs the core without Android VpnService TUN ownership.

PVNetwork should likewise model connection mode separately from the selected application protocol/core.

## Android VPN lifecycle evidence

Pinned `CoreVpnService.kt` demonstrates a significant amount of product-owned lifecycle outside the Xray core:

- `VpnService.prepare` permission state;
- foreground notification ownership;
- interface establishment/closure;
- MTU/address/route/DNS configuration;
- IPv6 option;
- per-app allow/bypass behavior;
- optional platform HTTP proxy setting on newer Android versions;
- tun2socks mode selection;
- revoke/destroy cleanup;
- always-on/system restart handling;
- startup lock to reduce duplicate concurrent starts.

This confirms again that a native Xray wrapper alone cannot implement a production Android VPN app.

## Network handover behavior

Pinned `CoreServiceManager.kt` monitors underlying network changes and can reload the core while keeping service/interface state alive. The source comment explains that generated config is rebuilt because resolved outbound server addresses can become invalid after network handover.

PVNetwork regression requirements:

- Wi-Fi -> mobile and mobile -> Wi-Fi transitions;
- DNS/resolved endpoint changes;
- core reload while TUN stays active;
- notification/UI state during reload;
- no stale sockets/routes from previous underlying network;
- cancel/reload race handling.

## Start/stop ordering lesson

Pinned `CoreVpnService.stopAllService` documents a real lifecycle sensitivity: stop ordering between service/core/interface can affect whether old core sockets/ports are fully released. The code also uses a short delay before closing the VPN interface to avoid a race affecting UI/status-bar state.

PVNetwork lesson:

- teardown ordering is part of correctness;
- write deterministic tests for repeated start/stop cycles;
- do not assume `core.stop()` alone means platform TUN/service state is clean;
- avoid magic sleeps in final architecture where an explicit lifecycle signal can replace them.

## Always-on / restart behavior

Pinned manifest declares Android Always-On support metadata and boot/package-replacement receivers. Current service code explicitly handles OS/system starts and killed-process restart conditions.

PVNetwork Android acceptance tests must include:

- user-started session;
- always-on system start;
- app/process kill;
- OS reboot;
- package update/replacement;
- permission revoke;
- notification/foreground-service lifecycle.

## Android TV signal

Pinned manifest includes:

- optional Leanback feature;
- Leanback launcher category on MainActivity;
- touchscreen not required;
- application TV banner;
- KEYCODE_BUTTON_B handling in main activity.

This is evidence that v2rayNG attempts TV compatibility, but it is **not proof of a full 10-foot/D-pad-optimized Android TV UX**.

PVNetwork must separately test focus traversal, D-pad-only onboarding, TV QR/pairing, readability, no-touch operation and Play TV policy.

## Permissions / Store lessons

Pinned manifest requests capabilities including network/VPN/foreground/boot/camera/notification and `QUERY_ALL_PACKAGES`. Some have explicit policy-ignore annotations in source.

PVNetwork must not copy this permission set automatically. Every permission must be justified against current Google Play rules and actual PVNetwork features.

Per-app routing may motivate package visibility requirements, but Store feasibility must be independently reviewed at release time.

## Localization / RTL

Pinned manifest sets `supportsRtl=true`. Build locale filters include `fa` and `ar`.

This is valuable reference evidence, but source-level RTL enablement alone is not enough for PVNetwork. Required tests still include:

- IP/port/URL mixed-direction strings;
- profile cards;
- dialogs;
- logs;
- route rules;
- protocol/transport English tokens inside Persian;
- TV layouts;
- accessibility.

## Current test evidence caution

Current upstream `docs/AGENTS.md` says unit tests exist and describes no meaningful instrumented test suite; the repository tree itself includes GitHub workflow files, so that guide's statement “No CI” must not be accepted uncritically without reading the current workflow.

PVNetwork research rule:

**upstream AGENTS/README are clues, source/tree/workflows are the final evidence.**

## PVNetwork reuse decision

v2rayNG application code is GPLv3 and should be **REFERENCE-ONLY by default for a closed commercial PVNetwork product**.

Useful concepts to learn from:

- service/core separation;
- dedicated daemon process;
- profile/settings/business managers;
- Android VPN lifecycle;
- network handover/reload;
- per-app routing;
- Android TV compatibility attempts;
- import/UI structure.

For actual Xray integration, narrow core/wrapper candidates such as Xray-core/libXray should be evaluated separately.

## Remaining gaps

- current workflow/CI exact audit;
- exact native submodule/core version relationship;
- full `CoreNativeManager` API map;
- detailed menu/screen inventory in separate file;
- full storage/credential review in separate file;
- import/subscription parser round-trip/lossiness audit;
- current issues for Android 14/15/16/TV/background/Always-On behavior;
- Play/F-Droid distribution differences;
- exact Android TV UI behavior on emulator/real TV.
