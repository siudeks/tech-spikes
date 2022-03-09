# Mutiny / gRpc code generation issue

Generation gRpc classesd from scanned jars
- used: java 11, maven
- mvn clean install
- expected: generated classes Person1OuterClass, Person2OuterClass in grpc-client
- actual: generated class Person2OuterClass in grpc-client