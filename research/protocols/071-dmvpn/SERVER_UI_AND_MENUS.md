# DMVPN — Server UI and Menus

Reviewed: 2026-08-15

DMVPN has no protocol-defined web panel. Canonical administration is infrastructure CLI/configuration.

Relevant surfaces: hub/spoke role, mGRE tunnel, NHRP network/NHS/cache/map/redirect/shortcut, routing protocol/prefix state, IKE/IPsec peer/SA security state, tunnel/NHRP statistics and diagnostics. Cisco exposes `show dmvpn`, NHRP/tunnel/crypto views; FRR exposes NHRP/DMVPN VTY/JSON-capable show commands.

A PVNetwork admin UI should preserve these component states independently. Consumer login/subscription/QR/Store UI is N/A.