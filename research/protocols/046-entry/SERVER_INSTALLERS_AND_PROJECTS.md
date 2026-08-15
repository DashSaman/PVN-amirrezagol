# 046 ShadowTLS — installers/deployment projects

Official tree contains Rust/Cargo build, Cargo.lock, Docker/Compose, GitHub CI/release workflows, examples, SIP003 support and client/server binary. Release v0.2.25 publishes Linux musl and macOS binaries across multiple architectures; no official native Windows release asset is claimed.

Current HEAD fixes are newer than the tagged binary, so production must choose deliberately between a reviewed source build and tagged release and record artifact hash/SBOM/license notices.

sing-box is a broader deployment/runtime alternative but GPL/additional-condition licensed. SIP003 enables composition with Shadowsocks/plugin ecosystems; each inner proxy/plugin retains its own source/license/security lifecycle.
