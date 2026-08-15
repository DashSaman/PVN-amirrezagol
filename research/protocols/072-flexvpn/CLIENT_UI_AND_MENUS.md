# Cisco FlexVPN — Client UI and Menus

Reviewed: 2026-08-15

FlexVPN defines no portable consumer GUI. Remote-access UI belongs to the selected IKEv2/AnyConnect/native client; router-side UI is IOS XE administration.

A PVNetwork profile should expose generic IKEv2 fields first (server/identity/authentication/certificates or PSK/EAP, routes/DNS where supported) and only explicit Cisco extension/capability fields where official evidence exists. It must not mimic Cisco branding or claim unsupported CFG/AAA behavior.

Client UI state should separate IKE/SA status, authorization/configuration results and routing/interface state.