# Entries 002 / 003 — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14

Entries:

- **002 — WireGuard**
- **003 — AmneziaWG**

Purpose: reconcile the shared WireGuard/AWG dossier line-by-line against the second-layer completion gate in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` while keeping **source/reference coverage** separate from **runtime/install/Store/interoperability receipts**.

State before this reconciliation: `IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED`.

## Status vocabulary used here

- `REFERENCE-PASS`: the contract's research/reference category has traceable repository evidence.
- `SOURCE-PASS`: source-level question is resolved, but no runtime certification is implied.
- `BLOCKED_EXTERNAL`: the remaining proof requires a runtime/OS/device/Store/signing/interoperability environment not available in this agent run.
- `N/A`: evidence-backed not-applicable.

A `REFERENCE-PASS` is **not** a production or execution certification.

## 1. Server implementation/project ecosystem mapped

### 002 WireGuard — `REFERENCE-PASS`

Evidence:

- `SERVER_IMPLEMENTATIONS.md`
- `../SOURCE_REVISIONS.md`

Covers Linux kernel WireGuard, `wireguard-go`, official platform families, routing-peer/server interpretation, and management/control-plane separation.

### 003 AmneziaWG — `REFERENCE-PASS`

Evidence:

- `SERVER_IMPLEMENTATIONS.md`
- `../SOURCE_REVISIONS.md`

Covers `amneziawg-go`, official Linux kernel module, platform repositories, generation-aware engine distinctions and interop risks.

## 2. Official and major community installer/deployment projects reviewed

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `WGEASY_V15_3_SECURITY_AUDIT.md`
- `WGEASY_V15_3_OCI_PIN.md`
- `WGEASY_V15_3_REQUEST_BOUNDARY_AND_PROXY.md`
- `WGEASY_V15_3_FRAMEWORK_REQUEST_SEMANTICS.md`
- `WGEASY_V15_3_NITRO_DEPENDENCY_BOUNDARY.md`

The dossier distinguishes protocol engines from management/deployment projects and does not promote blind remote-script execution.

## 3. Server OS/container/orchestration install matrix completed

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS` with narrower official platform coverage

Evidence:

- `SERVER_INSTALL_MATRIX.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`

The matrix records official/community paths and important platform distinctions. Where a target has no evidenced official AWG path, the dossier does not invent one.

**Execution note:** representative install/uninstall receipts remain `BLOCKED_EXTERNAL`; this does not erase the source/reference matrix but prevents execution certification.

## 4. Server panel/UI/menu maps completed

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS` as management-plane reference, not canonical AWG server UI

Evidence:

- `SERVER_UI_AND_MENUS.md`
- wg-easy v15.3 source/security/request-boundary files listed above

Important distinction: WireGuard and AWG do not define a canonical web admin UI. Third-party control planes are mapped as separate products rather than protocol features.

## 5. Client install matrix completed across relevant OS targets

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS`

Evidence:

- `CLIENT_INSTALL_MATRIX.md`
- `APPLE_ENTITLEMENTS_AND_EXTENSION_BOUNDARY.md`
- `APPLE_BUILD_IDS_AND_STORE_PROVENANCE.md`
- parent platform/source dossiers and pins in `../SOURCE_REVISIONS.md`

Apple source target identity is now resolved. Public Store presence is documented separately from source-to-binary provenance.

**Execution note:** actual clean-install/update/uninstall receipts on all representative targets remain `BLOCKED_EXTERNAL`.

## 6. Major client UI/menu maps completed separately

### 002 — `REFERENCE-PASS` at source/reference level
### 003 — `REFERENCE-PASS` at source/reference level

Evidence:

- `CLIENT_UI_AND_MENUS.md`
- parent Android/Windows/Apple source dossiers
- Apple build/entitlement files

The dossier preserves client/platform differences instead of flattening all implementations into one UI.

**Residual execution proof:** current running-app screenshots, exact D-pad/accessibility behavior and every Store-distributed build's UI are runtime/product-version evidence, not source-reference facts.

## 7. Cryptographic design documented from authoritative specification/source

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS` with explicit non-equivalence rule

Evidence:

- `CRYPTOGRAPHY.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- canonical WireGuard protocol/source links recorded in `REFERENCE_INDEX.md`
- pinned AWG source in `../SOURCE_REVISIONS.md`

The AWG dossier does not claim a cryptographic primitive change merely from packet-shaping/obfuscation changes.

## 8. Data path/wire flow documented

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS`

Evidence:

- `DATA_PATH_AND_WIRE_FLOW.md`
- `DEPLOYMENT_TOPOLOGIES.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`

Kernel/userspace boundaries, routing-peer behavior, roaming/keepalive and AWG generation deltas are separated.

## 9. Ports/transports/handshake documented

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS`

Evidence:

- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `CRYPTOGRAPHY.md`

WireGuard's UDP/Noise_IK-derived handshake model is kept distinct from operational port choices; AWG packet-format/generation behavior is treated as an implementation/protocol derivative, not a generic WireGuard setting.

## 10. Deployment topologies documented

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS`

Evidence:

- `DEPLOYMENT_TOPOLOGIES.md`
- `DATA_PATH_AND_WIRE_FLOW.md`

Remote-access, routing-peer/site-to-site, hub/control-plane and split/full-tunnel patterns are covered without confusing control planes such as Tailscale/NetBird with the canonical protocol engine.

## 11. Source/license/activity pins recorded for server and client projects

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS`

Evidence:

- `../SOURCE_REVISIONS.md`
- `REFERENCE_INDEX.md`
- `SERVER_IMPLEMENTATIONS.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`

Component-specific licenses remain separate (for example MIT/Apache/GPL distinctions); no family-wide license is invented.

## 12. Security/supply-chain risks of installer projects recorded

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS` for shared management/deployment references

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `WGEASY_V15_3_SECURITY_AUDIT.md`
- `WGEASY_V15_3_OCI_PIN.md`
- `WGEASY_V15_3_STATE_CHANGING_API_GUARD_MATRIX.md`
- request/framework boundary files

The wg-easy investigation includes immutable source/image pinning, auth/API guard analysis, request/proxy trust boundaries and dependency provenance cautions.

## 13. Upgrade/uninstall/rollback behavior researched

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS` where upstream paths are evidenced

Evidence:

- `SERVER_INSTALL_MATRIX.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `CLIENT_INSTALL_MATRIX.md`

The dossier records upgrade/uninstall/rollback expectations and explicitly distinguishes documentation evidence from a successful removal/rollback receipt.

**Execution receipts:** `BLOCKED_EXTERNAL`.

## 14. Protocol/server/client differences and uncertainties explicitly listed

### 002 — `REFERENCE-PASS`
### 003 — `REFERENCE-PASS`

Evidence:

- `REFERENCE_INDEX.md`
- `CRYPTOGRAPHY.md`
- `SERVER_IMPLEMENTATIONS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `APPLE_BUILD_IDS_AND_STORE_PROVENANCE.md`

Key preserved uncertainties include AWG generation compatibility, source-to-Store provenance, runtime reverse-proxy behavior and exact cross-generation interoperability.

## 15. REFERENCE_INDEX links the complete dossier

### 002 — `SOURCE-PASS after index synchronization`
### 003 — `SOURCE-PASS after index synchronization`

Required action from this work unit: update `REFERENCE_INDEX.md` to link the new Apple provenance file, Nitro dependency boundary and this reconciliation.

## 16. Latest AGENTS handoff contains exact continuation state

### 002 — `SOURCE-PASS after checkpoint`
### 003 — `SOURCE-PASS after checkpoint`

Required action from this work unit: create the next WireGuard/AWG v2 handoff and update the `AGENTS.md` pointer / run-state ledger.

---

# Formal reference coverage result

All **16 research/reference categories** in `FULL_PROTOCOL_REFERENCE_CONTRACT.md` now have traceable source/reference evidence for entry 002 and, where applicable, entry 003. No mandatory category is silently absent.

However, the existing repository work unit deliberately adopted stricter evidence expectations than simple document presence. The following are still not proven in this environment:

1. representative live server/container install -> start -> upgrade -> rollback -> uninstall receipts;
2. representative Windows/Android/Apple clean install/update/uninstall receipts;
3. Apple archive/signing/TestFlight/App Store build-to-source correspondence;
4. real-device Network Extension behavior;
5. generated wg-easy/Nitro built-image request behavior behind an actual reverse proxy;
6. AWG multi-generation, kernel/userspace/platform interoperability matrix executed against selected pins.

These are `BLOCKED_EXTERNAL`, not missing source research.

# Promotion decision

**Do not mark 002 or 003 `COMPLETE-REFERENCE-v2` in the strict repository tracker during this work unit.**

Recommended state after checkpoint:

- entry 002: `REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / NOT IMPLEMENTED`
- entry 003: `REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / NOT IMPLEMENTED`

Because all remaining gaps require external execution/signing/device/interoperability environments, the current WireGuard/AWG work unit may be moved to `BLOCKED_EXTERNAL` and the continuous agent should immediately continue the next independent v2 family rather than idle.
