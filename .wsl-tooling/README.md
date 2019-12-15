# WSL tools useful to work

if you decided to use VSCode remote WSL extension, find below my list of tools which I found useful on my WSL dev environment

- of course [install WSL](https://docs.microsoft.com/en-us/windows/wsl/install-win10)
- enable [remote WSL extension](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-wsl) to integrate your VSCode with WSL
- [reuse yours .ssh windows folder in WSL](https://devblogs.microsoft.com/commandline/sharing-ssh-keys-between-windows-and-wsl-2/)
- [Install Terraform on WSL](https://techcommunity.microsoft.com/t5/Azure-Developer-Community-Blog/Configuring-Terraform-on-Windows-10-Linux-Sub-System/ba-p/393845)
- [Install Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli-apt)
- install [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/#install-kubectl-on-linux) to monitor remote aks instances
- install openjdk-11 **sudo apt install openjdk-11-jdk**
- install [dotnet core 3.1](https://docs.microsoft.com/dotnet/core/install/linux-package-manager-ubuntu-1804)
- use **export DOCKER_HOST='tcp://0.0.0.0:2375'** to allow working WSL docker commands with yours windows docker desktop
