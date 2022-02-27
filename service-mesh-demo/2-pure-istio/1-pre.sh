export NAMESPACE=demo0
export ISTIO_DIR=istio-1.12.2
export PATH=$PWD/$ISTIO_DIR/bin:$PATH

# install ISTIO
istioctl install --set profile=demo -y

# load and apply acces to ISTIO addons
kubectl apply -f $ISTIO_DIR/samples/addons
kubectl apply -f apply-addons.yaml

# load application
kubectl apply -f deployment.yaml
