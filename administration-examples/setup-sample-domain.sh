#!/bin/sh

# Sample shell script for creating a new domain.

# Payara Variable Initialisation
# DOMAIN_NAME will be the name that you use to reference your domain when
# working with some Payara/Glassfish commands.
DOMAIN_NAME="sample-domain"
# The PAYARA_HOME variable points to your Payara install location. The below
# path would be appropriate for Payara versions 4.1.x
PAYARA_HOME="/path/to/Payara/appserver/distributions/payara/target/stage/payara41"
# The ASADMIN variable points to the location of the asadmin script used
# to run the Payara asadmin commands
ASADMIN=${PAYARA_HOME}/bin/asadmin
# The PORT_BASE variable indicates the number where port assignment starts.
PORT_BASE="4800"
# The ADMIN_PORT that Payara assigns by default or when using --portbase
# (see below). You can access the admin console at http://yourserver:ADMIN_PORT
ADMIN_PORT=$(($PORT_BASE + 48))
# The DEPLOYMENTS_LOCATION variable indicates the location of the application WAR
# file to be deployed. The path needs to point to the Payara_Examples folder
# in this instance.
DEPLOYMENTS_LOCATION="/path/to/Payara-Examples/Administration-Samples"

# Uses the initialised variables to create the new domain using the asadmin
# command create-domain. The command creates a Payara Server domain, a namespace
# that complies with the Java EE standard and can have a configuration that is
# different from other domains present on the server.
#
# The create-domain command has many options, although they cannot all be used
# together. In this example command the options used are --nopassword and
# --portbase.
#
#
#
# --nopassword: The default value for this is false and the administrative
# user will have a password that is specified by AS_ADMIN_PASSWORD in the
# asadmin password file. If AS_ADMIN_PASSWORD is not set the user will be
# prompted to enter the new password.
#
# If set as --nopassword true then the administrative user is created with no
# password. When --user is not used to specify user name and --nopassword true
# 'admin' is the default user name.
#
#
#
# --portbase: The portbase option uses the PORT_BASE variable declared at the
# top of the script to determine where port assignments start at. Using the
# portbase option does not let you use the --adminport, --instanceport or
# --domainproperties options. A few of the ports derived from the portbase
# are as listed below:
#
#   Administration port: portbase + 48
#
#   HTTP listener port: portbase + 80
#
#   HTTPS listener port: portbase + 81
#
# More ports are listed in the documentation for the asadmin create-domain
# command documentation for Oracle GlassFish, listed under the --portbase option.
#
#
#
# The use of the DOMAIN_NAME variable at the end of the asadmin command is used
# to determine the directory with which the domain's data is stored in within
# your Payara install. The name must contain only ASCII characters and be valid
# for a directory name on your operating system.
#
# More information regarding the use and option of the asadmin create-domain
# command can be found at: https://docs.oracle.com/html/E24938_01/create-domain.htm
${ASADMIN} create-domain --nopassword --portbase ${PORT_BASE} ${DOMAIN_NAME}

# Start the newly created sampleDomain domain using the asadmin start-domain
# command.
${ASADMIN} start-domain ${DOMAIN_NAME}

# Runs the asadmin command to create a new cluster with the name "sample-cluster"
# on the newly created domain.
#
#
# The option -p points the asadmin command to run on the port specified by the
# ADMIN_PORT variable. In this case the admin port of the domain we wish to create
# the cluster on is stored as the ADMIN_PORT variable.
#
#
# The create-cluster command will create an empty Payara server cluster, with
# the name following the command being given as the cluster name.
#
# Options and their usage is detailed by the Oracle GlassFish documentation at:
# http://docs.oracle.com/cd/E26576_01/doc.312/e24938/create-cluster.htm#GSRFM00017
${ASADMIN} -p ${ADMIN_PORT} create-cluster sample-cluster

# Runs asadmin commands to create local instances on the sample-cluster to form
# a two node cluster on the new sample-domain.
#
# The option -p points the asadmin command to run on the port specified by the
# ADMIN_PORT variable. The ADMIN_PORT points to the domain which the cluster just
# was created on, so that is used again.
#
# The create-local-instance asadamin subcommand creates a new Payara server
# instance on the host which the subcommand was run on. Two create-local-instance
# options are used here: --cluster and --portbase.
#
#
#
# --cluster: The --cluster option tells Payara which cluster the created instance
# should inherit its configuration from, creating a clustered instance. In this
# case the instances are clustered to the just created sample-cluster.
#
#
#
# --portbase: The --portbase option tells Payara where the port assignment should
# begin for each instance. The --portbase option for the create-local-instance
# subcommand works the same as is does for the create-domain subcommand because
# these instances are being created on the same domain as was created earlier.
#
# Following the options each instance is given a name, sample-instance-1 and
# sample-instance-2 respectively.
#
# More options and their usage is detailed by the Oracle GlassFish documentation at:
# http://docs.oracle.com/cd/E26576_01/doc.312/e24938/create-local-instance.htm#GSRFM00044
${ASADMIN} -p ${ADMIN_PORT} create-local-instance --cluster sample-cluster --node localhost-sample-domain --portbase ${PORT_BASE} sample-instance-1
${ASADMIN} -p ${ADMIN_PORT} create-local-instance --cluster sample-cluster --node localhost-sample-domain --portbase ${PORT_BASE} sample-instance-2

# asadmin subcommand to start the cluster
#
# The option -p specifies the port to run the start-cluster command on, in this
# case it is run on the ADMIN_PORT for the created sample domain.
#
# The name of the cluster to start is provided following the start-cluster command.
${ASADMIN} -p ${ADMIN_PORT} start-cluster sample-cluster

# asadmin subcommand to enable hazelcast on the created cluster. The four options
# used here are -p, --enabled, --dynamic and --target.
#
# The option -p specifies the port which the asadmin subcommand is run on, which
# should be in this case the admin port the cluster runs on, given by the
# ADMIN_PORT variable.
#
#
# --enabled: The enabled option can be used to enable or disable hazelcast
# through --enabled true and --enabled false respectively.
#
#
# --dynamic: The dynamic command determines if the Hazelcast member embedded in
# the Payara server will be restarted to apply changes made. To enable Hazelcast
# without requiring the cluster to restart the option --dynamic true is added.
#
#
# --target: To enable Hazelcast on the sample-cluster the --target option is used
# to point the asadmin subcommand to the cluster that we want to enable Hazelcast
# on.
#
# Documentation on enabling Hazelcast (for 4.1.153) can be seen at:
# https://github.com/payara/Payara/wiki/Hazelcast-(Payara-4.1.153)#3-enabling-hazelcast
#
# While documentation on configuring Hazelcast (for 4.1.153) can be found at:
# https://github.com/payara/Payara/wiki/Hazelcast-(Payara-4.1.153)#4-configuring-hazelcast
${ASADMIN} -p ${ADMIN_PORT} set-hazelcast-configuration --enabled true --dynamic true --target sample-cluster

# asadmin subcommands to enable web container availability and change the persistence type to hazelcast
#
# The asadmin subcommands are pointed towards the running cluster by the -p option.
#
# The set subcommand is used to change the value of the web container availability
# enabled value to true.
#
# Another set subcommand is then called so as to set the web container persistence
# type to Hazelcast.
${ASADMIN} -p ${ADMIN_PORT} set sample-cluster-config.availability-service.web-container-availability.availability-enabled=true
${ASADMIN} -p ${ADMIN_PORT} set sample-cluster-config.availability-service.web-container-availability.persistence-type=hazelcast


# asadmin subcommand to deploy an app to the created cluster
#
# The option -p specifies the admin port used by the cluster.
#
# The --target option here points the asadmin subcommand to the cluster which we
# want to deploy the app on.
#
# Following the options the location of the WAR file to deploy is given.
# Here the location points to the directory given by the DEPLOYMENTS_LOCATION
# variable and the file team-info.war inside it.
${ASADMIN} -p ${ADMIN_PORT} deploy --target sample-cluster ${DEPLOYMENTS_LOCATION}/team-info.war
