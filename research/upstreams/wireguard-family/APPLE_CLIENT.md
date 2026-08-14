# WireGuard Apple Platforms — Developer Dossier

Pinned source: `WireGuard/wireguard-apple@2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`.

Research state: `IN-RESEARCH`.

## Source architecture

The pinned tree separates Apple integration into clear layers:

- `Sources/WireGuardApp/` — application UI/product layer.
- `Sources/WireGuardNetworkExtension/` — NetworkExtension packet-tunnel provider.
- `Sources/WireGuardKit/` — WireGuard adapter/library-facing layer.
- `Sources/Shared/` — shared models, keychain, logging and utilities.
- localized resources and app assets under the app tree.
- Xcode project/build configuration, `Package.swift` and `MOBILECONFIG.md` at repository level.

This is strong evidence that PVNetwork Apple support should be designed around an **app + NetworkExtension + adapter/shared model** architecture rather than treating Apple as a desktop/mobile wrapper around a generic process.

## NetworkExtension boundary

`PacketTunnelProvider.swift` subclasses `NEPacketTunnelProvider` and owns a `WireGuardAdapter`. It loads the saved tunnel-provider configuration, asks the adapter to start/stop the session, maps adapter failures to product-visible error categories, configures extension logging, and supports a narrow app-to-extension message path for runtime information.

PVNetwork should mirror the separation principle:

`App UI/state -> Apple tunnel manager -> NetworkExtension -> protocol adapter/core`

The product UI must not directly own packet-tunnel internals.

## Protected profile/secret storage

`Sources/Shared/Keychain.swift` stores configuration material using Apple's Security/Keychain APIs and returns persistent references rather than treating raw text as the long-lived cross-process identifier. The code also distinguishes iOS and macOS access behavior and allows the app and extension to access the required protected item.

PVNetwork requirement:

- imported configuration text is an interchange format, not the long-term persistence design;
- secrets/profile material needed by an extension should use Apple-supported protected storage and explicit access-group/entitlement architecture;
- deletion/migration must clean up stale references;
- diagnostic/export features must not accidentally expose keychain contents.

## Configuration/model evidence

The shared tree includes:

- `TunnelConfiguration+WgQuickConfig.swift` — text configuration conversion.
- `NETunnelProviderProtocol+Extension.swift` — NetworkExtension model conversion/storage bridge.
- app tunnel-management models under `Sources/WireGuardApp/Tunnel/`, including `TunnelsManager.swift`, state/error types and activation/on-demand support.

PVNetwork should keep canonical profile parsing separate from the Apple persistence object model, then generate the extension-facing representation at the adapter boundary.

## UI and product areas visible in the tree

The pinned source contains app tunnel-management code, localized strings, resources, document icons and platform-specific application code. A full screen-by-screen UI catalog is still required before this dossier can be marked complete.

The app resources are **reference evidence only**. Do not copy WireGuard logos/icons/document artwork into PVNetwork.

## Logging/error model

The source has shared logging plus NetworkExtension-specific error notification. The packet-tunnel provider converts low-level failures into a smaller set of user/product failure categories while preserving detailed logs.

PVNetwork should adopt the same principle:

- stable product error taxonomy;
- detailed internal error context;
- extension/app correlation identifier where useful;
- support export with secret redaction.

## Platform-specific lesson

The reviewed code includes a macOS-specific workaround for an Apple platform issue in extension shutdown. This is an important engineering lesson: PVNetwork's shared UI/core abstraction must still allow platform-specific lifecycle workarounds, and those workarounds need regression tests and version gating rather than being hidden in generic code.

## License/reuse

Repository metadata and source headers report MIT for the reviewed source. This is promising for reuse, but final shipping decisions require:

- dependency review;
- entitlement and NetworkExtension architecture review;
- Apple App Store/Mac distribution review at release time;
- attribution/trademark review;
- testing on current iOS/iPadOS/macOS versions.

Current classification: `REUSE-CANDIDATE / ARCHITECTURE-REFERENCE`.

## PVNetwork regression requirements derived from this source

Future Apple tests should cover:

- app/extension state synchronization;
- protected configuration reference creation/update/deletion;
- extension launch both from app-driven and OS-driven flows;
- corrupted/missing saved configuration;
- adapter error mapping to stable product categories;
- extension stop/restart and OS lifecycle changes;
- on-demand configuration changes;
- log correlation without secret leakage;
- app upgrade/migration while saved tunnels exist;
- Persian RTL in the app while addresses, keys, hashes and paths remain readable LTR tokens.

## Remaining gaps

- full iOS/macOS screen/menu map;
- exact tunnel-manager persistence lifecycle;
- full entitlement/app-group inventory;
- current issue/mailing-list/release regression review;
- complete test target/CI inventory;
- dependency/SBOM audit;
- current Store policy/entitlement feasibility review;
- performance/battery/network-transition testing on real devices.