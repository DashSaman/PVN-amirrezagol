# PVNetwork — Full Protocol Reference Expansion Contract

Owner requirement recorded: 2026-08-14

This contract defines the **second exhaustive research layer** for every numbered protocol/technology in `docs/PROTOCOL_MATRIX.md`.

## Priority rule

The existing research campaign remains first priority. Finish the previously defined source/client/core/license/architecture/issues/platform research before allowing this expansion to derail the active backlog.

After the prior research gates are satisfied for an entry/family, expand it using this contract. Where evidence is already available while finishing the prior work, it may be captured early, but do not skip unfinished prior gates.

The long-term goal is for the PVNetwork repository to become a complete engineering reference for each protocol from both **client and server viewpoints**.

## Mandatory per-protocol reference files

Each applicable numbered protocol dossier should eventually contain separate files with the following responsibilities. If a topic is not applicable, keep the file or section with an evidence-backed `NOT-APPLICABLE` explanation rather than silently omitting it.

### `SERVER_IMPLEMENTATIONS.md`
Inventory all serious server-side implementations and ecosystems:

- canonical/official server implementation;
- major open-source alternatives;
- vendor implementations where documentation exists;
- maintained forks;
- control planes/panels that deploy or manage the protocol;
- popular community server projects;
- container images/Helm/Ansible/Terraform or orchestration projects where relevant;
- installer-script projects maintained by different authors/communities;
- relationship between each project and the protocol itself;
- source repository, release/tag, pinned revision, activity, license and maintenance status;
- whether the project is a reusable server candidate, deployment reference, management/UI reference, or reference-only.

Do not treat popularity as security approval.

### `SERVER_INSTALLERS_AND_PROJECTS.md`
Research how the ecosystem actually installs/deploys the server:

- official packages/installers;
- upstream build-from-source path;
- distribution packages;
- Docker/Podman/container images;
- Compose/Kubernetes/Helm where applicable;
- one-click/community installers;
- web panels/control panels;
- automation scripts;
- cloud images/templates;
- third-party deployment projects.

For every installer/deployment project record:

- source URL and pinned revision;
- supported OS/distribution versions;
- privilege/root requirements;
- dependencies/packages/services installed;
- firewall/routing/DNS changes;
- files/directories/services created;
- configuration ownership;
- upgrade/uninstall/rollback model;
- security/supply-chain risks;
- whether remote scripts are pinned/verified;
- default credentials/secrets behavior;
- exposed management ports/interfaces;
- license and redistribution implications;
- current maintenance state.

Do not recommend blind `curl | sh` execution. Installer scripts are research subjects and must be source-reviewed before trust.

### `SERVER_INSTALL_MATRIX.md`
Build an evidence-backed server OS/deployment matrix.

Rows should cover every meaningful server environment for that protocol, for example:

- Ubuntu LTS releases;
- Debian stable;
- RHEL/Rocky/Alma/Fedora where supported;
- Arch where relevant;
- Alpine where relevant;
- openSUSE where relevant;
- FreeBSD/OpenBSD where relevant;
- Windows Server where relevant;
- macOS server use only if technically meaningful;
- Docker/Podman;
- Kubernetes;
- common CPU architectures such as x86_64 and ARM64.

For each row record:

- supported / unsupported / experimental / unknown;
- official vs community path;
- package/source/container installation method;
- required kernel/modules/capabilities;
- service manager;
- firewall/network prerequisites;
- upgrade path;
- known limitations;
- exact evidence source and review date.

### `SERVER_UI_AND_MENUS.md`
Create a detailed server-management UI/menu map for every serious server panel/control plane.

Capture separately for each project:

- dashboard/home;
- server/status overview;
- users/accounts/peers;
- profiles/config generation;
- listeners/inbounds/interfaces;
- routing/firewall/NAT;
- DNS;
- certificates/keys/PKI;
- authentication/MFA/SSO/LDAP/RADIUS where applicable;
- traffic/quotas/limits;
- logs/audit/diagnostics;
- backups/import/export;
- updates/version management;
- system/network settings;
- API/tokens/webhooks;
- admin/users/roles/permissions;
- security settings;
- notifications;
- clustering/high availability;
- integrations;
- about/license;
- every dialog/context menu/wizard discovered;
- empty/loading/error states;
- responsive/mobile behavior if the panel supports it.

Reference source files, routes/components, screenshots or official docs for every menu where possible. Keep each server panel's menu map distinct instead of merging unrelated UIs.

### `CLIENT_INSTALL_MATRIX.md`
For every meaningful client implementation and each target OS, record installation/packaging paths:

- Windows x64/ARM64 where relevant;
- Android phone/tablet;
- Android TV / Google TV;
- iOS/iPadOS;
- macOS Intel/Apple Silicon;
- Linux distributions/package formats;
- Flatpak/Snap/AppImage where relevant;
- CLI/package-manager variants;
- Store vs direct-download packages.

Record:

- official vs community package;
- minimum OS/version;
- architecture support;
- required permissions/drivers/extensions/services;
- signing/notarization/store source;
- install/update/uninstall behavior;
- first-run permission flow;
- known packaging issues;
- source/release evidence.

### `CLIENT_UI_AND_MENUS.md`
Create a **screen-by-screen and menu-by-menu client reference** for every serious client selected for study.

For each client, inventory:

- onboarding;
- home/dashboard;
- connect/disconnect controls;
- server/profile/subscription lists;
- add/import/QR/clipboard/file flows;
- protocol-specific profile editor fields;
- account/login/subscription flows;
- routing/proxy modes;
- DNS;
- split tunnel/per-app controls;
- kill switch/always-on where applicable;
- network/interface options;
- advanced/experimental options;
- logs/diagnostics/export;
- traffic/statistics;
- updates;
- backup/restore;
- notifications;
- tray/menu bar/quick settings/widgets;
- permissions;
- language/theme/accessibility;
- about/license;
- every context menu/dialog/wizard;
- empty/loading/offline/error/reconnect states;
- mobile/tablet/TV/desktop differences;
- keyboard/D-pad/accessibility behavior;
- RTL behavior and mixed LTR technical tokens.

Create separate source-backed subsections for each client; do not flatten all clients into one generic menu list.

### `CRYPTOGRAPHY.md`
Document the protocol's cryptographic design at an engineering/reference level.

Record, where applicable:

- protocol versions and cipher-suite negotiation model;
- confidentiality algorithms;
- integrity/authentication algorithms;
- AEAD usage;
- key exchange/agreement;
- signatures/certificates/PSK/static keys;
- KDF/HKDF/PRF usage;
- nonce/IV/counter construction at a high level;
- forward secrecy properties;
- rekey/key-rotation behavior;
- replay protection;
- peer/server authentication model;
- trust anchors/PKI/pinning options;
- legacy/deprecated algorithms and why they should be disabled;
- crypto backend/library used by major implementations;
- hardware-backed key possibilities;
- known cryptographic limitations/advisories;
- exact RFC/spec/source references.

Do not invent cryptography and do not substitute a client application's defaults for the protocol specification.

### `DATA_PATH_AND_WIRE_FLOW.md`
Describe how data moves through the protocol technically and visually.

Include evidence-backed diagrams in text/Mermaid where useful and document:

- application packet entry;
- TUN/TAP/socket/proxy entry model;
- control channel vs data channel;
- handshake/authentication phases;
- session establishment;
- encapsulation layers;
- outer transports such as TCP/UDP/TLS/DTLS/QUIC/ESP where applicable;
- packet framing/record boundaries at a safe architectural level;
- encryption/authentication stage;
- multiplexing/streams/channels where applicable;
- compression only where protocol/implementation actually uses it;
- keepalive/DPD/health messages;
- MTU/MSS/fragmentation implications;
- route/DNS integration boundary;
- server decapsulation and forwarding path;
- return traffic path;
- roaming/reconnect/session-resumption behavior;
- NAT traversal where applicable;
- IPv4/IPv6 behavior;
- what metadata remains visible outside encryption;
- implementation differences that materially change the data path.

The goal is architectural understanding and interoperability, not traffic-evasion instructions.

### `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
Record protocol/network behavior separately from UI/install documentation:

- default/common ports only where defined or conventionally used;
- configurable ports;
- TCP/UDP/other transport use;
- TLS/DTLS/QUIC/ESP/GRE/etc. layering;
- initial handshake state sequence;
- authentication exchange type;
- session setup/resumption;
- fallback transports;
- proxy support;
- NAT traversal/keepalive;
- version negotiation;
- failure/retry behavior;
- packet-capture/spec/source references where publicly documented.

### `DEPLOYMENT_TOPOLOGIES.md`
Document real deployment models:

- remote-access client/server;
- site-to-site;
- hub-and-spoke;
- mesh where applicable;
- reverse-proxy/gateway combinations;
- HA/load balancing;
- multi-server/control-plane models;
- split/full tunnel;
- dual-stack;
- cloud/on-prem/hybrid patterns;
- relationships between management plane, control plane and data plane.

### `REFERENCE_INDEX.md`
Maintain a compact index for the protocol containing links to every research file, upstream project, pinned source/release, major spec/RFC, current completion state, known blockers and exact next action.

## Separate server-panel menu files when needed

If one protocol has multiple important management products/panels, `SERVER_UI_AND_MENUS.md` may become an index and each product should receive a separate file such as:

- `server-ui/<project-name>.md`

Likewise, if many major clients exist, `CLIENT_UI_AND_MENUS.md` may become an index and each client should receive:

- `client-ui/<client-name>.md`

The owner's requirement is **granular recoverable evidence**, not one giant unreadable file.

## Daily/work-unit research record

Every meaningful research work unit must be persisted before moving on:

- update the relevant per-protocol file(s);
- update `docs/RESEARCH_LOG.md` or a dated campaign-status file;
- update `docs/PROJECT_STATE.md` when current phase/state changes;
- update the current `AGENTS_HANDOFF_*.md` or create a new one;
- make `AGENTS.md` point to the newest handoff;
- record blocked connector writes and do not loop blindly;
- record the exact next action.

## Evidence and safety rules for server installers

- Prefer canonical/official source first, then important community projects.
- Record forks and one-click installers because real operators use them, but never assume they are safe.
- Review shell/PowerShell/container manifests before rating an installer.
- Record security-sensitive side effects such as root execution, firewall changes, privileged containers, host networking, secret generation and auto-update behavior.
- Do not copy large third-party source trees into PVNetwork unless redistribution is approved.
- Do not create or preserve malicious persistence, exploitation, credential theft, destructive commands, or stealth mechanisms.
- Keep the research focused on legitimate deployment, interoperability, maintainability and security review.

## Second-layer completion gate

After the original `COMPLETE-RESEARCH-v1` gate, an entry may be marked `COMPLETE-REFERENCE-v2` only when every applicable requirement below has evidence:

- [ ] Server implementation/project ecosystem mapped
- [ ] Official and major community installer/deployment projects reviewed
- [ ] Server OS/container/orchestration install matrix completed
- [ ] Server panel/UI/menu maps completed
- [ ] Client install matrix completed across relevant OS targets
- [ ] Major client UI/menu maps completed separately
- [ ] Cryptographic design documented from authoritative specifications/source
- [ ] Data path/wire flow documented
- [ ] Ports/transports/handshake documented
- [ ] Deployment topologies documented
- [ ] Source/license/activity pins recorded for server and client projects
- [ ] Security/supply-chain risks of installer projects recorded
- [ ] Upgrade/uninstall/rollback behavior researched
- [ ] Protocol/server/client differences and uncertainties explicitly listed
- [ ] `REFERENCE_INDEX.md` links the complete dossier
- [ ] Latest AGENTS handoff contains the exact continuation state

`COMPLETE-REFERENCE-v2` still does not mean PVNetwork implementation or production certification.