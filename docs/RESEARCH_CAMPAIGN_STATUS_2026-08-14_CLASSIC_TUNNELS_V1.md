# PVNetwork Research Campaign Status — 2026-08-14 — Classic / Legacy Tunnels v1 Closure

Repository phase: research / requirements / architecture.

Entries 009–012: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

## Shared evidence created

Under `research/upstreams/classic-tunnels-family/`:

- `SOURCE_ARCHITECTURE.md`
- `SSTP_CLIENT.md`
- `DEPENDENCIES_SECURITY_TESTS.md`
- `SUPPORT_REUSE_DECISIONS.md`

## Numbered evidence created

- `research/protocols/009-l2tpv3/V1_RESEARCH.md`
- `010-l2tpv3-ipsec/V1_RESEARCH.md`
- `011-sstp/V1_RESEARCH.md`
- `012-pptp/V1_RESEARCH.md`

## Main decisions

### 009 L2TPv3

`ADVANCED SITE-TO-SITE / KERNEL-OR-OS-IMPLEMENTATION FIRST / LOW CONSUMER PRIORITY`

Plain L2TPv3 is tunneling/encapsulation, not confidentiality.

### 010 L2TPv3/IPsec

`ADVANCED PROTECTED SITE-TO-SITE COMPOSITION / REUSE IPSEC SECURITY MODEL`

Model L2TPv3 pseudowire separately from the shared IPsec/IKE protection layer.

### 011 SSTP

`COMPATIBILITY REMOTE-ACCESS TARGET / WINDOWS-NATIVE-FIRST / LINUX SSTP-CLIENT CANDIDATE`

Windows native stack is preferred first. Linux research uses `sstp-client`/NetworkManager direction. `sstp-client` root COPYING is GPLv2-family and requires deliberate closed-product distribution architecture.

### 012 PPTP

`LEGACY / INSECURE / OPTIONAL COMPATIBILITY ONLY`

Never recommended/default/fallback. Preserve only for explicit legacy demand.

## Current security/deprecation context

Current Microsoft documentation/guidance favors modern IKEv2/SSTP choices over older PPTP/L2TP families, reinforcing the product rule that PPTP belongs under legacy/insecure compatibility rather than normal secure onboarding.

Linux kernel L2TP documentation confirms L2TPv3's kernel pseudowire/networking role rather than a generic consumer VPN abstraction.

## Residual gaps

- exact immutable `sstp-client` source/tag and dependency versions;
- exact PPTP historical/current source pin/package landscape;
- Linux kernel/iproute2 L2TPv3 package/version matrix;
- exact Windows SSTP provisioning/profile API matrix;
- current issue/regression sampling;
- Android/Apple SSTP feasibility;
- full install/server/menu/crypto/wire-flow evidence deferred to mandatory v2.

## Next exact action

Continue original v1 immediately. Select the next unfinished modern-proxy group from actual repository state, prioritizing entries **044 TUIC, 045 AnyTLS, 046 ShadowTLS, 047 NaiveProxy, 048 Snell, then generic proxy/SSH/Tor entries as appropriate**. Reuse shared engine/client evidence where valid but keep each protocol distinct. Do not begin mass v2 yet.
