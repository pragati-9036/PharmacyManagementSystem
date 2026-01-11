FROM tomcat:9.0-jdk17
WORKDIR /usr/local/tomcat/webapps
COPY deploy/PharmacyManagementSystem.war ROOT.war
CMD ["catalina.sh", "run"]
