# XFRM/IPsec — Client UI and Menus

Reviewed: 2026-08-15

There is no canonical portable consumer XFRM-interface UI. A Linux infrastructure editor may expose:

- interface name / XFRM interface ID;
- optional underlying interface;
- link/address/route state and statistics;
- inbound/outbound interface-ID association in the IPsec profile;
- separate IKE/IPsec identity/authentication/proposals/SA/diagnostic controls.

Important state rules: an interface can exist before the matching policies/SAs; traffic routed to an interface without matching operational policy/SAs is not a successful protected connection. Conversely policies/SAs linked to an interface ID require the matching interface for normal operation.

Account, subscription, QR catalog and Store-specific UI are NOT-APPLICABLE to the XFRM interface abstraction.
