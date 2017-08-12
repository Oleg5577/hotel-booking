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
    <%--TODO make tag for user-info--%>
    <table class="table vertical-align-table">
        <tbody>
        <tr>
            <td class="text-center">Email</td>
            <td class="text-center">${user.email}</td>
        </tr>
        <tr>
            <td class="text-center">Name</td>
            <td class="text-center">${user.name}</td>
        </tr>
        <tr>
            <td class="text-center">Surname</td>
            <td class="text-center">${user.surname}</td>
        </tr>
        <tr>
            <td class="text-center">Phone number</td>
            <td class="text-center">${user.phoneNumber}</td>
        </tr>
        <c:if test="${user.role == 'ADMIN'}">
            <tr>
                <td class="text-center">Role</td>
                <td class="text-center">${user.role}</td>
            </tr>
        </c:if>
        <tr>
            <td></td>
            <td class="text-center">
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
    <h4><fmt:message key="client.account.requests" bundle="${ i18n }"/></h4>
    <table class="table vertical-align-table">
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
                        <a class="btn btn-xs btn-info" href="/controller?command=find_room&requestId=${roomRequest.id}">Find room</a>
                    </c:if>
                </td>
                <td>
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
    <h4><fmt:message key="client.account.orders" bundle="${ i18n }"/></h4>
    <table class="table vertical-align-table">
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
                <td>${roomOrder.user}</td>
                <td>
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
