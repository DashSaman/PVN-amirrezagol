# DMVPN — Server Installers and Deployment Projects

Reviewed: 2026-08-15

Cisco IOS XE supplies DMVPN as proprietary router-native functionality; install/update/rollback follow IOS XE platform lifecycle.

The public Linux path is a composition of kernel GRE, FRR/nhrpd/routing and strongSwan. There is no canonical standalone DMVPN package or container that replaces these components. FRR and strongSwan distribution/package lifecycle, privileges and licenses remain separate.

Avoid unreviewed all-in-one scripts that silently modify GRE, NHRP, routing, firewall and IKE/IPsec state. Deployment must preserve component ownership and rollback boundaries.