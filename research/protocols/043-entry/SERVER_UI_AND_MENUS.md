# 043 Hysteria2 — server UI/menu maps

Official Hysteria2 is CLI/config driven; no canonical first-party web panel is required. Web-menu map is evidence-backed N/A for the primary engine.

Current server config families include:
- listen endpoint;
- TLS cert/key or ACME including DNS providers;
- QUIC receive windows/idle/stream limits/PMTU/socket behavior;
- bandwidth/congestion policy;
- auth backend/config;
- traffic statistics;
- ACL/outbound/resolver;
- UDP enablement;
- Salamander obfs;
- HTTP/3 masquerade content/file/proxy behavior;
- logging/service controls.

A PVNetwork UI must keep auth, TLS/trust, obfs, masquerade, congestion/rate, QUIC and routing as separate sections and capability-gate platform/version-specific fields.
