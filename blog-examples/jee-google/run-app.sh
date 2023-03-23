#!/bin/bash

package-app() {
  mvn clean package -DskipTests
}

copy-app() {
  /usr/bin/cp -f target/*war deployments
}

up() {
  docker-compose up -d
}

stop() {
  mvn clean
  docker-compose stop
}

export PARAM="${1}"

MVN_VERSION=$(mvn -q \
  -Dexec.executable=echo \
  -Dexec.args='${project.artifactId}:${project.version}' \
  --non-recursive \
  exec:exec)
if [ "$PARAM" = "up" ]; then
  up
fi

if [ "$PARAM" = "deploy" ]; then
  echo "Packing ${MVN_VERSION}"
  package-app
  echo "Deploying application artefact..."
  for artefact in target/*.war; do
    echo "$artefact"
  done
  copy-app
fi



if [ "$PARAM" = "stop" ]; then
  stop
fi
