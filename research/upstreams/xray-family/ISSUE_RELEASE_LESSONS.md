# Xray Family — Issue / Release / Regression Lessons

Research date: 2026-08-14

State: `IN-RESEARCH`; failure-class evidence for future tests. An issue report is not automatically a verified vulnerability or universal bug.

## Evidence rule

Classify issue/release evidence as one of:

- upstream-fixed/release-confirmed;
- source/commit-confirmed;
- reproducible client regression with a merged fix;
- open/unverified report;
- compatibility discussion/operational report.

Do not turn a user report into a product claim without corroborating source/fix/release evidence.

## Xray-core — main ahead of stable release

Current stable release observed: `v26.3.27`.

Pinned main head: `7d214f8b094f75322fa3990f8aadad1c912f24f5` from 2026-08-12.

The head commit itself closes issue #6559 and fixes WireGuard outbound `sendThrough` behavior.

### PVNetwork lesson

- release-vs-main diff matters;
- non-headline protocol modules inside a multi-protocol core can regress;
- engine-upgrade tests must cover every engine capability PVNetwork actually uses, not only VLESS/REALITY.

## Xray-core issue #6559 — routing/bind behavior

Status at review: closed/completed 2026-08-12 with the pinned head fix.

The report identified different behavior between a stable release and later versions around WireGuard outbound source/gateway selection and an invalid local bind scenario.

PVNetwork regression category:

- generated routing/source-address settings must be validated against actual platform-local addresses;
- error mapping should distinguish bind/config errors from general connectivity failure;
- upgrades require routing behavior tests across supported outbound families.

## XHTTP / configuration-default regression class

Current Xray issue history contains reports where changed/default XHTTP/XMUX-related behavior caused large differences in connection/resource behavior across client/core versions. Issue #6376 is a recent example discussed and closed in 2026.

Do not preserve that report as universal performance truth. Preserve the engineering class:

- defaults can change between core versions;
- clients may omit a field and unknowingly inherit new semantics;
- server/client core-version mismatch can create difficult-to-diagnose behavior;
- "unset" is not always equivalent to a stable explicit default.

PVNetwork requirements:

- canonical profiles distinguish explicit user value from core default/unspecified;
- runtime config generator records the core version used;
- upgrades run compatibility tests for omitted/defaulted fields;
- diagnostics should show effective generated settings without exposing secrets.

## VLESS / flow / transport combination reports

Issue history contains real-world reports about behavior differences across combinations such as VLESS, Vision, REALITY and XHTTP. Some are closed as configuration/compatibility discussions rather than accepted core bugs.

PVNetwork lesson:

- validate protocol + flow + security + transport as a combination;
- do not allow every UI option to combine freely;
- a support badge must refer to tested combinations/core versions, not isolated feature names.

## Security/detectability claims in public issues

The Xray issue tracker also contains contested claims about traffic/protocol detectability. Example issue #6091 received substantial discussion and was closed as not planned.

Research rule:

- do not record a public issue title as a proven cryptographic/security fact;
- require reproducible technical evidence, upstream response, independent research or a security advisory before changing security claims;
- keep protocol-identification/censorship-evasion research separate from PVNetwork's basic interoperability and product-security claims.

This repository should remain an engineering reference, not a collection of unverified forum assertions.

## v2rayN current TUN/DNS regression lesson

Pinned v2rayN head:

`e01717d8326a4f5060b335523590c5fda943fe03`

The 2026-08-10 head commit fixes a generated TUN self-address rejection rule that could cover an address used by the system resolver, causing system DNS to fail while the proxy path itself remained healthy. The change also restores/extends regression tests.

PVNetwork requirements:

- test system DNS independently of core connectivity;
- generated TUN route rules require semantic tests;
- test resolver addresses inside TUN interface prefixes;
- prevent route loops without rejecting legitimate system-resolver traffic;
- keep user-visible state capable of distinguishing “engine up” from “system network usable”.

## v2rayNG localization/import semantics lesson

Current v2rayNG head reviewed:

`e8a82d9810ca1cf97a3cc8a9b9525a9f21955807`

The current commit refines localized labels and explicitly distinguishes copying a profile share link from copying a fully generated configuration.

PVNetwork lesson:

- share-link export and generated-engine-config export are different actions;
- UI/localization must use different terminology;
- import/export regression tests should compare semantic content, not only text parsing success.

## Failure classes to add to future PVNetwork tests

### Core/config

- omitted-field default changes across core versions;
- removed/deprecated transport migration;
- protocol/security/flow/transport invalid combinations;
- source/bind/routing option changes;
- client/server core-version mismatch;
- config parser accepts data but runtime rejects it.

### TUN/DNS/routing

- engine connected but system DNS broken;
- own-interface address routing loops;
- resolver address incorrectly rejected;
- IPv4/IPv6 route asymmetry;
- route/DNS cleanup after crash or reconnect.

### Import/export

- share-link vs full-config confusion;
- lossy import silently dropping routing/DNS/policy semantics;
- core-specific fields lost during canonical normalization;
- unknown future fields destroyed during edit/save.

### UI/core state

- UI reports connected while platform network path is unusable;
- selected core differs from core that generated the current config;
- stale statistics after reconnect/core switch;
- subscription refresh changes effective profile without visible diff.

## Remaining issue research

- systematic current open/closed issue matrix for Xray-core by protocol/transport;
- release-note mapping from stable v26.3.27 to current main;
- v2rayN issue categories beyond the current TUN/DNS fix;
- v2rayNG Android VpnService/process/network-change issues;
- Hiddify/Karing/NekoBox/Throne issue-derived product lessons;
- authoritative security advisory/dependency review;
- performance regressions tied to exact releases.
