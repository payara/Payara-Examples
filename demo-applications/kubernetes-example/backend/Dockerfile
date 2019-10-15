FROM openjdk:8-jdk-alpine
ADD target/backend-1.0-SNAPSHOT-microbundle.jar /backend.jar
CMD ["java", "-jar", "backend.jar", "--nocluster"]
