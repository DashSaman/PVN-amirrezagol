# PPTP — Deployment Topologies

Review date: 2026-08-14

Entry: 012 PPTP.

Every topology in this file is **legacy compatibility only**. New deployments should use a modern approved VPN.

## 1. Windows native client to Windows RRAS

Primary historical interoperability topology:

`Windows PPTP client`

`-> TCP1723 control + GRE protocol47 data`

`-> Windows Server RRAS`

`-> PPP/auth/MPPE`

`-> private network / egress`

Current Windows Server 2025 behavior makes server enablement an explicit legacy exception.

Required proof:

- exact Windows builds;
- TCP1723/GRE;
- PPP auth/MPPE;
- address/routes/DNS;
- direct and NAT path;
- migration/disable lifecycle.

## 2. Windows client to MikroTik RouterOS PPTP server

Current RouterOS capability makes this a practical legacy interop target.

Test:

- exact RouterOS release;
- auth/MPPE requirements;
- Call IDs/GRE;
- client behind NAT;
- address pool/routes;
- vendor warning/migration plan.

## 3. RouterOS client to legacy server

Useful only where an inherited server requires PPTP. Preserve source/vendor security warnings and replacement plan.

## 4. Linux historical client to Windows/RouterOS

`Linux pptp client + pppd`

`-> TCP1723/GRE`

`-> legacy server`

Use only in an isolated compatibility lab after exact client/package/source pinning. Do not create a new production Linux PPTP dependency to expand feature count.

## 5. Linux pptpd server

State: `HISTORICAL LAB ONLY`.

`legacy clients -> pptpd -> pppd -> Linux routing/NAT`

A modern Internet-exposed deployment is not recommended. If needed for migration testing, isolate and destroy/decommission after use.

## 6. Client behind home/enterprise NAT

`client -> NAT/PPTP helper -> Internet -> server`

PPTP helper/ALG tracks TCP1723 call setup and GRE Call IDs.

Test:

- helper enabled/disabled;
- single client;
- multiple clients behind one public IP;
- stale mappings;
- nested NAT/CGNAT;
- reconnect after address change.

## 7. Server behind NAT

Possible only when the NAT/firewall can forward/track:

- TCP1723;
- GRE protocol47;
- associated Call IDs/session state.

Generic TCP port forwarding alone is insufficient.

## 8. Carrier-grade NAT

State: `HIGH INTEROP RISK`.

CGNAT introduces another PPTP helper/stateful layer outside the user's control. Do not promise reliability based on local-router testing.

## 9. Cloud VM

Cloud provider networking must support GRE protocol47 end to end. Many security-group/load-balancer/NAT systems focus on TCP/UDP and may not preserve PPTP GRE session semantics.

Validate provider-specific behavior before claiming compatibility.

## 10. L4 load balancers

Ordinary TCP load balancing only sees the PPTP control channel and does not automatically distribute the associated GRE flow to the same backend.

Do not place a generic TCP1723 load balancer in front of PPTP and assume HA works. A product would need explicit GRE/PPTP call-aware state or direct endpoint routing.

## 11. High availability

PPTP call IDs, GRE state, PPP session/auth/address assignment and NAT helper state are per-session. Seamless failover is not assumed.

Prefer explicit client reconnect during a short migration window rather than engineering a new complex HA system around PPTP.

## 12. IPv6

PPTP is historically IPv4-centric in common deployments. Do not infer IPv6 outer transport or PPP IPv6 support from generic PPP capability. Exact implementation evidence is required; migration to a modern IPv6-capable VPN is preferable.

## 13. Segment-restricted legacy access

If PPTP cannot yet be removed, reduce risk:

`legacy PPTP client`

`-> dedicated legacy gateway`

`-> narrow firewall/ACL`

`-> only required legacy application/subnet`

Avoid broad corporate-network or full-Internet access through an obsolete tunnel when the business requirement is narrower.

## 14. Parallel migration topology

Recommended transition:

1. keep existing PPTP service temporarily;
2. deploy preferred modern protocol in parallel;
3. provision replacement profile to each client/device;
4. test authentication/routes/DNS/application access;
5. switch default/operational workflow;
6. monitor remaining PPTP usage;
7. disable PPTP server/listener;
8. remove TCP1723/GRE/helper exceptions and legacy credentials.

No silent downgrade/fallback from modern profile to PPTP.

## 15. Required topology labs

- Windows -> RRAS;
- Windows -> RouterOS;
- RouterOS client/server combinations retained in scope;
- Linux historical client interop only if required;
- NAT single/multi-client;
- nested/CGNAT where business-critical;
- GRE blocked while TCP1723 succeeds;
- cloud/provider path only if retained;
- segment-restricted access;
- replacement-protocol migration/cutover;
- final listener/firewall/helper removal.
