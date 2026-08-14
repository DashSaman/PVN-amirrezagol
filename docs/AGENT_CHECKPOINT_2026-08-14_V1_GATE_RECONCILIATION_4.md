# PVNetwork Agent Checkpoint — V1 Gate Reconciliation 4

Date: 2026-08-14

## Campaign truth

- Active campaign: `COMPLETE-RESEARCH-v1`
- Strict V1 complete: **15 / 93**
- V2: hard-locked until V1 is 93/93
- Implementation/certification: not claimed

## Entries promoted in this slice

- 001 OpenVPN
- 002 WireGuard
- 003 AmneziaWG
- 004 IKEv2/IPsec
- 005 IKEv1/IPsec
- 006 IPsec ESP
- 007 IPsec AH

Each promotion has a dedicated `V1_GATE_RECONCILIATION.md` mapping the exact 20 original research gates to traceable dossier evidence or evidence-backed N/A treatment.

## Fresh evidence added

### OpenVPN

OpenVPN3 current release freshness was resolved from canonical upstream tags:

- `release/3.11.7`
- commit `18edfae7e7fd8051c93bd4746ec69be91eb02dbb`
- upstream release commit is signed/verified

The existing deep source-analysis pin remains distinct from the current release reference so implementation cannot silently assume they are the same build.

### WireGuard / AmneziaWG

The already-deep family and second-layer evidence was reconciled back against the original 20-gate contract. Runtime/install/Store/interoperability receipts remain certification evidence and were not fabricated.

### IKE/IPsec

The strongSwan family now has a mature source/reference dossier and current release/security pin:

- strongSwan 6.0.7
- target commit `5973ff8e41deef4e015e1138a2de688acedf6f75`

Entries 006 ESP and 007 AH use evidence-backed data-plane/N-A-standalone treatment instead of inventing separate consumer applications.

## Commits from this slice

- `a174bb3a3d593bdf96c8fabc0d0fdd9e0854d736` — OpenVPN V1 reconciliation
- `b44f36166a9577e3cd255a0550eb74ff8254e4d2` — OpenVPN3 3.11.7 release pin
- `680fde6e8cf64dddd85e2f15caf88b951c641eb9` — tracker promotes OpenVPN
- `56b8dc80150422a3ca94030e964986940c429670` — WireGuard V1 reconciliation
- `3a6bdb45be679f5c4fa9dff4e9bebfd42ce874f4` — AmneziaWG V1 reconciliation
- `dff7c8217c741fee7e33e05544448ab5248375b7` — tracker promotes WireGuard/AWG
- `faacae3501f58856c46482d33e843dce17844fbd` — IKEv2 V1 reconciliation
- `24ab5cef61ccad9c85772d9fe3c00b82c6ba636f` — IKEv1 V1 reconciliation
- `cd0691404ce52643b011336bf0ba49eb2dee21fc` — ESP V1 reconciliation
- `3369626b381a0325fe8c93435ca8e64028c0e14a` — AH V1 reconciliation
- `d4b34adadf4e1f19363253fa6e53fd9c91361539` — tracker promotes entries 004-007
- `77a62261719ac54b554af5724e68ea82280a04e8` — machine state advances to entry 016

## Exact next action

Reconcile entry **016 Cisco AnyConnect** against the original 20-gate contract using the shared OpenConnect-family evidence. Then continue entries 017-025 where evidence is mature, preserving proprietary-source uncertainty and vendor-specific distinctions rather than flattening them into generic OpenConnect support.
