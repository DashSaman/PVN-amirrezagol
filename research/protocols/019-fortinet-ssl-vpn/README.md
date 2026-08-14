# 019 — Fortinet FortiGate SSL VPN

V1 status: `COMPLETE-RESEARCH-v1`.

V2 status: `COMPLETE-REFERENCE-v2` as of 2026-08-14 UTC.

The complete V2 dossier is under `reference-v2/` and the exact 16-gate reconciliation is `V2_GATE_RECONCILIATION.md`.

FortiGate/FortiOS and FortiClient are proprietary Fortinet products and remain vendor reference/certification targets, not source-reuse candidates. OpenConnect Fortinet mode is tracked separately as an LGPL public compatible client implementation and remains experimental/partial upstream.

Current lifecycle boundary is critical: FortiOS 7.6.3 and later remove SSL VPN tunnel mode and require migration to IPsec VPN. Entry 019 therefore remains a **legacy/version-bounded SSL-VPN tunnel compatibility target** for maintained older branches such as FortiOS 7.4.x and other explicitly supported pre-7.6.3 deployments. Agentless VPN/web mode is a different product mode and must not be substituted for the removed tunnel protocol.
