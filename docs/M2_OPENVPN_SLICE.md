# M2 OpenVPN Adapter — First Implementation Slice

Status: **PASS for product-owned import/adapter build and tests; runtime integration remains pending**

## Research/license boundary reused

The completed OpenVPN family reuse decision remains authoritative. Primary research candidate:

`OpenVPN/openvpn3@1fd271caefc9a71406afdc2ff2460999dcfdb234`

The dossier records **AGPL-3.0-only OR MPL-2.0** at that pin and requires an MPL-path, dependency/SBOM and platform/distribution review before any product dependency import. No OpenVPN3 or GPL/reference client source/binary is imported by this slice.

## Product-owned source

`engines/openvpn-adapter` implements PVNetwork-owned `.ovpn` normalization, protected original-source preservation, protected inline key/TLS/certificate material, explicit external-file/unsupported-directive warnings, transactional secret rollback, and a runtime-factory boundary. Runtime capability is advertised only by a concrete available runtime.

The first slice intentionally requires an explicit `remote` port and does not invent an OpenVPN default during canonicalization.

## Real build/test evidence

GitHub Actions run `31939586890` on commit `6f1e65930382f2d5b656a09b54f45f510c213242` completed **SUCCESS** for:

```bash
gradle --no-daemon :engines:openvpn-adapter:jvmTest --stacktrace
```

Tests cover:

- protected original `.ovpn` preservation without raw sensitive material in normal profile/config strings;
- inline private-key/TLS material moved behind `SecretRef`;
- explicit warnings for unsupported and external-file directives;
- rollback of protected refs after failed import;
- no capability advertisement from research alone.

## Status boundary

- OpenVPN research: RESEARCHED.
- PVNetwork OpenVPN import/adapter boundary: IMPLEMENTED + BUILT + TESTED.
- OpenVPN3/native runtime in PVNetwork: NOT YET INTEGRATED.
- real OpenVPN connection/interoperability: NOT YET VERIFIED.
- DEVICE VERIFIED / Store / PRODUCTION READY: no.

M2 remains IN_PROGRESS.
