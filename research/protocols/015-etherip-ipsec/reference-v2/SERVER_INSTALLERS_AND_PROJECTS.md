# EtherIP/IPsec — Installers and Deployment Projects

Review date: 2026-08-14 UTC

## SoftEther composed server

Use the canonical SoftEther deployment model already reviewed for entry 013 and shared family evidence. Entry 015 additionally owns:

- EtherIP mapping/bridge state;
- IPsec service enablement;
- IKE authentication/secret state;
- ESP/NAT-T/raw-ESP packet ownership;
- potential conflict with host OS IPsec processing/services;
- firewall exposure for IKE/NAT-T/ESP as actually configured.

Pinned SoftEther source build/package facts include CMake/C99, tracked submodules and DEB/RPM component packaging. Container deployment is privilege/topology-sensitive and no community image is selected without source/image-digest review.

## OpenBSD native composition

`etherip(4)` + native IPsec policy/flow tooling is OS-owned. No third-party “EtherIP/IPsec installer” is required.

## Alternative IPsec backends

Existing strongSwan/Libreswan research is reusable only as backend/reference evidence. A future EtherIP + alternative-backend deployment needs an explicit supported selector/routing/ownership design; it is not automatically certified by having both packages installed.

## Lifecycle / supply chain

Back up configuration and secret references, pin old/new binaries/packages, preserve compatible EtherIP mappings and IPsec policy, verify OS service ownership on upgrade/rollback, and remove firewall/bridge/IPsec objects on decommission. No live lifecycle receipt is claimed.
