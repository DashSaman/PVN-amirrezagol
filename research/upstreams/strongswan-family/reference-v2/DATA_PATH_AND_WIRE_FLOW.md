# IKE / IPsec — Data Path and Wire Flow

Review date: 2026-08-14

Scope: entries 004 IKEv2/IPsec, 005 IKEv1/IPsec, 006 ESP and 007 AH.

This document traces traffic from product/profile provisioning through IKE negotiation into the operating-system IPsec data plane. It intentionally separates **control plane** from **protected packet path**.

## 1. Canonical product path

Recommended PVNetwork architecture:

`PVNetwork profile/UI`

`-> secure credential references + typed IPsec requirements`

`-> platform IPsec Adapter`

`-> native OS IKEv2 OR strongSwan/Libreswan backend`

`-> negotiated IKE SA`

`-> negotiated CHILD/data SAs + traffic selectors`

`-> OS/native/kernel IPsec policy + SA installation`

`-> protected packet path (normally ESP)`

Product persistence stops at policy/configuration and secure references. Negotiated traffic keys stay inside the selected mature backend/OS session state.

## 2. IKEv2 control flow — entry 004

### Step A — endpoint and credential preparation

The backend resolves/selects the remote gateway, loads public policy and obtains access to the required credential material through the platform credential store/provider.

PVNetwork should log only safe metadata such as credential reference type and certificate fingerprint/provenance, never reusable PSKs/passwords/private keys.

### Step B — IKE_SA_INIT

Initiator and responder negotiate IKE cryptographic parameters and exchange nonces/key-exchange data. NAT detection and supported extensions may also be signaled.

Outputs:

- candidate/established IKE SA cryptographic state;
- negotiated peer transport/NAT context;
- no assumption yet that the application traffic tunnel is usable.

### Step C — IKE_AUTH

Peers authenticate identities and negotiate the first CHILD_SA/data SA plus traffic selectors.

Outputs:

- authenticated IKE SA;
- negotiated data protection parameters;
- local/remote traffic selectors;
- parameters needed for OS/backend SA/policy installation.

### Step D — data-SA installation

The native OS/strongSwan/Libreswan backend installs directional data SAs and policy into the appropriate IPsec data plane.

On Linux this commonly means:

`charon/pluto control`

`-> kernel XFRM/IPsec API`

`-> Security Association Database + Security Policy Database`

The IKE daemon does not process every ordinary packet merely because it negotiated the keys.

## 3. Outbound ESP data path — entry 006

For a policy-based Linux-style tunnel, conceptual flow is:

1. application emits ordinary IP packet;
2. routing/policy selects an egress path;
3. IPsec SPD/XFRM policy matches the packet and associated selector;
4. matching outbound ESP SA is selected;
5. backend/kernel adds ESP protection according to tunnel/transport mode and negotiated transform;
6. packet leaves as either:
   - native ESP, IP protocol 50; or
   - UDP-encapsulated ESP when NAT-T is active;
7. remote peer identifies the SA by SPI and validates sequence/anti-replay/integrity before decrypting where confidentiality is used;
8. inner/original packet is delivered/routed according to the remote policy.

PVNetwork should treat routing/firewall selection before/after IPsec as part of system networking, not as an ESP cryptographic feature.

## 4. Inbound ESP data path

1. native ESP or UDP-encapsulated ESP arrives;
2. NAT-T UDP demultiplexing separates ESP from IKE control messages when UDP/4500 is shared;
3. SPI selects an inbound SA;
4. sequence/anti-replay checks run;
5. integrity/authentication and decryption are performed according to the SA transform;
6. tunnel-mode outer IP header is removed where applicable;
7. policy checks validate that the decapsulated traffic matches installed security policy/selectors;
8. inner packet continues through host routing/firewall/input/forwarding.

An authenticated/decrypted packet can still be dropped by local firewall/routing/policy. Therefore “IKE connected” and “application reachable” are separate diagnostics.

## 5. Directionality and multiple CHILD_SAs

IPsec SAs are directional. Bidirectional communication involves separate inbound/outbound SA state and SPIs.

One IKE SA can control multiple CHILD/data SAs with different selectors/lifetimes. Therefore PVNetwork must support states such as:

- IKE authenticated, no usable child;
- one child established, another failed;
- child rekey while IKE SA remains established;
- IKE SA rekey while child traffic continues/changes according to backend semantics.

Never model one connection as a single `sharedKey + connected=true` object.

## 6. Rekey and liveness flow

IKEv2 can create/rekey CHILD SAs and rekey the IKE SA through protected exchanges. During rekey, old/new SA overlap and deletion behavior are implementation/timing sensitive.

Tests must verify:

- no traffic black hole beyond defined tolerance;
- no stale duplicate policies/SAs;
- sequence/replay state correctly belongs to the active SA;
- old SPIs disappear after transition;
- disconnect removes all product-owned state.

Liveness/DPD and MOBIKE/network-mobility behavior are backend/profile/platform capabilities and must not be universally assumed.

## 7. NAT traversal path

### IKE control

Normally begins on UDP/500. NAT detection can cause the connection to use UDP/4500.

### Data plane

With NAT-T, ESP is encapsulated inside UDP as defined by RFC 3948. This allows NAT devices to track the flow using UDP addresses/ports while preserving the ESP SA semantics inside.

### Important separation

NAT translation affects outer addresses/ports. Traffic selectors, inner tunnel networks and ESP cryptographic state remain distinct concepts.

Do not rewrite inner selectors merely because the outer NAT address changed unless the negotiated/profile topology specifically requires it.

## 8. Route-based vs policy-based tunnel flow

### Policy-based

Traffic matches IPsec selectors/policies directly. There may be no conventional tunnel interface carrying cleartext inner packets as a normal routed interface.

Operational objects:

- SPD policies;
- SAs;
- selectors;
- ordinary routes/firewall state.

### Route-based / VTI / XFRM interface

A virtual interface is used as a routing anchor while IPsec still provides the actual protected data path.

Operational objects additionally include:

- VTI/XFRM interface;
- interface address/MTU where applicable;
- routes to that interface;
- firewall zones/rules attached to it.

UI must not claim VTI is a different encryption protocol.

## 9. Transport mode vs tunnel mode

### Tunnel mode

The original packet becomes an inner packet protected by an outer IPsec packet. Common for gateway/remote-access VPN use.

### Transport mode

Protection applies to the transport/payload of the original IP packet while the original IP addressing remains the packet's outer addressing. Common in host-to-host/specialized compositions.

The mode belongs to data-SA policy. It should not be inferred merely from whether the peer is called a “server.”

## 10. AH data path — entry 007

Conceptual outbound flow resembles policy/SPI/sequence processing, but AH adds authentication/integrity coverage rather than ESP confidentiality.

Inbound:

1. AH packet arrives as IP protocol 51;
2. SPI selects SA;
3. sequence/anti-replay checked;
4. Integrity Check Value is validated over AH's specified packet coverage;
5. accepted packet continues according to policy.

Because selected immutable/predictable IP-header fields are included in authentication, ordinary NAT address rewriting conflicts with AH integrity. This is why AH cannot be treated as “ESP but without encryption” from a deployment perspective.

## 11. IKEv1 legacy control path — entry 005

Legacy conceptual flow:

`Phase 1 Main/Aggressive Mode`

`-> authenticated IKE/ISAKMP SA`

`-> Phase 2 Quick Mode`

`-> ESP/AH IPsec SAs`

`-> OS/kernel data path`

This stays isolated from the IKEv2 state terminology. Product normalization may map both to high-level stages, but technical logs/screens must identify the actual protocol/version/mode.

## 12. Android native path

`PVNetwork app`

`-> Ikev2VpnProfile/VpnManager provisioning`

`-> Android platform VPN service/implementation`

`-> Android-owned IKEv2 + IPsec data plane`

The app is not expected to receive session keys or process ESP packets. Product state comes from approved platform callbacks/status and networking observations.

## 13. Android strongSwan/VpnService path

Reviewed strongSwan Android frontend uses a dedicated application/service/native integration rather than the same Linux kernel-control architecture.

Conceptually:

`Android VpnService virtual interface`

`<-> strongSwan Android/native backend`

`<-> protected peer traffic`

Exact packet ownership between userspace and platform IPsec components must be pinned to the selected implementation/build; do not import Linux XFRM assumptions into Android.

## 14. Apple path

`PVNetwork app`

`-> NetworkExtension VPN configuration + Keychain references`

`-> OS-managed IKEv2/IPsec`

`-> system route/DNS/VPN state`

The OS owns protocol execution. App UI must reconcile external/system disconnect/profile changes and must not retain derived traffic keys.

## 15. Windows path

`PVNetwork UI/service`

`-> documented native profile/VPNv2/Windows API`

`-> Windows IKE/IPsec services`

`-> Windows networking/IPsec data plane`

Routing, DNS, certificate stores, Always-On/device policy and firewall can influence usability independently of IKE/ESP establishment.

## 16. Failure domains to instrument

Separate at least:

### Pre-IKE

- DNS/endpoint resolution;
- missing credential;
- permission/provisioning;
- no route/firewall reachability to UDP 500/4500.

### IKE control

- version mismatch;
- no proposal;
- peer identity mismatch;
- certificate/trust failure;
- PSK/EAP/authentication failure;
- extension/fragmentation/interoperability error.

### Data-SA negotiation/install

- no ESP/AH proposal;
- traffic selector mismatch;
- kernel/native SA/policy install error;
- unsupported mode/algorithm;
- route/VTI/XFRM setup failure.

### Data path

- NAT-T blocked;
- MTU/fragmentation;
- policy/firewall drop;
- asymmetric route;
- DNS leak/misroute;
- replay/sequence errors;
- stale SA after rekey;
- server route/forward/NAT misconfiguration.

## 17. Safe observability

Safe telemetry can include:

- IKE version;
- backend/platform;
- server endpoint class (redacted as needed);
- NAT-T active;
- negotiated algorithm identifiers where policy permits;
- IKE/CHILD state;
- safe certificate fingerprint/issuer metadata;
- selector counts/CIDRs with privacy policy;
- SPI only in diagnostic mode if acceptable;
- bytes/packets and rekey counters;
- error classes.

Never collect/persist:

- PSKs;
- passwords;
- private keys;
- derived IKE/ESP traffic keys;
- full sensitive identity strings without a defined privacy purpose.

## 18. Required runtime evidence

Source/reference flow is complete only at documentation level. Strict certification requires synchronized packet/backend receipts for selected combinations:

- IKEv2 -> ESP native;
- IKEv2 -> NAT-T UDP ESP;
- route-based VTI/XFRM;
- rekey;
- roaming/network change where supported;
- legacy IKEv1 compatibility lab;
- AH on an intentional non-NAT topology;
- disconnect/uninstall cleanup.
