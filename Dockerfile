FROM maven:3.9.5 AS builder
WORKDIR builder
COPY . .
RUN mvn install -Dmaven.test.skip=true

FROM eclipse-temurin:17-jdk-focal AS runner
WORKDIR backend
ENV APP_PORT=8080
EXPOSE $APP_PORT
COPY --from=builder /builder/target/*.jar app.jar
CMD ["java", "-Dserver.port=$APP_PORT", "-jar", "app.jar"]
