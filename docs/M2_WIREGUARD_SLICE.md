# M2 WireGuard Adapter — First Implementation Slice

Status: **PASS for product-owned adapter/import tests and isolated Linux kernel real-link verification**

## Research reused

The implementation reused the completed WireGuard family dossier, including `SUPPORT_REUSE_DECISIONS.md`, `SOURCE_REVISIONS.md`, `CORE_ARCHITECTURE.md`, `DEPENDENCIES_SBOM.md`, and `LESSONS_AND_TESTS.md`. No protocol research was reopened.

The reviewed portable upstream pin remains `WireGuard/wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0` (MIT), but the product module still does not vendor or link that upstream source. The adapter keeps an explicit native/runtime boundary and does not implement cryptography.

## Adapter/security evidence

`engines/wireguard-adapter` implements typed config/import, `SecretStore` separation, unsupported-field warnings, validation, runtime capability gating, and transactional rollback of newly-created secret refs when import fails.

GitHub Actions run `31939414530` on commit `cc429454c54479abfd6bb05df2891e10fa3e7c57` completed **SUCCESS** for:

```bash
gradle --no-daemon :engines:wireguard-adapter:jvmTest --stacktrace
```

This includes the regression test proving a failed import leaves zero newly-created secrets behind.

## Real Linux WireGuard evidence

The same run installed Ubuntu `wireguard-tools 1.0.20210914-1ubuntu4` as CI test tooling and executed `scripts/test-wireguard-real-link.sh` as root on Ubuntu 24.04.4.

The harness created two network namespaces, a veth underlay, two Linux kernel WireGuard interfaces and independently generated ephemeral key pairs. Actual tunnel traffic result:

- `3 packets transmitted, 3 received, 0% packet loss` through `10.203.0.1/24 <-> 10.203.0.2/24`;
- non-zero latest-handshake timestamps were required on both peers;
- peer A transfer: `476 532` bytes;
- peer B transfer: `532 476` bytes;
- exact log marker: `PVNetwork WireGuard real-link: PASS`.

This is real Linux kernel WireGuard handshake/packet-transfer evidence in an isolated hosted-CI namespace harness. It is **not** evidence that a PVNetwork desktop/mobile privileged runtime has been integrated, and it is not device or public-server interoperability certification.

## Status boundary

- WireGuard research: RESEARCHED.
- PVNetwork WireGuard config/import/adapter boundary: IMPLEMENTED + BUILT + TESTED.
- Linux kernel WireGuard peer interoperability in CI namespace harness: INTEROPERABILITY VERIFIED **for this narrow test setup only**.
- PVNetwork platform runtime integration: NOT YET IMPLEMENTED.
- DEVICE VERIFIED: no.
- PRODUCTION READY: no.

M2 remains IN_PROGRESS because the roadmap also requires the OpenVPN and Xray first-wave cores and product runtime/real-connection integration.
