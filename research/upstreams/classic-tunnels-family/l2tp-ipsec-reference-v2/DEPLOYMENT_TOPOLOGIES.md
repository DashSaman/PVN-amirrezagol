# L2TP/IPsec — Deployment Topologies and Migration

Review date: 2026-08-14

Entry: 008 L2TP/IPsec.

## Default product stance

`LEGACY COMPOSED COMPATIBILITY TARGET`.

L2TP/IPsec may be retained for interoperability with installed clients/servers, but it should not be the default architecture for new PVNetwork deployments when an approved modern alternative is available.

## Topology A — Native remote-access client to appliance/server

`Windows / Apple / verified native client`

`-> Internet/NAT`

`-> IPsec endpoint`

`-> L2TP server`

`-> PPP/AAA + address pool`

`-> firewall/routing/NAT`

Use when an existing organization requires native L2TP/IPsec compatibility. Keep machine authentication and PPP user authentication as separate policy objects.

## Topology B — Linux composed server

`Internet`

`-> strongSwan or Libreswan IPsec`

`-> xl2tpd / kl2tpd / selected L2TP service`

`-> pppd / AAA`

`-> routing/firewall`

This topology has multiple daemon/config ownership boundaries. A PVNetwork manager must use a typed adapter and transactional configuration; do not expose arbitrary remote shell/file editing as the management model.

## Topology C — Windows Server RRAS

`native clients -> RRAS -> Windows policy/NPS/RADIUS -> internal network`

Current Microsoft guidance reviewed 2026-08-14 states that **new Windows Server 2025 RRAS setups do not accept L2TP/PPTP by default**, though administrators can explicitly enable them; existing upgraded configurations retain behavior. This makes RRAS L2TP a deliberate legacy-compatibility enablement, not an assumed default.

## Topology D — pfSense/Netgate layered service

Current Netgate documentation separates:

- L2TP server configuration/users/addressing;
- L2TP client firewall rules;
- separate IPsec mobile/protection configuration;
- separate L2TP/IPsec logs/status domains.

This is a useful reference for preserving layer ownership in PVNetwork UI, while vendor runtime evidence remains separately required.

## Topology E — Multiprotocol server

SoftEther or another reviewed multiprotocol server may expose L2TP/IPsec alongside other protocols. Treat its L2TP/IPsec feature as one server implementation, not as the protocol specification. Exact release/source/license/security/runtime review is required before reuse or recommendation.

## Topology F — Site-to-site / special compatibility

L2TP/IPsec is primarily treated here as remote-access compatibility. Do not generalize it into a preferred site-to-site architecture merely because an implementation can route networks through a PPP/L2TP session. Prefer purpose-built approved site-to-site technologies unless a documented interoperability requirement exists.

## NAT topology

NAT is common in remote access. The outer IPsec layer may use NAT-T/UDP 4500; L2TP remains inside the IPsec protection path. Test:

- client behind one NAT;
- server behind NAT where supported/required;
- multiple clients behind one NAT;
- mobile network changes;
- source-port behavior;
- stale mappings/reconnect.

Do not publish compatibility claims from a single home-NAT success.

## High availability

L2TP/IPsec session state spans multiple layers. HA/failover must consider:

- IKE/IPsec SA state;
- L2TP tunnel/session state;
- PPP/AAA state;
- address allocation;
- routing/firewall/NAT;
- accounting/session cleanup.

Do not claim seamless failover without execution evidence showing how the selected server stack handles all layers.

## Security zones

Recommended separation:

- public VPN ingress;
- IPsec/L2TP service host or appliance;
- management plane not exposed on public VPN ports;
- AAA/RADIUS network path;
- internal client-access zone;
- logs/metrics sink with secret redaction.

## Migration topology

Preferred controlled migration:

1. inventory L2TP/IPsec clients and exact OS versions;
2. identify modern protocol supported by both endpoint and policy;
3. provision modern profile alongside legacy profile;
4. test authentication, routes, DNS, reconnect and application access;
5. switch default only after successful receipt;
6. retain legacy profile for a defined rollback window if policy allows;
7. remove legacy server enablement/profile/PSKs after migration acceptance;
8. verify firewall and secret cleanup.

Never silently reinterpret an L2TP profile as IKEv2 or automatically downgrade a failed modern connection to L2TP.

## Unsupported/unsafe patterns

- exposing UDP/1701 as an intentionally unprotected public remote-access service while calling it equivalent to L2TP/IPsec;
- one shared PSK embedded in client logs/backups/scripts without secret controls;
- legacy algorithm exceptions applied globally to modern IPsec profiles;
- unmanaged parallel strongSwan/Libreswan/xl2tpd ownership on an appliance that already owns those services;
- claiming Android/Apple/Windows parity without exact-version testing;
- using a privileged third-party container/image without source, digest, capability and update review.

## External execution gates

- representative RRAS 2025 explicit L2TP enablement and rollback;
- pfSense current-release L2TP/IPsec end-to-end setup and backup/restore;
- Linux strongSwan/Libreswan + L2TP + pppd clean install/update/uninstall;
- multi-client NAT interoperability;
- native Windows/Apple and selected Android/Linux clients;
- HA/failover if included in product scope;
- migration drill from L2TP/IPsec to selected modern target.
