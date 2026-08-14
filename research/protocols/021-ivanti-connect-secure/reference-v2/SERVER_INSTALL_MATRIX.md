# Ivanti Connect Secure — Server Deployment Matrix

Reviewed: 2026-08-14 UTC

| Target | V2 treatment |
|---|---|
| ICS 25.1.2.1 build 15773 | CURRENT RELEASE/ACTIVITY baseline; proprietary; exact supported platform path must follow vendor release docs |
| ISA6500 | tested 25.1.2.0 → 25.1.2.1 upgrade path documented |
| Migration into 25.1.2.1 | tested from 25.1.0.1, 25.1.1.0, 22.8R2.3, 22.7R2.12 |
| ICS 25.1.1.1 / 22.8R2.4 / 22.7R2.14 | current ISAC 22.8R7 desktop compatibility table includes these maintained branches |
| Generic Linux/Windows package | N/A-PROPRIETARY |
| Generic OCI container | N/A; no canonical vendor ICS container selected |
| Kubernetes | N/A; no canonical ICS workload/Helm deployment selected |

Client/server qualification is independent from headend release activity. The published ISAC 22.8R7 table qualifies 25.x through 25.1.1.1, not the newer 25.1.2.1. Do not infer the missing pair.

References:
- https://help.ivanti.com/ps/help/en_US/ICS/25.1.x/25.1.2.1/rn/upgrade_and_migration.htm
- https://help.ivanti.com/ps/help/en_US/ISAC/22.X/spg-22.X/server-platform-compatibility.htm
