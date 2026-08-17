# M2 Xray Host-Supplied JVM Runtime Validation

Status: **INTEROPERABILITY_VERIFIED for the explicitly scoped Linux/JVM VLESS RAW + security=none CI path**

Validation date: 2026-08-17 UTC  
Authoritative GitHub Actions run: `32072138649`  
Validated head SHA: `d1329c252c3eb42967793e612002fa5d516ddb69`

## Scope proved

PVNetwork now has a product-owned JVM managed-subprocess runtime for a **host-supplied** Xray executable. The runtime itself does not download, bundle, embed, cache or publish Xray.

The successful real-binary CI path is:

`test client -> PVNetwork SOCKS inbound -> PVNetwork-generated VLESS outbound -> real Xray VLESS server -> Freedom outbound -> isolated IPv4 loopback TCP echo origin`

The test requires a known marker to traverse that path in both directions and verifies that the PVNetwork prepared connection remains in canonical `CONNECTED` state while the payload round-trip succeeds.

## Runtime implementation proved

Implementation:

- `engines/xray-adapter/src/jvmMain/kotlin/com/pvnetwork/engine/xray/JvmHostXrayRuntime.kt`

The JVM runtime boundary:

1. accepts an explicit host executable or discovers `xray` from the host PATH/common host locations;
2. rejects unavailable/non-executable/non-Xray probes and advertises no VLESS capability in that case;
3. invokes `xray version` directly with a bounded timeout and without a shell;
4. resolves the VLESS identity only through `SecretStore`;
5. creates a mode-0700 POSIX runtime directory and mode-0600 transient JSON configuration;
6. asks the same executable to validate generated configuration with `xray run -test -c ...` before long-lived launch;
7. starts the long-lived process with `xray run -c ...` directly, without a shell;
8. maps readiness/process exit/stop to canonical PVNetwork connection states;
9. drains child output without retaining an unbounded/raw secret-bearing log;
10. removes transient configuration on normal stop and failure paths.

## Deterministic unit/lifecycle evidence

The companion JVM suite covers:

- executable/version probe fail-closed behavior;
- config-test-before-start ordering;
- failure before long-lived launch when config validation fails;
- missing-identity fail-closed behavior;
- mode-0700/mode-0600 runtime material permissions;
- PREPARING -> CONNECTING -> ESTABLISHING_TUNNEL -> CONNECTED lifecycle;
- DISCONNECTING -> DISCONNECTED stop lifecycle and transient-file cleanup;
- Vision validation requiring TLS or REALITY security.

In run `32072138649`, job **Xray VLESS adapter/share-link contracts** completed successfully.

## Real external binary receipt

The interoperability job used an ephemeral CI-only fixture exactly as permitted by `docs/M2_XRAY_STABLE_RELEASE_GATE.md`:

- upstream: `XTLS/Xray-core`
- tag: `v26.7.28`
- upstream commit reported by the binary/release evidence: `5ca6f4b`
- platform asset: `Xray-linux-64.zip`
- SHA-256: `8195d909f1109b8f3d99eefe401a3c451d7bf4af71f24d3815420f77e5dd2a40`
- observed binary: `Xray 26.7.28 ... 5ca6f4b (go1.26.5 linux/amd64)`

The workflow verifies the checksum before extraction and execution. The fixture is not committed as a product dependency and passing this job does **not** promote the prerelease to a production dependency.

In run `32072138649`, job **Real Xray VLESS JVM data path** completed successfully, including the exact-checksum fixture install and the bidirectional VLESS payload proof.

## Xray private-address test harness rule

During validation, the real server initially did not connect to the loopback echo origin because Xray `v26.7.28` Freedom applies a default private-IP block to inbound names including `vless`. Upstream source at that tag establishes `defaultBlockPrivateRule` for VLESS/VMess/Trojan/Hysteria/WireGuard-originated Freedom traffic.

The CI-only server therefore has an explicit first `finalRules` entry with `action: allow` so the isolated loopback origin is intentionally reachable. This is a test-harness exception, not a weakening of the PVNetwork client runtime and not a recommended production server policy.

## What this does not prove

This receipt is intentionally narrow. It does **not** certify every Xray/VLESS combination. In particular, it does not yet provide real-interoperability certification for each of TLS, REALITY, Vision, WebSocket, gRPC, XHTTP or mKCP, and it does not certify Windows/macOS/device/Store behavior.

It also does not unblock bundled/imported production Xray. `docs/M2_XRAY_STABLE_RELEASE_GATE.md` remains authoritative for that separate supply/distribution gate.

## M2 acceptance impact

For the roadmap M2 scope, the selected Xray product-runtime strategy is the host-supplied JVM managed-subprocess boundary. Together with the already retained WireGuard and OpenVPN real-link receipts, run `32072138649` satisfies the remaining M2 requirement for a real connection test on the selected Xray runtime scope.

M2 may therefore transition to PASS while the bundled-production Xray release/import blocker remains recorded independently for any future product-managed distribution decision.
