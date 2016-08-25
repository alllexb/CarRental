<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <title>Car List</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>
<h3>Car List</h3>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>

<table cellpadding="0" class="print_table">
  <thead>
  <tr>
    <th>Model</th>
    <th>Color</th>
    <th>Year of manufacture</th>
    <th>Rental Price</th>
    <th>Rented</th>
    <th>Description</th>
  </tr>
  </thead>
  <tbody>
  <%! Integer counter = 0; %>
  <c:forEach items="${carList}" var="car">
    <% if (counter%2 == 0) {%><tr class="even"><%} else {%><tr><%}%>
    <td>${car.model}</td>
    <td>${car.color}</td>
    <td>${car.yearOfManufacture}</td>
    <td>${car.rentalPrice}</td>
    <td><input type="checkbox" <c:if test="${car.rented}"> checked="checked" </c:if> onclick="return false;"/></td>
    <td>${car.description}</td>
    </tr>
    <% counter++; %>
  </c:forEach>
  </tbody>
</table>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>