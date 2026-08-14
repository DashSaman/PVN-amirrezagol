# IKE / IPsec — Server Installation Matrix

Review date: 2026-08-14

Scope: entries 004–007. This matrix records evidenced installation/deployment paths, not successful PVNetwork runtime certification.

Status vocabulary:

- `OFFICIAL-PACKAGE/DOC`: upstream/vendor documents the path;
- `SOURCE-BUILD`: upstream source documents/builds for the target;
- `APPLIANCE`: IPsec is owned by a firewall/OS appliance;
- `NEEDS-LAB`: source/reference exists but PVNetwork has not produced an execution receipt;
- `NOT-NORMAL-SERVER-PATH`: technically possible components may exist, but this is not a normal PVNetwork server target.

## 1. Linux — Debian / Ubuntu

### strongSwan

State: `OFFICIAL-PACKAGE/DOC / NEEDS-LAB`.

Official strongSwan docs distinguish:

- legacy starter/metapackage path;
- modern `charon-systemd` + `strongswan-swanctl` path.

Important gate: ensure only one daemon/service model owns the IKE service and config.

### Libreswan

State: `OFFICIAL-PACKAGE/DOC + SOURCE-BUILD / NEEDS-LAB`.

Pinned v5.4 README documents Debian/Ubuntu/Mint dependency setup, DEB build flow and service use.

### Required server test receipt

- clean host image/version;
- package/repo source and signature;
- exact engine version;
- service enable/start;
- kernel XFRM capability;
- IKEv2 connection;
- ESP native/NAT-T;
- upgrade and rollback;
- uninstall and XFRM/firewall cleanup.

## 2. Linux — Fedora / RHEL family

### strongSwan

State: `DISTRIBUTION-PACKAGE/SOURCE CAPABLE / NEEDS-LAB`.

Exact package names/repositories vary by distro/version and must be pinned at execution time.

### Libreswan

State: `OFFICIAL-PACKAGE/DOC + SOURCE/RPM BUILD / NEEDS-LAB`.

Pinned v5.4 README documents Fedora/CentOS-Stream/RHEL-family dependency installation, `dnf builddep`, RPM build and systemd `ipsec.service` behavior.

Libreswan is a particularly important native-family server reference on RPM ecosystems.

## 3. Linux — Alpine

### strongSwan

State: `DISTRIBUTION-PACKAGE/SOURCE CAPABLE / NEEDS-LAB`.

### Libreswan

State: `OFFICIAL-PACKAGE/DOC + SOURCE-BUILD / NEEDS-LAB`.

Pinned README documents Alpine dependencies; v5.4 release notes list an Alpine test domain update.

Container suitability does **not** follow automatically from Alpine package availability; kernel/XFRM/network namespace privileges still apply.

## 4. Linux — Arch

### strongSwan

State: `OFFICIAL-PACKAGE/DOC / NEEDS-LAB`.

StrongSwan official installation documentation lists Arch packages.

### Libreswan

State: `OFFICIAL-PACKAGE/DOC / NEEDS-LAB`.

Pinned Libreswan README lists Arch among distributions providing prebuilt packages.

## 5. FreeBSD

### strongSwan

State: `SOURCE/PACKAGE PLATFORM / NEEDS-LAB`.

Backend behavior differs from Linux XFRM and must be validated against the selected FreeBSD IPsec/kernel interface.

### Libreswan

State: `OFFICIAL-PACKAGE/DOC + SOURCE-BUILD / NEEDS-LAB`.

Pinned README documents FreeBSD package/build dependencies. v5.4 release notes explicitly include FreeBSD kernel behavior fixes and current test-domain coverage.

## 6. NetBSD

### strongSwan

State: `PLATFORM CAPABLE / NEEDS-LAB`.

### Libreswan

State: `SOURCE/PACKAGE-WIP / NEEDS-LAB`.

Pinned README states NetBSD package files are in pkgsrc/wip and documents source dependencies. Treat this as weaker packaging maturity than ordinary stable distro package paths until verified on the selected NetBSD release.

## 7. OpenBSD

### strongSwan

State: `PLATFORM CAPABLE / NEEDS-LAB`.

### Libreswan

State: `SOURCE-BUILD / NEEDS-LAB`.

Pinned README says OpenBSD needs a source build and provides dependency commands. v5.4 release notes include OpenBSD kernel fixes/test-domain evidence.

## 8. OPNsense

State: `APPLIANCE / NEEDS-LAB`.

Use OPNsense-supported installation/update mechanisms. Current docs expose IPsec Connections based closely on the swanctl model plus legacy tunnel settings, mobile clients, credentials and status/SPD/SAD views.

Do not layer an unmanaged second strongSwan install over the appliance.

Required receipt:

- exact OPNsense release/build;
- config backup;
- IKEv2 site-to-site and road-warrior lab;
- upgrade/rollback through appliance mechanism;
- firewall/VTI/SA cleanup verification.

## 9. pfSense

State: `APPLIANCE / NEEDS-LAB`.

Use the supported appliance configuration/update mechanism. Current docs expose Phase 1, Phase 2, mobile clients, PSKs, advanced settings, policy/route-based modes and status.

Do not infer exact underlying daemon version from the web UI alone; capture package/system version in the lab receipt.

## 10. Docker / OCI

State: `EVIDENCE-GAP / NO GENERIC IMAGE APPROVED`.

A containerized IKE/IPsec server is not equivalent to an ordinary userspace web service. Common blockers/risks include:

- kernel XFRM belongs to a kernel/network namespace;
- NET_ADMIN/privileged capabilities;
- host networking/UDP 500/4500;
- sysctl forwarding;
- policy routing;
- access to kernel modules/interfaces;
- cleanup after container exit;
- secret/config volume permissions.

Before promotion, select an exact source Dockerfile/image digest and prove the network namespace/XFRM model.

## 11. Kubernetes

State: `ADVANCED / EVIDENCE-GAP`.

Do not claim generic Kubernetes support merely because an IKE daemon can run in a pod. Required design evidence includes:

- node vs pod XFRM ownership;
- hostNetwork requirement;
- privileged/capability model;
- node scheduling/HA/failover;
- CNI interaction;
- Service/NAT behavior for UDP 500/4500;
- secret distribution;
- SA/policy cleanup when a pod/node dies.

This likely belongs to a dedicated gateway/node appliance model rather than a stateless replicated Deployment.

## 12. Windows Server

State: `NATIVE-OS TARGET / NEEDS-LAB`.

Windows includes native IPsec/IKE policy/VPN capabilities. For a Windows-native server/gateway scenario, use documented Windows roles/policy mechanisms rather than installing a Unix IKE daemon by assumption.

Exact editions/features, routing role, IKEv2 remote-access support and management APIs require a separate Windows Server version matrix.

## 13. macOS / iOS / Android

State: `NOT-NORMAL-SERVER-PATH` for PVNetwork server scope.

These platforms are primarily client targets in this project. Do not treat client-side IKE/IPsec APIs as server implementations.

## 14. Execution matrix required before strict v2 completion

Representative minimum lab set:

| Target | Engine/control plane | Install | Start | IKEv2 | IKEv1 legacy | ESP | AH | Upgrade | Rollback | Uninstall cleanup |
|---|---|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| Debian/Ubuntu | strongSwan swanctl/VICI | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Fedora/RHEL-like | Libreswan | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| FreeBSD or selected BSD | selected engine | TODO | TODO | TODO | optional | TODO | optional | TODO | TODO | TODO |
| OPNsense | appliance UI | TODO | TODO | TODO | optional | TODO | optional | TODO | TODO | TODO |
| pfSense | appliance UI | TODO | TODO | TODO | optional | TODO | optional | TODO | TODO | TODO |
| selected OCI image | pinned image/source | TODO | TODO | TODO | optional | TODO | optional | TODO | TODO | TODO |

`TODO` is deliberate external execution work, not a successful result.
