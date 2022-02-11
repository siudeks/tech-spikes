export NAMESPACE=demo0
export ISTIO_DIR=istio-1.12.2
export PATH=$PWD/$ISTIO_DIR/bin:$PATH
kubectl apply -f deployment.yaml

# install ISTIO
# istioctl install --set profile=demo -y
# kubectl label namespace $NAMESPACE istio-injection=enabled
# kubectl apply -f $ISTIO_DIR/samples/addons

# enable ingress for ISTIO tools
kubectl apply -f apply-addons.yaml


# export MYHOST=20.103.20.145
# export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].port}')
# export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
# echo $GATEWAY_URL
