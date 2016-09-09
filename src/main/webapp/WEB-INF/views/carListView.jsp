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
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<div class="headline"><h3>Car List</h3></div>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<c:if test="${admin != null && admin.id != 0}"><div class="right"><a class="button-link" href="${pageContext.request.contextPath}/car_list/create" >Create Car</a></div></c:if>
<table cellpadding="0" class="print_table">
  <thead>
  <tr>
    <c:if test="${admin != null && admin.id != 0}">
      <th>ID</th>
      <th>Number plate</th>
    </c:if>
    <th>Model</th>
    <th>Color</th>
    <th>Year of<br/>manufacture</th>
    <th>Rental Price</th>
    <th>Rented</th>
    <th>Description</th>
    <c:if test="${admin != null && admin.id != 0}">
      <th>Edit</th>
      <th>Delete</th>
    </c:if>
  </tr>
  </thead>
  <tbody>
  <%! Integer counter = 0; %>
  <c:forEach items="${carList}" var="car">
    <% if (counter%2 == 0) {%><tr class="even"><%} else {%><tr><%}%>
    <c:if test="${admin != null && admin.id != 0}">
      <td>${car.id}</td>
      <td>${car.numberPlate}</td>
    </c:if>
    <td>${car.model}</td>
    <td>${car.color}</td>
    <td>${car.yearOfManufacture}</td>
    <td>${car.rentalPrice}</td>
    <td><input type="checkbox" <c:if test="${car.rented}"> checked="checked" </c:if> onclick="return false;"/></td>
    <td>${car.description}</td>
    <c:if test="${admin != null && admin.id != 0}">
      <td><a href="${pageContext.request.contextPath}/car_list/edit?id=${car.id}">Edit</a></td>
      <td><a href="${pageContext.request.contextPath}/car_list/delete?id=${car.id}">Delete</a></td>
    </c:if>
    </tr>
    <% counter++; %>
  </c:forEach>
  </tbody>
</table>
<c:if test="${admin != null && admin.id != 0}"><p><div class="right"><a class="button-link" href="${pageContext.request.contextPath}/car_list/create" >Create Car</a></div></p></c:if>
<jsp:include page="_footer.jsp"/>
</body>
</html>