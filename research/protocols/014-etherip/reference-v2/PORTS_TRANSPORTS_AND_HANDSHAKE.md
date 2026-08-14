# EtherIP — Ports, Transport and Handshake

Review date: 2026-08-14 UTC

- Encapsulation: IP directly, not TCP and not UDP.
- IPv4 Protocol field: **97 decimal** for EtherIP (RFC 3378 / IANA assignment documented by RFC).
- EtherIP header: 16 bits; version **3**, reserved bits **0**.
- Payload: Ethernet/IEEE 802.3 MAC frame without FCS.
- No protocol-defined TCP/UDP listener port.
- No cryptographic/session handshake in raw EtherIP.
- Peer reachability, outer-address configuration and implementation-specific mapping/control are deployment concerns, not a wire handshake.

Firewalls and observability must distinguish IP protocol number 97 from UDP/TCP port 97.
