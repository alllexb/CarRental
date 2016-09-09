<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <title>Error Page</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<div id="error_box">
  <div class="cell">
    <div class="content_img">
      <img src="<c:url value="/images/crash_img.jpg"/>"/>
    </div>
  </div>
  <div class="cell">
    <div class="content">
      <h1> ${error_code} Error </h1>
      <h2> ${error_message}</h2>

      <h3> ${error_comment}</h3>

      <p><a class="button-link" href="<c:url value="/"/>">Return to the homepage</a></p>
    </div>
  </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>