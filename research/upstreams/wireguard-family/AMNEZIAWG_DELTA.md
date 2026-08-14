# AmneziaWG — Compatibility / Architecture Delta

Pinned source: `amnezia-vpn/amneziawg-go@1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`.

Research state: `IN-RESEARCH`.

## Relationship to WireGuard

GitHub identifies `amnezia-vpn/amneziawg-go` as a fork derived from `WireGuard/wireguard-go`. The reviewed upstream documentation describes it as retaining the WireGuard-derived architecture while adding an expanded compatibility/configuration surface intended to make its wire behavior less distinctive.

For PVNetwork, this must be treated as a **separate compatibility capability**, not as a cosmetic WireGuard profile option.

## Why a separate adapter capability is required

The reviewed source/documentation exposes additional protocol/configuration concepts beyond a standard WireGuard profile. The exact set has evolved across AmneziaWG revisions. Therefore PVNetwork should not flatten AmneziaWG into the standard WireGuard model and silently discard unknown fields.

Recommended product architecture:

- shared UI/profile concepts where they are truly common;
- a versioned `AmneziaWGProfileExtension` or equivalent internal extension model;
- a dedicated adapter capability/version identifier;
- explicit import validation against the selected engine version;
- round-trip preservation of supported extension fields;
- clear warning for unsupported or lossy conversion;
- independent interoperability tests against compatible upstream implementations.

## Core/client separation

`amneziawg-go` is the userspace Go core reviewed here. Its repository metadata reports MIT at the pinned revision.

The Amnezia desktop/mobile application is a separate codebase and must be audited under its own license and architecture. Never infer the application license from the core or the core license from the application.

The upstream README also points to platform-specific AmneziaWG projects for Windows and Apple platforms. Those should be reviewed as platform references before PVNetwork chooses its final per-platform implementation strategy.

## Safe research boundary

The upstream documentation contains detailed tuning and packet-shaping parameters. PVNetwork research should record their **existence, schema/versioning impact, parser requirements and interoperability implications**, but this repository should not become an operational guide for traffic-evasion tuning.

## Canonical profile-model implications

The future PVNetwork canonical model should support:

1. common WireGuard-compatible identity/network fields;
2. a namespaced/versioned AmneziaWG extension object;
3. source-format metadata for round-trip export;
4. engine compatibility constraints;
5. migration rules when the supported AmneziaWG schema changes;
6. validation errors that identify unsupported extension semantics rather than dropping them.

## Update/version strategy

Because compatibility behavior can change with the upstream protocol/core, PVNetwork should pin the supported AmneziaWG engine version and couple parser/schema compatibility to that version. Engine updates must pass an interoperability regression matrix before promotion.

The application should not automatically assume that a profile created for a newer upstream schema can be safely executed by an older embedded core.

## PVNetwork acceptance tests derived from this delta

- standard WireGuard profiles remain semantically separate from AmneziaWG profiles;
- import/export round trip preserves all supported AmneziaWG extension fields;
- unknown/new extension fields produce an explicit compatibility result;
- engine version and profile schema compatibility are checked before activation;
- migration never silently converts an AmneziaWG profile to ordinary WireGuard;
- common profile UI remains simple while advanced compatibility data is preserved;
- adapter errors are normalized without erasing the underlying compatibility cause;
- interoperability is tested against the exact pinned upstream family/version before release.

## Reuse decision — current stage

- `amneziawg-go`: `REUSE-CANDIDATE`, pending dependency, platform and packaging review.
- Amnezia application UI/source: separate audit; do not copy by association.
- AmneziaWG compatibility in PVNetwork: keep separate from standard WireGuard under the Core Adapter layer.

## Remaining gaps

- recursive source-tree/module delta review against the pinned WireGuard base;
- platform-specific AmneziaWG Windows/Apple/Android implementation review;
- current upstream release/change history and migration notes;
- dependency/SBOM and attribution audit;
- exact profile schema/version compatibility matrix;
- real interoperability/performance testing.