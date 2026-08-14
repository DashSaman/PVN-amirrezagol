# Palo Alto GlobalProtect — Server / Control UI Map

Review date: 2026-08-14 UTC

Current PAN-OS 12.1 administration baseline:

## Portal

`Network > GlobalProtect > Portals`

Relevant operator concepts:

- portal name and interface/IP ownership;
- SSL/TLS service profile / server certificate;
- authentication profile / certificate profile and client authentication rules;
- agent/client configuration distribution;
- gateway list/selection and app settings;
- client software/update policy where applicable;
- optional HIP-related policy/configuration subject to license and endpoint capability.

## Gateway

`Network > GlobalProtect > Gateways`

Relevant operator concepts:

- interface/IP and authentication;
- tunnel mode / tunnel interface;
- IP pool, routes/split tunnel and DNS/network settings;
- SSL/TLS service profile;
- IPsec crypto profile and IPsec enablement;
- client settings and access policy;
- logging/session visibility;
- internal vs external gateway role where configured.

Prisma Access management is a separate vendor control plane; this dossier does not fabricate a pixel-identical PAN-OS menu tree for it.

The UI must keep **portal/configuration**, **gateway/session**, **SSL tunnel**, **IPsec tunnel** and optional **HIP/posture** state separate.
