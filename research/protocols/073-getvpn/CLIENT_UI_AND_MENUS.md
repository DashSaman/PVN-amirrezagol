# Cisco GETVPN — Group-Member UI and Menus

Reviewed: 2026-08-15

There is no portable consumer client UI. A group member is administered as network infrastructure.

Evidence-backed conceptual state: group/KS identity, registration protocol/mode (GDOI vs GKM/G-IKEv2 where supported), registration/authentication state, downloaded policy, group IPsec SAs, anti-replay state, rekey/lifetime, fail-close/receive-only behavior, GM removal/re-registration and diagnostics.

PVNetwork should represent KS/control-plane state separately from GM data-plane state and must not expose TEK/KEK/private credentials in logs. Consumer login/subscription/QR/Store workflows are NOT-APPLICABLE.