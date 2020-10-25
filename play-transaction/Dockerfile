# Second stage - build image
FROM openjdk:8-jre-alpine
MAINTAINER H.P.Yang <timyang2016@foxmail.com>


VOLUME /tmp
ADD play-transaction-0.0.1-SNAPSHOT.jar play-transaction.jar

ENV JAVA_OPTS=""

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar play-transaction.jar
ENV SERVER_PORT 8020
EXPOSE ${SERVER_PORT}
