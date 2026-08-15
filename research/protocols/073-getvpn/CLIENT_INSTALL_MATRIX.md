# Cisco GETVPN — Client / Group-Member Install Matrix

Reviewed: 2026-08-15

GETVPN does not use consumer VPN clients. The endpoint role is a Cisco group member (GM) that protects traffic originating on or passing through the device.

Supported Cisco router/software combinations are governed by current IOS/IOS XE feature documentation. Key servers and group members may have different GKM-version/feature capabilities; current 2026 Cisco G-IKEv2 documentation lists GKM version requirements and specific restrictions.

Generic IKEv2/IPsec clients on Windows/macOS/iOS/Android/Linux are NOT GETVPN clients and must not be advertised as such. Generic IPsec capability does not provide group registration/rekey/group-SA semantics.