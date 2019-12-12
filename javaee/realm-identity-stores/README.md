# Realm Identity Stores Example
An example application to demonstrate using Payara Server realms as Java EE Security Identity Stores. This application uses 2 realms "file" and "file2" as identity stores. As specified by Java EE Security, users are authenticated if they are authenticated by at least one of the realms.

This application uses 2 file realms as an example. One of them is enabled using the general `@RealmIdentityStoreDefinition` annotation, another one is enabled using the specific `@FileIdentityStoreDefinition` annotation. The difference is that the general realm can only enable realms already defined in the server while the latter annotation will create a file realm if it doesn't exist in the server. It's possible to use any combination of identity store definition annotations in Payara API to enable any combination of Payara Server realms, e.g. a File realm with another File realm, with an LDAP realm or any other realm.

## Preconditions

### Sample Realms

The application requires a file realm "file" and another realm "anotherrealm", which can be of any type. We can create it as another file realm.

#### "file" File Realm

This realm exists in Payara Server by default. We need to add the following users to it:

Username | Password | Roles
--- | --- | ---
user1 | password | ROLE_USER

You can run the following asadmin command to add the user:

```
create-file-user --groups=ROLE_USER --authrealmname=file user1
```

#### "anotherrealm" File Realm

We'll create another File realm called "anotherrealm" to demonstrate using 2 realms in a single application.

Username | Password | Roles
--- | --- | ---
user2 | password | ROLE_USER

You can run the following asadmin command to set up the realm and the user:

```
create-auth-realm --classname=com.sun.enterprise.security.auth.realm.file.FileRealm --property=file=$\{com.sun.aas.instanceRoot\}\/config\/keyfile.file2:jaas-context=fileRealm: anotherrealm
create-file-user --groups=ROLE_USER --authrealmname=anotherrealm user2
```
## Build

Build with

```
mvn package
```

## Run the application

Deploy the application file `target/multiple-realms.war` to Payara Server or run with Payara Micro.

Once the application is running, you can access an public resource at the following URL:

http://localhost:8080/multiple-realms/

A secured resource is available at:

http://localhost:8080/multiple-realms/secured.jsp

When you access it, the browser will ask for a user and password. Because we added both "file" and "anotherrealm" realms to our application, users in any of these two realms with the role ROLE_USER will be allowed to access the secured resource. Try logging in with user "user1" and password "password, defined in the "file" realm, and with user "user2", password "password", defined in the "anotherrealm" realm.

### Run in Docker

In order to run on Payara Server in Docker, follow these steps.
Make sure you use Java 8 to build because the Docker image uses Java 8.

```
mvn package
docker build -t payara-multrealms .
```

Test scenario:

* run the docker container:

```
docker run -it -p 14848:4848/tcp -p 18080:8080/tcp payara-multrealms:latest
```

* verify the app is deployed: access http://localhost:18080/multiple-realms/

* access the secured resource: http://localhost:18080/multiple-realms/secured.jsp

The asadmin command executed to configure Payara Server and deploy the application can be found in file `configuration.asadmin`

## Description of the application

The realm identity stores are enabled in `AuthenticationConfig.java`:

```
@BasicAuthenticationMechanismDefinition
@FileIdentityStoreDefinition("file")
@RealmIdentityStoreDefinition("anotherrealm")
@ApplicationScoped
public class AuthenticationConfig {

}
```

This also enables the Basic HTTP authentication mechanism which will instruct the browser to prompt for the user and password before serving the secured resources.

Resources can be secured in the standard way as any Servlet resource, either in `web.xml` or using standard security annotations. In this example, the secured.jsp resource is secured using the `security-constraint` in `web.xml`:

```
    <security-constraint>
        <web-resource-collection>
            <web-resource-name>Protected resources</web-resource-name>
            <url-pattern>/secured.jsp</url-pattern>
        </web-resource-collection>
        <auth-constraint>
            <role-name>ROLE_USER</role-name>
        </auth-constraint>
    </security-constraint>
```
