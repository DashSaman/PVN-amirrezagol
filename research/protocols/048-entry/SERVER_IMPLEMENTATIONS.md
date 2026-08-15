# 048 Snell — server implementations / ecosystem

Reviewed: 2026-08-15

## Official implementation boundary

Snell is a proprietary encrypted proxy protocol developed by the Surge team. The authoritative vendor knowledge base currently publishes prebuilt **Snell Server v5.0.1** Linux binaries for amd64, i386, aarch64 and armv7l and states that Snell is intended for Surge users only. The vendor explicitly asks others not to reverse-analyze the protocol or make compatible clients.

No public official Snell server source repository, source tag/commit or open-source license is published in the authoritative material reviewed. The official implementation is therefore **proprietary / binary-only / reference-use**, and no source pin is fabricated.

Current generation boundary:
- stable public server baseline: **v5.0.1**;
- v5 server is backward compatible with v4 clients;
- current Surge client documentation supports v6 but explicitly marks Snell v6 **beta** and warns that incompatible protocol changes may occur;
- v6 must therefore be modeled as an explicit beta generation with compatible client/server pairing, not as the stable server replacement.

## Third-party interoperability references

`missuo/opensnell@3100984fd7c3a2bd7b41e292ad41f10d928bfb2d` is an active Go v4/v5 client/server implementation, repository metadata GPL-3.0, release v1.0.4. It is **interoperability/research evidence only**. Its GPL license grants rights only to that third-party code and does not grant rights to Surge's proprietary protocol, official binaries, branding or vendor assets.

`icpz/open-snell` is an older community implementation and remains historical reference only.

PVNetwork default decision: `PROPRIETARY SURGE COMPATIBILITY TARGET / OFFICIAL BINARY AND DOC REFERENCE ONLY / NO DIRECT REVERSE-ENGINEERED IMPLEMENTATION OR BUNDLING WITHOUT RIGHTS REVIEW OR VENDOR AUTHORIZATION`.
