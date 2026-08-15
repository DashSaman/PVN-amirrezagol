# 048 Snell — installers and deployment projects

Reviewed: 2026-08-15

## Official server packaging

Surge's authoritative Snell page currently publishes direct zip downloads for **snell-server-v5.0.1** on Linux amd64, i386, aarch64 and armv7l. The official server is described as a single binary with no dependency other than glibc. v5 supports systemd Socket Activation and egress-interface controls.

Public download availability is not a redistribution, modification or commercial-use grant. PVNetwork must record vendor terms/authorization separately before bundling or mirroring the binary.

Supply-chain warning: a 2025 Surge community report documented an armv7l v5.0.1 package/version mismatch that the Surge team attributed to a publishing-process issue and corrected. Therefore package freeze must verify downloaded artifact version/architecture/hash rather than trusting filename alone.

## Community deployment

`missuo/opensnell` offers third-party Go deployment/client/server tooling under GPL-3.0 and currently advertises v4/v5 compatibility. It is not official Surge distribution and cannot substitute for official rights/compatibility evidence.

No canonical official Docker/Helm/Kubernetes project is published for Snell. Such deployments are evidence-backed N/A at the official-project layer and must be separately created/reviewed only if vendor rights permit the binary usage model.
