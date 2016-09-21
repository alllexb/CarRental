<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>' />
  <link rel="stylesheet" type="text/css" href='<c:url value="/css/frames.css"/>' />
  <script type="text/javascript">
    window.onload = function () {
      document.getElementById("new_password").onchange = validatePassword;
      document.getElementById("conf_new_password").onchange = validatePassword;
    };
    function validatePassword(){
      var conf_pass=document.getElementById("conf_new_password").value;
      var pass=document.getElementById("new_password").value;
      if(pass!=conf_pass)
        document.getElementById("conf_new_password").setCustomValidity("Passwords Don't Match");
      else {
        document.getElementById("conf_new_password").setCustomValidity('');
      }
    }
  </script>
  <title>Edit Administrator</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<div id="window_container">
  <div id="window_frame">
    <form action="${pageContext.request.contextPath}/admin_list/edit?id=${administrator.id}" method="POST" id="subForm">
      <p><strong>EDIT ADMINISTRATOR ID: #${administrator.id}</strong></p>
      <div class="form_labels">
        <label for="firstName">First name:</label><br/><input type="text" name="firstName" value="${administrator.firstName}" id="firstName"/><br/>
        <label for="lastName">Last name:</label><br/><input type="text" name="lastName" value="${administrator.lastName}" id="lastName"/><br/>
        <label for="email">E-mail:</label><br/><input required type="email" name="email" value="${administrator.email}" id="email"/><br/>
        <label for="login">Login:</label><br/><input required type="text" name="login" value="${administrator.login}" id="login" readonly="readonly"/><br/>
        <label for="password">Editing administrator password:</label><br/><input required type="password" name="password" value="" id="password"/><br/>
        <label for="changePassword">Change password?</label><input type="checkbox" name="changePassword" id="changePassword" onclick="document.getElementById('new_password').disabled=!this.checked; document.getElementById('conf_new_password').disabled=!this.checked;" /><br/><br/>
        <label for="new_password">New password:</label><br/><input required type="password" name="new_password" value="" id="new_password" disabled="disabled"/><br/>
        <label for="conf_new_password">Confirm new password:</label><br/><input required type="password" name="conf_new_password" value="" id="conf_new_password" disabled="disabled"/><br/>
      </div>
      <div class="button_box">
        <input type="submit" value="Edit" id="button" class="button-link"/> <a href="${pageContext.request.contextPath}/admin_list" class="button-link">Return to the list</a>
      </div>
    </form>
  </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>