# Happ — Client/Product Research Note

Reviewed public repository: `Happ-proxy/happ-desktop` on 2026-08-14.

## Important source-availability finding
The public desktop repository currently contains only `README.md` and a `release` file at its root. GitHub metadata does not report a repository license, and the repository is primarily used to publish/download desktop release packages.

Therefore, do **not** classify Happ Desktop as an open-source client codebase suitable for direct forking based on this repository alone.

## Product facts from its public README
The README publishes builds for Windows, macOS and multiple Linux package formats and links Android/iOS distribution. It says Happ uses Xray core and lists support for VLESS/Reality, VMess, Trojan, Shadowsocks and Socks. It also states that user data is kept on-device and points to a separate issue-reporting service.

## PVNetwork research classification
- Product/UX/reference value: **YES**.
- Public full desktop source confirmed in the reviewed repo: **NO**.
- Direct code-reuse candidate from this repo: **NO / NOT ESTABLISHED**.
- Underlying Xray core: research separately from the Happ application.

## Required next research
- identify whether canonical source exists in another official repository;
- audit Android repository separately for source availability vs binary-release hosting;
- map UI/settings/screens from official product evidence;
- review issue service, release history and platform bugs;
- determine storage format and configuration handling only from legitimate public evidence;
- do not use decompiled third-party APK repositories as a substitute for licensed source.

This note prevents future AI agents from incorrectly saying “Happ is open source” merely because a GitHub release repository exists.