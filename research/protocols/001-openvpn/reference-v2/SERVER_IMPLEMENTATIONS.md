# OpenVPN — Server Implementations / Forks / Products

Reference layer: `COMPLETE-REFERENCE-v2`

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

## Scope

This file distinguishes the **OpenVPN wire/protocol server implementation** from installers, management panels and commercial products.

A project that installs/configures OpenVPN is not automatically a separate OpenVPN protocol implementation.

---

# 1. OpenVPN Community Server — primary open-source implementation

Repository:

`OpenVPN/openvpn`

Role:

- canonical/community OpenVPN 2.x client/server implementation;
- shared server/client executable codebase;
- TLS control channel;
- encrypted/authenticated data channel;
- tun/tap virtual interface integration;
- UDP/TCP transport modes;
- certificates, username/password plugins/scripts and multiple authentication extensions according to build/config;
- routing/topology/push-option behavior;
- platform-specific Windows/Unix service/network integration;
- DCO support paths on supported platforms/releases.

### PVNetwork reference role

This is the primary protocol/server source for:

- OpenVPN server behavior;
- configuration directives;
- control/data channel behavior;
- interoperability tests against OpenVPN3 clients;
- compatibility fixtures;
- legacy-vs-modern directive decisions.

PVNetwork must pin an exact Community release/tag for every server certification lab. A moving `master` branch is not a reproducible server reference.

### Important distinction

`OpenVPN/openvpn` (Community 2.x client/server) and `OpenVPN/openvpn3` (client library/core) are different codebases and version families.

Do not report one generic “OpenVPN version”.

Record:

- server Community version;
- client core/application version;
- TLS backend;
- DCO/userspace data path;
- OS/package version.

---

# 2. OpenVPN Access Server — commercial server product

Official documentation:

`https://openvpn.net/as-docs/`

Role:

- commercial OpenVPN server product;
- administrative web interface;
- user/client web portal;
- integrated user/authentication/profile management;
- OpenVPN-compatible client profiles;
- additional enterprise/product functionality beyond a plain Community Server configuration.

### Source classification

Do not treat Access Server as equivalent to the open-source `OpenVPN/openvpn` repository or assume its full product source is available under the Community license.

Use Access Server as:

- official server/product interoperability target;
- admin/UI/menu reference;
- profile/provisioning/authentication reference;
- enterprise deployment reference.

Do not copy proprietary Access Server web assets/code into PVNetwork.

---

# 3. Pritunl — third-party OpenVPN-oriented server/control platform

Repository:

`pritunl/pritunl`

Role:

- third-party server/control-plane product;
- web administration;
- user/organization/server/profile management;
- OpenVPN-related deployments plus broader product capabilities depending on version.

### PVNetwork reference role

Pritunl is valuable for studying:

- multi-user administrative UX;
- profile lifecycle;
- organization/user/server separation;
- server fleet/management concepts;
- operational failure/update lessons.

### Reuse rule

Pritunl application/server code, client code and any separately licensed components must be reviewed independently. Do not infer the license of `pritunl-client` or OpenVPN itself applies to the Pritunl server code.

No direct code reuse decision is made by this file.

---

# 4. Router / firewall distributions embedding OpenVPN

Major network platforms can expose OpenVPN server/client capabilities through their own configuration layers, for example open-source router/firewall distributions and commercial appliances.

These are **integration/server targets**, not necessarily independent protocol implementations.

PVNetwork later interoperability labs should include representative platforms only when they represent real customer demand.

Per-platform evidence must record:

- OpenVPN daemon/build/version actually embedded;
- platform-generated configuration;
- TLS/data-cipher policy;
- pushed routes/DNS;
- authentication plugins;
- management UI abstractions;
- update cadence.

Never assume an appliance labeled “OpenVPN” behaves exactly like the latest upstream Community Server.

---

# 5. Containers/images wrapping Community Server

Examples researched separately in `SERVER_INSTALLERS_AND_PROJECTS.md` include Docker-based OpenVPN server projects.

These are typically packaging/configuration layers around the Community implementation rather than new wire implementations.

For every container reference record:

- base image;
- OpenVPN package/source version;
- Linux capabilities/privileged requirements;
- `/dev/net/tun` access;
- host networking/iptables/nftables changes;
- volume/config/PKI persistence;
- update model;
- image signing/provenance;
- archived/maintenance status.

---

# 6. Managed VPN services/products

Many commercial VPN providers use OpenVPN as one protocol but their server-side orchestration, authentication, routing and control planes are proprietary.

Do not list every VPN provider as a separate OpenVPN implementation.

PVNetwork protocol certification should be against:

- protocol/server implementation/version;
- authentication/provisioning interface;
- profile semantics;
- server feature subset.

---

# Server compatibility classes for PVNetwork

## Class A — Community reference

OpenVPN Community Server with a pinned release/config.

Required for baseline protocol certification.

## Class B — Official OpenVPN commercial product

OpenVPN Access Server pinned product version.

Required for Access Server-specific profile/provisioning/auth behavior.

## Class C — Third-party control plane wrapping OpenVPN

Examples: Pritunl/PiVPN-generated installations and comparable products.

Certify only when relevant.

## Class D — appliance/vendor generated OpenVPN

Router/firewall/vendor platform. Record actual embedded OpenVPN version and generated profile/config behavior.

---

# Minimum server-version test record

For every certified server target store:

- product/project name;
- exact version/tag/commit/package;
- OS/distribution/container image digest;
- OpenVPN daemon version;
- TLS backend/version;
- DCO capability/data path;
- listening transport/port;
- server mode/topology;
- authentication methods;
- certificate/PKI model;
- data-cipher policy;
- compression policy;
- IPv4/IPv6;
- pushed DNS/routes;
- client profile/export format;
- relevant plugins/scripts;
- test date;
- PVNetwork client build/core version;
- PASS/known limitations.

---

# Security rule

Do not label a server secure merely because it runs OpenVPN.

Security depends on exact:

- OpenVPN version;
- TLS backend/version;
- TLS certificate policy;
- data ciphers;
- authentication;
- compression settings;
- script/plugin surface;
- privilege model;
- patch state;
- OS/kernel/DCO path;
- exposed management interfaces.

---

# Remaining evidence gaps

- materialize exact current Community Server release/tag/source pin in `REFERENCE_INDEX.md`;
- pin current Access Server release/documentation version;
- pin exact Pritunl source/version/license for the selected reference;
- select representative router/firewall implementations only after product demand is known;
- build actual server interoperability lab later during implementation/certification.
