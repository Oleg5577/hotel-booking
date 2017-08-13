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
<div class="container col-md-4 col-md-offset-1">
    <table class="table vertical-align-table">
        <tbody>
        <tr>
            <td class="text-right">
                <fmt:message key="signup.label.email" bundle="${ i18n }"/>
            </td>
            <td class="text-right">${user.email}</td>
        </tr>
        <tr>
            <td class="text-right">
                <fmt:message key="signup.label.name" bundle="${ i18n }"/>
            </td>
            <td class="text-right">${user.name}</td>
        </tr>
        <tr>
            <td class="text-right">
                <fmt:message key="signup.label.surname" bundle="${ i18n }"/>
            </td>
            <td class="text-right">${user.surname}</td>
        </tr>
        <tr>
            <td class="text-right">
                <fmt:message key="signup.label.phone-number" bundle="${ i18n }"/>
            </td>
            <td class="text-right">${user.phoneNumber}</td>
        </tr>
            <tr>
                <td></td>
                <td class="text-right">
                    <fmt:message key="admin.account.label.role" bundle="${ i18n }"/>
                </td>
            </tr>
        <tr>
            <td></td>
            <td class="text-right">
                <a href="<fmt:message key="path.page.user.edit-user-info" bundle="${ path }"/> " class="btn btn-xs btn-info ">
                    <fmt:message key="common.button.edit" bundle="${ i18n }"/>
                </a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<div class="container">
    <h2 class="text-center"><fmt:message key="admin.account.label" bundle="${ i18n }"/></h2>
</div>
<div class="container col-md-10 col-md-offset-1">
    <h4 class="text-center"><fmt:message key="client.account.requests" bundle="${ i18n }"/></h4>
    <table class="table vertical-align-table">
        <thead>
            <tr>
                <th class="text-center">
                    <fmt:message key="our-rooms.label.check-in" bundle="${ i18n }"/>
                </th>
                <th class="text-center">
                    <fmt:message key="our-rooms.label.check-out" bundle="${ i18n }"/>
                </th>
                <th class="text-center">
                    <fmt:message key="our-rooms.label.room-type" bundle="${ i18n }"/>
                </th>
                <th class="text-center">
                    <fmt:message key="our-rooms.label.room-size" bundle="${ i18n }"/>
                </th>
                <th class="text-center">
                    <fmt:message key="admin.account.label.request-status" bundle="${ i18n }"/>
                </th>
                <th class="text-center">
                    <fmt:message key="admin.account.label.client" bundle="${ i18n }"/>
                </th>
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
                <td class="text-center">${roomRequest.checkInDate}</td>
                <td class="text-center">${roomRequest.checkOutDate}</td>
                <td class="text-center">${roomRequest.roomType}</td>
                <td class="text-center">${roomRequest.roomSize}</td>
                <td class="text-center">${roomRequest.requestStatus}</td>
                <td class="text-right">${roomRequest.user}</td>
                <td class="text-center">
                    <c:if test="${roomRequest.requestStatus == 'IN_PROGRESS'}">
                        <a class="btn btn-xs btn-info" href="/controller?command=find_room&requestId=${roomRequest.id}">Find room</a>
                    </c:if>
                </td>
                <td class="text-center">
                    <c:if test="${roomRequest.requestStatus == 'IN_PROGRESS'}">
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="deny_request_by_admin">
                            <fmt:message key="common.button.cancel" bundle="${ i18n }" var="buttonValue"/>
                            <input type="submit" name="submit" value="${buttonValue}" class="btn btn-xs btn-danger">
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="container col-md-10 col-md-offset-1">
    <h4 class="text-center"><fmt:message key="client.account.orders" bundle="${ i18n }"/></h4>
    <table class="table vertical-align-table">
        <thead>
            <tr>
                <th class="text-center">
                    <fmt:message key="our-rooms.label.check-in" bundle="${ i18n }"/>
                </th>
                <th class="text-center">
                    <fmt:message key="our-rooms.label.check-out" bundle="${ i18n }"/>
                </th>
                <th class="text-right">
                    <fmt:message key="our-rooms.label.room-price" bundle="${ i18n }"/>
                </th>
                <th class="text-center">
                    <fmt:message key="admin.account.label.room" bundle="${ i18n }"/>
                </th>
                <th class="text-center">
                    <fmt:message key="admin.account.label.order-status" bundle="${ i18n }"/>
                </th>
                <th class="text-center">
                    <fmt:message key="admin.account.label.client" bundle="${ i18n }"/>
                </th>
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
                <td  class="text-right">${roomOrder.amount} EUR</td>
                <td class="text-center">${roomOrder.room}</td>
                <td class="text-center">${roomOrder.orderStatus}</td>
<%--TODO add link to full info about the user--%>
                <td class="text-right">${roomOrder.user}</td>
                <td class="text-center">
                    <c:if test="${roomOrder.orderStatus == 'EXPECT_GUEST_ARRIVAL'}">
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="cancel_order_by_admin">
                            <fmt:message key="common.button.cancel" bundle="${ i18n }" var="buttonValue"/>
                            <input type="submit" name="submit" value="${buttonValue}" class="btn btn-xs btn-danger">
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
