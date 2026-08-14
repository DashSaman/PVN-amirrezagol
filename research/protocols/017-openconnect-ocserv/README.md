# 017 — OpenConnect / ocserv-compatible

Status: `IN-RESEARCH`; not implemented by PVNetwork.

Shared evidence:
- `research/upstreams/openconnect-family/SOURCE_PIN.md`
- `research/upstreams/openconnect-family/VENDOR_COMPATIBILITY_MATRIX.md`
- `research/upstreams/openconnect-family/README.md`

Current research baseline uses OpenConnect v9.21 from the canonical GitLab upstream. This entry is the preferred controlled interoperability baseline for the OpenConnect family because an open compatible server can be used later for repeatable PVNetwork integration tests.

This baseline does not prove compatibility with Cisco, GlobalProtect, Pulse/Ivanti, Juniper, F5, Fortinet, or Array products; those remain separate numbered dossiers and require their own version/capability evidence.

Current reuse direction: evaluate the LGPL-2.1 OpenConnect public library API behind the PVNetwork Core Adapter. Keep product authentication UI, browser/SSO flow, protected credentials, diagnostics, and platform networking responsibilities outside private OpenConnect internals.

Remaining research: dependency/SBOM and LGPL distribution architecture, current issue/fix mapping, public API ownership/threading analysis, platform packaging, frontend/UI references, and final interoperability test design.

Research completion is not implementation completion.