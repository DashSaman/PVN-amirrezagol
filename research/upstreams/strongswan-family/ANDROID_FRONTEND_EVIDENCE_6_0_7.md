# strongSwan Android frontend evidence — release 6.0.7

Research date: 2026-08-14

State: `EVIDENCE-BACKED V1 RESEARCH / NOT IMPLEMENTED / NOT STORE-CERTIFIED`.

Pinned upstream release commit:

`5973ff8e41deef4e015e1138a2de688acedf6f75` (`6.0.7`)

This note maps the Android frontend at that immutable revision. It is a source/reference study for PVNetwork, not a decision to copy the GPL application.

## Source layout

Pinned source contains:

`src/frontends/android/`

with Gradle/Android application structure plus NDK/native integration. The main Java package is split into explicit domains:

- `data/`
- `logic/`
- `security/`
- `ui/`
- `utils/`

Primary tree evidence:

- `https://github.com/strongswan/strongswan/tree/5973ff8e41deef4e015e1138a2de688acedf6f75/src/frontends/android`
- `https://github.com/strongswan/strongswan/tree/5973ff8e41deef4e015e1138a2de688acedf6f75/src/frontends/android/app/src/main/java/org/strongswan/android`

PVNetwork lesson: Android is not just a thin GUI around desktop `charon`; upstream has a dedicated Android application/service/data layer around native strongSwan code.

## Build/toolchain pin

At the 6.0.7 source pin, `src/frontends/android/app/build.gradle` records:

- application ID: `org.strongswan.android`
- compile SDK: `36`
- target SDK: `36`
- min SDK: `21`
- Android app version: `2.6.2`
- version code: `96`
- NDK: `27.3.13750724`
- native build: `ndkBuild` using `src/main/jni/Android.mk`
- Java compatibility: 1.8

Declared Java dependencies include pinned AndroidX/Material versions and test dependencies. This is evidence for the upstream Android build only; PVNetwork must resolve its own dependency/SBOM and current Store/API requirements at implementation time.

Primary evidence:

`https://github.com/strongswan/strongswan/blob/5973ff8e41deef4e015e1138a2de688acedf6f75/src/frontends/android/app/build.gradle`

## Runtime/service boundary

The pinned manifest declares `CharonVpnService` with:

- `android.permission.BIND_VPN_SERVICE`
- an `android.net.VpnService` intent filter
- `foregroundServiceType="specialUse"`
- a special-use foreground-service property identifying the `VpnService` instance

The application also declares foreground-service and notification permissions, network-state access, and other permissions required by its feature set.

Source path:

`src/frontends/android/app/src/main/java/org/strongswan/android/logic/CharonVpnService.java`

This is direct evidence that Android traffic capture/tunnel ownership is implemented through the Android VPN service model. PVNetwork must own Android permission, foreground-service, lifecycle, reconnect, and route/DNS behavior explicitly even if strongSwan native components are reused.

## Main Android data/storage map

Pinned `DatabaseHelper.java` uses SQLite database:

`strongswan.db`

The profile table schema includes fields for:

- UUID and profile name;
- gateway and VPN type;
- username;
- a `password` TEXT field;
- certificate references;
- MTU and port;
- split tunneling;
- local/remote IDs;
- excluded/included subnets;
- selected-app mode/list;
- NAT keepalive;
- flags;
- explicit IKE and ESP proposals;
- DNS servers;
- proxy host/port/exclusions.

Additional tables cover trusted certificates and user certificates; the user-certificate table also contains a password field.

Primary evidence:

`https://github.com/strongswan/strongswan/blob/5973ff8e41deef4e015e1138a2de688acedf6f75/src/frontends/android/app/src/main/java/org/strongswan/android/data/DatabaseHelper.java`

### PVNetwork security decision

The source-level schema proves that secret-looking fields exist as SQLite `TEXT` columns. This research does **not** claim whether every value is encrypted, transformed, transient, or protected elsewhere in upstream code.

PVNetwork must therefore **not copy this persistence model blindly**. Product architecture requires:

- reusable passwords/PSKs/private-key secrets behind Android secure-storage references;
- non-secret profile fields stored separately;
- explicit migration/export policy;
- redaction in logs/backups;
- no assumption that a field is safe merely because upstream persists it.

## Managed configuration / enterprise path

The pinned data tree contains classes including:

- `ManagedConfiguration`
- `ManagedConfigurationService`
- managed trusted/user certificate repositories

The manifest declares Android managed configuration metadata (`android.content.APP_RESTRICTIONS`).

This is useful enterprise reference evidence: managed/provisioned profiles are a distinct source of configuration and should not be conflated with user-edited local profiles.

PVNetwork canonical model should track profile provenance/read-only policy separately from the protocol settings themselves.

## UI / menu surface from source

Pinned manifest and `ui/` source expose major application surfaces including:

- `MainActivity`
- `VpnProfileDetailActivity`
- `VpnProfileControlActivity`
- `VpnProfileSelectActivity`
- `VpnProfileImportActivity`
- `TrustedCertificatesActivity`
- `TrustedCertificateImportActivity`
- `SelectedApplicationsActivity`
- `SettingsActivity`
- `LogActivity`
- remediation instruction/state screens
- Quick Settings tile service (`VpnTileService`)

Profile import accepts the strongSwan profile MIME type and `.sswan` file patterns through VIEW intents. The manifest also exposes start/disconnect profile actions through `VpnProfileControlActivity`.

PVNetwork lessons:

1. profile import is a first-class workflow and requires validation before persistence/use;
2. certificate management is a separate UI/security domain;
3. per-app include/exclude selection is a separate Android routing capability, not a generic protocol field;
4. diagnostics/log access is an explicit product surface;
5. quick-tile/external action entry points require authorization/state handling and should not bypass canonical profile validation.

Primary evidence:

- `https://github.com/strongswan/strongswan/blob/5973ff8e41deef4e015e1138a2de688acedf6f75/src/frontends/android/app/src/main/AndroidManifest.xml`
- `https://github.com/strongswan/strongswan/tree/5973ff8e41deef4e015e1138a2de688acedf6f75/src/frontends/android/app/src/main/java/org/strongswan/android/ui`

## Permission/Store review surface

Pinned manifest requests or declares capabilities including:

- Internet/network state;
- foreground service + special-use foreground service;
- notifications;
- external-storage read permission;
- ignore-battery-optimizations request;
- system-alert-window;
- `QUERY_ALL_PACKAGES` for selected-app/EAP-TNC behavior.

These are **upstream source facts**, not automatic PVNetwork requirements. Several permissions are sensitive or policy-relevant. PVNetwork must justify each permission against the exact shipped feature set and re-check current Google Play requirements before release.

The manifest explicitly sets `android:allowBackup="false"`, which is a useful upstream security/privacy design reference but not sufficient proof that every secret is protected at rest.

## Adapter/reuse decision

Android decision at v1 research layer:

`REFERENCE + ENGINE CANDIDATE / DO NOT COPY FRONTEND BY DEFAULT`

Rationale:

- strongSwan provides mature Android-specific VPN-service/native integration evidence;
- the application/frontend is GPL-family source and carries product/license consequences;
- PVNetwork needs its own unified product UI, canonical profile model, localization, secure persistence and Core Adapter boundary;
- Android native platform IKEv2 capabilities remain a separate backend option and must be compared feature-by-feature.

Recommended boundary:

`PVNetwork UI / canonical profile / secure secret refs`

`-> IPsec Core Adapter`

`-> Android native IKEv2 backend OR reviewed strongSwan Android/native backend`

`-> Android VpnService / OS networking`

## Gaps remaining after this note

This closes a substantial portion of the source-level Android architecture/storage/menu gap, but not:

- complete method-by-method UI map;
- actual runtime secret-at-rest verification;
- current Play Store policy approval;
- real-device battery/reconnect/per-app/DNS/IPv6 tests;
- exact strongSwan-vs-native Android capability matrix;
- final selected backend/build/SBOM.
