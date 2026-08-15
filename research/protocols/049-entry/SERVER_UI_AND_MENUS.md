# 049 SOCKS4 — Server UI and menus

Review: 2026-08-15

SOCKS4 defines no canonical GUI. The selected 3proxy server is primarily configuration/CLI/service driven; its current docs also expose an `admin` web interface for administration/statistics. Do not invent a protocol-standard dashboard.

Relevant management surfaces for a SOCKS4 deployment are: service/listener bind and port; auth/ACL; users/USERID policy where used; parent/chaining; DNS resolver/cache policy; logging/rotation/statistics; traffic/bandwidth limits; service lifecycle; and configuration backup/restore. 3proxy documentation separates the SOCKS service from `admin`, DNS proxy and other proxy services.

Security boundary: the admin surface is a server-management concern and must not be conflated with the PVNetwork consumer UI. Management exposure, credentials, roles and TLS depend on the selected deployment and are not properties of SOCKS4 itself.