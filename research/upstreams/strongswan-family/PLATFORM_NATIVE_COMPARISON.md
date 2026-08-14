# IKE/IPsec — strongSwan vs Native Platform API Comparison

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

This file records architecture/capability direction, not a final implementation choice. Store/platform APIs change; re-check official documentation when implementation begins.

## Product rule

PVNetwork should expose one typed product-level `IPsecAdapter` contract while allowing different backends per OS.

Do **not** force a single engine across all platforms when native APIs provide better Store/security/lifecycle integration.

---

# Linux

## Primary candidate

**strongSwan + OS kernel IPsec backend**.

### Why

- mature source/project designed for IKEv1/IKEv2/IPsec;
- Linux kernel XFRM/IPsec integration is a natural data-plane backend;
- broad plugin/authentication/algorithm/interoperability surface;
- VICI/swanctl provides a structured control boundary;
- suitable for advanced/enterprise profiles beyond a minimal consumer IKEv2 subset.

### Product architecture

Prefer:

`PVNetwork UI`

`-> product daemon/service`

`-> IPsec Adapter`

`-> strongSwan/VICI`

`-> kernel XFRM/IPsec`

rather than running privileged IKE/kernel operations directly inside the GUI.

### Linux gaps

- NetworkManager integration decision;
- distro/package compatibility;
- kernel feature/algorithm variation;
- DNS/resolver/routes/kill-switch integration;
- root/capability/service packaging;
- exact plugin set.

---

# Android

## Native platform IKEv2

Current Android platform APIs include `Ikev2VpnProfile` and `VpnManager` on modern Android releases. The API supports OS-managed IKEv2 VPN profiles and separates app provisioning/control from a custom in-app `VpnService` engine.

This can be attractive for standard IKEv2 because Android owns substantial IPsec/VPN lifecycle.

### Advantages

- platform-native lifecycle/security integration;
- reduced amount of privileged/native VPN data-plane code inside PVNetwork;
- OS handles IKEv2/IPsec tunnel implementation;
- potentially strong fit for managed/Always-On-capable deployments depending on current API/policy.

### Limitations/risks to validate

- API-level availability means it cannot cover every old Android release targeted by PVNetwork;
- feature/algorithm/authentication surface may be narrower than strongSwan;
- IKEv1 is not the same capability;
- vendor-specific extensions/interoperability may be missing;
- provisioning/user-consent/Always-On behavior must be tested by Android version;
- per-app/split-routing/product-statistics behavior differs from a custom VpnService engine.

## strongSwan Android path

strongSwan source includes an Android front end/application integration and has historically used Android VpnService-style architecture for user-space VPN functionality.

This can be valuable for:

- older Android versions not covered by modern native `Ikev2VpnProfile` APIs;
- advanced authentication/plugin needs;
- interoperability outside the native API surface.

### Cost

- larger native/plugin dependency surface;
- GPL/reuse architecture;
- product-owned VpnService/background/native lifecycle;
- more Store/security testing;
- more battery/memory/process-state risk.

## Provisional Android decision

Use a **capability-based backend selector**:

1. native Android IKEv2 backend where OS/API/version and profile features satisfy requirements;
2. strongSwan-based backend only where compatibility/features/older supported Android versions justify it and licensing/Store architecture is approved.

Do not expose backend choice to normal users unless needed for compatibility/diagnostics.

---

# Apple — iOS/iPadOS/macOS

## Native NetworkExtension

Apple provides `NEVPNProtocolIKEv2` through NetworkExtension for IKEv2 VPN configuration.

This is the preferred research direction for standard IKEv2 on Apple platforms because it aligns with:

- NetworkExtension entitlement/security model;
- Keychain/identity storage;
- platform VPN lifecycle;
- App Store review expectations;
- native sleep/network-change handling.

### Product-owned responsibilities remain

- canonical profile/import;
- Keychain credential/certificate references;
- profile provisioning/removal;
- UI/status/error mapping;
- routing/on-demand policy exposed by allowed APIs;
- Store privacy/entitlements;
- compatibility tests against target servers.

### Limits

Native `NEVPNProtocolIKEv2` should not be treated as proof of:

- generic IKEv1 support;
- every strongSwan plugin/EAP method;
- every algorithm/vendor extension;
- L2TP/IPsec support through the same API;
- identical behavior across iOS/macOS releases.

## Provisional Apple decision

**Native NetworkExtension first for IKEv2.**

Only consider a custom/strongSwan packet-tunnel engine if a required capability cannot be achieved with allowed native APIs and App Store/entitlement/legal feasibility is proven.

---

# Windows

## Native Windows VPN/IKEv2

Windows has built-in IKEv2/IPsec VPN capabilities and enterprise provisioning mechanisms such as the VPNv2 CSP/native profile model.

This is attractive for standard IKEv2 because the OS owns:

- IKE/IPsec engine;
- virtual interface/lifecycle;
- Windows credential/certificate stores;
- routing integration;
- enterprise policy/provisioning;
- service/update lifecycle.

### PVNetwork integration direction

A Windows-native adapter can generate/provision/manage a narrow approved VPN profile model through documented APIs/PowerShell/CSP/native configuration mechanisms as appropriate for the product distribution model.

The product UI should not shell arbitrary commands with unvalidated user input.

### Limits

- native Windows client feature set is not identical to strongSwan;
- IKEv1/vendor-specific remote-access behavior may require separate engines/clients;
- algorithm/security defaults differ by OS/version/policy;
- L2TP/IPsec is a separate native profile type/composition;
- Store/privilege/admin requirements vary by provisioning approach.

## Provisional Windows decision

**Native Windows IKEv2 first for standard profiles**, strongSwan/other engine only if a concrete interoperability gap justifies added GPL/native-service complexity.

---

# Backend selection should be profile-capability driven

PVNetwork canonical IPsec profile should declare requirements such as:

- IKE version;
- authentication type;
- local/remote identity;
- certificate/key/PSK/EAP secure references;
- algorithm/proposal constraints only when explicitly required;
- traffic selectors/routes;
- mobility/rekey/liveness requirements;
- server/vendor compatibility metadata;
- L2TP composition when applicable.

Backend selection:

`profile requirements + OS version/capabilities + Store/build channel`

`-> candidate backend capability match`

`-> selected native/strongSwan adapter`

Never choose backend solely from user-visible server name.

# Capability fallback rule

If native backend cannot represent a required profile feature:

1. report the unsupported capability;
2. evaluate an approved alternate backend;
3. never silently weaken algorithms/authentication or drop traffic selectors;
4. never silently switch IKE version;
5. preserve the original imported requirements.

# Product status normalization

Native OS and strongSwan backends must map into one product state model:

- Permission/ProvisioningRequired
- PreparingCredentials
- NegotiatingIKE
- Authenticating
- EstablishingDataSA
- Connected
- Rekeying
- Reconnecting
- NetworkUnavailable
- UserActionRequired
- ConfigurationUnsupported
- PlatformPolicyBlocked
- Error
- Disconnecting/Disconnected

Backends may expose different low-level states; product semantics stay stable.

# Credential storage

Use platform-native secure stores where possible:

- Android Keystore / managed credential facilities;
- Apple Keychain/identities;
- Windows certificate/credential/DPAPI-backed storage;
- Linux Secret Service/keyring or an encrypted product vault with strict permissions.

Do not place PSKs/private keys/passwords in ordinary PVProfile JSON.

# Tests required before backend certification

For every platform/backend/profile class:

- provisioning/permission;
- password/PSK/certificate/EAP authentication;
- wrong credential/certificate/identity;
- algorithm mismatch;
- network loss/handover;
- sleep/resume;
- rekey;
- server restart;
- IPv4/IPv6;
- split/full routing;
- DNS/leak behavior;
- disconnect cleanup;
- stored profile upgrade;
- OS update;
- Store/package update;
- Persian error/UI behavior.

# Current conclusion

PVNetwork should pursue **native IKEv2 first on Apple/Windows/modern Android when the required profile fits**, and **strongSwan as the main Linux/advanced compatibility candidate**.

This minimizes custom privileged VPN code while preserving a path for advanced interoperability.

Exact backend choices remain `IN-RESEARCH` until security/license/platform/version matrices are complete.
