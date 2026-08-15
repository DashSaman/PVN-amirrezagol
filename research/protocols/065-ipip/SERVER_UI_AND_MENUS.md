# IPIP — Server UI and Menus

Reviewed: 2026-08-15

IPIP has no protocol-defined control plane, users, certificates, API or web panel. Linux's authoritative management surface is `ip tunnel`/netlink.

Relevant Linux fields/actions: add/change/delete/show tunnel, `mode ipip`, local/remote endpoint, TTL, TOS/DS field, device binding, PMTU/DF behavior and interface routing/address configuration.

The following concepts are evidence-backed NOT-APPLICABLE to bare IPIP: accounts, MFA, certificates, cryptographic suite selection, subscription management, quotas, protocol-owned backup/restore, notifications or clustering UI.

A future PVNetwork infrastructure editor must warn that IPIP itself provides no encryption/authentication and should expose only platform-supported tunnel/routing fields.
