# 047 NaiveProxy — installers/deployment projects

Naive release `v150.0.7871.63-1` publishes multi-platform binaries and Android plugin APKs with GitHub SHA-256 digests. The exact Naive+Chromium version is one lifecycle unit for support/provenance.

Server fork `klzgrad/forwardproxy@d62c80d...` contains a GitHub workflow that uses Go/xcaddy to build Caddy with the naive forwardproxy fork and emits a SHA-256 for the Caddy executable. Production must pin the Caddy/xcaddy/Go/module graph rather than repeat the workflow's moving `@latest`/`@master` dependencies blindly.

No canonical Kubernetes operator/Helm is required. Docker/systemd/reverse-proxy deployment is packaging/infrastructure-specific and must freeze exact Caddy module build and certificate/auth configuration.
