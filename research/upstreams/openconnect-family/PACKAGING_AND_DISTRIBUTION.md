# OpenConnect Family — Packaging / Distribution Review

Research date: 2026-08-14

State: `IN-RESEARCH`; packaging evidence only, not PVNetwork release proof.

## Components must be separated

The OpenConnect ecosystem has materially different packaging surfaces:

1. `libopenconnect` / CLI core;
2. standalone OpenConnect GUI;
3. NetworkManager-openconnect Linux plugin/frontend;
4. platform helper/network integration components;
5. optional crypto/token/PKCS#11/TPM dependencies.

PVNetwork must not treat these as one package or one license unit.

## OpenConnect core / CLI

Current canonical source/release authority is the OpenConnect GitLab project and Infradead release site. Stable research baseline: **v9.21**.

Upstream release/test history provides evidence for:

- normal source builds on Unix-like systems;
- Linux distribution packaging;
- Windows MinGW build paths and generated Windows installers in modern release history;
- Android NDK build paths across multiple ABIs in CI;
- multiple TLS/crypto backend configurations.

PVNetwork implication: package the exact core build as a versioned component with a per-platform SBOM; do not infer one universal dependency set.

## Standalone OpenConnect GUI

Canonical project: `openconnect/openconnect-gui` on GitLab.

Current upstream README identifies Windows 10+ and macOS 10.12+ as supported GUI platforms. Stable v1.6.2 release publishes a Windows installer. The repository also contains:

- NSIS packaging;
- macOS bundle support;
- CMake/Qt build infrastructure;
- MinGW/RPM packaging material;
- CI/release automation;
- Windows code-signing work reflected in v1.6.2 release notes.

Historical macOS packages existed, while current release artifacts are not automatically equivalent across every platform. Treat exact release artifact availability separately from source portability.

PVNetwork lesson: Store/direct-distribution packaging must be designed per platform rather than assuming that a cross-platform core implies identical product packaging.

## NetworkManager-openconnect

The GNOME integration project includes evidence for distribution-oriented packaging and service integration:

- RPM/spec material;
- Debian-oriented packaging/copyright material;
- systemd/sysusers integration;
- D-Bus service policy;
- GTK3/GTK4 and NetworkManager plugin dependencies.

This is a strong Linux desktop reference but it is not a generic cross-platform packaging model.

PVNetwork Linux should separately evaluate:

- NetworkManager-integrated package path;
- application-managed service path;
- package formats/distributions selected for product support.

## Windows considerations

Packaging review must cover, at minimum:

- exact architecture(s) shipped;
- code signing;
- networking driver/helper ownership;
- upgrade/uninstall cleanup;
- whether the core is a replaceable shared component for LGPL compliance;
- dependency DLL placement/versioning;
- route/DNS/network-profile cleanup during uninstall or failed upgrades.

The standalone GUI's historical/current Windows packaging is useful reference evidence, but PVNetwork should not inherit its installer architecture blindly.

## Android considerations

OpenConnect upstream CI demonstrates Android-native build feasibility, but this does **not** equal a complete Android product architecture.

PVNetwork still needs its own decisions for:

- native library ABI packaging;
- `VpnService` lifecycle;
- foreground/background policy;
- per-app/split-tunnel integration where supported;
- app signing/Play compliance;
- crash/process-death recovery;
- exact minimum Android/API support.

## Apple considerations

Source portability of OpenConnect does not prove iOS/App Store feasibility.

PVNetwork Apple packaging must be evaluated around:

- Network Extension architecture;
- entitlements and signing;
- macOS sandbox/direct distribution differences;
- notarization for direct macOS builds;
- App Store review constraints;
- LGPL component replacement/relinking feasibility within the selected packaging model.

## Linux considerations

Linux support must record exact packaging target rather than saying only "Linux supported".

For each selected target distribution/package, record:

- OpenConnect/libopenconnect package availability/version;
- NetworkManager plugin availability if used;
- WebKit/libsecret/GTK dependencies if using GNOME frontend integration;
- service/user/D-Bus integration;
- update and rollback behavior;
- filesystem and configuration ownership;
- package-signing/repository trust source.

## LGPL distribution architecture

`libopenconnect` is treated as an LGPL-2.1 reuse candidate. Preferred engineering direction remains a replaceable shared-library boundary where platform and Store architecture permit it.

Static linking is not approved as a default path without a deliberate LGPL compliance/relinking design.

PVNetwork release artifacts must preserve required license notices/source or relinking obligations as determined by final legal review.

## Supply-chain release gate

For every shipped package record:

- exact OpenConnect commit/tag;
- exact frontend/product commit;
- exact crypto backend;
- dependency/SBOM hashes/versions;
- source/release provenance;
- signing identity/status;
- packaging toolchain version;
- update source/channel;
- license/notice bundle;
- reproducibility evidence where feasible.

## Remaining gaps

- exact current Windows installer contents/dependency manifest for OpenConnect core and GUI;
- current macOS GUI bundle/notarization status;
- distro-by-distro NetworkManager-openconnect package matrix;
- final Android embedding/package design;
- Apple Network Extension feasibility proof;
- Microsoft Store packaging feasibility for the selected Windows architecture;
- exact signed artifact verification and SBOM generation once PVNetwork implementation starts.
