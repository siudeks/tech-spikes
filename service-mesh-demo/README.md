# ISTIO demo

1. Create very simple Spring Boot services **demo1, demo2**.
   1. demo1 invokes internally demo2
   1minikube. demo1 is reachable from the ingress
1. Run local repo to simplify creation of reusable images **docker run -d -p 5000:5000 --restart=always --name registry registry:2**
1. Build demo1
   ```bash
   docker build -t demo1 .
   ```
1. Create naemspace for tests
   ```bash
   kubectl create namespace my-test
   kubectl config set-context --current --namespace my-test
   ```
1. Reuse local docker with locally build images
   ```bash
   # https://stackoverflow.com/a/42564211/1237627
   eval $(minikube docker-env)
   ```

## Useful linke
- [How to bring external traffic to your Kubernetes cluster with Istio Gateway?](https://www.youtube.com/watch?v=ssqDgcEvdZ0)
- [Configure minikube for istio](https://istio.io/latest/docs/setup/platform-setup/minikube/)
- [Install istio on minikube](https://www.youtube.com/watch?v=voAyroDb6xk)
