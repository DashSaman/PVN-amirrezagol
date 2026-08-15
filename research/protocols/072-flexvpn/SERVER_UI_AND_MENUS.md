# Cisco FlexVPN — Server UI and Menus

Reviewed: 2026-08-15

Canonical administration is IOS XE configuration/operational state rather than a protocol-defined web UI.

Evidence-backed conceptual surfaces: IKEv2 profile, identity/authentication, authorization policy/AAA/RADIUS, IPsec profile, virtual-template/tunnel interface, address/route/configuration attributes, session/SA status and troubleshooting. Cisco-specific CFG attributes must remain extension fields over generic IKEv2/IPsec.

PVNetwork must use its own UI and branding. Generic client/server controls are inherited from the IKEv2/IPsec layer; Cisco-specific menus/commands are behavior references, not reusable UI assets.