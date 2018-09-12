FROM tomcat:8-jre8
ADD scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT [ "/wait-for-it.sh", "notification-db:5432", "--" ]
COPY target/notification-service.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]