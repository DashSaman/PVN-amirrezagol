# PVNetwork Research Campaign Status — 2026-08-14 — OpenVPN v1 Closure

Repository phase: research / requirements / architecture.

Shared OpenVPN family state: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

## Closure evidence

New/updated files in this closure:

- `research/upstreams/openvpn-family/ICS_OPENVPN_ANDROID.md` — Android source/service/profile/EncryptedFile/UI reference.
- `research/upstreams/openvpn-family/DEPENDENCIES_TESTS_SECURITY.md` — OpenVPN3 dependency/TLS backend/test/security/DCO/driver review.
- `research/upstreams/openvpn-family/SUPPORT_REUSE_DECISIONS.md` — OpenVPN3-core-first research decision and GUI/client reuse roles.
- `research/protocols/001-openvpn/README.md` — numbered entry synchronized to v1 handoff-ready.
- `research/upstreams/openvpn-family/README.md` — shared family synchronized to v1 handoff-ready.

## Current primary core candidate

`OpenVPN/openvpn3@1fd271caefc9a71406afdc2ff2460999dcfdb234`

License at pin: `AGPL-3.0-only OR MPL-2.0`.

Research direction: evaluate OpenVPN3 under the appropriate license/dependency/platform architecture rather than fork a GUI client.

## Main reference clients

- OpenVPN Connect — official UX/product behavior reference.
- OpenVPN GUI Windows — Windows tray/config/registry/source reference.
- ics-openvpn — Android VpnService/profile/encrypted-storage/UI reference.
- Tunnelblick — macOS menu/helper/profile reference.
- Pritunl Client — UX reference only under current non-commercial/redistribution restrictions.

## Important security/storage conclusion

`.ovpn` is an interoperability format, not the required internal database. Protect passwords/private keys/certificates with platform secure storage. Pinned ics-openvpn source provides a concrete Android reference using AndroidX Security MasterKey + EncryptedFile.

## Dependency/security conclusion

Exact OpenVPN3 release builds can differ by TLS backend, native driver/data path and optional token/plugin support. Final SBOM/advisory review must be per exact shipped platform build.

## Residual gaps

- final OpenVPN3 release pin and current security/release comparison;
- complete exact-build SBOM/advisory table;
- current issue matrices for all reference clients;
- exhaustive screenshots/menu/accessibility evidence;
- product implementation/device/E2E/Store proof;
- server installers/menus/cryptography/wire flow deferred to mandatory `COMPLETE-REFERENCE-v2`.

## Next exact action

Continue immediately to the next unfinished original-v1 family from actual repository state. Current selected next target: **SoftEther family (013–015)**, because a shared dossier exists but remains thin and the family covers SoftEther native plus EtherIP-related entries.
