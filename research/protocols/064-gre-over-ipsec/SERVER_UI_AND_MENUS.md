# GRE over IPsec — Server UI and Menus

Reviewed: 2026-08-15

The composition has no protocol-owned web panel. Management surfaces belong to the GRE endpoint and IPsec/IKE implementation.

- Linux: GRE interface fields/actions are documented in entry 063; IPsec/IKE control/configuration surfaces are documented in the strongSwan-family V2 dossier. Important boundaries are tunnel endpoint/routing, IKE identity/authentication, CHILD_SA/traffic selectors, ESP proposals, credentials/certificates, status/logging and lifecycle.
- Cisco IOS XE: official workflow exposes IKEv2 keyring/profile, authentication, transform set, IPsec profile, tunnel interface address/source/destination and `tunnel protection ipsec profile`.

No accounts/subscriptions/quotas/consumer dashboard are intrinsic to GRE-over-IPsec. Any future PVNetwork UI must keep GRE routing fields and IPsec security/credential fields visibly separate.
