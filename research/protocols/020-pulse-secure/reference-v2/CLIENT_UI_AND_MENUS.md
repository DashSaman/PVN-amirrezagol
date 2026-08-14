# Client UI and menus

The current vendor UI is Ivanti Secure Access Client; Pulse Secure Client is the historical branding. Ivanti documents both Classic UI and New-UX during the transition.

Desktop recoverable flow: launch client -> connection list -> add/edit connection -> Name + Server URL (`https://hostname[:port][/sign-in-page]`) -> Save -> connect/authenticate -> status/disconnect. Enterprise deployments may preconfigure/import connections; Linux tooling documents `/opt/pulsesecure/bin/pulseUI` and `jamCommand /ImportFile`.

Authentication/connection behavior can include realm/role selection, password/certificate/smart-card/SAML/MFA depending on gateway policy. Credential Provider integration can establish a tunnel before Windows domain logon. Mobile has separate iOS/Android UX and MDM/per-app/always-on capabilities; do not flatten those screens into desktop UI.

No undocumented context menu, TV/D-pad UI or RTL behavior is claimed.

Sources: https://help.ivanti.com/ps/help/en_US/ISAC/22.X/ag-22.X/using_ui.htm ; https://help.ivanti.com/ps/help/en_US/ICS/22.x/vtcg/credential-provider-auth-for-ics.htm ; https://help.ivanti.com/ps/help/en_US/ISAC/22.X/ios-ag.pdf