# Lifecycle, security and supply chain

Vendor gateway/client artifacts are proprietary. Downloads and appliance images must come from Ivanti-authorized support/software channels; public source provenance cannot be fabricated. OpenConnect is a separate LGPL-2.1 implementation with its own canonical GitLab source/release chain.

ICS 22.8R2.3 build 18655 includes an OpenSSL security patch and is the reviewed 2026 point-release anchor. Current release notes and Ivanti security advisories must be checked before deployment.

Upgrade is release-matrix constrained. 22.8R2 introduced SecureBoot; migration to that line can prevent rollback to non-SecureBoot 22.7x. Later 22.8R2.x notes define specific hardware/VM upgrade paths. Configuration migration and appliance upgrade are separate operations. External-interface admin access is removed in newer releases.

ISAC desktop/mobile have independent version/update cadences. Mobile distribution is Store-based; desktop packages are vendor-distributed. Uninstall/rollback behavior must follow the platform/client release documentation rather than assuming a generic package-manager rollback.

Sources: https://help.ivanti.com/ps/help/en_US/ICS/22.x/22.8R2.2/rn/whatsnew.htm ; https://help.ivanti.com/ps/help/en_US/ICS/22.x/22.8R2.2/rn/upgrade_and_migration.htm ; https://help.ivanti.com/ps/help/en_US/ISAC/22.X/rn-22.X/overview.htm