# IPIP — Data Path and Wire Flow

Reviewed: 2026-08-15

## Base path

```text
inner IPv4 datagram
  -> tunnel endpoint selects IPIP interface/route
  -> prepend outer IPv4 header (Protocol = 4)
  -> route across outer IPv4 network
  -> remote tunnel endpoint receives protocol 4
  -> remove outer IPv4 header
  -> route/deliver original inner IPv4 datagram
```

RFC 2003 defines this as adding an outer IPv4 header while preserving the original IP datagram as payload. The encapsulator/decapsulator are tunnel endpoints configured out of band.

Linux implements this in pinned `net/ipv4/ipip.c`; iproute2 exposes `mode ipip`, local/remote endpoints, TTL/TOS, device binding and PMTU/DF controls.

## MTU / fragmentation

The additional outer IPv4 header reduces effective payload MTU. RFC 2003 discusses fragmentation/ICMP behavior and tunnel MTU concerns; operational designs should account for PMTU and nested encapsulation.

## Security-visible metadata

Bare IPIP does not encrypt. Outer endpoints and the inner packet are observable to an on-path party that can inspect the encapsulated traffic.

## Separation from entry 066

No IKE/ESP step appears here. IPIP-over-IPsec adds a security layer and changes wire overhead/visibility/handshake and belongs to entry 066.

Evidence: RFC 2003; pinned Linux `ipip.c`; pinned iproute2 `ip-tunnel.8`.
