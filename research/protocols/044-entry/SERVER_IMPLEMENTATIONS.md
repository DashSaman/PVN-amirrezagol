# 044 TUIC v5 — server implementations / ecosystem

Reviewed: 2026-08-15

Canonical protocol authority: `tuic-protocol/tuic@8e118f242f24a17a9f487dc344cc50d7e63e557e`, tree `3dab59619e77fe44d4f97b534e7b8ea9a0e96475`, `SPEC.md` blob `fe246d88e57e306e767265230fa178640950060a`, protocol version `0x05`. The repository is GPL-3.0 documentation/governance; its README states the TUIC protocol concept itself is license-free, untrademarked and unpatented. The canonical repository deliberately has no official implementation.

Serious current implementations:
- `Watfaq/clash-rs@b0538e86aedcbe7f000bb9f00889175ffb85176c`, Apache-2.0: active client/core/FFI/dashboard ecosystem and strongest permissive client-engine candidate.
- `cfal/shoes@7a5a8ee3bd1c52bc15ec57e074e95e374d41f275`, MIT: active server/client/TUN implementation; canonical TUIC README lists it as server-side implementation.
- `Itsusinn/tuic@0eef0b1d62758bb63f954a81f7ac74b94ed9da29`, tree `cfd1d3bf38c5eeb3ba72de5f65fb737e5ef7c8a7`: active full client/server successor with GPL/copyright complexity; reference-only by default for closed PVNetwork.

Other canonical-list implementations include mihomo and sing-box plus closed clients. They are interoperability/UI references until separately pinned.

Reuse decision: evaluate ClashRS TUIC library/FFI and shoes server/reference first; do not invent an 'official TUIC engine'.
