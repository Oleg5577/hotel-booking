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
            <th></th>
        </tr>
        <%--<jsp:useBean id="allRoomsAccordingRequest" scope="request" class="com.pronovich.hotelbooking.entity.Room"/>--%>
        <c:forEach items="${allRoomsAccordingRequest}" var="room">
            <tr>
                <td>${room.roomNumber}</td>
                <td>${room.size}</td>
                <td>${room.price}</td>
                <td>${room.roomType}</td>
                <td>
                    <form action="/controller" method="post">
                        <input hidden name="command" value="create_order">
                        <input hidden name="roomId" value="${room.id}">
                        <input hidden name="requestId" value="${requestId}">
                        <fmt:message key="admin.assign-room.button.submit" var="buttonValue"/>
                        <input type="submit" name="submit" value="${buttonValue}">
                    </form>
                </td>
                <%--<td><a href="/controller?command=assign_room_to_request&=room" Assign to request</td>--%>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
