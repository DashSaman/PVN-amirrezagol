# 038 VMess — ports, transports and handshake

Reviewed: 2026-08-15

## Ports

VMess has **no universal fixed listening port**. Xray inbound address/port are operator configuration. Port 443 is only a deployment choice when an outer HTTPS/TLS-like path uses it; it is not a VMess protocol constant.

## Outer transport/security composition

Xray config keeps VMess protocol settings separate from `streamSettings`. Depending on the pinned Xray version, VMess may be carried over supported stream transports such as RAW/TCP and HTTP-family encapsulations, with TLS/REALITY/other stream security configured independently. Exact combinations must come from the selected core/version capability matrix rather than from a generic claim that every VMess core supports every Xray transport.

## Current handshake/authentication sequence

At `v26.7.28` the protocol-level sequence is:

`outer transport/security establishment (if configured) -> 16-byte time-sensitive AEAD AuthID -> AEAD-encrypted VMess request header -> payload chunks using selected VMess body AEAD -> protected response header/body`

The current AuthID rejects clock skew beyond ±120 seconds and replays. VMess itself lacks Forward Secrecy according to current Xray warnings.

## Commands

Current Xray server accepts TCP, UDP and Mux request commands. TCP/UDP here identify requested proxy semantics, not the necessarily underlying socket/stream transport.

Historical VMess mode tables must not override current Xray source/config restrictions.
