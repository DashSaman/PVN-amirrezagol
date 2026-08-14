# Ivanti Connect Secure — Data Path / Wire Flow

Reviewed: 2026-08-14 UTC

Reference VPN-tunneling flow:

1. endpoint reaches ICS over HTTPS/TLS;
2. authentication realm/server validates the configured identity method;
3. role-mapping rules select a user role and restrictions/Host Checker policy;
4. role enables VPN Tunneling and resource policies select access, address/connection profile, split/full tunnel behavior, DNS/routes and related network state;
5. Pulse-compatible client establishes IF-T/TLS tunnel state;
6. client virtual adapter exchanges network traffic with the ICS VPN-tunneling service;
7. when supported/reachable, ESP can provide the accelerated data path while TLS remains the compatible control/fallback path;
8. ICS applies resource policies and forwards permitted traffic toward enterprise resources;
9. reauthentication, idle/session timeout, reconnect and cleanup follow exact role/client/gateway policy.

A valid HTTPS login is not proof of a functioning L3 tunnel; a tunnel is not proof of Host Checker compliance; and OpenConnect Pulse success is not proof of full proprietary ISAC feature parity.
