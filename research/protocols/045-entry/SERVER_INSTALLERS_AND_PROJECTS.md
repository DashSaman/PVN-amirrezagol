# 045 AnyTLS — installers/deployment projects

Reviewed: 2026-08-15

`anytls-go v0.0.13` publishes official release assets for Windows x64/ARM64, Linux x64/ARM64 and macOS x64/ARM64 with GitHub SHA-256 digests. Source uses Go modules/GoReleaser and includes example server/client commands.

Because anytls-go source licensing is unresolved, binary use and source reuse require separate legal review even though release assets are public. Exact asset digest must be frozen if selected.

sing-box provides a mature cross-platform runtime/deployment ecosystem and typed AnyTLS inbound/outbound configuration, but its GPL/additional-condition license is an explicit architecture/legal boundary. Throne is a GPL cross-platform GUI reference rather than a server implementation.

No canonical Kubernetes operator/Helm chart for AnyTLS is claimed. Containers/community panels are implementation-specific and require independent source/license/security pins.
