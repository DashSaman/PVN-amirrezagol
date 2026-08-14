# strongSwan / IKE / IPsec — Source Architecture

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

## Upstream

Repository: `strongswan/strongswan`

Current master/source was reviewed during this work unit. Earlier project research recorded a 2026 master head beginning `5011838…`; the exact full immutable SHA must be materialized again before implementation/release and should not be guessed from a shortened historical note.

Root `COPYING` reviewed: GNU GPL v2 family. Exact component/exceptions/linking implications must be reviewed before embedding in a closed commercial product.

## Core conceptual separation

PVNetwork must keep these concepts distinct:

### IKEv2 / IKEv1

Control-plane protocols for:

- peer authentication;
- cryptographic proposal negotiation;
- key exchange;
- creation/rekey/deletion of Security Associations;
- traffic-selector/CHILD_SA negotiation;
- liveness/mobility and related extensions.

### ESP

IPsec data-plane protocol normally providing confidentiality plus integrity/authentication depending on selected transforms/mode.

### AH

IPsec Authentication Header: integrity/authentication protection without payload encryption. It is a different data-plane protocol and has different NAT/deployment implications.

### L2TP/IPsec

Composition where IPsec protects traffic for an L2TP tunnel/session. It is not equivalent to “IKEv2 client” and requires an L2TP layer in addition to IKE/IPsec.

Do not implement one flat `ipsec=true` profile model.

## `libstrongswan`

`src/libstrongswan/` is the shared foundational library layer.

Responsibilities include categories such as:

- plugin framework;
- credential/certificate/key abstractions;
- cryptographic primitives/providers through plugins;
- ASN.1/encoding/parsing utilities;
- networking/general utility classes;
- settings/configuration;
- threading/scheduling/helpers;
- algorithm/transform registries.

PVNetwork lesson:

strongSwan behavior depends heavily on the exact plugin set loaded. “strongSwan version” alone is not enough to define the actual cryptographic/auth/platform capability surface.

## `libcharon`

`src/libcharon/` contains the reusable IKE/IPsec daemon/session machinery used by Charon front ends.

Important domains include:

- IKE_SA lifecycle;
- CHILD_SA/IPsec data-SA lifecycle;
- IKEv1 and IKEv2 task/state machines;
- authentication/credential handling;
- configuration/controller/bus/state;
- kernel-IPsec/network integration interfaces;
- plugins;
- daemon/service architecture;
- event/status/control interfaces.

### IKE_SA vs CHILD_SA

Pinned/current source has distinct `ike_sa` and `child_sa` abstractions.

PVNetwork canonical/state model should mirror the conceptual separation without copying private types:

- IKE session/auth/control state;
- one or more negotiated data/tunnel child states;
- product-level connection state above both.

A child rekey/failure is not always equivalent to losing authentication/profile state.

## Charon daemon/front ends

The project has Charon-based executables/front ends rather than one universal UI process.

Relevant roles include:

### `charon`

Main IKE daemon/service role on Unix-like platforms.

### `swanctl`

Modern configuration/control CLI using VICI rather than directly embedding UI state into daemon internals.

### `charon-cmd`

A more client-oriented command-line Charon front end useful as source/reference for simple initiated connections.

### NetworkManager/front-end integrations

StrongSwan can integrate through platform desktop network-management front ends/plugins. These are useful UI/integration references but not necessarily the right PVNetwork product API.

### Android front end

The source tree includes Android front-end/application integration. Android uses platform VPN APIs rather than assuming kernel XFRM/IPsec is directly owned like a desktop Linux daemon.

PVNetwork Android must separately own VpnService/user permission/service lifecycle and cannot assume the same kernel integration path as Linux.

## Plugin architecture

`src/libcharon/plugins/` and `src/libstrongswan/plugins/` contain major capability providers.

Examples of capability categories include:

- kernel IPsec/network backends;
- socket/network I/O;
- VICI control;
- EAP methods;
- certificate/credential loaders;
- crypto providers;
- PKCS#11/smart-card/token support;
- DNS/resolver-related behavior;
- mobility/HA/advanced extensions;
- logging/management/front-end integration.

### PVNetwork rule

Never advertise an algorithm/authentication/platform feature solely because strongSwan source contains a plugin for it.

Record:

- core version;
- compiled plugin set;
- loaded plugin set;
- OS/kernel backend;
- crypto provider;
- selected IKE/IPsec proposal;
- server interoperability evidence.

## Kernel/IPsec boundary

On Linux/Unix-like systems strongSwan commonly controls kernel IPsec state/policies through kernel backend plugins such as netlink/PF_KEY-style implementations.

This creates a clear split:

`IKE daemon/control`

`-> kernel backend`

`-> OS IPsec policy/SA/data plane`

PVNetwork Linux design should not implement ESP in user-space if the selected supported architecture uses the mature kernel IPsec stack.

## VICI control boundary

The `vici` plugin exposes a structured IPC/control protocol used by tools such as `swanctl`.

This is attractive for a PVNetwork daemon adapter because it separates product/UI control from private daemon internals.

Security/product rule:

- keep VICI/local control endpoint private and permission-restricted;
- never expose arbitrary VICI operations to untrusted plugins or LAN clients;
- normalize commands/events through a PVNetwork IPsec Adapter;
- sanitize logs/config/event payloads;
- version/test daemon/client compatibility.

## Configuration model

Modern strongSwan uses structured daemon/client configuration (`swanctl`/VICI model), while legacy `ipsec.conf`/starter-style paths exist historically.

PVNetwork should not use either native config syntax as its authoritative database.

Use:

`external/native/imported profile`

`-> canonical PVProfile/IPsec extension`

`-> platform/capability validation`

`-> generated strongSwan/native-OS configuration`

`-> transient IKE/CHILD session state`

Preserve unsupported/legacy input and mark lossy conversions.

## Authentication model

IKE authentication can involve multiple families such as:

- certificate/public-key;
- pre-shared key where appropriate;
- EAP username/password/token methods;
- smart-card/PKCS#11/key-store identities;
- platform-managed identities.

PVNetwork should model authentication type and **secure credential/key reference** separately from endpoint/policy config.

Never persist reusable IKE passwords/PSKs/private keys in ordinary profile JSON.

## Native OS vs embedded strongSwan decision

PVNetwork should not force strongSwan everywhere.

Evaluate per platform:

- Linux: strongSwan + kernel IPsec is a strong primary candidate;
- Android: strongSwan Android/VpnService is a reference/candidate, but native platform IKEv2 facilities may be preferable depending on required feature coverage and API version;
- Apple: official NetworkExtension/NEVPN IKEv2 APIs are likely preferable for Store-aligned IKEv2 where feature coverage is sufficient;
- Windows: native Windows IKEv2/IPsec/VPN platform may be preferable for standard IKEv2 profiles;
- IKEv1/vendor-specific/advanced combinations may require separate engine/platform decisions.

The PVNetwork IPsec Adapter should hide whether a platform uses native OS APIs or a strongSwan-based engine.

## Product state model

Normalize at least:

- profile/configured;
- preparing credentials;
- negotiating IKE;
- authenticating;
- IKE established;
- CHILD/data SA establishing;
- connected;
- rekeying;
- reconnecting;
- auth/user action required;
- network unavailable;
- policy/kernel install failure;
- disconnected/error.

Do not collapse every IKE notify/error/kernel failure into “connection failed”.

## Reuse direction

strongSwan is a **strong Linux/advanced IPsec reference and candidate engine**, but GPL/root/component obligations plus plugin/dependency architecture require deliberate reuse design for a closed commercial product.

Prefer:

- native OS IKEv2 APIs when they satisfy product requirements and Store constraints;
- strongSwan daemon/library/control integration where advanced interoperability or platform support justifies it;
- process/service separation on desktop/server-like systems rather than embedding privileged IPsec management directly into UI.

## Remaining v1 gaps

- exact current full source/tag/release pin;
- complete plugin/capability/dependency/license inventory;
- current strongSwan security advisories/releases;
- Android exact architecture/package/license/store evidence;
- native Windows/Apple/Android capability comparison;
- exact IKEv1 vs IKEv2 algorithm/auth support matrix;
- per-entry decisions 004–008;
- current issues/regression examples.

Later `COMPLETE-REFERENCE-v2` must add server implementations/installers, full OS install matrices, server/client menus, cryptography, IKE/ESP/AH wire/data path, ports/NAT-T/handshake and deployment topologies.
