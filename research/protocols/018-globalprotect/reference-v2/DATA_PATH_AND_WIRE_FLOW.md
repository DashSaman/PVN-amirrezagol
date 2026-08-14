# Palo Alto GlobalProtect — Data Path / Wire Flow

Review date: 2026-08-14 UTC

Reference flow:

1. endpoint contacts the **Portal** over HTTPS/TLS and obtains managed GlobalProtect configuration/gateway information according to policy;
2. endpoint contacts the selected **Gateway** over HTTPS/TLS and performs gateway authentication/session establishment;
3. gateway returns network/tunnel parameters and the endpoint creates the virtual tunnel path;
4. if IPsec is enabled and successfully established, ESP is the primary protected data path;
5. when the configured policy permits fallback and IPsec cannot be established, the SSL VPN tunnel can carry data instead;
6. in current releases that support/configure **IPsec Only**, failure to establish IPsec is not silently converted into SSL fallback;
7. gateway applies routes/split-tunnel/DNS/security policy and optional HIP/posture decisions according to license/platform configuration;
8. re-authentication, gateway selection, update/reconnect and session termination follow portal/gateway policy.

OpenConnect GP mode publicly exposes the portal/gateway split and compatible login/config endpoints, but its implementation is not evidence for every proprietary posture/management feature.

A successful portal login is not proof of gateway tunnel establishment; a successful TLS gateway session is not proof that the data path is using IPsec.
