# SSTP / MS-SSTP — Server Installers and Deployment Projects

Review date: 2026-08-14

Entry: 011 SSTP / MS-SSTP.

## 1. Windows Server RRAS official deployment

Primary supported server path:

- install Windows Server **Remote Access** role;
- install/configure the VPN/RAS role service;
- enable/configure RRAS through supported Windows management;
- install/select the SSTP TLS server certificate/private key;
- configure user/NPS/RADIUS authentication policy;
- configure client address pools/routing/firewall.

### Installation ownership

Windows owns:

- RRAS binaries/services;
- HTTP/TLS integration;
- certificate store/private-key ACLs;
- VPN miniports/native network stack;
- Windows Firewall/service lifecycle;
- cumulative/security updates.

PVNetwork should automate only documented Windows role/profile/PowerShell/WMI/CSP/API surfaces after exact-version validation.

### Required install receipt

- Windows Server edition/build/patch level;
- installed roles/features;
- SSTP port/listener state;
- server certificate thumbprint/subject/SAN/expiry and private-key access;
- RRAS authentication/RADIUS/NPS policy;
- IP pool/routes/DNS;
- firewall;
- restart/reboot behavior;
- update/rollback/uninstall.

## 2. SoftEther VPN Server

Existing source pin:

`SoftEtherVPN/SoftEtherVPN@49eb2f08641709d1af57a0d04971973ff94461db`

SoftEther has first-party server packages/builds across multiple desktop/server operating systems and can expose SSTP compatibility from its multiprotocol server.

### Deployment review required

For the exact selected release/platform record:

- source/release commit;
- installer/package origin and signature/hash;
- service user/privileges;
- listener ports;
- SSTP enablement;
- TLS certificate/private key;
- Virtual Hub/user/RADIUS ownership;
- SecureNAT/bridge/routing;
- logs;
- backup/restore;
- update/uninstall.

Disable unrelated SoftEther protocols/listeners when not required; multiprotocol convenience expands attack surface.

## 3. Linux community SSTP server projects

No generic small community server is approved by popularity.

Any candidate must document:

- canonical repository and immutable pin;
- license;
- current maintenance/security activity;
- TLS library and certificate validation/server-key handling;
- SSTP/MS-SSTP control completeness/crypto binding;
- PPP/pppd integration;
- RADIUS/local-auth integration;
- daemon privileges;
- TCP443 listener/http parser;
- fuzz/tests;
- service packaging;
- update/uninstall/rollback.

Prefer RRAS/SoftEther reference deployments unless a smaller Unix server provides a clear maintained/security-reviewed advantage.

## 4. Reverse proxy / load balancer projects

Do not treat a generic Nginx/Apache/HAProxy HTTPS setup as an SSTP server installer.

SSTP uses long-lived duplex HTTP/TLS semantics and channel/crypto-binding behavior. TLS termination or L7 proxying can change the security channel.

A front-end proxy/load balancer is admitted only with exact vendor/implementation evidence for SSTP pass-through/offload behavior.

Safer default architecture:

- TCP pass-through where operationally appropriate and supported; or
- direct SSTP termination on RRAS/SoftEther.

Even pass-through must be tested for idle timeout, TCP resets and health-check behavior.

## 5. Cloud VM images/templates

A Windows or SoftEther VM image can host SSTP, but marketplace/template convenience is not source/security evidence.

Record:

- immutable image ID/build;
- publisher;
- OS/package patch state;
- bootstrap scripts;
- generated/default credentials;
- certificate provisioning;
- firewall/security-group TCP443;
- update ownership;
- decommission/secret cleanup.

## 6. OCI / Docker

### SoftEther container

Possible through community/official packaging patterns, but no generic image is approved automatically.

Review:

- Dockerfile/source pin/base image;
- SoftEther release/hash;
- TCP443 exposure;
- certificate/secret mounts;
- NET_ADMIN/bridge/TUN privileges if routing/bridge features need them;
- persistence of server config/user database;
- health/restart behavior;
- update/rollback.

### Windows RRAS container

Not treated as a normal container deployment target. RRAS is a Windows Server role/native networking feature and should be managed as a supported Windows server/VM unless Microsoft explicitly documents a supported container model.

## 7. Kubernetes

No generic SSTP server Deployment is approved.

Challenges:

- long-lived TCP connections;
- stable server identity/certificate;
- PPP/session/address state;
- routing/NAT/network privileges;
- TCP443 service/load-balancer idle timeout;
- TLS pass-through vs termination;
- user/accounting state;
- failover/reconnect semantics.

A dedicated gateway VM/node is generally simpler unless a Kubernetes network-function architecture is explicitly designed and proven.

## 8. Certificate automation

Certificate issuance/renewal is part of server lifecycle.

Required controls:

- public/internal CA ownership;
- DNS/SAN name matching client profile;
- automated renewal before expiry;
- private-key ACLs;
- RRAS/SoftEther certificate rebinding/reload semantics;
- old/new certificate overlap and client trust;
- revocation behavior.

Do not run a certificate renewal hook that changes the SSTP certificate without verifying the service actually uses the new cert and clients still validate it.

## 9. Upgrade/rollback

### Windows

- record OS cumulative/security update;
- preserve RRAS config/certificate binding/NPS policy;
- test SSTP after reboot;
- use supported rollback/backup mechanisms;
- check TLS policy changes.

### SoftEther

- backup server config/user/hub state;
- pin old/new binaries;
- review release notes/security fixes;
- test SSTP and unrelated disabled listener state;
- restore exact previous config on rollback.

## 10. Uninstall/decommission

- disable SSTP listener/service;
- disconnect active sessions;
- remove RRAS role or SoftEther service only after config backup/retention decision;
- remove firewall/security-group rules;
- remove product-managed certificate/private key only when no other service owns it;
- remove user/RADIUS secrets according to policy;
- remove routes/NAT/address pools;
- scrub temp/generated config/log secrets;
- revoke server certificate if the endpoint is retired and policy requires it.

## 11. Supply-chain rule

Do not recommend opaque “SSTP VPN server install” scripts that:

- download unsigned binaries;
- generate weak/self-signed certs silently;
- disable certificate validation;
- enable obsolete TLS globally;
- write PPP/RADIUS secrets world-readable;
- open extra VPN protocols/management ports;
- lack uninstall/rollback.
