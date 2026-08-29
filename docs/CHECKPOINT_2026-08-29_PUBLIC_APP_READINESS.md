# Checkpoint — Public Mobile App Readiness

Date: 2026-08-29
Repository: `DashSaman/PVN-amirrezagol`

## Completed in this slice

- Audited current repository mobile/product readiness against current Google Play and Apple App Store requirements for a public VPN application.
- Added `app/PRODUCTION_READINESS_GAP_ANALYSIS.md`.
- Updated `app/README.md` to index the readiness analysis and reflect that mobile/product/store engineering is now the primary distance-to-market bottleneck.

## Repository truth captured

- `apps:desktop` exists; Android/iOS app modules do not.
- `core:foundation` uses Kotlin Multiplatform but currently configures JVM only.
- `docs/PROJECT_STATE.md` still correctly states `DEVICE VERIFIED: none` and `PRODUCTION READY: no`.
- Existing protocol/runtime evidence is useful foundation but does not certify a mobile Store release.

## Current external Store blockers

- Apple VPN apps require organization-enrolled publisher and Apple VPN/Network Extension architecture/privacy compliance.
- Google Play requires an Organization account for apps approved to use `VpnService` under current/upcoming Play Console requirements; D-U-N-S/verification is part of organization onboarding.
- Starting 2026-08-31, new Android phone/tablet apps and updates submitted to Play must target Android 16 / API 36+.
- Native Android cores must pass 64-bit and 16 KB page-size compatibility requirements.
- Since 2026-04-28, Apple submissions require Xcode 26+ and iOS/iPadOS/tvOS 26 SDK or later.
- Google `VpnService` declaration/disclosure/video/data declarations are required.
- Apple VPN data-use restriction, App Privacy, privacy policy and privacy-manifest requirements must be designed into the product.
- In-app digital VPN subscriptions require a Store-compliant billing model; account deletion paths are required if account creation is offered.

## Recommended next milestone

Do not prioritize another broad protocol-count wave.

Start a mobile product design/implementation plan covering:

1. organization/publisher + package/bundle identity;
2. account and billing model;
3. Android/iOS KMP module layout;
4. `PlatformVpnService` contract;
5. secure storage contract;
6. first Android real-device tunnel slice;
7. first iOS real-device packet-tunnel slice;
8. subscription/account/quota/expiry UX;
9. privacy/data model;
10. device/network acceptance matrix;
11. Store submission checklist.

The detailed P0-P6 gates and acceptance criteria are in `app/PRODUCTION_READINESS_GAP_ANALYSIS.md`.
