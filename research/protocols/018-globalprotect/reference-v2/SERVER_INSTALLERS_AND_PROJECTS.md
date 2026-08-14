# Palo Alto GlobalProtect — Server Deployment / Installer Review

Review date: 2026-08-14 UTC

## Proprietary vendor deployment

GlobalProtect portal/gateway is deployed on supported Palo Alto Networks firewall/PAN-OS infrastructure or Prisma Access. Current official setup documentation requires the operator to configure the relevant interfaces/zones, certificates, portal/gateway objects, authentication and tunnel/routing policy.

There is no legitimate open-source `GlobalProtect server` installer to substitute for PAN-OS/Prisma Access, and this dossier does not invent a source build, Docker image, Helm chart or Kubernetes workload for the proprietary headend.

Server lifecycle therefore follows the selected PAN-OS/Prisma Access support/release/licensing process. Configuration export/backup, supported upgrade path, rollback/recovery and certificate/secret continuity are vendor-platform responsibilities.

## Public compatible client project

OpenConnect is client-side compatibility evidence only. It must not be misclassified as a GlobalProtect portal/gateway deployment project.

## Supply-chain boundary

- vendor headend software/images/updates: obtain through authorized Palo Alto distribution/support channels and verify according to vendor platform procedures;
- no third-party appliance image or community `GlobalProtect server` package is selected;
- public OpenConnect builds/packages require their own package/source/signature/dependency review and remain separate from the proprietary server trust chain.
