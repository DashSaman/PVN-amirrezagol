# 049 SOCKS4 — Client UI and menus

Review: 2026-08-15

SOCKS4 has no canonical client GUI. curl/libcurl is API/CLI and therefore GUI menu mapping is evidence-backed N/A. OpenSSH dynamic forwarding is CLI/config driven and likewise not a standalone SOCKS consumer GUI.

PVNetwork's unified profile UI should expose only protocol-relevant fields: SOCKS4 version, proxy host, proxy port, optional USERID where the chosen server/engine supports it, and a prominent local-DNS/privacy consequence. Routing, TUN/per-app, kill switch, logs, language/theme and platform permissions belong to the product shell, not the SOCKS4 protocol object.

Import/export must preserve `socks4://` distinctly from `socks4a://`; changing between them changes DNS ownership. Error UX should distinguish local DNS failure, proxy TCP-connect failure, SOCKS reply rejection and destination-connect failure.

No third-party GUI assets or menus are copied. Multi-protocol clients may inform product UX only under their own source/license boundaries.