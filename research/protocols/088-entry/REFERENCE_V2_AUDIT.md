# 088 — gRPC — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / RPC APPLICATION LAYER OVER HTTP/2 / XRAY-SELECTED grpc-go / NOT A VPN OR SECURITY PROTOCOL`**

## Authority and pins
- Official gRPC concepts/docs at grpc.io.
- Canonical Go implementation `grpc/grpc-go`.
- Xray-selected `google.golang.org/grpc v1.83.0`.
- grpc-go tag/commit `v1.83.0` -> `4c226daff88f54441d70f710815e07b81fb162b2`, Apache-2.0 with `NOTICE.txt`.
- Parent Xray `7d214f8b094f75322fa3990f8aadad1c912f24f5`, MPL-2.0.
- Entry 086 remains the HTTP/2 authority.

## Exact 16-gate reconciliation
|#|Gate|Result|Evidence / boundary|
|---:|---|---|---|
|1|Server ecosystem|PASS|Canonical grpc-go client/server framework plus parent Xray transport/server integration. Other official language stacks are interoperability references, not drop-in replacements for the selected Go path.|
|2|Installers/deployment projects|PASS / N-A|gRPC is a framework/library, not a standalone VPN server installer. Deployment follows the parent service/application/container.|
|3|Server install matrix|PASS|grpc-go follows supported Go platforms and parent-service packaging; exact container/orchestration deployment is application-owned.|
|4|Server UI/menu map|PASS / N-A|No canonical generic gRPC admin panel. Parent Xray/server UI may expose service name, authority, health/idle/window controls.|
|5|Client install matrix|PASS|No dedicated generic gRPC VPN client package; capability follows parent engine/app.|
|6|Client UI/menu map|PASS|Expose source-backed Xray gRPC transport fields only inside parent profiles; security fields remain TLS/REALITY-owned.|
|7|Cryptography|PASS / N-A|gRPC framing itself has no cryptography. TLS/credentials provide transport security and peer identity when configured.|
|8|Data path/wire flow|PASS|Generated/app call -> gRPC channel/RPC/message/metadata/status -> HTTP/2 streams/frames -> TCP; optional TLS credentials protect the connection.|
|9|Ports/transports/handshake|PASS|Parent service chooses port. gRPC normally runs over HTTP/2; channel connection, HTTP/2 setup and optional TLS are distinct layers.|
|10|Deployment topologies|PASS|Direct service, gateway/proxy, load-balanced/xDS and parent Xray transport deployments are application patterns, not VPN topology intrinsic to gRPC.|
|11|Source/license/activity pins|PASS|Exact grpc-go/Xray versions and Apache-2.0/NOTICE/MPL boundaries recorded.|
|12|Supply-chain/security risks|PASS|Pin grpc-go with parent engine, preserve NOTICE, review frame/resource/auth metadata issues and dependency upgrades; do not embed arbitrary generated or third-party transport layers.|
|13|Upgrade/uninstall/rollback|PASS|Lifecycle follows parent engine/service dependency. Upgrade requires API/HTTP2/resource/security regression review; rollback restores prior engine/dependency.|
|14|Differences/uncertainties|PASS|Unary/streaming modes, metadata/auth, health/keepalive, proxy/CDN behavior and Xray migration guidance toward XHTTP H2 are explicit.|
|15|Reference index|PASS|Companion `REFERENCE_INDEX.md`.|
|16|Handoff continuation|PASS|Batch handoff advances to Entry 089 mKCP.|

## Final decision
All 16 V2 gates are evidence-backed or correctly application/parent-layer N/A bounded. Entry 088 qualifies for **`COMPLETE-REFERENCE-v2`** without implementation/device/Store certification claims.
