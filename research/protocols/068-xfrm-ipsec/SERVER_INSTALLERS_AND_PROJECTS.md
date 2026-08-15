# XFRM/IPsec — Server Installers and Deployment Projects

Reviewed: 2026-08-15

XFRM interfaces are provided by Linux kernel/iproute2 and used with an existing IKE/IPsec stack. There is no canonical standalone XFRM-interface server installer.

- Kernel requirement: Linux 4.19+ per current strongSwan documentation.
- iproute2 requirement: 5.1.0+ for native XFRM interface creation; strongSwan also provides `xfrmi` for older iproute2 environments.
- IKE/IPsec installation, package, source, privilege, supply-chain and lifecycle evidence is already audited in `research/upstreams/strongswan-family/reference-v2/`.

Deployment requires privileged network/interface/routing/policy administration. Prefer native distribution kernel/iproute2 and reviewed IKE packages; no unreviewed one-click installer is justified.

Rollback is deletion/reversion of XFRM interfaces/routes and the corresponding IKE/IPsec configuration; package rollback follows the selected OS/IKE component lifecycle.
