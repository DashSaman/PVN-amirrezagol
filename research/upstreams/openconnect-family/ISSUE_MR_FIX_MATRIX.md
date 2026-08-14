# OpenConnect — Vendor Issue / MR / Fix Matrix

Review date: 2026-08-14

Scope: high-impact upstream issues, merge requests and release fixes that materially affect PVNetwork enterprise compatibility planning.

Status rule:

- `MERGED/FIXED` means upstream evidence identifies a merged fix/release.
- `OPEN/DRAFT` means current GitLab listing/page still showed active review or draft state at this review.
- `HISTORICAL-LESSON` means the exact old defect may be fixed, but the failure class remains a PVNetwork regression requirement.
- This file does not claim that an old issue still affects OpenConnect v9.21 unless current evidence says so.

Canonical project: `https://gitlab.com/openconnect/openconnect`

Stable research baseline: **v9.21**.

## 0. Cross-core / release baseline

### v9.21 high-CPU/infinite-loop fix — `MERGED/FIXED`

Source:
- `https://gitlab.com/openconnect/openconnect/-/releases`

The v9.21 release fixed a long-standing buffer bug that became substantially easier to trigger after v9.20 reused the same buffer path for environment-variable construction.

PVNetwork lesson:
- every core update runs the full compatibility matrix;
- auth/config/environment-building paths require CPU/runaway watchdog tests;
- a dependency update is treated as behavioral change, not housekeeping.

### OpenSSL handshake timeout — `OPEN` at review

MR:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/615`

Current upstream listing still shows active work for a simple TLS-handshake timeout in the OpenSSL path.

PVNetwork lesson:
- connection-state timeouts must be explicit and bounded;
- test stalled TLS handshake separately for each selected crypto backend;
- cancellation must interrupt a stalled handshake immediately.

## 1. Cisco AnyConnect-compatible

### MFA challenge loses required opaque server state — `OPEN` at review

MR !633:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/633`
- related issue #824.

The MR reports Cisco ASA deployments using Microsoft Authenticator TOTP where MFA can fail if the XML POST probe returns 404 and required opaque/session state is not propagated into the MFA challenge response. The contributor reports successful testing against a previously failing server after the patch.

PVNetwork regression requirement:
- MFA challenge state must round-trip every server-required opaque field;
- an optional/probe request failure must not silently discard auth continuation state;
- test TOTP challenge after alternate/probe failure paths.

### External-auth command for AnyConnect SSO — `DRAFT/OPEN`

MR !616:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/616`

The draft adds an external-auth helper path for AnyConnect SSO. Its own description says tests, localization, Windows work and non-AnyConnect validation were incomplete at submission time.

PVNetwork consequence:
- do not certify CLI/external-helper SSO from the existence of this draft;
- product Browser/SSO Service remains a separate tested PVNetwork capability;
- require Windows/macOS/Linux/Android behavior evidence independently.

### Suppress external-auth advertisement — `MERGED/HISTORICAL-LESSON`

MR !398:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/398`

This added a Cisco-specific mode that avoids advertising external-browser authentication capability when the client cannot/will not handle it, allowing some servers to fall back to a scriptable method.

PVNetwork lesson:
- advertised client capabilities affect server-selected authentication behavior;
- only advertise browser/SSO modes that the current PVNetwork frontend/platform can actually complete;
- capability advertisement must be adapter-driven and platform-aware.

### Windows posture/CSD limitation — `KNOWN PLATFORM LIMITATION / VERIFY CURRENT`

Issue #535:
- `https://gitlab.com/openconnect/openconnect/-/issues/535`

Historical Windows 11 reports show a deployment can authenticate initially and later require a posture/CSD path unavailable on the platform/client path.

PVNetwork requirement:
- Cisco posture/host-check support must be a separate capability flag from tunnel/auth support;
- certify exact server policy + platform combinations rather than a single Cisco-support boolean.

## 2. GlobalProtect

### SSO non-progress loop — `OPEN` at review

MR !649:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/649`

The MR describes a rejected cached/stored SSO token being fetched and resubmitted unchanged indefinitely. The proposed change detects identical rejected tokens and terminates instead of spinning forever.

PVNetwork tests:
- identical rejected SSO token cannot loop indefinitely;
- auth state machine must detect non-progress;
- UI must show a recoverable auth/session failure instead of endless connecting;
- CPU/network activity remains bounded during rejected SSO state.

### Empty SSO token accepted by handler — `OPEN` at review

MR !648:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/648`

The proposed library fix rejects null/empty SSO values at a shared consumption point before they are submitted to the server.

PVNetwork tests:
- success callback with empty token is treated as invalid state;
- empty/null secret is never submitted as if authentication succeeded;
- external-browser and webview paths share the same validation semantics.

### External SSO wrapper — `OPEN` at review

MR !647:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/647`

The MR addresses CLI builds that lack a webview by delegating SAML/SSO to an external helper. The description states GlobalProtect was tested end to end; AnyConnect shares form handling but was not equivalently proven in that MR.

PVNetwork consequence:
- do not infer cross-protocol proof from shared code alone;
- wrapper/browser helper process needs cancellation, timeout, secret handling and lifecycle tests;
- prefer a product-owned cross-platform Browser/SSO Service behind a stable adapter contract.

### Portal and gateway both require SAML — `OPEN` at review

MR !564:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/564`

This covers deployments where SAML authentication occurs at both the portal and gateway, requiring separate auth phases/state.

PVNetwork tests:
- auth state machine supports multiple browser/auth phases in one connection attempt;
- cookies/tokens from stage 1 and stage 2 are not conflated;
- cancel/retry at the second phase cleans the whole session safely.

### IPv6 split-route handling — `ACTIVE/VERIFY MERGE STATE`

MR !528:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/528`

The current GlobalProtect MR listing includes work around IPv6 access routes/split tunnel data from server XML.

PVNetwork requirement:
- split-tunnel certification must test IPv4 and IPv6 independently;
- effective routes must be inspectable and cleanup verified after disconnect/failure.

## 3. Fortinet FortiGate SSL VPN

### SAML/SSO support — `OPEN` at review

MR !632:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/632`

The 2026 MR adds Fortinet SAML/SSO handling for GUI webview and CLI/external-browser modes and reports testing against FortiOS 7.2.12 plus an IdP setup.

PVNetwork consequence:
- upstream SAML capability is still moving and must be pinned to exact OpenConnect revision;
- certification requires exact FortiOS version + IdP/auth method + frontend path;
- browser callback and local continuation listener lifecycle need dedicated security and cancellation tests.

### Reconnect capability varies by server configuration/version — `OPEN/UNRESOLVED CLASS`

Issues/MR:
- `https://gitlab.com/openconnect/openconnect/-/issues/280`
- `https://gitlab.com/openconnect/openconnect/-/issues/334`
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/292`

Upstream research shows Fortinet reconnect/DPD behavior is not universal and can depend on server generation/configuration and whether reconnect-without-reauth is enabled.

PVNetwork tests:
- classify reconnect-without-reauth capability at runtime/server profile level;
- do not endlessly retry a server that requires fresh authentication;
- distinguish link loss, DPD detection, reconnect support and full reauthentication in UI/logs.

### PPP/TLS framing family — `HISTORICAL-LESSON`

Sources:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/169`
- `https://gitlab.com/openconnect/openconnect/-/issues/252`

Fortinet and F5 entered OpenConnect through a shared PPP-based core. Upstream discussion highlights packet concatenation/framing behavior that differs from Cisco/GlobalProtect assumptions.

PVNetwork consequence:
- do not share packet-framing assumptions across enterprise protocols merely because they use TLS;
- protocol-specific interoperability/performance tests are mandatory.

## 4. Pulse / Ivanti Connect Secure

### Client OS identity affects configuration — `MERGED`

MR !481:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/481`
- merged into master with commit `0e5a69e8`.

The MR documents Pulse servers that may depend on reported client OS information to produce complete/correct configuration.

PVNetwork requirement:
- client identity/platform metadata must be an explicit versioned adapter capability;
- test server response differences by reported platform where relevant;
- do not hardcode one global fake identity for every platform/vendor.

### Pulse/Juniper parsing on macOS — `OPEN` at review

MR !631:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/631`

Current upstream MR listing identifies active work to resolve Pulse/Juniper parsing issues on macOS.

PVNetwork requirement:
- vendor protocol tests must cross platform dimensions;
- parsing/config results from Linux cannot be assumed equivalent on macOS.

### IF-T/TLS packet framing / throughput — `CURRENT FOLLOW-UP / VERIFY AGAINST PINNED RELEASE`

Issue #456:
- `https://gitlab.com/openconnect/openconnect/-/issues/456`

Recent discussion reports severe throughput degradation on a Pulse TLS-only path and compares it with framing fixes already proven for Array/PPP/oNCP. The thread contains newer testing and fix discussion, but the exact status must be rechecked against the exact OpenConnect commit selected by PVNetwork.

PVNetwork tests:
- large sustained transfer in UDP-disabled/TLS-only mode;
- split packet across TLS records;
- multiple packets in one TLS frame/record where protocol permits;
- repeated disconnect/reconnect cycles;
- throughput comparison against a known-good native/vendor client when legally/test-lab feasible.

## 5. Juniper Network Connect

### macOS parsing shared with Pulse — `OPEN` at review

MR !631:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/631`

PVNetwork tests:
- config/auth parser corpus must run on macOS as well as Linux/Windows;
- legacy Juniper mode remains separate from Pulse/Ivanti even when the same appliance can expose both.

### Protocol framing precedent — `HISTORICAL-LESSON`

Issue #252 discussion identifies oNCP/Juniper as one of the protocol families that already required handling packet boundaries independently from TLS record boundaries.

PVNetwork lesson:
- wire framing belongs in protocol-specific tests, not generic TLS assumptions.

## 6. F5 BIG-IP SSL VPN

### Authentication form diversity — `OPEN/RECURRING CLASS`

Issues:
- `https://gitlab.com/openconnect/openconnect/-/issues/571`
- `https://gitlab.com/openconnect/openconnect/-/issues/573`
- `https://gitlab.com/openconnect/openconnect/-/issues/512`
- `https://gitlab.com/openconnect/openconnect/-/issues/464`

Reports cover MFA pages, auto-submitted/hidden forms, SAML/IdP redirects, challenge field variations and browser/JavaScript-dependent state.

PVNetwork tests:
- arbitrary form sequence rendering;
- hidden/auto-submit continuation state;
- MFA challenge fields with different server naming;
- external-browser/SSO handoff when HTML/JavaScript exceeds the core form parser;
- explicit capability failure rather than misleading username/password fallback.

### Plain auth regression — `MERGED/FIXED HISTORICAL-LESSON`

Issue #351:
- `https://gitlab.com/openconnect/openconnect/-/issues/351`

Maintainer identified a real regression in F5 plain authentication and linked a fix commit.

PVNetwork lesson:
- maintain protocol-specific auth regression fixtures even for the simplest username/password path.

### Initial PPP implementation and limited auth scope — `HISTORICAL BASELINE`

MR !169:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/169`

The original F5/Fortinet work explicitly described a limited authentication subset and PPP-over-TLS first, demonstrating why upstream protocol presence should never be converted into blanket vendor support.

## 7. Array Networks

### TLS-frame packet splitting/concatenation performance bug — `MERGED/FIXED`

Issue #435 and release history:
- `https://gitlab.com/openconnect/openconnect/-/issues/435`
- fix pattern referenced by commit `ad2e3199` and OpenConnect 9.10 release notes.

The failure was a major throughput difference caused by assuming a simpler relationship between IP packets and TLS frames. Upstream later handled multiple packets per TLS frame and packets split across records.

PVNetwork regression tests:
- multi-packet TLS frame;
- one packet split across multiple TLS records;
- sustained throughput and packet-loss validation;
- DTLS-disabled/TLS-only path;
- reconnect and session-timeout behavior.

### Array as framing reference for Pulse — `HISTORICAL-LESSON`

Issue #456 explicitly uses the Array framing fix as a working template when discussing Pulse's similar class of problem.

PVNetwork lesson:
- a fixed bug in one protocol can become a regression template for another without assuming the implementations are identical.

## 8. Cross-platform cleanup / lifecycle

### Windows Ctrl+C/disconnect cleanup — `MERGED/FIXED HISTORICAL-LESSON`

MR !323:
- `https://gitlab.com/openconnect/openconnect/-/merge_requests/323`

The issue was that Windows process termination could bypass normal disconnect handling and therefore skip network cleanup/helper invocation.

PVNetwork tests:
- normal disconnect;
- window/app quit;
- service stop;
- process termination/crash recovery;
- OS shutdown/logoff where applicable;
- route/DNS/interface cleanup after every exit class.

## 9. Release-gating rules derived from this matrix

For any OpenConnect version proposed for PVNetwork:

1. Freeze exact core version/commit and crypto backend.
2. Re-check open high-impact MRs/issues above.
3. Run per-vendor capability tests only for features PVNetwork plans to advertise.
4. Run the same vendor tests on every supported platform, not just Linux.
5. Test browser/SSO handler presence and non-progress/empty-token behavior.
6. Test packet framing and throughput in both preferred and fallback transports.
7. Test reconnect vs forced reauthentication behavior.
8. Test clean network artifact removal after normal and abnormal exit.
9. Store exact server/vendor versions in the evidence report.
10. Do not promote a vendor capability based solely on upstream source presence or one user's success report.

## 10. Remaining issue-research gaps

- verify the exact merge state/target release of every currently open MR immediately before implementation;
- map !631 details/commits and affected macOS versions more deeply;
- map current open issues by label for every vendor rather than only high-impact examples;
- add security advisories/CVEs and dependency advisories;
- correlate GUI/NetworkManager issue trackers with core issue state;
- create executable regression cases after the PVNetwork Enterprise Adapter exists.