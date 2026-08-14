# L2TP/IPsec — Server Installation Matrix

Review date: 2026-08-14

Entry: 008 L2TP/IPsec.

This matrix records credible deployment paths and their ownership. It does not claim that PVNetwork has executed the installs.

## Status vocabulary

- `REFERENCE-PATH`: supported/documented architecture exists;
- `LEGACY-EXPLICIT`: available only as deliberate compatibility deployment;
- `NEEDS-LAB`: no execution receipt in this agent environment;
- `NO-GENERIC-PATH`: no generally safe/approved installation path is promoted.

## 1. Debian / Ubuntu server

### Composition A — strongSwan + xl2tpd + pppd

State: `REFERENCE-PATH / LEGACY-EXPLICIT / NEEDS-LAB`.

Install ownership:

- distro strongSwan packages, preferably modern daemon/control path selected deliberately;
- distro `xl2tpd` package or pinned v1.3.20 source build;
- distro pppd/plugins;
- Linux kernel XFRM + L2TP/PPP modules;
- firewall/NAT/routing.

Required version receipt:

- exact OS/kernel;
- package repository/version/signature;
- IKEv1 policy state required for the target clients;
- L2TP kernel/userspace mode;
- pppd version/auth plugins;
- service unit ownership;
- secret file permissions.

### Composition B — Libreswan + xl2tpd + pppd

State: `REFERENCE-PATH / LEGACY-EXPLICIT / NEEDS-LAB`.

Important current warning: Libreswan 5.x defaults may reject IKEv1 unless policy is explicitly changed. NetworkManager-l2tp documents this behavior. A server/client composition must never silently weaken the global IKE policy merely to make one old profile work.

### Composition C — IPsec + Accel-PPP

State: `REFERENCE-PATH / NEEDS-LAB`.

Use Accel-PPP for integrated L2TP/PPP/AAA/pools and an explicitly coordinated IPsec layer. Verify that unprotected UDP/1701 is not reachable outside the intended IPsec policy.

## 2. Fedora / RHEL family

State: `REFERENCE-PATH / LEGACY-EXPLICIT / NEEDS-LAB`.

Candidate stack:

- Libreswan is a particularly relevant distro-native IKE/IPsec reference;
- NetworkManager-l2tp current source includes Fedora/RHEL build examples and supports Libreswan/strongSwan;
- xl2tpd or go-l2tp/kl2tpd availability depends selected distro/packages;
- kernel L2TP modules may be in additional module packages or subject to blacklist policy on some releases.

Required lab must test module loading, SELinux, systemd ownership, firewalld/nftables and RADIUS/secret permissions.

## 3. openSUSE

State: `REFERENCE-PATH / NEEDS-LAB`.

NetworkManager-l2tp current source includes an openSUSE Tumbleweed build example and supports the same layered strongSwan/Libreswan + L2TP + PPP model.

Do not infer server package names or defaults from the client plugin example; pin selected distro packages in the actual server lab.

## 4. Arch Linux

State: `REFERENCE-PATH / NEEDS-LAB`.

StrongSwan/Libreswan/L2TP/ppp packages or source builds can form the stack, but the exact package set and systemd configuration must be recorded from the selected Arch snapshot/repositories.

## 5. Alpine Linux

State: `ADVANCED / NEEDS-LAB`.

Possible for source/package-based IKE/L2TP/PPP stacks, especially in compact gateway environments, but not automatically a safe container choice. Verify kernel modules, musl/build compatibility, init/service ownership, firewall and `/dev/ppp`.

## 6. FreeBSD / BSD family

State: `ADVANCED / NEEDS-LAB`.

The completed IPsec dossier records strongSwan/Libreswan/BSD implementation distinctions. For entry 008, add a verified L2TP+PPP implementation compatible with the selected BSD kernel/userland and prove the RFC3193 binding.

No generic “Linux instructions work on BSD” assumption is allowed.

## 7. Windows Server 2016 / 2019 / 2022

State: `NATIVE RRAS / LEGACY COMPATIBILITY / NEEDS-LAB`.

Microsoft documents RRAS support for L2TP and other VPN protocols. Install through the Remote Access role and configure protocol/ports/address pools/authentication through supported Windows management surfaces.

Required evidence:

- exact build/patch level;
- role install;
- L2TP WAN Miniport/server acceptance;
- IPsec machine auth/PSK/certificate;
- NPS/RADIUS/user auth;
- client pool;
- Windows Firewall;
- NAT scenarios;
- upgrade/uninstall.

## 8. Windows Server 2025

State: `NATIVE RRAS / LEGACY-EXPLICIT / NEEDS-LAB`.

Current Microsoft documentation says new RRAS setups **do not accept L2TP/PPTP by default**. They can be explicitly enabled if needed.

PVNetwork implication: any Server 2025 L2TP deployment requires an explicit legacy-compatibility decision and a documented enablement step. It should never be turned on as part of a generic default VPN role install.

## 9. pfSense appliance

State: `APPLIANCE / LEGACY-EXPLICIT / NEEDS-LAB`.

Official Netgate docs provide:

- `VPN > L2TP` server enable/config;
- local users or RADIUS;
- remote IP range/server address;
- L2TP firewall-rule interface;
- L2TP/IPsec recipe using a separate IPsec mobile configuration.

Current Netgate guidance warns that L2TP/IPsec has severe limitations/problems compared with modern alternatives and specifically highlights Windows-behind-NAT incompatibilities with the strongSwan-based IPsec layer.

Treat this as a compatibility target, not recommended greenfield server.

## 10. OPNsense

State: `EVIDENCE-GAP FOR DEDICATED L2TP SERVER`.

Existing OPNsense/IPsec v2 research is strong, but current official documentation search in this work unit did not yield a dedicated modern L2TP server guide. Do not infer current L2TP server availability from old IPsec or generic appliance history.

If OPNsense is required for entry 008, inspect current source/plugins/release package set and exact GUI availability before adding it to the certification matrix.

## 11. SoftEther VPN Server

State: `MULTIPROTOCOL SERVER / NEEDS CURRENT PIN + LAB`.

Existing PVNetwork source evidence confirms L2TP/IPsec-related implementation families in SoftEther. Before strict v2 completion:

- refresh to exact selected release;
- install on representative Windows/Linux server;
- enable only required protocols;
- configure IPsec PSK/auth/users;
- capture listener/firewall behavior;
- upgrade/uninstall/backup/restore.

## 12. OCI / Docker

State: `NO GENERIC PATH APPROVED / NEEDS-LAB`.

Minimum technical requirements often include:

- UDP 500/4500 and protected UDP 1701;
- NET_ADMIN or privileged capabilities;
- host/kernel XFRM;
- L2TP kernel modules;
- `/dev/ppp`;
- forwarding/sysctl;
- firewall/NAT;
- coordinated network namespace across IKE and L2TP/PPP daemons.

Do not certify a container merely because all processes are running.

## 13. Kubernetes

State: `NO GENERIC PATH APPROVED / ADVANCED`.

A realistic design likely requires node/gateway ownership rather than an ordinary stateless Deployment. Required proof:

- host networking/node affinity;
- kernel XFRM/L2TP/PPP device ownership;
- capabilities/privilege;
- UDP endpoint stability;
- CNI/NAT effects;
- secrets;
- cleanup/failover.

## 14. Cloud marketplace/community VM images

State: `UNREVIEWED`.

Do not trust a prebuilt “one-click L2TP/IPsec” image without source/package/provisioning evidence. Capture image publisher, immutable image ID/digest, installed versions, generated secrets, update lifecycle and uninstall/decommission behavior.

## 15. Strict execution table

| Server target | Stack | Install | Start | Protected L2TP | PPP auth | Multi-NAT clients | Upgrade | Rollback | Uninstall cleanup |
|---|---|---:|---:|---:|---:|---:|---:|---:|---:|
| Ubuntu/Debian | strongSwan + xl2tpd + pppd | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Fedora/RHEL | Libreswan + selected L2TP/PPP | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux scale test | IPsec + Accel-PPP | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Windows Server 2025 | RRAS L2TP explicitly enabled | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| pfSense | appliance L2TP + IPsec | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| SoftEther | selected release | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| selected OCI image | pinned source/image | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO values are external execution gates, not assumed success.
