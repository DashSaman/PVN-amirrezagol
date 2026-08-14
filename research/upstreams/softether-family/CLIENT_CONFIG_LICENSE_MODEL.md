# SoftEther client configuration, persistence and license model

Pinned source for architecture/config review: `SoftEtherVPN/SoftEtherVPN@b1f7ef00040786d00bfa06c27fa463d106851e0c`.

This note supports the original-v1 audit for PVNetwork entry **013 SoftEther VPN Protocol** and the shared runtime used by entries 014–015. It does not change any entry to strict completion.

## 1. Native client persistence is a first-class service concern

`src/Cedar/Client.h` defines the native client configuration filename as:

- `CLIENT_CONFIG_FILE_NAME = "$vpn_client.config"`

and also defines:

- a dedicated client configuration/RPC port;
- a notification port;
- `CLIENT_SAVER_INTERVAL = 30 * 1000`;
- a Windows client executable name `vpnclient.exe`;
- a client UI-helper service instance;
- a Windows registry key plus `RpcPort` and `RpcPid` values.

This is strong evidence that the upstream client is a persistent local service/manager system rather than a disposable command invocation.

Pinned source:
https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/src/Cedar/Client.h

## 2. Configuration writes are durable and periodic

`src/Cedar/Client.c` contains `CiSaverThread()`, which waits for `CLIENT_SAVER_INTERVAL` and calls `CiSaveConfigurationFile(c)`. Account creation through `CtCreateAccount(...)` also calls `CiSaveConfigurationFile(c)` and `CiNotify(c)` after mutating the account list.

When the client initializes its configuration reader/writer, the default path resolves through `NewCfgRw(&root, CLIENT_CONFIG_FILE_NAME)` unless an explicit path is supplied.

### PVNetwork consequence

A PVNetwork SoftEther adapter must not write `$vpn_client.config` behind the native service's back while it is running. The service has its own save loop and notification/RPC lifecycle, so direct concurrent file mutation risks lost updates or inconsistent state. Prefer the upstream management/RPC boundary for account lifecycle; use file import/export only as an offline/migration mechanism with explicit ownership.

## 3. Account schema is materially richer than host/user/password

The pinned `ACCOUNT` and RPC structures include, among other fields:

- `CLIENT_OPTION` and `CLIENT_AUTH` objects;
- server-certificate checking and retry behavior;
- default trust-store behavior;
- pinned server certificate;
- startup-account flag;
- shortcut key;
- create/update/last-connect timestamps;
- virtual adapter/device selection;
- proxy type/name;
- server port and Virtual HUB;
- live connection/session status;
- negotiated cipher/protocol/underlay details.

### Canonical schema decision

PVNetwork should keep a canonical product-level profile schema and translate it into the SoftEther account/RPC model. It should **not** define the native `$vpn_client.config` layout as its public cross-platform schema. Fields that have no portable equivalent should remain in an adapter-specific extension object rather than being silently dropped.

## 4. Remote configuration is an explicit upstream policy surface

`CLIENT_CONFIG` contains `AllowRemoteConfig`, and `RPC_CLIENT_PASSWORD` includes both a password and `PasswordRemoteOnly`. Together with the dedicated configuration/RPC port, this means remote management exposure is part of the security model.

### PVNetwork policy

- Default adapter behavior: management endpoint bound/accessible only as narrowly as required by the upstream local manager design.
- Do not enable remote client configuration merely to simplify PVNetwork orchestration.
- If remote management is explicitly enabled, require a separate product security decision, credential lifecycle and network exposure audit.

## 5. Virtual adapter lifecycle belongs to the native client

The client RPC model has explicit create/get/set/enumerate operations for Virtual LAN adapters and records device name, enabled state, MAC address, version, driver filename and GUID where applicable. `Client.c` also contains machine-change logic that can regenerate virtual-adapter MAC addresses.

### PVNetwork consequence

Adapter reconciliation must treat the virtual NIC as upstream-owned state. PVNetwork can observe and request lifecycle changes but should not assume a static interface identity across reinstall/machine-identity changes. Connection health must therefore key on upstream account/session identity plus current adapter mapping, not only an OS interface name.

## 6. Config import/export and secret handling: explicit residual gap

The pinned source proves the durable config filename, save loop, account/auth structures and RPC ownership, but this review has **not yet established a safe generic import/export contract for secrets across every supported platform**.

Accordingly:

- do not document `$vpn_client.config` as a plaintext-portable interchange format;
- do not assume every `CLIENT_AUTH` credential representation is safe to round-trip through PVNetwork JSON;
- require a dedicated secret-handling audit before the adapter persists credentials outside upstream-owned storage;
- prefer references to product secret storage where possible, with translation into the native client at execution time.

This residual gap remains a blocker for strict `COMPLETE-RESEARCH-v1` on entry 013.

## 7. License and redistribution facts

The top-level pinned `LICENSE` is Apache License 2.0. Its redistribution terms require, among other obligations, providing the license, preserving applicable notices, marking modified files, and carrying NOTICE attributions if such a NOTICE is part of the distributed work.

The repository license text also explicitly points distributors to `src/THIRD_PARTY.TXT` for included third-party software license conditions. Separately, `.gitmodules` shows source dependencies/submodules including `cpu_features`, `tinydir`, `BLAKE2`, `libhamcore`, `oqs-provider` and `liboqs`.

Pinned sources:

- https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/LICENSE
- https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/src/THIRD_PARTY.TXT
- https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/.gitmodules

### PVNetwork redistribution rule

A binary redistribution gate must include a generated third-party attribution bundle based on the exact build graph. Recording “Apache-2.0” for the top-level repository alone is insufficient for a shipped package that incorporates third-party/submodule code.

## 8. Entry-level decision update

For entry 013, the preferred product architecture is now more explicit:

`PVNetwork canonical profile -> SoftEther adapter -> native client management/RPC -> upstream persistent service/config -> virtual adapter/session`

Direct mutation of `$vpn_client.config` while the service owns it is **not** the preferred architecture.

For entries 014–015, the client file format is not the primary integration surface because the current source evidence classifies those capabilities as server-centric/composite server capabilities.

## 9. Remaining closure gaps

- safe fixed release after the currently reviewed security advisory situation;
- exact secret-at-rest representation and supported migration/export paths;
- `src/THIRD_PARTY.TXT` + dependency-specific license inventory transformed into a PVNetwork attribution checklist;
- final UI/command mapping and gate-by-gate audit against `research/PROTOCOL_RESEARCH_TEMPLATE.md`.

Entries 013–015 therefore remain below strict completion.