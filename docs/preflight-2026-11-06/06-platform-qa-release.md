# 06 — Platform security, QA and release engineering

**Review date:** 2026-11-06 (Asia/Tehran; GitHub timestamps remain UTC).  
**Repository:** DashSaman/PVN-amirrezagol.  
**Source baseline:** `78dcd4511dbb830a999cc8c1796f2a38f88587e8` (`main`).  
**Coordination baseline:** `ed21086e3e74772ab4dc31825eb87336f917fd67` on `docs/preflight-2026-11-06`.  
**Delivery branch:** `prep/2026-11-06/06-platform-qa-release`.  
**Exclusive deliverable:** `docs/preflight-2026-11-06/06-platform-qa-release.md`.

## 1. Decision and evidence discipline

**Production Android release: NO-GO. Windows whole-device VPN release: NO-GO. iOS/macOS VPN release: NO-GO.** A reusable Android implementation and desktop proxy client exist, but device acceptance, runtime correctness, credential lifetime, native provenance and production signing are not established. An engineering preview is not a release candidate.

The 93-entry V1/V2 research campaign is documented complete; it was not restarted. This report follows the newer coordination scope and changes only this file. Shared state files, runtime code, other workers' reports and existing plans are not edited. Earlier pointers saying that Android does not exist, or that public-app Task 1 is next, are superseded by the actual tree and later evidence. [R00][R01][R02]

### Evidence vocabulary

| Label | Meaning in this report |
|---|---|
| OBSERVED | Read in the pinned source, current API metadata or retained logs; static conclusions state their limits |
| DOCUMENTED | Historical repository assertion without an independently reproduced test here |
| VERIFIED | Reserved for an actual reproducible execution with retained receipt; no new product test receives this label in this review |
| PROPOSED | Implementation contract, acceptance criterion or future command |
| UNKNOWN | Evidence unavailable or insufficient |
| BLOCKED | Required dependency or evidence is not available |

**Execution boundary:** the shell tool returned “No usable execution environment is available.” No build, unit/instrumentation test, device installation, packet capture, native-binary inspection, signing verification, Store upload or workflow rerun was performed here. Historical CI executions are identified by their original run IDs; reading their logs does not make them fresh PASS results. No signing keys, production profiles or customer data were retrieved.

## 2. Actual APK, Windows package and Apple status

| Surface | What is actually evidenced | Release interpretation |
|---|---|---|
| Android source | `apps/android`; app ID `com.pvnetwork.client`; minSdk 26, targetSdk 36, compileSdk 37; arm64-v8a only; real VpnService → hev JNI → Xray process implementation | Implementation exists; no physical-device certification. API compatibility defect is B01 below |
| Android historical build | M4 run **35897687631**, 2026-09-23, baseline SHA, completed successfully; job runs `assembleDebug` | Historical debug-build receipt, not production signing or usable tunnel evidence |
| Android historical artifact | Upload log names `pvnetwork-android-debug-apk`, ID `10767671232`; uploaded ZIP 25,885,734 bytes; ZIP SHA-256 `133224685f66c425328dee587cefa4f65540944bd3879bf6b6b79890f584ca32` | ZIP hash is not the APK hash. APK bytes/certificate were not inspected |
| Android currently available artifact | Run artifacts API now returns `total_count: 0`; current public release has no APK asset | No current downloadable APK was established. Retention is 14 days, consistent with disappearance; exact expiry/deletion cause is UNKNOWN |
| Windows available asset | Prerelease `v0.1.0-preview` contains only `PVNetwork-Windows-0.3.0.zip`, 80,765,944 bytes; GitHub metadata digest `3683a3c4df7bb1ab7d4873a3b035b87b551f7c7f0f2492ff42e1c52ae5674e80` | Asset existence observed; bytes, signer, installation and exact source provenance unverified |
| Windows historical execution | M-P2 document reports Windows 11 launch and six real Xray binary cases: VLESS RAW, VLESS Vision+TLS, VMess RAW, Trojan TLS, Shadowsocks RAW, VLESS XHTTP | DOCUMENTED local Windows evidence. Does not certify all traffic, UDP, DNS or crash recovery |
| Windows networking | Xray loopback proxy plus per-user WinINET registry settings; DPAPI secret files | Describe as system-proxy mode. This does not establish a whole-device tunnel or kill switch |
| Apple | Foundation declares `iosArm64` and `iosSimulatorArm64`; no `apps/ios` host/extension or macOS Network Extension in the complete tree | Shared library targets only. No installable Apple VPN application, provisioning or Store evidence |

Sources: Android build/service [R03][R04], historical validation [R02], current release and artifact APIs [R05][R06], desktop implementation [R07][R08], foundation targets [R09], desktop secret storage [R27].

### Distribution inconsistency to fix

The release body still names `PVNetwork-Windows-x64.zip` and `PVNetwork-Android-arm64-debug.apk` with old checksums. Those do not describe the currently attached Windows ZIP. The release tag points to `75c5961598b1d70e204a08ad72a6794073a1ad86`, different from the reviewed main SHA; desktop `packageVersion` and Android `versionName` remain `0.1.0`, while the ZIP name says `0.3.0`. Build a manifest connecting **source SHA → version → actual asset → hash → signer → test receipts** before distributing a candidate. A release title mentioning Android is not evidence that its APK is attached. [R03][R05][R10][R28]

## 3. Tests that exist, their evidence and tests that do not

### Historical CI inspected, not rerun

| Slice | Retained run | Actual scope/result |
|---|---|---|
| Foundation | [35868899915](https://github.com/DashSaman/PVN-amirrezagol/actions/runs/35868899915), source prefix `2f7ae227` | JVM and mobile-target jobs successful. Apple compilation tasks were **FROM-CACHE**; no Xcode linkage, extension or device test |
| Desktop shell | [35897687630](https://github.com/DashSaman/PVN-amirrezagol/actions/runs/35897687630), baseline SHA | Linux Xvfb launch smoke log reports success |
| Public apps | [35897687631](https://github.com/DashSaman/PVN-amirrezagol/actions/runs/35897687631), baseline SHA | Android debug build; desktop tests/package run on **Ubuntu**, not Windows |
| WireGuard | [35868899899](https://github.com/DashSaman/PVN-amirrezagol/actions/runs/35868899899), `2f7ae227…` | Linux namespace real-link log: three ping replies, zero loss; no Android integration receipt |
| OpenVPN | [35868899934](https://github.com/DashSaman/PVN-amirrezagol/actions/runs/35868899934), `2f7ae227…` | Linux namespaces and real TLS peer/product-runtime job successful; no mobile support inference |
| Xray contracts | [35877948152](https://github.com/DashSaman/PVN-amirrezagol/actions/runs/35877948152), `6ddccb75…`, job `107238663220` | **30 tests, one failure:** `hostRuntimeValidatesStartsAndCleansProtectedConfig`, assertion at test line 81 |
| Xray real paths | Same run, job `107238663604` | **Seven tests, one failure:** `realVlessVisionRealityDataPath`, latest log line 113 |
| Mihomo/OpenConnect | [35868899981](https://github.com/DashSaman/PVN-amirrezagol/actions/runs/35868899981), `2f7ae227…` | Contracts/exact-binary checks and OpenConnect contracts successful; real Mihomo group **three tests, one failure:** TUIC v5, line 197 |
| Recent scheduled green | [42265814098](https://github.com/DashSaman/PVN-amirrezagol/actions/runs/42265814098), 2026-11-06 | Agent-state validation only; not a new app build |

These are the relevant inspected historical runs, not a claim that every suite ran at the current candidate SHA. Path-filtered CI makes that distinction necessary. [R11]

**Newly identified stale-register gap:** the existing broken-CI register lists REALITY and TUIC but omits the Xray cleanup-contract failure. The test calls `prepared.stop`, observes `DISCONNECTED`, then fails because `runtimeConfig` still exists. Root cause is unproven; reproduce deterministic cleanup/concurrency failure before changing code. Do not erase assertions or add arbitrary sleeps to hide it. [R12][R13]

### Source-level coverage inventory

| Existing tests | What they do not prove |
|---|---|
| Foundation contracts, account/entitlement models, state transitions, diagnostic-key filtering | Actual backend authorization, billing, device VPN lifecycle or value-level secret redaction |
| Xray import/config escaping and fake host-runtime tests | Encrypted Android traffic or native-stack compatibility |
| Real Xray localhost SOCKS/TCP tests | Android TUN, UDP/DNS, Wi-Fi/cellular switching, all transports |
| Desktop `ShellModelTest` | DPAPI/registry rollback, controller transactions, subscription persistence or real selection/connect UI |
| Linux WireGuard/OpenVPN harnesses | Android/Windows/macOS integration |
| Android unit/instrumentation directories | **Absent** from inspected tree; no Android lifecycle test suite |
| Apple app/extension tests | **Absent** with the app/extension itself |

**False-green hazard:** real Xray test methods return normally when fixture executable/TLS environment variables are absent. A successful Gradle exit can therefore contain no real interoperability exercise. Required release jobs must fail on missing prerequisites and record expected executed case IDs/counts, failures and skips. `NO-SOURCE`, `SKIPPED`, task caching or an early return cannot satisfy a required runtime gate. [R14]

## 4. Release blocker and critical-bug register

Severity describes release impact, not a CVSS score or a claim of demonstrated exploitation. Static defects below still need reproduction and regression evidence. “Leak” is not asserted without a packet or secret-exposure receipt.

| ID / priority | Evidence and bounded finding | Required closure / owner |
|---|---|---|
| **B01 Blocker — Android compatibility** | Service rejects only API <29 then calls `excludeRoute`, introduced API33. API29–32 therefore enter an unsupported call; API26–28 can install but are deliberately rejected. [R04] (lines 100–119)[A01] | Align declared support and code. Prefer compatible protected-socket integration; test lowest supported API and API32/33 boundary. If a narrower floor is chosen, document product scope change. **Codex C1** |
| **B02 Critical — connection truth** | Android emits CONNECTED immediately after JNI start; no Xray readiness/config check. Early engine exit can precede `running=true`; direct publication bypasses shared coordination. [R04] (lines 151–193) | Separate platform-established, engine-ready and usable-path state; negative auth/TLS/port/early-exit cases must not stay falsely connected. **C1** |
| **B03 Critical — lifecycle ownership** | Each start spawns an independent thread; stop does not cancel pending start. Engine/TUN fields lack session-generation ownership. Engine exit only reports error; sticky null-intent restart stops for missing profile; network-change/retry handling absent. [R04] (lines 49–65,184–210) | One cancellable coordinator/session; idempotent stop; no late revival; network callbacks and bounded recovery; explicit user-stop semantics. **C1** |
| **B04 Critical property — credential lifetime** | Credential-bearing config written to persistent `filesDir/engine/config.json`; teardown never deletes it. Profile deletion does not delete secrets. Backup exclusions absent. This proves retained plaintext, not external access. [R04] (lines 151–153,196–210)[R15][R16] | Private transient config, cleanup before stop completion, startup stale-file cleanup, backup exclusions and linked-secret deletion; synthetic-canary tests. **C2** |
| **B05 Blocker — native provenance** | `fetchXrayCore` returns if target exists; both native binaries are committed. ZIP checksum therefore does not verify preexisting packaged bytes; no final-binary hash gate. [R03] (lines 62–101)[R17] | Verify archive AND final native files on clean/cached builds; byte-mutation test must block packaging; reproducible hev recipe and dependency inventory. **C3/Z2** |
| **B06 Blocker — unresolved Xray tests** | Current retained cleanup-contract and REALITY receipts are red. [R12][R13] | Reproduce/fix with unchanged behavioral requirements; retain fresh required-fixture receipts at candidate SHA. REALITY remains blocked until green and device-proven. **C2/C3** |
| **B07 Blocker — certification/signing** | No physical Android path, leak/lifecycle or production certificate receipt; no protected signed-release pipeline. [R02][R11] | Complete device matrix and signing/provenance gates below. Missing signing material yields BLOCKED, never debug-key fallback. **C4/Z1/owner** |
| **B08 Blocker — profile/subscription integrity** | Android direct JSON write and corrupt-read→empty-list can lose records. Desktop constructor loads subscription map then resets it; refresh deletes old profiles/secrets before committing replacements. [R15] (lines 99–122)[R18] (lines 25,69,85)[R07] (lines 181–193) | Atomic versioned transactions, corruption recovery, preserve last working set, restart association regression and injected write-failure tests. **C2/Z1** |
| **B09 Critical property — subscription credentials/diagnostics** | Desktop saves raw subscription URL; key-name sanitizer does not inspect values/errors. Credential exposure in an actual log was not observed. [R19][R20] | Protect tokenized URLs; structured safe errors; sentinel secrets absent from logs/support/export/backup. **C2/Z1** |
| **B10 Blocker for Windows release** | WinINET snapshot lives in memory; partial failure clears it; restore clears before writes and omits deletion of formerly absent values. Shutdown hook does not cover hard-kill. [R08] (lines 22–67) | Durable ownership journal, exact rollback and startup recovery; protect user changes made by other software. **C5/Z1** |
| **B11 Major, primary-flow candidate blocker** | Android selected profile/list are ordinary singleton properties; selection/delete mutations do not directly notify Compose. [R16] (lines 100–117,284–295) | Instrument select→Connect and deletion without unrelated recomposition; use observable state. Escalate to Blocker if primary connect is blocked. **C1 + Chat04 integration** |
| **B12 Blocker for advertised scope** | Android UI imports VLESS links only; desktop has more formats. Mobile WireGuard/NaiveProxy and other research entries are not integrated merely because adapters/dossiers exist. TUIC interop remains red. [R16][R07][R12] | Chat03 defines admitted capability tuples; test exact engine/transport/security/device combinations; disable unsupported paths truthfully. No silent removal of required product scope. **C3 + Chat03/07** |
| **B13 Blocker for Play secure-path claim** | VLESS RAW/security=none is accepted and generated with no outer TLS/REALITY. Existing RAW CI is lab compatibility evidence. [R21][G02] | Public Play capability policy must enforce encrypted device-to-endpoint paths; reject or separately justify any plaintext path. Do not confuse VLESS's inner `encryption:none` with absent encryption when TLS/REALITY is present. **C3** |
| **B14 Blocker for Store release** | Publisher verification, privacy/deletion disclosures, commerce reconciliation and actual Store approvals not evidenced. [R01][R02] | Execute current channel gates with Chat05; real backend purchase verification before enabling purchase. External-provider import remains independent of account availability. **Owner + Chat05/C4** |

### Risks requiring tests, not invented findings

- Android config already includes **IPv4 and IPv6** addresses/default routes. Its DNS constants do not prove correct DNS relay or a leak. [R04]
- One resolved endpoint is globally excluded while Xray resolves the hostname independently. Multi-address DNS, endpoint rotation and network changes may select another address; destination exclusion also bypasses that address for other apps. A native socket-protection/binding bridge is the proposed fix. Adding a Kotlin `protect` call without access to the engine's actual sockets is insufficient.
- There is no receipt for DNS TCP fallback, Android Private DNS, UDP, IPv6-only/NAT64/464XLAT, captive portals or the fixed MTU 8500.
- Loopback SOCKS/HTTP listeners use `127.0.0.1`; do not claim LAN exposure. Test access from another local app and isolation when per-app policies are introduced. [R21]
- `specialUse` being introduced in API34 is not by itself a proven crash on earlier versions; Android permits newer FGS type values on earlier OS versions. The existing manifest contains its permission and subtype. Test the actual eligibility/start flow. [R22][A03][A13]
- The engine executes from `nativeLibraryDir`, not a downloaded writable config directory. Do not invent a writable-directory executable restriction violation. [R04]


## 5. Security Audit Plan

This is the plan for a scoped release audit, not a completed penetration test. Use owned lab peers, synthetic accounts and non-sensitive traffic. Keep raw captures/keys/customer-identifying diagnostics out of this public repository; commit sanitized results and hashes, with controlled evidence locations.

### Trust boundaries and acceptance

| Audit ID / boundary | Required exercise | Acceptance evidence |
|---|---|---|
| SEC01 — untrusted import → canonical profile | Malformed/oversized URI, duplicate fields, JSON/YAML nesting, invalid Unicode, embedded credentials, unsupported security/transport, hostile redirects and interrupted subscription fetch | Bounded parsing/download; no shell execution; unsupported/lossy options explicit; accepted profile preserves security fields; no secret in display/error; last working config survives failure |
| SEC02 — app traffic → TUN → engine → peer | Bidirectional TCP and UDP markers from an ordinary app on the test device; correct/incorrect auth; invalid CA/hostname/REALITY key; endpoint outage; occupied listener | Exact authenticated/encrypted path, expected egress and correct failure state; no plaintext fallback or persistent false CONNECTED |
| SEC03 — DNS and bootstrap | Controlled A/AAAA names through UDP53/TCP53, Android Private DNS off/auto/strict and app DoH; unique query IDs; resolver outage | Correlate device traffic, gateway capture and authoritative DNS/egress receipts. No protected DNS outside approved policy; bootstrap endpoint lookup is separately identified and minimized |
| SEC04 — IPv6 and routes | IPv4-only, dual stack, IPv6-only/NAT64/464XLAT, multi-A/AAAA endpoints, endpoint-address change, subnet/LAN overlap, MTU-sensitive transfer | Each family either traverses the approved tunnel or is deliberately blocked; never silently falls through. Check routing tables AND packets; absence of an IPv6 route alone is not proof of a leak [A01] |
| SEC05 — kill switch | Ordinary mode, temporary reconnect mode and OS always-on+lockdown tested separately; kill engine/service; remove network; restore network | With promised lockdown enabled, protected test-app TCP/UDP/DNS never appear directly on underlying egress. UI states actual policy. Explicit stop does not reconnect. Document OS/control-plane exceptions |
| SEC06 — secret at rest → transient config | Import/delete; connect/stop/failure; crash then restart; key invalidation; backup/restore; profile migration; subscription URL persistence | Keystore/DPAPI/Keychain boundary works; transient plaintext absent after cleanup and next-start recovery; intended backup excludes secrets/runtime config; no orphan credentials; no unsupported guarantee of forensic secure erase |
| SEC07 — errors/logs/support | Synthetic credential, UUID/password, tokenized URL, encoded token, auth header, exception and native-crash canaries | Canaries absent from exported logs, UI reasons, CI output and support bundles; redaction before persistence/upload; only deliberate user export |
| SEC08 — network/lifecycle concurrency | Repeated start/stop, stop during DNS resolution, profile switch, permission revoke, other VPN, process/service/engine death, sleep/wake | Single active generation; bounded resource lifetime; cleanup completes; callbacks from an older session cannot revive or modify the newer session |
| SEC09 — supply chain/signing/update | Changed native bytes, wrong dependency digest, missing signing identity, wrong certificate, interrupted update/migration | Fail closed before publication; independently approved signer matches; source/native/SBOM provenance retained; version and migration recovery verified |
| SEC10 — accounts/commerce | External import without login; backend offline; revoked session; fake/duplicate/out-of-order purchase event; restore/refund/expiry | Client never invents paid entitlement; backend authoritative and idempotent; external-provider profiles remain usable without PVNetwork account; defer endpoint specifics to Chat05 |
| SEC11 — Windows privileged/system settings | Prior proxy/PAC including absent values; partial write; hard kill/restart; another app changes settings; future service IPC abuse | Exact owned changes restored transactionally; no overwrite of newer external settings; privileged helper has authenticated bounded commands; proxy/tunnel scope truthful |

**Capture method:** establish a tunnel-off baseline using a non-secret random marker, run a continuous marker stream from a separate test app, then observe connect/handoff/fault/recovery/disconnect. Capture on the controlled router/AP or lab gateway plus the VPN egress; record fixture-side DNS requests. Do not run a second VPN-based capture app on Android: only one VPN service is active per user/profile. Mobile-carrier leak checks need an appropriate lab/managed device or carrier-side observation; a public-IP screenshot alone is inadequate. Do not interpret packets sent by a host-side `curl` as traffic from the device. [A02]

## 6. Real-device and network matrix

**All cases in this section: PROPOSED / NOT RUN. Device inventory and test infrastructure: UNKNOWN/BLOCKED until assigned.** Record actual model, OS build and network properties; the table is not a claim that these devices are available.

### Required coverage

| Coverage | Minimum release evidence |
|---|---|
| Reference Android | Physical arm64 Pixel/reference-class device on current supported OS; actual API and page size recorded |
| OEM lifecycle | Physical Samsung One UI and Xiaomi/HyperOS/MIUI-class device; default battery restrictions plus explicitly configured modes |
| Oldest supported Android | Physical low-resource arm64 device at approved minimum; API29/32/33 compatibility boundary added on device/emulator; if API26–28 remain declared, they need working-path acceptance |
| Native page size | 4 KB and 16 KB execution environments; physical-device tunnel proof retained, with 16 KB device where available and 16 KB emulator as additional coverage |
| Windows | Supported Windows 11 x64 for current candidate; other OS/architectures claimed only after separate package/driver evidence |
| Future Apple | Oldest approved and current iOS/iPadOS hardware, including a real iPhone cellular handoff; macOS distribution architecture tested separately |

Do not invent the OS support policy. The source's `minSdk=26` is not evidence of working API26 connectivity. Apple foundation compilation is not evidence for any iPhone.

### Executable acceptance cases

| Case | Procedure | GO criterion / retained receipt |
|---|---|---|
| DEV01 Permission and install | Clean install; first connect; accept/refuse consent; notification denied; another VPN takes over; revoke VPN consent | Correct UI/notification; no unsolicited connect or stuck service; actual system ownership follows state |
| DEV02 Exact first tunnel | Import sanitized fixture for each admitted protocol+transport+security tuple; transfer bidirectional TCP/UDP marker and DNS query | Test app uses VPN egress; authentication/encryption negative cases fail; build/core/version tuple attached |
| DEV03 Network families | Repeat DEV02 on IPv4-only, dual-stack and IPv6-only/NAT64/464XLAT lab networks | Expected family behavior and no unapproved protected-traffic egress; unsupported case blocked visibly |
| DEV04 DNS | UDP/TCP fallback, A/AAAA, Private DNS variants, app DoH, resolver failure and endpoint bootstrap | Correlated capture/resolver receipts; no silent policy downgrade |
| DEV05 Handoff | Wi-Fi→cellular→Wi-Fi during TCP/UDP/DNS streams; DHCP/IP change; airplane/offline; reconnect after restoration | No unauthorized bypass, no retry storm; recovery measured against approved baseline budget; explicit disconnect cancels pending work |
| DEV06 Sleep and OEM | Home/background, activity recreation, screen lock/sleep/wake, Doze, battery saver, OEM task removal | Truthful state on return; tunnel lifecycle matches intended policy; no orphan process/TUN; throughput and battery observations retained |
| DEV07 Fault isolation | Separately kill engine, fail native stack, terminate VPN process, user force-stop, reboot, revoke permission | Distinct expected outcomes; cleanup/recovery verified; enabled lockdown protects test traffic; force-stop/manual stop never mislabeled as automatic OS restart |
| DEV08 Races | Stop while DNS/start pending; duplicate start/stop; rapid profile switch; subscription refresh while active | One session owns handles; no late CONNECTED/revival; active profile transitions atomically |
| DEV09 Storage and privacy | Interrupted writes; corrupt one record; missing key; import/delete; synthetic backup/restore; diagnostic export | No silent total-profile loss; last working set preserved; no known secret canary in persistent plaintext/export |
| DEV10 Product separation | Import external configuration with no account and PVNetwork backend unreachable; separately test account revoke/expiry | External-provider path remains independent; no fabricated quota, payment success or support capability |
| DEV11 Windows recovery | Preconfigure synthetic proxy/PAC; connect/stop; partial registry failure; hard-kill/restart; external proxy edit | Exact restore/startup reconciliation; no whole-device/UDP/DNS claim from proxy-only success |
| DEV12 Upgrade | Install prior approved signed fixture, import profiles, install candidate, restart, interrupted migration and downgrade attempt | Signer continuity, preserved profiles, explicit schema compatibility; no destructive automatic reinstall workaround |

**Proposed endurance workload:** 100 connect/disconnect cycles, 20 handoff cycles and an eight-hour sustained/idle soak for release-critical paths across the agreed matrix. These are test repetitions, not measured performance claims. Go requires zero observed protected-traffic leaks, false-connected states, crashes/ANRs or destructive data loss in those runs. Establish real cold-start/connect/reconnect/CPU/memory/battery baselines first, then ratify numeric performance budgets; do not invent a p95 promise now. [R01]

**Architecture caveat:** the current Android service shares the app process. “Kill UI process while service survives” is not a valid assumption for this build. Test activity destruction separately from process death; if a dedicated VPN process is introduced later, add that distinct case.

### Required test receipt

Every case must record: case ID; PASS/FAIL/SKIP/BLOCKED; exact source SHA; APK/AAB and installed split hashes; signer certificate fingerprint; native core hashes/version; device model/API/OS/ABI/page size; profile tuple without secrets; network family and intended route/DNS/kill-switch mode; fixture ID; start/end UTC; commands and exit codes; actual assertions; sanitized capture/log hashes; failure ticket and owner. Skipped mandatory cases block promotion. A VPN icon, `CONNECTED` label or open local SOCKS port is not sufficient.

## 7. Platform constraints and growth without rewriting shared logic

### Android

Use one `VpnService` per Android user/profile, consent through `prepare()`, the protected service declaration and an appropriate foreground lifecycle. Protect/bind **actual engine outbound sockets** to prevent routing loops; define how JNI or an embedded/process bridge transfers that responsibility. Permission can change between connections. Always-on service restart, reconnect and OS lockdown are distinct features and require separate receipts. [A02]

`excludeRoute(IpPrefix)` starts at API33. Retaining API26 support requires another implementation, not a lower version guard. Test FGS permission/type/notification paths; `specialUse` needs its meaningful subtype and Play justification. Do not switch blindly to `systemExempted`: eligibility depends on the app's configured VPN role. [A01][A03]

### Shared contract to preserve and complete

Reuse the existing canonical profile, capability/adapter, account/entitlement, routing/DNS, localization and SecretStore contracts in `core/foundation`. Implement the missing shared coordinator using the **actual package root** `com/pvnetwork/core`; older plan examples omit `com/`. [R09][R23]

| Shared responsibility | Platform responsibility |
|---|---|
| Profile normalization/import results and capability admission | OS consent, service/extension lifecycle and permissions |
| Canonical connection state, session generation, user intent, retry policy | TUN/packet flow, socket protection, underlying network callbacks |
| Routing/DNS intent and unsupported/lossy results | OS routes, DNS installation, firewall/lockdown mechanics |
| Account/entitlement and transactional subscription logic | Keystore/DPAPI/Keychain, Store billing bindings |
| Sanitized diagnostics and evidence schema | Native crash symbols, package signing, installer/update integration |

The first contract should expose prepare/start/stop/reconcile, observable platform state and capability constraints. Stop completes after resource cleanup; engine-ready is distinct from tunnel-established. Use one coordinator owner per process boundary and versioned, bounded IPC. Shared logic must not hold Android `Context`, Swift manager objects, Win32 handles or shell commands.

### Platform continuation

| Target | Reuse | Required new boundary / acceptance |
|---|---|---|
| Windows proxy maintenance | Compose UI, canonical model, JVM runtime and DPAPI | Transactional WinINET recovery; exact package identity/signature; Windows runner and install/upgrade/hard-kill tests |
| Windows full VPN | Same product/use-case/state contracts | Reviewed TUN driver/service integration, route/DNS/WFP policy as selected, authenticated IPC, least privileges; prove ordinary-app TCP/UDP/DNS and crash cleanup. Wintun is a candidate to evaluate, not a dependency approved here |
| iOS/iPadOS | KMP models, policy, subscriptions/accounts and compatible import/config logic | New host + Packet Tunnel extension, `NETunnelProviderManager`/`NETunnelProviderProtocol`, packet flow/network settings, Keychain and controlled App Group sharing; embedded native engine bridge |
| macOS App Store | Shared contracts plus compatible Apple packet-tunnel engine components | Appropriate app/system extension, Keychain, entitlement/signing and real sleep/wake/route tests |
| macOS direct distribution | Same business logic | Developer ID system-extension packaging, correct entitlement values, notarization and installation/uninstallation/recovery evidence |

Apple's custom packet-tunnel manager belongs to the NEVPNManager family. iOS cannot reuse a JVM/subprocess engine launcher: Apple DTS states iOS apps cannot spawn child processes. Use an extension-safe native library with explicit start/stop, packet I/O and bounded memory ownership. A KMP target or simulator compile does not demonstrate extension execution. Do not assume a fixed universal extension memory limit; measure the selected OS/device budget. [A04][A05][A06]

TN3134 distinguishes iOS app extensions and managed per-app mode from macOS distribution: macOS packet-tunnel app extensions are App Store-only; system-extension packaging supports Developer ID distribution. Use the provisioning profile's actual allowed entitlement values, including the Developer ID `-systemextension` form where required. App Group sharing does not itself encrypt secrets. Ordinary consumer on-demand VPN must not be marketed as supervised managed Always On VPN. [A06][A07][A14]


## 8. Build pipeline, dependencies, SBOM and release identities

### Current pipeline assessment

**Useful existing controls:** inspected app/engine Actions use full action commit SHAs, `contents: read`, bounded timeouts, Gradle 9.5.0 and JDK major 21. Kotlin 2.4.10, Compose 1.11.1 and AGP 9.3.3 are explicitly pinned. Real-engine fixture workflows check archive digests. Keep those controls. [R03][R11][R24]

**Missing/insufficient controls:** no Gradle wrapper, dependency lockfiles or verification metadata; no generated product SBOM/notices; no Android test sources; no lint/device/native-page-size gate in M4; no Windows/macOS product runner; no production signing/provenance workflow. The complete tree was not truncated. Research SBOM/licensing dossiers are not the inventory/notices of a shipped artifact. M4 does not retain JUnit/lint/device reports and its artifact upload lacks `if-no-files-found: error`. System-package fixtures are not locked to exact resolved versions. [R11][R17]

Branch listing reports `main.protected=false`; repository rulesets listing returned an empty array. Organization/environment policies and secret availability were not established. Recommend required app release checks and protected signing environment, but do not claim that secret values or all organization controls were inspected. [R25][R29]

### Native production selection

Current Android source packages Xray v26.7.28 and hev 2.17.1. The Xray gate document permits v26.7.28 only as an ephemeral CI fixture; the current bundling therefore lacks reconciled production approval. [R02][R03][R26]

A bounded live upstream check changes the old availability premise:

- Official v26.7.28 metadata still says prerelease; its Android archive digest matches the declared download pin.
- Official `releases/latest` now resolves to **v26.10.31**, non-prerelease, published 2026-10-31; tag commit `a4a3dff5b20c2faae567b742c172c7b95e75c083`.
- Official asset metadata gives Android arm64 archive SHA-256 `ee7878bb47ce28ff5b068e2d173ba1a9fab20ca1aec94191644919ec08ba5f1d`; Windows x64 archive `2e04a9995a6f2ad1b28c0bb25c708ecb2695e43a1c9c7889c4b6ac2b48cf7a6a`.
- This is a **candidate for review**, not an approved dependency or a local hash verification. The release notes describe an MPTCP security update; review applicability to actual OS/kernel/listener configuration without asserting an untested exploit. [U01][U02]

The older GHSA affected range remains historical documentation: the advisory could not be retrieved live through the available paths. Do not label v26.7.28 affected by that old advisory merely because the old stable candidate was rejected. Do not keep “no stable candidate exists” as today's blocker. The real blocker is missing current approval, complete dependency review, reproducible provenance and regression/device evidence.

### Proposed candidate pipeline

| Stage | Required behavior |
|---|---|
| 1. Source freeze | Immutable candidate SHA and tag mapping; clean checkout; explicit platform/capability allowlist; no candidate drift between test and sign |
| 2. Resolve/verify | Pin wrapper distribution/checksum, JDK/SDK/build tools/NDK and native source/submodules; commit reviewed dependency locks/verification metadata; validate already-present native binaries as well as downloads |
| 3. Contracts | Shared/adapter/controller/storage tests; reproduce existing red cases; required real-fixture preflight; expected test counts and explicit skips; collect XML on failure |
| 4. Android build | Lint, real unit/instrumentation tests, APK/AAB, merged-manifest review, every ELF ABI/alignment check and 16 KB execution |
| 5. Platform QA | Fresh candidate device matrix; Windows build/package/registry/DPAPI tests on Windows; Apple jobs only after actual host/extension exists |
| 6. Supply chain | Resolved SPDX/CycloneDX SBOM, licenses/notices/source-availability obligations, current advisory review and explicit dispositions; no blanket “scanner clean = secure” claim |
| 7. Protected signing | Approved identity in protected environment; no signing secrets in untrusted PR jobs; fail if identity unavailable; verify resulting certificate against approved fingerprint |
| 8. Promote artifact | Retain exact tested artifact or explicitly prove signed rebuild matches tested inputs; hash APK, AAB, native components and enclosing ZIP separately |
| 9. Release evidence | Manifest, symbols/mappings as generated, checksums, signer, SBOM and sanitized test receipts; no raw credentials/captures; coherent listing and asset names |
| 10. Channel promotion | Internal QA → relevant internal/closed Store channel → approved RC → staged rollout; independent Store acceptance and post-release observations |

SBOM must include resolved Kotlin/Java transitive dependencies, JNI/C sources and submodules, Xray/Go modules and build metadata, native libraries and packaged desktop JRE/components. A `go.mod`, a `gradle dependencies` listing or a research dossier alone is insufficient. Inventory the **actual final artifact**, reconcile it with source inputs and retain notices/source material required by Chat02's licensing conclusion.

### Android signing and secret management

Use the owner's approved signing identity; do not generate a substitute release identity during this preflight. Distinguish the **Play upload key** from the **app signing key** used for user-delivered APKs. Verify the final installed Play APK/splits against the Play app-signing certificate; verify direct-distribution APKs against their approved direct-channel identity. An upload-signed AAB, debug APK, checksum or successful build is not proof of the user's production signing identity. [G04]

Inject signing inputs only into the protected signing job, using approved secret facilities or an identity-backed signing service. Use temporary restricted files; avoid command-line password exposure and shell tracing; remove temporary material on success/failure. Retain public certificate fingerprints and provenance, never private keys/keystores/passwords. PR validation needs no production keys. Keep a controlled backup/recovery and rotation procedure with authorized custodians.

The current `release` build type only sets `isMinifyEnabled=false`. Lack of obfuscation is not itself a blocker. Missing verified release identity, safe secret custody and reproducible signed output are blockers.

## 9. Current Google Play and Apple App Store gates

Official requirements were rechecked on the review date. Console/account-specific approvals remain UNKNOWN. This section complements Chat05 commerce work and Chat02 licensing; it does not invent backend APIs or bypass channel rules.

### Google Play / Android distribution

| Gate | Current official requirement and PVNetwork action |
|---|---|
| G-API | New phone/tablet apps and updates require target API **36+** since 2026-08-31; stated extension ended 2026-11-01. Source target36 matches this numeric floor; compilation/behavior still need testing. TV/XR have separate API34 rules and separate future QA. [G01] |
| G-VPN | Complete VpnService declaration; describe VPN use in listing; encrypt device-to-endpoint traffic. Provide relevant in-app prominent disclosure/affirmative consent and demonstration requested by Console. No traffic monetization manipulation. [G02] |
| G-ORG | VpnService-approved apps require organization account selection and the applicable identity/D-U-N-S verification. Existing publisher/account status is not evidenced here. [G03] |
| G-PACKAGE | New Play applications use AAB and Play App Signing. Produce/inspect bundle-derived APKs; app ID and signer ownership must be ratified before first upload. [G04][G05] |
| G-NATIVE | Every shipped native component must work with the declared ABI and 16 KB environment; target35+ compatibility applies. See date qualification below. [G06] |
| G-FGS | Match manifest service type, permission and explanation to behavior and Play foreground-service declaration; test user initiation, restart and notification restrictions. [A03] |
| G-PRIVACY | Complete Data Safety from actual app/backend/SDK inventory; accessible accurate privacy policy; test diagnostics and traffic behavior against disclosures. [G07] |
| G-DELETE | If accounts can be created, provide in-app deletion and external web deletion resource with associated-data/retention behavior. No account-deletion fiction while backend remains undefined. [G08] |
| G-COMMERCE | Digital VPN purchase flow must follow Play payments policy; alternative billing/linking depends on regional programs and enrollment. If billing is added now, choose a supported exact **v8+** Billing Library version; v7's final extension ended 2026-11-01. [G09][G10] |
| G-REGION | Check current Android developer/package verification for intended channels/regions. September 30 rollout covers participating stores in Brazil, Indonesia, Singapore and Thailand; do not generalize this to a worldwide ban on sideloading. [G11] |

**16 KB date qualification:** current Android guidance states target35+ compatibility and dates **app-update** blocking to 2027-02-01; the earlier official announcement said new apps/updates from 2025-11-01, with a later extension announcement. Do not assume the new update wording exempts a new PVNetwork app. Require actual 16 KB compatibility and capture the submitted AAB's current Play Console checks. A successful `zipalign` alone does not prove ELF compatibility or native runtime behavior. [G06][G12][G13]

### Apple App Store / Apple platforms

| Gate | Current official requirement and PVNetwork action |
|---|---|
| A-VPN | Guideline 5.4 requires organization enrollment and NEVPNManager-family use, a pre-use/purchase data declaration, the specified privacy/data-use restrictions and regional VPN license details where required. Prepare exact reviewer instructions. [A08] |
| A-SDK | Since 2026-04-28: Xcode26+ and iOS/iPadOS26+ SDK; since 2026-09-09 uploads must target iOS13+. SDK and deployment target differ. Proposed product iOS16 floor is separate and not yet implemented. [A09] |
| A-PROVISION | Host and Packet Tunnel extension need correct bundle IDs, signing/provisioning and Network Extension entitlement; shared groups/keychain access must match the actual profiles. No profile or entitlement availability was verified. [A04][A06] |
| A-DATA | Accurate App Privacy answers, applicable privacy manifests/required-reason API declarations and listed SDK binary signature requirements. Audit actual dependencies; avoid assuming every SDK has identical obligations. [A10][A11] |
| A-PURCHASE | IAP and regional external-link exceptions must match the chosen storefront flow. Account creation needs in-app deletion. Consumer VPN must also function on IPv6-only networks. [A08] |
| A-DEVICE | Real host+extension installation, consent, lock/unlock, cellular handoff, extension restart, NAT64 and resource tests required; simulator compile is insufficient. [R01] |
| A-MAC | Choose Mac App Store versus direct Developer ID packaging; follow TN3134 and notarization/signing requirements for the direct route. [A06][A12] |

**Store NO-GO** persists until actual publisher ownership, identities/entitlements, privacy/support URLs, billing/deletion integration, territories, review package and exact candidate checks are recorded. Internal testing and TestFlight are not App Review approval. Store approval is not evidence that all protocol combinations work.


## 10. Test and release commands

**All commands below are instructions for the implementer; NOT RUN in this review.** Run against the chosen immutable candidate. Current repository uses installed Gradle 9.5.0; `./gradlew` does not exist yet. Once the reviewed wrapper is added, replace the executable consistently.

### 10.1 Existing shared/build commands

```bash
git rev-parse HEAD
git status --short
java -version
gradle --version

gradle --no-daemon :core:foundation:jvmTest   :core:foundation:compileCommonMainKotlinMetadata   :core:foundation:compileAndroidMain   :core:foundation:bundleAndroidMainAar   :core:foundation:testAndroidHostTest --stacktrace

gradle --no-daemon :engines:xray-adapter:jvmTest   :engines:wireguard-adapter:jvmTest   :engines:openvpn-adapter:jvmTest   :engines:mihomo-adapter:jvmTest   :engines:openconnect-adapter:jvmTest --stacktrace

gradle --no-daemon :apps:desktop:test --stacktrace
gradle --no-daemon -Dorg.gradle.jvmargs=-Xmx4g   :apps:android:assembleDebug --stacktrace
```

Expected debug output from existing CI: `apps/android/build/outputs/apk/debug/android-debug.apk`. Confirm actual file existence and hash; a successful task with no retained file is not an artifact receipt.

### 10.2 Required Xray runtime reproduction

Prepare the pinned executable and ephemeral test CA/server certificate exactly as the selected fixture workflow specifies; do not use production credentials. After C3 approves a new pin, update fixtures and expected evidence together.

```bash
set -euo pipefail
test -n "$PVNETWORK_XRAY_TEST_EXECUTABLE"
test -x "$PVNETWORK_XRAY_TEST_EXECUTABLE"
test -n "$PVNETWORK_XRAY_TEST_TLS_CERT"
test -f "$PVNETWORK_XRAY_TEST_TLS_CERT"
test -n "$PVNETWORK_XRAY_TEST_TLS_KEY"
test -f "$PVNETWORK_XRAY_TEST_TLS_KEY"

gradle --no-daemon :engines:xray-adapter:jvmTest   --tests '*JvmHostXrayRuntimeTest.hostRuntimeValidatesStartsAndCleansProtectedConfig'   --rerun-tasks --no-build-cache --stacktrace

gradle --no-daemon :engines:xray-adapter:jvmTest   --tests '*JvmHostXrayRealBinaryInteropTest'   --rerun-tasks --no-build-cache --stacktrace
```

Run with fail-fast shell semantics in the actual CI script so a missing prerequisite cannot fall through. The latter pattern includes the REALITY class suffix. Check expected case receipts and XML, not only exit status. For Mihomo/TUIC use the exact pinned fixture/TLS environment in `.github/workflows/m3-mihomo-adapter-ci.yml`; a bare `jvmTest` is not evidence of real peers. [R11][R14]

Existing isolated Linux link harnesses, **lab/CI host only**:

```bash
sudo --preserve-env=PATH bash ./scripts/test-wireguard-real-link.sh
sudo bash scripts/test-openvpn-real-link.sh
```

They manipulate lab network namespaces and require their workflow prerequisites. They do not validate a phone.

### 10.3 Proposed Android test/release gates

Create meaningful Android tests and runner configuration first. These task names are proposed invocations, not evidence of present coverage:

```bash
gradle --no-daemon :apps:android:tasks --all
gradle --no-daemon :apps:android:lintDebug   :apps:android:testDebugUnitTest --stacktrace
gradle --no-daemon :apps:android:connectedDebugAndroidTest --stacktrace

# Protected release job only, after approved signing configuration exists.
gradle --no-daemon :apps:android:assembleRelease   :apps:android:bundleRelease --stacktrace
```

Assert nonzero expected test counts, explicit fail/skip totals and retained reports under module `build/test-results`, `build/reports` and Android result directories as actually generated. A task reporting `NO-SOURCE` does not close the gate. Release path/name must be discovered from produced outputs; do not assume the debug filename describes an AAB.

On a controlled test device, with `QA_SERIAL`, `QA_APK` and `QA_EVIDENCE_DIR` set to lab values:

```bash
adb devices -l
adb -s "$QA_SERIAL" shell getprop ro.build.version.sdk
adb -s "$QA_SERIAL" shell getprop ro.product.cpu.abi
adb -s "$QA_SERIAL" shell getconf PAGE_SIZE
adb -s "$QA_SERIAL" install -r "$QA_APK"
adb -s "$QA_SERIAL" shell am start -W   -n com.pvnetwork.client/.MainActivity
adb -s "$QA_SERIAL" shell dumpsys connectivity   > "$QA_EVIDENCE_DIR/connectivity.txt"
adb -s "$QA_SERIAL" shell dumpsys meminfo com.pvnetwork.client   > "$QA_EVIDENCE_DIR/memory.txt"

# Dedicated force-stop scenario; not a simulation of low-memory process death.
adb -s "$QA_SERIAL" shell am force-stop com.pvnetwork.client
```

Perform consent/profile/connect/handoff through UI or implemented instrumentation. These shell commands alone do not run DEV01–DEV12. Preserve permission errors as evidence gaps. Do not erase real customer data to make a signing upgrade pass; use disposable synthetic lab installs.

### 10.4 Final artifact identity and native compatibility

Use the pinned SDK tools on the **final signed artifact**, with `QA_APK` and `QA_AAB` set to actual outputs:

```bash
sha256sum "$QA_APK" "$QA_AAB"
apksigner verify --verbose --print-certs "$QA_APK"
zipalign -c -P 16 -v 4 "$QA_APK"
unzip -l "$QA_APK"
```

Compare the signer certificate with the independently approved channel fingerprint; successful verification by an arbitrary/debug key fails the release-identity gate. For AAB, validate using the pinned bundletool and inspect/test the actual Play-generated splits; `apksigner` is for APKs, not AABs.

Extract native files into a temporary evidence directory and inspect **every** packaged ELF using pinned NDK `llvm-readelf -lW` and the official 16 KB checking procedure. Compare final native hashes with the reviewed native manifest; retain ABI/load-alignment findings and `getconf PAGE_SIZE=16384` runtime receipt. Compressed/legacy packaging does not waive ELF compatibility. [G06]

### 10.5 Windows and future Apple

On a real Windows build runner:

```powershell
gradle --no-daemon :apps:desktop:test :apps:desktop:packageDistributionForCurrentOS --stacktrace
Get-FileHash -Algorithm SHA256 -LiteralPath $env:QA_WINDOWS_PACKAGE
Get-AuthenticodeSignature -LiteralPath $env:QA_WINDOWS_EXE
```

Record signature status and approved publisher/certificate. Validate installer and every required executable, with timestamp/chain verification as appropriate. A ZIP itself is not an Authenticode-signed executable. Run DEV11, install/upgrade/uninstall and future full-tunnel tests separately.

For Apple, **no executable app/extension build command can be asserted today** because no project/scheme exists. After creation, use `xcodebuild -list` on the actual project, then its committed shared scheme for build/test/archive/export. Retain provisioning/entitlement and signature inspection plus real-device receipts. Existing foundation `compileKotlinIosArm64` and `compileKotlinIosSimulatorArm64` commands do not replace this.

## 11. GO / NO-GO release checklist

Each checkbox starts **OPEN / NOT RUN**, except documentary observations expressly listed earlier. Completion requires linked evidence at the exact candidate, not a reviewer ticking boxes from memory.

| Gate | Required criterion | Current state |
|---|---|---|
| RC01 Source and scope | Immutable SHA, version/tag mapping; Chat03 capability tuples and Chat07 reconciled scope | OPEN |
| RC02 Critical correctness | Zero open Blocker/Critical defects; every Major has explicit scoped disposition | NO-GO: B01–B14 unresolved as applicable |
| RC03 Required tests | Shared/contracts/real fixtures green on candidate with actual counts; no silent early-return/skip | NO-GO: red Xray/TUIC receipts and missing Android tests |
| RC04 Physical tunnel | DEV01–DEV10 pass for each advertised Android path/device category | BLOCKED: no device receipts |
| RC05 Privacy/network | SEC01–SEC08 pass; no observed unapproved marker egress; correct IPv6/DNS/kill-switch claims | OPEN |
| RC06 Data integrity | Atomic storage/refresh, safe cleanup, migration and credential deletion | NO-GO: source defects |
| RC07 Supply chain | Approved exact cores, reproducible provenance, final hashes, complete SBOM/notices and advisory disposition | NO-GO: native verification/approval gaps |
| RC08 Signing | Final APK signer approved; AAB/upload/delivery identities reconciled; protected key custody | UNKNOWN/BLOCKED |
| RC09 Artifact availability | Actual downloads match manifest, hashes/version/source, retained symbols and evidence | NO-GO: Android unavailable; metadata inconsistent |
| RC10 Commerce | Real purchase verification/restore/refund/expiry/deletion; account-free external import | OPEN; depends on Chat05 and selected launch scope |
| RC11 Store readiness | Verified publisher, VPN/FGS declarations, privacy/disclosure/listing/territory/toolchain gates | BLOCKED/UNKNOWN |
| RC12 Channel acceptance | Actual internal/closed testing and exact submitted-build Store acceptance | UNKNOWN: no submission/approval receipt |
| RC13 Rollout/recovery | Tested migration, emergency fix path, staged rollout and support ownership | OPEN |
| RC-Windows | Windows signatures/install/registry recovery; full tunnel only if separately implemented/tested | NO-GO for full VPN; proxy preview uncertified |
| RC-Apple | Host/extension build, entitlements/signing and real-device acceptance | NO-GO: application not implemented |

### Promotion decisions

- **GO for continued engineering:** yes; source and historical CI provide reusable starting points.
- **GO for controlled lab installation:** only after building an identified artifact, fixing primary install/connect blockers and assigning disposable lab devices; never imply privacy certification.
- **GO for public signed Android RC:** all RC01–RC11 applicable gates satisfied, with exact physical-device evidence and valid release identity. Current decision remains **NO-GO**.
- **GO for Store publication:** additionally actual channel acceptance, resolved submission requirements and rollout plan. Neither a six-day target nor an internal build guarantees approval.
- **GO for broader protocol/platform claims:** only the exact independently evidenced tuples; do not turn 93 completed research entries into a support count.

Rollback means stopping rollout and delivering an approved corrective build/config rollback as the channel permits. Do not promise arbitrary binary downgrades over installed Android versions. Preserve schema compatibility and last approved config/core inputs; never silently fetch “latest” during emergency recovery.

## 12. Precise Codex and Z.ai implementation handoff

Paths below are **proposed future edits**, not edits made by this report. Runtime/architecture ownership belongs to Codex; Z.ai owns bounded tests/evidence automation against the agreed contracts. Avoid concurrent edits of the same file.

| Ticket / owner | Exact target files or new proposed paths | Dependencies / acceptance |
|---|---|---|
| **C1 Codex — one Android lifecycle** | New `core/foundation/src/commonMain/kotlin/com/pvnetwork/core/platform/PlatformVpnService.kt`; new `.../connection/ConnectionCoordinator.kt`; existing `.../connection/ConnectionState.kt`; Android `PvVpnService.kt`, `VpnHub.kt`, `MainActivity.kt` | Freeze shared contracts with Chat03/04 first. Fix API boundary, cancel/session ownership, readiness, network recovery and observable UI. SEC02/04/05/08 and DEV01–08; no scope reduction hidden in implementation |
| **C2 Codex — secrets and transactions** | Android `Storage.kt`, `AndroidManifest.xml`, proposed backup/data-extraction XML; `JvmHostXrayRuntime.kt`; desktop `ProfileRepository.kt`, `SubscriptionSupport.kt`, `DesktopVpnController.kt`; shared `diagnostics/Diagnostics.kt` | Fix B04/B06 cleanup/B08/B09, transactional profile/subscription promotion, URL protection and structured redaction. Inject storage/cleanup races and failures; prove stop completion and last-known-good survival |
| **C3 Codex — exact engine admission/native build** | `apps/android/build.gradle.kts`; `engines/xray-adapter` runtime/config boundary; proposed `engines/native-lock.json` and reproducible hev build recipe; later approved engine modules from Chat03 | Chat02 license + Chat03 capability decisions. Reconcile production pin, final native hash checks, socket bridge, encryption policy and required REALITY/other exact-path evidence. No new binary approved merely by version number |
| **C4 Codex — release signing/integration** | Proposed `.github/workflows/release-candidate.yml`; Android signing configuration; candidate manifest schema and publication gate | After tested input contract C3/Z2. Protected environment and owner identity available; unsigned/wrong signer cannot publish; delivery artifacts map to tested source |
| **C5 Codex — Windows recovery** | `apps/desktop/.../SystemProxyController.kt`, lifecycle integration in `DesktopVpnController.kt`; future platform service package only after approved scope | Durable ownership journal; partial-failure and hard-kill recovery; Windows runner receipts. Keep proxy and full-tunnel capability separate |
| **Z1 Z.ai — adversarial tests and device harness** | New Android `src/test` and `src/androidTest`; shared coordinator/secret transaction tests; desktop repository/controller/proxy tests; proposed `tools/device-qa/` | Work after contract freeze; deterministic fault fixtures and test-only controls, no production secrets or public debug bypass. DEV/SEC IDs map to nonzero executions; do not independently redesign runtime |
| **Z2 Z.ai — reproducible CI/SBOM evidence** | Wrapper files; Gradle lock/verification metadata; existing `m0`, `m2`, `m3`, `m4` workflows; proposed SBOM/native-verification/evidence tooling | Coordinate Android build edits with C3; pin toolchains/actions, verify missing-fixture failures, retain XML/lint/native/symbol evidence, include transitive native/JRE components. Codex reviews build/signing changes |
| **Z3 Z.ai — release manifest and checklist automation** | Proposed `tools/release/`; future release evidence/checklist files assigned by Chat07 | Consume C4 artifacts; distinguish APK/AAB/ZIP hashes and signer roles; fail incomplete manifests; sanitized receipt validation and coherent release metadata |
| **External owners** | Developer-account consoles, approved signing custody, device lab, service/backend fixtures, privacy/support assets | Owner/admin/legal/Chat05 supply genuine facts; never fabricate keys, IDs, approvals, endpoints or receipts |

Future Apple engineering starts after the common lifecycle contract is stable: add `apps/ios/` host/extension and platform/native bridge, then Mac distribution integration. It is not on the Android six-day critical path and must not be advertised as completed.

### Dependency order and sprint boundary

1. **Day 1 target:** reconcile Chat01–05 outputs; freeze capability/minimum-OS/coordinator contracts; reserve physical devices and signing/publisher owners.
2. **Days 1–3 target:** C1/C2 runtime/storage fixes; C3 provenance in parallel; Z1 prepares adversarial fixtures against the frozen contract.
3. **Days 3–4 target:** fresh required CI and real Android encrypted-path/device matrix; Z2 evidence collection. Stop promotion if red receipts remain.
4. **Days 4–5 target:** C4 signed candidate from tested inputs; upgrade/page-size/OEM/endurance runs; privacy/commerce checks.
5. **Day 6 target:** evidence review and bounded release decision. If any mandatory gate is missing, publish a **NO-GO** engineering handoff, not a production claim.

These are scheduling targets, not a guaranteed delivery date. Apple/Windows full VPN, unimplemented engines and external Store review cannot be silently folded into a six-day completion promise.

### Five highest-leverage tasks

1. **C1:** repair API compatibility and serialize truthful Android connect/stop/recovery.
2. **C2:** eliminate retained credentials and destructive profile/subscription updates; close the failing Xray cleanup contract.
3. **C3:** approve an exact production engine/native chain and verify final packaged bytes, including 16 KB behavior.
4. **Z1/Z2:** execute and retain actual device/CI evidence for the narrow admitted encrypted paths.
5. **C4/Z3:** produce a correctly identified signed candidate, complete channel/privacy/commerce gates and publish matching artifact metadata.

### Decisions still needed, without blocking independent engineering

Approved Android minimum and first supported capability tuples; whole-device versus proxy Windows launch scope; publisher/channel/territories; valid signing identities and custody; actual lab devices; backend/commerce readiness; privacy/support URLs and final owner branding. Chat07 must reconcile these with Chats02–05. None is replaced by an invented default.

## 13. Completion state of this preflight

**DONE:** pinned source/CI/artifact review, stale-evidence reconciliation, current official platform/Store checks, security audit plan, physical-device matrix, exact commands and release gates, file-level Codex/Z.ai handoff.  
**OPEN:** all proposed implementation and execution work above.  
**BLOCKED/UNKNOWN:** device/signing/Store/account/backend evidence that this environment cannot supply.  
**Final product verdict:** **NO-GO for production publication at the inspected baseline.**

The independent PR for this single-file report targets `main` from the delivery branch stated at the top; it must be reviewed with the other preflight reports and must not auto-merge.

## 14. Source register and retrieval limits

Repository links below are pinned to the reviewed commit unless they intentionally reference live API metadata. Workflow runs are dated execution receipts, not fresh tests. Official platform/Store pages were checked on 2026-11-06 and must be rechecked at submission. Apple API pages sometimes exposed only JavaScript shells; official indexed documentation supplied the manager/entitlement/deployment details. Historical 16 KB announcements were also checked through official search excerpts where direct retrieval was rate-limited. Live advisory text and binary/signing evidence remain unavailable as described above.

[R00]: https://github.com/DashSaman/PVN-amirrezagol/blob/ed21086e3e74772ab4dc31825eb87336f917fd67/docs/preflight-2026-11-06/START_HERE.md
[R01]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/app/PUBLIC_APP_MASTER_REQUIREMENTS.md
[R02]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/docs/M-P2_PUBLIC_APPS_VALIDATION.md
[R03]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/android/build.gradle.kts
[R04]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/android/src/main/kotlin/com/pvnetwork/client/PvVpnService.kt
[R05]: https://api.github.com/repos/DashSaman/PVN-amirrezagol/releases?per_page=10
[R06]: https://api.github.com/repos/DashSaman/PVN-amirrezagol/actions/runs/35897687631/artifacts
[R07]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/desktop/src/main/kotlin/com/pvnetwork/desktop/DesktopVpnController.kt
[R08]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/desktop/src/main/kotlin/com/pvnetwork/desktop/SystemProxyController.kt
[R09]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/core/foundation/build.gradle.kts
[R10]: https://api.github.com/repos/DashSaman/PVN-amirrezagol/git/ref/tags/v0.1.0-preview
[R11]: https://github.com/DashSaman/PVN-amirrezagol/tree/78dcd4511dbb830a999cc8c1796f2a38f88587e8/.github/workflows
[R12]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/docs/BROKEN_CI_REGISTER.md
[R13]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/engines/xray-adapter/src/jvmTest/kotlin/com/pvnetwork/engine/xray/JvmHostXrayRuntimeTest.kt#L81
[R14]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/engines/xray-adapter/src/jvmTest/kotlin/com/pvnetwork/engine/xray/JvmHostXrayRealBinaryInteropTest.kt
[R15]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/android/src/main/kotlin/com/pvnetwork/client/Storage.kt
[R16]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/android/src/main/kotlin/com/pvnetwork/client/MainActivity.kt
[R17]: https://api.github.com/repos/DashSaman/PVN-amirrezagol/git/trees/78dcd4511dbb830a999cc8c1796f2a38f88587e8?recursive=1
[R18]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/desktop/src/main/kotlin/com/pvnetwork/desktop/ProfileRepository.kt
[R19]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/desktop/src/main/kotlin/com/pvnetwork/desktop/SubscriptionSupport.kt
[R20]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/core/foundation/src/commonMain/kotlin/com/pvnetwork/core/diagnostics/Diagnostics.kt
[R21]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/engines/xray-adapter/src/commonMain/kotlin/com/pvnetwork/engine/xray/XrayClientConfig.kt
[R22]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/android/src/main/AndroidManifest.xml
[R23]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/docs/superpowers/plans/2026-08-29-public-mobile-client.md
[R24]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/build.gradle.kts
[R25]: https://api.github.com/repos/DashSaman/PVN-amirrezagol/rulesets
[R26]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/docs/M2_XRAY_STABLE_RELEASE_GATE.md
[R27]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/desktop/src/main/kotlin/com/pvnetwork/desktop/DesktopSecretStore.kt
[R28]: https://github.com/DashSaman/PVN-amirrezagol/blob/78dcd4511dbb830a999cc8c1796f2a38f88587e8/apps/desktop/build.gradle.kts
[R29]: https://api.github.com/repos/DashSaman/PVN-amirrezagol/branches?per_page=100
[A01]: https://developer.android.com/reference/android/net/VpnService.Builder#excludeRoute(android.net.IpPrefix)
[A02]: https://developer.android.com/develop/connectivity/vpn
[A03]: https://developer.android.com/develop/background-work/services/fgs/service-types
[A04]: https://developer.apple.com/documentation/networkextension/netunnelprovidermanager
[A05]: https://developer.apple.com/forums/thread/747499
[A06]: https://developer.apple.com/documentation/technotes/tn3134-network-extension-provider-deployment
[A07]: https://support.apple.com/guide/deployment/vpn-overview-depae3d361d0/1/web/1.0
[A08]: https://developer.apple.com/app-store/review/guidelines/
[A09]: https://developer.apple.com/news/upcoming-requirements/
[A10]: https://developer.apple.com/app-store/app-privacy-details/
[A11]: https://developer.apple.com/support/third-party-SDK-requirements/
[A12]: https://developer.apple.com/documentation/security/notarizing-macos-software-before-distribution
[A13]: https://developer.android.com/reference/android/content/pm/ServiceInfo#FOREGROUND_SERVICE_TYPE_SPECIAL_USE
[A14]: https://developer.apple.com/documentation/bundleresources/entitlements/com.apple.developer.networking.networkextension
[G01]: https://support.google.com/googleplay/android-developer/answer/11926878?hl=en
[G02]: https://support.google.com/googleplay/android-developer/answer/12564964?hl=en
[G03]: https://support.google.com/googleplay/android-developer/answer/13634885?hl=en
[G04]: https://developer.android.com/studio/publish/app-signing
[G05]: https://developer.android.com/guide/app-bundle
[G06]: https://developer.android.com/guide/practices/page-sizes
[G07]: https://support.google.com/googleplay/android-developer/answer/10787469?hl=en
[G08]: https://support.google.com/googleplay/android-developer/answer/13327111?hl=en
[G09]: https://support.google.com/googleplay/android-developer/answer/9858738?hl=en
[G10]: https://developer.android.com/google/play/billing/deprecation-faq
[G11]: https://developer.android.com/developer-verification
[G12]: https://android-developers.googleblog.com/2025/05/prepare-play-apps-for-devices-with-16kb-page-size.html
[G13]: https://support.google.com/googleplay/android-developer/thread/368982598/clarification-of-the-16kb-page-size-deadline-and-information-on-extensions?hl=en
[U01]: https://api.github.com/repos/XTLS/Xray-core/releases/tags/v26.10.31
[U02]: https://api.github.com/repos/XTLS/Xray-core/releases/tags/v26.7.28
[U03]: https://api.github.com/repos/XTLS/Xray-core/git/ref/tags/v26.10.31
