# Classic tunnel reference pins — 2026-08-14

State: `EVIDENCE-BACKED V1 RESEARCH / NOT IMPLEMENTED / NOT CERTIFIED`.

Entries informed by this note:

- 009 L2TPv3
- 010 L2TPv3/IPsec
- 011 SSTP
- 012 PPTP

This file adds current source/activity/license pins for concrete open-source references. It does not make any protocol supported in PVNetwork.

## 1. accel-ppp — active Linux server/interoperability reference

Repository: `accel-ppp/accel-ppp`

GitHub repository metadata on 2026-08-14 identifies it as a high-performance Linux server supporting PPTP/L2TP/SSTP/PPPoE/IPoE.

Observed current `master` pin:

`4f562467dbdf819395e138617c2a057e02595b9e`

Commit date: `2026-08-12T19:44:57Z`

Commit subject:

`Merge pull request #344 from nuclearcat/accel-ng-ported — l2tp hidden-AVP length fix, per-session IPv6 DNS, crypto.h cleanup`

GitHub license metadata: `GPL-2.0`.

Primary evidence:

- `https://github.com/accel-ppp/accel-ppp`
- `https://api.github.com/repos/accel-ppp/accel-ppp`
- `https://github.com/accel-ppp/accel-ppp/commit/4f562467dbdf819395e138617c2a057e02595b9e`

### PVNetwork decision

`SERVER / INTEROPERABILITY REFERENCE — NOT A CROSS-PLATFORM CLIENT ENGINE`

Use it to study server behavior, authentication, configuration, regressions and interoperability for SSTP/PPTP/L2TP-related deployments. Do not infer mobile/desktop client support from a Linux access-concentrator/server implementation.

The recent L2TP maintenance is useful evidence that the project is currently active, but activity alone is not a security certification.

## 2. kittoku/Open-SSTP-Client — current Android SSTP client candidate/reference

Repository: `kittoku/Open-SSTP-Client`

Repository description: `Open SSTP Client for Android`.

Primary language reported by GitHub: Kotlin.

GitHub license metadata: `MIT`.

Observed current `main` pin:

`13f417b243988f8b6f137c661a0aed31e23d0ab5`

Commit date: `2026-05-02T10:51:26Z`

Commit subject: `RLS: 1.10.2`.

Repository metadata showed a 2026 push and the repository was not archived when reviewed.

Primary evidence:

- `https://github.com/kittoku/Open-SSTP-Client`
- `https://api.github.com/repos/kittoku/Open-SSTP-Client`
- `https://github.com/kittoku/Open-SSTP-Client/commit/13f417b243988f8b6f137c661a0aed31e23d0ab5`

### PVNetwork decision

`HIGH-VALUE ANDROID SSTP REFERENCE / CANDIDATE FOR DEEP AUDIT`

It is materially more relevant to Android SSTP client research than inferring client capability from server-side SoftEther or accel-ppp code. Before any reuse decision, a deeper audit must still cover:

- complete source architecture and Android `VpnService` ownership;
- profile and credential persistence;
- TLS/certificate verification behavior;
- PPP/SSTP state handling;
- DNS/routes/IPv6/split tunneling;
- background/reconnect/battery behavior;
- dependencies and transitive license/SBOM;
- issues/releases/regressions;
- current Google Play requirements and real-device tests.

MIT metadata is favorable for research/reuse evaluation but does not replace dependency and asset-license review.

## 3. reliablehosting/sstp-client — historical Linux/macOS SSTP source reference

Repository: `reliablehosting/sstp-client`

Repository description says it is an SSTP client implementation for Linux / Mac OS X.

GitHub license metadata: `GPL-2.0`.

Observed current `master` pin:

`383eebbdc18fe081aa19c04af2f3acaf970b5824`

Last source commit observed: `2017-03-27T18:10:59Z`.

The repository was not marked archived, but the source branch has not advanced since 2017. Therefore it must **not** be treated as a current maintained engine merely because the repository remains public/non-archived.

Primary evidence:

- `https://github.com/reliablehosting/sstp-client`
- `https://api.github.com/repos/reliablehosting/sstp-client`
- `https://github.com/reliablehosting/sstp-client/commit/383eebbdc18fe081aa19c04af2f3acaf970b5824`

### PVNetwork decision

`HISTORICAL LINUX/UNIX SSTP SOURCE REFERENCE — NOT PRIMARY MODERN ENGINE CANDIDATE`

It remains useful for understanding protocol/client integration history and comparison with newer implementations, but its maintenance age and GPL license materially reduce its suitability as the primary cross-platform PVNetwork SSTP engine.

## 4. Protocol-specific conclusions

### 009 L2TPv3

No evidence in this file changes the architecture decision: prefer mature OS/kernel/network-stack or dedicated site-to-site implementations. accel-ppp's L2TP support is useful surrounding interoperability evidence, but ordinary L2TP access-server support is not proof of L2TPv3 pseudowire implementation.

### 010 L2TPv3/IPsec

Keep L2TPv3 and IPsec as composable capabilities. The strongSwan family research owns IKE/IPsec security-layer evidence; this classic-tunnel file does not duplicate it.

### 011 SSTP

Current candidate order at the research layer:

1. Windows: native Windows SSTP stack for standard Windows profiles;
2. Android: `kittoku/Open-SSTP-Client` as a high-value open-source reference/candidate for deep audit;
3. Linux/Unix: dedicated SSTP client/network-manager paths require current source/maintenance review; `reliablehosting/sstp-client` is historical reference rather than a preferred modern baseline;
4. server/interoperability: accel-ppp and SoftEther are useful server references, not automatic client-engine choices.

No Apple/mobile Store support is claimed from these references.

### 012 PPTP

The availability and active maintenance of a server such as accel-ppp does not change the product classification:

`LEGACY / INSECURE / COMPATIBILITY-ONLY`

PVNetwork must never make PPTP a recommended/default secure VPN option. Any future optional compatibility module requires explicit demand, OS availability, warning UX, and security review.

## 5. Anti-confusion rules

- server support != client support;
- repository activity != security certification;
- non-archived != actively maintained;
- repository-level license != complete dependency/SBOM clearance;
- one platform implementation != cross-platform support;
- L2TP != L2TPv3;
- SSTP over TLS != OpenVPN or generic HTTPS proxy support;
- PPTP availability != acceptable modern security.

## 6. Exact next research actions

For entry 011 SSTP, deep-audit `kittoku/Open-SSTP-Client` source architecture, VpnService lifecycle, TLS/certificate validation, storage/config, dependencies, issues/releases, and menu/import UX.

For 009/010, pin Linux kernel L2TPv3/source documentation and an actual L2TPv3-capable userspace/network implementation rather than extrapolating from ordinary L2TP servers.

For 012, collect authoritative legacy-security/deprecation evidence later without investing equivalent engineering depth unless actual compatibility demand justifies it.
