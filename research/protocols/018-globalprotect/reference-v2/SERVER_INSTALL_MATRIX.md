# Palo Alto GlobalProtect — Server Deployment Matrix

Review date: 2026-08-14 UTC

| Target | Headend model | V2 conclusion |
|---|---|---|
| Palo Alto NGFW / PAN-OS | GlobalProtect Portal + Gateway | AUTHORITATIVE / PROPRIETARY; PAN-OS 12.1 admin reference baseline |
| Palo Alto virtual firewall deployment | PAN-OS/VM-Series where the selected platform/license supports GlobalProtect | VENDOR-PLATFORM-SPECIFIC; not treated as a generic Linux VM package |
| Prisma Access | cloud-managed GlobalProtect service/headend | AUTHORITATIVE-CLOUD / PROPRIETARY; separate subscription/control plane |
| Generic Linux/Windows server | no public Palo Alto GlobalProtect server package | N/A-PROPRIETARY |
| Generic OCI container | no canonical vendor GlobalProtect headend container selected | N/A |
| Kubernetes | no canonical vendor GlobalProtect portal/gateway workload | N/A |
| ocserv/OpenConnect server | not a GlobalProtect server | N/A / DIFFERENT PROTOCOL FAMILY |

A vendor deployment target is not considered supported merely because PAN-OS can run virtually. Exact model, release, license and feature support must be verified at implementation/certification time.
