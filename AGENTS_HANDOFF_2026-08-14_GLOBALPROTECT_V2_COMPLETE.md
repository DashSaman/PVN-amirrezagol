# PVNetwork Agent Handoff — GlobalProtect V2 Complete

Date: 2026-08-14 UTC

Authoritative phase: `COMPLETE-REFERENCE-v2`; V1 remains 93/93.

## Completed in this checkpoint

- 018 — Palo Alto GlobalProtect: `COMPLETE-REFERENCE-v2`

A full dedicated V2 dossier was built under `research/protocols/018-globalprotect/reference-v2/` and reconciled against all exact 16 gates.

Key boundaries:

- PAN-OS GlobalProtect Portal/Gateway and Prisma Access are proprietary Palo Alto headends; no public source/build or open-source reuse is invented.
- PAN-OS 12.1 administration help is the selected current server-management reference baseline.
- GlobalProtect App 6.3 official compatibility/feature matrices are used for endpoint coverage. 6.3.3-h11 (6.3.3-c1016) is retained as a selected Windows/macOS `Preferred` research baseline while newer 6.3.3-h13 maintenance documentation demonstrates continuing activity; exact preferred/latest release must be refreshed at implementation freeze.
- GlobalProtect SSL/TLS control, SSL VPN data tunnel and IPsec/ESP data tunnel are separate. The vendor control plane negotiates the IPsec tunnel; this is not generalized into an IKEv2/UDP-500/4500 requirement.
- OpenConnect v9.21 remains separately pinned at `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1, and can be a compatible client-engine candidate through public API. It is not assumed to provide proprietary HIP/posture/SAML/mobile/version parity.
- licensing/subscription, official package distribution, lifecycle and third-party OSS disclosures are recorded without misclassifying proprietary Palo Alto software as open source.
- runtime/device/Store/interoperability/vendor-certification receipts remain later certification evidence, not hidden V2 gates.

## Exact continuation state

V2: **18/93**.

Next unfinished V2 entry: **019 — Fortinet FortiGate SSL VPN**.

Exact next action: build/reconcile all 16 V2 gates for FortiGate SSL VPN, keep FortiOS/FortiClient proprietary boundaries separate from OpenConnect Fortinet-compatible mode, pin current vendor/public references, fill only real gaps, then continue entry 020 Pulse Secure.
