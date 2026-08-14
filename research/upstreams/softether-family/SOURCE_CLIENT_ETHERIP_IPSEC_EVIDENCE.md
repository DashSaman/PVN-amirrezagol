# SoftEther source, client, EtherIP and IPsec evidence

Research pin: `SoftEtherVPN/SoftEtherVPN@b1f7ef00040786d00bfa06c27fa463d106851e0c`.

This note closes several high-value original-v1 evidence gaps for PVNetwork entries **013 SoftEther VPN Protocol**, **014 EtherIP**, and **015 EtherIP/IPsec**. It is an evidence note, not a strict completion claim.

## 1. Source ownership and protocol boundaries

The pinned upstream tree has dedicated Cedar protocol implementations rather than treating EtherIP/IPsec as UI-only aliases:

- `src/Cedar/Proto_EtherIP.c` / `.h` — EtherIP protocol stack and L2 bridging path.
- `src/Cedar/Proto_IPsec.c` / `.h` — IPsec service, UDP/raw-ESP dispatch and service lifecycle.
- `src/Cedar/Proto_IKE.c` / `.h` — IKE control plane used by the IPsec server.
- `src/Cedar/IPC.c` and the Virtual HUB/session machinery — internal handoff between protocol front ends and the SoftEther switching/session core.
- `src/Cedar/Client.c` — native client manager/service-side account and virtual-NIC control path.

Pinned source links:

- https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/src/Cedar/Proto_EtherIP.c
- https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/src/Cedar/Proto_IPsec.c
- https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/src/Cedar/Proto_IKE.c
- https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/src/Cedar/Client.c

### PVNetwork consequence

Entries 013, 014 and 015 should share a SoftEther-family integration layer but **must not be collapsed into one capability flag**. Entry 013 is the native SoftEther client/server protocol path. Entry 014 is a distinct EtherIP L2 encapsulation path. Entry 015 adds IPsec/IKE service state and transport/security ownership around that EtherIP path.

## 2. EtherIP packet and Virtual HUB lifecycle

`Proto_EtherIP.c` gives direct evidence for the runtime ownership of entry 014:

1. `EtherIPIpcConnectThread()` resolves an EtherIP client ID through `SearchEtherIPId(...)`, including wildcard fallback.
2. The resolved record supplies `HubName`, `UserName`, and `Password` to `NewIPC(...)` with `IPC_LAYER_2`.
3. Receive-side EtherIP processing checks the two-byte EtherIP header/version before forwarding the recovered Ethernet frame with `IPCSendL2(...)`.
4. Virtual-HUB-to-tunnel traffic is received with `IPCRecvL2(...)`, receives the EtherIP header, and is queued for transmission.
5. A change to the EtherIP ID mapping version triggers comparison of hub/user/password state and forces reconnect when the effective mapping changes.
6. The same implementation can run an L2TPv3 mode, but EtherIP and L2TPv3 are explicitly distinguished by `s->L2TPv3`; PVNetwork must preserve that distinction.

This establishes that EtherIP in SoftEther is not merely a configuration label: it is a concrete L2 data plane joined to the Virtual HUB through SoftEther IPC.

### Reuse decision — entry 014

**Reuse candidate: strong for a SoftEther-backed server capability, weak for a generic embedded client core.**

PVNetwork can reuse the upstream protocol/server implementation when it intentionally ships or controls the SoftEther runtime. It should expose EtherIP as its own capability with server-side mapping fields rather than pretending the normal SoftEther VPN account profile is sufficient. A future independent EtherIP implementation should not depend on the entire SoftEther server merely to obtain the protocol.

## 3. EtherIP/IPsec is coupled to the SoftEther IKE/IPsec service

`Proto_IPsec.c` directly ties service enablement to both `EtherIP_IPsec` and `L2TP_IPsec`:

- `IPsecCheckOsService()` computes whether IPsec is in use from `sl.EtherIP_IPsec || sl.L2TP_IPsec`.
- On Windows, enabling the SoftEther IPsec stack initializes the Windows integration and can stop the conflicting OS IPsec service; disabling it restores the OS service.
- On non-Windows systems the code toggles kernel ESP processing to avoid conflicting ownership.
- Packet dispatch recognizes IKE/ISAKMP, UDP-encapsulated ESP and raw ESP, including non-ESP/non-IKE markers used around NAT-T-style demultiplexing.
- Service settings are guarded by `LockSettings`; normalization maintains an IPsec secret and valid default Virtual HUB state.

`Proto_EtherIP.c` also receives the IKE/IPsec server objects at construction and its MSS calculation explicitly subtracts IP/UDP/ESP/tunnel overhead. This is strong source evidence that entry 015 is not simply “entry 014 plus a checkbox”: the runtime owns IKE/IPsec transport, OS-service conflict handling, encryption/tunnel overhead and service state.

### Reuse decision — entry 015

**Reuse candidate: strong only as a SoftEther-server composite.**

For PVNetwork, `EtherIP/IPsec` should be modeled as a composite capability requiring the SoftEther IPsec/IKE server plus EtherIP mapping. UI/config should surface both the EtherIP identity-to-HUB mapping and the IPsec service/secret state. Do not expose entry 015 as available just because the EtherIP parser is present.

## 4. Native SoftEther client architecture evidence

`src/Cedar/Client.c` identifies itself as the **Client Manager** and imports account, admin, connection, IPC, listener, virtual adapter, VLAN and platform UI modules. The pinned source shows several important ownership facts:

- client state is long-lived (`static CLIENT *client`), with listener/notification state separated from active sessions;
- Windows virtual-NIC lifecycle is owned by the client and can regenerate adapter MAC addresses when machine identity changes;
- client notification readiness is checked through a localhost service/port path, proving a manager/service separation rather than a purely in-process GUI;
- RPC-style client operations (`CtEnumVLan`, `CtSetVLan` and related client manager functions) mediate virtual adapter and account operations;
- configuration support is provided through the Mayaqua `Cfg` layer, rather than being an arbitrary PVNetwork JSON contract.

### Reuse decision — entry 013

**Reuse candidate: conditional.** The upstream native client/service is a substantial, stateful runtime with virtual-adapter and local-manager semantics. PVNetwork should prefer adapter/wrapper integration around the supported SoftEther client/service boundary when native SoftEther compatibility is required, while keeping PVNetwork's canonical profile schema independent. Copying pieces of Cedar directly into a shared lightweight client core would create lifecycle, service and platform coupling that is larger than the protocol itself.

## 5. Build, packaging and dependency ownership

Pinned top-level `CMakeLists.txt` provides the following reproducible build facts:

- CMake minimum is 3.15 and the project is C99.
- The pinned project version is formed as `5.02.${BUILD_NUMBER}` with default build number `5187`.
- Builds from a Git checkout require submodules; missing `src/libhamcore/CMakeLists.txt` is a fatal configuration error.
- Windows/vcpkg integration is explicitly checked when a vcpkg target triplet is used.
- Unix packaging defines separate components for `common`, `vpnserver`, `vpnclient`, `vpnbridge`, and `vpncmd`, with DEB and RPM packaging paths.
- Build numbers below 5180 are warned as incompatible with client binaries distributed by SoftEther Corporation. PVNetwork therefore must not treat arbitrary rebuild version numbers as wire/client-compatible release identifiers.

Pinned `.gitmodules` records external source dependencies/submodules including:

- `google/cpu_features`
- `cxong/tinydir`
- `BLAKE2/BLAKE2`
- `SoftEtherVPN/libhamcore`
- `open-quantum-safe/oqs-provider`
- `open-quantum-safe/liboqs`

This means a PVNetwork source build/SBOM must include submodule provenance rather than reporting only the top-level SoftEther repository.

The pinned `.github/workflows/` tree also contains separate workflows for Linux, macOS, FreeBSD, musl, LibreSSL, Fedora Rawhide, Docker AIO, sanitizer, Coverity and source-release build paths. That breadth is useful upstream evidence, but it does **not** substitute for PVNetwork's own reproducible-build and dependency verification gates.

Source links:

- https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/CMakeLists.txt
- https://github.com/SoftEtherVPN/SoftEtherVPN/blob/b1f7ef00040786d00bfa06c27fa463d106851e0c/.gitmodules
- https://github.com/SoftEtherVPN/SoftEtherVPN/tree/b1f7ef00040786d00bfa06c27fa463d106851e0c/.github/workflows

## 6. Explicit support/reuse matrix

| Entry | Upstream ownership | PVNetwork original-v1 decision | Why |
|---|---|---|---|
| 013 SoftEther VPN Protocol | Cedar native client/server + local client manager/service + virtual adapter | `SUPPORTED-BY-UPSTREAM-ADAPTER`, conditional reuse | Full runtime is reusable, but lifecycle/service/schema should remain behind a PVNetwork adapter. |
| 014 EtherIP | `Proto_EtherIP` + IPC L2 + EtherIP ID mapping | `SERVER-CAPABILITY`, conditional reuse | Concrete L2 protocol exists; deployment/config ownership is server-centric. |
| 015 EtherIP/IPsec | EtherIP + `Proto_IPsec` + IKE/ESP + OS-service ownership | `COMPOSITE-SERVER-CAPABILITY`, conditional reuse | Requires IPsec/IKE runtime and service configuration in addition to EtherIP. |

These are research/support decisions, **not production-support claims**.

## 7. Remaining original-v1 gaps before a strict completion claim

The evidence above materially closes source ownership, client/service architecture, EtherIP data plane, EtherIP/IPsec coupling, build packaging and dependency provenance. The family must still remain below `COMPLETE-RESEARCH-v1` until the common template gates are all explicitly closed, especially:

- release/tag selection and maintenance policy at a reviewed current pin;
- security advisory / CVE review and a documented safe-version rule;
- representative upstream issue/regression review for client, virtual adapter, EtherIP/IPsec and packaging behavior;
- exact configuration persistence paths/secret-handling audit across supported platforms;
- license/NOTICE obligations for top-level and incorporated/submodule components;
- platform UI/config/import/export mapping needed by the final PVNetwork adapter contract;
- a final per-entry gate audit against `research/PROTOCOL_RESEARCH_TEMPLATE.md`.

Therefore entries 013–015 remain **IN-RESEARCH** after this note.