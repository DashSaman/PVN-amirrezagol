# Ivanti Connect Secure — Server / Admin UI Map

Reviewed: 2026-08-14 UTC

Current Ivanti administration documentation maps the applicable control surfaces:

- `Users > User Roles` — session role, enabled access features and role restrictions;
- role access feature `VPN Tunneling` — secure SSL-based network-level remote access;
- `Users > User Roles > <Role> > VPN Tunneling` — split tunnel, auto-launch/auto-uninstall and client role behavior;
- `Users > Resource Policies > VPN Tunneling > Access Control` — permitted IPv4/IPv6/FQDN/tcp/udp/icmp resources;
- `... > Connection Profiles` — address assignment and transport/encryption session policy;
- `... > Split Tunneling` — tunnel route policy;
- authentication realms/servers and role-mapping rules;
- Host Checker restrictions as a distinct posture control;
- system network/VPN tunneling settings, logging/monitoring and maintenance/installers;
- separate IKEv2 configuration is a different access capability and must not be folded into Pulse-compatible tunneling.

Authoritative current admin references:
- https://help.ivanti.com/ps/help/en_US/ICS/25.1.x/ag/user_roles.htm
- https://help.ivanti.com/ps/help/en_US/ICS/vNow/vtcg/configuring-vpn-tunneling.htm

No public source-backed ICS web-panel implementation is claimed; this is a vendor UI/behavior map.
