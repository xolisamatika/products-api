FROM amazoncorretto:17.0.4-alpine3.15
VOLUME /tmp
ADD /target/productsapi-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]