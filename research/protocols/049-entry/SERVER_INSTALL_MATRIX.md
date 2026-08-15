# 049 SOCKS4 — Server install matrix

Review: 2026-08-15. Server reference: 3proxy pin `4fb5c957046c6011b5a0b45f48c1b854daf70bca`.

| Environment | Evidence-backed status | Path / boundary |
|---|---|---|
| Linux/Unix | Supported by upstream project | source/package-style build; daemon/service configuration |
| Windows | Supported by upstream project | native build and documented service install/remove |
| macOS | Supported by upstream project | documented source/build path; server use is niche |
| Docker | Supported by upstream project | upstream Docker documentation/images; bind/network/config ownership must be reviewed |
| FreeBSD/BSD | Supported family in upstream docs/build model | source build; service integration is platform-specific |
| Kubernetes/Helm | No canonical SOCKS4-specific orchestration selected | N/A for completion; generic container orchestration must not be presented as official support |
| Android/iOS | Not meaningful canonical server targets | N/A; consumer-client targets |

SOCKS4 itself is TCP/IPv4-oriented and needs no protocol-specific kernel module. Host firewall, bind address, DNS and routing remain deployment concerns. Exact distro release certification is later implementation/operations work, not a hidden reference gate.