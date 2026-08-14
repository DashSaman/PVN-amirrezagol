# OpenConnect Family — Security / Advisory Review

Research date: 2026-08-14

State: `IN-RESEARCH`; security-reference evidence, not a claim that PVNetwork is secure or implemented.

## Source priority

Primary evidence used for this dossier:

- official OpenConnect changelog/release history at Infradead/OpenConnect;
- canonical OpenConnect GitLab issues/MRs/releases;
- pinned source/licensing evidence already recorded in this family dossier.

## Current release baseline

Current stable release reviewed: **OpenConnect v9.21**, released 2026-06-16.

The v9.21 changelog fixes a high-CPU/infinite-loop condition in text-buffer handling that became easier to trigger after v9.20 expanded use of the buffer helper. The same release also records a NULL-passphrase crash fix in a TPM2 authentication-dialog path.

### PVNetwork lesson

Even mature libraries can surface old defects when internal helpers are reused more widely. Core upgrades require regression testing around adapter callbacks, environment/config construction, authentication and resource usage rather than only verifying that the library links successfully.

## Historical security-relevant fixes to preserve as regression knowledge

### CVE-2020-12105 — certificate validation

OpenConnect fixed incorrect OpenSSL certificate-validation behavior for trusted-but-invalid certificates. This is an important trust-model regression class.

PVNetwork requirement:

- never override platform/library certificate validation casually;
- certificate exceptions/pinning must be explicit, scoped and reviewable;
- UI must distinguish a normal trusted chain from an explicit pinned exception.

### CVE-2020-12823 — local certificate pretty-name buffer overflow

The v8.10 release was triggered by a buffer overflow in local-certificate display/name handling when built against GnuTLS. Upstream noted that the path involved locally supplied client certificates/supporting CAs rather than a normal remote server certificate.

PVNetwork requirement:

- treat certificate metadata parsing/display as security-sensitive input handling;
- fuzz or adversarially test certificate import/display boundaries owned by PVNetwork;
- do not assume UI-only certificate fields are harmless.

### CVE-2019-16239 — HTTP chunked-encoding buffer overflow

OpenConnect v8.05 fixed a chunked-encoding buffer overflow.

PVNetwork requirement:

- rely on the maintained upstream parser rather than reimplementing protocol parsing;
- pin known-good releases;
- include malformed/framing regression coverage at the adapter/integration boundary where PVNetwork transforms data.

### CVE-2013-7098 — MTU/reconnect overflow history

Later release notes record a fix for a possible heap overflow when MTU increased on reconnection.

PVNetwork requirement:

- reconnect and network-transition tests must include changed MTU/interface/address-family conditions;
- runtime values copied from the engine must not be assumed stable across reconnect/rekey.

## Legacy crypto policy

Current OpenConnect documentation explicitly disables ancient 3DES and RC4 by default unless an insecure compatibility option is requested. Historical release notes also record obsolete-server-crypto testing.

PVNetwork policy direction:

- legacy insecure crypto must never be silently enabled;
- if legacy interoperability is retained, expose it only behind an explicit warning/advanced policy;
- capability/support matrices must distinguish "protocol supported" from "server requires obsolete crypto".

## TunnelVision / route-control class

Canonical upstream has an issue discussing CVE-2024-3661 / "TunnelVision", a DHCP route-injection class affecting route-based VPN designs depending on platform/network behavior.

This is not purely an OpenConnect protocol defect; it demonstrates that VPN confidentiality also depends on OS route/DNS ownership and kill-switch policy.

PVNetwork requirements:

- test route ownership against network changes and DHCP updates;
- test kill-switch/full-tunnel behavior independently from engine connection success;
- platform-specific route/DNS leakage tests are mandatory;
- do not advertise "connected" as equivalent to "all intended traffic is protected".

## Authentication / SSO security boundaries

OpenConnect supports external-browser/SSO style authentication, but frontend support varies. Current issue history includes browser/SSO handoff failures and authentication continuation problems.

PVNetwork requirements:

- bind SSO result to the initiating session/profile;
- minimize lifetime of cookies/tokens;
- never log browser callback secrets;
- cancel/timeout must invalidate pending auth state;
- prevent stale SSO results from being reused by another connection attempt.

## Debug logging risk

NetworkManager-openconnect's own debug help warns that verbose logging may expose passwords. OpenConnect itself also has deep HTTP/debug logging modes intended for troubleshooting.

PVNetwork requirements:

- upstream TRACE/debug output is not automatically safe for users/support bundles;
- build a redaction layer for secrets, auth headers, cookies, private-key material and token values;
- support bundles should default to sanitized logs and identify when low-level engine logs were omitted/redacted.

## Dependency and backend risk

The exact OpenConnect security surface depends on selected build features/backends, including TLS/crypto backend, libxml2/zlib, token/PKCS#11/TPM support, proxy support and platform helper integration.

Therefore security review must be **per exact shipped build/SBOM**, not based only on the OpenConnect repository version.

## Upgrade gate for PVNetwork

Before updating a shipped OpenConnect component:

1. review canonical changelog/releases/issues/MRs since the pinned version;
2. regenerate exact per-platform SBOM;
3. review TLS/crypto and XML/compression dependencies;
4. run adapter contract tests;
5. run authentication/SSO tests;
6. run reconnect/network-transition and route/DNS cleanup tests;
7. run vendor-specific interoperability tests for claimed families;
8. verify support-bundle redaction;
9. update the protocol/vendor capability matrix.

## Remaining gaps

- build a machine-readable historical CVE/advisory table with affected/fixed versions from authoritative security databases plus upstream confirmation;
- review current dependency advisories for the exact selected OpenConnect build profile;
- review platform-specific privileged helper/network-script risks;
- map current security-relevant open issues/MRs that are not yet release-fixed;
- define final PVNetwork security acceptance tests after implementation architecture is selected.
