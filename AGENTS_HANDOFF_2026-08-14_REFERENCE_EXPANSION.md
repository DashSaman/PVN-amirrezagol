# AGENTS Handoff — 2026-08-14 — Full Reference Expansion + Active OpenConnect Continuation

This is the newest mandatory continuation file for `DashSaman/PVN-amirrezagol`.

## Owner priority rule

**Do not abandon or reorder the existing research backlog.** The previously defined research work remains first priority. The new full-reference expansion becomes mandatory **after the prior research gates for an entry/family are completed**.

The owner's long-term requirement is for this repository to become a complete engineering reference for every protocol/technology from both client and server viewpoints.

Detailed second-layer contract committed at:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

Commit: `2e3ba6f23b18a3ff0a967fae400eb94f8bc91582`

## Mandatory second-layer research after previous gates

Every applicable protocol dossier must eventually contain separate evidence-backed research for:

- server implementations and major forks/projects;
- official/community server installers and deployment projects;
- server installation matrix across supported OS/distributions/containers/orchestration;
- server web-panel/control-plane menus and UI, project by project;
- client installation matrix across Windows/Android/Android TV/iOS/iPadOS/macOS/Linux and relevant architectures/packages;
- client UI/menu map, separately for every major client;
- protocol cryptography from authoritative specifications/source;
- data path and wire flow;
- ports/transports/handshake behavior;
- deployment topologies;
- server/client project source pins, licenses, maintenance and supply-chain risk;
- reference index and exact continuation state.

The new completion state is `COMPLETE-REFERENCE-v2`, which comes **after** the original `COMPLETE-RESEARCH-v1` gate and still does not mean implementation/production support.

## Server installer security-review rule

Major community installer scripts/panels must be researched because real operators use them, but popularity is not trust. Record root requirements, packages/services, firewall/routing changes, exposed management interfaces, secret/default credential behavior, update/uninstall model, privileged containers/host networking and supply-chain risks. Never recommend blind remote-script execution without source review.

## Active previous-priority work: OpenConnect / Enterprise

Do not start the 93-entry v2 expansion campaign yet. Continue the previous OpenConnect backlog first.

Already committed before this handoff:

- `research/upstreams/openconnect-family/SOURCE_PIN.md`
- `VENDOR_COMPATIBILITY_MATRIX.md`
- `LESSONS_AND_TESTS.md`
- `FRONTEND_OPENCONNECT_GUI.md` — commit `35628002c8597f4ee5d7005362e528282c55c251`
- `FRONTEND_NETWORKMANAGER.md` — commit `b185bde202684b48e3085a161d0451f6e2ddea89`
- frontend-aware shared README sync — commit `48f3bc5f991beec51566c3138c954cca0aa6ef91`
- numbered enterprise dossiers 017–024 linked to shared research; 016 Cisco README remains a connector-write blocker.

### Frontend conclusions now established

- Core capability and frontend capability are separate: libopenconnect may support a browser/SSO callback while a frontend can still fail if it does not implement the handler.
- Keep Enterprise Core Adapter, generic Auth Challenge UI and platform browser/SSO service as separate layers.
- OpenConnect GUI is a Qt/C++ standalone desktop reference and GPL-2.0-or-later application; treat as architecture/UX reference by default, separately from LGPL libopenconnect.
- NetworkManager-openconnect is a Linux integration reference with service/plugin, connection editor and auth dialog separation; pinned mirror commit `ea97564887f897a3a9bb8edf49d4a70bebae5a4a`.
- Current NetworkManager build evidence includes libopenconnect, WebKit2GTK, libsecret, GTK3/GTK4/libnma and NetworkManager integration. Its source tree includes Persian `po/fa.po`, which is terminology evidence only, not proof of correct RTL behavior.

### Dependency / LGPL stage completed

New file:

`research/upstreams/openconnect-family/DEPENDENCIES_AND_LGPL.md`

Commit: `d5031368b533d97eac5335fed101c6c46c23acfe`

Main findings:

- OpenConnect core/library is LGPL-2.1; frontend application licenses must be reviewed separately.
- Official build requirements include libxml2, zlib and one TLS backend (GnuTLS or OpenSSL), plus build tooling.
- Optional feature dependencies include PKCS#11/TPM/proxy/token-related libraries documented by upstream.
- `vpnc-script`-compatible network configuration is a separate runtime/platform dependency in the traditional OpenConnect model and must be versioned/audited or replaced with a product-owned native networking layer.
- Preferred engineering candidate for legal/platform review is public API + replaceable shared `libopenconnect` where technically/store feasible; this is not final legal advice.
- Static linking is not approved without a deliberate LGPL compliance/relinking design.
- Exact per-platform SBOM must be generated from the real selected build rather than inferred from top-level license alone.

## Current connector blockers to preserve

Do not loop on previously rejected writes:

- `016-cisco-anyconnect` README update;
- detailed OpenConnect API adapter-map file;
- several WireGuard standalone files including Windows dossier/dependency inventory;
- `003-amneziawg` README update;
- full 93-entry tracker rewrite;
- older ics-openvpn/strongSwan/some Xray writes.

Use smaller accepted files and handoff/status documents.

## Exact next action

1. Finish the **previous-priority OpenConnect work** by mapping current high-impact issues/MRs/fixes per vendor family and reviewing remaining packaging/test/source gaps.
2. Update the OpenConnect shared dossier and status/handoff with those results.
3. Continue any unfinished previous R1–R4 research families/entries before beginning the full `COMPLETE-REFERENCE-v2` expansion campaign.
4. When the previous research campaign reaches its intended gate, execute `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` entry by entry/family by family.
5. For every meaningful work unit, update or create the newest `AGENTS_HANDOFF_*.md` and make `AGENTS.md` point to it.

## No false completion

No current protocol is production-implemented or production-certified merely because research/reference files exist.