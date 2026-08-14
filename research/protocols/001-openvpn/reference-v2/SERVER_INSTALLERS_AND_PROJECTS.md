# OpenVPN — Server Installers / Deployment Projects / Panels

Reference layer: `COMPLETE-REFERENCE-v2`

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

## Research rule

An installer/panel is not the OpenVPN protocol implementation. It is a privileged automation/control layer around OpenVPN Community Server or a product that includes OpenVPN functionality.

For every installer/project record separately:

- source repository/version;
- license;
- maintenance/activity;
- supported OS/distributions;
- root/admin requirement;
- packages/services installed;
- PKI/certificate behavior;
- firewall/NAT/routing/DNS changes;
- default listen transport/port decisions;
- generated server/client config;
- admin/web interfaces exposed;
- credential/default-secret behavior;
- update path;
- uninstall/rollback;
- supply-chain/download behavior;
- suitability as reference vs direct reuse.

Never recommend blind `curl | bash` / remote-script execution without reviewing the exact source revision first.

---

# 1. Angristan `openvpn-install`

Repository: `angristan/openvpn-install`

Type: shell-based automated OpenVPN Community Server installer/configurator.

Role in PVNetwork research:

**`POPULAR INSTALLER REFERENCE / DO NOT EMBED BLINDLY`**

The project is useful for understanding real operator expectations:

- OS/package detection;
- OpenVPN/Easy-RSA/PKI setup;
- certificate/client creation;
- server config generation;
- firewall/NAT/routing changes;
- DNS choices;
- client profile generation;
- add/revoke client workflows;
- uninstall flow.

### Security/supply-chain review requirements

Before PVNetwork ever recommends/automates this project:

- pin exact commit/tag and script hash;
- review every remote download/package repository added;
- inspect generated firewall rules;
- inspect sysctl forwarding changes;
- inspect permissions on PKI/private-key directories;
- inspect generated client configs for embedded private keys/secrets;
- inspect update/uninstall cleanup;
- ensure no moving-branch remote execution is used in production automation.

### Product-use decision

Use primarily as **operator workflow/reference evidence**. PVNetwork server-management tooling should implement typed, auditable deployment operations rather than simply run a moving third-party shell script as root.

---

# 2. Nyr `openvpn-install`

Repository: `Nyr/openvpn-install`

Type: shell-based OpenVPN installer/configurator.

Role:

**`POPULAR MINIMAL INSTALLER REFERENCE / SOURCE-PIN REQUIRED`**

This is another widely referenced installer pattern and should be compared against Angristan instead of assuming one represents the protocol's canonical deployment.

Research points:

- distro/version support;
- package source and OpenVPN version installed;
- firewall implementation;
- DNS resolver choices;
- client certificate lifecycle;
- server config defaults;
- IPv6 handling;
- removal/cleanup;
- current maintenance state.

### Product-use decision

Reference and interoperability fixture generator only until exact source/license/security review is complete. Do not treat a generated profile as authoritative PVNetwork storage.

---

# 3. PiVPN

Repository: `pivpn/pivpn`

Type: interactive VPN installer/management project historically focused on Raspberry Pi/Debian-family systems and supporting OpenVPN/WireGuard workflows according to project version.

Role:

**`OPERATOR UX + INSTALLER/MANAGEMENT REFERENCE`**

Valuable areas to study:

- interactive installer menus;
- platform/package detection;
- OpenVPN vs WireGuard selection/segmentation;
- unattended-install settings;
- user/client add/remove/list operations;
- QR/profile export where applicable;
- firewall/NAT/DNS setup;
- status/debug commands;
- update/reconfigure/uninstall workflows.

### PVNetwork lesson

PiVPN demonstrates that server deployment UX is a workflow product, not just a daemon install. A PVNetwork server module should expose equivalent high-level steps with explicit before/after state and rollback evidence.

### Reuse caution

Review exact current license, scripts and bundled helpers per pinned revision. Do not copy branding or assume all supported VPN components share one license.

---

# 4. `kylemanna/docker-openvpn`

Repository: `kylemanna/docker-openvpn`

Type: containerized OpenVPN server/PKI packaging project.

Role:

**`CONTAINER DEPLOYMENT REFERENCE / MAINTENANCE-STATUS CHECK REQUIRED`**

Key concepts to audit:

- container image/base image;
- OpenVPN package/version inside image;
- `/dev/net/tun` access;
- Linux capabilities/privileged flags;
- host networking / port publishing;
- iptables/nftables/NAT assumptions;
- persistent volumes for configuration/PKI;
- PKI initialization/client generation commands;
- image update/rebuild model;
- immutable image digest/provenance.

### Product security rule

Containerization does not remove kernel/network privilege. Record exactly which capabilities/devices/host settings are required and do not recommend `--privileged` when narrower capabilities are sufficient.

### Supply-chain rule

For reproducible PVNetwork server deployments, pin image digest or build from pinned source/base/package versions. Never deploy `latest` blindly.

---

# 5. OpenVPN Access Server installer/packages

Official product documentation: `openvpn.net/as-docs/`

Type: official commercial OpenVPN server product/package.

Role:

**`OFFICIAL COMMERCIAL SERVER PRODUCT / PRIMARY ENTERPRISE UI+PROVISIONING REFERENCE`**

Deployment research must track official supported platforms and current package/repository/install instructions rather than community scripts.

Important product components:

- Access Server service(s);
- Admin Web UI;
- Client Web UI;
- user/profile provisioning;
- authentication integrations;
- certificates/PKI/product configuration;
- licensing/subscription state;
- backup/restore/upgrade;
- network/listener/routing settings.

### Security rule

Admin Web UI exposure, initial administrator credentials/setup, TLS certificate for admin/client portals, firewall reachability and product updates are part of the deployment security surface.

Do not copy Access Server proprietary assets/code into PVNetwork.

---

# 6. Pritunl

Repository: `pritunl/pritunl`

Type: third-party VPN server/control-plane product with web administration and OpenVPN-related deployment capabilities.

Role:

**`SERVER CONTROL-PLANE / ADMIN UX REFERENCE`**

Study separately from the Pritunl desktop client.

Important areas:

- organizations/users;
- servers/routes;
- profile/user configuration distribution;
- authentication/SSO/MFA integrations by version;
- web administration;
- service/database dependencies;
- certificate/PKI state;
- logging/auditing;
- upgrades/backups;
- multi-server operational model.

### Reuse/license rule

Pin exact server source/license/current product edition before any code reuse. Do not infer the server license from `pritunl-client-electron`, OpenVPN or older Pritunl versions.

### Product decision

Reference for admin workflow and interoperability. PVNetwork's own server management should use its own branding/data model and must not depend on Pritunl unless explicit integration is a product goal.

---

# 7. Distribution package managers

For normal Community Server deployment, prefer official distribution packages or OpenVPN's documented repositories when appropriate rather than third-party scripts.

For each supported distribution later record:

- repository/package name;
- exact package version;
- service unit names;
- config directories;
- system user/group;
- capabilities/root requirements;
- firewall/routing prerequisites;
- Easy-RSA/PKI package relationship;
- upgrade/rollback/removal behavior.

Distribution package versions may lag upstream; certification must record the actual package, not just upstream latest.

---

# 8. Configuration-management / infrastructure automation

Ansible roles, Terraform modules, cloud-init templates, Helm/Kubernetes manifests and cloud marketplace images can automate OpenVPN deployment but must be treated as separate supply-chain artifacts.

PVNetwork v2 should only list specific projects after checking:

- current maintenance;
- exact source/license;
- secrets handling;
- idempotency;
- firewall/route changes;
- rollback/destroy behavior;
- image/module dependencies;
- privilege boundaries.

Do not fill the repository with unverified installer links merely for quantity.

---

# Installer acceptance checklist

An installer/project cannot be marked `PVNETWORK-RECOMMENDED` until:

- [ ] immutable source revision recorded;
- [ ] license reviewed;
- [ ] supported OS matrix verified;
- [ ] package sources reviewed;
- [ ] installed services/files recorded;
- [ ] firewall/NAT/routes/sysctl before/after documented;
- [ ] DNS changes documented;
- [ ] PKI/private-key permissions checked;
- [ ] generated client config inspected;
- [ ] admin interface exposure/default credentials reviewed;
- [ ] update path tested;
- [ ] uninstall/rollback tested;
- [ ] supply-chain downloads pinned/verified where practical;
- [ ] security advisories/current maintenance reviewed;
- [ ] actual OpenVPN server version recorded;
- [ ] end-to-end PVNetwork client interoperability tested.

Until then the status is `REFERENCE` or `RESEARCH`, not recommendation.

---

# Current v2 decision

For a future PVNetwork-managed OpenVPN server deployment, the safest architecture direction is to build a **typed deployment engine** that can use native packages/container images/configuration management with explicit source/version choices, while using community installers as behavior/reference sources.

Do not make “run this internet shell script as root” the core PVNetwork server architecture.

## Remaining gaps

- exact current immutable pins/license summaries for all installer projects;
- detailed source-derived before/after firewall/package/file maps;
- supported-OS matrix per current revision;
- Access Server exact current supported platform/version matrix;
- Pritunl exact server edition/source/license/dependencies;
- selected Ansible/cloud/container alternatives;
- hands-on install/uninstall verification later in a lab.
