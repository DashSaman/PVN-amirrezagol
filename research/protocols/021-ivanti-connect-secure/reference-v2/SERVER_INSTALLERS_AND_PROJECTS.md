# Ivanti Connect Secure — Server Deployment / Installer Review

Reviewed: 2026-08-14 UTC

ICS is vendor appliance software. Hardware/virtual images, updates and migration packages must come from Ivanti-authorized support/software channels and follow the exact platform/release matrix.

Current 25.1.2.1 tested lifecycle evidence:

- in-place upgrade to 25.1.2.1 is documented for ISA6500 from the embedded 25.1.2.0 baseline;
- configuration migration to 25.1.2.1 is documented from 25.1.0.1, 25.1.1.0, 22.8R2.3 and 22.7R2.12;
- generic Linux package, arbitrary VM image, OCI image or Helm/Kubernetes server deployment is **not** a canonical ICS installation model.

Official upgrade/migration evidence: https://help.ivanti.com/ps/help/en_US/ICS/25.1.x/25.1.2.1/rn/upgrade_and_migration.htm

No community project is promoted as an ICS replacement. OpenConnect packages remain client-side only.

Supply-chain rule: preserve vendor image provenance/signature/support-channel verification, configuration backup and exact release notes; separately verify OpenConnect source/package provenance when that client is selected.
