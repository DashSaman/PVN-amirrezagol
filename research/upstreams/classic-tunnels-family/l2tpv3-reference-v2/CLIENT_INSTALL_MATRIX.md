# L2TPv3 — Peer Installation Matrix

Review date: 2026-08-14

Entry: 009 L2TPv3.

The v2 contract calls this a client install matrix; for L2TPv3 the correct semantic unit is a **peer/pseudowire endpoint**. Do not invent consumer mobile clients for a network-infrastructure protocol.

## 1. Linux peer — kernel + iproute2

State: `PRIMARY OPEN-SOURCE PEER / NEEDS-LAB`.

Install/provision:

- selected distro/kernel with L2TP modules;
- iproute2;
- root/CAP_NET_ADMIN control;
- static `ip l2tp` tunnel/session;
- bridge/VLAN/netdevice integration.

Relevant kernel modules are documented by go-l2tp testing as `l2tp_core`, `l2tp_netlink`, `l2tp_eth`, `l2tp_ip`, `l2tp_ip6` for the supported Linux data-plane modes.

## 2. Linux peer — ql2tpd

State: `OPEN-SOURCE ORCHESTRATED STATIC PEER / NEEDS-LAB`.

Pinned source:

- `katalix/go-l2tp@0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- MIT.

Install a pinned Go build/package, configure tunnels/sessions and optionally HELLO timeout when both sides are compatible ql2tpd peers.

Do not treat optional HELLO as a complete generic signaled client.

## 3. Cisco IOS XE peer

State: `PROPRIETARY NETWORK-OS PEER / NEEDS-LAB`.

No client package is installed. Select a supported router/switch platform and IOS XE image, then configure L2TPv3 pseudowire/xconnect through supported device management.

Exact feature/platform licensing and software lifecycle belong to the device vendor.

## 4. Linux-to-Cisco heterogeneous pair

State: `HIGH-VALUE INTEROP TARGET / NEEDS-LAB`.

For static/manual mode, both sides must match:

- underlay endpoint addresses;
- local/remote tunnel/session IDs as applicable;
- cookies;
- encapsulation;
- pseudowire type;
- attachment-circuit framing/MTU.

For signaled mode, a Linux full RFC3931 control-plane implementation must be selected and tested; static iproute2 alone is insufficient.

## 5. Other router/network OS peers

State: `PER-VENDOR UNVERIFIED`.

Add only with exact current product/software documentation naming L2TPv3. Do not map MPLS/EVPN/VXLAN pseudowires into entry 009.

## 6. Virtualized Linux router/VM

State: `REFERENCE-PATH / NEEDS-LAB`.

A Linux VM can be a strong pseudowire endpoint when its virtual NIC/bridge/MTU and underlay routing are controlled. This is preferable to over-privileged application containers for many deployments.

Record hypervisor NIC offload, VLAN trunking and MTU effects.

## 7. OCI container peer

State: `ADVANCED / NEEDS-LAB`.

Not a standalone protocol engine: it still uses the host kernel. Verify namespace and CAP_NET_ADMIN requirements, module availability, bridge visibility and persistent endpoint addressing.

## 8. Kubernetes node peer

State: `ADVANCED / NO GENERIC SUPPORT CLAIM`.

If used, treat as node networking infrastructure. Avoid user-app pod semantics. Record node pinning, CNI, hostNetwork/netns, module/capability ownership and cleanup.

## 9. Windows client

State: `NO NATIVE CONSUMER L2TPv3 CLAIM`.

Windows built-in L2TP VPN features concern L2TPv2/IPsec remote access, not generic L2TPv3 Ethernet pseudowires. Do not reuse the entry-008 Windows matrix here.

Any specialized Windows L2TPv3 software must be reviewed as a separate product if discovered/required.

## 10. Apple platforms

State: `NO NATIVE L2TPv3 PSEUDOWIRE CLAIM`.

Apple native L2TP deployment/profile support is not evidence of L2TPv3 pseudowire support.

## 11. Android / Android TV

State: `NO NATIVE L2TPv3 PSEUDOWIRE CLAIM`.

Do not add entry 009 to the consumer PVNetwork mobile protocol picker based on Android legacy L2TP support.

## 12. FreeBSD/BSD peer

State: `UNVERIFIED`.

Requires exact current kernel/userland L2TPv3 implementation evidence. Do not infer from L2TPv2 or IPsec support.

## 13. Provision/update/remove matrix

| Peer target | Backend | Provision | Forward frames | Restart recovery | Upgrade | Rollback | Remove/cleanup |
|---|---|---:|---:|---:|---:|---:|---:|
| Linux static | kernel + iproute2 | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux ql2tpd | go-l2tp + kernel | TODO | TODO | TODO | TODO | TODO | TODO |
| Cisco IOS XE | built-in network OS | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux -> Cisco | heterogeneous | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux VM | kernel + iproute2 | TODO | TODO | TODO | TODO | TODO | TODO |
| selected OCI | host kernel + tooling | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO values are external execution gates.

## 14. Product installation implication

PVNetwork consumer client installers should **not** bundle L2TPv3 by default. If PVNetwork gains an infrastructure/network-admin edition, install/configure L2TPv3 adapters only on platforms where the peer role is meaningful and explicitly enabled.
