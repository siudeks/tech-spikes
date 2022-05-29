# Issue reproduction

Related to [Github #16885](https://github.com/quarkusio/quarkus/issues/16885)

Reproduction steps:
1) *mvn install*
2) Check produced logs  
   Expected:
   ```logs
   Hibernate: 
    insert 
    into
        Client
        (name, id) 
    values
        (1, 'my name')
   ```  
   Actual
   ```
   Hibernate: 
    insert 
    into
        Client
        (name, id) 
    values
        (?, ?)
   ```
