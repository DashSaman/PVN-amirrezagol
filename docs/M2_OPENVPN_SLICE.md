# M2 OpenVPN Adapter — Implementation and Interoperability Slices

Status: **system-package protocol link INTEROPERABILITY VERIFIED; product-owned Linux system-process runtime IMPLEMENTED + BUILT + TESTED; runtime-path interoperability still pending**

## Research/license boundary reused

The completed OpenVPN family reuse decision remains authoritative, with the newer implementation decision in `docs/ENGINE_SET_R6.md` controlling product integration. Historical research considered OpenVPN3 as a client-core candidate, but R6 does not authorize silently embedding it. These slices import no OpenVPN3 source/binary and copy no GPL/reference-client code into PVNetwork.

The real-link test uses the Ubuntu 24.04 GitHub Actions runner's system OpenVPN package in ephemeral Linux network namespaces. The desktop/JVM runtime likewise discovers and executes an OpenVPN binary supplied by the host system. Neither path vendors or bundles OpenVPN into PVNetwork.

## Product-owned import/adapter source

`engines/openvpn-adapter` implements PVNetwork-owned `.ovpn` normalization, protected original-source preservation, protected inline key/TLS/certificate material, transactional secret rollback, and a runtime-factory boundary.

The adapter fails closed when an imported profile contains semantics this slice did not resolve:

- unsupported directives are named in `openvpn.unsupported-directive-names` and block runtime preparation;
- external `ca`, `cert`, `key`, `tls-auth`, `tls-crypt`, or file-backed `auth-user-pass` references are marked in `openvpn.unresolved-external-material-names` and block runtime preparation;
- runtime capability is advertised only by a concrete available runtime;
- the first slice requires an explicit `remote` port and does not invent a default during canonicalization.

## Product-owned Linux/JVM system runtime

`engines/openvpn-adapter/src/jvmMain/kotlin/com/pvnetwork/engine/openvpn/JvmSystemOpenVpnRuntime.kt` adds a Linux desktop runtime boundary that:

- uses an unbundled host-system `openvpn` executable and never invokes a shell;
- probes `openvpn --version` with a hard timeout while concurrently draining output so a silent or chatty process cannot deadlock the probe;
- retains only the first bounded version line and does not persist raw OpenVPN diagnostics;
- reads the protected original profile only through `SecretStore.withSecret`;
- atomically creates a POSIX mode-0700 runtime directory and mode-0600 transient profile;
- launches OpenVPN through `PreparedConnection` and maps lifecycle to PVNetwork connection states;
- recognizes a successful OpenVPN initialization without storing raw log lines;
- destroys the process and recursively removes transient runtime material on stop, startup failure, missing secret, or process exit.

The JVM tests use a fake executable to verify discovery/probing, bounded output draining, 0700/0600 materialization, state transitions, missing-secret failure, process lifecycle and cleanup. A fake runtime is test evidence for product-owned lifecycle semantics only; it is not interoperability evidence.

## Build/test/interoperability evidence

- GitHub Actions run `31940904674`: **SUCCESS** after fail-closed import/validation regression coverage.
- GitHub Actions run `31941002218`: **SUCCESS** for both adapter tests and the isolated system-package real-link harness.
- GitHub Actions run `31941209625`: **SUCCESS** after the first Linux/JVM system runtime lifecycle tests.
- GitHub Actions run `31941352613` on the current hardened runtime/test slice: **SUCCESS** for both jobs:
  1. `OpenVPN adapter/import contracts` — JVM tests including system-process runtime, protected material lifecycle and stress version-probe coverage.
  2. `OpenVPN real link / isolated Linux namespaces` — Ubuntu runner system OpenVPN package, ephemeral CA/server/client certificates, two isolated namespaces, TLS tunnel and bidirectional tunneled ping.

The standalone real-link harness remains `scripts/test-openvpn-real-link.sh`. It establishes protocol interoperability but bypasses `JvmSystemOpenVpnRuntimeFactory`; therefore it cannot by itself promote the new product runtime path to interoperability-verified.

## Status boundary

- OpenVPN research: **RESEARCHED**.
- PVNetwork OpenVPN import/adapter boundary: **IMPLEMENTED + BUILT + TESTED**.
- PVNetwork Linux/JVM system-process runtime boundary: **IMPLEMENTED + BUILT + TESTED** via run `31941352613`.
- Isolated Ubuntu CI system-package OpenVPN protocol link: **INTEROPERABILITY VERIFIED** via run `31941002218` / current repeated real-link success in `31941352613`.
- `JvmSystemOpenVpnRuntimeFactory` exercised against a real OpenVPN executable/TLS peer: **not yet verified**.
- OpenVPN3/native library embedded in PVNetwork: **no**.
- DEVICE VERIFIED: **no**.
- Store verified/certified: **no**.
- PRODUCTION READY: **no**.

M2 remains **IN_PROGRESS**. The next gate is to exercise the actual PVNetwork `JvmSystemOpenVpnRuntimeFactory` path against a real system OpenVPN executable and real TLS peer in CI before upgrading that specific runtime path to interoperability-verified.
