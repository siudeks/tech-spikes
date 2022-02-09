FROM mcr.microsoft.com/java/jre:11-zulu-ubuntu
RUN adduser --system --group spring
WORKDIR /app
COPY target/demo2-0.0.1-SNAPSHOT.jar .

ENTRYPOINT [ "java", "-jar", "/app/demo2-0.0.1-SNAPSHOT.jar" ]
