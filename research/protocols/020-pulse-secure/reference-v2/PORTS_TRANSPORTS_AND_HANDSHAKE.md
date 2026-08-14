# Ports, transports and handshake

The user-facing gateway is configured as an HTTPS URL and may specify a non-default port. Authentication begins over HTTPS/TLS. Pulse mode then uses IF-T/TLS for its TCP tunnel and may negotiate UDP-encapsulated ESP for data transport.

High-level sequence: TLS connection -> gateway authentication / realm-policy flow -> authenticated session/cookie -> IF-T/EAP tunnel setup -> IP data over IF-T/TLS; optional UDP ESP path -> keepalive/reconnect according to implementation and gateway policy.

Pulse and older Juniper Network Connect (`nc`) are different protocols even though many gateways support both and authentication cookies can be compatible. OpenConnect `--protocol=pulse` and `--protocol=nc` must remain distinct adapter capabilities.

Do not hard-code an undocumented ESP UDP port or assume all authentication methods work in OpenConnect. OpenConnect documents missing Pulse auth methods and no Pulse Host Checker/TNCC.

Sources: https://www.infradead.org/openconnect/pulse.html ; https://www.infradead.org/openconnect/manual.html