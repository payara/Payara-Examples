# Using the Payara Micro 5 snapshot build.
FROM payara/micro:5-SNAPSHOT
 
# Downloads the database connector library
RUN wget -O $PAYARA_PATH/database-connector.jar http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.43/mysql-connector-java-5.1.43.jar
 
# Adds an application to be loaded
ADD target/database-ping-1.0-SNAPSHOT.war $PAYARA_PATH/ROOT.war
 
ENTRYPOINT java -jar $PAYARA_PATH/payara-micro.jar --addJars $PAYARA_PATH/database-connector.jar --deploy $PAYARA_PATH/ROOT.war
