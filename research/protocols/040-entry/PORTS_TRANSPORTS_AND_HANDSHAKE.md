# 040 Shadowsocks — ports, transports and handshake

Reviewed: 2026-08-15

Shadowsocks has no fixed protocol port; server port is configured per profile. Examples such as 8388 are examples, not constants.

Classic AEAD has no TLS-style public-key handshake. Client/server share a password/master key and exact cipher method. TCP begins with a unique random salt, derives a session subkey, then sends AEAD chunk stream. UDP independently includes a random salt per encrypted packet.

Underlying network capability is TCP and/or UDP according to server/client configuration. Optional plugins can transform/encapsulate transport but are separate executable protocols with their own config/source/license/security review.

Method mismatch, password mismatch, salt replay/uniqueness handling or AEAD authentication failure must be distinguishable from DNS/routing/plugin/local-TUN failures in diagnostics.
