# 038 VMess — data path and wire flow

Reviewed against Xray-core `v26.7.28`.

## Bounded client -> server flow

`local application / TUN / SOCKS -> client routing -> VMess outbound -> selected outer stream transport/security -> VMess server inbound -> user/auth validation -> VMess request decode -> server routing/outbound -> destination`

Local TUN/system-proxy capture and outer WebSocket/gRPC/XHTTP/TLS/REALITY choices are separate layers, not VMess wire fields.

## Current VMess request path

1. Client creates random request body IV/key and response-header byte.
2. Client constructs VMess request material: version, body IV/key, response header, options, padding/security nibble, reserved byte, command, destination address/port when applicable, optional random padding and FNV checksum.
3. Request command header is sealed using VMess AEAD with a key derived from the configured user's command key.
4. Server first reads the 16-byte AEAD AuthID and resolves/validates the user, time window and replay state.
5. Server opens the AEAD request header and extracts session keys/options/security/command/destination.
6. Server rejects duplicate session IDs, invalid checksum, invalid address and unknown security modes.
7. Payload chunks are encrypted/authenticated with AES-128-GCM or ChaCha20-Poly1305 according to the negotiated/current account behavior.
8. Response header and response body use the corresponding response-key/IV derivation and current AEAD framing.

Current server recognizes TCP, UDP and Mux request commands. UDP command support is an application-proxy semantic and does not mean the outer transport must be UDP.

## Replay/time behavior

AuthID replay prevention and the ±120-second time requirement are part of current VMess authentication behavior. Clock-skew diagnostics therefore belong in product error mapping and support tooling.
