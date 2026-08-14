# Linux Tunnel / IPsec Composition Family — V1 Source / Tree / Build Audit

Review date: 2026-08-14
Scope: shared source evidence for entries 063–070 only where the same Linux kernel/iproute2 implementation boundary applies.

This file closes the source-inventory portion of `research/PROTOCOL_RESEARCH_TEMPLATE.md` without re-running protocol-specific research. Protocol-specific semantics and decisions remain in each numbered entry reconciliation.

## A. Linux kernel implementation baseline

### Identity and version pin

- Project: Linux kernel
- Canonical project site: `https://www.kernel.org/`
- Reviewed source repository: `https://github.com/torvalds/linux`
- Upstream owner/maintainer boundary: Linux kernel upstream; the reviewed repository is Linus Torvalds' kernel source tree.
- Default branch observed through GitHub repository metadata: `master`.
- Exact source-analysis commit: `ad8d485e665829ecbf3c97b22ce251f8ff5f8037`.
- Commit tree SHA: `cdfb6ad04701df82290575494f40fbb00efe0512`.
- Latest stable tag observed in the reviewed tag feed: `v7.1` at `8cd9520d35a6c38db6567e97dd93b1f11f185dc6`.
- Newer pre-release tags existed at review time (`v7.2-rc*`); they are not represented as stable releases. The source analysis remains pinned to the exact commit above rather than silently floating with `master`.
- Main repository language reported by GitHub: C. The full kernel tree also contains Rust support, scripts and generated/build tooling; the tunnel/XFRM/VXLAN implementation paths reviewed for this family are C.

### License / legal boundary

- Root license pointer: `COPYING` at the pinned commit.
- The kernel tree uses SPDX identifiers on individual files; tunnel-family paths must be interpreted by their file-level SPDX identifiers, not by a blanket assumption.
- Examples already reviewed in the shared family evidence:
  - `net/ipv4/ip_gre.c` — GPL-2.0-or-later SPDX.
  - `net/ipv4/ipip.c` — GPL-2.0-or-later SPDX.
  - `net/ipv4/ip_vti.c` — GPL-2.0-or-later SPDX.
  - `drivers/net/vxlan/vxlan_core.c` — GPL-2.0-only SPDX.
- PVNetwork reuse classification for kernel source: `REFERENCE-ONLY` for source copying/linking in the planned closed application boundary; prefer stable OS/kernel/netlink interfaces. Any future direct incorporation requires separate legal review at the exact file boundary.

### Complete source-tree reference

The complete recursive manifest is intentionally referenced rather than copied into PVNetwork:

`https://api.github.com/repos/torvalds/linux/git/trees/cdfb6ad04701df82290575494f40fbb00efe0512?recursive=1`

Pinned top-level/source areas relevant to understanding the repository:

- `arch/` — architecture-specific kernel code.
- `block/` — block layer.
- `certs/` — certificate/key handling used by kernel build/runtime facilities.
- `crypto/` — kernel cryptographic API/algorithms; not a reason for PVNetwork to reimplement protocol cryptography.
- `Documentation/` — upstream kernel documentation.
- `drivers/` — device/network drivers; VXLAN implementation is under `drivers/net/vxlan/` in the reviewed tree.
- `fs/` — filesystems.
- `include/` — public/internal kernel headers and UAPI definitions.
- `init/` — initialization.
- `ipc/` — IPC facilities.
- `kernel/` — core kernel facilities.
- `lib/` — common kernel libraries/helpers.
- `LICENSES/` — SPDX license texts/exceptions used by the tree.
- `mm/` — memory management.
- `net/` — networking stack; GRE/IPIP/XFRM evidence for this family is principally here.
- `rust/` — Rust support within the wider kernel source tree.
- `samples/` — kernel API/examples.
- `scripts/` — Kbuild/configuration/code-generation/package helper tooling; `scripts/package/` is the source packaging helper boundary.
- `security/` — Linux security subsystem.
- `tools/` — userspace developer/testing tools, including `tools/testing/selftests/`.
- `usr/` — initramfs-related build support.
- `virt/` — virtualization support.

Important family paths:

- GRE: `net/ipv4/ip_gre.c` and related tunnel/UAPI/rtnetlink support.
- IPIP: `net/ipv4/ipip.c` and common IPv4 tunnel helpers.
- VTI: `net/ipv4/ip_vti.c` plus IPv6 counterpart where applicable.
- XFRM: `net/xfrm/`, related `include/net/xfrm.h`, UAPI and protocol transform hooks.
- VXLAN: `drivers/net/vxlan/`, especially `vxlan_core.c`.
- Network tests: `tools/testing/selftests/net/` and related networking selftests where applicable.

### Build / CI / test / packaging inventory

- Primary kernel build system: Kbuild/Kconfig driven by root `Makefile`, `Kconfig` hierarchy and `scripts/`.
- Architecture toolchains live under `arch/*` and standard kernel build tooling.
- Tests: `tools/testing/selftests/` is the in-tree selftest framework; networking tests are under its network-related subtrees. Additional subsystem tests and static-analysis/build review exist outside a single GitHub Actions workflow.
- GitHub Actions are not treated as the canonical kernel validation surface; absence of a PVNetwork-captured GitHub workflow is not converted into a fabricated CI result.
- Packaging helper surface exists under `scripts/package/`; distribution packages/installers are downstream artifacts and must be reviewed separately if selected for deployment.
- Documentation/examples are in `Documentation/`, `samples/` and relevant `tools/` material.
- Platform scope: Linux kernel. Other operating systems/vendor network OSes are interoperability targets, not implementations proven by this source tree.
- Third-party/vendor code, assets and localization are not used as completion shortcuts; no consumer UI asset set applies to these kernel infrastructure entries.

## B. iproute2 userspace control-plane baseline

### Identity and version pin

- Project: iproute2
- Canonical upstream homepage recorded by repository metadata: `https://git.kernel.org/pub/scm/network/iproute2/iproute2.git`
- Reviewed GitHub repository: `https://github.com/iproute2/iproute2`
- Repository metadata explicitly describes the GitHub repository as a publish-only source mirror and points contributors to `README.devel`.
- Default branch observed: `main`.
- Exact source-analysis commit: `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`.
- Commit tree SHA: `c822a724f3a2d2cb8ca93b2329358ee90d02c2e2`.
- Latest reviewed release tag: `v7.1.0` at `d7d8203844bad5f9d8a6f3708c9b39141369aa51`.
- Main language reported by repository metadata: C.

### License / legal boundary

- Root license file: `COPYING`.
- GitHub repository metadata reports `GPL-2.0`; individual files may carry their own SPDX expressions and must be respected path-by-path.
- PVNetwork reuse classification: `REFERENCE-ONLY` for source copying/linking in the planned closed application boundary; use structured netlink/native interfaces rather than embedding iproute2 or concatenating uncontrolled shell commands. Direct code reuse requires exact file-level legal review.

### Complete source-tree reference

The complete recursive manifest is referenced at the exact reviewed tree:

`https://api.github.com/repos/iproute2/iproute2/git/trees/c822a724f3a2d2cb8ca93b2329358ee90d02c2e2?recursive=1`

Major source areas in the reviewed repository include:

- `ip/` — `ip` command implementation, including link/tunnel/XFRM control modules.
- `lib/` — shared userspace networking/parsing/netlink helpers.
- `include/` — userspace headers and copied/synchronized UAPI definitions.
- `tc/` — traffic-control command implementation.
- `bridge/` — bridge/FDB/VLAN control.
- `devlink/` — devlink control utility.
- `rdma/` — RDMA utility.
- `tipc/` — TIPC utility.
- `vdpa/` — vDPA utility.
- `misc/` — auxiliary networking tools.
- `man/` — manual pages.
- `testsuite/` — upstream userspace tests.
- `bash-completion/` — shell completion resources.

Family-specific control paths already pinned in shared evidence:

- `ip/link_gre.c` — GRE/GRETAP link attributes.
- `ip/link_vti.c` — VTI attributes.
- `ip/iplink_vxlan.c` — VXLAN attributes.
- `ip/xfrm_*` / `ip xfrm` implementation area — XFRM state/policy control.
- generic route/link/address/tunnel code in `ip/` supports lifecycle integration.

### Build / CI / test / packaging inventory

- Root build entry: `Makefile`; configure/build support is kept in the repository root and subdirectory Makefiles.
- Development/contribution guidance: `README.devel`.
- Security guidance: `SECURITY.md`.
- Tests: `testsuite/`; feature behavior is also constrained by Linux UAPI/kernel support.
- Manual/documentation surface: `man/`, `README`, `README.devel`.
- Packaging is primarily distribution-owned; this source mirror is not treated as a universal installer. Exact distro packaging is a deployment/V2 concern when selected.
- Platform scope: Linux userspace/control plane.
- UI/assets/localization: no canonical consumer GUI; shell completion/manual resources are administrative UX, not consumer application screens.

## C. Gate-4 / Gate-5 closure for entries 063–070

For V1 completion purposes, this shared audit provides the mandatory source-tree reference/manifest, default branch, exact source pin, latest reviewed tag, language/build/test/package/documentation boundaries for the Linux kernel and iproute2 candidates used across entries 063–070.

It does **not** make every family entry complete by itself. Each numbered entry still requires its own 20-gate reconciliation and protocol/composition-specific evidence. Runtime/device/vendor interoperability remains acceptance work unless a numbered research contract explicitly makes a particular receipt mandatory.

## D. Evidence receipts used for this audit

Reviewed through authoritative GitHub repository/API records on 2026-08-14:

- `torvalds/linux` repository metadata: default branch `master`, main language C.
- `torvalds/linux` tag feed: stable `v7.1` and newer `v7.2-rc*` prereleases observed separately.
- Linux pinned commit `ad8d485e665829ecbf3c97b22ce251f8ff5f8037`: tree SHA `cdfb6ad04701df82290575494f40fbb00efe0512`.
- Linux pinned root contents: `COPYING`, build/tooling and source hierarchy verified at the pinned commit.
- `iproute2/iproute2` repository metadata: default branch `main`, C, GPL-2.0 metadata, publish-only mirror description and canonical git.kernel.org homepage.
- iproute2 tag feed: `v7.1.0` at `d7d8203844bad5f9d8a6f3708c9b39141369aa51`.
- iproute2 pinned commit `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`: tree SHA `c822a724f3a2d2cb8ca93b2329358ee90d02c2e2`.
- iproute2 pinned root contents: `COPYING`, `Makefile`, `README`, `README.devel`, `SECURITY.md` and source subtrees verified at the pinned commit.
