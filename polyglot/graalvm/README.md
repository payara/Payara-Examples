# Running Polyglot application on Payara Micro with GraalVM

Project contains example of JavaScript library called from Kotlin written JAX-RS resources.


       mvn clean package

Compile project and create WAR package


       java -jar payara-micro-5.193.jar --noCluster target/graalvm.war

Run Payara Micro with the demo application. This requires GraalVM as the JVM.


       http://localhost:8080/graalvm/chroma/darken/D4F880

Example to call the JAX-RS endpoint calling the JavaScript code.
