# EtherIP/IPsec — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14 UTC

Entry: **015 — EtherIP/IPsec**

Scope: protected infrastructure composition; EtherIP and IPsec/IKE remain typed, separately observable layers.

## Canonical / repository evidence

- `research/protocols/015-etherip-ipsec/V1_GATE_RECONCILIATION.md`
- entry 014 V2 dossier / RFC 3378 for EtherIP wire behavior;
- `research/upstreams/softether-family/SOURCE_CLIENT_ETHERIP_IPSEC_EVIDENCE.md` — direct pinned SoftEther EtherIP/IPsec/IKE source review at `b1f7ef00040786d00bfa06c27fa463d106851e0c`;
- `research/upstreams/softether-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/softether-family/CLIENT_SERVER_CONFIG_UI.md`
- `research/upstreams/softether-family/DEPENDENCIES_TESTS_SECURITY.md`
- `research/upstreams/softether-family/RELEASE_SECURITY_ISSUE_REVIEW.md`
- `research/upstreams/softether-family/SUPPORT_REUSE_DECISIONS.md`
- completed entries 004–007 / `research/upstreams/strongswan-family/reference-v2/` for separate IKE/IPsec ecosystem, crypto, port, lifecycle and backend evidence;
- OpenBSD `etherip(4)` canonical OS documentation for native EtherIP + IPsec selector topology.

## Source / license / activity boundary

- Primary combined source: `SoftEtherVPN/SoftEtherVPN@b1f7ef00040786d00bfa06c27fa463d106851e0c`; Apache-2.0 root license with third-party/submodule obligations tracked separately.
- Shared later SoftEther family baseline: `49eb2f08641709d1af57a0d04971973ff94461db`; Stable sibling evidence remains separate.
- Alternative IPsec backend source/license pins remain those recorded in the completed IPsec family; no license is inferred across components.
- OpenBSD is treated as native platform/reference behavior, not a copied component.

## Mandatory V2 files

- `SERVER_IMPLEMENTATIONS.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `CRYPTOGRAPHY.md`
- `DATA_PATH_AND_WIRE_FLOW.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `DEPLOYMENT_TOPOLOGIES.md`
- `REFERENCE_INDEX.md`

Reuse decision: **ADVANCED PROTECTED SITE-TO-SITE TARGET / ETHERIP + TYPED-IPSEC COMPOSITION**. SoftEther is a strong combined-runtime reference; other combinations remain backend-specific.
