# 027 — SonicWall Global VPN / IPsec — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: reference/research completion only. This does not claim official GVC replacement compatibility, live gateway certification, production support, or rights to redistribute SonicWall software.

## Evidence boundaries

Existing detailed evidence is reused from `GVC_IPSEC_CURRENT_AUDIT.md`, `V1_GATE_RECONCILIATION.md`, the completed generic IKEv1/IPsec entries, and `research/upstreams/strongswan-family/`.

### Proprietary SonicWall boundary

Entry 027 is the **Windows SonicWall Global VPN Client (GVC) / SonicOS GroupVPN remote-access IPsec** family. It is not entry 026 NetExtender, Mobile Connect, L2TP/IPsec, or generic site-to-site VPN.

Current public SonicWall evidence continues to treat GVC as an active Windows remote-access product and directs customers to install the latest released GVC from MySonicWall. Public documentation exposes the 4.10 documentation line, but the current public HTML does not provide a sufficiently authoritative exact latest package version/hash for a release freeze. Therefore this audit explicitly records:

`CURRENT-PRODUCT-LINE-ACTIVE / WINDOWS / EXACT-LATEST-INSTALLER-REQUIRES-MYSONICWALL-PACKAGE-FREEZE`

No invented 4.10.x package is used.

Canonical references include:

- https://www.sonicwall.com/products/remote-access/vpn-clients/
- https://www.sonicwall.com/support/knowledge-base/kA1VN0000000LHO0A2
- https://www.sonicwall.com/support/technical-documentation/docs/sonicos-8-0-ipsec_vpn/
- https://www.sonicwall.com/support/technical-documentation/docs/sonicos-7-1-ipsec_vpn/Content/site-to-site-vpns-groupvpn-manage.htm
- https://www.sonicwall.com/support/knowledge-base/how-can-i-configure-ipsec-client-based-vpn-for-remote-users/kA1VN0000000HnN0AU

SonicWall client, firewall firmware, UI, RCF behavior and branding are proprietary/reference-only.

### Standards engine pin

Generic IKE/IPsec semantics reuse the repository's current strongSwan pin:

- canonical upstream: `strongswan/strongswan`
- release: **6.0.7**
- annotated tag object: `de1c5e42ac35fb6d4121d9bce095806c5f7f90a7`
- exact commit: **`5973ff8e41deef4e015e1138a2de688acedf6f75`**
- license: **GPLv2** baseline from pinned `COPYING`

strongSwan is a standards/interoperability reference, not proof of SonicWall GroupVPN provisioning compatibility and not a permissive closed-source drop-in.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server is proprietary SonicOS GroupVPN on supported SonicWall firewall/virtual firewall families. Canonical client is proprietary Windows GVC. strongSwan/Libreswan are standards IKE/IPsec references only. No maintained independent SonicWall-specific GVC drop-in is promoted without evidence.

2. **Official/community installers/deployment projects — PASS.** GVC is distributed through SonicWall/MySonicWall Windows installers (`GVCSetupXX.exe` documented for 32/64-bit lines). Server deployment follows SonicWall appliance/NSv lifecycle. Generic IPsec engines are independently packaged but do not become GroupVPN installers. Docker/Helm/Kubernetes GVC servers are not invented.

3. **Server OS/container/orchestration install matrix — PASS / evidence-backed N/A.** SonicOS firewall/NSv appliances are the server boundary; arbitrary Linux distributions and generic containers are not supported GroupVPN server targets unless vendor-documented. Current SonicWall product/support material identifies TZ/NSa/NSsp/NSv families for VPN-client use. Open-source Linux IPsec server capability is generic IPsec evidence, not a SonicWall server implementation.

4. **Server panel/UI/menu maps — PASS.** GroupVPN administration maps to SonicOS VPN settings: WAN/WLAN GroupVPN policy; authentication method; IKE Phase 1 proposal; IPsec/Phase 2 proposal; XAUTH requirement and credential caching; Client settings; virtual-adapter behavior; DHCP-over-VPN/address assignment; split/full-tunnel/default-route settings; user/group VPN Access; policy export. SonicOS user/group pages separately control VPN Access. These management semantics are vendor-specific and not reduced to a generic IKE form.

5. **Client install matrix — PASS.** Current GVC product line is Windows. Public documentation/support includes Windows 10/11 operation and 32-/64-bit installers; current support KBs still address Windows 11. No macOS/Linux/iOS/Android GVC client is inferred from other SonicWall families. strongSwan platform support is separate and does not widen official GVC platform support.

6. **Major client UI/menu maps — PASS.** Official GVC workflow is mapped from vendor support: `File > New Connection`, gateway address, connection enable, PSK/XAUTH credentials, virtual adapter/address acquisition, Connected state; Connection Properties tabs include General/User Authentication/Peers/Status; `View > Options` covers global launch/warning/window behavior; `Help > Generate Report` exports client/driver/system/IP/routes/log evidence. RCF import uses `File > Import`. This is behavioral reference only; SonicWall visual assets are not reusable.

7. **Cryptographic design/security boundary — PASS.** Standards core is IKEv1/IPsec/ESP with XAUTH and NAT traversal where configured; proposals are gateway policy. SonicWall GroupVPN adds vendor policy/provisioning semantics beyond standards. Simple Client Provisioning can use Aggressive Mode with a default key according to vendor docs; this is a compatibility/security characteristic, not a recommended PVNetwork default. Certificate-based variants exist. No proprietary payload cryptography is fabricated; strongSwan is reused only for standards-defined IKE/IPsec pieces.

8. **Data path/wire flow — PASS.** Reference state sequence is: GVC policy/profile acquisition -> IKE Phase 1 -> XAUTH/certificate/user authentication where configured -> GroupVPN client policy/configuration -> IKE Quick Mode/IPsec SA -> virtual adapter/DHCP-over-VPN address acquisition -> effective routes -> user/group VPN Access authorization -> application traffic. A tunnel can pass IKE phases and still fail at `Acquiring IP` or authorization, so Connected/IKE state is not equated with usable data path.

9. **Ports/transports/handshake — PASS.** Reuse generic standards evidence: IKE commonly UDP 500; NAT-T commonly UDP 4500; ESP when not UDP-encapsulated. Handshake stages are IKEv1 Phase 1, optional XAUTH/provisioning/authentication, Quick Mode/IPsec SA, then address/route/policy setup. SonicWall-specific GroupVPN provisioning payloads remain proprietary/uncertified and are not inferred from generic strongSwan code.

10. **Deployment topologies — PASS.** Evidence-backed topology is remote Windows GVC -> SonicWall Internet-facing firewall/NSv GroupVPN -> internal networks, with split-tunnel or gateway-default/full-tunnel policy, DHCP-over-VPN/virtual adapter, and user/group VPN Access. GroupVPN can coexist with site-to-site policy on the gateway but entry 027 remains client-to-gateway remote access. Hub/remote-network reachability through additional VPNs is policy/topology specific and not blanket-enabled.

11. **Source/release/license/activity pins — PASS.** SonicWall GVC/SonicOS are proprietary. Current product activity is evidenced by 2026 GVC support material, but the exact latest GVC installer remains behind MySonicWall and is intentionally **not fabricated**; an implementation freeze must record its exact package/hash/signature. Standards candidate strongSwan is pinned to 6.0.7 commit `5973ff8e41deef4e015e1138a2de688acedf6f75`, GPLv2. The two source/license boundaries remain distinct.

12. **Security/supply-chain risks — PASS.** Exact vendor installer version/hash/signature matters; old GVC installers have documented DLL search-order/security history. Use official MySonicWall delivery and current vendor advisories. Windows virtual-driver/DNE cleanup and coexistence with other IPsec clients are installation/security risks. PSK, XAUTH credentials, certificates/private keys, RCF protection password and transient SA keys are separate secret classes. strongSwan 6.0.7 is the repo's current reviewed security floor, not a security certification.

13. **Upgrade/uninstall/rollback — PASS.** GVC install/uninstall is documented through the Windows installer and may require GVC Cleaner/DNE cleanup for stale virtual-adapter bindings. Third-party IPsec coexistence can cause runtime/install conflicts. SonicOS firmware lifecycle is separate from GVC package lifecycle. Future package freeze must support clean rollback of both client package and driver state without assuming an arbitrary older 4.10 package is safe.

14. **Differences/uncertainties — PASS.** Generic IKEv1/XAUTH/ESP compatibility is not equivalent to GroupVPN compatibility. Vendor-specific policy provisioning, RCF semantics, virtual-adapter/DHCP behavior, exact certificate/XAUTH/RADIUS/LDAP permutations and current installer package identity remain bounded. Exact GroupVPN wire payloads and live strongSwan-vs-GVC interoperability remain certification tasks, not hidden reference gates.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `README.md`, `GVC_IPSEC_CURRENT_AUDIT.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, this audit, completed entries 005/006 and `research/upstreams/strongswan-family/`. Reuse decision: `VENDOR IPSEC INTEROPERABILITY TARGET / REUSE AUDITED IPSEC BACKEND FOR STANDARD SEMANTICS / DO NOT CLAIM GVC POLICY COMPATIBILITY WITHOUT PROOF`. SonicWall code/assets remain proprietary reference-only; strongSwan reuse requires GPL-compatible architecture/legal review.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 027 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 27/93 and continue at **028 — Sophos SSL VPN**. Runtime/device/Store/live-interoperability evidence is not introduced as an unstated gate.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by proprietary/current-package uncertainty. The inability to publicly pin the current MySonicWall installer is recorded honestly and does not create a false source pin; any future implementation/package freeze must resolve it before shipping.

Decision: **COMPLETE-REFERENCE-v2**.
