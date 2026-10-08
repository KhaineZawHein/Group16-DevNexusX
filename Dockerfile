FROM amazoncorretto:26

# Copy the self-contained JAR file we just built into the Docker image
COPY target/SEMCode-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/Main.jar

WORKDIR /tmp

# Run the JAR file
ENTRYPOINT ["java", "-jar", "Main.jar"]