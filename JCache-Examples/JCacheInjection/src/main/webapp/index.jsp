
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <h1>An Example of a custom Cache using Injection</h1>
        <p>This cache will store student information using the student ID as key and last name as value</p>
        <form action ="Controllerz" method="POST">
            Enter StudentID:
            <input type ="number" name ="id">
            </br>
            Enter Last Name:
            <input type ="text" name ="lastname">
            </br></br> 
            <input type ="submit" value ="AddStudent"> ${message}
            </br>
        </form></br>
        <form action ="ControllerRemove" method ="POST">
            <input type="submit" value="RemoveAllEntries"> ${message1}
        </form>
        </br>
        <form action ="ControllerRetrieve" method="POST">
            Enter studentID to retrieve student's last name:
            <input type="text" name ="idlastname">
            </br></br>
            <input type="submit" value="Retrieve">
        </form></br>${message2}
    </body>
</html>
