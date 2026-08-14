# 081 — TCP

Research state: `PENDING-DEEP-AUDIT`. PVNetwork implementation state: not implemented.

Classification: transport protocol, not a standalone VPN protocol.

This dossier will document how the selected PVNetwork engines rely on operating-system or runtime TCP implementations, relevant platform APIs and diagnostics, connection-state/error semantics, performance/test considerations, and why PVNetwork should not implement its own TCP stack for normal client use.