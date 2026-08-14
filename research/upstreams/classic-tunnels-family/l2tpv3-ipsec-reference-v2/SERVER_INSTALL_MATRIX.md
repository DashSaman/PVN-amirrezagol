# L2TPv3/IPsec — Protected Endpoint Installation Matrix

Review date: 2026-08-14

Entry: 010 L2TPv3/IPsec.

## Status vocabulary

- `REFERENCE-PATH`: documented/source-backed composition exists.
- `NEEDS-LAB`: no execution receipt in this agent environment.
- `EXACT-VENDOR-PROOF`: both features may exist but protected composition needs exact product/version evidence.
- `NO-GENERIC-PATH`: no broadly safe deployment is approved.

## 1. Debian / Ubuntu Linux

State: `REFERENCE-PATH / NEEDS-LAB`.

Components:

- Linux kernel XFRM + L2TP modules;
- iproute2;
- strongSwan modern swanctl/VICI package path as primary candidate;
- optional Libreswan alternative;
- optional ql2tpd for L2TPv3 orchestration;
- bridge/VLAN/network namespace tools.

Required lab:

- exact OS/kernel/module package;
- IKE engine/plugin/provider versions;
- direct protocol-115 selector;
- UDP selector case;
- startup ordering;
- protected forwarding;
- rekey;
- no clear fallback;
- upgrade/rollback/uninstall cleanup.

## 2. Fedora / RHEL family

State: `REFERENCE-PATH / NEEDS-LAB`.

Libreswan is an important native-family IPsec reference; strongSwan may also be selected depending repositories/product policy. Linux kernel/iproute2 owns L2TPv3.

Verify SELinux, firewalld/nftables, kernel module package and service ordering.

## 3. Arch / rolling Linux

State: `REFERENCE-PATH / NEEDS-LAB`.

Pin tested kernel + iproute2 + IKE-engine versions. Rolling updates make cross-version protection/restart tests especially important.

## 4. Alpine Linux

State: `ADVANCED / NEEDS-LAB`.

Possible for dedicated infrastructure, but verify kernel modules, musl/build compatibility, init/service ownership and security-provider capability. Do not infer container safety from Alpine availability.

## 5. Linux VM/network appliance

State: `PREFERRED INFRASTRUCTURE FORM / NEEDS-LAB`.

A dedicated VM with controlled NICs/MTU/VRF is often simpler and safer than a privileged container because kernel XFRM/L2TP and attachment interfaces are locally owned.

## 6. Cisco IOS XE

State: `EXACT-VENDOR-PROOF / NEEDS-LAB`.

Current Cisco evidence proves L2TPv3 pseudowire capability, and IOS XE platforms also have IPsec capabilities, but generic entry-010 composition is not inferred.

Required selected-platform receipt:

- hardware/model;
- IOS XE image/release;
- L2TPv3 static/signaled config;
- exact IPsec protection config or protected routed underlay;
- show/debug/packet proof;
- upgrade/rollback.

## 7. Linux-to-Cisco

State: `HIGH-VALUE INTEROP / NEEDS-LAB`.

Use Linux strongSwan/Libreswan + XFRM + kernel L2TPv3 and an exact Cisco endpoint/protection design. Validate both IPsec and pseudowire layers independently and as a composition.

## 8. Dedicated IPsec gateways protecting separate L2TPv3 peers

State: `REFERENCE TOPOLOGY / NEEDS-LAB`.

The L2TPv3 endpoint hosts may route through separate site-to-site IPsec gateways. This is acceptable only when routing/ACL evidence proves no clear bypass.

Install matrix must include both IPsec gateway products and L2TPv3 peer platforms.

## 9. OCI / Docker

State: `NO GENERIC PATH APPROVED`.

Required proof:

- host kernel XFRM + L2TP modules;
- same intended netns for selector and pseudowire;
- CAP_NET_ADMIN/privilege;
- bridge/VLAN visibility;
- IKE UDP500/4500/ESP exposure;
- protocol115/UDP clear-route blocking;
- secret mounts;
- crash cleanup.

## 10. Kubernetes

State: `NO GENERIC PATH APPROVED`.

Likely node-bound network-function architecture only. Verify node affinity, host networking/netns, XFRM/L2TP ownership, CNI interaction, endpoint stability and fail-safe attachment shutdown.

## 11. Windows / Apple / Android consumer platforms

State: `NOT-NORMAL ENTRY-010 ENDPOINT PATH`.

Their legacy/native L2TP VPN clients are not L2TPv3 pseudowire implementations. Do not reuse entry-008 install claims here.

## 12. BSD

State: `UNVERIFIED`.

Needs exact L2TPv3 pseudowire plus IPsec composition evidence on the selected BSD release.

## 13. Strict execution table

| Endpoint | Composition | Install | IPsec ready | PW ready | Protected traffic | Rekey | No-clear fallback | Upgrade | Cleanup |
|---|---|---:|---:|---:|---:|---:|---:|---:|---:|
| Ubuntu/Debian | strongSwan + kernel L2TP | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Fedora/RHEL | Libreswan + kernel L2TP | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux VM | selected IKE + L2TP | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux -> Cisco | heterogeneous | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Cisco selected platform | exact vendor config | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| selected OCI | host kernel + pinned image | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO values are external execution gates.
