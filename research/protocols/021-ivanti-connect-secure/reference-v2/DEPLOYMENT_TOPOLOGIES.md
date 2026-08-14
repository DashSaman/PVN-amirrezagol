# Ivanti Connect Secure — Deployment Topologies

Reviewed: 2026-08-14 UTC

Covered current-product patterns:

1. Internet-facing ICS remote-access appliance/VA with ISAC desktop clients.
2. Split tunnel by role/resource policy and configured split-tunneling networks.
3. Full tunnel by disabling split tunneling so endpoint traffic traverses the VPN path.
4. IPv4/IPv6 resource-policy/tunnel combinations according to exact appliance/client support.
5. Certificate/password/MFA/SAML/realm-specific authentication.
6. Host Checker/posture-gated role mapping and access restrictions.
7. Centrally provisioned/preconfigured ISAC via vendor delivery/enterprise software distribution.
8. Mobile iOS/Android/ChromeOS clients with Store/MDM policy as a separate lifecycle.
9. OpenConnect `--protocol=pulse` compatibility for the supported authentication/tunnel subset.
10. Separate IKEv2 access only when deliberately selected; never as Pulse protocol equivalence.

HA/cluster/virtual-appliance topology is vendor-platform/release specific and must be certified against the selected ICS build rather than inferred from generic VPN assumptions.
