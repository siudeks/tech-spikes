# Demo of using one of multiple beans
- **mvn -D"spring-boot.run.arguments=--my-variable=value" spring-boot:run** should display "my default bean" as it is started when env variable is defined
- **mvn spring-boot:run** should display "my alternative bean" as it is started when env variable is defined