# PVNetwork Research Campaign Status — 2026-08-14 — OpenVPN COMPLETE-REFERENCE-v2 Structure

Repository phase: exhaustive technical reference research.

OpenVPN entry 001 v2 state:

**`V2-STRUCTURE-HANDOFF-READY / DEEP REFERENCE IN-RESEARCH / NOT IMPLEMENTED`**

This is not a claim that every lab receipt/version/menu screenshot is complete.

## Required v2 files now present

Under `research/protocols/001-openvpn/reference-v2/`:

1. `SERVER_IMPLEMENTATIONS.md`
2. `SERVER_INSTALLERS_AND_PROJECTS.md`
3. `SERVER_INSTALL_MATRIX.md`
4. `SERVER_UI_AND_MENUS.md`
5. `CLIENT_INSTALL_MATRIX.md`
6. `CLIENT_UI_AND_MENUS.md`
7. `CRYPTOGRAPHY.md`
8. `DATA_PATH_AND_WIRE_FLOW.md`
9. `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
10. `DEPLOYMENT_TOPOLOGIES.md`
11. `REFERENCE_INDEX.md`

## Main OpenVPN v2 conclusions

### Server implementation taxonomy

- OpenVPN Community Server is the primary open-source protocol/server reference.
- OpenVPN Access Server is an official commercial server/product/admin/provisioning target, not the same source/licensing surface as Community Server.
- Pritunl and comparable projects are third-party control planes/wrappers/interoperability references.
- container images/installers are packaging/deployment layers, not new wire implementations.

### Installer research

Projects recorded include Angristan openvpn-install, Nyr openvpn-install, PiVPN, docker-openvpn, Access Server packages and Pritunl.

PVNetwork rule: never make blind moving-branch `curl | bash` execution the core server architecture. Future deployment automation must pin source/package/image, show network changes, secure PKI, verify uninstall/rollback and record supply-chain evidence.

### Server installation matrix

Reference now distinguishes Community vs Access Server vs scripts vs containers across Linux distributions, ARM, cloud VMs, containers, system containers and advanced non-Linux targets. Exact version/package receipts remain required.

### Server UI/admin surfaces

Separate inventories exist for:

- Community config/service/management domains;
- Access Server Admin Web UI and Client Web UI;
- Pritunl web administration;
- PiVPN/install-script operator menus;
- future PVNetwork server UI.

Exact product-version menu labels/screenshots remain a residual version-pinned task.

### Client installation/UI

Reference covers:

- OpenVPN Connect;
- OpenVPN GUI Windows;
- ics-openvpn Android;
- Tunnelblick macOS;
- Linux Community/NetworkManager;
- Pritunl Client reference.

Platform install receipts and exact current Store/package versions remain required.

### Cryptography

Reference separates:

- TLS control channel;
- certificate/identity validation;
- tls-auth / tls-crypt-style control protection;
- symmetric data channel and modern AEAD negotiation;
- legacy cipher policy;
- rekeying;
- username/password/MFA/PKI;
- crypto/TLS backend;
- DCO data path;
- compression legacy/security risk;
- long-lived vs transient secrets.

PVNetwork must report effective negotiated cryptography rather than hardcode “OpenVPN uses cipher X”.

### Data path

Reference documents:

- traditional TUN/userspace encryption path;
- DCO kernel/driver offload path;
- UDP vs TCP transport;
- full/split tunnel;
- DNS;
- routed server/site-to-site/TAP;
- reconnect/rekey/kill-switch failure behavior.

### Ports/handshake

- OpenVPN port is configurable; 1194 is a common historical/default association, not a mandatory port.
- UDP/TCP are underlay transports.
- handshake stages are separated into endpoint resolution, transport, optional static control protection, TLS control channel, user auth, option negotiation, data-channel establishment and platform tunnel setup.

### Deployment topologies

Reference includes full/split remote access, routed site-to-site, hub-and-spoke, HA/failover, multi-region, Access Server, container/cloud gateway, private-only, per-user restricted, TAP bridge, NAT, external IdP/PKI, multi-hop and per-app concepts.

## OpenVPN v2 residual gaps — mandatory, not hidden

1. exact current stable Community Server release/tag/commit;
2. exact current OpenVPN3 production-research pin;
3. full exact-build SBOM/advisory tables;
4. Access Server exact current version/OS support/menu screenshot snapshot;
5. OpenVPN Connect exact current version/menu screenshot snapshot per platform;
6. installer exact immutable pins/licenses/current OS matrices;
7. source-derived installer before/after files/firewall/sysctl/DNS maps;
8. exact cipher/TLS/DCO support matrix for selected releases;
9. packet/opcode/state-machine source references and sanitized captures;
10. actual install/uninstall/rollback receipts;
11. real-device/server interoperability/performance/Store evidence.

These residuals require exact version/lab evidence and will be revisited in refinement/certification. They do not justify blocking the breadth-first 93-entry v2 campaign.

## Next exact action

Activate **WireGuard / AmneziaWG COMPLETE-REFERENCE-v2** immediately.

Create the same v2 reference structure for entries 002–003, including:

- server implementations;
- server installers/projects/panels;
- server install matrices;
- server/admin UI/menu evidence where applicable;
- client install matrices;
- client menus;
- cryptography;
- packet/data path;
- ports/handshake;
- topologies;
- exact reference index;
- explicit AWG generation/version differences.

Do not wait for owner prompting.