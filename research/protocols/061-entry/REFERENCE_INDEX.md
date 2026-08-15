# 061 — Tinc — Reference Index

Status after review: **COMPLETE-RESEARCH-v1 + COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED / NOT CERTIFIED**

## Repository evidence

- `research/protocols/061-entry/V1_GATE_RECONCILIATION.md`
- `research/protocols/061-entry/REFERENCE_V2_AUDIT.md`

## Canonical pins

- repository: `gsliepen/tinc`
- current reviewed `1.1` development head: `211e3dfaef32d8736962e25f0b096dad951b7104`
- reviewed tree: `00b983a651524c52f4a17b8cf5a6300a4d91f910`
- latest reviewed 1.1 prerelease: `release-1.1pre18` -> `3217d5efb432f5a03beebd5d00b36392ec4b22ef`
- stable 1.0 release: `release-1.0.37` -> `2904e324ea68475fa3a131e7d39a43d80465b39a`
- license: GPL-2.0-or-later

## Primary references

- README / stability and packaging: https://github.com/gsliepen/tinc/blob/1.1/README.md
- QUICKSTART / provisioning and diagnostics: https://github.com/gsliepen/tinc/blob/1.1/QUICKSTART.md
- 1.1 manual: https://www.tinc-vpn.org/documentation-1.1/
- repository: https://github.com/gsliepen/tinc

## Critical stability boundary

Upstream explicitly says the 1.1 branch is not stable and recommends 1.0.x when a stable tinc version is required. 1.1 is protocol-compatible with 1.0.x, but program/control-socket behavior may change. Nightly/prebuilt packages are not heavily tested or officially supported.

## Architecture summary

`tinc virtual interface -> router/switch/hub mode -> direct authenticated/encrypted peer path when possible -> intermediate tinc forwarding fallback -> remote tinc interface/advertised subnet`

There is no mandatory centralized VPN server or canonical web management panel. A few known peers bootstrap the mesh; topology knowledge spreads and direct connections are attempted automatically.

## Configuration / management state

First-class concepts:
- network/netname;
- node name/identity;
- host/public/private keys;
- peer Address/Port;
- advertised IPv4/IPv6 Subnets;
- `ConnectTo` bootstrap peers;
- router/switch/hub mode;
- `tinc-up`/interface integration;
- config import/export;
- invite/join provisioning;
- daemon/control-socket lifecycle;
- info/dump/log diagnostics.

Invitation URLs are sensitive until consumed. Private keys are device secrets; public host configs still disclose topology/address/subnet information.

## Platform / install boundary

- upstream advises distro packages when available or source builds;
- Unix-like systems and Windows have canonical/source/package paths;
- prebuilt/nightly packages carry upstream's support warning;
- containers are deployment/test references, not a separate server architecture;
- no canonical Android/iOS/TV client or server role is asserted.

## License / reuse decision

GPL-2.0-or-later applies to tinc. Direct embedding/derivative distribution in a closed proprietary application requires deliberate GPL-compatible architecture and legal review. If support is required, PVNetwork should prefer a separately managed maintained tinc daemon/package behind an adapter rather than reimplementing tinc protocol/crypto.

## Exact continuation

Continue `COMPLETE-REFERENCE-v2` at **062 — innernet**. Apply all exact 16 gates using its V1 dossier and current canonical source/release/license evidence. Preserve coordination-server vs peer-client roles, WireGuard underlay, CIDR/peer association model, invite/enrollment and infrastructure-vs-consumer UI boundaries. If 062 passes, continue **063 — GRE**.
