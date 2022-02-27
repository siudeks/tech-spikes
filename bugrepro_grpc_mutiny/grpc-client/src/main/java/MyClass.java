import io.quarkus.example.HelloRequest;

// The class should be compilable as referenced proto class is generated
public class MyClass {
    public HelloRequest someProtoMessage = HelloRequest.newBuilder().build();
}
