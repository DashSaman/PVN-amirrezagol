# PVNetwork handoff — TUIC v5 V2 complete — 2026-08-15

After promotion: V1 **93/93**, V2 **44/93**, first PENDING **045 — AnyTLS**. Always re-fetch live `main` before writes.

TUIC canonical spec is v0x05 at `tuic-protocol/tuic@8e118f...`; the spec repository has no official implementation. TLS-exporter auth uses UUID + raw password; TCP uses bidirectional QUIC streams; UDP uses 16-bit association IDs with either QUIC DATAGRAM/native or unidirectional-stream/quic mode; Heartbeat uses DATAGRAM and command errors are implementation-defined. Current serious pins remain ClashRS Apache-2.0 `b0538e...`, shoes MIT `7a5a8e...`, and Itsusinn/tuic `0eef0b...` with copyleft/legal complexity.

Exact next action: **045 AnyTLS**. Identify protocol authority/spec and current maintained server/client/core implementations. Map TLS security, password/auth, padding/multiplexing, UDP-over-stream, fallback/probe behavior, install/admin/client matrices, source/license/releases and wire flow independently; do not infer AnyTLS from TLS alone. Then continue to 046 ShadowTLS.
