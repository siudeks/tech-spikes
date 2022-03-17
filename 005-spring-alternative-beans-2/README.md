# Demo of using one of multiple beans

- **mvn spring-boot:run** should display "my default bean"
- **mvn -D"spring-boot.run.arguments=--my-variable=false" spring-boot:run** should display "my fallback bean"

## Notes:
Order of creating beans forces to produce fallback bean earlier to prove the implementation is working even with unfriendly order of creating beans