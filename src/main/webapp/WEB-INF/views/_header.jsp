<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="view" uri="http://allexb.kiev.ua/jsp/tlds/viewtags" %>
<div id="header">
    <h1 class="title">Car Rental Service</h1>
    <div class="admin">
        <view:acv admin="${loginedAdmin}">
            <view:accept><b>Hello, <a href="${pageContext.request.contextPath}/adminInfo">${loginedAdmin.login}</a></b></view:accept>
            <view:deny><a href="${pageContext.request.contextPath}/login">Login</a></view:deny>
        </view:acv><br/>
    </div>
    <div class="search">
        Search <input name="search">
    </div>
</div>