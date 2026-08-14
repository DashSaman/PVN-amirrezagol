# AGENTS Handoff — 2026-08-14 — WireGuard / AmneziaWG v2 slice 4

Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`  
Entries: 002 WireGuard, 003 AmneziaWG  
State: **IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED**

## Durable evidence added

### wg-easy v15 authentication/session

Pinned upstream `wg-easy v15.0.0` to commit `f79b0fd025f5ee3b8359de042523d867cb0f5c3a` and audited source paths for login, password storage and session construction.

Evidence now establishes for this pin:
- password hashing/verification uses Node `argon2`, not inherited v14 bcrypt/PASSWORD_HASH recipes;
- optional TOTP is implemented with 6 digits, 30-second period, SHA-1 and validation window 1;
- browser login persists `userId` in the wg-easy session;
- session secret comes from database general config; remember-me maps cookie maxAge to database sessionTimeout;
- source has a TODO for session expiration, so independent server-side idle/absolute expiry is not claimed;
- HTTP Basic authentication is also accepted by the API path and source notes a username-enumeration timing TODO;
- `:15` remains a moving major image selector; production needs exact OCI digest plus release/commit.

### Official WireGuard Apple import path

Pinned exact source functions at `WireGuard/wireguard-apple@2fec12a...`:
- iOS add menu exposes file import and QR scan;
- document picker accepts WireGuard config, text and ZIP;
- `TunnelImporter` expands ZIP or parses text with `TunnelConfiguration(fromWgQuickConfig:called:)`;
- QR scan returns parsed `TunnelConfiguration` to `tunnelsManager.add`;
- disposable externally opened files route through the same importer.

### Standalone AmneziaWG Apple current pin

Pinned current upstream commit `fe2e2d7ebb835a0a2ad1d8de75094a6de134da4d` (2026-08-14, GitHub verified). At that immutable pin the fork retains explicit file and QR import UI paths and the TunnelImporter pipeline. The separate `amneziawg://` feature remains unimplemented/requested and is not claimed.

## Research commits

- `0134acfccee555b07097313366725d3e467a69d8` — pin wg-easy v15 auth/session implementation.
- `6a597748cf81d34ee520874cabb55b070aeea8b9` — pin official WireGuard Apple import source paths.
- `f7505a8229194636ebca9b008f5f6916566ca7d4` — pin current standalone AmneziaWG Apple import source.

## Strict checks

- entries 002/003 COMPLETE-REFERENCE-v2: **NO**.
- file presence treated as completion: **NO**.
- v14 authentication recipe inherited into v15: **NO**.
- standalone AWG custom URL scheme claimed: **NO**.
- QR input confused with QR export: **NO**.

## Execution blocker

No representative WireGuard/AWG server/Apple-device execution infrastructure is available through this run. Therefore install/update/rollback and AWG generation interoperability rows requiring execution receipts remain blocked externally and are not promoted to PASS.

## Residual gates

1. wg-easy v15 bootstrap/setup persistence, CSRF/origin/reverse-proxy trust, session invalidation and current patch-line delta audit;
2. exact OCI image digest/SBOM/provenance and install/update/rollback receipts;
3. exact WireGuard Apple and standalone AWG export/share/archive source behavior;
4. Apple entitlements/app groups, shipped binary revision mapping and real-device malformed/import/export receipts;
5. execute AWG 1.x/1.5/2.0 interop/upgrade matrix when infrastructure exists;
6. line-by-line entries 002/003 reconciliation against `FULL_PROTOCOL_REFERENCE_CONTRACT.md` before tracker promotion.

## Exact next action

Continue the same work unit. Audit wg-easy v15 setup/bootstrap and middleware security boundaries plus current stable patch-line delta; then source-pin Apple export/share behavior. If execution infrastructure remains unavailable, keep its rows explicitly blocked and continue source/dependency/advisory reconciliation. Do not mark entries 002/003 COMPLETE-REFERENCE-v2 until every applicable gate has evidence or an allowed contract disposition.
