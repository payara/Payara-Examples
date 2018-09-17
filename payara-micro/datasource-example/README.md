## Datasource Example
This example shows how to deploy a datasource along with a war file using JavaEE 7
JavaEE 7 introduce a standard mechanism for defining a datasource either by annotations or in the web.xml.

This example defines a MySQL Datasource and packages up the MySQL Driver in the war.

This example also shows how to add Payara Specific deployment properties to add
extra capabilities to the Datasource for example connection validation, SQL Tracing and slow SQL logging.

The example also shows how to package up a custom SQLTraceListener which can be used to
provide your own custom tracing of the SQL sent to the database.

The example expects a MySQL User with username test and password test.
