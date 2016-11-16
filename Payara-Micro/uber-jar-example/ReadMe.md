Uber Jar Example
----------------

This example shows how to create an executable Uber Jar using the Payara Micro api and Maven Shade.

Payara Micro can be used to generate an UberJar using the --outputUberJar command line.
However if ou want to use Payara Micro programatically you can use the api to deploy a war file from any InputStream making it easy to build an uber jar building on this api and the Maven shade plugin.

The maven project builds a Jar file containing the executable class UberMain and adds the rest-jcache examples war to the jar. The maven shade plugin then adds the whole of Payara Micro jar to the jar file.

Run the final jar file with java -jar uber-jar-example-1.0-SNAPSHOT.jar from the target directory after the project has built.

Payara Micro will boot and deploy the rest-jcache example at context root /jcache
