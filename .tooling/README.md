# Ubuntu tools useful to prepare dev environment

if you decided to use VSCode remote WSL2 extension, find below my list of tools which I found useful on my WSL2 dev environment

## Option: WSL2
- of course [install WSL2](https://docs.microsoft.com/en-us/windows/wsl/install-win10)
- enable [remote WSL2 extension](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-wsl) to integrate your VSCode with WSL2
- (optionally) [reuse yours .ssh windows folder in WSL2](https://devblogs.microsoft.com/commandline/sharing-ssh-keys-between-windows-and-wsl-2/)
- [install kubernetes](https://gist.github.com/wholroyd/748e09ca0b78897750791172b2abb051)

## Option: Ubuntu
- [install kubernetes](https://ubuntu.com/kubernetes/install#single-node)

## Common part for: WSL2 / Ubuntu
- install [sdk man](https://sdkman.io/install) and then
  - **sdk list** or **sdk list java** to see list of available software, and as the next step e.g.
  - **sdk install java** to install java LTS
  - **sdk install scala** to install scala
  - **sdk install sbt** to install sbt
- install [dotnet core 3.1](https://docs.microsoft.com/dotnet/core/install/linux-package-manager-ubuntu-1804)
- install [Node Version Manager](https://github.com/nvm-sh/nvm)
- [Install Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli-apt)
- [Install Terraform on WSL2](https://techcommunity.microsoft.com/t5/Azure-Developer-Community-Blog/Configuring-Terraform-on-Windows-10-Linux-Sub-System/ba-p/393845)
- [Install Docker on WSL2](https://dev.to/bartr/install-docker-on-windows-subsystem-for-linux-v2-ubuntu-5dl7)
- Install [go](https://www.linuxfordevices.com/tutorials/ubuntu/install-go-on-ubuntu-debian)
