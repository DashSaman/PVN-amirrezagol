# 041 Shadowsocks 2022 — server UI/menu maps

Reviewed: 2026-08-15

`shadowsocks-rust` is CLI/config/service oriented and has no canonical first-party web panel. Web-menu gate is therefore evidence-backed N/A for the primary implementation; the configuration surface remains applicable.

Required SS2022 server/profile controls:
- endpoint/listener and TCP/UDP mode;
- exact method: required AES variants and only implementation-supported optional methods;
- fixed-length base64 PSK, never arbitrary classic password derivation;
- optional EIH chain / iPSK / uPSK and multi-user mapping;
- plugin/runtime/logging/manager settings as separate layers;
- secret-safe export/diagnostics.

Current rust config explicitly validates base64 key encoding and exact key length and parses EIH form `iPSK1:...:iPSKn:uPSK` only for supported AES methods. No panel UI is claimed unless it preserves those semantics exactly.
