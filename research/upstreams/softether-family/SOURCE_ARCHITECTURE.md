# SoftEther VPN — Source / Architecture Map

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

## Source pin

Repository: `SoftEtherVPN/SoftEtherVPN`

Reviewed research revision: `b1f7ef0...` on the current SoftEtherVPN source line previously pinned in this repository. Before implementation, materialize and record the exact full commit/tag again and compare it with the current release branch.

Root license reviewed: **Apache License 2.0**.

## Important source layers

The current source tree is split into product executables and shared networking/runtime libraries rather than one monolithic VPN application.

### `src/Cedar/`

Core VPN/networking/session/protocol code.

Verified protocol/source areas include:

- `EtherIP.c`
- `L2TP.c`
- `IPsec.c`
- `Proto_OpenVPN.c`
- `Proto_SSTP.c`
- `Protocol.c`
- client/server/session/network logic and related headers.

This is the key reusable technical core area, but file-level dependencies and platform assumptions still need exact build review.

### `src/Mayaqua/`

Shared lower-level runtime/platform/crypto/network/file/system utilities used by Cedar and product executables.

PVNetwork must not treat `Cedar` as dependency-free: any reuse of protocol/core code pulls a larger Mayaqua/platform dependency surface that needs SBOM/license/security review.

### `src/vpnserver/`

VPN Server executable/product entry point.

### `src/vpnclient/`

VPN Client executable/product entry point.

### `src/vpnbridge/`

VPN Bridge product/source entry point.

### `src/vpncmd/`

Command-line administration/management tool.

### Manager/UI source

The repository also contains server/client-manager UI and management source/resources for supported desktop environments/builds. These should be treated as administrator UX references, not automatically as reusable PVNetwork UI.

## Native protocol vs compatibility protocols

SoftEther is both:

1. a native SoftEther VPN protocol/client-server implementation; and
2. a VPN server product that implements compatibility/protocol modules for other client families.

This distinction is critical for PVNetwork.

A SoftEther VPN Server accepting OpenVPN, SSTP, L2TP/IPsec or EtherIP/IPsec does **not** imply that PVNetwork should route every one of those client profiles through a SoftEther client engine.

PVNetwork should choose the best client engine per protocol while using SoftEther source as:

- the primary native SoftEther protocol candidate/reference;
- a server-side compatibility/reference implementation for some related protocols;
- a source of EtherIP/L2TPv3/IPsec behavior evidence where applicable.

## Native client/server architecture

The source separates client and server product executables from Cedar protocol/session code. This suggests a potential PVNetwork integration boundary around reusable shared libraries/core behavior rather than embedding or reskinning the complete SoftEther client manager UI.

Before integration approval, determine whether the needed native-client capabilities can be cleanly exposed behind a stable adapter/library boundary on each target OS.

## Virtual Hub / server-centric model

SoftEther's server architecture includes server-side Virtual Hub/session/user/bridge concepts. These are server/control-plane concepts and should not leak into the normal PVNetwork consumer client model unless the user is explicitly administering a SoftEther server.

PVNetwork should keep separate modes:

- **Connect** — end-user client profile/session;
- **Admin/Server Management** — optional advanced tool/module later, if product scope includes it.

Do not force Virtual Hub administration into the normal Connect screen.

## Configuration architecture

SoftEther products maintain persistent product/server/client configuration separately from transient sessions. The source contains dedicated configuration and management layers rather than using one `.ovpn`-style endpoint file as the sole database.

PVNetwork consequence:

- native SoftEther connection information needs normalization into PVNetwork's canonical profile model;
- server administration configuration is a separate domain model;
- passwords/keys/certificates must be mapped to product secure storage rather than copied blindly from SoftEther's product config representation;
- runtime session state remains transient.

A separate config/storage dossier is required before implementation.

## Management surfaces

SoftEther exposes multiple administration surfaces:

- GUI manager(s) on supported desktop builds;
- `vpncmd` command-line administration;
- server/client product configuration/runtime interfaces.

These are useful references for the later exhaustive server/client menu campaign, but PVNetwork should define its own versioned management API and authorization boundary rather than exposing arbitrary native management commands to untrusted UI/plugins.

## Platform implications

Source portability of server/core code does not prove one universal client experience across Android, iOS, Android TV, Windows, macOS and Linux.

PVNetwork needs per-platform decisions for:

- native SoftEther client availability/reuse;
- TUN/TAP/virtual adapter integration;
- privileged service/helper architecture;
- mobile VPN APIs;
- Store feasibility;
- background lifecycle;
- secure storage;
- packaging/signing/update.

## Protocol modules are not one capability flag

The source has separate protocol modules. PVNetwork must keep capability states independent for:

- native SoftEther VPN Protocol;
- EtherIP;
- EtherIP/IPsec;
- L2TP/L2TPv3-related modes;
- OpenVPN compatibility;
- SSTP compatibility;
- IPsec/IKE compatibility.

A bug or unsupported feature in one module does not automatically apply to all SoftEther modes.

## Reuse direction

Apache-2.0 root licensing makes SoftEther source attractive for commercial engineering evaluation relative to many GPL GUI applications, but reuse still requires:

- exact source pin;
- dependency/path-level license review;
- notices;
- patent/trademark/branding separation;
- security/advisory review;
- platform integration feasibility;
- API/binary boundary analysis;
- tests for only the modules PVNetwork actually ships.

## Remaining v1 gaps

- exact full current commit/tag pin and tree manifest;
- build/dependency/SBOM map;
- exact native SoftEther client configuration/profile/storage map;
- client/server manager UI/source map;
- current tests/CI/security/release evidence;
- protocol capability differences for entries 013–015;
- final support/reuse decision.

The later `COMPLETE-REFERENCE-v2` phase must add all server installers/panels/install matrices, full menus, cryptography and wire/data-flow documentation.
