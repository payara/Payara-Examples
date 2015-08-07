<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<jsp:useBean id="userBean" scope="request" class="fish.payara.simple.login.models.UserBean"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
    </head>
    <body>

    <h1>Dashboard</h1>

    <form name="User Form" action="dashboard-update" method="POST">

        <p>
        Welcome, <input type="hidden" name="username" value=<jsp:getProperty name="userBean" property="username" />><jsp:getProperty name="userBean" property="username" /></input>.

        <br>

        Age: <jsp:getProperty name="userBean" property="age" />

        <br>

        Registered for: <jsp:getProperty name="userBean" property="registerAge" />
        </p>


        <p>
        Bio:<br> <textarea name="bio" maxlength="250" rows="5" cols="75"><jsp:getProperty name="userBean" property="bio"/></textarea>
        </p>

        <input type="submit" value="Update Bio">
    </form>

    <form action="${pageContext.request.contextPath}/index.jsp">
        <input type="submit" value="Log Out">
    </form>

    </body>
</html>