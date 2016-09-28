<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="view" uri="http://allexb.kiev.ua/jsp/tlds/viewtags" %>
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
<view:acv admin="${loginedAdmin}">
  <view:accept><div class="right"><a class="button-link" href="${pageContext.request.contextPath}/car_list/create">Create Car</a></div></view:accept>
<table cellpadding="0" class="print_table">
  <thead>
  <tr>
    <view:accept>
      <th>ID</th>
      <th>Number plate</th>
    </view:accept>
    <th>Model</th>
    <th>Color</th>
    <th>Year of<br/>manufacture</th>
    <th>Rental Price</th>
    <th>Status</th>
    <th>Description</th>
    <view:accept>
      <th>Edit</th>
      <th>Delete</th>
    </view:accept>
  </tr>
  </thead>
  <tbody>
  <%! Integer counter = 0; %>
  <c:forEach items="${carList}" var="car">
    <% if (counter%2 == 0) {%><tr class="even"><%} else {%><tr><%}%>
    <view:accept>
      <td><a href="${pageContext.request.contextPath}/car_list/display?id=${car.id}">${car.id}</a></td>
      <td>${car.numberPlate}</td>
    </view:accept>
    <td>${car.model}</td>
    <td>${car.color}</td>
    <td>${car.yearOfManufacture}</td>
    <td>${car.rentalPrice} $/day</td>
    <td>${car.status}</td>
    <td>${car.description}</td>
    <view:accept>
      <td><a href="${pageContext.request.contextPath}/car_list/edit?id=${car.id}">Edit</a></td>
      <td><a href="${pageContext.request.contextPath}/car_list/delete?id=${car.id}">Delete</a></td>
    </view:accept>
    </tr>
    <% counter++; %>
  </c:forEach>
  </tbody>
</table>
<view:accept><p/><div class="right"><a class="button-link" href="${pageContext.request.contextPath}/car_list/create">Create Car</a></div><p/></view:accept>
</view:acv>
<jsp:include page="_footer.jsp"/>
</body>
</html>