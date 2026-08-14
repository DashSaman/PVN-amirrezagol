# 055 — Tor SOCKS — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: Tor privacy-overlay client exposed through a SOCKS interface, not a normal single-hop VPN protocol.

Decision: **`OPTIONAL PRIVACY-OVERLAY MODULE / ARTI OR TOR-DAEMON IMPLEMENTATION`**.

Primary modern embedding/reference direction: Tor Project Arti, with the established Tor daemon ecosystem as another mature implementation path.

PVNetwork must not implement onion routing from scratch and must not market Tor as equivalent to a fast single-hop VPN.

Canonical/product model should keep:

- Tor engine/bootstrap state;
- local SOCKS endpoint;
- bridge/pluggable-transport configuration where supported;
- privacy/isolation settings;
- secure secrets/auth material;
- circuit/network status;
- product routing policy selecting which traffic uses Tor.

A local Tor SOCKS listener is not proof that full-device TUN routing, DNS leak prevention or per-app behavior works; those remain PVNetwork responsibilities.

Later v2 adds exact Arti/Tor source/license/release pins, bridge/PT ecosystem, cryptography/onion-routing data path, server/relay concepts, platform install matrices and complete UI/menu evidence.
