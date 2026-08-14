# 036 — Juniper Secure Connect — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT JUNIPER-CERTIFIED`**.

Decision: **`VENDOR JUNIPER REMOTE-ACCESS ADAPTER/REFERENCE / SRX-vSRX GATEWAY + PROPRIETARY FIRST-PARTY CLIENT / REUSE IKE-IPSEC COMPONENTS ONLY FOR EXACT STANDARD LAYERS / JUNIPER CONFIG-DOWNLOAD+AUTH+CERT+PROTECTED-NETWORK POLICY DISTINCT / NO GENERIC IPSEC COMPATIBILITY CLAIM / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`**.

All 20 original V1 research gates are reconciled in `V1_GATE_RECONCILIATION.md`, with current Juniper evidence consolidated in `JUNIPER_SECURE_CONNECT_CURRENT_AUDIT.md`.

Key boundaries:

- Current solution is an SRX/vSRX gateway plus the first-party Juniper Secure Connect application.
- Current official client platforms are Windows, macOS, Android and iOS/iPadOS; a Linux Secure Connect client is not inferred from generic IPsec.
- Protected networks, address pools and SRX policy define routing/split tunnel behavior.
- Authentication flows include local/external, RADIUS, EAP-MSCHAPv2 and EAP-TLS with explicit certificate/PKI requirements.
- Juniper Secure Connect downloads configuration from SRX and has product-specific orchestration despite standards-level IKE/IPsec configuration.
- No canonical public complete-source repository was identified; complete first-party code/binaries/assets are proprietary/reference-only.

Do not infer Secure Connect compatibility solely from generic IKE/IPsec support.