# 039 Trojan — installers/deployment projects

Reviewed: 2026-08-15

Shared Xray deployment evidence is reused from entries 037/038:

- official `XTLS/Xray-install@e741a4f56d368afbb9e5be3361b40c4552d3710d`, GPL-3.0;
- official Xray container path `ghcr.io/xtls/xray-core` (digest to be frozen during package certification);
- `MHSanaei/3x-ui@ad32144c42455696ea9f14e12168beac3e25f5d2`, GPL-3.0, major Xray/Trojan web-management reference;
- `remnawave/panel@4c222c2db180fd472d9f79a9ddf132e455fe788d`, AGPL-3.0, active Xray node/control-plane reference.

Historical standalone Trojan also includes C++/CMake/Docker/build documentation but is old and GPLv3; it is not selected as PVNetwork's closed-product engine.

Supply-chain: do not execute moving-head `curl | bash` blindly; pin installer/core/panel artifacts, protect panel DB/admin/subscription/certificate secrets, and keep core/geodata/config/panel DB/certs as separate lifecycle objects.
