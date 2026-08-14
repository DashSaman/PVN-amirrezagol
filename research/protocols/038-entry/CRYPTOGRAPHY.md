# 038 VMess — cryptographic design

Reviewed against `XTLS/Xray-core v26.7.28` / commit `5ca6f4b7d4dc20a881d4330e498892697627ec0c`.

## Current security boundary

Xray's current config source explicitly warns: **VMess has no Forward Secrecy** and recommends **VLESS Encryption**. That warning is retained as a protocol limitation; an outer TLS/REALITY layer can provide separate transport security but does not change VMess's own key-establishment history.

## VMess AEAD authentication/header

Current `proxy/vmess/aead/authid.go` builds a 16-byte AuthID containing timestamp, random data and CRC, encrypted with an AES block cipher key derived from the user's command key. The decoder:

- validates CRC;
- rejects negative/invalid timestamps;
- requires client/server UTC to be within **120 seconds**;
- uses an anti-replay filter and rejects replayed AuthIDs.

`TimedUserValidator` maps configured users to command-key-derived AEAD decoders.

The command/request header is sealed/opened through `proxy/vmess/aead` rather than sent as the classic plaintext VLESS-style header.

## Per-session body keys and payload AEAD

`proxy/vmess/encoding/client.go` generates per-session random 16-byte request body key and 16-byte IV plus response-header byte. Response body key/IV are derived from request key/IV using SHA-256 and truncation as implemented by Xray.

Current body security modes accepted by `infra/conf/vmess.go` are:

- AES-128-GCM;
- ChaCha20-Poly1305;
- `auto`, which resolves to a supported current mode.

Chunk nonces increment per record. Optional current experiments include authenticated chunk lengths; SHAKE128 can mask lengths/generate padding. The ChaCha key-expansion helper uses MD5 internally to expand a 16-byte body key to 32 bytes; this is exact protocol implementation behavior, **not** a password hashing recommendation.

Response header uses KDF-derived AES-GCM protection in current source.

## Historical/config compatibility boundary

Historical VMess protocol pages mention older security codes/modes. Current Xray config source does not accept legacy `none`/AES-CFB as current account security choices. PVNetwork must preserve imported legacy metadata for diagnosis/migration, but must not silently re-enable deprecated modes merely because old protocol documentation lists them.

Do not implement VMess cryptography from scratch. Use a pinned reviewed core and version capability matrix.
