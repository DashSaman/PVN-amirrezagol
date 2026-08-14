# Cisco AnyConnect — Server / Headend Control Map

Review date: 2026-08-14 UTC

## Cisco ASA selected headend map

Current ASA 9.24 configuration documentation exposes the relevant administration concepts through CLI and Cisco management tooling:

- `webvpn` enablement on selected interfaces;
- Secure Client / AnyConnect package image assignment;
- tunnel groups / connection profiles;
- group policies;
- authentication / AAA / certificate and SAML-related policy as configured;
- client VPN profiles and policy distribution;
- address pools, DNS and routes/split-tunnel policy;
- TLS/SSL and DTLS settings;
- DPD, keepalive/reconnect and session policy;
- client software/profile updates;
- logs/session inspection and disconnect actions.

Official current guide baseline: Cisco Secure Firewall ASA VPN CLI Configuration Guide 9.24, AnyConnect VPN Client Connections.

## FTD boundary

Cisco current deployment docs confirm FTD as a Secure Client web-deploy headend. Exact FMC/FDM menu layout is management-release-specific and is not cloned or generalized into ASA UI. This dossier records functionality/ownership, not a fake uniform Cisco panel.

## ocserv compatible server

ocserv has no canonical web panel. Configuration is file/service/CLI (`ocserv`, `occtl`) based; this is a separate public compatible server management model.
