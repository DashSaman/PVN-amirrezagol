# 093 — DTLS

Research state: `IN-RESEARCH`. PVNetwork implementation state: not implemented.

Classification: datagram security transport used by some VPN implementations; not a standalone consumer VPN product.

OpenConnect family research already provides one important DTLS usage reference. This dossier will compare the mature DTLS libraries/stacks actually used by selected candidate engines, pinned source/license/platform evidence, version compatibility, diagnostics, tests and the decision to inherit DTLS through those engines rather than create a separate PVNetwork implementation.