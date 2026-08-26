# Firewall Vendor, Chinese-Language, and Weakness Evidence Audit

> **Companion reference to [`/FIREWALL.md`](../FIREWALL.md)**  
> Audit/access date: **2026-08-26**  
> Scope: censorship-vendor evidence, official Chinese technical sources, Chinese-language research, protocol-project documentation, implementation limitations/false positives, and Iran vendor attribution.  
> Safety boundary: this appendix documents architecture, capabilities, limitations, measurement artifacts, and defensive/diagnostic implications. It intentionally omits operational endpoint-hiding recipes, active-probe evasion procedures, packet-mutation recipes, working bypass IP/domain lists, exploit instructions, and step-by-step censorship circumvention guidance.

---

## 0. Why this appendix exists

The root `FIREWALL.md` is the primary packet-level reference. This appendix closes a narrower evidence gap that is easy to mishandle in censorship research: **what vendors, official state research bodies, Chinese-language technical sources, and proxy-project maintainers themselves actually say**, and how those claims differ from independently measured deployment.

The most important additions are:

1. **USENIX Security 2026 source-code evidence for Geedge Networks / Tiangou Secure Gateway (TSG).** Researchers analyzed leaked commercial DPI source code, built a local TSG instance, characterized parser/rule architecture, and extracted fingerprints/parser idiosyncrasies that can be compared with censorship deployments. This materially strengthens the vendor-code evidence base beyond marketing material and black-box inference.
2. **Official Chinese state research evidence.** The Chinese Academy of Sciences' National Engineering Research Center for Information Content Security publicly describes work on high-speed encrypted-application identification, protocol identification/reconstruction, traffic classification, unknown-protocol sensing, deep protocol parsing, network behavior analysis, and information-disposition systems. This proves R&D/engineering capability, **not automatically deployment in the national GFW**.
3. **Chinese-language measurement sources.** GFW Report provides Chinese versions of its peer-reviewed Shadowsocks and fully-encrypted-traffic studies. They are valuable primary-language technical sources, not merely community anecdotes.
4. **Current Project X/Xray documentation.** The official documentation itself now states that Shadowsocks/VMess traffic appearances can be classified, that WireGuard has a fixed UDP signature that is easy to identify/block, and that TLS/REALITY provides an ordinary-HTTPS-like outer appearance as a project design claim. These statements are useful for protocol threat modeling but are **not evidence that a specific censor deploys a dedicated VLESS/REALITY parser**.
5. **Iran vendor attribution.** 2026 official EU records identify Yaftar Pazhohan Pishtaz Rayanesh and Douran Software Technologies in connection with Iranian internet censorship, traffic analysis, internet filtering, and VPN blocking. Filterwatch and Citizen Lab provide additional investigative/technical evidence around Douran contracts and PROTEI mobile DPI.
6. **Known censorship-system weaknesses and false positives.** Peer-reviewed measurements show classifier collateral damage, DNS overblocking, residual state, asymmetry, computational bottlenecks, and parser/state limitations. These are documented here as **measurement and engineering limitations**, not as operational bypass recipes.

---

# 1. Evidence hierarchy used in this audit

| Label | Meaning |
|---|---|
| `PEER-REVIEWED MEASUREMENT` | Reproducible academic measurement or source-code study accepted at a reviewed venue. Highest weight for concrete behavior. |
| `OFFICIAL GOVERNMENT / REGULATORY` | Government, regulator, standards body, or sanctions/legal record. Strong for attribution and institutional facts; not necessarily packet-level proof. |
| `OFFICIAL PROJECT / VENDOR` | The vendor/project's own documentation, source code, datasheet, or public statement. Strong for intended capability/design; weaker for deployment/effectiveness. |
| `LEAK-CORROBORATED INVESTIGATION` | Reporting/research based on leaked internal records with a documented methodology. Stronger than marketing, but provenance/scope must remain explicit. |
| `INDEPENDENT INVESTIGATION` | Credible technical/human-rights investigation such as Citizen Lab, Amnesty, InterSecLab, or Filterwatch. |
| `OFFICIAL R&D CAPABILITY` | State/university/research-center description of research systems or projects. Proves capability/research direction, not a particular production deployment. |
| `COMMUNITY EXPERIMENT` | Reproducible-looking developer/user test without independent publication-grade validation. Useful lead only. |
| `ANECDOTAL` | User reports or forum discussion without controlled evidence. |
| `HISTORICAL` | Valid for its measured period, but should not be projected unchanged into 2026. |
| `UNKNOWN` | No sufficient evidence found. |

### Non-negotiable inference rules

- **Vendor capability != national deployment.**
- **National deployment != uniform deployment on every ISP/province/link.**
- **A protocol can be fingerprintable without a censor using that fingerprint.**
- **A censor can block a protocol class without semantically parsing that protocol.**
- **A failed Xray/V2Ray/Trojan configuration does not prove protocol recognition.**
- **A vendor saying “VPN detection” does not prove it recognizes VLESS, REALITY, XHTTP, or XTLS Vision by name.**
- **A source-code parser/rule exists does not prove that exact rule is enabled on a production GFW node.**
- **Traffic analysis, IP reputation, SNI, TLS/QUIC metadata, port policy, and endpoint blocking must be ruled out before claiming protocol-specific detection.**

---

# 2. China vendor and commercial-firewall evidence map

## 2.1 Geedge Networks / Tiangou Secure Gateway (TSG)

### 2026 source-code analysis: major evidence upgrade

`PEER-REVIEWED MEASUREMENT` — **Ablove et al., USENIX Security 2026, “Technical Analysis of the Geedge Networks Firewall Source Code Leak.”**

The paper's public abstract states that researchers analyzed more than 100K leaked internal documents/code/communications from Geedge Networks, focused on the flagship **Tiangou Secure Gateway (TSG)**, successfully built and ran a local copy, and characterized architecture including the protocols it can parse and the format of blocking rules used for sites, proxies, and other resources. The researchers also extracted fingerprints such as custom RNG behavior and parser idiosyncrasies that allow comparison with TSG use and similar deployments in the GFW.

**What this changes:**

- It moves a large part of Geedge/TSG discussion from vendor marketing and black-box speculation toward **source-code-backed capability evidence**.
- It provides a defensible basis for saying that commercial carrier-grade DPI used in censorship infrastructure contains concrete protocol parsers and blocking-rule machinery.
- It provides a way to associate some observed network behavior with TSG-like implementations through implementation fingerprints.

**What it does *not* justify:**

- It does not imply every Geedge feature is enabled in every GFW location.
- It does not make the entire GFW equivalent to one TSG appliance.
- It does not prove every proxy protocol has a dedicated parser or signature in the currently deployed national system.
- It does not prove Iran deploys TSG.
- It does not justify importing unverified implementation details from third-party summaries unless checked against the paper/source itself.

### InterSecLab 2025 leak investigation

`LEAK-CORROBORATED INVESTIGATION` — InterSecLab's **The Internet Coup** reports that Geedge offers a suite of national-scale surveillance/censorship products and describes deployments/contracts outside China as well as systems within regions of China. Its public summary attributes capabilities including DPI, real-time subscriber monitoring, granular traffic control, and region-specific censorship rules.

This is important because it demonstrates that a modern censorship stack may be a **suite**, not a single firewall function:

- a gateway/DPI enforcement component;
- analytics/query tooling for collected records;
- deployment and asset-management tooling;
- centralized policy/rule distribution;
- carrier/network integration.

Treat those as a product-system model, not proof that all components are installed at every GFW observation point.

### Supply-chain corroboration

`INDEPENDENT INVESTIGATION` — InterSecLab's 2026 **MADLink** investigation documents Geedge's hardware supply chain and quotes ADLINK's statement that its China branch supplied standard CSA-7400 network-security platforms to Geedge in 2019. ADLINK explicitly denied participating in the design/construction of the GFW or similar systems. That denial is important evidence discipline: a hardware supplier relationship is not equivalent to designing the censorship policy/system.

### Amnesty / partner investigation

`INDEPENDENT INVESTIGATION` — Amnesty International's 2025 **Shadows of Control** and the broader Great Firewall Export investigation document the commercialization/export of censorship and surveillance technology, including Chinese suppliers. Use Amnesty/InterSecLab/partner documents as corroboration for vendor relationships, product roles, and deployment context; use peer-reviewed measurement/source-code analysis for precise packet-level behavior where possible.

## 2.2 Geedge company identity and standards participation

`OFFICIAL GOVERNMENT / REGULATORY` — China's National Public Service Platform for Standards lists **积至（海南）信息技术有限公司** among the drafting organizations for **GB/T 35275-2026** (information-security standard, published 2026-04-30).

This is useful for corporate identity/standards participation. It is **not evidence that the company authored or operates a national censorship policy**, and it should not be used as a shortcut for deployment attribution.

---

# 3. Official Chinese state R&D sources

These sources are valuable because they show what state-linked research organizations publicly acknowledge as technical priorities.

## 3.1 National Engineering Research Center for Information Content Security

The Chinese Academy of Sciences / Institute of Information Engineering hosts the **信息内容安全国家工程研究中心** (National Engineering Research Center for Information Content Security).

### Official leadership

`OFFICIAL R&D CAPABILITY` — the center's current leadership page names **方滨兴 (Fang Binxing)** as director. This is institutional context, not packet-level deployment proof.

### Encrypted-application identification on high-speed links

`OFFICIAL R&D CAPABILITY` — an official profile for engineer/researcher **苟高鹏 (Gou Gaopeng)** states long-term research in encrypted-application identification, encrypted-network behavior analysis, and encrypted malicious-service discovery. It says he has led core systems for national information-security infrastructure and that related systems operate on high-speed network links, addressing rapid/accurate detection of particular encrypted applications.

This is one of the strongest public Chinese official-source statements for the existence of high-speed encrypted-traffic classification engineering capability.

**Do not overclaim:** the page does not say “this is the exact production GFW VMess/VLESS classifier.”

### Network situational awareness and protocol processing

`OFFICIAL R&D CAPABILITY` — the center's research page lists:

- protocol identification and reconstruction;
- protocol reverse analysis;
- traffic classification;
- unknown-protocol sensing;
- high-efficiency stream processing;
- replay/generation for network simulation;
- high-speed network-flow acquisition;
- deep protocol parsing;
- streaming classification of network information content.

Again, these are public research/engineering areas, not an inventory of enabled GFW rules.

### Network measurement and behavior analysis

`OFFICIAL R&D CAPABILITY` — the center also describes research in:

- network measurement;
- behavior analysis;
- network-flow classification;
- encrypted-channel fine-grained classification;
- behavior transformation/tracking detection;
- automatic protocol discovery;
- protocol mimicry/behavior analysis.

For threat modeling this matters: it confirms that **payload secrecy alone is not the only classification surface considered by Chinese state-linked research**.

### Information-processing architecture and disposition

`OFFICIAL R&D CAPABILITY` — another official page describes architectures that combine acquisition, analysis, and information disposition, including real-time blocking/interference/tracking as possible system actions, with applications including firewalls and network audit systems.

This supports a generic multi-stage model:

```text
acquire traffic -> parse/classify -> correlate -> policy decision -> block/interfere/track/log
```

It does **not** prove one particular national GFW topology.

---

# 4. Chinese-language primary technical sources

## 4.1 GFW Report — Shadowsocks (Chinese)

`PEER-REVIEWED MEASUREMENT` — the Chinese edition of the IMC 2020 paper **《Shadowsocks是如何被检测和封锁的》** documents a two-stage system:

1. passive suspicion from early-flow properties such as first-payload length/entropy;
2. active probes to suspected servers for confirmation.

The paper also documents replay/randomized probe classes and centralized characteristics of the probing infrastructure. The Chinese edition is useful as a first-party-language technical reference and mirrors the peer-reviewed work rather than relying on forum folklore.

**Safe weakness summary:** the study demonstrates that passive classifiers and active-probe validators have assumptions and implementation-specific response surfaces. This appendix intentionally does not reproduce the paper's step-by-step evasion procedure.

## 4.2 GFW Report — fully encrypted traffic (Chinese)

`PEER-REVIEWED MEASUREMENT` — the Chinese version of the USENIX Security 2023 paper **《中国的防火长城是如何检测和封锁完全加密流量的》** documents passive detection of fully encrypted traffic that affected protocols including Shadowsocks, VMess, and Obfs4.

The important engineering result is that the measured censor did not need to “decrypt VMess” to classify it. The inferred system used cheap exemption/statistical heuristics involving common-protocol fingerprints, bit statistics, and printable-ASCII characteristics of early payloads.

**Implication for this repository:**

- `VMess detected` may mean **fully-encrypted traffic-class detection**, not a semantic VMess parser.
- `VLESS failed` does not inherit VMess's evidence automatically.
- encrypted payload != traffic indistinguishability.

## 4.3 Why Chinese-language sources need evidence grading

Chinese-language content should be split into four groups:

1. peer-reviewed/research translations from groups such as GFW Report;
2. official Chinese state/research documentation;
3. official project documentation (Project X/V2Fly);
4. community blogs/forums/issues.

Only groups 1–3 should normally support core reference claims without independent corroboration. Community content is valuable for hypotheses, reproduction targets, and terminology but should not silently become `CONFIRMED` national deployment evidence.

---

# 5. Measured GFW weaknesses, limitations, and false positives

This section records limitations as **engineering and measurement facts**. It intentionally avoids converting them into operational evasion recipes.

## 5.1 Fully-encrypted classifier collateral damage

`PEER-REVIEWED MEASUREMENT` — USENIX Security 2023 inferred crude but efficient heuristics for fully-encrypted traffic and tested them against ordinary traffic. The authors estimated that, **if applied broadly**, the inferred rules could collateral-block roughly **0.6% of normal Internet traffic**.

### Measurement implication

A false positive is plausible when a benign flow accidentally falls into the same statistical class. Therefore:

- classify multiple clean controls;
- preserve first-payload bytes/lengths in PCAP metadata;
- compare protocol class against ordinary controls on the same path;
- never infer “dedicated VMess parser” from a binary block result alone.

## 5.2 DNS regex overblocking

`PEER-REVIEWED MEASUREMENT` — GFWatch (USENIX Security 2021) reverse-engineered broad GFW DNS matching and found roughly **41K innocuous domains** that matched censorship expressions during its measurement period.

`PEER-REVIEWED / LATER MEASUREMENT` — GFWeb later reported that the GFW had addressed important examples of this specific overbroad matching behavior, illustrating that censorship rules evolve.

### Measurement implication

- historical regex behavior should not be assumed current;
- domain blocking may reflect substring/rule collisions rather than intentional listing;
- repeat longitudinal tests before describing an overblocking rule as persistent.

## 5.3 Residual censorship state

`PEER-REVIEWED MEASUREMENT` — GFWeb reports prolonged residual censorship after a trigger, with subsequent connections sharing the same tuple affected for up to roughly **350 seconds** in the measured behavior.

### Measurement implication

Back-to-back A/B tests can contaminate each other. A test harness should record:

- source/destination IP;
- destination port;
- test order;
- time since prior triggering request;
- whether a fresh source/tuple/control was used.

Otherwise a researcher can mistake residual state for an intrinsic property of the second test case.

## 5.4 Directionality and asymmetry

`PEER-REVIEWED MEASUREMENT` — GFWeb found asymmetries and bidirectional/loss-tolerant properties in HTTP/HTTPS censorship behavior.

### Measurement implication

One-sided packet capture is insufficient for strong attribution. Capture both sides where possible and identify whether:

- client->server packets leave;
- server->client packets return;
- forged RSTs appear in one/both directions;
- only one direction is dropped.

## 5.5 QUIC computational bottleneck

`PEER-REVIEWED MEASUREMENT` — USENIX Security 2025 measured GFW QUIC SNI censorship and found that decrypting QUIC Initial packets at scale creates computational overhead that reduces censor effectiveness under moderate traffic load.

### Measurement implication

A single successful/failed QUIC attempt is not deterministic evidence of policy absence/presence. Repeat tests and record load/time/path context.

This appendix deliberately does **not** reproduce traffic-generation or bypass procedures from the paper.

## 5.6 QUIC parser/reassembly limitations

`OBSERVED / RESEARCH REPORT` — GFW Report experiments around the newer QUIC censor have documented parser/reassembly behavior that differs from fully standards-compliant endpoint processing for some fragmented/reassembled Initial layouts.

### Safe interpretation

This demonstrates a generic rule for DPI engineering: a censor's parser may implement only a bounded approximation of endpoint state/reassembly. Parser mismatch creates false negatives/positives and measurement artifacts.

Do not turn parser limitations into packet-construction recipes in this repository.

## 5.7 Historical monitor-model flaws

`HISTORICAL PEER-REVIEWED` — FOCI 2013 work studying the GFW as an on-path censorship monitor found flaws/limitations in areas such as:

- transport control-block creation/destruction;
- IP fragmentation/TCP segment reassembly;
- packet validation;
- completeness of HTTP analysis;
- state management.

These findings are valuable to understand why NIDS-like censors can have parser/state disagreement. They are **historical** and must not be assumed unchanged in current GFW implementations.

---

# 6. Current Xray / Project X official-source audit

## 6.1 What Project X officially says in 2026

`OFFICIAL PROJECT` — current Xray transport documentation explicitly separates:

- proxy protocol (`VMess`, `VLESS`, `Trojan`, etc.);
- transport (`RAW`, `XHTTP`, `gRPC`, `WebSocket`, `HTTPUpgrade`, `mKCP`, `Hysteria`);
- transport security (`TLS`, `REALITY`).

That layering is essential for censorship diagnosis. “VLESS” alone is not a complete wire-level description.

The same official documentation states, in substance:

- Shadowsocks and VMess encrypt/protect payloads but their **traffic appearance can still be classified**;
- WireGuard has a **fixed UDP signature** that is comparatively easy to identify/block;
- those protocols do not inherently provide the ordinary HTTPS-like appearance of TLS/REALITY;
- VLESS without an outer transport-security layer is not equivalent to ordinary HTTPS.

These are **Project X design/threat-model statements**, not proof that a national censor deploys the exact classifier implied by them.

## 6.2 VLESS + XTLS Vision

`OFFICIAL PROJECT` — current VLESS docs describe VLESS as a stateless lightweight protocol and document `xtls-rprx-vision` flow behavior, including inner-handshake random padding and operation with TCP+TLS/REALITY.

### Evidence verdict

- The protocol/source can be analyzed for wire structure.
- Outer TLS/REALITY and flow behavior create observable metadata.
- **No public independent evidence found in this audit proves a dedicated national-GFW semantic VLESS parser in production.**
- **No public independent evidence found proves a dedicated Iran-wide semantic VLESS parser.**

## 6.3 REALITY

`OFFICIAL PROJECT` — Project X describes REALITY as a modification of TLS using the appearance/handshake characteristics of a target site. The project claims that externally the traffic can be consistent with ordinary web browsing.

### Critical evidence discipline

That is a **design claim**. It must not be rewritten as “undetectable.” Even if application data is protected, an observer can still use endpoint identity, IP/ASN, port, flow statistics, TCP behavior, observable TLS/QUIC fields, implementation inconsistencies, and active experimentation.

The official docs also explain that unauthenticated/non-REALITY traffic can be forwarded toward the configured target. That behavior is relevant to threat modeling because error/fallback behavior is itself a possible observable surface.

### Evidence verdict

- Dedicated national-GFW `REALITY` classifier: **NOT INDEPENDENTLY CONFIRMED** by the public sources reviewed here.
- Dedicated Iran `REALITY` classifier: **NOT INDEPENDENTLY CONFIRMED**.
- Generic TLS/endpoint/flow/DPI classification that can affect REALITY connections: **technically plausible and supported at generic-mechanism level**, but this is not the same claim.

## 6.4 XHTTP

`OFFICIAL PROJECT` — XHTTP is a current Project X transport, but project naming and documentation are not evidence that a censor has a dedicated `XHTTP` label/parser.

### Evidence verdict

- dedicated GFW XHTTP classifier: `UNKNOWN / NOT INDEPENDENTLY CONFIRMED`;
- dedicated Iran XHTTP classifier: `UNKNOWN / NOT INDEPENDENTLY CONFIRMED`;
- HTTP/TLS/flow-level classification of an XHTTP deployment: possible and dependent on the outer wire behavior.

## 6.5 VMess

The strongest national-GFW evidence remains USENIX Security 2023:

- **VMess is affected by the fully-encrypted traffic detector**;
- this is strong evidence of traffic-class detection;
- it is **not proof that the GFW decrypts VMess or necessarily uses a full VMess semantic parser**.

For Iran, this audit found no new independent source that upgrades the existing root-file verdict to a dedicated VMess classifier.

## 6.6 Trojan

Trojan/Trojan-Go/Xray Trojan should be separated:

- the original `trojan-gfw/trojan` project documentation describes a TLS-first design and fallback behavior;
- that design intent is not immunity proof;
- the original project is comparatively old and should not be treated as the current authority for Xray's implementation;
- current Xray source/docs are the better reference for Xray-specific Trojan behavior.

No source in this vendor audit proves an Iran-wide dedicated Trojan semantic classifier.

## 6.7 WireGuard

Project X's current docs themselves describe WireGuard's fixed UDP signature as easy to identify/block. Iran also has strong independent evidence of UDP disruption at scale, but:

- `UDP disrupted` != `WireGuard parser confirmed`;
- endpoint/port/UDP policy can produce the same user-visible symptom.

---

# 7. Xray Chinese community experiments: useful, but not deployment proof

## 7.1 Xray-core issue #6121

`COMMUNITY EXPERIMENT` — a 2026 Chinese-language Xray-core issue titled **“關於vless-cracker-v1的檢測”** discusses user experiments comparing REALITY-like server/fallback behavior with genuine TLS implementations under replayed/malformed input. The issue includes anonymized test logs and discussion of TLS-stack-dependent differences.

### What can safely be learned

- community researchers are actively testing whether **implementation/error-path behavior** can expose differences between a proxy construction and the genuine TLS target it resembles;
- TLS library behavior may differ across stacks and targets;
- response behavior to unusual inputs is a legitimate fingerprinting research surface.

### What cannot be concluded

- this is not peer-reviewed proof that China's national GFW uses the described detector;
- it is not evidence that Iran deploys it;
- it does not justify publishing probe bytes, target-selection advice, or a scanner recipe in this repository.

Label any conclusion derived only from this issue `COMMUNITY EXPERIMENT`, not `CONFIRMED`.

---

# 8. Iran censorship-vendor evidence map

## 8.1 WGDICC / filtering institutional context

Official and investigative sources identify the **Working Group for Determining Instances of Criminal Content (WGDICC)** as a central actor in content-filtering decisions and contractor relationships.

This institutional evidence complements packet-level measurement but does not specify the exact appliance traversed by an arbitrary subscriber packet.

## 8.2 Yaftar Pazhohan Pishtaz Rayanesh

`OFFICIAL GOVERNMENT / REGULATORY` — Council Implementing Regulation (EU) 2026/267, dated 2026-01-29, identifies **Yaftar Pazhohan Pishtaz Rayanesh Limited Company** and states that it collaborates with WGDICC, is involved in website/app blocking and censorship, develops systems involving search-engine data for identifying content, and has cooperated with the Attorney General's Office in blocking VPNs.

### What this proves

It is strong official attribution of a company to Iranian censorship/VPN-blocking activity.

### What it does not prove

It does not identify a packet-level VLESS/VMess/Trojan/REALITY classifier, algorithm, signature set, or deployment point.

## 8.3 Douran Software Technologies

`OFFICIAL GOVERNMENT / REGULATORY` — the same EU regulation identifies **Douran Software Technologies (Douran Group)** as involved in internet blocking/censoring and states that it provides traffic-analysis tools, internet filtering, and VPN-blocking equipment to Iranian internet providers; it also associates Douran with National Information Network projects and WGDICC.

`LEAK-CORROBORATED INVESTIGATION` — Filterwatch's 2023 **Internet Oppressors** report, based on leaked WGDICC-related emails plus other sources, identifies Douran as a filtering contractor and reports contracts with operators including Irancell and TCI for filtering/protection and domestic/global traffic-separation infrastructure. The report includes images/specifications mentioning DPI-related equipment.

`INDEPENDENT INVESTIGATION / 2026` — Filterwatch's March 2026 reporting describes a newer tiered/whitelist-style environment and cites Douran in the context of filtering infrastructure. Treat source-based allegations about specific recent operational roles according to Filterwatch's stated sourcing, not as packet-capture proof.

### What this changes for the root protocol verdicts

It strengthens attribution that Iranian companies/operators use traffic-analysis/filtering/VPN-blocking systems. It **does not independently prove** a named dedicated parser for VMess, VLESS, Trojan, REALITY, Vision, or XHTTP.

## 8.4 PROTEI and Ariantel mobile DPI

`INDEPENDENT INVESTIGATION` — Citizen Lab's 2023 **You Move, They Follow** analyzed documents/emails around Iranian MVNO Ariantel and vendors. It reports that PROTEI was selected to provide core-network components including DPI and describes acceptance-test material showing traffic-management capabilities such as classifying service types, bandwidth restriction, and blocking data services.

### Critical distinction

This is strong evidence of **mobile-operator DPI / lawful-intercept / traffic-management integration**, but it should not automatically be equated with the national web-censorship stack or a universal GFW-like border firewall.

Use it to support the multi-layer architecture model:

```text
national / central filtering
+ operator / mobile-core policy
+ lawful-intercept / subscriber-control interfaces
+ local routing/DNS/security policies
```

## 8.5 Chinese technology transfer into Iran

`INDEPENDENT INVESTIGATION` — ARTICLE 19's 2026 **Tightening the Net: China's Infrastructure of Oppression in Iran** reviews historical Chinese technology transfer and cites ZTE/Huawei DPI/surveillance relationships in Iran going back to the early 2010s, alongside later surveillance vendors.

Treat this as **historical supplier/infrastructure context**. Vendor presence in Iran does not prove that current censorship of an Xray protocol is being performed by a particular Chinese box.

---

# 9. Updated protocol-vs-vendor verdict matrix

| Country | Protocol/transport | What the new vendor/source audit adds | Current defensible verdict |
|---|---|---|---|
| China | Shadowsocks | Chinese primary-language IMC 2020 evidence + vendor-code context | `CONFIRMED` passive suspicion + active probing in measured system |
| China | VMess | Xray docs acknowledge classifiability; USENIX 2023 explicitly measured impact | `STRONGLY SUPPORTED TRAFFIC-CLASS DETECTION`; semantic parser not proven by that study |
| China | VLESS | Project X official design/source only; no new independent national measurement found | `NOT INDEPENDENTLY CONFIRMED` dedicated parser |
| China | REALITY | Official design claims + community fingerprint experiment; no publication-grade national deployment proof | `NOT INDEPENDENTLY CONFIRMED` dedicated classifier |
| China | XTLS Vision | Project docs/source expose behavior; no dedicated national classifier evidence found | `UNKNOWN / NOT INDEPENDENTLY CONFIRMED` |
| China | XHTTP | Official transport exists; no dedicated censor-label evidence found | `UNKNOWN / NOT INDEPENDENTLY CONFIRMED` |
| China | Trojan | TLS/outer behavior observable; no independent dedicated national parser proof found | `NOT INDEPENDENTLY CONFIRMED` dedicated parser |
| China | WireGuard | Project docs acknowledge fixed UDP signature; generic censorship capability exists | fingerprintable; exact national mechanism must be measured |
| Iran | Generic VPN/proxy class | EU/vendor attribution strengthens evidence that VPN blocking/traffic analysis is an operational objective/capability | `STRONGLY SUPPORTED` generic VPN/filtering capability |
| Iran | VMess | No new independent named-protocol classifier proof | `NOT INDEPENDENTLY CONFIRMED` |
| Iran | VLESS | No new independent named-protocol classifier proof | `NOT INDEPENDENTLY CONFIRMED` |
| Iran | Trojan | No new independent named-protocol classifier proof | `NOT INDEPENDENTLY CONFIRMED` |
| Iran | REALITY | No new independent named-protocol classifier proof | `NOT INDEPENDENTLY CONFIRMED` |
| Iran | XTLS Vision / XHTTP | No independent dedicated classifier evidence found | `UNKNOWN / NOT INDEPENDENTLY CONFIRMED` |
| Iran | WireGuard | Generic UDP disruption and DPI capability exist | dedicated WireGuard classifier `UNKNOWN`; do not infer from UDP failure alone |

---

# 10. What “VPN blocking” in a vendor document can mean

A vendor/regulatory phrase such as “VPN blocking” is underspecified. It can be implemented through one or more of:

- known endpoint/IP/prefix reputation;
- known service/domain/SNI rules;
- fixed port or transport policy;
- TLS/QUIC fingerprinting;
- application/protocol signatures;
- flow-shape/statistical classification;
- active confirmation/probing;
- subscriber-specific policy;
- DNS manipulation;
- generic UDP degradation/blocking;
- allowlisting during shutdown/selective-access periods.

Therefore a vendor statement does not answer the packet-level question by itself. The required diagnostic question remains:

> **Which observable feature changed the outcome while all relevant controls stayed healthy?**

---

# 11. Vendor-aware measurement plan for PVNetwork

For every suspected filtering incident, collect enough evidence to distinguish endpoint, outer transport, and protocol identity.

## 11.1 Minimum event record

```text
timestamp_utc
client_isp
client_asn
access_type (mobile/fixed/datacenter)
client_ipv4_or_ipv6_family
server_asn
server_prefix_class
server_port
transport (TCP/UDP)
proxy_protocol
outer_transport
transport_security (none/TLS/REALITY)
SNI_or_outer_hostname_if_observable
TLS_version
ALPN
QUIC_version_if_applicable
TCP_handshake_result
TLS_or_QUIC_handshake_stage_reached
first_failure_direction
RST_seen_and_direction
ICMP_seen
bytes_up / bytes_down
packet_count_up / packet_count_down
first_payload_lengths
flow_duration
retransmissions
MTU/MSS observations
control_test_id
prior_trigger_elapsed_seconds
PCAP_client_side
PCAP_server_side
```

## 11.2 Controls required before “protocol detected”

At minimum compare:

1. same server/IP, ordinary allowed service control;
2. same protocol on a clean different endpoint;
3. same endpoint with different outer transport where legitimate/testable;
4. same test from another ISP/ASN;
5. IPv4 vs IPv6 if both are genuinely available;
6. TCP vs UDP when comparing different transport classes;
7. repeat after enough time to avoid residual-state contamination;
8. two-sided PCAP where possible.

## 11.3 Evidence ladder

```text
Single client failure
    -> operational symptom only
Repeated same endpoint failure
    -> endpoint/path suspicion
Same endpoint + clean control differs
    -> feature-specific suspicion
Multiple endpoints + same protocol/feature fail
    -> protocol/feature-class suspicion
Multiple ISPs + clean controls + repeatability
    -> stronger censor-policy hypothesis
Packet-level trigger isolation + independent reproduction
    -> measurement-grade finding
External independent study/source-code match
    -> high-confidence attribution
```

---

# 12. Weakness-aware measurement hazards

## 12.1 Residual-state contamination

Do not run trigger/control pairs in a way that lets a previous censorship event poison the next result. Randomize/order tests and record elapsed time.

## 12.2 Censor overload/non-determinism

Systems that perform expensive parsing/decryption can produce probabilistic-looking results under load. Repeat samples and report rates, not only one success/failure.

## 12.3 Parser mismatch

DPI may process fragments, reassembly, invalid fields, extension ordering, or timeout/state differently from the real endpoint. If a result depends on malformed edge cases, label it as a parser-model finding, not normal-protocol behavior.

## 12.4 Middlebox plurality

A connection can traverse multiple policy layers. A result from Henan, a specific Chinese cloud provider, MCI, Irancell, or TCI may contain local policy in addition to national policy.

## 12.5 Vendor-version drift

Even source-code-backed commercial firewall behavior is version-specific. Record:

- source/release/build if available;
- observation date;
- deployment geography/provider;
- whether the behavior was black-box measured or inferred from source.

## 12.6 “TLS-looking” is not “unobservable”

Even when an outer transport resembles HTTPS, observers still have access to some combination of:

- IP/ASN/prefix;
- ports;
- timing and packet sizes;
- directionality/burst patterns;
- ordinary TLS ClientHello fields when not protected by ECH;
- QUIC Initial information recoverable by an on-path observer;
- server/error/fallback behavior;
- connection reuse and lifecycle patterns.

---

# 13. Source-quality exclusion policy

The audit intentionally searched beyond academic papers, but **core claims are not based on weak sources merely because they mention a desired protocol**.

The following source types may be catalogued as leads but should not upgrade a verdict without corroboration:

- VPN-provider marketing blogs;
- SEO/affiliate “best protocol for China/Iran” pages;
- unsourced Telegram posts;
- forum posts claiming “GFW detects X” without captures/controls;
- one-off speed tests;
- generic AI-generated protocol summaries;
- scanner websites that do not disclose methodology;
- vendor marketing claims presented as proof of deployment;
- leaked screenshots with no provenance chain;
- community issues without independent reproduction.

A lower-quality source can still be useful to formulate a **testable hypothesis**, but it should be labeled and kept outside the confirmed-evidence chain.

---

# 14. Source register

Accessed/re-checked **2026-08-26** unless noted.

| ID | Source | Type | What it supports | What it does NOT prove |
|---|---|---|---|---|
| V01 | USENIX Security 2026 — *Technical Analysis of the Geedge Networks Firewall Source Code Leak* — https://www.usenix.org/conference/usenixsecurity26/presentation/ablove | Peer-reviewed source-code analysis | TSG source-code analysis, local build, protocol parsing/rule architecture, implementation fingerprints | Uniform deployment of every feature across the GFW |
| V02 | USENIX Security 2026 technical sessions — https://www.usenix.org/conference/usenixsecurity26/technical-sessions | Official conference record | Publication/session status and abstract | Additional undocumented implementation details |
| V03 | InterSecLab — *The Internet Coup* — https://interseclab.org/research/the-internet-coup/ | Leak-corroborated investigation | Geedge product-suite/deployment/export context | Exact national rule enablement |
| V04 | InterSecLab — *MADLink* — https://interseclab.org/research/madlink-a-taiwanese-vestige-in-the-geedge-supply-chain/ | Supply-chain investigation | Geedge hardware supply chain; ADLINK statement | ADLINK designed/operated GFW |
| V05 | Amnesty International — *Shadows of Control* — https://www.amnesty.org/en/documents/asa33/0206/2025/en/ | Independent investigation | Commercial censorship/surveillance ecosystem | Exact GFW packet parser |
| V06 | Amnesty Chinese-language report landing page — https://www.amnesty.org/zh-hans/documents/asa33/0206/2025/en/ | Chinese-language independent source | Chinese-language version/context | Independent packet measurement by itself |
| V07 | China National Public Service Platform for Standards — GB/T 35275-2026 — https://std.samr.gov.cn/gb/search/gbDetailed?id=511EBC5968889318E06397BE0A0AFBD5 | Official standards record | `积至（海南）信息技术有限公司` as drafting organization | GFW deployment |
| V08 | CAS NER Center — leadership — https://nelist.iie.cas.cn/sysgk_1/sysld/ | Official R&D | Fang Binxing listed as director | Specific deployed GFW design |
| V09 | CAS NER Center — Gou Gaopeng profile — https://nelist.iie.cas.cn/yjdw/fgry/202207/t20220708_709033.html | Official R&D | encrypted-app identification, high-speed-link systems | named VMess/VLESS production classifier |
| V10 | CAS NER Center — network situational awareness — https://nelist.iie.cas.cn/yjfx/shgz/ | Official R&D | protocol identification/reconstruction, traffic classification, unknown-protocol sensing, deep parsing | national deployment map |
| V11 | CAS NER Center — network measurement/behavior analysis — https://nelist.iie.cas.cn/yjfx/rhwlbm/ | Official R&D | encrypted-channel classification, flow classification, protocol discovery | exact censor rules |
| V12 | CAS NER Center — information processing architecture — https://nelist.iie.cas.cn/yjfx/dsjcc/ | Official R&D | acquisition/analysis/disposition architecture; blocking/interference as generic actions | one-box GFW topology |
| C01 | GFW Report Chinese IMC 2020 — https://gfw.report/publications/imc20/zh/ | Peer-reviewed Chinese primary-language source | Shadowsocks passive detection + active probing | current universal behavior for every proxy |
| C02 | GFW Report Chinese USENIX 2023 — https://gfw.report/publications/usenixsecurity23/zh/ | Peer-reviewed Chinese primary-language source | fully-encrypted passive detector; VMess affected | VMess decryption/semantic parser |
| C03 | USENIX Security 2023 — https://www.usenix.org/conference/usenixsecurity23/presentation/wu-mingshi | Peer-reviewed measurement | classifier heuristics and ~0.6% broad-application collateral estimate | exact current rules in every region |
| C04 | USENIX Security 2021 GFWatch — https://www.usenix.org/conference/usenixsecurity21/presentation/hoang | Peer-reviewed measurement | DNS injection/regex overblocking; 41K innocuous matches in study | persistence of old regexes into 2026 |
| C05 | USENIX/GFWeb article — https://www.usenix.org/publications/loginonline/measuring-great-firewall%E2%80%99s-multi-layered-web-filtering-apparatus | Research summary | evolution, residual censorship, local-provider interference | all national mechanisms are identical |
| C06 | USENIX Security 2025 QUIC — https://www.usenix.org/conference/usenixsecurity25/presentation/zohaib | Peer-reviewed measurement | QUIC Initial inspection/SNI censorship; computational bottleneck | application-data decryption |
| C07 | FOCI 2013 monitor-model paper — https://www.usenix.org/system/files/conference/foci13/foci13-khattak.pdf | Historical peer-reviewed workshop | parser/state/reassembly limitations | unchanged 2026 behavior |
| X01 | Project X transport docs (EN) — https://xtls.github.io/en/config/transport.html | Official project | protocol/transport/security layering; classifiability warnings | censor deployment |
| X02 | Project X transport docs (ZH) — https://xtls.github.io/config/transport.html | Official project Chinese | Chinese version of current design/threat-model statements | independent measurement |
| X03 | Project X REALITY docs — https://xtls.github.io/en/config/transports/reality.html | Official project | REALITY design and fallback/target behavior | “undetectable” claim |
| X04 | Project X REALITY Chinese docs — https://xtls.github.io/config/transports/reality.html | Official project Chinese | Chinese primary project source | national classifier evidence |
| X05 | Project X VLESS docs — https://xtls.github.io/en/config/inbounds/vless.html | Official project | VLESS/XTLS Vision configuration/design | national deployment evidence |
| X06 | Project X VLESS Chinese docs — https://xtls.github.io/config/inbounds/vless.html | Official project Chinese | Chinese primary project source | national deployment evidence |
| X07 | Xray-core issue #6121 — https://github.com/XTLS/Xray-core/issues/6121 | Community experiment | possible TLS-stack/error-path fingerprint research lead | GFW/Iran deployment proof |
| I01 | EU Council Implementing Regulation 2026/267 — https://eur-lex.europa.eu/eli/reg_impl/2026/267/oj | Official government/regulatory | Yaftar and Douran attribution to censorship/VPN-blocking roles | packet-level named protocol classifier |
| I02 | EU Council Implementing Regulation 2026/1851 — https://eur-lex.europa.eu/eli/reg_impl/2026/1851/oj | Official government/regulatory | current 2026 Iran human-rights restrictive-measures framework update | does not by itself add packet-level details |
| I03 | Filterwatch — *Internet Oppressors* — https://filter.watch/english/2023/09/14/internet-oppressors-a-look-at-the-office-of-irans-attorney-general-and-its-contractors/ | Leak-corroborated investigation | WGDICC contractors, Douran filtering/traffic-analysis context | universal current ISP deployment |
| I04 | Filterwatch — *The Digital Frontline* — https://filter.watch/english/2026/03/06/network-monitoring-february-2026-a-new-phase-of-selective-internet-in-iran/ | Independent investigation | 2026 selective/tiered access and infrastructure context | exact packet trigger for each ISP |
| I05 | Citizen Lab — *You Move, They Follow* — https://citizenlab.ca/research/uncovering-irans-mobile-legal-intercept-system/ | Independent technical investigation | Ariantel/PROTEI mobile DPI and traffic-management integration | identical national border firewall |
| I06 | ARTICLE 19 — *Tightening the Net: China's Infrastructure of Oppression in Iran* — https://www.article19.org/wp-content/uploads/2021/07/A19-Tightening-the-Net-China-Iran-Report.pdf | Independent investigation | historical Chinese vendor/DPI transfer context | current Xray protocol classifier attribution |

---

# 15. Audit conclusions

## 15.1 What is now strongly established

- Commercial carrier-grade censorship/DPI stacks such as Geedge TSG have source-code-backed protocol parsing and blocking-rule architecture evidence.
- Chinese state-linked research institutions publicly describe high-speed encrypted-application classification, protocol parsing/reconstruction, network-flow classification, unknown-protocol sensing, and behavior-analysis capabilities.
- The GFW has independently measured passive fully-encrypted traffic classification and active probing for some protocol families.
- The GFW's modern QUIC censor can recover/inspect QUIC Initial ClientHello information and apply SNI filtering.
- Censorship systems have measurable false positives, residual state, parser mismatch, asymmetry, and performance limitations.
- Official 2026 EU records attribute Iranian companies Yaftar and Douran to censorship/VPN-blocking and traffic-analysis roles.
- Independent Iranian investigations support a layered architecture including operator/mobile-core DPI and filtering contractors.

## 15.2 What remains unproven and must stay labeled as such

- exact physical vendor inventory of every national GFW observation point;
- exact vendor/device mapping for every Iranian ISP;
- a dedicated, independently measured Iran-wide semantic parser for VMess;
- a dedicated, independently measured Iran-wide semantic parser for VLESS;
- a dedicated, independently measured Iran-wide semantic parser for Trojan;
- a dedicated, independently measured Iran-wide semantic parser for REALITY;
- a dedicated Iran/GFW classifier specifically named `XTLS Vision` or `XHTTP`;
- that every capability listed in a Geedge product/codebase is enabled in China's national GFW;
- that a community REALITY fingerprint experiment is deployed by a national censor.

## 15.3 Reference rule for future agents

When a new claim appears — for example “Iran now detects REALITY” — do not update the verdict from a screenshot or failed config. Require, in order of preference:

1. peer-reviewed or independent controlled measurement;
2. source-code/vendor rule evidence tied to the deployed network;
3. repeatable multi-endpoint/multi-ISP packet captures with controls;
4. corroboration by an independent research group.

Until then, keep the claim `ANECDOTAL`, `OBSERVED`, or `NOT INDEPENDENTLY CONFIRMED` as appropriate.

---

# 16. Completion checklist for this audit

- [x] Latest Geedge/TSG peer-reviewed source-code work checked.
- [x] Commercial-vendor/export investigations checked.
- [x] Chinese standards/corporate identity evidence checked.
- [x] Official Chinese state R&D capability pages checked.
- [x] Chinese-language GFW measurement sources checked.
- [x] Current Project X/Xray English + Chinese docs checked.
- [x] Xray community issue evidence checked and downgraded appropriately.
- [x] GFW classifier false-positive/collateral evidence checked.
- [x] DNS overblocking evolution checked.
- [x] residual-state/asymmetry measurement hazards checked.
- [x] QUIC censor computational limitation checked.
- [x] historical parser/state mismatch research checked.
- [x] official 2026 Iran censorship-vendor attribution checked.
- [x] Douran investigative contract evidence checked.
- [x] Iranian mobile DPI / PROTEI evidence checked.
- [x] China-to-Iran technology-transfer context checked.
- [x] VMess/VLESS/Trojan/REALITY verdicts re-audited without overclaiming.
- [x] weak/marketing/community source exclusion rules documented.
- [x] PVNetwork measurement implications and telemetry fields documented.

**Status as of 2026-08-26:** this vendor/Chinese-language/weakness-source gap is closed to the level supported by public, reviewable evidence. Future changes should be treated as a living-research update rather than rewriting unknowns as facts.
