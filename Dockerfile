FROM gradle:7.5-jdk17 as builder
WORKDIR /build

COPY build.gradle.kts settings.gradle.kts /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build
RUN gradle build -x test --parallel

FROM openjdk:17-oracle
WORKDIR /app
RUN microdnf install findutils
COPY --from=builder /build/build/libs/trigger-0.0.1-SNAPSHOT.war .

ENTRYPOINT ["java", "-jar", "trigger-0.0.1-SNAPSHOT.war"]