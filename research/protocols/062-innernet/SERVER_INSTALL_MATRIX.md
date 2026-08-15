# innernet server install matrix

| Environment | State | Evidence-backed path / boundary |
|---|---|---|
| Linux | Supported/primary | Cargo/source; systemd service documented; WireGuard kernel support or wireguard-go required. |
| Arch Linux | Supported packaging | `pacman -S innernet` documented upstream. |
| Debian/Ubuntu | Community packaging | upstream points to `tommie/innernet-debian`; treat as separate trust boundary. |
| macOS | Officially tested overall | Homebrew client path documented; server use is technically buildable but not promoted here as a canonical production-server target. |
| OpenBSD | Experimental | upstream explicitly calls support experimental. |
| Windows | Unknown/not claimed | no official supported path in reviewed upstream. |
| Docker | Test/development evidence | upstream provides Docker-based tests; not promoted as canonical production deployment. |
| Kubernetes/Helm | Not claimed | no canonical path found in reviewed upstream. |

Reviewed against `v2.0.0` and main `1ba6154b6ebacd68dfe79c3a4f6273fd3e8dea35`.
