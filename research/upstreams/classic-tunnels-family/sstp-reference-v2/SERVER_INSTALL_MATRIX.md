# SSTP / MS-SSTP — Server Installation Matrix

Review date: 2026-08-14

Entry: 011 SSTP / MS-SSTP.

## Status vocabulary

- `NATIVE/OFFICIAL`: platform-supported server path.
- `OPEN-SOURCE REFERENCE`: serious source-backed alternative.
- `NEEDS-LAB`: source/reference exists but no PVNetwork execution receipt.
- `NO-GENERIC-PATH`: no broadly safe/approved deployment is claimed.

## 1. Windows Server 2025

State: `NATIVE/OFFICIAL / PRIMARY / NEEDS-LAB`.

Use Remote Access/RRAS role, supported certificate store, Windows Firewall and NPS/RADIUS/local Windows authorization as required.

Current Microsoft direction keeps SSTP as a supported RRAS remote-access protocol while new RRAS deployments de-emphasize legacy PPTP/L2TP.

Required lab:

- exact Server 2025 build/patch;
- Remote Access/RAS role installation;
- SSTP listener TCP443;
- certificate binding/name/trust;
- user/NPS/RADIUS policy;
- address pool/routes/DNS;
- Windows client interoperability;
- reboot/update/rollback/uninstall.

## 2. Windows Server 2022 / earlier supported releases

State: `NATIVE/OFFICIAL / NEEDS-LAB`.

Maintain an exact support matrix only for Windows Server releases the product actually certifies. Do not assume identical TLS defaults, RRAS UI or certificate behavior across releases.

## 3. SoftEther on Windows Server/Desktop

State: `OPEN-SOURCE REFERENCE / NEEDS CURRENT RELEASE LAB`.

Use exact selected SoftEther release/installer/source, configure SSTP compatibility, certificate/listener/user/Virtual Hub and routing. Disable unnecessary protocol listeners.

## 4. SoftEther on Linux

State: `OPEN-SOURCE REFERENCE / NEEDS-LAB`.

Record:

- distro/release;
- SoftEther package/source pin;
- service account/privileges;
- TCP443 binding/capabilities;
- certificate/private key;
- user/RADIUS/Virtual Hub;
- bridge/SecureNAT/routing;
- systemd/init;
- firewall;
- upgrade/uninstall.

## 5. SoftEther on FreeBSD/macOS or other supported builds

State: `PRODUCT-SPECIFIC / NEEDS-LAB`.

Do not certify merely from source portability. Use the exact platform/release officially supported by the selected SoftEther release and execute the server lifecycle.

## 6. Small Unix SSTP server daemon

State: `NO GENERIC PATH APPROVED`.

A candidate must pass the source/license/TLS/PPP/crypto-binding/security-maintenance gates before entering the matrix.

## 7. Cloud Windows VM

State: `REFERENCE DEPLOYMENT / NEEDS-LAB`.

A cloud VM running RRAS is still Windows RRAS. Add provider-specific evidence for:

- public/private IP topology;
- security group TCP443;
- certificate DNS name;
- source/destination checks/forwarding;
- route tables/NAT;
- image/patch lifecycle.

## 8. Cloud Linux VM + SoftEther

State: `REFERENCE DEPLOYMENT / NEEDS-LAB`.

Same SoftEther source/security gates plus cloud firewall/routing/persistence/image ownership.

## 9. OCI / Docker SoftEther

State: `ADVANCED / NO GENERIC IMAGE APPROVED`.

Container must be pinned by image digest/source and tested for listener, persistent config, certificate mounts, routing/TUN/bridge privileges, restart, upgrade and secret cleanup.

## 10. Kubernetes

State: `NO GENERIC PATH APPROVED`.

Long-lived TCP, stable TLS identity, PPP/session state and routing privileges make a simple stateless Service/Deployment model insufficient without a dedicated gateway design.

## 11. Reverse proxy/load balancer

State: `TOPOLOGY-SPECIFIC / NEEDS-LAB`.

- TCP pass-through: potentially viable, test idle timeout/health/failover;
- TLS termination/L7 forwarding: not assumed safe/compatible because SSTP channel/crypto binding depends on the TLS endpoint.

## 12. Strict execution table

| Server | Stack | Install | TCP443/TLS | SSTP/PPP | Native clients | Linux client | Update | Rollback | Uninstall |
|---|---|---:|---:|---:|---:|---:|---:|---:|---:|
| Windows Server 2025 | RRAS | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Windows Server selected older | RRAS | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux | SoftEther | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Windows | SoftEther | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Cloud Windows | RRAS | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| selected container | pinned SoftEther image | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO entries are external execution gates.
