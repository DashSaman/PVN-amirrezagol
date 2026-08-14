# L2TPv3 — Security, Cookies and Cryptographic Boundaries

Review date: 2026-08-14

Entry: 009 L2TPv3.

L2TPv3 is a pseudowire protocol. Its native cookie/control authentication mechanisms must not be misrepresented as payload encryption.

## 1. Data channel confidentiality

RFC 3931 explicitly states that the L2TP data channel provides **no cryptographic security** by itself. On public/untrusted IP networks where privacy or sophisticated attacks matter, IPsec must be available to secure L2TP traffic.

Therefore:

- plain L2TPv3 over IP/UDP is not an encrypted VPN;
- an Ethernet pseudowire can expose entire Layer-2 frames to an observer if the underlay is untrusted and no cryptographic protection is added;
- entry 010 will own the explicit L2TPv3/IPsec composition and its exact IKE/ESP policy.

## 2. Session ID

L2TPv3 data messages identify a session with a 32-bit Session ID.

The Session ID is routing/session state, not authentication by itself. A receiver first looks up session context from the Session ID and then validates other configured/session attributes such as a cookie.

Do not use predictable Session IDs as a security control.

## 3. Cookie

RFC 3931 defines an optional 0/32/64-bit Cookie associated with a session.

Purpose:

- additional verification that a data message belongs to the expected session;
- reduce accidental misdirection from reused/corrupted Session IDs;
- provide some resistance to blind packet injection.

Security limits from RFC 3931:

- a 64-bit cookie is required by the RFC's security considerations when protection against brute-force blind insertion is desired;
- 32-bit cookie is not an effective barrier to high-rate brute-force blind insertion;
- a cookie does **not** protect against a sophisticated attacker that can sniff/correlate traffic;
- cookie is **not a substitute for IPsec** on an untrusted network.

Product rule:

Never label `cookie` as an encryption key or password in UI. Call it session cookie/anti-spoofing token and explain its scope.

## 4. Cookie generation/storage

When dynamically signaled through the RFC3931 control connection, Assigned Cookie AVPs can carry random cookie values.

For static/manual deployments:

- both peers are configured out-of-band with local/peer cookie values;
- use cryptographically random values when a cookie is used;
- protect configuration provenance because an attacker who learns the cookie can bypass the blind-injection barrier;
- still use IPsec or a trusted underlay when confidentiality/integrity is required.

Cookie values are not equivalent to reusable IPsec secrets, but should still be treated as sensitive operational session configuration when static.

## 5. Control connection authentication/integrity

RFC 3931 defines a shared-secret-based control-connection/message security mechanism that can provide:

- mutual LCCE endpoint authentication;
- integrity/authenticity checking of individual control messages;
- hidden AVP support using the shared secret where applicable.

This protects the **control connection**, not the data payload as cryptographic tunneling.

Important special case:

For L2TPv3 directly over IP, RFC 3931 notes there is no UDP checksum. It recommends Control Message Authentication even with an empty password to provide a packet integrity check for control messages.

PVNetwork rule:

- dynamic control authentication secret is a separate credential class from data-session cookies;
- do not infer payload confidentiality from authenticated signaling;
- static `ip l2tp` sessions have no RFC3931 control protocol and therefore no dynamic control-auth exchange.

## 6. IPsec composition boundary

RFC 3931 states that both L2TPv3 over UDP and L2TPv3 directly over IP can be secured with IPsec.

For direct IP encapsulation, selectors can use:

- source IP;
- destination IP;
- L2TPv3 IP protocol number 115.

For UDP encapsulation, IPsec policy can bind appropriate endpoint/UDP traffic similarly to the L2TP/IPsec recommendations.

Entry 009 only records this security dependency. Entry 010 must define:

- exact IKE version;
- authentication;
- ESP transform/security policy;
- selectors;
- NAT-T/outer transport if used;
- deployment/runtime evidence.

## 7. End-to-end security caveat

Protecting the pseudowire underlay with IPsec protects frames between the L2TPv3 endpoints. It does not replace end-to-end security between applications/hosts carried inside the Layer-2 service.

A Layer-2 extension may carry:

- ARP/ND;
- DHCP;
- VLAN-tagged traffic;
- unencrypted application protocols;
- broadcast/multicast traffic.

PVNetwork server/network design must still apply segmentation/firewall/access policy at the bridged/routed service boundary.

## 8. Ethernet pseudowire integrity/privacy

RFC 4719 inherits RFC3931 security considerations for Ethernet pseudowires. Ethernet frames themselves do not gain confidentiality simply because they traverse a pseudowire.

Extending a broadcast domain over an untrusted network without cryptographic underlay protection exposes more than a routed L3 tunnel in many cases, including MAC-level metadata and broadcast traffic.

## 9. ECN / tunnel safety update

RFC 9601 (2024) updates RFC 3931 regarding ECN propagation across tunnel headers separated by shim headers.

This is not a cryptographic issue, but it is part of safe modern tunnel behavior. Future backend policy must account for current ECN handling rather than assuming old L2TPv3 behavior is frozen.

## 10. Secret classes

Recommended canonical classes:

### Control-auth secret

- optional RFC3931 dynamic control-connection shared secret;
- secure-store/config reference;
- never logged or returned through normal read APIs.

### Static session cookie

- 4/8-byte local/peer operational token;
- sensitive configuration metadata;
- not called a cryptographic VPN secret.

### IPsec credential — entry 010

- PSK/certificate/private-key credential;
- separate secure reference and lifecycle.

### Ephemeral protocol state

- control nonces/digests/session state;
- dynamic cookie/session IDs;
- IPsec traffic keys when composed.

## 11. No custom crypto

PVNetwork must not invent a proprietary encryption layer inside L2TPv3. Use:

- IPsec through a mature/native backend for entry 010; or
- another explicitly approved protected underlay/topology outside entry 009 if product architecture requires it.

## 12. Required security tests

Before any strict support claim:

- wrong/missing cookie -> data dropped when cookie is required;
- 32 vs 64-bit cookie policy validation;
- wrong control shared secret -> control connection fails;
- malformed/control replay/spoof tests;
- static sessions prove no dynamic control-auth assumption;
- packet capture proves plain L2TPv3 has no confidentiality;
- protected entry-010 lab proves IPsec binding and no cleartext bypass;
- bridge/VLAN segmentation and loop protection;
- ECN/fragmentation/MTU behavior;
- config/log redaction of control secrets/static cookies where policy marks them sensitive.
