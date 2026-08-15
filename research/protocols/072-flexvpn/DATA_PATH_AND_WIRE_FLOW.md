# Cisco FlexVPN — Data Path and Wire Flow

Reviewed: 2026-08-15

At architecture level: IKEv2 negotiates/authenticates peer and SAs -> authorization/configuration mode may supply address/routes/policy attributes -> IPsec protects the data plane -> tunnel/virtual-template/routing state forwards traffic. Exact role/topology changes how virtual access/tunnel interfaces and routes are instantiated.

Cisco-specific CFG_REQUEST/CFG_REPLY/CFG_SET/CFG_ACK and authorization/AAA behavior belong to the framework/profile layer; ESP/IPsec remains the protected data plane.

Generic IKEv2 success alone is not proof that all FlexVPN authorization, route, reconnect or Cisco attribute behavior succeeded. Diagnostics must preserve those states separately.