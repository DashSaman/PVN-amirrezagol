# XFRM/IPsec — Server UI and Menus

Reviewed: 2026-08-15

No protocol-owned web panel exists. Linux exposes XFRM interface management plus ordinary IKE/IPsec controls.

XFRM-interface management fields/actions include interface name, 32-bit interface ID, optional underlying device, link state, addresses/routes where needed, statistics and delete lifecycle. strongSwan maps `if_id_in`/`if_id_out` to policies/SAs and may generate unique IDs per CHILD_SA/direction.

The security surface remains separate: IKE identity/authentication, credentials, proposals, CHILD_SAs, policies, status/logging/rekey/liveness. Interface state is not security-association state.

Network namespace/VRF placement and optional route installation are implementation/operations controls, not cryptographic settings. Account/subscription/consumer-dashboard concepts are NOT-APPLICABLE to XFRM interfaces themselves.
