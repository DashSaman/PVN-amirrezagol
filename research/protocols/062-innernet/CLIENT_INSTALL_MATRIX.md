# innernet client install matrix

| Target | State/path |
|---|---|
| Linux | Officially tested; WireGuard required; Arch package, Cargo/source, and community Debian/Ubuntu builds documented. |
| macOS Intel/Apple Silicon | Officially tested overall; Homebrew tap `tonarino/innernet/innernet`; WireGuard userspace support as needed. |
| OpenBSD | Experimental upstream support. |
| Windows | No official supported client path claimed in reviewed upstream. |
| Android / Android TV | No canonical client/package claimed. |
| iOS/iPadOS | No canonical client/package claimed. |
| Flatpak/Snap/AppImage | No canonical package claimed. |

Cargo installs are manually updated or may use `cargo-update`; omitting `--locked` reduces dependency determinism relative to upstream CI-tested lock state.
