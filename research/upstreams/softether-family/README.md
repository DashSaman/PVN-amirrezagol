# SoftEther Family — Shared Source / Client Research

Related matrix entries: **013 SoftEther VPN Protocol**, **014 EtherIP**, **015 EtherIP/IPsec**, with 009/010 L2TPv3-related compatibility context where code paths overlap.

Research state: `IN-RESEARCH` — materially deepened, but **not** `COMPLETE-RESEARCH-v1`.

## Pinned architecture upstream

- Repository: `SoftEtherVPN/SoftEtherVPN`
- Architecture/source pin: `b1f7ef00040786d00bfa06c27fa463d106851e0c`
- Recursive tree reference: `https://api.github.com/repos/SoftEtherVPN/SoftEtherVPN/git/trees/b1f7ef00040786d00bfa06c27fa463d106851e0c?recursive=1`
- Root license at the pin: Apache License 2.0.

The architecture pin is intentionally separate from **current release/security review**. Do not infer that the pinned source revision or newest release is a production-approved version.

## Evidence index

### `SOURCE_CLIENT_ETHERIP_IPSEC_EVIDENCE.md`

Deep source/runtime audit covering:

- Cedar protocol ownership and client/server separation;
- native client manager/service architecture;
- EtherIP L2 packet path and Virtual HUB IPC handoff;
- EtherIP ID -> HUB/user/password mapping and reconnect semantics;
- EtherIP/IPsec coupling to IKE/ESP and OS-service ownership;
- build/package split and submodule provenance;
- explicit PVNetwork reuse/support decisions for entries 013–015.

### `CLIENT_CONFIG_LICENSE_MODEL.md`

Client state and redistribution audit covering:

- `$vpn_client.config` as the native client config file;
- 30-second native saver loop and save-on-account-mutation behavior;
- client RPC/notification/service ownership;
- account, certificate, proxy and virtual-NIC schema surface;
- remote-config policy surface;
- canonical PVNetwork schema -> SoftEther adapter -> native management/RPC decision;
- Apache-2.0 and third-party attribution requirements.

### `RELEASE_SECURITY_ISSUE_REVIEW.md`

Current upstream release/security review covering:

- observed latest non-prerelease GitHub Release `5.2.5188`;
- release-tag vs Windows asset-version mismatch risk;
- `CVE-2026-39312` / `GHSA-q5g3-qhc6-pr3h` high-severity pre-auth DoS affecting Developer Edition 5.2.5188;
- `CVE-2025-32787` shared-core crash evidence;
- representative upstream regression/operational issues;
- reviewed-version allowlist rule for PVNetwork.

## Current entry-level conclusions

| Entry | Current research decision | Strict state |
|---|---|---|
| 013 SoftEther VPN Protocol | `SUPPORTED-BY-UPSTREAM-ADAPTER`, conditional reuse of native client/service behind a PVNetwork adapter | `IN-RESEARCH` |
| 014 EtherIP | `SERVER-CAPABILITY`, reuse is sensible when PVNetwork intentionally controls a SoftEther server runtime | `IN-RESEARCH` |
| 015 EtherIP/IPsec | `COMPOSITE-SERVER-CAPABILITY`, requires EtherIP + IKE/IPsec service + listener/OS ownership | `IN-RESEARCH` |

No production support is implemented by these classifications.

## Important security block

Do **not** equate upstream `latest` with `safe`.

The official upstream advisory record currently lists Developer Edition `5.2.5188` as affected by `CVE-2026-39312`, with no patched-version field in the reviewed GitHub advisory. Until a fixed commit/release is identified and verified, PVNetwork must not bless the newest Developer Edition release as a generic production pin.

## Architecture direction

The preferred entry-013 integration boundary is:

`PVNetwork canonical profile -> SoftEther adapter -> native client management/RPC -> upstream persistent service/config -> virtual adapter/session`

Directly editing `$vpn_client.config` while the native service owns it is not the preferred runtime design.

Entries 014–015 are server-centric/composite capabilities and must remain separate capability flags even though they share the same upstream codebase.

## Remaining original-v1 blockers

Research is now substantially narrower, but strict completion still requires:

- identify/verify a release or commit that actually resolves the current high-severity advisory situation, or retain an explicit production-security blocker;
- complete exact credential/secret-at-rest and safe migration/export audit;
- convert `src/THIRD_PARTY.TXT` plus the selected build graph into an exact redistribution/NOTICE checklist;
- finish UI/command/control mapping where applicable;
- perform a final gate-by-gate audit against `research/PROTOCOL_RESEARCH_TEMPLATE.md`;
- synchronize numbered entries 013–015 and checkpoint/handoff state.

Current family classification: **`V1-CLOSURE-IN-PROGRESS`**, not handoff-ready yet and not strict complete.