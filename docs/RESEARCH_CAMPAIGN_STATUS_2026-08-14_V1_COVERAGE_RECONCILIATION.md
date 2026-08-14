# PVNetwork Original 93-Entry Campaign — v1 Coverage Reconciliation

Date: 2026-08-14

Repository: `DashSaman/PVN-amirrezagol`

State: **`V1-COVERAGE-HANDOFF-READY / NOT IMPLEMENTED`**

This is a research coverage milestone. It does **not** mean all entries are implemented, E2E tested, device certified, secure for production, or Store approved.

## Reconciliation method

The actual repository tree, dated family handoffs/status files, shared upstream dossiers and numbered `V1_RESEARCH.md`/synchronized entry files were used instead of relying solely on the older `research/RESEARCH_COMPLETENESS.md`, which may lag because some large connector rewrites were historically rejected.

## Family/range coverage

### 001

OpenVPN — shared family and numbered entry `V1-HANDOFF-READY`.

### 002–003

WireGuard / AmneziaWG — shared family and numbered entries `V1-HANDOFF-READY`.

### 004–008

IKEv2/IKEv1/ESP/AH/L2TP-IPsec — strongSwan/native-OS family decisions and separate numbered files.

### 009–012

L2TPv3/L2TPv3-IPsec/SSTP/PPTP — classic/legacy tunnel family with separate numbered files.

### 013–015

SoftEther/EtherIP/EtherIP-IPsec — shared family plus separate numbered evidence.

### 016–024

OpenConnect/enterprise compatibility — shared OpenConnect family and numbered evidence. Entry 016 now has a separate `V1_RESEARCH.md` after earlier README update blockers.

### 025–036

Vendor enterprise group — Check Point, SonicWall, Sophos, WatchGuard, Aruba, Citrix, Barracuda, Juniper Secure Connect each have separate v1 decisions.

### 037–040

Xray core protocols — VLESS, VMess, Trojan, Shadowsocks synchronized under Xray v1 closure.

### 041

Shadowsocks 2022 — separate v1 decision, `shadowsocks-rust`/approved-core direction.

### 042–043

Hysteria / Hysteria2 — shared family plus numbered decisions.

### 044–055

TUIC, AnyTLS, ShadowTLS, NaiveProxy, Snell, SOCKS4/4a/5, HTTP, CONNECT, SSH, Tor — modern proxy family plus separate numbered decisions.

### 056–062

Tailscale, ZeroTier, NetBird, Netmaker, Nebula, Tinc, innernet — mesh/overlay family plus separate numbered decisions.

### 063–073

GRE, GRE-IPsec, IPIP, IPIP-IPsec, VTI, XFRM, VXLAN, VXLAN-IPsec, DMVPN, FlexVPN, GETVPN — router/site-to-site family plus separate numbered decisions.

### 074–076

REALITY / XTLS / XTLS Vision — synchronized under Xray v1 closure.

### 077–093

TLS/uTLS/Cloak/TLS fragmentation/TCP/UDP/QUIC/WebSocket/HTTP1/HTTP2/HTTP3/gRPC/mKCP/KCP/XHTTP/RAW/DTLS — transport/security classification established. Xray-specific entries were already synchronized; remaining entries now have separate v1 files/shared decisions.

## What v1 means

For each entry, the repository now has enough evidence to preserve the original research decision and move to the next layer without restarting from chat memory.

Typical v1 evidence includes, depending on applicability:

- candidate/upstream client/core/server references;
- architecture classification;
- license/reuse direction;
- platform direction;
- source/client lessons;
- security/legacy classification;
- engine-minimization decision;
- explicit residual gaps.

## What v1 does NOT mean

It does not prove:

- implementation;
- compiled PVNetwork integration;
- E2E connection success;
- exact production core pin/SBOM;
- real-device performance;
- server-version certification;
- Store approval;
- exhaustive menus/screenshots;
- exhaustive server installer/panel review;
- complete cryptography/wire-flow reference.

Those are mandatory later gates.

## Mandatory next phase

Activate **`COMPLETE-REFERENCE-v2`** defined by:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

The v2 phase expands every applicable entry with separate evidence for:

- server implementations/forks/projects;
- server installers/deployment projects/panels;
- server install matrix across OS/container/orchestration targets;
- server UI/control-panel menus;
- client install matrix across relevant OS/architectures/package formats;
- every major client's UI/menu map;
- cryptography;
- data path/wire flow;
- ports/transports/handshake;
- deployment topologies;
- source/license/activity/supply-chain/security review;
- exact reference index and next action.

## v2 priority order

Start with highest-value families first while maintaining all 93 entries:

1. OpenVPN
2. WireGuard / AmneziaWG
3. IKE/IPsec / L2TP
4. Xray / VLESS / VMess / Trojan / Reality / XHTTP
5. Hysteria2 / TUIC / AnyTLS / ShadowTLS / Naive / Shadowsocks
6. OpenConnect enterprise families
7. SoftEther
8. vendor enterprise groups
9. mesh/overlay ecosystems
10. router/site-to-site and low-priority legacy/transport-only entries.

## Exact next action

Begin OpenVPN `COMPLETE-REFERENCE-v2` immediately. Create the v2-required server/client/install/menu/crypto/wire/topology files under the OpenVPN protocol/shared research area, using current primary sources and versioned references. Then checkpoint and continue WireGuard/AWG v2 without owner prompting.
