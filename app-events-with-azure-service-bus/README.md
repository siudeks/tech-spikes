# Application Events on Spring using Azure Hub

## Scope of spike

Application events are volatile events used to emit real-time small messages about state changes.\
In the spike we use [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream) with binder Azure to spread *application events* amound listeners.

## Notable details

We use spring-cloud-stream version 2.1.x (codename: Fishtown) because spring-cloud-azure latest version (1.1.0.M5) has dependency on spring cloud stream 2.1 and does not work with newest versions of Spring Cloud Stream.
In Spring Stream is introduced also [new programming model based on functions](https://spring.io/blog/2018/08/28/spring-cloud-stream-fishtown-m2-2-1-0-m2-release-announcement) and interface-based approach in the next version is [obsolete]((https://cloud.spring.io/spring-cloud-static/spring-cloud-stream/3.0.0.RELEASE/reference/html/spring-cloud-stream.html#_annotation_based_binding_names_legacy)

## Smoke test

1. Create Azure ServiceBus topic using terraform and provided script *infrastructure.tf*. Copy values provided by the script to application.properties
2. Run one instance of application mvn spring-boot:run
3. On the application console, write 'John' and see how application prints 'Hello John'
4. Run more instances and see how other instances also prints 'Hello John' every time when you type 'John' in one's console
5. Time to clean up:
6. Stop applications
7. Delete created Azure Event Hub if you don't want to reuse it (removing event hub => costs saving)

## Conclusions
Azure Service Bus Topic does not fit our needs. It does not distribute data for all listener, but it allows to add virtual queues (named: subscriptions) per topic what makes the whole idea useless from lightway spreating 

Interesting is Azure examples are based on annotations which is considered as [legacy approach]
)
## Used articles

- <https://docs.microsoft.com/en-us/azure/java/spring-framework/configure-spring-cloud-stream-binder-java-app-azure-event-hub>
- <https://docs.microsoft.com/en-us/azure/event-hubs/event-hubs-quickstart-cli>
- https://www.terraform.io/docs/providers/azurerm/r/servicebus_subscription.html
- https://docs.microsoft.com/en-us/azure/service-bus-messaging/service-bus-quickstart-topics-subscriptions-portal