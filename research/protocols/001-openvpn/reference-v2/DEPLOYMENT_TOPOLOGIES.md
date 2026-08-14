# OpenVPN — Deployment Topologies

Reference layer: `COMPLETE-REFERENCE-v2`

Research date: 2026-08-14

State: `IN-RESEARCH` — topology classes are documented; actual lab designs need exact IP plans/firewall/HA products.

## 1. Remote-access full tunnel

```text
Laptop / Phone
      |
      | OpenVPN tunnel
      v
OpenVPN Server / Gateway
      |
      +--> Private networks
      |
      +--> NAT/routed Internet egress
```

Use when all client traffic must traverse the VPN.

Required decisions:

- VPN client pool;
- server return routes;
- NAT vs routed egress;
- DNS resolver path;
- IPv4/IPv6;
- kill switch;
- local LAN access policy;
- server underlay/external firewall.

## 2. Remote-access split tunnel

```text
Client
  +--> private prefixes via OpenVPN
  +--> other traffic directly
```

Server may push private routes, while product/admin policy decides whether to accept/override them.

Risk areas:

- DNS split routing;
- overlapping home/corporate subnets;
- local bypass;
- routes changing after server update;
- domain-based policy vs IP routes.

## 3. Site-to-site routed VPN

```text
Site A LAN
   |
Gateway A / OpenVPN client
   |
   | encrypted routed tunnel
   v
OpenVPN server/gateway
   |
Site B LAN
```

Can be hub-and-spoke or point-to-point-like using OpenVPN server/client routing.

Requirements:

- non-overlapping prefixes or explicit translation;
- return routes;
- client-specific route ownership where server needs to know which client reaches which subnet;
- forwarding/firewall rules;
- dynamic route integration only through carefully designed external tooling.

## 4. Hub-and-spoke multi-site

```text
           Site A
             |
             v
Site B -> OpenVPN Hub <- Site C
             ^
             |
          Remote users
```

Server/hub handles routing among spokes according to policy.

PVNetwork server manager should model spokes/subnets separately from individual user devices.

## 5. High-availability server pair / cluster-like deployment

Community OpenVPN itself does not magically create a state-synchronized HA cluster.

HA can be built with external components such as:

- load balancers;
- DNS failover;
- floating/virtual IP;
- replicated PKI/configuration;
- shared/replicated authentication backend;
- multiple remote endpoints in client profile.

Access Server and third-party control planes may provide product-specific HA/clustering features; document those by exact version rather than assigning them to Community Server generically.

Client requirements:

- ordered multiple remotes;
- retry/backoff;
- certificate/name compatibility;
- consistent routes/DNS/auth policy;
- session continuity expectations clearly stated.

## 6. Multiple regions / geo endpoints

```text
Client profile
  ├─ DE endpoint
  ├─ FI endpoint
  └─ US endpoint
```

Use one profile/group or separate profiles depending on server/config semantics.

PVNetwork can provide health/latency selection only when credentials/config are genuinely interchangeable and server policy permits it.

Do not automatically combine unrelated `.ovpn` profiles into a load-balancing group.

## 7. Access Server enterprise deployment

Conceptual components:

```text
Users / Identity Provider
       |
       v
OpenVPN Access Server
  ├─ Admin Web UI
  ├─ Client Web UI / provisioning
  ├─ VPN listener(s)
  ├─ authentication integration
  └─ routed/NAT private networks
```

Security requires separate exposure policy for:

- VPN listener;
- Admin UI;
- Client UI;
- identity provider connectivity;
- database/config/backup state.

## 8. Containerized Community Server

```text
Linux Host
  ├─ kernel TUN / networking
  ├─ firewall/NAT
  └─ OpenVPN container
       ├─ server config
       └─ PKI volume
```

Container is not an isolation substitute for host network security.

Audit:

- TUN device access;
- Linux capabilities;
- published port;
- host vs container firewall;
- persistent PKI/config volume;
- image provenance;
- secret backup;
- restart/update behavior.

## 9. Cloud VM gateway

```text
Internet
   |
Cloud firewall / security group
   |
OpenVPN VM
   |
VPC/VNet routes
   |
Private subnets
```

Cloud-specific requirements:

- source/destination check or forwarding settings where applicable;
- cloud route tables;
- security groups/firewalls;
- public IP/DNS;
- HA/floating IP strategy;
- secrets/PKI storage;
- image snapshot/backup policy.

PVNetwork automation must not assume host iptables alone controls cloud routing.

## 10. Private network only / no Internet egress

Remote users access only defined internal services.

Prefer explicit private routes and no default-route push.

Good for:

- administration networks;
- internal applications;
- database/dev environments;
- zero-trust-adjacent restricted VPN segments.

Apply firewall least privilege even after VPN authentication.

## 11. Per-user/per-device restricted access

Server can combine:

- unique client certificates;
- user identity/authentication;
- client-specific route/config;
- firewall policy external to OpenVPN;
- group/user policy in Access Server/control planes.

PVNetwork should not equate “connected” with “authorized to all internal networks”.

## 12. TAP / Layer-2 bridge

```text
Remote TAP
   |
OpenVPN Ethernet frames
   |
Server TAP -- Bridge -- LAN
```

Use only where Layer-2 semantics are genuinely needed.

Risks/costs:

- broadcast/multicast propagation;
- bridge loops;
- larger traffic volume;
- difficult mobile support;
- DHCP/security domain extension;
- increased lateral-movement exposure.

Classify Advanced/Legacy-compatible.

## 13. OpenVPN behind NAT

Server sits behind an upstream router/firewall.

Requirements:

- port-forward configured listener;
- stable public endpoint/DDNS where needed;
- return routing;
- double-NAT/CGNAT limitations;
- admin UI not automatically exposed with tunnel port.

## 14. OpenVPN through TCP/HTTPS-like perimeter

OpenVPN TCP can be placed on a chosen TCP port, potentially behind/alongside certain proxy/load-balancer designs only when protocol handling supports it.

Do not assume a normal HTTP reverse proxy can transparently proxy raw OpenVPN TCP just because the port is 443.

Use protocol-aware or raw TCP/L4 load balancing when appropriate.

## 15. OpenVPN with external authentication / IdP

```text
Client
  -> OpenVPN control TLS
  -> Server auth hook/product
       -> LDAP/RADIUS/SAML/IdP/MFA
```

Exact flow depends on Community plugins/scripts, Access Server or third-party product.

Security requirements:

- IdP TLS validation;
- timeout/failure isolation;
- MFA challenge state;
- least-privilege service credentials;
- no auth secrets in OpenVPN logs;
- define behavior when IdP unavailable.

## 16. External/offline PKI

Runtime VPN server holds only necessary server cert/key and CA/public revocation material, while CA signing key stays offline or in external PKI/HSM.

Preferred for higher-security deployments.

PVNetwork server management should support:

- CSR generation;
- external signing/import;
- certificate rotation;
- CRL/revocation sync;
- no assumption that local Easy-RSA owns CA private key.

## 17. Multi-hop / chained VPN

OpenVPN can technically be part of a chained routing design, but multi-hop is a **product routing graph**, not a native one-click OpenVPN protocol feature.

PVNetwork must explicitly model:

- first hop;
- second hop;
- route to next-hop endpoint through prior tunnel;
- DNS policy;
- failure cleanup;
- MTU/performance;
- leak behavior.

Never create recursive routing loops.

## 18. Split by application

On platforms supporting per-app VPN, selected apps can use the OpenVPN tunnel while others remain direct.

This is a platform VPN/routing capability, not a server-side OpenVPN feature.

Test Android/iOS/macOS/Windows/Linux separately; do not promise parity.

## 19. Management plane separation

Recommended production design:

```text
Public/user network
   -> VPN listener

Restricted admin network
   -> Admin UI / SSH / management API
```

Do not expose privileged server-management interfaces to the same broad network simply because the VPN listener must be public.

## 20. Topology record schema

For every real deployment reference record:

```text
Topology ID:
Server product/version:
Server OS/arch:
Client types:
Underlay public/private:
VPN transport/port:
VPN address pools:
Private subnets:
Full/split:
NAT/routed:
IPv6:
DNS path:
Identity/auth:
PKI location:
Admin interface exposure:
HA/failover:
Firewall zones:
Logging/SIEM:
Backup/restore:
PVNetwork client versions tested:
Known limitations:
```

## Remaining v2 gaps

- exact Access Server HA/clustering capabilities by current version;
- reference cloud provider route/firewall examples;
- Kubernetes/site-to-site lab designs;
- measured MTU/performance by topology;
- packet captures and failure-mode diagrams.
