<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/frames.css"/>' />
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
    $( function() {
      $( ".formatted_data" ).datepicker({
        minDate: new Date(1920, 1 - 1, 1),
        maxDate: new Date(<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy, M - 1, d"/>),
        dateFormat: "yy-mm-dd"
      });
      $( "#birthday" ).datepicker( "setDate", "<fmt:formatDate value="${client.birthday}" pattern="yyyy-MM-dd"/>" );
    } );
  </script>
  <title>Edit Client</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<div id="window_container">
  <div id="window_frame">
    <form action=""${pageContext.request.contextPath}/car_list/edit?id=${client.id}" method="POST" id="subForm">
      <p><strong>EDIT CLIENT ID: #${client.id}</strong></p>
      <div class="form_labels">
        <label for="firstName">First name:</label><br/><input type="text" name="firstName" value="${client.firstName}" id="firstName"/><br/>
        <label for="lastName">Last name:</label><br/><input type="text" name="lastName" value="${client.lastName}" id="lastName"/><br/>
        <label for="birthday">Birthday:</label><br/><input type="date" name="birthday" id="birthday" class="formatted_data"/><br/>
        <label for="dLNumber">Driver's license number:</label><br/><input required type="number" min="0" max="2147483647" step="1" name="dLNumber" value="${client.dLNumber}" id="dLNumber"/><br/>
        <label for="lengthOfDrivingExperience">Length of driving experience:</label><br/><input type="number" min="0" max="99" step="1" name="lengthOfDrivingExperience" value="${client.lengthOfDrivingExperience}" id="lengthOfDrivingExperience"/><br/>
      </div>
      <div class="button_box">
        <input type="submit" value="Edit" id="button" class="button-link"/> <a href="${pageContext.request.contextPath}/client_list" class="button-link">Return to the list</a>
      </div>
    </form>
  </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>