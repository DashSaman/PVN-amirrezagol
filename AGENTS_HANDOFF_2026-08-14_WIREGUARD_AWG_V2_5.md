# AGENTS Handoff — 2026-08-14 — WireGuard / AmneziaWG v2 slice 5

Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`  
Entries: 002 WireGuard, 003 AmneziaWG  
State: **IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED**

## Durable evidence added

### wg-easy current stable reconciliation

The upstream release ledger was reconciled against the older immutable v15.0.0 source audit. As of 2026-08-14, `v15.3.0` is the latest stable release and `v15.4.0-beta.1` is prerelease. Important stable-line deltas recorded in `SERVER_INSTALLERS_AND_PROJECTS.md` include experimental AmneziaWG support and API security changes in v15.2.0, session/setup middleware changes in v15.2.1, userspace `wireguard-go` plus 2FA-reset CLI behavior in v15.2.2, and server-side Allowed-IP enforcement plus AWG 2.0 H1-H4 range support in v15.3.0.

Result: v15.0.0 auth/session observations remain reproducible generation evidence but are not silently inherited into the current stable baseline. Production still requires exact release + OCI digest and a current-stable source/control audit.

### Apple export/share source paths

At official `WireGuard/wireguard-apple@2fec12a6...`, iOS Settings exposes `exportZipArchive`; `exportConfigurationsAsZipFile` requires `PrivateDataConfirmation`, gathers all tunnel configurations, calls `ZipExporter.exportConfigFiles`, writes `wireguard-export.zip`, and presents `UIDocumentPickerViewController` in `.exportToService` mode.

At standalone `amnezia-vpn/amneziawg-apple@fe2e2d7e...`, the derived path is retained with `amneziawg-export.zip` and the same privacy-confirmation / ZipExporter / export-to-service architecture.

This closes the exact **all-tunnels ZIP export source path** gap for both pinned iOS trees. It does not establish QR export, per-tunnel sharing, custom-scheme export, Store binary equivalence, or real-device success.

## Research commits

- `8241561deabbd69dc13674c3e7ce57ce84cc6939` — reconcile current wg-easy stable release delta.
- `8114458055754375646a79efe925781fd4d51f46` — pin WireGuard/AWG Apple ZIP export paths.

## Strict checks

- entries 002/003 COMPLETE-REFERENCE-v2: **NO**.
- v15.0.0 treated as current stable deployment baseline: **NO**.
- prerelease v15.4 beta promoted to production baseline: **NO**.
- Apple QR export inferred from QR import: **NO**.
- Apple ZIP export treated as real-device execution receipt: **NO**.

## Execution blockers

No representative server/container registry execution environment, AWG multi-generation peer matrix, or Apple device/App Store binary environment is available through this run. Therefore immutable deployed OCI digest, install/update/rollback receipts, AWG generation interop receipts and Apple real-device import/export receipts remain blocked externally.

## Residual gates

1. audit wg-easy v15.3.0 current-stable route/auth/setup API controls, CSRF/origin/reverse-proxy trust and session invalidation;
2. exact OCI image digest/SBOM/provenance plus install/update/rollback receipts;
3. Apple entitlement/app-group inventory and shipped Store/build revision mapping;
4. Apple QR export/per-tunnel share behavior if present; do not infer it;
5. execute AWG 1.x/1.5/2.0 interoperability/upgrade matrix when infrastructure exists;
6. line-by-line entries 002/003 reconciliation against `FULL_PROTOCOL_REFERENCE_CONTRACT.md` before tracker promotion.

## Exact next action

Continue the same work unit. Audit wg-easy v15.3.0 current-stable route/auth/setup API controls including CSRF/origin/reverse-proxy/session invalidation. Then inventory Apple entitlements/app groups and shipped-version mapping. Keep execution-only rows explicitly blocked and do not mark entries 002/003 COMPLETE-REFERENCE-v2 until every applicable gate has evidence or an allowed contract disposition.
