<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <title>Home Page</title>
</head>
<body>

<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_menu.jsp"></jsp:include>

<h3>Home Page</h3>

This is Car Rental Service web application for car renting administration. <br><br>
<b>It includes the following functions:</b>
<ul>
  <li>Login</li>
  <li>LogOut</li>
  <li>Storing administrator information in cookies</li>
  <li>Getting cars list</li>
  <li>Getting clients list</li>
  <li>Getting administrator list</li>
  <li>Getting orders list</li>
  <li>Adding new car to the cars list</li>
  <li>Adding new client to the clients list</li>
  <li>Adding new administrator to the administrators list</li>
  <li>Forming order and saving it in application data base</li>
  <li>Closing order after retuning the car with forming bill for the service</li>
  <li>Storing information about all company orders in application database</li>
</ul>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>