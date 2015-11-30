DataSource Example
------------------

This shows how to deploy a Datasource with your war file and access it via a servlet on Payara Micro.

This also works on core Payara Server

The servlet code is very simple and just creates a connection and runs select 1 from dual.

You must edit the mysql username and password in the WEB-INF/web.xml to connect to your mysql database.

You can test the servlet on the URL http://127.0.0.1:8080/datasource-example-1.0-SNAPSHOT/SelectServlet

This is the best way to deploy datasources with Payara micro.