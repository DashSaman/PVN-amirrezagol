# PVNetwork Protocol / Technology Matrix

Status: **Research scope only. No entry below is currently implemented by PVNetwork.**

This list intentionally includes VPN protocols, proxy protocols, enterprise VPN compatibility families, mesh/overlay technologies, site-to-site technologies, security layers, and transports. Therefore it must **not** be described publicly as “93 VPN protocols.”

## A. Classical VPN / tunneling

1. OpenVPN — VPN — RESEARCH
2. WireGuard — VPN — RESEARCH
3. AmneziaWG — VPN derivative — RESEARCH
4. IKEv2/IPsec — VPN — RESEARCH
5. IKEv1/IPsec — VPN — RESEARCH
6. IPsec ESP — IPsec component — RESEARCH
7. IPsec AH — IPsec component — RESEARCH
8. L2TP/IPsec — VPN — RESEARCH
9. L2TPv3 — tunnel — RESEARCH
10. L2TPv3/IPsec — site-to-site VPN — RESEARCH
11. SSTP / MS-SSTP — VPN — RESEARCH
12. PPTP — legacy VPN — LEGACY
13. SoftEther VPN Protocol — VPN — RESEARCH
14. EtherIP — tunnel — RESEARCH
15. EtherIP/IPsec — site-to-site VPN — RESEARCH

## B. Enterprise VPN compatibility families

16. Cisco AnyConnect — enterprise VPN — RESEARCH
17. OpenConnect / ocserv-compatible — enterprise VPN — RESEARCH
18. Palo Alto GlobalProtect — enterprise VPN — RESEARCH
19. Fortinet FortiGate SSL VPN — enterprise VPN — RESEARCH
20. Pulse Secure — enterprise VPN — RESEARCH
21. Ivanti Connect Secure — enterprise VPN — RESEARCH
22. Juniper Network Connect — enterprise VPN — RESEARCH
23. F5 BIG-IP SSL VPN — enterprise VPN — RESEARCH
24. Array Networks SSL VPN — enterprise VPN — RESEARCH
25. Check Point VPN / SNX — enterprise VPN — RESEARCH
26. SonicWall NetExtender / SSL VPN — enterprise VPN — RESEARCH
27. SonicWall Global VPN / IPsec — enterprise VPN — RESEARCH
28. Sophos SSL VPN — enterprise VPN — RESEARCH
29. Sophos IPsec Remote Access — enterprise VPN — RESEARCH
30. WatchGuard IKEv2 VPN — enterprise VPN — RESEARCH
31. WatchGuard SSL VPN — enterprise VPN — RESEARCH
32. WatchGuard L2TP VPN — enterprise VPN — RESEARCH
33. Aruba VIA — enterprise VPN — RESEARCH
34. Citrix Secure Access / Gateway VPN — enterprise VPN — RESEARCH
35. Barracuda TINA VPN — enterprise VPN — RESEARCH
36. Juniper Secure Connect — enterprise VPN — RESEARCH

## C. Modern proxy / tunnel protocols

37. VLESS — proxy/tunnel protocol — RESEARCH
38. VMess — proxy/tunnel protocol — RESEARCH
39. Trojan — proxy protocol — RESEARCH
40. Shadowsocks — proxy protocol — RESEARCH
41. Shadowsocks 2022 — proxy protocol — RESEARCH
42. Hysteria — proxy/tunnel protocol — RESEARCH
43. Hysteria2 — proxy/tunnel protocol — RESEARCH
44. TUIC — proxy/tunnel protocol — RESEARCH
45. AnyTLS — proxy protocol — RESEARCH
46. ShadowTLS — security/proxy layer — RESEARCH
47. NaiveProxy — proxy technology — RESEARCH
48. Snell — proxy protocol — RESEARCH
49. SOCKS4 — proxy protocol — RESEARCH
50. SOCKS4a — proxy protocol — RESEARCH
51. SOCKS5 — proxy protocol — RESEARCH
52. HTTP Proxy — proxy protocol — RESEARCH
53. HTTPS / HTTP CONNECT — proxy protocol — RESEARCH
54. SSH Tunnel — tunnel technology — RESEARCH
55. Tor SOCKS — anonymity/proxy technology — RESEARCH

## D. Mesh / overlay networking

56. Tailscale — mesh VPN — RESEARCH
57. ZeroTier — overlay network — RESEARCH
58. NetBird — mesh VPN — RESEARCH
59. Netmaker — network orchestration — RESEARCH
60. Nebula — overlay network — RESEARCH
61. Tinc — mesh VPN — RESEARCH
62. innernet — network manager — RESEARCH

## E. Site-to-site / router-oriented technologies

63. GRE — tunnel — RESEARCH
64. GRE over IPsec — site-to-site VPN — RESEARCH
65. IP-in-IP / IPIP — tunnel — RESEARCH
66. IPIP over IPsec — site-to-site VPN — RESEARCH
67. VTI/IPsec — site-to-site VPN — RESEARCH
68. XFRM/IPsec — IPsec architecture — RESEARCH
69. VXLAN — overlay/tunnel — RESEARCH
70. VXLAN over IPsec — site-to-site overlay — RESEARCH
71. DMVPN — dynamic site-to-site VPN — RESEARCH
72. Cisco FlexVPN — IKEv2-based enterprise VPN — RESEARCH
73. GETVPN — group VPN — RESEARCH

## F. Security / transport-related layers

These are not standalone VPN protocols.

74. REALITY — security layer — RESEARCH
75. XTLS — security/transport layer — RESEARCH
76. XTLS Vision — flow/security mode — RESEARCH
77. TLS — security transport — RESEARCH
78. uTLS / TLS Fingerprinting — TLS compatibility layer — RESEARCH
79. Cloak — obfuscation layer — RESEARCH
80. TLS Fragmentation — transport behavior — RESEARCH

## G. Transport technologies

These are transports, not VPN protocols.

81. TCP — transport — RESEARCH
82. UDP — transport — RESEARCH
83. QUIC — transport — RESEARCH
84. WebSocket — transport — RESEARCH
85. HTTP/1.1 — application transport — RESEARCH
86. HTTP/2 — application transport — RESEARCH
87. HTTP/3 — application transport — RESEARCH
88. gRPC — RPC/transport — RESEARCH
89. mKCP — transport — RESEARCH
90. KCP — transport — RESEARCH
91. XHTTP — transport — RESEARCH
92. RAW — core-specific/raw transport concept — RESEARCH
93. DTLS — datagram security transport — RESEARCH

---

# Current research principle

The eventual application should use the smallest practical number of mature, maintainable, legally suitable engines to cover the useful portion of this scope.

A technology may only be marked as PVNetwork-supported after its own implementation and verification evidence exists.

Future research revisions should add, where appropriate:

- best reference client
- candidate upstream project
- license
- maintenance status
- platform feasibility
- distribution/store constraints
- known limitations
- test strategy
- final decision
