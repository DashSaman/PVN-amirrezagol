# WireGuard / AmneziaWG v2 — Data Path and Wire Flow

Status: architecture/reference evidence; not packet-capture certification.

## WireGuard baseline

WireGuard is a UDP tunnel using a Noise_IK-derived authenticated handshake. The operational path is best modeled as:

`application packet -> OS routing decision -> WireGuard interface -> AllowedIPs peer selection -> encrypted WireGuard UDP packet -> network -> remote UDP listener -> authentication/decryption -> remote WireGuard interface -> remote routing/firewall/NAT -> destination`

The reverse path follows the peer's cryptokey-routing and endpoint state rather than a separate client/server protocol role.

## Control/configuration plane versus data plane

- Linux: `wg`/`wg-quick` or NetworkManager/systemd configure the kernel implementation; packet encryption/encapsulation occurs in the data plane.
- Userspace: `wireguard-go` supplies the data-plane implementation while control tooling configures it.
- Windows: WireGuard Windows/service/UI configures the Windows implementation; official embedding guidance recommends the embeddable DLL service for applications rather than directly binding WireGuardNT.
- Apple: WireGuardKit is the supported embedding boundary.
- Android: the official tunnel library is the embedding boundary.

Source inventory: <https://www.wireguard.com/repositories/> and <https://www.wireguard.com/embedding/>.

## Handshake and endpoint behavior

The previously captured protocol reference establishes:

1. handshake initiation/response over UDP;
2. authenticated session-key establishment using WireGuard's Noise construction;
3. encrypted transport data messages;
4. periodic rekey behavior;
5. endpoint roaming: authenticated packets can update the runtime endpoint for a peer;
6. optional persistent keepalive to maintain NAT/firewall mappings when needed.

Configured endpoint and runtime learned endpoint must therefore be distinguished in diagnostics.

## AllowedIPs has two directions of meaning

For outbound traffic, AllowedIPs participates in selecting the peer to which a destination should be encrypted. For inbound authenticated traffic, it constrains which inner source addresses are acceptable for a peer. PVNetwork UI/documentation must not reduce it to a generic “route list” without preserving this security/routing dual role.

## Routing peer / gateway topology

A typical Internet-exit deployment adds OS functions outside WireGuard itself:

`client app -> client WG -> Internet UDP -> gateway WG -> IP forwarding -> firewall/NAT -> public Internet`

Return traffic:

`Internet -> gateway conntrack/NAT -> route to WG peer -> encrypted UDP -> client WG -> application`

NAT, forwarding and DNS are deployment policy, not properties of the WireGuard cryptographic protocol.

## AmneziaWG delta

AmneziaWG preserves the WireGuard-derived tunnel model while adding generation-specific packet-layout/obfuscation behavior. The reference layer must keep these concepts separate:

- underlying tunnel/routing model;
- AWG junk/padding/header/signature/timing features;
- generation-specific parameters and header-protection behavior;
- interoperability constraints between client/server generations.

Do not draw a single timeless AWG packet diagram and label it universal. Any production packet capture must state exact AWG client/server versions/generations.

## Observability points for implementation tests

Future certification should capture at least:

1. interface state before/after activation;
2. configured peer endpoint and AllowedIPs;
3. runtime latest-handshake and endpoint state;
4. outer UDP 5-tuple without exposing private key material;
5. inner route selection;
6. gateway forwarding/NAT rule hit counters where gateway mode is used;
7. DNS resolver path separately from tunnel path;
8. teardown showing routes/interface/service are removed or restored;
9. AWG generation/parameter set and interop result.

## Security boundary

Never log private keys or preshared keys. Public keys, interface names, anonymized endpoints and aggregate handshake timestamps may be retained in test evidence if the environment permits. AWG header-protection or shared obfuscation material must be treated as secret configuration where applicable.

## Residual evidence

Packet captures and route/service receipts for exact supported OS/client combinations remain required in implementation/device validation. This document provides the architecture contract against which those receipts should be checked.
