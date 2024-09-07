# Build Stage
FROM gradle:8-jdk17 AS build
WORKDIR /build
COPY build.gradle .
COPY src ./src
RUN gradle clean build -x test

# Runtime Stage
FROM amazoncorretto:17
WORKDIR /app
RUN ls ./
COPY --from=build /build/build/libs/authorization-server-*.jar .
EXPOSE 9000
CMD java -jar authorization-server-1.0.jar