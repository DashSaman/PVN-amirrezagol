# SOCKS family — V1 shared source audit

Review date: 2026-08-14

Scope: entries 049 SOCKS4, 050 SOCKS4a, 051 SOCKS5.

This shared dossier captures reusable evidence only. Each numbered entry still requires an independent 20-gate conclusion because addressing, DNS, authentication and UDP capabilities differ materially between the three protocol generations.

## 1. Protocol authorities

### SOCKS4

Historical specification mirror used by maintained implementations:

- `https://www.openssh.com/txt/socks4.protocol`
- maintained curl source explicitly cites this document from its SOCKS4 implementation.

Core semantics:

- version byte 4;
- TCP CONNECT and BIND model;
- destination is IPv4 address + port;
- NUL-terminated USERID field;
- no UDP relay;
- no protocol-level encryption;
- USERID/identd-era identification is not modern cryptographic authentication.

### SOCKS4a

Historical extension specification:

- `https://www.openssh.com/txt/socks4a.protocol`

The extension preserves SOCKS4 framing but allows the client to send a hostname after USERID. The client uses a marker address such as `0.0.0.1`; the proxy resolves the hostname. This makes **remote DNS resolution** the defining 4a difference. It does not add UDP, encryption or strong authentication.

### SOCKS5

IETF standards-track authorities:

- RFC 1928 — SOCKS Protocol Version 5;
- RFC 1929 — Username/Password Authentication for SOCKS V5;
- RFC 1961 — GSS-API Authentication Method for SOCKS Version 5.

RFC 1928 adds method negotiation, CONNECT/BIND/UDP ASSOCIATE and typed addresses for IPv4, domain names and IPv6. RFC 1929 explicitly warns that its password is carried in cleartext and is unsuitable where sniffing is practical. RFC 1961 defines a GSS-API mechanism with integrity and optional confidentiality depending on the negotiated mechanism/protection level.

Base SOCKS5 itself is **not an encrypted tunnel**. Security depends on the selected authentication/protection method and the lower-layer/network environment.

## 2. Current maintained client implementation — curl

Repository: `curl/curl`

Reviewed stable release:

- release/tag: `curl-8_21_0`
- release published: 2026-07-29
- signed tag object: `3f00a2f6fa97f7721b65606954aac979dcb6caac`
- release commit: `68720b4837284335b2d63cb358f8f6ce65f5bc55`
- release tree: `e23d273aeb073c04aa5b073b35c9fde16979c896`
- implementation file: `lib/socks.c`
- license/SPDX: `curl` license, permissive notice-based grant allowing use/copy/modify/merge/publish/distribute/sell under the COPYING terms.

The exact `lib/socks.c` state machine independently verifies:

- SOCKS4 and SOCKS4a are distinct modes;
- SOCKS4 locally resolves only IPv4;
- SOCKS4a sends the hostname to the proxy and sets a `0.0.0.1` marker;
- current curl SOCKS4 implementation uses CONNECT, not UDP;
- SOCKS5 negotiates no-auth, GSS-API when compiled/selected, and username/password;
- SOCKS5 can choose local vs proxy-side hostname resolution;
- credentials are bounded and handled separately from destination address;
- explicit protocol/state/error diagnostics exist.

Current libcurl API exposes distinct proxy types for SOCKS4, SOCKS4A, SOCKS5 and SOCKS5_HOSTNAME, reinforcing the requirement that PVNetwork not collapse remote-DNS behavior into one generic SOCKS toggle.

The 8.21.0 release itself also records security fixes in curl. Those are curl-release facts, not evidence that the SOCKS standards were vulnerable; they reinforce the need to pin and patch the chosen runtime.

## 3. Current maintained server/client reference — Dante

Official project: Inferno Nettverk Dante, `https://www.inet.no/dante/`.

Reviewed current public release:

- Dante `1.4.4`;
- source release dated 2024-12-15 on the official download page;
- authoritative tarball SHA-256: `1973c7732f1f9f0a4c0ccf2c1ce462c7c25060b25643ea90f9b98f53a813faec`;
- license: BSD/CMU-type, complete source code published by the vendor.

Official current status/docs state:

- server supports SOCKS4 and SOCKS5;
- SOCKS4: TCP, no UDP;
- SOCKS5: TCP + UDP and IPv4 + IPv6;
- SOCKS4a is the hostname-resolution workaround where the SOCKS server resolves the name;
- SOCKS5 implements RFC 1928, RFC 1929 and RFC 1961;
- client `socksify` can be configured via `SOCKS_SERVER`, `SOCKS4_SERVER`, `SOCKS5_SERVER` or `/etc/socks.conf`-style routing rules;
- v4 does not support authentication in the Dante model;
- username/password authentication transmits the password in cleartext and is not recommended over an insecure network;
- supported server platforms are primarily Unix/Linux/BSD; Windows is not supported by Dante server;
- client cross-compilation to Android is documented.

Dante is a strong server/client interoperability and configuration reference. It is not the only valid implementation and need not be embedded into PVNetwork.

## 4. Capability matrix

| Capability | SOCKS4 | SOCKS4a | SOCKS5 |
|---|---|---|---|
| TCP CONNECT | yes | yes | yes |
| TCP BIND | protocol supports | inherits v4 | yes |
| UDP ASSOCIATE | no | no | yes |
| IPv4 target | yes | yes | yes |
| IPv6 target | no | no | yes |
| Domain target without client DNS | no | yes, server resolves | yes via DOMAINNAME / remote-DNS mode |
| Built-in method negotiation | no | no | yes |
| Username/password auth | no standardized strong auth | no standardized strong auth | RFC 1929 option, cleartext unless protected by lower layer |
| GSS-API | no | no | RFC 1961 option |
| Protocol-level encryption by default | no | no | no |

Do not infer BIND/UDP/GSS-API support merely because a product says “SOCKS5”; certify each advertised command/auth mode against the chosen implementation.

## 5. PVNetwork architecture model

Treat SOCKS as an application proxy layer, not a VPN/TUN protocol by itself.

Canonical product objects should separate:

- SOCKS generation: 4 / 4a / 5;
- endpoint/port;
- DNS resolution policy: local vs proxy-side where valid;
- authentication method and secret references;
- command/capability set: CONNECT/BIND/UDP ASSOCIATE;
- address family support;
- selected implementation/core/version;
- optional lower-layer protection/chaining;
- product routing/DNS/TUN/per-app state outside SOCKS identity;
- original imported source separately from normalized/generated engine config.

A local SOCKS listener is not proof that full-device routing/TUN is active.

## 6. Secrets/privacy/security

1. SOCKS4/4a provide no confidentiality and no modern standardized authentication.
2. SOCKS5 no-auth is unauthenticated by design.
3. RFC 1929 username/password sends the password in cleartext inside the SOCKS subnegotiation; use only over a trusted/protected path or a separately secure lower layer.
4. RFC 1961 can provide authentication/integrity/optional confidentiality, but only when the implementation and selected GSS mechanism/protection level support it.
5. Remote DNS (4a or SOCKS5 hostname mode) changes privacy/leak behavior and must be explicit.
6. Credentials must be stored in secure credential storage and redacted from URIs/configs/logs/support bundles.
7. BIND and UDP ASSOCIATE increase reachable/network-facing behavior and need distinct policy/firewall/testing.
8. Do not market any SOCKS generation as equivalent to an encrypted VPN.

## 7. UI/config/persistence evidence

The canonical protocols are network standards rather than GUI applications, so exhaustive GUI screens are not an applicable upstream requirement for V1. Current source-backed user-facing configuration evidence exists in:

- curl/libcurl distinct SOCKS proxy-type settings and credential/DNS options;
- Dante `socks.conf`, `sockd.conf`, `socksify`, environment variables and command/method/routing configuration.

PVNetwork should render these capabilities through its own typed profile UI rather than copy an unrelated client GUI.

Persistence requirements:

- proxy endpoint and generation are ordinary profile state;
- username/password/GSS credential handles are secret/security state;
- DNS mode is privacy-sensitive profile state;
- active BIND/UDP association/session state is transient runtime state.

## 8. Tests, issues, release and diagnostics evidence

- curl 8.21.0 is a current signed stable release with a dedicated SOCKS state machine and broad CI/test infrastructure;
- Dante 1.4.4 has a vendor-published source digest and current documentation/status pages;
- current curl code exposes explicit SOCKS protocol states and error codes for resolution, version, authentication and request failures;
- current Dante docs expose logs, authentication/routing policy and command-specific behavior.

PVNetwork later acceptance must independently test malformed frames, address-family boundaries, DNS location, auth negotiation, BIND/UDP behavior where advertised, reconnect/timeouts and credential redaction. Runtime/device certification is later evidence, not a hidden V1 research gate.

## 9. Reuse decision

- Protocol behavior can be independently implemented from the published specifications/RFCs.
- curl is a current permissively licensed client implementation/reference, but embedding all of libcurl solely for SOCKS should be justified against an already-approved networking core.
- Dante is a mature BSD/CMU-style server/client reference and possible server component where its platform/config model fits.
- Prefer one maintained approved engine with clear patch cadence over multiple duplicate SOCKS implementations.
- Preserve notices and full dependency/SBOM obligations for any reused implementation.

## 10. Shared uncertainties for later certification/V2

- exact production engine and per-platform release;
- exact BIND and UDP ASSOCIATE support in the chosen client/server pair;
- GSS-API mechanism availability on each target OS;
- DNS leak/fallback behavior under failures;
- proxy chaining semantics;
- IPv6 and NAT/firewall behavior;
- mobile background lifecycle/TUN integration;
- exact performance and concurrent-session limits;
- V2 server installer/menu/wire/handshake/port/deployment evidence.
