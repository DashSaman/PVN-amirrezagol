# Firewall Research Reference Index

> Last audit: **2026-08-26**

This index defines the canonical reading order for the PVNetwork China/Iran censorship research set.

## Canonical documents

1. [`FIREWALL.md`](FIREWALL.md) — primary packet-level reference: architecture, DNS/IP/HTTP/TLS/QUIC/ECH, DPI, traffic analysis, active probing, shutdowns, IPv6/NAT, Xray/V2Ray/Trojan/REALITY/XTLS/XHTTP, protocol verdicts, measurement methodology, PCAP diagnosis, false positives, and PVNetwork operational implications.
2. [`research/FIREWALL_VENDOR_CHINESE_SOURCE_AUDIT.md`](research/FIREWALL_VENDOR_CHINESE_SOURCE_AUDIT.md) — vendor/source-code audit, official Chinese R&D sources, Chinese-language primary research, GFW implementation limitations/false positives, current Project X documentation, Xray community evidence grading, and Iran vendor attribution.
3. [`research/FIREWALL_OFFICIAL_PROTOCOL_PROJECT_AUDIT.md`](research/FIREWALL_OFFICIAL_PROTOCOL_PROJECT_AUDIT.md) — official V2Fly/VMess, Project X/VLESS/REALITY/XTLS/XHTTP, and original Trojan project/specification audit; transport layering, documentation staleness, project-design claims versus independent censorship measurement, and cross-project verdicts.

All three documents must be read together before changing a protocol-detection verdict.

## Evidence rule

A future agent must not promote a claim such as “Iran detects VLESS/REALITY” or “the GFW has a dedicated XHTTP parser” from a failed configuration, vendor marketing statement, project anti-detection claim, forum post, or one-off client test. Prefer controlled measurement, packet captures with controls, source-code/deployment evidence, and independent corroboration.

Vendor capability is not deployment evidence. Protocol fingerprintability is not proof that a national censor currently uses that fingerprint. A protocol project's claim that it is “unidentifiable,” “HTTPS-like,” or resistant to probing is a design claim until independently validated against the censor/version/time period being discussed.

## Direct-vendor-site access note

During the 2026-08-26 audit, direct retrieval attempts for the public Geedge Networks website (`geedgenetworks.com`) and Douran website (`douran.ir`) were not reliably fetchable from the research environment (HTTP/fetch failures). The audit therefore does **not** pretend that those sites were fully scraped. Claims that would normally rely on those sites are instead sourced from peer-reviewed source-code analysis, official regulatory records, archived/quoted vendor material, and independent investigations.

No clearly attributable public official website for Yaftar was located in the audit. Yaftar attribution therefore relies on official EU/Swiss/US records and corroborating investigations rather than an invented corporate-source URL.

This limitation is an evidence-quality note, not evidence that the companies lack public web infrastructure.

## Latest high-value additions as of 2026-08-26

- USENIX Security 2026: source-code analysis of Geedge Networks' Tiangou Secure Gateway (TSG), including a locally built copy, parser/rule architecture, and implementation fingerprints.
- Official Chinese Academy of Sciences research-center pages describing encrypted-application identification, traffic classification, protocol reconstruction/reverse analysis, unknown-protocol sensing, high-speed traffic processing, and deep protocol parsing.
- Chinese-language GFW Report versions of peer-reviewed Shadowsocks and fully-encrypted-traffic studies.
- Current Project X/Xray English and Chinese documentation used as project-design evidence, explicitly separated from censor-deployment evidence.
- Official V2Fly VMess wire/configuration documentation, including the AEAD/legacy distinction and the separation of proxy protocol from TCP/WebSocket/HTTP2/gRPC/QUIC-style transports.
- Original Trojan protocol documentation audited as historical project/specification evidence; its “unidentifiable” claim is explicitly downgraded to project design intent, and the original project's latest GitHub release is recorded as v1.16.0 from 2020-06-10.
- Official 2026 EU attribution of Yaftar and Douran to Iranian censorship/VPN-blocking and traffic-analysis roles.
- Filterwatch and Citizen Lab evidence for Iranian filtering contractors, operator/mobile DPI, and layered subscriber-control infrastructure.
- Peer-reviewed documentation of GFW collateral damage, DNS overblocking, residual state, asymmetry, QUIC computational limits, and historical parser/state limitations.

## Maintenance rule

When a future source claims that a previously unknown protocol is now detected, record:

- exact date and network/ISP/province/country;
- implementation and version where known;
- inner proxy protocol;
- outer transport/security;
- endpoint/ASN context;
- packet/control methodology;
- evidence grade;
- whether the finding proves semantic protocol parsing, traffic-class recognition, endpoint policy, or only a user-visible failure.

Do not silently overwrite an `UNKNOWN`/`NOT INDEPENDENTLY CONFIRMED` verdict from anecdotal evidence.

## Safety boundary

These references are for architecture understanding, censorship measurement, diagnosis, defensive engineering, and evidence-backed research. Do not add endpoint-hiding recipes, active-probe evasion procedures, working bypass IP/domain lists, packet-mutation recipes, exploit instructions, or step-by-step censorship-circumvention guidance.
