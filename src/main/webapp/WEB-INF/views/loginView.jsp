<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>'/>
    <title>Login Page</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<div class="headline"><h3>Login Page</h3></div>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<form method="POST" action="${pageContext.request.contextPath}/doLogin">
    <table border="0">
        <tr>
            <td>Administrator Login</td>
            <td><input type="text" name="login" value="${admin.login}"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" value="${admin.password}"/></td>
        </tr>
        <tr>
            <td>Remember me</td>
            <td><input type="checkbox" name="rememberMe" value="Y"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <br/><input type="submit" value="Submit"/>
            </td>
        </tr>
    </table>
</form>
<p style="color:blue;">Admin Name: admin-bj, password: bj1212!</p>
<jsp:include page="_footer.jsp"/>
</body>
</html>