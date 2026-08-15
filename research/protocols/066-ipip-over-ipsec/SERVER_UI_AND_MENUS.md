# IPIP over IPsec — Server UI and Menus

Reviewed: 2026-08-15

No protocol-owned web panel exists for this composition. Management is the combination of:

- IPIP endpoint/interface/routing configuration from entry 065; and
- IKE/IPsec peer identity, authentication/credentials, proposals, CHILD_SA/traffic selectors, SA status/logging and lifecycle from the strongSwan-family V2 reference layer.

A product UI must keep those sections distinct: outer/inner tunnel endpoint and route fields belong to IPIP; cryptographic identity/policy belongs to IPsec/IKE. Accounts, subscriptions, quotas and generic consumer-server dashboards are NOT-APPLICABLE unless supplied by a selected implementation.
