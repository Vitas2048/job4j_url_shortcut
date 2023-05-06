FROM maven:3.6.3-openjdk-17 as maven

WORKDIR /jshortcut

COPY . /jshortcut
RUN mvn install

FROM openjdk:17.0.2-jdk

WORKDIR /jshoetcut

COPY --from=maven /jshortcut/target/job4j_url_shortcut-1.0-SNAPSHOT.jar jshortcut.jar

CMD ["java", "-jar", "jshortcut.jar"]