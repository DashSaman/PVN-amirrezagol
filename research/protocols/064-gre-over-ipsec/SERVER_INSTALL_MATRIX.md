# GRE over IPsec — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Evidence-backed path |
|---|---|---|
| Linux | Supported composition | Linux GRE + iproute2 from entry 063; Linux XFRM/IPsec + strongSwan/native IPsec from strongSwan-family V2 dossier; privileged network admin required |
| Cisco IOS XE supported platforms | Supported where current feature guide lists platform/release | Native GRE tunnel interface plus IKEv2/IPsec profile and tunnel protection |
| Containers/Kubernetes | Not a canonical server target by itself | May operate only with host/kernel networking privileges and an actual IPsec/GRE stack; no protocol-defined container/Helm deployment |
| Windows/macOS/mobile consumer platforms | Not promoted by this dossier | Do not infer generic GRE-over-IPsec client support from native IKE/IPsec support; composition must be proven per platform |

Linux implementation inherits kernel/module/capability and distribution lifecycle requirements from GRE and IPsec layers. Cisco/Junos-like appliance lifecycle remains vendor controlled.
