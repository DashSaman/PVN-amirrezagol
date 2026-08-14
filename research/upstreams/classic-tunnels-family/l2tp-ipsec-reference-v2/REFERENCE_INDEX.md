# L2TP/IPsec — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Entry: **008 — L2TP/IPsec**

State: `REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / LEGACY COMPOSED COMPATIBILITY TARGET / NOT IMPLEMENTED`

Original v1 state: `V1-HANDOFF-READY / NOT IMPLEMENTED`.

Strict `COMPLETE-REFERENCE-v2` promotion is still forbidden until representative execution/interoperability gates are satisfied.

## Composition boundary

L2TP/IPsec is not one monolithic protocol engine. Keep these layers separate:

1. **IPsec/IKE protection layer** — SAs, authentication/key management, ESP protection and NAT traversal;
2. **L2TPv2 tunnel/session layer** — RFC 2661 control/data messages;
3. **PPP layer** — link establishment, user authentication and network-layer configuration where used;
4. **OS/product networking** — address assignment, DNS, routes, firewall/NAT and lifecycle.

The completed 004–007 IKE/IPsec v2 dossier is reused as a dependency. It does not automatically complete L2TP or PPP evidence.

## Standards baseline

- RFC 2661 — L2TPv2;
- RFC 3193 — securing L2TP with IPsec;
- RFC 1661 / STD 51 — PPP;
- RFC 2759 — MS-CHAPv2 where used;
- current IPsec standards/security guidance inherited from the separately reconciled entries 004–007.

RFC 3193 reflects an older IPsec/IKE era. Do not copy its historical cipher assumptions into current product policy.

## Serious source/project pins

- **xl2tpd** — `xelerance/xl2tpd`, reviewed release `v1.3.20`, commit `07b3063e2b6870fad16366bc8d7c52a6f2a4292f`, GPL-2.0-or-later family; L2TP + PPP composition, external IPsec.
- **Accel-PPP NG** — `accel-ppp/accel-ppp-ng`, reviewed commit `9654bb66fa129fc3c20b24612ea91fb43dd14f38`, GPLv2; L2TP/PPP/auth/pools/RADIUS access-server implementation, external IPsec policy.
- **pppd / ppp-project** — reviewed commit `86c240ea75d48205310a4d0761784cb11f0b086e`; per-file licensing; PPP implementation used by classic Linux L2TP stacks.
- **NetworkManager-l2tp** — reviewed release/source `1.52.4`, commit `ef970e2f3bf3e219d99c949b7a91a6bb55ab6ef7`, GPLv2; Linux desktop composition across L2TP, IPsec and PPP backends.
- **Katalix go-l2tp / kl2tpd** — reviewed `v0.1.8`/commit `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`, MIT; L2TP component/client daemon, not a complete IPsec VPN by itself.

## Current platform evidence

### Windows

Current Microsoft documentation still supports L2TP clients and RRAS, but new Windows Server 2025 RRAS configurations do not accept L2TP/PPTP by default; administrators can explicitly re-enable them. Existing upgraded configurations retain prior behavior. This is treated as explicit legacy compatibility, not a preferred deployment default.

### Apple

Current Apple Platform Deployment documentation reviewed 2026-08-14 still lists L2TP over IPsec as built-in on current Apple platforms and documents MS-CHAPv2 user password plus shared-secret machine authentication for the L2TP model. Current macOS documentation exposes L2TP over IPSec configuration and options in System Settings.

### Android

Legacy L2TP/IPsec availability remains exact-version/OEM dependent for product certification. No universal current Android support claim is made without the remaining capability/runtime matrix.

## Mandatory v2 files

All 11 mandatory dossier files now exist:

| File | Source/reference state |
|---|---|
| `SERVER_IMPLEMENTATIONS.md` | complete for source/reference scope |
| `SERVER_INSTALLERS_AND_PROJECTS.md` | complete for source/reference scope |
| `SERVER_INSTALL_MATRIX.md` | complete for source/reference scope; execution TODOs preserved |
| `SERVER_UI_AND_MENUS.md` | complete for source/reference scope; exact runtime UI receipts pending |
| `CLIENT_INSTALL_MATRIX.md` | complete for source/reference scope; device receipts pending |
| `CLIENT_UI_AND_MENUS.md` | complete for source/reference scope; device receipts pending |
| `CRYPTOGRAPHY.md` | complete for source/reference scope |
| `DATA_PATH_AND_WIRE_FLOW.md` | complete for source/reference scope; packet/runtime receipts pending |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | complete for source/reference scope |
| `DEPLOYMENT_TOPOLOGIES.md` | complete for source/reference scope; HA/migration execution pending |
| `REFERENCE_INDEX.md` | synchronized |

## Security/reuse decision

- Classification: **legacy compatibility**.
- Prefer modern approved protocols for new deployments.
- Never silently downgrade to L2TP/IPsec.
- Use native OS client stacks where supported rather than bundling an unnecessary custom L2TP client.
- On Linux, treat NetworkManager-l2tp/IPsec/L2TP/pppd as a composed stack with explicit version ownership.
- If server management is implemented, use narrow typed adapters; do not expose unrestricted shell/file editing.
- Keep IPsec machine secret/private key, PPP user password and RADIUS secret as separate credential classes.

## External execution blockers — do not fabricate

1. Windows 10/11 native profile/provision/connect/update/removal receipts;
2. Apple iOS/iPadOS/macOS real-device/managed-profile receipts;
3. exact Android release/OEM capability and connection receipts if Android remains in product scope;
4. selected Linux distro NetworkManager-l2tp install/UI/backend/update/uninstall receipts;
5. representative Linux server clean install/start/update/rollback/uninstall;
6. Windows Server 2025 RRAS explicit L2TP enablement/disable rollback;
7. current pfSense/other selected appliance end-to-end configuration and backup/restore;
8. synchronized IPsec + L2TP + PPP packet/log traces;
9. NAT and multiple-clients-behind-one-NAT interoperability;
10. rekey/network-change/reconnect/MTU/route/DNS cleanup tests;
11. migration drill to an approved modern target;
12. any HA/failover behavior included in product scope.

## Non-negotiable rules

1. L2TP is not the confidentiality boundary; IPsec provides protection in this composition.
2. PPP authentication is separate from IPsec machine/peer authentication.
3. Raw/public UDP/1701 success is not L2TP/IPsec certification.
4. Do not expose old IKEv1/3DES/SHA1-era compatibility as a new-deployment default.
5. Source support does not prove native-platform UI/runtime availability.
6. Multiple clients behind NAT/source-port behavior are interoperability dimensions.
7. Do not claim strict completion from file existence or source/reference coverage.

## Next action

Perform the formal 16-gate entry-008 reconciliation against `FULL_PROTOCOL_REFERENCE_CONTRACT.md`, keeping execution-dependent gates blocked. Then checkpoint this family and continue the next independent v2 entry without waiting for owner input.
