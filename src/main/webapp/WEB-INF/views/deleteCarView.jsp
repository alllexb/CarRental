<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="ua.kiev.allexb.carrental.model.Car" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/input_form.css"/>' />
  <title>Create Car</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<div id="form_container" style="height: 560px;">
  <div id="form_input" style="height: 550px;">
    <form action="${pageContext.request.contextPath}/car_list/delete?id=${car.id}" method="POST" id="subForm">
      <p><strong>DELETE CAR ID: #${car.id}</strong></p>
      <div class="form_labels">
        <label for="numberPlate">Number Plate</label><br/><input type="text" name="numberPlate" value="${car.numberPlate}" id="numberPlate" disabled/><br/>
        <label for="model">Model:</label><br/><input type="text" name="model" value="${car.model}" id="model" disabled/><br/>
        <label for="color">Color:</label><br/><select required size="1" name="color" id="color" disabled>
        <% Car car = (Car) request.getAttribute("car");
          Car.Color selectedColor = null;
          if (car != null) selectedColor = car.getColor();
          List<Car.Color> items = Arrays.asList(Car.Color.values());
          boolean selected = (selectedColor != null) && items.contains(selectedColor);
          for (int i = 0; i < items.size(); i++) {
            if (!selected && i == 0) {
              out.println("<option selected = \"selected\">" + items.get(i).name() + "</option>");
            } else {
              if (items.get(i).equals(selectedColor)) {
                out.println("<option selected = \"selected\">" + items.get(i).name() + "</option>");
              } else {
                out.println("<option>" + items.get(i).name() + "</option>");
              }
            }
          }%>
      </select><br/>
        <label for="description">Description:</label><br/><input type="text" name="description" value="${car.description}" id="description" disabled/><br/>
        <label for="yearOfManufacture">Year of manufacture:</label><br/><input type="number" min="1900" step="1" name="yearOfManufacture" value="${car.yearOfManufacture}" id="yearOfManufacture" disabled/><br/>
        <label for="rentalPrice">Rental price:</label><br/><input type="number" min="0.00" step="0.01" name="rentalPrice" value="${car.rentalPrice}" id="rentalPrice" disabled/><br/>
        <label for="rented">Is car already rented:</label><input type="checkbox" name="rented" value="true" id="rented" <c:if test="${car.rented}"> checked="checked" </c:if> disabled/><br/>
        <input type="hidden" name="id" value="${car.id}"/>
        <br/><input type="submit" value="Delete" id="button"/> <a href="${pageContext.request.contextPath}/car_list" class="button-link">Return to the list</a>
      </div>
    </form>
  </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>