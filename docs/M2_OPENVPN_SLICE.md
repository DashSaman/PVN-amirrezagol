# M2 OpenVPN Adapter — Implementation and Interoperability Slices

Status: **INTEROPERABILITY VERIFIED for the scoped Ubuntu/Linux product runtime path and isolated system-package protocol link**

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

The JVM tests use a fake executable for deterministic lifecycle/probe/secret-cleanup coverage and a CI-only real-peer fixture for the actual system runtime path.

## Build/test/interoperability evidence

Earlier receipts remain valid:

- GitHub Actions run `31940904674`: **SUCCESS** after fail-closed import/validation regression coverage.
- GitHub Actions run `31941002218`: **SUCCESS** for adapter tests plus the isolated system-package real-link harness.
- GitHub Actions run `31941352613`: **SUCCESS** for the hardened product-owned runtime tests and repeated isolated real-link harness.

Current promotion receipt:

- GitHub Actions run `31942028587`: **SUCCESS** with all three jobs passing:
  1. `OpenVPN adapter/import contracts`;
  2. `OpenVPN real link / isolated Linux namespaces`;
  3. `PVNetwork system runtime / real OpenVPN TLS peer`.
- The third job installs the Ubuntu 24.04 runner's unbundled system OpenVPN `2.6.19`, starts a real local TLS peer, and executes the targeted product test `JvmSystemOpenVpnRuntimeTest.realSystemOpenVpnRuntimePathReachesConnectedWhenCiFixtureEnabled`.
- That test requires the actual `JvmSystemOpenVpnRuntimeFactory` to discover the real executable, reach `CONNECTED`, create the requested TUN interface, avoid `ERROR`, then cleanly stop to `DISCONNECTED`, remove the TUN interface, and emit both `DISCONNECTING` and `DISCONNECTED` lifecycle states.

The standalone data-plane harness remains `scripts/test-openvpn-real-link.sh`; run `31942028587` also repeats that independent isolated-namespace link gate.

## Status boundary

- OpenVPN research: **RESEARCHED**.
- PVNetwork OpenVPN import/adapter boundary: **IMPLEMENTED + BUILT + TESTED**.
- PVNetwork Linux/JVM unbundled system-process runtime boundary: **IMPLEMENTED + BUILT + TESTED**.
- Isolated Ubuntu CI system-package OpenVPN protocol link: **INTEROPERABILITY VERIFIED**.
- `JvmSystemOpenVpnRuntimeFactory` against a real unbundled OpenVPN executable and TLS peer: **INTEROPERABILITY VERIFIED**, scoped to the Ubuntu 24.04 CI runtime path proven by run `31942028587`.
- OpenVPN3/native library embedded in PVNetwork: **no**.
- DEVICE VERIFIED: **no**.
- Store verified/certified: **no**.
- PRODUCTION READY: **no**.

M2 remains **IN PROGRESS** because Xray still lacks a concrete runtime/interoperability gate. The next active work is the Xray stable-release import gate defined by `docs/ENGINE_SET_R6.md` and `docs/M2_XRAY_SLICE.md`.
