# SoftEther VPN Protocol — Server Installers and Deployment Projects

Review date: 2026-08-14

Entry: 013 SoftEther VPN Protocol.

## 1. Canonical SoftEther VPN Server deployment

Use the canonical `SoftEtherVPN/SoftEtherVPN` project or official release packaging for the selected platform. Existing PVNetwork source baseline is commit `49eb2f08641709d1af57a0d04971973ff94461db`.

Before implementation freeze, refresh/pin the exact selected release and record source/archive hash, compiler/toolchain and third-party dependency/license inventory.

## 2. Windows server

Typical ownership:

- SoftEther VPN Server service;
- TCP listeners;
- server certificate/private key;
- Virtual Hubs/users/groups;
- local bridge/SecureNAT/cascade;
- management by Server Manager/vpncmd;
- Windows Firewall/service lifecycle.

Install evidence must record installer provenance/signature/hash, service account, listener exposure, management exposure, persistent config, update/rollback and uninstall cleanup.

## 3. Linux server

Typical ownership:

- pinned source/package build;
- `vpnserver` service/daemon;
- privileged TCP binding and network bridge/tap capabilities as required;
- persistent server config/certificates;
- systemd/init ownership;
- firewall;
- local bridge/SecureNAT requirements.

Avoid running as unrestricted root when the selected build/deployment supports a narrower privilege model, but do not invent capability reductions without lab evidence.

## 4. Other Unix-like builds

SoftEther source historically targets multiple Unix-like systems, but exact current release support must be verified per platform. Source compilation success alone is not support certification.

## 5. VPN Bridge

SoftEther VPN Bridge is a distinct deployment role useful for site/Layer-2 extension. Review installer/package/service privilege, physical NIC/tap ownership, bridge loops/VLAN behavior and management exposure separately from a normal remote-access server.

## 6. Container / OCI

State: `ADVANCED / PIN EXACT IMAGE OR BUILD`.

A containerized server may need:

- stable TCP listener exposure;
- persistent config/certificate volumes;
- NET_ADMIN/TUN/bridge access for selected networking modes;
- host networking or explicit port mappings;
- safe management access;
- restart/upgrade/rollback and config backup.

Do not trust a community image without Dockerfile/base-image/source/release/digest review.

## 7. Kubernetes

State: `ADVANCED / TOPOLOGY-SPECIFIC`.

Remote-access TCP listeners can run behind stable services, but Virtual Hub sessions, local bridge/TUN privileges, certificate identity, long-lived TCP sessions and persistent configuration make a generic stateless Deployment claim unsafe.

Prefer dedicated gateway nodes/VMs unless a Kubernetes-specific design is proven.

## 8. Multiprotocol surface minimization

SoftEther Server can enable several compatibility protocols. Entry 013 deployments should explicitly inventory and disable unused:

- SSTP;
- L2TP/IPsec;
- OpenVPN compatibility;
- EtherIP;
- other listeners/features not required.

A native SoftEther deployment should not accidentally expose every compatibility protocol.

## 9. Management exposure

Server Manager/vpncmd administration should be restricted separately from native client listeners. Record management bind/listener, authentication, ACL/source restrictions and audit logs.

Do not expose privileged management broadly because TCP443/native client traffic is public.

## 10. Upgrade/rollback

Before upgrade:

- backup server configuration/Virtual Hubs/users/certificates according to secret policy;
- pin old/new binaries/source;
- review release/security notes;
- test native client compatibility and disabled compatibility listeners;
- verify bridge/SecureNAT/cascade behavior.

Rollback restores a coherent binary/config/certificate set.

## 11. Uninstall/decommission

1. migrate/disconnect native sessions;
2. disable listeners;
3. remove local bridge/SecureNAT/cascade objects owned by the deployment;
4. stop/remove service;
5. remove firewall/management exposure;
6. retain/delete config/certificates according to backup/retention policy;
7. revoke endpoint certificate when appropriate;
8. verify no compatibility listener remains.

## 12. Supply-chain rule

No blind install scripts/images. Record every downloaded artifact, build source, third-party library, service privilege, listener and secret path before deployment.
