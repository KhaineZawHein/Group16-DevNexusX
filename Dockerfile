FROM eclipse-temurin:25

# Copy the self-contained JAR file we just built into the Docker image
COPY target/SEMCode-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/app.jar

WORKDIR /tmp

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
