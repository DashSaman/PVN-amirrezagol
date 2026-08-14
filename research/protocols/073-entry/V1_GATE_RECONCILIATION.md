# 073 — Cisco GETVPN — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **073 — GETVPN**

Decision: **`COMPLETE-RESEARCH-v1 / CISCO PROPRIETARY INFRASTRUCTURE VPN FEATURE SET / GROUP IPSEC / NOT OPEN SOURCE / NOT IMPLEMENTED / NOT CERTIFIED`**

## Primary evidence reviewed

### Current standards baseline

- RFC 9838 — *Group Key Management Using the Internet Key Exchange Protocol Version 2 (IKEv2)*, November 2025, IETF Proposed Standard:
  `https://www.rfc-editor.org/info/rfc9838/`
  - defines G-IKEv2 group key management;
  - provides Group Controller/Key Server (GCKS) registration and group rekeying for authorized Group Members (GMs) and IPsec Group Security Associations;
  - **obsoletes RFC 6407**.
- RFC 6407 — *The Group Domain of Interpretation*:
  `https://www.rfc-editor.org/info/rfc6407/`
  - historical/current-compatibility evidence for GDOI deployments;
  - explicitly obsolete after RFC 9838 and therefore not presented as the current standards endpoint.
- RFC 3547 — original GDOI specification, already obsoleted by RFC 6407 and therefore historical only.

### Cisco product authority

- Cisco IOS XE 17.x — *Cisco Group Encrypted Transport VPN*:
  `https://www.cisco.com/c/en/us/td/docs/routers/ios/config/17-x/sec-vpn/b-security-vpn/m_sec-get-vpn.html`
- Cisco IOS XE 17.x — *GETVPN G-IKEv2*:
  `https://www.cisco.com/c/en/us/td/docs/routers/ios/config/17-x/sec-vpn/b-security-vpn/m_sec-get-vpn-gikev2.html`
- Cisco IOS XE — *Perfect Forward Secrecy for GETVPN*:
  `https://www.cisco.com/c/en/us/td/docs/routers/ios-xe/security-vpn/security-vpn/m-pfs-for-getvpn.html`
- Cisco IOS XE — *GET VPN Interoperability*:
  `https://www.cisco.com/c/en/us/td/docs/routers/ios-xe/security-vpn/security-vpn/m_sec-conn-getvpn-interop.html`
- Cisco IOS XE 17.x — *GETVPN GDOI Bypass*:
  `https://www.cisco.com/c/en/us/td/docs/routers/ios/config/17-x/sec-vpn/b-security-vpn/m_sec-get-vpn-gdoi-bypass.html`
- Cisco support — *Troubleshoot Common GETVPN Issues*:
  `https://www.cisco.com/c/en/us/support/docs/security/group-encrypted-transport-vpn/116958-troubleshoot-getvpn-00.html`
- Cisco current contract portal / software terms:
  `https://www.cisco.com/c/en/us/about/legal/cloud-and-software/software-terms.html`
  - Cisco states its General Terms replaced the legacy EULA effective 2024-02-05; exact purchased entitlement/offer terms remain the shipping legal authority.

## Identity and proprietary boundary

Cisco GETVPN is a Cisco IOS/IOS XE **vendor feature set**, not a publicly sourced standalone implementation repository. Cisco documents GETVPN as group-encrypted transport for IP multicast/unicast on enterprise private WANs. It applies IPsec protection to native/nontunneled IP traffic and distributes group policy/keying through GDOI or G-IKEv2 depending the deployed feature/profile.

The open IETF specifications for GDOI/G-IKEv2 and IPsec **do not make Cisco GETVPN open source**. No public canonical Cisco IOS XE GETVPN source repository, source tree, implementation language, build recipe, reproducible package build or source license was identified in the reviewed official material. Those facts are therefore recorded as proprietary/unavailable rather than invented.

## Architecture and security model

- **Key Server (KS/GCKS):** control-plane authority defining group policy, traffic selectors, group SAs, rekey policy and authorization.
- **Group Member (GM):** registers/authorizes with the KS, receives policy and key material, and performs the protected data-plane processing.
- **Key management:** legacy/current-compatibility GDOI (IKEv1-era group DOI) or G-IKEv2, with RFC 9838 now the current IETF G-IKEv2 standards endpoint.
- **Data protection:** group IPsec SAs/TEKs protect native unicast/multicast traffic without requiring GRE-style point-to-point tunnel encapsulation.
- **Rekey/control protection:** KEK/rekey and registration/authentication state are independent from traffic selectors/TEKs.
- **Authorization:** Cisco documents PSK and PKI authorization; authorization is important when a KS serves more than one group so a GM cannot simply request another group's policies/keys.
- **Cleartext/bypass:** GETVPN has explicit bypass and fail-close behavior. A product UI or migration tool must never equate “GM registered” with “all traffic encrypted”; local/global policy and fail-close/bypass state must be visible.

## 20-gate reconciliation

| # | Gate | Result | Evidence / conclusion |
|---:|---|---|---|
| 1 | Top clients / implementations | PASS | Cisco IOS/IOS XE GETVPN is the canonical product implementation selected for this entry. Cisco's current GET VPN interoperability documentation acknowledges version/third-party interoperability behavior, but no public maintained third-party GETVPN implementation is promoted without independent evidence. |
| 2 | Repository / source identification | PASS/N-A-PROPRIETARY | Cisco product/configuration/release documentation and IETF RFCs are authoritative. No public Cisco IOS XE GETVPN source repository exists in the reviewed official evidence; therefore no fake GitHub repository/tag/commit is assigned. RFC 9838 is the current group-key standards baseline; RFC 6407/3547 are explicitly historical/obsolete. |
| 3 | Licensing / legal reuse | PASS | Cisco software is proprietary and governed by Cisco's current General Terms plus applicable entitlement/offer terms; it is not assigned an OSI license. No right to redistribute, modify, reverse engineer, sublicense, copy implementation code, or obtain source is inferred. IETF RFC rights apply to specification text and do not grant Cisco implementation-source rights. Trademark/branding rights are not inferred. PVNetwork reuse decision is vendor integration/interoperability only, subject to the exact shipping Cisco entitlement. |
| 4 | Source-tree review | PASS/N-A-PROPRIETARY | A recursive source tree, source manifest and build tree are unavailable for the Cisco GETVPN implementation. This is an evidence-backed proprietary N/A, not an omitted search. The reviewed replacement evidence is Cisco's current feature/configuration/troubleshooting documentation plus standards; no source-tree completeness claim is fabricated. |
| 5 | Languages / build / dependencies / packaging | PASS/N-A-PROPRIETARY | Cisco does not publish GETVPN's implementation language/build/dependency graph in the reviewed product documentation. Product availability is tied to supported Cisco IOS/IOS XE images, platforms, licensing and feature/release support; exact platform/image support must be pinned at deployment. No implementation language or reproducible build is guessed. |
| 6 | Internal architecture / data flow | PASS | KS/GCKS control plane -> GM registration/authorization -> policy + group SA/key distribution -> GM native IPsec data plane -> rekey/maintenance. GDOI/G-IKEv2 control state, TEK/data SA, KEK/rekey, ACL/selectors, fail-close/bypass and routing are modeled separately. |
| 7 | Engine integration points | PASS | PVNetwork must treat GETVPN as an external Cisco-managed infrastructure capability, not embed/rewrite Cisco code. Integration can be through documented device configuration/management/telemetry interfaces selected later; core cryptography/key management stays in Cisco IOS/XE. A future native non-Cisco implementation would require a separate standards-based engineering project and must not be called Cisco GETVPN merely because it implements G-IKEv2/IPsec. |
| 8 | UI / settings map | PASS/N-A-CONSUMER | No canonical consumer/mobile GETVPN client UI exists. If PVNetwork exposes it, place under Advanced / Infrastructure / Enterprise VPN with explicit KS/GM role, group, registration, authorization, policy, SA/rekey, fail-close/bypass and health states. Consumer one-click VPN UI is N/A. |
| 9 | Configuration / import / export / URI / QR | PASS | Typed fields include device/role, group name/ID, KS addresses, GDOI vs G-IKEv2 mode, IKE/auth profile, PSK or PKI references, authorization identity/address policy, group ACL/selectors, transform/TEK/KEK/rekey/lifetimes, anti-replay, COOP/PFS/capability state, crypto-map/interface/VRF binding, fail-close/bypass and version/capability data. No canonical GETVPN consumer subscription URI/QR format was identified; Cisco CLI dumps are not portable secret-safe profiles. |
| 10 | Persistence / secrets | PASS | PSKs, private keys, certificate private material, KEKs/TEKs and active group/IKE/IPsec keying state are secrets and must not enter ordinary config exports or support logs. Persistent desired policy is separate from runtime registration/SAs/rekey state. Device-side secure key storage and exact credential ownership remain implementation-specific. |
| 11 | Platforms / device implementation | PASS for research | Cisco IOS/IOS XE network devices are the documented platform family. Exact router model, IOS XE train/image, entitlement and feature compatibility are deployment-time pins; Cisco documentation directs operators to current feature information. Mobile/desktop consumer OS support is N/A for this infrastructure entry. |
| 12 | Logs / diagnostics / failure mapping | PASS | Diagnostics are split into IKE/GDOI/G-IKEv2 establishment, registration/auth/authorization, KS reachability/COOP, policy/ACL download/merge, TEK/KEK/rekey/lifetime, replay/PFS, IPsec SA/data plane, fail-close/bypass and version/interoperability domains. Cisco documents `show crypto gkm ...`, GDOI/GKM state, syslogs and dedicated troubleshooting flows; secret key material must be redacted. |
| 13 | Assets / icons / localization | PASS/N-A | No canonical end-user GETVPN application assets, QR screens, Store listing art or localization bundle applies. Cisco logos/trademarks are not reusable product assets by default. |
| 14 | Forks / alternatives / variants | PASS | GDOI and G-IKEv2 are key-management generations/modes, not open-source GETVPN forks. COOP KS, PFS, IPv6 data plane and interoperability versions are product capabilities. DMVPN (071), Cisco FlexVPN (072), ordinary point-to-point IPsec and standards-only G-IKEv2 are distinct architectures/entries, not aliases. |
| 15 | Issues / releases / advisories | PASS | Current Cisco IOS XE 17.x documentation records material operational/version caveats: on IOS XE >=17.11, legacy 3DES rekey defaults from earlier releases can prevent GM rekey until AES is configured; unsupported terminal `deny` behavior in KS GETVPN ACLs can corrupt GM policy/produce undefined behavior with affected XE trains; current PFS documentation warns force rekey can cause traffic loss from key mismatch and notes scale/load implications. These prove exact image/release review is mandatory without making a runtime lab a hidden V1 gate. |
| 16 | Official docs / support authority | PASS | Current Cisco IOS XE configuration guides, current Cisco support feature guides/troubleshooting, Cisco software terms and RFC 9838 are primary. RFC 6407/3547 are retained only for legacy GDOI history/interoperability. Random tutorials/forums are not used to close gates. |
| 17 | Tests / CI / quality evidence | PASS/N-A-PROPRIETARY | Cisco internal source CI and build test systems are not public and no fake test results are created. Vendor configuration/verification/troubleshooting commands and compatibility/release evidence define the research surface. PVNetwork lab/device/packet/interoperability certification remains a later implementation/acceptance phase unless a future formal contract requires it. |
| 18 | Store / privacy / security | PASS | Consumer Store distribution is N/A. Security-critical state includes KS trust, GM authorization, PSK/PKI credentials, group keys/SAs, selectors, rekey, replay protection, PFS and fail-close/bypass. Cisco recommends/implements explicit GM authorization mechanisms; product UX must surface bypass/unprotected states and must not export keys. Device telemetry/support data can reveal topology/identity and requires minimization/redaction. |
| 19 | Reuse / rewrite / hybrid decision | PASS | **VENDOR-INTEGRATION / INTEROPERABILITY ONLY** for Cisco GETVPN. Do not copy/rewrite proprietary IOS/XE implementation code, do not invent source availability, and do not market a standards-derived independent group-IPsec implementation as Cisco GETVPN. Reuse completed IPsec research for semantic modeling while Cisco remains the implementation owner. |
| 20 | Open uncertainties / blockers | PASS | Later evidence must pin exact supported Cisco device/image/license, GDOI↔G-IKEv2 migration/capability matrix, COOP/PFS/version interoperability, management API chosen by PVNetwork, hardware scale/performance, exact cryptographic recommendations, packet/fail-close behavior and V2 UI/CLI/wire/deployment references. None of these are unresolved facts required to make the V1 architectural/reuse decision. |

## Current standards correction receipt

Any older project text that treats RFC 6407 as the current endpoint must be read as legacy GDOI evidence. **RFC 9838 (November 2025) now obsoletes RFC 6407** and is the current reviewed IETF G-IKEv2 group-key-management standard. This correction is intentionally recorded to prevent future agents from silently reverting to the stale standards baseline.

## Final V1 decision

All 20 `COMPLETE-RESEARCH-v1` gates are evidence-backed or explicitly and correctly bounded as proprietary/infrastructure N/A. Cisco GETVPN remains proprietary; source code/build internals are not fabricated, and runtime/device interoperability is not used as a hidden Research Completion requirement.

Entry 073 therefore qualifies for **`COMPLETE-RESEARCH-v1`** while remaining **not implemented / not runtime-certified / not device-certified**.
