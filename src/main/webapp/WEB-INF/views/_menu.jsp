<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="view" uri="http://allexb.kiev.ua/jsp/tlds/viewtags" %>
<div id="main_menu">
  <ul>
    <view:acv admin="${loginedAdmin}">
    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
      <view:accept>
    <li><a href="${pageContext.request.contextPath}/admin_list">Administrators List</a></li>
    <li><a href="${pageContext.request.contextPath}/client_list">Clients List</a></li>
      </view:accept>
    <li><a href="${pageContext.request.contextPath}/car_list">Cars List</a></li>
      <view:accept>
    <li><a href="${pageContext.request.contextPath}/order_list">Order List</a></li>
      </view:accept>
      <view:deny>
    <li style="float:right"><a href="${pageContext.request.contextPath}/login">Login</a></li>
      </view:deny>
      <view:accept>
    <li style="float:right"><a href="${pageContext.request.contextPath}/logout">LogOut</a></li>
      </view:accept>
    </view:acv>
  </ul>
</div>