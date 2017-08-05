<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler" var="i18n"/>
<fmt:setBundle basename="property.page" var="path"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="client.account.label" bundle="${ i18n }"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <jsp:include page="/jsp/section/header.jsp"/>
    <div class="container">
        <h2><fmt:message key="client.account.label" bundle="${ i18n }"/></h2>
        <p>Contextual classes can be used to color table rows or table cells. The classes that can be used are: .active, .success, .info, .warning, and .danger.</p>
        <table class="table">
            <thead>
                <tr>
                    <th>Check-in date</th>
                    <th>Check-out date</th>
                    <th>Room type</th>
                    <th>Room size</th>
                    <th>Request status</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Default</td>
                    <td>Defaultson</td>
                    <td>def@somemail.com</td>
                </tr>
            <tr class="success">
                <td>Success</td>
                <td>Doe</td>
                <td>john@example.com</td>
            </tr>
            <tr class="danger">
                <td>Danger</td>
                <td>Moe</td>
                <td>mary@example.com</td>
            </tr>
            <tr class="info">
                <td>Info</td>
                <td>Dooley</td>
                <td>july@example.com</td>
            </tr>
            <tr class="warning">
                <td>Warning</td>
                <td>Refs</td>
                <td>bo@example.com</td>
            </tr>
            <tr class="active">
                <td>Active</td>
                <td>Activeson</td>
                <td>act@example.com</td>
            </tr>
            </tbody>
        </table>
    </div>

    <table class="room-request-list">
        <tr>
            <th>Check-in date</th>
            <th>Check-out date</th>
            <th>Room type</th>
            <th>Room size</th>
            <th>Request status</th>
        </tr>
        <c:forEach items="${listRoomRequests}" var="roomRequest">
            <tr>
                <td>${roomRequest.checkInDate}</td>
                <td>${roomRequest.checkOutDate}</td>
                <td>${roomRequest.roomType}</td>
                <td>${roomRequest.roomSize}</td>
                <td>${roomRequest.requestStatus}</td>
            </tr>
        </c:forEach>
    </table>
    <table class="room-order-list">
        <tr>
            <th>Check-in date</th>
            <th>Check-out date</th>
            <th>Amount</th>
            <th>Room</th>
            <th>Order status</th>
        </tr>
        <c:forEach items="${listRoomOrders}" var="roomOrder">
            <tr>
                <td>${roomOrder.checkInDate}</td>
                <td>${roomOrder.checkOutDate}</td>
                <td>${roomOrder.amount}</td>
                <td>${roomOrder.room}</td>
                <td>${roomOrder.orderStatus}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
