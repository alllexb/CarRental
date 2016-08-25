<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <title>Client List</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<h3>Client List</h3>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>

<table cellpadding="0" class="print_table">
  <thead>
  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Birthday</th>
    <th>DL Number</th>
    <th>Length of driving experience</th>
  </tr>
  </thead>
  <tbody>
  <%! Integer counter = 0; %>
  <c:forEach items="${clientList}" var="client">
    <% if (counter%2 == 0) { %><tr class="even"><%} else {%><tr><%}%>
    <td>${client.firstName}</td>
    <td>${client.lastName}</td>
    <td>${client.birthday}</td>
    <td>${client.dLNumber}</td>
    <td>${client.lengthOfDrivingExperience}</td>
    </tr>
    <% counter++; %>
  </c:forEach>
  </tbody>
</table>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>