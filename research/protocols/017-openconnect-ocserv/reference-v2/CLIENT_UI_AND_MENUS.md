# OpenConnect / ocserv — Client UI / Menu Map

Review date: 2026-08-14 UTC

Reusable/reference client surfaces are already source-backed under `research/upstreams/openconnect-family/`:

- CLI `openconnect`: server/group/protocol, user/auth/token/certificate/key, proxy, trust pin, MTU/DTLS, reconnect/logging/script options;
- OpenConnect GUI: profile selection/edit/connect/disconnect, auth dialog, logs/status and tray/session behavior;
- NetworkManager-openconnect: connection profile, gateway, certificate/private-key/user/group/proxy options, secrets via NetworkManager service/secret agent, connect/disconnect/status.

PVNetwork should not copy a frontend wholesale. Reuse libopenconnect public API and map settings into the PVNetwork-owned profile/secret/session model. Protocol-core state and frontend persistence remain separate.
