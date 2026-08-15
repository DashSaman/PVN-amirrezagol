# 054 — SSH Tunnel — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / SSH SECURE FORWARDING COMPOSITION / OPENSSH-CANONICAL / NOT IMPLEMENTED / NOT CERTIFIED`**

## Scope and authoritative standards

Entry 054 covers SSH transport/authentication/connection channels as used for TCP forwarding. It keeps local, remote and dynamic forwarding distinct and does not merge the SOCKS layer used by dynamic forwarding into SSH itself.

Primary standards:

- RFC 4251 — SSH Protocol Architecture
- RFC 4252 — SSH Authentication Protocol
- RFC 4253 — SSH Transport Layer Protocol
- RFC 4254 — SSH Connection Protocol and TCP/IP forwarding

Current algorithm policy is implementation/release controlled; PVNetwork must not freeze historical 2006 algorithm defaults as a modern security profile.

## Pinned implementations and licenses

### OpenSSH portable — canonical client/server reference

From the completed V1 dossier:

- repository: `openssh/openssh-portable`
- reviewed master: `528055671c26962093a871bff8241a48d42dd9a0`
- reviewed tree: `377ab7f76a7ce3751aae83e48daaad172c46d9ec`
- stable tag: `V_10_5_P1` / OpenSSH 10.5p1
- release commit: `b3f7344209832eea8ece447d871ea748767c444b`
- release tree: `13213785969f767d706fe319e3668f3fb2e1c539`
- release commit date: 2026-08-11; signature verified in the V1 audit
- license: component BSD or more-permissive notices in the exact OpenSSH `LICENCE`; no GPL code claimed by that licence text

OpenSSH upstream recommends stable releases for most users and documents portable builds using autoconf/make with a C toolchain; crypto backend availability can include LibreSSL/OpenSSL/AWS-LC/BoringSSL and optional features such as zlib/FIDO depend on build inputs.

### libssh2 — embedded-library candidate

- repository: `libssh2/libssh2`
- reviewed master: `4f271a3b8ebbcf204443d456210a6d6568682f6c`
- reviewed tree: `f8f818249eb89a27c8c9781b0cf9162fcbe8602e`
- reviewed release: `1.11.1` (2024-10-16), signed release; project remains active in 2026
- BSD-3-Clause

Role: reusable embedded SSH2 library candidate if its exact algorithm/backend/channel APIs satisfy the frozen product requirements.

### Windows platform distribution reference

Microsoft's current OpenSSH Server for Windows guidance covers Windows Server 2019/2022/2025 and Windows 10/11. Windows Server 2025 installs OpenSSH by default; older supported versions can add OpenSSH Client/Server as Windows capabilities. `sshd` is a Windows service and setup creates/enables the `OpenSSH-Server-In-TCP` firewall rule for TCP 22. This is a platform deployment reference, not a claim that the separate `PowerShell/Win32-OpenSSH` repository is the product's reusable source baseline.

## Server implementations / installers / install matrix

### Unix-like systems

OpenSSH portable is the canonical source/server reference. Meaningful deployment paths are:

- distribution OpenSSH packages managed by the distro package/service manager;
- canonical portable source build for platforms needing upstream build control;
- `sshd` service configuration with host keys, authorized users/keys and forwarding policy.

Linux/BSD/macOS server packaging details vary by platform/distribution and must be frozen at implementation time; the SSH protocol does not mandate a package manager or container runtime.

### Windows

Official Microsoft platform path:

- Windows Server 2025: OpenSSH present by default, `sshd` can be enabled in Server Manager;
- Server 2019/2022 and supported Windows clients: Optional Feature / Windows Capability installation;
- service: `sshd`;
- inbound firewall rule: `OpenSSH-Server-In-TCP`, normally TCP 22.

Container/Kubernetes/Helm are not required by SSH and no unpinned community installer is promoted merely to fill a matrix cell. A containerized SSH server is possible but its image, base OS, privileges, host keys and exposed listener must be independently pinned/reviewed.

## Server administration / UI map

SSH defines no canonical web panel. Evidence-backed N/A applies to a protocol-level GUI.

Canonical OpenSSH administration surfaces are configuration/service oriented:

- `sshd_config` / platform configuration;
- listen addresses/ports;
- host keys and algorithms;
- user authentication policy;
- authorized keys and per-key restrictions;
- forwarding controls such as `AllowTcpForwarding`, `PermitOpen`, `PermitListen`, `GatewayPorts` or equivalent current controls;
- logs/audit/service status;
- host firewall/network configuration;
- service start/stop/update through the platform service/package manager.

Windows additionally exposes Server Manager/Services/PowerShell capability/service surfaces. These are OS administration UIs, not an SSH protocol GUI.

## Client install / UI map

### OpenSSH client

Desktop/server ecosystems commonly provide the `ssh` CLI and config files through the OS/package manager. The canonical behavior surface includes:

- server/port/user;
- identity/private key/agent selection;
- known-host/host-key trust;
- local (`-L`), remote (`-R`) and dynamic (`-D`) forwarding;
- jump/proxy hops where configured;
- keepalive/timeouts and diagnostics supported by the selected release.

There is no canonical OpenSSH consumer GUI, so V2 client-UI requirements are satisfied by evidence-backed N/A plus a map of the CLI/config fields that PVNetwork must expose safely. Third-party GUI clients remain behavioral references unless their exact source/license is independently pinned.

### PVNetwork profile surface

Keep typed and distinct:

- SSH server endpoint/port and username;
- host-key trust state;
- auth method and protected credential/key/agent reference;
- forwarding mode: local / remote / dynamic;
- listener bind/port and target endpoint fields by mode;
- dynamic SOCKS version/capability as a composed SOCKS concern;
- connection/channel/log status.

No mobile/TV Store package or background entitlement is claimed as a research requirement.

## Cryptography / trust model

SSH transport provides encrypted and integrity-protected transport plus server host authentication; user authentication is layered above transport; connection channels/forwarding are layered above authentication. Exact KEX, host-key, cipher and MAC availability/defaults are selected by the maintained OpenSSH/libssh2 release and crypto backend.

Security requirements:

- do not silently auto-accept changed host keys;
- private keys/passphrases/passwords are protected-store material;
- agent forwarding is privileged and should not be enabled by default;
- obsolete algorithms must not be re-enabled merely for compatibility without explicit policy;
- production pin requires current algorithm/advisory review.

## Data path / wire flow

```text
TCP connect to SSH server
  -> SSH version exchange
  -> key exchange + server host-key authentication
  -> encrypted SSH transport
  -> user authentication
  -> SSH connection protocol
       -> session / forwarding channels
       -> local forwarding: local listener -> direct-tcpip channel -> server-side target
       -> remote forwarding: server listener -> forwarded-tcpip channel -> client-side target
       -> dynamic forwarding: local SOCKS request -> SSH forwarding channel -> target
```

Dynamic forwarding composes SOCKS4/SOCKS5 request parsing with SSH channels. It does not imply SOCKS5 UDP ASSOCIATE or generic UDP tunneling.

## Ports / handshake / transport

SSH normally runs over TCP; TCP 22 is the conventional/default server port, but it is configurable. Forwarding channels are multiplexed inside the authenticated SSH connection. Failure taxonomy should separate:

- network reachability;
- version/KEX/algorithm negotiation;
- host-key trust;
- user authentication;
- forwarding permission/listener bind;
- channel-open/target DNS/connect;
- keepalive/reconnect/session closure.

## Deployment topologies

- local port forward to a server-reachable target;
- remote port forward exposing a server-side listener toward a client-reachable target;
- dynamic local SOCKS proxy over SSH;
- jump/bastion-host composition with host-key and credential trust per hop;
- administrative SSH server with restricted forwarding policy;
- multiple independent tunnels over separate sessions or multiplexed channels where supported.

Remote-listener exposure is materially different from loopback-only local forwarding and must not be hidden behind one ambiguous "SSH Tunnel" switch.

## Supply-chain / lifecycle

- prefer stable, signed OpenSSH release provenance rather than arbitrary master builds for production;
- preserve the complete OpenSSH component licence notices;
- freeze exact libssh2 release/backend/dependencies/SBOM before embedding;
- platform packages/Windows capabilities own service update/uninstall/rollback behavior;
- source builds require explicit dependency/version ownership;
- unpinned third-party installers/containers are not approved defaults;
- host private keys, user keys and known-host state must survive upgrades according to explicit ownership/backup policy, not by accident.

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence / conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | OpenSSH `sshd` canonical; OS distributions/Windows capability are deployment ecosystems; libssh2 is client/library, not a server substitute. |
| 2 | Official/community installer/deployment projects reviewed | PASS | Canonical portable source, distro package/service and Microsoft Windows Capability paths mapped; unpinned third-party images/scripts deliberately not trusted. |
| 3 | Server OS/container/orchestration install matrix | PASS | Unix-like package/source service and supported Windows Server/Windows capability paths mapped; container/orchestration treated as optional deployment-specific work, not invented protocol requirements. |
| 4 | Server panel/UI/menu maps | PASS / N/A | No protocol GUI; `sshd_config`, service/log/firewall and Windows Server Manager/Services/PowerShell administration surfaces mapped. |
| 5 | Client install matrix | PASS | OS/package OpenSSH client plus libssh2 embedded-library path mapped across relevant desktop/server platforms; mobile/Store packaging correctly remains implementation-specific. |
| 6 | Major client UI/menu maps | PASS / N/A | OpenSSH canonical CLI/config surface and required PVNetwork typed profile fields mapped; no fake canonical GUI. |
| 7 | Cryptographic design | PASS | SSH transport/host-key/KEX/user-auth/channel layering and maintained-algorithm policy documented; no custom crypto claimed. |
| 8 | Data path/wire flow | PASS | Version/KEX/host-key/auth/connection/channel flow and local/remote/dynamic forwarding paths documented. |
| 9 | Ports/transports/handshake | PASS | TCP, conventional configurable port 22, handshake layers, forwarding channel multiplexing and failure taxonomy mapped. |
| 10 | Deployment topologies | PASS | Local, remote, dynamic, bastion/jump and restricted-forwarding topologies mapped with exposure boundaries. |
| 11 | Source/license/activity pins | PASS | Exact OpenSSH master/release/tree pins and component licence boundary plus exact libssh2 pin/release/BSD-3-Clause retained. |
| 12 | Installer security/supply-chain risks | PASS | Stable signed release preference, package ownership, unpinned installer/container distrust, host-key/key persistence and service/firewall effects explicit. |
| 13 | Upgrade/uninstall/rollback | PASS | Package/service/Windows Capability/source-build ownership mapped; keys/trust/config persistence must be deliberate. |
| 14 | Differences/uncertainties explicit | PASS | Local/remote/dynamic, SOCKS composition, jump hops, agent forwarding, TCP-only standard forwarding and algorithm/backend/platform uncertainties are explicit. |
| 15 | `REFERENCE_INDEX.md` complete | PASS | Added beside this audit. |
| 16 | Latest AGENTS handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-15_SSH_TUNNEL_V2_COMPLETE.md` advances to entry 055 Tor SOCKS. |

## Final decision

All applicable 16 V2 research/reference gates are evidence-backed. Entry 054 may be promoted to **`COMPLETE-REFERENCE-v2`** while remaining not implemented, not device/Store certified and not production verified.
