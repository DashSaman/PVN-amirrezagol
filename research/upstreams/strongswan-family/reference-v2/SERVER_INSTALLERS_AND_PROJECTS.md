# IKE / IPsec — Server Installers and Deployment Projects

Review date: 2026-08-14

Scope: entries 004–007. This file inventories **deployment/install ownership** separately from protocol implementations. Do not equate a convenience panel/image/script with the security properties of IKE/ESP/AH.

## 1. strongSwan official installation paths

Pinned engine baseline: strongSwan 6.0.7 (`5973ff8e41deef4e015e1138a2de688acedf6f75`).

Official strongSwan installation documentation supports two broad families:

### Distribution packages

The official docs explicitly document packaged installation on distributions including Arch and Debian/Ubuntu, while other distributions provide their own packages.

On Debian/Ubuntu, the docs make an important backend distinction:

- the `strongswan` metapackage may install the legacy starter/daemon/configuration path;
- for modern `swanctl`/VICI operation, use the `charon-systemd` + `strongswan-swanctl` package path and avoid running competing starter/systemd daemon units simultaneously.

PVNetwork/server automation must therefore verify **which daemon/config backend is actually installed**, not just whether a package named strongSwan exists.

### Source build

Official docs cover:

- building released source;
- building from Git;
- Linux kernel requirements;
- other-platform builds;
- monolithic builds;
- configuration-management references.

A production source build must pin:

- source commit/tag;
- configure flags;
- plugins built and loaded;
- crypto/provider dependencies;
- init/service model;
- install prefix/config paths;
- kernel backend;
- upgrade/uninstall ownership.

Do not run an unpinned build-from-master flow in production.

## 2. Libreswan official installation paths

Pinned reviewed release: Libreswan v5.4 (`5eb03b7772b312e705feab9ad5868678a3c007e6`).

The pinned README documents prebuilt package availability across a broad Unix/Linux set, including:

- Alpine;
- Arch;
- CentOS/RHEL-family distributions;
- Debian;
- Fedora;
- FreeBSD;
- Mint;
- Oracle Linux;
- Ubuntu;
- NetBSD via pkgsrc/wip;
- OpenBSD as a source-build path in the reviewed README.

It also documents source-build dependency sets for Debian-family, RPM-family, Alpine, FreeBSD, NetBSD and OpenBSD.

### Build/install ownership

Pinned README documents:

- `gmake` / install to `/usr/local` for generic source install;
- RPM packaging flow;
- DEB packaging flow;
- service integration with systemd/upstart/sysvinit/openrc detection;
- Linux systemd service named `ipsec` in the documented example;
- `ipsec start` / `ipsec stop` abstraction;
- configuration primarily under `/etc/ipsec.conf`, `/etc/ipsec.secrets`, includes under `/etc/ipsec.d/`;
- NSS-backed private-key/X.509 storage and initialization/import tools;
- upgrade caution and NSS directory migration history.

### Supply-chain rule

Prefer distro packages or signed/pinned upstream release artifacts with digest/signature verification. Do not mix a distro install in `/usr` with an unmanaged source install in `/usr/local`; the upstream README itself warns against installing Libreswan twice.

## 3. OPNsense appliance/control-plane deployment

Current reference pin:

- `opnsense/core@6f6d6fa05ec274a4b3589d33e6e4249a162993c2`
- root license: BSD-style permissive terms.

OPNsense is a full firewall/appliance product and management plane, not a lightweight IKE library. Its IPsec UI exposes current/legacy connection models, credentials, pools, SA/SPD status and firewall integration.

### Deployment implications

If used as a server/reference target:

- installation/update ownership belongs to the OPNsense appliance/package system;
- strongSwan-style configuration exposed by the UI must not be edited concurrently by an unrelated PVNetwork installer;
- firewall policy, interfaces, certificate authority and user/auth systems are part of the product state;
- backup/restore of appliance config may include sensitive IPsec metadata/secrets and needs explicit handling.

PVNetwork should treat OPNsense as an interoperability/control-plane target, not install its own strongSwan over the appliance unmanaged.

## 4. pfSense appliance/control-plane deployment

Current reference pin:

- `pfsense/pfsense@9363ac5b8651a1c7a333180425ce7719070f95f9`
- repository `LICENSE`: Apache 2.0.

pfSense is likewise a full firewall management product with IPsec Phase 1/Phase 2/mobile/settings/status surfaces.

### Deployment implications

- use the product's supported package/update/config mechanisms;
- do not install a competing standalone IPsec daemon without a deliberately isolated lab design;
- firewall/VTI/routing/certificate/user configuration are part of the deployment, not external incidental settings;
- backups and configuration exports require secret review/redaction policy.

## 5. Community Docker/container images

There are numerous community strongSwan/IPsec images. They are **not approved by default**.

Before any image is added to the PVNetwork reference/certification set, record:

1. canonical source repository;
2. exact source commit/tag;
3. Dockerfile/base image pin;
4. package/source-build origin of strongSwan/Libreswan;
5. image digest;
6. update cadence and maintainer activity;
7. root/capabilities/privileged mode requirements;
8. host networking and `/dev`/sysctl mounts;
9. XFRM/kernel namespace behavior;
10. firewall/NAT modifications;
11. secret/config mount permissions;
12. healthcheck/startup/rollback/uninstall behavior.

Do not recommend blind `docker run --privileged` or remote shell-script installers just because an image can create a tunnel.

## 6. Configuration-management projects

Official strongSwan docs reference configuration-management approaches such as Chef/Puppet. In real deployments, Ansible/system-management roles are common, but a role is not trusted merely because it is popular.

For each selected automation project later, capture:

- source/license/revision;
- supported distro versions;
- package source;
- generated config paths;
- service ownership;
- secret handling;
- idempotency;
- firewall/sysctl routing changes;
- rollback/uninstall semantics.

## 7. Cloud marketplace / managed VPN gateways

Cloud-provider VPN gateways are serious interoperability targets but typically do not expose their server source or package installation. They belong in a **managed-service certification matrix**, not the open-source installer table.

For such a target, evidence must instead include:

- official product/version/API documentation;
- supported IKE/IPsec proposals;
- routing/topology limits;
- HA/rekey/failover behavior;
- logging/diagnostics;
- pricing/service limits if relevant at deployment time;
- reproducible client/server interop receipts.

## 8. Security and privilege checklist for all server deployment projects

Any server installer/panel must be reviewed for:

- root privileges/capabilities;
- service user/group;
- writable configuration directories;
- private-key/PSK permissions;
- firewall/routing/sysctl changes;
- forwarding/NAT enablement;
- exposed UDP 500/4500 and management ports;
- package signing/digests;
- automatic update behavior;
- uninstall cleanup of SAs/policies/routes/firewall rules;
- log permissions and secret redaction;
- backup/restore of credentials;
- kernel module/backend prerequisites.

## 9. Current selection direction

### PVNetwork-managed Linux server

Prefer a reviewed distro package or reproducible pinned release build of **strongSwan** as the primary advanced reference, with Libreswan kept as a major alternative/interoperability implementation.

### Appliance target

Treat OPNsense/pfSense as independent managed products. Configure through their supported management surfaces/APIs in a lab; do not overwrite their daemon/package ownership.

### Container target

`EVIDENCE-GAP`: no generic community image is yet promoted. A container must pass source/image/capability/host-network security review first.

## 10. Remaining installer work

- immutable release/package pins per representative distro;
- selected container image source+digest and non-root/privilege review;
- Kubernetes/orchestration feasibility with XFRM/namespace constraints;
- OPNsense/pfSense version/release pin rather than moving master alone;
- representative install -> start -> upgrade -> rollback -> uninstall execution receipts;
- cleanup verification for policies/SAs/routes/firewall rules.
