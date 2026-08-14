# EtherIP/IPsec — Peer UI / Menu Map

Review date: 2026-08-14 UTC

No canonical consumer UI exists. Operator/peer UI should keep layers visible:

- EtherIP peer / mapping / bridge target;
- selected IPsec backend;
- auth/secret/certificate **reference** rather than raw secret display;
- IKE mode/version only where backend actually supports/uses it;
- ESP protection/security policy summary;
- outer addresses and NAT-T state where relevant;
- IKE/SA/rekey diagnostics;
- ESP counters/path;
- EtherIP mapping/IPC/interface state;
- bridge/Virtual Hub state;
- cleanup/service-conflict warnings.

Never expose one opaque “secure EtherIP” checkbox that hides whether protection is actually active.
