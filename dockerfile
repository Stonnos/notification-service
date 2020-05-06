FROM openjdk:8
ADD scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
COPY target/notification-service.war notification-service.war
ENTRYPOINT exec /wait-for-it.sh notification-db:5432 --timeout=$DB_CONTAINER_WAIT_TIMEOUT -- java -jar notification-service.war