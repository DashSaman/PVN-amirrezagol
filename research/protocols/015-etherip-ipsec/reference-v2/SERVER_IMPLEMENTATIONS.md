# EtherIP/IPsec — Server / Peer Implementations

Review date: 2026-08-14 UTC

Entry 015 is a composition: **EtherIP L2 encapsulation + an IPsec/IKE protection backend**.

## Primary composed implementation

`SoftEtherVPN/SoftEtherVPN@b1f7ef00040786d00bfa06c27fa463d106851e0c`:

- `src/Cedar/Proto_EtherIP.c` — EtherIP L2 path;
- `src/Cedar/Proto_IPsec.c` — IPsec service, raw/UDP-encapsulated ESP dispatch and OS-service ownership;
- `src/Cedar/Proto_IKE.c` — reviewed IKE/ISAKMP + ESP stack.

The pinned source explicitly ties IPsec service use to `EtherIP_IPsec || L2TP_IPsec`. The reviewed IKE code shows Main/Aggressive/Quick Mode, so this specific SoftEther path is **IKEv1-style**. This is not generalized to every possible EtherIP/IPsec deployment.

## Independent platform composition

OpenBSD `etherip(4)` documents protecting EtherIP using native IPsec flows/selectors (including protocol 97 selection). This is an independent OS-native combined topology/reference.

The broader IPsec implementation ecosystem is separately completed under entries 004–007 / `research/upstreams/strongswan-family/`. Those backends may be considered only where the exact composed topology is supported; their presence does not automatically make every EtherIP peer compatible.

Reuse decision: SoftEther composed path is the strongest single-runtime candidate; otherwise use typed EtherIP + approved IPsec backend composition with explicit backend ownership.
