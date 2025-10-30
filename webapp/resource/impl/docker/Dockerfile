FROM eclipse-temurin:22

RUN mkdir -p /opt/app/config

ARG JAR_FILE=target/*-exec.jar

COPY ${JAR_FILE} /opt/app/taco_cloud.jar

WORKDIR /opt/app

ENTRYPOINT ["java", "-jar", "/opt/app/taco_cloud.jar"]



# docker build -f docker/Dockerfile -t taco-manuel/tacocloud:0.0.1-SNAPSHOT .       ===> run from parent folder !!!!
# Or simply use 'mvn spring-boot:build-image' without use Dockerfile

#docker run \
#  -v $(pwd)/config/application-docker.yml:/opt/app/config/application-docker.yml \
#  -e SPRING_CONFIG_LOCATION=file:/opt/app/config/application-docker.yml \
#  -e SPRING_PROFILES_ACTIVE=docker,jpa,h2,ssl \
#  taco-cloud