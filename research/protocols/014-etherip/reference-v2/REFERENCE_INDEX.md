# EtherIP — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14 UTC

Entry: **014 — EtherIP**

Scope: infrastructure L2 encapsulation / peer capability; not a consumer VPN application.

## Authoritative / canonical evidence

- RFC 3378 — EtherIP wire format, protocol 97, version 3, security limitations.
- `research/protocols/014-etherip/V1_GATE_RECONCILIATION.md` — complete original research and canonical source set.
- `research/upstreams/softether-family/SOURCE_CLIENT_ETHERIP_IPSEC_EVIDENCE.md` — direct pinned `Proto_EtherIP.c` ownership/data-path review at `b1f7ef00040786d00bfa06c27fa463d106851e0c`.
- `research/upstreams/softether-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/softether-family/CLIENT_SERVER_CONFIG_UI.md`
- `research/upstreams/softether-family/DEPENDENCIES_TESTS_SECURITY.md`
- `research/upstreams/softether-family/RELEASE_SECURITY_ISSUE_REVIEW.md`
- `research/upstreams/softether-family/SUPPORT_REUSE_DECISIONS.md`
- `research/upstreams/classic-tunnels-family/softether-protocol-reference-v2/SERVER_INSTALLERS_AND_PROJECTS.md` — reusable SoftEther product deployment/lifecycle evidence, not native-protocol conflation.
- OpenBSD `etherip(4)` and FreeBSD `gif(4)` canonical OS manuals recorded in V1.

## Exact source / license / activity boundary

- Direct EtherIP source analysis pin: `SoftEtherVPN/SoftEtherVPN@b1f7ef00040786d00bfa06c27fa463d106851e0c`, Apache-2.0 root license with separately tracked third-party/submodule obligations.
- Shared later SoftEther family baseline: `49eb2f08641709d1af57a0d04971973ff94461db`.
- Official Stable sibling observed in existing research: `ed17437af9719ac66acab30faa29e375d613c35f` (`v4.44-9807-rtm`).
- BSD implementations are OS-native reference/integration targets, not source-copy reuse approvals.

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

Reuse decision: **ADVANCED L2 ENCAPSULATION / SERVER-CAPABILITY / LOW CONSUMER PRIORITY / NOT ENCRYPTED BY ITSELF**.
