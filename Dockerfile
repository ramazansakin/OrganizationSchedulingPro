FROM openjdk:8-jdk-alpine

MAINTAINER Ramazan Sakin <ramazansakin63@gmail.com>
EXPOSE 8085
ADD target/organization-scheduling-pro.jar organization-scheduling-pro.jar

ENTRYPOINT ["java","-jar","/organization-scheduling-pro.jar"]