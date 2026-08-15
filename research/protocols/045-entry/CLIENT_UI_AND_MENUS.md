# 045 AnyTLS — major client UI/menu maps

Throne is the strongest source-backed GUI reference: dedicated AnyTLS editor, typed outbound config, profile persistence/factory and subscription updater paths exist in its GPL source. It is UX/architecture evidence only by default.

A correct PVNetwork AnyTLS profile editor/import flow must expose:
1. endpoint/port;
2. reusable password stored via secure-store reference;
3. protocol/session pool controls where selected engine exposes them;
4. client metadata policy (real/custom/disabled according to compatibility policy);
5. padding scheme/capability metadata;
6. TLS SNI/certificate/CA/insecure controls in a **separate TLS section**;
7. routing/DNS/TUN/per-app outside AnyTLS;
8. import/export from `anytls://` with password redaction.

Canonical URI form is `anytls://[auth@]hostname[:port]/?...#display-name`; omitted port defaults to 443. `insecure=1` must trigger an explicit security warning.
