# L2TPv3 — Endpoint Installation Matrix

Review date: 2026-08-14

Entry: 009 L2TPv3.

“Server” here means a pseudowire endpoint/PE/LCCE role. The protocol is normally symmetric infrastructure transport rather than a consumer VPN service.

## 1. Linux — Debian / Ubuntu

State: `REFERENCE-PATH / NEEDS-LAB`.

Preferred components:

- distro kernel with L2TP modules;
- distro iproute2;
- optional pinned go-l2tp/ql2tpd;
- Linux bridge/VLAN/network namespace tools.

Current go-l2tp documentation notes some Ubuntu kernels require an extra `linux-modules-extra-$(uname -r)` package to obtain the full L2TP module set.

Execution receipt must record exact kernel/config/modules and iproute2 version rather than generic distro name.

## 2. Linux — Fedora / RHEL family

State: `REFERENCE-PATH / NEEDS-LAB`.

Use distro kernel/iproute2 and verify the selected kernel package exposes L2TP generic-netlink and Ethernet/IP modules. Exact package/module split and SELinux/firewalld policy require per-release evidence.

## 3. Linux — Arch / rolling

State: `REFERENCE-PATH / NEEDS-LAB`.

Kernel/iproute2 move quickly. Pin a tested kernel + iproute2 pair in certification evidence; do not infer behavior from moving main branches.

## 4. Linux — Alpine

State: `REFERENCE-PATH / ADVANCED / NEEDS-LAB`.

Useful for minimal infrastructure, but kernel module packaging and container/host-kernel ownership must be explicit.

## 5. Linux — router/distribution appliances

State: `PRODUCT-SPECIFIC / NEEDS-LAB`.

OpenWrt or other Linux network OSes may expose kernel L2TP features depending image/packages. No generic support claim until exact firmware/package evidence is recorded.

## 6. Cisco IOS XE

State: `BUILT-IN FEATURE / NEEDS-LAB`.

Current official Cisco IOS XE documentation shows L2TPv3 configuration on supported platforms/releases through pseudowire/xconnect commands.

Install means selecting/upgrading a supported IOS XE image/feature set, not installing a Linux package.

Required matrix:

- hardware/platform;
- IOS XE exact release;
- attachment circuit type;
- static vs signaled control;
- direct IP/UDP availability if relevant;
- cookie/sequence/PMTU options;
- reload/upgrade/rollback behavior.

## 7. Other proprietary router OS

State: `UNREVIEWED PER PRODUCT`.

Add only after current official docs identify L2TPv3 specifically. Generic L2VPN/pseudowire capability is not enough.

## 8. Container / OCI

State: `ADVANCED / NO GENERIC IMAGE APPROVED`.

A container uses host kernel L2TP. Required proof:

- kernel modules on host;
- CAP_NET_ADMIN/privilege;
- network namespace ownership;
- bridge/VLAN attachment;
- direct IP protocol 115 or UDP exposure;
- persistent endpoint IP;
- crash cleanup.

## 9. Kubernetes

State: `ADVANCED / NO GENERIC PATH APPROVED`.

Likely model is a node-bound network function/DaemonSet-like endpoint rather than an ordinary movable application pod.

Verify node affinity, netns, CNI, privileges, underlay reachability, config persistence and cleanup before any claim.

## 10. FreeBSD / BSD

State: `UNVERIFIED`.

Do not infer L2TPv3 pseudowire parity from generic BSD L2TP/IPsec or IPsec support. Exact kernel/userland implementation and current release documentation are required.

## 11. Windows / macOS / Android / iOS

State: `NOT-NORMAL-ENTRY-009-ENDPOINT PATH`.

These platforms' consumer L2TP VPN support refers mainly to L2TPv2/L2TP-IPsec and must not be confused with L2TPv3 infrastructure pseudowires.

If a specialized third-party L2TPv3 implementation exists on one of these platforms, review it separately by source/product/version; do not infer native support.

## 12. Strict execution table

| Endpoint target | Implementation | Install/feature present | Static PW | Dynamic/signaled | Ethernet frames | VLAN | Restart cleanup | Upgrade/rollback |
|---|---|---:|---:|---:|---:|---:|---:|---:|
| Ubuntu/Debian | kernel + iproute2 | TODO | TODO | N/A with iproute2 | TODO | TODO | TODO | TODO |
| Fedora/RHEL | kernel + iproute2 | TODO | TODO | N/A with iproute2 | TODO | TODO | TODO | TODO |
| Linux | ql2tpd + kernel | TODO | TODO | limited HELLO only | TODO | TODO | TODO | TODO |
| Cisco IOS XE selected platform | built-in | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux-to-Cisco | mixed | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| selected OCI | host kernel + image | TODO | TODO | depends | TODO | TODO | TODO | TODO |

All TODO values are external execution gates.
