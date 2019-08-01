# An example OSGi bundle with a package dependency

An OSGi bundle with a dependency on a Java package exported by a bundle provided by Payara Server.

## Build and deploy

1. Build with maven:

```
mvn package
```

This will produce the OSGi bundle JAR in `target/bundle-example-eclipselink-1.0.jar`

2. Start Payara Server

```
/path/to/payara/bin/asadmin start-domain
```

3. Deploy the OSGi bundle

```
/path/to/payara/bin/asadmin deploy --force --type=osgi target/bundle-example-eclipselink-1.0.jar
```

After a successful deployment, you should see the following messages in the Payara Server log:

```
INFO|My Payara-Example bundle activated
INFO|Created ExpressionBuilder 
Base QUERY OBJECT
INFO|Started Payara-Example [422]
INFO|bundle-example-eclipselink-1.0 was successfully deployed in 36 milliseconds.
```

## What the bundle does

The OSGi bundle runs the `start` method in the `Activator` class, which creates an `ExpressionBuilder` object to prove that the `org.eclipse.persistence.expressions` package was imported successfully.

In `pom.xml`:

* dependency on `org.osgi.core` to implement the bundle activator in `Activator.java`
* dependency on `eclipselink` to compile `Activator.java`
* plugin apache-felix `maven-bundle-plugin` - configures the activator; imports the necessary eclipselink package, which is provided by Payara Server; imports `org.osgi.framework` which provides the OSGi activator API

Produces a simple OSGi bundle `target/bundle-example-eclipselink-1.0.jar`, which contains the following in its `META-INF/MANIFEST.MF`:

```
Import-Package: org.eclipse.persistence.expressions;resolution:=optional
 ,org.osgi.framework;version="[1.5,2)"
```

This is the only required part of the MANIFEST.MF to deploy the bundle to Payara Server and let it create an `ExpressionBuilder` object after deployed.
