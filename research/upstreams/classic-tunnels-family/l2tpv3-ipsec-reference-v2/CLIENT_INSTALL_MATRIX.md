# L2TPv3/IPsec — Protected Peer Installation Matrix

Review date: 2026-08-14

Entry: 010 L2TPv3/IPsec.

For this infrastructure technology, the v2 client category is interpreted as the protected **pseudowire peer**. Consumer Windows/Apple/Android L2TP VPN clients are not L2TPv3 peers.

## 1. Linux peer — strongSwan + kernel L2TP

State: `PRIMARY REFERENCE PATH / NEEDS-LAB`.

Install/provision:

- Linux kernel XFRM + L2TP modules;
- iproute2;
- strongSwan selected package/release/plugin set;
- bridge/VLAN/network namespace integration;
- optional ql2tpd;
- profile-specific XFRM selector or protected-underlay route.

Required proof:

- IPsec starts before forwarding;
- direct protocol115 selector case;
- UDP case;
- no clear fallback;
- rekey;
- cleanup.

## 2. Linux peer — Libreswan + kernel L2TP

State: `ALTERNATIVE REFERENCE PATH / NEEDS-LAB`.

Use Libreswan v5.4 baseline plus Linux kernel/iproute2. Do not reuse strongSwan configuration syntax. Capture exact Libreswan connection/policy and XFRM behavior.

## 3. Linux peer — ql2tpd + selected IPsec

State: `ORCHESTRATED STATIC PEER / NEEDS-LAB`.

ql2tpd can own static pseudowire creation while strongSwan/Libreswan owns protection. Optional HELLO behavior remains like-peer limited unless proven otherwise.

## 4. Cisco IOS XE protected peer

State: `EXACT-VENDOR-PROOF / NEEDS-LAB`.

No consumer client package exists. Configure the selected router/network OS through supported vendor management and prove the actual L2TPv3 + IPsec composition.

## 5. Linux-to-Cisco pair

State: `HIGH-VALUE HETEROGENEOUS INTEROP / NEEDS-LAB`.

Both layers must match:

### IPsec

- endpoint identities;
- IKE/authentication;
- ESP policy;
- selectors or protected route;
- lifetimes/NAT behavior.

### L2TPv3

- transport;
- IDs;
- cookies;
- pseudowire type;
- attachment framing;
- MTU.

## 6. Separate IPsec gateway + L2TPv3 peer

State: `MULTI-DEVICE TOPOLOGY / NEEDS-LAB`.

The L2TPv3 peer may be a Linux/Cisco system while a different firewall/router protects the endpoint route via IPsec. Installation/certification must include both devices and prove no clear bypass.

## 7. Linux VM

State: `PREFERRED ADVANCED DEPLOYMENT FORM / NEEDS-LAB`.

A dedicated VM can own kernel L2TP/XFRM and virtual L2 interfaces coherently. Record hypervisor MTU/offload/VLAN behavior.

## 8. OCI container

State: `ADVANCED / NO GENERIC SUPPORT CLAIM`.

Uses host kernel XFRM/L2TP. Verify same netns, privileges, bridge visibility, protection guard and cleanup.

## 9. Kubernetes

State: `ADVANCED / NO GENERIC SUPPORT CLAIM`.

Treat as node network function; no normal reschedulable app-pod semantics.

## 10. Windows / Apple / Android

State: `NO NATIVE CONSUMER ENTRY-010 CLAIM`.

Native L2TP/IPsec remote-access support from entry 008 does not imply L2TPv3 pseudowire support. Do not expose entry 010 on consumer clients without a separately reviewed specialized implementation.

## 11. BSD

State: `UNVERIFIED`.

Needs current L2TPv3 pseudowire + IPsec composition evidence for the exact BSD release.

## 12. Provision/update/remove table

| Peer | Composition | Provision | IPsec protection | PW forwarding | Rekey | Restart | Update/rollback | Cleanup |
|---|---|---:|---:|---:|---:|---:|---:|---:|
| Linux | strongSwan + kernel L2TP | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux | Libreswan + kernel L2TP | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux | ql2tpd + IPsec | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Cisco selected platform | vendor composition | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux -> Cisco | heterogeneous | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux VM | selected composition | TODO | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO values are external execution gates.
