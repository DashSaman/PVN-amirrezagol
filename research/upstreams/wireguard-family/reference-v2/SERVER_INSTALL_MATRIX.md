# WireGuard / AmneziaWG v2 — Server / Routing-Peer Install Matrix

Status: current evidence matrix; installation evidence is not implementation certification.

## WireGuard

| Platform | Canonical/current path | Data plane | Control/config | Evidence note |
|---|---|---|---|---|
| Ubuntu | `apt install wireguard` | Linux kernel on modern kernels | `wireguard-tools` | official install page |
| Debian | `apt install wireguard` | kernel/module according to release | `wireguard-tools` | official install page; older releases may need backports |
| Fedora | `dnf install wireguard-tools` | kernel support | `wireguard-tools` | official install page |
| Arch | `pacman -S wireguard-tools` | kernel; legacy alternatives documented for old kernels | tools | official install page |
| Alpine | `apk add -U wireguard-tools` | kernel support | tools | official install page |
| OpenSUSE/SLE | `zypper install wireguard-tools` | platform kernel | tools | official page marks version unknown/out-of-date; validate distro state before deployment |
| OpenWRT | `opkg install wireguard` | target/kernel package | tools/integration | official install page |
| FreeBSD | `pkg install wireguard` | FreeBSD kernel/userspace options | tools | official install page |
| OpenBSD | `pkg_add wireguard-tools` | OS implementation | tools | official install page |
| Windows | official installer/MSI | WireGuardNT | WireGuard Windows service/UI | official installation + repository inventory |
| macOS CLI/routing peer | Homebrew or MacPorts `wireguard-tools` | userspace Go path where applicable | tools | official install page; GUI/App Store is a separate client surface |

Authoritative baseline: <https://www.wireguard.com/install/> and <https://www.wireguard.com/repositories/>.

### Linux interpretation

For kernels where WireGuard is upstream, installing `wireguard-tools` does not mean a second protocol engine is being installed. The data plane is in the kernel and userspace tools configure it. Older compatibility-module instructions on the official page are explicitly version-sensitive and must not be copied into a modern deployment recipe without validating kernel/distro support.

## AmneziaWG

| Platform family | Official documented path | Engine distinction | Validation status |
|---|---|---|---|
| Ubuntu | prerequisites, `ppa:amnezia/ppa`, `apt-get install -y amneziawg` | official AWG Linux kernel module/package + tools | documented upstream; production receipt still required |
| Debian | Launchpad Amnezia PPA entries + `apt-get install -y amneziawg` | kernel module/package + tools | documented upstream; note legacy `apt-key` usage in current README and audit for target distro |
| Linux Mint | enable source repositories, add `ppa:amnezia/ppa`, install `amneziawg` | kernel module/package + tools | documented upstream |
| RHEL/CentOS/SUSE/Fedora family | enable COPR `amneziavpn/amneziawg`; install `amneziawg-dkms amneziawg-tools` | DKMS + tools | documented upstream; validate exact target distro/kernel |
| generic Linux source | clone official kernel-module repo; `make` / `make install` or DKMS sequence | source-built kernel module | documented upstream; immutable source pin required |
| userspace Linux/portable | official `amneziawg-go` plus `amneziawg-tools` where applicable | userspace data plane + AWG tools | project-level evidence; target packaging receipt required |

Primary source: <https://github.com/amnezia-vpn/amneziawg-linux-kernel-module>.

## Install / uninstall / rollback contract for future certification

For every target that PVNetwork eventually certifies, retain a machine-readable receipt with:

- OS image/release and kernel version;
- package repository and package version OR immutable source commit;
- installed file/module/service inventory;
- exact install command;
- exact enable/start command;
- configuration location and permissions;
- clean stop/disable sequence;
- uninstall sequence;
- rollback path to the prior known-good package/image;
- post-uninstall check showing no unintended tunnel/service remains;
- for AWG: generation/version and all peer-shared parameters required for interoperability.

## Important negative claims avoided

- This matrix does not call Windows a special WireGuard “server”; it is a peer that can be configured for routing/service use.
- Presence of an Amnezia client binary does not prove that every AWG generation is interoperable with every server implementation.
- A README installation command is documentation evidence, not a successful deployment receipt.
- Legacy/out-of-date methods visible on the WireGuard install page are not promoted as recommended current deployment paths.

## Remaining evidence

Exact Windows service/menu configuration, Apple/Android client installation/UI, AWG desktop/mobile client mapping, server UI/control-plane projects, packet-flow diagrams and topology recipes remain separate mandatory v2 files.
