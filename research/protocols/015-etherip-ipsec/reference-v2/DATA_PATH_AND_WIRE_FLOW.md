# EtherIP/IPsec — Data Path and Wire Flow

Review date: 2026-08-14 UTC

Conceptual protected path:

`Ethernet frame`
→ `EtherIP encapsulation (version 3)`
→ `IPsec policy / SA selection`
→ `ESP protection`
→ `outer IP` (or UDP-encapsulated ESP when NAT-T/backend requires it)
→ peer IPsec verification/decryption
→ recovered EtherIP packet
→ EtherIP decapsulation
→ bridge / Virtual Hub forwarding.

Control plane:

`IKE authentication/negotiation`
→ `IPsec SA creation/rekey/delete`
→ data-plane ESP processing.

SoftEther source evidence additionally ties EtherIP construction to its IKE/IPsec server objects and accounts for IP/UDP/ESP/tunnel overhead in MSS calculations. On Windows/non-Windows the reviewed runtime also coordinates host OS/kernel IPsec ownership to avoid conflicts.

A successful IKE SA alone does not prove EtherIP forwarding, and a working EtherIP mapping alone does not prove packets are protected.
