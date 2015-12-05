#!/bin/sh

# Sample script to create a MqSQL Datasource on Payara Server.
#
# Usage create-mysql-datasource <password>
#
# Payara Variable Initilistation
# The PAYARA_HOME variable points to your Payara install location. The below
# path would be appropriate for Payara versions 4.1.x
PAYARA_HOME="/path/to/directory/containing/payara/from/root/Payara/appserver/distributions/payara/target/stage/payara41"
#
# Name of the Domain 
#
DOMAIN_NAME=domain1
#
# The ASADMIN variable points to the location of the asadmin script used
# to run the Payara asadmin commands
#
ASADMIN=${PAYARA_HOME}/bin/asadmin
#
# The PORT_BASE variable indicates the number where port assignment started for
# the domain being targeted.
#
PORT_BASE="4800"
#
# The ADMIN_PORT assigned by Payara for the given PORT_BASE. This is used to
# point to the target domain for which you wish to create the datasource.
#
ADMIN_PORT=$(($PORT_BASE + 48))
#
# The location of the MySQL JAR
#
MYSQL_JDBC_JAR="/path/to/mysql/jdbc/jar"
#
MYSQL_USER="root"
#
# MySQL Password is passed into the script
#
MYSQL_PASSWORD=$1
#
# MySQL Database
#
MYSQL_DATABASE=test-database
#
#
cp ${MYSQL_JDBC_JAR} ${PAYARA_HOME}/glassfish/domains/${DOMAIN_NAME}/lib
${ASADMIN} -p ${ADMIN_PORT} restart-domain ${DOMAIN_NAME}
${ASADMIN} -p ${ADMIN_PORT} set-hazelcast-configuration --enabled true --dynamic true
