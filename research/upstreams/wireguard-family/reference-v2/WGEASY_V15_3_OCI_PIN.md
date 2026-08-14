# wg-easy v15.3.0 — OCI registry pin

Status: registry provenance observation; **not an install/update/rollback execution receipt**.

Audit date: 2026-08-14  
Package: `ghcr.io/wg-easy/wg-easy`  
Stable application release: `v15.3.0`  
Stable source commit: `2dc8ba779216929c10c1998341d36963fe0eca7a`

## Registry observation

The GitHub Container Registry package page for `wg-easy/wg-easy` currently groups tags `15.3`, `15.3.0`, and `15` on digest:

`sha256:b6ad56f6be5c879ce9ea9a7e577a05c95cab9681eb74d8a96563fd59efc818e6`

Registry source: <https://github.com/wg-easy/wg-easy/pkgs/container/wg-easy>

The moving `development` image is shown separately with a different digest, which reinforces the repository rule that a moving tag is not certification evidence.

## PVNetwork pinning rule

For a deployment intended to reproduce the registry observation above, record the image as an immutable digest reference rather than only `:15` or `:15.3.0`:

`ghcr.io/wg-easy/wg-easy@sha256:b6ad56f6be5c879ce9ea9a7e577a05c95cab9681eb74d8a96563fd59efc818e6`

This record closes the **registry-side immutable digest identification** gap for the observed v15.3 image. It does not prove that any PVNetwork host pulled or executed these exact bytes.

## Still required before production certification

- pull/inspect receipt from a representative target host;
- architecture/platform-specific manifest resolution where applicable;
- SBOM/provenance/attestation capture and validation if exposed by the registry/build pipeline;
- clean install receipt;
- update receipt from the previous approved release;
- rollback receipt preserving configuration/database integrity;
- post-start health/auth/tunnel tests;
- confirmation that transient `INIT_*` secrets are absent from the steady-state container environment.

Therefore entries 002/003 remain `PENDING` for COMPLETE-REFERENCE-v2.
