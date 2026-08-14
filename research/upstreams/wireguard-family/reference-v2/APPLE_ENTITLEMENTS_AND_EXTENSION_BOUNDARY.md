# WireGuard / AmneziaWG Apple — entitlement and extension boundary

Status: source-evidence slice for entries 002/003; **not a device receipt, Store receipt, or COMPLETE-REFERENCE-v2**.

## Scope and pins

This note records source-level signing/capability structure for the official WireGuard Apple tree and the standalone AmneziaWG Apple fork. It deliberately separates source capability declarations from proof about a shipped App Store binary.

- WireGuard source pin used here: commit `2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`.
- standalone AmneziaWG Apple source pin used here: `fe2e2d7e` (the previously pinned current revision in this campaign).

## WireGuard iOS app target

`Sources/WireGuardApp/UI/iOS/WireGuard.entitlements` declares:

- `com.apple.developer.networking.networkextension` with `packet-tunnel-provider`;
- `com.apple.developer.networking.wifi-info = true`;
- `com.apple.security.application-groups` containing `group.$(APP_ID_IOS)`.

Therefore the source model expects the containing iOS app to possess Network Extension packet-tunnel capability and to share an App Group derived from the configured iOS application identifier.

## WireGuard iOS Network Extension target

`Sources/WireGuardNetworkExtension/WireGuardNetworkExtension_iOS.entitlements` declares:

- `com.apple.developer.networking.networkextension` with `packet-tunnel-provider`;
- the same `group.$(APP_ID_IOS)` application group.

The shared group on app and extension is a concrete source-level boundary for cross-target shared state. It is not evidence that an arbitrary downstream build has a valid provisioning profile for those entitlements.

## standalone AmneziaWG Apple fork

At the pinned AmneziaWG revision, the corresponding iOS app and Network Extension entitlement files preserve the same structural declarations:

- app target: packet-tunnel-provider + Wi-Fi information + `group.$(APP_ID_IOS)`;
- Network Extension target: packet-tunnel-provider + `group.$(APP_ID_IOS)`.

This is strong reuse evidence: the standalone AWG Apple fork retains the WireGuard Apple app/extension entitlement architecture while substituting AWG protocol implementation pieces elsewhere in the fork.

## Integration consequence for PVNetwork

A PVNetwork iOS client cannot treat WireGuardKit/AWG code as a normal in-process library only. A production packet tunnel requires a separately signed Network Extension target with the packet-tunnel-provider entitlement and an App Group aligned between the containing app and extension. Bundle identifiers, App Group identifiers, Developer Team, provisioning profiles and Store capabilities must be owned by the PVNetwork app rather than copied literally from upstream placeholders.

This also creates a release-engineering gate: successful source compilation is insufficient. The release candidate must prove signing/provisioning, extension embedding, tunnel start/stop on a real Apple device, app↔extension shared-state access, and Store/TestFlight entitlement acceptance.

## What is not established

This source audit does **not** establish:

- the exact entitlements embedded in any currently shipped WireGuard or AmneziaWG App Store binary;
- App Store version ↔ source commit correspondence;
- PVNetwork Team ID, bundle IDs, App Group ID or provisioning profile;
- successful installation/tunnel operation on an Apple device;
- whether all Wi-Fi-information uses remain necessary for a future PVNetwork fork.

Those require Store metadata/build provenance and/or Apple signing/device infrastructure. They remain explicit gates rather than inferred PASS results.

## Evidence paths

WireGuard pin:

- `Sources/WireGuardApp/UI/iOS/WireGuard.entitlements`
- `Sources/WireGuardNetworkExtension/WireGuardNetworkExtension_iOS.entitlements`

AmneziaWG Apple pin:

- `Sources/WireGuardApp/UI/iOS/WireGuard.entitlements`
- `Sources/WireGuardNetworkExtension/WireGuardNetworkExtension_iOS.entitlements`

## Remaining Apple gate

Next source-only work can inventory Xcode target product bundle identifiers/config templates and release metadata. Device signing, TestFlight/App Store provenance and runtime tunnel receipts remain externally blocked until suitable Apple infrastructure exists.
