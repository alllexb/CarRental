<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="ua.kiev.allexb.carrental.model.Car" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="view" uri="http://allexb.kiev.ua/jsp/tlds/viewtags" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/frames.css"/>' />
  <title>Delete Car</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<div id="window_container">
  <div id="window_frame">
    <form action="${pageContext.request.contextPath}/car_list/delete?id=${car.id}" method="POST" id="subForm">
      <p><strong>DELETE CAR ID: #${car.id}</strong></p>
      <div class="form_labels">
        <label for="numberPlate">Number Plate</label><br/><input type="text" name="numberPlate" value="${car.numberPlate}" id="numberPlate" disabled/><br/>
        <label for="model">Model:</label><br/><input type="text" name="model" value="${car.model}" id="model" disabled/><br/>
        <label for="color">Color:</label><br/><input type="text" name="color" value="${car.color}" id="color" disabled/><br/>
        <label for="description">Description:</label><br/><input type="text" name="description" value="${car.description}" id="description" disabled/><br/>
        <label for="yearOfManufacture">Year of manufacture:</label><br/><input type="text" name="yearOfManufacture" value="${car.yearOfManufacture}" id="yearOfManufacture" disabled/><br/>
        <label for="rentalPrice">Rental price:</label><br/><input type="text" name="rentalPrice" value="${car.rentalPrice} $/day" id="rentalPrice" disabled/><br/>
        <label for="status">Color:</label><br/><input type="text" name="status" value="${car.status}" id="status" disabled/><br/>
        <input type="hidden" name="id" value="${car.id}"/>
      </div>
      <div class="button_box">
        <input type="submit" value="Delete" id="button" class="button-link"/> <a href="${pageContext.request.contextPath}/car_list" class="button-link">Return to the list</a>
      </div>
    </form>
  </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>