# 045 AnyTLS — server UI/menu maps

The canonical reference server is CLI/config oriented and has no first-party web panel. Web-menu map is therefore evidence-backed N/A for anytls-go.

Server/product controls required by current protocol/docs:
- listen endpoint and TLS certificate/key/trust configuration in a **separate TLS object**;
- one or more password-backed users according to selected implementation;
- padding scheme;
- protocol/version compatibility and client-metadata policy;
- session/idle/heartbeat behavior where implementation exposes it;
- fallback target for failed auth/probe handling where supported;
- UDP-over-TCP capability;
- logs/alerts/diagnostics with passwords, URI and TLS/API secrets redacted.

shoes current config independently exposes `type: anytls`, users/passwords, UDP enablement, padding scheme and fallback inside a separate TLS/Reality wrapper, reinforcing the protocol/TLS separation without making shoes canonical.
