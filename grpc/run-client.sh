#!/bin/sh

BASEDIR=$(dirname "$0")

java -cp ${BASEDIR}/target/route-guide-classes.jar:${BASEDIR}/target/lib/*:${BASEDIR}/target/test-classes fish.payara.example.grpc.TestGrpc