# SSTP / MS-SSTP — Deployment Topologies

Review date: 2026-08-14

Entry: 011 SSTP / MS-SSTP.

## 1. Windows native client to Windows RRAS

Primary reference topology:

`Windows 10/11 native VPN client`

`-> TCP 443 / TLS / SSTP`

`-> Windows Server RRAS`

`-> PPP/NPS/RADIUS/user policy`

`-> private networks / Internet egress`

### Required validation

- DNS and server certificate name match;
- native Windows TLS/SSTP negotiation;
- PPP/EAP authentication;
- SSTP crypto binding;
- client address/DNS/routes;
- split/full tunnel;
- firewall/NAT;
- certificate rotation;
- reboot/update/reconnect.

This is the baseline Microsoft-to-Microsoft interoperability case.

## 2. Windows native client to SoftEther SSTP server

`Windows native SSTP`

`-> TLS/TCP443`

`-> SoftEther SSTP compatibility handler`

`-> SoftEther user/Virtual Hub/RADIUS`

`-> bridge/SecureNAT/routed network`

High-value open-source server interoperability target.

Test differences in:

- certificate/TLS behavior;
- SSTP control/crypto binding;
- PPP/auth methods;
- address assignment;
- routing/NAT;
- listener sharing;
- long-lived session behavior.

## 3. Linux sstp-client to Windows RRAS

`Linux route/PPP`

`-> pppd`

`-> sstp-client`

`-> TLS/TCP443`

`-> RRAS`

This tests open-source client compatibility with the authoritative Microsoft server.

Required evidence:

- exact sstp-client source/release;
- TLS/cert validation;
- PPP auth choices;
- crypto-binding behavior;
- routes/DNS;
- proxy support;
- reconnect/cleanup.

## 4. Linux sstp-client to SoftEther

Open-source client/server interoperability target. Do not infer success from either Windows interop case; test exact versions and profiles.

## 5. Client behind restrictive firewall

`client`

`-> firewall allowing TCP443`

`-> SSTP server`

SSTP can be useful where outbound HTTPS/TCP443 is allowed, but the firewall/proxy can still block:

- SSTP's long-lived duplex HTTP request;
- unusual HTTP method/URI;
- idle sessions;
- TLS policy/certificate;
- large/long TCP flows.

“Port 443 open” is not a compatibility guarantee.

## 6. Explicit HTTP proxy traversal

`client`

`-> HTTP proxy / CONNECT`

`-> end-to-end TLS`

`-> SSTP server`

Separate credentials/trust:

- proxy auth;
- SSTP server TLS certificate;
- PPP/EAP user authentication.

A transparent CONNECT proxy is different from a TLS-intercepting proxy. The latter changes the server certificate/channel binding and requires explicit security policy/testing.

## 7. TLS pass-through load balancer

Potential architecture:

`clients -> L4 TCP load balancer -> SSTP servers`

This preserves end-to-end TLS to the SSTP server if the load balancer passes TCP unchanged.

Required proof:

- long-lived TCP idle timeout;
- health checks;
- session persistence/stickiness where needed;
- RRAS/SoftEther backend state;
- client reconnect on backend loss;
- certificate identity remains correct.

Do not infer SSTP support from generic TCP proxy capability without testing.

## 8. TLS termination / reverse proxy

State: `NOT GENERICALLY APPROVED`.

`client TLS -> reverse proxy -> backend SSTP`

can break the intended SSTP TLS/channel/crypto-binding model or the duplex HTTP semantics.

Only support a specific offload architecture if the SSTP implementation/vendor explicitly supports it and packet/security tests prove the binding remains valid.

## 9. High availability RRAS farm

A possible enterprise architecture uses multiple RRAS gateways behind supported network/load-balancing/DNS infrastructure.

Key state considerations:

- TLS certificate identity consistent across nodes;
- NPS/RADIUS/user policy shared;
- address pools/routing coordinated;
- long-lived TCP session sticks to one node;
- no seamless migration assumption for an established SSTP/TCP/TLS/PPP session;
- reconnect/failover timing measured.

## 10. SoftEther HA/multiprotocol gateway

SoftEther deployments may use multiple servers/Virtual Hubs/cascade or external load-balancing designs depending product architecture.

Do not assume RRAS HA semantics or enable all SoftEther protocols for redundancy. Pin the exact topology and minimize exposed listeners.

## 11. Cloud Windows RRAS

`Internet clients`

`-> cloud security group TCP443`

`-> Windows RRAS VM`

`-> VPC/VNet/private networks`

Validate:

- public DNS/certificate;
- cloud firewall/security group;
- source/destination check or forwarding requirements;
- route tables;
- egress NAT;
- Windows firewall/RRAS;
- image patching.

## 12. Cloud SoftEther

Similar public TCP443 topology with SoftEther server. Add persistent config, service privileges, certificate management and cloud networking evidence.

## 13. Containerized SoftEther

`client -> TCP443 service/NAT -> SoftEther container`

Advanced only. Verify:

- image/source digest;
- TLS termination location;
- persistent server config/user database;
- TUN/bridge/network privileges;
- stable routing/address pool;
- restart/session cleanup;
- host firewall.

## 14. IPv6

Test independently:

- server hostname resolves IPv6;
- TCP443/TLS over IPv6;
- SSTP implementation support;
- PPP IPv6/network configuration;
- split/full IPv6 routes;
- DNS;
- firewall;
- mixed IPv4/IPv6 Happy Eyeballs behavior.

No IPv6 certification from IPv4 success.

## 15. TCP-over-TCP lossy path

SSTP can suffer performance degradation when inner TCP flows are nested in the outer SSTP TCP connection.

Lab topology should include controlled:

- packet loss;
- latency;
- jitter;
- bandwidth limitation;
- proxy idle timeout.

Compare throughput, latency, fairness and recovery with other protocols when making product recommendations.

## 16. Migration topology

For organizations using SSTP primarily for Windows/firewall compatibility:

`existing SSTP`

`-> provision preferred modern profile in parallel`

`-> test auth/routes/DNS/reachability`

`-> move default policy`

`-> retain SSTP compatibility fallback only when explicitly required`

`-> retire SSTP listener/profile when usage reaches zero and business policy allows.`

Do not silently downgrade from IKEv2/WireGuard/OpenVPN to SSTP; user/admin policy must define fallback.

## 17. Required topology labs

- Windows client -> Windows Server RRAS;
- Windows client -> SoftEther;
- Linux sstp-client -> RRAS;
- Linux sstp-client -> SoftEther;
- direct Internet and HTTP proxy;
- TLS-intercept negative;
- L4 load balancer;
- certificate rotation;
- server failover/reconnect;
- cloud RRAS/SoftEther if retained;
- IPv6;
- lossy TCP-over-TCP performance;
- split/full tunnel routing/DNS.

All remain external execution gates until actual receipts are committed.
