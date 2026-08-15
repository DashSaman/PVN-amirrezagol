# Cisco FlexVPN — Client / Peer Install Matrix

Reviewed: 2026-08-15

FlexVPN remote-access interoperability is based on IKEv2/IPsec plus Cisco framework/profile behavior.

Cisco IOS XE is the canonical router/client/server framework. Cisco documentation cites interoperability with IKEv2 clients including Cisco AnyConnect IKEv2 and Windows IKEv2 in supported scenarios. Generic strongSwan/OS-native IKEv2 capability is reference evidence only and does not prove every Cisco-specific authorization/configuration attribute.

Windows/macOS/iOS/Android/Linux support must therefore be represented as exact profile/feature certification, not a Boolean “FlexVPN supported” derived from generic IKEv2 availability.