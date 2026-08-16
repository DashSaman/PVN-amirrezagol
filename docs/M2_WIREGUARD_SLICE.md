# M2 WireGuard Adapter — First Implementation Slice

Status: **IN PROGRESS — adapter unit gate PASS; transactional secret fix and real Linux connection gate pending**

## Research reused before implementation

This slice reuses `SUPPORT_REUSE_DECISIONS.md`, `SOURCE_REVISIONS.md`, `CORE_ARCHITECTURE.md`, `DEPENDENCIES_SBOM.md`, and `LESSONS_AND_TESTS.md` from the completed WireGuard dossier. `WireGuard/wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0` remains a reviewed MIT source pin, but no upstream WireGuard engine source is vendored or linked by the application module in this slice.

## Source and unit evidence

`engines/wireguard-adapter` contains typed non-secret config, first wg-quick import support, protected secret refs, unsupported-field warnings, validation, and a runtime factory boundary. Capability advertisement is withheld when no concrete runtime is available.

GitHub Actions run `31939273972` on commit `1695abed2e2974ca65fc3018322c5c97fb6bc38f` completed **SUCCESS** for:

```bash
gradle --no-daemon :engines:wireguard-adapter:jvmTest --stacktrace
```

The importer is now additionally wrapped in a secret transaction so any secret refs created by a failed import are deleted. A regression test asserts that an invalid profile leaves zero newly-created secrets behind. This security change still requires its new CI run.

## Real connection gate added

`scripts/test-wireguard-real-link.sh` creates two isolated Linux network namespaces, a veth underlay, two kernel WireGuard interfaces, independently generated ephemeral key pairs, and a `10.203.0.0/24` encrypted tunnel. The gate requires:

- three successful ICMP packets through the WireGuard tunnel;
- non-zero latest-handshake timestamps on both peers;
- transfer counters;
- exact marker `PVNetwork WireGuard real-link: PASS`.

GitHub CI installs distribution `wireguard-tools` only for this verification harness. This is **test tooling**, not a vendored or product runtime dependency. No private key is printed; ephemeral key files are mode-restricted by `umask 077` and removed by a cleanup trap.

This new real-link gate is pending and must not be called successful until the workflow actually completes.

## Evidence boundary

- WireGuard: RESEARCHED.
- product-owned config/import/adapter boundary: IMPLEMENTED; first unit run PASS, security revision pending retest.
- Linux kernel WireGuard real-link interoperability: gate configured, **not yet verified**.
- PVNetwork production platform runtime: NOT YET INTEGRATED.
- DEVICE VERIFIED / Store / PRODUCTION READY: NOT CLAIMED.

M2 remains IN_PROGRESS and also includes the OpenVPN and Xray priority candidates from the roadmap.
