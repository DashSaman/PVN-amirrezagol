# PVNetwork Research Campaign Status — 2026-08-14 — IKE/IPsec v1 Closure

Repository phase: research / requirements / architecture.

Entries 004–008: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

## Shared evidence created

Under `research/upstreams/strongswan-family/`:

- `SOURCE_ARCHITECTURE.md`
- `PLATFORM_NATIVE_COMPARISON.md`
- `PROTOCOL_CAPABILITY_MODEL.md`
- `DEPENDENCIES_SECURITY_TESTS.md`
- `SUPPORT_REUSE_DECISIONS.md`

## Numbered evidence created

- `research/protocols/004-ikev2-ipsec/V1_RESEARCH.md`
- `005-ikev1-ipsec/V1_RESEARCH.md`
- `006-ipsec-esp/V1_RESEARCH.md`
- `007-ipsec-ah/V1_RESEARCH.md`
- `008-l2tp-ipsec/V1_RESEARCH.md`

## Main architecture decision

`NATIVE-IKEV2-FIRST WHERE CAPABLE / STRONGSWAN FOR LINUX+ADVANCED COMPATIBILITY`

PVNetwork should use one product-level IPsec Adapter with backend capability discovery rather than force one engine across every OS.

## strongSwan role

Root license reviewed: GPLv2 family.

Research classification:

`STRONG LINUX/ADVANCED ENGINE CANDIDATE / GPL+PLUGIN+DAEMON-BOUNDARY REVIEW REQUIRED`

Source architecture separates `libstrongswan`, `libcharon`, IKE_SA/CHILD_SA state, plugins, kernel backends and VICI/swanctl control.

## Protocol classification

- 004 IKEv2/IPsec — high-priority standard VPN target.
- 005 IKEv1/IPsec — legacy/vendor compatibility target, not default.
- 006 ESP — foundational IPsec data plane, not a normal standalone profile.
- 007 AH — integrity/authentication without payload encryption; advanced/low priority.
- 008 L2TP/IPsec — layered legacy stack requiring both IPsec and L2TP/PPP-style components.

## Native platform direction

- Linux: strongSwan + kernel IPsec primary candidate.
- Apple: native NetworkExtension IKEv2 first when profile requirements fit.
- Windows: native Windows IKEv2 first for standard profiles.
- modern Android: native IKEv2 profile/VpnManager first where API/profile requirements fit; strongSwan path for advanced/legacy/older compatibility only when justified.

No native backend is assumed to have full strongSwan feature parity.

## Security/product rules established

- do not silently downgrade IKE version;
- do not silently weaken algorithms;
- secure credentials/PSKs/private keys outside ordinary profile JSON;
- keep IKE control state and ESP/AH data-plane state separate;
- VICI/control endpoints private/permissioned behind the product adapter;
- strongSwan capabilities depend on compiled/loaded plugin set;
- final SBOM/security evidence must be per exact backend/platform build.

## Residual gaps preserved

- exact full current strongSwan release/tag/commit;
- exact plugin/dependency/license/advisory matrix;
- source-level Android front-end/menu/storage map;
- exact native OS capability/version matrices;
- current issue/regression sampling;
- interoperability/device/performance/Store proof;
- server installers/panels, exhaustive menus, cryptography, IKE/ESP/AH/L2TP wire flow, UDP 500/4500/NAT-T and deployment topologies deferred to mandatory v2.

## Next exact action

Continue original v1 immediately. Selected next group: **remaining classic/legacy tunnel entries 009–012 (L2TPv3, L2TPv3/IPsec, SSTP, PPTP)**. Research them independently even where SoftEther or native OS implementations overlap. Do not begin mass v2 yet.
