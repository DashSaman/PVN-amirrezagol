# GRE — Server Installers and Deployment Projects

Reviewed: 2026-08-15

Bare GRE is a kernel/router networking capability, not a standalone server package. Therefore there is no protocol-defined official GRE daemon installer, container image, Helm chart, one-click web panel, or cloud appliance that is required to obtain GRE itself.

## Linux

- Capability is supplied by the Linux kernel GRE/IP tunnel stack.
- Configuration is normally performed by distribution-provided `iproute2` (`ip tunnel add ... mode gre ...`) or higher-level network managers/system configuration.
- Root/CAP_NET_ADMIN is required to create/change tunnel interfaces.
- Deployment changes routing/interface state and may require firewall rules permitting GRE (IP protocol 47); there is no TCP/UDP management port intrinsic to GRE.
- Upgrade/rollback follows kernel + iproute2/distribution lifecycle; tunnel configuration is removed with the interface/configuration owner.

## Network operating systems

Cisco IOS XE and Junos provide GRE as built-in proprietary platform functionality; upgrades/rollback follow their network OS lifecycle. They are not redistributable GRE installer projects.

## Supply-chain conclusion

No independent shell installer or container should be introduced merely to enable bare GRE. Prefer OS-native packages/platform APIs. This avoids unnecessary privileged third-party install scripts.

Evidence: RFC 2784/2890; pinned Linux/iproute2 sources in `SERVER_IMPLEMENTATIONS.md`; Cisco and Juniper official GRE configuration guides.
