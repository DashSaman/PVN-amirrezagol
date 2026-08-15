# GRE — Data Path and Wire Flow

Reviewed: 2026-08-15

## Base flow

```text
inner packet
  -> tunnel endpoint selects GRE interface/route
  -> prepend GRE header
  -> prepend outer delivery IP header
  -> route across outer IP network
  -> remote endpoint receives IP protocol 47
  -> validate/parse GRE header and optional fields
  -> decapsulate payload according to GRE Protocol Type
  -> route/deliver inner packet
```

RFC 2784 defines the structure as Delivery Header + GRE Header + Payload. For IPv4 payload the GRE Protocol Type is 0x0800. RFC 2890 optionally adds a 32-bit Key and/or 32-bit Sequence Number after the optional checksum area.

## Linux boundary

Linux implements GRE in `net/ipv4/ip_gre.c` at pinned kernel commit `15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`. iproute2 configures local/remote endpoints, key/checksum flags, TTL/TOS, device binding and PMTU behavior.

## MTU and routing

GRE adds an outer IP header plus GRE header and therefore reduces effective payload MTU. Linux iproute2 exposes PMTU/DF controls; tunnel routes and outer endpoint reachability remain separate concerns. Loops and TTL/hop-limit behavior matter because nested encapsulation can recurse; Linux source contains explicit tunnel recursion handling.

## Security-visible metadata

Bare GRE does not encrypt the payload. Outer source/destination, GRE framing and unencrypted inner content remain observable unless another security layer protects them.

## Separation from entry 064

No ESP/IKE phase appears in this flow. Adding IPsec changes outer processing, authentication/encryption and NAT behavior and belongs to GRE-over-IPsec entry 064.

Evidence: RFC 2784, RFC 2890, pinned Linux `ip_gre.c`, pinned iproute2 `ip-tunnel.8`.
