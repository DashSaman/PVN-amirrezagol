# GRE — Server / Peer Install Matrix

Reviewed: 2026-08-15

| Environment | State | Path / requirements | Evidence boundary |
|---|---|---|---|
| Linux distributions with GRE-enabled kernel | Supported | Kernel GRE tunnel support + iproute2; privileged network administration; outer IP reachability; firewall must permit IP protocol 47 | Linux `net/ipv4/ip_gre.c`; iproute2 `ip-tunnel.8` |
| Cisco IOS XE 17.x routers | Supported on documented platforms/features | Native tunnel interface; source/destination; `tunnel mode gre ip`; routing | Cisco IOS XE 17 tunnel guide |
| Juniper Junos OS supported routing platforms | Supported with platform-specific limits | Native GRE/tunnel service interface; routable endpoints; platform limits apply | Juniper GRE/tunnel-services guide |
| Docker/Podman | Not a GRE implementation itself | A privileged/container network namespace can use host/kernel GRE only if required capabilities/modules are available; no canonical GRE container is part of the protocol | Infrastructure N/A treatment |
| Kubernetes | Not a canonical GRE server deployment target | GRE may be used by networking components, but bare GRE has no canonical Helm/operator installation contract | Infrastructure N/A treatment |
| Windows Server | Not promoted here | Generic bare-GRE endpoint capability was not established from a current authoritative Windows source in this audit; do not infer it from PPTP/GRE support | Explicit UNKNOWN |
| macOS/iOS/Android consumer endpoints | Not promoted here | No evidence-backed product requirement for a native bare-GRE consumer tunnel endpoint in this dossier | Explicit N/A for PVNetwork consumer UX |

CPU architecture is primarily inherited from the selected OS/router platform; this dossier does not claim an independent GRE binary architecture matrix.
