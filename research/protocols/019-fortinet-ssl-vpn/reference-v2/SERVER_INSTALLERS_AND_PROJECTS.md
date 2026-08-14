# Fortinet FortiGate SSL VPN — Server Deployment / Installer Review

Review date: 2026-08-14 UTC

FortiGate SSL VPN tunnel mode is deployed as a FortiOS feature on a supported FortiGate model/VM and valid vendor software branch. There is no open-source FortiGate SSL-VPN server installer, source build, container image or Helm chart selected by this dossier.

For the selected legacy server reference FortiOS 7.4.12, official deployment requires:

- enable SSL-VPN feature visibility where applicable;
- configure server certificate and authentication/users/groups;
- configure `VPN > SSL-VPN Portals`;
- configure `VPN > SSL-VPN Settings` including listener/interface/port and authentication-to-portal mapping;
- create policies using the `ssl.root` tunnel interface;
- configure address pools, routes/split/full-tunnel behavior and DNS as required;
- monitor active sessions and forward traffic.

Official sample: https://docs.fortinet.com/document/fortigate/7.4.12/administration-guide/559546/ssl-vpn-full-tunnel-for-remote-user

## Lifecycle / migration

Before upgrading a tunnel-mode deployment to FortiOS 7.6.3 or later, Fortinet requires manual migration to IPsec VPN because tunnel-mode settings and related policy are not carried forward automatically. Fortinet documents IPsec-over-TCP/443 as a migration option for environments that previously depended on SSL-VPN reachability.

Migration reference: https://docs.fortinet.com/document/fortigate/7.6.6/administration-guide/155142/ssl-vpn-tunnel-mode-to-ipsec-vpn-migration

## Supply-chain boundary

Use vendor-authorized FortiOS images/upgrade paths and current FortiGuard PSIRT guidance. Do not deploy a third-party image pretending to be a FortiGate SSL-VPN server. Public OpenConnect packages remain client-side and have their own source/package trust chain.
