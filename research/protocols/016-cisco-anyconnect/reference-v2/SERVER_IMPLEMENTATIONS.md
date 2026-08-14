# Cisco AnyConnect / Secure Client — Server Ecosystem

Review date: 2026-08-14 UTC

Entry 016 preserves the distinction between Cisco's proprietary headends/clients and public compatible implementations.

## Cisco authoritative headends

- **Cisco Secure Firewall ASA** — proprietary headend. Current reviewed server documentation baseline: ASA VPN CLI Configuration Guide **9.24**, AnyConnect VPN Client Connections. Official guide: https://www.cisco.com/c/en/us/td/docs/security/asa/asa924/configuration/vpn/asa-924-vpn-config/vpn-anyconnect.html
- **Cisco Secure Firewall Threat Defense (FTD)** — proprietary Cisco headend/deployment family. Current Cisco Secure Client deployment documentation explicitly describes web deployment from Secure Firewall Threat Defense as well as ASA.

Cisco server/client source code is not public and is **not a reuse candidate**. Vendor documentation/release behavior is reference-only.

## Public compatible ecosystem

- **OpenConnect v9.21** client/library, canonical GitLab tag commit `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1. Selected public compatible client/engine reference.
- **ocserv 1.5.0** compatible server, canonical GitLab tag commit `49f9956eeeffd613e4bcac3f6450c682ec21e75a`, GPLv2+. It is an OpenConnect/AnyConnect-compatible server, not Cisco server source and not proof of complete Cisco appliance equivalence.

Reuse direction: public OpenConnect API behind a PVNetwork adapter. Cisco appliances/clients remain proprietary behavioral/certification references.
