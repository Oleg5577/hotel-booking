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
    <title><fmt:message key="admin.account.label" bundle="${ i18n }"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div class="container">
    <h2 class="text-center"><fmt:message key="admin.account.label" bundle="${ i18n }"/></h2>
    <h4><fmt:message key="client.account.requests" bundle="${ i18n }"/></h4>
    <table class="table">
        <thead>
            <tr>
                <th>Check-in date</th>
                <th>Check-out date</th>
                <th>Room type</th>
                <th>Room size</th>
                <th>Request status</th>
                <th>Client</th>
                <th></th>
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
                <td>${roomRequest.checkInDate}</td>
                <td>${roomRequest.checkOutDate}</td>
                <td>${roomRequest.roomType}</td>
                <td>${roomRequest.roomSize}</td>
                <td>${roomRequest.requestStatus}</td>
                <td>${roomRequest.user}</td>
                <td>
                    <c:if test="${roomRequest.requestStatus == 'IN_PROGRESS'}">
                        <a class="btn btn-md btn-info" href="/controller?command=find_room&requestId=${roomRequest.id}">Find room</a>
                    </c:if>
                </td>
                <td>
                    <c:if test="${roomRequest.requestStatus == 'IN_PROGRESS'}">
                        <form action="#<%-- TODO add link--%>">
                            <input type="hidden" name="command" value="cancel_client_request">
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
    <h4><fmt:message key="client.account.orders" bundle="${ i18n }"/></h4>
    <table class="table">
        <thead>
            <tr>
                <th>Check-in date</th>
                <th>Check-out date</th>
                <th>Amount</th>
                <th>Room</th>
                <th>Order status</th>
                <th>Client</th>
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
                <td>${roomOrder.checkInDate}</td>
                <td>${roomOrder.checkOutDate}</td>
                <td  class="text-right">${roomOrder.amount} EUR</td>
                <td>${roomOrder.room}</td>
                <td>${roomOrder.orderStatus}</td>
<%--TODO add link to full info about the user--%>
<%--TODO add l18n--%>
                <td>${roomOrder.user}</td>
                <td>
                    <c:if test="${roomOrder.orderStatus == 'EXPECT_GUEST_ARRIVAL'}">
                        <form action="#<%-- TODO add link--%>">
                            <input type="hidden" name="command" value="cancel_client_request">
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
