# Palo Alto GlobalProtect — Client UI / Menu Map

Review date: 2026-08-14 UTC

The proprietary GlobalProtect app UI is platform/release-specific. The reusable V2 map records concepts rather than copying Palo Alto trade dress.

## Core endpoint surfaces

- portal / connection target and managed configuration source;
- Connect / Disconnect and explicit connection state;
- portal authentication vs gateway authentication state where both occur;
- browser/SAML/MFA interaction when required by headend policy;
- selected gateway / gateway reachability;
- tunnel mode/status: SSL vs IPsec where exposed/diagnosable;
- assigned IP, routes/split tunnel and DNS/network diagnostics where exposed;
- Settings / connection information / troubleshooting/log collection;
- certificate/device/user identity state;
- optional HIP/posture state only when the licensed/platform-specific product actually provides it;
- software/update state.

## OpenConnect compatible path

OpenConnect GP mode uses the public OpenConnect CLI/API/frontend model already researched under `research/upstreams/openconnect-family/`. Its screens/options must remain branded and licensed as the selected frontend; do not clone Palo Alto UI or claim proprietary feature parity.

Secrets, cookies, private keys and tokens must be redacted and stored through the selected platform secret model.
