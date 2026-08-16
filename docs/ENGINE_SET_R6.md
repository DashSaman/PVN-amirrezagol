# PVNetwork R6 — Minimum Viable Engine Set Approval

Date: 2026-08-16
Status: **PASS — minimum initial engine set approved for implementation planning**

This is an architecture/integration approval only. It does **not** mean any protocol is implemented, built, tested, interoperable, device verified, Store verified, certified, or production ready.

## Why this set

The roadmap makes M2 the first networking wave and names WireGuard, OpenVPN and Xray. R6 therefore approves the smallest set needed to unblock that wave while avoiding the larger candidate list until there is a concrete coverage need.

Initial set:

1. **Official WireGuard implementation family**
2. **OpenVPN implementation family with platform-specific legal boundaries**
3. **Xray-core family**

AmneziaWG, Mihomo, OpenConnect, strongSwan/native IPsec, SoftEther and Hysteria2 remain researched candidates for later waves. They are not rejected; they are intentionally not added to the initial attack/maintenance surface.

## 1 — WireGuard

Repository evidence:

- `research/protocols/002-wireguard/V1_GATE_RECONCILIATION.md`
- `research/upstreams/wireguard-family/SUPPORT_REUSE_DECISIONS.md`

Decision: **APPROVED FOR ADAPTER IMPLEMENTATION, REUSE-FIRST**.

Integration strategy:

- use official/mature WireGuard implementations for each platform;
- never reimplement WireGuard cryptography;
- prefer native/kernel facilities where available and an official userspace implementation where required;
- keep engine/platform details behind the product-owned `CoreAdapter` and platform networking boundary.

License/source boundary:

- Linux kernel WireGuard remains kernel-side GPL code and is controlled through supported userspace interfaces rather than copied into PVNetwork;
- `wireguard-go` is a reusable MIT userspace candidate;
- Android/Apple/Windows official projects retain their own pinned-artifact and packaging obligations.

**Dependency import gate:** before a concrete platform adapter imports or bundles an upstream artifact, lock that platform's exact release/tag/commit, checksum where applicable, transitive license set, and packaging/update strategy. Research completion alone is not an import pin.

Store/platform rationale: the family has official native/userspace paths across the target OS families, but Store/device behavior is downstream verification and is not claimed by R6.

## 2 — OpenVPN

Repository evidence:

- `research/protocols/001-openvpn/V1_GATE_RECONCILIATION.md`
- `research/upstreams/openvpn-family/SUPPORT_REUSE_DECISIONS.md`
- `research/protocols/001-openvpn/reference-v2/REFERENCE_INDEX.md`

Decision: **APPROVED AS AN M2 PROTOCOL FAMILY, WITH PLATFORM-SPECIFIC ENGINE GATES**.

The repository evidence does not justify one universal embedded OpenVPN library for a closed product. R6 therefore records explicit boundaries instead of hiding license conflicts:

- **OpenVPN 2.x**: GPLv2; a controlled desktop subprocess/service boundary is the preferred first integration candidate where distribution obligations are satisfied.
- **OpenVPN 3 Linux**: repository evidence records an AGPL-3.0-era license path and treats proprietary linking/embedding as a red path without a compatible commercial/legal strategy. It is **not approved for silent embedding**.
- **TunnelKit**: Apple candidate, repository evidence records tag `4.1.0` and MPL-2.0; exact dependency/SBOM/product-fit checks are still required before import.
- **ics-openvpn**: useful Android reference/reuse candidate with GPL-family obligations; do not embed it into a closed application unless the selected distribution model is demonstrably compatible.

Store/platform rationale: OpenVPN is required by the roadmap and has mature platform implementations, but Android/Apple packaging and Store acceptance are separate downstream gates. A platform adapter may remain blocked while another platform proceeds.

**Dependency import gate:** no OpenVPN implementation is imported until its exact source/release pin and license/distribution strategy are recorded for that platform.

## 3 — Xray-core

Repository evidence:

- `research/protocols/037-entry/README.md`
- `research/protocols/037-entry/REFERENCE_V2_AUDIT.md`
- `research/upstreams/xray-family/SUPPORT_REUSE_DECISIONS.md`

Research pins:

- `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`
- root license at that pin: **MPL-2.0**
- narrow wrapper reference: `XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a`, wrapper root **MIT**

Decision: **APPROVED FOR ADAPTER IMPLEMENTATION AFTER STABLE-RELEASE IMPORT GATE**.

Integration strategy:

- prefer a narrow `libXray`-style native wrapper on mobile/Apple only after lifecycle, dependency and Store review;
- prefer a managed Xray subprocess on desktop/server-like targets where it improves crash isolation, replaceability and MPL component separation;
- never fork a GPL GUI merely to obtain Xray connectivity for a closed PVNetwork product;
- canonical protocol/security/flow/transport models stay product-owned and version-aware.

The V2 audit explicitly says the recorded research `main` pin is not automatically a production core. It records 2026 release evidence but leaves exact production-release/SBOM selection downstream.

**Dependency import gate:** before source/binary import, select one exact stable Xray release, record its full commit/tree/checksums, verify MPL and transitive dependency licenses/security advisories, pin the matching wrapper/core relationship, and record update/rollback policy. No release pin is invented in this R6 decision.

Store/platform rationale: cross-platform core/wrapper evidence makes Xray a strong first-wave candidate, but mobile Network Extension/VpnService lifecycle and Store feasibility remain implementation/verification work.

## R6 license and attack-surface rule

The approved set is deliberately three families. Additional researched engines are added only when they provide a capability that cannot be supplied safely and maintainably by the approved set or by a native platform facility.

GPL/AGPL/reference-only code stays outside the closed product unless an explicit compatible distribution/legal strategy is approved. Proprietary implementations remain proprietary/reference-only. No cryptography is reimplemented from scratch.

## Status boundary at R6 close

- Research V1: 93/93 complete.
- Research V2: 93/93 complete.
- R5 architecture: PASS.
- R6 engine-set approval: PASS.
- Protocol IMPLEMENTED: **none claimed by R6**.
- Protocol BUILT/TESTED/INTEROPERABILITY VERIFIED/DEVICE VERIFIED/PRODUCTION READY: **none claimed by R6**.

## Next work

Proceed to M0 application foundation. M0 may implement and test product-owned models/contracts/build tooling without importing any protocol engine. Each later engine adapter must pass the dependency-import gate above immediately before its first source/binary dependency is added.
