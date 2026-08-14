# Vendor Enterprise VPN Family — PVNetwork Support / Reuse Decisions

Decision date: 2026-08-14

State: research architecture decision only. No PVNetwork implementation/certification exists.

Entries covered:

- 025 Check Point VPN
- 026 SonicWall NetExtender / SSL VPN
- 027 SonicWall Global VPN / IPsec
- 028 Sophos SSL VPN
- 029 Sophos IPsec Remote Access
- 030 WatchGuard IKEv2 VPN
- 031 WatchGuard SSL VPN
- 032 WatchGuard L2TP VPN
- 033 Aruba VIA
- 034 Citrix Secure Access / Gateway VPN
- 035 Barracuda TINA VPN
- 036 Juniper Secure Connect

## Core rule

A vendor product name is not automatically a unique wire protocol, and the existence of one standards-based mode does not imply all vendor features are compatible with generic clients.

PVNetwork support must be version/vendor/capability based.

---

## 025 — Check Point VPN

Primary official reference: Check Point remote-access clients/appliances.

Open-source technical reference: `ancwrd1/snx-rs`.

Current research records `snx-rs` as a Rust implementation supporting Check Point-related SSL/IPsec client behavior, with AGPL-3.0 licensing.

Decision:

**`VALUABLE OPEN-SOURCE INTEROPERABILITY REFERENCE / AGPL DIRECT-EMBED CAUTION`**

Do not copy/embed AGPL code into a closed PVNetwork product without an intentional compatible architecture/legal model. Use it to understand protocol behavior, authentication, SSO/MFA, platform issues and tests; separately evaluate whether a clean-room/standards/native implementation is feasible.

## 026 — SonicWall NetExtender / SSL VPN

Decision:

**`VENDOR-SPECIFIC COMPATIBILITY TARGET / OFFICIAL CLIENT PRIMARY / NO MATURE GENERAL OSS DROP-IN ASSUMED`**

Do not claim support until a maintained reusable implementation or documented compatible standard mode is proven by exact gateway/version tests.

## 027 — SonicWall Global VPN / IPsec

Decision:

**`VENDOR IPSEC INTEROPERABILITY TARGET / REUSE IPSEC MODEL WHERE STANDARD SEMANTICS MATCH`**

Use the shared IPsec/IKE capability model for standards-based portions, but do not assume generic IKE support covers all SonicWall policy/mode/vendor extension behavior.

## 028 — Sophos SSL VPN

Decision:

**`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST WHERE CURRENT SOPHOS PROFILE IS STANDARD-COMPATIBLE`**

Sophos SSL VPN deployments commonly expose OpenVPN-style profile/client behavior. PVNetwork should route compatible `.ovpn` profiles through the OpenVPN Adapter rather than add a separate Sophos SSL engine.

Exact current Sophos Firewall/version/profile compatibility remains mandatory before certification.

## 029 — Sophos IPsec Remote Access

Decision:

**`IPSEC INTEROPERABILITY TARGET / NATIVE-OS OR STRONGSWAN BACKEND`**

Reuse the shared IKE/IPsec profile/backends and certify exact Sophos Firewall/profile versions. Do not build a separate IPsec cryptographic stack.

## 030 — WatchGuard IKEv2 VPN

Decision:

**`STANDARD IKEV2 INTEROPERABILITY TARGET / NATIVE-OS-FIRST`**

Use the shared IKEv2/IPsec backend model; certify exact Firebox/profile/server versions.

## 031 — WatchGuard SSL VPN

Decision:

**`SSL-VPN COMPATIBILITY TARGET / PROFILE-PROTOCOL DETECTION REQUIRED`**

Where the deployed WatchGuard profile is OpenVPN-compatible, route through the OpenVPN Adapter. Do not assume all WatchGuard SSL VPN generations/options are identical without profile/server evidence.

## 032 — WatchGuard L2TP VPN

Decision:

**`LEGACY L2TP/IPSEC INTEROPERABILITY TARGET / NATIVE COMPOSED STACK`**

Reuse the existing L2TP/IPsec layered model. Mark legacy status consistently with entry 008.

## 033 — Aruba VIA

Decision:

**`VENDOR REMOTE-ACCESS TARGET / OFFICIAL CLIENT PRIMARY UNTIL REUSABLE IMPLEMENTATION PROVEN`**

Do not infer support solely from underlying IPsec/SSL concepts. Aruba-specific provisioning/posture/authentication/server behavior may require official client semantics.

## 034 — Citrix Secure Access / Gateway VPN

Decision:

**`VENDOR-SPECIFIC REMOTE-ACCESS TARGET / OFFICIAL CLIENT PRIMARY`**

No mature generic open-source drop-in is assumed at this research stage. Keep as vendor compatibility research rather than inventing a protocol implementation.

## 035 — Barracuda TINA VPN

Decision:

**`VENDOR-SPECIFIC TINA TARGET / OFFICIAL CLIENT PRIMARY / NO CUSTOM IMPLEMENTATION WITHOUT SOURCE+SPEC EVIDENCE`**

TINA is vendor-specific. Do not reverse-engineer/implement from incomplete behavior when no reusable licensed implementation/spec is available.

## 036 — Juniper Secure Connect

Decision:

**`VENDOR REMOTE-ACCESS TARGET / STANDARD IPSEC REUSE ONLY WHERE EXACT MODE IS DOCUMENTED`**

Juniper Secure Connect is separate from older Juniper Network Connect/OpenConnect compatibility. Use native/strongSwan IPsec only for configurations demonstrably based on standard IKE/IPsec semantics; otherwise keep official-client compatibility status.

---

# Product capability states

For every vendor target use states such as:

- Official-client only
- Standard-protocol compatible and tested
- Open-source interoperability candidate
- Experimental
- Unsupported
- Vendor/version-specific limitation

Never expose one broad “Vendor supported” checkbox.

# Authentication / SSO / posture rule

Enterprise VPN compatibility can fail even when the tunnel wire protocol is understood because of:

- SAML/browser SSO;
- MFA/OTP;
- device certificate selection;
- host-check/posture;
- vendor provisioning portals;
- user-agent/platform checks;
- split-tunnel policy;
- endpoint-compliance plugins.

PVNetwork certification must test authentication/provisioning and data tunnel separately.

# Reuse strategy

Prefer reuse of already-approved standard cores when the vendor profile is genuinely standard-compatible:

- OpenVPN3 for compatible SSL/OpenVPN profiles;
- native/strongSwan IPsec for compatible IKE/IPsec profiles;
- OpenConnect for the vendor families already proven in entries 016–024;
- vendor official client only where no reusable implementation exists.

Do not add vendor-specific engines without source/license/maintenance/security evidence.

# Residual v1 gaps

- exact current official vendor product/version matrices;
- source/license/current activity for every third-party interoperability project;
- current SSO/MFA/posture capability matrix;
- client menu/storage/update/Store behavior;
- issues/security/regression sampling;
- precise standard-vs-proprietary protocol boundaries.

Mandatory v2 later adds server/appliance installation/deployment references where applicable, full client/server/admin menus, cryptography/wire flow and topology evidence.