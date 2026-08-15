# VXLAN over IPsec — Ports, Transports and Handshake

Reviewed: 2026-08-15

VXLAN uses UDP (RFC/IANA destination 4789; Linux historical default caveat documented in entry 069). VXLAN itself has no authentication/key-exchange handshake.

The security handshake, peer authentication, SA establishment/rekey/liveness and protected data-plane transport belong to IKE/IPsec. Depending on IPsec mode/NAT traversal, the VXLAN UDP packet is carried inside the selected IPsec protection and outer framing.

Do not describe IKE/NAT-T ports as VXLAN ports and do not interpret a VNI/FDB event as an authenticated security session.