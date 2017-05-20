## Datasource Example
This example shows how to deploy a datasource along with a war file using JPA in JavaEE 7
JavaEE 7 introduce a standard mechanism for defining a datasource either by annotations or in the web.xml.

This example defines a MySQL Datasource and packages up the MySQL Driver in the war.

This example also shows how to add Payara Specific deployment properties to add
extra capabilities to the Datasource for example connection validation, SQL Tracing and slow SQL logging.

This example also shows environment variable replacement in Payara Micro from 172 onwards.

The datasource definition contains
```xml
     <server-name>${ENV=DB_HOST}</server-name>
     <port-number>3306</port-number>
     <database-name>test</database-name>
     <user>${ENV=JDBC_USER}</user>
     <!-- Example of using a Payara environment variable alias in the datasource definition -->
     <password>${ENV=JDBC_PASSWORD}</password>
```

To deploy the example you need to have the environment variables DB_HOST, JDBC_USER and JDBC_PASSWORD set as these will be replaced in the web.xml on deployment.
