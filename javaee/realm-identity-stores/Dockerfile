FROM payara/server-full:6.2022.1

COPY configuration.asadmin $HOME_DIR/configuration.asadmin
COPY newuser.password $HOME_DIR/newuser.password
COPY target/multiple-realms.war $DEPLOY_DIR/multiple-realms.war

ENV POSTBOOT_COMMANDS=$HOME_DIR/configuration.asadmin


