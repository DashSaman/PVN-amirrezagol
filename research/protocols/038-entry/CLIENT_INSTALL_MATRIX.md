# 038 VMess — client install matrix

Reviewed: 2026-08-15

VMess client support is available through Xray-core or wrappers that embed/manage Xray or another compatible core. Wrapper license and core license remain separate.

| Platform | Selected reference | Pin / license | Treatment |
|---|---|---|---|
| Windows | v2rayN | `2dust/v2rayN@230a2f6773d09a12ce4130404aa5571b20de63a2`, GPL-3.0 | major VMess/Xray GUI reference |
| Linux | v2rayN + bare Xray | same pin + Xray MPL-2.0 | desktop GUI/CLI reference |
| macOS | v2rayN + Xray ecosystem | same pin + Xray MPL-2.0 | desktop reference; Apple networking/package rules remain wrapper-specific |
| Android | v2rayNG | `2dust/v2rayNG@b348ca792bd26b207c4969fb97c8c384e98f2628`, v2.3.4, GPL-3.0 | major Android VMess client reference |
| iPhone/iPad | multiple Xray/V2Ray-family clients exist | mixed licenses/source availability | app/version-specific only; no canonical Apple-source claim |
| Android TV / Google TV | not proven by Android-phone support alone | — | do not infer TV UX/device certification |

The protocol semantic source pin for this audit is Xray `v26.7.28` / `5ca6f4b...`. Exact Store listings, signing and real-device results remain later certification work.
