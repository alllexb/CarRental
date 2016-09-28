<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/frames.css"/>' />
  <title>Order List</title>
</head>
<body>

<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>

<div class="headline"><h3>Order List</h3></div>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<%--<div class="right"><a class="button-link" href="${pageContext.request.contextPath}/order_list/create" >Create Order</a></div>--%>
<table cellpadding="0" class="print_table">
  <thead>
  <tr>
    <th>ID</th>
    <th>Client</th>
    <th>Cars</th>
    <th>Rental Start</th>
    <th>Rental End</th>
    <th>Closed</th>
    <th>Edit</th>
    <th>Delete</th>
  </tr>
  </thead>
  <tbody>
  <%! Integer counter = 0; %>
  <c:forEach items="${orderList}" var="order">
    <% if (counter%2 == 0) { %><tr class="even"><%} else {%><tr><%}%>
    <td><a href="${pageContext.request.contextPath}/order_list/display?id=${order.id}">${order.id}</a></td>
    <td><a href="${pageContext.request.contextPath}/client_list/display?id=${order.client.id}">${order.client.firstName} ${order.client.lastName}</a></td>
    <td>
      <c:forEach items="${order.cars}" var="car">
        <div><a href="${pageContext.request.contextPath}/car_list/display?id=${car.id}">${car.numberPlate}</a></div>
      </c:forEach>
    </td>
    <td>${order.rentalStart}</td>
    <td>${order.rentalEnd}</td>
    <td><input type="checkbox" <c:if test="${order.closed}"> checked="checked" </c:if> onclick="return false;"/></td>
    <td><a href="${pageContext.request.contextPath}/order_list/edit?id=${order.id}">Edit</a></td>
    <td><a href="${pageContext.request.contextPath}/order_list/delete?id=${order.id}">Delete</a></td>
  </tr>
    <% counter++; %>
  </c:forEach>
  </tbody>
</table>

<%--<p/><div class="right"><a class="button-link" href="${pageContext.request.contextPath}/order_list/create" >Create Order</a></div><p/>--%>
<jsp:include page="_footer.jsp"/>

</body>
</html>