# Cisco GETVPN — Data Path and Wire Flow

Reviewed: 2026-08-15

Control plane:
1. GM contacts/registers with KS/GCKS using configured group-key-management mode.
2. KS authenticates/authorizes GM and distributes group policy plus group security/key material.
3. KS periodically rekeys group members; current Cisco G-IKEv2 supports unicast or multicast rekey behavior subject to configuration/features.

Data plane:
- GMs do **not** establish pairwise IPsec tunnels with every other GM.
- Native unicast/multicast packets are protected using downloaded group IPsec policy/SAs while preserving the private-WAN routing topology rather than adding an overlay tunnel.
- Receiving GMs use the group SA/key state to authenticate/decrypt protected traffic.

Control-plane registration/rekey health and IPsec data-plane SA/anti-replay health are distinct. KS availability is critical for registration/rekey but existing group-member forwarding lifecycle depends on installed policy/key lifetimes and product behavior.