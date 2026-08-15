# VTI/IPsec — Server Installers and Deployment Projects

Reviewed: 2026-08-15

VTI is supplied by the Linux kernel and configured through iproute2; IKE/IPsec lifecycle comes from the selected IKE daemon (e.g. strongSwan). There is no standalone canonical VTI/IPsec installer, container or panel.

The strongSwan-family V2 dossier already reviews official/community packages, privileges, configuration ownership, update/uninstall/rollback and supply-chain concerns for the IPsec/IKE layer. VTI adds privileged interface/routing/mark configuration, but no independent package lifecycle.

Prefer native distribution kernel/iproute2 and reviewed IKE packages. Avoid unreviewed privileged one-click scripts.

Evidence: pinned Linux `ip_vti.c`; pinned iproute2; official strongSwan route-based VPN documentation; shared strongSwan-family V2 installer dossier.
