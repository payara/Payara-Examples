# Example of using Microprofile Config API

This example shows how to use the Microprofile Config API to inject properties into a servlet (servlet `ShowConfigValues.java`)

There is also an example of a Customer Converter that creates a POJO from a String (`CustomConverter.java`)
There is also an example of a Custom Converter that creates a List of Strings from a comma separated list (`ConfigListConverter.java`)
Both converters are registered via a Service Loader file in `META-INF/services`.

There is also an example of a Custom Config Source that just echos the property value. (`EchoConfigSource.java`)
The config source is registered via a Service Loader file in `META-INF/services`.

Also shown is the injection of standard Payara Server properties (in servlet `ShowConfigValues.java`, method `printPayaraProperties()`)
