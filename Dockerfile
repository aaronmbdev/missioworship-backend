FROM openjdk:17-alpine
EXPOSE 8080
COPY target/missio-worship-backend.jar missio-worship-backend.jar 
RUN chmod +x missio-worship-backend.jar
ENTRYPOINT ["java","-jar","/missio-worship-backend.jar"]