# IKE / IPsec — Server and Peer Implementations

Review date: 2026-08-14

Scope: entries 004 IKEv2/IPsec, 005 IKEv1/IPsec, 006 ESP and 007 AH.

“Server” is an operational role. The implementation inventory must identify which component negotiates IKE and which OS/kernel/userspace component actually installs/processes IPsec SAs and policies.

## 1. strongSwan

Pinned reviewed release: `strongswan/strongswan@5973ff8e41deef4e015e1138a2de688acedf6f75` (`6.0.7`).

License: GPLv2-family root `COPYING` as recorded in the parent dossier.

### Roles

- `libcharon` / Charon front ends: IKEv1/IKEv2 session/control and CHILD_SA lifecycle;
- `libstrongswan`: shared crypto/credential/plugin infrastructure;
- kernel backend plugins: install negotiated IPsec SAs/policies into the operating-system data plane where applicable;
- VICI / `swanctl`: modern control/configuration boundary;
- Android frontend: separate application/VpnService integration.

### Entry relevance

- 004 IKEv2: first-class implementation/candidate;
- 005 IKEv1: compatibility/legacy implementation, not a reason to make IKEv1 a product default;
- 006 ESP: negotiated and installed through OS/kernel IPsec backends rather than a normal standalone user profile;
- 007 AH: implementation capability subject to exact backend/kernel/plugin/platform support; low remote-access priority.

### PVNetwork role

Primary Linux/advanced interoperability reference and candidate engine behind a process/service adapter. GPL and privileged-daemon/plugin architecture require deliberate distribution and integration design.

## 2. Libreswan

Pinned reviewed release: `libreswan/libreswan@5eb03b7772b312e705feab9ad5868678a3c007e6` (`v5.4`).

Annotated tag object: `0b94f1a582d979303e9e8ff1e5452cc5b2c49ec8`.

Root `COPYING`: GNU GPL version 2 text.

### Roles

Libreswan is a serious alternative IPsec/IKE implementation centered on its IKE daemon and OS kernel IPsec integration. The reviewed v5.4 release notes show current work across:

- IKEv2 exchanges and authentication;
- multiple-key-exchange/RFC 9370 support;
- ML-KEM integration using an NSS dependency version floor for that feature;
- Linux and BSD kernel integration;
- VTI/XFRM diagnostics;
- configuration, DNS-resolver integration, build hardening and test platforms.

### Entry relevance

- 004: major interoperable IKEv2 server/gateway implementation;
- 005: legacy IKEv1 compatibility must be documented feature-by-feature, not assumed from IKEv2;
- 006/007: relies on host IPsec data-plane support and configured SAs/policies.

### PVNetwork role

Important interoperability/server alternative and issue/regression reference. It is not selected as a PVNetwork embedded client engine merely because it is current or feature-rich. GPL architecture and platform fit must be evaluated separately.

## 3. Native operating-system IPsec stacks

### Linux kernel XFRM/IPsec

On Linux, the kernel normally owns ESP/AH SA/policy processing while an IKE daemon such as strongSwan or Libreswan performs negotiation/control. This split is fundamental to entries 004–007.

Role: data-plane implementation and routing/security-policy target, not an IKE user interface.

### Windows native IKE/IPsec/VPN

Windows provides native IKEv2/IPsec VPN and IPsec policy capabilities. For standard remote-access IKEv2 profiles, the parent dossier's direction is native-first where the required profile can be represented.

Role: client and potentially gateway/policy platform depending Windows edition/server configuration; exact supported transforms/auth methods and management surface are OS/version/policy specific.

Do not infer IKEv1 parity from IKEv2 support.

### Apple NetworkExtension / native IPsec

Apple platforms expose native IKEv2 through NetworkExtension APIs. This is the preferred research direction for normal App Store-aligned IKEv2 client use when features fit.

Role: client-side OS-managed IKE/IPsec implementation. It is not a general strongSwan-equivalent plugin surface and must not be treated as generic IKEv1/AH support.

### Android native IKEv2

Modern Android exposes OS-managed IKEv2 profile/VPN APIs on supported API levels. Android also has the separately reviewed strongSwan Android frontend/VpnService path.

Role: client backend option; API-level and profile-feature constraints matter.

## 4. Network appliances and vendor stacks

Commercial firewalls, routers, cloud VPN gateways and operating-system network stacks implement IKE/IPsec extensively. These are important interoperability targets but cannot be treated as one implementation family.

For the v2 reference, vendor implementations should be recorded as interoperability/server targets by exact product/version/documentation when selected for a certification matrix. Examples of categories include:

- enterprise firewall/VPN gateways;
- cloud managed VPN gateways;
- router OS IPsec stacks;
- Unix/BSD native IPsec stacks.

No vendor is marked “supported” merely because it speaks standards-based IKE/IPsec.

## 5. Control-plane and management products

Products such as OPNsense/pfSense and NetworkManager front ends may configure IPsec/IKE implementations. They are management/control surfaces, not new cryptographic protocol implementations.

The v2 server-UI and installer files must therefore record separately:

- underlying IKE engine;
- underlying ESP/AH data plane;
- UI/config database;
- privilege boundary;
- update/package ownership;
- exposed management interfaces.

## 6. Entry-specific selection direction

### 004 IKEv2/IPsec

`HIGH PRIORITY / NATIVE-OS-FIRST WHERE CAPABLE / STRONGSWAN FOR LINUX+ADVANCED INTEROP`

Alternative server/interoperability implementation: Libreswan.

### 005 IKEv1/IPsec

`LEGACY COMPATIBILITY / NOT DEFAULT / NO SILENT DOWNGRADE`

StrongSwan/Libreswan are relevant server/reference implementations. Native client availability must be established per OS/version; do not infer it.

### 006 ESP

`FOUNDATIONAL DATA PLANE / OBTAIN THROUGH MATURE OS IPSEC STACK`

Use kernel/native IPsec and mature negotiated SA management. Do not implement ESP transforms from scratch.

### 007 AH

`ADVANCED / LOW REMOTE-ACCESS PRIORITY / NON-CONFIDENTIALITY DATA PLANE`

Only expose if an exact backend/server/platform combination is evidenced and tested. Never use as an invisible fallback from ESP.

## 7. Remaining implementation-inventory work

Still to deepen in later slices:

- exact current FreeBSD/OpenBSD native implementation/package distinctions;
- selected OPNsense/pfSense engine and source/license pins;
- selected cloud/vendor gateway version matrix;
- container images and orchestration ownership for strongSwan/Libreswan;
- runtime interop receipts for selected server/client combinations.

These gaps do not invalidate the canonical strongSwan/Libreswan/native architecture inventory above.
