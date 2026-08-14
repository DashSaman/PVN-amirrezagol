# OpenVPN Family — Upstream Failure Lessons and PVNetwork Test Requirements

Research state: `IN-RESEARCH`. This file records lessons from public upstream issue trackers and converts them into future PVNetwork regression-test requirements. It does not claim those bugs exist in PVNetwork.

## Android network-change/reconnect reliability
Public ics-openvpn issues show repeated classes of failures around network transitions:

- **Issue #1299 — “No reconnect on network change”**: report of Wi-Fi/LTE transition not restoring a usable connection without manual reconnect.
- **Issue #761 — IPv6-to-IPv4 reachability transition**: report of a session becoming stuck when network/server reachability changed between address families.
- **Issue #1244 — reconnect after sleep/restoring internet**: report where the client/status path considered reconnection successful while practical connectivity remained broken.
- **Issue #1816** references unreliable reconnect-on-network-change behavior as a reason for users to build external automation.
- **Issue #1849** discusses stale server-name resolution during long-lived/persistent sessions after server IP changes.

### PVNetwork regression requirements derived from these reports
Future OpenVPN mobile testing must explicitly cover:
- Wi-Fi -> mobile and mobile -> Wi-Fi transitions;
- dual-stack -> IPv4-only and IPv4-only -> dual-stack transitions;
- sleep/resume and temporary loss of connectivity;
- server DNS result changing while a profile remains configured;
- UI connection state matching actual end-to-end reachability rather than only engine state;
- bounded reconnect behavior and manual cancellation;
- restoration of routing/DNS/application state after successful recovery.

## Profile import / parser / model translation
Public issues show that “profile imported successfully” can still hide semantic conversion failures:

- **Issue #1832**: an imported profile appeared successful but later profile-version/configuration handling prevented normal use; the generated configuration also identified directives that did not map to application settings.
- **Issue #1752**: imported proxy-related configuration could be represented in the UI yet behave differently from an equivalent manually entered/custom-option configuration.
- **Issue #1246**: proxy-authentication configuration exposed parser/parameter compatibility differences.

### PVNetwork regression requirements
- import success must mean both syntax and semantic validation passed;
- unsupported directives must remain visible and must never be silently discarded;
- normalized profile conversion must round-trip important fields;
- UI representation and generated engine configuration must remain equivalent;
- parser compatibility tests need real-world corpus coverage, not only hand-written happy-path examples;
- profile schema/version changes require migration tests and concurrency/versioning tests.

## Status/UI race conditions
- **Issue #1682 — “Building client configuration” forever** describes a case where practical state and UI/notification state diverged, suggesting timing/state synchronization problems.
- **Issue #804 — incorrect throughput displayed** shows that user-facing statistics can be wrong even while the tunnel itself works.

### PVNetwork regression requirements
- define one authoritative connection state machine;
- prevent UI/notification layers from inventing independent state;
- verify state transitions under boot/resume/reconnect races;
- test counter-unit changes and rate calculations independently;
- distinguish “engine says connected” from “connectivity verification succeeded”.

## Environment-aware behavior
OpenVPN GUI **issue #639** requests automatic behavior when a user moves between external and corporate/local network environments. **Issue #323** requests per-program split tunneling.

### PVNetwork product lessons
- network-context policies should be explicit and testable rather than hidden heuristics;
- per-app routing is an important capability on platforms that support it;
- policy automation needs clear UI indication and an easy manual override.

## Research rule
These issue reports are user/maintainer evidence, not universal proof about every version. Before implementation, check whether each issue is fixed/closed in current upstream, inspect related commits/PRs when available, and pin regression expectations to the selected engine/client version.

## Next issue/release research
Still required:
- OpenVPN 3 issue/release/security history;
- OpenVPN GUI closed regressions and Windows OS-update/DCO/service issues;
- Tunnelblick issue/release history across macOS upgrades;
- official Connect release/support notes by platform;
- security advisories and CVEs tied to selected engine versions;
- issue-to-fix commit mapping for the highest-impact cases.

Status: `IN-RESEARCH`.