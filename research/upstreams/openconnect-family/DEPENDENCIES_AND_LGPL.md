# OpenConnect — Dependencies / SBOM / LGPL Integration Research

Research date: 2026-08-14

Stable research baseline: OpenConnect v9.21 from the canonical `openconnect/openconnect` GitLab project.

Status: `IN-RESEARCH`. This is engineering license/supply-chain research, not final legal advice or legal sign-off.

## 1. Canonical license evidence

OpenConnect's official project site and canonical source identify the project/library as **GNU LGPL version 2.1**.

Primary references:

- `https://www.infradead.org/openconnect/licence.html`
- canonical source `COPYING.LGPL`
- GNU LGPL-2.1 official license text.

The LGPL-2.1 is materially different from the GPL licenses found in some OpenConnect frontend applications. Do not assign a GUI's GPL license to `libopenconnect`, and do not assume the LGPL library license applies to unrelated frontend code.

## 2. Practical integration direction for PVNetwork

The likely lowest-friction commercial-product architecture to evaluate is a **replaceable shared-library boundary** around `libopenconnect`, with the PVNetwork application communicating only through the public API.

Why this is attractive:

- LGPL-2.1 explicitly contemplates a suitable shared-library mechanism in which the user can replace the library with an interface-compatible modified version;
- it avoids copying private OpenConnect source directly into PVNetwork product code;
- it supports independent library updates/SBOM tracking;
- it reinforces the architectural separation already selected for the Enterprise Adapter.

This is only an engineering recommendation for legal review. Final distribution design must be checked by qualified legal review for every target package/store.

## 3. LGPL obligations that engineering must preserve

From the LGPL-2.1 license text, distribution of a linked work requires conditions such as:

- prominent notice that the LGPL library is used;
- inclusion/access to the license text;
- preservation of library copyright/license notices;
- access to the corresponding source of the library and modifications as required by the license;
- a mechanism that preserves the user's ability to use a modified compatible library, or another compliant relinking/source/object-code path under the license;
- product terms must not prohibit modification/reverse engineering where the license requires that freedom for debugging modifications to the library.

PVNetwork engineering must not make final legal interpretations itself. These requirements must be converted into a distribution checklist and approved before release.

## 4. Core build dependencies from official OpenConnect documentation

The current official build documentation lists these required libraries/tools:

- `libxml2`
- `zlib`
- one TLS/crypto backend: **OpenSSL** or **GnuTLS** (GnuTLS is the normal/default build preference described by upstream)
- Autotools/build tooling including `automake`, `autoconf`, `pkg-config` for source builds.

Optional feature dependencies documented by upstream include:

- `p11-kit` for PKCS#11 support;
- `libp11` for PKCS#11 when using OpenSSL;
- `libproxy` for automatic system proxy discovery;
- `trousers` for TPM 1.x support with GnuTLS;
- `libtasn1` plus `tss2-esys` or an IBM TPM2 TSS path for TPM 2.0 support with GnuTLS;
- `libstoken` for SecurID software-token support;
- `libpskc` for RFC6030 PSKC HOTP/TOTP key storage;
- `libpcsclite` for hardware-token support documented by upstream.

The exact feature/dependency graph must be generated from the build configuration used by PVNetwork; optional dependencies must not be included merely because upstream supports them.

## 5. Mandatory network-configuration helper dependency

Official OpenConnect documentation states that the client core handles communication with the VPN server but delegates routing/DNS/network configuration to a `vpnc-script`-compatible helper on platforms using that model.

Important implications:

- a packaged Unix/Linux build needs a suitable compatible script or an equivalent product-owned network integration path;
- the configured script location is part of the build/runtime contract;
- Windows uses a Windows-specific script path in the upstream default packaging model;
- script version and cleanup behavior are part of connection correctness and must be tested independently from `libopenconnect` itself.

The official documentation specifically warns that without an appropriate script the tunnel may establish while routing/name-service configuration is incorrect.

PVNetwork must therefore treat the network-configuration helper as a separately versioned dependency or replace it with a platform-native networking layer behind the Enterprise Adapter.

## 6. Platform packaging evidence

Official OpenConnect package documentation currently records maintained packages/builds across multiple Linux distribution families and Windows cross-builds, while the official platform page documents broad Unix/BSD/Windows/macOS portability.

This is upstream availability evidence only. PVNetwork still requires its own package/runtime compatibility matrix.

The official docs also make clear that platform integration may involve different TUN/TAP drivers or network helpers. These artifacts belong in the SBOM/package manifest even when they are not compiled into `libopenconnect`.

## 7. Crypto-backend choice affects the dependency/SBOM surface

OpenConnect can be built against GnuTLS or OpenSSL. The choice is not cosmetic:

- it changes runtime dependencies;
- it can change support paths for PKCS#11/TPM features;
- it changes security-advisory tracking responsibilities;
- it may change platform packaging availability;
- it must be pinned and tested per PVNetwork release.

PVNetwork should select a backend per target platform only after security, packaging, licensing, feature and maintenance comparison. Do not silently allow different build systems to choose different TLS backends without recording the result.

## 8. Proposed SBOM fields for the OpenConnect integration

For every release/target platform record:

- OpenConnect version/tag/commit;
- `libopenconnect` build hash;
- selected TLS/crypto backend and version;
- `libxml2` version;
- `zlib` version;
- network helper/script version or native replacement;
- every enabled optional dependency;
- TUN/TAP/driver/helper artifacts;
- build-only vs runtime dependency classification;
- source URL and resolved version/hash;
- SPDX/license identifier verified from source;
- copyright/NOTICE obligations;
- security-advisory status at release review time;
- target OS/architecture;
- whether the component is dynamically linked, statically linked, bundled, system-provided or subprocess/helper based.

## 9. Candidate PVNetwork integration models to compare

### A. Dynamic/shared `libopenconnect`

Current preferred engineering candidate for legal/maintenance evaluation.

Benefits:
- clear engine boundary;
- replaceable library model aligns well with LGPL-2.1's shared-library option;
- smaller product/core coupling;
- easier independent engine updates and SBOM diffs.

Risks/requirements:
- ABI/API compatibility control;
- package/store rules may constrain arbitrary runtime replacement on some platforms;
- each platform needs a legal/store-specific interpretation and distribution plan.

### B. Static linking

Not automatically forbidden, but creates more demanding LGPL compliance/relinking distribution questions and larger coupling. Do not select without explicit legal/distribution design.

### C. Separate executable/subprocess

Can provide a strong process boundary, but has lifecycle, IPC, credential-passing, packaging and UX costs. License obligations still apply to redistribution of OpenConnect itself. This path should be compared, not assumed to eliminate licensing work.

### D. OS/package-provided OpenConnect

Potentially attractive on some Linux distributions, but version fragmentation and capability drift become product risks. PVNetwork would need strict minimum-version and feature detection.

## 10. Store and platform review required later

Before a shipping decision, separately review:

- Google Play and Android packaging constraints;
- Apple App Store / Network Extension architecture and whether the chosen library distribution model is technically/store feasible;
- macOS direct vs Mac App Store packaging;
- Microsoft Store/MSIX/service/driver packaging;
- Linux distro package/Flatpak/Snap/AppImage implications.

Do not assume a licensing-compliant desktop package is automatically acceptable in every Store sandbox.

## 11. Supply-chain security requirements

- Pin the exact OpenConnect release/commit.
- Verify source archive/signature where upstream publishes one.
- Generate an SBOM from the actual release build, not a hand-maintained list alone.
- Treat optional feature enablement as a supply-chain change.
- Fail review on unexpected dependency additions.
- Track security advisories for OpenConnect plus the selected TLS backend and parsers (`libxml2`, etc.).
- Pin/review the network configuration helper or replace it with product-owned platform integration.
- Keep frontend GUI dependencies separate from core library dependencies.

## 12. Current decision

- `libopenconnect`: remains `REUSE-CANDIDATE / LGPL-DISTRIBUTION-REVIEW-REQUIRED`.
- Preferred engineering shape to take into legal/platform review: **public API + replaceable shared library where platform/store architecture permits**.
- Static linking: not approved.
- Copying OpenConnect GUI application code into PVNetwork: not approved and separately constrained by frontend licenses.
- Product-specific auth/browser/secret-store/network lifecycle remains outside private OpenConnect internals.

## Remaining gaps

- machine-generated full dependency graph for exact v9.21 build profiles;
- source/license verification for every direct/transitive dependency;
- exact `vpnc-scripts` license/source pin and platform-specific helper audit;
- security advisory inventory at pinned dependency versions;
- legal review of shared/static/subprocess models;
- Store-specific distribution analysis;
- build-reproducibility and signed-artifact pipeline after implementation begins.