# Этап сборки для общего кода (common)
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /build
COPY . /build
RUN mvn clean install -f pom.xml

# Этап для сборки Employee
FROM maven:3.8.4-openjdk-17-slim AS employee-build
WORKDIR /app
COPY --from=build /build/employee/target/employee-0.0.1-SNAPSHOT.jar /app/employee/target/
COPY --from=build /build/common/target/common-0.0.1-SNAPSHOT.jar /app/employee/target/
ENTRYPOINT ["java", "-jar", "/app/employee/target/employee-0.0.1-SNAPSHOT.jar"]

# Этап для сборки User
FROM maven:3.8.4-openjdk-17-slim AS user-build
WORKDIR /app
COPY --from=build /build/user/target/user-0.0.1-SNAPSHOT.jar /app/user/target/
COPY --from=build /build/common/target/common-0.0.1-SNAPSHOT.jar /app/user/target/
ENTRYPOINT ["java", "-jar", "/app/user/target/user-0.0.1-SNAPSHOT.jar"]

# Этап для сборки Gateway
FROM maven:3.8.4-openjdk-17-slim AS gateway-build
WORKDIR /app
COPY --from=build /build/gateway/target/gateway-0.0.1-SNAPSHOT.jar /app/gateway/target/
ENTRYPOINT ["java", "-jar", "/app/gateway/target/gateway-0.0.1-SNAPSHOT.jar"]

FROM maven:3.8.4-openjdk-17-slim AS core-build
WORKDIR /app
COPY --from=build /build/core/target/core-0.0.1-SNAPSHOT.jar /app/core/target/
COPY --from=build /build/common/target/common-0.0.1-SNAPSHOT.jar /app/core/target/
ENTRYPOINT ["java", "-jar", "/app/core/target/core-0.0.1-SNAPSHOT.jar"]