# strongSwan / IKE / IPsec — Dependency / Security / Test Surface

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

## Root license / integration consequence

The reviewed strongSwan root `COPYING` is GNU GPL v2 family.

PVNetwork must not treat strongSwan like a permissively licensed drop-in static library without legal/architecture review.

Possible architecture directions to research include:

- use native OS IKEv2 APIs where sufficient;
- use a separately packaged strongSwan daemon/service controlled over VICI/IPC on platforms where distribution obligations and product architecture are compatible;
- use strongSwan-derived Android/front-end code only if the chosen product source-distribution/legal model permits it;
- do not copy GPL GUI/front-end code into a closed product by default.

Engineering license notes are not final legal advice.

## Plugin-defined capability surface

StrongSwan is highly modular. Cryptographic, authentication, kernel, socket, credential and management capabilities are loaded via plugins.

A final SBOM therefore needs:

- exact strongSwan commit/tag;
- exact configure/build options;
- compiled plugin list;
- loaded plugin list;
- external crypto/provider libraries;
- kernel/network backend;
- platform front-end;
- certificate/token libraries;
- packaging/service scripts;
- all direct/transitive native dependencies.

Do not report one generic “strongSwan supports algorithm X” without confirming the selected plugin/build contains it.

## Crypto-provider rule

StrongSwan can obtain algorithms/credentials through multiple plugins/providers. Product security depends on the exact selected providers and OS libraries.

Before release record:

- encryption/AEAD algorithms actually enabled;
- PRF/integrity algorithms;
- DH/ECDH groups;
- signature/certificate algorithms;
- random-number provider;
- crypto library/provider versions;
- FIPS/compliance mode if claimed;
- disabled/deprecated algorithms.

PVNetwork must define its own secure default policy instead of exposing every compiled legacy algorithm to ordinary users.

## EAP / authentication plugins

StrongSwan can support multiple EAP/authentication methods through plugins.

Each method expands:

- code/dependency surface;
- credential storage needs;
- user-interaction state;
- server interoperability;
- security review.

Only compile/ship/authenticate methods that PVNetwork actually needs and can test.

## PKCS#11 / smart-card / token support

Token/key-store plugins can provide valuable enterprise capability but introduce:

- native library/provider dependencies;
- PIN/user-interaction flows;
- device/session lifetime;
- Store/sandbox restrictions;
- hardware compatibility matrix.

Do not bundle enterprise token support in every consumer build unless required.

## Kernel backend / OS data-plane security

On Linux, strongSwan typically controls OS kernel IPsec policy/SAs via kernel plugins.

Release evidence must include:

- kernel version/features;
- XFRM/netlink/PF_KEY backend actually used;
- supported transforms;
- route/policy install behavior;
- network namespace/container behavior where applicable;
- cleanup after daemon crash/restart;
- permissions/capabilities.

A successful IKE handshake is not proof that traffic is correctly protected/routed by the data plane.

## Native platform backend security

When PVNetwork uses Apple/Windows/Android native IKEv2 APIs, the exact OS build becomes part of the security/runtime implementation.

Record:

- OS version;
- API/profile type;
- configured cryptographic policy;
- credential/key-store identity;
- routing/DNS behavior;
- vendor/server version;
- known OS update regressions.

Do not assume a native backend supports the same algorithms/extensions as strongSwan.

## IKE proposal policy

PVNetwork should ship a versioned **secure default** proposal policy and a separately classified legacy/interoperability policy.

Rules:

- never silently downgrade IKEv2 to IKEv1;
- never silently enable weak/deprecated transforms to make connection succeed;
- if a server requires legacy crypto, show a clear compatibility/security state;
- log effective algorithms in sanitized diagnostics;
- test server proposal mismatch separately from credential failure.

## Certificate validation

Tests must cover:

- valid trusted chain;
- wrong server identity;
- untrusted/expired/not-yet-valid cert;
- missing intermediate;
- client cert identity mismatch;
- revoked cert behavior where configured;
- certificate/key-store selection;
- user/admin override behavior;
- clock/date errors.

Do not provide a hidden “accept all certificates” fallback in Simple Mode.

## PSK handling

PSKs are reusable secrets.

PVNetwork requirements:

- platform secure storage/vault;
- no ordinary profile JSON/plaintext logs;
- controlled export/backup;
- minimum secret-strength/admin policy where product controls provisioning;
- explicit distinction between IPsec PSK and L2TP/PPP user password.

## IKEv1 legacy risk

IKEv1 support can be necessary for older/vendor environments but should be treated as a legacy compatibility surface.

Before enabling:

- pin exact required modes/extensions;
- prohibit insecure/default algorithms unless explicitly justified;
- test NAT traversal and server interoperability;
- isolate UI from current recommended IKEv2 profiles;
- provide a legacy/security warning.

## ESP / AH security distinction

### ESP

Product security depends on selected transforms/mode and whether confidentiality is enabled. Treat data-plane algorithm negotiation separately from IKE SA algorithms.

### AH

AH authenticates/integrity-protects but does not encrypt payload. Never label it “encrypted VPN” and do not use it as automatic fallback from ESP.

## L2TP/IPsec layered security

L2TP/IPsec has two credential/protocol layers. Tests must distinguish:

- IKE/IPsec authentication/security failure;
- L2TP tunnel/session failure;
- PPP/user authentication failure;
- address/DNS assignment failure;
- route/cleanup failure.

A generic “L2TP failed” error is insufficient.

## Current security-advisory source rule

During this research the GitHub repository security-advisory endpoint and official strongSwan security/release documentation were queried as primary sources.

Regardless of current endpoint contents, final release security review must include:

- strongSwan official security advisories/CVEs;
- exact selected release notes;
- plugin/provider dependency CVEs;
- Linux kernel/IPsec advisories where applicable;
- native OS VPN/security updates on Apple/Windows/Android;
- security-relevant commits/issues since the last shipped pin.

One empty advisory endpoint is never a certification.

## Test layers

### Parser/profile tests

- native/legacy profile import;
- canonical normalization;
- unsupported plugin/algorithm detection;
- unknown-field preservation;
- secure secret references;
- round trip/migration.

### IKE control-plane tests

- IKEv2 normal auth;
- IKEv1 only where supported;
- PSK/cert/EAP methods;
- proposal mismatch;
- identity mismatch;
- retransmit/timeout;
- rekey;
- DPD/liveness;
- MOBIKE/network change where supported;
- server restart.

### Data-plane tests

- ESP policy/SA installed;
- actual traffic follows protected route;
- no plaintext leak on failure/rekey/reconnect;
- IPv4/IPv6;
- split/full selectors;
- MTU/fragmentation;
- kernel/backend cleanup;
- AH semantics only when explicitly tested.

### Product/platform tests

- Android provisioning/VpnService/native profile lifecycle;
- Apple NetworkExtension on-demand/background/update;
- Windows built-in VPN profile/service/network transitions;
- Linux daemon/service/network manager integration;
- secure credential store;
- crash/process/service restart;
- Store/package upgrade.

## VICI / management security

If PVNetwork controls strongSwan through VICI:

- local/private endpoint only;
- OS permissions restrict caller;
- no arbitrary raw VICI exposure to plugins/UI;
- product adapter validates operations;
- sensitive configuration/events redacted;
- daemon/client protocol/version compatibility tested.

## Upgrade gate

Before changing any IPsec backend:

1. pin backend/core/OS version;
2. review advisories/releases;
3. diff algorithms/plugins/config defaults;
4. resolve exact SBOM;
5. run profile migration tests;
6. run IKE/auth/certificate tests;
7. run ESP/route/leak tests;
8. run rekey/network-change/sleep tests;
9. test server/vendor compatibility;
10. verify packaging/signing/rollback.

## Residual v1 gaps

- exact current full strongSwan source/release pin;
- exact compiled plugin/dependency matrix for a prospective PVNetwork build;
- current CVE/security-advisory table;
- source-level Android front-end/storage/menu map;
- exact native Apple/Windows/Android profile capability matrices;
- current issues/regression examples;
- final per-entry support/reuse decisions.

These can be explicit residuals at v1 handoff; final exact-build security evidence repeats during implementation/release.
