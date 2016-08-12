<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <title>Administrators List</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<h3>Administrators List</h3>

<table cellpadding="0" class="print_table">
  <thead>
  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th>E-mail</th>
    <th>Login</th>
    <th>Password (HASH-code)</th>
  </tr>
  </thead>
  <tbody>
  <%! Integer counter = 0; %>
  <c:forEach items="${admins_list}" var="administrator">
    <% if (counter%2 == 0) {out.println("<tr class = \"even\">");
    } else {out.println("<tr>"); }%>
    <td>${administrator.firstName}</td>
    <td>${administrator.lastName}</td>
    <td>${administrator.email}</td>
    <td>${administrator.login}</td>
    <td>${administrator.password}</td>
    </tr>
    <% counter++; %>
  </c:forEach>
  </tbody>
</table>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>