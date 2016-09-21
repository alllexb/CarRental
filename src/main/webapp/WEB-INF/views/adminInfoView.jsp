<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/frames.css"/>' />
  <title>Administrator Information Page</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<div class="headline"><h3>Administrator Information Page</h3></div>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<c:set var="admin" scope="page" value="${loginedAdmin}"/>
<div id="window_container" style="width: 380px;">
  <div id="window_frame" >
   <h4> Hello: ${admin.firstName} ${admin.lastName}</h4>
   <h4> Login: ${admin.login}</h4>
   <h4> E-mail: ${admin.email}</h4>
   <div class="link_box"> <a class="button-link" href="${pageContext.request.contextPath}/logout">LogOut</a></div>
  </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>