# 039 Trojan — server UI/menu maps

Reviewed: 2026-08-15

Bare Xray has no first-party web panel; server config is JSON/CLI/API driven. Trojan inbound fields include users/clients with password/level/email, listener, fallbacks, and separate stream transport/security.

Current `infra/conf/trojan.go` also accepts historical `flow` fields but rejects non-empty Trojan Flow as removed; current Xray emits a deprecation warning for `Trojan (with no Flow, etc.)` in favor of `VLESS with Flow & Seed`.

Selected panel reference: 3X-UI -> Dashboard -> Inbounds -> Add/Edit Inbound -> Trojan -> listener/users/password -> transport/security/fallback options -> client quota/expiry -> routing/outbounds/subscriptions/nodes/settings. Panel labels are version-specific and are not PVNetwork's canonical schema.

Remnawave supplies a second independent Xray node/config/subscription/auth/log management model. Bare core, panel controls and Trojan wire semantics remain distinct.
