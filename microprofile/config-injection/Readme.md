# Example of using Microprofile Config API

This example shows how to use the Microprofile Config API to inject properties into a servlet (servlet [`ShowConfigValues.java`](src/main/java/fish/payara/examples/microprofile/configinjection/ShowConfigValues.java))

There is also an example of a Customer Converter that creates a POJO from a String ([`CustomConverter.java`](src/main/java/fish/payara/examples/microprofile/configinjection/CustomConverter.java))
There is also an example of a Custom Converter that creates a List of Strings from a comma separated list ([`ConfigListConverter.java`](src/main/java/fish/payara/examples/microprofile/configinjection/ConfigListConverter.java))
Both converters are registered via a Service Loader file in [`META-INF/services`](src/main/resources/META-INF/services/
).

There is also an example of a Custom Config Source that just echos the property value. ([`EchoConfigSource.java`](src/main/java/fish/payara/examples/microprofile/configinjection/EchoConfigSource.java))
The config source is registered via a Service Loader file in [`META-INF/services`](src/main/resources/META-INF/services/
).

Also shown is the injection of standard Payara Server properties (in servlet [`ShowConfigValues.java`](src/main/java/fish/payara/examples/microprofile/configinjection/ShowConfigValues.java), method `printPayaraProperties()`)
