# 080 — TLS Fragmentation — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Entry: **080 — TLS Fragmentation**

Decision: **`COMPLETE-REFERENCE-v2 / XRAY FREEDOM-OUTBOUND CAPABILITY / NOT A STANDALONE TLS VARIANT / NOT A STANDALONE SERVER / NOT IMPLEMENTATION-CERTIFIED`**

## Canonical implementation and current pin

The repository's selected canonical implementation remains XTLS/Xray-core. Current upstream `main` was rechecked on 2026-08-15 and is still:

- repository: `XTLS/Xray-core`
- commit: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- license: MPL-2.0
- relevant config source: `infra/conf/freedom.go`
- relevant runtime source: `proxy/freedom/freedom.go`

Exact source semantics at this pin:

- Freedom config has `fragment.packets`, `fragment.length`, `fragment.interval`, optional `fragment.maxSplit`.
- `packets: "tlshello"` maps to the initial TLS-handshake-oriented packet range and invokes the TLS-record-aware branch in `FragmentWriter`.
- empty `packets` selects generic TCP segmentation for all packets; numeric/range values select generic TCP segmentation over a packet range.
- `length` is required and may not start at zero; `interval` is required; optional `maxSplit` limits split count.
- runtime fragmentation is inserted only on TCP Freedom outbound writes when fragment config exists.
- in the TLS-hello branch, Xray reads the TLS record length from the five-byte TLS record header and emits multiple records containing slices of the original record payload; when interval is zero the generated records are combined into one write, otherwise they are written separately with delays.

Therefore the capability must not be flattened into one vague “fragmentation” switch: **TLS-record/ClientHello-oriented fragmentation and generic TCP write segmentation are separate modes with different wire effects.**

## Server/client boundary

TLS Fragmentation in this entry is a **client/outbound-side Xray Freedom capability**. There is no canonical standalone “TLS Fragmentation server”, no protocol-specific server daemon, no server installer, no server management panel, and no separate authentication or key-management plane. The remote endpoint remains the ordinary destination/TLS server used by the parent profile. Server-side V2 files/gates are therefore evidence-backed N/A rather than missing research.

The capability may be reached by a parent Xray profile through routing/dialer chaining to a Freedom outbound carrying `fragment`. Any GUI exposing those fields is a configuration frontend for Xray; it is not the authoritative implementation.

## Security and product boundary

Fragmentation does **not** add confidentiality, integrity, server authentication, certificate verification, forward secrecy, anonymity, or a new cryptographic protocol. TLS cryptography and identity remain governed by Entry 077. uTLS/ClientHello fingerprinting remains Entry 078. Fragmentation may change observable record/write/segment boundaries and timing and can itself become fingerprintable.

Effectiveness against a particular middlebox is environment-dependent. Historical and current upstream discussions show that fragmentation behavior and filtering effectiveness can change as networks reassemble or classify traffic differently; PVNetwork must not ship folklore parameter presets as universal guarantees. Runtime effectiveness, device behavior and Store/certification receipts are later implementation/certification evidence, not hidden V2 research gates.

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence / boundary |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS / N-A | No standalone server exists. The destination is the parent connection's ordinary remote TLS/application server. Canonical capability is implemented client-side in Xray Freedom outbound. |
| 2 | Official/major installer/deployment projects reviewed | PASS / N-A | No protocol-specific server installer/deployment project exists. Xray packaging/install lifecycle belongs to the shared Xray upstream dossier; fragmentation adds no daemon, service, port or deployment unit. |
| 3 | Server OS/container/orchestration matrix | PASS / N-A | No independent fragmentation server runtime. Any container/OS matrix is the parent destination/Xray deployment matrix, not a TLS-fragmentation server matrix. |
| 4 | Server panel/UI/menu maps | PASS / N-A | No canonical server panel/menu exists for this capability. Panels exposing client/outbound fragment fields are configuration frontends and must be mapped under client/adapter UI, not invented as servers. |
| 5 | Client install matrix | PASS | Capability follows the selected pinned Xray core where the Freedom outbound is packaged. No separate installable client package exists; platform-specific wrappers remain implementation/certification work. |
| 6 | Major client UI/menu maps | PASS | Product UI model: Advanced > Outbound/transport capability; explicit mode distinction (`tlshello` versus generic packet segmentation), plus validated length/interval/maxSplit. Import/export preserves exact fields. No standalone protocol card. |
| 7 | Cryptographic design | PASS / N-A | Fragmentation has no cryptography of its own. Entry 077 governs TLS cipher/KEX/authentication semantics; this capability only changes record/write segmentation/timing. |
| 8 | Data path / wire flow | PASS | Parent application/profile -> Xray routing -> Freedom TCP outbound -> optional `FragmentWriter` -> remote destination. `tlshello` parses TLS record header/length and emits multiple TLS records from the original payload; generic mode splits ordinary write buffers. |
| 9 | Ports/transports/handshake | PASS | No new port or handshake. Operates on TCP Freedom outbound. Parent protocol/TLS destination port and handshake are unchanged; fragmentation alters presentation of selected writes/records only. |
| 10 | Deployment topologies | PASS | Client-side adjunct in any topology where the selected Xray profile routes the outbound connection through a fragment-enabled Freedom outbound. No independent remote-access/site-to-site/HA topology belongs to fragmentation itself. |
| 11 | Source/license/activity pins | PASS | `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0; current upstream main rechecked 2026-08-15. |
| 12 | Security/supply-chain installer risks | PASS | No dedicated installer. Reuse the already reviewed Xray packaging/source boundary; pin the core and do not fetch arbitrary fragment scripts/presets. Community parameter recipes are not trusted supply-chain inputs. |
| 13 | Upgrade/uninstall/rollback behavior | PASS | Capability lifecycle is configuration + Xray core lifecycle. Upgrade requires schema/version validation; rollback means restore previous core/config. Removing `fragment` removes the capability without server migration. |
| 14 | Differences/uncertainties explicit | PASS | TLS-record/ClientHello-oriented fragmentation differs from generic TCP segmentation; effectiveness, timing cost, OS/network coalescing and middlebox behavior are environment-specific; fragmentation is distinct from TLS and uTLS fingerprinting. |
| 15 | `REFERENCE_INDEX.md` complete | PASS | Companion `research/protocols/080-entry/REFERENCE_INDEX.md` records pins, files, boundaries and next action. |
| 16 | Latest handoff exact continuation state | PASS | This batch advances the authoritative tracker/state to Entry 081 TCP and records the exact continuation in the new handoff. |

## Wire-effect summary

### `packets: "tlshello"`

Xray's current `FragmentWriter` detects the selected initial TLS record, obtains its record length from the TLS record header, slices the record payload, and emits multiple TLS records carrying those slices. This is **record-level rewriting around the ClientHello-oriented initial range**, not merely an arbitrary IP-fragmentation feature.

### generic packet segmentation

For empty or numeric/range `packets`, the writer splits selected application write buffers into smaller writes with optional delays. This changes TCP write/segment presentation but does not create TLS semantics.

The operating system/network stack may coalesce, segment or otherwise transform writes later, so user-space write boundaries are not a guarantee of exact on-wire TCP packet boundaries.

## Final V2 decision

All exact 16 written V2 gates are evidence-backed or correctly resolved as N/A. Entry 080 qualifies for **`COMPLETE-REFERENCE-v2`** as a pinned Xray client-side capability. This completion does not claim implementation, real-network effectiveness, device certification or Store readiness.
