# VXLAN over IPsec — Client UI and Menus

Reviewed: 2026-08-15

No canonical consumer client UI exists. A future infrastructure editor should visibly separate VXLAN overlay fields (VNI/VTEP/UDP/FDB/bridge/MTU) from IKE/IPsec security fields (identity/authentication/credentials/policy/SA status).

Do not present VNI as a security credential and do not equate VXLAN link state with protected-SA state. Consumer subscription/QR/Store flows are N/A to this infrastructure composition.