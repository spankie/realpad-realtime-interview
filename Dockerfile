FROM openjdk:24-slim

# RUN addgroup -S spring && adduser -S spring -G spring

# USER spring:spring

ARG JAR_FILE=target/*.war

COPY ${JAR_FILE} app.war

ENTRYPOINT ["java","-jar","/app.war"]