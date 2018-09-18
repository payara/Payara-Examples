<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<jsp:useBean id="memberList" scope="request" class="fish.payara.team.info.models.MemberList"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Members With Name: <jsp:getProperty name="memberList" property="name" /></title>
    </head>
    <body>

    <h2>Team Member List</h2>

    <form name="Member List Form" action="select-member" method="POST">
        <p>
        <jsp:getProperty name="memberList" property="listOutput" />
        </p>
        Member ID: <input type="text" name="id"></input><input type="submit" value="Edit Member">
    </form>

    <form action="${pageContext.request.contextPath}/index.jsp">
        <p>
        <input type="submit" value="Home">
        </p>
    </form>

    </body>
</html>