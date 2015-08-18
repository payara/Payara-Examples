#Team-Info Simple Sample Program#

The sample program is a simple CRUD (create, read, update, delete) web app and has the following functions:
* Create new team member
* Edit team member name, email and bio
* Search team member by name
* Informs if there is not team member with a given name
* If there are multiple team members with the same name, displays a list with ID, name and email. Select the correct one by ID
* Delete team member by ID

The sample uses the default Derby datasource included with Payara/Glassfish and accesses it via the Java Persistence API.

You can download Payara [here](http://www.payara.co.uk/downloads).

To deploy the application to the app server you need to build the project using [Maven](https://maven.apache.org/).


