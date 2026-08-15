# GRE — Server UI and Menus

Reviewed: 2026-08-15

GRE defines no server control plane, users/accounts, certificates, web UI, API, quota system, backup UI, notification system, clustering controller, or protocol-owned administration menu.

## Linux

The canonical management surface is CLI/netlink, principally `ip tunnel`. Relevant fields/actions are: add/change/delete/show tunnel; mode `gre`; local/remote endpoint; TTL/TOS; device binding; PMTUD/DF behavior; GRE key (`key`/`ikey`/`okey`); checksum (`csum`/`icsum`/`ocsum`); sequence flags. iproute2 explicitly warns that its sequence option does not work and should not be used.

## Cisco IOS XE / Junos

Their CLI/configuration trees are vendor UI references, not protocol-defined menus. Cisco documents tunnel interface source/destination/mode/key/keepalive; Juniper documents GRE tunnel-interface configuration and verification with platform-specific constraints.

## N/A conclusion

A consumer-client or server-panel screen-by-screen map is NOT-APPLICABLE to bare GRE. PVNetwork should expose only infrastructure/profile fields that the selected platform adapter can implement, without inventing accounts, cryptographic controls, subscriptions or a GRE server dashboard.
