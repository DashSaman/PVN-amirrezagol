# innernet data path and wire flow

1. `innernet-server` holds coordination state: peers, CIDRs, associations and observed/overridden endpoints.
2. A joining peer redeems a one-time invitation, creates a new WireGuard key pair and registers it.
3. Peers fetch the subset of network state visible to them under CIDR policy.
4. Peer payload traffic enters the WireGuard interface and is encrypted/authenticated by WireGuard.
5. Where endpoints are reachable, payloads flow directly peer-to-peer; the coordination server is not automatically an application-data relay.
6. CIDR associations determine which peer address groups may communicate; route/interface policy integrates that overlay with the host.
7. Endpoint discovery may use the address observed by the server; admins can override endpoints when NAT discovery is unsuitable.

Visible outer metadata is the ordinary WireGuard/UDP endpoint metadata; innernet does not claim an extra obfuscation layer.
