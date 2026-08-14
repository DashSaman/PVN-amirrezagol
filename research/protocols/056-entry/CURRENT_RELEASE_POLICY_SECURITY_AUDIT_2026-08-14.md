# 056 Tailscale — Current Release / Policy / Security Audit — 2026-08-14

This addendum supplements `V1_GATE_RECONCILIATION.md` with current release, policy, relay, key-management and security-bulletin evidence. Where this file is more specific or newer, it wins.

## Exact current stable release pin

Canonical repository: `tailscale/tailscale`.

Current GitHub stable release reviewed on 2026-08-14:

- tag: `v1.102.2`
- published: 2026-08-04
- annotated tag object: `6cac918179d4d673bfebe2fc74f81183ddd73fea`
- release commit: `eb67e5dcbe145d63e1128b9b4b630f8a82da101f`
- release tree: `ff6371b99d4117c03ab2315f90fd4599793f8abc`
- exact root license: BSD-3-Clause
- exact `go.mod` at the release declares Go `1.26.5` and pins the dependency graph, including Tailscale's WireGuard fork.

The annotated tag contains an SSH signature, but GitHub's API reports `verified=false` with reason `unknown_key`. Therefore PVNetwork records **signed tag / GitHub unable to verify key**, not “GitHub-verified release”. Artifact/package provenance still needs the selected platform's official package/signature/hash verification before shipping.

## Control plane versus data plane

Current official Tailscale documentation explicitly separates:

- **control plane**: coordination server, identity/authentication, device registration/authorization, public-key distribution, network maps, policy, DNS, routes, exit-node/subnet configuration and peer/NAT-traversal coordination;
- **data plane**: device-local WireGuard packet encryption/decryption and forwarding.

The control service can therefore observe/manage account, device, public-key, endpoint, policy, route and coordination metadata even though peer payload remains end-to-end encrypted. PVNetwork must not convert “WireGuard-encrypted data plane” into a claim that the provider has no metadata visibility.

The open BSD client source is not a license grant for the hosted Tailscale coordination service, its account offering, closed GUI wrappers or Tailscale branding. Client-code reuse and hosted-service integration are separate legal/product decisions.

## Connection types — current model includes Peer Relay

Current Tailscale docs (validated June 2026) identify **three** connection types:

1. direct UDP peer-to-peer;
2. DERP relayed;
3. Tailscale Peer Relay through another authorized tailnet device.

All three carry end-to-end WireGuard-encrypted peer traffic. Connection establishment normally begins through DERP coordination, then attempts direct connectivity; if direct fails, an eligible Peer Relay can be preferred before DERP remains as fallback.

This supersedes any simplified product model that only exposes `direct` versus `DERP`.

PVNetwork diagnostics/capability state should therefore be able to represent at least:

- `direct`;
- `peer-relay`;
- `derp-relay`;
- unknown/transitioning.

DERP and Peer Relay can observe relay-level metadata and traffic volume/timing but do not receive plaintext WireGuard payload. Do not claim relay use is equivalent to a direct path from a privacy/performance-metadata perspective.

## Access policy — Grants are current preferred syntax

Current official documentation states:

- Grants are Tailscale's recommended policy model for new configurations;
- Grants include all original ACL capabilities plus application-level capabilities and routing-aware features;
- legacy ACLs remain supported indefinitely but do not receive new features;
- both use deny-by-default semantics when access policy is configured.

PVNetwork must not hard-code an “ACL-only” policy editor/model. A provider adapter should treat current Grants as first-class and legacy ACLs as compatibility syntax.

## Device/key lifecycle

Current official node-key documentation separates:

- machine keys: identify the physical/client installation to the coordination service;
- node keys: tie authenticated device authorization to a user/device and participate in WireGuard peer identity; node keys can rotate.

Provisioning credentials are separately sensitive:

- auth keys can be one-off/reusable/ephemeral/pre-authorized depending intended lifecycle;
- OAuth clients can generate auth keys for automation;
- current docs warn that passing auth keys directly on command lines can leak them into shell history.

PVNetwork secret handling requirements:

1. never persist reusable auth keys/OAuth secrets/node private keys in ordinary profile JSON;
2. never log full auth keys, OAuth access tokens or private node/machine keys;
3. prefer short-lived/one-off or OAuth-generated provisioning flows over indefinitely copied reusable auth keys;
4. model logout/device removal/key expiry/rotation as identity lifecycle, not a simple connection toggle.

## DNS and routed roles

Current official docs confirm:

- MagicDNS maps stable tailnet device names and is separate from ordinary upstream DNS behavior;
- subnet routers advertise specific private routes;
- exit nodes route non-tailnet internet traffic and require explicit advertising/approval/use;
- exit-node routing and DNS can change leak/fail-close behavior;
- expired keys on connector nodes can leave routes configured but unreachable intentionally to avoid leaking traffic through an unintended path.

PVNetwork must test route withdrawal/fail-close, DNS behavior and local-network access explicitly for exit/subnet roles rather than treating them as generic WireGuard peers.

## 2026 security-bulletin floor

Tailscale's official Security Bulletins page contains multiple 2026 disclosures relevant to optional capabilities, including:

- `TS-2026-009`: Tailscale SSH username argument handling could permit root access contrary to policy; fixed in `1.98.9+`;
- `TS-2026-008`: malformed Serve/Funnel request could pin a CPU core; fixed in the current patched line;
- `TS-2026-007`: Services inbound filtering issue could expose loopback listeners; fixed in `1.98.9+`;
- `TS-2026-006`: numeric UID handling in Tailscale SSH could bypass non-root restrictions; fixed in `1.98.9+`;
- `TS-2026-005` and related SSH/Serve local privilege-boundary issues;
- `TS-2026-003`: OAuth access tokens were recorded in tailnet audit logs by the coordination service for a bounded 2026 period; the service was corrected and historical tokens had expired;
- `TS-2026-002`: client web-interface grants capability bypass, fixed in `1.98.0+`.

The reviewed stable `v1.102.2` is newer than those cited minimum patched client versions, but PVNetwork must still freeze and re-check the bulletin/advisory set at the exact production release. Optional Tailscale SSH/Serve/Funnel/Services features must not be enabled merely because base mesh connectivity is certified.

The OAuth audit-log incident is also direct evidence that control-plane/service security and client source security are separate audit surfaces.

## V1 gate effect

This addendum strengthens:

- gate 2 — exact stable release pin/provenance;
- gate 3 — exact BSD client license versus service/branding boundaries;
- gate 5 — exact Go release dependency baseline;
- gate 6/7 — current control/data/relay architecture;
- gate 9/10 — policy, identity and secret lifecycle;
- gate 12 — direct/peer-relay/DERP diagnostic states;
- gate 15 — current security bulletins and minimum patched lines;
- gate 18 — provider metadata, auth-key/OAuth and routed-role privacy/security;
- gate 19 — reuse only as a dedicated mesh/provider integration, not merely to duplicate WireGuard;
- gate 20 — production package/signature/SBOM, hosted-service API/terms, exact platform UI and optional-feature certification remain later work.

## V1 conclusion

No unresolved research gap remains that should block `COMPLETE-RESEARCH-v1`. Runtime/device/Store/provider-account interoperability remains later implementation/certification evidence under the repository contract.
