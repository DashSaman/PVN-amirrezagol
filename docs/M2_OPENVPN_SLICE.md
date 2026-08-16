# M2 OpenVPN Adapter — First Implementation Slice

Status: **IN PROGRESS — product-owned source committed; CI pending**

## Research/license boundary reused

Before implementation, the completed OpenVPN family reuse decision was used as the authority. Its primary research candidate is:

`OpenVPN/openvpn3@1fd271caefc9a71406afdc2ff2460999dcfdb234`

The dossier records the license at that pin as **AGPL-3.0-only OR MPL-2.0** and classifies OpenVPN3 as a strong reuse candidate with an **MPL path + dependency + platform review required**.

This slice deliberately imports **no OpenVPN3 source or binary**. The exact production release/SBOM/transitive-license/legal path remains a dependency-import gate. GPL/reference clients are not copied.

## Product-owned source added

`engines/openvpn-adapter` implements only PVNetwork-owned contracts:

- first `.ovpn` import/normalization surface for `remote`, `proto`, `dev`, `auth-user-pass` and common inline material blocks;
- complete original profile preserved only via `SecretStore`, so unsupported directives remain recoverable without putting raw sensitive source into normal metadata;
- inline `<key>`, `<tls-auth>`, `<tls-crypt>`, `<ca>` and `<cert>` material represented only by opaque secret/protected refs;
- external credential/certificate file paths are not read implicitly and produce explicit warnings;
- unknown directives are named in warnings while the full original source remains protected for later lossless handling;
- transactional rollback prevents protected source/material refs from being orphaned after a failed import;
- `OpenVpnRuntimeFactory` boundary for a later approved upstream/native core;
- runtime capability is advertised only when a concrete approved runtime reports available.

The first slice requires an explicit port in `remote`; it does not silently invent an OpenVPN default during canonicalization.

## CI gate

`.github/workflows/m2-openvpn-adapter-ci.yml` runs:

```bash
gradle --no-daemon :engines:openvpn-adapter:jvmTest --stacktrace
```

Tests cover protected original-source preservation, secret non-leakage, unsupported/external directive warnings, transactional rollback and the rule that completed research is not treated as an available runtime.

Until the new CI run actually succeeds, this slice is IMPLEMENTED in source only; BUILT/TESTED are not claimed.

No OpenVPN engine connection, interoperability, device, Store, or production claim is made by this source slice.
