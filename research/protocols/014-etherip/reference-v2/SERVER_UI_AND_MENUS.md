# EtherIP — Server / Control UI Map

Review date: 2026-08-14 UTC

EtherIP defines no protocol web panel or consumer menu. Management is implementation-owned.

## SoftEther ownership

Existing source/UI evidence under `research/upstreams/softether-family/` maps separate Server Manager, `vpncmd`, server/bridge roles, Virtual Hubs and compatibility services. Entry-014-specific management concepts are:

- EtherIP service/capability enabled state;
- outer peer/local address ownership where exposed by deployment;
- EtherIP client-ID mapping;
- mapping to Virtual Hub / User / Password in the reviewed source path;
- bridge/Virtual-Hub attachment;
- mapping lookup/reconnect state;
- diagnostics/logs;
- explicit **unencrypted by itself** classification.

## BSD ownership

OpenBSD/FreeBSD use native interface/bridge configuration tools rather than a protocol-defined GUI. Relevant controls are tunnel endpoint addresses, interface up/down, bridge membership and policy/firewall state.

IPsec controls are intentionally excluded from raw entry 014 and belong to entry 015 / IPsec backend administration.
