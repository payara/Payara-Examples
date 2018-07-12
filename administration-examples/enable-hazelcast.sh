#!/bin/sh

# Sample script to enable hazelcast on a running domain.

# Payara Variable Initilistation
# The PAYARA_HOME variable points to your Payara install location. The below
# path would be appropriate for Payara versions 4.1.x
PAYARA_HOME="/path/to/directory/containing/payara/from/root/Payara/appserver/distributions/payara/target/stage/payara41"
# The ASADMIN variable points to the location of the asadmin script used
# to run the Payara asadmin commands
ASADMIN=${PAYARA_HOME}/bin/asadmin
# The PORT_BASE variable indicates the number where port assignment started for
# the domain being targeted.
PORT_BASE="4800"
# The ADMIN_PORT assigned by Payara for the given PORT_BASE. This is used to
# point to the target domain for which you wish to enable Hazelcast on.
ADMIN_PORT=$(($PORT_BASE + 48))

# asadmin command to enable hazelcast on the domain currently using the
# ADMIN_PORT value specified within the script. The three options used here are
# -p, --enabled and --dynamic.
#
# The option -p specifies the port which the asadmin command is run on, which
# should be in this case the admin port, declared by the $ADMIN_PORT variable.
#
#
# --enabled: The enabled option can be used to enable or disable hazelcast
# through --enabled true and --enabled false respectively.
#
#
# --dynamic: The dynamic command determines if the Hazelcast member embedded in
# the Payara server will be restarted to apply changes made. To enable Hazelcast
# without requiring the target instance or cluster to restart we use the option
# --dynamic true
#
# You can specify a target cluster using the --target option if you already have
# a created Payara cluster. The option would be added as follows for a cluster
# with a name of "example-cluster":
#
# --target example-cluster
#
# Documentation on enabling Hazelcast (for 4.1.153) can be seen at:
# https://github.com/payara/Payara/wiki/Hazelcast-(Payara-4.1.153)#3-enabling-hazelcast
#
# While documentation on configuring Hazelcast (for 4.1.153) can be found at:
# https://github.com/payara/Payara/wiki/Hazelcast-(Payara-4.1.153)#4-configuring-hazelcast
${ASADMIN} -p ${ADMIN_PORT} set-hazelcast-configuration --enabled true --dynamic true
