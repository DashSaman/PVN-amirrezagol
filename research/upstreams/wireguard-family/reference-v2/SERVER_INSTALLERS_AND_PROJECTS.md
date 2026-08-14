# WireGuard / AmneziaWG v2 — Server Installers and Projects

Status: evidence-backed reference slice; **not COMPLETE-REFERENCE-v2 by itself**.

## Scope and classification rule

WireGuard has no separate protocol-level server implementation: a deployed “server” is a WireGuard peer that additionally owns routing/NAT/firewall/control-plane responsibilities. Installer, panel and orchestration projects therefore MUST NOT be confused with the cryptographic/data-plane engine.

## Canonical WireGuard projects

| Project / path | Role | Upstream status / provenance | PVNetwork classification |
|---|---|---|---|
| Linux in-kernel WireGuard | data-plane implementation | Official WireGuard project; `wireguard-linux` is listed as Complete | primary Linux engine |
| `wireguard-tools` | configuration/control CLI (`wg`, `wg-quick`) | Official WireGuard project; Complete | primary control tooling |
| `wireguard-go` | cross-platform userspace implementation | Official WireGuard project; Complete-maintained/takeover-welcome | fallback/userspace engine |
| `wireguard-windows` + WireGuardNT | Windows client/service + kernel implementation | Official WireGuard projects; Complete | primary Windows implementation family |
| `wireguard-apple` / WireGuardKit | Apple client + embedding library | Official WireGuard project | primary Apple implementation/reference |
| `wireguard-android` | Android client/tunnel library | Official WireGuard project | primary Android implementation/reference |

Authoritative project inventory: <https://www.wireguard.com/repositories/>.

## Canonical installation surface

The official installation page is the preferred baseline rather than an arbitrary curl-to-shell installer. As of the 2026-08 research pass it documents Ubuntu/Debian `apt install wireguard`, Fedora `dnf install wireguard-tools`, Arch `pacman -S wireguard-tools`, Alpine `apk add -U wireguard-tools`, OpenWRT `opkg install wireguard`, FreeBSD `pkg install wireguard`, official Windows installation, Android official APK/store paths, Apple App Store paths and macOS CLI package-manager paths.

Source: <https://www.wireguard.com/install/>. Legacy package/module warnings on that page must be preserved rather than treating every historical method as equally current.

## AmneziaWG canonical projects

### Kernel/data plane

`amnezia-vpn/amneziawg-linux-kernel-module` is the official Linux kernel-module repository. Its installation surface includes the Amnezia PPA path for Ubuntu/Debian, COPR packaging for RPM-family systems and manual source/DKMS paths. Official source: <https://github.com/amnezia-vpn/amneziawg-linux-kernel-module>.

### Userspace/tooling

`amnezia-vpn/amneziawg-go` and `amnezia-vpn/amneziawg-tools` are separate from the kernel module. Tools CI creates SHA-256 sidecars for release artifacts; this is integrity evidence but not equivalent to signed provenance/attestation. Official source: <https://github.com/amnezia-vpn/amneziawg-tools>.

### Amnezia client/control plane

`amnezia-vpn/amnezia-client` is a higher-level multi-protocol application, not the canonical AWG engine. Platform/release claims must remain version/date scoped. Official releases: <https://github.com/amnezia-vpn/amnezia-client/releases>.

## wg-easy v15: control-plane privilege and exposure boundary

Pinned research observation, 2026-08-14: the current upstream `wg-easy/wg-easy` example compose uses `ghcr.io/wg-easy/wg-easy:15`, publishes UDP 51820 and TCP 51821, mounts `/lib/modules` read-only, grants `NET_ADMIN` and `SYS_MODULE`, and enables IPv4/IPv6 forwarding sysctls. Therefore the web application is not a harmless presentation-only container: its deployment has network-administration privilege and access to the host kernel-module tree. PVNetwork must treat compromise of this panel/container as a high-impact control-plane event.

The example also documents optional `HOST=0.0.0.0` and `INSECURE=false`; merely setting `INSECURE=false` is not evidence that TCP 51821 is safely exposed to an untrusted network. Recommended PVNetwork posture is to bind management to loopback/private management networking or place it behind an explicitly authenticated TLS reverse proxy, and never conflate the WireGuard UDP listener with the panel TCP listener.

Source anchors:
- <https://github.com/wg-easy/wg-easy/blob/master/docker-compose.yml>
- upstream v15 image is version-selected (`:15`) rather than floating `latest` in the example, but production certification still needs an immutable image digest and rollback receipt.

### Authentication/version caution

Historical wg-easy v14 guidance around `PASSWORD_HASH` does not safely transfer to v15. Upstream discussion in 2025 explicitly notes `PASSWORD_HASH` as a v14 mechanism while v15 moved to a different initialization/authentication model. The development compose exposes `INIT_ENABLED`, `INIT_HOST`, `INIT_PORT`, `INIT_USERNAME` and `INIT_PASSWORD` for test initialization. PVNetwork therefore MUST version-scope authentication instructions and must not publish v14 password-hash recipes as v15 facts.

Research anchors:
- <https://github.com/wg-easy/wg-easy/discussions/2110>
- <https://github.com/wg-easy/wg-easy/blob/master/docker-compose.dev.yml>

This is still not a production authentication certification: exact v15 bootstrap persistence, password hashing at rest, session/cookie policy, CSRF behavior, reverse-proxy trust and account recovery remain gates before panel reuse.

## Third-party AWG installer: useful but non-canonical

`bivlked/amneziawg-installer` remains a third-party orchestration reference. Its documentation is useful because it records operational compatibility constraints, but those constraints are not promoted to canonical protocol guarantees without corroboration from Amnezia upstream.

Observed installer claims include AWG 2.0 requiring 2.0-aware clients, client configuration retaining `S3`/`S4`, and explicit warning that AWG 2.0 configurations with nonzero S3/S4 do not interoperate with older clients lacking those fields. The documentation points to upstream kernel-module issue #168. It also documents DKMS recovery after kernel upgrades. These are strong test vectors for PVNetwork migration validation.

Sources:
- <https://github.com/bivlked/amneziawg-installer/blob/main/README.en.md>
- <https://github.com/bivlked/amneziawg-installer/blob/main/ADVANCED.en.md>

## AWG generation interoperability — evidence and unresolved boundary

Current evidence does **not** justify a generic “AWG versions interoperate” statement:

1. AmneziaWG tools issue #31 documents a 2025 mismatch where 1.5-era `I1`/related parameters were visible in client/UI documentation but not accepted by the CLI tooling under test. The issue is closed, so it is historical regression evidence, not proof of a current defect.
2. Kernel-module issue #168 (referenced by the installer documentation) concerns AWG 2.0 S3/S4 behavior and older clients.
3. Open kernel-module issue #191, filed 2026-07-24, asks whether upgrading tools/module can preserve legacy 1.x interfaces or whether 2.0 header generation changes behavior globally. Until upstream resolves or repository-level tests prove the answer, PVNetwork must treat mixed 1.x/2.0 raw-tools migration as **UNVERIFIED**.
4. A feature request for Xray support of AWG 1.5/2.0 was closed as not planned in 2026; therefore Xray must not be assumed to provide an AWG compatibility bridge.

Primary issue anchors:
- <https://github.com/amnezia-vpn/amneziawg-tools/issues/31>
- <https://github.com/amnezia-vpn/amneziawg-linux-kernel-module/issues/168>
- <https://github.com/amnezia-vpn/amneziawg-linux-kernel-module/issues/191>
- <https://github.com/XTLS/Xray-core/issues/6200>

### Required interop test matrix before strict completion

| Server / module | Client/config generation | Expected status now | Required receipt |
|---|---|---|---|
| AWG 1.x | AWG 1.x | baseline legacy | handshake + traffic + restart |
| AWG 1.5 | 1.5-aware client | evidence gap | I1/I2/etc parse + handshake |
| AWG 2.0 | 2.0-aware client with matching S3/S4 | documented intended path | handshake + traffic + config round-trip |
| AWG 2.0 | AWG 1.x / client lacking S3/S4 | incompatible-risk | explicit negative test |
| upgraded 2.0-capable module | legacy 1.x interface | **UNVERIFIED / issue #191** | no-change upgrade test |
| one host, parallel 1.x + 2.0 interfaces | mixed peers | **UNVERIFIED / issue #191** | simultaneous-interface test |

No row may be converted to PASS from documentation alone when the contract requires an execution receipt.

## Supply-chain decision table

| Surface | Source ownership | Pinning expectation | Integrity expectation | Reuse posture |
|---|---|---|---|---|
| WireGuard distro package / official installer | distro or official WireGuard | distro/release version | package signing / official distribution | preferred |
| WireGuard source build | official WireGuard | immutable commit/tag | source provenance + build receipt | acceptable when needed |
| AWG PPA/COPR packages | Amnezia packaging path | package/repo version | repository/package trust chain | preferred subject to distro validation |
| AWG source/DKMS build | official Amnezia repos | immutable commit/tag | source pin + build receipt | acceptable |
| wg-easy | third-party panel | major version **plus immutable image digest** | registry provenance + config/secret audit | reference until authenticated/exposure audit passes |
| third-party installer/panel | third party | immutable release/commit | installer + transitive downloads verified | research/reference until audited |

## Residual gates

This file does **not** close the v2 contract. Still required include exact Apple import/export/deep-link/QR evidence, immutable installer/image receipts and rollback tests, wg-easy v15 authentication/session-source audit, generation-specific AWG execution receipts, and entry-specific 002/003 reconciliation against every applicable FULL_PROTOCOL_REFERENCE_CONTRACT gate.
