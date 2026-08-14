# PVNetwork Agent Handoff — V1 Gate Reconciliation 14

Date: 2026-08-14

## Campaign state

- Active phase: `COMPLETE-RESEARCH-v1`
- Completed: **33 / 93**
- V2 remains hard-locked until 93/93.

## Newly completed entry

### 033 — HPE Aruba Networking VIA

State: `COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT VENDOR-CERTIFIED`

Evidence:

- `research/protocols/033-aruba-via/V1_RESEARCH.md`
- `research/protocols/033-aruba-via/ARUBA_VIA_CURRENT_AUDIT.md`
- `research/protocols/033-aruba-via/V1_GATE_RECONCILIATION.md`
- shared IKE/IPsec evidence under entries 004–007 and `research/upstreams/strongswan-family/` only where exact standards semantics match.

Decision:

`VENDOR REMOTE-ACCESS REFERENCE / HPE FIRST-PARTY CLIENT REFERENCE-ONLY / STANDARD IKE-IPSEC REUSE ONLY FOR EXACT LAYERS / ARUBA PROFILE+ROLE+AUTH+ROUTING+LIFECYCLE REMAIN DISTINCT / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`

Current baseline:

- VIA Help Center lists release families through VIA 4.7.6;
- Windows/macOS/Linux/Android/iOS documented;
- IKEv1/IKEv2, certificates, EAP-TLS/EAP-MSCHAPv2, MFA/smart-card flows;
- HTTPS/profile provisioning, role/policy orchestration, split/full tunnel, DNS, updates and supported desktop SSL fallback are Aruba-specific product layers;
- complete first-party source is proprietary; HPE OSS notices apply component-by-component and do not make the whole product reusable.

## Exact next entry

**034 — Citrix Secure Access / Gateway VPN**

Required sequence:

1. Resolve/use the real named 034 dossier and ignore any placeholder.
2. Establish current Citrix Secure Access client / Citrix Gateway VPN product line, current platform/version lifecycle and server/gateway deployment model.
3. Distinguish full VPN/tunnel from ICA/HDX proxy/access features, browser/clientless access, EPA/posture and ZTNA-style features.
4. Identify actual wire/tunnel protocols and any maintained public interoperability implementations; do not assume generic TLS/OpenVPN/OpenConnect compatibility without evidence.
5. Pin public sources/licenses where reusable candidates exist; use proprietary N/A for Citrix private source/build/test internals.
6. Map first-party UI/menu, gateway admin UI, profiles/config, auth/SAML/RADIUS/MFA/cert/EPA, platform persistence/secrets, routing/DNS, logs/diagnostics, installer/update/uninstall, assets, releases/advisories, tests/CI and Store/privacy implications.
7. Reconcile all 20 original v1 gates and promote only if evidence-backed/N-A/bounded.
8. Continue 035 Barracuda TINA automatically.

Fetch latest tracker/Run State before every state write and never move concurrent progress backward.