FROM tomcat:10.1-jdk17
WORKDIR /usr/local/tomcat/webapps
COPY target/PharmacyManagementSystem.war ROOT.war
CMD ["catalina.sh","run"]
