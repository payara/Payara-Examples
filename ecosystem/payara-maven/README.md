# Demonstration of using Payara Server with the Maven Cargo plugin

## Quick start

Start Payara server at the standard admin port 4848 and HTTP listener port 8080 and run:

```
mvn clean install
```

This profile `payara5x-remote` will be enabled by default.

This will do:

1. build the test application WAR
2. deploy it to the running Payara Server (using the standard 4848 admin port)
3. perform an integration test to check it's deployed (the server should listen on the standard 8080 HTTP port)
4. undeploy the application
5. verify the results of the previous tests - build will fail if the application couldn't be deployed

## Using the Cargo plugin

The Cargo plugin (`cargo-maven2-plugin`) is preconfigured in the `pluginManagement` element.

Executions:
 - `start-cargo` - runs goal start to start a local server in background (not possible with remote deployment type)
 - `redeploy-cargo` - runs goal redeploy to deploy the built application (will undeploy if needed)
 - `undeploy-cargo` - runs goal undeploy to undeploy the built application
 - `stop-cargo` - runs goal stop to stop the server running in background (not possible with remote deployment type)

The property `cargo.servlet.port` is configured to the value of `servlet.port` maven property.

In order to run one of the goals during the build lifecycle, it's only necessary to redefine one of the executions. For example, to bind the redeploy goal to the install phase, add the following execution to the `cargo-maven2-plugin` configuration:

```xml
<executions>
  <execution>
    <id>redeploy-cargo</id>
    <phase>install</phase>
  </execution>
...
```

If multiple predefined executions are used, they are executed in the following order: start-cargo -> redeploy-cargo -> undeploy-cargo -> stop-cargo (the order in which they are defined in the `pluginManagement` section).

Additionally, one of the maven profiles must be turned on to configure server-specific cargo plugin configurtion, so that it knows how and where to deploy the application.

## payara5x-remote profile

This profile configures the cargo plugin to work with a remote Payara Server. This profile operates in remote mode, which means that the server needs to be manually started or already running and accessible via the asadmin port.

Only `redeploy-cargo` and `undeploy-cargo` executions are used by this profile, because the server cannot be started or stopped.

Available maven properties:

 - `payara.username` - name of the admin user ("admin" by default)
 - `payara.password` - password of the admin user (empty by default)
 - `payara.adminPort` - admin port (by default the standard port 4848 is used)
 - `payara.hostname` - hostname/IP where the server is running ("localhost" by default)

## payara5x-local profile

This profile configures the cargo plugin to work with a local Payara Server installation. This profile operates on an existing local installation of the server and on an existing domain.

It will start Payara server (using the `start-cargo` execution), deploy an application, undeploy an application and stop the previously started server (using the `stop-cargo` execution).

To run this project with this profile, at least the `payara.home` property needs to be defined and point a local Payara server installation:

```
mvn install -Ppayara6x-local -Dpayara.home=/path/to/payara5
```

Available maven properties:
 
 - `payara.home` (Required) - the path to the local Payara server installation (e.g. "/op/payara5")
 - `payara.username` - name of the admin user ("admin" by default)
 - `payara.password` - password of the admin user (empty by default)
 - `payara.adminPort` - admin port (by default the standard port 4848 is used)
 - `payara.hostname` - hostname/IP where the server is running ("localhost" by default)
 - `payara.domainName` - the domain used when starting the server ("domain1" by default)
 - `payara.domainDir` - the parent directory of the domain directory ("${payara.home}/glassfish/domains" by default)


## More information about the Cargo plugin and Payara server

The docmentation about the GlassFish4/Payara Cargo plugin connector is here: [https://codehaus-cargo.github.io/cargo/Glassfish+4.x.html](https://codehaus-cargo.github.io/cargo/Glassfish+4.x.html)