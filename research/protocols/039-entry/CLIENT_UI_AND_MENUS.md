# 039 Trojan — client UI/menu maps

Reviewed: 2026-08-15

v2rayN and v2rayNG provide the major open-source UI references. Trojan flow:

1. add/import Trojan profile or subscription;
2. edit endpoint/port and Trojan password secret;
3. configure TLS/security/server-name/certificate policy and supported outer transport separately from protocol identity;
4. select active profile;
5. set routing/system-proxy/TUN/DNS as client-layer concerns;
6. connect/disconnect and inspect status/logs;
7. update subscription/app/core separately.

PVNetwork must never store a reusable Trojan password only because the wire sends `hex(SHA224(password))`; the plaintext secret is needed to derive the token and belongs in protected credential storage. Share links/config/logs need redaction. Insecure certificate validation must be explicit/high-risk, never an invisible compatibility default.
