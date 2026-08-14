# Cisco AnyConnect — Data Path / Wire Flow

Review date: 2026-08-14 UTC

OpenConnect's public protocol behavior and Cisco headend documentation support this layered model:

1. client establishes HTTPS/TLS to headend;
2. authentication may involve forms, certificates, MFA/token or browser/SSO according to policy;
3. authenticated session/cookie state is established;
4. client opens the CSTP VPN tunnel over TLS/HTTPS;
5. server assigns/pushes network parameters such as address, routes/split policy and DNS;
6. IP packets flow through the virtual tunnel interface and CSTP;
7. when enabled and reachable, a parallel DTLS UDP tunnel carries data; TLS remains control/fallback path;
8. DPD/reconnect/session policy governs failover/re-establishment.

Cisco ASA documentation states DTLS and SSL tunnels can coexist and DPD is required for DTLS fallback to TLS. A successful TLS login does not prove DTLS, posture or all enterprise modules.
