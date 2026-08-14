# OpenConnect — Vendor Compatibility Matrix for PVNetwork Research

Research date: 2026-08-14

Source basis: current official OpenConnect protocol documentation and v9.21-era changelog/release material. This matrix describes **upstream OpenConnect capability**, not PVNetwork support.

## Capability-state rule

PVNetwork must never translate “OpenConnect has a protocol mode” into a binary “fully supported vendor”. Use capability dimensions such as:

- transport/session support;
- basic authentication;
- certificate authentication;
- MFA/challenge support;
- browser/SSO support;
- posture/host-check support;
- reconnect/roaming behavior;
- IPv6;
- known server-version limitations;
- test evidence against exact vendor/server versions.

## 016 — Cisco AnyConnect-compatible

Official OpenConnect documentation describes AnyConnect as the original/default protocol family and the most mature baseline.

Research position:
- strong core candidate through OpenConnect;
- broad authentication capabilities exist at the library level;
- recent v9.20 release notes show that even this mature family can require compatibility updates when newer Cisco server behavior changes;
- browser/SSO and posture/CSD-style requirements must be handled as explicit capabilities, not assumed from basic tunnel success.

PVNetwork state: `IN-RESEARCH`; likely high-priority enterprise compatibility target.

## 017 — OpenConnect / ocserv-compatible

This is the natural OpenConnect protocol/library baseline and should be used as a controlled interoperability target where PVNetwork can test against an open server implementation.

Research position:
- high-value for automated integration testing because client and compatible server behavior can be reproduced without proprietary vendor appliances;
- does not replace testing against actual vendor products for the vendor-specific matrix entries.

PVNetwork state: `IN-RESEARCH`; high-value test baseline.

## 018 — Palo Alto GlobalProtect

Official documentation describes separate portal and gateway concepts, HTTPS authentication/configuration, optional certificate authentication, SAML use in some deployments, and optional HIP/posture reporting.

Research position:
- transport support exists, but authentication flow and gateway selection can vary materially by deployment;
- SSO/browser integration and HIP/posture should be separate PVNetwork capability flags;
- a successful tunnel without expected HIP compliance may still fail to provide intended enterprise access;
- current OpenConnect development still receives GlobalProtect SSO-related work, so this family needs active regression tracking.

PVNetwork state: `IN-RESEARCH / VERSION-MATRIX-REQUIRED`.

## 019 — Fortinet FortiGate SSL VPN

Official documentation labels Fortinet support experimental and documents PPP-based support plus authentication limitations/variants.

Research position:
- basic username/password, optional client certificate and known challenge-response MFA paths are documented;
- official docs state a newer non-PPP tunnel protocol is not supported;
- reconnect behavior differs by FortiGate/FortiOS generation and may require reauthentication after a network interruption;
- therefore “Fortinet supported” must never be a single checkbox.

PVNetwork state: `IN-RESEARCH / PARTIAL-UPSTREAM-CAPABILITY`.

## 020 — Pulse Secure

Official documentation describes Pulse as distinct from the older Juniper Network Connect protocol even though many appliances can expose both.

Research position:
- tunnel connectivity and IPv6 capability are documented;
- not all Pulse authentication modes are supported;
- Host Checker/TNCC support is explicitly incomplete/unimplemented for Pulse mode in current official documentation;
- where an appliance exposes both families, Juniper-mode compatibility is a separate path, not an automatic fallback inside PVNetwork unless deliberately modeled/tested.

PVNetwork state: `IN-RESEARCH / AUTH-AND-POSTURE-GAPS`.

## 021 — Ivanti Connect Secure

Treat current Ivanti/Pulse naming and product/version behavior as a vendor/version matrix rather than a new protocol assumption. Official OpenConnect docs group modern Ivanti/Pulse Connect Secure with Pulse support and also note that many such servers can expose the older Juniper protocol unless disabled.

PVNetwork state: `IN-RESEARCH / APPLIANCE-VERSION-MATRIX-REQUIRED`.

## 022 — Juniper Network Connect

Official documentation describes this older protocol as substantially different from Pulse and notes browser-like/HTML/JavaScript authentication complexity.

Research position:
- common login forms are supported, but arbitrary/custom login pages can require a browser-like environment;
- Host Checker/TNCC can be relevant;
- official docs note IPv6 limitations for the older Juniper protocol;
- this entry must preserve the difference between “tunnel works” and “enterprise auth/posture flow works”.

PVNetwork state: `IN-RESEARCH / LEGACY-COMPATIBILITY-TARGET`.

## 023 — F5 BIG-IP SSL VPN

Official documentation labels F5 support experimental and PPP-based.

Research position:
- basic username/password, optional TLS client certificate and common domain selection are documented;
- some F5 authentication pages rely heavily on browser/JavaScript behavior;
- official docs identify at least one proprietary URI/auth-flow area that is not fully understood/implemented;
- DTLS behavior depends on server generation/configuration.

PVNetwork state: `IN-RESEARCH / PARTIAL-AUTH-COMPATIBILITY`.

## 024 — Array Networks SSL VPN

Official documentation labels Array support experimental.

Research position:
- current official docs describe basic username/password authentication support only;
- other authentication methods are not yet generally supported;
- DTLS support is documented with version/security constraints that may conflict with modern OS policy defaults.

PVNetwork state: `IN-RESEARCH / LIMITED-UPSTREAM-CAPABILITY`.

## Cross-family architecture consequence

PVNetwork should represent enterprise compatibility as a **capability object**, not a protocol-name string. Example conceptual fields:

- `transportSupported`
- `basicAuthSupported`
- `clientCertificateSupported`
- `mfaSupported`
- `externalBrowserSupported`
- `postureCheckSupported`
- `ipv6Supported`
- `reconnectSupported`
- `testedServerVersions[]`
- `knownLimitations[]`

The exact schema is not approved yet; these fields illustrate the required granularity.

## Required future test matrix

For each vendor family/version actually claimed by PVNetwork, test:

1. clean authentication and connection;
2. invalid credentials/cancel flow;
3. certificate trust and client certificate flow;
4. MFA/challenge path where claimed;
5. external browser/SSO where claimed;
6. posture/host-check where claimed;
7. reconnect after network loss;
8. Wi-Fi/interface/address-family transition;
9. DNS/route cleanup on disconnect/failure;
10. session expiration/reauthentication;
11. server-side group/realm/gateway selection;
12. upgrade from previous OpenConnect library version;
13. platform-specific UI/credential-store behavior;
14. redacted diagnostic export.

## Evidence links to keep current

- `https://www.infradead.org/openconnect/protocols.html`
- vendor-specific pages under `https://www.infradead.org/openconnect/`
- `https://www.infradead.org/openconnect/changelog.html`
- `https://gitlab.com/openconnect/openconnect/-/releases`
- current canonical issues/MRs under the GitLab project.

Status: shared matrix created; every numbered entry still needs its own final conclusion and PVNetwork evidence.