FROM openjdk:8u191-jre-alpine3.8
WORKDIR /app
COPY d-mail.war .
CMD echo "172.17.0.3      d-server-main\n172.17.0.5       d-server-admin" >> /etc/hosts && java -jar d-mail.war