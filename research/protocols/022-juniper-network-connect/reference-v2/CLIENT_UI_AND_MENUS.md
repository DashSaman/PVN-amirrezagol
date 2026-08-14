# Juniper Network Connect — Client UI / Authentication Map

Reviewed: 2026-08-14 UTC

There is no maintained current proprietary Network Connect app UI to clone. Historical authentication was strongly web-driven.

OpenConnect NC mode exposes the maintained compatibility surface:

- gateway URL / realm or usergroup selection;
- username/password/certificate/SecurID/token inputs as supported;
- browser-like HTML form authentication;
- optional externally obtained `DSID` session cookie;
- Host Checker/TNCC wrapper integration where required;
- Connect/Disconnect, tunnel status, routes/DNS, logs and certificate trust through the selected OpenConnect frontend;
- explicit protocol selector `nc` rather than `pulse`.

OpenConnect documents arbitrary HTML/JavaScript/legacy Java requirements on customized gateways; some login sequences may require an actual browser/external helper. This is an explicit capability limit, not a UI gap to hide.

Do not copy retired Juniper/Pulse trade dress or persist DSID/password/private-key secrets in plaintext diagnostics.
