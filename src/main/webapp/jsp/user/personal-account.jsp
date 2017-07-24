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
    <title>Add room request</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<jsp:include page="/jsp/section/header.jsp"/>
<table class="room-request-list">
    <tr>
        <th>
            Check-in date
        </th>
        <th>
            Check-out date
        </th>
        <th>
            Room size
        </th>
        <th>
            Room type
        </th>
        <th>
            Request status
        </th>
    </tr>
    <jsp:useBean id="listRoomRequest" scope="request" type="java.util.List"/>
    <c:forEach items="${listRoomRequest}" var="roomRequest">
        <tr>
            <td>
                    ${roomRequest.countryFrom},
            </td>
            <td>
                    ${roomRequest.countryTo},
            </td>
            <td>
                    ${roomRequest.dateLoadingFrom} -
            </td>
            <td>
                    ${roomRequest.transportType}
            </td>
            <td>
                    ${roomRequest.transportType}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
