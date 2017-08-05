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
    <title>Add room request</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div class="container">
    <div class="row">
        <form class="form-horizontal" method="post" action="/controller">
            <input type="hidden" name="command" value="add_room_request">
            <div class="form-group">
                <label class="control-label col-sm-4" for="check-in-request">
                    <fmt:message key="add-room-request.label.check-in" bundle="${ i18n }"/>:
                </label>
                <div class="col-sm-4">
                    <input type="date" class="form-control" id="check-in-request" name="check-in-request" placeholder="">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4" for="check-out-request">
                    <fmt:message key="add-room-request.label.check-out" bundle="${ i18n }"/>:
                </label>
                <div class="col-sm-4">
                    <input type="date" class="form-control" id="check-out-request" name="check-out-request" placeholder="">
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-4 col-sm-4">
                    <button type="submit" class="btn btn-success">Submit</button>
                </div>
            </div>
        </form>
    </div>
</div>


<%--<form method="post" action="/controller">--%>
    <%--<input type="hidden" name="command" value="add_room_request">--%>
    <%--<label for="check-in-request"><fmt:message key="add-room-request.label.check-in"/>:</label>--%>
    <%--<input type="date" id="check-in-request" name="check-in-request" required>--%>
    <%--<br>--%>
    <%--<label for="check-out-request"><fmt:message key="add-room-request.label.check-out"/>:</label>--%>
    <%--<input type="date" id="check-out-request" name="check-out-request" required>--%>
    <%--<br>--%>
    <label for="room-size"><fmt:message key="add-room-request.label.room-size" bundle="${ i18n }"/>:</label>
    <select id="room-size" name="room-size" required>
        <option value=""></option>
        <option value="1" ${roomRequestValues.roomSize == '1' ? 'selected' : ''}>1 person</option>
        <option value="2" ${roomRequestValues.roomSize == '2' ? 'selected' : ''}>2 persons</option>
    </select>
    <%--<br>--%>
    <label for="room-type"><fmt:message key="add-room-request.label.room-type" bundle="${ i18n }"/>:</label>
    <select id="room-type" name="room-type" required>
        <option value=""></option>
        <option value="standard" ${roomRequestValues.roomType == 'standard' ? 'selected' : ''}>standart</option>
        <option value="semilux" ${roomRequestValues.roomType == 'semilux' ? 'selected' : ''}>semilux</option>
        <option value="lux" ${roomRequestValues.roomType == 'lux' ? 'selected' : ''}>lux</option>
        <option value="president" ${roomRequestValues.roomType == 'president' ? 'selected' : ''}>president</option>
    </select>
    <br>
    <fmt:message key="add-room-request.button.submit" var="buttonValue" bundle="${ i18n }"/>
    <input type="submit" name="submit" value="${buttonValue}">
</form>
</body>
</html>
