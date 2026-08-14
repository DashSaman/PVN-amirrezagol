# L2TPv3 — Endpoint Installers and Deployment Projects

Review date: 2026-08-14

Entry: 009 L2TPv3.

L2TPv3 is infrastructure pseudowire technology. The “installer” layer is usually an OS/kernel/network-OS package or automation role, not a consumer VPN server script.

## 1. Linux kernel + iproute2

### Source pins

- Linux kernel: `torvalds/linux@2f1baf1fc8929e6c48370be543ad028ac7ad4131`
- iproute2: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`

### Normal deployment path

Prefer distribution-owned kernel and iproute2 packages with the L2TP modules enabled. Exact package names vary by distro/kernel packaging.

Required runtime capability may include modules such as:

- `l2tp_core`
- `l2tp_netlink`
- `l2tp_eth`
- `l2tp_ip`
- `l2tp_ip6`

The go-l2tp project explicitly lists these modules for its root-required L2TP tests.

### Security/ownership rule

An installer must record:

- kernel version/config;
- iproute2 version;
- module source/package;
- network namespace;
- CAP_NET_ADMIN/root requirement;
- persistent network configuration mechanism;
- bridge/VLAN attachment;
- firewall for IP protocol 115 or UDP;
- cleanup on stop/uninstall.

Do not install a second random kernel/module set just to obtain L2TPv3 without matching the running kernel.

## 2. go-l2tp / ql2tpd

Pinned source:

- `katalix/go-l2tp@0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- MIT.

Upstream documents:

`go install github.com/katalix/go-l2tp/...@latest`

For reproducible deployment, PVNetwork must replace `@latest` with an exact source/release version and preserve the Go toolchain/module lock/build receipt.

ql2tpd requires root to program the Linux kernel L2TP subsystem.

### Service packaging work required

If ql2tpd becomes a selected deployment component, define and test:

- systemd/OpenRC service unit;
- dedicated config path/permissions;
- capability minimization vs full root;
- restart semantics;
- tunnel/session cleanup on crash;
- binary/source signature/hash;
- upgrade/rollback.

Do not assume upstream `go install` is a production package manager.

## 3. Static provisioning via system network automation

L2TPv3 can be provisioned by configuration-management/network scripts calling `ip l2tp`, `ip link`, bridge/VLAN and firewall commands.

Any selected Ansible/systemd-networkd/NetworkManager/custom automation must be reviewed for:

- idempotency;
- exact command ordering;
- peer ID/cookie synchronization;
- namespace/VRF support;
- persistent reboot behavior;
- rollback;
- removal of stale tunnel/session/netdevice/bridge state.

A shell script that only creates the tunnel and never removes it is not a complete installer.

## 4. Cisco IOS XE built-in feature deployment

L2TPv3 is a built-in network-OS feature on supported Cisco IOS XE platforms/releases; it is not installed as a third-party package.

Deployment ownership belongs to:

- Cisco image/software lifecycle;
- device configuration;
- supported platform/license/feature set;
- backup/rollback/config management.

PVNetwork should treat Cisco as an interoperability/manage-via-supported-CLI/API target, not attempt to redistribute Cisco software.

Required evidence:

- exact hardware/platform;
- exact IOS XE release;
- feature availability;
- static vs signaled configuration;
- attachment-circuit type;
- software upgrade/ISSU/reload effects on pseudowires;
- configuration rollback.

## 5. Container / OCI Linux endpoint

State: `ADVANCED / NO GENERIC IMAGE APPROVED`.

A container that runs `ip l2tp` or ql2tpd depends on the host kernel L2TP subsystem and networking namespace.

Likely requirements:

- CAP_NET_ADMIN or privileged mode;
- host/dedicated network namespace access;
- L2TP kernel modules loaded on the host;
- bridge/VLAN interfaces visible in the namespace;
- direct IP protocol 115 or UDP reachability;
- deterministic cleanup when container exits.

Supply-chain review must pin both container image and host kernel/iproute2 behavior.

## 6. Kubernetes

State: `ADVANCED / NO GENERIC DEPLOYMENT APPROVED`.

Pseudowire endpoints are stateful network infrastructure tied to node interfaces/kernel namespace. A normal reschedulable stateless pod model is unsafe by default.

Required proof before any claim:

- node affinity/ownership;
- hostNetwork or dedicated netns design;
- CAP_NET_ADMIN/privilege;
- module loading ownership;
- CNI interaction;
- bridge/VLAN attachment;
- peer endpoint stability;
- cleanup/failover if pod/node disappears.

## 7. Router/network automation systems

Infrastructure controllers can push L2TPv3 configuration to Cisco/network devices or Linux peers. Each such product is a separate management plane.

Review:

- API authentication/RBAC;
- config diff/commit/rollback;
- secret/cookie handling;
- device inventory/version compatibility;
- audit logs;
- failure rollback;
- topology source of truth.

## 8. No blind community installer rule

Do not recommend a generic GitHub “L2TPv3 setup script” without reviewing:

- source/license/revision;
- all downloaded artifacts;
- root commands;
- bridge/firewall modifications;
- persistence hooks;
- security defaults;
- rollback/uninstall.

L2TPv3 is simple enough to configure statically that an opaque root installer usually creates more supply-chain risk than value.

## 9. Upgrade/rollback requirements

### Linux

- preserve tunnel/session configuration separately from generated kernel runtime state;
- test kernel/iproute2 compatibility after upgrades;
- delete/recreate sessions intentionally across network service restart;
- verify netdevice names/bridge membership;
- restore old kernel/userspace together when rollback requires it.

### Cisco

- backup exact running/startup config;
- verify image feature compatibility;
- capture pseudowire state before/after upgrade;
- test rollback/reload.

## 10. Uninstall/decommission requirements

Remove in safe order:

1. stop forwarding/attachment circuit;
2. detach pseudowire netdevice from bridge/VLAN;
3. delete L2TP session;
4. delete L2TP tunnel;
5. remove firewall/protocol rules created for the deployment;
6. remove persistent config/service unit;
7. unload modules only if no other service owns them;
8. remove optional IPsec protection in entry 010 separately.

Never unload shared kernel modules blindly.
