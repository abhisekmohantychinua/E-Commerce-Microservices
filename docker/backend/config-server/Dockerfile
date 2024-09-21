FROM gradle:8-jdk17-alpine as build
WORKDIR /build
COPY . .
RUN gradle clean build -x test


FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /build/build/libs/config-server-*.jar .
CMD java -jar config-server-1.0.jar --spring.profiles.active=native,prod