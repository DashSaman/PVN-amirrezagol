# Classic / Legacy Tunnel Family — L2TPv3, SSTP, PPTP Source Architecture

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

Entries covered:

- 009 L2TPv3
- 010 L2TPv3/IPsec
- 011 SSTP
- 012 PPTP

These are grouped only for research efficiency. They are **not one protocol family at runtime** and must remain independent PVNetwork capabilities.

## 009 — L2TPv3

Classification: Layer-2 tunneling/data pseudowire technology, primarily site-to-site/provider/router/server oriented rather than a typical consumer remote-access VPN.

Primary open-source implementation/reference directions:

- Linux kernel L2TP subsystem and userspace netlink/API tooling;
- SoftEther source/server/bridge support where applicable;
- router/network OS implementations for interoperability reference.

### PVNetwork architecture direction

Do not build a portable L2TPv3 packet engine from scratch for the consumer super-client.

Treat L2TPv3 as an advanced/site-to-site capability and prefer mature OS/kernel/server implementations.

On Linux, the kernel networking subsystem is a primary reference/candidate for data path. Product UI would configure an approved local helper/service rather than implement pseudowire encapsulation inside the desktop GUI.

### Security classification

Plain L2TPv3 provides tunneling/encapsulation, not modern confidentiality by itself. Do not market it as encrypted VPN protection unless paired with a separate security layer such as IPsec.

## 010 — L2TPv3/IPsec

Classification: composition:

`L2TPv3 pseudowire/tunnel`

`+ IPsec/IKE protection`

Primary implementation/reference directions:

- Linux kernel L2TPv3 + strongSwan/kernel IPsec;
- SoftEther server/bridge implementation/reference;
- router/vendor site-to-site implementations.

### PVNetwork architecture direction

Reuse the existing typed IPsec capability model for the security layer. Do not duplicate PSK/certificate/IKE fields inside a separate opaque L2TPv3-IPsec blob.

Likely belongs to advanced/server/site-to-site modules rather than normal mobile onboarding.

## 011 — SSTP

Classification: Microsoft-originated SSL/TLS-based VPN tunneling protocol.

Primary client/reference directions:

- native Windows SSTP VPN stack for Windows;
- open-source `sstp-client/sstp-client` for Linux/Unix research;
- SoftEther server SSTP compatibility as a server/interoperability reference.

### Important client/server distinction

SoftEther containing `Proto_SSTP.c` is server-side compatibility evidence. It does not automatically make SoftEther the preferred SSTP client on every OS.

### PVNetwork direction

- Windows: native OS SSTP is the preferred first research path for standard profiles;
- Linux: evaluate `sstp-client`/NetworkManager integration and source/license state;
- Android/iOS/macOS: do not promise SSTP until an acceptable engine/platform integration is proven.

SSTP should have its own adapter/capability because TLS/proxy/certificate/platform semantics differ from OpenVPN.

## 012 — PPTP

Classification: legacy VPN protocol family.

Research direction:

**`LEGACY / INSECURE / COMPATIBILITY-ONLY`**

PPTP and its historical authentication/encryption stack are not suitable as a recommended secure VPN choice for a modern PVNetwork product.

### Product rule

- never default to PPTP;
- never advertise it as modern secure VPN;
- mark legacy/insecure status prominently;
- only consider an optional legacy compatibility module if real customer/server demand justifies it and the target OS still permits it;
- do not weaken product Store/security posture merely to preserve obsolete compatibility.

Primary historical/open-source references include Linux PPTP client implementations and OS/vendor native legacy stacks. Exact current source/license pins remain to be recorded if PVNetwork decides this low-priority module is worth implementation.

## Common UI rule

Simple Mode should not list L2TPv3, L2TPv3/IPsec and PPTP next to modern consumer VPNs without context.

Recommended product grouping:

### Standard/normal remote access

SSTP only where certified and relevant.

### Advanced / Site-to-Site

- L2TPv3
- L2TPv3/IPsec

### Legacy / Insecure compatibility

- PPTP

## Configuration storage

For every capability keep separate:

- endpoint/session metadata;
- authentication/security credentials;
- security-layer settings;
- OS/kernel helper/backend settings;
- original imported source;
- transient runtime/session state.

Do not store PSKs/passwords/private keys in ordinary plaintext profile JSON.

## Platform feasibility rule

One implementation on Linux/Windows is not proof of support on:

- Android;
- Android TV;
- iOS/iPadOS;
- macOS;
- Windows Store build;
- arbitrary Linux distributions.

Each platform needs explicit engine/API/package evidence.

## Later v2 work

Mandatory `COMPLETE-REFERENCE-v2` must add:

- server/client implementations and forks;
- server installers/panels;
- OS install matrices;
- complete server/client menus;
- cryptography/security status;
- wire/data flow;
- ports/handshake;
- deployment topologies;
- exact PPTP legacy-security evidence and warnings.
