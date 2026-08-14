# Fortinet FortiGate SSL VPN — Client UI / Menu Map

Review date: 2026-08-14 UTC

FortiClient 7.4.7 official remote-access documentation provides the core tunnel UI model:

- `Remote Access` surface;
- `Add New VPN` / edit/delete profile where policy permits;
- VPN Type = `SSL VPN`;
- Connection Name / Description;
- Remote Gateway, including multiple gateways where supported;
- `Customize port` (default 443, configurable);
- certificate requirements / certificate selection;
- username/password and MFA/challenge flow according to gateway policy;
- EMS-provisioned vs personal profile distinction;
- Connect / Disconnect;
- connection state, duration and tunnel details;
- certificate/server-trust warnings according to policy;
- diagnostics/log/support tools as separately packaged modules.

Official configuration reference: https://docs.fortinet.com/document/forticlient/7.4.7/administration-guide/205286/configuring-an-ssl-vpn-connection

Password storage is user-scoped according to current FortiClient documentation; secrets/cookies/cert private keys must not be exported in diagnostics.

OpenConnect Fortinet mode uses OpenConnect CLI/API/frontends already mapped under `research/upstreams/openconnect-family/`. Do not clone FortiClient trade dress or infer proprietary posture/EMS behavior from OpenConnect.
