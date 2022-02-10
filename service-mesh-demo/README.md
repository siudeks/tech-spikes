# ISTIO demo

1. Compile two uservices **mvn clean install**
1. Test localy if they work
   ```bash
   docker-compose up --build
   [Test availability of entry uservice](http://localhost:8081)
   [Test connection between uservices](http://localhost:8081/status)
   ```
1. FYI Images already build and pushed to docker registry
   ```
   docker build -t siudeks/demo:demo1 demo1
   docker push siudeks/demo:demo1
   docker build -t siudeks/demo:demo2 demo2
   docker push siudeks/demo:demo2
   ```
2. Deploy to AKS
   1. 001-  
## Useful linke
- [How to bring external traffic to your Kubernetes cluster with Istio Gateway?](https://www.youtube.com/watch?v=ssqDgcEvdZ0)
- [Configure minikube for istio](https://istio.io/latest/docs/setup/platform-setup/minikube/)
- [Install istio on minikube](https://www.youtube.com/watch?v=voAyroDb6xk)
