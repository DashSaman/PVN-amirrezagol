# SSTP — Client / Platform / Source Research

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

Entry: **011 SSTP / MS-SSTP**.

## Primary client directions

### Windows native SSTP

For Windows, the built-in Windows VPN/RAS SSTP implementation is the preferred first research path for a standard SSTP profile.

Why:

- OS owns TLS/RAS/tunnel lifecycle;
- integrates with Windows certificate and credential stores;
- avoids bundling a second native PPP/TLS client on Windows;
- aligns better with Windows service/update/policy infrastructure.

PVNetwork should provision/control supported Windows SSTP profiles through documented OS APIs/profile mechanisms rather than reimplement the SSTP protocol stack when native capability is sufficient.

### Linux/Unix — sstp-client

Repository reviewed:

`sstp-client/sstp-client`

Current project is a native open-source SSTP client for Linux/Unix-like systems.

Root `COPYING` reviewed: GNU GPL v2 family.

Research classification:

`STRONG LINUX SSTP REFERENCE / GPL CLIENT ENGINE REQUIRES DISTRIBUTION-ARCHITECTURE REVIEW`

This is not a permissive library that can automatically be statically embedded in a closed PVNetwork binary.

## Architecture

The project implements SSTP client behavior and integrates with the traditional PPP stack rather than treating SSTP as a complete IP configuration/authentication system on its own.

Conceptually:

`SSTP TLS/HTTP-style tunnel`

`-> PPP session/authentication`

`-> OS interface/address/DNS/routing`

This layered design must remain explicit in PVNetwork error/status reporting.

## PPP relationship

SSTP transports PPP. Therefore product failures may originate from different layers:

- TCP/reachability;
- TLS/certificate validation;
- SSTP negotiation;
- PPP negotiation;
- PPP authentication (for example supported PAP/CHAP/MSCHAP-style methods according to backend/server configuration);
- address/DNS assignment;
- route installation.

Do not show one generic `SSTP failed` error when the layer is known.

## TLS / certificate policy

SSTP security relies on TLS. PVNetwork must model:

- server name/hostname;
- system/custom CA trust where supported;
- certificate validation;
- proxy behavior where supported by the selected implementation;
- unsafe validation overrides only as explicit advanced compatibility options, never defaults.

Tests must include wrong hostname, expired/untrusted certificate and certificate rotation.

## Proxy capability

SSTP is HTTPS/TLS-shaped and some implementations can operate in environments with web proxy requirements. Proxy support must be version/backend-specific and must not be assumed across Windows/native/Linux clients without evidence.

PVNetwork canonical SSTP profile should keep proxy settings separate from the SSTP endpoint/authentication fields.

## Linux NetworkManager direction

For Linux desktop UX, evaluate NetworkManager SSTP integration/plugins as a front-end/platform reference in addition to raw `sstp-client`.

PVNetwork may either:

- use its own service/adapter and the underlying client; or
- integrate through NetworkManager where that produces a more native/reliable distro experience.

Backend choice must be distro/package/version aware.

## Storage / credentials

The SSTP engine/PPP stack is not PVNetwork's credential database.

Store reusable secrets using platform secure storage:

- Windows Credential Manager/certificate store/DPAPI-backed product vault;
- Linux Secret Service/keyring or encrypted product vault.

Never dump PPP passwords or certificate/private-key data to normal logs.

## Android / Apple / TV / macOS

No support claim is made merely because a Linux client source exists.

Before SSTP is enabled on Android, iOS/iPadOS, Android TV or macOS, PVNetwork must identify a maintained and legally compatible implementation that can integrate with:

- Android VpnService;
- Apple NetworkExtension;
- platform background/sandbox rules;
- target Store policies.

If no strong implementation exists, SSTP may remain Windows/Linux only in the initial product capability matrix.

## Reuse direction

### Windows

`NATIVE OS FIRST`

### Linux

`sstp-client / NetworkManager integration candidate`

### Other platforms

`UNPROVEN / RESEARCH REQUIRED`

## License rule

`sstp-client` GPLv2-family licensing means its distribution/interaction model requires deliberate legal/architecture review for a closed commercial PVNetwork application.

Possible strategies to evaluate include a separately packaged GPL component/process where legally appropriate, or using a platform-provided implementation. Do not copy source into PVNetwork by default.

## Regression requirements

- correct server-name/certificate validation;
- proxy/no-proxy connection path;
- PPP authentication success/failure;
- network loss/reconnect;
- sleep/resume;
- DNS/address assignment;
- route cleanup;
- wrong credential vs TLS failure classification;
- repeated connect/disconnect without stale PPP process/interface;
- native Windows profile lifecycle;
- Linux NetworkManager/service lifecycle;
- secret redaction.

## Current legacy/deprecation context

Current Microsoft guidance favors IKEv2/SSTP over older PPTP/L2TP families. SSTP remains a relevant compatibility protocol, but PVNetwork should still prefer modern IKEv2/WireGuard/OpenVPN options where users control both ends and requirements permit.

## Remaining v1 gaps

- exact current `sstp-client` immutable commit/tag pin;
- exact dependency/version table (PPP/TLS/event/platform libraries);
- current issue/release matrix;
- NetworkManager SSTP plugin pin/license/menu map;
- Windows exact API/profile capability matrix;
- full platform install/package matrix;
- Android/Apple feasibility.

Later `COMPLETE-REFERENCE-v2` must add wire framing/handshake, cryptography/TLS details, server implementations/installers/panels and exhaustive UI/menu/install evidence.
