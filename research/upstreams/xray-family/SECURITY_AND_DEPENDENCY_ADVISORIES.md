# Xray-core — Security / Dependency Advisory Review

Research date: 2026-08-14

State: `IN-RESEARCH`; security evidence for component selection. This file does not claim PVNetwork security certification.

## Source policy

Primary evidence reviewed:

- `XTLS/Xray-core/SECURITY.md`;
- repository-published GitHub Security Advisories;
- current releases/tags;
- pinned `go.mod` and source/release evidence.

Do not rely only on a version string from a client GUI.

## Security reporting process

Pinned `SECURITY.md` directs vulnerability reporters to GitHub private security reporting for the repository.

This establishes an upstream disclosure channel but does not replace PVNetwork's own dependency/advisory monitoring.

## Current published repository advisory

### GHSA-5wf9-h793-w73c

Published: **2026-07-10**.

Repository summary: certificate pinning through `pinnedPeerCertSha256` could, in certain Hy2/gRPC cases, allow a certificate-hostname mismatch scenario that could enable MITM when a CA certificate is pinned and effective hostname verification is absent.

Repository severity: **low**.

CWE recorded by upstream: **CWE-297 — Improper Validation of Certificate with Host Mismatch**.

Upstream vulnerable range:

`>= v26.1.13`

Upstream patched range:

`>= v26.7.11`

### Critical release-selection consequence

GitHub's current “latest non-prerelease” endpoint returned **v26.3.27**, which falls inside the upstream advisory's vulnerable version range.

At the same time, the release list contains newer prerelease builds such as **v26.7.28**, which are newer than the patched threshold.

Therefore:

**PVNetwork must not select v26.3.27 merely because GitHub marks it as latest non-prerelease.**

Release selection requires:

- advisory-state check;
- newer patch/prerelease/main review;
- exact feature/regression tests;
- final risk decision.

The current research main pin from 2026-08-12 is newer than the patched threshold but still must not be shipped solely because it is newer.

## Certificate validation / pinning product rules

PVNetwork should treat certificate options as security-sensitive policy, not convenience text fields.

Requirements:

- distinguish leaf-certificate pin vs CA pin where supported;
- maintain hostname/server-name verification semantics;
- do not automatically combine pinning with disabled hostname verification;
- advanced `insecure`/verification-bypass controls require clear warnings and must not be default;
- imported profiles with unsafe verification semantics must be surfaced explicitly;
- security-layer UI should explain effective verification, not only SNI text.

Regression tests must cover endpoints expressed as both domain names and IP addresses.

## Main/stable/prerelease policy

Current source/release landscape demonstrates three different states:

1. **latest non-prerelease** — may lag security fixes;
2. **newer prerelease** — may contain fixes but also newer behavior/config changes;
3. **current main** — may contain fixes after release plus untagged changes.

PVNetwork component policy should be:

- no automatic trust in `releases/latest`;
- compare all security advisories against candidate version;
- prefer an upstream-patched tagged release when one meets product quality gates;
- if only prerelease/main contains a required security fix, document the decision and increase regression coverage;
- keep rollback package/version available.

## Exact build dependency risk

Pinned Xray `go.mod` includes substantial third-party networking/security dependencies.

Important categories include:

- `github.com/refraction-networking/utls`;
- `github.com/xtls/reality`;
- QUIC implementation;
- `golang.org/x/crypto`;
- DNS/network libraries;
- gRPC/protobuf;
- WireGuard/Wintun;
- Shadowsocks implementations;
- gVisor/network stack components;
- platform routing/netlink dependencies;
- serialization/compression/support libraries.

A secure Xray-core version can still ship with a vulnerable dependency if the exact build graph is not reviewed.

## Mandatory SBOM/advisory gate

For every candidate Xray/libXray build:

1. pin Xray-core commit/tag;
2. pin libXray wrapper commit if used;
3. resolve exact Go module graph;
4. record separate native/bundled assets such as Wintun and GeoData;
5. record all licenses;
6. run current Go/module vulnerability scanning where available in the release environment;
7. review upstream repository advisories;
8. review security-relevant commits since previous shipped pin;
9. run TLS/certificate/import/routing regression tests;
10. archive SBOM and hashes with the PVNetwork release evidence.

## Configuration security boundary

Xray's flexible configuration can expose risky user choices even when engine code is patched.

PVNetwork needs policy around:

- `insecure`/certificate validation overrides;
- SNI/server-name/fingerprint settings;
- certificate pinning;
- local management/API listeners;
- exposed inbound/listen addresses;
- LAN sharing;
- local proxy credentials;
- DNS routing/leak behavior;
- logs containing generated config/secrets.

Simple Mode should not encourage unsafe combinations.

## Management API risk

`XRAY_API_CONTROL.md` shows Commander/proxyman/router/stats can expose mutable and potentially privacy-sensitive runtime operations.

Treat any management endpoint as privileged/private. Do not expose it to LAN/public interfaces by default or let plugins/UI pass arbitrary core commands.

## Client-side storage risk

Current v2rayNG reference source stores profile records containing fields such as passwords, secret keys and PSKs in MMKV logical profile storage, with no explicit MMKV crypt key observed in the reviewed manager initialization.

PVNetwork should improve on this:

- use Android Keystore/Apple Keychain/Windows secure storage/Linux secret service as appropriate;
- keep secret references separate from ordinary profile metadata;
- redact generated Xray config and imported raw data from logs/support exports.

This is a product-design lesson, not an accusation that the upstream app defeats Android device-level filesystem encryption.

## libXray issue-derived reliability/security-adjacent classes

Current libXray issue history contains reports worth converting into tests, while treating unverified reports cautiously.

Examples observed during current review:

### Issue #127 — metrics panic

Closed in 2026 after a report of process panic when enabling metrics due exported-variable reuse. This reinforces that optional runtime services can affect process stability.

PVNetwork rule: optional stats/metrics services need enable/disable lifecycle tests and should not be automatically enabled only to power UI counters.

### Issue #104 — possible long-running memory growth

Open report discussing possible memory growth in iOS clients using Xray-core/libXray under routing policies.

This is **not yet accepted here as a proven universal memory leak**.

PVNetwork test requirement: long-duration iOS/Android memory and route-policy soak tests before production certification.

### Issue #118 — iOS config-test/input semantics

Open report involving `testXray` input handling on a real iOS device.

PVNetwork lesson: wrapper input contract must be typed/versioned and tested on real devices; avoid loosely passing file URLs/base64/JSON through ambiguous strings.

## Security claims in public issues

Do not turn claims about traffic detectability, censorship resistance, fingerprinting or protocol security from ordinary public issues/forums into verified facts without:

- upstream acknowledgement/fix/advisory;
- independent research;
- reproducible evidence;
- relevant standards/cryptographic analysis.

Store contested claims separately from normal interoperability evidence.

## Update gate

Before updating Xray/libXray in PVNetwork:

- advisory scan;
- dependency/SBOM diff;
- config/default/migration diff;
- protocol/security/transport combination tests;
- certificate verification tests;
- TUN/DNS/routing/leak tests;
- management/control API tests if enabled;
- long-running memory/resource tests on mobile;
- Store/package/signing tests;
- rollback compatibility with canonical PVProfile storage.

## Current selection conclusion

Xray-core remains a strong candidate, but **v26.3.27 should not be treated as a safe default production candidate because the upstream advisory marks it vulnerable and patched versions begin at v26.7.11**.

A future PVNetwork implementation must choose an exact patched pin based on both security and regression testing.

## Remaining gaps

- full resolved Go module vulnerability scan for a chosen patched candidate;
- historical repository advisory/CVE inventory if additional advisories exist/appear;
- security comparison of stable/prerelease/main candidate pins;
- exact libXray embedded Xray version mapping and issue-fix mapping;
- platform secure-storage implementation proof;
- platform route/DNS leak test infrastructure;
- audit of all management/listening features exposed by generated configs.
