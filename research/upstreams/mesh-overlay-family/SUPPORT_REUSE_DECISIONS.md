# Mesh / Overlay Family — PVNetwork Support / Reuse Decisions

Decision date: 2026-08-14

State: research architecture decision only. No PVNetwork implementation/certification exists.

Entries:

- 056 Tailscale
- 057 ZeroTier
- 058 NetBird
- 059 Netmaker
- 060 Nebula
- 061 Tinc
- 062 innernet

## Critical classification rule

A mesh product can include multiple layers:

1. data plane / packet tunnel;
2. peer discovery/coordination;
3. identity/authentication;
4. policy/ACLs;
5. DNS/naming;
6. relay/NAT traversal;
7. account/control plane;
8. update/device management;
9. UI/client applications.

PVNetwork must not treat an entire ecosystem as one interchangeable “VPN protocol”.

---

## 056 — Tailscale

Research classification:

**`WIREGUARD-BASED MESH ECOSYSTEM / INTEROPERATE OR OPTIONAL MODULE, NOT NEEDED FOR BASIC WIREGUARD`**

Primary source: `tailscale/tailscale`.

Reviewed root license in current research: BSD-3-Clause.

### Product decision

PVNetwork already researches direct WireGuard separately. Do not embed the whole Tailscale ecosystem merely to obtain WireGuard connectivity.

Potential value of Tailscale support is higher-level functionality:

- Tailscale network/account/device participation;
- coordination/control-plane compatibility;
- identity/ACL/tailnet semantics;
- exit-node/subnet-router concepts;
- MagicDNS/relay behavior.

If added, model as an optional ecosystem/account integration rather than a generic WireGuard profile.

### Reuse direction

Evaluate official client/library/tsnet-style components only for a concrete Tailscale integration use case and record control-plane/service dependencies separately.

---

## 057 — ZeroTier

Research classification:

**`DISTINCT OVERLAY NETWORK ECOSYSTEM / OPTIONAL INTEGRATION`**

Primary source/reference: ZeroTier One.

ZeroTier uses its own overlay/data-plane architecture rather than simply being a WireGuard profile manager.

### Product decision

If user demand exists, treat ZeroTier as a separate optional engine/ecosystem integration with its own network identity/member/control-plane concepts.

Do not attempt to translate ZeroTier networks into WireGuard profiles.

### License caution

ZeroTier source/license terms have changed across project history/components. Final reuse requires exact current file/path/license review for the selected ZeroTier component and version; do not rely on an old generic license label.

---

## 058 — NetBird

Research classification:

**`WIREGUARD-BASED MESH/CONTROL-PLANE ECOSYSTEM / CLIENT-SIDE INTEGRATION POSSIBLE WITH PATH-LEVEL LICENSE AUDIT`**

Primary source: `netbirdio/netbird`.

Previous/current repository research records a split license model: much client-side/shared code is BSD-3-Clause while several management/control-plane/server directories are AGPLv3.

### Product decision

Do not infer one license for the whole repo. If PVNetwork adds NetBird compatibility, define whether it needs:

- only a client/device integration;
- management/control-plane code;
- relay/signal components;
- self-hosted server administration.

Path-level SBOM/license review is mandatory.

---

## 059 — Netmaker

Research classification:

**`WIREGUARD NETWORK ORCHESTRATION / CONTROL-PLANE REFERENCE, NOT A REQUIRED PVNETWORK DATA-PLANE ENGINE`**

Netmaker's main value is management/orchestration of WireGuard-style networks rather than a unique consumer VPN protocol.

### Product decision

Do not add a Netmaker engine solely for WireGuard transport. Consider later integration only if PVNetwork must join/manage Netmaker-managed networks or provide administration tooling.

Control-plane/server licensing and commercial/community boundaries require exact current review before reuse.

---

## 060 — Nebula

Research classification:

**`DISTINCT CRYPTOGRAPHIC OVERLAY / OPTIONAL ADVANCED MESH ENGINE`**

Primary source/reference: Nebula project.

Nebula is not just WireGuard coordination; it defines its own overlay/identity/certificate/data-plane behavior.

### Product decision

Keep as a separate optional mesh capability if user demand justifies another engine and certificate-management UX.

Do not translate Nebula certificates/configuration into WireGuard profiles.

Final integration needs exact source/license/security/platform/mobile review.

---

## 061 — Tinc

Research classification:

**`MATURE/LEGACY MESH VPN / LOW PRIORITY COMPATIBILITY`**

Tinc is an older open-source mesh VPN ecosystem with its own daemon/configuration model.

### Product decision

Retain for reference completeness, but do not prioritize a dedicated Tinc engine unless real users require it. Current source/license/maintenance/security state must be pinned before implementation.

---

## 062 — innernet

Research classification:

**`WIREGUARD-BASED PRIVATE NETWORK MANAGER / ORCHESTRATION REFERENCE`**

innernet builds private-network management around WireGuard rather than defining a new independent data-plane protocol.

### Product decision

Basic WireGuard support does not require innernet. Consider integration only for joining/administering innernet-managed networks.

Do not duplicate its control-plane logic inside PVNetwork without a concrete feature requirement and source/license review.

---

# Product architecture recommendation

Use a separate **Mesh/Ecosystem Adapter** layer when an integration requires account/control-plane/device semantics beyond a normal connection profile.

Possible object split:

- `MeshProviderAccount`
- `MeshNetwork`
- `MeshDeviceIdentity`
- `MeshPolicy/ACL summary`
- `MeshConnectionSession`
- underlying engine/data-plane metadata.

Do not force mesh networks into the same schema as a single server/port VPN profile.

# Engine minimization

- Tailscale/NetBird/Netmaker/innernet should not be included merely to duplicate WireGuard.
- ZeroTier/Nebula/Tinc represent truly distinct overlay engines and add real maintenance/attack-surface cost.
- Only ship distinct mesh engines when user demand and platform support justify them.

# Security/privacy rule

Mesh ecosystems may involve cloud/self-hosted control planes and device identity metadata.

PVNetwork privacy UI must distinguish:

- traffic/data plane;
- coordination/control plane;
- account/device metadata;
- relays;
- DNS/name service;
- telemetry/update systems.

Do not claim “all traffic stays only between peers” without exact provider/topology evidence.

# Residual v1 gaps

- exact current pins/license/SBOM for ZeroTier/Netmaker/Nebula/Tinc/innernet;
- complete current Tailscale/NetBird component/path license maps;
- current client UI/menu/platform/Store evidence;
- issue/advisory/security/performance matrices;
- exact account/control-plane APIs and self-hosted compatibility.

Later v2 must add server/control-plane installers, deployment matrices, exhaustive menus, cryptography/wire flow and topology details for each applicable ecosystem.
