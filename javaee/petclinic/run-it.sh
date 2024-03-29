#!/usr/bin/env bash

export POSTGRESQL_HOST=localhost
export POSTGRESQL_PORT=5432
export POSTGRESQL_DATABASE=petclinic_jakartaee_wildfly
export POSTGRESQL_USER=petclinic_jakartaee

export POSTGRESQL_PASSWORD=petclinic_jakartaeepwd

./mvnw -Parq-wildfly-managed
