<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.locale" var="i18n"/>
<fmt:setBundle basename="property.page" var="path"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="add-room-request.label.search-room" bundle="${ i18n }"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="shortcut icon" href="../../img/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div class="container">
    <div class="row">
        <form class="form-horizontal" method="post" action="/controller">
            <input type="hidden" name="command" value="add_room_request">
            <div class="form-group">
                <label class="control-label col-sm-4" for="checkInRequest">
                    <fmt:message key="add-room-request.label.search-room" bundle="${ i18n }"/>:
                    <span class="asterisk"> *</span>
                </label>
                <div class="col-sm-4">
                    <input type="date" class="form-control" id="checkInRequest" name="checkInRequest"
                           value="${requestValues.checkInRequest}" required>
                    <label class="wrong-values">${wrongValues.checkInRequest}</label>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4" for="checkOutRequest">
                    <fmt:message key="add-room-request.label.check-out" bundle="${ i18n }"/>:
                    <span class="asterisk"> *</span>
                </label>
                <div class="col-sm-4">
                    <input type="date" class="form-control" id="checkOutRequest" name="checkOutRequest"
                           value="${requestValues.checkOutRequest}" required>
                    <label class="wrong-values">${wrongValues.checkOutRequest}</label>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4" for="roomSizeRequest">
                    <fmt:message key="add-room-request.label.room-size" bundle="${ i18n }"/>:
                    <span class="asterisk"> *</span>
                </label>
                <div class="col-sm-4">
                    <select class="select form-control" id="roomSizeRequest" name="roomSizeRequest" required>
                        <option value=""></option>
                        <option value="1" ${requestValues.roomSizeRequest == '1' ? 'selected' : ''}>
                            <fmt:message key="add-room-request.label.room-size.1-person" bundle="${ i18n }"/>
                        </option>
                        <option value="2" ${requestValues.roomSizeRequest == '2' ? 'selected' : ''}>
                            <fmt:message key="add-room-request.label.room-size.2-person" bundle="${ i18n }"/>
                        </option>
                    </select>
                    <label class="wrong-values">${wrongValues.roomSizeRequest}</label>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4" for="roomTypeRequest">
                    <fmt:message key="add-room-request.label.room-type" bundle="${ i18n }"/>:
                    <span class="asterisk"> *</span>
                </label>
                <div class="col-sm-4">
                    <select class="select form-control" id="roomTypeRequest" name="roomTypeRequest" required>
                        <option value=""></option>
                        <option value="standard" ${requestValues.roomTypeRequest == 'standard' ? 'selected' : ''}>
                            <fmt:message key="add-room-request.label.room-type.standard" bundle="${ i18n }"/>
                        </option>
                        <option value="semilux" ${requestValues.roomTypeRequest == 'semilux' ? 'selected' : ''}>
                            <fmt:message key="add-room-request.label.room-type.semilux" bundle="${ i18n }"/>
                        </option>
                        <option value="lux" ${requestValues.roomTypeRequest == 'lux' ? 'selected' : ''}>
                            <fmt:message key="add-room-request.label.room-type.lux" bundle="${ i18n }"/>
                        </option>
                        <option value="president" ${requestValues.roomTypeRequest == 'president' ? 'selected' : ''}>
                            <fmt:message key="add-room-request.label.room-type.president" bundle="${ i18n }"/>
                        </option>
                    </select>
                    <label class="wrong-values">${wrongValues.roomTypeRequest}</label>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-4 col-sm-4">
                    <fmt:message key="add-room-request.button.submit" var="buttonValue" bundle="${ i18n }"/>
                    <input type="submit" class="btn btn-success" name="submit" value="${buttonValue}">
                </div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/jsp/section/footer.jsp"/>
</body>
</html>
