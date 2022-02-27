kubectl delete -f deployment.yaml

export NAMESPACE=demo0
export ISTIO_DIR=istio-1.12.2
export PATH=$PWD/$ISTIO_DIR/bin:$PATH

kubectl delete -f $ISTIO_DIR/samples/addons

./istio-1.12.2/bin/istioctl x uninstall --purge -y
