# Mutiny / gRpc code generation issue

Assume we have a simple maven/grpc project (see grpc-client folder) to generate code based on proto contract kept in an external folder (see grpc-contract folder)
1) Use mvn clean install to see properly compiled code and to validate initial solution (tested with java 11)
2) Uncomment in pom.xml sections related to generation Mutiny classes (configured as pointed at [Quarkus grpc guide](https://quarkus.io/guides/grpc-getting-started#generating-java-files-from-proto-with-protobuf-maven-plugin))
   1) Expected: compilabel again
   2) Actual: PROTOC FAILED (...) n.MutinyGrpcGenerator: not found

