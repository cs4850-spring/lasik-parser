FROM openjdk:17
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
ADD build/libs/Package.zip /jars/
COPY /jars/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
