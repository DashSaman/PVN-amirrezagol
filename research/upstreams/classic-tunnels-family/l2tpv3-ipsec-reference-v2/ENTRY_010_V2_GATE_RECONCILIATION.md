# Entry 010 — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14

Entry: **010 — L2TPv3/IPsec**

Purpose: reconcile the protected pseudowire dossier against every research/reference category in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` while preserving the separation between the L2TPv3 pseudowire layer and the IKE/IPsec protection layer.

## Status vocabulary

- `REFERENCE-PASS`: category has traceable source/reference evidence.
- `N/A-CONSUMER / PEER-MAPPED`: consumer client semantics are not appropriate; infrastructure peer evidence is provided instead.
- `BLOCKED_EXTERNAL`: strict proof needs live Linux/vendor/kernel/IPsec/interoperability/packet-capture environments.

## 1. Server/peer implementation ecosystem mapped

`REFERENCE-PASS`

Evidence:

- `SERVER_IMPLEMENTATIONS.md`
- reused entry-009 Linux kernel/iproute2/go-l2tp source pins;
- reused strongSwan 6.0.7 and Libreswan v5.4 IPsec pins;
- Linux XFRM protected composition;
- Cisco treated as an exact-version interoperability target rather than inferred generic support.

## 2. Installer/deployment projects reviewed

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`

Covers Linux package/kernel/service composition, ql2tpd packaging, system ordering, vendor/network-OS ownership, separate IPsec gateways, OCI/Kubernetes privilege/netns risks, upgrade/rollback/uninstall and anti-blind-script requirements.

## 3. Server OS/container/orchestration matrix completed

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALL_MATRIX.md`

Covers Debian/Ubuntu, Fedora/RHEL, Arch, Alpine, Linux VMs, Cisco exact-product proof, Linux-Cisco, separate IPsec gateway topology, OCI/Kubernetes and explicit non-claims for consumer OS/BSD until evidenced.

## 4. Server/control-plane UI map completed

`REFERENCE-PASS`

Evidence:

- `SERVER_UI_AND_MENUS.md`

Maps separate L2TPv3 and IPsec control domains, selector/protected-route preview, Layer-2 attachment, correlated status, transactional start/stop, fail-safe protection and RBAC/secret handling.

## 5. Client/peer install matrix completed

`N/A-CONSUMER / PEER-MAPPED / REFERENCE-PASS`

Evidence:

- `CLIENT_INSTALL_MATRIX.md`

Maps Linux strongSwan/Libreswan/ql2tpd peers, Cisco, heterogeneous Linux-Cisco, separate IPsec gateways, VM/container/Kubernetes and explicitly rejects native consumer L2TPv2 clients as entry-010 evidence.

## 6. Client/peer UI map completed

`N/A-CONSUMER / PEER-MAPPED / REFERENCE-PASS`

Evidence:

- `CLIENT_UI_AND_MENUS.md`

Provides protected peer editor, selector preview, status dashboard, fail-safe UI, paired static config, dynamic-control UI, credential boundaries and consumer-hidden placement.

## 7. Cryptographic design/security boundary documented

`REFERENCE-PASS`

Evidence:

- `CRYPTOGRAPHY.md`
- completed entries 004–007 IPsec dossier.

Documents current IKE/ESP model reuse, direct protocol-115 selector, UDP selector complexity, ESP confidentiality/integrity/anti-replay, credential separation, no-clear fallback, current-algorithm-policy rule and Layer-2 residual risks.

## 8. Data path/wire flow documented

`REFERENCE-PASS`

Evidence:

- `DATA_PATH_AND_WIRE_FLOW.md`

Covers direct-IP and UDP flow-selective composition, broader protected-underlay mode, inbound processing, startup ordering, dynamic control inside IPsec, rekey, outage/fail-safe behavior, MTU/ECN, Layer-2 post-decryption risks and observability.

## 9. Ports/transports/handshake documented

`REFERENCE-PASS`

Evidence:

- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`

Separates IKE UDP500/4500/ESP from protected inner protocol115 or L2TPv3 UDP; documents static and dynamic session startup, firewall policy, NAT-T, rekey and packet-proof requirements.

## 10. Deployment topologies documented

`REFERENCE-PASS`

Evidence:

- `DEPLOYMENT_TOPOLOGIES.md`

Covers Linux-Linux direct and UDP selective protection, protected-underlay, separate IPsec gateways, Linux-Cisco, Cisco-Cisco exact-proof requirement, VLAN/whole-LAN, dynamic control, NAT, IPv6, HA, container/node and migration alternatives.

## 11. Source/license/activity pins recorded

`REFERENCE-PASS`

Evidence:

- `REFERENCE_INDEX.md`
- reused entry-009 source pins:
  - Linux kernel `2f1baf1fc8929e6c48370be543ad028ac7ad4131`;
  - iproute2 `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`;
  - go-l2tp `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`;
- reused strongSwan 6.0.7 and Libreswan v5.4 exact pins/licenses;
- Cisco kept proprietary/current-doc interop-only.

## 12. Security/supply-chain risks recorded

`REFERENCE-PASS`

Evidence:

- `CRYPTOGRAPHY.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_UI_AND_MENUS.md`

Includes kernel/package/source provenance, CAP_NET_ADMIN/root, XFRM/L2TP netns alignment, secret ownership, no-clear fallback, vendor exact-version proof, bridge/VLAN risks, container privilege and blind-script rejection.

## 13. Upgrade/uninstall/rollback researched

`REFERENCE-PASS at reference layer / BLOCKED_EXTERNAL for receipts`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `CLIENT_INSTALL_MATRIX.md`

Defines coordinated upgrade/rollback across kernel/iproute2/IKE engine/policy and fail-safe decommission ordering so IPsec is not removed while a clear pseudowire remains active.

## 14. Protocol/server/client differences and uncertainties explicit

`REFERENCE-PASS`

Evidence throughout dossier:

- plain L2TPv3 != L2TPv3/IPsec;
- flow-selective != protected-underlay IPsec;
- direct protocol115 != UDP L2TPv3;
- L2TP Cookie/control auth != IKE/IPsec credentials;
- static != dynamic control;
- infrastructure peers != consumer clients;
- IPsec does not solve Layer-2 loops/VLAN risks;
- Cisco composition requires exact proof rather than feature inference.

## 15. REFERENCE_INDEX links complete dossier

`SOURCE-PASS after synchronization`

Update the index after this reconciliation with all mandatory files and final external blockers.

## 16. Latest AGENTS handoff has exact continuation state

`SOURCE-PASS after checkpoint`

Create a new entry-010 handoff/checkpoint, update Run State and the AGENTS pointer, then continue the next independent v2 task.

---

# Formal source/reference result

All 16 research/reference categories now have traceable evidence for entry 010.

Recommended internal state:

`REFERENCE-V2-SOURCE-COMPLETE / PROTECTED-PSEUDOWIRE-EXECUTION-BLOCKED / NOT IMPLEMENTED`

# Strict external blockers

Do not mark strict `COMPLETE-REFERENCE-v2` until representative proof exists for:

1. Linux strongSwan/XFRM direct protocol115 selector and protected traffic;
2. Linux Libreswan equivalent;
3. UDP L2TPv3 protected flow, including actual static/dynamic port behavior;
4. broader protected-underlay route-exclusivity/no-clear-fallback case;
5. forced IKE/ESP failure proving pseudowire forwarding blocks;
6. IKE/ESP rekey during active Layer-2 forwarding;
7. packet captures proving ESP/NAT-T on untrusted interface and no clear L2TPv3;
8. MTU/PMTU/ECN with combined L2TPv3+IPsec overhead;
9. VLAN/bridge/STP/broadcast behavior after decapsulation;
10. Linux-to-Cisco exact-version protected pseudowire interoperability;
11. exact Cisco protected composition or protected-underlay evidence;
12. IPv6 protected composition;
13. restart/upgrade/rollback/uninstall cleanup;
14. OCI/Kubernetes netns/capability fail-safe proof if retained.

# Promotion decision

Keep entry 010 `PENDING` in the strict v2 tracker under the repository's execution-evidence standard.

Checkpoint the source/reference closure and continue the next independent v2 entry selected from actual repository state. Do not redo entries 002–010 source/reference work unless upstream evidence materially changes.
