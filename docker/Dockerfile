FROM openjdk:8u191-jre-alpine3.8
WORKDIR /app
COPY d-mail.war .
CMD echo "172.18.0.6      d-server-main" >> /etc/hosts && echo "172.18.0.3      d-server-admin" >> /etc/hosts && java -jar d-mail.war