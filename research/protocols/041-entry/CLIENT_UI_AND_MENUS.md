# 041 Shadowsocks 2022 — client UI/menu maps

Reviewed: 2026-08-15

Primary dedicated client surface is `sslocal` configuration/CLI. Official Android is the strongest current GUI reference because its build explicitly enables the SS2022 core feature.

A correct SS2022 editor/import flow must expose and validate:
1. server and port;
2. exact `2022-*` method;
3. fixed-length base64 PSK, not a generic arbitrary password;
4. optional EIH chain `iPSK...:uPSK` only for methods that support it;
5. TCP/UDP mode and plugin as separate capabilities;
6. connect/disconnect/status/routing/VPN integration/logging;
7. secret-safe share/export.

UI must reject wrong-length/invalid-base64 keys rather than truncating, padding or running classic EVP_BytesToKey. Classic entry-040 import must remain typed as classic unless user explicitly supplies a valid SS2022 profile.
