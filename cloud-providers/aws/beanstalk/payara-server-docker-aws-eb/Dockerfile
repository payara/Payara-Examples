FROM payara/server-full

COPY target/application.war $DEPLOY_DIR

ENV DEPLOY_PROPS=--contextroot=/

EXPOSE 8080
