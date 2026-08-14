# WireGuard Core Architecture — Developer Research Notes

Pinned research source: `WireGuard/wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0`

Status: `IN-RESEARCH`.

## Role in the ecosystem

`wireguard-go` is the portable userspace implementation/reference, not the universal answer for every PVNetwork platform. The upstream README itself recommends the native/kernel implementation where appropriate and points Windows users toward the fuller Windows application/module integration.

PVNetwork should therefore model WireGuard as a **capability behind a Core Adapter**, with platform-specific engine choices, rather than coupling product logic to one userspace executable.

## Source layout at the pinned revision

The recursive tree shows these important architectural areas:

- `device/` — protocol/device state, peers, key/handshake-related state machine, queueing and packet processing, allowed-IP structures, timers and device lifecycle.
- `conn/` — transport/socket binding abstractions and platform-specific implementations.
- `tun/` — virtual-interface abstraction and OS-specific TUN implementations.
- `ipc/` — control/configuration interface implementation and platform-specific IPC support.
- `ratelimiter/` — rate-limiting support with tests.
- `rwcancel/` — cancellation/read-write utility layer.
- root/platform files — process entry point and platform-specific integration.
- tests are distributed beside the relevant packages rather than isolated in one test directory.

This separation is useful for PVNetwork because it demonstrates a clear boundary between protocol/device logic, socket binding, virtual interface, and control plane.

## Engine ownership model for PVNetwork

Recommended abstraction boundary for later implementation research:

`PVNetwork UI -> Application/Session layer -> WireGuardAdapter -> platform-selected WireGuard engine`

The adapter should own only product-facing lifecycle/state translation. It should not reproduce WireGuard cryptography or protocol primitives.

Candidate platform strategy to validate later:

- Linux: prefer OS/native kernel capability when available; userspace implementation is a fallback/reference.
- Windows: study the official Windows app/service/module architecture rather than treating `wireguard-go` as a standalone desktop product.
- Android: official Android source already provides a backend abstraction with userspace and kernel-oriented options.
- Apple: official Apple source uses NetworkExtension plus a WireGuard adapter/library layer.
- Other Unix-like systems: userspace support exists but platform limitations differ and must be tested separately.

## Control/status boundary

The portable source includes a dedicated IPC/control area and device state machinery. PVNetwork's future adapter should normalize only the product-facing concepts it needs, for example:

- lifecycle state;
- selected profile identity;
- human-readable failure category;
- transfer/peer statistics where available;
- interface/session health;
- reconnect/recovery events.

Do not expose raw engine implementation details directly to UI models.

## Configuration boundary

WireGuard configuration parsing/serialization is implemented differently in the platform applications as well as in shared protocol libraries. PVNetwork should define a canonical internal profile model and preserve enough metadata to round-trip supported configuration without silently dropping fields.

Import should be separated from persistence:

1. parse into an immutable/canonical model;
2. validate semantics;
3. show any lossy/unsupported fields;
4. persist using platform-appropriate protected storage;
5. hand an engine-specific representation to the adapter only at activation time.

## Testing lessons from the core tree

The pinned tree contains extensive package-level tests around allowed-IP handling, device behavior, cookies, bindings and other core areas. PVNetwork should not duplicate those upstream protocol tests. It should add **adapter-contract and interoperability tests** around its own boundary:

- canonical-profile round trip;
- adapter state transitions;
- engine crash/exit mapping;
- network-change recovery;
- statistics mapping;
- cleanup after failure;
- cross-platform configuration equivalence.

## License/reuse position

The reviewed repository license is MIT. This makes the core a promising reuse candidate, but a final decision still requires dependency review, target-platform packaging review, trademark/attribution review, and Store-specific architecture review.

## PVNetwork decision — current research stage

- Study/reuse mature upstream engine: **YES, candidate**.
- Reimplement protocol or cryptography: **NO**.
- Use the exact same engine arrangement on every platform: **NO**.
- Couple UI directly to `wireguard-go`: **NO**.
- Keep WireGuard and AmneziaWG as distinct adapter capabilities/versioned engines: **YES**.

## Remaining evidence gaps

- current canonical upstream release/tag policy beyond the pinned GitHub mirror snapshot;
- dependency-level SBOM/license review;
- upstream mailing-list/release regression review;
- exact Store/entitlement constraints for each target platform;
- performance/resource measurements on PVNetwork target devices;
- final API contract for the PVNetwork WireGuard adapter.