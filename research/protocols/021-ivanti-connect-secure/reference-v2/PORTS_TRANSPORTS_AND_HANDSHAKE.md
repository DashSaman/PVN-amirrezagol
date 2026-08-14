# Ivanti Connect Secure — Ports / Transports / Handshake

Reviewed: 2026-08-14 UTC

Pulse-compatible VPN tunneling uses the appliance HTTPS/TLS service for login/control and IF-T/TLS tunnel transport; **TCP 443** is the normal HTTPS exposure unless the deployment deliberately changes the service path.

Public Pulse wire evidence:

- IF-T/TLS over TCP;
- EAP/EAP-TTLS authentication behavior;
- ESP accelerated data transport;
- TLS-compatible fallback/control path.

Reference: https://www.infradead.org/openconnect/pulse.html

No undocumented fixed UDP port is fabricated for the ESP path. Exact NAT/firewall behavior remains gateway/release/topology-specific.

Three separate protocol families must remain distinct:

- `pulse` — current Pulse/ICS-compatible IF-T/TLS family;
- `nc` — older Juniper Network Connect/oNCP family, entry 022;
- ICS `IKEv2` — separate standards-based access capability.
