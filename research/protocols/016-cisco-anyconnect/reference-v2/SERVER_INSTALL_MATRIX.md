# Cisco AnyConnect — Server Install / Deployment Matrix

Review date: 2026-08-14 UTC

| Target | Headend/project | V2 treatment |
|---|---|---|
| Cisco Secure Firewall ASA | proprietary ASA software/appliance/virtual platform | AUTHORITATIVE-CISCO-HEADEND; source/build N/A-PROPRIETARY |
| Cisco Secure Firewall Threat Defense | proprietary FTD platform | AUTHORITATIVE-CISCO-HEADEND; source/build N/A-PROPRIETARY |
| Linux/Unix controlled compatible server | ocserv 1.5.0 | PUBLIC-COMPATIBLE-REFERENCE; exact tag/commit/license pinned |
| Generic OCI | no Cisco server image selected; ocserv containerization is deployment-specific | N/A-CISCO / ADVANCED-OCSERV |
| Kubernetes | no canonical Cisco AnyConnect server workload model | N/A-CISCO; any ocserv gateway design requires separate privilege/state review |

Cisco headend version and licensing are vendor-platform concerns. ocserv is useful for controlled compatibility/testing and open deployment, but must never be labeled as Cisco ASA/FTD.
