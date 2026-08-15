# VXLAN — Server Installers and Deployment Projects

Reviewed: 2026-08-15

No canonical standalone VXLAN server installer is required. Linux provides VXLAN in the kernel and uses iproute2/bridge tooling; Open vSwitch is a separate serious implementation/project for virtual-switch deployments.

Deployment requires privileged network/interface/bridge/FDB administration. Distribution kernel/iproute2/OVS packages should be preferred over unreviewed one-click scripts. Upgrade/uninstall/rollback follow the selected kernel/network-stack or OVS lifecycle plus deletion/reversion of VXLAN devices, bridge/FDB/routes and underlay configuration.

There is no protocol-defined Docker image, Helm chart, account server or web panel that is required to obtain bare VXLAN.