FROM openjdk:25-ea-21-jdk-slim

WORKDIR /app
COPY build/libs/*.jar app.jar

ENV JVM_OPTS="-Xms256m -Xmx512m \
              -XX:+UseG1GC \
              -XX:MaxGCPauseMillis=100 \
              -Duser.timezone=UTC"

EXPOSE 8080

ENTRYPOINT sh -c "java $JVM_OPTS -jar app.jar"