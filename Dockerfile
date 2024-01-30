FROM maven:3.9.5 AS builder
WORKDIR builder
COPY pom.xml pom.xml
RUN mvn dependency:resolve
COPY src src
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-slim AS runner
ARG APP_PORT
ENV PORT=$APP_PORT
EXPOSE $PORT
WORKDIR backend
COPY --from=builder /builder/target/*.jar app.jar
CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]