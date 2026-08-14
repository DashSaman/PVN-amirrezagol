# 033 — HPE Aruba Networking VIA — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT VENDOR-CERTIFIED`**.

Decision: **`VENDOR REMOTE-ACCESS REFERENCE / HPE FIRST-PARTY CLIENT REFERENCE-ONLY / STANDARD IKE-IPSEC REUSE ONLY FOR EXACT LAYERS / ARUBA PROFILE+ROLE+AUTH+ROUTING+LIFECYCLE REMAIN DISTINCT / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`**.

All 20 original V1 research gates are reconciled in `V1_GATE_RECONCILIATION.md`, with current HPE Aruba Networking evidence consolidated in `ARUBA_VIA_CURRENT_AUDIT.md`.

Key boundaries:

- VIA is HPE Aruba Networking's remote-access VPN solution, not a generic synonym for IPsec.
- Current documentation models VIA as official clients plus Aruba VPNC/gateway/controller configuration and role-attached downloadable connection profiles.
- Current product documentation exposes IKEv2/IKEv1 and IPsec crypto maps plus a hybrid IPsec/SSL model and supported SSL fallback.
- IKEv2 client authentication documented for VIA includes user certificate, EAP-TLS and EAP-MSCHAPv2; IKEv2 PSK is explicitly not supported for VIA.
- Full/split tunnel, VPN IP pools, DNS suffixes, block-until-tunnel-up, destination rules, MTU, reconnect/session controls, logs, auto-login, upgrade and lockdown are centrally provisioned surfaces.
- The current VIA Help Center covers Windows, macOS, Linux, Android and iOS and lists release-note families through VIA 4.7.6; exact PVNetwork device/Store certification remains separate.
- No canonical public source repository for the complete first-party VIA client/gateway implementation was identified. Do not invent a source SHA or reuse right; first-party product code/binaries/assets remain proprietary/reference-only.

Do not infer VIA compatibility solely from generic IKE/IPsec support. Shared standards components may be reused only where exact semantics match; HPE-specific provisioning, authentication orchestration, policy and lifecycle require a distinct vendor adapter/reference boundary.