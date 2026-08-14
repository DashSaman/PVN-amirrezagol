# IKE / IPsec — Server UI, Control Plane and Menu Maps

Review date: 2026-08-14

Scope: entries 004–007. IKE/IPsec itself does not define a canonical web UI. This file therefore separates protocol/daemon control surfaces from serious firewall/control-panel UIs that configure IPsec.

## 1. strongSwan server control surface

Pinned engine baseline: strongSwan 6.0.7, commit `5973ff8e41deef4e015e1138a2de688acedf6f75`.

### `swanctl` / VICI model

The modern strongSwan control model is daemon + VICI + `swanctl`, not a built-in browser panel.

Server/operator domains to expose in documentation/UI adapters include:

- connection definitions;
- local/remote authentication identities;
- CHILD_SA definitions and traffic selectors;
- credentials/certificates/keys;
- address pools;
- IKE/ESP proposals;
- initiation/termination/rekey;
- IKE_SA and CHILD_SA status;
- installed policies/SAs through the backend;
- logs/diagnostics.

PVNetwork should not expose arbitrary VICI to remote users. If a management product ever controls strongSwan, use a narrow privileged local adapter/API with authorization and validation.

## 2. Libreswan control surface

Pinned release: Libreswan v5.4 commit `5eb03b7772b312e705feab9ad5868678a3c007e6`.

Libreswan is also daemon/config/CLI-oriented rather than a canonical web panel. The operator surface includes configuration and `ipsec` command/status/control flows around its daemon/kernel state.

Treat its terminology separately from strongSwan's VICI/swanctl data model; do not make one parser/UI depend on private daemon syntax.

## 3. OPNsense IPsec web UI

Current source reference during this research:

- repository: `opnsense/core`
- reviewed master commit: `6f6d6fa05ec274a4b3589d33e6e4249a162993c2`
- root license: permissive BSD-style terms in repository `LICENSE`.

Official OPNsense documentation exposes the following IPsec navigation under the VPN/IPsec area:

### Configuration/control

- **Connections** — newer configuration interface following the `swanctl` connection/pool model;
- **Tunnel Settings** — legacy configuration tool;
- **Mobile Clients** — remote-user/mobile attributes and pool-related configuration;
- **Pre-Shared Keys** — local authentication secrets;
- **Key Pairs** — public/private-key material;
- **Advanced Settings** — passthrough networks, logging and generic settings.

### Status/diagnostics

- **Status Overview** — tunnel state;
- **Lease Status** — mobile-client pool leases;
- **Security Association Database** — installed/known security associations;
- **Security Policy Database** — installed security policies;
- **Virtual Tunnel Interfaces** — route-based/VTI interfaces;
- **Log File** — IPsec logs.

### New Connections editor structure

Official docs show the Connections workflow broken into explicit sub-areas such as:

- Connections/general IKE parameters;
- Local Authentication;
- Remote Authentication;
- Children;
- separate Pre-Shared Key records.

This is a valuable UI reference because it mirrors the required product separation between IKE authentication/control and CHILD/data-SA definitions.

### Security lesson

OPNsense documentation explicitly notes that the newer Connections feature does not simply auto-generate broad WAN firewall rules; operators must define appropriate rules. PVNetwork should keep tunnel configuration and firewall exposure as separate explicit policy domains.

## 4. pfSense IPsec web UI

Current source reference during this research:

- repository: `pfsense/pfsense`
- reviewed master commit: `9363ac5b8651a1c7a333180425ce7719070f95f9`
- root repository license: Apache License 2.0.

Source paths directly materialize major web surfaces:

- `src/usr/local/www/vpn_ipsec.php`
- `src/usr/local/www/vpn_ipsec_phase1.php`
- `src/usr/local/www/vpn_ipsec_phase2.php`
- `src/usr/local/www/vpn_ipsec_mobile.php`
- `src/usr/local/www/vpn_ipsec_settings.php`
- `src/usr/local/www/status_ipsec.php`
- dashboard IPsec widget.

Official documentation maps the operator workflow as:

### Tunnels

`VPN > IPsec` tunnel list with one Phase 1 definition and one or more Phase 2 definitions.

### Phase 1 / IKE

Typical groups include:

- General Information;
- IKE Endpoint Configuration;
- Key Exchange Version (IKEv1/IKEv2/Auto);
- Authentication Method;
- IKE proposal/cryptographic settings;
- lifetime/replacement;
- advanced options.

### Phase 2 / data SA

Typical groups include:

- General Information;
- mode / policy-based / route-based VTI choices;
- local/remote networks or tunnel-interface parameters;
- Phase 2 Proposal / SA / key-exchange settings;
- protocol choice ESP or AH;
- lifetime/replacement;
- keepalive-related behavior.

### Mobile/credentials/global settings

Official documentation lists surfaces including:

- Mobile Clients;
- Pre-Shared Keys;
- Mobile Group Pools on applicable editions;
- Advanced IPsec Settings.

### Status

The product provides IPsec status and management views tied to configured Phase 1/Phase 2 entries.

## 5. Terminology caution for PVNetwork

The phrase “Phase 1 / Phase 2” is deeply embedded in many firewall UIs and operator habits. It maps naturally to IKEv1 terminology and is also used by products as a configuration abstraction for IKE + CHILD/IPsec SA policy.

PVNetwork internal model should instead use protocol-correct concepts:

- IKE session / IKE SA;
- authentication;
- CHILD/data SA;
- traffic selectors;
- data-plane transform.

The UI may show familiar aliases for administrators, but must not mislabel IKEv2 wire exchanges as literal IKEv1 phases.

## 6. Recommended PVNetwork server-management information architecture

If PVNetwork later includes an operator/server panel, a protocol-correct menu tree should resemble:

### IPsec

- Connections
  - IKE Settings
  - Local Authentication
  - Remote Authentication
  - Child/Data SAs
  - Traffic Selectors / Routes
- Credentials
  - Certificates / Trust
  - PSKs
  - External/Token Credentials
- Address Pools
- Routing / VTI / XFRM Interfaces
- Firewall / Reachability Guidance
- Status
  - IKE SAs
  - CHILD/Data SAs
  - Policies
  - Leases
- Logs / Diagnostics
- Compatibility / Legacy IKEv1
- Advanced / Extensions

AH should appear only as an advanced data-SA transform/protocol option when the selected backend supports it, not as a peer to “IKEv2 VPN”.

## 7. Secret handling

Panel/source research must record how PSKs/private keys are stored. A UI field being password-masked is not proof of encryption at rest.

PVNetwork rule:

- secret values -> secure secret store/reference;
- configuration DB -> reference/provenance only where possible;
- UI/API -> never return stored secret value after creation unless a deliberately designed secure export flow requires it;
- logs/audit -> redact secrets and private-key material.

## 8. Source/reuse distinction

OPNsense and pfSense are useful UI/operator references, but their application/source licenses and complete product stacks differ from strongSwan/Libreswan licenses. No UI code/assets should be copied by assumption.

Use them to learn:

- menu hierarchy;
- validation needs;
- status concepts;
- phase/child separation;
- operational warnings;
- firewall/routing integration.

PVNetwork must use its own branding/UI and its own licensed implementation strategy.

## 9. Remaining panel/UI work

- source-level OPNsense exact IPsec model/controller/view inventory;
- source-level pfSense every IPsec form/action/status control;
- API/CSRF/auth/session/write-boundary review for both management products;
- installer/update/backup/restore and secret-at-rest audit;
- real running screenshots/version correspondence;
- accessibility/mobile/responsive behavior;
- selected cloud/vendor management portals when a certification target is chosen.
