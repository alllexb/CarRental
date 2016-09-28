<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/frames.css"/>' />
  <script>
    window.goBack = function (e){
      var defaultLocation = "<%=request.getRequestURL().substring(0, request.getRequestURL().length()-request.getRequestURI().length())%><c:url value="/home"/>";
      var oldHash = window.location.hash;
      history.back();
      var newHash = window.location.hash;
      if(newHash === oldHash &&
              (typeof(document.referrer) !== "string" || document.referrer  === "")
      ){
        window.setTimeout(function(){
          window.location.href = defaultLocation;
        },2000);
      }
      if(e){
        if(e.preventDefault)
          e.preventDefault();
        if(e.preventPropagation)
          e.preventPropagation();
      }
      return false;
    }
  </script>
  <title>Display Administrator</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<div id="window_container">
  <div id="window_frame">
    <form action="" id="subForm">
      <p><strong>ADMINISTRATOR ID: #${administrator.id}</strong></p>
      <div class="form_labels">
        <label for="firstName">First name:</label><br/><input type="text" name="firstName" value="${administrator.firstName}" id="firstName" disabled/><br/>
        <label for="lastName">Last name:</label><br/><input type="text" name="lastName" value="${administrator.lastName}" id="lastName" disabled/><br/>
        <label for="email">E-mail:</label><br/><input required type="email" name="email" value="${administrator.email}" id="email" disabled/><br/>
        <label for="login">Login:</label><br/><input required type="text" name="login" value="${administrator.login}" id="login" disabled/><br/>
      </div>
      <div class="link_box"><a href="" onclick="goBack()" class="button-link">Return</a></div>
    </form>
  </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>