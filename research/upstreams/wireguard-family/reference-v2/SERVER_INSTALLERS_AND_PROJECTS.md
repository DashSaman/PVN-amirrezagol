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

The official installation page is the preferred baseline rather than an arbitrary curl-to-shell installer. As of the 2026-08 research pass it documents:

- Ubuntu/Debian: `apt install wireguard`;
- Fedora: `dnf install wireguard-tools`;
- Arch: `pacman -S wireguard-tools`;
- Alpine: `apk add -U wireguard-tools`;
- OpenWRT: `opkg install wireguard`;
- FreeBSD: `pkg install wireguard`;
- Windows: official installer/MSI path;
- Android: Play Store and direct official APK;
- iOS/macOS: App Store;
- macOS CLI: Homebrew/MacPorts `wireguard-tools` paths.

Source: <https://www.wireguard.com/install/>. The page itself labels several legacy package/module paths as out of date; PVNetwork must preserve those warnings instead of presenting all listed methods as equally current.

## AmneziaWG canonical projects

### Kernel/data plane

`amnezia-vpn/amneziawg-linux-kernel-module` is the official Linux kernel-module repository. Its README documents:

- Ubuntu: prerequisites + `ppa:amnezia/ppa` + `apt-get install -y amneziawg`;
- Debian: Launchpad PPA configuration followed by `apt-get install -y amneziawg`;
- RHEL/CentOS/SUSE/Fedora family: COPR `amneziavpn/amneziawg`, then `amneziawg-dkms amneziawg-tools`;
- manual source/DKMS build paths.

Official source: <https://github.com/amnezia-vpn/amneziawg-linux-kernel-module>.

### Userspace/tooling

`amnezia-vpn/amneziawg-go` and `amnezia-vpn/amneziawg-tools` are kept separate from the kernel module. The tools CI builds `awg` and `awg-quick` for Ubuntu 22.04 and Alpine 3.19 and creates SHA-256 sidecars for release artifacts. That is useful supply-chain evidence, but a CI-produced checksum is not equivalent to a signed release/provenance attestation.

Official source: <https://github.com/amnezia-vpn/amneziawg-tools>.

### Amnezia client/control plane

`amnezia-vpn/amnezia-client` is a higher-level multi-protocol application and must be classified as a client/control product, not the canonical AWG protocol engine. Current releases expose multiple platform assets; release availability can differ by OS/version, so a matrix must be version/date scoped rather than saying “all platforms supported” without evidence.

Official releases: <https://github.com/amnezia-vpn/amnezia-client/releases>.

## Third-party installers and managers

Third-party installer/panel projects may be useful deployment references, but they are never promoted to canonical-engine status merely because they automate installation.

Example observed during this pass: `bivlked/amneziawg-installer` describes an AmneziaWG 2.0 Ubuntu/Debian installer, pins downloaded helper scripts to a release branch by default, and verifies hard-coded SHA-256 values for downloaded helper scripts. This is stronger than an unpinned `curl | bash`, but remains third-party and requires its own license, maintainer, release and rollback audit before reuse.

PVNetwork policy:

1. prefer distribution/official project package paths for canonical engine installation;
2. classify third-party installers as orchestration only;
3. never inherit an installer's license/supply-chain assumptions into the engine classification;
4. pin installer release/commit and downloaded dependencies before any production reuse;
5. require uninstall/rollback receipts in a later implementation-certification phase.

## Supply-chain decision table

| Surface | Source ownership | Pinning expectation | Integrity expectation | Reuse posture |
|---|---|---|---|---|
| WireGuard distro package / official installer | distro or official WireGuard | distro/release version | package signing / official distribution | preferred |
| WireGuard source build | official WireGuard | immutable commit/tag | source provenance + build receipt | acceptable when needed |
| AWG PPA/COPR packages | Amnezia project packaging path | package/repo version | repository/package trust chain | preferred AWG Linux path subject to distro validation |
| AWG source/DKMS build | official Amnezia repos | immutable commit/tag | source pin + reproducible build receipt where possible | acceptable |
| third-party installer/panel | third party | immutable release/commit | installer + transitive downloads verified | research/reference until audited |

## Residual gates

This file does **not** close the v2 contract. Still required: exact per-platform install matrix, UI/menu evidence, data path/wire flow, deployment topologies, generation-specific AWG interop receipts, rollback/uninstall evidence and entry-specific 002/003 reconciliation.
