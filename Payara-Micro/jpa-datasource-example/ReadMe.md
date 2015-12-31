## Datasource Example
This example shows how to deploy a datasource along with a war file using JPA in JavaEE 7
JavaEE 7 introduce a standard mechanism for defining a datasource either by annotations or in the web.xml.

This example defines a MySQL Datasource and packages up the MySQL Driver in the war.

This example also shows how to add Payara Specific deployment properties to add
extra capabilities to the Datasource for example connection validation, SQL Tracing and slow SQL logging.

This example expects a username test to be created with password test.
