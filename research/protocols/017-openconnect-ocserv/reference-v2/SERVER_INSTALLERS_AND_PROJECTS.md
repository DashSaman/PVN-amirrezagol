# OpenConnect / ocserv — Installers and Deployment Projects

Review date: 2026-08-14 UTC

Canonical server project: `openconnect/ocserv` **1.5.0** / `49f9956eeeffd613e4bcac3f6450c682ec21e75a`, GPLv2+.

Official source project provides build/config examples and GitLab CI. Basic installation requires server certificate/key, dedicated unprivileged worker user/group and a configuration file. Current sample config documents PAM/plain/certificate/RADIUS/GSSAPI auth, per-vhost config, routes, DNS, TUN policy and firewall helpers.

Do not bless arbitrary install scripts/images. Distro packages may be used only with exact package source/version/security tracking. OCI deployment must preserve TUN/network capabilities, certificate/config persistence, worker privilege separation and firewall/routing ownership. Kubernetes is gateway/stateful-networking specific, not a generic stateless workload.

Upgrade/rollback: pin old/new ocserv binaries/package and config, review release/security notes, preserve compatible cert/auth/routing state, and roll back binary+config coherently. Version 1.5.0 contains security fixes; older deployment must be evaluated against those fixes.
