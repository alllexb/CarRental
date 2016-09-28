<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <title>Administrator List</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<div class="headline"><h3>Administrator List</h3></div>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<div class="right"><a class="button-link" href="${pageContext.request.contextPath}/admin_list/create">Create Administrator</a></div>
<table cellpadding="0" class="print_table">
  <thead>
  <tr>
    <th>ID</th>
    <th>Login</th>
    <th>First Name</th>
    <th>Last Name</th>
    <th>E-mail</th>
    <th>Edit</th>
    <th>Delete</th>
    <%--<th>Password (HASH-code)</th>--%>
  </tr>
  </thead>
  <tbody>
  <%! Integer counter = 0; %>
  <c:forEach items="${administratorList}" var="administrator">
    <% if (counter%2 == 0) {%><tr class = "even"><%} else {%><tr><%}%>
    <td><a href="${pageContext.request.contextPath}/admin_list/display?id=${administrator.id}">${administrator.id}</a></td>
    <td>${administrator.login}</td>
    <td>${administrator.firstName}</td>
    <td>${administrator.lastName}</td>
    <td>${administrator.email}</td>
    <td><a href="${pageContext.request.contextPath}/admin_list/edit?id=${administrator.id}">Edit</a></td>
    <td><a href="${pageContext.request.contextPath}/admin_list/delete?id=${administrator.id}">Delete</a></td>
    <%--<td>${administrator.password}</td>--%>
    </tr>
    <% counter++; %>
  </c:forEach>
  </tbody>
</table>
<jsp:include page="_footer.jsp"/>
</body>
</html>