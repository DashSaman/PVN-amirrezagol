# 038 VMess — server implementations / project ecosystem

Reviewed: 2026-08-15

## Primary implementation

**XTLS/Xray-core** is the primary VMess implementation selected for PVNetwork reference work.

- repository: `https://github.com/XTLS/Xray-core`
- license: MPL-2.0
- reviewed safe tag: `v26.7.28`
- tag commit: `5ca6f4b7d4dc20a881d4330e498892697627ec0c`
- moving `main` observed during this campaign: `7d214f8b094f75322fa3990f8aadad1c912f24f5` (activity evidence only, not the frozen candidate)
- advisory floor: GHSA-5wf9-h793-w73c is fixed from `v26.7.11`; older `v26.3.27` remains historical packaging evidence only.

Canonical implementation areas at `v26.7.28`:

- `proxy/vmess/`
- `proxy/vmess/aead/`
- `proxy/vmess/encoding/`
- `proxy/vmess/inbound/`
- `proxy/vmess/outbound/`
- `infra/conf/vmess.go`

Current Xray supports VMess inbound/server and VMess outbound/client/next-hop roles. Transport/security, routing, DNS, local TUN/system proxy, API and logging remain separate Xray layers.

## Ecosystem boundary

V2Ray-family and multi-core applications also implement/support VMess, but parser acceptance does not establish exact interoperability with every current Xray extension/default. PVNetwork therefore treats VMess as its own compatibility-target adapter and never silently rewrites it to VLESS.

Preferred product boundary:

`PVNetwork canonical profile -> VMess adapter -> pinned reviewed core -> separately selected transport/security adapter`

Do not implement VMess cryptography from scratch and do not couple product UI directly to Xray JSON.
