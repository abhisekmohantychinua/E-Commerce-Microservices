FROM gradle:8-jdk17-alpine as build
WORKDIR /build
COPY . .
RUN gradle clean build -x test


FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /build/build/libs/service-registry-*.jar .
EXPOSE 8761
CMD java -jar service-registry-1.0.jar