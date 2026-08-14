# Juniper Network Connect — Ports / Transports / Handshake

Reviewed: 2026-08-14 UTC

- authentication/control: HTTPS, normally TCP 443 on SSL VPN deployments unless the gateway is configured otherwise;
- session handoff: authenticated HTTP cookie (commonly DSID in legacy deployments) is reused for the VPN connection;
- TCP tunnel/data path: HTTP(S)/oNCP over the authenticated session;
- accelerated UDP data path: UDP-encapsulated ESP according to OpenConnect's NC implementation/gateway negotiation;
- no undocumented fixed UDP port is invented here;
- legacy NC is distinct from Pulse IF-T/TLS and from standards-based IKEv2.

OpenConnect v9.21 remains the current source/activity authority for the reusable client path; exact old gateway/NAT behavior is appliance-version specific.
