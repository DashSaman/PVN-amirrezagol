# v2rayNG — Build / CI / Native-Core Supply-Chain Audit

Research date: 2026-08-14

State: `IN-RESEARCH / REFERENCE-ONLY` because v2rayNG application code is GPLv3.

Pinned v2rayNG source:

`2dust/v2rayNG@e8a82d9810ca1cf97a3cc8a9b9525a9f21955807`

## Important correction to upstream internal guide

The repository's current `docs/AGENTS.md` says there is no CI, but the pinned repository tree actually contains GitHub Actions workflows under `.github/workflows/`.

Current workflows observed:

- `build.yml`
- `fastlane.yml`

PVNetwork research rule:

**README/AGENTS text is a clue; actual current source tree/workflows are authoritative for build automation claims.**

## Main Android build workflow

Pinned `.github/workflows/build.yml`:

- runs on push to `master` and manual dispatch;
- checks out recursive submodules with full history;
- installs Android platform/build tools 37;
- installs NDK 29.0.14206865;
- builds/caches `hev-socks5-tunnel` native libraries;
- determines the current tag of the checked-out `AndroidLibXrayLite` submodule;
- downloads `libv2ray.aar` from the matching `2dust/AndroidLibXrayLite` release tag;
- uses Java 21 for CI;
- decodes Android signing material from GitHub Actions secrets;
- runs an F-Droid license report task;
- builds signed release APKs;
- uploads architecture-specific and universal APK artifacts;
- optional release job creates detached GPG signatures and publishes a prerelease.

## Native submodules

Pinned `.gitmodules` declares:

- `AndroidLibXrayLite` -> `https://github.com/2dust/AndroidLibXrayLite`
- `hev-socks5-tunnel` -> `https://github.com/heiher/hev-socks5-tunnel`

At the pinned v2rayNG commit, the AndroidLibXrayLite submodule points to:

`b21389865ed69ba01e81c1521965c27832a33cf9`

and `hev-socks5-tunnel` points to a separate pinned submodule revision in the v2rayNG tree.

## AndroidLibXrayLite license

At pin `b21389865ed69ba01e81c1521965c27832a33cf9`, root `LICENSE` is **LGPL-3.0**.

This is a crucial component-level distinction:

- v2rayNG app = GPLv3;
- AndroidLibXrayLite wrapper = LGPL-3.0;
- embedded Xray-core = MPL-2.0 plus its dependencies.

Do not infer one license for the whole chain.

## AndroidLibXrayLite build model

Pinned README describes a gomobile binding workflow targeting Android API 24.

Current wrapper `go.mod` uses Go 1.26 and directly depends on:

- Xray-core pseudo-version `v1.260327.1-0.20260728075948-5ca6f4b7d4dc`;
- a QUIC dependency;
- `golang.org/x/mobile`;
- many transitive Xray/network dependencies.

### Security relevance

The embedded Xray-core pseudo-version timestamp is **2026-07-28**, later than the published Xray advisory patch threshold `>= v26.7.11` recorded in the Xray family dossier.

This is encouraging evidence that the current v2rayNG native wrapper does not appear to be pinned to the vulnerable v26.3.27-era core. However:

- a timestamp/newer commit is not enough for final security sign-off;
- exact commit/advisory mapping and dependency scan remain required;
- PVNetwork must perform its own pinned build/SBOM rather than inheriting v2rayNG's release chain.

## Supply-chain architecture lesson

The current workflow **does not build AndroidLibXrayLite from source inside the v2rayNG job**. It reads the submodule's current tag and downloads a prebuilt AAR from that repository's GitHub release.

This creates a supply-chain boundary between:

- source submodule commit;
- release tag resolution;
- downloaded prebuilt AAR;
- app build.

PVNetwork should strengthen this pattern if adopting a similar architecture:

1. pin exact wrapper commit/tag;
2. verify downloaded artifact hash/signature/provenance;
3. preferably reproduce/build critical native artifacts in controlled CI;
4. record artifact digest in SBOM/release evidence;
5. ensure the release tag actually corresponds to the reviewed source commit;
6. archive toolchain versions.

Do not dynamically resolve “current tag” in a production reproducibility contract without recording the resolved immutable tag/digest.

## Native tun2socks supply chain

The main build can compile `hev-socks5-tunnel` from the checked-out submodule using NDK and caches resulting native libraries.

PVNetwork requirements if using such a component:

- pin submodule source;
- record license;
- build in controlled CI;
- record compiler/NDK version;
- verify architecture outputs;
- include native library hashes in release evidence;
- fuzz/soak-test TUN-to-proxy lifecycle and memory behavior.

## Signing model

Current v2rayNG CI decodes Android keystore material from GitHub Actions secrets and injects signing credentials into the Gradle build. A separate release step imports a GPG private key and creates detached signatures for APK artifacts.

PVNetwork lessons:

- signing secrets must remain in secure CI secret storage, never the repository;
- Android app signing and downloadable artifact signatures are different trust layers;
- Store builds/direct builds may require separate signing/update pipelines;
- build logs must not expose secret values/paths unnecessarily.

## F-Droid / Play split

Current app has both `fdroid` and `playstore` flavors. CI explicitly invokes the F-Droid release license report task while building release variants.

PVNetwork should likewise separate distribution-channel constraints rather than forcing one build to satisfy all channels with hidden runtime toggles.

Potential future channel split:

- Google Play / Android TV;
- direct APK;
- F-Droid-compatible build only if dependency/license/update rules align.

Each channel needs its own permission/update/telemetry/billing policy review.

## Fastlane metadata workflow

Pinned `fastlane.yml` validates Fastlane Supply metadata on push/PR/manual runs.

This is useful evidence that store/listing metadata can be CI-validated independently from the binary build.

PVNetwork should eventually validate:

- localized Store descriptions;
- screenshot/feature-graphic completeness;
- version/release notes;
- privacy/support URLs;
- language metadata;
- Android TV assets;
- absence of unsupported claims.

## Missing CI evidence

The current pinned workflows do not, from the files reviewed, demonstrate a dedicated GitHub Actions job that runs the app's Kotlin/JUnit test suite or instrumented Android device tests before every build.

Do not conclude there are no tests in the repository; conclude only that the reviewed CI workflows are primarily build/release/metadata workflows.

PVNetwork CI should explicitly separate:

- unit tests;
- parser/import round-trip tests;
- lint/static analysis;
- native wrapper tests;
- Android VpnService integration tests;
- emulator/device tests;
- Store metadata checks;
- signed packaging.

## Reproducibility requirements for PVNetwork

For every Android release record:

- app source commit;
- wrapper/core commit;
- native submodule commits;
- Gradle/AGP/JDK/SDK/NDK versions;
- dependency lock/SBOM;
- native artifact digests;
- signing channel identity;
- Store/direct flavor;
- generated config schema version;
- test evidence.

## PVNetwork reuse decision

v2rayNG build scripts/app remain **reference-only** under GPLv3 for a closed product.

The useful independent lessons are:

- recursive submodule pinning;
- channel flavors;
- native core/TUN artifact separation;
- ABI-specific releases;
- metadata validation;
- separate signing layers.

For actual PVNetwork integration, independently evaluate libXray/Xray-core or another narrow wrapper and build it under PVNetwork-controlled reproducible CI.

## Remaining gaps

- map AndroidLibXrayLite release tag corresponding exactly to submodule `b213898...`;
- review AndroidLibXrayLite CI/release workflow and provenance checks;
- review hev-socks5-tunnel pinned license/source/build flags;
- exact app test source and whether any external CI runs it;
- current Play Store build/bundle format and signing pipeline;
- F-Droid metadata/build recipe relationship;
- dependency vulnerability/license scan for exact Android app build.
