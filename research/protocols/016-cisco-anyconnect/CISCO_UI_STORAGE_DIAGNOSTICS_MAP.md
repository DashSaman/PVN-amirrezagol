# 016 — Cisco Secure Client — UI / Storage / Diagnostics Reference Map

Review date: 2026-08-14

Scope: current **official Cisco documentation** used as proprietary behavioral/UI reference for entry 016. This is not source-code visibility and not permission to copy Cisco UI/assets.

## 1. Primary connection surface

Current Cisco Secure Client documentation retains a user-facing connection workflow around VPN connection state, host/profile selection and Connect/Disconnect behavior. Cisco also exposes CLI connect/disconnect/statistics commands on supported desktop platforms.

Official CLI/UI reference:

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/admin/guide/cisco-secure-client-admin-guide-new/customize-secure-client-intro/r_use_the_ac_cli_commands.html

PVNetwork lesson:

- connection state must be authoritative and shared across main UI/tray/CLI-equivalent control surfaces;
- `Disconnected`, `Connecting`, `Connected`, `Reconnecting`, `Disconnecting` and failure states remain explicit product states;
- statistics are diagnostics, not the source of connection truth.

## 2. Advanced / statistics / diagnostics surfaces

Cisco's current troubleshooting documentation maps the desktop statistics/diagnostic path differently by OS:

- Windows: gear icon -> Advanced Window -> Statistics -> AnyConnect VPN;
- macOS: Statistics control near the gear/application UI;
- Linux: Details from the user GUI.

Current statistics/diagnostic actions include exported statistics, reset of collected counters and DART diagnostics where the corresponding module/package is present.

Official reference:

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/admin/guide/cisco-secure-client-admin-guide-new/troubleshoot-anyconnect-intro/c_gather_information_for_troubleshooting.html

Cisco's Advanced Panel is component-oriented and can expose component-specific preferences, statistics, security/product state and message history. Do not treat optional posture/module panels as core AnyConnect VPN protocol requirements.

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/admin/guide/cisco-secure-client-admin-guide-new/configure-posture-intro/c_advanced_panel.html

PVNetwork lesson:

- keep simple connect UI shallow;
- put statistics/diagnostics/advanced enterprise capability detail behind a secondary surface;
- separate VPN-core state from posture/other optional module state;
- support a sanitized diagnostic export rather than exposing raw engine logs by default.

## 3. DART / log collection reference

Cisco's Diagnostics and Reporting Tool (DART) is a separate diagnostic collection surface for installation/connection troubleshooting on supported desktop platforms. Cisco documentation distinguishes normal statistics from richer diagnostic bundles and platform-specific log collection.

PVNetwork must not copy DART code, assets or bundle format. Behavioral lessons only:

- diagnostic collection is explicit user/admin action;
- default vs custom bundle contents should be distinguishable;
- bundle destination/contents should be visible;
- sensitive data requires a PVNetwork-owned redaction/privacy policy;
- install/uninstall logs are a separate failure class from normal VPN-session logs.

## 4. Profile / preference ownership

Cisco's current profile-editor documentation distinguishes administrator-managed VPN profiles from user/global preference state. Users do not generally edit the administrator VPN profile directly.

Official profile reference:

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/admin/guide/cisco-secure-client-admin-guide-new/profile-editor-intro/c_the_anyconnect_vpn_profile.html

Current official preference-path documentation records desktop user/global preference files including:

- Windows user: `%LOCALAPPDATA%\Cisco\Cisco Secure Client\VPN\preferences.xml`
- Windows global: `%ALLUSERSPROFILE%\Cisco\Cisco Secure Client\VPN\preferences_global.xml`
- macOS/Linux user: `$HOME/.vpn/.anyconnect`
- platform global paths under Cisco installation directories.

Official update/preferences reference:

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/admin/guide/cisco-secure-client-admin-guide-new/deploy-anyconnect-intro/c_updating_anyconnect_software_and_profiles.html

PVNetwork lesson:

- separate admin/provisioned policy from user preferences;
- separate profile metadata from reusable credentials and transient authenticated-session state;
- profile import/management must not silently merge conflicting security policy;
- user-visible preference persistence must be explicit and clearable.

## 5. Network/transport reference visible in current Cisco docs

Cisco's current administrator documentation lists the common AnyConnect/Secure Client transport ports separately from IKEv2/IPsec:

- TLS: TCP 443;
- optional SSL redirection: TCP 80;
- optional/recommended DTLS: UDP 443;
- IKEv2/IPsec: UDP 500/4500.

This supports the research rule that entry 016's TLS/DTLS AnyConnect-compatible path and Cisco's optional IKEv2 capability must not be collapsed into one transport assumption.

## 6. Customization / localization / visual assets

Cisco documents custom GUI text/message and icon/logo replacement capabilities for deployments. This is evidence that branding/localization is an explicit product layer, not evidence that Cisco-provided assets are reusable.

Official references:

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/admin/guide/cisco-secure-client-admin-guide-new/customize-secure-client-intro/c_customize_the_anyconnect_gui_text_and_messages.html
- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/admin/guide/cisco-secure-client-admin-guide-new/customize-secure-client-intro/c_create_custom_icons_and_logos.html

PVNetwork rule:

- Cisco icons/logos/trade dress remain reference-only / do-not-copy;
- PVNetwork uses owner-supplied branding and its own localized UI;
- Persian/RTL behavior must be tested independently rather than inferred from Cisco localization support.

## 7. Custom UI API evidence

Cisco documents a Secure Client API/custom-UI path for desktop systems. This is proprietary product extensibility evidence only; it is **not** the selected PVNetwork integration path and does not make Cisco private internals reusable.

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/admin/guide/cisco-secure-client-admin-guide-new/customize-secure-client-intro/c_write_and_deploy_custom_applications_with_the_anyconnect_api.html

PVNetwork selected research direction remains the public OpenConnect API behind a product-owned Enterprise Adapter, not coupling PVNetwork to proprietary Cisco UI binaries/APIs.

## 8. v1 gate implications

This file closes proprietary-reference evidence for:

- UI/menu/navigation/state mapping at research level;
- desktop platform differences;
- profile/preferences persistence ownership;
- logs/statistics/diagnostics behavior;
- localization/customization/asset references;
- transport/port behavioral reference;
- proprietary-source limitations.

It does **not** establish Cisco source-tree/build internals, Cisco internal CI, product interoperability or PVNetwork implementation.