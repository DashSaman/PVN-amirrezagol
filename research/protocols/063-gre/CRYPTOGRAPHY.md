# GRE — Cryptography

Reviewed: 2026-08-15

## Protocol boundary

Bare GRE has **no intrinsic confidentiality, peer authentication, key exchange, certificate/PSK system, AEAD, replay-protection cryptography or forward secrecy**. RFC 2784 defines encapsulation and an optional one's-complement checksum; a checksum is an integrity/error-detection field, not cryptographic authentication. RFC 2890 adds optional Key and Sequence Number fields, but the Key is a traffic-flow/context identifier and the RFC does not define cryptographic derivation or authentication for it.

Therefore:

- a GRE `key` must never be represented as an encryption key or credential;
- GRE checksums must never be described as message authentication;
- sequence numbering must not be described as cryptographic anti-replay;
- traffic carried by bare GRE remains visible to an on-path observer except for whatever protection the payload itself provides.

## Security composition

When confidentiality/authentication is required, GRE is commonly composed with another security layer. **GRE over IPsec is entry 064 and is intentionally not merged into this entry.** Its cryptographic design belongs to IPsec/IKE evidence, not bare GRE.

## Authoritative evidence

- RFC 2784 §2 (header/checksum) and §6 (security considerations): https://www.rfc-editor.org/rfc/rfc2784.html
- RFC 2890 §2 (Key/Sequence extensions) and §3: https://www.rfc-editor.org/rfc/rfc2890.html
- iproute2 GRE key/checksum configuration: pinned `iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, `man/man8/ip-tunnel.8`.
