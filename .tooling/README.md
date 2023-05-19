# Ubuntu tools useful to prepare dev environment

if you decided to use VSCode remote WSL2 extension, find below my list of tools which I found useful on my WSL2 dev environment

## Option: WSL2
- of course [install WSL2](https://docs.microsoft.com/en-us/windows/wsl/install-win10), image Ubuntu 22.04
- enable [remote WSL2 extension](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-wsl) to integrate your VSCode with WSL2
- [install Docker](https://adsorder-dev.netlify.app/cuan-https-techcommunity.microsoft.com/t5/itops-talk-blog/using-wsl-2-on-windows-server-2022-to-run-linux-containers/ba-p/3624745), in my case [such fix](https://github.com/microsoft/WSL/issues/9868#issuecomment-1490060424) was required as well  
- Simplyfy your dev experience by [executing the Docker command without sudo](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-22-04#step-2-executing-the-docker-command-without-sudo-optional)
- [install minikube on top of working Docker](https://minikube.sigs.k8s.io/docs/start/)

## Common part for: WSL2 / Ubuntu
- install [sdk man](https://sdkman.io/install) and then
  - **sdk list** or **sdk list java** to see list of available software, and as the next step e.g.
  - **sdk install java** to install java LTS
  - **sdk install scala** to install scala
  - **sdk install sbt** to install sbt
- install [dotnet](https://docs.microsoft.com/en-us/dotnet/core/install/linux-ubuntu)
- install [Node Version Manager](https://github.com/nvm-sh/nvm)
- [Install Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli-apt)
- [Install Terraform](https://www.terraform.io/downloads)
- Install minikube on WSL
- install python 3: **sudo apt install python3 python3-pip ipython3**

Not used currently:
- [Install podman on WSL](https://www.redhat.com/sysadmin/podman-windows-wsl2)
- Install [KWOK](https://kwok.sigs.k8s.io/) to test application without PODs
- [Install skaffold](https://skaffold.dev/docs/install/)
