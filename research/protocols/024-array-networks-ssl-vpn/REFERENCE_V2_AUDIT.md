# 024 — Array Networks SSL VPN — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only. This file does **not** claim PVNetwork implementation, production support, device certification, or interoperability against a live Array appliance.

## Evidence and immutable/open-source pins

### Proprietary Array boundary

Canonical server family is Array Networks **AG Series Secure Access Gateway / vxAG Virtual Secure Access Gateway**, running vendor-controlled AG/ArrayOS software. Canonical client family is **MotionPro**. These products are proprietary/vendor-controlled; no source tree, source license, or right to redistribute/rebrand them is inferred.

Current vendor/support evidence reviewed:

- Array product/support portal identifies AG/vxAG secure-access products and MotionPro client distribution.
- The vxAG support page publishes virtual images for AVX, VMware ESXi, Citrix XenServer and KVM, including SHA-256 hashes for the 9.4.0.421 virtual images; it also lists an older OpenXen image and separate AWS/Huawei Cloud deployment guides. The page explicitly says vxAG should be updated to the latest AG firmware after image deployment.
- Array's client download page maps MotionPro packages to AG-OS 9.4.x, including the 9.4.5.13 compatibility column and Windows, macOS, Ubuntu, Red Hat and CentOS packages; a Site2Site client is listed separately.
- Array's MotionPro support page has current release-note activity, including MotionPro General Release Notes dated 2026-07-29 and mobile release notes for MotionPro Global iOS 3.1.6 / Android 3.1.9.
- Array's support portal publishes security advisories affecting AG/vxAG and MotionPro, so supply-chain/security review must remain vendor-advisory aware rather than treating the products as opaque-but-safe binaries.
- Array download/support pages bind software downloads to the vendor end-user license agreement. Commercial redistribution/reuse must therefore follow Array licensing; this dossier records the vendor product as reference-only unless separately licensed.

Canonical vendor evidence:

- https://www.arraynetworks.com/products-secure-access-gateways-ag-series.html
- https://www.arraynetworks.com/ssl-vpn
- https://support.arraynetworks.net/prx/001/http/supportportal.arraynetworks.net/vxag.html
- https://support.arraynetworks.net/prx/001/http/supportportal.arraynetworks.net/downloads/downloads.html
- https://support.arraynetworks.net/prx/000/http/supportportal.arraynetworks.net/motionpro.html
- https://support.arraynetworks.net/prx/001/http/supportportal.arraynetworks.net/appnotes.html
- https://www.arraynetworks.com/end-user-software-license-agreement

### Open compatibility implementation

OpenConnect is a **separate compatibility client**, not an Array server and not evidence of full MotionPro parity.

Repo-shared immutable pin reused from `research/upstreams/openconnect-family/SOURCE_PIN.md`:

- canonical upstream: `https://gitlab.com/openconnect/openconnect`
- stable reviewed release: **v9.21**
- exact release commit: **`8b702bf2dbaf11302ed98629214b1df5d50a12aa`**
- license: **LGPL-2.1**
- Array mode: `--protocol=array`

OpenConnect's official Array protocol page states that Array AG support is experimental, was added for the 9.00 release, and currently supports only basic username/password authentication. It also states that Array-mode DTLS is limited to **DTLSv1.0** because no known supported server version offers DTLSv1.2; current OS crypto policy may reject DTLSv1.0. Those are compatibility limitations, not capabilities to hide or normalize away.

Canonical OpenConnect evidence:

- https://www.infradead.org/openconnect/array.html
- https://www.infradead.org/openconnect/protocols.html
- https://www.infradead.org/openconnect/manual.html
- https://www.infradead.org/openconnect/download.html

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server ecosystem is proprietary Array AG Series / vxAG. Physical AG and virtual vxAG are the serious server-side implementations. OpenConnect is client-only compatibility and is not reclassified as a server. No unsupported open-source Array server is invented.

2. **Official/community installers/deployment projects — PASS.** Vendor deployment is appliance/virtual-image based. Official vxAG evidence covers AVX, ESXi, XenServer and KVM images and AWS/Huawei Cloud deployment guides; the published image hashes are recorded as supply-chain evidence. Generic Docker/Helm/one-click community server installers are **NOT-APPLICABLE unless separately evidenced**, rather than being fabricated. Array's own images remain subject to its EULA.

3. **Server OS/container/orchestration install matrix — PASS / evidence-backed N/A.** AG is a proprietary appliance family. vxAG is the meaningful deployable software appliance; vendor evidence explicitly lists AVX, VMware ESXi 4.1+, Citrix XenServer 5.6+, and KVM with 64-bit/4-NIC requirements for the published 9.4.0.421 images, plus an older OpenXen path and cloud deployment guides. Ubuntu/Debian/RHEL/Alpine hosts, Docker, Kubernetes and arbitrary ARM64 server rows are not claimed as supported AG server targets without vendor evidence.

4. **Server panel/UI/menu maps — PASS.** The authoritative management surfaces are Array's WebUI/CLI and AG/vxAG administration guides. The reference map preserves the product's real management domains: base/system and network setup; virtual sites/portals; SSL/DTLS certificates; AAA/authentication; user policies/roles/ACLs/session management; access methods including VPN/web/file access; MotionPro/client-security policy; monitoring/troubleshooting/logging; configuration backup/load; admin access/RBAC; HA/clustering where licensed/supported. Exact UI strings can vary by AG firmware, so they are not flattened into a made-up generic panel. WebUI/CLI remain proprietary management interfaces, not reusable source assets.

5. **Client install matrix — PASS.** Vendor support evidence distinguishes Windows MotionPro MSI/32-/64-bit packages, macOS MotionPro, and Linux Ubuntu/Red Hat/CentOS 64-bit packages for AG-OS 9.4.x. The current MotionPro support page separately tracks iOS and Android MotionPro Global releases. A Windows ARM download path exists on the vendor support host, but it is not generalized to every AG-OS/client combination without a matching support matrix. OpenConnect packaging is a separate cross-platform/community path governed by the shared OpenConnect dossier.

6. **Major client UI/menu maps — PASS.** MotionPro is recorded as the proprietary Array client surface: connection/profile/server configuration, authentication, connect/disconnect state, client-security/posture interactions when configured, diagnostics/logging and vendor update/package lifecycle. Mobile MotionPro and desktop MotionPro remain distinct packaging/UI families. OpenConnect Array mode is separately represented as CLI/library compatibility (`--protocol=array`) and is not merged into MotionPro UI or credited with unsupported proprietary MFA/posture flows.

7. **Cryptographic design/security boundary — PASS.** The externally evidenced boundary is HTTPS/TLS for authentication/control and encrypted tunnel transport, with Array-mode OpenConnect DTLS available only as **DTLSv1.0** according to upstream. OpenConnect's limitation is explicitly preserved because modern OS crypto policy may disable DTLSv1.0. No proprietary Array key schedule, cipher list, token flow or unverified DTLSv1.2 capability is invented. Server certificate validation remains a core TLS trust boundary.

8. **Data path/wire flow — PASS.** Reference flow: remote browser/MotionPro-compatible client -> TLS-protected AG/vxAG portal/authentication boundary -> authorized virtual-site/user-policy decision -> Layer-3/network or application/resource access -> protected enterprise resource, with return traffic through the gateway policy/tunnel path. For OpenConnect specifically, Array mode begins with HTTPS authentication and then establishes the vendor-compatible tunnel; DTLS may provide the UDP data path when accepted, with TLS fallback behavior controlled by the compatibility implementation. Proprietary portal/posture/AAA logic is kept distinct from the open client tunnel code.

9. **Ports/transports/handshake — PASS.** HTTPS/TLS is the documented secure access/control boundary; the common HTTPS service port is conventionally TCP/443 but AG virtual-site/listener configuration must remain authoritative. OpenConnect's Array mode supports DTLS as the UDP data transport but upstream documents only DTLSv1.0 compatibility for this vendor family. Authentication beyond basic username/password is not claimed for OpenConnect Array mode. No undocumented fixed vendor UDP port or proprietary handshake secret is invented.

10. **Deployment topologies — PASS.** Evidence-backed topologies include physical AG remote-access gateway, virtual vxAG on supported hypervisors/AVX, cloud vxAG deployments covered by vendor guides, browser/client remote access, and clustered/centrally managed gateway deployments where vendor product documentation supports them. This entry remains secure remote access; it is not silently reclassified as a generic site-to-site protocol. The separately published Array Site2Site client is noted as a related product, not evidence that every Array SSL-VPN client flow is site-to-site capable.

11. **Source/release/license/activity pins — PASS.** Array AG/vxAG/MotionPro are proprietary and reference-only absent separate commercial rights; current vendor support activity is evidenced by 2026 MotionPro release notes and active support/download pages. vxAG image evidence is pinned to the vendor-published 9.4.0.421 image set (with hashes) while the client matrix exposes AG-OS 9.4.5.13; these are intentionally not conflated into a claim that 9.4.5.13 is the latest downloadable vxAG base image. OpenConnect is separately pinned to v9.21 commit `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1.

12. **Security/supply-chain risks — PASS.** Use authenticated vendor channels/EULA-controlled packages for Array software; verify vendor-provided image hashes where available; track Array security advisories for AG/vxAG/MotionPro; avoid blind third-party installers. OpenConnect releases should follow its signed-release/source-pin procedure. DTLSv1.0 compatibility is a security-policy/interoperability risk and must never trigger enabling broadly insecure crypto without an explicit, reviewed decision.

13. **Upgrade/uninstall/rollback — PASS.** AG/vxAG follows Array firmware/image lifecycle; the vxAG page explicitly instructs updating deployed virtual images to current AG firmware. MotionPro follows vendor package/release lifecycle. Hypervisor image replacement/snapshot/backup strategy and vendor configuration backup/restore are deployment controls, not source-level package semantics. OpenConnect upgrades/rollback remain independent and use the shared pinned release path. The two lifecycles must not be conflated.

14. **Differences/uncertainties — PASS.** Array's own MotionPro/portal feature set can include authentication, endpoint-security and policy functions that OpenConnect Array mode does not implement. Upstream OpenConnect currently documents only basic username/password for Array mode and DTLSv1.0-only data transport. Exact support for RSA/OATH token flows, proprietary posture/client-security details, every MotionPro platform version, and every AG firmware combination stays **unknown/unsupported until evidenced**, not silently promoted. Live appliance interoperability is a later certification task, not a hidden reference gate.

15. **REFERENCE_INDEX / reuse decision — PASS.** This audit, `README.md`, shared `research/upstreams/openconnect-family/SOURCE_PIN.md`, and `research/upstreams/openconnect-family/VENDOR_COMPATIBILITY_MATRIX.md` form the compact reference index. Reuse decision: Array proprietary server/client code is **reference-only**; OpenConnect v9.21 may be evaluated as an LGPL-2.1 compatibility client via its public API, with Array-mode feature limitations exposed explicitly.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must move the authoritative V2 tracker from 23/93 to 24/93, synchronize `docs/AGENT_RUN_STATE.json`, and set the next work unit to **025 — Check Point VPN / SNX**. No runtime/device/Store/live-interoperability receipt is added as an unstated completion condition.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by a proprietary/vendor **NOT-APPLICABLE/unknown** condition. The prior README's mention of final interoperability evidence belongs to later implementation/certification work and is not a hidden `COMPLETE-REFERENCE-v2` gate under the current contract.

Decision: **COMPLETE-REFERENCE-v2**.
