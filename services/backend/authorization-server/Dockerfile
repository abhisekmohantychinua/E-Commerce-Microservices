FROM gradle:8-jdk17-alpine as build
WORKDIR /build
COPY . .
RUN gradle clean build -x test


FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /build/build/libs/authorization-server-*.jar .
EXPOSE 9000
CMD java -jar authorization-server-1.0.jar