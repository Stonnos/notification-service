FROM openjdk:8
ADD scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
COPY target/notification-service.war notification-service.war
ENTRYPOINT exec java -jar notification-service.war