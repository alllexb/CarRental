<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<c:url value="/css/frames.css"/>'/>
    <title>Login Page</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<div class="headline"><h3>Login Page</h3></div>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<div id="window_container">
    <div id="window_frame">
        <form method="POST" action="${pageContext.request.contextPath}/doLogin">
            <p><strong>LOGIN FORM</strong></p>
            <div class="form_labels">
                <table border="0">
                    <tr>
                        <td>Administrator Login</td>
                        <td><input style="width: 285px" type="text" name="login" value="${admin.login}"/></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input style="width: 285px" type="password" name="password" value="${admin.password}"/></td>
                    </tr>
                    <tr>
                        <td>Remember me</td>
                        <td><input type="checkbox" name="rememberMe" value="Y"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="button_box">
                                <input type="submit" value="Login" class="button-link" id="button"/>
                            </div>
                        </td>
                    </tr>
                </table>
                <div style="color:blue; margin-bottom: 25px">Admin Name: admin-bj, password: bj1212!</div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>