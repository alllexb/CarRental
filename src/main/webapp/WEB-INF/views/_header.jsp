<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="header">
    <h1 class="title">Car Rental Service</h1>
    <div class="admin">
        <c:choose>
            <c:when test="${loginedAdmin != null}"><b>Hello, <a href="${pageContext.request.contextPath}/adminInfo">${loginedAdmin.login}</a></b></c:when>
            <c:otherwise><a href="${pageContext.request.contextPath}/login">Login</a></c:otherwise>
        </c:choose><br/>
    </div>
    <div class="search">
        Search <input name="search">
    </div>
</div>