# SoftEther release, security and issue review

Reviewed upstream: `SoftEtherVPN/SoftEtherVPN`.

This note is part of the original-v1 closure for PVNetwork entries **013 SoftEther VPN Protocol**, **014 EtherIP**, and **015 EtherIP/IPsec**. It records current upstream release/security facts separately from the older source pin used for architecture research.

## 1. Current upstream release observation

Official GitHub Releases currently exposes `5.2.5188` as the newest non-prerelease release observed during this review. It was published on 2025-07-18.

Official release:

- https://github.com/SoftEtherVPN/SoftEtherVPN/releases/tag/5.2.5188

Important release-shape observation: the release tag is `5.2.5188`, while several Windows binary assets embedded in that release are named `5.02.5187`. PVNetwork must therefore **not infer executable build identity from the GitHub release tag alone**. The selected artifact filename, embedded/product version where available, digest and release/tag must be tracked separately.

The official release metadata publishes SHA-256 digests for uploaded assets. PVNetwork should pin the exact asset digest when distributing an upstream binary instead of resolving a mutable “latest” URL at runtime.

The release notes also include dependency/CI changes such as updates to `liboqs`/`oqs-provider`, CPU-feature handling and packaging/build machinery. That reinforces the need to review submodule/dependency deltas when moving between SoftEther pins.

## 2. Critical current security finding: do not bless 5.2.5188 Developer Edition as a safe production pin

The upstream repository has a published GitHub Security Advisory:

- `GHSA-q5g3-qhc6-pr3h`
- `CVE-2026-39312`
- severity: **High** (CVSS 3.1 7.5)
- summary: pre-authentication EAP-TLS denial of service on SoftEther VPN Developer Edition 5.2.5188
- advisory URL: https://github.com/SoftEtherVPN/SoftEtherVPN/security/advisories/GHSA-q5g3-qhc6-pr3h

The advisory states that Developer Edition 5.2.5188 is confirmed vulnerable and describes a pre-authentication crash path in `src/Cedar/Proto_PPP.c` during EAP-TLS fragment reassembly. The GitHub advisory record currently reports the vulnerable range as `<= 5.2.5188` and does not provide a `patched_versions` value.

### PVNetwork safe-version rule

**BLOCK production selection of SoftEther Developer Edition 5.2.5188 as a generic “latest = safe” dependency.**

Until an upstream fixed release/commit is identified and independently verified against the advisory:

1. Do not automatically promote `5.2.5188` merely because it is the newest GitHub Release.
2. Treat raw L2TP exposure and the affected EAP-TLS path as security-sensitive surface.
3. If SoftEther is used in a production bundle before an upstream fixed release is established, deployment must use an explicitly reviewed mitigation profile and must not claim the advisory is patched.
4. A future release-selection gate must query upstream advisories again and record the first verified non-vulnerable commit/release plus artifact digest.

This is a direct reason entries 013–015 cannot be marked `COMPLETE-RESEARCH-v1` yet: the safe release pin is unresolved.

## 3. Additional published advisory evidence

Another official GitHub Security Advisory reviewed here is:

- `GHSA-xw53-587j-mqh6`
- `CVE-2025-32787`
- severity: Low (CVSS 3.1 3.1)
- issue: NULL dereference in `DeleteIPv6DefaultRouterInRA`
- affected versions listed by the advisory include 5.02.5184 through 5.02.5187
- advisory URL: https://github.com/SoftEtherVPN/SoftEtherVPN/security/advisories/GHSA-xw53-587j-mqh6

The defect is in shared packet/Virtual-HUB processing rather than only a single branded VPN protocol. For PVNetwork this matters because a multi-protocol SoftEther server shares substantial Cedar/Hub/session code across front ends; protocol enablement decisions cannot assume isolation from shared-core faults.

## 4. Representative issue/regression lessons

### 4.1 Shared server/session crash risk

Upstream issue `#1222` (“Segmentation fault”) documents a production-observed Linux server crash in shared Session/Hub/Network lifetime handling.

- https://github.com/SoftEtherVPN/SoftEtherVPN/issues/1222

The stack discussed in the issue crosses `Session.c`, `Hub.c`, `Network.c`, connection acceptance and listener/thread-pool code. This is another reason PVNetwork should treat the SoftEther server as a stateful shared runtime and use process/service isolation and health supervision rather than embedding it casually into a GUI process.

### 4.2 Protocol/release behavior can diverge across Developer and Stable editions

Upstream issue `#1722` records an OpenVPN compatibility regression/behavior difference involving TLS 1.3 in Developer Edition versus Stable Edition.

- https://github.com/SoftEtherVPN/SoftEtherVPN/issues/1722

Although this issue concerns the OpenVPN compatibility front end rather than entries 013–015 directly, it is relevant to release policy: **edition, exact build and enabled front end are part of the compatibility contract**. PVNetwork must not collapse “SoftEther version” to one semantic value without recording edition/build provenance.

### 4.3 IPsec/L2TP service operation depends on listener/service configuration

Issue `#1139` documents an operational case where expected L2TP/IPsec listener behavior did not match user assumptions.

- https://github.com/SoftEtherVPN/SoftEtherVPN/issues/1139

Combined with `Proto_IPsec.c` source evidence, the lesson for PVNetwork is that IPsec-family capability status should expose listener/service readiness separately from stored configuration. A profile being configured does not prove the underlying IKE/ESP/L2TP service has bound and is healthy.

## 5. Release and dependency policy for the PVNetwork adapter

For every SoftEther upgrade candidate, record all of the following before promotion:

- repository and exact source commit;
- release tag and edition;
- exact selected binary/package filename;
- SHA-256 digest if using an upstream artifact;
- top-level license plus submodule/dependency provenance;
- security-advisory review date and all advisories affecting the selected version;
- relevant release-note deltas;
- protocol-specific smoke tests for enabled PVNetwork capabilities;
- service/listener health checks;
- rollback pin.

The adapter must therefore use a **reviewed-version allowlist**, not `latest` discovery.

## 6. Impact on entries 013–015

| Entry | Release/security conclusion | Current V1 state |
|---|---|---|
| 013 SoftEther VPN Protocol | upstream reusable conditionally, but current newest DE release is not safe to bless automatically because CVE-2026-39312 includes 5.2.5188 | `IN-RESEARCH` |
| 014 EtherIP | protocol source is concrete, but it shares server/Hub/session runtime with other SoftEther paths; process isolation and shared-core advisory review are required | `IN-RESEARCH` |
| 015 EtherIP/IPsec | composite service additionally requires listener/IKE/ESP health and safe service configuration; no “configured = healthy” assumption | `IN-RESEARCH` |

## 7. Remaining closure work

After this review the major remaining original-v1 gaps are narrower:

- identify and verify a commit/release that actually fixes or otherwise excludes `CVE-2026-39312`, or keep the family explicitly security-blocked for production selection;
- audit exact configuration persistence/secret handling and export/import behavior;
- complete top-level + dependency NOTICE/license obligations;
- finish UI/control mapping and per-entry template audit;
- synchronize the numbered entries and shared family index with these findings.

No strict `COMPLETE-RESEARCH-v1` claim is made by this document.