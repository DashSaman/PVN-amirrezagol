# PVNetwork — 02: Source reuse, licensing and delivery accelerators

Date: **2026-11-06 (Asia/Tehran)**  
Scope: **CHAT 02 preflight only; no production changes and no third-party code/assets imported.**  
Repository inspected: `DashSaman/PVN-amirrezagol` — **public**.  
Baseline: `78dcd4511dbb830a999cc8c1796f2a38f88587e8`.  
Coordination document: `docs/preflight-2026-11-06/START_HERE.md` at `ed21086e3e74772ab4dc31825eb87336f917fd67` (PR #2).  
Delivery branch: `prep/2026-11-06/02-reuse-licenses`; target: `main`.

## 1. Decision

**Continue the existing PVNetwork-owned KMP/Compose product and reuse narrowly scoped, licensed engines and native bridges. A wholesale Flutter/plugin migration is not justified by the evidence inspected.**

The quickest credible starting package is the existing `core/foundation` + `engines/xray-adapter` + Android/desktop applications, with a traceable Xray build and a traceable TUN-to-SOCKS component. An audited `XTLS/libXray` bridge is a candidate for later embedded/mobile integration; it does not require replacing the product with Flutter or rewriting a VPN protocol. The version and license gates below apply before distribution.

- **GO:** continue demonstrably owner-controlled product code; preserve canonical profiles, secret references and adapter contracts; implement original UI/behavior from requirements.
- **CONDITIONAL:** distribute Xray/MPL-covered files, permissive wrappers and TUN libraries only after complete artifact provenance, dependencies, notices, corresponding source and target-device gates pass.
- **NO-GO for the stated private-code product:** copy GPL client source into the combined proprietary application, use a restrictive/noncommercial fork without permission, or treat proprietary/no-source applications as code donors.
- **No release approval is issued by this report.** A root license badge, successful APK build or cross-platform README is insufficient to approve the delivered native artifact.

Here, GO/CONDITIONAL/NO-GO describe **the proposed reuse route**, not whether a project is good software. GPL permits commercial activity; the conflict is the requirement to keep the affected combined application proprietary. Licensing conclusions are engineering distribution decisions based on inspected evidence, with unresolved legal interpretation and contracts explicitly retained as gates.

### Evidence vocabulary

| Label | Meaning in this report |
|---|---|
| OBSERVED | File, manifest, license, source path or remote CI result actually inspected. |
| VERIFIED | A stated, reproducible local check was executed; scope is named. |
| DOCUMENTED | A historical repository/upstream assertion; not a fresh build or device receipt. |
| PROPOSED | Recommended implementation or experiment, not completed work. |
| UNKNOWN / BLOCKED | Missing evidence or a prerequisite prevents approval. |

## 2. Changes and ambiguities resolved against existing research

The August source matrix and architecture recommendation were read, together with startup rules, current state/handoff, public-app requirements/decisions, the implementation plan, source-mirroring policy and actual main tree. The 93-protocol investigation was **not reopened**. Local counting of the two fetched trackers found 93 numbered completed rows in each; this is a tracker consistency check, not a new validation of every protocol. [R0] [R1] [R2]

| Prior statement / question | Current evidence and execution consequence |
|---|---|
| The shared foundation is JVM-only. | Stale: `core/foundation/build.gradle.kts`, lines 9–24, configures Android, JVM, `iosArm64` and `iosSimulatorArm64`. Configured targets do not prove an iOS app or tunnel extension. [R3] |
| A Flutter plugin could save us from building the first Android VPN shell. | `apps/android` already exists. `PvVpnService.kt` owns a TUN and starts Xray plus hev JNI; the desktop module already integrates the owned Xray adapter. Migration must beat this actual baseline. [R4] [R5] [R12] |
| Only a historical local build is available. | Remote run **35897687631** at the baseline SHA reports successful Android debug assembly/upload and Linux-hosted desktop tests/distributable. Job metadata/steps were inspected. This is scoped upstream CI evidence, not a build run in this chat, Windows execution or physical Android validation. [R6] |
| A green dashboard means all protocol gates pass. | The latest state-validation run is green, but the most recent inspected Xray adapter push run **35877948152** failed on an earlier commit; Mihomo run **35868899981** also failed. Their failures are not cleared by an unrelated documentation/state or packaging job. Detailed defect ownership belongs to Chat 01/06. [R7] [R7-M] |
| The Xray download is always checksum-verified. | `apps/android/build.gradle.kts` pins an archive SHA-256, but line 78 returns if the target exists. The target `.so` is already committed. Therefore this task does not authenticate the pre-existing packaged binary. A clean/untrusted cache and a tampered committed/extracted binary need explicit negative tests. [R4] |
| Native provenance is complete because versions are documented. | The tree contains `libxray.so` and `libhev-socks5-tunnel.so`. The validation record names versions and a historical hev hash; this audit did not reproduce the native builds or hash downloaded binaries. No release-level third-party notice bundle, resolved artifact SBOM or native source bundle was found in the inspected application tree. These remain release blockers. [R8] [R9] |
| This public repository can hold the new private commercial product. | Repository metadata reports public visibility. Keep this report public; put future proprietary product work in an owner-approved private repository, after ownership/provenance review. Existing public history cannot be made undisclosed retrospectively. Required OSS source/notices must remain available to entitled recipients. [R10] [L-GITHUB] |

The historical `M-P2` record describes Android as built/code-reviewed without physical-device execution and reports scoped Windows results. Those are preserved as DOCUMENTED, not promoted to current device/Store certification. No production code, shared state JSON, existing architecture document or another chat's report is changed here.

## 3. Source decision matrix

### 3.1 Full applications and product references

Versions below are **audited references**, not versions approved to import. Tags were resolved to commits; GPL-only applications are excluded from the private source base without a different rights grant.

| Source | Audited release / source commit | Actual license / source finding | Decision for PVNetwork |
|---|---|---|---|
| **Karing** | `v1.2.25.2802` — `0c320dc182d095ec0d483f852169421e749c2bac` | `LICENSE.md`: GPL-3.0-or-later plus prior-consent wording for name/association. [A-KARING] | **NO-GO for copied private app code; GO as behavior/architecture reference.** Its separate `vpn_service` and native artifacts require their own audit; the app checkout does not fully pin the bridge. |
| **Hiddify** | `v5.5.5` — `7804cadac01419f31b1ec7802d9e1be599e66de2` | Extended GPL text still imposes GitHub-fork/source, GitHub Actions, attribution/change documentation, naming/interface and noncommercial-without-consent conditions. [A-HIDDIFY] | **NO-GO without an adequate written rights grant.** Treat as custom restricted/source-available terms, not ordinary permissive or unmodified GPL. A Hiddify permission would not automatically clear third-party core copyrights. |
| **v2rayNG** | `2.3.15` — `57907cf354d9769200ed4717b4430d605abe0952` | GPLv3 text in actual LICENSE. [A-V2RAYNG] | **NO-GO for copying Android application/service code into the private app; reference only.** Xray's MPL does not relicense the Android GUI or its TUN/wrapper stack. |
| **v2rayN** | `7.25.6` — `1883178b5f1e222b6c6db2782e7a3d989b7cc18f` | GPLv3 with a project-specific 2dust notice giving version 3 or later; application is distinct from separately packaged engines. [A-V2RAYN] | **NO-GO as private source base; reference for desktop orchestration.** Audit every separately redistributed core/driver if adopting the same architecture independently. |
| **AmneziaVPN** | `5.1.0.6` — `ac175c8a8147cfe56f98c8372ec17ea2ba00bb02` | GPLv3 application; Qt/native/protocol dependencies have separate terms. [A-AMNEZIA] | **NO-GO as private client source base; native/service reference only.** A Qt LGPL/commercial route does not change Amnezia application's GPL grant. |
| **Happ** | Android `4.10.1` / `c87cc5ea28b5748aab8a4225f998d3080d2c18ae`; desktop `4.7.2` / `ca87d08932113de73e3c96941fe293991658fbf5`; iOS README snapshot `e5d38081d3b62455670131fa8e0f0b37039a12dc` | Exact distribution-repository trees contain README/release/workflow metadata, not app implementation or an app-source license. Pins identify those repositories, **not the source used to compile the apps**. [A-HAPP-A] [A-HAPP-D] [A-HAPP-I] | **NO-GO for source reuse; UNKNOWN rights/source delivery.** Product behavior may be a reference. No assertion is made that a private source/licensing arrangement cannot exist. |
| **Official V2Box GUI** | HexaSoftware product identity checked through store/vendor pages; no app-source revision established. | Product/privacy listings establish an application, not a reusable source license. The unrelated `imanheidary/v2box` plugin cannot grant rights to this GUI. [A-V2BOX] [A-V2BOX-VENDOR] | **NO-GO for source import; UNKNOWN authoritative source/grant.** Reference behavior only; no decompilation or use of similarly named mirrors. |
| **NPV Tunnel / NapsternetV** | VONMATRIX product/store and NapsternetV vendor pages; no app-source revision established. | A downloadable client or custom server binary is not the client's source grant. A bounded current check did not establish authoritative reusable app source. [A-NPV] [A-NPV-VENDOR] | **NO-GO for source import; UNKNOWN grant.** Do not import third-party release mirrors/config-decryption projects as an implementation shortcut. |

For projects whose file contains only the standard GPLv3 text, this report does not infer an `-or-later` grant from generic example boilerplate. Preserve exact per-file licensing in the later SBOM. Karing/sing-box explicitly say version 3 or later; v2rayN fills in the project copyright in that notice. Assets remain separately governed.

Two useful deltas prevent misleading reuse estimates: Karing's tag above contains `version: 1.2.25+2801` in `pubspec.yaml` (different build suffix from the tag), and overrides `vpn_service: 1.0.0` with **`path: ../vpn-service/`**. The inspected app checkout therefore does not by itself establish a fully pinned native bridge or source-to-release-binary correspondence. [A-KARING-PUBSPEC] Reviewed stable releases also need not be the newest prerelease; this report deliberately pins the listed reviewed stable candidates rather than an unreviewed moving draft.

### 3.2 Engines and reusable building blocks

| Component | Inspected license / revision | Reuse decision and constraint |
|---|---|---|
| **Xray-core** | MPL-2.0; current stable candidate `v26.10.31`, commit `a4a3dff5b20c2faae567b742c172c7b95e75c083`. [E-XRAY] | **CONDITIONAL, preferred modern-proxy engine.** Independently owned UI/domain can remain private with MPL and all dependency obligations satisfied. Existing product uses an older pin; see §7 before an upgrade. |
| **XTLS/libXray** | MIT wrapper; `v26.10.31` resolves through an annotated tag to `7fe8528063e54343af8ba9a37431afc56000de21`. [E-LIBXRAY] | **CONDITIONAL, preferred narrow bridge candidate**, especially for embedded/Apple work. Its `go.mod` explicitly selects the Xray commit above and Go `1.27.3`; do not pair it arbitrarily with an older Xray binary. It is not an OS tunnel service. [E-LIBXRAY-MOD] |
| **hev-socks5-tunnel** | MIT root; latest inspected `2.19.3`, commit `c5584feca8f6df70ee53eb2879e034b849a3d15a`; product historically uses `2.17.1`. [E-HEV] | **CONDITIONAL, preferred existing TUN-to-SOCKS route.** Root MIT does not clear submodules or the committed JNI binary automatically. |
| **sing-box** | `v1.14.4`, `c005f35300ce76427dc285481ec5695f5b179cd2`; GPL-3.0-or-later plus name/association wording. [E-SINGBOX] | **NO-GO for direct combined private integration under current plan. CONDITIONAL** only as a genuinely independent distribution boundary or under sufficient alternative permission, with full applicable obligations. Not a default mobile shortcut. |
| **Mihomo** | `v1.19.33`, `3f27ef39f8c96bbfbc8b0b9bb10f5c9ef60149d8`; actual GPLv3 text. [E-MIHOMO] | Same **NO-GO / CONDITIONAL** distinction as above. Keep existing owned adapter contracts; the existence of an adapter does not authorize bundled redistribution. |
| **Official WireGuard Android tunnel module** | `1.0.20260315`, source `0856e2ecf7f3cce0e9c9014db3ad7665669adce5`; root Apache-2.0, with separately licensed native payloads. [E-WG] | **CONDITIONAL alternative for the WireGuard adapter.** Its built `libwg.so`/`libwg-quick.so` are executables from a GPLv2 tools subtree; `libwg-go.so` is a different Go shared library. Do not call the whole AAR Apache-only. Narrow the needed backend and audit shipped contents. [E-WG-NATIVE] [E-WG-TOOLS] |
| **OpenVPN 3** | Current source snapshot `0b27d9df493db8bc9dc459a2b1cb6b67037742b2`, `LICENSE.md`: **AGPL-3.0-only OR MPL-2.0**, with an OpenSSL exception for the AGPL route. This is a source snapshot, not a selected stable release. [E-OVPN3] | **CONDITIONAL, additional MPL-route candidate for the OpenVPN adapter.** Correct any AGPL-only assumption. Select and audit an actual release, per-file coverage, TLS/crypto/compression dependencies and native binding before adoption. The option does not relicense OpenVPN 2 or an unrelated mobile wrapper. |
| **OpenVPN 2, OpenConnect, Naive-specific integration** | No fresh exact release/source/dependency closure completed in this bounded audit. | **CONDITIONAL for further targeted evaluation; no distribution approval.** Retain existing protocol research and adapter boundaries. If Chat 03 makes one a sprint requirement, choose its exact upstream pin and clear the actual native graph; do not infer permission from another project's similarly named protocol adapter. |

`flutter_vless`, `imanheidary/v2box` and alternative Flutter wrappers are evaluated at file/native-artifact level in §6. Their package name or top-level license is not a substitute for this matrix.

## 4. License obligations for the actual distribution

| License / route | Compatibility with independently owned private product code | Concrete distribution duties |
|---|---|---|
| **MIT / BSD permissive components** | Usually suitable, after provenance and dependencies are cleared. | Preserve copyright and applicable license/disclaimer text; respect BSD's precise notice/non-endorsement conditions where present. No automatic right to trademarks, unrelated assets or a dependency's code. The inspected MIT wrapper/tool licenses are component-scoped. |
| **Apache-2.0** | Suitable for proprietary combinations under its terms. | Include the license, retain applicable notices, mark modified files, and carry relevant upstream NOTICE attribution when supplied. Patent grant/termination and trademark limitations matter. Generated/app code must still have cleared dependencies. [L-APACHE] |
| **MPL-2.0** | A proprietary Larger Work can contain covered components; the independent files need not all become MPL. | Make the MPL-covered source corresponding to the distributed executable available, including changes; identify the source location and license, preserve notices and recipients' MPL rights. This applies to unchanged covered code too. Keeping source in separate files helps define the boundary; renaming a copied file does not remove copyleft. [L-MPL] [L-MPL-FAQ] |
| **GPL-2.0 / GPL-3.0; exact only/or-later form matters** | Commercial sales are allowed. Incorporating covered client/service code into a combined private application is **NO-GO** under the current product objective absent an adequate exception/license. | For covered distributed work, supply the appropriate complete corresponding source and notices under the selected license version, including required build/install material; GPLv3 may require Installation Information for applicable User Products. Do not impose conflicting EULA/DRM restrictions. GPL itself does not require publishing every unrelated backend or using a GitHub fork. [L-GPL-FAQ] |
| **LGPL-2.1 / LGPL-3.0** | **CONDITIONAL** library integration, not an automatic proprietary exception for everything nearby. | Preserve library notices/license and library-source/modification duties; provide a compliant replacement/relink route. Suitable dynamic loading must actually permit a compatible modified library; static integration generally needs corresponding application object/material needed to relink. Preserve permitted modification/reverse-engineering rights and check version-specific installation obligations. Mobile packaging/signing can complicate the practical route. [L-LGPL] |
| **AGPL** | Strong copyleft; do not assume server/network use has the same obligations as ordinary GPL. | Modified network-interactive covered software can trigger remote-source duties as well as distribution duties. Apply the exact license and exceptions; choose a valid alternative license only where the rights holder actually offers it. [L-AGPL] |
| **Custom / extended / commercial terms** | Requires the exact rights grant for the chosen files/version and use. | Noncommercial permission does not authorize this commercial product. Verify rights to modify, redistribute, keep owned code private, ship dependencies and use assets; satisfy named attribution/UI/source conditions if applicable. Do not delete disputed additional terms and declare the remainder approved. |
| **Public source without a verified license / binary-only release** | **NO-GO as a code donor** until a rights holder grants sufficient permission. | Public visibility/download availability is not a general reuse license. Absence of an identified source license is an evidence gap, not a claim that the vendor's own distribution is unlawful. [L-GITHUB] |

### Process separation and brand boundaries

A process/socket/CLI interface can be evidence of independent programs, but the nature and intimacy of the interaction matter; the FSF explicitly does not treat transport choice alone as decisive. FFI/JNI/dynamic linking to a GPL core is not a general exemption. Even an independent redistributed GPL executable retains its own source/notices obligations. Therefore a desktop Mihomo/sing-box process is **CONDITIONAL on reviewed separation and compliance**, while direct integration into the private combined app is **NO-GO under the present plan**. [L-GPL-FAQ]

For Xray, the positive private-product route comes from its MPL terms and cleared dependencies, not from the mere fact that `ProcessBuilder` was used. For Hiddify/Karing, preserve the actual additional wording and examine its scope. A Hiddify commercial-use consent alone may leave source/fork and other conditions intact; an alternative grant must expressly cover the intended private-code distribution and all relevant rights. Treat name/association restrictions separately from commercial-use restrictions. PVNetwork must use its own supplied brand assets; a code license does not establish ownership of a mascot, icon, font, screenshot or trademark.

## 5. Dependency and artifact closure

**The review is not a completed transitive-license clearance of a release.** No final APK/AAB/installer was built or extracted in this chat. A graph of potential modules and a root LICENSE cannot substitute for the exact linked/bundled graph. Rejected GUI applications do not need a new exhaustive dependency campaign; the selected distribution does.

### 5.1 Existing product dependencies

| Component / surface | Observed version or location | Obligations and remaining gate |
|---|---|---|
| Kotlin/toolchain | Kotlin `2.4.10`; AGP `9.3.3`; documented Gradle `9.5.0`, JDK `21`. | Preserve the baseline for comparison. Kotlin license at `v2.4.10` is Apache-2.0; resolve exact build/runtime artifacts and distinguish tools from distributed runtime. No toolchain upgrade is authorized by this report. [R11] [D-KOTLIN] |
| Compose desktop | Compose Multiplatform `1.11.1`. | Apache-2.0 at the inspected tag. Include actual Skiko/Skia and other native dependencies, fonts and JRE in the desktop artifact inventory; the Compose license alone does not cover them. [R5] [D-COMPOSE] |
| Android UI/runtime | Compose BOM `2026.09.00`, activity-compose `1.13.0`, core-ktx `1.19.0`, lifecycle-runtime-ktx `2.9.4`; foundation/material3/ui resolved through BOM. | Record resolved versions, POM/artifact license material and transitives. Do not misrepresent the BOM as a complete per-artifact license inventory. [R4] |
| JNA + jna-platform | Both `5.19.1`; source tag resolves to `1a91122853f6ab6f1fb2a4a284a6cf2ed8af0a4d`. | Actual LICENSE offers `Apache-2.0 OR LGPL-2.1-or-later`; **select Apache-2.0** for the JNA-covered code. Include license/notices and separately inventory `jnidispatch`/libffi/native payloads. An OR is a choice, not an obligation to apply both licenses. [R5] [D-JNA] |
| Packaged Java runtime | Desktop distribution generated by Compose packaging. | Exact vendor/build and packaged modules remain UNKNOWN. OpenJDK documents GPLv2 plus Classpath Exception for covered parts, and separate third-party components. The exception can protect independently licensed application modules; it does not remove JRE redistribution notices/source duties. Audit the actual distributed runtime. [D-JDK] |
| Xray executable | Android archive version `26.7.28` in build script; committed extracted binary. | Archive pin is OBSERVED, artifact authentication and complete source/dependency matching remain OPEN. Provide MPL-covered source and licenses for the exact shipped build, even when upstream files are unchanged. [R4] |
| hev native library + JNI entry point | Historical `2.17.1`; committed `.so`; `apps/android/src/main/kotlin/hev/htproxy/TProxyService.kt`. | Audit upstream sources, submodules, JNI file provenance and native build flags. Preserve all applicable notices; record source commit, submodule commits and actual binary SHA-256. A Kotlin package named after upstream is not ownership proof. [R8] [R9] |
| GeoIP/geosite/rules, fonts, icons, certificates, optional Wintun/driver assets | Included/generated/downloaded content must be discovered from final artifacts. | Track asset/provider licenses independently. Do not copy competitors' logos, screenshots, translation files or rule datasets without permission. Do not infer a Wintun license from a Go wrapper's license or from a similarly named driver. |

### 5.2 Required closure record for **every distributed dependency**

The implementer must generate a machine-readable SPDX or CycloneDX SBOM per OS/ABI/flavor and a human-readable notice/source index, then reconcile it with final extracted artifacts. Include Gradle runtime classpaths, Go module/build information, native archives and submodules, Apple Pods/Swift packages/frameworks if selected, packaged JRE, data and assets. Keep build-only tools in a separate inventory so they are not confused with shipped libraries.

For each component record: canonical URL; exact commit/version; package and binary hashes; file-level license expression and selected dual-license branch; modifications; static/dynamic/process/bundled-data relationship; target/ABI; source offer or source-download location; copyright/NOTICE material; dependency parents; build recipe/toolchain; security-review state; and reviewer/date.

Fail the release gate when the concluded license or sufficient redistribution rights remain unresolved, or for an unidentified prebuilt AAR/XCFramework/SO/DLL; unlicensed copied files/assets; missing copyleft source or relink material; floating native download; missing source-to-binary correspondence; incompatible commercial terms; or an undisclosed dependency not in the allowlist. Upstream `NOASSERTION` metadata requires inspection of the actual terms; document approved custom terms with an appropriate `LicenseRef` and evidence. Do not mark a whole plugin MIT when its AAR/TUN/core has different terms.

### 5.3 Targeted native dependency findings

The selected HEV **2.19.3** source has the following exact gitlinks. Their inspected license files support a permissive route, with the listed notices preserved. This does **not** establish which revisions produced the already committed **2.17.1** binary. [D-HEV-MODULES]

| Parent path / upstream | Gitlink commit | Inspected license and obligation |
|---|---|---|
| `src/core` / `heiher/hev-socks5-core` | `dadbb54bf7509f6dea3c1b30e45198efa400679c` | MIT; retain copyright/license. [D-HEV-CORE] |
| `third-part/hev-task-system` / `heiher/hev-task-system` | `d0365c370aadd39129924b563b80751cb63332d7` | MIT; retain copyright/license. [D-HEV-TASK] |
| `third-part/lwip` / `heiher/lwip` | `495671e59d736ef59f6cfa7dc9d35b7359921062` | BSD-3-Clause; retain copyright, conditions and disclaimer, including no endorsement. [D-HEV-LWIP] |
| `third-part/yaml` / `heiher/yaml` | `94441ec8777eb319936735a58b376963d09c73d4` | MIT; retain copyright/license. [D-HEV-YAML] |

For **libXray**, the checked-in Go graph includes its own cryptographic/network forks and modules, so it is not interchangeable with the standalone Xray binary's graph. Its build orchestration defaults `LIBXRAY_GOMOBILE_VERSION` to **`latest`**, performs dependency/tool resolution and snapshots/restores manifests. Freeze gomobile/gobind and capture the **resolved build graph**; recording the repository's original `go.mod` alone is insufficient. The newer wrapper's ABI/global-state/ownership behavior was not diffed comprehensively against the older research pin `d0ab60ae4dd91cf119c878152d12103e6f84b78a`; review allocation/free, concurrency and lifecycle contracts before replacing an existing bridge. [E-LIBXRAY-MOD] [D-LIBXRAY-BUILD]

For **WireGuard Android**, the native tree pins `wireguard-tools` to `e2ecaaa739144997ccff89d6ad6ec81698ea6ced`, whose COPYING contains GPLv2. Its separate Go module selects WireGuard Go `v0.0.0-20250521234502-f333402bd9cb`; the full Go/native artifact closure remains OPEN. This is why the top-level Apache license cannot approve the assembled AAR. A `.so` suffix also does not prove a shared-library boundary: the tools build uses executable targets. [E-WG-NATIVE] [E-WG-TOOLS] [D-WG-GO]

The rejected full-app source bases have distinct native graphs too: Karing's sibling `vpn_service` is not pinned by its app checkout; Hiddify uses a `hiddify-core` submodule/FFI boundary; v2rayNG uses AndroidLibXrayLite and HEV; Amnezia has Qt and protocol-specific native components. Their GUI licenses cannot be replaced by the license of one embedded engine. This report does not spend the implementation budget exhaustively clearing applications that are already excluded from the private-code source base. [A-KARING-PUBSPEC] [D-HIDDIFY-MODULES] [D-V2RAYNG-MODULES] [D-AMNEZIA-MODULES]

## 6. Flutter plugins: maturity, platforms and build truth

**No Flutter VPN plugin is approved here as a complete production package. `flutter_vless` is the strongest candidate for a separate, bounded experiment; it has not demonstrated a net saving over the existing KMP product.** MIT wrapper rights, native dependency rights, successful compilation and actual OS VPN behavior are separate questions.

### 6.1 Exact source candidates

| Project / inspected source | License and important dependency finding | Decision |
|---|---|---|
| **`XIIIFOX/flutter_vless`**, `1.1.7`, tag `v1.1.7` → `f73ed3f517bd5474fb36c52dbec13908fe5c68c7` | MIT first-party license. Android selects `dev.tfox.fluttervless:xray-android:26.10.31-protect2`; full native artifact/source correspondence is unverified. Root pubspec uses ranges for federated packages, so the root version alone does not lock the graph. [F-VLESS-LICENSE] [F-VLESS-PUB] [F-VLESS-GRADLE] | **CONDITIONAL for licensed wrapper reuse and a spike; no production GO.** Most relevant Flutter candidate; do not migrate the product on package marketing alone. |
| **Independent `imanheidary/v2box`**, source `bc194f6a2fddee401d1c3b8141fec8d76c8d19f3`; manifest `v2ray_box 1.0.6` | MIT wrapper; repository/package identity is separate from the official V2Box GUI. Android expects host-supplied `libxray.aar`; Apple requires external frameworks; sing-box remains separately licensed. No package-archive/tag correspondence was established. [F-BOX-LICENSE] [F-BOX-PUB] [F-BOX-GRADLE] | **NO-GO as a complete cross-platform drop-in; CONDITIONAL for individually reviewed bridge files.** Downgrade the earlier matrix's optimistic direct-candidate interpretation. |
| **`blueboy-tm/flutter_v2ray`**, manifest `2.0.0`, source `73550ff3e89be6681e595636c8ed100361e67d31` | MIT wrapper. Android references `com.github.2dust:libv2ray:v26.10.17@aar` through a release-download Ivy pattern; producer commit, checksum and bundled tun2socks source were not matched. Tag association was not established. [F-V2RAY-LICENSE] [F-V2RAY-PUB] [F-V2RAY-GRADLE] | **CONDITIONAL Android-only evaluation; NO-GO for satisfying the cross-platform requirement.** An upstream AAR coordinate is useful provenance input, not clearance of its bytes. |
| **`amir-zr/flutter_v2ray_client`**, manifest `3.5.0`, source `c296aad34eeed7d65119a310c79fc64ebf58251b` | MIT wrapper. Loads checked-in `android/libs/libv2ray.aar` and ABI-specific tun2socks libraries; exact producing source/build correspondence is unresolved. Tag association was not established. [F-CLIENT-LICENSE] [F-CLIENT-PUB] [F-CLIENT-GRADLE] | **CONDITIONAL Android-only evaluation; NO-GO as an iOS/cross-platform shortcut.** Its registered iOS implementation is a stub. |

These are exact **source inspection pins**, not claims that a pub archive or downloaded native artifact was reproduced from them. Wrapper MIT allows reuse of code actually covered by that grant with its notice; it does not establish third-party file provenance or override Xray/MPL, sing-box/GPL, TUN, framework or driver terms.

### 6.2 Platform claims versus inspected implementation

| Candidate | Android | iOS | macOS | Windows | Linux |
|---|---|---|---|---|---|
| `flutter_vless 1.1.7` | Registered federated implementation; native runtime dependency identified. | Registered; substantial Swift source present. Host/extension/build/runtime not proven here. | Federated implementation with substantial native source present. | Federated implementation with substantial C++ source present; different-commit CI evidence below. | **Not registered** in inspected pubspec. |
| Independent `v2ray_box 1.0.6` | Native bridge source; host must supply/package AAR. | Source and manual framework/PacketTunnel integration; no demonstrated signed build. | Source/podspec describes separate Xray/sing-box CLI and system proxy; no system-wide VPN proof. | **Stub:** only `getPlatformVersion`; other methods not implemented. | **Stub:** only `getPlatformVersion`; other methods not implemented. |
| `flutter_v2ray 2.0.0` | Only platform registered by package. | Repository contains a version-only stub; not a supported VPN implementation. | Not registered. | Not registered. | Not registered. |
| `flutter_v2ray_client 3.5.0` | Registered implementation with local native artifacts. | **Registered but explicitly unimplemented:** version-only handler. | Not registered. | Not registered. | Not registered. |

Sources: manifests and pinned handlers/podspecs, not screenshots. Native file presence establishes that implementation work exists, **not that its methods or tunnel data path work**. [F-VLESS-PUB] [F-VLESS-IOS] [F-VLESS-MAC] [F-VLESS-WIN] [F-BOX-WIN] [F-BOX-LINUX] [F-BOX-IOS] [F-BOX-MAC] [F-V2RAY-PUB] [F-V2RAY-IOS] [F-CLIENT-PUB] [F-CLIENT-IOS]

### 6.3 Native provenance and reproducibility blockers

For **flutter_vless**, the Android build uses Kotlin `2.2.20`, AGP `8.1.0`, compile SDK `34`, min SDK `23`, target SDK `35` and four ABIs. These values were read, **not successfully built locally**. The package requires Dart `>=3.4.0 <4.0.0` / Flutter `>=3.3.0`; its federated dependencies include `^1.1.7` and `^1.1.7+1`. Freeze the resolved lock and verify every platform package. A configurable local Maven repository can also change where the fixed runtime coordinate resolves. [F-VLESS-PUB] [F-VLESS-GRADLE]

Its `build_tun2socks.sh` clones or pulls **floating `xjasonlyu/tun2socks`**; its alignment check warns on mismatch rather than establishing a release gate. A separate unpublished preview manifest pins `57550350c847c469aaa26a4748f4e51b6629ee7b` and names `native-tun-forwarder-preview-1`. **That manifest does not prove the source of the shipping Maven artifact.** The named tun2socks commit's own LICENSE is **MIT**, not GPL; the issue is unclosed artifact provenance and transitive terms, not an invented GPL classification. Inspect actual AAR/framework/EXE contents and source/build attestations before reuse. [F-VLESS-TUN] [F-VLESS-PREVIEW] [F-TUN-LICENSE]

For **independent v2ray_box**, Android `compileOnly` for local `libxray.aar` transfers packaging work to the host. The iOS podspec expects manual `Libbox.xcframework`/`LibXray.xcframework` work and warns about duplicate Go symbols; pod metadata version `1.0.0` differs from Dart `1.0.6`. The Android sing-box builder defaults `SINGBOX_REF` to `latest`, with fallback `v1.12.22`, and compiles an executable named `libsingbox.so`. None of that grants proprietary linking rights to sing-box or proves a ready packet tunnel. [F-BOX-GRADLE] [F-BOX-IOS] [F-BOX-SINGBOX]

### 6.4 Maintenance and actual verification ledger

| Evidence inspected | What it supports / limit |
|---|---|
| `flutter_vless` repository is not archived and reports a push on **2026-11-05**; release tag was resolved. | Current maintenance activity, not a support contract, security audit or proof of future maintainer capacity. [F-VLESS-META] |
| Exact release-commit Actions query for `f73ed3f...` returned **zero runs**. | No matching release-commit CI run was established through that query. Do not transfer a different commit's success to this tag. [F-VLESS-TAG-RUNS] |
| Development-commit CI **42073917574** at `b1d7e2df15883dfe4e68514e1c7fdf484986296d` reports overall **failure**. Job/step metadata was inspected. | Dart/Flutter checks, Android native unit tests and the Windows example build succeeded. Android's mandatory CI gate failed before emulator execution; iOS optional-bridge checking failed and the example Xcode build was skipped. These mixed results establish scoped work, not universal failure or a release pass. [F-VLESS-CI] |
| macOS validation **42045513810**, at different commit `dd13caa61d300ca657431c8e0d567281135f0150`, reports success; child **42047084945** also reports success. | Scoped upstream macOS validation evidence at those commits. It does not authenticate the release tag, demonstrate PVNetwork integration or replace device/entitlement evidence. [F-VLESS-MAC-CI] [F-VLESS-MAC-CHILD] |
| Independent v2box source snapshot is from **2026-02-26**; v2ray wrapper snapshots are **2026-11-02** and **2026-09-17**, respectively. | Dates identify inspected work; they are not proof of abandonment or production maturity. No successful CI run or device test was verified for these three candidates in this audit. [F-BOX-COMMIT] [F-V2RAY-COMMIT] [F-CLIENT-COMMIT] |
| Local tool availability check | `flutter`, `dart` and `gradle` were not installed. **No local Flutter dependency resolution, compile, native binary download, device run or artifact hash reproduction was performed.** Local document checks and remote source/CI reads are the completed verification scope. |

The next useful evidence is a legally cleared, pinned example that actually compiles and passes the same data-path/lifecycle checks as the current Android baseline. §8 defines the timebox and switch criteria. Additional star counts, broad README protocol claims or more generated UI demos do not resolve this decision.

## 7. Recommended ready-code package and version policy

### 7.1 Selected package

Use **PVNetwork-owned KMP/domain/UI + Xray-core + hev-socks5-tunnel**, with platform lifecycle code owned by PVNetwork and all native inputs built or acquired reproducibly. Keep `EngineAdapter` as the product boundary. Add **XTLS/libXray** only when a measured embedded/mobile requirement justifies it; its MIT bridge is reusable independently of Flutter. This package preserves existing work and avoids implementing VPN protocol engines. It does not turn Xray into a universal implementation of WireGuard, OpenVPN or every researched protocol.

The smallest first deliverable is a traceable Android/desktop Xray path that satisfies the existing canonical import, secret handling and connection contracts. Keep distinct mature engines behind separate capability adapters when required by the accepted protocol plan. Do not include sing-box/Mihomo solely to claim a larger protocol count. Apple still needs an owned host, Packet Tunnel integration and entitlement/build evidence.

### 7.2 Pin ledger and upgrade policy

| Purpose | Exact version / source commit | Decision |
|---|---|---|
| Existing Xray comparison baseline | `v26.7.28` — `5ca6f4b7d4dc20a881d4330e498892697627ec0c` | Preserve to reproduce existing behavior; it is **not automatically approved for a new release**. The build names archive SHA-256 `a442892c175fa648fc56866ec872aac441c5a6b8946a1b60f0258ae16a7fb402`; this chat did not independently download/verify that archive or the committed output. [R4] [P-XRAY-OLD] |
| Existing hev comparison baseline | `2.17.1` — `9a06bc6e7989da54e3d32ff701ef7a7ce4995d3a` | Historical record names a local Android NDK r27c build and SHA-256 `0c26c47620d7a3c881650e2a8d0f35cf164dd680b3aa0db35e37d1e3b88707e0`. Treat it as a receipt to reconcile with the real binary, not new verification. [R8] [P-HEV-OLD] |
| Preferred Xray upgrade candidate | `v26.10.31` — `a4a3dff5b20c2faae567b742c172c7b95e75c083` | CONDITIONAL: release/source identity and MPL inspected; build, dependency/advisory review, protocol/lifecycle regression and binary hashes remain. [E-XRAY] |
| Preferred hev upgrade candidate | `2.19.3` — `c5584feca8f6df70ee53eb2879e034b849a3d15a` | CONDITIONAL: root MIT inspected; pin its submodules and produce the Android/JNI artifact with a recorded toolchain. [E-HEV] |
| Optional embedded bridge candidate | `XTLS/libXray v26.10.31` — `7fe8528063e54343af8ba9a37431afc56000de21` | CONDITIONAL: MIT bridge whose module selects the current Xray commit above, Go `1.27.3`. Adopt that coherent source graph if selected; do not mix it silently with the older process binary. [E-LIBXRAY] [E-LIBXRAY-MOD] |
| Product build baseline | Kotlin `2.4.10`, Compose `1.11.1`, AGP `9.3.3`, Gradle `9.5.0`, JDK `21`; JNA `5.19.1` under its Apache option | Retain for baseline reproduction. Resolve exact runtime graph and JDK vendor/build; version names alone are not a complete lock or clearance. [R4] [R5] [R11] [D-JNA] |
| Flutter VPN plugin | **None approved as the production package** | §6 records inspected snapshots for evaluation, not a recommendation to ship the corresponding AARs/frameworks. |

Resolve stable tags to immutable commits and preserve a local/source archive plus dependency lock, actual binary hash, build recipe and license evidence. A Git tag, Git blob SHA-1 and binary SHA-256 serve different purposes; never substitute one for another. The candidate upgrades above are a recommendation for controlled validation, **not a claim that newer automatically means safer**. Review security advisories and release changes for the selected graph at implementation time, then promote one coherent set only after compliance and device gates pass. Keep a reproducible rollback package; do not auto-follow `main`, `latest` or an unverified download URL.

## 8. Three architecture options and a bounded decision experiment

| Criterion | A — existing KMP/Compose + native adapters | B — Flutter app + ready VPN plugin | C — Flutter presentation + retained KMP/native boundary |
|---|---|---|---|
| Reuse of current work | Highest: shared profile/secret/import/adapter contracts, Android service and desktop shell survive. | Recreates or migrates UI/application state and plugin semantics; native/plugin issues still need fixing. | Retains domain contracts but adds bridge serialization, error/cancellation/lifecycle mapping and another UI runtime. |
| Proven starting evidence | Existing main packaging/test CI; historical scoped runtime receipts. Device work remains. | Source audit plus scoped upstream CI at specified commits; no local pinned build or device receipt. | A proposed design; no PVNetwork hybrid implementation was demonstrated. |
| Private commercial code | Viable with owner-controlled code and component-specific compliance. | Depends on every native component, not Dart wrapper label. A GPL-derived Android service or TUN library can defeat the intended license model. | Same dependency rules; FFI/method channels do not sanitize incompatible code. |
| Platform coverage | Shared Android/JVM/iOS targets observed; Apple host/extension still must be built and validated. | Use actual platform implementation table in §6, not README folders. | Android add-to-app is feasible; Apple remains native. Do not assume the same add-to-app support on Windows/Linux. [T-HYBRID] |
| Security/maintenance | Smallest new surface; current process/TUN provenance and lifecycle defects must be closed. | Additional maintainer/artifact supply chain; plugin forks and outdated cores create work. | Most moving boundaries/toolchains; keep one authoritative connection/profile model. |
| Speed assessment | **Recommended inference** from code and CI already present; not a measured elapsed-time guarantee. | No demonstrated end-to-end time advantage. | Reasonable escape route if measured UI benefit later justifies cost; poor default for a six-day target. |
| Decision | **GO for continuation; release CONDITIONAL.** | **NO-GO for wholesale migration now; only a license-cleared spike can reconsider it.** | **CONDITIONAL future experiment; do not add it merely to use a plugin.** |

### Experiment contract for Codex after consolidation (PROPOSED, maximum four engineering hours)

1. **Before any spike:** pass source/license provenance for the candidate. Stop immediately on incompatible/copied GPL files or an unidentified binary; a working demo cannot override this gate. Use a private isolated checkout and synthetic configuration, without production credentials.
2. **Baseline, up to one hour:** build existing Android debug and run the shared/adapter tests; record commit, SDK/JDK/Gradle/NDK and output hashes. Exercise one already-selected Xray profile on the same physical device/server used for comparison. Record actual obstacles rather than upgrading everything.
3. **Candidate, up to two hours:** only if §6 leaves a viable legally clear candidate, build its pinned example, adapt through PVNetwork-owned contracts, and run the same import, permission, traffic and teardown checks. Record actual commands, logs and artifacts; a folder listing or `flutter analyze` is insufficient.
4. **Compare, up to one hour:** time to reproducible build and real data path; custom native code still required; behavior/secret-model parity; dependency/license work; measured binary size and memory; future Apple/Windows bridge effort; migration and rollback cost. Record measured values or UNKNOWN, never invented percentages.
5. **Switch gate:** all legal/provenance gates pass, actual Android data path and lifecycle are no worse, existing canonical tests remain, and a task-level estimate including migration shows a clear net saving within the remaining sprint. Otherwise keep A. Chat 07 owns the integrated architecture decision; this report does not authorize a rewrite.

Use a minimal fair device set: permission accept/deny, connect/disconnect/reconnect, external profile import without account, DNS/IPv4/IPv6 observations, Wi-Fi-to-cellular change, app/service/core termination and route cleanup. Detailed protocol and QA matrices remain Chat 03/06 deliverables. A four-hour spike is an experiment budget, **not a promise to certify those platforms**.

## 9. What white-label and AI tools actually accelerate

| Tool / route | Verified capability | PVNetwork decision |
|---|---|---|
| Android product flavors / Flutter flavors | Official Flutter docs configure identifiers, names, icons, endpoints and build variants; Apple flavors use Xcode schemes/configurations. [T-FLAVORS] [T-APPLE] | **GO for configuration automation** within an approved application. It grants no rights to a competitor's code and supplies no VPN engine or backend. KMP/Android can use Gradle variants without a Flutter migration. |
| `flutter_flavorizr 2.6.0` | MIT at `89ebdb52c7aa349e13f424a8ba3e82b9aea3f499`; pubspec requires Dart `>=3.9.0 <4.0.0`; generator modifies platform/build assets. [T-FLAVORIZR] | **CONDITIONAL dev-only accelerator** if Flutter is selected. Pin package and lockfile, audit template/asset downloads, review generated diff in a disposable branch. Do not run it over the existing KMP product. |
| FlutterFlow **Studio** | Official docs state ownership of generated output, export support and native method-channel/custom-plugin integration; third-party packages retain their licenses. [T-FF-OWN] [T-FF-EXPORT] [T-FF-NATIVE] | **CONDITIONAL for UI/account screens only**, after a small exported build proves value. Preserve native customizations across regeneration. No verified replacement for `VpnService`, Packet Tunnel, license audit or release QA. |
| FlutterFlow's separate current AI-builder product | The `/ffx/what-you-can-build` documentation says compiled phone builds can be downloaded, but source export/GitHub export is unavailable. [T-FFX] | **NO-GO for this source-controlled proprietary product workflow** at the inspected snapshot. Do not confuse this product with Studio's export documentation. |
| DhiWise / Rocket | Official Flutter documentation describes code generation/download/sync; current terms retain customer rights in generated Customer Content while the platform remains vendor-owned. [T-DHIWISE] [T-ROCKET] | **CONDITIONAL UI scaffolding only.** Verify the specific product, subscription, export/native editing rights and third-party output before selection. No evidence of a complete independently buildable universal VPN client. No purchase is recommended by this report. |
| WLVPN commercial SDK/sample-app route | Vendor FAQ offers SDKs and sample apps, requires reseller agreement (or NDA for evaluation), says it does not build/brand partner apps; protocol FAQ lists WireGuard/OpenVPN/IKEv2/IPsec. [T-WLVPN] [T-WLVPN-PROTOCOLS] | A real paid commercial integration route, **not selected**: source redistribution/ownership, arbitrary external subscriptions, own infrastructure and Xray/Naive compatibility are not established by the inspected public docs. Obtain concrete contractual terms and a reproducible sample before considering it. |
| Codex + Z.ai/ZCode | Codex documents repository `AGENTS.md` guidance; ZCode's official docs describe working in a project directory with its coding agent. [T-CODEX] [T-ZCODE] | **GO as development tools under the contract in §10.** They write/test owned integration code; they do not relicense source, supply absent signing entitlements or prove a VPN data path. No model-speed or cost superiority is asserted. |

The useful workflow is to provide one accepted source/architecture decision, exact file ownership and acceptance tests to implementers. Do not feed restricted competitor code to an agent and request a translation or superficial rewrite as a licensing workaround. Requirements and publicly observable generic behavior can guide independent implementation; preserve provenance of any actual reused expression.

## 10. Exact Codex/Z.ai implementation handoff

The following is a **future implementation contract**, to be reconciled with Chats 01/03/06 by Chat 07. It is not permission to change production code during this documentation task.

```text
PVNETWORK / IMPLEMENT THE ACCEPTED SOURCE-REUSE DECISION

Read AI_START_HERE.md, AGENTS.md, the newest real handoff, this report,
and 00-integration-handoff.md if it exists. Reconcile the actual HEAD.
The current public repository is a research/evidence surface. Use the
owner-approved private product repository for proprietary code. Do not
create a new repository, change visibility or publish new product code
as an incidental workaround.

Keep the PVNetwork canonical profile, secret store, connection state and
EngineAdapter contracts. Keep the existing KMP/Compose architecture unless
the accepted consolidated decision records a successful alternative spike.
Do not implement VPN cryptography/protocol engines from scratch.

Allowed starting package: the approved owner-controlled baseline and only
the exact engine/native pins admitted by this report's artifact gate.
CONDITIONAL means audit/build/test first, not permission to silently ship.
Do not import competitor GPL GUI/services, unlicensed assets, decompiled
code or a wrapper whose native dependency provenance remains unresolved.
Do not remove copyright notices or ask another model to relicense code.

Work in isolated branches with exclusive file ownership. One implementer
owns Android/native integration and dependency pins. A second may own
notice/SBOM tooling and synthetic fixtures. Neither edits the other's files.
Use Codex for the cross-module integration and final review; Z.ai may take
bounded parser/fixture/UI or compliance tasks that the integration owner
assigns. These assignments are an operating choice, not a model benchmark.

Execute L02-1 through L02-5 below in dependency order. If a branch is blocked
by an external contract or signing account, continue independent owned work;
do not substitute another unreviewed binary. Never downgrade a dependency
or widen capability claims just to obtain a green build.

Every PR records changed files, applicable source/license pins and dependency
changes, relevant test results, limitations and rollback. Binary or runtime
changes also record affected artifact hashes and required device evidence;
mark genuinely inapplicable evidence explicitly. Parser tests, upstream CI
and actual device traffic are separate evidence. No automatic merge or
production/store deployment is authorized
by this preflight report. Finish with DONE / OPEN / BLOCKED and exact next
action; do not claim the whole product is ready after packaging succeeds.
```

### Five highest-leverage implementation tasks

| ID / dependency | Exact existing or proposed targets | Required result |
|---|---|---|
| **L02-1 — artifact inventory and ownership**; first | Existing `apps/android/build.gradle.kts`, both `apps/android/src/main/jniLibs/arm64-v8a/*.so`, `apps/desktop/build.gradle.kts`, `apps/android/src/main/kotlin/hev/htproxy/TProxyService.kt`; proposed private-product `docs/licensing/DEPENDENCIES.lock.json` | Identify every actual native binary, original source/submodules, owner/license, selected dual-license route, and binary/archive hashes. Reconcile with complete resolved runtime graph. Missing native provenance fails closed. Confirm private product location and rights to the existing product files. |
| **L02-2 — deterministic native acquisition**; after L02-1 | `apps/android/build.gradle.kts`; proposed `scripts/verify_native_artifacts.py` and a reproducible hev/native build recipe | Remove the unverified-existing-target path; authenticate both downloads and extracted/committed outputs on every relevant build, even when Gradle/cache says up-to-date. Prefer build-generated native inputs over opaque committed binaries. Negative checks: modified binary, wrong archive, missing pin and stale version must fail; exact approved cache must pass. Record NDK/Go/toolchain and ABI/16-KB checks. |
| **L02-3 — legal distribution bundle**; after L02-1, parallel with L02-2 | Proposed `THIRD_PARTY_NOTICES.md`, `licenses/`, `docs/licensing/SOURCE_INDEX.md`, per-target SBOM generation; Android `MainActivity.kt`, desktop UI entry points and packaging scripts | Package readable license/copyright/NOTICE files, accurate source download/offer information, modification notices and any relink materials. Keep source required by MPL/GPL/LGPL accessible to recipients without exposing unrelated private business code or secrets. Inspect final APK/installer contents; generated reports must match shipped hashes. |
| **L02-4 — validate the selected native path**; after L02-2 | `apps/android/src/main/kotlin/com/pvnetwork/client/PvVpnService.kt`, `VpnHub.kt`, existing `core/foundation` connection contracts and `engines/xray-adapter` | Prove actual traffic and cleanup on a physical Android device. Check socket protection/route exclusions, correct OS API guards, permission/lifecycle, DNS, IPv6, network changes, process death, concurrent connect/stop and secret-safe errors. Report CONNECTED only against agreed data-path/health criteria. Use Chat 03/06 acceptance; this report's source choice is not a waiver of runtime defects. |
| **L02-5 — independent release review / optional bridge spike**; after L02-1–4 for release | Proposed private-product native bridge module behind existing adapters; future `apps/ios` host/Packet Tunnel only when Apple prerequisites exist; `.github/workflows/m4-public-apps-ci.yml` and native/license gates | Independent reviewer checks source-to-binary correspondence, full SBOM, notices/source availability, advertised capabilities and release evidence. If embedded/Apple integration needs libXray, use its exact dependency-compatible pin and serialize/free/own its native boundary correctly. Flutter remains optional under §8's timebox, not a parallel product rewrite. |

### Reproducible commands and receipts

The repository uses externally installed Gradle in the inspected CI; no Gradle wrapper was observed. On the approved builder with the pinned toolchain/Android SDK, start with:

```bash
gradle --version
java -version
gradle --no-daemon :core:foundation:jvmTest :engines:xray-adapter:jvmTest :apps:desktop:test --stacktrace
gradle --no-daemon :apps:android:assembleDebug --stacktrace
gradle --no-daemon :apps:desktop:packageDistributionForCurrentOS --stacktrace
gradle --no-daemon :apps:android:dependencies --configuration debugRuntimeClasspath
gradle --no-daemon :apps:desktop:dependencies --configuration runtimeClasspath
```

These are **commands for the implementer**, not commands claimed to have passed here. If variant configurations differ on the selected toolchain, list the actual resolvable configurations and record the correction rather than fabricating output. A dependency tree is inventory input, not a license/SBOM result. Run the artifact-verifier negative cases after that verifier exists; do not cite a proposed script as completed evidence.

For an admitted Flutter candidate in its separate pinned example/host: capture `flutter --version`, `flutter doctor -v`, `flutter pub get`, `flutter analyze`, `flutter test` and `flutter build apk --debug --target-platform android-arm64`; capture native prerequisite commands from that exact source. Missing generated AAR/framework/TUN artifacts are BLOCKED, not fixed by downloading arbitrary releases. Build Apple on macOS with the required Xcode/team/entitlements; Windows needs its own native build/package receipt.

Retain raw tool output privately, publish sanitized receipts with commit, command, environment, exit code, hash, target and limitation. Do not commit subscription links, real server secrets, signing keys, API tokens or unredacted diagnostics to this public report/repository.

## 11. Completion, open gates and decision owners

### DONE — this bounded preflight

- Read and reconciled the existing source/architecture research against the actual baseline and coordination rules.
- Inspected primary source/license manifests and named candidate versions; separated source reuse, artifact provenance, compilation, CI and device evidence.
- Prepared the GO / CONDITIONAL / NO-GO matrix, concrete obligations, architecture comparison, accelerator assessment and five-task Codex handoff in this single report.
- Independent report review found no material blockers after clarifying license metadata, unsupported engine wording and proportionate PR evidence. Local document checks found no unresolved placeholders, missing source-reference definitions, unbalanced code fences or malformed long commit/hash identifiers. These are documentation checks, not an app build or complete legal clearance.
- Kept production files, shared state and other workers' deliverables untouched. The PR is independent and is not merged by this task.

### OPEN — implementation/release evidence

- Complete per-target transitive SBOM and binary/source match; downloadable corresponding source and packaged notices; copied JNI/helper-file provenance.
- Repair and test the existing binary hash bypass; reproduce native builds and verify ABI/page-size compatibility.
- Current security/advisory checks for the exact delivered graph, actual Android data-path/device lifecycle, and regression/rollback receipts.
- A measured plugin/bridge spike if still justified after consolidation. No local Flutter/device build was performed here.

### BLOCKED / decisions requiring the right owner

| Item | Owner / next concrete action |
|---|---|
| Private product repository and existing code ownership | Product owner/integration lead confirms destination and contributor/source rights before proprietary implementation is published. This audit does not change repository visibility. |
| Restrictive custom licenses or GPL combined private-code distribution | Rights holder plus legal reviewer must provide a compatible license/exception; otherwise keep the component out of the private application. A sales page or MIT wrapper is insufficient. |
| Complete native provenance | Native integration owner supplies exact sources, dependency hashes/build recipe and artifact correspondence; unresolved components remain excluded from release. |
| Apple signed tunnel/Store proof | Release owner supplies the actual Apple build/signing environment and entitlement evidence; Chat 06 owns validation. |
| Final implementation architecture | Chat 07 reconciles this recommendation with all six reports. Default remains existing KMP; no automatic framework switch or universal six-day completion claim. |

**Preflight report completion is not app completion, legal clearance of an unknown binary, Store approval or production readiness.**

## Sources

Sources below are canonical upstream/project or official vendor/legal documents. Immutable commits are used for code decisions where resolved; website/vendor-contract pages are dated observations and must be rechecked before purchasing or shipping. No third-party source, logo or asset is reproduced in this report.

[R0]: https://github.com/DashSaman/PVN-amirrezagol/blob/ed21086e3e74772ab4dc31825eb87336f917fd67/docs/preflight-2026-11-06/START_HERE.md
[R1]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/app/CLIENT_SOURCE_REUSE_MATRIX.md
[R2]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/app/CROSS_PLATFORM_ARCHITECTURE_RECOMMENDATION.md
[R3]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/core/foundation/build.gradle.kts
[R4]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/android/build.gradle.kts
[R5]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/desktop/build.gradle.kts
[R6]: https://github.com/DashSaman/PVN-amirrezagol/actions/runs/35897687631
[R7]: https://github.com/DashSaman/PVN-amirrezagol/actions/runs/35877948152
[R7-M]: https://github.com/DashSaman/PVN-amirrezagol/actions/runs/35868899981
[R8]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/docs/M-P2_PUBLIC_APPS_VALIDATION.md
[R9]: https://github.com/DashSaman/PVN-amirrezagol/tree/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/android/src/main
[R10]: https://api.github.com/repos/DashSaman/PVN-amirrezagol
[R11]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/build.gradle.kts
[R12]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/android/src/main/kotlin/com/pvnetwork/client/PvVpnService.kt
[L-GITHUB]: https://docs.github.com/en/repositories/managing-your-repositorys-settings-and-features/customizing-your-repository/licensing-a-repository
[L-APACHE]: https://www.apache.org/licenses/LICENSE-2.0
[L-MPL]: https://www.mozilla.org/en-US/MPL/2.0/
[L-MPL-FAQ]: https://www.mozilla.org/en-US/MPL/2.0/FAQ/
[L-GPL-FAQ]: https://www.gnu.org/licenses/gpl-faq.en.html
[L-LGPL]: https://www.gnu.org/licenses/old-licenses/lgpl-2.1.dbk
[L-AGPL]: https://www.gnu.org/licenses/agpl-3.0.en.html
[D-KOTLIN]: https://github.com/JetBrains/kotlin/blob/5687445832cd835b4509b9fbc264cdf1a8201093/license/LICENSE.txt
[D-COMPOSE]: https://github.com/JetBrains/compose-multiplatform/blob/38f6fc65eab734483af06b2b1b4cc5698b4cd448/LICENSE.txt
[D-JNA]: https://github.com/java-native-access/jna/blob/1a91122853f6ab6f1fb2a4a284a6cf2ed8af0a4d/LICENSE
[D-JDK]: https://github.com/openjdk/jdk21u/blob/master/ADDITIONAL_LICENSE_INFO
[T-HYBRID]: https://docs.flutter.dev/add-to-app
[T-FLAVORS]: https://docs.flutter.dev/deployment/flavors
[T-APPLE]: https://docs.flutter.dev/deployment/flavors-ios
[T-FLAVORIZR]: https://github.com/AngeloAvv/flutter_flavorizr/blob/89ebdb52c7aa349e13f424a8ba3e82b9aea3f499/LICENSE
[T-FF-OWN]: https://docs.flutterflow.io/misc/application-data-ownership/
[T-FF-EXPORT]: https://docs.flutterflow.io/flutterflow-cli/exporting/
[T-FF-NATIVE]: https://docs.flutterflow.io/concepts/advanced/method-channels/
[T-FFX]: https://docs.flutterflow.io/ffx/what-you-can-build
[T-DHIWISE]: https://qualitydocs.dhiwise.com/docs/flutter/build-or-preview-app
[T-ROCKET]: https://www.dhiwise.com/terms-of-service
[T-WLVPN]: https://wlvpn.com/wlvpn-faqs/
[T-WLVPN-PROTOCOLS]: https://whitelabelvpn.zendesk.com/hc/en-us/articles/17678479236635-What-protocols-are-supported
[T-CODEX]: https://developers.openai.com/codex/guides/agents-md/
[T-ZCODE]: https://zcode.z.ai/en/docs/install
[A-KARING]: https://github.com/KaringX/karing/blob/0c320dc182d095ec0d483f852169421e749c2bac/LICENSE.md
[A-HIDDIFY]: https://github.com/hiddify/hiddify-app/blob/7804cadac01419f31b1ec7802d9e1be599e66de2/LICENSE.md
[A-V2RAYNG]: https://github.com/2dust/v2rayNG/blob/57907cf354d9769200ed4717b4430d605abe0952/LICENSE
[A-V2RAYN]: https://github.com/2dust/v2rayN/blob/1883178b5f1e222b6c6db2782e7a3d989b7cc18f/LICENSE
[A-AMNEZIA]: https://github.com/amnezia-vpn/amnezia-client/blob/ac175c8a8147cfe56f98c8372ec17ea2ba00bb02/LICENSE
[A-KARING-PUBSPEC]: https://github.com/KaringX/karing/blob/0c320dc182d095ec0d483f852169421e749c2bac/pubspec.yaml
[A-HAPP-A]: https://github.com/Happ-proxy/happ-android/tree/c87cc5ea28b5748aab8a4225f998d3080d2c18ae
[A-HAPP-D]: https://github.com/Happ-proxy/happ-desktop/tree/ca87d08932113de73e3c96941fe293991658fbf5
[A-HAPP-I]: https://github.com/Happ-proxy/happ-ios/tree/e5d38081d3b62455670131fa8e0f0b37039a12dc
[A-V2BOX]: https://play.google.com/store/apps/details?id=dev.hexasoftware.v2box
[A-V2BOX-VENDOR]: https://hexasoftware.dev/v2box-terms/
[A-NPV]: https://play.google.com/store/apps/details?id=com.napsternetlabs.napsternetv
[A-NPV-VENDOR]: https://napsternetv.vonmatrix.com/about.html
[E-XRAY]: https://github.com/XTLS/Xray-core/blob/a4a3dff5b20c2faae567b742c172c7b95e75c083/LICENSE
[E-LIBXRAY]: https://github.com/XTLS/libXray/blob/7fe8528063e54343af8ba9a37431afc56000de21/LICENSE
[E-LIBXRAY-MOD]: https://github.com/XTLS/libXray/blob/7fe8528063e54343af8ba9a37431afc56000de21/go.mod
[E-HEV]: https://github.com/heiher/hev-socks5-tunnel/blob/c5584feca8f6df70ee53eb2879e034b849a3d15a/LICENSE
[E-SINGBOX]: https://github.com/SagerNet/sing-box/blob/c005f35300ce76427dc285481ec5695f5b179cd2/LICENSE
[E-MIHOMO]: https://github.com/MetaCubeX/mihomo/blob/3f27ef39f8c96bbfbc8b0b9bb10f5c9ef60149d8/LICENSE
[P-XRAY-OLD]: https://github.com/XTLS/Xray-core/tree/5ca6f4b7d4dc20a881d4330e498892697627ec0c
[P-HEV-OLD]: https://github.com/heiher/hev-socks5-tunnel/tree/9a06bc6e7989da54e3d32ff701ef7a7ce4995d3a
[E-WG]: https://github.com/WireGuard/wireguard-android/blob/0856e2ecf7f3cce0e9c9014db3ad7665669adce5/COPYING
[E-WG-NATIVE]: https://github.com/WireGuard/wireguard-android/blob/0856e2ecf7f3cce0e9c9014db3ad7665669adce5/tunnel/tools/CMakeLists.txt
[E-WG-TOOLS]: https://github.com/WireGuard/wireguard-tools/blob/e2ecaaa739144997ccff89d6ad6ec81698ea6ced/COPYING
[E-OVPN3]: https://github.com/OpenVPN/openvpn3/blob/0b27d9df493db8bc9dc459a2b1cb6b67037742b2/LICENSE.md
[D-HEV-MODULES]: https://github.com/heiher/hev-socks5-tunnel/blob/c5584feca8f6df70ee53eb2879e034b849a3d15a/.gitmodules
[D-HEV-CORE]: https://github.com/heiher/hev-socks5-core/blob/dadbb54bf7509f6dea3c1b30e45198efa400679c/LICENSE
[D-HEV-TASK]: https://github.com/heiher/hev-task-system/blob/d0365c370aadd39129924b563b80751cb63332d7/LICENSE
[D-HEV-LWIP]: https://github.com/heiher/lwip/blob/495671e59d736ef59f6cfa7dc9d35b7359921062/LICENSE
[D-HEV-YAML]: https://github.com/heiher/yaml/blob/94441ec8777eb319936735a58b376963d09c73d4/LICENSE
[D-LIBXRAY-BUILD]: https://github.com/XTLS/libXray/blob/7fe8528063e54343af8ba9a37431afc56000de21/build/app/build.py
[D-WG-GO]: https://github.com/WireGuard/wireguard-android/blob/0856e2ecf7f3cce0e9c9014db3ad7665669adce5/tunnel/tools/libwg-go/go.mod
[D-HIDDIFY-MODULES]: https://github.com/hiddify/hiddify-app/blob/7804cadac01419f31b1ec7802d9e1be599e66de2/.gitmodules
[D-V2RAYNG-MODULES]: https://github.com/2dust/v2rayNG/blob/57907cf354d9769200ed4717b4430d605abe0952/.gitmodules
[D-AMNEZIA-MODULES]: https://github.com/amnezia-vpn/amnezia-client/blob/ac175c8a8147cfe56f98c8372ec17ea2ba00bb02/.gitmodules
[F-VLESS-LICENSE]: https://github.com/XIIIFOX/flutter_vless/blob/f73ed3f517bd5474fb36c52dbec13908fe5c68c7/LICENSE
[F-VLESS-PUB]: https://github.com/XIIIFOX/flutter_vless/blob/f73ed3f517bd5474fb36c52dbec13908fe5c68c7/pubspec.yaml
[F-VLESS-GRADLE]: https://github.com/XIIIFOX/flutter_vless/blob/f73ed3f517bd5474fb36c52dbec13908fe5c68c7/packages/flutter_vless_android/android/build.gradle
[F-VLESS-TUN]: https://github.com/XIIIFOX/flutter_vless/blob/f73ed3f517bd5474fb36c52dbec13908fe5c68c7/packages/flutter_vless_android/android/build_tun2socks.sh
[F-VLESS-PREVIEW]: https://github.com/XIIIFOX/flutter_vless/blob/f73ed3f517bd5474fb36c52dbec13908fe5c68c7/third_party/tun2socks-native/pin-manifest.json
[F-TUN-LICENSE]: https://github.com/xjasonlyu/tun2socks/blob/57550350c847c469aaa26a4748f4e51b6629ee7b/LICENSE
[F-VLESS-IOS]: https://github.com/XIIIFOX/flutter_vless/blob/f73ed3f517bd5474fb36c52dbec13908fe5c68c7/ios/flutter_vless/Sources/flutter_vless/FlutterVlessPlugin.swift
[F-VLESS-MAC]: https://github.com/XIIIFOX/flutter_vless/blob/f73ed3f517bd5474fb36c52dbec13908fe5c68c7/packages/flutter_vless_macos/macos/flutter_vless_macos/Sources/flutter_vless_macos/FlutterVlessPlugin.swift
[F-VLESS-WIN]: https://github.com/XIIIFOX/flutter_vless/blob/f73ed3f517bd5474fb36c52dbec13908fe5c68c7/packages/flutter_vless_windows/windows/flutter_vless_plugin.cpp
[F-VLESS-META]: https://api.github.com/repos/XIIIFOX/flutter_vless
[F-VLESS-TAG-RUNS]: https://api.github.com/repos/XIIIFOX/flutter_vless/actions/runs?head_sha=f73ed3f517bd5474fb36c52dbec13908fe5c68c7&per_page=100
[F-VLESS-CI]: https://github.com/XIIIFOX/flutter_vless/actions/runs/42073917574
[F-VLESS-MAC-CI]: https://github.com/XIIIFOX/flutter_vless/actions/runs/42045513810
[F-VLESS-MAC-CHILD]: https://github.com/XIIIFOX/flutter_vless/actions/runs/42047084945
[F-BOX-LICENSE]: https://github.com/imanheidary/v2box/blob/bc194f6a2fddee401d1c3b8141fec8d76c8d19f3/LICENSE
[F-BOX-PUB]: https://github.com/imanheidary/v2box/blob/bc194f6a2fddee401d1c3b8141fec8d76c8d19f3/pubspec.yaml
[F-BOX-GRADLE]: https://github.com/imanheidary/v2box/blob/bc194f6a2fddee401d1c3b8141fec8d76c8d19f3/android/build.gradle
[F-BOX-WIN]: https://github.com/imanheidary/v2box/blob/bc194f6a2fddee401d1c3b8141fec8d76c8d19f3/windows/v2ray_box_plugin.cpp
[F-BOX-LINUX]: https://github.com/imanheidary/v2box/blob/bc194f6a2fddee401d1c3b8141fec8d76c8d19f3/linux/v2ray_box_plugin.cc
[F-BOX-IOS]: https://github.com/imanheidary/v2box/blob/bc194f6a2fddee401d1c3b8141fec8d76c8d19f3/ios/v2ray_box.podspec
[F-BOX-MAC]: https://github.com/imanheidary/v2box/blob/bc194f6a2fddee401d1c3b8141fec8d76c8d19f3/macos/v2ray_box.podspec
[F-BOX-SINGBOX]: https://github.com/imanheidary/v2box/blob/bc194f6a2fddee401d1c3b8141fec8d76c8d19f3/example/scripts/build_android_libsingbox.sh
[F-BOX-COMMIT]: https://github.com/imanheidary/v2box/commit/bc194f6a2fddee401d1c3b8141fec8d76c8d19f3
[F-V2RAY-LICENSE]: https://github.com/blueboy-tm/flutter_v2ray/blob/73550ff3e89be6681e595636c8ed100361e67d31/LICENSE
[F-V2RAY-PUB]: https://github.com/blueboy-tm/flutter_v2ray/blob/73550ff3e89be6681e595636c8ed100361e67d31/pubspec.yaml
[F-V2RAY-GRADLE]: https://github.com/blueboy-tm/flutter_v2ray/blob/73550ff3e89be6681e595636c8ed100361e67d31/android/build.gradle
[F-V2RAY-IOS]: https://github.com/blueboy-tm/flutter_v2ray/blob/73550ff3e89be6681e595636c8ed100361e67d31/ios/Classes/FlutterV2rayPlugin.swift
[F-V2RAY-COMMIT]: https://github.com/blueboy-tm/flutter_v2ray/commit/73550ff3e89be6681e595636c8ed100361e67d31
[F-CLIENT-LICENSE]: https://github.com/amir-zr/flutter_v2ray_client/blob/c296aad34eeed7d65119a310c79fc64ebf58251b/LICENSE
[F-CLIENT-PUB]: https://github.com/amir-zr/flutter_v2ray_client/blob/c296aad34eeed7d65119a310c79fc64ebf58251b/pubspec.yaml
[F-CLIENT-GRADLE]: https://github.com/amir-zr/flutter_v2ray_client/blob/c296aad34eeed7d65119a310c79fc64ebf58251b/android/build.gradle
[F-CLIENT-IOS]: https://github.com/amir-zr/flutter_v2ray_client/blob/c296aad34eeed7d65119a310c79fc64ebf58251b/ios/Classes/FlutterV2rayPlugin.swift
[F-CLIENT-COMMIT]: https://github.com/amir-zr/flutter_v2ray_client/commit/c296aad34eeed7d65119a310c79fc64ebf58251b
