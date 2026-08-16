# M2 OpenVPN Adapter — First Implementation Slice

Status: **INTEROPERABILITY VERIFIED for the isolated Ubuntu CI real-link harness; product runtime integration remains pending**

## Research/license boundary reused

The completed OpenVPN family reuse decision remains authoritative, with the newer implementation decision in `docs/ENGINE_SET_R6.md` controlling product integration. Historical research considered OpenVPN3 as a client-core candidate, but R6 does not authorize silently embedding it. This slice imports no OpenVPN3 source/binary and copies no GPL/reference-client code into PVNetwork.

The real-link test uses the Ubuntu 24.04 GitHub Actions runner's system OpenVPN package in ephemeral Linux network namespaces. That is protocol/interoperability test infrastructure, not a PVNetwork-distributed OpenVPN binary.

## Product-owned source

`engines/openvpn-adapter` implements PVNetwork-owned `.ovpn` normalization, protected original-source preservation, protected inline key/TLS/certificate material, transactional secret rollback, and a runtime-factory boundary.

The adapter now fails closed when an imported profile contains semantics this slice did not resolve:

- unsupported directives are named in `openvpn.unsupported-directive-names` and block runtime preparation;
- external `ca`, `cert`, `key`, `tls-auth`, `tls-crypt`, or file-backed `auth-user-pass` references are marked in `openvpn.unresolved-external-material-names` and block runtime preparation;
- runtime capability is advertised only by a concrete available runtime;
- the first slice still requires an explicit `remote` port and does not invent a default during canonicalization.

## Build/test evidence

GitHub Actions run `31940904674` completed **SUCCESS** after the fail-closed import/validation regression tests were added.

The current workflow receipt is GitHub Actions run `31941002218` on commit `984ecea9d9539b330be227ae123e60956e1d92b7`. Both jobs completed **SUCCESS**:

1. `OpenVPN adapter/import contracts`
   - ran `gradle --no-daemon :engines:openvpn-adapter:jvmTest --stacktrace`;
   - verified protected source/material handling, rollback, runtime capability gating, and fail-closed unsupported/unresolved semantics.
2. `OpenVPN real link / isolated Linux namespaces`
   - installed the Ubuntu runner's OpenVPN package at test time;
   - generated ephemeral CA/server/client certificates;
   - created two isolated network namespaces and a veth underlay;
   - established a TLS OpenVPN tunnel;
   - completed tunneled ping in both directions.

The real-link harness lives at `scripts/test-openvpn-real-link.sh`. It deliberately does not vendor or bundle an OpenVPN executable into PVNetwork.

## Status boundary

- OpenVPN research: **RESEARCHED**.
- PVNetwork OpenVPN import/adapter boundary: **IMPLEMENTED + BUILT + TESTED**.
- Isolated Ubuntu CI OpenVPN protocol link: **INTEROPERABILITY VERIFIED**.
- Concrete OpenVPN runtime wired into the PVNetwork desktop/application lifecycle: **not yet implemented**.
- OpenVPN3/native library embedded in PVNetwork: **no**.
- DEVICE VERIFIED: **no**.
- Store verified/certified: **no**.
- PRODUCTION READY: **no**.

M2 remains **IN_PROGRESS**. The next product-runtime work must preserve the R6 license/distribution boundary, keep reusable secrets behind `SecretRef`, and obtain its own runtime/build/integration evidence.
