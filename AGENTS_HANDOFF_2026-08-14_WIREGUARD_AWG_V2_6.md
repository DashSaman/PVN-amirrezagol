# AGENTS Handoff — 2026-08-14 — WireGuard / AmneziaWG v2 slice 6

Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`  
Entries: 002 WireGuard, 003 AmneziaWG  
State: **IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED**

## Durable evidence added

### wg-easy v15.3.0 current-stable source audit

New file: `research/upstreams/wireguard-family/reference-v2/WGEASY_V15_3_SECURITY_AUDIT.md`.

The current stable tag is now pinned to full source commit `2dc8ba779216929c10c1998341d36963fe0eca7a` and tree `ad7f098478c283332f585bf68ae90269322873be`. The audit establishes that current-stable `definePermissionEventHandler` resolves the current user and enforces permission checks, while `defineSetupEventHandler` independently enforces the server-side setup state/order machine. Setup step 2 creates the first user only in the valid state and advances setup state afterward.

Current-stable session evidence was rechecked rather than inherited from v15.0.0: cookie `secure` still depends on `INSECURE`, remember-me still uses database `sessionTimeout`, the TODO for independent server-side session expiration remains, HTTP Basic remains an alternate API path with the username-enumeration timing TODO, and logout explicitly clears the current session. Global revocation remains unproven.

No explicit application-level CSRF token, Origin/Referer allow-list, or trusted-proxy allow-list was established in the audited handler/session/setup/config source set. This is recorded as `NOT ESTABLISHED BY AUDITED SOURCE`, not as a claim that the entire framework stack lacks implicit controls.

Research commit: `a89d3744f875eae2ccb774f746fd46a024c7e752`.

### wg-easy v15.3 OCI registry pin

New file: `research/upstreams/wireguard-family/reference-v2/WGEASY_V15_3_OCI_PIN.md`.

The GitHub Container Registry package page currently maps tags `15.3`, `15.3.0`, and `15` to digest `sha256:b6ad56f6be5c879ce9ea9a7e577a05c95cab9681eb74d8a96563fd59efc818e6`. This closes registry-side immutable digest identification for the observed v15.3 image, but does not substitute for target-host pull/inspect, architecture manifest, SBOM/provenance, install/update/rollback, or post-start receipts.

Research commit: `e3c8c9829b3a285e7f885a2649539c26bd77ff76`.

## Strict checks

- entries 002/003 COMPLETE-REFERENCE-v2: **NO**.
- current-stable source pin: **YES**.
- current-stable setup state/order guard evidenced: **YES**.
- current-session logout clear evidenced: **YES**.
- global session revocation evidenced: **NO**.
- explicit application CSRF/origin/trusted-proxy control evidenced: **NO / NOT ESTABLISHED**.
- registry-side immutable v15.3 digest identified: **YES**.
- deployed-image execution receipt: **NO**.

## External blockers retained

No representative container host, Apple device/App Store binary environment, or AWG multi-generation peer matrix is available through this run. Therefore install/update/rollback, real-device Apple, and AWG generation interoperability receipts remain externally blocked and must not be fabricated.

## Exact next action

Continue the same work unit. Enumerate every state-changing wg-easy v15.3.0 API handler and classify its guard (`permission`, `setup-state`, intentionally public, or unresolved), then inspect framework/deployment evidence for Origin/Host/forwarded-header behavior without assuming implicit protection. In parallel, inventory Apple entitlements/app groups and shipped Store/build revision mapping. Keep entries 002/003 PENDING until line-by-line FULL_PROTOCOL_REFERENCE_CONTRACT reconciliation passes.
