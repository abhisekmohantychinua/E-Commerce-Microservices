# Build Stage
FROM gradle:8-jdk17 AS build
WORKDIR /build
COPY build.gradle .
COPY src ./src
RUN gradle clean build -x test

# Runtime Stage
FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /build/build/libs/api-gateway-*.jar .
EXPOSE 8080
CMD java -jar api-gateway-1.0.jar