# PVNetwork Handoff — Mesh Entries 056–058 V2 Complete

Date: 2026-08-15

Authoritative campaign state after this batch:

- `COMPLETE-RESEARCH-v1`: **93/93**
- `COMPLETE-REFERENCE-v2`: **58/93**
- next unfinished V2 entry: **059 — Netmaker**

## Completed in this batch

### 056 — Tailscale

Evidence:

- `research/protocols/056-entry/V1_GATE_RECONCILIATION.md`
- `research/protocols/056-entry/CURRENT_RELEASE_POLICY_SECURITY_AUDIT_2026-08-14.md`
- `research/protocols/056-entry/REFERENCE_V2_AUDIT.md`
- `research/protocols/056-entry/REFERENCE_INDEX.md`

Key boundaries preserved:

- WireGuard-based peer data plane is distinct from account/device/control/network-map semantics;
- DERP is encrypted relay fallback, not a decrypting conventional VPN server;
- hosted coordination/control and some GUI wrappers are not inferred open-source from the BSD client repository;
- custom-control-server support does not make Headscale equivalent to the hosted Tailscale service.

### 057 — ZeroTier

Evidence:

- `research/protocols/057-entry/V1_GATE_RECONCILIATION.md`
- `research/protocols/057-entry/REFERENCE_V2_AUDIT.md`
- `research/protocols/057-entry/REFERENCE_INDEX.md`

Key boundaries preserved:

- ZeroTierOne node/service, standalone controller and hosted ZeroTier Central are separate roles;
- `node/`/`osdep/`/`service/` free paths are not conflated with `nonfree/` or `ext/` licensing;
- localhost service API token (`authtoken.secret`) is a privileged management secret;
- direct peer connectivity is preferred but relay/fallback behavior exists.

### 058 — NetBird

Evidence:

- `research/protocols/058-entry/V1_GATE_RECONCILIATION.md`
- `research/protocols/058-entry/REFERENCE_V2_AUDIT.md`
- `research/protocols/058-entry/REFERENCE_INDEX.md`

Key boundaries preserved:

- NetBird is not raw WireGuard; Management, Signal, Relay, identity, policy, DNS and routing are first-class provider state;
- client/general paths are BSD-3-Clause while `management/`, `signal/`, `relay/`, `combined/` are AGPL-3.0;
- Cloud, OSS self-host and Enterprise commercial self-host remain distinct modes;
- current `Networks` is preferred for new routed resources; legacy `Routes` remains a compatibility/exit-node model;
- direct and relayed paths are distinct diagnostics states.

## Exact continuation

Continue `COMPLETE-REFERENCE-v2` at **059 — Netmaker**.

Apply all exact 16 V2 gates using current canonical Netmaker source/docs and the existing V1 dossier. Map, with entry-specific evidence:

- management/server/control-plane components;
- client/remote-access components and platform packaging;
- WireGuard data-plane reuse and any ingress/egress/relay/gateway roles;
- current installer, Docker/Compose and Kubernetes deployment paths only where canonical evidence exists;
- admin/web UI and client UI boundaries;
- routing, DNS, remote-access and network-role semantics;
- exact source/release/activity pins and path-specific licenses;
- supply-chain/security, secrets, upgrade/uninstall/rollback and explicit uncertainties.

Do not treat runtime/device/Store/interoperability certification as a hidden V2 research gate. If 059 passes, immediately advance to **060 — Nebula**.
