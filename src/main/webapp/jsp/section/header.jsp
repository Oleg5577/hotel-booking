<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="property.bundler"/>
<fmt:setBundle basename="property.page" var="path"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header>
    <form>
        <select id="language" name="language" onchange="submit()">
            <option value="en_US" ${language == "en_US" ? "selected" : ""}>English</option>
            <option value="ru_RU" ${language == "ru_RU" ? "selected" : ""}>Русский</option>
        </select>
    </form>
    <jsp:useBean id="user" scope="session" class="com.pronovich.hotelbooking.entity.User"/>
    <c:if test="${user.id != null}">
        <form method="post" action="controller">
            <input type="hidden" name="command" value="sign_out">
            <fmt:message key="signout.button.submit" var="buttonValue"/>
            <input type="submit" name="submit" value="${buttonValue}">
        </form>
    </c:if>
    <c:if test="${user.id == null}">
        <br>
        <a href="<fmt:message key="path.page.signup" bundle="${ path }"/>">Go to sign up</a>
        <br>
        <a href="<fmt:message key="path.page.signin" bundle="${ path }"/>">Go to sign in</a>
        <br>
    </c:if>
    <c:choose>
        <c:when test="${user.role == 'ADMIN'}">
<%--            <form action="/controller" method="get">
                <input type="hidden" name="command" value="get_info_for_admin_account">
                <fmt:message key="admin.account.button.submit" var="buttonValue"/>
                <input type="submit" name="submit" value="${buttonValue}">
            </form>--%>
            <%--<a href="<fmt:message key="path.page.admin.admin-account" bundle="${ path }"/>">Admin account</a>--%>
            <a href="/controller?command=find_info_for_admin_account">Admin account</a>
            <br>
        </c:when>
        <c:when test="${user.role == 'USER'}">
            <a href="<fmt:message key="path.page.user.add-room-request" bundle="${ path }"/>">Add room request</a>
            <br>
            <a href="<fmt:message key="path.page.user.personal-account" bundle="${ path }"/>">Personal-account</a>
            <br>
        </c:when>
    </c:choose>
</header>
</body>
</html>
