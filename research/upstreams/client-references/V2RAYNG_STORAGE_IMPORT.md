# v2rayNG — Storage / Profile / Subscription / Import Model

Research date: 2026-08-14

State: `IN-RESEARCH / REFERENCE-ONLY` because application code is GPLv3.

Pinned source:

`2dust/v2rayNG@e8a82d9810ca1cf97a3cc8a9b9525a9f21955807`

## Storage technology

Current source uses **Tencent MMKV** as the primary application data layer.

`AngApplication.onCreate()` initializes MMKV and then initializes application settings/theme/work scheduling.

`MmkvManager` creates multiple named MMKV stores in multi-process mode rather than using one giant key/value namespace.

Observed stores include:

- `MAIN`
- `PROFILE_FULL_CONFIG`
- `SERVER_RAW`
- `SERVER_AFF`
- `SUB`
- `ASSET`
- `SETTING`

This is useful architectural evidence: separate logical stores reduce accidental coupling between profile content, transient/test affiliation metadata, subscriptions, assets and settings.

## Important encryption/storage caution

The `MmkvManager` initialization calls reviewed use `MMKV.mmkvWithID(..., MMKV.MULTI_PROCESS_MODE)` without an explicit MMKV `cryptKey` parameter.

The profile entity contains fields that may be sensitive, including passwords, secret keys and pre-shared keys.

Therefore:

- **application-level MMKV encryption is not evident from the reviewed manager calls**;
- this does not mean Android provides no filesystem/device encryption;
- it does mean PVNetwork should not copy this persistence approach for sensitive credentials without an explicit secure-storage design.

PVNetwork rule:

- canonical non-secret profile metadata can live in a product database/store;
- reusable passwords/tokens/private keys/PSKs must use platform secure storage/keychain/keystore or an explicitly encrypted vault;
- profile records should contain secure references where practical rather than raw secrets;
- support/backup/export behavior must classify each field by sensitivity.

## Profile schema

Current `ProfileItem` is versioned (`configVersion = 4`) and carries both generic and protocol-specific fields.

Observed categories include:

### Identity / grouping

- config type;
- subscription ID;
- added time;
- remarks/description.

### Endpoint / auth

- server;
- port;
- username;
- password;
- method;
- flow.

### Transport

- network;
- header type;
- host/path;
- KCP parameters;
- QUIC-related values;
- gRPC service/authority;
- XHTTP mode/extra/final-mask values.

### Security

- security mode;
- SNI;
- ALPN;
- fingerprint;
- insecure flag;
- ECH/pinning/certificate-name fields;
- REALITY public-key/short-ID/spider-style fields;
- additional verification fields.

### Other protocol fields

- WireGuard/private/PSK/local-address/reserved/MTU fields;
- Hysteria2 obfuscation/port-hopping/bandwidth fields;
- policy-group and proxy-chain references;
- browser dialer mode.

### PVNetwork lesson

A versioned canonical profile is necessary, but do not simply copy v2rayNG's one data class. PVNetwork needs a core-neutral typed schema with:

- shared endpoint/auth fields;
- protocol-specific extensions;
- transport/security/flow as separate typed axes;
- secure references instead of raw secrets where possible;
- original-source preservation;
- schema migrations;
- unknown-field preservation when feasible.

## Raw source preservation

`MmkvManager` has a dedicated `SERVER_RAW` store and methods to encode/decode raw server configuration alongside normalized profile records.

This is a valuable importer lesson.

PVNetwork should preserve:

1. original imported source (when safe/legal);
2. normalized canonical representation;
3. generated runtime core config.

These are different artifacts.

## Server list/grouping model

Current source stores profiles by GUID and maintains separate ordered lists by subscription/group ID.

It also stores a selected-server GUID separately.

PVNetwork lesson:

- profile identity should be stable and independent from current display order/group;
- subscription refresh should not require replacing unrelated local profile identity;
- selected/favorite/display state belongs outside core endpoint credentials.

## Duplicate detection

Current `ProfileItem.duplicateIdentity()` intentionally excludes metadata such as remarks, timestamps and subscription ID while including connection-affecting fields.

PVNetwork should use a similarly explicit **semantic connection identity**, but must account for:

- secure fields/references;
- protocol-specific defaults;
- core-version-dependent defaults;
- normalized domain/IP representation;
- transport/security/flow semantics.

Avoid hash/dedupe rules based only on share-link text.

## Subscription persistence

Current source stores subscription records separately from profiles and keeps subscription membership/server lists independently.

A subscription record can include auto-update behavior, URL and scheduling metadata.

`SubscriptionUpdater` schedules periodic WorkManager jobs only for subscriptions that have auto-update enabled and a non-empty URL, with per-subscription unique work and a persisted last-update timestamp.

### PVNetwork lesson

Separate:

- subscription source/account metadata;
- refresh schedule/state;
- fetched raw content;
- normalized profile set;
- user-local overrides/favorites;
- entitlement/traffic/expiry metadata where available.

Subscription refresh should not silently destroy user-owned local edits.

## Background subscription scheduling

Current `SubscriptionUpdater` uses WorkManager/RemoteWorkManager, network-connected constraints and unique periodic jobs per subscription. It includes explicit scheduling logic to avoid rapid reschedule loops.

PVNetwork should preserve the ideas:

- one stable task identity per subscription;
- minimum allowed refresh interval;
- network-aware scheduling;
- clear last-success/last-attempt/error state;
- no update storm after restart/manual refresh;
- foreground/background policy compatible with Store requirements.

Do not inherit exact scheduling intervals without product/Store review.

## Import entry points from current UI

Pinned `MainActivity` exposes separate actions for:

- QR-code import;
- clipboard import;
- local file/content import;
- manual profile creation by supported profile type.

Current manual creation routes to dedicated editors for:

- VMess;
- VLESS;
- Shadowsocks;
- SOCKS;
- HTTP;
- Trojan;
- WireGuard;
- Hysteria2;
- policy groups;
- proxy chains;
- custom config.

This is strong evidence that importer UX should distinguish **source type** and **semantic profile type**.

## Share link vs full generated configuration

Pinned `MainActivity` has separate actions for:

- sharing a profile/link representation;
- sharing full generated content/configuration.

Current localization changes also explicitly distinguish these concepts.

PVNetwork requirement:

Do not label both as “Export config.” Provide distinct actions such as:

- Share connection/profile link;
- Export canonical profile/backup;
- Export generated engine configuration (advanced/diagnostics), when appropriate.

## Full-config / endpoint import lossiness

A full Xray config can contain:

- inbounds;
- outbounds;
- routing;
- DNS;
- policies;
- observability/API behaviors.

A single endpoint/profile link usually contains far less.

PVNetwork importer must identify which semantic class was imported and mark unsupported sections explicitly. Never flatten a full engine config to one endpoint while reporting “import successful” without a lossiness warning.

## Backup / restore

Current source includes backup UI/manager paths and WebDAV-related functionality. This is useful reference evidence but needs separate security review because backups may contain connection secrets.

PVNetwork backup requirements:

- classify secrets;
- use encryption/password protection where sensitive data is exported;
- let user choose whether credentials/private keys are included;
- never upload plaintext credential backups to remote storage by default;
- version backup schema and support migrations.

## Multi-process implications

Current MMKV stores use `MULTI_PROCESS_MODE`, matching the app architecture where core services run in a dedicated daemon process.

PVNetwork Android must explicitly decide whether:

- UI and VPN daemon share one multi-process data store;
- IPC sends immutable session snapshots to the service;
- the daemon reads only runtime-safe copies.

A cleaner product architecture may reduce shared mutable storage by passing a versioned session configuration to the daemon.

## Logging concern

Pinned `CoreServiceManager` logs generated config content at debug level. Since generated configuration may include sensitive profile fields, PVNetwork must not assume core-generated config is safe for ordinary logs.

PVNetwork rule:

- generated config log must be redacted or disabled in production;
- support bundle should remove passwords, UUID/credentials/keys, PSKs, subscription secrets and identifying account metadata;
- raw source store must never be dumped blindly to logs.

## PVNetwork reuse decision

v2rayNG storage/import code is GPLv3 application code: **reference-only by default**.

Architecture lessons worth reimplementing independently:

- stable profile IDs;
- separate raw and normalized representations;
- separate subscription records;
- explicit profile schema version;
- per-subscription background refresh;
- distinct share-link vs generated-config operations;
- logical storage separation;
- multi-process lifecycle awareness.

Security improvement PVNetwork should make:

**do not persist reusable secrets/private keys as ordinary profile JSON in a non-explicitly-encrypted application data store.**

## Remaining gaps

- exact backup/WebDAV data format and secret exposure audit;
- exact subscription parser source/HTTP headers/response handling;
- all format parsers and lossiness behavior;
- settings store field matrix;
- migration history across profile config versions;
- direct test evidence for MMKV multi-process consistency;
- current issue history for data loss/subscription refresh/backup/import;
- secure-storage behavior, if any, outside the paths reviewed here.
