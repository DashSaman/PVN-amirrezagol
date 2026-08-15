# Entry 074 — REALITY — COMPLETE-REFERENCE-v2 audit

Research date: 2026-08-15

Result: **all 16 COMPLETE-REFERENCE-v2 research/reference gates PASS**.

This is research completion only. It is not implementation, device/Store certification, interoperability certification, censorship-resistance certification or a performance claim.

## Evidence baseline

Canonical engine: `XTLS/Xray-core`.

Reviewed current source pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5` (current `main` observed 2026-08-15; commit dated 2026-08-12).

License: MPL-2.0.

Latest GitHub release observed during this review: `v26.3.27`, published 2026-03-27. The repository remained active after that release; therefore the release and current-source pins are deliberately recorded separately.

Current source directly establishes that REALITY is a **security setting** separate from protocol and transport. `infra/conf/transport_internet.go` accepts `security: "reality"`, builds `realitySettings`, currently permits it with RAW/TCP, XHTTP and gRPC, and rejects legacy `security: "xtls"` as removed. This is the authoritative boundary for this entry.

## Exact 16-gate reconciliation

### 1. Server ecosystem — PASS

Primary server implementation is Xray-core itself, using the REALITY server path in `transport/internet/reality/` plus the `github.com/xtls/reality` library dependency. Server-side settings in the pinned schema include destination/type/xver, accepted server names, private key, min/max client version, maximum time difference, short IDs, optional ML-DSA-65 seed, fallback upload/download limits and key-log option.

REALITY is not treated as a standalone server product or VPN daemon: it is selected as a security layer for a compatible Xray inbound/application-protocol/transport combination. Third-party panels may generate Xray configuration, but they are not canonical REALITY implementations and are not used to redefine the protocol/security boundary.

### 2. Installer / deployment projects — PASS

Canonical deployment unit is Xray-core. Official GitHub releases publish platform archives; the reviewed latest release is `v26.3.27`. Repository-wide deployment/source evidence is indexed in `research/upstreams/xray-family/`.

Third-party installers/panels are reference-only and require independent license/supply-chain review. No third-party installer is promoted to canonical status for REALITY.

### 3. Server install matrix — PASS

REALITY has no independent installer matrix because it is compiled into Xray-core. The applicable server matrix is therefore the Xray-core binary/deployment matrix, not a fictional REALITY package matrix. Official release assets include Linux/Windows/macOS/FreeBSD/Android architecture builds across the project release set; server deployment is normally a supported Xray-core binary/container/service arrangement on an applicable host OS.

Evidence-backed N/A: there is no separate `reality-server` package to install. The relevant install lifecycle belongs to Xray-core.

### 4. Server UI / menu map — PASS via evidence-backed N/A plus config map

Canonical Xray-core is a configuration/CLI engine and does not define a canonical built-in graphical REALITY administration panel. Therefore a universal server GUI/menu hierarchy is **not applicable**.

The canonical configuration surface is mapped instead:

- stream `security = "reality"`;
- `realitySettings` object;
- server fields from the pinned `transport/internet/reality/config.proto`: `dest`, `type`, `xver`, `server_names`, `private_key`, client-version bounds, `max_time_diff`, `short_ids`, optional `mldsa65_seed`, fallback limits and `master_key_log`.

Third-party panel menu names are product-specific references and must not be represented as Xray/REALITY canonical UI.

### 5. Client install matrix — PASS

There is no independent REALITY client binary; support comes from Xray-core or compatible client applications embedding/hosting a sufficiently current compatible engine. Existing Xray-family research maps major desktop/mobile references such as v2rayN, v2rayNG, Hiddify, Karing and other wrapper/client projects with their independent licenses and platform boundaries.

Evidence-backed N/A: there is no separately installable canonical `REALITY client`. Platform support must be stated at the selected client/core/version level.

### 6. Client UI / menu map — PASS

Canonical field map is pinned from Xray source rather than copied from one GPL/custom GUI. Client-side REALITY schema currently includes:

- fingerprint;
- server name;
- public key;
- short ID;
- optional ML-DSA-65 verification material;
- `spider_x` / `spider_y` behavior fields;
- optional master key log.

Product UIs vary and are not normative. PVNetwork must model these as typed security-layer fields associated with the engine adapter and compatible transport/application-protocol combination, not as a standalone “VPN protocol screen”.

### 7. Cryptography / security boundary — PASS

Pinned `reality.go` imports and directly uses:

- X25519 via Go `crypto/ecdh`;
- HKDF-SHA-256 to derive the REALITY authentication key material;
- AES-GCM for protection/authentication of the REALITY session-ID payload;
- uTLS/TLS 1.3 handshake machinery and configurable ClientHello fingerprint;
- HMAC-SHA-512 and Ed25519-related certificate verification logic;
- optional ML-DSA-65 verification material through Cloudflare CIRCL in current source.

The client requires a TLS-1.3-capable fingerprint path; source returns an error when the chosen fingerprint cannot establish the required TLS 1.3 handshake state.

Boundary: REALITY does not replace the selected application protocol (for example VLESS) or transport. Current optional ML-DSA-65 support is recorded as current-source capability, not retroactively claimed for all versions/deployments.

### 8. Data path / wire flow — PASS

High-level current-source flow:

1. the client builds a uTLS ClientHello using the selected fingerprint and SNI/server name;
2. client session metadata includes Xray version/time/short-ID material;
3. an X25519 shared secret is derived against the configured REALITY public key;
4. HKDF-SHA-256 derives authentication material;
5. AES-GCM authenticates/seals the REALITY metadata carried in the ClientHello session-ID area;
6. the REALITY/TLS-like handshake completes and certificate/authentication verification determines whether the connection is accepted as REALITY;
7. after security establishment, the selected application protocol/flow/transport carries the actual proxied traffic.

Current source also contains invalid/redirection handling that can process a real certificate path and HTTP/2 spider behavior. This is implementation behavior, not a guarantee that a deployment is indistinguishable or uncensorable.

### 9. Ports / transports / handshake — PASS

REALITY has no reserved IANA port. It uses the listening port chosen by the enclosing Xray inbound/service.

At the reviewed pin, Xray's config builder permits REALITY only with:

- RAW/TCP;
- XHTTP;
- gRPC.

It rejects other transport combinations in this current builder. Handshake identity/configuration is driven by server name, public/private key pair, short ID, time/version constraints and fingerprint, with the exact current cryptographic behavior pinned to source above.

### 10. Deployment topologies — PASS

Evidence-backed deployment patterns:

- Xray server/inbound with REALITY security in front of a compatible application protocol and supported outer transport;
- clients connecting directly to that Xray endpoint using matching public-key/server-name/short-ID/fingerprint parameters;
- REALITY as one layer inside a typed profile stack, not a standalone tunnel;
- third-party GUI/panel or subscription systems may generate the same engine configuration but remain separate product/control-plane components.

The configured destination/fallback behavior is part of the REALITY security-layer design. Deployment diagrams must keep destination/mimic target, Xray listener, application protocol and transport as distinct elements.

### 11. Source / release / license / activity pins — PASS

- repository: `XTLS/Xray-core`;
- current source pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`;
- source pin date: 2026-08-12;
- latest release observed: `v26.3.27`, published 2026-03-27;
- license: MPL-2.0;
- repository activity: current source was still changing in August 2026.

The release and current `main` pins are intentionally distinct. A current-source feature must not be assumed to exist in the latest tagged release without checking the relevant tag.

### 12. Installer supply-chain / security risks — PASS

Risks and controls:

- use official XTLS/Xray-core repository/releases or independently verified distribution channels;
- verify official release digests/assets where available before packaging;
- do not treat third-party panels, scripts or GUI binaries as equivalent trust roots;
- review each GUI/wrapper license independently from Xray-core MPL-2.0;
- pin the engine/version used by generated profiles because REALITY fields/compatibility can evolve;
- key material (`privateKey`, public key, short IDs, optional ML-DSA material) must not leak into logs/telemetry/backups without explicit secure handling;
- `master_key_log` is diagnostic-sensitive and must default off in product operation.

Runtime malware scanning/signing/device attestation is implementation/certification work, not a hidden V2 research gate.

### 13. Upgrade / uninstall / rollback — PASS

REALITY lifecycle is the Xray-core lifecycle. Preserve canonical profile data separately from generated runtime JSON so the engine can be upgraded/rolled back without destructive profile rewriting.

Before changing Xray versions, validate the selected combination `(application protocol, transport, security=REALITY, flow, core version)` against the target adapter/version. Current source already demonstrates that compatibility constraints and removed/deprecated modes change over time.

Rollback requirements are therefore configuration/schema preservation plus a pinned known-good Xray binary, not an independent REALITY uninstall procedure.

Evidence-backed N/A: no separate REALITY package exists to uninstall.

### 14. Differences / uncertainties — PASS

Explicit boundaries/uncertainties:

- REALITY is a security-layer capability, not a standalone VPN protocol.
- VLESS/VMess/Trojan/etc. are separate application-protocol concerns.
- XTLS Vision is a separate flow/mode entry; legacy `security: "xtls"` is removed in reviewed current source.
- TLS and uTLS/fingerprinting are separately tracked capabilities.
- allowed transport combinations are version-sensitive; reviewed current source restricts REALITY to RAW/TCP, XHTTP and gRPC.
- current `main` contains optional ML-DSA-65 fields/verification, but latest tagged release support must be checked at the tag before a release-specific claim.
- third-party GUI labels are not normative REALITY semantics.
- research completion does not certify blocking resistance, stealth, performance or interoperability across every client/server pair.

### 15. REFERENCE_INDEX — PASS

`research/protocols/074-reality/REFERENCE_INDEX.md` records canonical pins, paths, repository-shared evidence and the classification boundary.

### 16. Handoff / exact continuation — PASS

After tracker promotion, continue with **Entry 075 — XTLS**. The next audit must preserve the crucial current-source distinction: legacy XTLS as a stream `security` mode is removed, while XTLS-related behavior today is represented through current flow/feature semantics (notably Vision) combined with TLS or REALITY where supported. Do not merge Entry 075 XTLS with Entry 076 XTLS Vision, and do not invent a current standalone XTLS security setting that the pinned source explicitly rejects.

## Completion decision

All 16 written V2 research/reference gates are evidence-backed or explicitly N/A where the concept does not exist independently for a security-layer capability. Entry 074 is eligible for `COMPLETE-REFERENCE-v2` tracker promotion.
