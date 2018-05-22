FROM maven:3-jdk-8

MAINTAINER wangsike<wsk@acegear.com>

COPY pom.xml pom.xml

COPY src src

RUN mvn package && mv ./target/orderpay-0.0.1-SNAPSHOT.jar app.jar && mvn clean

COPY skywalking-agent.zip skywalking-agent.zip
COPY run.sh run.sh

CMD java -jar -Dspring.profiles.active=production app.jar