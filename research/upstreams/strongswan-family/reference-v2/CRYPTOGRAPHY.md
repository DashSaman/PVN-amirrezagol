# IKE / IPsec — Cryptography and Security-Association Model

Review date: 2026-08-14

Scope: entries 004 IKEv2/IPsec, 005 IKEv1/IPsec, 006 ESP, 007 AH.

This file records the **cryptographic architecture and policy boundaries**. It deliberately does not turn old example cipher suites into PVNetwork defaults. Algorithm requirements evolve and must be taken from current standards guidance plus the exact selected implementation/provider build.

## 1. IKEv2 cryptographic role — entry 004

Authoritative base: RFC 7296 (STD 79), plus its update RFCs.

IKEv2 is the authenticated key-management/control protocol. Its cryptographic responsibilities include:

- negotiate algorithms/key-exchange methods for the IKE SA;
- exchange nonces and key-exchange material;
- derive keys protecting IKE traffic;
- authenticate peer identities/credentials;
- negotiate CHILD_SAs containing IPsec data-plane transform parameters and traffic selectors;
- rekey/delete IKE and CHILD SAs.

A PVNetwork profile therefore needs distinct concepts for:

- IKE cryptographic proposal/policy;
- authentication method and secure credential reference;
- CHILD/ESP/AH transform proposal;
- traffic selectors;
- lifetimes/rekey policy;
- effective negotiated IKE and data-SA state.

Do not store the negotiated state as if it were the original policy/profile.

## 2. IKEv2 algorithm guidance is versioned separately

RFC 8247 supplies algorithm implementation requirements and usage guidance for IKEv2 and explicitly separates that topic from the base RFC. RFC 9395 later updates RFC 8247 and deprecates IKEv1/obsolete algorithms.

Engineering rule:

- never copy an algorithm list from RFC 7296 examples and treat it as current security policy;
- track the latest applicable algorithm guidance, IANA status, selected server policy and implementation/provider capability;
- distinguish `implemented`, `allowed by policy`, `offered`, and `negotiated`.

Current PVNetwork research does not freeze a universal cipher suite.

## 3. Multiple key exchanges / post-quantum transition

RFC 9370 updates IKEv2 to support multiple key exchanges when establishing/rekeying SAs. It uses IKE_INTERMEDIATE and introduces/further uses follow-up key-exchange mechanisms for additional key exchanges.

Current implementation evidence:

- Libreswan v5.4 release notes record RFC 9370-related IKE_INTERMEDIATE / additional/follow-up key-exchange work and ML-KEM-768 support with a specified NSS dependency floor for that feature.

This is **implementation/version evidence**, not a generic requirement that all IKEv2 peers support ML-KEM or RFC 9370.

PVNetwork capability model should represent multi-KE/PQ capability explicitly rather than overloading a legacy `dhGroup` field.

## 4. IKEv2 authentication

IKEv2 supports authentication based on configured credentials/methods and extensions. Product modeling must separate:

- peer identity;
- authentication method;
- credential/certificate/private-key/PSK/EAP secure reference;
- trust policy;
- server-requested/negotiated extension behavior.

RFC 9593 defines a mechanism for announcing supported authentication methods, improving interoperability when peers have multiple possible credential/auth methods. It is an extension and must not be assumed present on all peers.

Do not silently switch authentication method when a configured method fails unless explicit policy permits a tested alternative.

## 5. IKEv1 cryptographic role — entry 005

RFC 2409 defines legacy IKEv1 Main Mode, Aggressive Mode, Quick Mode and related exchanges. RFC 9395 deprecates IKEv1 and moves its core RFCs to Historic status.

PVNetwork rule:

- IKEv1 is legacy/vendor-compatibility only;
- no silent downgrade from IKEv2;
- do not import IKEv2 algorithm guidance into IKEv1 and claim equivalence;
- legacy/authentication/mode weaknesses must be represented in policy/UI warnings where IKEv1 is intentionally exposed;
- exact server/backend compatibility is mandatory before enabling it.

## 6. ESP cryptographic role — entry 006

Authoritative base: RFC 4303, within the IPsec architecture of RFC 4301.

ESP can provide combinations of:

- confidentiality;
- data-origin authentication/integrity;
- anti-replay protection;
- limited traffic-flow confidentiality features.

The actual service set depends on the selected Security Association and algorithms.

ESP packet processing uses SA state including:

- SPI;
- sequence-number/replay state;
- encryption/authentication or AEAD transform;
- keys;
- mode (transport/tunnel);
- selectors/policy.

PVNetwork must not implement ESP transforms itself. The product should delegate to mature OS/kernel/native IPsec or approved implementation backends.

## 7. Current ESP/AH algorithm guidance

RFC 8221 supplies algorithm implementation/usage guidance for ESP/AH. RFC 9395 updates it to deprecate obsolete algorithms associated with legacy use.

Important architecture guidance from RFC 8221:

- modern AEAD transforms can provide encryption + authentication in one transform;
- non-AEAD ESP requires appropriate integrity/authentication protection according to the algorithm/policy;
- ESP+AH as a double-protocol construction is not the normal preferred design merely to obtain confidentiality+integrity.

Product rule: represent ESP transform choice and AH use independently; never add AH automatically because an old configuration example did so.

## 8. AH cryptographic role — entry 007

Authoritative base: RFC 4302.

AH provides authentication/integrity and anti-replay semantics over its defined coverage; it **does not provide payload confidentiality**.

The IPsec architecture (RFC 4301) requires ESP support and makes AH optional for compliant implementations. This is one reason PVNetwork must not assume AH capability from generic “IPsec supported” signals.

AH is especially sensitive to IP-header changes, which is an important deployment/NAT distinction from ESP/NAT-T.

PVNetwork rule:

- do not market AH as encrypted VPN;
- do not silently fall back from ESP to AH;
- expose only for an evidenced platform/backend/server combination and advanced use case.

## 9. Sequence numbers and anti-replay

ESP and AH Security Associations track sequence numbers and receive anti-replay state. RFC 4303/4302 define 32-bit sequence fields with optional extended sequence-number processing.

RFC 9827 (2025) updates IKEv2 terminology by renaming Transform Type 5 from Extended Sequence Numbers to Sequence Numbers and clarifies its existing transform values. Product diagnostics/spec documentation should follow current terminology while retaining compatibility with older logs/config names.

## 10. Key/credential storage boundary

The protocol specifications do not define PVNetwork's local persistence design.

Required product rule:

- passwords/PSKs/private keys/tokens -> platform secure store or protected credential provider;
- certificates/trust anchors -> platform/managed certificate store or protected product store with explicit provenance;
- public policy/profile fields -> canonical profile database;
- ephemeral derived session keys -> backend memory/session only, never product profile persistence;
- logs/backups/exports -> redact reusable secrets unless an explicit secure export format requires them.

## 11. Crypto-provider/SBOM rule

For strongSwan/Libreswan or any native backend, a support claim requires more than the application version. Record:

- exact engine/source version;
- compiled/loaded plugin/provider set;
- crypto library/provider versions;
- kernel/native IPsec backend;
- platform security policy/FIPS mode where applicable;
- offered/negotiated algorithms;
- relevant advisories.

Libreswan v5.4's ML-KEM feature depending on a minimum NSS version is a concrete example of why the dependency/provider layer is part of protocol capability.

## 12. Reference set

Primary standards:

- RFC 7296 — IKEv2 / STD 79
- RFC 8247 — IKEv2 algorithm implementation requirements/guidance
- RFC 9370 — multiple key exchanges in IKEv2
- RFC 9593 — supported authentication-method announcements
- RFC 9827 — current Sequence Numbers transform terminology
- RFC 9395 — IKEv1 deprecation and obsolete-algorithm updates
- RFC 2409 — historic IKEv1
- RFC 4301 — IPsec architecture
- RFC 4303 — ESP
- RFC 4302 — AH
- RFC 8221 — ESP/AH algorithm implementation requirements/guidance

## 13. Remaining evidence

Before production/certification:

- freeze exact algorithm policy per profile class and platform;
- verify current updates to the standards/guidance set;
- record exact implementation/provider capability matrices;
- test negative/mismatch/downgrade/rekey scenarios;
- test IKEv1 legacy paths only in explicit compatibility labs;
- run ESP/AH known-answer/interoperability tests through the selected mature backends rather than reimplementing primitives.
