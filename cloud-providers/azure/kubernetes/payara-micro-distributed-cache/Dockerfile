FROM payara/micro:5.193
COPY target/cluster-demo.war $DEPLOY_DIR/cluster-demo.war
CMD ["--contextRoot", "/", "/opt/payara/deployments/cluster-demo.war"]

