This sample uses a MySQL database to store created users along with their details in and to validate user logins, while the web application is deployed on the Payara open source edition appserver.

You can download Payara Server here: http://www.payara.co.uk/downloads (Full Java EE)

To run the sample you need to set up the MySQL database, the JDBC connection pool and the JDBC resource.

To set up the MySQL install you can follow the documentation provided at the following address:

https://dev.mysql.com/doc/refman/5.6/en/installing.html

This documentation refers to MySQL v5.6.x as that is the version that was used while producing the sample.

## FOREWARNING: If you wish to change the names of anything that is not a variable you will also have to change files in the source and rebuild the application with Maven, see notes at the bottom of the page ##

##MySQL database setup##

-Start up the MySQL server following the instructions appropriate for your operating system at https://dev.mysql.com/doc/refman/5.6/en/starting-server.html 

-Once in the MySQL command line interface, use the following command to create the empty database: 
	
	CREATE DATABASE userdatabase; 
  

-To set the password used to connect to the database, use the command: 
	
	SET PASSWORD FOR '{yourlocaluser}'@'localhost' = PASSWORD('{password}');
 
Where {yourlocaluser} is the user account used to access the MySQL database, such as bob or admin, and where {password} is the password you wish
to be used to access the database.


##Creating the JDBC connection pool##

-Load up your Payara admin console in a web browser.

-Along the menu on the left navigate to: Resources -> JDBC -> JDBC Connection Pools

-Click the "New" button and enter the following:
	
	Pool Name: simple-loginPool
	Resource Type: javax.sql.ConnectionPoolDataSource
	Database Driver Vendor: MySQL

-Hit the "Next" button. Check the box labelled "Non Transactional Connections".

-We will now fill in the additional properties needed for Payara to access the MySQL database. Hit the "Add Property" button
 and fill the blank fields with the folowwing:
	
	Name: DatabaseName
	Value: userdatabase

-Repeat this step until you have added the following properties:
	
	Name: User
	Value: {yourlocaluser} - This one is your user account used to access the MySQL database, for example if your user account was called admin you would set the property as follows:

	Name: User
	Value: admin



	Name: ServerName
	Value: localhost

	Name: Password
	Value: {password} - This one is the password you set earlier, for example if you set the password as password then you would set the property as follows:

	Name: Password
	Value: password


-Once you have added the following properties you can then hit the "Finish" button.


##Creating the JDBC resource##

-In your Payara admin console use the menu on the left to navigate to: Resources -> JDBC -> JDBC Resources

-Click the "New" button and enter the following:
	
	JNDI Name: jdbc/simple-login
	Pool Name: simple-loginPool

-Hit the "OK" button to create the resource.


##Deploying the application##

-Hit the "Home" button in the Payara admin console.

-From the Common Tasks screen click the "Deploy an Application" button. Select "Choose File" and then navigate to the /target directory of the sample and select simple-login-1.0-SNAPSHOT.war

-Click the "OK" button and then you can see the Simple-Login sample listed in the deployed applications list.

##Testing the Sample##

You can test the sample by clicking the Launch action of the application list.
