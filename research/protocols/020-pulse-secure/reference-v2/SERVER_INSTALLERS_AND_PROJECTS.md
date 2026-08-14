# Server installers and deployment projects

ICS is a proprietary appliance product. Supported deployment is through Ivanti hardware/virtual-appliance images and documented cloud/virtual workflows, not an upstream Linux package or community `curl | sh` installer.

Reviewed 22.8R2 baseline: ISA6000/ISA8000 hardware and VMware virtual appliance are vendor-qualified; virtual/cloud support varies by point release. Licensing/downloads use Ivanti support/software channels. Generic Docker/Podman/Kubernetes/Helm installers are N/A unless Ivanti explicitly publishes support for the reviewed release.

Supply-chain rule: obtain images/packages only from Ivanti-authorized portals, verify vendor signing/integrity guidance, preserve backups/config exports, and follow the exact release upgrade matrix. Never infer root/package side effects from a generic Linux server model.

Sources: https://help.ivanti.com/ps/help/en_US/ICS/22.x/22.8R2/rn/support_and_compatibility.htm ; https://help.ivanti.com/ps/help/en_US/ICS/22.x/22.8R2.2/rn/upgrade_and_migration.htm