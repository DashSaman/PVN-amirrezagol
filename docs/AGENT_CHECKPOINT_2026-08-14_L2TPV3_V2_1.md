# Agent Checkpoint — 2026-08-14 — L2TPv3 v2 slice 1

Work unit: `L2TPV3-COMPLETE-REFERENCE-V2`

Entry: 009 L2TPv3

State transition: source/reference layer complete; strict v2 promotion remains `BLOCKED_EXTERNAL`.

## Completed

- all 11 mandatory v2 dossier files under `research/upstreams/classic-tunnels-family/l2tpv3-reference-v2/`;
- Linux kernel/iproute2/go-l2tp source pins and licenses;
- current Cisco IOS XE pseudowire interoperability/control model;
- direct-IP protocol 115 vs UDP/1701 transport separation;
- static vs dynamic control distinction;
- Cookie/control-auth security boundary and plain-data no-confidentiality rule;
- kernel/netlink/Ethernet pseudowire data path;
- Linux/Cisco/VM/container peer install matrices;
- infrastructure peer/operator UI maps rather than fake consumer client UI;
- Layer-2/VLAN/STP/MTU/HA/topology risks;
- all 16 `FULL_PROTOCOL_REFERENCE_CONTRACT.md` research/reference categories reconciled;
- final `REFERENCE_INDEX.md` synchronized;
- `AGENTS_HANDOFF_2026-08-14_L2TPV3_V2_1.md` created.

## Checks

- mandatory v2 file categories: PASS at source/reference level;
- 16 reference contract categories: PASS at source/reference level;
- strict `COMPLETE-REFERENCE-v2`: NOT PASS because runtime/interoperability evidence is missing;
- product implementation/support: NOT CLAIMED.

## External blockers

- real Linux kernel/iproute2 lifecycle and frame forwarding;
- direct-IP and UDP pseudowires;
- VLAN/STP/multicast/broadcast behavior;
- cookie/sequence/reorder/MTU/ECN negatives;
- ql2tpd runtime;
- Linux-to-Cisco exact-version static and dynamic interop;
- Cisco upgrade/rollback/show/debug evidence;
- container/Kubernetes netns/capability proof;
- packet capture proving plain L2TPv3 lacks confidentiality;
- entry 010 protected composition lab.

## Active task after checkpoint

`L2TPV3-IPSEC-COMPLETE-REFERENCE-V2`

Entry 010.

Exact resume action: reuse entry 009 pseudowire reference plus entries 004–007 IPsec reference; build the separate L2TPv3/IPsec composition dossier, including selectors/protection order/credential ownership/install/UI/topologies and protected-path interoperability evidence. Do not mark entry 009 strict complete until the external receipts exist.
