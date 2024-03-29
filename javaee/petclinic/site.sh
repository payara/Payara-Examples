#!/usr/bin/env bash

./mvnw -Parq-wildfly-managed -Pwildfly -Ppayara -Pglassfish -Pliberty -Ptomee -Puml -Pwar -Pdocker-glassfish -Pdocker-liberty -Pdocker-wildfly clean dependency:resolve dependency:resolve-plugins dependency:tree dependency:analyze-dep-mgt dependency:analyze-report dependency:sources package site:site site:jar site:deploy liberty:package