# 047 NaiveProxy — server implementations / ecosystem

Reviewed: 2026-08-15

Canonical client/runtime: `klzgrad/naiveproxy` stable tag `v150.0.7871.63-1` -> `3ba967e2d36cc133a896e81a36257ad4c6ea20f4`, tree `56158501cd8c99d6b5cf81d933d084e031de277a`, Chromium `150.0.7871.63`, BSD-3-Clause root license plus Chromium third-party notices/dependencies. Upstream warns not to track rebased `master`; stable tag is the provenance anchor.

Canonical maintained server path: `klzgrad/forwardproxy` branch `naive` -> `d62c80d3dd2c706b6b87579844d2397bddd18317`, tree `9e52b3b9043d7af6fa0e240ac8b3b29fe1ddb4c2`, Apache-2.0, built into Caddy with xcaddy. It adds Naive padding behavior to forward-proxy CONNECT service.

Ordinary compatible H2 proxies/HAProxy frontends can participate in some topologies but do not become NaiveProxy implementations merely by supporting CONNECT. Client-side Chromium network-stack behavior and padding negotiation remain Naive-specific.
