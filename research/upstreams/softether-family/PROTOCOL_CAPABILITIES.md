# SoftEther Family — Protocol Capability Separation

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

## Core rule

SoftEther VPN Server is a **multi-protocol server**. PVNetwork must not translate “SoftEther Server accepts protocol X” into “SoftEther client is the best client engine for protocol X.”

This file keeps entries 013, 014 and 015 technically separate and also records related compatibility modes without merging them.

## 013 — SoftEther VPN Protocol

Classification: **native SoftEther VPN client/server protocol family**.

Primary upstream candidate/reference:

`SoftEtherVPN/SoftEtherVPN`

### Product role

This is the capability for connecting a compatible native SoftEther client to a SoftEther VPN Server using SoftEther's own SSL-VPN/native session protocol.

The source contains shared client/server/session protocol logic under Cedar and separate `vpnclient` / `vpnserver` products.

### PVNetwork decision direction

If PVNetwork wants native SoftEther support, evaluate SoftEther's reusable client/core source directly rather than tunneling the profile through OpenVPN/SSTP compatibility modes.

Native SoftEther support should have its own adapter/capability because native server features, authentication, virtual adapter behavior and session semantics are not identical to OpenVPN or SSTP compatibility.

### Current gap

A clean embeddable client API/library boundary still needs exact source/API analysis. Do not approve integration solely because the repo builds a client executable.

---

## 014 — EtherIP

Classification: **Layer-2-over-IP tunnel protocol**, not an encrypted VPN by itself.

Verified SoftEther source:

`src/Cedar/EtherIP.c`

### Security classification

Raw EtherIP provides encapsulation, not confidentiality/authentication on its own. Product/UI must not present plain EtherIP as equivalent to an encrypted modern remote-access VPN.

If retained for compatibility/research, mark it as advanced/site-to-site/legacy-sensitive depending on deployment context.

### PVNetwork role

Likely lower priority for the end-user super-client UI and more relevant to:

- site-to-site/bridge scenarios;
- SoftEther server/bridge interoperability;
- router/server administration modules.

Do not expose it as a normal consumer one-click VPN option without an explicit use case.

---

## 015 — EtherIP/IPsec

Classification: **composition of EtherIP encapsulation with IPsec protection**.

Verified SoftEther source areas include both:

- `src/Cedar/EtherIP.c`
- `src/Cedar/IPsec.c`

### Product rule

Do not treat EtherIP/IPsec as one opaque unrelated protocol if product architecture already has an IPsec capability layer. The canonical model should record:

- EtherIP/L2 encapsulation role;
- IPsec/IKE protection role;
- peer/authentication/security parameters;
- platform/server compatibility.

### PVNetwork role

Mostly advanced/site-to-site interoperability. A normal consumer client may not need this in its first release, but the research scope retains it and later v2 must document server/router/client deployment paths.

---

# Related SoftEther server compatibility modes

These are relevant to SoftEther server research but are separate numbered/client-engine decisions elsewhere in PVNetwork.

## L2TP / L2TP-IPsec / L2TPv3-related support

Verified source:

`src/Cedar/L2TP.c`

and IPsec source for protected variants.

Do not merge L2TP client support into SoftEther native protocol support.

## OpenVPN compatibility

Verified source:

`src/Cedar/Proto_OpenVPN.c`

SoftEther server can implement OpenVPN-compatible server behavior. PVNetwork's OpenVPN client decision remains OpenVPN3-core-first; SoftEther OpenVPN server compatibility is valuable later for interoperability/server testing, not as the primary OpenVPN client engine.

## SSTP compatibility

Verified source:

`src/Cedar/Proto_SSTP.c`

This is server/protocol compatibility evidence. PVNetwork's SSTP client-engine decision must be researched independently.

## IPsec

Verified source:

`src/Cedar/IPsec.c`

SoftEther contains IPsec-related server compatibility logic. PVNetwork's IKE/IPsec client architecture remains a separate family and may prefer native OS/strongSwan-style components.

# Capability matrix rule for PVNetwork

Never use one flag:

`softether_supported = true`

to imply every module works.

Track at least:

| Capability | Role | PVNetwork research priority |
|---|---|---|
| SoftEther native protocol | remote access/client-server | High enough to preserve as unique coverage |
| EtherIP | L2 encapsulation | Low/advanced |
| EtherIP/IPsec | protected L2/site-to-site | Advanced |
| OpenVPN compatibility | SoftEther server compatibility | Server/interoperability reference |
| SSTP compatibility | SoftEther server compatibility | Server/interoperability reference |
| L2TP/IPsec | SoftEther server compatibility | Separate client family |
| L2TPv3-related | site-to-site/server | Advanced |
| IPsec | server compatibility/protection layer | Separate client family |

## Product UI rule

Simple Mode should not list all compatibility modes simply because one server codebase contains them.

Only expose a connection type when PVNetwork has:

- an approved client engine;
- importer/profile model;
- platform support;
- tests;
- meaningful user demand.

Server administration/compatibility features belong to the later server-management/reference layer.

## Later v2 requirements

For entries 013–015, the full-reference phase must add:

- exact server/client implementations and forks;
- installers/panels;
- OS/container install matrices;
- full server/client menu maps;
- cryptography/security classification;
- packet/data path and framing;
- ports/transport/handshake details;
- deployment topologies;
- interoperability matrix.
