# WireGuard / AmneziaWG — Cryptography Reference

Review date: 2026-08-14

Status: evidence-backed reference; not implementation/certification.

## WireGuard authoritative baseline

WireGuard's official protocol documentation identifies the following primitives:

- ChaCha20 for symmetric encryption with Poly1305 authentication using the AEAD construction;
- Curve25519/X25519-style ECDH for public-key agreement;
- BLAKE2s for hashing and keyed hashing;
- SipHash24 for hashtable keys;
- HKDF for key derivation.

The official protocol description identifies the handshake construction as `Noise_IKpsk2_25519_ChaChaPoly_BLAKE2s`. WireGuard also supports an optional pre-shared key mixed into the public-key handshake. Handshakes periodically derive fresh symmetric keys so data-plane key material rotates over time.

Primary source:

- https://www.wireguard.com/protocol/

## Security/identity model relevant to PVNetwork

- Static WireGuard public keys identify peers at the protocol layer.
- The initiator is expected to know the responder's static public key before the handshake.
- The protocol is intentionally connectionless over UDP; cryptographic state is refreshed independently of a TCP-style connection lifecycle.
- Optional PSK material is an additional secret and must be treated separately from private/public key storage.
- Private keys and PSKs are secrets; public keys, endpoints and AllowedIPs are configuration metadata but can still reveal topology/identity information.

PVNetwork implication: canonical profile storage must distinguish secret key material from non-secret routing/endpoint metadata, and adapters must not serialize private keys into ordinary logs/analytics.

## AmneziaWG boundary

AmneziaWG is derived from WireGuard. Current reviewed AWG3-era project documentation adds mechanisms including:

- header protection with a `HeaderProtectionKey`;
- content padding;
- configurable timing ranges;
- junk packets before handshakes;
- altered/configurable message type/header values;
- custom signature packets;
- transport-message padding/shaping.

Primary project source:

- https://github.com/amnezia-vpn/amneziawg-go

These mechanisms must **not** be described as replacement cryptographic primitives without pinned source/spec evidence. For the current PVNetwork reference model, classify them as packet/header/content/timing obfuscation or protocol-layout changes layered into a WireGuard-derived implementation unless an exact AWG generation proves otherwise.

## AWG header-protection key

Current AWG3+ documentation exposes `HeaderProtectionKey` as a server-side value used by header-protection behavior and states that the associated S1-S4 crypto-padding values must meet minimum size requirements for that feature.

PVNetwork implications:

- this is additional sensitive configuration distinct from the WireGuard private key and optional WireGuard PSK;
- import/export and secure storage schemas need generation-aware fields rather than an unversioned opaque “extra” map;
- validation must occur before engine activation because AWG generation/parameter mismatches can fail interoperability.

## Generation/version safety rule

Never treat the names `AmneziaWG`, `AWG2`, `AWG3`, or `AWG3.1` as equivalent capability claims. Diagnostics should record at least:

- AWG config generation;
- engine/module revision;
- client wrapper revision;
- platform/native component revision where separate.

The parent `SOURCE_REVISIONS.md` already records independently pinned Go, Windows, Android and Apple components.

## Test requirements derived from crypto/config separation

Before any future support certification:

1. known-answer/interop tests against official WireGuard implementations for base WireGuard;
2. key import/export round-trip without logging private keys;
3. optional-PSK on/off matrix;
4. AWG generation-specific interop matrix;
5. header-protection-key mismatch negative test;
6. malformed/range-invalid AWG fields rejected before runtime;
7. config migration tests that never silently reinterpret old AWG fields as newer-generation semantics;
8. crash/log inspection proving private keys, PSKs and AWG header-protection keys are redacted.

## Reuse decision

- Do not implement WireGuard cryptographic primitives from scratch.
- Prefer established platform/core implementations behind a PVNetwork adapter.
- Treat AWG as a separately versioned adapter/capability family with explicit generation negotiation/config validation.
- Do not infer cryptographic equivalence or divergence from marketing names; pin and inspect source.
