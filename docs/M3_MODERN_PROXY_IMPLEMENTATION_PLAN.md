# M3 Modern Proxy Wave — Implementation Plan

Status: **IN PROGRESS**

## Goal

M3 expands the verified M2 external-core architecture without reimplementing proxy cryptography. Product support is promoted only when a concrete runtime path is implemented, fail-closed validation exists, and a retained real connection/data-path receipt covers the advertised combination.

## Engine partition

### Xray host-supplied runtime — selected

The already verified `JvmHostXrayRuntimeFactory` is the preferred runtime boundary for Xray-owned capabilities. M3 will extend it incrementally for:

- VLESS (already scoped real-path verified in M2);
- VMess compatibility;
- Trojan compatibility;
- Shadowsocks compatibility;
- REALITY where supported by VLESS;
- Vision (`xtls-rprx-vision`) where supported by current Xray semantics;
- XHTTP where supported by current Xray.

The existing bundled/imported Xray production gate remains separate and blocked. M3 does not change that distribution decision.

### Legacy XTLS security — not selected

Current Xray treats the historical `security: xtls` path as removed. PVNetwork will not manufacture a legacy XTLS runtime. The supported modern direction is Vision flow with TLS or REALITY where exact interoperability evidence exists.

### Hysteria2 / TUIC / AnyTLS — separate exact-core gate

These capabilities are not silently mapped to Xray. M3 must choose an exact maintained upstream core (Mihomo and/or dedicated maintained cores) only after current source/release/license/security/SBOM and executable lifecycle review. No core may be bundled or promoted from a floating `latest` reference.

## Xray M3 canonical-profile contract

The product-owned `PVProfile` remains generic. Xray protocol-specific reusable credentials are always opaque `SecretRef` values:

- `vless` -> `xray.vless.identity`;
- `vmess` -> `xray.vmess.identity`;
- `trojan` -> `xray.trojan.password`;
- `shadowsocks` -> `xray.shadowsocks.password`.

Common stream dimensions remain namespaced extensions rather than being flattened into an opaque URI:

- `xray.application-protocol`;
- `xray.security`;
- `xray.transport`;
- server name / fingerprint / REALITY fields;
- path / host / gRPC service name;
- protocol-specific fields such as `xray.vmess-security` or `xray.shadowsocks-method`.

Unknown protocol/security/transport/cipher combinations fail closed before runtime preparation.

## Promotion sequence

1. Extend concrete Xray runtime capabilities and protocol-specific config generation.
2. Add validation regression tests for each protocol and secret role.
3. Add real-binary VLESS-adjacent local interoperability tests using only the exact-checksum CI fixture allowed by the Xray release gate.
4. Promote only combinations that actually pass; retain explicit non-claims for untested stream/security combinations.
5. Select and gate the non-Xray modern core for Hysteria2/TUIC/AnyTLS.
6. Close M3 only when the selected scope in `docs/ROADMAP.md` has concrete retained evidence; parser acceptance alone is never enough.

## Security boundaries

- no protocol cryptography is reimplemented in Kotlin;
- no reusable password/UUID/key is persisted in canonical profile metadata;
- child process config remains transient/private;
- no shell execution;
- no unpinned runtime download;
- unsafe TLS verification is never a hidden product default;
- deprecation warnings in upstream VMess/Trojan/Shadowsocks are treated as compatibility signals, not hidden from the product decision record.
