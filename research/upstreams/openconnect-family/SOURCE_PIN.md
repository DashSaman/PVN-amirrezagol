# OpenConnect — Source Pin

Research date: 2026-08-14

- Canonical upstream: `https://gitlab.com/openconnect/openconnect`
- Stable reviewed release: **v9.21**
- Stable source tree: `https://gitlab.com/openconnect/openconnect/-/tree/v9.21`
- Release evidence: `https://gitlab.com/openconnect/openconnect/-/releases`
- Current development tree is separate from the release pin: `https://gitlab.com/openconnect/openconnect/-/tree/master`
- Public API: `https://gitlab.com/openconnect/openconnect/-/blob/master/openconnect.h`
- Reviewed public API version on current master: **5.10**
- License identified by canonical source/header: **LGPL-2.1**

The archived GitHub repository is a historical mirror and must not be treated as current release authority.

## Important source areas

Canonical source contains shared library/API files, protocol-specific C modules, TLS backends, tunnel/platform code, Android/Java wrappers, translations, docs and tests. Current tree evidence includes protocol modules for Cisco/AnyConnect, GlobalProtect, Juniper, Pulse, F5, Fortinet and Array families plus PPP/ESP/DTLS-related support.

## PVNetwork direction

Evaluate OpenConnect through its **public library API**, not private structures. Browser/SSO/auth UI, protected credential storage and PVNetwork session state must remain above the library boundary.

Every library upgrade must be treated as a compatibility change and rerun per-vendor regression tests. v9.21 itself was released to fix a CPU/infinite-loop regression exposed more readily after v9.20 changes.

Status: `IN-RESEARCH`; no implementation claim.