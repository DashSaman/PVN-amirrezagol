# Fortinet FortiGate SSL VPN — Server Deployment Matrix

Review date: 2026-08-14 UTC

| Target / branch | Tunnel-mode status | V2 conclusion |
|---|---|---|
| FortiGate / FortiOS 7.4.12 | SSL VPN tunnel mode documented and supported subject to exact model/support matrix | SELECTED MAINTAINED LEGACY REFERENCE |
| FortiOS 7.6.0–7.6.2 | tunnel mode existed, but 2 GB RAM FortiGate models lost SSL VPN support beginning in 7.6.0 | VERSION/MODEL-BOUNDED; not selected as long-term baseline |
| FortiOS 7.6.3 and later | SSL VPN tunnel mode removed; migrate to IPsec VPN | **N/A-TUNNEL / RETIRED** |
| FortiOS 7.6.3+ web/Agentless VPN | browser-based Agentless VPN | DIFFERENT MODE; not entry-019 tunnel replacement |
| FortiGate VM | proprietary FortiOS platform where selected version/license/model supports tunnel mode | VENDOR-SPECIFIC; not generic Linux VM package |
| Generic Linux/Windows server | no public FortiGate SSL-VPN server package | N/A-PROPRIETARY |
| OCI/Kubernetes | no canonical FortiGate SSL-VPN tunnel server workload selected | N/A |

Fortinet also documents model-specific exclusions. A FortiOS version number alone is insufficient to claim tunnel-mode availability.

References:
- https://docs.fortinet.com/document/fortigate/7.6.0/best-practices/566002/ssl-vpn-and-agentless-vpn
- https://docs.fortinet.com/document/fortigate/7.6.3/fortios-release-notes/173430/ssl-vpn-tunnel-mode-replaced-with-ipsec-vpn
