# strongSwan release/security pin — 2026-08-14

State: `EVIDENCE-BACKED V1 RESEARCH / NOT IMPLEMENTED / NOT SECURITY-CERTIFIED`.

This file closes the previously explicit “exact current full source/release pin” gap for the strongSwan family at the research layer. It does **not** define the final PVNetwork build, plugin set, SBOM, or production security certification.

## Canonical upstream and immutable pins

Canonical repository: `https://github.com/strongswan/strongswan`

Latest tagged release observed on 2026-08-14:

- release/tag: `6.0.7`
- annotated tag object: `de1c5e42ac35fb6d4121d9bce095806c5f7f90a7`
- tag target commit: `5973ff8e41deef4e015e1138a2de688acedf6f75`
- tagger date recorded by GitHub: `2026-06-07T17:54:30Z`
- official release announcement date: 2026-06-08

Current `master` head observed during this research slice:

- `5011838b32ac88ba9593af4b727932c34b28e127`
- commit message: `windows: Avoid potential races with canceling threads`
- author date: `2026-07-28T09:51:16Z`
- committer date: `2026-07-31T14:07:52Z`

The release pin and master pin serve different purposes. PVNetwork must not silently substitute a moving `master` checkout for a reviewed release build.

Primary evidence:

- `https://api.github.com/repos/strongswan/strongswan/git/ref/tags/6.0.7`
- `https://api.github.com/repos/strongswan/strongswan/git/tags/de1c5e42ac35fb6d4121d9bce095806c5f7f90a7`
- `https://github.com/strongswan/strongswan/releases/tag/6.0.7`
- `https://www.strongswan.org/blog/2026/06/08/strongswan-6.0.7-released.html`
- `https://api.github.com/repos/strongswan/strongswan/commits?sha=master&per_page=1`

## License pin

`COPYING` at release commit `5973ff8e41deef4e015e1138a2de688acedf6f75` contains the GNU General Public License version 2 text.

Primary evidence:

`https://github.com/strongswan/strongswan/blob/5973ff8e41deef4e015e1138a2de688acedf6f75/COPYING`

Engineering consequence: strongSwan must not be treated as a permissive closed-source drop-in. Exact distribution/linking/component obligations require deliberate architecture and legal review. This file is engineering license research, not legal advice.

## Security floor observed for the 6.0.x line

### 6.0.7

Official 6.0.7 release material records a fix for:

- `CVE-2026-47895` — `libstrongswan` identity-cloning issue that could result in a double-free and potentially remote code execution; the official announcement states versions since 4.3.3 were affected.

The release also tightened certificate-validity enforcement for pre-trusted/incomplete trust-chain cases.

### 6.0.6

Official 6.0.6 release material records fixes for seven vulnerabilities:

- `CVE-2026-35328` — `libtls` supported_versions processing / infinite loop;
- `CVE-2026-35329` — `libstrongswan` + `pkcs7` encrypted PKCS#7 processing / crash;
- `CVE-2026-35330` — `libsimaka` EAP-SIM/AKA attribute processing / infinite loop or heap overflow, potentially remote code execution;
- `CVE-2026-35331` — `constraints` plugin X.509 name-constraint validation issue;
- `CVE-2026-35332` — `libtls` ECDH public-value processing for TLS < 1.3 / crash;
- `CVE-2026-35333` — `libradius` RADIUS attribute processing / infinite loop or out-of-bounds read;
- `CVE-2026-35334` — `gmp` plugin RSA decryption / crash.

Primary evidence:

- `https://github.com/strongswan/strongswan/releases/tag/6.0.6`
- `https://www.strongswan.org/blog/2026/04/22/strongswan-6.0.6-released.html`

## PVNetwork decision from this pin

For research and future build evaluation, **6.0.7 is the minimum reviewed release baseline for the current 6.0.x line as of 2026-08-14** because it includes the fixes listed above through 6.0.7. This is a patch-floor statement, not a claim that 6.0.7 has no vulnerabilities.

Before any implementation or shipped build, repeat the release/advisory check and resolve:

1. exact selected strongSwan tag/commit;
2. exact configure/build options;
3. compiled and loaded plugin set;
4. direct/transitive dependency SBOM;
5. native crypto providers and versions;
6. platform/kernel backend;
7. advisories after this research date;
8. server interoperability and IKE/data-plane regression tests.

## Source-vs-release rule

`master` currently contains post-6.0.7 work, including a Windows thread-cancellation race fix. That is useful evidence that platform-specific fixes continue after the release tag, but it is **not** sufficient reason to ship `master`.

Selection policy:

- prefer a reviewed tagged release as the normal baseline;
- evaluate post-release fixes individually when they affect a selected PVNetwork platform;
- never infer security certification from “latest master”;
- pin the exact source revision in every build record.

## Remaining v1 gaps after this file

This file closes only the exact release/source pin and current release-security-floor gap. Remaining original-v1 gaps still include:

- exact prospective compiled plugin/dependency/SBOM matrix;
- source-level Android front-end/storage/menu details;
- native Apple/Windows/Android capability evidence;
- issue/regression examples tied to selected features/platforms;
- final family handoff/checkpoint after numbered entries 004–008 and shared decisions are synchronized.
