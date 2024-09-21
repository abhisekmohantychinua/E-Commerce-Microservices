FROM gradle:8-jdk17-alpine as build
WORKDIR /build
COPY . .
RUN gradle clean build -x test


FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /build/build/libs/api-gateway-*.jar .
EXPOSE 8080
CMD java -jar api-gateway-1.0.jar