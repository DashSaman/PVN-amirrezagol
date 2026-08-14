# OpenVPN — Ports / Transports / Handshake Stages

Reference layer: `COMPLETE-REFERENCE-v2`

Research date: 2026-08-14

State: `IN-RESEARCH` — common/default conventions and stage model are documented; exact server profile overrides always win.

## 1. Port is configurable

OpenVPN is **not bound to one mandatory port**.

The commonly associated/default historical OpenVPN service port is **1194**, usually UDP in many deployments, but servers can listen on other ports and TCP/UDP according to configuration/product policy.

PVNetwork must always use the profile/server's explicit transport/port and must not assume 1194 when absent unless the applicable import/product format defines a default.

## 2. Underlay transports

Primary OpenVPN transports:

- UDP
- TCP

Transport is part of the profile/server capability, not a different VPN protocol entry.

### UDP

Preferred by many deployments because it avoids wrapping tunneled TCP flows inside an additional reliable TCP stream.

### TCP

Useful when UDP is blocked or server policy requires it, but can perform poorly under packet loss for TCP-over-TCP workloads.

PVNetwork can support an ordered/fallback remote list only when the imported profile/server defines or product policy explicitly allows it. Never silently change UDP to TCP if the server has no corresponding listener.

## 3. Address family

Endpoint may resolve/use:

- IPv4
- IPv6

Profiles/core versions may support address-family-specific transport options.

PVNetwork should test:

- IPv4-only client network;
- IPv6-only/IPv6-preferred network where supported;
- dual-stack DNS answers;
- server DNS answer change;
- Happy-Eyeballs-like product policy only if compatible with OpenVPN core behavior;
- network handover between families.

## 4. Proxy path

OpenVPN clients may support web/SOCKS proxy connection options depending on core/client/profile/version.

A proxy creates an additional path:

```text
OpenVPN client
  → proxy connection/auth
  → OpenVPN server endpoint
```

PVNetwork must classify proxy failure separately from OpenVPN TLS/server failure and protect proxy credentials.

## 5. Handshake conceptual stages

The exact OpenVPN protocol packet/state machine is version-specific, but a product-level handshake can be represented safely as:

### Stage A — endpoint resolution

- parse/select remote;
- resolve DNS if hostname;
- choose address/transport/port;
- select physical interface/route.

### Stage B — transport establishment

UDP:

- prepare connected/unconnected UDP transport behavior according to core;
- send/receive OpenVPN control packets.

TCP:

- establish TCP socket;
- enter OpenVPN framed stream behavior.

Proxy:

- establish proxy tunnel/session first where configured.

### Stage C — optional pre-TLS control protection

If profile uses `tls-auth`, `tls-crypt` or another supported control-channel protection mode, the client/server process the appropriate static-key protection before/during TLS control-channel packet handling.

Failure categories:

- missing/wrong static key;
- direction/mode mismatch;
- unsupported control-key mode;
- packet rejected before TLS.

### Stage D — TLS control-channel negotiation

Conceptual work:

- TLS protocol negotiation;
- server certificate authentication;
- optional mutual client certificate authentication;
- cryptographic key exchange;
- establish protected control channel.

Exact TLS messages are handled by the selected TLS backend and OpenVPN protocol layer.

### Stage E — user/additional authentication

Depending on server/product:

- username/password;
- OTP/MFA challenge;
- external plugin/auth backend;
- SSO/provisioning flows at product layer.

This may occur as part of/after protected control-channel establishment depending on configuration.

### Stage F — OpenVPN option / capability negotiation

Client/server exchange/validate session capabilities/options such as:

- data-channel cipher capabilities;
- tunnel parameters;
- pushed route/DNS/options;
- compression compatibility;
- protocol-feature/version-dependent state.

### Stage G — data-channel key establishment

Keying material/session state for the symmetric OpenVPN data channel is established through the authenticated control-channel protocol.

### Stage H — platform tunnel setup

- create/configure TUN/TAP or platform VPN interface;
- assign address;
- install routes;
- apply DNS;
- activate DCO state if negotiated/supported;
- preserve underlay route to server.

### Stage I — Connected / data traffic

Only after both secure data-channel state and effective platform routing/interface configuration are ready should PVNetwork report `Connected`.

## 6. Handshake error taxonomy

Map low-level errors to at least:

- invalid profile;
- DNS resolution failed;
- network unreachable;
- transport blocked/refused/timed out;
- proxy authentication/CONNECT failed;
- control static-key mismatch;
- TLS protocol/cipher failure;
- server certificate invalid;
- client certificate/key invalid;
- user authentication failed;
- MFA/user action required;
- data-cipher negotiation failed;
- pushed option unsupported/rejected;
- virtual adapter permission/driver failure;
- route/DNS configuration failure;
- DCO incompatibility/failure;
- server closed session.

## 7. Keepalive / liveness

OpenVPN configurations can use keepalive/ping/restart behavior to detect dead peers/NAT mappings and trigger reconnect.

PVNetwork should not show keepalive knobs in Simple Mode. Advanced configuration should reflect actual profile/core semantics and server push behavior.

Tests:

- server silent drop;
- NAT mapping timeout;
- Wi-Fi loss;
- roaming to cellular;
- sleep longer than keepalive timeout;
- server restart;
- reconnect storms/backoff.

## 8. Rekey / renegotiation

OpenVPN sessions periodically rekey/renegotiate according to protocol/configuration policy.

Product state should remain stable where traffic remains valid, perhaps exposing `Rekeying` only in diagnostics.

Test rekey under:

- continuous TCP traffic;
- UDP traffic;
- DCO;
- userspace data path;
- high packet loss;
- network change.

## 9. DCO handshake relationship

DCO does not replace the TLS/control-channel handshake.

Conceptually:

```text
OpenVPN userspace control/auth handshake
  → negotiate data-channel algorithms/keys
  → install supported data-channel state in DCO
  → kernel/driver handles supported data packets
```

If the profile/cipher/feature cannot be offloaded, userspace fallback or explicit incompatibility occurs depending on product/core policy.

## 10. Firewall requirements

Server firewall must allow the configured OpenVPN listener transport/port.

Additionally, routed deployments require explicit forwarding/NAT/routing rules as described in server install/topology docs.

Do not open TCP and UDP on multiple ports “just in case”. Open only configured listeners.

## 11. Access Server web ports are not OpenVPN tunnel ports

OpenVPN Access Server also exposes administrative/client web services. Their HTTPS ports/listeners are a **product management/provisioning surface**, not automatically the same as the OpenVPN tunnel listener.

PVNetwork documentation and firewall UI must distinguish:

- VPN tunnel listener(s);
- Admin Web UI;
- Client Web UI;
- API/management endpoints.

Never label all Access Server open ports as “VPN port”.

## 12. Client-side ephemeral ports

The client typically uses OS-assigned local source ports unless explicitly configured. Do not add unnecessary inbound client firewall rules for normal remote-access OpenVPN sessions.

## 13. Port/protocol fingerprinting / censorship context

OpenVPN can operate on configurable ports/transports, but merely moving to a common port does not make OpenVPN indistinguishable from normal HTTPS.

PVNetwork must not market port 443 alone as censorship-proof obfuscation.

If additional transport/obfuscation layers are used, model them explicitly as separate components and test security/compatibility.

## 14. Handshake test receipt

```text
Server product/version:
Server listener IP/family:
Transport:
Port:
Proxy path:
Control protection: none/tls-auth/tls-crypt/other
TLS backend/version:
Server certificate identity:
Client certificate mode:
User auth/MFA:
Negotiated data cipher:
DCO active?:
Tunnel address:
Routes/DNS:
Time to control-channel established:
Time to tunnel ready:
Connected result:
Failure stage if any:
```

## Remaining v2 gaps

- selected OpenVPN protocol-version exact control packet/opcode/state references;
- Access Server exact version-specific listener/web-port defaults;
- precise current tls-crypt extension modes/version matrix;
- exact transport framing differences by protocol version;
- packet captures/lab receipts.
