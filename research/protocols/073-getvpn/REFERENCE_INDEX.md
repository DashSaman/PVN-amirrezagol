# Cisco GETVPN — Reference Index

Reviewed: 2026-08-15

Entry files: server implementations/installers/matrix/UI, GM matrix/UI, cryptography, data path, ports/handshake, topologies, audit, existing `V1_RESEARCH.md`.

Authoritative references:
- RFC 9838 (2025) G-IKEv2, obsoletes RFC 6407: https://www.rfc-editor.org/rfc/rfc9838.html
- RFC 6407 legacy GDOI: https://www.rfc-editor.org/rfc/rfc6407.html
- RFC 8263 GDOI GROUPKEY-PUSH ACK.
- Cisco IOS XE 17 GETVPN G-IKEv2 guide, updated 2026-04-24: https://www.cisco.com/c/en/us/td/docs/routers/ios-xe/security-vpn/security-vpn/m_sec-get-vpn-gikev2.html
- Cisco IOS XE 17 GETVPN guide: https://www.cisco.com/c/en/us/td/docs/routers/ios/config/17-x/sec-vpn/b-security-vpn/m_sec-get-vpn.html
- Cisco GETVPN GDOI Bypass guide (UDP 848 evidence).
- Cisco GETVPN security advisory `cisco-sa-getvpn-rce-g8qR68sx`, showing historical GDOI/G-IKEv2 parser attack risk and patched-software requirement.

Key boundary: current Cisco supports GDOI and G-IKEv2/GKM, but current Cisco page references a standards draft; do not assert exact RFC 9838 conformance without explicit evidence.

Next: 074 REALITY.