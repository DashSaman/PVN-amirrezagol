# Karing Deep Source Analysis

Snapshot: **2026-08-29**

Status: **source/reuse research only; no third-party Karing code is imported into PVNetwork by this document.**

## Why this document exists

Karing is materially more capable than a simple proxy GUI. Its useful design is spread across three layers:

1. `KaringX/karing` — Flutter product/UI, profile management, routing editor, presets and bundled data references.
2. `KaringX/sing-box` — Karing-maintained fork of sing-box containing the protocol registry, routing engine, DNS engine and transport/TLS capabilities.
3. `KaringX/karing-ruleset` — the ruleset ecosystem used by Karing for GeoIP, GeoSite and ACL categories, including Iran-specific data.

For PVNetwork, these layers must be studied separately because their code/data provenance, license and reuse risk are different.

---

# 1. Open-source status and an important caveat

## Karing application

Canonical repository:

- https://github.com/KaringX/karing

The repository is public and exposes substantial Flutter source, including screens, application modules, assets and routing/preset UI.

License observed in the repository:

- GPL v3-or-later.
- Additional naming/association condition: derivative works may not use the Karing name or imply association without prior consent.

Therefore the source is open for inspection and GPL-compatible modification/distribution, but it is **not a permissive source base** for an independently licensed proprietary PVNetwork application.

## Karing sing-box fork

Canonical repository:

- https://github.com/KaringX/sing-box

Inspected branch:

- `dev-next`

License observed:

- GPL v3-or-later.
- The checked license also contains the no-name/no-association derivative condition.

This fork is where a large fraction of Karing's real protocol/routing capability lives.

## Karing ruleset

Canonical repository:

- https://github.com/KaringX/karing-ruleset

Default branch observed:

- `sing`

The repository is public and its README says it is used by Karing by default. It contains/produces GeoIP/GeoSite/ACL classifications used by the client.

## The `vpn_service` caveat

Karing's `pubspec.yaml` currently declares:

```yaml
vpn_service:
  path: ../vpn-service/
  #git:
    #url: https://github.com/KaringX/vpn-service.git
    #ref: main
```

The referenced public GitHub URL currently does not resolve as a public repository. In addition, source files in the Karing app import `package:vpn_service/vpn_service.dart` and application-local VPN service wrappers that are not fully present in the inspected public tree.

**Conclusion:** the Karing application repository is open and highly useful, but the currently inspected public repository is not a fully self-contained proof that every piece needed for a clean build is publicly available.

Do not plan a PVNetwork fork around the assumption that cloning only `KaringX/karing` provides every runtime/service component.

---

# 2. Protocol support verified in Karing's core source

The most authoritative source inspected is `include/registry.go` in `KaringX/sing-box` plus build-tag-specific registration files.

## Standard inbound/outbound families

Verified in source:

- SOCKS
- HTTP
- mixed SOCKS/HTTP inbound
- Shadowsocks
- VMess
- Trojan
- ShadowTLS
- VLESS
- AnyTLS
- Naive

Additional outbound/core roles:

- Tor outbound
- SSH outbound
- Direct
- Block
- DNS outbound
- Selector group
- URL-test group

Ingress/system roles:

- TUN inbound
- Redirect
- TProxy
- Direct inbound

## QUIC-family protocols

The build-tagged `include/quic.go` registers:

- Hysteria
- Hysteria2
- TUIC

It also enables QUIC/HTTP3 DNS transports and the V2Ray QUIC transport.

## WireGuard and Tailscale

Verified in source:

- WireGuard outbound
- WireGuard endpoint support
- Tailscale endpoint support

These are registered through build/platform include boundaries rather than all being hard-coded into the main registry.

## NaiveProxy is a real first-class core path

This is especially relevant to PVNetwork/PVNaive.

`protocol/naive/outbound.go` is not a superficial link parser. It implements a Naive outbound through `cronet-go` / Chromium networking and exposes a real `cronet.NaiveClient`.

Observed behavior/options include:

- TLS is mandatory.
- server name/SNI handling.
- certificate/certificate-path handling.
- custom extra headers.
- DNS resolution through the sing-box DNS router.
- ECH support.
- QUIC support.
- configurable QUIC congestion control:
  - default
  - BBR
  - BBRv2
  - CUBIC
  - Reno
- optional UDP-over-TCP support.
- reports the NaiveProxy engine version when started.

The Naive outbound is guarded by the build tag:

```go
//go:build with_naive_outbound
```

That means a Karing-grade build system deliberately compiles optional capabilities into the core.

### PVNetwork implication

Our future client should treat protocol capability as a **runtime/build capability matrix**, not merely as a list of link schemes understood by the UI.

A profile can only be considered supported when:

1. the parser understands it;
2. the canonical model can represent it;
3. the selected engine build actually contains it;
4. the current platform can execute it;
5. the adapter can translate all required fields without silent loss.

---

# 3. Transport layer verified in the Karing sing-box fork

The fork contains transport implementations including:

- simple-obfs
- SIP003 plugin transport
- Trojan transport helpers
- V2Ray transport layer
- V2Ray gRPC
- V2Ray gRPC-lite
- V2Ray HTTP
- V2Ray HTTP Upgrade
- V2Ray QUIC
- V2Ray WebSocket
- WireGuard transport support

This explains why counting only top-level protocols understates Karing's compatibility surface. A VLESS/VMess/Trojan profile may have materially different transport/TLS capabilities even though its top-level protocol label is the same.

### PVNetwork implication

The canonical profile model must separate at least:

```text
Protocol
  + Authentication
  + Server endpoint
  + Transport
  + TLS/security layer
  + Multiplex
  + Dialer/network options
```

Do not store a profile as a flat set of core-specific JSON fields.

---

# 4. TLS/security capabilities in the core

The inspected `option/tls.go` exposes considerably more than a basic TLS on/off switch.

Verified outbound-side capabilities include:

- SNI/server name
- ALPN
- TLS min/max version
- cipher suites
- curve preferences
- CA/certificate pinning inputs
- client certificates/keys
- TLS fragmentation
- TLS record fragmentation
- kernel TX/RX TLS toggles
- ECH
- uTLS fingerprint selection
- REALITY

The curve set includes `X25519MLKEM768`, in addition to P-256/P-384/P-521/X25519.

### PVNetwork implication

`SecurityOptions` must be a first-class canonical object. It should not be embedded only inside the Xray adapter or hidden behind a generic `tls=true` field.

---

# 5. Routing: much richer than GeoSite/GeoIP alone

Karing's Flutter routing editor already exposes a broad custom-rule UI, but the core route model is even richer.

The inspected sing-box `RawDefaultRule` supports matching on:

## Traffic/protocol identity

- inbound tag
- IP version
- network (`tcp`, `udp`, etc.)
- authenticated user
- detected protocol
- client

## Domain matching

- exact domain
- domain suffix
- domain keyword
- domain regex
- GeoSite category

## Destination/source IP matching

- destination GeoIP
- source GeoIP
- destination CIDR
- source CIDR
- private destination IP
- private source IP

## Port matching

- destination port
- destination port range
- source port
- source port range

## Process/application matching

- process name
- process path
- process path regex
- Android package name
- user
- UID

## Network/environment matching

- network type
- expensive-network flag
- constrained-network flag
- Wi-Fi SSID
- Wi-Fi BSSID
- interface address
- network-interface address
- default-interface address
- preferred-by metadata

## External/built-in rule data

- rule-set
- rule-set CIDR match-source mode

## Rule composition

- invert
- logical rules

This is substantially broader than the common "Iran domains direct / everything else proxy" model.

---

# 6. Routing actions verified in source

Rules are not limited to choosing a proxy.

Verified actions include:

- `route`
- `route-options`
- `direct`
- `bypass`
- `reject`
- `hijack-dns`
- `sniff`
- `resolve`

Route actions can also alter:

- outbound
- destination address
- destination port
- network strategy
- fallback delay
- UDP behavior/timeouts
- TLS fragmentation behavior
- TLS record fragmentation behavior

### PVNetwork implication

Our canonical route model should separate:

```text
RouteMatch
RouteAction
RoutePolicy
```

instead of using a single `destination -> outbound` pair.

---

# 7. GeoIP / GeoSite / ACL are real first-class Karing subsystems

The Karing app repository includes asset paths for:

- `assets/datas/geosite/`
- `assets/datas/geoip/`
- `assets/datas/acl/`
- `assets/datas/preset/`

It also contains:

- `geosite_codes.txt`
- `geoip_codes.txt`
- `geoip_subnets.json`
- ACL code lists

The dedicated ruleset repository broadens this data and is used by Karing by default.

This means Karing's GeoIP/GeoSite behavior is not an incidental feature. It is a maintained data pipeline plus UI plus core-rule capability.

---

# 8. Iran-specific preset verified in the app source

Karing ships an explicit:

- `assets/datas/preset/ir.json`

This is particularly useful for understanding product UX for Iranian users.

Examples observed in the preset:

## Blocking/security

- `geosite:category-ads`
- `geosite:category-ads-ir`
- ACL-based ad lists
- malware
- phishing
- cryptominers

## Service-specific routing

- YouTube by GeoSite + Android package
- Google Play by GeoSite + Android package
- Google by GeoSite + GeoIP
- TikTok
- Instagram
- Netflix by GeoSite + GeoIP
- WhatsApp by GeoSite/ACL + Android package + Windows/macOS process names
- Telegram by GeoSite + GeoIP + Android package + desktop process names
- Claude via ACL
- OpenAI by GeoSite + GeoIP
- GitHub by GeoSite + GeoIP + desktop process names
- Bing
- OneDrive
- Microsoft
- gaming platforms via ACL

## Iranian infrastructure/direct rules

The preset explicitly contains direct rules for at least:

- ParsPack GeoIP
- ArvanCloud GeoIP

The separate Karing ruleset repository also lists much wider Iran-specific categories, including categories for banking, government, payment, education, universities, media, shopping, travel, technology and other Iranian services.

### Important lesson

Karing can identify the same service through **multiple signals simultaneously**:

```text
GeoSite + GeoIP + Android package + desktop process
```

This is much more robust than a single domain list.

---

# 9. DNS subsystem

The core has its own DNS routing and policy model.

Observed capabilities include:

- multiple named DNS servers
- DNS-specific rules
- final/default DNS server
- reverse mapping
- cache control
- independent cache
- cache capacity
- EDNS client subnet
- strategy selection
- FakeIP support

Observed transport families include:

- UDP
- TCP
- TLS (DoT)
- HTTPS (DoH)
- QUIC (DoQ)
- HTTP/3
- local
- hosts
- FakeIP
- resolved/system service
- DHCP (when built)
- Tailscale DNS integration (when built)

DNS therefore needs to be a **separate policy engine** in PVNetwork, not a couple of text boxes in Settings.

---

# 10. Product architecture proposed for PVNetwork after this deeper inspection

The previous PVNetwork KMP/adapter direction remains correct, but the common model must be expanded to Karing-grade capability.

Recommended shape:

```text
PVNetwork UI (Compose Multiplatform / native where needed)
        |
        v
CanonicalProfile
  - ProtocolSpec
  - TransportSpec
  - SecuritySpec
  - MultiplexSpec
  - DialerSpec
        |
        +--------------------------+
        |                          |
        v                          v
CanonicalRoutingPolicy       CanonicalDNSPolicy
  - Domain                    - DNS servers
  - Suffix                    - transport
  - Keyword                   - rules
  - Regex                     - cache/fake-IP
  - CIDR                      - client subnet
  - GeoIP
  - GeoSite
  - RuleSet
  - Package
  - Process
  - Network/interface
  - Wi-Fi
  - Protocol/port
  - logical rules
        |
        v
Capability Resolver
  profile + platform + engine build -> supported/degraded/unsupported
        |
        v
EngineAdapter
  - Xray
  - Mihomo
  - WireGuard
  - OpenVPN
  - OpenConnect
  - future audited sing-box adapter
```

---

# 11. Recommendation on a future sing-box adapter

After inspecting Karing more deeply, a sing-box adapter is more strategically valuable than the initial client survey suggested because it would offer one engine boundary for many of the capabilities Karing demonstrates.

However:

- Karing's fork is GPL v3-or-later.
- upstream sing-box is also a GPL-family dependency in the currently studied line.
- process separation does **not automatically erase GPL redistribution obligations**.
- Karing ruleset/data licensing must also be audited separately.

Therefore:

**Do not copy/import the Karing fork into PVNetwork yet.**

First create a written distribution/license decision for PVNetwork. Then choose one of these approaches:

### A. GPL-compatible distribution

If PVNetwork can be distributed under a compatible open-source model, a sing-box integration becomes much easier legally.

### B. Independent product + external user-supplied core

Potentially keep the product boundary independent and let users install/select compatible engines, subject to legal review and Store/platform constraints.

### C. Preserve existing permissive/MPL engine mix and clean-room features

Keep Xray-core and other acceptable engines behind adapters, implement the **PVNetwork-owned routing/DNS/profile model**, and translate only features supported by each engine.

This is the safest engineering direction until licensing is deliberately settled.

---

# 12. What is safe to learn vs what is safe to copy

## Strong clean-room reference targets

Study and independently implement concepts from:

- Karing route editor UX
- profile/subscription separation
- IR/default/country preset concept
- GeoSite/GeoIP/ACL picker UX
- package/process-based routing UX
- routing groups
- selector/URL-test user experience
- DNS policy UX
- capability detection
- cross-platform adaptive UX

## GPL-dependent code

Do not copy into an independently licensed PVNetwork product without a GPL-compatible legal/distribution plan:

- Karing Flutter application source
- Karing sing-box fork source
- Karing rule-engine implementation
- Karing ruleset source/data where GPL obligations apply

## Missing/publicly unresolved component

Do not treat the currently referenced `vpn_service` dependency as reusable source until its provenance/source/license is actually available and pinned.

---

# 13. Concrete backlog created from Karing findings

When PVNetwork client implementation resumes, add these engineering tasks:

1. Define `CanonicalRouteRule` with Karing-grade match fields.
2. Define `RouteAction` independently from match conditions.
3. Define `CanonicalDNSPolicy` and DNS-rule model.
4. Define `ProtocolCapability`, `TransportCapability`, and `SecurityCapability` matrices per engine/platform.
5. Add GeoIP/GeoSite/RuleSet provider interfaces; do not hardwire Karing data URLs.
6. Add package/process routing capability flags by platform.
7. Add country/product preset layer using PVNetwork-owned JSON schema.
8. Build an Iran preset from independently auditable/licensed data sources.
9. Add an engine-translation report so unsupported fields are never silently discarded.
10. Prototype a `sing-box-adapter` only after license/distribution review.
11. Add Naive capability fields for ECH, QUIC and congestion-control where the chosen engine supports them.
12. Add automated tests for combined rules, e.g. `GeoSite + package + process`.

---

# Primary inspected upstream paths

Karing application:

- `KaringX/karing/LICENSE.md`
- `KaringX/karing/pubspec.yaml`
- `KaringX/karing/lib/main.dart`
- `KaringX/karing/lib/screens/diversion_group_custom_edit_screen.dart`
- `KaringX/karing/assets/datas/`
- `KaringX/karing/assets/datas/preset/ir.json`

Karing core fork:

- `KaringX/sing-box/include/registry.go`
- `KaringX/sing-box/include/quic.go`
- `KaringX/sing-box/protocol/naive/outbound.go`
- `KaringX/sing-box/option/route.go`
- `KaringX/sing-box/option/rule.go`
- `KaringX/sing-box/option/rule_action.go`
- `KaringX/sing-box/option/dns.go`
- `KaringX/sing-box/option/tls.go`
- `KaringX/sing-box/transport/`
- `KaringX/sing-box/LICENSE`

Ruleset:

- `KaringX/karing-ruleset` branch `sing`
- `KaringX/karing-ruleset/README.md`

## Bottom line

Karing is one of the most important references for the future PVNetwork client, but **the highest-value material is not merely its Flutter UI**.

The key design lessons are:

- broad build-time engine capability;
- separate protocol/transport/security layers;
- rich route matching;
- rich route actions;
- dedicated DNS policy;
- GeoSite/GeoIP/ACL/rule-set data pipeline;
- service detection by domain + IP + app/package + process;
- country-specific presets;
- capability-aware cross-platform behavior.

PVNetwork should reproduce these capabilities behind its own KMP domain and adapter contracts rather than becoming a cosmetic fork of Karing.