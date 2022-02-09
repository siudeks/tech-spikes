FROM mcr.microsoft.com/java/jdk:11-zulu-ubuntu as build
WORKDIR /build
COPY . .
RUN ./mvnw --batch-mode --no-transfer-progress clean package -DskipTests

FROM mcr.microsoft.com/java/jre:11-zulu-ubuntu as app
RUN adduser --system --group spring
WORKDIR /app
COPY --from=build /build/target/demo1-0.0.1-SNAPSHOT.jar .

ENTRYPOINT [ "java", "-jar", "/app/demo1-0.0.1-SNAPSHOT.jar" ]
