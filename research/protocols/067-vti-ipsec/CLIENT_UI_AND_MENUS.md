# VTI/IPsec — Client UI and Menus

Reviewed: 2026-08-15

There is no canonical portable consumer VTI client UI. A Linux infrastructure editor may expose VTI interface/endpoint/mark/route state separately from IKE/IPsec identity/security/SA state.

Evidence-backed UI boundaries:

- VTI section: local/remote endpoint, mark, link state, interface address/routes, statistics.
- IPsec section: authentication/credentials, proposals, traffic selectors/policies, SA state, diagnostics/lifecycle.
- Explicit warning: VTI marks are selectors, not cryptographic secrets; interface-up is not equal to protected-SA-up.

Account/subscription/QR/Store flows are NOT-APPLICABLE to the VTI abstraction itself.
