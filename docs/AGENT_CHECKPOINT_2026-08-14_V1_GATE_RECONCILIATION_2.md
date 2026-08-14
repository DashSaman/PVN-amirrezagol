# PVNetwork Agent Checkpoint — V1 Gate Reconciliation 2

Date: 2026-08-14

Active campaign: `COMPLETE-RESEARCH-v1`

Strict V1 tracker after this slice: **4 / 93 COMPLETE-RESEARCH-v1**.

Hard V2 phase lock remains enabled.

## Completed entries in this fresh corrected run

- **011 SSTP / MS-SSTP** → `COMPLETE-RESEARCH-v1`
- **013 SoftEther VPN Protocol** → `COMPLETE-RESEARCH-v1`
- **014 EtherIP** → `COMPLETE-RESEARCH-v1`
- **015 EtherIP/IPsec** → `COMPLETE-RESEARCH-v1`

Each promotion has its own `V1_GATE_RECONCILIATION.md` mapping all 20 original research gates. Runtime/device/interoperability/Store/production proof was not fabricated or used as a hidden completion gate.

## Additional SSTP correction

Early research used a stale GitHub-style identity for the Linux SSTP client. Current canonical evidence was refreshed to:

- project: `sstp-project/sstp-client` on GitLab;
- research tag: `1.0.20`;
- canonical GitLab short identifier: `dd243124`;
- license packaging metadata: GPLv2+.

Durable correction:

- `research/upstreams/classic-tunnels-family/SSTP_CLIENT_SOURCE_PIN_2026-08-14.md`

A future build lockfile still needs the full object SHA/archive digest. This is source-freeze evidence, not a hidden v1 research blocker.

## Key research commits in this slice

SoftEther 013:

- `564576c42820f9507c40d9d694b050bebab62e10` — gate reconciliation
- `ba02454d1a6d980bddec72cc97d4b150e2b0d5f7` — tracker promotion

EtherIP 014:

- `a953311f29331305973d6218afe37c7bae727187` — gate reconciliation
- `286ab31e46bd8f02af636c256d2f892a37333637` — tracker promotion

EtherIP/IPsec 015:

- `d01d9f3dfea459ecb07cb3e64ce56da676a97d8d` — gate reconciliation
- `1c252cd862d531f2f27c86fd3511c71eabb1d997` — tracker promotion

SSTP 011:

- `95ea1905745fb4b6544f7e97021780e71f7f6e86` — Linux client canonical pin correction
- `be3433199777dcef07f65b328ddeae42016d5f32` — gate reconciliation
- `336007de2d5cfd9406aa755d9c988ea61d5adc46` — tracker promotion

## Next work

Next mature V1 candidate: **012 PPTP**. A detailed v2 dossier/gate reconciliation already exists, so the next run should recover original-v1 evidence from it, apply the strict 20-gate V1 template, preserve PPTP's legacy/security classification, and promote only if every applicable original-v1 research gate is genuinely evidenced.

If entry 012 exposes a true v1 evidence gap, record that exact gap and continue to another mature V1 entry rather than returning to V2 or waiting for runtime certification.
