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
        <table class="table vertical-align-table">
            <thead>
                <tr>
                    <th class="text-left"></th>
                    <th class="text-center"><fmt:message key="our-rooms.label.room-type" bundle="${ i18n }"/></th>
                    <th class="text-center"><fmt:message key="our-rooms.label.room-size" bundle="${ i18n }"/></th>
                    <th class="text-center"><fmt:message key="our-rooms.label.room-price" bundle="${ i18n }"/></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="img-thumbnail"><img src="../img/room/1.1%20standard.jpg" alt="" width="200" height="150"></td>
                    <td class="text-center">${roomList[0].roomType}</td>
                    <td class="text-center">${roomList[0].size} person(s)</td>
                    <td class="text-right">${roomList[0].price} EUR</td>
                </tr>
                <tr>
                    <td class="img-thumbnail"><img src="../img/room/1.2%20standard.jpg" alt="" width="200" height="150"></td>
                    <td class="text-center">${roomList[1].roomType}</td>
                    <td class="text-center">${roomList[1].size} person(s)</td>
                    <td class="text-right">${roomList[1].price} EUR</td>
                </tr>
                <tr>
                    <td class="img-thumbnail"><img src="../img/room/2.1%20semilux.jpg" alt="" width="200" height="150"></td>
                    <td class="text-center">${roomList[2].roomType}</td>
                    <td class="text-center">${roomList[2].size} person(s)</td>
                    <td class="text-right">${roomList[2].price} EUR</td>
                </tr>
                <tr>
                    <td class="img-thumbnail"><img src="../img/room/2.2%20semilux.jpg" alt="" width="200" height="150"></td>
                    <td class="text-center">${roomList[3].roomType}</td>
                    <td class="text-center">${roomList[3].size} person(s)</td>
                    <td class="text-right">${roomList[3].price} EUR</td>
                </tr>
                <tr>
                    <td class="img-thumbnail"><img src="../img/room/3.1%20lux.jpg" alt="" width="200" height="150"></td>
                    <td class="text-center">${roomList[4].roomType}</td>
                    <td class="text-center">${roomList[4].size} person(s)</td>
                    <td class="text-right">${roomList[4].price} EUR</td>
                </tr>
                <tr>
                    <td class="img-thumbnail"><img src="../img/room/3.2%20lux.jpg" alt="" width="200" height="150"></td>
                    <td class="text-center">${roomList[5].roomType}</td>
                    <td class="text-center">${roomList[5].size} person(s)</td>
                    <td class="text-right">${roomList[5].price} EUR</td>
                </tr>
                <tr>
                    <td class="img-thumbnail"><img src="../img/room/4.2%20president.jpg" alt="" width="200" height="150"></td>
                    <td class="text-center">${roomList[6].roomType}</td>
                    <td class="text-center">${roomList[6].size} person(s)</td>
                    <td class="text-right">${roomList[6].price} EUR</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="container text-center">
    <div class="row">
    <c:choose>
        <c:when test="${user.role == 'CLIENT'}">
            <a class="btn btn-md btn-info btn-space" href="<fmt:message key="path.page.client.add-room-request" bundle="${ path }"/>">
                <fmt:message key="add-room-request.label.search-room" bundle="${ i18n }"/>
            </a>
        </c:when>
        <c:when test="${user.role == null}">
            <a class="btn btn-sm btn-info btn-space" href="<fmt:message key="path.page.signin" bundle="${ path }"/>">
                <fmt:message key="signin.label" bundle="${ i18n }"/>
            </a>
            <span><fmt:message key="our-rooms.label.offer-to-sigin" bundle="${ i18n }"/></span>
        </c:when>
    </c:choose>
    </div>
</div>
</body>
</html>
