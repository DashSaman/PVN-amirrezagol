# 038 VMess — server installers and deployment projects

Reviewed: 2026-08-15

VMess shares the Xray runtime/deployment ecosystem with entry 037, so this gate reuses the same pinned deployment evidence rather than re-researching it.

## Official XTLS paths

### XTLS/Xray-install

- repo: `https://github.com/XTLS/Xray-install`
- pin: `e741a4f56d368afbb9e5be3361b40c4552d3710d`
- license: GPL-3.0

The pinned README documents systemd-oriented installation on CentOS/Debian/OpenSUSE-class systems, separate OpenRC guidance for Alpine/Gentoo, Xray binary/config/geodata paths, upgrade, geodata-only update, logrotate, removal and purge.

Upstream one-line `curl | bash` examples are convenience paths, not PVNetwork's supply-chain baseline. Production automation must pin/review the installer/package and verify artifacts.

### Official container

Xray upstream lists `ghcr.io/xtls/xray-core`. A digest was not frozen during research; package-freeze work must pin an immutable digest before deployment certification.

## Major management references

### 3X-UI

- `MHSanaei/3x-ui@ad32144c42455696ea9f14e12168beac3e25f5d2`
- GPL-3.0
- web panel around Xray-core with VMess/VLESS/Trojan/etc.
- direct-host and Docker-oriented deployment, inbound/client/subscription/routing/node/status management.

### Remnawave

- `remnawave/panel@4c222c2db180fd472d9f79a9ddf132e455fe788d`
- AGPL-3.0
- Xray management/node/control-plane reference; separate database/auth/subscription/node lifecycle.

Panels are not VMess protocol implementations by themselves. Their GPL/AGPL code, database, credentials, certificates, subscriptions and update chains are distinct license/security boundaries.

## Upgrade / uninstall / rollback

Core binary, geodata, generated runtime config, canonical PVNetwork profile, panel database and certificates are separate lifecycle objects. Xray-install can remove while preserving config/logs or purge them. Rollback must restore a known-good core plus compatible configuration/schema; downgrading only the binary is not assumed safe.
