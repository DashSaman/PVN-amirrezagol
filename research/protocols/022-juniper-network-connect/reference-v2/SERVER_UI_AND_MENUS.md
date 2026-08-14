# Juniper Network Connect — Server / Control UI Map

Reviewed: 2026-08-14 UTC

A current standalone NC-specific admin UI is evidence-backed N/A because the proprietary client/protocol is retired from modern vendor endpoint support.

Historical/current lineage concepts relevant to a legacy gateway are:

- authentication realms/servers and role mapping;
- VPN tunneling / Network Connect role enablement;
- network/address/route/split-tunnel resource policy;
- Host Checker/TNCC policy;
- session/log/monitoring;
- legacy NC protocol enable/disable state if exposed by the exact appliance version;
- replacement/current ISAC/Pulse tunneling policy must remain distinct from legacy NC wire state.

Current ICS admin surfaces are documented in entry 021, but are not copied here as proof of current NC support.

For a PVNetwork compatibility profile, UI should show `Legacy Network Connect/oNCP`, exact gateway/version evidence, NC wire capability state, posture/TNCC requirement and migration warning rather than a generic “Ivanti VPN” toggle.
