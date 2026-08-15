# 042 Hysteria v1 — server implementations / ecosystem

Reviewed: 2026-08-15

Canonical legacy implementation: `apernet/hysteria` tag `v1.3.5`, commit `57c5164854d6cfe00bead730cce731da2babe406`, tree `f337850416be8834f2276118e0ce8a2630bd67ee`, released 2023-06-12.

This entry is **Hysteria 1.x legacy only**. Current upstream README explicitly links Hysteria 1.x as legacy while active development targets Hysteria2. No Hysteria2 source/config/wire claim is inherited here.

Source layout at the pin: Go 1.20 `app` + `core`, custom `apernet/quic-go` fork, `core/cs` client/server/protocol, `core/pktconns` UDP/wechat/faketcp/obfs adapters, congestion control, PMTUD, ACL and platform integration.

PVNetwork decision: compatibility/import target only by default; if shipped, use exact legacy runtime/source/build-tag/dependency pin and label generation=v1.
