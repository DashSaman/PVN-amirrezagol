# 093 — DTLS — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: datagram TLS security/transport layer, not a standalone VPN protocol.

Decision: **`MATURE ENGINE/TLS LIBRARY CAPABILITY / NO DEDICATED VPN ENGINE`**.

For enterprise VPN use, OpenConnect already uses DTLS in supported protocol families where appropriate. PVNetwork should consume DTLS through the selected mature engine/TLS implementation rather than add a separate DTLS VPN core.

Keep DTLS version/cipher/certificate behavior, UDP reachability and fallback-to-TLS/TCP behavior as engine capabilities.

Shared evidence: `research/upstreams/transport-security-family/` and `research/upstreams/openconnect-family/`.

Later v2 adds exact standards/handshake/crypto, library implementations, data-flow/fallback behavior and platform/server compatibility.