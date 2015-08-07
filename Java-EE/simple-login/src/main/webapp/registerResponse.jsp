<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<jsp:useBean id="userBean" scope="request" class="fish.payara.simple.login.models.UserBean"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Registered</title>
    </head>
    <body>

    <p>
    Registered user: <jsp:getProperty name="userBean" property="username" />

    <br>

    With age: <jsp:getProperty name="userBean" property="age" />
    </p>

    Login <a href="${pageContext.request.contextPath}/index.jsp">here</a>

    </body>
</html>