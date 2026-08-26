# Official Proxy-Project Source Audit — V2Fly, Project X, and Trojan

> **Companion reference to [`/FIREWALL.md`](../FIREWALL.md)**  
> Audit/access date: **2026-08-26**  
> Scope: official protocol specifications, official project documentation, transport layering, project anti-detection/design claims, documentation staleness, and how those sources should be reconciled with independent censorship measurement.  
> Safety boundary: this document is for architecture, protocol understanding, censorship measurement, diagnosis, and defensive research. It intentionally omits deployment recipes, endpoint-hiding guidance, active-probe evasion procedures, probe byte sequences, packet-mutation recipes, working bypass endpoints/domains, and step-by-step circumvention instructions.

---

## 0. Why this audit is separate

The firewall reference needs two different kinds of protocol evidence:

1. **What a protocol/project actually sends or intends to send.** This comes from official source code and project documentation.
2. **What a national censor actually detects or blocks.** This requires independent controlled measurement, deployment evidence, or source-code evidence tied to the censor.

Those are not interchangeable.

A project may claim that a transport looks like HTTPS, that a protocol is difficult to distinguish, or that a fallback resists probes. Such statements are useful for understanding design intent. They are **not proof of current censorship resistance**.

Conversely, a paper can show that a censor blocks a traffic class without proving that the censor semantically parses the underlying proxy protocol.

This file closes the official-project-source gap for **V2Fly/VMess, Project X/Xray/VLESS/REALITY, and the original Trojan project**.

---

# 1. Evidence labels used here

| Label | Meaning |
|---|---|
| `OFFICIAL PROTOCOL SPEC` | Published by the protocol/project maintainers and describes wire format or configuration semantics. |
| `OFFICIAL PROJECT DESIGN CLAIM` | Project statement about intended detectability, mimicry, privacy, or anti-probing properties. Useful as design intent only. |
| `SOURCE-CODE EVIDENCE` | Behavior verified in project source code/release pin. Strong for implementation, not national deployment. |
| `PEER-REVIEWED CENSOR MEASUREMENT` | Independent controlled study of censorship behavior. Highest weight for what a censor actually does. |
| `COMMUNITY EXPERIMENT` | Developer/user experiment without publication-grade independent validation. |
| `HISTORICAL` | Valid for its version/period but not assumed current. |
| `NOT INDEPENDENTLY CONFIRMED` | Plausible or claimed, but no independent deployment evidence found. |
| `UNKNOWN` | Evidence insufficient. |

### Priority rule

When sources conflict, use this ordering for **censor behavior**:

```text
independent controlled censor measurement
> censor/vendor source-code evidence tied to deployment
> official protocol source/specification
> official project design/marketing claim
> community experiment
> anecdote
```

For **protocol wire format**, use the current implementation/source/specification rather than censorship papers that only describe the protocol at a high level.

---

# 2. V2Fly / V2Ray official-source audit

## 2.1 VMess official protocol specification

`OFFICIAL PROTOCOL SPEC` — V2Fly's current VMess protocol documentation describes VMess as V2Ray's original encrypted communication protocol.

The current public specification distinguishes two protocol-header authentication families:

- **AEAD authentication** — current mechanism, providing authenticated protection for the protocol header;
- **MD5-based legacy authentication** — deprecated compatibility mechanism.

The official specification states that legacy MD5 authentication is deprecated and that VMess can negotiate the appropriate header format based on the received protocol header.

### Important wire-level facts from the official specification

Without reproducing operational configuration recipes, the specification establishes that VMess has structured fields and versioned behavior even though significant portions are encrypted/authenticated. The modern AEAD request contains an encrypted authentication identifier, encrypted header-length information, nonce material, encrypted instruction/header data, and the subsequent data stream.

The specification also documents:

- a current protocol version number;
- authentication material derived from the configured user identity;
- time-dependent authentication material;
- request/response formats that are asymmetric;
- optional metadata masking/padding mechanisms;
- multiple application-data encryption modes;
- TCP and UDP proxy commands carried by the protocol.

These facts are valuable for source-level fingerprint analysis. They do **not** prove a censor parses those fields.

## 2.2 VMess time dependency and AEAD transition

`OFFICIAL PROTOCOL SPEC` — V2Fly's VMess configuration documentation states that VMess relies on reasonably synchronized system time. It also records the historical transition to VMess AEAD and the ability to disable legacy MD5-authentication compatibility in newer versions.

### Research implication

A VMess connection failure can arise from protocol/configuration state such as clock problems or compatibility settings. Therefore a failed VMess session should not be attributed to censorship until basic endpoint/protocol health is verified.

This is especially important for PVNetwork diagnostics: **protocol self-failure and censor-induced failure can look identical to a user.**

## 2.3 VMess encryption does not prove traffic indistinguishability

V2Fly's newer v5 documentation describes VMess traffic as encryption-obfuscated/random-looking traffic. That is a project description of payload appearance.

`PEER-REVIEWED CENSOR MEASUREMENT` — the GFW's fully-encrypted-traffic detector measured at USENIX Security 2023 affected VMess without needing to prove semantic VMess decryption. The measured censor used cheap early-flow/common-protocol/statistical heuristics to classify some fully encrypted traffic.

### Canonical verdict

**China / VMess**

- affected by measured fully-encrypted traffic classification: `STRONGLY SUPPORTED / PEER-REVIEWED`;
- dedicated VMess semantic parser in the national GFW: `NOT PROVEN BY THAT STUDY`;
- “VMess is encrypted, therefore invisible”: `FALSE INFERENCE`.

**Iran / VMess**

- generic encrypted/proxy/VPN classification capability: supported at a broad level;
- independently measured dedicated VMess parser: `NOT INDEPENDENTLY CONFIRMED`.

---

# 3. V2Fly transport layer audit

## 3.1 Transport is separate from proxy protocol

`OFFICIAL PROTOCOL SPEC` — V2Fly documentation treats the underlying stream/transport separately from the proxy protocol.

Current/legacy documentation includes transport families such as:

- TCP;
- WebSocket;
- mKCP;
- HTTP/2-related transport;
- gRPC;
- QUIC;
- HTTP Upgrade;
- Hysteria2 in newer stream documentation;
- additional project-specific stream modes depending on generation/version.

### Diagnostic implication

The string “VMess” is not enough to describe the observable flow. A meaningful measurement record should include at least:

```text
proxy protocol
+ underlying transport
+ security layer
+ TCP/UDP
+ TLS/QUIC metadata
+ endpoint/IP/ASN
+ hostname/SNI if observable
```

A censor may act on any one of those layers.

## 3.2 WebSocket

`OFFICIAL PROTOCOL SPEC` — V2Fly's WebSocket transport uses standard WebSocket semantics and exposes HTTP-layer configuration such as request path/header behavior.

### What is observable

Before/around TLS and at the outer network layer, a censor may still use:

- destination IP/ASN;
- port;
- TLS ClientHello features where visible;
- SNI where not protected by ECH;
- connection timing/size/direction;
- HTTP/WebSocket semantics when not encrypted by TLS.

The presence of WebSocket does not by itself make the inner proxy protocol visible, nor does it guarantee ordinary-browser equivalence.

## 3.3 gRPC / HTTP/2

V2Fly provides gRPC/HTTP2-based stream mechanisms in current documentation.

Some project documentation contains advice intended to reduce probing or make deployment less conspicuous. This audit records **that such anti-probing design concerns exist** but intentionally does not reproduce deployment recipes or service-name/path-selection guidance.

### Evidence rule

- gRPC transport exists: `OFFICIAL PROTOCOL SPEC`;
- GFW can filter ordinary TLS/SNI and analyze flow behavior: independently established elsewhere in the reference;
- dedicated national `V2Ray-gRPC` semantic classifier: do not claim without controlled evidence.

## 3.4 QUIC

V2Fly documentation includes QUIC as a transport option in several generations of configuration.

Independent standards and GFW measurement supersede any assumption that “QUIC encryption hides the handshake from an on-path censor.” QUIC Initial secrets are derivable from public connection information, and the GFW has been measured recovering the contained TLS ClientHello/SNI at scale.

### Canonical rule

A protocol project's older wording about encrypted QUIC transport must not override current standards or 2025 peer-reviewed GFW measurement.

---

# 4. Project X / Xray official-source reconciliation

The detailed Xray source-code analysis remains in `FIREWALL.md`, and the vendor/Chinese-source audit contains the current Project X documentation review. This section records the cross-project interpretation so future agents do not mix V2Fly and Xray terminology.

## 4.1 VLESS

`OFFICIAL PROTOCOL SPEC / SOURCE-CODE EVIDENCE` — VLESS is a lightweight/stateless proxy protocol with a structured request header. Current Xray source code is the authority for the exact Xray implementation.

### Censor verdict

- protocol has analyzable wire structure: yes;
- dedicated national GFW semantic VLESS parser independently measured: `NOT INDEPENDENTLY CONFIRMED`;
- dedicated Iran-wide VLESS parser independently measured: `NOT INDEPENDENTLY CONFIRMED`.

Do not promote a failed VLESS session to “VLESS detected” without controlled feature isolation.

## 4.2 REALITY

`OFFICIAL PROJECT DESIGN CLAIM` — Project X describes REALITY as a TLS-oriented security mechanism intended to borrow characteristics of a genuine target site's TLS behavior/appearance.

This is **design intent**, not a scientific guarantee of indistinguishability.

An observer may still exploit generic surfaces such as:

- endpoint/IP/ASN identity;
- port;
- TCP behavior;
- observable TLS fields;
- flow lengths/timing/direction;
- server-side error/fallback behavior;
- implementation differences;
- active experimentation in principle.

A 2026 Xray community issue explored implementation/error-path differences under unusual TLS inputs. That issue remains `COMMUNITY EXPERIMENT`, not proof of national censor deployment.

### Censor verdict

- China dedicated REALITY classifier: `NOT INDEPENDENTLY CONFIRMED`;
- Iran dedicated REALITY classifier: `NOT INDEPENDENTLY CONFIRMED`;
- generic TLS/endpoint/flow mechanisms affecting REALITY: technically plausible and supported at generic mechanism level.

## 4.3 XTLS Vision

Current Project X documentation/source describes XTLS Vision flow behavior. That establishes implementation semantics.

It does not establish a national DPI rule named “Vision.”

- China dedicated Vision classifier: `UNKNOWN / NOT INDEPENDENTLY CONFIRMED`;
- Iran dedicated Vision classifier: `UNKNOWN / NOT INDEPENDENTLY CONFIRMED`.

## 4.4 XHTTP

XHTTP is a current Project X transport. Its name in project configuration is not necessarily visible as a protocol label to an on-path observer.

- Project transport exists: `OFFICIAL PROTOCOL SPEC`;
- outer HTTP/TLS/flow characteristics can be measured/classified: yes;
- dedicated GFW XHTTP classifier: `NOT INDEPENDENTLY CONFIRMED`;
- dedicated Iran XHTTP classifier: `NOT INDEPENDENTLY CONFIRMED`.

---

# 5. Original Trojan project audit

## 5.1 Project age and maintenance context

The original `trojan-gfw/trojan` repository remains an important historical/primary source for the Trojan protocol design.

GitHub's release record shows the latest release of the original project as:

- **Trojan v1.16.0**;
- published **2020-06-10**.

### Consequence

The original Trojan documentation should not be treated as a current statement about 2026 GFW capability.

For current Xray Trojan behavior, use current Xray source/docs. For Trojan-Go, use its own source/release history. For the original protocol rationale, the original Trojan documentation remains useful.

## 5.2 Real TLS first

`OFFICIAL PROTOCOL SPEC` — the original Trojan protocol documentation states that a connection begins with a genuine TLS handshake. The proxy authentication/request is carried after TLS has been established.

This is important because it separates:

- the **outer TLS handshake**, some metadata of which remains visible to an on-path observer;
- the **inner Trojan authentication/request**, which is protected by TLS once the session is established.

### Safe packet-level interpretation

Ordinary on-path DPI cannot simply read the inner Trojan request from encrypted TLS application data without key material or endpoint compromise. However, that does not make the connection invisible: endpoint, TLS, flow, timing, direction, and implementation behavior remain potential classification surfaces.

## 5.3 Fallback / other-protocol behavior

`OFFICIAL PROTOCOL SPEC` — the original Trojan server design includes a fallback behavior for traffic that is not accepted as a valid Trojan request after TLS. The documentation's goal is to make invalid/ordinary traffic receive plausible service behavior rather than a uniquely proxy-specific error path.

### Threat-model value

Fallback behavior matters because active fingerprinting often studies **how a server behaves under invalid or unexpected inputs**.

### Safety boundary

This reference records that architectural fact but does not provide a probe sequence, scanner implementation, fallback-deployment recipe, or evasion configuration.

## 5.4 “Unidentifiable” claim must be downgraded

The original project branding/documentation calls Trojan “unidentifiable” and explains its intent to resemble HTTPS.

For a rigorous reference, this must be labeled:

`OFFICIAL PROJECT DESIGN CLAIM / HISTORICAL`

—not `CONFIRMED`.

Why:

- no general network protocol can be declared unidentifiable solely by its own project documentation;
- endpoint and traffic metadata remain visible;
- implementation/version differences can create observable behavior;
- censor capabilities evolve;
- independent measurement outranks project branding.

### Canonical verdict

**China / Trojan**

- TLS-first design: confirmed by official protocol documentation;
- dedicated national semantic Trojan parser: `NOT INDEPENDENTLY CONFIRMED` in the reviewed public evidence;
- generic TLS/endpoint/flow classification: independently plausible/established at mechanism level.

**Iran / Trojan**

- generic VPN/proxy blocking and traffic-analysis capability: strongly supported;
- independently measured dedicated Trojan semantic classifier: `NOT INDEPENDENTLY CONFIRMED`.

---

# 6. Trojan vs Trojan-Go vs Xray Trojan

These names should not be conflated.

| Implementation/family | Primary authority | Maintenance/evidence note |
|---|---|---|
| Original Trojan | `trojan-gfw/trojan` repo + original docs | Primary historical protocol source; latest original release is from 2020. |
| Trojan-Go | `p4gefau1t/trojan-go` source/docs | Separate implementation with its own extensions/history; not interchangeable with original release behavior. |
| Xray Trojan | current `XTLS/Xray-core` source/docs | Current Xray implementation; use pinned Xray source for exact behavior. |

A censor measurement that names “Trojan” should be checked for which implementation and outer configuration was tested.

---

# 7. Cross-project protocol evidence matrix

| Protocol / transport | Official project/source proves | Independent censor evidence proves | What remains unknown |
|---|---|---|---|
| VMess | structured encrypted/authenticated protocol; AEAD + deprecated legacy path; transport layering | China fully-encrypted detector affects VMess | whether national GFW uses a dedicated semantic VMess parser; dedicated Iran parser |
| VLESS | structured lightweight/stateless protocol in Xray | generic censor mechanisms can affect its outer transport | dedicated China/Iran semantic VLESS classifier |
| REALITY | TLS-oriented project design and target/fallback concepts | generic TLS/endpoint/flow classification exists | dedicated China/Iran REALITY classifier |
| XTLS Vision | current Xray flow behavior | no independent named Vision classifier found | exact censor-specific recognition |
| XHTTP | current Xray HTTP-oriented transport exists | generic HTTP/TLS/flow filtering exists | dedicated XHTTP classifier |
| Trojan | real TLS first; protected inner request; fallback design | generic TLS/flow/endpoint mechanisms exist | dedicated national semantic Trojan parser |
| WebSocket | standard HTTP/WebSocket transport semantics | HTTP/TLS/domain filtering well established | whether a censor labels a given flow by inner proxy protocol |
| gRPC/H2 | HTTP/2/gRPC stream transport exists | TLS/SNI/flow filtering exists | dedicated V2Ray/Xray gRPC classifier |
| QUIC | project transport implementation exists | China recovers QUIC Initial ClientHello/SNI and filters it | application-data decryption is not implied |
| WireGuard | defined UDP protocol; Project X docs acknowledge recognizable fixed pattern | generic UDP disruption/traffic policy exists | dedicated Iran WireGuard parser; exact regional GFW implementation details |

---

# 8. Project claims versus independent measurement

## 8.1 Rule: encrypted != indistinguishable

Encryption can hide application content while leaving:

- packet sizes;
- timing;
- directions;
- connection duration;
- endpoint identities;
- ports;
- TCP behavior;
- TLS/QUIC handshake metadata;
- first-flow statistical properties.

The GFW 2023 fully-encrypted detector is the clearest example: classification can occur without reading the protected application payload.

## 8.2 Rule: HTTPS-like != browser-identical

A project can use real TLS or HTTP semantics yet still differ from ordinary browser traffic through:

- TLS library/client fingerprint;
- extension ordering and values;
- ALPN;
- connection reuse;
- request cadence;
- packet-size distribution;
- server reaction to malformed/unexpected requests;
- endpoint reputation/context.

A claim of “looks like HTTPS” therefore needs independent validation in the relevant censor and time period.

## 8.3 Rule: old docs are historical evidence

Examples:

- original Trojan documentation dates from an ecosystem whose latest original release is 2020;
- V2Fly documentation contains both legacy and current VMess behavior;
- censorship capabilities measured in 2023–2026 supersede older assumptions about what encrypted/random-looking traffic can evade.

Always record source date/version.

---

# 9. Failure diagnosis by protocol layer

A protocol incident should be decomposed as follows:

```text
A. endpoint healthy?
B. DNS resolution healthy?
C. route/IP reachability healthy?
D. TCP or UDP transport healthy?
E. TLS/QUIC handshake healthy?
F. hostname/SNI policy implicated?
G. outer transport implicated?
H. inner proxy protocol implicated?
I. result reproducible on multiple clean endpoints?
J. result reproducible across ISP/ASN controls?
```

Only after A–G are controlled should an investigator seriously promote H (“inner protocol detected”).

---

# 10. Source staleness and contradiction policy

Future agents must explicitly handle contradictory evidence.

### Example 1 — project says traffic is difficult to identify; peer-reviewed censor detects the class

Use the peer-reviewed censor measurement for deployment reality. Preserve the project statement only as historical/design intent.

### Example 2 — project documentation describes a deprecated protocol path

Do not assume it is enabled in current clients/servers. Check the current release/source/config default.

### Example 3 — a community issue demonstrates a potential fingerprint

Label it `COMMUNITY EXPERIMENT` until independently reproduced and tied to a censor deployment.

### Example 4 — vendor advertises “VPN blocking”

Do not infer a specific VMess/VLESS/Trojan/REALITY signature. VPN blocking can be endpoint-, SNI-, port-, TLS-, flow-, protocol-, or allowlist-based.

---

# 11. Canonical source register

Accessed/re-checked **2026-08-26** unless the source itself is historical.

| ID | Source | Evidence role |
|---|---|---|
| P01 | V2Fly VMess protocol (EN) — https://www.v2fly.org/en_US/developer/protocols/vmess.html | Official VMess wire/protocol specification |
| P02 | V2Fly VMess protocol (ZH) — https://www.v2fly.org/developer/protocols/vmess.html | Chinese official VMess specification |
| P03 | V2Fly VMess configuration — https://www.v2fly.org/config/protocols/vmess.html | AEAD transition, clock/configuration behavior |
| P04 | V2Fly v5 VMess — https://www.v2fly.org/en_US/v5/config/proxy/vmess.html | Newer VMess project description |
| P05 | V2Fly transport — https://www.v2fly.org/en_US/config/transport.html | Transport/protocol separation; TCP/WS/H2/QUIC etc. |
| P06 | V2Fly v5 stream — https://www.v2fly.org/en_US/v5/config/stream.html | Current stream/security families |
| P07 | V2Fly WebSocket — https://www.v2fly.org/en_US/v5/config/stream/websocket.html | WebSocket stream semantics |
| P08 | V2Fly WebSocket (ZH) — https://www.v2fly.org/config/transport/websocket.html | Chinese official WebSocket transport documentation |
| P09 | Original Trojan overview — https://trojan-gfw.github.io/trojan/overview.html | Historical project design claim/HTTPS mimicry rationale |
| P10 | Original Trojan protocol — https://trojan-gfw.github.io/trojan/protocol.html | Official TLS-first protocol/fallback design |
| P11 | Original Trojan GitHub releases — https://github.com/trojan-gfw/trojan/releases | Release history; latest original release v1.16.0 (2020-06-10) |
| P12 | Project X transport — https://xtls.github.io/en/config/transport.html | Current Xray protocol/transport/security layering |
| P13 | Project X REALITY — https://xtls.github.io/en/config/transports/reality.html | REALITY project design semantics |
| P14 | Project X VLESS — https://xtls.github.io/en/config/inbounds/vless.html | VLESS/XTLS Vision official documentation |
| P15 | USENIX Security 2023 — fully encrypted GFW detector — https://www.usenix.org/conference/usenixsecurity23/presentation/wu-mingshi | Independent VMess-affecting censorship measurement |
| P16 | USENIX Security 2025 — GFW QUIC SNI censorship — https://www.usenix.org/conference/usenixsecurity25/presentation/zohaib | Independent QUIC Initial/SNI censorship measurement |

---

# 12. Final protocol-project verdicts

## VMess

`OFFICIAL SPEC`: well documented and structured.  
`CHINA`: measured traffic-class detection affects VMess.  
`DEDICATED VMESS PARSER`: not established by the 2023 fully-encrypted classifier paper.  
`IRAN`: dedicated VMess classifier not independently confirmed.

## VLESS

`OFFICIAL SPEC/SOURCE`: structured Xray protocol.  
`CHINA`: dedicated semantic VLESS classifier not independently confirmed.  
`IRAN`: dedicated semantic VLESS classifier not independently confirmed.

## REALITY

`OFFICIAL DESIGN`: TLS-oriented mimicry/target behavior is a project design property.  
`UNDETECTABLE`: not a defensible scientific label.  
`CHINA`: dedicated REALITY classifier not independently confirmed.  
`IRAN`: dedicated REALITY classifier not independently confirmed.

## XTLS Vision

`OFFICIAL SOURCE`: implementation behavior documented.  
`CHINA/IRAN DEDICATED CLASSIFIER`: unknown / not independently confirmed.

## XHTTP

`OFFICIAL SOURCE`: current Project X transport.  
`CHINA/IRAN DEDICATED CLASSIFIER`: unknown / not independently confirmed.

## Trojan

`OFFICIAL ORIGINAL PROTOCOL`: real TLS first, protected inner request, fallback design.  
`ORIGINAL PROJECT CLAIM`: “unidentifiable” is historical design/branding, not proof.  
`ORIGINAL RELEASE FRESHNESS`: latest original release v1.16.0, 2020-06-10.  
`CHINA`: dedicated semantic Trojan parser not independently confirmed in the reviewed evidence.  
`IRAN`: dedicated semantic Trojan parser not independently confirmed.

---

# 13. Completion checklist

- [x] V2Fly official VMess wire specification audited.
- [x] VMess AEAD vs deprecated MD5 path recorded.
- [x] VMess operational self-failure/clock issue separated from censorship.
- [x] V2Fly transport layering audited.
- [x] WebSocket and HTTP/gRPC transport implications separated from inner protocol identity.
- [x] QUIC project documentation reconciled with modern GFW measurement.
- [x] Project X VLESS/REALITY/Vision/XHTTP verdicts reconciled with official docs/source.
- [x] Original Trojan protocol documentation audited.
- [x] Original Trojan “unidentifiable” claim downgraded to historical design claim.
- [x] Original Trojan release freshness verified from GitHub.
- [x] Original Trojan vs Trojan-Go vs Xray Trojan explicitly separated.
- [x] Cross-project evidence matrix added.
- [x] Project-claim-vs-measurement precedence rule documented.
- [x] Safety boundary maintained; no operational evasion recipe added.

**Status as of 2026-08-26:** official protocol-project documentation is now represented as a first-class evidence category in the firewall research set, with explicit rules preventing project claims from being mistaken for censor-deployment facts.
