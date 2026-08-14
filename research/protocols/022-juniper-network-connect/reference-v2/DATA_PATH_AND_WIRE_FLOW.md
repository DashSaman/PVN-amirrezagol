# Juniper Network Connect — Data Path / Wire Flow

Reviewed: 2026-08-14 UTC

OpenConnect's current NC/oNCP documentation maps the legacy wire flow:

1. client connects to the SSL VPN gateway over HTTP(S);
2. browser-like authentication posts forms and obtains a session/authentication cookie such as DSID;
3. the cookie is used to establish the actual Network Connect VPN connection over HTTP(S);
4. gateway supplies legacy IPv4 address/routing/DNS configuration;
5. client creates/configures its tunnel interface and exchanges IP packets through the HTTPS/oNCP path;
6. client attempts to establish UDP accelerated transport using ESP;
7. if ESP is unavailable, traffic continues on the HTTPS path;
8. disconnect/logout should clean the Juniper session and local routes/DNS state.

OpenConnect documents **no IPv6 support for the Juniper NC protocol**. That is a protocol/implementation boundary distinct from Pulse mode, which added IPv6 support.
