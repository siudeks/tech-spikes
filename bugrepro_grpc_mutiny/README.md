# Mutiny / gRpc code generation issue

Assume we have a simple maven/grpc project (see grpc-client folder) to generate code based on proto contract kept in an external folder (see grpc-contract folder)
1. validate initial solution (tested with java 11)
  - go to grpc-client folder
  - mvn clean install
  - actual: proto code generated, src compilable
1. Uncomment in pom.xml commented sections to generation Mutiny classes (configured as pointed at [Quarkus grpc guide](https://quarkus.io/guides/grpc-getting-started#generating-java-files-from-proto-with-protobuf-maven-plugin))
   - Expected: compilable again
   - Actual: PROTOC FAILED (...) n.MutinyGrpcGenerator: not found

