# Data path and wire flow

Evidence-backed interoperable model:

1. Client reaches the configured HTTPS gateway/sign-in URL.
2. Authentication establishes the vendor session/cookie context; authentication method is gateway-policy dependent.
3. Pulse mode establishes the VPN data path using IF-T over TLS on the TCP path; OpenConnect documents EAP/EAP-TTLS inside this flow.
4. Where negotiated, UDP-encapsulated ESP provides the fast data path.
5. Client traffic enters the VPN virtual interface, receives gateway-assigned routing/DNS/policy, crosses the encrypted tunnel, and is decapsulated/filtered by ICS VPN Tunneling resource policies.
6. Return traffic follows the corresponding gateway policy/tunnel path. IPv4 and IPv6 are supported by Pulse mode, subject to server/client policy and version.

Host Checker/TNCC is a separate posture/authentication dependency; OpenConnect does not currently implement Pulse-mode Host Checker. Split/full tunnel and resource ACLs are gateway policy, not properties inferred from TLS alone.

Sources: https://www.infradead.org/openconnect/pulse.html ; https://help.ivanti.com/ps/help/en_US/ICS/22.x/vtcg/about-vpn-tunneling.htm ; https://help.ivanti.com/ps/help/en_US/ICS/22.x/vtcg/about-vpn-tunneling-resource-policies.htm