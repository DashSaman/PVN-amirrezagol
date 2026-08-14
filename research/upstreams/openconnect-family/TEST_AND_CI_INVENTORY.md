# OpenConnect — Test / CI / Quality Inventory

Review date: 2026-08-14

Canonical upstream: `https://gitlab.com/openconnect/openconnect`

Stable research baseline: **v9.21**; current `master` CI/test structure is also reviewed for post-release coverage direction.

Status: `IN-RESEARCH`. Upstream test success is not PVNetwork integration evidence.

## 1. Current GitLab CI structure

The current canonical `.gitlab-ci.yml` includes distinct stages/jobs covering areas such as:

- signoff/changelog/repository hygiene;
- primary Linux build/test paths;
- additional Linux distribution/configuration builds;
- static analysis;
- Coverity-oriented analysis paths;
- GCC/Clang sanitizer runs including undefined/address-sanitizer coverage;
- Windows MinGW 32/64 build paths;
- Android NDK build paths across multiple architectures;
- external/specialized test jobs.

Current build-image variables show multiple CentOS/Fedora/Ubuntu environments plus MinGW and Android build images. Exact CI contents can change after this review and must be re-read when pinning a release.

## 2. Crypto-backend matrix implication

OpenConnect supports GnuTLS and OpenSSL build paths, and upstream CI/release history exercises more than one TLS backend.

PVNetwork requirement:

- the exact crypto backend selected per platform belongs to the release matrix;
- adapter/interoperability tests must run against the actual backend shipped;
- one backend's successful upstream tests are not proof for the other backend.

## 3. Current `tests/` tree evidence

The canonical `tests/` tree contains a broad mixture of shell/C/test-data/certificate/config assets. Current examples visible in the tree include:

- protocol and framing tests;
- PPP-over-TLS tests;
- Pulse packet/auth/ping-oriented tests;
- ESP sequence/replay-related tests;
- certificate/password encoding tests including non-ASCII cases;
- server-certificate/hash tooling tests;
- signal/termination tests;
- TPM/SoftHSM-related configuration and state;
- TUN/TAP-related tests;
- exported-symbol/API consistency checks.

The tree is active: recent work includes Pulse tests and checks that symbols listed for export remain represented in the public API header.

## 4. Mock/fake server testing

OpenConnect development has used protocol-oriented mock/fake HTTPS servers for authentication/configuration testing. Historical F5/Fortinet integration work explicitly added Python/Flask-backed fake-server tests for auth/config fetch behavior.

The main OpenConnect test suite has also historically used `ocserv` in test scenarios; upstream issue discussion records the complexity of running multiple server instances/network namespaces/socket wrappers.

PVNetwork lesson:

- keep deterministic mock-server tests for adapter/auth state machines;
- also maintain real-server interoperability tests separately;
- never treat fake-server success as proof of vendor appliance compatibility.

## 5. Protocol framing tests as a release gate

Upstream issue/fix history for Array, PPP/oNCP and Pulse shows why packet-boundary behavior needs explicit fixtures:

- multiple network packets inside one TLS record/frame;
- one packet split across multiple TLS records;
- short reads/reassembly;
- preferred UDP transport unavailable, forcing TLS fallback;
- repeated reconnect/reassembly-state cleanup.

PVNetwork should reuse these **failure classes** at the adapter/interoperability level rather than duplicating upstream internal implementation tests.

## 6. Authentication test categories PVNetwork must add

Upstream source/tests cover substantial authentication logic, but PVNetwork owns product UI/platform integration. Add product-level tests for:

- generic auth-form rendering from library callbacks;
- text/password/select/challenge fields;
- certificate trust UI;
- client certificate selection;
- OTP/TOTP challenge continuation;
- external-browser/webview handoff;
- multiple SSO phases;
- empty/invalid SSO token rejection;
- non-progress/repeated SSO token detection;
- cancellation at every auth phase;
- secret redaction from logs/state persistence.

## 7. Platform test layers PVNetwork must add

### Windows

- service/helper lifecycle;
- TUN/TAP/Wintun-selected architecture;
- shutdown/logoff/process-kill cleanup;
- route/DNS cleanup;
- certificate store/system key behavior;
- package upgrade/uninstall;
- x64/ARM64 decision when implementation architecture is selected.

### Linux

- NetworkManager-integrated path if selected;
- application-managed service path if selected;
- systemd/service lifecycle;
- distribution package differences;
- NetworkManager/libsecret/WebKit version compatibility where applicable;
- DNS/resolver integration and route cleanup.

### Android

- JNI/native library packaging if selected;
- VpnService ownership/permission lifecycle;
- process death/background restrictions;
- network transition and battery behavior;
- ABI coverage.

### Apple platforms

Upstream OpenConnect build portability does not prove App Store/Network Extension feasibility. If OpenConnect is selected for iOS/macOS, PVNetwork must create an Apple-specific integration/test architecture and validate entitlement/store constraints separately.

## 8. API/ABI regression gate

The public API has explicit versioning and the test tree now contains exported-symbol/header consistency checks.

PVNetwork release gate for a core upgrade:

- compile/link binding against the pinned public API;
- confirm required symbols are present;
- verify callback ownership/lifetime assumptions;
- verify error-code mapping;
- run adapter state-machine tests;
- run all certified vendor/platform interoperability tests.

Do not bind PVNetwork to non-exported/private internals simply because they are convenient.

## 9. Static analysis / sanitizer lesson

Upstream actively uses static analysis and sanitizer jobs and continues to receive fixes from those tools.

PVNetwork should add its own analysis at the integration layer:

- FFI ownership/lifetime;
- callback threading/reentrancy;
- secret buffer handling;
- cancellation races;
- session teardown;
- malformed callback/form/config data;
- process/helper IPC if used.

## 10. Localization test implications

Upstream OpenConnect/NetworkManager trees contain broad translation resources, including Persian in NetworkManager-openconnect.

PVNetwork tests must go beyond string presence:

- RTL layout;
- BiDi isolation for hostnames/IPs/URLs/IDs/fingerprints;
- long translated labels;
- auth forms generated dynamically by servers;
- error/log screens containing mixed Persian and LTR technical values;
- keyboard/accessibility behavior.

## 11. Proposed PVNetwork OpenConnect test pyramid

### Layer A — pure PVNetwork model tests

- enterprise capability model;
- auth challenge conversion;
- error taxonomy;
- profile model/migration;
- redaction.

### Layer B — libopenconnect adapter contract tests

- context create/free;
- lifecycle/cancel;
- callback conversion;
- browser handoff contract;
- certificate callback contract;
- reconnect/statistics/error mapping;
- API version/feature detection.

### Layer C — deterministic local integration

- ocserv/open test server where relevant;
- controlled mock auth servers;
- malformed/edge-case responses;
- framing/fallback/reconnect cases.

### Layer D — vendor interoperability lab

For each capability actually advertised, exact vendor/server versions and auth modes must be recorded. This is mandatory for Cisco, GlobalProtect, Fortinet, Pulse/Ivanti, Juniper, F5 and Array claims.

### Layer E — real platform/device/package

- OS update/upgrade;
- network changes;
- sleep/resume;
- process death;
- package update;
- route/DNS cleanup;
- Store/package-specific behavior.

## 12. Release evidence requirement

A PVNetwork release report for OpenConnect support should contain:

- exact OpenConnect commit/release;
- exact crypto backend/dependency set;
- exact PVNetwork adapter version;
- platform/build identifiers;
- vendor/server versions tested;
- auth modes tested;
- transport/fallback modes tested;
- pass/fail/known limitation summary;
- unresolved upstream issues/MRs checked at release time.

## Remaining gaps

- complete exact v9.21 test-file manifest;
- job-by-job current CI command/dependency mapping;
- upstream code coverage metrics if available;
- current fuzzing coverage beyond sanitizer/static analysis evidence;
- full current security-advisory test implications;
- implementation of the PVNetwork test pyramid after an Enterprise Adapter exists.