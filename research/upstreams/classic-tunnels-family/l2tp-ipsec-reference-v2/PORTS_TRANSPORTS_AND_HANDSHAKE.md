# L2TP/IPsec — Ports, Encapsulation and Session Establishment

Review date: 2026-08-14

Entry: 008 L2TP/IPsec.

The wire sequence is layered. A successful IPsec SA does not mean the L2TP tunnel is established, and a successful L2TP tunnel does not mean PPP/user/network-layer negotiation is complete.

## 1. Outer IPsec/IKE transport

Reuse the exact IPsec transport model from the completed 004–007 dossier:

- UDP 500 — IKE control traffic;
- UDP 4500 — IKE/NAT-T and UDP-encapsulated ESP where applicable;
- native ESP — IP protocol 50 when not UDP encapsulated.

RFC 3193's standardized L2TP/IPsec composition was defined against IKEv1/Quick Mode. Exact native stacks must be tested for their actual IKE version/profile behavior rather than inferred from generic IPsec support.

## 2. L2TPv2 transport

RFC 2661 registers UDP destination port **1701** for L2TP over UDP/IP.

Important detail from RFC 2661:

- the tunnel initiator may use UDP source port 1701 **or an ephemeral port**;
- the responder receives the initial packet on destination UDP 1701 and may itself use a selected response port according to the protocol/session behavior.

Therefore a product/firewall must not hard-code the false assumption that both sides always use UDP 1701 as source and destination for the whole tunnel.

## 3. L2TP/IPsec filter/SA binding

RFC 3193 binds the IPsec protection to the L2TP endpoints/UDP port values and discusses dynamically created filters when source ports are dynamic.

Security rule:

- L2TP/IPsec traffic must be accepted as protected L2TP only when it arrives through the expected IPsec SA/policy and the IP/UDP socket values correspond to the intended L2TP tunnel;
- do not permit a parallel clear UDP/1701 path to bypass required IPsec protection.

## 4. High-level voluntary remote-access sequence

Typical legacy client-to-LNS/gateway sequence:

### Stage 0 — endpoint reachability

- resolve server;
- obtain route;
- UDP 500/4500 reachability;
- credentials/trust available.

### Stage 1 — IKE / IPsec protection establishment

Historically RFC 3193 uses:

`IKEv1 Phase 1`

`-> IKE/ISAKMP SA`

`-> Quick Mode / Phase 2`

`-> ESP transport-mode SA(s) protecting L2TP UDP traffic`

Some products may implement proprietary/newer variations; they require exact evidence.

### Stage 2 — L2TP control connection

RFC 2661 control-connection message sequence includes:

- `SCCRQ` — Start-Control-Connection-Request;
- `SCCRP` — Start-Control-Connection-Reply;
- `SCCCN` — Start-Control-Connection-Connected.

The L2TP control channel uses reliable sequencing/retransmission semantics over UDP at the L2TP layer.

### Stage 3 — L2TP session/call establishment

For the common incoming-call model used by remote-access-style L2TP, control messages include:

- `ICRQ` — Incoming-Call-Request;
- `ICRP` — Incoming-Call-Reply;
- `ICCN` — Incoming-Call-Connected.

RFC 2661 also defines outgoing-call messages (`OCRQ/OCRP/OCCN`) for other deployment models. Do not assume every L2TP topology uses the same call direction.

### Stage 4 — PPP Link Control

PPP LCP establishes/configures the point-to-point link.

Per RFC 1661, PPP link operation proceeds by LCP configuration before normal network-layer traffic.

### Stage 5 — optional/required PPP authentication

If the negotiated profile requires authentication, the peers run the selected PPP authentication protocol, for example PAP/CHAP/MS-CHAPv2 according to backend/server policy.

This user authentication occurs **inside the already established L2TP/IPsec composition** in the normal secured remote-access model.

### Stage 6 — PPP Network Control Protocols

PPP NCPs configure network-layer operation such as IP addressing/protocol parameters.

### Stage 7 — user traffic

Conceptual outbound encapsulation:

`IP payload`

`-> PPP`

`-> L2TP data header/session`

`-> UDP`

`-> IPsec ESP transport protection`

`-> optional UDP encapsulation for ESP/NAT-T`

`-> IP network`

The peer reverses those layers and then routes/forwards the resulting PPP/IP traffic.

## 5. RFC 3193 timing dependency

RFC 3193 states that when an IPsec Phase 2 SA bundle is not already present to protect the initial `SCCRQ`, sending the SCCRQ should trigger/request IKE to establish the necessary SAs; if the protection cannot be established, the L2TP packet must be dropped.

It also recommends coordination so L2TP retransmission timers do not race against slow IPsec SA creation.

PVNetwork diagnostic stages should therefore distinguish:

- `ProtectingL2tpTransport`
- `L2tpControlStarting`
- `L2tpSessionStarting`
- `PppLinkNegotiating`
- `PppAuthenticating`
- `PppNetworkConfiguring`
- `Connected`

## 6. NAT traversal and multiple clients

There are two interacting NAT/port domains:

### IPsec NAT-T

Outer IKE/ESP uses UDP 4500 when NAT traversal is active.

### L2TP UDP source port

Inside the protected flow, L2TP may use source port 1701 or an ephemeral port.

Current NetworkManager-l2tp documentation records a practical interoperability tradeoff:

- some servers reject clients that do not originate L2TP from source port 1701 even though dynamic/ephemeral source ports are standards-compatible;
- multiple clients behind the same NAT can be difficult when they all use the same source port 1701;
- the plugin exposes an “ephemeral source port” compatibility option.

This must become an explicit advanced compatibility capability/test dimension, not an undocumented magic retry.

## 7. L2TP control reliability

L2TP control messages include sequence numbers/windows/retransmission handling. Data packets do not use the same reliable control-message semantics.

Operational implication:

- a working UDP socket does not imply the control connection is healthy;
- expose timeout/retransmission/control-state errors separately from PPP/data errors.

## 8. L2TP tunnel vs session IDs

L2TP has separate tunnel/control-connection identity and per-session/call identity. A single tunnel can carry multiple sessions in appropriate deployments.

PVNetwork internal diagnostics should preserve:

- tunnel ID;
- session ID;
- peer endpoint/UDP port;
- control state;
- PPP state;
- IPsec SA state.

Do not use one generic `connectionId` as the only technical identifier.

## 9. PPP state sequence

RFC 1661 describes PPP link operation as:

- Link Dead;
- Link Establishment via LCP;
- optional Authentication;
- Network-Layer Protocol configuration via NCP;
- Link Termination.

For L2TP/IPsec, the PPP link exists logically inside the L2TP session. A PPP auth failure should not be mislabeled as an IPsec/IKE failure.

## 10. Disconnect/teardown ownership

A clean disconnect may involve multiple layers:

1. stop user traffic / PPP NCP;
2. terminate PPP link/session;
3. send L2TP call/tunnel disconnect control messages as appropriate (`CDN`, `StopCCN`);
4. remove L2TP runtime state/ports;
5. delete IPsec ESP SAs/policies;
6. delete IKE SA;
7. restore routes/DNS/firewall/native profile state.

Crash recovery must also clean stale state when orderly messages are impossible.

## 11. Firewall model

A server/operator checklist must distinguish:

- UDP 500 — IKE;
- UDP 4500 — NAT-T/IKE/encapsulated ESP where used;
- ESP protocol 50 — if native ESP is used;
- L2TP UDP 1701 — **must be protected/filtered as intended by the L2TP/IPsec policy**, not generally exposed as an unprotected VPN endpoint;
- server forwarding/NAT for assigned PPP addresses;
- RADIUS/AAA ports only if an external AAA backend is selected;
- management UI/SSH/API separately.

## 12. MTU/overhead

The packet may accumulate:

- PPP framing;
- L2TP header;
- UDP/IP;
- ESP transform overhead;
- optional NAT-T UDP overhead;
- outer network headers.

Therefore PMTU/fragmentation/MRU/MTU testing is mandatory. Do not copy one fixed MTU from a blog or old sample to every platform.

Accel-PPP's example config and classic pppd/xl2tpd stacks expose MTU/MRU controls; those are operational tuning inputs, not protocol constants.

## 13. Suggested normalized failure states

- `IKE_UNREACHABLE`
- `IPSEC_AUTH_FAILED`
- `IPSEC_NO_PROPOSAL`
- `IPSEC_POLICY_INSTALL_FAILED`
- `L2TP_PORT_OR_POLICY_MISMATCH`
- `L2TP_CONTROL_TIMEOUT`
- `L2TP_TUNNEL_REJECTED`
- `L2TP_SESSION_REJECTED`
- `PPP_LCP_FAILED`
- `PPP_AUTH_FAILED`
- `PPP_ADDRESS_NEGOTIATION_FAILED`
- `ROUTE_DNS_INSTALL_FAILED`
- `NAT_MULTI_CLIENT_INCOMPATIBLE`
- `MTU_FRAGMENTATION_FAILURE`

The user-facing UI may simplify wording, but technical details should preserve the true layer.

## 14. Required packet/lab proof

Before strict support:

- packet capture proving initial IKE/IPsec protection before accepted L2TP control traffic;
- SCCRQ/SCCRP/SCCCN progression;
- session establishment and PPP LCP/auth/NCP progression;
- source-port 1701 and ephemeral-port interop cases;
- single/multiple clients behind NAT;
- native ESP vs NAT-T outer path where applicable;
- reconnect and SA rekey during active L2TP/PPP session;
- clean teardown/no cleartext fallback;
- MTU/fragmentation behavior.
