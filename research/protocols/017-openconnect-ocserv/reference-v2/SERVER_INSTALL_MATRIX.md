# OpenConnect / ocserv — Server Install Matrix

Review date: 2026-08-14 UTC

| Target | Deployment model | Reference state |
|---|---|---|
| Linux | ocserv source or distro package, system service, TUN/routing/firewall | PRIMARY / REFERENCE-COVERED |
| FreeBSD/Unix-like | source/build support evidenced by project history (1.5.0 includes FreeBSD build fix) | REFERENCE-COVERED; exact platform package certification later |
| OCI | self-built/pinned image only after source/base image/digest/privilege review | ADVANCED |
| Kubernetes | dedicated VPN gateway design with TUN/network privileges and persistent secrets/config | ADVANCED / TOPOLOGY-SPECIFIC |
| Windows server | no canonical native ocserv server selected | N/A |
| Cisco ASA/FTD | proprietary Cisco server, compatibility reference only | NOT-OCSERV |

Exact runtime support is package/platform specific; no device receipt is inferred.
