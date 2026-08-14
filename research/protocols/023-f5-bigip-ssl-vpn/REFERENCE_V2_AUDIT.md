# 023 — F5 BIG-IP SSL VPN — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only; this does not claim PVNetwork implementation or production certification.

## Evidence pins

Canonical vendor family: F5 BIG-IP Access Policy Manager (APM), with BIG-IP Edge Client / F5 Access clients. Current F5 documentation reviewed includes BIG-IP APM 17.5.1/17.5.0 and current Edge Client 7.2.7 documentation updated 2026-07-07. F5 documentation states Network Access provides secure corporate network access through a browser or BIG-IP Edge Client and documents Windows, macOS, Linux and F5 Access mobile-client families.

Compatibility implementation: OpenConnect F5 mode. OpenConnect documents F5 SSL VPN support as experimental, PPP-based, requested with `--protocol=f5`; it prefers PPP-over-DTLS and falls back to PPP-over-TLS. Basic username/password, optional TLS client certificate and domain/authgroup are documented, but this is not evidence of full parity with proprietary F5 endpoint/posture/auth flows.

Canonical evidence:
- https://techdocs.f5.com/en-us/bigip-17-1-0/big-ip-access-policy-manager-network-access/about-network-access.html
- https://techdocs.f5.com/en-us/edge-client-7-2-7/big-ip-access-policy-manager-edge-client-and-application-configuration-7-2-7/clients-for-linux.html
- https://techdocs.f5.com/en-us/edge-client-7-2-7/big-ip-access-policy-manager-edge-client-and-application-configuration-7-2-7/big-ip-edge-client-and-f5-access-for-macos.html
- https://techdocs.f5.com/kb/en-us/products/big-ip_apm/manuals/bigip-edge-apps.html
- https://www.infradead.org/openconnect/f5.html

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server is proprietary BIG-IP APM/Network Access. OpenConnect is a client compatibility implementation, not a replacement F5 server. No unsupported claim of an open-source BIG-IP/APM server is made.
2. **Official/community installers/deployment projects — PASS.** BIG-IP/APM is vendor appliance/software lifecycle, not a generic community daemon. Edge Client can be delivered from BIG-IP hosted content/browser flow; F5 documentation covers downloadable/customized client packages. Generic third-party server installers are NOT-APPLICABLE because no equivalent open server implementation is evidenced.
3. **Server OS/container/orchestration install matrix — PASS / evidence-backed N/A.** The canonical server is the BIG-IP platform. Ubuntu/Debian/RHEL/Alpine Docker/Kubernetes rows are not treated as supported server targets without evidence. This is a proprietary appliance/platform boundary, not a missing research gate.
4. **Server panel/UI/menu maps — PASS.** APM management concepts are mapped at reference level: Network Access resource, connectivity profile, access profile/policy, webtop, HTTPS virtual server, ACL/policy assignment, client package/hosted content and logging/diagnostics. Product-specific GUI details remain vendor-controlled and are not flattened into an invented generic panel.
5. **Client install matrix — PASS.** F5 documentation explicitly covers Windows, macOS and Linux Edge/Network Access clients and F5 Access apps; browser-downloaded Network Access components are documented for Linux/macOS/Windows. Mobile F5 Access families are separately identified by F5. Unsupported architecture/minimum-version values are left unknown rather than invented.
6. **Major client UI/menu maps — PASS.** Edge Client reference surface includes connect/disconnect/auto-connect behavior, VPN server/profile/connectivity behavior, always-connected/location awareness where supported, logs/diagnostics, package customization and platform-specific client flows. OpenConnect F5 mode is separately treated as CLI compatibility surface, not merged with proprietary Edge Client UI.
7. **Cryptographic design — PASS.** F5 Network Access security boundary is TLS/HTTPS with DTLS available for data transport; OpenConnect documents PPP-over-DTLS with PPP-over-TLS fallback. TLS certificate/client-certificate authentication is recorded where documented. No invented cipher suite or proprietary key schedule is asserted.
8. **Data path/wire flow — PASS.** Remote client/browser or Edge Client -> APM access/auth policy -> Network Access tunnel -> corporate resources. OpenConnect compatibility path is PPP framed over DTLS, with TLS fallback. APM policy/posture/authentication remains a distinct control boundary from tunnel forwarding.
9. **Ports/transports/handshake — PASS.** HTTPS/TLS is the primary documented secure connection boundary; DTLS is an optional/optimized Network Access transport. OpenConnect F5 mode documents DTLS preference and TLS fallback. No fixed undocumented proprietary port is invented.
10. **Deployment topologies — PASS.** Remote-access client-to-BIG-IP/APM gateway, browser-launched Network Access, managed Edge Client, always-connected/machine-tunnel variants where platform documentation supports them, and HA/load-balanced BIG-IP deployment as a vendor gateway boundary. This entry is not reclassified as a generic site-to-site protocol.
11. **Source/license/activity pins — PASS.** BIG-IP APM and Edge Client are proprietary/vendor-controlled and therefore reference-only for source reuse. OpenConnect is the separately researched open compatibility candidate under the shared `research/upstreams/openconnect-family/` evidence; F5 mode must retain its compatibility/feature limits.
12. **Security/supply-chain risks — PASS.** Proprietary client packages must come from F5/BIG-IP controlled delivery or authenticated vendor distribution; browser-delivered components require server trust. Community/open compatibility clients are not assumed feature-equivalent. No blind third-party installer is recommended.
13. **Upgrade/uninstall/rollback — PASS.** Server lifecycle follows BIG-IP vendor release/upgrade/rollback procedures; Edge Client has vendor package/update/removal lifecycle and downloadable package management. OpenConnect follows its independent package/release lifecycle. These lifecycles must not be conflated.
14. **Differences/uncertainties — PASS.** Proprietary BIG-IP APM server and Edge/F5 Access clients are distinct from OpenConnect F5 compatibility mode. OpenConnect documents basic authentication support but does not establish full parity for every browser/JavaScript, endpoint posture, MFA/SSO or appliance-specific flow. Exact unsupported combinations remain explicit unknowns rather than blockers to reference completion.
15. **REFERENCE_INDEX — PASS.** This audit plus `README.md` and shared OpenConnect-family evidence form the compact dossier index for entry 023. Canonical URLs and implementation boundaries are recorded here.
16. **Latest continuation state — PASS when tracker/state/handoff are advanced.** Promotion must atomically advance V2 to entry 024 Array Networks SSL VPN and preserve the exact next action.

## Completion decision

All 16 V2 research/reference gates are evidence-backed or explicitly NOT-APPLICABLE at the proprietary server/platform boundary. Runtime device certification, Store testing and live interoperability are implementation/certification work and are not hidden completion gates under `FULL_PROTOCOL_REFERENCE_CONTRACT.md`.

Decision: **COMPLETE-REFERENCE-v2**.
