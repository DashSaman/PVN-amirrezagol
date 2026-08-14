# AGENTS Handoff — 2026-08-14 — IKE/IPsec v1 Closure

Mandatory continuation checkpoint.

## State transition

Entries 004–008 are now:

`V1-HANDOFF-READY / NOT IMPLEMENTED`

## Shared evidence

`research/upstreams/strongswan-family/` now contains:

- `SOURCE_ARCHITECTURE.md`
- `PLATFORM_NATIVE_COMPARISON.md`
- `PROTOCOL_CAPABILITY_MODEL.md`
- `DEPENDENCIES_SECURITY_TESTS.md`
- `SUPPORT_REUSE_DECISIONS.md`

## Numbered evidence

- 004 IKEv2/IPsec
- 005 IKEv1/IPsec
- 006 ESP
- 007 AH
- 008 L2TP/IPsec

all have `V1_RESEARCH.md` research decisions.

## Main backend decision

`NATIVE-IKEV2-FIRST WHERE CAPABLE / STRONGSWAN FOR LINUX+ADVANCED COMPATIBILITY`

StrongSwan root license reviewed GPLv2 family. Use deliberate daemon/plugin/license architecture rather than assuming permissive embedding.

## Important semantic rule

- IKEv1/IKEv2 = control/auth/key negotiation;
- ESP/AH = data plane;
- AH does not encrypt payload;
- L2TP/IPsec = layered composition, not just IKE.

## Residual gaps

Exact strongSwan release/plugin/SBOM/advisory, native OS feature matrices, Android front end, issue sampling and device/interoperability remain explicit. Full server/install/crypto/wire-flow work is mandatory v2.

## Exact next action

1. Activate original-v1 classic/legacy tunnel group 009–012.
2. Research 009 L2TPv3, 010 L2TPv3/IPsec, 011 SSTP and 012 PPTP separately.
3. Use SoftEther only as one implementation/reference where appropriate; also identify native/dedicated open-source client/server candidates.
4. Mark legacy/insecure technologies explicitly instead of treating all as recommended VPN choices.
5. Create source/architecture/security/reuse decisions and sync numbered entries.
6. Checkpoint and continue next unfinished v1 family without owner prompting.
7. Do not start mass COMPLETE-REFERENCE-v2 yet.
