# EtherIP/IPsec — Server / Control UI Map

Review date: 2026-08-14 UTC

There is no protocol-defined universal panel. Management must expose the two layers separately.

## EtherIP controls

- peer/client identity and outer addresses;
- bridge / Virtual Hub mapping;
- SoftEther EtherIP ID → Hub/User/Password mapping where that backend is used;
- EtherIP forwarding/mapping status.

## IPsec/IKE controls

- IPsec service enabled state;
- backend identity (SoftEther built-in vs native/other approved backend);
- IKE authentication credential reference / PSK or certificate ownership as supported;
- exact IKE/ESP policy choices supported by the backend;
- SA/rekey/status;
- NAT-T/raw-ESP path and firewall state where relevant;
- host OS IPsec-service conflict/ownership warnings.

## Status rule

Show IKE/SA, ESP protection, EtherIP mapping and bridge/Hub health separately. “IKE connected” is not proof of EtherIP forwarding; “EtherIP mapping active” is not proof of ESP protection.
