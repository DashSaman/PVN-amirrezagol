# Research Campaign Status — 2026-08-14 — SoftEther family v1

Work unit: `SOFTETHER-V1-CLOSURE`.

Family scope: entries 013 SoftEther VPN Protocol, 014 EtherIP, 015 EtherIP/IPsec.

State: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**, with explicit residual/security blockers. This is not `COMPLETE-RESEARCH-v1`.

## Evidence now present

Shared family dossiers include:

- `SOURCE_ARCHITECTURE.md`
- `PROTOCOL_CAPABILITIES.md`
- `CLIENT_SERVER_CONFIG_UI.md`
- `DEPENDENCIES_TESTS_SECURITY.md`
- `SUPPORT_REUSE_DECISIONS.md`
- `SOURCE_CLIENT_ETHERIP_IPSEC_EVIDENCE.md`
- `CLIENT_CONFIG_LICENSE_MODEL.md`
- `RELEASE_SECURITY_ISSUE_REVIEW.md`
- synchronized family `README.md`

Numbered v1 decisions exist for entries 013, 014 and 015.

## Key closure conclusions

- Native SoftEther is a distinct stateful client/server protocol target; preferred product boundary is a PVNetwork adapter around upstream management/RPC and service lifecycle, not direct live mutation of `$vpn_client.config`.
- EtherIP is a concrete L2 server-side data plane connected to Virtual HUB IPC and must remain a separate capability.
- EtherIP/IPsec is a composite server capability requiring EtherIP plus IKE/ESP/service/listener/OS ownership.
- Top-level Apache-2.0 is commercially workable, but the shipped dependency graph requires third-party/submodule attribution and SBOM handling.
- A reviewed-version allowlist is mandatory; `latest` is not a security policy.

## Security blocker preserved

Official upstream GitHub advisory `GHSA-q5g3-qhc6-pr3h` / `CVE-2026-39312` identifies a high-severity pre-authentication DoS affecting Developer Edition 5.2.5188 and the reviewed advisory has no patched-version field. Therefore the current newest observed non-prerelease release must not be blessed automatically as a safe production pin.

## Residual v1 gaps

- verified fixed release/commit for the current high-severity advisory situation;
- exact credential/secret-at-rest migration/export audit;
- exact third-party NOTICE/license inventory for the chosen build graph;
- final exhaustive UI/command mapping and gate-by-gate template audit;
- implementation/device/E2E/Store proof remains later implementation/certification work.

## Transition

SoftEther family research is sufficient for deterministic handoff with residual gaps explicit. The campaign now proceeds to the next unfinished original-v1 family: **Hysteria / Hysteria2 (entries 042–043)**.

Do not begin mass `COMPLETE-REFERENCE-v2` expansion yet.