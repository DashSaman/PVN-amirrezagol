# Ivanti Connect Secure — Client UI / Menu Map

Reviewed: 2026-08-14 UTC

Current ISAC endpoint concepts:

- connection list and managed/manual profile distinction;
- Add/Edit/Delete Connection where administrator policy permits;
- connection Name and Server URL;
- Connect / Disconnect / reconnect and explicit session state;
- realm/authentication prompts including password, certificate, token, SAML/browser or other configured methods;
- New UI vs Classic UI transition according to exact release/platform;
- Host Checker/posture prompts when server policy requires them;
- assigned tunnel/network state, diagnostics and support logs;
- client provisioning/update state;
- mobile MDM/Store policy as a separate mobile surface.

Pulse Secure Client branding has been replaced by Ivanti Secure Access Client; UI/trade dress is proprietary and must not be copied.

OpenConnect CLI/GUI/NetworkManager surfaces remain the separately licensed public frontend model documented under `research/upstreams/openconnect-family/`. OpenConnect Pulse mode has no Pulse Host Checker/TNCC implementation, so that UI capability must remain unavailable/unsupported rather than synthesized.
