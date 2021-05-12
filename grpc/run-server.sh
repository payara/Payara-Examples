#!/bin/sh

BASEDIR=$(dirname "$0")

java -cp ${BASEDIR}/target/grpc-sample-1.0-SNAPSHOT-classes.jar:${BASEDIR}/target/lib/* uk.me.mattgill.grpc.server.custom.Main