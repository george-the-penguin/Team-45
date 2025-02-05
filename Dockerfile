FROM maven:3.9.1-amazoncorretto-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM amazoncorretto:17
EXPOSE 8080
COPY --from=build /home/app/target/inventario-*.jar /usr/local/lib/inventario-app.jar
COPY start.sh start.sh
RUN chmod +x start.sh
ENTRYPOINT ["./start.sh"]
