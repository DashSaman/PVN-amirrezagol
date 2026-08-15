# GRE over IPsec — Client UI and Menus

Reviewed: 2026-08-15

No canonical consumer client UI exists for the GRE-over-IPsec composition. Relevant configuration comes from two layers:

1. **GRE layer** — local/remote endpoint, tunnel/interface addressing, routes, GRE key/checksum where applicable, MTU/PMTU; see entry 063.
2. **IPsec/IKE layer** — peer identity, authentication method, certificate/PSK handling, IKE/ESP proposals, traffic selectors/SA status, logs and lifecycle; see `research/upstreams/strongswan-family/reference-v2/CLIENT_UI_AND_MENUS.md`.

A future PVNetwork infrastructure editor should display these as separate sections and state that IPsec protects the GRE-carried traffic. It must not present a GRE key as an IPsec secret or imply that bare GRE alone supplies encryption.

Consumer concepts such as subscription catalog, QR import and Store onboarding are NOT-APPLICABLE to this infrastructure composition unless a selected implementation later provides them.
