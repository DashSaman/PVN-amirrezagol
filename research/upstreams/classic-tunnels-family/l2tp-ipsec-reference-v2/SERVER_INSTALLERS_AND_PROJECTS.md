# L2TP/IPsec — Server Installers and Deployment Projects

Review date: 2026-08-14

Entry: 008 L2TP/IPsec.

A server deployment is a composition of IPsec + L2TP + PPP/AAA + routing/firewall. Installer research must therefore record ownership of all layers and cleanup behavior, not merely install one package named `xl2tpd`.

## 1. Linux classic packages — strongSwan/Libreswan + xl2tpd + pppd

### IPsec packages

Reuse the completed server installer evidence from:

`research/upstreams/strongswan-family/reference-v2/SERVER_INSTALLERS_AND_PROJECTS.md`

Preferred rule: use a pinned distro package or reproducible upstream release build of strongSwan/Libreswan with exact daemon/backend ownership.

### xl2tpd

Pinned upstream release:

- `xelerance/xl2tpd@07b3063e2b6870fad16366bc8d7c52a6f2a4292f` (`v1.3.20`)
- GPL-2.0-or-later.

Upstream README documents a direct source build/install path:

- `make`
- `sudo make install`

and identifies the man page/configuration as the authoritative configuration reference.

For production, prefer distro-owned packaging where available or preserve an exact source/build/install manifest. Do not mix an unmanaged `/usr/local` daemon with a distro service/package without defining which owns config, init and upgrades.

### pppd

Pinned current source:

- `ppp-project/ppp@86c240ea75d48205310a4d0761784cb11f0b086e`.

A selected distro package normally owns pppd and plugins. Record each plugin's license and ABI/version compatibility; pppd core and plugins do not all share one license.

### Required composed install manifest

Capture:

- OS/release/kernel;
- strongSwan/Libreswan package/version/plugin set;
- xl2tpd package/source pin;
- pppd package/version/plugins;
- service units/init ownership;
- L2TP kernel modules;
- IPsec kernel/XFRM capability;
- generated/config files;
- PSK/private-key/PPP secret/RADIUS secret storage;
- UDP 500/4500 and protected L2TP firewall policy;
- forwarding/NAT/routes/DNS;
- uninstall cleanup.

## 2. Accel-PPP NG composition

Pinned source:

- `accel-ppp/accel-ppp-ng@9654bb66fa129fc3c20b24612ea91fb43dd14f38`
- GPLv2.

Accel-PPP provides L2TP + PPP/auth/pool/RADIUS functionality in one Linux daemon, but IPsec remains separate for entry 008.

Deployment composition:

`strongSwan/Libreswan`

`+ Accel-PPP L2TP/PPP`

`+ kernel IPsec/L2TP support`

`+ firewall/routing/AAA`

### Supply-chain/privilege review

Before approval, record:

- exact build/release commit;
- CMake flags/modules;
- system libraries;
- service user/capabilities;
- kernel/netlink requirements;
- RADIUS secret handling;
- local auth files;
- generated interface naming/pools;
- logs;
- restart/upgrade behavior;
- IPsec coordination/binding so raw L2TP is not exposed accidentally.

## 3. Windows Server RRAS

Current Microsoft-supported deployment path:

- install the **Remote Access** server role;
- install/configure **DirectAccess and VPN (RAS)** role service;
- manage through Routing and Remote Access/Server Manager/PowerShell-supported mechanisms.

Current Microsoft documentation states RRAS supports L2TP, but starting with new Windows Server 2025 RRAS setups, L2TP and PPTP are not accepted by default and must be deliberately enabled when necessary.

### Deployment implication

Treat L2TP enablement as an explicit legacy policy switch. Do not create a generic “enable all VPN protocols” install path.

Record:

- exact Windows Server edition/build;
- Remote Access role installation receipt;
- L2TP port/miniport state;
- IPsec machine authentication policy/certificate/PSK;
- RRAS user/NPS/RADIUS policy;
- IP address pool;
- Windows Firewall rules;
- update/rollback behavior;
- removal of role/profile/secrets.

## 4. pfSense appliance

Current official Netgate documentation provides a built-in L2TP server UI under `VPN > L2TP` and a separate L2TP/IPsec recipe.

Important current vendor warning:

- L2TP itself is unencrypted;
- L2TP/IPsec can be configured but is considered problematic/legacy compared with IKEv2/OpenVPN/WireGuard;
- current docs specifically warn about common Windows-behind-NAT interoperability problems.

### Deployment ownership

pfSense owns:

- appliance installation/update;
- L2TP daemon configuration;
- users/RADIUS integration;
- IPsec layer;
- firewall rules;
- logs/status.

PVNetwork should not install a parallel unmanaged xl2tpd/strongSwan stack on the appliance.

## 5. SoftEther VPN Server

SoftEther is a full multiprotocol server product with L2TP/IPsec functionality in its own server/admin architecture.

Existing PVNetwork research pins source under `classic-tunnels-family/SOURCE_ARCHITECTURE.md`.

Deployment review before approval must record:

- exact current release/source pin;
- platform package/installer;
- service account;
- virtual hub/user/AAA ownership;
- IPsec/L2TP settings and PSK storage;
- listener/firewall ports;
- upgrade/uninstall/data backup semantics;
- license obligations;
- whether other enabled protocols expand attack surface unnecessarily.

Do not enable unrelated SoftEther protocols merely because the server ships them.

## 6. Community all-in-one scripts/images

There are many public “L2TP/IPsec VPN server” shell scripts and Docker images. None are approved by popularity.

Before any is admitted to the reference set, record:

1. canonical source repository;
2. exact commit/tag;
3. license;
4. package sources and signature verification;
5. whether it installs strongSwan/Libreswan/OpenSwan and exact version;
6. L2TP daemon/version;
7. pppd/AAA components;
8. root/privileged operations;
9. sysctl/firewall/NAT changes;
10. generated PSKs/user credentials and defaults;
11. exposed ports;
12. update mechanism;
13. uninstall/rollback cleanup;
14. unattended remote-download execution risks.

**Do not recommend `curl | sh` style installation without reviewing and pinning the actual script and every downloaded artifact.**

## 7. OCI/container deployment

State: `ADVANCED / NO GENERIC IMAGE APPROVED`.

L2TP/IPsec containers may require:

- UDP 500/4500/1701 reachability;
- host networking;
- NET_ADMIN/privileged capabilities;
- kernel XFRM and L2TP modules;
- `/dev/ppp`;
- sysctls/forwarding;
- iptables/nftables changes;
- namespace coordination between IKE daemon and L2TP/PPP daemon.

A container that starts successfully can still fail to install SAs/policies or PPP interfaces in the intended namespace. Strict runtime proof is mandatory.

## 8. Kubernetes/orchestration

State: `ADVANCED / EVIDENCE-GAP`.

A normal stateless Deployment abstraction is a poor default model for a protocol stack with host kernel XFRM, UDP identity, PPP interfaces and long-lived security/session state.

Required design proof:

- hostNetwork/node binding;
- privileged/capability model;
- `/dev/ppp` access;
- kernel module availability;
- XFRM namespace ownership;
- CNI/Service/NAT handling;
- state/credential persistence;
- failover/rekey semantics;
- cleanup after pod/node failure.

## 9. Upgrade/uninstall rules

Every selected installer must prove:

### Upgrade

- config migration across IKE/L2TP/PPP layers;
- no unexpected enabling of weak legacy algorithms;
- service ordering preserved;
- user/address pools preserved;
- certificate/PSK/RADIUS secrets preserved securely;
- kernel module/API compatibility.

### Rollback

- older binaries understand migrated config or backup is restored;
- no mixed daemon/plugin versions;
- active SAs/sessions are intentionally interrupted/re-established;
- rollback does not silently fall back to plain L2TP.

### Uninstall

- services stopped/disabled;
- IPsec SAs/policies removed;
- L2TP interfaces/sockets removed;
- PPP sessions/interfaces removed;
- routes/firewall/NAT/sysctl changes restored;
- generated secrets/temp files removed according to retention policy;
- logs/backups handled deliberately.

## 10. Current deployment recommendation

- New deployments: prefer a modern protocol; L2TP/IPsec is not the default.
- Compatibility Linux server: pinned strongSwan/Libreswan + xl2tpd/pppd or reviewed Accel-PPP composition.
- Windows-native legacy server: RRAS only when explicitly required and version-tested.
- Appliance: use appliance-owned UI/update path.
- Container/community script: not approved until source/supply-chain/privilege review passes.
