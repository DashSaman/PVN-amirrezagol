# 047 NaiveProxy — major client UI/menu maps

Official Naive runtime is config/CLI-first. `USAGE.txt` covers:
- local `socks://`, `http://`, `redir://` listeners;
- upstream `https://` or `quic://` proxy URI and chains;
- username/password credentials;
- tunnel/idle timeouts;
- `insecure-concurrency` with upstream detectability warning;
- extra headers/resolver rules;
- logs, Chromium NetLog and TLS key-log diagnostics;
- current post-quantum behavior and explicit opt-out.

v2rayN provides a real open-source GUI reference for add/import/edit/select Naive profiles and generating underlying core config. PVNetwork must keep username/password secure-store backed, redact full URIs, and place NetLog/TLS key logging behind developer-only high-risk controls.
