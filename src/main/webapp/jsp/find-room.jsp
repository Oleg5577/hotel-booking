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
    <title>Find room</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<div class="media">
    <div class="media-left">
        <a href="#">
            <img class="media-object" src="..." alt="...">
        </a>
    </div>
    <div class="media-body">
        <h4 class="media-heading">Media heading</h4>
        ...
    </div>
</div>

<form method="post" action="/controller">
    <%--    <input type="hidden" name="command" value="add_room_request">
        <label for="check-in-request"><fmt:message key="find-room.label.check-in"/>:</label>
        <input type="date" id="check-in-request" name="check-in-request" required>--%>
<%--    <br>
    <label for="check-out-request"><fmt:message key="find-room.label.check-out"/>:</label>
    <input type="date" id="check-out-request" name="check-out-request" required>
    <br>--%>
    <%--    TODO ADD RADIO BUTTON ??--%>
    <table>
        <tr>
            <th>//*PHOTO*//</th>
            <th>Room type</th>
            <th>Room size</th>
            <th>Price</th>
            <th>Check-in</th>
            <th>Check-out</th>
            <th></th>
        </tr>
        <tr>
            <td>**PHOTO**</td>
            <td>Standard</td>
            <td>1 guest</td>
            <td>
                <input type="hidden" name="command" value="add_room_request">
                <label><fmt:message key="find-room.label.check-in" bundle="${ i18n }"/>:
                    <input type="date" name="check-in-request" required>
                </label>
            </td>
            <td>
                <label><fmt:message key="find-room.label.check-out" bundle="${ i18n }"/>:
                    <input type="date" id="check-out-request" name="check-out-request" required>
                </label>
            </td>
            <td>
                <label><fmt:message key="find-room.label.check-out" bundle="${ i18n }"/>:
                    <input type="date" id="check-out-request" name="check-out-request" required>
                </label>
            </td>
            <td>**SEARCH IN DB</td>
            <td>
                <fmt:message key="find-room.button.submit" bundle="${ i18n }" var="buttonValue"/>
                <input type="submit" name="submit" value="${buttonValue}">
            </td>
        </tr>
        <tr>
            <td>**PHOTO**</td>
            <td>Standard</td>
            <td>2 guest</td>
            <td>
                <input type="hidden" name="command" value="add_room_request">
                <label><fmt:message key="find-room.label.check-in" bundle="${ i18n }"/>:
                    <input type="date" name="check-in-request" required>
                </label>
            </td>
            <td>
                <label><fmt:message key="find-room.label.check-out" bundle="${ i18n }"/>:
                    <input type="date" id="check-out-request" name="check-out-request" required>
                </label>
            </td>
            <td>**SEARCH IN DB</td>
            <td>
                <fmt:message key="find-room.button.submit" bundle="${ i18n }" var="buttonValue"/>
                <input type="submit" name="submit" value="${buttonValue}">
            </td>
        </tr>
        <tr>
            <td>**PHOTO**</td>
            <td>Semilux</td>
            <td>1 guest</td>
            <td>
                <input type="hidden" name="command" value="add_room_request">
                <label><fmt:message key="find-room.label.check-in" bundle="${ i18n }"/>:
                    <input type="date" name="check-in-request" required>
                </label>
            </td>
            <td>
                <label><fmt:message key="find-room.label.check-out" bundle="${ i18n }"/>:
                    <input type="date" id="check-out-request" name="check-out-request" required>
                </label>
            </td>
            <td>**SEARCH IN DB</td>
            <td>
                <fmt:message key="find-room.button.submit" bundle="${ i18n }" var="buttonValue"/>
                <input type="submit" name="submit" value="${buttonValue}">
            </td>
        </tr>
        <tr>
            <td>**PHOTO**</td>
            <td>Semilux</td>
            <td>2 guests</td>
            <td>
                <input type="hidden" name="command" value="add_room_request">
                <label><fmt:message key="find-room.label.check-in" bundle="${ i18n }"/>:
                    <input type="date" name="check-in-request" required>
                </label>
            </td>
            <td>
                <label><fmt:message key="find-room.label.check-out" bundle="${ i18n }"/>:
                    <input type="date" id="check-out-request" name="check-out-request" required>
                </label>
            </td>
            <td>**SEARCH IN DB</td>
            <td>
                <fmt:message key="find-room.button.submit" bundle="${ i18n }" var="buttonValue"/>
                <input type="submit" name="submit" value="${buttonValue}">
            </td>
        </tr>
        <tr>
            <td>**PHOTO**</td>
            <td>Lux</td>
            <td>1 guest</td>
            <td>
                <input type="hidden" name="command" value="add_room_request">
                <label><fmt:message key="find-room.label.check-in" bundle="${ i18n }"/>:
                    <input type="date" name="check-in-request" required>
                </label>
            </td>
            <td>
                <label><fmt:message key="find-room.label.check-out" bundle="${ i18n }"/>:
                    <input type="date" id="check-out-request" name="check-out-request" required>
                </label>
            </td>
            <td>**SEARCH IN DB</td>
            <td>
                <fmt:message key="find-room.button.submit" bundle="${ i18n }" var="buttonValue"/>
                <input type="submit" name="submit" value="${buttonValue}">
            </td>
        </tr>
        <tr>
            <td>**PHOTO**</td>
            <td>Lux</td>
            <td>2 guests</td>
            <td>
                <input type="hidden" name="command" value="add_room_request">
                <label><fmt:message key="find-room.label.check-in" bundle="${ i18n }"/>:
                    <input type="date" name="check-in-request" required>
                </label>
            </td>
            <td>
                <label><fmt:message key="find-room.label.check-out" bundle="${ i18n }"/>:
                    <input type="date" id="check-out-request" name="check-out-request" required>
                </label>
            </td>
            <td>**SEARCH IN DB</td>
            <td>
                <fmt:message key="find-room.button.submit" bundle="${ i18n }" var="buttonValue"/>
                <input type="submit" name="submit" value="${buttonValue}">
            </td>
        </tr>
        <tr>
            <td>**PHOTO**</td>
            <td>President</td>
            <td>2 guests</td>
            <td>**SEARCH IN DB</td>
            <td>
                <label><fmt:message key="find-room.label.check-out" bundle="${ i18n }"/>:
                    <input type="date" id="check-out-request" name="check-out-request" required>
                </label>
            </td>
            <td>
                <fmt:message key="find-room.button.submit" bundle="${ i18n }" var="buttonValue"/>
                <input type="submit" name="submit" value="${buttonValue}">
            </td>
        </tr>
    </table>

    <%--    <label for="room-size"><fmt:message key="find-room.label.room-size"/>:</label>
        <select id="room-size" name="room-size" required>
            <option value=""></option>
            <option value="1" ${roomRequestValues.roomSize == '1' ? 'selected' : ''}>1 person</option>
            <option value="2" ${roomRequestValues.roomSize == '2' ? 'selected' : ''}>2 persons</option>
        </select>
        <br>
        <label for="room-type"><fmt:message key="find-room.label.room-type"/>:</label>
        <select id="room-type" name="room-type" required>
            <option value=""></option>
            <option value="standard" ${roomRequestValues.roomType == 'standard' ? 'selected' : ''}>standart</option>
            <option value="semilux" ${roomRequestValues.roomType == 'semilux' ? 'selected' : ''}>semilux</option>
            <option value="lux" ${roomRequestValues.roomType == 'lux' ? 'selected' : ''}>lux</option>
            <option value="president" ${roomRequestValues.roomType == 'president' ? 'selected' : ''}>president</option>
        </select>
        <br>
        <fmt:message key="find-room.button.submit" var="buttonValue"/>
        <input type="submit" name="submit" value="${buttonValue}">--%>
</form>
</body>
</html>
