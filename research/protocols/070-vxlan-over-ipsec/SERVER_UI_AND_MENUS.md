# VXLAN over IPsec — Server UI and Menus

Reviewed: 2026-08-15

No protocol-owned combined web panel exists. Management has two explicit sections:

- VXLAN: VNI, VTEP underlay addresses, UDP destination, FDB/learning, bridge membership, MTU/statistics.
- IPsec/IKE: peer identity/authentication/credentials, policies/selectors, proposals, SA state, rekey/liveness/logging.

VNI is not a cryptographic secret. VTEP/link-up does not prove an operational IPsec SA. Accounts/subscriptions/consumer dashboard concepts are N/A unless supplied by a selected product.