# OpenVPN Connect — Official Product / UX Reference

Research classification: **behavior/UX reference + public OpenVPN 3 core relationship**. Do not assume the complete OpenVPN Connect application source/UI is reusable merely because OpenVPN 3 is public.

## Official documentation evidence
Primary current documentation references:
- User guide: `https://openvpn.net/connect-docs/user-guide.html`
- Connection profiles: `https://openvpn.net/connect-docs/connection-profiles.html`
- Import profile: `https://openvpn.net/connect-docs/import-profile.html`
- Edit profile: `https://openvpn.net/connect-docs/edit-a-profile.html`
- Windows settings: `https://openvpn.net/connect-docs/app-settings-windows.html`
- Windows release notes: `https://openvpn.net/connect-docs/windows-release-notes.html`

## Product relationship to OpenVPN 3
OpenVPN 3 upstream states that it is used in production as the core of OpenVPN Connect clients across iOS, Android, Linux, Windows and macOS. Treat this as the public engine relationship. The Connect UI/product layer remains a separate behavior/reference subject.

## Main user-facing information architecture observed from official docs
Current documentation exposes these major concepts:
- connection/profile-focused home experience;
- **My Profiles**;
- profile add/import;
- profile edit/delete;
- **Settings**;
- optional proxy management;
- certificate/token management;
- authentication guidance including basic credentials, MFA and SAML-related flows;
- troubleshooting/logging documentation;
- OS-specific guidance for Windows, macOS, Android and iOS.

## Profile import UX
Official documentation describes profile import from:
- URL;
- local `.ovpn` file;
- drag/drop on Windows/macOS;
- double-click file association on Windows/macOS;
- bundled profile scenarios for supported enterprise products.

The import flow conceptually separates acquisition/import from connection. This is a useful PVNetwork UX lesson: imported configuration should become a managed profile object rather than being treated as an ephemeral raw file.

## Profile editing
Official docs describe an edit path through `Menu -> My Profiles -> Edit`. Depending on profile type, examples include profile display name, server override and proxy association. An active profile cannot simply be edited in place according to the documented behavior.

PVNetwork lesson: profile editing should have clear state rules when a profile is active and should distinguish editable local metadata from server-managed/generated fields.

## Settings map observed in current Windows documentation
Application-level settings include examples such as:
- Device ID / client UUID display;
- protocol preference: adaptive / TCP / UDP;
- connection timeout / continuous retry choices;
- launch behavior such as start app / connect latest / restore connection.

Advanced settings documented include examples such as:
- security compatibility level;
- TLS minimum behavior;
- DCO enablement where supported;
- IPv6 blocking option;
- DNS fallback/local resolver controls.

This settings map is a **reference**, not a list PVNetwork should copy verbatim. PVNetwork should expose only capabilities that are implemented and relevant to its normalized multi-core model.

## Current release/product lesson
Official Windows release notes for version 3.9.0 dated 2026-06-08 document pre-login connection functionality and authentication improvements. This demonstrates that enterprise lifecycle features can require deep OS integration and should not be designed as ordinary GUI toggles.

## Storage/source limitation
The official product documentation describes profile management behavior but does not by itself establish the internal on-disk database/credential implementation. Do not invent internal storage details. Those remain an evidence gap unless supported by official technical documentation/public source.

## Assets/screenshots
Official Connect documentation contains current product images/flow diagrams. Store **links and descriptions**, not copied artwork, unless reuse rights are confirmed. PVNetwork must use its own brand assets.

## PVNetwork lessons
- Make profile import a first-class workflow.
- Keep profile management distinct from the connection session.
- Separate simple defaults from advanced compatibility/security settings.
- Avoid exposing engine-specific terminology unless needed.
- Treat pre-login/system-session features as platform-specific projects.
- Maintain clear behavior for active-profile editing.
- Keep official-client compatibility regression tests separate from UI design.

## Reuse decision
- OpenVPN Connect UI/application code: **REFERENCE-ONLY unless OpenVPN provides a separately usable/public component and license evidence**.
- OpenVPN 3 core: evaluated separately as `REUSE-CANDIDATE`.
- Official Connect screenshots/assets: **REFERENCE-ONLY by default**.

## Remaining research gaps
- OS-specific menu/settings differences across Android/iOS/macOS;
- current diagnostic/log export UX for each platform;
- secure profile/credential persistence evidence;
- Accessibility/RTL/localization behavior;
- current issue/support patterns and common user failures;
- Store-specific product behaviors.

Status: `IN-RESEARCH`.