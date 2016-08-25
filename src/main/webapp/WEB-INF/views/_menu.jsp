<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="admin" scope="session" value = "${loginedAdmin}" />
<div id="main_menu">
  <a href="${pageContext.request.contextPath}/">Home</a>
<c:if test="${admin != null && admin.id != 0}">  |
  <a href="${pageContext.request.contextPath}/admin_list">Administrators List</a>
  |
  <a href="${pageContext.request.contextPath}/client_list">Clients List</a>
</c:if>  |
  <a href="${pageContext.request.contextPath}/car_list">Cars List</a>
<c:if test="${admin != null && admin.id != 0}">  |
  <a href="${pageContext.request.contextPath}/order_list">Order List</a>
</c:if>  |
<c:choose>
<c:when test="${admin == null || admin.id == 0}">
  <a href="${pageContext.request.contextPath}/login">Login</a>
</c:when>
<c:otherwise>
  <a href="${pageContext.request.contextPath}/logout">LogOut</a>
</c:otherwise>
</c:choose>
</div>