# OpenConnect / ocserv-compatible — Server Ecosystem

Review date: 2026-08-14 UTC

Primary public server: **ocserv 1.5.0**, canonical `openconnect/ocserv` GitLab signed tag, commit `49f9956eeeffd613e4bcac3f6450c682ec21e75a`, GPLv2+.

ocserv is designed as an OpenConnect-compatible VPN server and implements the TLS/CSTP + DTLS family used by AnyConnect-compatible clients. Canonical project documentation describes worker privilege separation, config, certificates, TCP/UDP listeners, TUN/routing, authentication backends, per-user/group policy and `occtl` control.

Reference clients:

- OpenConnect v9.21 client/library, commit `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1.
- OpenConnect GUI and NetworkManager-openconnect frontends are separate applications/components with separate licenses and lifecycle.
- Cisco Secure Client is a proprietary interoperability reference, not reusable source.

Reuse direction: ocserv is a strong controlled/open server candidate; libopenconnect is the preferred public client engine behind the PVNetwork adapter.
