# OpenConnect Family — Release / Compatibility Lessons for PVNetwork

Research date: 2026-08-14

Status: `IN-RESEARCH`. These are upstream failure classes converted into PVNetwork quality requirements; they are not claims that every historical bug remains present.

## Primary evidence reviewed

Current official GitLab releases, merge requests, protocol documentation and current source activity were reviewed. Stable research baseline remains OpenConnect **v9.21**.

## 1. Core library upgrades can expose old bugs through new code paths

The v9.21 release fixed an infinite-loop/high-CPU buffer bug that had existed for years but became easier to trigger after v9.20 reused that buffer path more broadly.

PVNetwork rule: an OpenConnect upgrade is a behavior change, not routine dependency housekeeping.

Regression requirement:
- rerun the complete enterprise compatibility matrix on every core upgrade;
- include CPU/runaway-loop watchdog tests around authentication, configuration parsing and environment/result construction;
- compare error/log behavior as well as successful connection behavior.

## 2. Mature vendor compatibility still changes

v9.20 included Cisco compatibility updates such as TLS/channel-binding and default user-agent behavior for newer servers.

PVNetwork rule: “worked with Cisco once” is not permanent evidence. Store exact tested server/software versions and the OpenConnect version used.

## 3. SSO state machines need explicit non-progress detection

Current OpenConnect merge-request activity includes work to detect non-progress in GlobalProtect SSO retry loops and to reject empty SSO tokens rather than repeatedly submitting invalid state.

PVNetwork requirements:
- model browser/SSO as an explicit state machine;
- detect repeated/non-progress transitions;
- impose bounded retry behavior;
- surface a clear authentication-state failure rather than spinning indefinitely;
- never log raw SSO tokens/cookies.

## 4. One vendor may require multiple SSO phases

Open GlobalProtect work documents deployments where portal and gateway can each require separate SAML authentication.

PVNetwork requirement: the generic enterprise auth model must allow more than one browser/auth phase in a single session lifecycle. Do not encode a universal “one browser callback then tunnel” assumption.

## 5. Vendor configuration can depend on client identity/platform metadata

Pulse upstream work has documented server behavior that can depend on client operating-system information when producing configuration.

PVNetwork requirement: platform/client identity fields belong to the versioned Enterprise Adapter capability model and must be covered by compatibility tests. Do not hardcode them globally without vendor/version evidence.

## 6. Fixes may be protocol-specific and platform-specific at the same time

v9.21 includes source fixes/warnings involving Fortinet and Windows code, while current development continues Pulse and SSO-related work.

PVNetwork requirement: test dimensions must cross vendor family with platform, not maintain a protocol-only matrix detached from Windows/Android/Apple/Linux behavior.

## Minimum OpenConnect-family regression categories

For every vendor/version PVNetwork eventually claims:

1. auth state makes forward progress or terminates clearly;
2. repeated/empty browser token state cannot loop forever;
3. user cancellation is immediate and cleanup is complete;
4. library upgrade does not change tested capability silently;
5. reconnect/network-change behavior is tested independently from initial login;
6. product UI and core state remain synchronized;
7. credentials/tokens/cookies are redacted from ordinary logs;
8. CPU/memory remain bounded during failed auth/retry paths;
9. vendor/client-identity fields are versioned and test-covered;
10. each supported platform runs its own vendor regression subset.

## Remaining evidence gaps

- map each high-impact issue/MR to merged/fixed commits and releases;
- review current open issues by protocol label;
- add frontend/browser integration failures from selected GUI clients;
- dependency/security-advisory review;
- convert these requirements into executable tests after a PVNetwork adapter exists.

No item here upgrades any numbered entry to implementation or production support.