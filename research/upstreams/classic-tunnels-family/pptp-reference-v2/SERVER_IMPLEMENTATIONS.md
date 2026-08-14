# PPTP — Server Implementations

Review date: 2026-08-14

Entry: 012 PPTP.

PPTP is an obsolete compatibility protocol. Implementation inventory exists to understand legacy estates and migration, not to recommend new server deployments.

## 1. Windows Server RRAS

Windows RRAS is the authoritative/proprietary native historical PPTP server reference.

Current Microsoft direction is explicitly legacy:

- RRAS still documents PPTP capability;
- new Windows Server 2025 RRAS setups do not accept PPTP/L2TP by default;
- administrators can deliberately enable them for compatibility.

RRAS owns:

- TCP1723 PPTP control listener/state;
- GRE/PPTP call data path;
- PPP session/authentication;
- optional MPPE/security policy according to Windows configuration;
- NPS/RADIUS/local/AD user policy;
- address assignment/routing/NAT/firewall.

PVNetwork status:

`PRIMARY HISTORICAL INTEROP TARGET / NOT NEW-DEPLOYMENT DEFAULT`

No Windows source reuse is implied.

## 2. MikroTik RouterOS

Current MikroTik documentation continues to expose PPTP server and client functionality. MikroTik explicitly warns that PPTP has known security issues and is not recommended for secure use.

RouterOS server concepts include:

- PPTP server enablement;
- PPP profile;
- authentication methods;
- MPPE/encryption requirements depending profile;
- local/remote address pools;
- PPP secrets/RADIUS;
- active sessions;
- firewall/NAT/GRE handling.

Role:

`CURRENT PROPRIETARY LEGACY INTEROP TARGET`

Use exact RouterOS version/hardware evidence before certification.

## 3. Poptop / pptpd

Historically important open-source Linux PPTP server daemon, commonly paired with pppd.

Architecture:

`TCP1723 control + GRE call handling`

`-> pptpd`

`-> pppd`

`-> PPP authentication / MPPE / addressing`

`-> Linux routing/firewall/NAT`

Current product treatment:

`HISTORICAL OPEN-SOURCE REFERENCE / MAINTENANCE+PIN MUST BE VERIFIED`

Do not assume old distro packages are maintained or security-supported. Before any lab/legacy deployment, materialize:

- canonical repository;
- exact immutable release/commit;
- license;
- last meaningful maintenance/security activity;
- distro patches;
- pppd/plugin versions;
- kernel GRE behavior.

If maintenance is inadequate, use only as historical/reference material.

## 4. pppd / PPP layer

PPTP carries PPP. Linux servers historically use `pppd` for:

- LCP;
- PAP/CHAP/MS-CHAP variants;
- MPPE negotiation/plugins;
- IPCP/IPv6CP;
- address/DNS/network scripts;
- RADIUS plugins.

Current PVNetwork PPP source evidence from classic-tunnels work uses `ppp-project/ppp` as the major maintained PPP implementation reference. Component/plugin licenses differ; do not assign one blanket license to every plugin.

## 5. Other router/firewall products

Some routers/firewalls continue to include PPTP for legacy interoperability. Admit them only with current official documentation for an exact release and explicit security warning.

Do not infer PPTP from a generic “PPP VPN” feature.

## 6. SoftEther boundary

Do not automatically list SoftEther as a PPTP server merely because it supports many VPN protocols. Existing PVNetwork SoftEther evidence must be checked for explicit current PPTP server capability before any claim. Keep SSTP/L2TP/IPsec/OpenVPN/SoftEther protocol features separate.

## 7. Authentication/security backend

Possible legacy PPP auth combinations include:

- PAP;
- CHAP;
- MS-CHAPv1;
- MS-CHAPv2;
- RADIUS-backed variants.

MPPE can be negotiated on PPP sessions, historically tied to MS-CHAP-derived keying.

PVNetwork rule:

- never enable PAP/weak CHAP/weak MPPE modes as an automatic fallback;
- record actual auth/encryption negotiated;
- classify all PPTP deployment as legacy regardless of MPPE.

## 8. Address/routing behavior

A functional server also owns:

- local/remote address pools;
- DNS assignment;
- forwarding;
- client-to-client policy;
- Internet NAT/egress;
- firewall/ACL;
- accounting/session cleanup.

TCP1723/GRE connectivity alone is not usable VPN proof.

## 9. Selection direction

### Existing legacy estate

Use native/vendor server already owned by the organization if migration cannot be immediate, lock configuration to the strongest interoperable legacy profile, segment access, monitor usage and publish a migration deadline.

### New server

`DO NOT RECOMMEND PPTP`.

Prefer IKEv2, WireGuard, OpenVPN or another approved modern protocol according to requirements.

## 10. Remaining implementation evidence

- immutable Poptop/pptpd source/license/activity pin;
- exact Windows Server versions/builds still supported in product scope;
- exact RouterOS release/server behavior;
- current Linux kernel/GRE/pptp client/server package availability;
- another maintained/current implementation only if a real legacy customer requires it;
- performance/NAT/multi-client evidence.
