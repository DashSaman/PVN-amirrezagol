# IPIP — Server Installers and Deployment Projects

Reviewed: 2026-08-15

IPIP is an OS/router tunneling capability. There is no canonical standalone IPIP server package, container, Helm chart, one-click installer or web panel.

## Linux

Use the distribution kernel plus iproute2. Creating/changing the tunnel requires privileged network administration; deployment changes interface/routing state and firewalls must permit outer IPv4 protocol 4 where filtering applies. Configuration ownership, persistence, upgrade and rollback belong to the OS/network manager/system configuration. Tunnel deletion removes the interface; package lifecycle follows kernel/iproute2 distribution updates.

## Supply-chain conclusion

Prefer native kernel/package paths and auditable configuration. Do not add a privileged third-party `curl | sh` installer merely to obtain IPIP functionality.

Evidence: RFC 2003; pinned Linux `ipip.c`; pinned iproute2 `ip-tunnel.8`.
