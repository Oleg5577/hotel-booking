<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler"/>
<fmt:setBundle basename="property.page" var="path"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Find room by request</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <jsp:include page="/jsp/section/header.jsp"/>
    <table class="room-list">
        <tr>
            <th>Room number</th>
            <th>Room size, persons</th>
            <th>Price</th>
            <th>Room type</th>
        </tr>
    <jsp:useBean id="roomList" scope="request" class="com.pronovich.hotelbooking.entity.Room"/>
    <c:forEach items="${roomList}" var="room">

    </c:forEach>
</body>
</html>
