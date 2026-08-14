# Server install matrix

| Environment | State | Evidence boundary |
|---|---|---|
| ISA6000 / ISA8000 hardware | Supported | ICS 22.8R2 release platform guide |
| VMware ESXi VA | Supported/qualified per release | 22.8R2 documents fresh VMware installation; later 22.8R2.x release notes define point-release upgrade paths |
| Generic Ubuntu/Debian/RHEL host package | N/A | ICS is not distributed as a generic host package |
| Docker/Podman | N/A | no canonical container deployment identified |
| Kubernetes/Helm | N/A | no canonical orchestration deployment identified |
| Windows Server host install | N/A | server is appliance software, not a Windows service |
| macOS server | N/A | no meaningful canonical deployment |

Do not turn unknown community packaging into support. Re-check the exact Ivanti Supported Platforms Guide before implementation/deployment.

Sources: https://help.ivanti.com/ps/help/en_US/ICS/22.x/22.8R2/rn/support_and_compatibility.htm ; https://help.ivanti.com/ps/help/en_US/ICS/22.x/22.8R2.2/spg/landingpage.htm