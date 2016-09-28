<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/frames.css"/>' />
  <title>Delete Client</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<div id="window_container">
  <div id="window_frame">
    <form action=""${pageContext.request.contextPath}/car_list/delete?id=${client.id}" method="POST" id="subForm">
    <p><strong>DELETE CLIENT ID: #${client.id}</strong></p>
    <div class="form_labels">
      <label for="firstName">First name:</label><br/><input type="text" name="firstName" value="${client.firstName}" id="firstName" disabled/><br/>
      <label for="lastName">Last name:</label><br/><input type="text" name="lastName" value="${client.lastName}" id="lastName" disabled/><br/>
      <label for="birthday">Birthday:</label><br/><input type="text" name="birthday" value="<fmt:formatDate value="${client.birthday}" pattern="yyyy-MM-dd"/>" id="birthday" disabled/><br/>
      <label for="dLNumber">Driver's license number:</label><br/><input type="text" name="dLNumber" value="${client.dLNumber}" id="dLNumber" disabled/><br/>
      <label for="lengthOfDrivingExperience">Length of driving experience:</label><br/><input type="text" name="lengthOfDrivingExperience" value="${client.lengthOfDrivingExperience}" id="lengthOfDrivingExperience" disabled/><br/>
    </div>
    <div class="button_box">
      <input type="submit" value="Delete" id="button" class="button-link"/> <a href="${pageContext.request.contextPath}/client_list" class="button-link">Return to the list</a>
    </div>
    </form>
  </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>