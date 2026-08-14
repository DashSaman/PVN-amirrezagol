# OpenConnect / ocserv — Data Path / Wire Flow

Review date: 2026-08-14 UTC

Reference flow:

1. OpenConnect connects to ocserv by HTTPS/TLS.
2. Authentication forms/certificates/tokens are processed according to server policy.
3. Authenticated session/cookie state is established.
4. CSTP tunnel is created over TLS/TCP.
5. ocserv allocates tunnel state/interface/address and returns route/DNS/MTU/policy parameters.
6. Client OS virtual tunnel ↔ libopenconnect ↔ CSTP ↔ ocserv worker ↔ server TUN/routing/firewall path carries traffic.
7. If enabled/reachable, DTLS UDP is negotiated for data; TLS/CSTP remains available for control/fallback.
8. DPD/reconnect/re-auth/session timeout and server cleanup govern lifecycle.

ocserv uses privileged/unprivileged process separation; its control socket/worker architecture and OS routing/firewall are server management boundaries, not application-layer tunnel payload.
