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
    <title>Admin account</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <jsp:include page="/jsp/section/header.jsp"/>
    <table class="room-request-list">
        <tr>
            <th>Check-in date</th>
            <th>Check-out date</th>
            <th>Room type</th>
            <th>Room size</th>
            <th>Request status</th>
            <th>User</th>
            <th></th>
        </tr>
        <%--<jsp:useBean id="listRoomRequests" scope="session" class="com.pronovich.hotelbooking.entity.RoomRequest"/>--%>
        <c:forEach items="${listRoomRequests}" var="roomRequest">
            <tr>
                <td>${roomRequest.checkInDate}</td>
                <td>${roomRequest.checkOutDate}</td>
                <td>${roomRequest.roomType}</td>
                <td>${roomRequest.roomSize}</td>
                <td>${roomRequest.requestStatus}</td>
                <td>${roomRequest.user}</td>
                <td><a href="/controller?command=find_room&requestId=${roomRequest.id}">Find room</a></td>
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
            <th>User</th><%--TODO add link to full info about the user--%>
        </tr>
        <%--<jsp:useBean id="listRoomOrders" scope="session" class="com.pronovich.hotelbooking.entity.RoomOrder"/>--%>
        <c:forEach items="${listRoomOrders}" var="roomOrder">
            <tr>
                <td>${roomOrder.checkInDate}</td>
                <td>${roomOrder.checkOutDate}</td>
                <td>${roomOrder.amount}</td>
                <td>${roomOrder.room}</td>
                <td>${roomOrder.orderStatus}</td>
                <td>${roomOrder.user}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
