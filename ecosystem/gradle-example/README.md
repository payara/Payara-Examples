# Demonstration of using Payara Server with Gradle

## Quick Start

Start Payara Server and deploy the project artifact using:

~~~
gradle cargoStartLocal -PpayaraHome=/path/to/payara5
~~~

There must be no running Payara Server instances before using this command.

## Using the Cargo Plugin

The Cargo plugin is preconfigured in the `cargo` element in the `build.gradle`.

Executions:
 - `cargoDeployRemote` - deploys the project artifact to a remote Payara Server instance.
 - `cargoUndeployRemote` - undeploys the project artifact from a remote Payara Server instance.
 - `cargoRedeployRemote` - redeploys the project artifact to a remote Payara Server instance.
 - `cargoDeployRemote` - deploys the project artifact to a remote Payara Server instance.
 - `cargoRunLocal` - starts a local Payara Server instance in the foreground, and waits for a `CTRL + C` to stop.
 - `cargoStartLocal` - starts a local Payara Server instance in the background, and deploys the project artifact. The domain created has the username `admin` and the password `adminadmin`.
 - `cargoRedeployLocal` - Redeploys the project artifact on a local Payara Server instance.
 - `cargoStopLocal` - stops a local Payara Server instance running in the background.

### Configuration
The following properties are configurable in the `gradle.properties`, but can be overriden by passing a property with `-P` to the gradle command. E.g. `gradle cargoStartLocal -PpayaraHome=/path/to/payara5`.

- `payaraHome` - the path to the local `/payara5` folder. Only required to start and stop the local Payara Server instance.
- `payaraHostname` - the hostname of the remote Payara Server instance. Only required for the remote commands.
- `payaraUsername` - the username of the remote Payara Server instance. Only required for the remote commands.
- `payaraPassword` - the password of the remote Payara Server instance. Only required for the remote commands.

## Using the Custom Tasks

Three custom tasks provide an alternative to the Cargo plugin to manage Payara Server. They will run quicker but be less flexible. They also don't allow remote server management.

- `startServer` - starts a local Payara Server instance.
- `stopServer` - stops a local Payara Server instance.
- `redeploy` - redeploys the project artifact to the local Payara Server instance.

### Configuration

The `payaraHome` property is required to use these tasks. It is specified in the `gradle.properties` file, but can be overriden by passing a property with `-P` to the gradle command. E.g. `gradle cargoStartLocal -PpayaraHome=/path/to/payara5`.