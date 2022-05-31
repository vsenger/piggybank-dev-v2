ARG UBI=amazoncorretto:17

FROM ${UBI} AS build
COPY . /opt/quarkus-src/
WORKDIR /opt/quarkus-src/
RUN /opt/quarkus-src/mvnw -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
    org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
RUN /opt/quarkus-src/mvnw -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
    -f /opt/quarkus-src/pom.xml \
    package

FROM ${UBI} AS runtime
EXPOSE 13372
COPY --from=build /opt/quarkus-src/target/quarkus-app /opt/quarkus-app/
ENTRYPOINT ["java","-jar","/opt/quarkus-app/quarkus-run.jar"]
