# 084 — WebSocket

Research state: `PENDING-DEEP-AUDIT`. PVNetwork implementation state: not implemented.

Classification: application transport, not a standalone VPN protocol.

This dossier will compare the WebSocket implementations used by selected candidate engines and runtimes, source/license/platform behavior, configuration representation, diagnostics and tests. PVNetwork should normally inherit WebSocket support from the chosen engine/runtime rather than maintain a separate protocol stack.