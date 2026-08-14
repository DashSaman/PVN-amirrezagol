# Palo Alto GlobalProtect — Ports / Transports / Handshake

Review date: 2026-08-14 UTC

## Control / SSL path

- Portal and Gateway use HTTPS/TLS; TCP **443** is the common/default service port, while portal/gateway addressing may be configured with explicit nondefault ports where supported.
- Portal and gateway authentication/configuration are distinct phases.
- The SSL VPN data tunnel uses the established GlobalProtect SSL/TLS service when that mode is selected/fallback is allowed.

## IPsec path

- GlobalProtect IPsec data uses **ESP (IP protocol 50)** according to the selected IPsec tunnel/crypto policy.
- Palo Alto documentation describes TLS/SSL as the negotiation/key-exchange control path for GlobalProtect even when IPsec is used. Therefore this dossier does **not** invent UDP 500/4500 IKE requirements or misclassify GlobalProtect as normal IKEv2/IPsec.
- OpenConnect GP source likewise identifies the GP UDP/data role as `ESP` and implements separate portal/gateway HTTPS exchanges.

## Important non-tunnel distinction

Any separate vendor notification/MFA UDP service documented for a deployment is not automatically a GlobalProtect tunnel port and must be modeled separately.

Exact firewall/NAT behavior is topology/version specific and remains a later certification concern.
