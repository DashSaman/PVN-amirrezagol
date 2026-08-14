# AGENTS Handoff — 2026-08-14 — SoftEther v1 Closure

Mandatory continuation checkpoint.

## State transition

SoftEther family entries 013–015 are now:

`V1-HANDOFF-READY / NOT IMPLEMENTED`

This is a research handoff only. It is **not** strict `COMPLETE-RESEARCH-v1` and does not imply implementation or production certification.

## New/verified closure evidence

- `research/upstreams/softether-family/SOURCE_CLIENT_ETHERIP_IPSEC_EVIDENCE.md`
- `research/upstreams/softether-family/CLIENT_CONFIG_LICENSE_MODEL.md`
- `research/upstreams/softether-family/RELEASE_SECURITY_ISSUE_REVIEW.md`
- synchronized `research/upstreams/softether-family/README.md`
- existing shared architecture/capability/UI/dependency/reuse dossiers retained
- numbered v1 decisions present for 013 SoftEther, 014 EtherIP and 015 EtherIP/IPsec
- dated status: `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_SOFTETHER_V1.md`

## Core decisions

### 013 SoftEther VPN Protocol

`SUPPORTED-BY-UPSTREAM-ADAPTER / CONDITIONAL REUSE`

Preferred boundary:

`PVNetwork canonical profile -> SoftEther adapter -> native management/RPC -> upstream persistent client service/config -> virtual adapter/session`

Do not use live direct edits of `$vpn_client.config` as the normal product integration path.

### 014 EtherIP

`SERVER-CAPABILITY / CONDITIONAL REUSE`

The pinned `Proto_EtherIP` implementation is a concrete L2 data plane joined to the Virtual HUB through IPC and identity mapping. Keep it distinct from the native SoftEther client protocol.

### 015 EtherIP/IPsec

`COMPOSITE-SERVER-CAPABILITY / CONDITIONAL REUSE`

Requires EtherIP plus SoftEther IKE/IPsec/ESP service and listener/OS ownership; configured state is not sufficient proof of service health.

## Critical security finding

Official upstream advisory `GHSA-q5g3-qhc6-pr3h` / `CVE-2026-39312` identifies a high-severity pre-authentication DoS affecting Developer Edition 5.2.5188. The reviewed advisory record has no patched-version field.

Therefore:

- do not promote upstream `latest` automatically;
- do not bless Developer Edition 5.2.5188 as a generic safe production pin;
- future production selection must identify and verify a non-vulnerable commit/release and exact artifact digest.

## Residual gaps preserved

- fixed/safe release selection after the current advisory situation;
- exact credential/secret-at-rest migration/export audit;
- dependency-specific NOTICE/license bundle for the exact chosen build;
- exhaustive UI/command mapping and final template audit;
- implementation/device/E2E/Store proof;
- deeper server/crypto/wire-flow expansion belongs to mandatory v2 where required.

## Exact next action

1. activate `HYSTERIA-V1-CLOSURE` for entries 042 Hysteria and 043 Hysteria2;
2. read the actual `research/upstreams/hysteria-family/` tree and numbered 042/043 entries;
3. pin official upstream source/release/license and separate Hysteria v1 from Hysteria2 semantics;
4. map client/server architecture, config/storage, QUIC/transport dependencies, platform integration, tests/CI, advisories/issues/releases and client references;
5. write explicit per-entry support/reuse decisions and synchronize 042/043;
6. if handoff-ready, checkpoint and immediately select the next unfinished original-v1 family;
7. do not start mass `COMPLETE-REFERENCE-v2` yet.
