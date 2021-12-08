# Expose 

Sometimes we need to
- get some config from web app in secure way
- put them to env variables
- use the variables to do some activity

In the example we are going to
1) expose JSON class with two variables with protected endpoint
2) use curl to get the JSON
3) use jq to read JSON variables and put them into environment variables

## Demo
```bash
mvn spring-boot:run > /dev/null &
RESULT=$(curl -u abc:def http://localhost:8080/config)
VARIABLE1_VALUE=$(echo $RESULT | jq ' .variable1 ')
VARIABLE2_VALUE=$(echo $RESULT | jq ' .variable2 ')

# print results
echo $VARIABLE1_VALUE
echo $VARIABLE2_VALUE
```
- run application