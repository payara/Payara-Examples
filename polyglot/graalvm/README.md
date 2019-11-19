# Running Polyglot application on Payara Micro with GraalVM

This project is an example of mixing JavaScript, Kotlin and Java EE using GraalVM. It demonstrates calling of a JavaScript library from JAX-RS resources, which are written in Kotlin.


Compile project and create WAR package

       mvn clean package


Run Payara Micro with the demo application. This requires GraalVM as the JVM.

       java -jar payara-micro-5.193.jar --noCluster target/graalvm.war


Example URL to call the JAX-RS endpoint calling the JavaScript code.
       
       http://localhost:8080/graalvm/chroma/darken/D4F880
