# 072 — Cisco FlexVPN — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: Cisco IKEv2/IPsec VPN framework/profile architecture, not a wholly separate cryptographic protocol.

Decision: **`VENDOR IKEV2/IPSEC INTEROPERABILITY TARGET / REUSE IPSEC MODEL`**.

PVNetwork should reuse its typed IKEv2/IPsec profile/security model and only claim FlexVPN when exact Cisco/server/backend combinations are tested. Generic IKEv2 support is not sufficient proof of FlexVPN interoperability.

Later v2 adds Cisco configurations/versions, server/router install/admin menus, IKEv2 extensions, crypto/wire flow and topology evidence.
