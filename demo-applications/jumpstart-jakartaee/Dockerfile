FROM payara/server-full:6.2022.1-jdk17

#COPY --chown=1001:0 assembly/conf/domain.xml ${PAYARA_DIR}/glassfish/domains/domain1/config
#COPY --chown=1001:0 assembly/mysql-jdbc-driver/mysql/main/mysql-connector-j-8.0.31.jar ${PAYARA_DIR}/glassfish/domains/domain1/lib
COPY --chown=1001:0 target/*.war ${DEPLOY_DIR}




