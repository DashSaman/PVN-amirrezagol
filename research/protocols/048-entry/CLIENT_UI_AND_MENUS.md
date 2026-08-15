# 048 Snell — client UI/menu maps

Reviewed: 2026-08-15

Current Surge proxy-policy configuration is the authoritative client surface:

`Proxy-Snell = snell, host, port, psk=password, version=...`

Documented controls:
- host/port;
- required `psk`;
- required explicit generation `version`;
- v4 optional `reuse`;
- v4 optional HTTP obfuscation: `obfs=http`, `obfs-host`, `obfs-uri`;
- UDP relay support by generation;
- optional proxy chaining through common `underlying-proxy`;
- optional ShadowTLS composition with separately versioned/passworded ShadowTLS layer;
- v6 is selected with `version=6` and has no client traffic-shaping fields because its profile is derived from PSK;
- runtime details/logs are Surge application features, not Snell wire fields.

PVNetwork UI requirements:
- PSK stored through secure-store reference and redacted from logs/export;
- generation required, never guessed;
- v6 clearly marked beta;
- v4/v5/v6 capability differences visible;
- third-party parser defaults must not overwrite original Surge-import semantics.
