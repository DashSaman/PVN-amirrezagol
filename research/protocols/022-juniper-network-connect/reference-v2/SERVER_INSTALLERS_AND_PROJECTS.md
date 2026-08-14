# Juniper Network Connect — Server Deployment / Installer Review

Reviewed: 2026-08-14 UTC

There is no selected current standalone Network Connect server installer/project. The protocol historically belonged to proprietary Juniper/Pulse SSL VPN appliances; current Ivanti ICS is the successor product family but its current 25.1.x NC wire compatibility is not asserted without direct evidence.

Therefore:

- historical appliance deployment is `PROPRIETARY / LEGACY`;
- generic Linux/Windows Network Connect server package is N/A;
- generic OCI/Kubernetes Network Connect gateway is N/A;
- current ICS deployment/lifecycle belongs to entry 021 and may only be reused for product lineage, not as proof of NC wire service.

OpenConnect is client-side only. Arbitrary reverse-engineered scripts, browser-cookie helpers, old `ncsvc` binaries or community images are not promoted as server projects.

Lifecycle decision: for a live legacy NC dependency, preserve the exact appliance/version configuration and establish a migration path to a maintained current VPN mode/client; do not deploy a new NC server by default.
