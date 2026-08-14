# Cryptography

Pulse is an enterprise SSL-VPN family, not a standalone new cryptographic primitive. In the independently implemented OpenConnect Pulse path, TCP transport uses IF-T/TLS, with EAP and EAP-TTLS where certificate authentication is involved; optional fast data transport is UDP-encapsulated ESP. TLS protects the outer authenticated transport and gateway certificate validation remains security-critical.

Current ICS exposes granular inbound/outbound OpenSSL cipher selection and security presets. Exact vendor cipher availability is release/configuration dependent; this dossier does not substitute application defaults for a protocol specification.

OpenConnect explicitly disables obsolete 3DES/RC4 and legacy SHA-1 certificate-validation behavior by default; enabling insecure legacy ciphers is a compatibility exception, not a recommended baseline.

Sources: https://www.infradead.org/openconnect/pulse.html ; https://www.infradead.org/openconnect/manual.html ; https://help.ivanti.com/ps/help/en_US/NSA/22.x/nsa-ics/ag/gw_sys_config.htm