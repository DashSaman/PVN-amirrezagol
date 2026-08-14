# WireGuard / AmneziaWG — Deployment Topologies

Status: **REFERENCE / NOT IMPLEMENTED / NOT COMPLETE-REFERENCE-v2**

This file separates protocol peer topology from product/control-plane topology for entries 002 WireGuard and 003 AmneziaWG.

## Core semantic rule

WireGuard peers use the same protocol role. “Client” and “server” are deployment labels, not different WireGuard handshake roles. A hub that routes traffic for many remote peers is still a WireGuard peer with routing/NAT/firewall responsibilities around the tunnel interface.

AmneziaWG inherits this peer-oriented model but adds generation-specific traffic-shaping/obfuscation parameters. Those parameters must be compatible across the communicating endpoints for the selected AWG generation.

## Topology A — remote-access hub

```text
phone/laptop peer
      |
   UDP Internet
      |
public hub peer ---- private LAN / Internet egress
```

Operational responsibilities on the hub can include IP forwarding, firewall policy, NAT/masquerade, DNS policy and route ownership. These are deployment controls around WireGuard; they are not cryptographic handshake features.

PVNetwork implications:

- imported profile should identify endpoint, keys and AllowedIPs without pretending NAT/firewall rules are embedded protocol semantics;
- “full tunnel” versus “split tunnel” is primarily expressed through routes/AllowedIPs plus platform policy;
- DNS handling is product/platform policy and must be represented separately from cryptokey routing.

## Topology B — site-to-site

```text
LAN A -- gateway peer A ===== UDP/WireGuard ===== gateway peer B -- LAN B
```

Each gateway owns routes for the remote prefixes. The reference model must preserve overlapping-prefix validation, forwarding policy and firewall/NAT decisions independently of the tunnel key material.

## Topology C — roaming endpoint

```text
mobile peer: Wi-Fi -> cellular -> Wi-Fi
                    |
             stable peer identity
                    |
             remote WireGuard peer
```

WireGuard endpoint roaming means the most recent authenticated source endpoint can become the runtime destination for a peer. Therefore PVNetwork must distinguish configured endpoint from observed/runtime endpoint and must not persist transient roaming state as if it were user configuration.

## Topology D — mesh / peer-to-peer

Multiple peers can be configured directly with each other. WireGuard itself does not provide a discovery/control plane. Products such as mesh coordinators add identity, key distribution, policy and discovery above the WireGuard data plane. Those control planes must not be described as part of the WireGuard protocol.

PVNetwork should keep any future managed-mesh feature behind a separate control-plane abstraction rather than baking it into the WireGuard engine adapter.

## Topology E — userspace constrained platform

```text
PVNetwork product layer
   -> platform VPN/TUN API
   -> userspace WireGuard/AWG engine
   -> UDP socket
```

This is relevant where a kernel implementation is unavailable or inappropriate. The Android WireGuard project explicitly documents opportunistic kernel use with userspace fallback. Platform lifecycle, background execution and VPN permission are therefore adapter responsibilities.

## Topology F — kernel-backed Linux gateway

```text
routing/firewall
      |
Linux WireGuard/AWG interface
      |
kernel implementation
      |
UDP network
```

WireGuard is in modern Linux kernels; AmneziaWG also maintains a Linux kernel-module path. Kernel and userspace deployments have different installation, update, rollback and supply-chain surfaces even when profile semantics look similar.

## Topology G — AWG remote-access deployment

The topology can resemble WireGuard remote access, but both endpoints must agree on the AWG generation and relevant packet/header/signature parameters. Existing repository research records AWG generation/version distinctions and current regression evidence. Do not silently down-convert or discard AWG-specific parameters when importing/exporting a profile.

## Topology H — self-hosted product/control plane

The Amnezia client demonstrates a product model that can use SSH credentials to provision protocol containers on a user-controlled server, then connect using the resulting profile. This is a control/provisioning workflow layered above the tunnel protocol.

Security separation required for PVNetwork research:

- SSH/server-administration credentials are not WireGuard private keys;
- installer/root privileges are not tunnel runtime privileges;
- generated tunnel configuration should survive without retaining provisioning credentials unless explicitly required and securely stored;
- server installer/panel supply-chain evidence belongs in `SERVER_INSTALLERS_AND_PROJECTS.md`, not in protocol cryptography claims.

## Failure domains to model

| Failure | Layer | Required product behavior |
|---|---|---|
| endpoint DNS resolution fails | product/network | report resolution failure separately from handshake timeout |
| UDP path blocked | transport/network | distinguish reachability from invalid keys |
| wrong public/private key pairing | protocol/config | configuration/authentication diagnosis without leaking secrets |
| AllowedIPs conflict | routing/config | deterministic validation and conflict explanation |
| roaming network switch | runtime/platform | reconnect/rebind without rewriting canonical config |
| userspace engine dies | runtime engine | supervised restart and explicit state transition |
| kernel module unavailable | platform/backend | capability detection; only fallback when supported and policy-approved |
| AWG generation mismatch | protocol variant | reject/diagnose incompatibility rather than dropping variant parameters |
| hub forwarding/NAT missing | deployment | tunnel may handshake while routed traffic fails; diagnostics must separate these states |

## Observability model

PVNetwork diagnostics should separately expose:

1. profile validity;
2. platform VPN permission/state;
3. engine/backend startup;
4. endpoint resolution;
5. handshake recency;
6. byte counters;
7. route/AllowedIPs state;
8. DNS state;
9. egress/reachability probe where policy allows;
10. AWG generation/backend identity.

A green “connected” badge based only on interface creation is insufficient.

## Reuse/architecture decision

- keep canonical profile, provisioning/control plane, runtime endpoint state and routing/DNS policy as separate models;
- represent hub/site-to-site/mesh as deployment templates, not different cryptographic protocols;
- do not implement WireGuard cryptography in the product layer;
- select pinned platform engines behind adapters;
- preserve AWG generation-specific fields end to end.

## Residual evidence before strict v2 completion

- exact tested install/uninstall/rollback receipts for representative server targets;
- current Apple platform lifecycle/topology evidence;
- exact AWG generation interop matrix and receipts;
- server UI/control-panel mapping where a management project is selected;
- entry-specific 002/003 reconciliation against every FULL_PROTOCOL_REFERENCE_CONTRACT gate.

Entries 002/003 remain `PENDING` until all required gates are evidenced.