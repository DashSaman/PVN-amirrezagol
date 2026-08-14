# 082 — UDP

Research state: `PENDING-DEEP-AUDIT`. PVNetwork implementation state: not implemented.

Classification: transport protocol, not a standalone VPN protocol.

This dossier will document how selected engines use operating-system/runtime UDP facilities, platform differences, observable error/state behavior, test/performance considerations and why PVNetwork should rely on mature platform/runtime implementations rather than implement a general UDP stack.