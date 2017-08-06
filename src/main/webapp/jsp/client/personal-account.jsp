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
        <h2 class="text-center"><fmt:message key="client.account.label" bundle="${ i18n }"/></h2>
        <h4 class="text-center"><fmt:message key="client.account.requests" bundle="${ i18n }"/></h4>
        <table class="table">
            <thead>
                <tr>
                    <th class="text-center">Check-in date</th>
                    <th class="text-center">Check-out date</th>
                    <th class="text-center">Room type</th>
                    <th class="text-center">Room size</th>
                    <th class="text-center">Request status</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${listRoomRequests}" var="roomRequest">
                <tr <c:choose>
                        <c:when test="${roomRequest.requestStatus == 'CONFIRMED'}">class="success" hidden</c:when>
                        <c:when test="${roomRequest.requestStatus == 'IN_PROGRESS'}">class="info"</c:when>
                        <c:when test="${roomRequest.requestStatus == 'DENIED'}">class="danger"</c:when>
                        <c:otherwise>class="default"</c:otherwise>
                    </c:choose>>
                    <td class="text-center">${roomRequest.checkInDate}</td>
                    <td class="text-center">${roomRequest.checkOutDate}</td>
                    <td class="text-center">${roomRequest.roomType}</td>
                    <td class="text-center">${roomRequest.roomSize} person(s)</td>
                    <td class="text-center">${roomRequest.requestStatus}</td>
                    <td class="text-center">
                        <c:if test="${roomRequest.requestStatus == 'IN_PROGRESS'}">
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="cancel_request_by_client">
                                <input hidden name="roomRequestId" value="${roomRequest.id}">
                                <fmt:message key="common.button.cancel" bundle="${ i18n }" var="buttonValue"/>
                                <input type="submit" name="submit" value="${buttonValue}" class="btn btn-md btn-danger">
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="container">
        <h4 class="text-center"><fmt:message key="client.account.orders" bundle="${ i18n }"/></h4>
        <table class="table">
            <thead>
                <tr>
                    <th class="text-center">Check-in date</th>
                    <th class="text-center">Check-out date</th>
                    <th class="text-right">Amount</th>
                    <th class="text-center">Room</th>
                    <th class="text-center">Order status</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${listRoomOrders}" var="roomOrder">
                <tr <c:choose>
                        <c:when test="${roomOrder.orderStatus == 'EXPECT_GUEST_ARRIVAL'}">class="info"</c:when>
                        <c:when test="${roomOrder.orderStatus == 'CHECKED_IN'}">class="active"</c:when>
                        <c:when test="${roomOrder.orderStatus == 'CHECKED_OUT'}">class="success"</c:when>
                        <c:when test="${roomOrder.orderStatus == 'CANCELED'}">class="danger"</c:when>
                        <c:otherwise>class="default"</c:otherwise>
                    </c:choose>>
                    <td class="text-center">${roomOrder.checkInDate}</td>
                    <td class="text-center">${roomOrder.checkOutDate}</td>
                    <td class="text-right">${roomOrder.amount}</td>
                    <td class="text-center">${roomOrder.room}</td>
                    <td class="text-center">${roomOrder.orderStatus}</td>
                    <td class="text-center">
                        <c:if test="${roomOrder.orderStatus == 'EXPECT_GUEST_ARRIVAL'}">
                            <form action="/controller" method="post">
                                <input type="hidden" name="command" value="cancel_order_by_client">
                                <input hidden name="orderId" value="${roomOrder.id}">
                                <fmt:message key="common.button.cancel" bundle="${ i18n }" var="buttonValue"/>
                                <input type="submit" name="submit" value="${buttonValue}" class="btn btn-md btn-danger">
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
