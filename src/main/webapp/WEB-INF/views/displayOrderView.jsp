<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel="stylesheet" type="text/css" href='<c:url value="/css/main.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<c:url value="/css/frames.css"/>'/>
    <script>
        window.goBack = function (e) {
            var defaultLocation = "<%=request.getRequestURL().substring(0, request.getRequestURL().length()-request.getRequestURI().length())%><c:url value="/home"/>";
            var oldHash = window.location.hash;
            history.back();
            var newHash = window.location.hash;
            if (newHash === oldHash &&
                    (typeof(document.referrer) !== "string" || document.referrer === "")
            ) {
                window.setTimeout(function () {
                    window.location.href = defaultLocation;
                }, 2000);
            }
            if (e) {
                if (e.preventDefault)
                    e.preventDefault();
                if (e.preventPropagation)
                    e.preventPropagation();
            }
            return false;
        }
    </script>
    <title>Display Order</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<div id="window_container" style="width: 800px;">
    <div id="window_frame">
        <p style="padding-bottom: 0px"><strong>ORDER ID: #${order.id}</strong></p>
        <label for="closed" class="tab"><strong>Is order already closed? </strong></label><input type="checkbox" name="closed" value="true" id="closed" <c:if test="${order.closed}"> checked="checked" </c:if> onclick="return false;"/><br/>
        <h4> Client information: </h4>
        <h4 class="tab"> Name: ${order.client.firstName} ${order.client.lastName}</h4>
        <h4 class="tab"> Birthday: ${order.client.birthday}</h4>
        <h4 class="tab"> Driver's license number: ${order.client.dLNumber}</h4>
        <h4 class="tab"> Length of driving experience: ${order.client.lengthOfDrivingExperience}</h4>
        <h4> Information about rented cars: </h4>
        <table cellpadding="0" class="print_table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Number plate</th>
                <th>Model</th>
                <th>Color</th>
                <th>Year of<br/>manufacture</th>
                <th>Rental Price</th>
                <th>Description</th>
            </tr>
            </thead>
            <tbody>
            <%! Integer counter = 0; %>
            <c:forEach items="${order.cars}" var="car">
                <% if (counter % 2 == 0) {%>
                <tr class="even">
                <%} else {%>
                <tr><%}%>
                    <td><a href="${pageContext.request.contextPath}/car_list/display?id=${car.id}">${car.id}</a></td>
                    <td>${car.numberPlate}</td>
                    <td>${car.model}</td>
                    <td>${car.color}</td>
                    <td>${car.yearOfManufacture}</td>
                    <td>${car.rentalPrice} $/day</td>
                    <td>${car.description}</td>
                </tr>
                <% counter++; %>
            </c:forEach>
            </tbody>
        </table>
        <h4> Date of rent beginning: ${order.rentalStart}</h4>
        <h4> Date of rent ending: ${order.rentalEnd}</h4>
        <h4> Time interval of renting: ${order.timeInterval} days</h4>
        <fmt:setLocale value="en_US"/>
        <h4> Total amount: <fmt:formatNumber type="currency" value="${order.totalAmount}"/></h4>
        <div class="link_box"><a href="" onclick="goBack()" class="button-link">Return</a></div>
    </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>