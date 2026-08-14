# Deployment topologies

Applicable patterns:

- enterprise remote-access client -> ICS/Pulse Connect Secure gateway -> protected network;
- split tunnel or full tunnel controlled by role/resource policy;
- hardware or virtual appliance gateway;
- active/active or other vendor-supported clustered/HA deployment with shared/managed licensing;
- multi-site cluster policy where release-supported;
- mobile per-app/on-demand/always-on variants managed by MDM where documented.

Management plane (admin console/licensing/configuration), authentication/control plane (realm/role/policy/session setup) and encrypted data plane (IF-T/TLS and optional UDP ESP) are separate concerns.

This entry is not a peer-to-peer mesh or generic site-to-site routing protocol; those rows are N/A unless a specific vendor feature is separately evidenced.

Sources: https://help.ivanti.com/ps/help/en_US/ICS/22.x/clcg/cluster_licensing.htm ; https://help.ivanti.com/ps/help/en_US/ICS/22.x/vtcg/configuring-vpn-tunneling.htm ; https://help.ivanti.com/ps/help/en_US/ISAC/22.X/ios-ag.pdf