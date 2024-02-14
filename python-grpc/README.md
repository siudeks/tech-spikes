# docs

## Steps
```
python -m pip install virtualenv
virtualenv .venv
. .venv/bin/activate
pip install --upgrade pip
pip install -r requirements.txt
python -m grpc_tools.protoc -I./protos --python_out=. --pyi_out=. --grpc_python_out=. ./protos/helloworld.proto
python main.py
```

## links
- https://grpc.io/docs/languages/python/quickstart/
- https://github.com/grpc/grpc/tree/master/examples/python