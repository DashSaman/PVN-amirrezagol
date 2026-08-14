# PPTP — Server Installation Matrix

Review date: 2026-08-14

Entry: 012 PPTP.

## Status

- `LEGACY NATIVE`: still available on exact platform/release for compatibility.
- `HISTORICAL`: source/package exists but should not be selected for new production.
- `NEEDS-LAB`: runtime proof absent.
- `NOT RECOMMENDED`: no new-deployment path promoted.

## 1. Windows Server 2025

State: `LEGACY NATIVE / EXPLICIT ENABLEMENT / NEEDS-LAB`.

Current Microsoft direction disables PPTP acceptance for new RRAS setups by default. Explicit legacy enablement is required when retained.

Lab must prove TCP1723, GRE47, PPP/auth/MPPE, address pool, NPS/RADIUS, NAT/firewall, reboot/update and final disable/removal.

## 2. Windows Server 2022 / selected older releases

State: `LEGACY NATIVE / NEEDS-LAB`.

Certify only exact releases still in business scope. Do not infer Server 2025 defaults or TLS-era policy from older systems.

## 3. MikroTik RouterOS

State: `LEGACY BUILT-IN / SECURITY WARNING / NEEDS-LAB`.

Current RouterOS retains PPTP capability but vendor documentation warns of known security problems. Exact RouterOS/hardware/auth/MPPE/NAT/helper behavior must be captured.

## 4. Debian/Ubuntu historical pptpd

State: `HISTORICAL / SOURCE+PACKAGE MAINTENANCE PIN REQUIRED / NOT RECOMMENDED`.

If a migration lab requires it, pin the archived/repository package and pppd/kernel helper stack. No greenfield deployment recommendation.

## 5. Fedora/RHEL-family historical pptpd

State: `HISTORICAL / PACKAGE AVAILABILITY MUST BE VERIFIED / NOT RECOMMENDED`.

Do not assume current supported repositories include pptpd. Use only in an isolated legacy lab with immutable packages/source.

## 6. Other Linux/Unix

State: `HISTORICAL / UNVERIFIED`.

Needs exact pptp client/server, pppd and GRE/kernel support. Source portability is not certification.

## 7. Cloud Windows RRAS

State: `LEGACY / PROVIDER-GRE-PATH NEEDS-LAB`.

Provider security groups/NAT/load balancers must pass TCP1723 **and GRE protocol47** and preserve PPTP call state.

## 8. Cloud Linux pptpd

State: `NOT RECOMMENDED`.

Do not create a new Internet-exposed legacy PPTP server to satisfy a research matrix. Use isolated lab only when required.

## 9. Containers

State: `NO GENERIC PATH / LEGACY LAB ONLY`.

Host kernel GRE/helper/PPP and NET_ADMIN make this unattractive and high privilege.

## 10. Kubernetes

State: `NO GENERIC PATH / DO NOT TARGET`.

No new PPTP Kubernetes service design is recommended.

## 11. Strict execution table

| Server | Install/enable | TCP1723 | GRE47 | PPP auth | MPPE | NAT multi-client | Update | Disable/cleanup |
|---|---:|---:|---:|---:|---:|---:|---:|---:|
| Windows Server 2025 | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| selected older Windows Server | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| RouterOS selected release | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| isolated Linux pptpd lab | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO cells are external execution gates. Completing them would certify only the exact legacy combination, not make PPTP recommended.
