# OpenConnect / ocserv — Server Control / Menu Map

Review date: 2026-08-14 UTC

ocserv has no canonical web panel; that absence is evidence-backed, not a missing invented UI.

Canonical control surfaces:

- `ocserv` service/process and config file;
- `occtl` administration/control socket;
- global/vhost listener config (`tcp-port`, `udp-port`, listen host);
- server certificate/key;
- auth methods: certificate/plain/PAM/RADIUS/GSSAPI as documented;
- groups / group selection;
- per-user / per-group config;
- IPv4/IPv6 pool, routes/no-routes/iroutes;
- DNS/split DNS/tunnel-all-DNS;
- MTU, DPD/keepalive/timeouts;
- DTLS enable/disable (`no-udp`);
- bandwidth/session limits;
- route/port restriction/firewall helpers;
- vhosts;
- logs/syslog and active session control.

Any third-party web UI would be a separate project requiring its own source/license/security review.
