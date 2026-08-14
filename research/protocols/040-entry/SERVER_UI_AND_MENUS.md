# 040 Shadowsocks — server UI/menu maps

Reviewed: 2026-08-15

Dedicated `shadowsocks-rust` is CLI/config/service oriented, not a first-party web panel. Primary server controls are config/CLI fields for server address/port, password or key, exact method, TCP/UDP mode, plugin/plugin options, manager/service/logging/network/runtime options. A canonical first-party web menu is therefore N/A.

3X-UI is a selected separate panel reference: Dashboard -> Inbounds -> Add/Edit Inbound -> Shadowsocks -> listener/client/method/password -> limits/expiry where panel supports them -> routing/outbounds -> subscriptions/nodes/settings. Panel labels/data model are version-specific and not Shadowsocks wire semantics.

`ssmanager`/manager API belongs to the dedicated implementation control plane; it must not be flattened into protocol framing.
