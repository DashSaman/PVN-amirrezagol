# SoftEther VPN Protocol — Server Installation Matrix

Review date: 2026-08-14

Entry: 013 SoftEther VPN Protocol.

## Status vocabulary

- `CANONICAL PATH`: SoftEther Server/Bridge source/product supports the role at reference level.
- `NEEDS-LAB`: no PVNetwork execution receipt yet.
- `VERIFY-RELEASE`: exact current release/platform packaging must be pinned before certification.

## 1. Windows Server / Windows desktop

State: `CANONICAL PATH / VERIFY-RELEASE / NEEDS-LAB`.

Install exact signed/reviewed SoftEther VPN Server release, configure native TCP listener(s), certificate, Virtual Hub/users and required network mode. Record service identity, Windows Firewall, management interface, update and uninstall.

## 2. Debian / Ubuntu Linux

State: `CANONICAL SOURCE/PACKAGE PATH / NEEDS-LAB`.

Record distro/kernel, exact SoftEther release/source package, compiler/dependencies, service/init, listener privileges, certificate/config paths, firewall, bridge/TAP/SecureNAT requirements and lifecycle.

## 3. Fedora / RHEL family

State: `SOURCE/PACKAGE PATH / VERIFY-RELEASE / NEEDS-LAB`.

Verify package/source support, SELinux, systemd, firewall and local-bridge permissions on the exact release.

## 4. Arch/rolling Linux

State: `SOURCE/COMMUNITY PACKAGE POSSIBLE / NEEDS-LAB`.

Pin both SoftEther and distribution package revision. Rolling dependency changes require rebuild/retest evidence.

## 5. FreeBSD / macOS / other Unix-like server builds

State: `VERIFY CURRENT RELEASE / NEEDS-LAB`.

The source tree has broad portability history, but exact current supported build/package and feature parity must be proven per platform. Do not promote based on source portability alone.

## 6. SoftEther VPN Bridge

State: `CANONICAL ROLE / NEEDS-LAB`.

Install/server-binary ownership may differ by package, but certification must include physical/TAP bridge privileges, VLAN/STP/loop policy, management and restart cleanup.

## 7. Cloud VM

State: `REFERENCE DEPLOYMENT / NEEDS-LAB`.

Works as the selected Windows/Linux server platform plus provider security group, public DNS/certificate, route/NAT/forwarding and image lifecycle evidence.

## 8. OCI / Docker

State: `ADVANCED / EXACT IMAGE DIGEST REQUIRED`.

Verify image source/base, SoftEther pin, persistent config/certificate, TCP listeners, management exposure, NET_ADMIN/TUN/bridge privilege, restart and upgrade/rollback.

## 9. Kubernetes

State: `ADVANCED / NO GENERIC CERTIFICATION`.

A basic TCP listener may fit a Service, but native sessions plus bridge/SecureNAT/network privileges and persistent state require an explicit gateway architecture. No stateless claim.

## 10. Strict execution table

| Target | Install | Native listener | Native client session | Virtual Hub | Bridge/SecureNAT | Update | Rollback | Uninstall |
|---|---:|---:|---:|---:|---:|---:|---:|---:|
| Windows selected release | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Ubuntu/Debian | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Fedora/RHEL | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| SoftEther Bridge | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| selected cloud VM | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| selected OCI image | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO values are external execution gates.
