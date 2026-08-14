# Classic / Legacy Tunnels — Dependency / Security / Test Surface

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

Entries: 009 L2TPv3, 010 L2TPv3/IPsec, 011 SSTP, 012 PPTP.

## Security classification must be visible

These technologies do not have equal security properties.

- **L2TPv3** — Layer-2 pseudowire/encapsulation; no confidentiality by itself.
- **L2TPv3/IPsec** — L2TPv3 protected by a separately configured IPsec/IKE layer.
- **SSTP** — VPN tunnel carried through TLS and PPP; security depends on TLS certificate validation plus PPP/authentication configuration.
- **PPTP** — legacy protocol with obsolete security properties; compatibility-only and not recommended as modern secure VPN.

PVNetwork must never collapse these into one “legacy VPN = secure” category.

## L2TPv3 dependency / backend direction

On Linux, the mature kernel L2TP subsystem is the primary data-plane reference/candidate rather than a new user-space packet implementation.

A complete product path may involve:

- Linux kernel L2TP support;
- userspace configuration via netlink/iproute2-style tooling or a typed helper library;
- bridge/interface setup;
- route/network namespace integration;
- optional IPsec protection for entry 010.

Final SBOM/release evidence must include the exact kernel/userspace helper/package versions for supported distributions even though kernel code is not bundled in the same way as an application library.

## L2TPv3/IPsec dependency layering

Entry 010 inherits two independent dependency/security surfaces:

1. L2TPv3 data/pseudowire implementation;
2. IKE/IPsec backend such as strongSwan + kernel IPsec or an equivalent native/router implementation.

Do not duplicate or bypass the IPsec policy/security gate already documented in the strongSwan/IPsec family.

## SSTP dependency surface

The open-source Linux/Unix client direction uses a native SSTP client with PPP integration and TLS dependencies.

Final exact build review must identify:

- SSTP client source/tag;
- TLS library/backend/version;
- PPP/pppd version and plugins;
- event/system libraries;
- certificate store/configuration;
- NetworkManager plugin if used;
- packaging/service integration.

Because the client is GPLv2-family, architecture/distribution obligations must be reviewed together with dependencies.

## SSTP security tests

At minimum:

- valid TLS certificate/hostname;
- wrong hostname;
- expired/untrusted certificate;
- proxy and no-proxy paths where supported;
- PPP auth success/failure;
- address/DNS assignment;
- route cleanup;
- reconnect/network transition;
- stale PPP process/interface detection;
- credential/log redaction.

A TLS handshake success is not enough to prove the PPP/tunnel/IP layer works.

## PPTP security policy

PPTP is retained only for legacy/reference completeness.

PVNetwork policy:

- disabled/not offered by default;
- prominent legacy/insecure label;
- never automatic fallback;
- no security claims based on historical MPPE/MSCHAP-style encryption/authentication;
- implement only if real compatibility demand exists and the target OS/package still supports it;
- separate from normal secure recommendations.

## PPTP dependency/reuse direction

Historical Linux clients commonly depend on PPP/pppd plus GRE/PPTP control implementation and OS routing/interface setup.

Before any implementation decision:

- pin an actual maintained/historical source;
- review license;
- verify target OS still supports required PPP/GRE behavior;
- review known protocol security limitations;
- confirm Store/distribution policy.

Do not spend product attack surface/permissions on PPTP if no customer requirement exists.

## Privilege boundary

L2TPv3, SSTP/PPP and PPTP on desktop Linux/Windows may require privileged network/interface/service operations.

PVNetwork should use least-privileged service/helper architecture rather than running the full UI as root/admin.

Privileged helper requirements:

- local authenticated IPC;
- fixed typed operations;
- input validation;
- no arbitrary shell execution;
- route/interface cleanup on crash;
- signed/package-managed binary;
- version compatibility with UI.

## Error taxonomy

Normalize by layer:

### L2TPv3

- kernel/module/backend unavailable;
- pseudowire/session creation failure;
- interface/bridge failure;
- peer unreachable;
- route failure;
- optional IPsec layer failure.

### SSTP

- TCP/proxy reachability;
- TLS certificate/handshake;
- SSTP negotiation;
- PPP negotiation;
- PPP authentication;
- IP/DNS assignment;
- route/platform failure.

### PPTP

- server/control reachability;
- GRE/data path;
- PPP/auth;
- legacy-security-policy blocked;
- OS support removed/disabled.

Do not expose one generic “VPN error”.

## Upgrade / support gate

For every platform/backend:

1. pin source/package/OS version;
2. review security/deprecation state;
3. resolve dependencies/licenses;
4. test import/profile migration;
5. test auth/certificate/PSK handling;
6. test route/interface lifecycle;
7. test sleep/reboot/network transition;
8. test cleanup after process/helper crash;
9. verify Store/package update behavior;
10. retain rollback/removal path.

## Residual v1 gaps

- exact sstp-client immutable pin and dependency versions;
- exact PPTP source/reference pin;
- current Linux kernel/iproute2 L2TPv3 version/package matrix;
- current Windows SSTP provisioning details;
- current issue/regression samples;
- mobile/Apple feasibility for SSTP;
- exact security/deprecation references for every OS release.

These may remain explicit at v1 handoff. Full technical cryptography/wire/install/menu/server work is mandatory later v2.
