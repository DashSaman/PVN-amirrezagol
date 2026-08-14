# SoftEther VPN — Client / Server / Configuration / Management Surface

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

Reviewed source line: `SoftEtherVPN/SoftEtherVPN` at research revision `b1f7ef0...` previously pinned in this repository. Exact full/tag materialization remains a closure gap.

## Product executables / roles

Verified source tree separates:

- `src/vpnserver/` — VPN Server product/process;
- `src/vpnclient/` — VPN Client product/process;
- `src/vpnbridge/` — VPN Bridge product/process;
- `src/vpncmd/` — command-line administration tool;
- `src/vpnsmgr/` — server manager UI source;
- `src/vpncmgr/` — client manager UI source;
- shared networking/session/protocol logic under `src/Cedar/`;
- shared platform/runtime utilities under `src/Mayaqua/`.

PVNetwork lesson: server administration, client connection, bridge/site-to-site and command-line administration are separate product roles. Do not merge them into one normal-user screen.

## Client Manager role

Current source contains a dedicated VPN Client Manager rather than exposing raw protocol configuration directly through the engine process.

For PVNetwork research, client-manager features should be decomposed into product concepts such as:

- connection profiles/accounts;
- create/edit/delete/duplicate/import/export where supported;
- virtual network adapter management;
- connect/disconnect/status;
- authentication/certificate settings;
- advanced connection options;
- logs/status/diagnostics;
- optional proxy/transport settings;
- local client-service management.

The later `COMPLETE-REFERENCE-v2` campaign must enumerate every current menu/dialog/control from source/resources/screenshots. This v1 file records the architectural surface only.

## Server Manager role

Current source contains a dedicated VPN Server Manager plus `vpncmd` administration.

Server administration concepts include, depending on server capability/version:

- connect to/manage a server/cluster endpoint;
- Virtual Hub administration;
- users/groups/authentication;
- sessions/connections;
- listeners/services;
- bridge/virtual Layer-3 features;
- IPsec/L2TP/OpenVPN/SSTP/EtherIP compatibility services;
- logs/status/security settings;
- certificates/keys;
- access lists/policies;
- server configuration/maintenance.

PVNetwork rule: these are **admin/server-management concepts**, not the normal one-click client UX. If PVNetwork later includes server administration, isolate it as an advanced/server module with explicit privileges and security boundaries.

## vpncmd role

`vpncmd` is valuable as a machine/CLI management reference and for later reproducible server/client administrative tests.

Do not expose arbitrary `vpncmd` command execution directly to untrusted UI/plugins. If PVNetwork later automates SoftEther administration, use a typed product management API or narrowly controlled subprocess/IPC contract with command/argument validation.

## Configuration persistence domains

SoftEther products maintain persistent configuration separate from live sessions. The source has dedicated configuration/management mechanisms for server/client/bridge roles.

PVNetwork must keep distinct:

1. **PVNetwork canonical client profile** — endpoint/auth/native SoftEther settings;
2. **protected credential/certificate/key storage**;
3. **generated/runtime SoftEther client configuration**;
4. **server administration model** — Virtual Hub/users/listeners/services/etc.;
5. **transient session/runtime state**;
6. **sanitized diagnostics/logs**.

Do not import a whole SoftEther server configuration into a consumer connection profile and claim semantic equivalence.

## Secret-storage caution

This v1 pass does not yet approve SoftEther's native configuration files as PVNetwork secret storage.

Before reuse, audit exact representation of:

- user passwords/hashes;
- client passwords;
- certificates/private keys;
- shared secrets/PSKs;
- SecureNAT/DHCP-related credentials if any;
- server administration passwords;
- exported configuration backups.

PVNetwork should still default to platform secure storage/keychain/vault for reusable client secrets.

## Native SoftEther connection model

A native SoftEther client connection profile may include product concepts such as:

- server/host;
- listener port;
- Virtual Hub target;
- authentication method/user identity;
- certificate/key or password material;
- virtual network adapter binding;
- transport/proxy-related settings;
- reconnect/session options.

The exact current field map must be source-backed in v2 before UI cloning or importer compatibility claims.

## Virtual network adapter concept

SoftEther Client on desktop platforms has a virtual adapter abstraction managed separately from account/profile configuration.

PVNetwork must decide per platform whether:

- it can reuse a native SoftEther virtual adapter/service architecture;
- it should integrate native OS TUN/TAP/VPN APIs behind the SoftEther adapter;
- the native client is unsupported on that OS and another architecture is required.

Do not create the same desktop virtual-adapter UI on mobile where Android/iOS impose different VPN APIs.

## Server/client IPC / privilege separation

SoftEther source separates product executables and management components, but PVNetwork must independently audit which operations require elevation/admin privileges and how management channels are authenticated.

Product requirements:

- ordinary UI should not run permanently as root/admin;
- privileged server/client service actions should use authenticated local IPC or OS service APIs;
- management endpoints must not be exposed broadly by default;
- administrative credentials and server configuration must be redacted from logs.

## UI design lessons for PVNetwork

### Normal Connect mode

Expose only:

- profile/server;
- connect/disconnect;
- auth prompt/status;
- basic statistics/diagnostics;
- import/create/edit.

### Advanced client mode

May expose:

- Virtual Hub;
- adapter selection;
- certificate/auth type;
- proxy/advanced transport settings;
- reconnect/log options;
- exact SoftEther-native options supported by selected platform/core.

### Server Admin mode (optional later)

Keep entirely separate from normal connect UX. It can mirror administrator concepts but should use PVNetwork's own design, permissions and localization.

## Persian/RTL considerations

SoftEther manager terminology is highly technical. PVNetwork localization should keep tokens such as:

- IP addresses;
- hostnames;
- ports;
- Virtual Hub names;
- certificate fingerprints;
- MAC addresses;
- protocol names;
- log lines;

as readable LTR technical fragments inside Persian RTL layouts.

## v2 menu expansion requirement

Later full-reference work must create separate detailed files under the protocol/server-client reference for:

- SoftEther VPN Client Manager menus/dialogs/fields;
- SoftEther VPN Server Manager menus/dialogs/fields;
- `vpncmd` command categories;
- any web/admin UI in current/community projects;
- every server installer/panel UI that is selected for study.

Each menu item must be mapped to source/resource path where possible and version-pinned.

## Remaining v1 gaps

- exact full current client-manager menu/resource map;
- exact server-manager menu/resource map;
- exact config-file names/paths and secret serialization in the pinned release;
- privilege/IPC/auth model;
- client/server import/export/backup semantics;
- platform/package support matrix;
- current issue/regression sampling.

These remain explicit and are primarily later menu/install/security work; they do not justify conflating server and client roles in the current architecture.
