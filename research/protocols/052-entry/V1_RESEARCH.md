# 052 — HTTP Proxy — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: foundational HTTP proxy capability.

Decision: **`FOUNDATIONAL PROXY CAPABILITY / MATURE HTTP STACK`**.

PVNetwork should use an existing approved networking core/library or native HTTP stack rather than add a dedicated VPN engine.

Canonical profile should separate endpoint, authentication secret reference, proxy headers/policy, DNS behavior and source metadata. Do not leak proxy credentials into logs or exported support bundles.

Later v2 adds standards/source implementations, install matrices, menus and technical request/forwarding flow.
