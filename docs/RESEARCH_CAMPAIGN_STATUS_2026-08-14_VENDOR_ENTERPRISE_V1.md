# PVNetwork Research Campaign Status — 2026-08-14 — Vendor Enterprise v1 Closure

Repository phase: research / requirements / architecture.

Entries 025–036: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

## Shared evidence

`research/upstreams/vendor-enterprise-family/SUPPORT_REUSE_DECISIONS.md`

## Numbered evidence

Separate `V1_RESEARCH.md` files now exist for entries 025–036.

## Key decisions

- Check Point: `snx-rs` is a valuable Rust interoperability reference but AGPL direct embedding is a closed-product concern.
- SonicWall NetExtender: vendor-specific/official client primary until a reusable implementation is proven.
- SonicWall Global VPN: reuse IPsec model only where exact standard semantics match.
- Sophos SSL: use OpenVPN Adapter where exact profile/version is genuinely OpenVPN-compatible.
- Sophos IPsec: reuse native/strongSwan IPsec model.
- WatchGuard IKEv2: standard IKEv2 interoperability target; native-OS first.
- WatchGuard SSL: detect actual profile protocol; use OpenVPN only when proven compatible.
- WatchGuard L2TP: legacy layered L2TP/IPsec model.
- Aruba VIA, Citrix Secure Access, Barracuda TINA: vendor-specific; official client primary until reusable source/spec evidence exists.
- Juniper Secure Connect: separate from old Juniper Network Connect/OpenConnect family; standard IPsec reuse only when exact mode is proven.

## Product rule

Enterprise compatibility is vendor/version/auth/SSO/posture/tunnel capability based, never one global vendor boolean.

## Residual gaps

Exact current vendor product/version matrices, source/license/activity for third-party projects, SSO/MFA/posture details, current issue/security behavior, full UI/storage/update/Store evidence and exact standard-vs-proprietary boundaries remain.

Mandatory v2 later adds server/appliance deployment references, full menus, cryptography/wire flow and topology evidence.

## Next exact action

Reconcile remaining original-v1 entries not yet handoff-ready. Prioritize 041 Shadowsocks 2022 and security/transport entries 077–093 using actual repository state. Preserve the distinction between protocol, security layer and transport. Then checkpoint original-v1 coverage before starting mandatory v2.
