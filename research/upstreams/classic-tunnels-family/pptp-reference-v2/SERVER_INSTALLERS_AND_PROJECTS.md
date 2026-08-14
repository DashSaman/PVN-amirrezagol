# PPTP — Server Installers and Deployment Projects

Review date: 2026-08-14

Entry: 012 PPTP.

PPTP deployment research exists for legacy inventory/migration. New server installation is not recommended.

## 1. Windows Server RRAS

Official legacy path:

- install Remote Access/RAS role;
- explicitly enable PPTP only when a legacy requirement exists;
- configure WAN Miniport/port count, PPP authentication, address assignment and NPS/RADIUS/local policy;
- configure Windows Firewall/network routing.

Current Windows Server 2025 direction is significant: new RRAS setups do not accept PPTP/L2TP by default. Treat any enablement as an auditable legacy exception.

Record exact build, role state, PPTP miniport/listener, GRE firewall, auth methods, MPPE policy, address pool, routing, update and removal.

## 2. MikroTik RouterOS

PPTP is a built-in RouterOS feature rather than a third-party installer.

Deployment ownership includes:

- exact RouterOS release/hardware;
- `/interface pptp-server server` or equivalent current menu/CLI;
- PPP profiles/secrets/RADIUS;
- authentication/encryption policy;
- address pools;
- firewall/NAT/PPTP helper;
- firmware update/rollback/config backup.

Current MikroTik security warning must remain visible in PVNetwork documentation.

## 3. Linux Poptop/pptpd

Historical package/source deployment:

`pptpd + pppd + kernel GRE + firewall/NAT`

No new production deployment is approved until the exact canonical source/release/license/maintenance status is materialized.

If a legacy lab requires it, record:

- distro snapshot;
- pptpd source/package hash;
- pppd/plugins;
- config/secret file permissions;
- service/init unit;
- kernel GRE/helper modules;
- iptables/nftables/sysctl changes;
- uninstall cleanup.

## 4. Community scripts/images

Do not recommend “PPTP VPN one-click” scripts/images. They often combine obsolete protocol security with root-level firewall/sysctl/credential changes.

Review before any lab use:

- immutable source;
- package repositories/signatures;
- generated passwords;
- weak auth/MPPE defaults;
- firewall/GRE helper changes;
- update/uninstall behavior;
- image/base OS maintenance.

## 5. Cloud VM

A cloud Windows RRAS or legacy Linux VM adds:

- security-group TCP1723;
- GRE protocol47 support through provider/NAT/load-balancer path;
- public/private route behavior;
- provider NAT/PPTP ALG availability;
- image patch lifecycle.

Many cloud L4 load balancers/NAT products are optimized for TCP/UDP and may not support GRE/PPTP call tracking. Verify provider behavior rather than assuming port rules are enough.

## 6. OCI/container

State: `NOT RECOMMENDED / ADVANCED LEGACY LAB ONLY`.

PPTP containers can need NET_ADMIN, PPP devices, GRE/helper kernel modules, forwarding/NAT and host networking. This is a poor tradeoff for an obsolete protocol.

No generic container image is approved.

## 7. Kubernetes

State: `NO GENERIC PATH / DO NOT DESIGN NEW PPTP K8S SERVICE`.

GRE, PPP state, kernel helpers and long-lived legacy sessions make ordinary Service/Ingress abstractions unsuitable. If an inherited appliance must run near Kubernetes, isolate it as a dedicated gateway/VM and migrate away.

## 8. Upgrade/rollback

Legacy servers still require safe lifecycle:

- preserve config/AAA/address pools;
- confirm OS/firmware update has not changed PPTP availability or crypto defaults;
- verify GRE helper/firewall after reboot;
- avoid re-enabling weak auth globally;
- keep rollback configuration backup;
- maintain migration target in parallel.

## 9. Uninstall/decommission

Preferred lifecycle is retirement:

1. provision/test replacement protocol;
2. migrate users/devices;
3. disable PPTP listener/server;
4. disconnect remaining sessions;
5. remove TCP1723/GRE firewall/helper exceptions;
6. remove PPTP-specific users/secrets/pools/routes;
7. remove pptpd package/service or RRAS PPTP enablement;
8. retain audit evidence according to policy;
9. verify no clients still attempt PPTP.

## 10. Supply-chain conclusion

The fact that PPTP is easy to install is not a benefit worth new supply-chain risk. Prefer native legacy support already present on an owned appliance only for a temporary migration window; otherwise do not add a new PPTP server dependency.
