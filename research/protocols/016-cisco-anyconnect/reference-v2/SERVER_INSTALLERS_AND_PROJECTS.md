# Cisco AnyConnect — Server / Deployment Projects

Review date: 2026-08-14 UTC

## Cisco proprietary deployment

Cisco's current Secure Client Administrator Guide documents:

- web deployment from Secure Firewall ASA and Secure Firewall Threat Defense headends;
- separate predeployment for endpoints;
- headend-hosted packages/profiles and update policy;
- administrative privilege requirements and platform-specific upgrade constraints.

Cisco headends are appliance/platform products, not an open-source server installer project. Do not invent a Cisco container or source build.

Official deployment overview: https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/admin/guide/cisco-secure-client-admin-guide-new/deploy-anyconnect-intro/c_anyconnect_deployment_overview.html

## Public compatible deployment

ocserv 1.5.0 is the major public server project for an OpenConnect/AnyConnect-compatible controlled test/server environment. Canonical release/tag is signed and resolves to `49f9956eeeffd613e4bcac3f6450c682ec21e75a`; GPLv2+. The project documents dedicated unprivileged worker user/group, certificates, config, systemd/resource-control and GitLab CI.

No community Docker image or one-line installer is selected by this research. Any future image must be pinned by digest/source/base image/privileges.
