# Classic / Legacy Tunnel Family — PVNetwork Support / Reuse Decision Record

Decision date: 2026-08-14

State: research architecture decision only. No PVNetwork implementation/certification exists.

## 009 — L2TPv3

Research classification:

**`ADVANCED SITE-TO-SITE / KERNEL-OR-OS-IMPLEMENTATION FIRST / LOW CONSUMER PRIORITY`**

Preferred research direction:

- Linux kernel L2TP subsystem for Linux data plane;
- typed userspace configuration/service layer rather than a new user-space encapsulation engine;
- SoftEther/server/router implementations as interoperability references.

Security rule:

plain L2TPv3 is encapsulation, not confidentiality. Do not market it as encrypted VPN protection.

Product placement:

Advanced/server/site-to-site, not normal consumer Connect onboarding.

Product claim today: none.

---

## 010 — L2TPv3/IPsec

Research classification:

**`ADVANCED PROTECTED SITE-TO-SITE COMPOSITION / REUSE IPSEC SECURITY MODEL`**

Preferred architecture:

`L2TPv3 pseudowire`

`+ existing PVNetwork IPsec/IKE capability/backend`

Do not duplicate PSK/certificate/IKE proposal fields inside a second opaque profile model.

On Linux, a likely architecture is kernel L2TPv3 plus strongSwan/kernel IPsec, subject to exact distro/package/capability tests. SoftEther/router implementations remain interoperability references.

Product claim today: none.

---

## 011 — SSTP

Research classification:

**`COMPATIBILITY REMOTE-ACCESS TARGET / WINDOWS-NATIVE-FIRST / LINUX SSTP-CLIENT CANDIDATE`**

Preferred direction:

- Windows: built-in SSTP/RAS stack first;
- Linux: evaluate `sstp-client/sstp-client` and NetworkManager integration;
- other platforms: unsupported until a maintained, legally compatible, Store-compatible engine is proven.

Open-source `sstp-client` root license reviewed as GPLv2 family, so closed-product distribution architecture needs deliberate review.

SoftEther's SSTP module is primarily server compatibility evidence and is not automatically the preferred PVNetwork SSTP client engine.

Product claim today: none.

---

## 012 — PPTP

Research classification:

**`LEGACY / INSECURE / OPTIONAL COMPATIBILITY ONLY`**

PVNetwork policy:

- never recommended as a modern secure VPN;
- never automatic fallback;
- disabled/hidden by default unless a legacy compatibility build/setting explicitly enables it;
- prominent warning when used;
- no promise on platforms that have removed/deprecated the stack;
- no investment in a custom protocol/crypto implementation.

If later user demand justifies support, prefer existing OS/historical maintained components after source/license/security review.

Product claim today: none.

---

# Shared product UX rule

Normal user connection list should not present these four with equal security/priority.

Recommended grouping:

### Compatibility remote access

- SSTP, only on supported/certified platforms.

### Advanced / site-to-site

- L2TPv3
- L2TPv3/IPsec

### Legacy / insecure

- PPTP

## Storage/security rule

Keep separate:

- endpoint/tunnel/session metadata;
- user authentication;
- IPsec security credentials/settings when applicable;
- platform/backend references;
- protected secrets;
- transient runtime state.

Never persist passwords/PSKs/private keys in ordinary plaintext profile JSON.

## Backend minimization

Do not add dedicated engines unnecessarily:

- use native OS/kernel L2TPv3 where appropriate;
- reuse the existing approved IPsec adapter for 010;
- use native Windows SSTP where possible;
- use one maintained Linux SSTP component where needed;
- avoid shipping PPTP unless actual compatibility demand exists.

## Family v1 closure position

Current research has enough original-v1 evidence to preserve architecture/security/reuse decisions and move on while exact package/source/dependency details remain explicit residual gaps.

## Residual gaps

- exact immutable `sstp-client` commit/tag and dependency matrix;
- exact PPTP historical/current source pin and package landscape;
- Linux L2TPv3 kernel/iproute2 package/version matrix;
- exact Windows SSTP API/profile/menu behavior;
- current issue/regression sampling;
- Android/Apple SSTP feasibility;
- full install/menu/server/crypto/wire-flow work deferred to mandatory v2.
