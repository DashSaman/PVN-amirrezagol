# 042 Hysteria v1 — installers/deployment projects

Reviewed: 2026-08-15

Canonical v1.3.5 tree contains official `install_server.sh`, `Dockerfile`, `docker-compose.yaml`, `build.sh`, `build.ps1`, Taskfile and release workflows. The v1.3.5 release publishes `hashes.txt` and multi-platform binaries.

These are frozen legacy assets, not current moving deployment infrastructure. Remote installer use must be pin-and-review; production must verify the published hash list/artifact and record build tags.

License boundary is unusual and mandatory: source is MIT, but an executable built with `-tags gpl` **must** be distributed under GPLv3. Dependency/SBOM obligations are separate.

No current third-party panel is promoted as canonical v1 management without an exact v1-capable version pin.
