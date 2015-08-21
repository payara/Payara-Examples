#!/bin/sh

# Sample shell script for creating a new domain.

# Payara Variable Initialisation
# DOMAIN_NAME will be the name that you use to reference your domain when
# working with some Payara/Glassfish commands.
DOMAIN_NAME="sampleDomain"
# The PAYARA_HOME variable points to your Payara install location. The below
# path would be appropriate for Payara versions 4.1.x
PAYARA_HOME="/path/to/directory/containing/payara/from/root/Payara/appserver/distributions/payara/target/stage/payara41"
# The ASADMIN variable points to the location of the asadmin script used
# to run the Payara asadmin commands
ASADMIN=${PAYARA_HOME}/bin/asadmin
# The PORT_BASE variable indicates the number where port assignment starts.
PORT_BASE="4800"
# The ADMIN_PORT that Payara assigns by default or when using --portbase
# (see below). You can access the admin console at http://yourserver:ADMIN_PORT
ADMIN_PORT=$(($PORT_BASE + 48))

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
