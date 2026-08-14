# L2TPv3/IPsec — Cryptography and Credential Boundaries

Review date: 2026-08-14

Entry: 010 L2TPv3/IPsec.

This entry adds cryptographic protection to the entry-009 pseudowire. The cryptography is provided by IPsec/IKE through a mature/native backend; L2TPv3 IDs/cookies/control authentication remain separate protocol concepts.

## 1. Why IPsec is required for an untrusted underlay

RFC3931 states that the L2TPv3 data channel provides no cryptographic security and that IPsec must be available when privacy or sophisticated attacks on an untrusted/public IP network are a concern.

Therefore entry 010's security property comes from the selected IPsec ESP/IKE profile, not from the L2TPv3 Cookie or control channel.

## 2. Reuse the typed IPsec security model

Do not invent new L2TPv3-specific cryptographic primitives. Reuse the completed entries 004–007 model:

- IKE version/capabilities;
- peer identity;
- authentication method;
- PSK/certificate/private-key/EAP credential reference as applicable;
- trust policy;
- IKE proposal/security policy;
- ESP/CHILD/data-SA proposal;
- lifetimes/rekey;
- current algorithm guidance;
- backend/provider/plugin capability;
- negotiated/effective state.

For new deployments, prefer approved IKEv2 policies where both endpoints support the intended composition. A legacy IKEv1 requirement must be explicit and separately justified/tested.

## 3. Direct-IP L2TPv3 selector

RFC3931 gives a precise security selector for L2TPv3 directly over IP:

- source IP address of L2TPv3 tunnel endpoint;
- destination IP address of L2TPv3 tunnel endpoint;
- IP protocol 115.

This allows IPsec policy to protect the L2TPv3 flow without inventing TCP/UDP ports.

Product validation should reject a “port 115” representation.

## 4. UDP L2TPv3 selector

RFC3931 says L2TPv3 over UDP has the same IPsec characteristics as L2TPv2-over-UDP and must follow the RFC3193 recommendation.

The protection policy therefore must bind the selected L2TP UDP flow using endpoint addresses and transport-layer selector information appropriate to the actual mode/control connection.

Important complication:

- a dynamic RFC3931 UDP control connection begins at destination UDP 1701 but selected source/response ports may change;
- static UDP sessions can use explicitly configured local/peer UDP ports;
- backend policy must be proven to match the actual packet path rather than assuming every packet remains 1701->1701.

## 5. ESP service

Use ESP through a mature IPsec implementation for confidentiality/integrity/anti-replay according to the approved SA policy.

Do not add AH automatically. AH does not provide payload confidentiality and is not required merely because the pseudowire carries Layer-2 frames.

## 6. L2TPv3 Cookie under IPsec

Cookie remains useful session demultiplexing/misdirection hardening even when IPsec protects the flow, but it is no longer the primary network attacker barrier.

Credential classes remain distinct:

- L2TPv3 Session ID — non-secret identifier;
- L2TPv3 Cookie — operational sensitive token;
- dynamic L2TPv3 control-auth secret — reusable protocol-control credential;
- IPsec PSK/private key/cert identity — cryptographic peer credential;
- IKE/ESP derived keys — ephemeral backend state only.

## 7. Dynamic control authentication under IPsec

A dynamically signaled L2TPv3 deployment can use both:

- IPsec endpoint authentication/confidentiality/integrity for the packet path;
- RFC3931 control-message authentication/integrity for LCCE protocol authentication/defense-in-depth.

Do not merge these into one password field. They can have different identities, lifecycle and failure states.

## 8. Algorithm policy

Do not inherit old RFC3193-era DES/3DES/SHA1 examples/requirements as a 2026 default.

At release/certification time, record:

- current IKEv2/ESP algorithm guidance;
- exact strongSwan/Libreswan/vendor capability;
- crypto-provider versions;
- platform FIPS/security mode if applicable;
- server policy;
- offered and negotiated algorithms.

The protected pseudowire profile should reference an approved IPsec policy object rather than embed arbitrary user-created weak transforms by default.

## 9. Perfect Forward Secrecy / multi-key-exchange

Where the selected IKEv2 backend/server supports current PFS/multiple-key-exchange extensions, represent them as IPsec policy capability. Do not imply L2TPv3 changes their cryptographic meaning.

## 10. Underlay tunnel-mode vs transport/selective protection

Two valid architectural families may exist:

### Flow-selective protection

IPsec policy directly selects the L2TPv3 flow (protocol 115 or selected UDP flow) between endpoint addresses.

### Protected underlay

A broader IPsec tunnel protects routed traffic between endpoint networks, and the L2TPv3 endpoint route is forced through it.

Both can cryptographically protect L2TPv3, but their selectors, routing, failure modes and attack surface differ. Record the selected model explicitly.

## 11. No cleartext fallback

If an entry-010 profile requires IPsec, product policy must prevent L2TPv3 from forwarding over an alternate unprotected underlay when the IPsec SA/policy is absent.

Required negative test:

- tear down IKE/ESP;
- verify no L2TPv3 protocol-115/UDP data reaches the peer over clear route;
- verify attachment circuit is blocked/down or otherwise policy-safe.

## 12. Credential storage

### IPsec

- PSK/password/private key -> secure store/provider;
- cert/trust -> platform/managed certificate store with provenance;
- ordinary profile -> opaque credential references.

### L2TPv3

- control auth secret -> secure reference;
- static cookie -> sensitive config, protected at rest according to product policy;
- session/tunnel IDs -> profile metadata.

### Runtime

Never persist derived IKE/ESP keys in the product database/logs/backups.

## 13. Layer-2 security remains separate

IPsec protects the pseudowire packets between endpoints, but once decapsulated the Layer-2 service may still carry malicious/broadcast traffic.

Continue to enforce:

- VLAN segmentation;
- STP/loop policy;
- DHCP/ARP/ND controls where required;
- MAC scale/storm protection;
- endpoint bridge/firewall/access policy.

IPsec is not a substitute for Layer-2 network architecture.

## 14. Required security tests

- direct protocol-115 flow selected by ESP policy;
- UDP L2TPv3 flow selected correctly for actual port behavior;
- wrong IPsec credential -> no PW forwarding;
- wrong Cookie -> session drop even when IPsec passes;
- wrong control secret -> dynamic control fails;
- no cleartext fallback after IKE/ESP loss;
- IPsec rekey during active pseudowire;
- replay/sequence and L2TP sequence behavior;
- configuration/log secret redaction;
- weak proposal rejection according to approved policy;
- bridge/VLAN attack-surface tests after decryption.
