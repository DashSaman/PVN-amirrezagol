# Agent Checkpoint — 2026-08-14 — L2TPv3/IPsec v2 slice 1

Work unit: `L2TPV3-IPSEC-COMPLETE-REFERENCE-V2`

Entry: 010 L2TPv3/IPsec

State transition: source/reference layer complete; strict v2 promotion remains `BLOCKED_EXTERNAL`.

## Completed

- all 11 mandatory v2 dossier files under `research/upstreams/classic-tunnels-family/l2tpv3-ipsec-reference-v2/`;
- entry-009 Linux kernel/iproute2/go-l2tp pseudowire evidence reused without duplication;
- entries 004–007 strongSwan/Libreswan/XFRM IPsec evidence reused without flattening the protocol layers;
- direct protocol-115 IPsec selector and UDP-selector composition documented from RFC3931/RFC3193 boundary;
- `FLOW-SELECTIVE-IPSEC` vs `PROTECTED-UNDERLAY-IPSEC` modeled separately;
- protected startup/order, rekey, no-clear-fallback and cleanup rules documented;
- peer/server install and UI matrices created for Linux/vendor/VM/container/network-function roles;
- Layer-2 bridge/VLAN/STP/MTU residual risks preserved after encryption;
- all 16 v2 reference categories reconciled;
- final `REFERENCE_INDEX.md` synchronized;
- `AGENTS_HANDOFF_2026-08-14_L2TPV3_IPSEC_V2_1.md` created.

## Checks

- 11 mandatory v2 file categories: PASS at source/reference layer.
- 16 `FULL_PROTOCOL_REFERENCE_CONTRACT.md` categories: PASS at source/reference layer.
- strict `COMPLETE-REFERENCE-v2`: NOT PASS because external execution evidence is missing.
- implementation/production support: NOT CLAIMED.

## External blockers

- real strongSwan and Libreswan selector/runtime proof;
- UDP static/dynamic protected flow;
- protected-underlay no-clear-fallback proof;
- forced IPsec loss/rekey during active pseudowire;
- packet captures showing only ESP/NAT-T on untrusted interface;
- combined MTU/PMTU/ECN;
- Layer-2 VLAN/STP/broadcast behavior;
- exact Cisco protected interop;
- IPv6;
- lifecycle cleanup;
- OCI/Kubernetes fail-safe proof if retained.

## Active task after checkpoint

Unless a newer concurrent Run State supersedes it, continue entry 011 SSTP/MS-SSTP under:

`SSTP-MS-SSTP-COMPLETE-REFERENCE-V2`

Exact resume action: read entry-011 v1 and existing SSTP/SoftEther/Windows evidence, then create the separate v2 reference set without confusing TLS transport, SSTP framing and PPP/authentication layers.
