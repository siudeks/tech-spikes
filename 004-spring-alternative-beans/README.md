# Demo of using one of multiple beans

- **mvn -D"spring-boot.run.arguments=--my-variable=value" spring-boot:run** should display "my default bean" as it is started when env variable is defined
- **mvn spring-boot:run** should display "my fallback bean" as it is started when env variable is defined

## Notes:
Order of creating beans forces to produce fallback bean earlier to prove the implementation is working even with unfriendly order of creating beans