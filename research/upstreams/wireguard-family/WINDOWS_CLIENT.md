# WireGuard for Windows — Source / Storage / Service / UI Map

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

## Source pin

Research mirror: `WireGuard/wireguard-windows`

Canonical upstream stated by the mirror: `git.zx2c4.com/wireguard-windows`

Pinned mirror commit:

`4e6726c23ae9c5cb58e0c9910f3b7515621d133d`

Root/repository license metadata reviewed: MIT.

Important source domains include:

- `conf/`
- `manager/`
- `ui/`
- `driver/`
- `services/`
- `ringlogger/`
- `updater/`
- `elevate/`
- `l18n/`
- installer/build assets
- `embeddable-dll-service/`.

## Product/process architecture

The pinned Windows client is not a single normal-user executable directly controlling networking without privilege separation.

The source uses a **Windows service manager + user UI process + IPC** architecture.

Pinned `manager/service.go` shows a `WireGuardManager` Windows service that:

- initializes the ring logger;
- tracks tunnel services;
- registers configuration-store change callbacks;
- tracks active Windows user sessions;
- decides whether a session user is admin/elevatable or an allowed Network Configuration Operator;
- launches UI processes for eligible user sessions;
- creates pipe/IPC channels and shares log mapping handles;
- handles Windows session logon/logoff events;
- supervises/restarts UI processes;
- notifies the UI when the manager is stopping;
- handles manager uninstall when requested.

### PVNetwork Windows lesson

Separate:

1. normal user UI/process;
2. privileged network/service owner;
3. tunnel-specific service/driver lifecycle;
4. explicit local IPC boundary;
5. logs/diagnostics.

Do not run the entire PVNetwork GUI permanently elevated merely because network operations require privilege.

## Configuration storage — DPAPI

Pinned `conf/store.go` has a strong security/storage design lesson.

Imported plaintext WireGuard files use `.conf`, but persisted application tunnel configuration uses:

`.conf.dpapi`

The save path serializes configuration to wg-quick form and encrypts it through Windows DPAPI before writing a locked-down file.

Load logic detects `.conf.dpapi` and decrypts it using DPAPI before parsing.

The store can also recognize/import unencrypted `.conf` files, but the application's own saved form is the DPAPI-protected form.

### PVNetwork rule

**import format is not storage format.**

For Windows PVNetwork:

- standard `.conf` remains an import/export format;
- private keys/PSKs/credentials should not remain as ordinary plaintext product files;
- use Windows secure storage/DPAPI or an equivalent product vault;
- canonical product metadata should reference protected secret material where practical;
- backups/exports need separate explicit secret-inclusion policy.

## Configuration naming / persistence behavior

The pinned store validates tunnel names before load/save/delete and maps each stored tunnel to one DPAPI-protected file under the application tunnel configuration directory.

The manager registers store-change callbacks and can migrate unencrypted configurations into the protected store.

PVNetwork lesson:

- validate identity/name independently from file path;
- support controlled migration from legacy/import plaintext into protected storage;
- never silently leave imported private keys in an unprotected staging file.

## Main Windows window

Pinned `ui/managewindow.go` defines `ManageTunnelsWindow`.

Current main tabs/pages include:

- **Tunnels**
- **Log**
- an **Update** page that is added when an update is found.

The window closes to tray rather than terminating the application/tunnel manager.

The source also adds an **About WireGuard** action to the Windows system menu.

### PVNetwork lesson

Connection/service lifetime should be independent from closing a desktop window. UI close/minimize must never accidentally terminate active protected connectivity unless explicitly requested.

## Tunnels page

Pinned `ui/tunnelspage.go` shows a source-level toolbar/context-menu inventory.

### Toolbar/add menu

- **Import tunnel(s) from file…**
- **Add empty tunnel…**
- remove selected tunnel(s)
- export all tunnels to ZIP

### Tunnel context menu / shortcuts

- Toggle
- Import tunnel(s) from file
- Add empty tunnel
- Export all tunnels to ZIP
- Edit selected tunnel
- Remove selected tunnel(s)
- Select all

### Selected-tunnel area

- configuration view
- **Edit** button for selected tunnel.

The import path handles standard `.conf` and ZIP archives containing `.conf` files, parses via wg-quick-compatible config parsing and rejects duplicate tunnel names.

PVNetwork lessons:

- file/ZIP import is separate from protected storage;
- bulk import should report partial failures instead of silently dropping invalid items;
- duplicate detection should be semantic/stable, not just filename based;
- standard WireGuard import/export should remain compatible while product secrets are stored securely.

## System tray menu

Pinned `ui/tray.go` shows current menu/state concepts:

- Status
- active Addresses (shown when applicable)
- individual tunnel toggle actions or a **Tunnels** submenu when many exist
- **Manage tunnels…**
- **Import tunnel(s) from file…**
- **About WireGuard…**
- **Exit**
- dynamically inserted **An Update is Available!** action when relevant.

Tray tunnel actions reflect active/stopped state and can toggle a tunnel directly.

The tray also shows activation/deactivation/error notifications.

### PVNetwork lesson

Desktop quick actions should remain shallow:

- status;
- current profile/server;
- connect/disconnect/switch;
- open main app;
- diagnostics/update when relevant.

Do not expose all advanced config fields in the tray.

## Limited operator model

Pinned manager source supports a restricted UI path for Windows Network Configuration Operators when a policy/config flag allows it, while full admin operations remain controlled.

PVNetwork should explicitly design Windows privilege roles if enterprise deployments need restricted operators rather than relying on “run as admin” for every user.

## IPC ownership

The manager launches UI processes with inherited pipes/handles and uses a dedicated IPC server/client layer for tunnel/config/state operations.

PVNetwork should build a versioned authenticated local IPC contract with:

- OS ACL restrictions;
- explicit operation authorization;
- no arbitrary command execution;
- length/schema validation;
- secret redaction;
- service/UI version compatibility.

Do not expose the privileged service as a general local/LAN network API.

## Logs

The manager initializes a ring logger and shares a mapping handle to UI processes. There is a dedicated Log page.

PVNetwork should preserve the usability idea while adding strict redaction for private keys, PSKs, endpoints/account metadata and generated core configs.

## Update surface

Pinned source has dedicated updater code and UI/update-page behavior.

PVNetwork must separate:

- Microsoft Store update path;
- direct signed Windows updater if offered;
- component/core update policy;
- rollback/recovery.

Do not copy a self-updater into a Store build without current Store-policy review.

## Reuse decision

WireGuard Windows is both:

- a strong official Windows architecture reference;
- a potential reuse source for appropriately separated MIT components, subject to dependency/path/build review.

PVNetwork should **not** necessarily fork the complete UI. More reusable concepts/components include:

- DPAPI-protected tunnel-store pattern;
- privileged manager/service separation;
- local IPC architecture;
- official tunnel/driver integration;
- config parser/storage behavior;
- embeddable DLL/service patterns.

Final reuse requires exact component/dependency review.

## Regression requirements derived from Windows source

- imported plaintext config is migrated/stored protected;
- repeated start/stop does not leak tunnel services/processes;
- UI process restart does not terminate active tunnel unexpectedly;
- Windows user logon/logoff/session switch does not corrupt manager state;
- limited operator cannot perform admin-only operations;
- tray/main window state remains synchronized;
- manager service restart restores expected UI/state cleanly;
- update/uninstall removes/replaces service components safely;
- logs remain redacted;
- malformed IPC messages cannot invoke privileged operations;
- multiple imported configs/ZIP partial failures are reported correctly.

## Remaining gaps

- exact tunnel service/driver install lifecycle map;
- embeddable DLL/service API review;
- updater signing/package details;
- full editor field map;
- complete current Windows UI screenshots/assets/translation audit;
- current Windows issue/regression review;
- exact Windows supported-version/architecture/package matrix;
- final Store/MSIX/direct distribution feasibility for PVNetwork.
