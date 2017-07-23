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
    <title>Sign up</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <jsp:include page="section/header.jsp"/>
    <a href="<fmt:message key="path.page.add-room-request" bundle="${ path }"/>">Add room request</a>
    <br>
    <a href="<fmt:message key="path.page.signup" bundle="${ path }"/>">Go to sign up</a>
    <br>
    <a href="<fmt:message key="path.page.signin" bundle="${ path }"/>">Go to sign in</a>
    <br>
    <%--<form action="<fmt:message key="path.page.signup" bundle="${ path }"/>">--%>
    <%--<input type="submit" value="Go to sign up">--%>
    <%--</form>--%>
    <form action="<fmt:message key="path.page.signin" bundle="${ path }"/>">
        <input type="submit" value="Go to sign in">
    </form>
    <form method="post" action="/controller">
        <input type="hidden" name="command" value="add_room_request">

        <label for="check-in-request"><fmt:message key="add-room-request.label.check-in"/>:</label>
        <input type="date" id="check-in-request" name="check-in-request" required>

        <label for="check-out-request"><fmt:message key="add-room-request.label.check-out"/>:</label>
        <input type="date" id="check-out-request" name="check-out-request" required>

        <label for="room-size"><fmt:message key="add-room-request.label.room-size"/>:</label>
        <select id="room-size" name="room-size" required>
            <option value=""></option>
            <option value="1" ${roomRequestValues.roomSize == '1' ? 'selected' : ''}>1 person</option>
            <option value="2" ${roomRequestValues.roomSize == '2' ? 'selected' : ''}>2 persons</option>
        </select>
        <select id="room-type" name="room-size" required>
            <option value=""></option>
            <option value="standart" ${roomRequestValues.roomType == 'standart' ? 'selected' : ''}>standart</option>
            <option value="semilux" ${roomRequestValues.roomType == 'semilux' ? 'selected' : ''}>semilux</option>
            <option value="lux" ${roomRequestValues.roomType == 'lux' ? 'selected' : ''}>lux</option>
            <option value="president" ${roomRequestValues.roomType == 'president' ? 'selected' : ''}>president</option>
        </select>
    </form>
</body>
</html>
