<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="view" uri="http://allexb.kiev.ua/jsp/tlds/viewtags" %>
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
  <title>Display Car</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<jsp:include page="_menu.jsp"/>
<c:if test="${errorString != null}"><p class="error">${errorString}</p></c:if>
<view:acv admin="${loginedAdmin}">
<div id="window_container">
  <div id="window_frame">
    <form action="" id="subForm">
      <p><strong>
        CAR<view:accept> ID: #${car.id}</view:accept>
      </strong></p>
      <div class="form_labels">
        <view:accept><label for="numberPlate">Number Plate</label><br/><input type="text" name="numberPlate" value="${car.numberPlate}" id="numberPlate" disabled/><br/></view:accept>
        <label for="model">Model:</label><br/><input type="text" name="model" value="${car.model}" id="model" disabled/><br/>
        <label for="color">Color:</label><br/><input type="text" name="color" value="${car.color}" id="color" disabled><br/>
        <label for="description">Description:</label><br/><input type="text" name="description" value="${car.description}" id="description" disabled/><br/>
        <label for="yearOfManufacture">Year of manufacture:</label><br/><input type="text" name="yearOfManufacture" value="${car.yearOfManufacture}" id="yearOfManufacture" disabled/><br/>
        <label for="rentalPrice">Rental price:</label><br/><input type="text" name="rentalPrice" value="${car.rentalPrice} $/day" id="rentalPrice" disabled/><br/>
        <view:accept><label for="status">Status:</label><br/><input type="text" name="status" value="${car.status}" id="status" disabled><br/></view:accept>
        <view:accept><input type="hidden" name="id" value="${car.id}"/></view:accept>
      </div>
      <div class="link_box"><a href="" onclick="goBack()" class="button-link">Return</a></div>
    </form>
  </div>
</div>
</view:acv>
<jsp:include page="_footer.jsp"/>
</body>
</html>