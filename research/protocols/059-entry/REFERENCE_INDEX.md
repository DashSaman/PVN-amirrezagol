# 059 — Netmaker — Reference Index

Status after review: **COMPLETE-RESEARCH-v1 + COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED / NOT CERTIFIED**

## Repository evidence

- `research/protocols/059-entry/V1_GATE_RECONCILIATION.md`
- `research/protocols/059-entry/CURRENT_RELEASE_LICENSE_AUDIT_2026-08-14.md`
- `research/protocols/059-entry/REFERENCE_V2_AUDIT.md`

## Canonical pins

### Netmaker server / control plane
- repository: `gravitl/netmaker`
- release: `v1.6.0`
- published: 2026-06-12
- tag commit: `5f20416e13c307696605551459b116428b4053d3`
- release tree: `13e70d47ebbc9fa32de217dfe04274356c9ed1b5`
- release: https://github.com/gravitl/netmaker/releases/tag/v1.6.0
- source: https://github.com/gravitl/netmaker/tree/v1.6.0

### Netclient
- repository: `gravitl/netclient`
- stable release family: `v1.6.0`
- release artifacts: Linux/macOS/Windows with multiple architectures and published SHA-256 digests
- source license at v1.6.0: Apache-2.0

## Exact license boundary

- Netmaker normal/community paths: Apache-2.0.
- `pro/`: `pro/PRO_LICENSE`; production use requires valid Enterprise rights and must not be treated as Apache/open-source.
- incorporated third-party code retains original licenses.
- hosted/SaaS and Enterprise terms are separate from source-code licenses.
- Netclient root license: Apache-2.0.

## First-party reference set

- README / self-host baseline: https://github.com/gravitl/netmaker/blob/v1.6.0/README.md
- release: https://github.com/gravitl/netmaker/releases/tag/v1.6.0
- release-pinned quick installer: https://github.com/gravitl/netmaker/blob/v1.6.0/scripts/nm-quick.sh
- quick install lifecycle: https://docs.netmaker.io/docs/server-installation/quick-install
- UI reference: https://docs.netmaker.io/docs/references/user-interface
- Gateways: https://docs.netmaker.io/docs/features/gateways
- remote-access gateways/clients: https://docs.netmaker.io/docs/features/remote-access-gateways-and-clients
- traffic-flow concepts: https://docs.netmaker.io/docs/netmaker-network-setup-concepts/3-configure-traffic-flow
- DNS: https://docs.netmaker.io/docs/features/dns
- nmctl: https://docs.netmaker.io/docs/references/nmctl

## Architecture summary

`Admin UI / nmctl / API -> Netmaker management/auth/DB/network/DNS/ACL -> Netclient managed hosts and Gateway roles -> WireGuard data plane`

Remote-access devices may use generated WireGuard configs/QR and connect through a Gateway. The management/control plane and gateway forwarding roles remain distinct from WireGuard peer cryptography.

## Deployment / UI boundaries

- Canonical quick-start server: Ubuntu 24.04 VM with static public IP, wildcard DNS recommended, 443/51821 TCP+UDP exposure documented.
- First-party Docker/Compose/Kubernetes assets exist.
- Managed Netclient: Linux/macOS/Windows first-party release evidence.
- Remote Access Clients: unmanaged WireGuard-capable devices, including phones/laptops/desktops/routers; generated config/QR workflow.
- Admin UI domains: Networks, Devices/Nodes, Gateways/Remote Access, Egress, DNS, ACL/policies, Users/Groups/Invites/Pending Users, graph/status and settings; Pro/Enterprise-only features remain edition-scoped.
- nmctl is an administrator API/CLI surface, not a consumer endpoint UI.

## Security / supply-chain notes

The first-party quick script runs as root and downloads/install-configures tooling and Netclient. Production should pin and source-review exact installer revisions rather than execute moving `master`. Protect WireGuard private keys/configs, master/API keys, enrollment/access tokens, OAuth/OIDC secrets, MQ/SMTP/metrics secrets and license/tenant credentials.

## PVNetwork reuse decision

**OPTIONAL DEDICATED NETMAKER ORCHESTRATION PROVIDER.** Reuse the existing WireGuard data-plane model but preserve Netmaker account/network/host/enrollment/gateway/DNS/ACL semantics. Apache community paths may be reuse candidates subject to dependency/NOTICE review. `pro/` and hosted/Enterprise features require separate legal/terms review.

## Exact continuation

After promotion of Entry 059, continue `COMPLETE-REFERENCE-v2` at **060 — Nebula** using its completed V1 dossier and current canonical SlackHQ Nebula source/release evidence. Apply all exact 16 gates; preserve certificate/CA/lighthouse/firewall/UDP data-plane distinctions and do not invent a server panel where Nebula is fundamentally peer/overlay infrastructure.
