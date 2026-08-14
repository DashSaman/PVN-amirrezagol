# SSTP Linux client source pin correction — 2026-08-14

Entry: 011 SSTP / MS-SSTP.

This note corrects the older repository label `sstp-client/sstp-client` used in early PVNetwork notes.

## Canonical current project

Current canonical project observed:

- GitLab namespace: `sstp-project/sstp-client`
- project: `https://gitlab.com/sstp-project/sstp-client`
- current release/tag used for the research freeze: **`1.0.20`**
- tag page: `https://gitlab.com/sstp-project/sstp-client/-/tags/1.0.20`
- tag/commit short identifier shown by canonical GitLab: **`dd243124`**
- tag date: 2024-08-02
- project activity page was still updated in 2026.

The tag is the immutable source identity for this research layer. Before implementation/vendor packaging, materialize the full object SHA and source archive digest into the build/SBOM lockfile; do not invent the long SHA here when the current connector evidence only exposes the canonical tag and short identifier.

## License and package evidence

Canonical project packaging metadata identifies:

- license: `GPLv2+` / GPLv2-family;
- package role: SSTP client;
- dependencies including `ppp`/pppd, `libevent`, and TLS/OpenSSL-related build/runtime integration.

Canonical references:

- project: `https://gitlab.com/sstp-project/sstp-client`
- tags: `https://gitlab.com/sstp-project/sstp-client/-/tags`
- current spec: `https://gitlab.com/sstp-project/sstp-client/-/blob/master/sstp-client.spec`
- README: `https://gitlab.com/sstp-project/sstp-client/-/blob/master/README.md`
- wiki: `https://gitlab.com/sstp-project/sstp-client/-/wikis/home`

## Architecture/reuse consequence

This remains a **Linux/Unix SSTP interoperability/reference client** and a potential separately packaged backend, not a permissively licensed library to copy into a closed PVNetwork binary without distribution/legal design.

The project describes itself as an SSTP client for Linux and integrates with PPP/pppd; its documented features include web-proxy support and PPP authentication methods dependent on the pppd capabilities. Exact authentication support must therefore remain backend/package/version-specific.

## Reconciliation consequence

The previous SSTP v2 gate file's “exact Linux-client pin residual” is now closed **at research source/tag level** by canonical tag `1.0.20`. Full commit SHA/archive digest remains a build/source-freeze task and is not a hidden original-v1 research gate.
