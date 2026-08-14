# Cisco AnyConnect — Client UI / Menu Map

Review date: 2026-08-14 UTC

Primary proprietary behavior reference: `research/protocols/016-cisco-anyconnect/CISCO_UI_STORAGE_DIAGNOSTICS_MAP.md`.

Core VPN surface:

- VPN endpoint/profile selection;
- Connect / Disconnect;
- explicit connection/reconnect/failure state;
- authentication/browser/MFA interaction according to headend policy;
- statistics / connection details;
- advanced panel/preferences where platform/product exposes them;
- diagnostics / DART as a separate diagnostic module/action;
- administrator-managed VPN profile vs user/global preferences;
- messages/history/troubleshooting surfaces.

Cisco desktop diagnostic entry points differ by OS (Windows Advanced Window/Statistics, macOS statistics control, Linux Details); do not invent pixel-identical UI.

Cisco posture, ZTA, NVM, Umbrella, DART and other modules are not protocol-core menus. Cisco branding/icons/trade dress remain do-not-copy.

Public OpenConnect GUI and NetworkManager maps under `research/upstreams/openconnect-family/` are reusable UX/architecture references with their own licenses, not Cisco UI replicas.
