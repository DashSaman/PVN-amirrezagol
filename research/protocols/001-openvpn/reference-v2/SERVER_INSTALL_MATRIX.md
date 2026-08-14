# OpenVPN — Server Installation Matrix

Reference layer: `COMPLETE-REFERENCE-v2`

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

This matrix separates **upstream/community daemon feasibility**, **official commercial Access Server support**, and **third-party installer/container support**. A green upstream build does not mean every deployment project supports the same OS.

Legend:

- `PRIMARY` — normal/strongly established server deployment path
- `POSSIBLE` — technically possible but requires exact package/build/integration evidence
- `PRODUCT-SPECIFIC` — depends on vendor installer/product support matrix
- `ADVANCED` — not a normal first-choice server deployment path
- `UNVERIFIED` — no claim until versioned evidence exists

| Platform / target | Community OpenVPN server | Access Server | Script installers | Containers | PVNetwork research decision |
|---|---|---|---|---|---|
| Ubuntu Server | PRIMARY via packages/source | PRODUCT-SPECIFIC official Linux package path | Common target for Angristan/Nyr/PiVPN-type projects, exact versions must be checked | PRIMARY Linux container host candidate | High-priority certification target |
| Debian | PRIMARY via packages/source | PRODUCT-SPECIFIC; current official support matrix must be checked | Common installer target depending on revision | PRIMARY container host candidate | High-priority certification target |
| RHEL-family | PRIMARY via distro/vendor packages/source where available | PRODUCT-SPECIFIC official package path on supported releases | Installer support varies | PRIMARY container host candidate | Enterprise certification target |
| Fedora | POSSIBLE via packages/source | Do not infer Access Server support from Community | Installer support varies | Container-host feasible | Community-only unless product matrix proves otherwise |
| Arch-based | POSSIBLE via distro packages/source | No generic Access Server claim | Community scripts vary | Container-host feasible | Advanced/community target |
| Alpine | POSSIBLE package/container context; verify exact OpenVPN features | No generic Access Server claim | Not assumed | Common as container base in third-party projects, exact image must be pinned | Container/reference target |
| Linux ARM64 | POSSIBLE/commonly buildable; exact package/crypto/DCO support varies | PRODUCT-SPECIFIC | Raspberry-Pi-oriented installers may support specific distro/arch combinations | ARM image support must be pinned | Required for ARM server/device use cases |
| Raspberry Pi OS | Community packages feasible | Do not infer Access Server support | PiVPN/reference installers target this ecosystem | Container possible with TUN/capabilities | Useful lab/home target |
| Windows Server | Community OpenVPN can run on Windows, but Linux is normally simpler for routed server deployments | Access Server is not assumed available unless official docs say so | Linux shell installers do not apply | Windows containers not assumed | Interoperability/reference only unless product need exists |
| macOS as server | Community daemon/source can be used for development/special cases | Not a normal Access Server assumption | Linux installers do not apply | Docker Desktop is not equivalent to a production Linux host | Development/advanced only |
| FreeBSD / BSD | Community source/package may be available depending on OS | No Access Server claim | Installer projects vary | Container assumptions differ | Advanced/reference; pin actual package |
| Docker on Linux | Community daemon packaged inside image | Access Server may have its own container/product path only if official | N/A | PRIMARY third-party deployment pattern | Must pin image digest/source and TUN/capabilities |
| Podman | Similar Linux-container feasibility | Product-specific | N/A | POSSIBLE | Validate rootless/rootful network/TUN capability explicitly |
| Kubernetes | Not a daemon-install target by itself; deployed via container/workload | Product-specific | N/A | ADVANCED | Requires privileged/TUN/network model, secrets and service exposure audit |
| LXC/LXD/system container | POSSIBLE with TUN/device/network privileges | Product-specific | Script may work if distro/systemd assumptions hold | N/A | Treat host/container privilege separately |
| VM / cloud instance | PRIMARY Linux deployment pattern | PRIMARY when OS/product version is officially supported | Common | Containers optional | High-priority production topology |
| Bare-metal Linux | PRIMARY | PRIMARY where product OS supported | Common | Optional | High-priority production topology |
| Router/firewall appliance | Appliance-specific embedded OpenVPN | No generic Access Server claim | Do not run arbitrary installers | Rare/not relevant | Interoperability target only, record embedded daemon version |

## Community Server installation paths

### Distribution package

Preferred for many managed Linux deployments because:

- integrates with package manager;
- service files/permissions are distribution-owned;
- security updates can flow through package repository;
- removal/upgrade can be audited.

Risk: distro package can lag upstream or be built with different TLS/DCO/plugin features.

Record:

- package repository;
- exact version/build string;
- package maintainer;
- build features;
- service unit/config locations;
- TLS library/backend;
- Easy-RSA/PKI relationship;
- DCO package/module state.

### Official/upstream package repository

Use only when current official OpenVPN documentation supports the target distribution/release. Record repository signing key, package source, exact version and rollback path.

### Build from source

Use when an exact feature/security build is necessary, not by default.

Record:

- source tag/commit;
- compiler/toolchain;
- configure/CMake options;
- TLS backend;
- optional plugins/features;
- DCO/TUN dependencies;
- install prefix/files;
- service integration;
- SBOM/hash.

## Access Server installation path

Access Server is a commercial Linux server product with its own official supported-platform/package documentation. The supported OS list is a **live product constraint** and must be rechecked before each release/deployment.

PVNetwork should not extrapolate Access Server support from Community OpenVPN portability.

For every Access Server lab target record:

- Access Server exact version;
- official supported OS/release;
- package/repository URL identity and signature metadata;
- license/subscription mode;
- Admin Web UI bind/port/TLS configuration;
- Client Web UI bind/port/TLS configuration;
- initial administrator creation/credentials;
- backup/restore procedure;
- upgrade/rollback path.

## Container matrix details

For every image/project record:

- image name/registry;
- digest;
- source repo/commit;
- base image digest;
- OpenVPN package/source version;
- UID/GID;
- `/dev/net/tun` mapping;
- Linux capabilities;
- privileged mode requirement if any;
- host vs bridge networking;
- UDP/TCP published ports;
- config/PKI volumes;
- firewall/NAT changes inside vs host;
- healthcheck;
- secret injection;
- update/rollback.

## Firewall / forwarding prerequisites

A routed OpenVPN server typically requires some combination of:

- IP forwarding;
- route availability;
- firewall input for listener port;
- forwarding policy;
- NAT/masquerade if clients must reach networks/Internet without reciprocal routes;
- IPv6 routing/firewall when enabled.

These are topology decisions, not installer defaults that PVNetwork should blindly apply.

Future deployment automation must display a **before/after network-policy plan** before applying changes.

## DNS prerequisites

Server can push DNS-related options, but the authoritative DNS server itself may be:

- external resolver;
- local resolver;
- Active Directory/domain DNS;
- split-DNS design;
- no DNS push.

Do not install a resolver automatically unless selected topology requires it.

## PKI prerequisites

Community deployments may use:

- Easy-RSA or other CA tooling;
- externally managed PKI;
- per-client certificates;
- additional username/password/MFA plugins.

PVNetwork server automation should support external CA/enterprise PKI without forcing a local CA in every deployment.

## Installation test receipt format

For every actual lab installation create a receipt containing:

```text
Deployment ID:
Date:
Installer/project/source pin:
Host OS + kernel + arch:
OpenVPN server version/build:
TLS backend:
DCO state:
Installed packages:
Created users/groups:
Created files/directories:
Created services:
Opened ports:
Firewall changes:
Routing/sysctl changes:
DNS changes:
PKI location/permissions:
Admin interfaces:
Generated client profile:
Install result: PASS/FAIL
Client E2E result:
Uninstall/rollback result:
Residual files/rules/services:
```

## Current v2 gaps

- exact current Access Server OS/version matrix copied from current official docs into a versioned snapshot;
- pinned OS support tables for Angristan/Nyr/PiVPN;
- current Community package versions by distro;
- Docker/Podman/Kubernetes hands-on privilege tests;
- actual install/uninstall receipts.
