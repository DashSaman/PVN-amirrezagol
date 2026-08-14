# L2TPv3/IPsec — Endpoint Installers and Deployment Ownership

Review date: 2026-08-14

Entry: 010 L2TPv3/IPsec.

A protected pseudowire deployment owns two infrastructure stacks: L2TPv3 and IPsec. Installation must record the source/package/service/kernel ownership of both, plus Layer-2 attachment and routing/firewall state.

## 1. Linux primary stack

### L2TPv3 layer

Reuse entry 009 evidence:

- distribution kernel with L2TP modules;
- distribution iproute2 or pinned build;
- optional pinned ql2tpd/go-l2tp.

### IPsec layer

Reuse entries 004–007 evidence:

- strongSwan 6.0.7 as primary reviewed advanced Linux engine;
- Libreswan v5.4 as serious alternative;
- Linux XFRM kernel data plane.

### Required composed install manifest

Record:

- distro/release/kernel;
- L2TP kernel module/version/source ownership;
- iproute2 version;
- strongSwan/Libreswan exact release/packages/plugins/providers;
- IKE daemon service model;
- ql2tpd if used;
- network namespace/VRF;
- bridge/VLAN attachment;
- IPsec selectors or protected-underlay route policy;
- firewall/IKE/NAT-T/ESP rules;
- credentials/secrets/cookies;
- startup ordering;
- cleanup/uninstall ordering.

## 2. Startup ordering

A deployment unit must prevent clear pseudowire forwarding before protection is ready.

Recommended system dependency:

1. underlay interfaces/routes;
2. IKE/IPsec daemon;
3. IPsec connection/policy establishment or readiness guard;
4. L2TPv3 tunnel/session creation;
5. attachment circuit/bridge enablement.

A static tunnel may exist earlier, but forwarding must remain blocked until the protection guard passes.

## 3. systemd/network automation

If Linux automation is selected, review separately:

- systemd units and ordering (`After`, `Requires`, restart behavior);
- scripts or network manager used to create/delete L2TP objects;
- VICI/swanctl or Libreswan configuration ownership;
- idempotency;
- protection-ready health check;
- crash cleanup;
- route/firewall rollback.

Do not use a startup shell script that creates the clear L2TPv3 path first and hopes IPsec appears later.

## 4. ql2tpd composition

Pinned go-l2tp is MIT and can orchestrate static Linux L2TPv3 sessions. The IPsec component is still separately installed and configured.

If production-selected:

- pin Go source/toolchain/module versions;
- package ql2tpd as an owned service;
- minimize privileges;
- ensure IPsec-before-forwarding dependency;
- verify ql2tpd crash/restart does not leave unsafe attachment state.

## 5. Cisco/network OS protected endpoint

Cisco IOS XE has built-in L2TPv3 and separate IPsec feature families. Because generic entry-010 composition has not been proven from one current vendor recipe in this work unit, deployment status is:

`INTEROP TARGET / EXACT PLATFORM CONFIG REQUIRED`.

If selected, capture:

- router model/platform;
- IOS XE release/image/license;
- L2TPv3 pseudowire config;
- IPsec/IKE config selecting or protecting the pseudowire underlay;
- route/ACL behavior;
- config archive;
- reload/upgrade/rollback;
- packet/debug/show evidence.

Do not automate a generic Cisco template until an exact tested platform matrix exists.

## 6. Protected-underlay appliances

A firewall/router may establish a site-to-site IPsec tunnel while separate endpoint systems run L2TPv3 through that protected route.

Install ownership is then split:

- IPsec gateways own IKE/ESP;
- L2TPv3 peers own pseudowire;
- routed underlay between them must have no clear alternate path.

This topology requires end-to-end dependency monitoring because the pseudowire hosts may not directly see IKE state.

## 7. OCI/container deployment

State: `ADVANCED / NO GENERIC IMAGE APPROVED`.

If both IKE and L2TPv3 run in containers, ensure they share the intended kernel/network namespace and XFRM/L2TP objects.

Typical risks:

- CAP_NET_ADMIN/privileged mode;
- host kernel modules;
- network namespace mismatch between XFRM and L2TP;
- hostNetwork/NAT behavior;
- UDP500/4500/ESP reachability;
- bridge/VLAN interfaces outside container namespace;
- clear-route leakage if one container dies;
- secret mounts.

Prefer a dedicated gateway VM/node unless containerization has a proven operational benefit.

## 8. Kubernetes

State: `ADVANCED / NO GENERIC SUPPORT CLAIM`.

A protected pseudowire is strongly node/network-stateful. Required design evidence:

- node affinity;
- hostNetwork or dedicated netns;
- XFRM and L2TP objects in the same intended namespace;
- CAP_NET_ADMIN/privilege;
- CNI bypass/interaction;
- stable endpoint identity;
- bridge/VLAN interface access;
- failover/cleanup;
- secret distribution.

Do not deploy as a freely rescheduled stateless application workload.

## 9. Community scripts

No blind one-line installer is approved. A combined L2TPv3/IPsec script has enough root/network authority to alter:

- XFRM state;
- routes;
- bridge/VLANs;
- firewall;
- kernel modules;
- IPsec secrets;
- protocol-115/UDP exposure.

Review and pin every downloaded artifact and cleanup action before inclusion.

## 10. Upgrade

A safe upgrade must coordinate:

- kernel/iproute2 compatibility;
- IKE engine/plugin/provider changes;
- current security policy/algorithm guidance;
- pseudowire config compatibility;
- service ordering;
- bridge/VLAN state;
- no temporary clear fallback during restart.

## 11. Rollback

Rollback must restore a coherent pair:

- previous IPsec daemon/config/provider;
- previous L2TPv3 tooling/kernel expectations;
- previous network policy;
- protection guard.

If a rollback cannot guarantee protected forwarding, keep the attachment circuit administratively down.

## 12. Uninstall/decommission order

1. disable/detach Layer-2 forwarding;
2. delete L2TPv3 sessions/tunnels;
3. remove bridge/VLAN state created by the product;
4. terminate/delete IPsec CHILD/IKE SAs;
5. remove product XFRM policies/routes/firewall rules;
6. stop/remove services/configuration;
7. remove product credentials according to retention policy;
8. unload kernel modules only when no other owner uses them.

Do not remove IPsec first while leaving an active clear pseudowire route.
