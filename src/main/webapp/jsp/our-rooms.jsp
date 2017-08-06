<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler" var="i18n"/>
<fmt:setBundle basename="property.page" var="path"/>
<jsp:useBean id="user" scope="session" class="com.pronovich.hotelbooking.entity.User"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="our-rooms.label" bundle="${ i18n }"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div class="container" >
    <div class="row col-md-8 col-md-offset-2">
        <h2 class="text-center"><fmt:message key="our-rooms.label" bundle="${ i18n }"/></h2>
        <table class="table">
            <thead>
                <tr>
                    <th class="text-left">/*PHOTO*/</th>
                    <th class="text-center">Room type</th>
                    <th class="text-center">Room size</th>
                    <th class="text-right">Price</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${roomList}" var="room">
                <tr>
                    <td class="text-left">/*LINK TO PHOTO*/</td>
                    <td class="text-center">${room.roomType}</td>
                    <td class="text-center">${room.size} person(s)</td>
                    <td class="text-right">${room.price} EUR</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="container text-center">
    <div class="row">
    <c:choose>
        <c:when test="${user.role == 'CLIENT'}">
            <a class="btn btn-md btn-info" href="<fmt:message key="path.page.client.add-room-request" bundle="${ path }"/>">
                *ADD ROOM REQUEST*
            </a>
        </c:when>
        <c:otherwise>
            <a class="btn btn-sm btn-info" href="<fmt:message key="path.page.signin" bundle="${ path }"/>">
                SIGN IN
            </a>
            <span>if you want to book a room</span>
        </c:otherwise>
    </c:choose>
    </div>
</div>
</body>
</html>
