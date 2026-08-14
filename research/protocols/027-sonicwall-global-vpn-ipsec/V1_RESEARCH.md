# 027 — SonicWall Global VPN / IPsec — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Formal 20-gate reconciliation:

`research/protocols/027-sonicwall-global-vpn-ipsec/V1_GATE_RECONCILIATION.md`

Current audit:

`research/protocols/027-sonicwall-global-vpn-ipsec/GVC_IPSEC_CURRENT_AUDIT.md`

Decision:

**`VENDOR IPSEC INTEROPERABILITY TARGET / REUSE APPROVED IKE-IPSEC BACKEND WHERE STANDARD SEMANTICS MATCH / SONICWALL GROUPVPN CERTIFICATION REQUIRED`**

Current SonicWall public product evidence continues to position Global VPN Client as a Windows traditional IPsec remote-access client. GroupVPN on SonicOS owns client policy provisioning, XAUTH policy, virtual-adapter/DHCP behavior, split/full routing and user VPN Access authorization.

Generic IKE/IPsec support is therefore not proof of complete GVC compatibility.

For standards behavior, reuse the existing strongSwan/IKEv1 research baseline rather than implementing cryptography from scratch. Current reviewed strongSwan baseline is v6.0.7 at exact commit `5973ff8e41deef4e015e1138a2de688acedf6f75`.

GVC/SonicOS code, installer/UI/branding and private build/test systems are proprietary/reference-only and are not copied or guessed.

The exact latest downloadable GVC package version/hash/signature is deliberately retained as a later MySonicWall package-freeze requirement rather than fabricated from older public 4.10 documentation.

`COMPLETE-RESEARCH-v1` means research closure only. Exact GroupVPN vendor payload/provisioning behavior, gateway/client version matrix, real strongSwan/native interoperability, Windows driver/coexistence, RCF semantics, packet captures and production certification remain later evidence states.