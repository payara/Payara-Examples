# Demonstration of using Payara Server with the Maven Cargo plugin

## Quick start

Just run:

```
mvn clean install
```

The profile `payara-local-managed` will be enabled by default.

This will do:

1. Build the test application WAR
2. Download and install Payara Server
3. Deploy the test application to the running Payara Server
4. Perform an integration test to check it's deployed
5. Undeploy the application
6. Sopt Payara Server
7. Verify the results of the previous tests - build will fail if the application couldn't be deployed

## Using the Cargo plugin

The Cargo plugin (`cargo-maven3-plugin`) is preconfigured in the `pluginManagement` element.

Executions:
 - `start-payara` - runs goal start to start a local server in background (not possible with remote deployment type)
 - `deploy` - runs goal redeploy to deploy the built application (will undeploy if needed)
 - `undeploy` - runs goal undeploy to undeploy the built application
 - `stop-payara` - runs goal stop to stop the server running in background (not possible with remote deployment type)

In order to run one of the goals during the build lifecycle, it's only necessary to redefine one of the executions. For example, to bind the redeploy goal to the install phase, add the following execution to the `cargo-maven3-plugin` configuration:

```xml
<executions>
  <execution>
    <id>redeploy-cargo</id>
    <phase>install</phase>
  </execution>
...
```

Additional maven profiles are used to extend the configuration for the way how the plugin should interact with Payara Server.

## payara-remote profile

This profile configures the cargo plugin to work with a remote Payara Server. This profile operates in remote mode, which means that the server needs to be manually started or already running and accessible via the asadmin port.

Only `deploy` and `undeploy` executions are used by this profile, because the server cannot be started or stopped.

Available maven properties:

 - `payara.username` - name of the admin user ("admin" by default)
 - `payara.password` - password of the admin user (empty by default)
 - `payara.adminPort` - admin port (by default the standard port 4848 is used)
 - `payara.hostname` - hostname/IP where the server is running ("localhost" by default)

To run this project against the "remote-host" hostname, execute the following:

```
mvn install -Ppayara-remote -Dpayara.hostname=remote-host
```

## payara-local profile

This profile configures the cargo plugin to work with a local Payara Server installation. This profile operates on an existing local installation of the server and on an existing domain.

It will start Payara server (using the `start-payara` execution), deploy an application, undeploy an application and stop the previously started server (using the `stop-payara` execution).

To run this project with this profile, at least the `payara.home` property needs to be defined and point to a local Payara server installation:

```
mvn install -Ppayara-local -Dpayara.home=/path/to/payara5
```

Available maven properties:
 
 - `payara.home` (Required) - the path to the local Payara server installation (e.g. "/op/payara5")
 - `payara.username` - name of the admin user ("admin" by default)
 - `payara.password` - password of the admin user (empty by default)
 - `payara.adminPort` - admin port (by default the standard port 4848 is used)
 - `payara.domainName` - the domain used when starting the server ("domain1" by default)
 - `payara.domainDir` - the parent directory of the domain directory ("${payara.home}/glassfish/domains" by default)

## payara-local-managed profile

This profile configures the cargo plugin to automatically install Payara Server, start it with a fresh new domain, deploy the test application, run the tests, undeploy the application and stop the server. Payara Server is completely managed from maven, there's no need to install or start anything outside of maven. This profile is ideal for running automated tests.

To run this project with this profile:

```
mvn install -Ppayara-local-managed
```

## More information about the Cargo plugin and Payara server

* The documentation about the [Payara Cargo plugin connector](https://codehaus-cargo.github.io/cargo/Payara.html)
