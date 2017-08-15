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
    <title>Find room by request</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="shortcut icon" href="../../img/favicon.ico" type="image/x-icon">
</head>
<body>
    <jsp:include page="/jsp/section/header.jsp"/>
<div class="container col-md-8 col-md-offset-2">
    <table class="table vertical-align-table">
        <tr>
            <th class="text-center">Room number</th>
            <th class="text-center">Room size</th>
            <th class="text-center">Price</th>
            <th class="text-center">Room type</th>
            <th></th>
        </tr>
        <c:forEach items="${allRoomsAccordingRequest}" var="room">
            <tr class="info">
                <td class="text-center">${room.roomNumber}</td>
                <td class="text-center">${room.size}</td>
                <td class="text-center">${room.price} EUR</td>
                <td class="text-center">${room.roomType}</td>
                <td class="text-center">
                    <form action="/controller" method="post">
                        <input hidden name="command" value="create_order">
                        <input hidden name="roomId" value="${room.id}">
                        <input hidden name="requestId" value="${requestId}">
                        <fmt:message key="admin.create-order.button.submit" bundle="${ i18n }" var="buttonValue"/>
                        <input class="btn btn-xs btn-info" type="submit" name="submit" value="${buttonValue}">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="/jsp/section/footer.jsp"/>
</body>
</html>
