<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <title>Client List</title>
</head>
<body>

<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>

<div class="headline"><h3>Client List</h3></div>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<div class="right"><a class="button-link" href="${pageContext.request.contextPath}/client_list/create">Create Client</a></div>
<table cellpadding="0" class="print_table">
  <thead>
  <tr>
    <th>ID</th>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Birthday</th>
    <th>DL Number</th>
    <th>Length of driving experience</th>
    <th>Edit</th>
    <th>Delete</th>
  </tr>
  </thead>
  <tbody>
  <%! Integer counter = 0; %>
  <c:forEach items="${clientList}" var="client">
    <% if (counter%2 == 0) { %><tr class="even"><%} else {%><tr><%}%>
    <td><a href="${pageContext.request.contextPath}/client_list/display?id=${client.id}">${client.id}</a></td>
    <td>${client.firstName}</td>
    <td>${client.lastName}</td>
    <td>${client.birthday}</td>
    <td>${client.dLNumber}</td>
    <td>${client.lengthOfDrivingExperience}</td>
    <td><a href="${pageContext.request.contextPath}/client_list/edit?id=${client.id}">Edit</a></td>
    <td><a href="${pageContext.request.contextPath}/client_list/delete?id=${client.id}">Delete</a></td>
    </tr>
    <% counter++; %>
  </c:forEach>
  </tbody>
</table>
<p/><div class="right"><a class="button-link" href="${pageContext.request.contextPath}/client_list/create">Create Client</a></div><p/>
<jsp:include page="_footer.jsp"/>

</body>
</html>