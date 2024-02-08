FROM openjdk:21
WORKDIR /opt
EXPOSE 8081
COPY flower-customer/target/flower-customer-1.0.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","app.jar"]